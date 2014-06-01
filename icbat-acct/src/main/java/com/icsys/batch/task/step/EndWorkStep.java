package com.icsys.batch.task.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.business.dayend.impl.EndWork;
import com.icsys.platform.dao.TXHelper;

/**
 * 日终结束
 * @author xinyh
 *
 */
public class EndWorkStep implements Step {

	private static Logger LOG  = Logger.getLogger(EndWorkStep.class);
	public long estimate(JobContext context) throws JobException {
		return 0;
	}
	public void perform(JobContext context, Executor executor) throws JobException {
		try{
			TXHelper.beginTX();
			try{
				EndWork endWork = new EndWork();
				endWork.work(true);
				TXHelper.commit();
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
				TXHelper.rollback();
				throw new JobException(e.getMessage(),e);
			}finally{
				TXHelper.close();
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new JobException(e.getMessage(),e);
		}
	}

}
