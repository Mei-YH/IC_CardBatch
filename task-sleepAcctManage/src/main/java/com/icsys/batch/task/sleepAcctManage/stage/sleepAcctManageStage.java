package com.icsys.batch.task.sleepAcctManage.stage;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.util.SystemParamValue;

public class sleepAcctManageStage implements Stage {

	public void prepare(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		try{
			String limit = SystemParamValue.getIcSysPara("100001");//账户余额最低额度
			String cycle = SystemParamValue.getIcSysPara("100002");//无业务间隔时间
			String remAmount = SystemParamValue.getIcSysPara("100003");//扣收金额
			if(null == limit){
				throw new Exception("参数值[100001]不能为NULL！");
			}
			if(cycle==null){
				throw new Exception("参数值[100002]不能为NULL！");
			}
			if(remAmount==null){
				throw new Exception("参数值[100003]不能为NULL！");
			}
			context.setAttribute("cycle", cycle);
			context.setAttribute("minLimit",limit);
			context.setAttribute("remAmount",remAmount);
			String path = (String) context.getAttribute("resultFilePath");// 要生成结果文件的路径
			if(path==null){
				throw new Exception("参数值[resultFilePath]不能为NULL！");
			}
			String listPath = (String)context.getAttribute("listPath");
			if(listPath==null){
				throw new Exception("参数值[listPath]不能为NULL！");
			}
			String sysDate = SystemParamValue.getSystemDate();
			context.setJobDate(sysDate);
		}catch(Exception ex){
			throw new JobException("批量初始化失败，任务挂起！" + ex.getMessage());
		}
	}

}
