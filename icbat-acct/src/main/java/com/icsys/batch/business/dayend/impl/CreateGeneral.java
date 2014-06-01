package com.icsys.batch.business.dayend.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.business.dayend.dao.AcctDayEndDAO;
import com.icsys.batch.business.dayend.dao.GeneralLedgerDay;
import com.icsys.batch.business.dayend.dao.GeneralLedgerDetail;
import com.icsys.batch.business.dayend.dao.GeneralLedgerMonth;
import com.icsys.batch.business.dayend.dao.GeneralLedgerYear;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.util.DateUtils;

/**
 * 日终记总账
 * 
 * @author kittyuu
 * 
 */
public class CreateGeneral implements WorkStep, ErrorDef {

	private static Logger LOG = Logger.getLogger(CreateGeneral.class);

	public void work(boolean bWorkFlag) throws AcctDayEndException {
		try {
			AcctSysParameter sysp = AcctSysUtil.getInstance().getAccSysParam();
			if (null == sysp) {
				throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
			}
			//轧差日期
			String rollOffDate = sysp.getRollOffDate();
			Date sysDate = DateUtils.strToDate(rollOffDate);
			// 获取日结数据
			List<GeneralLedgerDetail> genelDetList = AcctDayEndDAO
					.getGeneralDetail();
			// 获得年月信息
			int month = DateUtils.getDayMonth(sysDate);
			int year = DateUtils.getDayYear(sysDate);
			TXHelper.beginNewTX();
			try{
				for (GeneralLedgerDetail genglDet : genelDetList) {
					// 每天更新总账表
					GeneralLedgerDay gengl = prepareGeneralLedger(genglDet);
					AcctDayEndDAO.updateGeneralLedger(gengl);
					// 每天更新月总账
					GeneralLedgerMonth genglM = prepareGeneralLedgerM(genglDet,
							month);
					AcctDayEndDAO.updateGeneralLedgerM(genglM);
					
					
					// 是否月末
					/*if (DateUtils.isLastDayOfMonth(sysDate)) {
						// 增加年总账表中月份为当月的数据
						GeneralLedgerYear genglY = createGeneralYear(genglDet,
								year, month);
						AcctDayEndDAO.addGeneralLedgerY(genglY);
						// 累加年总账表中月份为0的数据
						GeneralLedgerYear genglY0 = prepareGeneralYear0(genglDet,
								year, month);
						AcctDayEndDAO.updateGeneralLedgerY(genglY0);
						// 更新月总账表（月份改为新的一月，设置期初余额和余额，其他清0）
						GeneralLedgerMonth genglNM = createGeneralLedgerM(genglDet,
								month);
						AcctDayEndDAO.updateGeneralMNewMonth(genglNM);
					}
					// 是否年末
					if (DateUtils.isLastDayOfYear(sysDate)) {
						// 年总账表（ic_gengl_y）增加下一年度的记录（月份为0和1）
						GeneralLedgerYear genglY0 = createInitGeneralLedgerY(
								genglDet, year, 0);
						AcctDayEndDAO.addGeneralLedgerY(genglY0);
						GeneralLedgerYear genglY1 = createInitGeneralLedgerY(
								genglDet, year, 1);
						AcctDayEndDAO.addGeneralLedgerY(genglY1);
					}*/
				}
				//TODO 处理月末和年末的特殊情况
				dealLastDayOfMonthOrYear(sysDate ,year);
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
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AcctDayEndException(CREATE_GENERAL_ERROR);
		}

	}
	
	private void dealLastDayOfMonthOrYear(Date lastDay,int year) throws Exception{
		
		try {
			// 如果是月末
			if (DateUtils.isLastDayOfMonth(lastDay)) {
				LOG.debug("当前日期"+lastDay+"为月末");
				List<GeneralLedgerMonth> records = AcctDayEndDAO.findAllGeneralLedgerM();
				for(GeneralLedgerMonth record : records){
					GeneralLedgerYear genglY = createGeneralLedgerY(record, year);
					//增加年总账表中月份为当月的记录
					AcctDayEndDAO.addGeneralLedgerY(genglY);
					//并且修改月份为0的记录（累加）
					updateGeneralLedgerY(record,year);
					
					//更新月总账表（月份改为新的一月，设置期初余额和余额，其他清0）
					//nextMonth
					int currmonth=record.getMonthes();
					int nextMonth = (currmonth + 1) > 12 ? 1:(currmonth + 1);
					record.setNextMonth(nextMonth);//设置下一个月份
					AcctDayEndDAO.updateGeneralMNewMonth(record);
					
				}
			}
			// 如果是年末（每年最后一天）
			/**
			 * modify by chenyuchang 20120109
			 * 在生成年终帐时只需要 增加一个月份为0的记录就可以。
			 * 
			 */
			if (DateUtils.isLastDayOfYear(lastDay)) {
				
				List<GeneralLedgerMonth> records = AcctDayEndDAO.findAllGeneralLedgerM();
				LOG.debug("当前日期"+lastDay+"为年末，需对年总账进行");
				for(GeneralLedgerMonth record : records){
//					GeneralLedgerYear genglY = createGeneralLedgerY(record, year);
					
//					// 年总账表（ic_gengl_y）增加下一年度的记录（月份为0和1）
//					AcctDayEndDAO.addGeneralLedgerY(genglY);
//					GeneralLedgerYear genglY1 = createGeneralLedgerY(
//							record, year, 1);
					GeneralLedgerYear genglY0 = createGeneralLedgerY(record, year, 0);
					AcctDayEndDAO.addGeneralLedgerY(genglY0);
//					
				}
				
			}
		}catch (Exception e) {
			throw e;
		}
	}

	//TODO 
	/* 
	 * 根据月总账记录，创建年总账当月数据
	 */
	public GeneralLedgerYear createGeneralLedgerY(
			GeneralLedgerMonth generalLedgerMonth, int year) {
		GeneralLedgerYear generalLY = new GeneralLedgerYear();
		String years = String.valueOf(year);
    	generalLY.setYears(years);
		generalLY.setBranchCode(generalLedgerMonth.getBranchCode());
		generalLY.setCurrType(generalLedgerMonth.getCurrType());
		generalLY.setSubjectCode(generalLedgerMonth.getSubjectCode());
		generalLY.setMonthes(generalLedgerMonth.getMonthes());
		generalLY.setBalanceCD(generalLedgerMonth.getBalanceCD());
		generalLY.setCurrentCreditSum(generalLedgerMonth.getCurrentCreditSum());
		generalLY.setCurrentDebitSum(generalLedgerMonth.getCurrentDebitSum());
		generalLY.setCurrentCreditCount(generalLedgerMonth.getCurrentCreditCount());
		generalLY.setCurrentDebitCount(generalLedgerMonth.getCurrentDebitCount());
		generalLY.setBalance(generalLedgerMonth.getBalance());
		generalLY.setBalanceCD(generalLedgerMonth.getBalanceCD());
		//add by chenyuchang 20111202 缺少设置初始值
		generalLY.setStartBalance(generalLedgerMonth.getStartBalance());
    	return generalLY;
	}

	/**
	 * 准备总账数据
	 * 
	 * @param genglDet科目日结数据
	 * @return
	 * @throws AcctDayEndException
	 */
	private GeneralLedgerDay prepareGeneralLedger(GeneralLedgerDetail genglDet)
			throws AcctDayEndException {
		String branchCode = genglDet.getBranchCode();
		String currType = genglDet.getCurrType();
		String subjectCode = genglDet.getSubjectCode();
		GeneralLedgerDay gengl = AcctDayEndDAO.getGeneralLedger(branchCode,
				currType,
				subjectCode);
		if (null == gengl) {
			LOG.error("Get general ledger error. branchCode=["
					+ branchCode + "]; currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "]");
			throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
		}
		BigDecimal balance = gengl.getBalance();
		//期初余额  --上一日余额
		gengl.setStartBalance(balance);
		
		// 余额
		
		// 根据余额标志计算余额
		if (AcctSysUtil.getInstance().isDebit(gengl.getBalanceCD())) {
			balance = balance.add(genglDet.getTodayDebitSum());
			balance = balance.subtract(genglDet.getTodayCreditSum());
		} else {
			balance = balance.add(genglDet.getTodayCreditSum());
			balance = balance.subtract(genglDet.getTodayDebitSum());
		}
		gengl.setBalance(balance);

		// 贷方发生额
		BigDecimal creditSum = gengl.getCreditSum();
		gengl.setCreditSum(creditSum.add(genglDet.getTodayCreditSum()));

		// 借方发生额
		BigDecimal debitSum = gengl.getDebitSum();
		gengl.setDebitSum(debitSum.add(genglDet.getTodayDebitSum()));

		// 贷方发生笔数
		gengl.setCreditCount(gengl.getCreditCount()
				+ genglDet.getTodayCreditCount());

		// 借方发生笔数
		gengl.setDebitCount(gengl.getDebitCount()
				+ genglDet.getTodayDebitCount());

		return gengl;
	}

	/**
	 * 准备月总账数据
	 * 
	 * @param genglDet科目日结数据
	 * @param month月份
	 * @return
	 * @throws AcctDayEndException
	 */
	private GeneralLedgerMonth prepareGeneralLedgerM(
			GeneralLedgerDetail genglDet, int month) throws AcctDayEndException {
		String branchCode = genglDet.getBranchCode();
		String currType = genglDet.getCurrType();
		String subjectCode = genglDet.getSubjectCode();
		GeneralLedgerMonth genglM = AcctDayEndDAO.getGeneralLedgerM(branchCode,
				currType,
				subjectCode, month);
		if (null == genglM) {
			LOG.error("Get month general ledger error. branchCode=["
					+ branchCode + "]; currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "];"
					+ " month=[" + month + "]");
			throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
		}
		// 余额
		BigDecimal balance = genglM.getBalance();
		// 根据余额标志计算余额
		if (AcctSysUtil.getInstance().isDebit(genglM.getBalanceCD())) {
			balance = balance.add(genglDet.getTodayDebitSum());
			balance = balance.subtract(genglDet.getTodayCreditSum());
		} else {
			balance = balance.add(genglDet.getTodayCreditSum());
			balance = balance.subtract(genglDet.getTodayDebitSum());
		}
		genglM.setBalance(balance);

		// 贷方发生额
		BigDecimal creditSum = genglM.getCurrentCreditSum();
		genglM.setCurrentCreditSum(creditSum.add(genglDet
				.getTodayCreditSum()));

		// 借方发生额
		BigDecimal debitSum = genglM.getCurrentDebitSum();
		genglM
				.setCurrentDebitSum(debitSum.add(genglDet
				.getTodayDebitSum()));

		// 贷方发生笔数
		genglM.setCurrentCreditCount(genglM.getCurrentCreditCount()
				+ genglDet.getTodayCreditCount());

		// 借方发生笔数
		genglM.setCurrentDebitCount(genglM.getCurrentDebitCount()
				+ genglDet.getTodayDebitCount());

		return genglM;
	}

	/**
	 * 生成新月总账数据
	 * 
	 * @param genglDet
	 * @param month
	 * @return
	 * @throws AcctDayEndException
	 */
	private GeneralLedgerMonth createGeneralLedgerM(
			GeneralLedgerDetail genglDet, int month) throws AcctDayEndException {
		String branchCode = genglDet.getBranchCode();
		String currType = genglDet.getCurrType();
		String subjectCode = genglDet.getSubjectCode();
		GeneralLedgerMonth genglM = AcctDayEndDAO.getGeneralLedgerM(branchCode,
				currType,
				subjectCode, month);
		if (null == genglM) {
			LOG.error("Get month general ledger error. branchCode=["
					+ branchCode + "]; currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "];"
					+ " month=[" + month + "]");
			throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
		}
		// 月份(1-12)
		int nextMonth = (month + 1) > 12 ? 1 : (month + 1);
		genglM.setNextMonth(nextMonth);
		// 期初余额 = 余额
		genglM.setStartBalance(genglM.getBalance());

		// 贷方发生额
		genglM.setCurrentCreditSum(new BigDecimal(0));

		// 借方发生额
		genglM.setCurrentDebitSum(new BigDecimal(0));

		// 贷方发生笔数
		genglM.setCurrentCreditCount(0);

		// 借方发生笔数
		genglM.setCurrentDebitCount(0);
		return genglM;
	}

	/**
	 * 生成年总账中月份为当月的数据
	 * 
	 * @param genglDet科目日结数据
	 * @param year年
	 * @param month月
	 * @throws AcctDayEndException
	 */
	private GeneralLedgerYear createGeneralYear(GeneralLedgerDetail genglDet,
			int year, int month) throws AcctDayEndException {
		GeneralLedgerYear genglY = new GeneralLedgerYear();
		// 准备数据
		String years = String.valueOf(year);
		genglY.setYears(years);
		String branchCode = genglDet.getBranchCode();
		String currType = genglDet.getCurrType();
		String subjectCode = genglDet.getSubjectCode();
		genglY.setBranchCode(branchCode);
		genglY.setCurrType(currType);
		genglY.setSubjectCode(subjectCode);
		genglY.setMonthes(month);
		// 获取当月月总账数据
		GeneralLedgerMonth genglM = AcctDayEndDAO.getGeneralLedgerM(branchCode,
				currType,
				subjectCode, month);
		if (null == genglM) {
			LOG.error("Get month general ledger error. branchCode=["
					+ branchCode + "]; currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "];"
					+ " month=[" + month + "]");
			throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
		}
		genglY.setCurrentCreditCount(genglM.getCurrentCreditCount());
		genglY.setCurrentCreditSum(genglM.getCurrentCreditSum());
		genglY.setCurrentDebitCount(genglM.getCurrentDebitCount());
		genglY.setCurrentDebitSum(genglM.getCurrentDebitSum());
		BigDecimal balance = genglM.getBalance();
		genglY.setStartBalance(balance);
		genglY.setBalance(balance);
		genglY.setBalanceCD(genglM.getBalanceCD());
		return genglY;
	}

	/**
	 * 修改年总账月份为0的数据
	 * 
	 * @param genglDet科目日结数据
	 * @param year年份
	 * @param month月份
	 * @return
	 * @throws AcctDayEndException
	 */
	private GeneralLedgerYear prepareGeneralYear0(GeneralLedgerDetail genglDet,
			int year, int month) throws AcctDayEndException {
		// 获取月份为0的年总账数据
		String branchCode = genglDet.getBranchCode();
		String currType = genglDet.getCurrType();
		String subjectCode = genglDet.getSubjectCode();
		GeneralLedgerYear genglY = AcctDayEndDAO.getGeneralLedgerY(branchCode,
				currType, subjectCode, year, 0);
		if (null != genglY) {
			// 获取当月月总账数据
			GeneralLedgerMonth genglM = AcctDayEndDAO.getGeneralLedgerM(
					branchCode,
					currType,
					subjectCode, month);
			if (null == genglM) {
				LOG.error("Get month general ledger error. branchCode=["
						+ branchCode + "]; currType=["
						+ currType
						+ "]; subjectCode=["
						+ subjectCode + "];"
						+ " month=[" + month + "]");
				throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
			}
			BigDecimal balance = genglY.getBalance();
			if (AcctSysUtil.getInstance().isDebit(genglY.getBalanceCD())) {
				balance = balance.add(genglM.getCurrentDebitSum());
				balance = balance.subtract(genglDet.getTodayCreditSum());
			} else {
				balance = balance.add(genglM.getCurrentCreditSum());
				balance = balance.subtract(genglDet.getTodayDebitSum());
			}
			genglY.setBalance(balance);
			// 贷方发生额
			BigDecimal creditSum = genglY.getCurrentCreditSum();
			genglY.setCurrentCreditSum(creditSum.add(genglM
					.getCurrentCreditSum()));

			// 借方发生额
			BigDecimal debitSum = genglY.getCurrentDebitSum();
			genglY.setCurrentDebitSum(debitSum.add(genglM
					.getCurrentDebitSum()));

			// 贷方发生笔数
			genglY.setCurrentCreditCount(genglY.getCurrentCreditCount()
					+ genglM.getCurrentCreditCount());

			// 借方发生笔数
			genglY.setCurrentDebitCount(genglY.getCurrentDebitCount()
					+ genglM.getCurrentDebitCount());
		}
		return genglY;
	}

	//TODO 
	/* 
	 * 修改年总账表月份为0的记录（累加指定月份的记录）
	 */
	public void updateGeneralLedgerY(GeneralLedgerMonth generalLedgerMonth, int year) throws Exception{
		// 获取月份为0的年总账记录
		// 获取月份为0的年总账数据
		String branchCode = generalLedgerMonth.getBranchCode();
		String currType = generalLedgerMonth.getCurrType();
		String subjectCode = generalLedgerMonth.getSubjectCode();
		GeneralLedgerYear genglY = AcctDayEndDAO.getGeneralLedgerY(branchCode,
				currType, subjectCode, year, 0);
		if (null == genglY) {
			LOG.error("Get year general ledger error. branch=["
					+ generalLedgerMonth.getBranchCode() + "]; currType=["
					+ generalLedgerMonth.getCurrType()
					+ "]; subjectCode=["
					+ generalLedgerMonth.getSubjectCode() + "]: month=[" + generalLedgerMonth.getMonthes()
					+ "]; year=[" + year + "]");
			throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
		}
		
		// 余额
		BigDecimal balance = genglY.getBalance();
		//借
		if (AcctSysUtil.getInstance().isDebit(genglY.getBalanceCD())) {
			balance = balance.add(generalLedgerMonth.getCurrentDebitSum());
			balance = balance.subtract(generalLedgerMonth.getCurrentCreditSum());
		} else {
			balance = balance.add(generalLedgerMonth.getCurrentCreditSum());
			balance = balance.subtract(generalLedgerMonth.getCurrentDebitSum());
		}
		
		genglY.setBalance(balance);
		
		// 贷方发生额
		BigDecimal creditSum = genglY.getCurrentCreditSum();
		genglY.setCurrentCreditSum(creditSum.add(generalLedgerMonth
				.getCurrentCreditSum()));

		// 借方发生额
		BigDecimal debitSum = genglY.getCurrentDebitSum();
		genglY.setCurrentDebitSum(debitSum.add(generalLedgerMonth
				.getCurrentDebitSum()));

		// 贷方发生笔数
		genglY.setCurrentCreditCount(genglY.getCurrentCreditCount()
				+ generalLedgerMonth.getCurrentCreditCount());

		// 借方发生笔数
		genglY.setCurrentDebitCount(genglY.getCurrentDebitCount()
				+ generalLedgerMonth.getCurrentDebitCount());
		//入库
		AcctDayEndDAO.updateGeneralLedgerY(genglY);
		
	}

	
	
	
	/**
	 * 创建下一年度的年总账初始数据
	 * 
	 * @param genglDet科目日结数据
	 * @param year年份
	 * @param month月份
	 * @return
	 * @throws AcctDayEndException
	 */
	private GeneralLedgerYear createInitGeneralLedgerY(
			GeneralLedgerDetail genglDet, int year, int month)
			throws AcctDayEndException {
		GeneralLedgerYear generalLY = new GeneralLedgerYear();
		String years = String.valueOf(year + 1);
		generalLY.setYears(years);
		String branchCode = genglDet.getBranchCode();
		String currType = genglDet.getCurrType();
		String subjectCode = genglDet.getSubjectCode();
		generalLY.setBranchCode(branchCode);
		generalLY.setMonthes(month);
		generalLY.setCurrentCreditSum(new BigDecimal(0));
		generalLY.setCurrentCreditCount(0);
		generalLY.setCurrType(currType);
		generalLY.setCurrentDebitSum(new BigDecimal(0));
		generalLY.setCurrentDebitCount(0);
		generalLY.setSubjectCode(subjectCode);

		// 获取月份为0的年总账记录
		GeneralLedgerYear genglY0 = AcctDayEndDAO.getGeneralLedgerY(branchCode,
				currType, subjectCode, year, month);
		if (null == genglY0) {
			LOG.error("Get year general ledger error. branchCode=["
					+ branchCode + "]; currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "]; month=[0];"
					+ " year=[" + year + "]");
			throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
		}
		// 新月份的期初余额=月份为0的年总账记录的余额
		BigDecimal balance = genglY0.getBalance();
		generalLY.setStartBalance(balance);
		generalLY.setBalance(balance);
		generalLY.setBalanceCD(genglY0.getBalanceCD());
		return generalLY;
	}
	
	/*
	 * 创建下一年度的初始记录
	 */
	public GeneralLedgerYear createGeneralLedgerY(
			GeneralLedgerMonth generalLedgerMonth, int year, int month) throws Exception{
		GeneralLedgerYear generalLY = new GeneralLedgerYear();
		String years = String.valueOf(year + 1);
		generalLY.setYears(years);
		String branchCode = generalLedgerMonth.getBranchCode();
		String currType = generalLedgerMonth.getCurrType();
		String subjectCode = generalLedgerMonth.getSubjectCode();
		generalLY.setBranchCode(branchCode);
		generalLY.setMonthes(month);
		generalLY.setCurrentCreditSum(new BigDecimal(0));
		generalLY.setCurrentCreditCount(0);
		generalLY.setCurrType(currType);
		generalLY.setCurrentDebitSum(new BigDecimal(0));
		generalLY.setCurrentDebitCount(0);
		generalLY.setSubjectCode(subjectCode);
		
		// 获取月份为0的年总账记录
		GeneralLedgerYear genglY0 = AcctDayEndDAO.getGeneralLedgerY(branchCode,
				currType, subjectCode, year, month);
		if (null == genglY0) {
			LOG.error("Get year general ledger error. branchCode=["
					+ branchCode + "]; currType=["
					+ currType
					+ "]; subjectCode=["
					+ subjectCode + "]; month=[0];"
					+ " year=[" + year + "]");
			throw new AcctDayEndException(ACCT_RECORD_NOT_FOUND);
		}
		// 新月份的期初余额=月份为0的年总账记录的余额
		BigDecimal balance = genglY0.getBalance();
		generalLY.setStartBalance(balance);
		generalLY.setBalance(balance);
		generalLY.setBalanceCD(genglY0.getBalanceCD());
		return generalLY;
	}


	
}
