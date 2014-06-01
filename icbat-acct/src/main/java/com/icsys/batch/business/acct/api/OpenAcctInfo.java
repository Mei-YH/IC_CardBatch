package com.icsys.batch.business.acct.api;

import java.math.BigDecimal;

import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.platform.util.DateUtils;

/**
 * 开户用的一些基本信息
 * 
 * @author wangzheng
 * 
 */
public class OpenAcctInfo {

	public static final int FIXED_RATE = 0;

	/** 开户机构 */
	private String branchCode;

	/** 科目代码 */
	private String subjectCode;

	/** 货币代码 */
	private String currencyType;

	/** 账户名称 */
	private String subAcctName;

	/** 账户种类 */
	private String acctClass;

	/** 利息计算模式 */
	private int interestType;

	/** 开户利率 */
	private BigDecimal fixedInterest;

	/** 操作员 */
	private String operator;

	/**
	 * 获取开户机构
	 * 
	 * @return 开户机构
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * 设置开户机构
	 * 
	 * @param branchCode
	 *            开户机构
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * 获取科目代码
	 * 
	 * @return 科目代码
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * 设置科目代码
	 * 
	 * @param subjectCode
	 *            科目代码
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * 获取币种
	 * 
	 * @return 币种
	 */
	public String getCurrencyType() {
		return currencyType;
	}

	/**
	 * 设置币种
	 * 
	 * @param currencyType
	 *            币种
	 */
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * 获取账户名称
	 * 
	 * @return 账户名称
	 */
	public String getSubAcctName() {
		return subAcctName;
	}

	/**
	 * 设置账户名称
	 * 
	 * @param subAcctName
	 *            账户名称
	 */
	public void setSubAcctName(String subAcctName) {
		this.subAcctName = subAcctName;
	}

	/**
	 * 获取账户种类
	 * 
	 * @return 账户种类
	 */
	public String getAcctClass() {
		return acctClass;
	}

	/**
	 * 设置账户种类
	 * 
	 * @param acctClass
	 *            账户种类
	 */
	public void setAcctClass(String acctClass) {
		this.acctClass = acctClass;
	}

	/**
	 * 获取利息计算模式
	 * 
	 * @return 利息计算模式
	 */
	public int getInterestType() {
		return interestType;
	}

	/**
	 * 设置利息计算模式
	 * 
	 * @param interestType
	 *            利息计算模式
	 */
	public void setInterestType(int interestType) {
		this.interestType = interestType;
	}

	/**
	 * 获取开户利率
	 * 
	 * @return 开户利率
	 */
	public BigDecimal getFixedInterest() {
		return fixedInterest;
	}

	/**
	 * 设置开户利率
	 * 
	 * @param fixedInterest
	 *            开户利率
	 */
	public void setFixedInterest(BigDecimal fixedInterest) {
		this.fixedInterest = fixedInterest;
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

	@Override
	public String toString() {
		return "OpenAcctInfo [acctClass=" + acctClass + ", branchCode="
				+ branchCode + ", currencyType=" + currencyType
				+ ", fixedInterest=" + fixedInterest + ", interestType="
				+ interestType + ", operator=" + operator + ", subAcctName="
				+ subAcctName + ", subjectCode=" + subjectCode + "]";
	}

	/**
	 * 输入信息转账户主档信息
	 * 
	 * @return 账户主档信息
	 */
	public Account toAccount() {
		Account acct = new Account();
		acct.setAccName(this.subAcctName);
		acct.setAccStatus(AcctStatus.COMMON.getIntValue());
		// 获取系统记账日期设为账户开户日期
		String sysDate = null;
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam)
			sysDate = DateUtils.getCurrentDate();
		else
			sysDate = accSysParam.getAcctingDate();
		acct.setAccOpendate(sysDate);
		acct.setAccOpteller(this.operator);
		acct.setAccIntmode(this.interestType);
		acct.setAccIntrate(this.getFixedInterest());
		acct.setSubjectCode(this.subjectCode);
		acct.setCurrencyCode(this.currencyType);
		acct.setAccOpbranch(this.branchCode);
		acct.setAppId(this.acctClass);
		return acct;
	}

	/**
	 * 合法性校验
	 * 
	 * @return 合法性校验信息
	 */
	public String validate() {
		if (this.currencyType == null || this.currencyType.length() != 3) {
			return "货币代码必须输入，并且为3位长";
		}
		if (this.subjectCode == null || this.subjectCode.length() != 4) {
			return "科目号必须输入，并且为4位长";
		}
		if (this.getBranchCode() == null
				|| this.getBranchCode().trim().equals("")
				|| this.branchCode.length() > 11) {
			return "机构代码不能为空，并且长度不能超过11位";
		}
		if (this.subAcctName == null
				|| this.subAcctName.trim().equals("")
				|| this.subAcctName.length() > 40) {
			return "账户名称不能为空，并且长度不能超过40位";
		}
		if (null != this.acctClass && this.acctClass.length() > 16) {
			return "账户种类长度不能超过16位";
		}
		if (null != this.operator && this.operator.length() > 15) {
			return "操作员代码长度不能超过15位";
		}
		return "";
	}

}
