package com.icsys.batch.business.acctting.impl;

import java.math.BigDecimal;

/**
 * 对应分录流水表
 * 
 * @author kittyuu
 * 
 */
public class AcctingList {
	/**
	 * 记账流水号
	 */
	private String serial;
	/**
	 * 交易流水号
	 */
	private String tranSerial;
	/**
	 * 原记账流水号
	 */
	private String orgSerial;
	/**
	 * 分录序号
	 */
	private Integer sn;
	/**
	 * 账号
	 */
	private String acctNO;
	/**
	 * 借贷标志
	 */
	private Integer acctCD;

	/**
	 * 科目代码
	 */
	private String subjectCode;
	/**
	 * 核算货币
	 */
	private String currType;

	/**
	 * 账务机构(对应主档表中的开户机构)
	 */
	private String acctingBranch;

	/**
	 * 发生机构
	 */
	private String tranBranch;
	/**
	 * 账务日期
	 */
	private String acctingDate;

	/**
	 * 柜员
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
	 * 流水标志0-正常，1-冲正
	 */
	private String serialFlag = "0";

	public String getTranBranch() {
		return tranBranch;
	}

	public void setTranBranch(String tranBranch) {
		this.tranBranch = tranBranch;
	}

	public String getSerialFlag() {
		return serialFlag;
	}

	public void setSerialFlag(String serialFlag) {
		this.serialFlag = serialFlag;
	}

	public Integer getAcctCD() {
		return acctCD;
	}

	public void setAcctCD(Integer acctCD) {
		this.acctCD = acctCD;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOrgSerial() {
		return orgSerial;
	}

	public void setOrgSerial(String orgSerial) {
		this.orgSerial = orgSerial;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getAcctNO() {
		return acctNO;
	}

	public void setAcctNO(String acctNO) {
		this.acctNO = acctNO;
	}

	public String getTranSerial() {
		return tranSerial;
	}

	public void setTranSerial(String tranSerial) {
		this.tranSerial = tranSerial;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectNO) {
		this.subjectCode = subjectNO;
	}

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public String getAcctingBranch() {
		return acctingBranch;
	}

	public void setAcctingBranch(String acctingBranch) {
		this.acctingBranch = acctingBranch;
	}

	public String getAcctingDate() {
		return acctingDate;
	}

	public void setAcctingDate(String acctingDate) {
		this.acctingDate = acctingDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serial == null) ? 0 : serial.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcctingList other = (AcctingList) obj;
		if (serial == null) {
			if (other.serial != null)
				return false;
		} else if (!serial.equals(other.serial))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}

}
