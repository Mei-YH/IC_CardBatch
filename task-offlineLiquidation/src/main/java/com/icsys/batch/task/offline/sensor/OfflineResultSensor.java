package com.icsys.batch.task.offline.sensor;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Sensor;
import com.icsys.batch.util.SystemParamValue;

public class OfflineResultSensor implements Sensor{

	private static Logger LOG = Logger.getLogger(OfflineResultSensor.class);

	public JobContext[] detect(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		String localPath = (String)context.getAttribute("UnionpayPath");
		localPath = localPath.endsWith(File.separator)?localPath:localPath + File.separator;
		try{
			String date = SystemParamValue.getClearDate();
			File[] list = new File(localPath + date).listFiles(new FileFilter() {
				
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					return pathname.isDirectory();
				}
			});
			if(null == list){
				LOG.error("未执行清算日[" + date + "]批量程序！");
				return new JobContext[]{};
			}
			boolean isOK = false;
			for(File file:list){
				File[] ok = file.listFiles(new FileFilter() {
					
					public boolean accept(File pathname) {
						// TODO Auto-generated method stub
						return pathname.getName().endsWith(".ok");
					}
				});
				if(ok.length > 0){
					isOK = true;
					break;
				}
			}
			if(!isOK){
				LOG.info("脱机清算批处理任务已完成,将进行文件汇总");
				JobContext jobContext = new JobContext();
				jobContext.setAttribute("jobDate", date);
				return new JobContext[]{jobContext};
			}
			LOG.info("脱机清算批处理任务未完成...");
			return new JobContext[]{};
		}catch (Exception e) {
			e.printStackTrace();
			return new JobContext[]{};
		}
	}

}
