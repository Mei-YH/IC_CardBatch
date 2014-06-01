package com.icsys.batch.task.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.business.dayend.impl.DailyClosing;
import com.icsys.platform.dao.TXHelper;

/**
 * 科目日结（日终步骤2）
 * @author xinyh
 *
 */
public class DailyClosingStep implements Step {

	private static Logger LOG  = Logger.getLogger(DailyClosingStep.class);
	public long estimate(JobContext context) throws JobException {
		return 0;
	}
	public void perform(JobContext context, Executor executor) throws JobException {
		TXHelper.beginTX();
		try{
			DailyClosing dailyClosing = new DailyClosing();
			dailyClosing.work(false);
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
