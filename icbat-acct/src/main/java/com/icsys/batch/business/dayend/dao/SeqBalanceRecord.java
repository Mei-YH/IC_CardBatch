package com.icsys.batch.business.dayend.dao;

import java.math.BigDecimal;

public class SeqBalanceRecord {
	/**
	 * 轧账日期
	 */
	private String rollOffDate;
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

	public BigDecimal getCalBalance() {
		return calBalance;
	}

	public void setCalBalance(BigDecimal calBalance) {
		this.calBalance = calBalance;
	}

	@Override
	public String toString() {
		return "SeqBalanceRecord [acctNO=" + acctNO + ", balance=" + balance
				+ ", balanceCD=" + balanceCD + ", calBalance=" + calBalance
				+ ", lastDayBalance=" + lastDayBalance + ", rollOffDate="
				+ rollOffDate + "]";
	}

}
