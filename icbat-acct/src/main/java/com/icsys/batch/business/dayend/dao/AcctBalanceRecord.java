package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * @author Administrator
 * 
 */
public class AcctBalanceRecord {
	/**
	 * 轧账日期
	 */
	private String rollOffDate;
	/**
	 * 机构代码
	 */
	private String branchCode;
	/**
	 * 币种
	 */
	private String currType;

	/**
	 * 借贷总额之差 (借总额 - 贷总额)
	 */
	private BigDecimal diffBalance;
	/**
	 * 科目代码
	 */
	private String subjectCode;
	/**
	 * 余额
	 */
	private BigDecimal balance;

	/**
	 * 计算余额
	 */
	private BigDecimal calBalance;

	public String getRollOffDate() {
		return rollOffDate;
	}

	public void setRollOffDate(String rollOffDate) {
		this.rollOffDate = rollOffDate;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCalBalance() {
		return calBalance;
	}

	public void setCalBalance(BigDecimal calBalance) {
		this.calBalance = calBalance;
	}

	public BigDecimal getDiffBalance() {
		return diffBalance;
	}

	public void setDiffBalance(BigDecimal diffBalance) {
		this.diffBalance = diffBalance;
	}

	@Override
	public String toString() {
		return "AcctBalanceRecord [balance=" + balance + ", branchCode="
				+ branchCode + ", calBalance=" + calBalance + ", currType="
				+ currType + ", diffBalance=" + diffBalance + ", rollOffDate="
				+ rollOffDate + ", subjectCode=" + subjectCode + "]";
	}

}
