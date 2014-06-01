package com.icsys.batch.business.acctting.impl;

import java.math.BigDecimal;

import com.icsys.batch.business.acct.api.CDFlag;

public class RunUpAcctInput {
	/**
	 * 挂账机构
	 */
	private String tranBranch;
	/**
	 * 挂账借贷标志
	 */
	private CDFlag acctingCD;
	/**
	 * 挂账金额
	 */
	private BigDecimal amount;
	
	public String getTranBranch() {
		return tranBranch;
	}
	public void setTranBranch(String tranBranch) {
		this.tranBranch = tranBranch;
	}
	public CDFlag getAcctingCD() {
		return acctingCD;
	}
	public void setAcctingCD(CDFlag acctingCD) {
		this.acctingCD = acctingCD;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "RunUpAcctInput [acctingCD=" + acctingCD + ", amount=" + amount
				+ ", tranBranch=" + tranBranch + "]";
	}
	
}
