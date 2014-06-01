package com.icsys.batch.business.dayend.impl;

import java.util.Date;
import java.util.EnumSet;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.acctsys.Step;
import com.icsys.batch.business.acctsys.StepAction;
import com.icsys.batch.business.dayend.api.AccountDayEnd;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.util.SystemStatus;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.exceptions.SystemException;
import com.icsys.platform.util.DateUtils;

/**
 * 日终轧账实现
 * 
 * @author kittyuu
 * 
 */
public class AccountDayEndImpl implements AccountDayEnd {

	private static Logger LOG = Logger.getLogger(AccountDayEndImpl.class);

	/**
	 * 日切检查(日切第一步) 日期检查
	 * 
	 * @param acctingDate记账日期
	 */
	public void acctDateSwitchCheck(String acctingDate)
			throws AcctDayEndException {

		// 日期检查
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		String localDate = accSysParam.getAcctingDate();
		if (!localDate.equalsIgnoreCase(acctingDate)) {
			AcctDayEndException adee = new AcctDayEndException(
					ErrorDef.DATE_SWITCH_DATE_ERROR);
			adee.setErrMsgArgs(new String[] { acctingDate, localDate });
			throw adee;// 日期不一致，不能再做日切
		}
		// 更改系统状态
		AcctSysUtil.getInstance().updateSystemStatus(
				SystemStatus.DATE_SWITCHING);
	}

	/**
	 * 日切数据清理(日切第二步) 
	 * 清理动户快照表和科目日结表
	 * 
	 * @throws AcctDayEndException
	 */
	public void acctDateSwitchCleanData() throws AcctDayEndException {
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		if (accSysParam.getSystemStatus() != SystemStatus.DATE_SWITCHING
				.getValue()) {
			throw new AcctDayEndException(ErrorDef.DATE_SWITCH_NOT_COMPLETED);
		}
		try {
			cleanData(AcctSysUtil.getInstance().getAccListTable(false));
		} catch (Exception e) {
			LOG.error("日切，系统错", e);
			throw new AcctDayEndException(ErrorDef.DATE_SWITCH_NOT_COMPLETED);
		}
	}

	/**
	 * 日切(第三步)
	 *1、 生成动户快照
	 *2、切换AB表
	 *3、系统日切
	 * @param acctingDate记账日期
	 * @throws AcctDayEndException
	 */
	public void acctDateSwitch(String acctingDate) throws AcctDayEndException {
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		if (accSysParam.getSystemStatus() != SystemStatus.DATE_SWITCHING
				.getValue()) {
			throw new AcctDayEndException(ErrorDef.DATE_SWITCH_NOT_COMPLETED);
		}
		TXHelper.beginTX();
		try {
			// 生成动户快照
//			buildSnapShotForModifiedAcct(acctingDate);
//			if (LOG.isDebugEnabled()) {
//				LOG.debug("生成动户快照。");
//			}
			// 切换分录流水AB表
			AcctSysUtil.getInstance().switchCurrAccList();
			if (LOG.isDebugEnabled()) {
				LOG.debug("分录流水表切换为"
						+ AcctSysUtil.getInstance().getAccListTable(true)
						+ "。");
			}
			// 日切日切切换到下一天
			Date aDate = DateUtils.strToDate(acctingDate);
			Date nextDate = DateUtils.getNextDay(aDate);
			AcctSysUtil.getInstance().updateSysDate(DateUtils.parseDate(nextDate), true);
			/*changed liuyb 20130909*/
			/*因山东农信没有日终扎账，所以日切时将扎账日期一同进行切换*/
			AcctSysUtil.getInstance().updateSysDate(DateUtils.parseDate(nextDate), false);
			// 更改系统状态
			AcctSysUtil.getInstance().updateSystemStatus(SystemStatus.DATE_SWITCHED);

			TXHelper.commit();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("日切，系统错", e);
			try {
				TXHelper.rollback();
			} catch (Exception e1) {
				LOG.error(e1.getMessage(),e1);
			}
			throw new AcctDayEndException(ErrorDef.DATE_SWITCH_NOT_COMPLETED);
		} finally {
			TXHelper.close();
		}
	}

	/**
	 * 轧账
	 * 
	 * @throws AcctDayEndException
	 */
	public void checkAccounts() throws AcctDayEndException {
		AcctSysParameter sysp = AcctSysUtil.getInstance().getAccSysParam();
		if (null == sysp) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		for (Step step : EnumSet.allOf(Step.class)) {
			int currStep = sysp.getStep();
			int stepState = sysp.getStepState();
			if (step.ordinal() < currStep)
				continue;
			else if (step.ordinal() == currStep) {
				if (stepState == 0)
					continue;
			}
			boolean bWorkFlag = false;
			int pos = step.getPosition();
			if (pos > 0) {
				// 判断步骤动作是否跳过
				int c =  sysp.getStepAction().charAt(pos - 1)-'0';
				if (c == StepAction.PASS.getValue())
					continue;
				// 检查是否自动挂账
				if (c == StepAction.RUNUP.getValue())
					bWorkFlag = true;
			}
			// 设置当前执行步骤及缺省状态（失败）
			AcctSysUtil.getInstance().updateStep(step);
			sysp.setStep(step.ordinal());
			sysp.setStepState(1);
			// 启动事务
			TXHelper.beginTX();
			try {
				// 执行步骤动作
				step.getWorkStep().work(bWorkFlag);
				// 设置步骤状态为成功
				AcctSysUtil.getInstance().updateStepState(true);
				sysp.setStepState(0);
				TXHelper.commit();
			} catch (AcctDayEndException e) {
				try {
					TXHelper.rollback();
				} catch (Exception e1) {
					LOG.error(e1.getMessage(),e1);
				}
				throw e;
			} catch (Throwable e) {
				LOG.error("Excute step:" + step.name() + " erro.", e);
				try {
					TXHelper.rollback();
				} catch (Exception e1) {
					LOG.error(e1.getMessage(),e1);
				}
				throw new SystemException(e);
			} finally {
				TXHelper.close();
			}

		}
	}

//	private void buildSnapShotForModifiedAcct(String clearDate) {
//		// 生成动户快照表
//		AcctDayEndDAO.buildSnapShot(clearDate);
//	}

	/**
	 * 数据清理
	 * 清理动户余额快照表
	 * 清理非当前分录流水表
	 */
	private void cleanData(String noCurAccListTable) {
		// 清理动户余额快照表
//		AcctDayEndDAO.truncate("gr_acc_chgbal");
		// 清理科目日结表
//		AcctDayEndDAO.truncate("gr_acc_subdate");
		// 清理非当前分录流水表（现在放在数据清理步骤实现）
		//不采用数据清理步骤
//		AcctDayEndDAO.moveNoCurrentAcctListToHistory(noCurAccListTable);
//		AcctDayEndDAO.truncate(noCurAccListTable);
	}

}
