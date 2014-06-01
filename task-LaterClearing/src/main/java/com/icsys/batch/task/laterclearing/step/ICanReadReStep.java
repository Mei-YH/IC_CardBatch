package com.icsys.batch.task.laterclearing.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.laterclearing.proxy.ICanReadReProxy;

/**
 * @author 该类主要处理不能读卡收回换卡的入口,主要处理代码放到excute方法中
 */
public class ICanReadReStep implements Step {
	private static Logger LOG = Logger.getLogger(ICanReadReStep.class);

	public long estimate(JobContext context) throws JobException {
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		try {
			ICanReadReProxy proxy = new ICanReadReProxy();
			proxy.dowork(context.getJobDate().trim());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new JobException("周期后处理出现异常,任务挂起!");
		}
	}
}
