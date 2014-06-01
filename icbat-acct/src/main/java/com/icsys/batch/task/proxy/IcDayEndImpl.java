package com.icsys.batch.task.proxy;

import java.util.Date;

import com.icsys.batch.business.dayend.api.AccountDayEnd;
import com.icsys.batch.business.dayend.impl.AccountDayEndImpl;
import com.icsys.batch.daySwitch.impl.ParamManager;
import com.icsys.batch.daySwitch.impl.ParamManagerImpl;
import com.icsys.platform.util.DateUtils;
import com.icsys.platform.util.SysParamConstant;

/**
 * IC卡平台日终实现
 * @author 
 *
 */
public class IcDayEndImpl{

	public void icDateSwitch() throws Exception {
		String acctingDate = null;
		String sysDate = null;
		ParamManager paraManager = new ParamManagerImpl();
		// 获取系统日期
		acctingDate = paraManager.getParameter(
				SysParamConstant.CLEARING_DATE).getParameterValue();
		sysDate = paraManager.getParameter(SysParamConstant.SYSTEM_DATE)
				.getParameterValue();
		
		// 调用账务核心的日切
		AccountDayEnd acctDayEnd = new AccountDayEndImpl();
		acctDayEnd.acctDateSwitchCheck(acctingDate);
		acctDayEnd.acctDateSwitchCleanData();
		acctDayEnd.acctDateSwitch(acctingDate);

		// 切换系统日期
		paraManager.setParameterValue(SysParamConstant.CLEARING_DATE,
				getNextDay(acctingDate));
		paraManager.setParameterValue(SysParamConstant.SYSTEM_DATE,
				getNextDay(sysDate));
		
		// 切换交易流水表
		switchCurrTXList(paraManager);
	}

	/**
	 * 获取下一天
	 * @param date
	 * @return
	 */
	private String getNextDay(String date) {
		Date aDate = DateUtils.strToDate(date);
		Date nextDate = DateUtils.getNextDay(aDate);
		return DateUtils.parseDate(nextDate);
	}

	/**
	 * 切换交易流水表
	 * @param paraManager
	 * @throws SysParamException
	 */
	private void switchCurrTXList(ParamManager paraManager)
			throws Exception {
		String newCurrTXList = "0";
		if (paraManager.getParameter(SysParamConstant.CURR_SERIAL_TABLE)
				.getParameterValue().trim().equals("0")) {
			newCurrTXList = "1";
		}
		paraManager.setParameterValue(SysParamConstant.CURR_SERIAL_TABLE,newCurrTXList);

	}
}
