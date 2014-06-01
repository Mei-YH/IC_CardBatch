package com.icsys.batch.task.offline.sensor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Sensor;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.SystemParamValue;

/**
 * 
 * @author 
 * @version 1.0
 */
public class OfflineTranSensor implements Sensor {

	private static Logger LOG = Logger.getLogger(OfflineTranSensor.class);
	
	@SuppressWarnings("unchecked")
	public JobContext[] detect(JobContext context) throws JobException {
		List<JobContext> jobContexts = new ArrayList<JobContext>();
		if(LOG.isInfoEnabled()){
			LOG.info("开始探测是否有需要处理的脱机消费文件...");
		}
		/*String ftpURL = (String) context.getAttribute("ftpURL");
		String ftpDir = (String) context.getAttribute("ftpDir");
		String localDir = (String) context.getAttribute("localDir");
		if(LOG.isInfoEnabled()){
			LOG.info("===========ftpURL:" + ftpURL + "===============ftpDir:" + ftpDir + "==========localDir:" + localDir);
		}
		List<String> dataFileNames = null;
		String tempDateStr = null;//脱机数据文件需要
		String downloadDate = null;
		try{
			if(getOfflineFile(new URL(ftpURL), ftpDir, localDir)){//下载文件
				LOG.info("==========开始遍历下载到的文件=============");
				downloadDate = Utils.getClearDate();// 日期格式yyyymmdd
				SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
				Date date = null;
				//获取T-1日期
				date = sp.parse(downloadDate);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DAY_OF_MONTH, -1);
				sp.applyPattern("yyyy-MM-dd");
				downloadDate = sp.format(c.getTime());
				sp.applyPattern("yyMMdd");
				tempDateStr = sp.format(c.getTime());
				dataFileNames = getDataFileNames(localDir+downloadDate+"/");//确保本地成功
			}else{
				return new JobContext[]{};//没有要处理的脱机文件
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		/*
		 * 检查FTS发送文件完整性
		 * 山东农信只有银联清算文件，暂时不考虑本行清算文件
		 */
		boolean isExist = false;
		List<File> cpList = new ArrayList<File>();
		Map<String,String> destFile = new HashMap<String,String>();
		String path = (String)context.getAttribute("offlinePath");
		String localPath = (String)context.getAttribute("UnionpayPath");
		localPath = localPath.endsWith(File.separator)?localPath:localPath + File.separator;
		String date = null;
		try{
			date = SystemParamValue.getClearDate();
			String lastDate = BatchDateUtil.getLastDateWithStringFormat(date);
				File[] dirList = new File(path,lastDate).listFiles(new FileFilter(){
					public boolean accept(File pathName){
						return pathName.isDirectory();/*入网机构*/
					}
				});
				if(null == dirList){
					LOG.error("无清算日期[" + lastDate + "]目录！");
					throw new Exception("无清算日期[" + lastDate + "]目录！");
				}
				if(dirList.length != 17){
					LOG.error("入网机构目录总数[" + dirList.length + "]数目不正确！");
					throw new Exception("入网机构目录总数[" + dirList.length + "]数目不正确！");
				}
				for(File dir:dirList){
					File[] fileList = dir.listFiles(new FileFilter(){
						public boolean accept(File pathName){
							return pathName.getName().endsWith(".chk");
						}
					});
					if(fileList.length != 2){
						LOG.error("入网机构[" + dir.getName() + "]文件数目不正确！");
						throw new Exception("入网机构[" + dir.getName() + "]文件数目不正确！");
					}
				}
				if(!new File(localPath + date).exists()){
					new File(localPath + date).mkdir();
				}
				for(File dir : dirList){
					File[] fileList = dir.listFiles(new FileFilter(){
						public boolean accept(File pathName){
							return pathName.getName().endsWith(".chk");
						}
					});
					for(File file:fileList){
						List records = FileUtils.readLines(file);
						for(Object o:records){
							String line = o.toString().trim();
							if("".equals(line)){
								continue;
							}
							File re = new File(file.getParent(),line.substring(0,line.indexOf(" ")));
							isExist = re.exists();
							if(isExist && Long.parseLong(line.substring(line.lastIndexOf(" ") + 1).trim()) == re.length()){
								if(re.length()%526 == 95 && re.length()/526 > 0){
									cpList.add(re);
								}
							}else{
								LOG.error("入网机构[" + dir.getName() + "]文件异常！");
								LOG.debug("isExist:" + isExist +",文件实际长度：" + re.length() + ",文件校验长度：" + 
										line.substring(line.lastIndexOf(" ") + 1).trim());
								isExist = false;
								break;
							}
						}
						if(isExist){
							for(File result:cpList){
								File toFile = new File(localPath + date + File.separator + result.getParentFile().getName(),result.getName());
								if(!toFile.getParentFile().exists()){
									toFile.getParentFile().mkdirs();
								}
								FileUtils.copyFile(result, toFile);
								FileUtils.touch(new File(toFile.getParent(),toFile.getName() + ".ok"));
								String org = destFile.get(result.getParentFile().getName());
								if(null != org){
									org = org + "|" + toFile.getAbsolutePath();
								}else{
									org = toFile.getAbsolutePath();
								}
								destFile.put(result.getParentFile().getName(),org);
								result.delete();
							}
							file.delete();
						}
						cpList.clear();
						isExist = false;
					}
				}
		}catch(Exception e){
			LOG.error("执行探测任务异常，原因:" + e.getMessage());
			return new JobContext[]{};
		}
		Iterator<Map.Entry<String, String>> dataFileName = destFile.entrySet().iterator();
		while (dataFileName.hasNext()) {
			Map.Entry<String, String> entry = dataFileName.next();
			String[] split = entry.getValue().split("\\|");
			for(String fn:split){
				JobContext jobContext = new JobContext();
				jobContext.setAttribute("dataFileName", fn);
	//			jobContext.setJobDate(date);
				jobContext.setAttribute("jobDate", date);
				jobContext.setAttribute("billOrg", entry.getKey());
				jobContexts.add(jobContext);
			}
		}
		if (jobContexts.size() < 1) {
			LOG.info("没有找到可以处理的本机脱机消费文件,探测结束.");
			return new JobContext[]{};
		}
		if(LOG.isInfoEnabled()){
			LOG.info("共找到了【" + jobContexts.size()+ "】个文件.");
			LOG.info("脱机消费文件探测结束...");
		}
		return jobContexts.toArray(new JobContext[jobContexts.size()]);
	}
	
	/**
	 * 
	 * @param ftpURL
	 *            远程FTP地址，格式：ftp://test:test@10.1.18.195:21
	 * @param ftpDir
	 *            远程FTP服务器文件路径，不包括 YYYY-MM-DD/IC_CARD_FILE.OK
	 * @param localDir
	 *            本地保存路径,实际路径为：localDir + “/YYYY-MM-DD/IC_CARD_FILE.OK”
	 * @return 文件存在，并且下载成功，返回true
	 * @throws Exception 
	 */
	/*public static boolean getOfflineFile(URL ftpURL, String ftpDir,
			String localDir) throws Exception {
		String downloadDate = Utils.getClearDate();// 日期格式yyyymmdd
		SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		String tempDateStr = null;//脱机数据文件需要
		try {//获取T-1日期
			date = sp.parse(downloadDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, -1);
			sp.applyPattern("yyyy-MM-dd");
			downloadDate = sp.format(c.getTime());
			sp.applyPattern("yyMMdd");
			tempDateStr = sp.format(c.getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		ftpDir = ftpDir + "/" + downloadDate  + "/";
		localDir = localDir + "/" +downloadDate+ "/";
		if (LOG.isInfoEnabled()) {
			LOG.info("==================FTPURL: " + ftpURL + "=============== DIR:" + ftpDir + "==============localDir" + localDir);
		}
		List <String> localFileFlag = getDataFileNames(localDir);
		if(localFileFlag!=null&&localFileFlag.size()>0){
			for (Iterator iterator = localFileFlag.iterator(); iterator.hasNext();) {
				String localFileName = (String) iterator.next();
				if(localFileName.endsWith(".OK")&&localFileName.startsWith("INF"+tempDateStr)){
					if (LOG.isInfoEnabled()) {
						LOG.info("重复下载标识文件存在:" + downloadDate + "脱机清算已经下载过了");
					}
					return false;//表示已经下载过了
				}
			}
		}
		PlatformFTP arteryFTP = new PlatformFTP();
		try {
			arteryFTP.connectServer(ftpURL);// 去总行银联取文件的URL
			if (arteryFTP.existDirectory(ftpDir + "IC_CARD_FILE.OK")) {//如果标识文件存在，表示已经生成脱机清算文件
				List<String> fileNames = arteryFTP.getFileList(ftpDir+"INF"+tempDateStr+"??C");
//				List<String> fileNames = arteryFTP.getFileList(ftpDir);
				for (Iterator iterator = fileNames.iterator(); iterator.hasNext();) {
					String fileName = (String) iterator.next();
					if(fileName!=null){
						//fileName = fileName.substring(fileName.indexOf("INF"+tempDateStr));
						fileName = fileName.replace(ftpDir, "");
					}
					if(!"IC_CARD_FILE.OK".equals(fileName)){//下载数据文件
						arteryFTP.download(ftpDir + fileName, localDir + fileName);
						File flagFile = new File(localDir + fileName + ".OK");
						flagFile.createNewFile();
						if (LOG.isInfoEnabled()) {
							LOG.info("主机当前文件下载成功，以及生成对应的防重复下载标识文件:" + fileName);
						}
					}
				}
				if (LOG.isInfoEnabled()) {
					LOG.info("=================主机文件全部下载成功,生成防止重复下载的OK文件======================");
				}
				return true;//处理完所有数据文件
			}
			if (LOG.isInfoEnabled()) {
				LOG.info("主机文件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			arteryFTP.closeServer();
		}
		return false;
	}
		
	/**
	 * 遍历指定目录下的所有文件
	 * 
	 * @param fileRootDir 指定目录
	 *            
	 * @return 数据文件名
	 */
	/*public static synchronized List<String> getDataFileNames(String fileRootDir) {
		List<String> dataFileNames = new ArrayList<String>();
		File offlineDir = new File(fileRootDir);
		LOG.info("本地存放文件的路径为["+fileRootDir+"]");
		if (offlineDir.isDirectory()) {
			File[] unionLocalDir = offlineDir.listFiles();
			for (File fileSrc : unionLocalDir) {
				if(fileSrc.isFile()){
					String name = fileSrc.getName();
					LOG.info("===========查找到的文件为["+name+"]");
					dataFileNames.add(name);
				}
			}
		}
		if(offlineDir.isFile()){
			dataFileNames.add(offlineDir.getName());
		}
		return dataFileNames;
	}	*/
}
