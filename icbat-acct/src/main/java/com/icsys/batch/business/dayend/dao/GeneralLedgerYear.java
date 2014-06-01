package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 对应年总账表IC_GENGL_Y
 * 
 * @author kittyuu
 * 
 */
public class GeneralLedgerYear {
	/**
	 * 年度
	 */
	private String years;

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
	 * 月份
	 */
	private Integer monthes;

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
	private BigDecimal currentDebitSum;

	/**
	 * 本期贷方发生额
	 */
	private BigDecimal currentCreditSum;

	/**
	 * 余额
	 */
	private BigDecimal balance;

	/**
	 * 本期借方发生笔数
	 */
	private Integer currentDebitCount;

	/**
	 * 本期借方发生笔数
	 */
	private Integer currentCreditCount;

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
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

	public Integer getMonthes() {
		return monthes;
	}

	public void setMonthes(Integer monthes) {
		this.monthes = monthes;
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

	public BigDecimal getCurrentDebitSum() {
		return currentDebitSum;
	}

	public void setCurrentDebitSum(BigDecimal currentDebitSum) {
		this.currentDebitSum = currentDebitSum;
	}

	public BigDecimal getCurrentCreditSum() {
		return currentCreditSum;
	}

	public void setCurrentCreditSum(BigDecimal currentCreditSum) {
		this.currentCreditSum = currentCreditSum;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getCurrentDebitCount() {
		return currentDebitCount;
	}

	public void setCurrentDebitCount(Integer currentDebitCount) {
		this.currentDebitCount = currentDebitCount;
	}

	public Integer getCurrentCreditCount() {
		return currentCreditCount;
	}

	public void setCurrentCreditCount(Integer currentCreditCount) {
		this.currentCreditCount = currentCreditCount;
	}

	@Override
	public String toString() {
		return "GeneralLedgerYear [balance=" + balance + ", balanceCD="
				+ balanceCD + ", branchCode=" + branchCode + ", currType="
				+ currType + ", currentCreditCount=" + currentCreditCount
				+ ", currentCreditSum=" + currentCreditSum
				+ ", currentDebitCount=" + currentDebitCount
				+ ", currentDebitSum=" + currentDebitSum + ", monthes="
				+ monthes + ", startBalance=" + startBalance
				+ ", subjectCode=" + subjectCode + ", years=" + years + "]";
	}

}
