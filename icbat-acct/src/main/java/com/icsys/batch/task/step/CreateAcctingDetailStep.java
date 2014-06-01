package com.icsys.batch.task.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.business.dayend.impl.CreateAcctingDetail;
import com.icsys.platform.dao.TXHelper;

/**
 * 生成账户明细（账户时序平衡检查）（日终步骤4）
 * @author 
 *
 */
public class CreateAcctingDetailStep implements Step {

	private static Logger LOG  = Logger.getLogger(CreateAcctingDetailStep.class);
	public long estimate(JobContext context) throws JobException {
		return 0;
	}
	public void perform(JobContext context, Executor executor) throws JobException {
		// TODO Auto-generated method stub
		TXHelper.beginTX();
		try{
			CreateAcctingDetail createAcctingDetail = new CreateAcctingDetail();
			createAcctingDetail.work(false);
			TXHelper.commit();
		}catch(Exception e){
			TXHelper.rollback();
			LOG.error(e.getMessage(),e);
		    throw new JobException(e.getMessage(),e);
		}finally{
			TXHelper.close();
		}
	}


}
