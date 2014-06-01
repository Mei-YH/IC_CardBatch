package com.icsys.batch.task.offline.step;



import java.io.File;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.offline.impl.ClearDetailServiceImpl;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.platform.dao.TXHelper;

/**
 * 公共步骤：复制差错记录至差错表
 * 
 * @author liuyb
 * 
 */
public class CopyErrMsgStep implements Step {

	private static final Logger LOG = Logger.getLogger(CopyErrMsgStep.class);

	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {

		String clearDate = context.getJobDate().trim();
//		String tranMethod = (String) context.getAttribute("tranMethod");
		String batchNo = context.getJobNo().trim();
		String dataFileName = (String) context.getAttribute("dataFileName");
		ClearDetailServiceImpl clearDetailService = new ClearDetailServiceImpl();
//		OnlineDetailServiceImpl onlineDetailService = new OnlineDetailServiceImpl();
		TXHelper.beginTX();
		try {
//			if (Constants.TRAN_METHOD_ONLINE.equals(tranMethod)) {
//				onlineDetailService.copyErr(clearDate, batchNo);//.......................
//			} else if (Constants.TRAN_METHOD_OFFLINE.equals(tranMethod)) {
			clearDetailService.deleteClearDetails(clearDate, batchNo);
			clearDetailService.copyErr(clearDate, batchNo);//........................
//			}
			if(LOG.isInfoEnabled()){
				LOG.info("复制差错记录到差错表完成.");
			}
			/*转移至历史表*/
			clearDate = BatchDateUtil.getLastDateWithStringFormat(clearDate);
			clearDetailService.moveToHis(clearDate,batchNo);
			if(LOG.isInfoEnabled()){
				LOG.info("转移数据到历史完成.");
			}
			TXHelper.commit();
			File file = new File(dataFileName);
			new File(file.getParent(),file.getName() + ".ok").delete();
		} catch (Exception e) {
			TXHelper.rollback();
			throw new JobException("后续数据处理异常,任务挂起!" + e.getMessage());
		} finally {
			TXHelper.close();
		}
		
	}
}