package com.icsys.batch.task.checkaccount.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.checkaccount.proxy.dao.CheckAccountDao;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.platform.dao.TXHelper;

/**
 * 对账数据转移准备
 * @author liuyb
 *
 */
public class CheckAccountDataTransferStep implements Step {
	
	private static Logger LOG = Logger.getLogger(CheckAccountDataTransferStep.class);

	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		// TODO Auto-generated method stub
		String checkDate =(String)context.getAttribute("check_date");
		String channelId = (String)context.getAttribute("channel_id");
		boolean isCups = (Boolean)context.getAttribute("is_cups");
		CheckAccountDao mvdao = new CheckAccountDao();
		TXHelper.beginTX();
		try {
			/*
			 * 删除当前渠道对账日期前一天的对账表数据
			 * 转移对账日期流水到对账表准备对账
			 */
			String lastDate = BatchDateUtil.getLastDateWithStringFormat(isCups?context.getJobDate().substring(0,8-checkDate.length()) + checkDate:checkDate);
			/*保留180天*/
//			String lastDate = DateUtil.getDateStri(DateUtil.getAfterNDay(DateUtil.getDateFromShortStr((isCups?context.getJobDate().substring(0,8-checkDate.length()) + checkDate:checkDate)),-180));
			lastDate = isCups?lastDate.substring(8-checkDate.length()):lastDate;
			mvdao.insertAndDelCheckAccList(isCups, channelId, lastDate,checkDate);
			TXHelper.commit();
			LOG.info("转移对账数据成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TXHelper.rollback();
			LOG.error("转移对账数据失败，任务挂起！原因：" + e.getMessage());
			throw new JobException("转移对账数据失败，任务挂起！原因：" + e.getMessage());
		}finally{
			TXHelper.close();
		}
	}
}
