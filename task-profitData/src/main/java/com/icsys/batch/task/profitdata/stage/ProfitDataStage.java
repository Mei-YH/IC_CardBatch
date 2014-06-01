package com.icsys.batch.task.profitdata.stage;

import com.icsys.batch.api.JobContext;

import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.util.SystemParamValue;

public class ProfitDataStage implements Stage {

	public void prepare(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		try{
			String sysDate = SystemParamValue.getSystemDate();
			context.setJobDate(sysDate);
			String path = (String) context.getAttribute("fileName");
			if(null == path){
				throw new Exception("参数值[fileName]不能为NULL！");
			}
			context.setJobNo(SystemParamValue.getBatchNo());
		}catch(Exception ex){
			throw new JobException("批量初始化失败，任务挂起！" + ex.getMessage());
		};
	}
}