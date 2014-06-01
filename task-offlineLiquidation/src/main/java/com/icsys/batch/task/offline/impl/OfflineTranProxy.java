package com.icsys.batch.task.offline.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.offline.ClearDetailChecker;
import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.util.Constants;
import com.icsys.batch.util.Utils;

/**
 * 脱机交易代理类
 * 
 * @author Administrator
 * 
 */
public class OfflineTranProxy implements Runnable{
	private static final Logger LOG = Logger.getLogger(OfflineTranProxy.class);
	private int pageNo = 0;// 当前对象负责处理的页码
//	private String tranCode = Constants.OFFLINE_CONSUMPTION;// 交易码
	private JobContext context;
	private String tranCode;

	public OfflineTranProxy(JobContext context,String tranCode, int pageNo) {
		this.context = context;
		this.pageNo = pageNo;
		
		this.tranCode = tranCode;
	}

	public void run() {
		// TODO Auto-generated method stub
		if(LOG.isDebugEnabled()){
			LOG.debug("当前线程为:"	+ Thread.currentThread().getId());
			LOG.debug("pageNo:" + pageNo);
			LOG.debug("pageLentgth:" + Constants.PAGE_LENGTH);
		}
		try {
			dowork(context.getJobDate().trim(), context.getJobNo().trim(),tranCode,pageNo,
					Constants.PAGE_LENGTH,(String)context.getAttribute("isAccount"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	public void dowork(String clearDate, String batchNo,String tranCode,int pageNo, int pageLength,String isAccount) throws Exception {

		ClearDetailServiceImpl clearBeanService = new ClearDetailServiceImpl();
		// **-----第一步：获得清算明细------------------------------------------------------------------------//
		if(LOG.isInfoEnabled()){
			LOG.info("开始获得清算明细...");
		}
		List<ClearDetailBean> result = clearBeanService.getClearingDetailBean(
				clearDate, batchNo,tranCode, pageNo, pageLength);
		if(LOG.isInfoEnabled()){
			LOG.info("要处理的数据条数为:" + result.size());
		}
		// **-----第二步：校验明细,转账------------------------------------------------------------------------//
		for (ClearDetailBean bean : result) {
			// LOG.error("================明细序号============["+bean.getBatchSN()+"]");
			/*是否应该判断处理成功或挂账的记录，其它状态需要执行 || '3' != bean.getStatus().charAt(3)*/
			if('1' != bean.getStatus().charAt(3)){
//			if ("0000".equals(bean.getStatus())) {
				/*只要转账未成功或挂账失败均重新进行校验，因状态不为初始化时，新状态不能做修改 by20131031 liuyb*/
				bean.setStatus("0000");
				ClearDetailChecker checker = new ClearDetailChecker();
				TranServiceImpl tranServiceImpl = new TranServiceImpl(clearDate);
				checker.setDetail(bean);
				checker.check(clearDate);// 校验
				
				/*TC或ATC校验失败并不记账时进行挂账*/
				if(('2'== bean.getStatus().charAt(1) || '2'== bean.getStatus().charAt(2))&&!"0".equals(isAccount)){
					try{
						String[] cdAccounts = SuspenseAcctImpl.getCDAcctNo(bean, clearDate, isAccount);
						SuspenseAcctImpl.chargeAccount(bean, cdAccounts[1], cdAccounts[0], "TC或ATC校验失败",clearDate);
					}catch(Exception e){
						LOG.error("TC或ATC校验失败时进行挂账处理失败" + e.getMessage());
						bean.setStatus(Utils.union(bean.getStatus(), "0002"));
					}
					new ClearDetailServiceImpl().updateDetail(bean);
					continue;
				}
				tranServiceImpl.transation(bean,isAccount);// 转账
			}else{
				LOG.error("此笔cups[" + bean.getCupsSerial() + "]acctNo[" + bean.getAcctNo() + "]消费或退货已经清算，被忽略");
			}
		}
		// **------end---------------------------------------------------------------------------//
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

//	public String getTranCode() {
//		return tranCode;
//	}
//
//	public void setTranCode(String tranCode) {
//		this.tranCode = tranCode;
//	}

	public JobContext getContext() {
		return context;
	}

	public void setContext(JobContext context) {
		this.context = context;
	}
}