package com.icsys.batch.task.offline.stage;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;

public class OfflineResultStage implements Stage {

	public void prepare(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		String resultFilePath = (String) context.getAttribute("resultFilePath");// 要生成结果文件的路径
		if(null == resultFilePath){
			throw new JobException("生成文件结果路径为空,任务挂起!");
		}

		String listPath = (String)context.getAttribute("listpath");
		if(null == listPath){
			throw new JobException("清单文件路径为:[null],任务挂起");
		}
		String date = (String)context.getAttribute("jobDate");
		if(null == date){
			throw new JobException("该任务不能直接启动，需要进行探测！");
		}
		context.setJobDate(date);
	}

}
