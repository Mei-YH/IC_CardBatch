package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

/**
 * 对应动户余额快照表
 * 
 * @author kittyuu
 * 
 */
public class ChangeAccount {

	/**
	 * 账号
	 */
	private String acctNO;
	/**
	 * 前日余额
	 */
	private BigDecimal lastDayBalance;
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 余额方向
	 */
	private String balanceCD;
	public String getAcctNO() {
		return acctNO;
	}
	public void setAcctNO(String acctNO) {
		this.acctNO = acctNO;
	}
	public BigDecimal getLastDayBalance() {
		return lastDayBalance;
	}
	public void setLastDayBalance(BigDecimal lastDayBalance) {
		this.lastDayBalance = lastDayBalance;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getBalanceCD() {
		return balanceCD;
	}
	public void setBalanceCD(String balanceCD) {
		this.balanceCD = balanceCD;
	}
	@Override
	public String toString() {
		return "ChangeAccount [acctNO=" + acctNO + ", balance=" + balance
				+ ", balanceCD=" + balanceCD + ", lastDayBalance="
				+ lastDayBalance + "]";
	}

}
