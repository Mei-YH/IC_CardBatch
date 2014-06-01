package com.icsys.batch.business.acct.api;

public class AcctSubject {
	private String currType; // 币种代码

	private String subjectCode; // 科目代码

	private String description; // 科目描述

	private String subCode1; // 一级科目号

	private String subCode2; // 二级科目号

	private String subCode3; // 三级科目号

	private Integer acctingType; // 账务类型

	private Integer balanceCD; // 余额方向

	private String parentSubCode; // 父科目代码

	private String sumSubCode; // 汇总科目代码

	private String subControlFlag; // 科目控制标志

	/**
	 * 获取科目描述
	 * 
	 * @return 科目描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置科目描述
	 * 
	 * @param description
	 *            科目描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取币种
	 * 
	 * @return 币种
	 */
	public String getCurrType() {
		return currType;
	}

	/**
	 * 设置币种
	 * 
	 * @param currType
	 *            币种
	 */
	public void setCurrType(String currType) {
		this.currType = currType;
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
	 * 获取一级科目代码
	 * 
	 * @return 一级科目代码
	 */
	public String getSubCode1() {
		return subCode1;
	}

	/**
	 * 设置一级科目代码
	 * 
	 * @param subCode1
	 *            一级科目代码
	 */
	public void setSubCode1(String subCode1) {
		this.subCode1 = subCode1;
	}

	/**
	 * 获取二级科目代码
	 * 
	 * @return 二级科目代码
	 */
	public String getSubCode2() {
		return subCode2;
	}

	/**
	 * 设置二级科目代码
	 * 
	 * @param subCode2
	 *            二级科目代码
	 */
	public void setSubCode2(String subCode2) {
		this.subCode2 = subCode2;
	}

	/**
	 * 获取三级科目代码
	 * 
	 * @return 三级科目代码
	 */
	public String getSubCode3() {
		return subCode3;
	}

	/**
	 * 设置三级科目代码
	 * 
	 * @param subCode3
	 *            三级科目代码
	 */
	public void setSubCode3(String subCode3) {
		this.subCode3 = subCode3;
	}

	/**
	 * 获取账务类型
	 * 
	 * @return 账务类型
	 */
	public Integer getAcctingType() {
		return acctingType;
	}

	/**
	 * 设置账务类型
	 * 
	 * @param acctingType
	 *            账务类型
	 */
	public void setAcctingType(Integer acctingType) {
		this.acctingType = acctingType;
	}

	/**
	 * 获取余额方向
	 * 
	 * @return 余额方向
	 */
	public Integer getBalanceCD() {
		return balanceCD;
	}

	/**
	 * 设置余额方向
	 * 
	 * @param balanceCD
	 *            余额方向
	 */
	public void setBalanceCD(Integer balanceCD) {
		this.balanceCD = balanceCD;
	}

	/**
	 * 获取父科目代码
	 * 
	 * @return 父科目代码
	 */
	public String getParentSubCode() {
		return parentSubCode;
	}

	/**
	 * 设置父科目代码
	 * 
	 * @param parentSubCode
	 *            父科目代码
	 */
	public void setParentSubCode(String parentSubCode) {
		this.parentSubCode = parentSubCode;
	}

	/**
	 * 获取科目控制标志
	 * 
	 * @return 科目控制标志
	 */
	public String getSubControlFlag() {
		return subControlFlag;
	}

	/**
	 * 设置科目控制标志
	 * 
	 * @param subControlFlag
	 *            科目控制标志
	 */
	public void setSubControlFlag(String subControlFlag) {
		this.subControlFlag = subControlFlag;
	}

	/**
	 * 获取汇总科目代码
	 * 
	 * @return 汇总科目代码
	 */
	public String getSumSubCode() {
		return sumSubCode;
	}

	/**
	 * 设置汇总科目代码
	 * 
	 * @param sumSubCode
	 *            汇总科目代码
	 */
	public void setSumSubCode(String sumSubCode) {
		this.sumSubCode = sumSubCode;
	}

	@Override
	public String toString() {
		return "AcctSubject [acctingType=" + acctingType + ", balanceCD="
				+ balanceCD + ", currType=" + currType + ", description="
				+ description + ", parentSubCode=" + parentSubCode
				+ ", subCode1=" + subCode1 + ", subCode2=" + subCode2
				+ ", subCode3=" + subCode3 + ", subControlFlag="
				+ subControlFlag + ", subjectCode=" + subjectCode
				+ ", sumSubCode=" + sumSubCode + "]";
	}

	/**
	 * 该科目是否允许开户
	 * 
	 * @return True代表允许开户，False代表不允许开户
	 */
	public boolean isOpenAcctAllowable() {
		return AcctSubject.STATUS.STOP_OPEN_ACCT.getValue() != this.subControlFlag
				.charAt(0);
	}

	/**
	 * 是否允许透支
	 * 
	 * @return True代表允许透支，False代表不允许透支
	 */
	public boolean isOverdrawingAllowable() {
		return AcctSubject.OVERDRAWFLAG.ALLOWABLE.getValue() == this.subControlFlag
				.charAt(0);
	}

	/**
	 * 状态是否正常
	 * 
	 * @return True代表状态正常，False代表非正常状态
	 */
	public boolean isStatusNormal() {
		return AcctSubject.STATUS.CANCEL.getValue() != this.subControlFlag
				.charAt(0);
	}

	/**
	 * 科目状态
	 * 
	 * @author wangzheng
	 * 
	 */
	public static enum STATUS {
		NORMAL(0), // 正常
		STOP_OPEN_ACCT(1), // 不能开户
		CANCEL(9);// 注销
		private int flag;

		STATUS(int value) {
			this.flag = value;
		}

		public int getValue() {
			return this.flag;
		}
	}

	/**
	 * 账务类型
	 * 
	 * @author wangzheng
	 * 
	 */
	public static enum ACCTINGTYPE {

		EXTERNAL(0), // 表外
		ECASH(1), // 电子现金
		IN_CARD_SPECIFIC(2), // 卡内专用
		DEBIT(4), // 借记主账户
		CREDIT(5), // 贷记主账户
		DEBT(6), // 负债类
		CAPITAL(7), // 资产类
		PANDL(8); // 损益类
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private ACCTINGTYPE(int value) {
			this.value = value;
		}

	}

	/**
	 * 子账类
	 * 
	 * @author wangzheng
	 * 
	 */
	public static enum SUBACCTINGTYPE {

		GENERAL(0), // 通用科目
		ECASH(1); // 电子现金专用科目

		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private SUBACCTINGTYPE(int value) {
			this.value = value;
		}

	}

	/**
	 * 透支标志
	 * 
	 * @author kitty
	 * 
	 */
	public static enum OVERDRAWFLAG {

		ALLOWABLE(1), // 允许透支
		NOT_ALLOWABLE(0); // 不允须透支

		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private OVERDRAWFLAG(int value) {
			this.value = value;
		}

	}
}
