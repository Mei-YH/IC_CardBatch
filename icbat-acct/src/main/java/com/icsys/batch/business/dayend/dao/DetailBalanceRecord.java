package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 流水平衡检查报告记录
 * 
 * @author kittyuu
 * 
 */
public class DetailBalanceRecord {

	/**
	 * 轧账日期
	 */
	private String rollOffDate;
	/**
	 * 机构
	 */
	private String tranBranch;
	
	/**
	 * 账务机构
	 */
	private String acctingBranch;
	
	
	public String getAcctingBranch() {
		return acctingBranch;
	}

	public void setAcctingBranch(String acctingBranch) {
		this.acctingBranch = acctingBranch;
	}

	/**
	 * 账务流水号
	 */
	private String tranSerial;
	
	/**
	 * 分录序号
	 */
	private Integer sn;

	/**
	 * 币种
	 */
	private String currType;

	/**
	 * 借贷方总额之差 (借方总额 - 贷方总额)
	 */
	private BigDecimal diffBalance;

	public String getRollOffDate() {
		return rollOffDate;
	}

	public void setRollOffDate(String rollOffDate) {
		this.rollOffDate = rollOffDate;
	}

//	public String getTranBranch() {
//		return tranBranch;
//	}
//
//	public void setTranBranch(String tranBranch) {
//		this.tranBranch = tranBranch;
//	}


	public String getCurrType() {
		return currType;
	}


	public String getTranSerial() {
		return tranSerial;
	}

	public void setTranSerial(String tranSerial) {
		this.tranSerial = tranSerial;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public BigDecimal getDiffBalance() {
		return diffBalance;
	}

	public void setDiffBalance(BigDecimal diffBalance) {
		this.diffBalance = diffBalance;
	}

	@Override
	public String toString() {
		return "DetailBalanceRecord [currType=" + currType + ", diffBalance="
				+ diffBalance + ", rollOffDate=" + rollOffDate
				+ ", tranBranch=" + tranBranch + ", tranSerial=" + tranSerial
				+ ",acctingBranch="+acctingBranch+"]";
	}

}
