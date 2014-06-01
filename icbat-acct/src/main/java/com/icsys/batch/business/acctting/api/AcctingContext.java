package com.icsys.batch.business.acctting.api;

import java.math.BigDecimal;

import com.icsys.batch.business.acct.api.CDFlag;

/**
 * 记账信息
 * 
 * @author kittyuu
 * 
 */
public class AcctingContext {
	/**
	 * 账号
	 */
	private String acctNO;

	/**
	 * 借贷标志
	 */
	private CDFlag acctingCD;

	/**
	 * 记账金额
	 */
	private BigDecimal amount;

	/**
	 * 获取账号
	 * 
	 * @return 账号
	 */
	public String getAcctNO() {
		return acctNO;
	}

	/**
	 * 设置账号
	 * 
	 * @param acctNO
	 *            账号
	 */
	public void setAcctNO(String acctNO) {
		this.acctNO = acctNO;
	}

	/**
	 * 获取借贷标志
	 * 
	 * @return 借贷标志
	 */
	public CDFlag getAcctingCD() {
		return acctingCD;
	}

	/**
	 * 设置借贷标志
	 * 
	 * @param acctingCD
	 *            借贷标志
	 */
	public void setAcctingCD(CDFlag acctingCD) {
		this.acctingCD = acctingCD;
	}

	/**
	 * 设置记账金额
	 * 
	 * @return 记账金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置记账金额
	 * 
	 * @param amount
	 *            记账金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "AccountContext [acctNO=" + acctNO + ", acctingCD=" + acctingCD
				+ ", amount=" + amount + "]";
	}
}
