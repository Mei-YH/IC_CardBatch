package com.icsys.batch.task.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.business.dayend.impl.StartWork;
import com.icsys.platform.dao.TXHelper;

/**
 * 日终开始(先做日切)
 * @author xinyh
 *
 */
public class StartWorkStep implements Step {

	private static Logger LOG  = Logger.getLogger(StartWorkStep.class);
	
	public long estimate(JobContext context) throws JobException {
		return 0;
	}
	
	public void perform(JobContext context, Executor executor) throws JobException {
		TXHelper.beginTX();
		try{
			StartWork startWork = new StartWork();
			startWork.work(false);
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
