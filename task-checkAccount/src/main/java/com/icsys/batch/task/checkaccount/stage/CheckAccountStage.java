package com.icsys.batch.task.checkaccount.stage;

import java.io.File;

import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.task.checkaccount.proxy.CheckAccountConstants;
import com.icsys.batch.util.SystemParamValue;

public class CheckAccountStage implements Stage {

	private static Logger LOG = Logger.getLogger(CheckAccountStage.class);
	
	public void prepare(JobContext context) throws JobException {
		try{
			LOG.info("进入对账处理阶段...");
			String fileName = (String) context.getAttribute(CheckAccountConstants.FILE_NAME);// 对账文件

			if(null == fileName){
				throw new JobException("对账文件名为:[null],任务挂起");
			}
			String listPath1 = (String) context.getAttribute("odslist");
			if(null == listPath1){
				throw new JobException("清单文件[odslist]路径为:[null],任务挂起");
			}
			String listPath2 = (String) context.getAttribute("cupslist");
			if(null == listPath2){
				throw new JobException("清单文件[cupslist]路径为:[null],任务挂起");
			}

			String file1 = (String) context.getAttribute(CheckAccountConstants.FILE_RESULT_CUPS_PATH);
			if(null == file1){
				throw new JobException("对账结果文件路径[" + CheckAccountConstants.FILE_RESULT_CUPS_PATH + "]路径为:[null],任务挂起");
			}
			String file2 = (String) context.getAttribute(CheckAccountConstants.FILE_RESULT_LOCAL_PATH);
			if(null == file2){
				throw new JobException("对账结果文件路径[" + CheckAccountConstants.FILE_RESULT_LOCAL_PATH + "]路径为:[null],任务挂起");
			}
			String name = new File(fileName).getName();
			/*截取渠道编号及对账日期*/
			String[] split = name.split("\\.");
			String channelId = split[2].substring(5);
			boolean isCups = CheckAccountConstants.CUPS_CHANNEL_ID.equals(channelId);
			context.setAttribute("is_cups", isCups);	
//			context.setJobNo(SystemParamValue.getBatchNo());
			String checkDate = isCups?split[3].trim().substring(split[3].trim().length()-4):split[3].trim();
			context.setAttribute("channel_id", channelId);
			context.setAttribute("check_date", checkDate);
			String jobDate = (String)context.getAttribute("jobDate");
			if(null == jobDate){
				jobDate = SystemParamValue.getSystemDate();
			}
			context.setJobDate(jobDate);
			LOG.info("channelId:" + channelId + ",checkDate:" + checkDate);
			LOG.info("对账处理阶段完成.");
		}catch(Exception e){
			LOG.error("任务挂起，异常:" + e.getMessage());
			throw new JobException(e.getMessage(),e);
		}
	}
}
