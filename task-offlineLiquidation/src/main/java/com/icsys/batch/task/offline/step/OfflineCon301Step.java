package com.icsys.batch.task.offline.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.offline.impl.ClearDetailServiceImpl;
import com.icsys.batch.task.offline.impl.OfflineTranProxy;
import com.icsys.batch.util.Constants;

/**
 * 脱机交易任务：处理脱机消费交易
 * 
 * @author liuyb
 * 
 */
public class OfflineCon301Step implements Step {
	private static Logger LOG = Logger.getLogger(OfflineCon301Step.class);

	/**
	 * 查询要处理的数据的总记录数（总页数）
	 * 
	 * @param clearDate
	 *            清算日期
	 * @param batchNo
	 *            批次号
	 * @param tranCode
	 *            交易码
	 * @return 总页数
	 */
	public long estimate(JobContext context) throws JobException {
		
		// 先处理消费
		ClearDetailServiceImpl clearBeanService = new ClearDetailServiceImpl();
		int count = clearBeanService.getTotalCount(context.getJobDate().trim(), context.getJobNo().trim(), "301");
		
		int leftCount = count % Constants.PAGE_LENGTH;// 余数
		int pageCount = count / Constants.PAGE_LENGTH;// 最小页数
		if (leftCount > 0) {
			pageCount = pageCount + 1;
		}
		if(LOG.isInfoEnabled()){
			LOG.info("总条数为:" + count);
			LOG.info("每页的条数为:" + Constants.PAGE_LENGTH);
			LOG.info("总页数为:" + pageCount );
		}
		return pageCount;// 返回总页数，根据页数分配任务
	}

	public void perform(JobContext context, Executor executor)	throws JobException {
		try {			    
			int start =(int) context.lbound;// 该节点（机器）分配的起始页
			int end = (int) context.ubound;// 该节点（机器）分配的终止页
			if(start < 1){
				throw new JobException("未设置并发处理");
			}
			if(LOG.isInfoEnabled()){
				LOG.info("进入脱机退货处理步骤.");
				LOG.info("Page start: " + start + "; Page end: " + end+",PAGE_LENGTH :"+Constants.PAGE_LENGTH);
			}
			for (int pageNo = start; pageNo <= end; pageNo++) {
				// 每个线程负责一页
				OfflineTranProxy proxy = new OfflineTranProxy(context,"301",pageNo);
				executor.execute(proxy);
			}
//			LOG.info("脱机消费处理成功...");
		} catch (Exception e) {
			throw new JobException("脱机消费退货异常,任务挂起,原因:" + e.getMessage());
		}		
	}

}