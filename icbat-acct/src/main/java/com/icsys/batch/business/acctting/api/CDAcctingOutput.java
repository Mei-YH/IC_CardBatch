package com.icsys.batch.business.acctting.api;

import java.math.BigDecimal;

/**
 * 标准借贷记记账输出信息
 * 
 * @author kitty
 * 
 */
public class CDAcctingOutput {

	/**
	 * 记账流水号
	 */
	private String serial;

	/**
	 * 记账日期
	 */
	private String acctingDate;

	/**
	 * 实际记账金额
	 */
	private BigDecimal acctingAmount;

	/**
	 * 拟透支金额
	 */
	private BigDecimal overdrawingAmount;

	/**
	 * 借方账户余额
	 */
	private BigDecimal debitBalance;

	/**
	 * 贷方账户余额
	 */
	private BigDecimal creditBalance;

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
	 * 获取实际记账金额
	 * 
	 * @return 实际记账金额
	 */
	public BigDecimal getAcctingAmount() {
		return acctingAmount;
	}

	/**
	 * 设置实际记账金额
	 * 
	 * @param acctingAmount
	 *            实际记账金额
	 */
	public void setAcctingAmount(BigDecimal acctingAmount) {
		this.acctingAmount = acctingAmount;
	}

	/**
	 * 获取拟透支金额
	 * 
	 * @return 拟透支金额
	 */
	public BigDecimal getOverdrawingAmount() {
		return overdrawingAmount;
	}

	/**
	 * 设置拟透支金额
	 * 
	 * @param overdrawingAmount
	 *            拟透支金额
	 */
	public void setOverdrawingAmount(BigDecimal overdrawingAmount) {
		this.overdrawingAmount = overdrawingAmount;
	}

	/**
	 * 获取借方账户余额
	 * 
	 * @return 借方账户余额
	 */
	public BigDecimal getDebitBalance() {
		return debitBalance;
	}

	/**
	 * 设置借方账户余额
	 * 
	 * @param debitBalance
	 *            借方账户余额
	 */
	public void setDebitBalance(BigDecimal debitBalance) {
		this.debitBalance = debitBalance;
	}

	/**
	 * 获取贷方账户余额
	 * 
	 * @return 贷方账户余额
	 */
	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	/**
	 * 设置贷方账户余额
	 * 
	 * @param creditBalance
	 *            贷方账户余额
	 */
	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	@Override
	public String toString() {
		return "CDAcctingOutput [acctingAmount=" + acctingAmount
				+ ", acctingDate=" + acctingDate + ", creditBalance="
				+ creditBalance + ", debitBalance=" + debitBalance
				+ ", overdrawingAmount=" + overdrawingAmount + ", serial="
				+ serial + "]";
	}

}
