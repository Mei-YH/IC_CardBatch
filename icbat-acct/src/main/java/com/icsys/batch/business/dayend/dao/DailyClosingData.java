package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 日结数据，由分录流水表得到的统计数据，用于生成科目日结表，总账，总账明细等
 * 
 * @author kittyuu
 * 
 */
public class DailyClosingData {
	/**
	 * 机构代码
	 */
	private String acctingBranch;

	public String getAcctingBranch() {
		return acctingBranch;
	}

	public void setAcctingBranch(String acctingBranch) {
		this.acctingBranch = acctingBranch;
	}

	/**
	 * 币种
	 */
	private String currType;

	/**
	 * 科目代码
	 */
	private String subjectCode;

	/**
	 * 本期借方发生额
	 */
	private BigDecimal debitSum;

	/**
	 * 本期贷方发生额
	 */
	private BigDecimal creditSum;

	/**
	 * 本期借方发生笔数
	 */
	private Integer debitCount;

	/**
	 * 本期借方发生笔数
	 */
	private Integer creditCount;


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
		return "DailyClosingData [acctingBranch=" + acctingBranch + ", creditCount="
				+ creditCount + ", creditSum=" + creditSum + ", currType="
				+ currType + ", debitCount=" + debitCount + ", debitSum="
				+ debitSum + ", subjectCode=" + subjectCode + "]";
	}

}
