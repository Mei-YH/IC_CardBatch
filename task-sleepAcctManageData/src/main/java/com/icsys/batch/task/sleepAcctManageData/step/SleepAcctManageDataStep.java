package com.icsys.batch.task.sleepAcctManageData.step;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.sleepAcctManageData.proxy.bean.SleepAcctManageBean;
import com.icsys.batch.task.sleepAcctManageData.proxy.dao.SleepAcctManageDao;
import com.icsys.batch.task.sleepAcctManageData.proxy.service.MapInit;
import com.icsys.platform.dao.TXHelper;

public class SleepAcctManageDataStep implements Step {

	private Logger LOG = Logger.getLogger(SleepAcctManageDataStep.class);

	public long estimate(JobContext arg0) throws JobException {
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		LOG.info("任务开始...");
		String fileName = (String) context.getAttribute("fileName");
		SleepAcctManageDao dao = new SleepAcctManageDao();
		File file = new File(fileName);
		String icPro = MapInit.getAreaMap().get(file.getName().substring(4,6));//卡属地势
		String coreDate = file.getName().substring(20,28);
		SleepAcctManageBean bean = new SleepAcctManageBean();
		try {
			TXHelper.beginTX();
			List<?> lines = FileUtils.readLines(file,"UTF-8");
			int i = 1;
			for(Object o : lines) {
				String line = o.toString();
				if("".equals(line.trim())){
					continue;
				}
				bean.setBatchNo(new Integer(context.getJobNo().trim()));//批次号
				bean.setBatchSn(new Integer(i));//批次内序号
				bean.setBatchDt(context.getJobDate());//批处理日期
				bean.setIcNo(line.substring(0,19));//卡号
				bean.setIcCardStat(line.substring(19, 21));//卡状态
				bean.setIcSleepTime(line.substring(21,29));//销卡时间
				bean.setIcAcctBalance(new BigDecimal(line.substring(29,44)).movePointLeft(2).toString());//余额
				bean.setIcRemoveBalance(new BigDecimal(line.substring(44,59)).movePointLeft(2).toString());//扣收金额
				bean.setCoreRemoveStat(line.substring(59,64));//核心扣收状态
				bean.setIcDate(context.getJobDate());//ic卡扣收时间
				bean.setCoreDate(coreDate);//核心扣收时间
				bean.setIcPro(icPro);//卡属地势
				bean.setIcBranch(line.substring(64).trim());
				dao.insertData(bean);
				i++;
				if(i%500==0){
					TXHelper.commit();
					TXHelper.beginTX();
				}
			}
			TXHelper.commit();
		} catch (Exception e) {
			TXHelper.rollback();
			LOG.error(e.getMessage());
			throw new JobException("处理异常，任务挂起！" + e.getMessage());
		} finally{
			TXHelper.close();
		}
		LOG.info("任务执行结束!");
	}

}
