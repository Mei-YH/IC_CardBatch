package com.icsys.batch.task.checkaccount.sensor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Sensor;
import com.icsys.batch.task.checkaccount.proxy.CheckAccountConstants;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.SystemParamValue;

public class CheckAccountSensor implements Sensor {

	private static Logger LOG = Logger.getLogger(CheckAccountSensor.class);

	public JobContext[] detect(JobContext context) throws JobException {
		String corePath = (String) context.getAttribute(CheckAccountConstants.FILE_CORE_PATH);
		String localPath = (String) context.getAttribute(CheckAccountConstants.FILE_LOCAL_PATH);
		if (null == corePath || "".equals(corePath)) {
			LOG.debug("核心对账文件存放路径值为NULL");
			return new JobContext[] {};
		}
		if (null == localPath || "".equals(localPath)) {
			LOG.debug("本地对账文件存放路径值为NULL");
			return new JobContext[] {};
		}
//		localPath = localPath.endsWith(File.separator)?localPath:localPath + File.separator;
		List<JobContext> resultList = new ArrayList<JobContext>();
		List<File> checkFileList = new ArrayList<File>();
		try {
			File coreFile = new File(corePath);
			String tranDate = SystemParamValue.getSystemDate();
			File filePath = new File(localPath,tranDate);
			if(!filePath.exists()){
				filePath.mkdir();
			}
			final String lastDate = BatchDateUtil.getLastDateWithStringFormat(tranDate);
			File[] chkFile = coreFile.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
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
			for (File file : checkFileList) {
				FileUtils.copyFile(file, new File(filePath, file.getName()));
				JobContext tempContext = new JobContext();
				tempContext.setAttribute(CheckAccountConstants.FILE_NAME, filePath.getAbsolutePath() + File.separator + file.getName());
				tempContext.setAttribute("jobDate", tranDate);
				resultList.add(tempContext);
				file.delete();
				/*删除chk文件,为防止COPY文件异常，所以没有放在前一个循环验证成功后*/
				new File(file.getParent() + File.separator + file.getName()	+ ".chk").delete();
			}
			if (resultList.size() < 1) {
				LOG.info("没有探测到需要处理的对账文件!");
				return new JobContext[] {};
			}
			if(LOG.isInfoEnabled()){
				LOG.info("共找到了【" + resultList.size()+ "】个文件.");
				LOG.info("对账文件探测结束...");
			}
			return resultList.toArray(new JobContext[resultList.size()]);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new JobContext[] {};
		}
	}
}
