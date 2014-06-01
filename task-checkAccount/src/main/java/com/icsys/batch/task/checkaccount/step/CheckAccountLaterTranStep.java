package com.icsys.batch.task.checkaccount.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.checkaccount.proxy.dao.CheckAccountDao;
import com.icsys.batch.task.checkaccount.proxy.dao.TCpsChkerrmsgDao;
import com.icsys.platform.dao.TXHelper;

/**
 * 对账后期处理,主要是copy差错记录以及清理当前流水表到历史表中
 * @author liuyb
 *
 */
public class CheckAccountLaterTranStep implements Step {
	
	private static Logger LOG = Logger.getLogger(CheckAccountLaterTranStep.class);
	
	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		String checkDate = (String)context.getAttribute("check_date");
		String channelId = (String)context.getAttribute("channel_id");
		boolean isCups = (Boolean)context.getAttribute("is_cups");
		TXHelper.beginTX();
		try{
			CheckAccountDao dao = new CheckAccountDao();
			/*入差错表，需要将对账平，写卡状态失败的记录记入差错表*/
			dao.copyUnBalanceRecords(isCups, channelId, checkDate);
			/*转移至历史表*/
			dao.moveRecordsToHistory(isCups, channelId, checkDate);
			/*为报表及调账文件准备数据,只针对银联*/
			if(isCups){
				new TCpsChkerrmsgDao().insertData(channelId, checkDate,context.getJobDate());
			}
			TXHelper.commit();
			LOG.info("所有对账数据处理完成!");
		}catch(Exception e){			
			TXHelper.rollback();
			LOG.error("任务挂起，异常:" + e.getMessage());
			throw new JobException("任务挂起，异常:" + e.getMessage());
		}finally{
			TXHelper.close();
		}
		
	}
	
	/**
	 * 将对账不平记录拷贝到差错表
	 */
//	private void copyUnBalanceRecords() throws Exception{
//		BatchSQLDao batchDao = new BatchSQLDao();
//		batchDao.copyUnBalanceRecordsToError();
//	}
	
	/**
	 * 迁移数据：将当前交易流水表和日志表中的数据迁移到历史表
	 */
//	private void moveRecordsToHistory(String tranDate) throws Exception{
//		BatchSQLDao batchDao = new BatchSQLDao();
//		batchDao.moveTxListRecordsToHistory();
//		batchDao.moveTranLogRecordsToHistory(tranDate);
//	}

}
