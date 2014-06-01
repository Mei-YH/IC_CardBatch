package com.icsys.batch.task.laterclearing.stage;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.util.SystemParamValue;

/**
 * @author 
 */
public class LaterClearStage implements Stage {

	public void prepare(JobContext context) throws JobException {
		try {
			String date = SystemParamValue.getSystemDate();
			context.setJobDate(date);
//			context.setJobNo(SystemParamValue.getBatchNo());
		} catch (Exception e) {
			throw new JobException("批处理任务初始化失败,任务挂起!");
		}
	}

}
