package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 对应总账表IC_GENGL
 * 
 * @author kittyuu
 * 
 */
public class GeneralLedgerDay {
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
	 * 分户账最大序号
	 */
	private Integer maxSerial;
	
	/**
	 * 余额方向
	 */
	private String balanceCD;
	
	/**
	 * 期初余额
	 */
	private BigDecimal startBalance;
	
	/**
	 * 本期借方发生额
	 */
	private BigDecimal debitSum;
	
	/**
	 * 本期贷方发生额
	 */
	private BigDecimal creditSum;
	
	/**
	 * 余额
	 */
	private BigDecimal balance;
	
	/**
	 * 本期借方发生笔数
	 */
	private Integer debitCount;
	
	/**
	 * 本期借方发生笔数
	 */
	private Integer creditCount;

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

	public Integer getMaxSerial() {
		return maxSerial;
	}

	public void setMaxSerial(Integer maxSerial) {
		this.maxSerial = maxSerial;
	}

	public String getBalanceCD() {
		return balanceCD;
	}

	public void setBalanceCD(String balanceCD) {
		this.balanceCD = balanceCD;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getDebitSum() {
		return debitSum;
	}

	public void setDebitSum(BigDecimal debitSum) {
		this.debitSum = debitSum;
	}

	public BigDecimal getCreditSum() {
		return creditSum;
	}

	public void setCreditSum(BigDecimal creditSum) {
		this.creditSum = creditSum;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getDebitCount() {
		return debitCount;
	}

	public void setDebitCount(Integer debitCount) {
		this.debitCount = debitCount;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}

	@Override
	public String toString() {
		return "GeneralLedgerDay [balance=" + balance + ", balanceCD="
				+ balanceCD + ", branchCode=" + branchCode + ", creditCount="
				+ creditCount + ", creditSum=" + creditSum + ", currType="
				+ currType + ", debitCount=" + debitCount + ", debitSum="
				+ debitSum + ", maxSerial=" + maxSerial + ", startBalance="
				+ startBalance + ", subjectCode=" + subjectCode + "]";
	}

}
