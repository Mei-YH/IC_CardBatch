package com.icsys.batch.task.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.business.dayend.impl.CheckDetailBalance;
import com.icsys.platform.dao.TXHelper;

/**
 * 分录流水检查(日终步骤1)
 * @author liuyb
 *
 */
public class CheckDetailBalanceStep implements Step {

	private static Logger LOG  = Logger.getLogger(CheckDetailBalanceStep.class);
	public long estimate(JobContext context) throws JobException {
		return 0;
	}
	public void perform(JobContext context, Executor executor) throws JobException {
		TXHelper.beginTX();
		try{
			CheckDetailBalance checkBalance = new CheckDetailBalance();
			checkBalance.work(false);
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
