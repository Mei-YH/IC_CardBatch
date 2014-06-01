package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 机构平衡检查报告记录
 * 
 * @author kittyuu
 * 
 */
public class BranchBalanceRecord {
	/**
	 * 机构
	 */
	private String tranBranch;
	
	/**
	 * 账务机构
	 */
	private String acctingBranch;
	
	/**
	 * 币种
	 */
	private String currType;

	/**
	 * 借贷方总额之差 (借方总额 - 贷方总额)
	 */
	private BigDecimal diffBalance;

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

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public BigDecimal getDiffBalance() {
		return diffBalance;
	}

	public void setDiffBalance(BigDecimal diffBalance) {
		this.diffBalance = diffBalance;
	}

	public String getAcctingBranch() {
		return acctingBranch;
	}

	public void setAcctingBranch(String acctBranch) {
		this.acctingBranch = acctBranch;
	}

	@Override
	public String toString() {
		return "BranchBalanceRecord [currType=" + currType + ", diffBalance="
				+ diffBalance + ", tranBranch=" + tranBranch + ",acctingBranch="+acctingBranch+"]";
	}

}
