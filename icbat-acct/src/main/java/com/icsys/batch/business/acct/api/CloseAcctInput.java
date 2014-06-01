package com.icsys.batch.business.acct.api;

/**
 * 结清账户输入类
 * 
 * @author kittyuu
 * 
 */
public class CloseAcctInput {
	/**
	 * 交易流水号
	 */
	private String tranSerial;
	/**
	 * 记账机构
	 */
	private String tranBranch;

	/**
	 * 结清账号
	 */
	private String closeAcctNO;
	/**
	 * 对方账号
	 */
	private String otherAcctNO;
	/**
	 * 操作员
	 */
	private String operator;

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
	 * 获取记账机构
	 * 
	 * @return 记账机构
	 */
	public String getTranBranch() {
		return tranBranch;
	}

	/**
	 * 设置记账机构
	 * 
	 * @param tranBranch
	 *            记账机构
	 */
	public void setTranBranch(String tranBranch) {
		this.tranBranch = tranBranch;
	}

	/**
	 * 获取结清账户
	 * 
	 * @return 结清账户
	 */
	public String getCloseAcctNO() {
		return closeAcctNO;
	}

	/**
	 * 设置结清账户
	 * 
	 * @param closeAcctNO
	 *            结清账户
	 */
	public void setCloseAcctNO(String closeAcctNO) {
		this.closeAcctNO = closeAcctNO;
	}

	/**
	 * 获取对方账户
	 * 
	 * @return 对方账户
	 */
	public String getOtherAcctNO() {
		return otherAcctNO;
	}

	/**
	 * 设置对方账户
	 * 
	 * @param otherAcctNO
	 *            对方账户
	 */
	public void setOtherAcctNO(String otherAcctNO) {
		this.otherAcctNO = otherAcctNO;
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

	@Override
	public String toString() {
		return "CloseAcctInput [closeAcctNO=" + closeAcctNO + ", operator="
				+ operator + ", otherAcctNO=" + otherAcctNO + ", remark="
				+ remark + ", tranBranch=" + tranBranch + ", tranSerial="
				+ tranSerial + "]";
	}

	/**
	 * 输入信息合法性校验
	 * 
	 * @return 合法性校验信息
	 */
	public String validate() {
		if (this.closeAcctNO == null || this.closeAcctNO.trim().equals("")) {
			return "结清账号必须输入";
		}
		if (this.otherAcctNO == null || this.otherAcctNO.trim().equals("")) {
			return "对方账号必须输入";
		}
		if (this.tranBranch == null
				|| this.tranBranch.trim().equals("")) {
			return "机构代码不能为空";
		}
		return "";
	}

}
