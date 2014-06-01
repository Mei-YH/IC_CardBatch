package com.icsys.batch.business.dayend.impl;

import java.util.Date;

import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.acctsys.Step;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.util.SystemStatus;
import com.icsys.platform.util.DateUtils;

public class EndWork implements WorkStep {

	public void work(boolean bWorkFlag) throws AcctDayEndException {
		// 设置轧账日期为下一天
		AcctSysParameter sysp = AcctSysUtil.getInstance().getAccSysParam();
		if (null == sysp) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		Date date = DateUtils.strToDate(sysp.getRollOffDate());
		date = DateUtils.getNextDay(date);
		AcctSysUtil.getInstance().updateSysDate(DateUtils.parseDate(date),
				false);
		// 设置系统状态为正常
		AcctSysUtil.getInstance().updateSystemStatus(SystemStatus.NORMAL);
		// 设置步骤为轧账未开始
		AcctSysUtil.getInstance().updateStep(Step.NO_CHECK_ACCTOUNTS);
	}

}
