package com.icsys.batch.business.dayend.dao;


/**
 * 对应到科目字典表ic_subdict
 * 
 * @author kittyuu
 * 
 */
public class Dictionary {

	/**
	 * 币种
	 */
	private String currType;
	
	/**
	 * 科目代码
	 */
	private String subjectCode;
	
	/**
	 * 科目描述
	 */
	private String description;
	
	/**
	 * 一级科目号
	 */
	private String subjectCode1;
	
	/**
	 * 二级科目号
	 */
	private String subjectCode2;
	
	/**
	 * 三级科目号
	 */
	private String subjectCode3;
	
	/**
	 * 账类
	 */
	private String acctingType;
	
	/**
	 * 余额方向
	 */
	private String balanceCD;
	
	/**
	 * 父科目代码
	 */
	private String parentSubCode;
	
	/**
	 * 汇总科目代码
	 */
	private String sumSubCode;
	
	/**
	 * 科目控制标志
	 */
	private String subControlFlag;

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubjectCode1() {
		return subjectCode1;
	}

	public void setSubjectCode1(String subjectCode1) {
		this.subjectCode1 = subjectCode1;
	}

	public String getSubjectCode2() {
		return subjectCode2;
	}

	public void setSubjectCode2(String subjectCode2) {
		this.subjectCode2 = subjectCode2;
	}

	public String getSubjectCode3() {
		return subjectCode3;
	}

	public void setSubjectCode3(String subjectCode3) {
		this.subjectCode3 = subjectCode3;
	}

	public String getAcctingType() {
		return acctingType;
	}

	public void setAcctingType(String acctingType) {
		this.acctingType = acctingType;
	}

	public String getBalanceCD() {
		return balanceCD;
	}

	public void setBalanceCD(String balanceCD) {
		this.balanceCD = balanceCD;
	}

	public String getParentSubCode() {
		return parentSubCode;
	}

	public void setParentSubCode(String parentSubCode) {
		this.parentSubCode = parentSubCode;
	}

	public String getSumSubCode() {
		return sumSubCode;
	}

	public void setSumSubCode(String sumSubCode) {
		this.sumSubCode = sumSubCode;
	}

	public String getSubControlFlag() {
		return subControlFlag;
	}

	public void setSubControlFlag(String subControlFlag) {
		this.subControlFlag = subControlFlag;
	}

	@Override
	public String toString() {
		return "Dictionary [acctingType=" + acctingType + ", balanceCD="
				+ balanceCD + ", currType=" + currType + ", description="
				+ description + ", parentSubCode=" + parentSubCode
				+ ", subControlFlag=" + subControlFlag + ", subjectCode="
				+ subjectCode + ", subjectCode1=" + subjectCode1
				+ ", subjectCode2=" + subjectCode2 + ", subjectCode3="
				+ subjectCode3 + ", sumSubCode=" + sumSubCode + "]";
	}
	
}
