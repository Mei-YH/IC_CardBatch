package com.icsys.batch.business.dayend.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acct.api.CDFlag;
import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.acctting.api.AcctingException;
import com.icsys.batch.business.acctting.impl.AccounterImpl;
import com.icsys.batch.business.acctting.impl.RunUpAcctInput;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.business.dayend.dao.AcctDayEndDAO;
import com.icsys.batch.business.dayend.dao.BranchBalanceRecord;
import com.icsys.batch.business.dayend.dao.DetailBalanceRecord;
import com.icsys.platform.dao.TXHelper;

/**
 * 分录平衡检查
 * 
 * @author kittyuu
 * 
 */
public class CheckDetailBalance implements WorkStep, ErrorDef {

	private static Logger LOG = Logger.getLogger(CheckDetailBalance.class);

	public void work(boolean bWorkFlag) throws AcctDayEndException {
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		// 获取轧账日期
		String rollOffDate = accSysParam.getRollOffDate();
		// 获取非当前分录流水表
		String noCurAccListTable = AcctSysUtil.getInstance().getAccListTable(
				false);
		List<BranchBalanceRecord> unBalancedBranchs = null;
		TXHelper.beginNewTX();
		try {
			// 按机构检查分录借贷平衡
			unBalancedBranchs = AcctDayEndDAO
					.getUnBalancedBranch(noCurAccListTable);
			AcctDayEndDAO.deleteDetailBalance(rollOffDate);
			for (BranchBalanceRecord bbr : unBalancedBranchs) {
				if(LOG.isDebugEnabled()){
					LOG.debug("TRAN_BRANCH IS:["+bbr.getAcctingBranch()+"]");
				}
				List<DetailBalanceRecord> unBalanced = AcctDayEndDAO
						.getUnBalancedSerial(bbr.getAcctingBranch(),
						noCurAccListTable);
				// 将分录不平结果记录入库
				for (DetailBalanceRecord dbr : unBalanced) {
					dbr.setRollOffDate(rollOffDate);
					AcctDayEndDAO.addDetailBalance(dbr);
				}
				// 根据设置自动挂账
				if (bWorkFlag) {
					// 准备挂账输入信息
					RunUpAcctInput raInput = new RunUpAcctInput();
					raInput.setTranBranch(bbr.getAcctingBranch());
					BigDecimal amt = bbr.getDiffBalance();
					raInput.setAmount(amt);
					if (amt.compareTo(BigDecimal.ZERO) == 1) // 借方多
//						raInput.setAcctingCD(CDFlag.CREDIT); // 挂贷方账
						//modify by chenyuchang 20120202   修改贷方多挂借方账
						raInput.setAcctingCD(CDFlag.DEBIT); // 挂贷方账
					else if (amt.compareTo(BigDecimal.ZERO) == -1) // 贷方多
						raInput.setAcctingCD(CDFlag.CREDIT); // 挂借方账
					new AccounterImpl().runUpAccount(raInput);
				}
			}
			TXHelper.commit();
		} catch (AcctingException e) {
			LOG.error("分录平衡检查，自动挂账错", e);
			try {
				TXHelper.rollback();
			} catch (Exception e1) {
				LOG.error(e1.getMessage(),e1);
			}
			throw new AcctDayEndException(RUNUP_ACCOUNT_ERROR);
		} catch (RuntimeException e) {
			try {
				TXHelper.rollback();
			} catch (Exception e1) {
				LOG.error(e1.getMessage(),e1);
			}
			LOG.error("分录平衡检查，系统错", e);
			throw new AcctDayEndException(CHECK_DETAIL_BALANCE_ERROR);
		}finally{
			TXHelper.close();
		}
		if (!bWorkFlag && unBalancedBranchs.size() != 0) {
			throw new AcctDayEndException(CHECK_DETAIL_NOT_BALANCE); // 分录平衡检查不平
		}

	}

}
