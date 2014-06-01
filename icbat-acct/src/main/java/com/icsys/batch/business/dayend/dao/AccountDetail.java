package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 对应账号明细表(ic_acct_det)
 * 
 * @author Administrator
 * 
 */
public class AccountDetail {
	/**
	 * 账号
	 */
	private String acctNO;

	/**
	 * 交易日期
	 */
	private String tranDate;
	/**
	 * 帐务日期
	 */
	private String acctingDate;
	/**
	 * 记账流水号
	 */
	private String serial;

	/**
	 * 分录序号
	 */
	private Integer SN;

	/**
	 * 发生机构
	 */
	private String tranBranch;

	/**
	 * 摘要
	 */
	private String remark;

	/**
	 * 借贷记标志
	 */
	private Integer tranCD;

	/**
	 * 记账金额
	 */
	private BigDecimal tranAmount;

	/**
	 * 手续费
	 */
	private BigDecimal fee;

	/**
	 * 余额
	 */
	private BigDecimal balance;

	/**
	 * 交易柜员
	 */
	private String tranTeller;

	public String getAcctNO() {
		return acctNO;
	}

	public void setAcctNO(String acctNO) {
		this.acctNO = acctNO;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getAcctingDate() {
		return acctingDate;
	}

	public void setAcctingDate(String acctingDate) {
		this.acctingDate = acctingDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getSN() {
		return SN;
	}

	public void setSN(Integer sN) {
		SN = sN;
	}

	public String getTranBranch() {
		return tranBranch;
	}

	public void setTranBranch(String tranBranch) {
		this.tranBranch = tranBranch;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTranCD() {
		return tranCD;
	}

	public void setTranCD(Integer tranCD) {
		this.tranCD = tranCD;
	}

	public BigDecimal getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getTranTeller() {
		return tranTeller;
	}

	public void setTranTeller(String tranTeller) {
		this.tranTeller = tranTeller;
	}

	@Override
	public String toString() {
		return "AccountDetail [SN=" + SN + ", acctNO=" + acctNO
				+ ", acctingDate=" + acctingDate + ", balance=" + balance
				+ ", fee=" + fee + ", serial=" + serial + ", remark="
				+ remark + ", tranAmount=" + tranAmount + ", tranBranch="
				+ tranBranch + ", tranCD=" + tranCD + ", tranDate=" + tranDate
				+ ", tranTeller=" + tranTeller + "]";
	}

}
