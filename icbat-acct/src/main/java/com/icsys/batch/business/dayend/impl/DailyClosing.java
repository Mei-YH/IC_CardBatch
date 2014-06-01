package com.icsys.batch.business.dayend.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.business.dayend.dao.AcctDayEndDAO;
import com.icsys.batch.business.dayend.dao.DailyClosingData;
import com.icsys.batch.business.dayend.dao.Dictionary;
import com.icsys.batch.business.dayend.dao.GeneralLedgerDay;
import com.icsys.batch.business.dayend.dao.GeneralLedgerDetail;
import com.icsys.platform.dao.TXHelper;

/**
 * 科目日结
 * 
 * @author kittyuu
 * 
 */
public class DailyClosing implements WorkStep, ErrorDef {

	private static Logger LOG = Logger.getLogger(DailyClosing.class);

	public void work(boolean bWorkFlag) throws AcctDayEndException {
		try {
			AcctSysParameter sysp = AcctSysUtil.getInstance().getAccSysParam();
			if (null == sysp) {
				throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
			}
			String sysDate = sysp.getRollOffDate();
			String noCurAccListTable = AcctSysUtil.getInstance()
					.getAccListTable(false);
			// 获取日结数据
			List<DailyClosingData> dailyClosingDataList = AcctDayEndDAO
					.getDailyClosingDataList(noCurAccListTable);
			TXHelper.beginNewTX();
			try{
				int sn = 1;
				for (DailyClosingData dailyClosingData : dailyClosingDataList) {
					// 通过日结数据生成总账明细数据
					GeneralLedgerDetail gd = createGeneralDetail(sysDate,
							dailyClosingData,sn);
					sn++;
					// 记录总账明细表
					AcctDayEndDAO.addGeneralLedgerDetail(gd, 1);
					// 记录科目日结表
					AcctDayEndDAO.addGeneralLedgerDetail(gd, 2);
				}
				TXHelper.commit();
			}catch(Exception e){
				try {
					TXHelper.rollback();
				} catch (Exception e1) {
					LOG.error(e1.getMessage(),e1);
				}
				throw e;
			}finally{
				TXHelper.close();
			}
		} catch (AcctDayEndException e) {
			LOG.error(e.getMessage(),e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new AcctDayEndException(DAILY_CLOSING__ERROR);
		}
	}

	/**
	 * 生成总账明细数据
	 * 
	 * @param sysDate账务日期
	 * @param dailyClosingData日结数据
	 * @param SN 账务记录序号
	 * @return
	 * @throws AcctDayEndException
	 */
	private GeneralLedgerDetail createGeneralDetail(String sysDate,
			DailyClosingData dailyClosingData,int Sn) throws AcctDayEndException {
		GeneralLedgerDetail gd = new GeneralLedgerDetail();
		gd.setAcctingDate(sysDate);
		//账务序号应该是顺序号
		gd.setAcctSN(Sn);
		String branchCode = dailyClosingData.getAcctingBranch();
		String subjectCode = dailyClosingData.getSubjectCode();
		String currType = dailyClosingData.getCurrType();
		gd.setBranchCode(branchCode);
		gd.setCurrType(currType);
		gd.setSubjectCode(subjectCode);
		gd.setTodayCreditCount(dailyClosingData.getCreditCount());
		gd.setTodayDebitCount(dailyClosingData.getDebitCount());
		gd.setTodayCreditSum(dailyClosingData.getCreditSum());
		gd.setTodayDebitSum(dailyClosingData.getDebitSum());
		// 从科目字典中获取余额方向
		Dictionary dic = AcctDayEndDAO.getSubDictionary(subjectCode, currType);
		if (null == dic) {
			LOG.error("Get subject dictionary error. currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "]");
			AcctDayEndException adee = new AcctDayEndException(
					ACCT_SUBJECT_NOT_FOUND);
			adee.setErrMsgArgs(new String[] { currType,
					subjectCode });
			throw adee;
		}
		gd.setBalanceCD(dic.getBalanceCD());
		
		// 从总账表中获得昨日余额
		GeneralLedgerDay gengl = AcctDayEndDAO.getGeneralLedger(branchCode,
				currType, subjectCode);
		BigDecimal balance = null;
		BigDecimal lastDayBalance = null;
		if (null == gengl) {
			LOG.error("Get General Ledger error. branchCode=[" + branchCode
					+ "]; currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "]");
			AcctDayEndException adee = new AcctDayEndException(
					ACCT_GENGL_NOT_FOUND);
			adee.setErrMsgArgs(new String[] { branchCode, currType,
					subjectCode });
			throw adee;
		}
		lastDayBalance = gengl.getBalance();
		gd.setLastDayBalance(lastDayBalance);
		// 计算余额
		if (AcctSysUtil.getInstance().isDebit(gengl.getBalanceCD())) {
			balance = lastDayBalance.add(dailyClosingData.getDebitSum());
			balance = balance.subtract(dailyClosingData.getCreditSum());
		} else {
			balance = lastDayBalance.add(dailyClosingData.getCreditSum());
			balance = balance.subtract(dailyClosingData.getDebitSum());
		}
		gd.setBalance(balance);

		return gd;
	}

}
