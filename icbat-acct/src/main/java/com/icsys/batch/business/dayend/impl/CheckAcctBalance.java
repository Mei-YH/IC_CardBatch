package com.icsys.batch.business.dayend.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.business.dayend.dao.AcctBalanceRecord;
import com.icsys.batch.business.dayend.dao.AcctDayEndDAO;
import com.icsys.batch.business.dayend.dao.GeneralLedgerDay;
import com.icsys.batch.business.dayend.dao.SeqBalanceRecord;
import com.icsys.platform.dao.TXHelper;

/**
 * 账务平衡检查
 * 
 * @author kittyuu
 * 
 */
public class CheckAcctBalance implements WorkStep, ErrorDef {

	private static Logger LOG = Logger.getLogger(CheckAcctBalance.class);
	public void work(boolean bWorkFlag) throws AcctDayEndException {
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		// 获取轧账日期
		String rollOffDate = accSysParam.getRollOffDate();
		try {
			// 总账平衡检查
			checkGeneralLedger(rollOffDate);
			// 总分平衡检查
			checkGeneralLedgerAndAcct(rollOffDate);
		} catch (AcctDayEndException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new AcctDayEndException(CHECK_ACCOUNT_BALANCE_ERROR);
		}

	}

	private void checkGeneralLedger(String rollOffDate)
			throws AcctDayEndException {
		try {
			List<AcctBalanceRecord> unBalanced = AcctDayEndDAO
					.getUnBalancedGeneralLedger();
			TXHelper.beginNewTX();
			try{
				AcctDayEndDAO.deleteAccountBalance(rollOffDate);
				// 将检查结果记录入库
				for (AcctBalanceRecord abr : unBalanced) {
					abr.setSubjectCode("NULL");
					abr.setRollOffDate(rollOffDate);
					AcctDayEndDAO.addAccountBalance(abr);
				}
				TXHelper.commit();
			}catch(Exception e){
				try {
					TXHelper.rollback();
				} catch (Exception e1) {
					LOG.error(e1.getMessage(),e1);
				}
				LOG.error(e.getMessage(),e);
				throw e;
			}finally{
				TXHelper.close();
			}
			if(unBalanced.size() > 0){
				LOG.error("总账平衡检查异常!");
				throw new Exception("总账平衡检查异常!");
			}
			
		} catch (Exception e) {
			throw new AcctDayEndException(GET_UNBALANCED_GENERAL_ERROR);
		}

	}

	private void checkGeneralLedgerAndAcct(String rollOffDate)
			throws AcctDayEndException {
		try {
			// 从账务主档中统计分户账余额合计
			List<AcctBalanceRecord> debitContextList = AcctDayEndDAO
					.getAcctDebitAmount();
			// 检查账务主档和总账是否平衡
			for (AcctBalanceRecord debitContext : debitContextList) {
				checkGeneralLedgerAndAcct(debitContext, rollOffDate);
			}
		} catch (AcctDayEndException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new AcctDayEndException(CHECK_UNBALANCED_ACCOUNT_ERROR);
		}

	}

	private void checkGeneralLedgerAndAcct(AcctBalanceRecord debitContext,
			String rollOffDate)
			throws Exception {
		// 查询总账
		GeneralLedgerDay gld = AcctDayEndDAO.getGeneralLedger(debitContext
				.getBranchCode(), debitContext.getCurrType(), debitContext
				.getSubjectCode());
		if (null == gld) {
			AcctDayEndException adee = new AcctDayEndException(ACCT_GENGL_NOT_FOUND);
			adee.setErrMsgArgs(new String[] { debitContext.getBranchCode(),
							debitContext.getCurrType(),
							debitContext.getSubjectCode() });
			throw adee;
		}
		BigDecimal gbalance = gld.getBalance();
		if (!AcctSysUtil.getInstance().isDebit(gld.getBalanceCD())) {
			gbalance = gbalance.negate();
		}
		if (gbalance.compareTo(debitContext.getCalBalance()) != 0) {
			LOG.error("总分平衡检查异常!");
			TXHelper.beginNewTX();
			try{
				AcctDayEndDAO.deleteAccountBalance(rollOffDate);
				debitContext.setBalance(gbalance);
				debitContext.setRollOffDate(rollOffDate);
				// 将检查结果记录入库
				AcctDayEndDAO.addAccountBalance(debitContext);
				
				TXHelper.commit();
			}catch(Exception e){
				try {
					TXHelper.rollback();
				} catch (Exception e1) {
					LOG.error(e1.getMessage(),e1);
				}
				LOG.error(e.getMessage(),e);
				throw e;
			}finally{
				TXHelper.close();
			}
			throw new Exception("总分平衡检查异常!");
		}
	}

}
