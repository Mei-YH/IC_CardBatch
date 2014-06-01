package com.icsys.batch.task.report.stage;

import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.SystemParamValue;

public class ReportsStage implements Stage {
	
	private Logger LOG = Logger.getLogger(ReportsStage.class);

	public void prepare(JobContext context) throws JobException {
		// TODO 自动生成方法存根
		LOG.info("批量报表阶段开始...");
		String sendpath = (String)context.getAttribute("sendpath");
		String listpath = (String)context.getAttribute("listpath");
		if(null == sendpath || "".equals(sendpath.trim())){
			LOG.error("获取系统参数[sendpath]失败!任务挂起");
			throw new JobException("获取系统参数[sendpath]失败!任务挂起");
		}
		if(null == listpath || "".equals(listpath.trim())){
			LOG.error("获取系统参数[listpath]失败!任务挂起");
			throw new JobException("获取系统参数[listpath]失败!任务挂起");
		}
		try {
			String sysdate = SystemParamValue.getSystemDate();
			sysdate = BatchDateUtil.getLastDateWithStringFormat(sysdate);
			context.setAttribute("sysDate", sysdate);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			LOG.error("获取系统参数[systemdate]失败!任务挂起" + e.getMessage());
			throw new JobException("获取系统参数[systemdate]失败!任务挂起");
		}
		LOG.info("批量报表阶段结束.");
	}

}
