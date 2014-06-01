package com.icys.batch.test.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.SystemParamValue;

/**
 * @author admin
 * <br/> 批量任务文件探测
 */
public class TaskSenor {

	/**
	 * </br> 01-平台日切扎帐
	 * @param sysdate 系统时间
	 * @param workfilePath 参数文件路径
	 * @return 
	 */
	public static String dayEndSensor (String sysdate, String workfilePath ){

		String noticPath = getValue("core_notice_path",workfilePath);
		try{
			File notic = new File(noticPath,sysdate);
			notic = new File(notic,"ISDADCF.FCRECDT.PS");
			if(notic.exists()){				
				return "发现核心 T1结束文件进行日切。";
			}
			return "未发现文件ISDADCF.FCRECDT.PS不能进行日切。";
		}catch (Exception e) {
			return "00-平台日切扎帐 文件探测异常";
		}

	}

	/**
	 * </br> 02-本行及银联对账
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String checkAccountSensor (String sysdate, String workfilePath ){
		String corePath = getValue("core_file_path",workfilePath);
		String localPath = getValue("local_file_path",workfilePath);
		if (null == corePath || "".equals(corePath)) {
			return "核心对账文件存放路径值为NULL";
		}
		if (null == localPath || "".equals(localPath)) {
			return "本地对账文件存放路径值为NULL";
		}

		List<File> checkFileList = new ArrayList<File>();
		try {
			File coreFile = new File(corePath);
			File filePath = new File(localPath,sysdate);
			if(!filePath.exists()){
				filePath.mkdir();
			}
			final String lastDate = BatchDateUtil.getLastDateWithStringFormat(sysdate);
			File[] chkFile = coreFile.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(lastDate.substring(4) + ".chk");
				}
			});
			for (File temp : chkFile) {
				List<?> records = FileUtils.readLines(temp, "GBK");
				for (Object o : records) {
					if ("".equals(o.toString().trim())) {
						continue;
					}
					String[] split = o.toString().trim().split(" ");
					File tempCheck = new File(corePath, split[0]);
					/*验证对账文件*/
					if (tempCheck.exists() && Long.valueOf(split[1]) == tempCheck.length()) {
						checkFileList.add(tempCheck);
					}
				}
			}
			if (checkFileList.size() < 1) {
				return "没有探测到需要处理的对账文件!";
			} else {
				return "共找到了【" + checkFileList.size()+ "】个文件.对账文件探测结束...";
			}
		} catch (Exception e) {
			return "02-本行及银联对账文件探测异常";
		}
	}



	/**
	 * </br>03-脱机清算
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String offlineTranSensor (String sysdate, String workfilePath ){

		/*
		 * 检查FTS发送文件完整性
		 * 山东农信只有银联清算文件，暂时不考虑本行清算文件
		 */
		boolean isExist = false;
		List<File> cpList = new ArrayList<File>();
		Map<String,String> destFile = new HashMap<String,String>();


		String path = getValue("offlineTran_offlinePath",workfilePath);
		String localPath =getValue("offlineTran_UnionpayPath",workfilePath);
		localPath = localPath.endsWith(File.separator)?localPath:localPath + File.separator;
		String date = null;
		try{
			// 0004 清算周期
			date  = DataBaseUtil.sysPrmValue("000004");

			System.out.println(localPath);

			String lastDate = BatchDateUtil.getLastDateWithStringFormat(date);
			File[] dirList = new File(path,lastDate).listFiles(new FileFilter(){
				public boolean accept(File pathName){
					return pathName.isDirectory();/*入网机构*/
				}
			});
			if(null == dirList){
				return "无清算日期[" + lastDate + "]目录！";
			}
			if(dirList.length != 17){
				return "入网机构目录总数[" + dirList.length + "]数目不正确！";
			}
			for(File dir:dirList){
				File[] fileList = dir.listFiles(new FileFilter(){
					public boolean accept(File pathName){
						return pathName.getName().endsWith(".chk");
					}
				});
				if(fileList.length != 2){
					return "入网机构[" + dir.getName() + "]文件数目不正确！";
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
							return "入网机构[" + dir.getName() + "]文件异常！";
							//isExist = false;
							//break;
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
			return "执行探测任务异常，原因:" + e.getMessage();
		}
		Iterator<Map.Entry<String, String>> dataFileName = destFile.entrySet().iterator();
		// 判断任务用的
		List<String>ListJop = new ArrayList<String>(); 
		while (dataFileName.hasNext()) {
			Map.Entry<String, String> entry = dataFileName.next();
			String[] split = entry.getValue().split("\\|");
			for(String fn:split){
				ListJop.add(fn);
			}
		}
		if (ListJop.size() < 1) {
			return "没有找到可以处理的本机脱机消费文件,探测结束.";
		} else{
			return "共找到了【" + ListJop.size()+ "】个文件.脱机消费文件探测结束...";
		}
	}


	/**
	 * </br>11-汇总脱机清算
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String offlineResultSensor (String sysdate, String workfilePath){

		String localPath = getValue("UnionpayPath",workfilePath);
		localPath = localPath.endsWith(File.separator)?localPath:localPath + File.separator;
		try{
			// 0004 清算周期
			String date  = DataBaseUtil.sysPrmValue("000004");
			File[] list = new File(localPath + date).listFiles(new FileFilter() {

				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});
			if(null == list){
				return "未执行清算日[" + date + "]批量程序！";
			}
			boolean isOK = false;
			for(File file:list){
				File[] ok = file.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname.getName().endsWith(".ok");
					}
				});
				if(ok.length > 0){
					isOK = true;
					break;
				}
			}
			if(!isOK){
				return "脱机清算批处理任务已完成,将进行文件汇总";

			}
			return "脱机清算批处理任务未完成...";
		}catch (Exception e) {
			e.printStackTrace();
			return "11-汇总脱机清算 文件探测出现异常...";
		}	
	}


	/**
	 * </br>08-转收益结果数据
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String profitDataSensor (String sysdate, String workfilePath){



		String minLimit = null;
		String date = null;
		String last_date = null;
		try {
			minLimit  = DataBaseUtil.sysPrmValue("100001");// 账户余额最低额度
			date = SystemParamValue.getSystemDate();
			last_date = BatchDateUtil.getLastDateWithStringFormat(date);

		} catch (Exception e) {
			return "获取系统参数异常";
		}
		final String lastDate = last_date;
		BigDecimal limit = new BigDecimal((minLimit == null || ""
				.equals(minLimit)) ? "0" : minLimit);
		if (limit.compareTo(BigDecimal.ZERO) <= 0) {
			return  "账户余额最低额度为0或null则不执行";
		}

		String filePath = getValue("profit_filePath",workfilePath);
		String localPath =getValue("profit_localPath",workfilePath);
		List<File> fileList = new ArrayList<File>();

		try {
			if (filePath != null) {

				File[] files = new File(filePath).listFiles(new FileFilter() {

					public boolean accept(File pathname) {
						return "FCRD854R".equals(pathname.getName()
								.substring(8, 16))
								&& pathname.getName().endsWith(lastDate + ".CHK");
					}

				});
				if(files.length != 17){
					return "文件个数不正确。";
				}
				for (File temp : files) {
					List<?> records = FileUtils.readLines(temp);
					for (Object o : records) {
						if ("".equals(o.toString().trim())) {
							continue;
						}
						String[] split = o.toString().trim().split(" ");
						File f = new File(temp.getParent(), split[0]);
						if (f.exists()&& Long.valueOf(split[1]) == f.length()) {
							fileList.add(f);
						}
					}
				}
				// if(files.length==34){
				/**
				 * 如果判断文件有34个则进行判断.CHK文件进行检查，返回context进行执行
				 */
				// List<File> fileList =
				// CheckFileUtil.getCheckSuccessFiles(filePackage,endOfCheckFile,histroyFile);
				// }else{
				// LOG.debug("得到数据个数只有"+files.length+"个,必须是34个！");
				// return new JobContext[]{};
				// }
			} else {
				return "文件路径不存在！参数不能为空";

			}
			if (fileList.size() > 0 && null != fileList) {// 如果返回有处理的文件
				List<String> listJop = new ArrayList<String>();
				for (File f : fileList) {
					if( f.length() > 0){
						listJop.add(f.getName());
					}
				}
				//  TODO 需要仔细判断
				if (listJop.size()>0){
					return "true共找到了【" + listJop.size()+ "】个文件.";
				} else {
					return "检查后没有需要处理的文件";
				}

			} else {
				return "检查后没有需要处理的文件";
			}
		} catch (Exception e) {
			return "08-转收益结果数据 文件探测出现异常";
		}
	}

	/**
	 * </br>09-睡眠账户扣收结果数据
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String sleepAcctManageDataSensor (String sysdate, String workfilePath){

		String minLimit = null;
		String cycle = null;
		String date = null;
		String last_date = null;
		try {
			minLimit = DataBaseUtil.sysPrmValue("100001");// 账户余额最低额度
			cycle = DataBaseUtil.sysPrmValue("100002");// 无业务间隔时间
			// 系统时间 0003
			date = sysdate;
			last_date = BatchDateUtil.getLastDateWithStringFormat(date);
		} catch (Exception e) {
			return "获取系统参数异常";
		}
		final String lastDate = last_date;
		int iCycle = Integer.valueOf(cycle);
		BigDecimal limit = new BigDecimal((minLimit == null || ""
				.equals(minLimit)) ? "0" : minLimit);

		if (limit.compareTo(BigDecimal.ZERO) == 0) {
			return  "账户余额最低额度为0或null则不执行";
		}

		// String endOfCheckFile = ".CHK";//检查文件结尾标识
		String filePath = getValue("sleep_filePath",workfilePath);
		String localPath =getValue("sleep_localPath",workfilePath);
		List<File> fileList = new ArrayList<File>();
		try {
			if (filePath != null) {
				File[] files = new File(filePath).listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return "FCR2201E".equals(pathname.getName().substring(8, 16)) && pathname.getName().endsWith(lastDate + ".CHK");
					}

				});
				if(files.length != 17){
					return "文件个数不正确。";
				}
				for (File temp : files) {
					List<?> records = FileUtils.readLines(temp);
					for (Object o : records) {
						if ("".equals(o.toString().trim())) {
							continue;
						}
						String[] split = o.toString().trim().split(" ");
						File f = new File(temp.getParent(), split[0]);
						if (f.exists()&& Long.valueOf(split[1]) == f.length()) {
							fileList.add(f);
						}
					}
				}
			} else {
				return "文件路径不存在！参数不能为空";
			}
			if (fileList.size() > 0 && null != fileList) {// 如果返回有处理的文件
				List<String> listJop = new ArrayList<String>();
				for (File f : fileList) {
					if(f.length() > 0){
						listJop.add(f.getName());
					}
				}
				//  TODO 需要仔细判断
				if (listJop.size()>0){
					return "true共找到了【" + listJop.size()+ "】个文件.";
				} else {
					return "检查后没有需要处理的文件";
				}
			} else {
				return "检查后没有需要处理的文件";

			}
		} catch (Exception e) {
			return "09-睡眠账户扣收结果数据 文件探测异常";
		}
	}

	/**
	 * @param key 
	 * @param filePath 属性文件路径
	 * @return
	 */
	public static String getValue (String key, String filePath){

		InputStream in = null;
		Properties p = null;
		// 从属性文件里取出的value值
		String strValue = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			p = new Properties();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 加载资源
		try {
			p.load(in);
			strValue = (String) p.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strValue;
	}


}
