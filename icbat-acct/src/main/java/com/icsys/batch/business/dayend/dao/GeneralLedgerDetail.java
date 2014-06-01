package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 对应总账明细/科目日结
 * @author kitty
 *
 */
public class GeneralLedgerDetail {
	/**
	 * 账务日期
	 */
	private String acctingDate;

	/**
	 * 机构代码
	 */
	private String branchCode;
	
	/**
	 * 币种
	 */
	private String currType;
	
	/**
	 * 科目代码
	 */
	private String subjectCode;
	
	/**
	 * 账户序号
	 */
	private Integer acctSN;
	
	/**
	 * 余额方向
	 */
	private String balanceCD;

	/**
	 * 昨日余额
	 */
	private BigDecimal lastDayBalance;
	
	/**
	 * 本日借方发生额
	 */
	private BigDecimal todayDebitSum;
	
	/**
	 * 本日贷方发生额
	 */
	private BigDecimal todayCreditSum;
	
	/**
	 * 余额
	 */
	private BigDecimal balance;
	
	/**
	 * 本日借方发生笔数
	 */
	private Integer todayDebitCount;
	
	/**
	 * 本日贷方发生笔数
	 */
	private Integer todayCreditCount;

	public String getAcctingDate() {
		return acctingDate;
	}

	public void setAcctingDate(String acctingDate) {
		this.acctingDate = acctingDate;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Integer getAcctSN() {
		return acctSN;
	}

	public void setAcctSN(Integer acctSN) {
		this.acctSN = acctSN;
	}

	public String getBalanceCD() {
		return balanceCD;
	}

	public void setBalanceCD(String balanceCD) {
		this.balanceCD = balanceCD;
	}

	public BigDecimal getLastDayBalance() {
		return lastDayBalance;
	}

	public void setLastDayBalance(BigDecimal lastDayBalance) {
		this.lastDayBalance = lastDayBalance;
	}

	public BigDecimal getTodayDebitSum() {
		return todayDebitSum;
	}

	public void setTodayDebitSum(BigDecimal todayDebitSum) {
		this.todayDebitSum = todayDebitSum;
	}

	public BigDecimal getTodayCreditSum() {
		return todayCreditSum;
	}

	public void setTodayCreditSum(BigDecimal todayCreditSum) {
		this.todayCreditSum = todayCreditSum;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getTodayDebitCount() {
		return todayDebitCount;
	}

	public void setTodayDebitCount(Integer todayDebitCount) {
		this.todayDebitCount = todayDebitCount;
	}

	public Integer getTodayCreditCount() {
		return todayCreditCount;
	}

	public void setTodayCreditCount(Integer todayCreditCount) {
		this.todayCreditCount = todayCreditCount;
	}

	@Override
	public String toString() {
		return "GeneralDetail [acctSN=" + acctSN + ", acctingDate="
				+ acctingDate + ", balance=" + balance + ", balanceCD="
				+ balanceCD + ", branchCode=" + branchCode + ", currType="
				+ currType + ", lastDayBalance=" + lastDayBalance
				+ ", subjectCode=" + subjectCode + ", todayCreditCount="
				+ todayCreditCount + ", todayCreditSum=" + todayCreditSum
				+ ", todayDebitCount=" + todayDebitCount + ", todayDebitSum="
				+ todayDebitSum + "]";
	}


}
