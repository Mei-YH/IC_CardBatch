package com.icsys.batch.business.acct.api;

import java.math.BigDecimal;

/**
 * 结清账户输出类
 * 
 * @author kittyuu
 * 
 */
public class CloseAcctOutput {
	/**
	 * 记账流水号
	 */
	private String serial;

	/**
	 * 记账日期
	 */
	private String acctingDate;

	/**
	 * 结清余额
	 */
	private BigDecimal closeBalance;

	/**
	 * 对方账户交易后余额
	 */
	private BigDecimal otherBalance;

	/**
	 * 获取记账流水号
	 * 
	 * @return 记账流水号
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * 设置记账流水号
	 * 
	 * @param serial
	 *            记账流水号
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * 获取记账日期
	 * 
	 * @return 记账日期
	 */
	public String getAcctingDate() {
		return acctingDate;
	}

	/**
	 * 设置记账日期
	 * 
	 * @param acctingDate
	 *            记账日期
	 */
	public void setAcctingDate(String acctingDate) {
		this.acctingDate = acctingDate;
	}

	/**
	 * 获取结清余额
	 * 
	 * @return 结清余额
	 */
	public BigDecimal getCloseBalance() {
		return closeBalance;
	}

	/**
	 * 设置结清余额
	 * 
	 * @param closeBalance
	 *            结清余额
	 */
	public void setCloseBalance(BigDecimal closeBalance) {
		this.closeBalance = closeBalance;
	}

	/**
	 * 获取对方账户交易后余额
	 * 
	 * @return 对方账户交易后余额
	 */
	public BigDecimal getOtherBalance() {
		return otherBalance;
	}

	/**
	 * 设置对方账户交易后余额
	 * 
	 * @param otherBalance
	 *            对方账户交易后余额
	 */
	public void setOtherBalance(BigDecimal otherBalance) {
		this.otherBalance = otherBalance;
	}

	@Override
	public String toString() {
		return "CloseAcctOutput [acctingDate=" + acctingDate
				+ ", closeBalance=" + closeBalance + ", otherBalance="
				+ otherBalance + ", serial=" + serial + "]";
	}

}
