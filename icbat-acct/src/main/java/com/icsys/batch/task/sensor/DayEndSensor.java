package com.icsys.batch.task.sensor;

import java.io.File;

import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Sensor;
import com.icsys.batch.util.BatchConstants;
import com.icsys.batch.util.SystemParamValue;

public class DayEndSensor implements Sensor {

	private static Logger LOG = Logger.getLogger(DayEndSensor.class);
	
	public JobContext[] detect(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		String noticPath = (String)context.getAttribute(BatchConstants.CORE_NOTICE_PATH);	
		try{
			String sysdate = SystemParamValue.getSystemDate();
			File notic = new File(noticPath,sysdate);
			notic = new File(notic,"ISDADCF.FCRECDT.PS");
			if(notic.exists()){
				JobContext job = new JobContext();
				job.setAttribute("lastDate", sysdate);
				LOG.info("发现核心T1结束文件进行日切。");
				return new JobContext[]{job};
			}
			LOG.info("未发现文件ISDADCF.FCRECDT.PS不能进行日切。");
			return new JobContext[]{};
		}catch (Exception e) {
			// TODO: handle exception
			return new JobContext[]{};
		}
	}

}
