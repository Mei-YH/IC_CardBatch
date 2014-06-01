package com.icsys.batch.account;

import java.math.BigDecimal;

/**
 * @author Runpu Hu  
 * @version 创建时间：2011-5-16 下午05:00:07 
 * 类说明 ：标准一借一贷输出bean
 *
 */
public class AccountOutput {
	
	/**
	 * 记账流水号
	 */
	private String acctingSerial;

	/**
	 * 记账日期
	 */
	private String acctingDate;
	
	/**
	 * 借方账户余额
	 */
	private BigDecimal debitBalance;
	
	/**
	 * 贷方账户余额
	 */
	private BigDecimal creditBalance;

	public String getAcctingSerial() {
		return acctingSerial;
	}

	public void setAcctingSerial(String acctingSerial) {
		this.acctingSerial = acctingSerial;
	}

	public String getAcctingDate() {
		return acctingDate;
	}

	public void setAcctingDate(String acctingDate) {
		this.acctingDate = acctingDate;
	}

	public BigDecimal getDebitBalance() {
		return debitBalance;
	}

	public void setDebitBalance(BigDecimal debitBalance) {
		this.debitBalance = debitBalance;
	}

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	@Override
	public String toString() {
		return "AccountOutput [acctingDate=" + acctingDate + ", acctingSerial="
				+ acctingSerial + ", creditBalance=" + creditBalance
				+ ", debitBalance=" + debitBalance + "]";
	}
	

}
