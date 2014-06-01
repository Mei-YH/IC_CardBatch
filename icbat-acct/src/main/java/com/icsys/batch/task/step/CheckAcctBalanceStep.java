package com.icsys.batch.task.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.business.dayend.impl.CheckAcctBalance;
import com.icsys.platform.dao.TXHelper;

/**
 * 检查账务平衡（日终步骤5）
 *  
 *
 */
public class CheckAcctBalanceStep implements Step {

	private static Logger LOG  = Logger.getLogger(CheckAcctBalanceStep.class);
	public long estimate(JobContext context) throws JobException {
		return 0;
	}
	public void perform(JobContext context, Executor executor) throws JobException {
		TXHelper.beginTX();
		try {
			CheckAcctBalance checkAcctBalance = new CheckAcctBalance();
			checkAcctBalance.work(false);
			TXHelper.commit();
		}catch (AcctDayEndException e) {
			TXHelper.rollback();
			LOG.error(e.getMessage(),e);
		    throw new JobException(e.getMessage(),e);
		}finally{
			TXHelper.close();
		}
	}

}
