package com.icsys.batch.task.sign.stage;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
//import com.icsys.batch.util.SystemParamValue;

public class IcToEsbSignStage implements Stage {

	public void prepare(JobContext context) throws JobException {
		String coreIp = (String)context.getAttribute("core_ip");
		String channel = (String)context.getAttribute("channel");
		String queueName = (String)context.getAttribute("queue_name");
		String port = (String)context.getAttribute("port");
		String ccsidStr = (String)context.getAttribute("ccsid");
		String requestName = (String)context.getAttribute("request_name");
		String responseName = (String)context.getAttribute("response_name");
		String brhId = (String)context.getAttribute("brh_id");
		if(null == coreIp || "".equals(coreIp.trim())){
			throw new JobException("参数[core_ip]不能为空");
		}
		if(null == channel || "".equals(channel.trim())){
			throw new JobException("参数[channel]不能为空");
		}
		if(null == queueName || "".equals(queueName.trim())){
			throw new JobException("参数[queue_name]不能为空");
		}
		if(null == port || "".equals(port.trim())){
			throw new JobException("参数[port]不能为空");
		}
		if(null == ccsidStr || "".equals(ccsidStr.trim())){
			throw new JobException("参数[ccsid]不能为空");
		}
		if(null == requestName || "".equals(requestName.trim())){
			throw new JobException("参数[request_name]不能为空");
		}
		if(null == responseName || "".equals(responseName.trim())){
			throw new JobException("参数[response_name]不能为空");
		}
		if(null == brhId || "".equals(brhId.trim())){
			throw new JobException("参数[brh_id]不能为空");
		}
//		try{
//			String sysDate = SystemParamValue.getSystemDate();
//			context.setJobDate(sysDate);
//		}catch(Exception ex){
//			throw new JobException("异常，任务挂起" + ex.getMessage());
//		}
	}

}
