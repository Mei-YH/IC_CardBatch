package com.icsys.batch.business.acctting.impl;

import java.math.BigDecimal;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acct.api.CDFlag;
import com.icsys.batch.business.acct.api.NoSuchAcctException;
import com.icsys.batch.business.acct.api.NoSuchSubjectException;
import com.icsys.batch.business.acct.impl.AccountRepsitory;

/**
 * 分录流水信息
 * 
 * @author kitty
 * 
 */
public class InnerAcctingInput {

	/**
	 * 交易流水号
	 */
	private String tranSerial;
	/**
	 * 原流水号
	 */
	private String orgSerial;
	/**
	 * 账号
	 */
	private String acctNO;

	/**
	 * 发生机构
	 */
	private String tranBranch;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 借贷标志
	 */
	private CDFlag acctingCD;
	/**
	 * 记账金额
	 */
	private BigDecimal amount;

	/**
	 * 摘要
	 */
	private String remark;

	/**
	 * 流水标志0-正常，1-冲正
	 */
	private String serialFlag = "0";

	public String getSerialFlag() {
		return serialFlag;
	}

	public void setSerialFlag(String serialFlag) {
		this.serialFlag = serialFlag;
	}

	public String getAcctNO() {
		return acctNO;
	}

	public void setAcctNO(String acctNO) {
		this.acctNO = acctNO;
	}

	public String getTranSerial() {
		return tranSerial;
	}

	public void setTranSerial(String tranSerial) {
		this.tranSerial = tranSerial;
	}

	public String getTranBranch() {
		return tranBranch;
	}

	public void setTranBranch(String tranBranch) {
		this.tranBranch = tranBranch;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrgSerial() {
		return orgSerial;
	}

	public void setOrgSerial(String originalSerial) {
		this.orgSerial = originalSerial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tranSerial == null) ? 0 : tranSerial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	public AcctingList toAcctingList() throws NoSuchAcctException,
			NoSuchSubjectException {
		AcctingList alist = new AcctingList();
		alist.setTranBranch(this.tranBranch);
		alist.setAcctCD(acctingCD.getFlag());
		alist.setAcctNO(acctNO);
		alist.setAmount(amount);
		Account acct = new AccountRepsitory().getAccount(acctNO);
		alist.setCurrType(acct.getCurrencyCode());
		alist.setSubjectCode(acct.getSubjectCode());
		alist.setAcctingBranch(acct.getAccOpbranch());
		alist.setOperator(operator);
		alist.setRemark(remark);
		alist.setTranSerial(tranSerial);
		alist.setOrgSerial(orgSerial);
		alist.setSerialFlag(serialFlag);
		return alist;
	}

}
