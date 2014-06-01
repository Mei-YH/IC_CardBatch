package com.icsys.batch.business.dayend.impl;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.util.SystemStatus;

public class StartWork implements WorkStep, ErrorDef {
	private Logger log=Logger.getLogger(StartWork.class);
	public void work(boolean bWorkFlag) throws AcctDayEndException {
		AcctSysParameter sysParam = AcctSysUtil.getInstance().getAccSysParam();
		if (null == sysParam) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		//如果上一日扎帐没有完整，
//		Date nextOffDay= DateUtils.getNextDay(DateUtils.strToDate(sysParam.getRollOffDate()));
//		if(DateUtils.parseDate(nextOffDay).equalsIgnoreCase(sysParam.getAcctingDate()))
//		{
//			log.error("上一个扎帐日期  "+sysParam.getRollOffDate()+"没有完成扎帐 不能能进行扎帐");
//			throw new AcctDayEndException(ErrorDef.ACCOUNT_CHECK_ERROR);
//		}
		// 判断日切是否完成，未完成不能开始轧账
		if (SystemStatus.DATE_SWITCHED.getValue() == sysParam.getSystemStatus()) {
			AcctSysUtil.getInstance().updateSystemStatus(
					SystemStatus.CHECK_ACCOUNTS);
			// 设置系统状态为正在轧账
			sysParam.setSystemStatus(SystemStatus.CHECK_ACCOUNTS.getValue());
		} else
			throw new AcctDayEndException(DATE_SWITCH_NOT_COMPLETED);
	}

}
