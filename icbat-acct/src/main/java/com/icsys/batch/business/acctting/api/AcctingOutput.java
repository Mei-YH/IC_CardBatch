package com.icsys.batch.business.acctting.api;

import java.math.BigDecimal;
import java.util.List;

/**
 * 记账输出信息
 * 
 * @author kitty
 * 
 */
public class AcctingOutput {

	/**
	 * 记账流水号
	 */
	private String serial;

	/**
	 * 记账日期
	 */
	private String acctingDate;

	/**
	 * 账户余额
	 */
	private List<BigDecimal> balanceList;

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
	 * 获取账户余额
	 * 
	 * @return 账户余额列表
	 */
	public List<BigDecimal> getBalanceList() {
		return balanceList;
	}

	/**
	 * 设置账户余额
	 * 
	 * @param balanceList
	 *            账户余额列表
	 */
	public void setBalanceList(List<BigDecimal> balanceList) {
		this.balanceList = balanceList;
	}

	@Override
	public String toString() {
		return "AcctingOutput [acctingDate=" + acctingDate + ", balanceList="
				+ balanceList + ", serial=" + serial + "]";
	}

}
