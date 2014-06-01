package com.icsys.batch.business.acctting.api;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;

import com.icsys.batch.business.acct.api.CDFlag;
import com.icsys.batch.business.acctting.impl.InnerAcctingInput;

/**
 * 标准借贷记记账输入
 * 
 * @author wangzheng
 * 
 */
/**
 * @author kitty
 * 
 */
public class CDAcctingInput {
	/**
	 * 交易流水号
	 */
	private String tranSerial;

	/**
	 * 原记账流水号
	 */
	private String orgSerial;

	/**
	 * 借方账号
	 */
	private String debitAcctNO;

	/**
	 * 贷方账号
	 */
	private String creditAcctNO;

	/**
	 * 发生机构
	 */
	private String tranBranch;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 记账金额
	 */
	private BigDecimal amount;

	/**
	 * 摘要
	 */
	private String remark;

	/**
	 * 获取交易流水号
	 * 
	 * @return 交易流水号
	 */
	public String getTranSerial() {
		return tranSerial;
	}

	/**
	 * 设置交易流水号
	 * 
	 * @param tranSerial
	 *            交易流水号
	 */
	public void setTranSerial(String tranSerial) {
		this.tranSerial = tranSerial;
	}

	/**
	 * 获取原记账流水号
	 * 
	 * @return 原记账流水号
	 */
	public String getOrgSerial() {
		return orgSerial;
	}

	/**
	 * 设置原记账流水号
	 * 
	 * @param orgSerial
	 *            原记账流水号
	 */
	public void setOrgSerial(String orgSerial) {
		this.orgSerial = orgSerial;
	}

	/**
	 * 获取借方账号
	 * 
	 * @return 借方账号
	 */
	public String getDebitAcctNO() {
		return debitAcctNO;
	}

	/**
	 * 设置借方账号
	 * 
	 * @param debitAcctNO
	 *            借方账号
	 */
	public void setDebitAcctNO(String debitAcctNO) {
		this.debitAcctNO = debitAcctNO;
	}

	/**
	 * 获取贷方账号
	 * 
	 * @return 贷方账号
	 */
	public String getCreditAcctNO() {
		return creditAcctNO;
	}

	/**
	 * 设置贷方账号
	 * 
	 * @param creditAcctNO
	 *            贷方账号
	 */
	public void setCreditAcctNO(String creditAcctNO) {
		this.creditAcctNO = creditAcctNO;
	}

	/**
	 * 获取发生机构
	 * 
	 * @return 发生机构
	 */
	public String getTranBranch() {
		return tranBranch;
	}

	/**
	 * 设置发生机构
	 * 
	 * @param tranBranch
	 *            发生机构
	 */
	public void setTranBranch(String tranBranch) {
		this.tranBranch = tranBranch;
	}

	/**
	 * 获取操作员
	 * 
	 * @return 操作员
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取记账金额
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

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 * 
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 输入信息合法性校验
	 * 
	 * @return 合法性校验信息
	 */
	public String validate() {
		if (null == this.tranBranch
				|| this.tranBranch.trim().equals("")
				|| this.tranBranch.length() > 11) {
			return "机构代码不能为空，并且长度不能超过11位";
		}
		if (null == this.debitAcctNO
				|| this.debitAcctNO.trim().equals("")
				|| this.debitAcctNO.length() > 32) {
			return "借方账号不能为空，并且长度不能超过32位";
		}
		if (null == this.creditAcctNO
				|| this.creditAcctNO.trim().equals("")
				|| this.creditAcctNO.length() > 32) {
			return "贷方账号不能为空，并且长度不能超过32位";
		}
		if (null == this.operator
				|| this.operator.trim().equals("")
				|| this.operator.length() > 15) {
			return "操作员不能为空，并且长度不能超过15位";
		}
		if (null == this.getAmount())
			return "记账金额不能为空";
		return "";
	}

	/**
	 * 输入信息拆分
	 * 
	 * @return 借方信息和贷方信息
	 */
	public InnerAcctingInput[] split() {
		try {
			InnerAcctingInput debitAL = new InnerAcctingInput();
			BeanUtils.copyProperties(debitAL, this);
			debitAL.setAcctNO(this.debitAcctNO);
			debitAL.setAcctingCD(CDFlag.DEBIT);

			InnerAcctingInput creditAL = new InnerAcctingInput();
			BeanUtils.copyProperties(creditAL, this);
			creditAL.setAcctNO(this.creditAcctNO);
			creditAL.setAcctingCD(CDFlag.CREDIT);

			return new InnerAcctingInput[] { debitAL, creditAL };
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "CDAcctingInput [amount=" + amount + ", creditAcctNO="
				+ creditAcctNO + ", debitAcctNO=" + debitAcctNO + ", operator="
				+ operator + ", orgSerial=" + orgSerial + ", remark=" + remark
				+ ", tranBranch=" + tranBranch + ", tranSerial=" + tranSerial
				+ "]";
	}
}
