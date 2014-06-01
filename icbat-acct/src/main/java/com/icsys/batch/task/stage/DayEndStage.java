package com.icsys.batch.task.stage;


import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;

public class DayEndStage implements Stage {

    private static Logger LOG = Logger.getLogger(DayEndStage.class);

	public void prepare(JobContext context) throws JobException {

		String sysdate = (String)context.getAttribute("lastDate");
		if(null == sysdate){
			LOG.error("任务不能直接启动，需要探测");
			throw new JobException("任务不能直接启动，需要探测");
		}
//		 try {
//			 context.setJobNo(getBatchNo());
//			 context.setJobDate(BatchUtil.getTranDate());
//		} catch (Exception e) {
//			LOG.error(e.getMessage(),e);
//			throw new JobException(e.getMessage(),e);
//		}
		LOG.info("日切阶段.");
		
	}


}
