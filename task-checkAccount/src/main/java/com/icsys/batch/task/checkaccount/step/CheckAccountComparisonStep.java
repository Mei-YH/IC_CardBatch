package com.icsys.batch.task.checkaccount.step;

import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.checkaccount.proxy.CheckAccountProxy;
import com.icsys.batch.task.checkaccount.proxy.bean.GrCheckAcctDet;
import com.icsys.batch.task.checkaccount.proxy.dao.CheckAccountDao;
import com.icsys.platform.dao.TXHelper;

/**
 * 数据勾对
 * 
 * @author liuyb
 * 
 */
public class CheckAccountComparisonStep implements Step {
	
	private static Logger LOG = Logger.getLogger(CheckAccountComparisonStep.class);
	
	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)throws JobException {
		String checkDate =(String)context.getAttribute("check_date");
		String channelId = (String)context.getAttribute("channel_id");
		boolean isCups = (Boolean)context.getAttribute("is_cups");
		CheckAccountDao dao = new CheckAccountDao();

		LOG.info("对账数据处理开始...");
		try {			
			List<GrCheckAcctDet> detailList = dao.queryDetail(channelId, checkDate);
			LOG.debug("循环处理开始...");
			for(GrCheckAcctDet detail : detailList){
				CheckAccountProxy proxy = new CheckAccountProxy();
				proxy.dowork(detail,isCups);
			}
			LOG.info("与核心数据勾对完成...");
		} catch (Exception e) {
			LOG.error("任务挂起，异常:" + e.getMessage());
			throw new JobException("任务挂起，异常:" + e.getMessage());
		}
		TXHelper.beginTX();
		try{
			// 反向遍历数据库更新平台多核心少的记录
			dao.updateExtraRecordsOnPlatForm(isCups, checkDate, channelId);
			// 如果IC卡平台流水对账状态为00－未对账,并且交易状态为:非成功,则更新其对账状态为11-平账
			dao.updateOnPlatFormCloseOut(isCups, channelId, checkDate);
			TXHelper.commit();
			LOG.info("对账数据处理完成...");
		}catch(Exception e){
			TXHelper.rollback();
			LOG.error("任务挂起，异常:" + e.getMessage());
			throw new JobException("任务挂起，异常:" + e.getMessage());
		}finally{
			TXHelper.close();
		}
	}	
}
