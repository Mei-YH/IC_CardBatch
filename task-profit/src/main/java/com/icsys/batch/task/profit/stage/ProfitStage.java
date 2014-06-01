package com.icsys.batch.task.profit.stage;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.util.SystemParamValue;

public class ProfitStage implements Stage {

	public void prepare(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		try {
			String path = (String) context.getAttribute("resultFilePath");
			if (path == null){
				throw new Exception("参数值[resultFilePath]不能为NULL！");
			}
			String list = (String) context.getAttribute("listPath");
			if (list == null){
				throw new Exception("参数值[listPath]不能为NULL！");
			}
			String sysDate = SystemParamValue.getSystemDate();
			context.setJobDate(sysDate);
		} catch (Exception ex) {
			throw new JobException("批量初始化失败，任务挂起！" + ex.getMessage());
		}

	}

}
