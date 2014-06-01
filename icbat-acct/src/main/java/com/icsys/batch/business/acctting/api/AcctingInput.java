package com.icsys.batch.business.acctting.api;

import java.util.List;

/**
 * 分录流水信息
 * 
 * @author kitty
 * 
 */
public class AcctingInput {

	/**
	 * 交易流水号
	 */
	private String tranSerial;
	/**
	 * 原流水号
	 */
	private String orgSerial;

	/**
	 * 发生机构
	 */
	private String tranBranch;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 摘要
	 */
	private String remark;

	/**
	 * 多借多贷信息
	 */
	private List<AcctingContext> acctingContext;

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
	 * 获取原流水号
	 * 
	 * @return 原流水号
	 */
	public String getOrgSerial() {
		return orgSerial;
	}

	/**
	 * 设置原流水号
	 * 
	 * @param orgSerial
	 *            原流水号
	 */
	public void setOrgSerial(String orgSerial) {
		this.orgSerial = orgSerial;
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
	 * 获取记账信息列表
	 * 
	 * @return 记账信息列表
	 */
	public List<AcctingContext> getAcctingContext() {
		return acctingContext;
	}

	/**
	 * 设置记账信息
	 * 
	 * @param acctingContext
	 *            记账信息列表
	 */
	public void setAcctingContext(List<AcctingContext> acctingContext) {
		this.acctingContext = acctingContext;
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
		if (null == this.operator
				|| this.operator.trim().equals("")
				|| this.operator.length() > 15) {
			return "操作员不能为空，并且长度不能超过15位";
		}
		if (null == this.acctingContext || this.acctingContext.size() == 0) {
			return "借贷信息列表不能为空";
		}
		for (AcctingContext ac : this.acctingContext) {
			if (null == ac.getAcctingCD())
				return "借贷标志不能为空";
			if (null == ac.getAcctNO() || ac.getAcctNO().trim().equals("")) {
				return "账号不能为空";
			}
			if (null == ac.getAmount())
				return "记账金额不能为空";
		}
		return "";
	}
}
