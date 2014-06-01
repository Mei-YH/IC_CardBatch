package com.icsys.batch.task.checkaccount.stage;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.util.SystemParamValue;

public class RequestCoreStage implements Stage {
	
	public void prepare(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		String coreIp = (String)context.getAttribute("core_ip");
		String channel = (String)context.getAttribute("channel");
		String queueName = (String)context.getAttribute("queue_name");
		String port = (String)context.getAttribute("port");
		String ccsidStr = (String)context.getAttribute("ccsid");
		String requestName = (String)context.getAttribute("request_name");
		String responseName = (String)context.getAttribute("response_name");
		if(null == coreIp || "".equals(coreIp)){
			throw new JobException("参数core_ip不能为NULL");
		}
		if(null == channel || "".equals(channel)){
			throw new JobException("参数channel不能为NULL");
		}
		if(null == queueName || "".equals(queueName)){
			throw new JobException("参数queue_name不能为NULL");
		}
		if(null == port || "".equals(port)){
			throw new JobException("参数port不能为NULL");
		}
		if(null == ccsidStr || "".equals(ccsidStr)){
			throw new JobException("参数ccsid不能为NULL");
		}
		if(null == requestName || "".equals(requestName)){
			throw new JobException("参数request_name不能为NULL");
		}
		if(null == responseName || "".equals(responseName)){
			throw new JobException("参数response_name不能为NULL");
		}

		try {
			String tranDate = SystemParamValue.getSystemDate();
			context.setJobDate(tranDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new JobException("获取系统日期异常！");
		}
	}
}
