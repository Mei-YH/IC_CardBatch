package com.icsys.batch.task.sleepAcctManage.proxy.bean;

import java.math.BigDecimal;

public class GrAccAcct {
	private String accNo;//帐号
	private String appId;//账户种类
	private String accName;//账户名称
	private String accType;//账户类型
	private String currencyCode;//货币代码
	private String subjectCode;//核算科目代码
	private String accOpendate;//启用日期
	private String accOpbranch;//启用机构
	private String accOpteller;//启用操作员
	private BigDecimal accYesbal;//昨日余额
	private BigDecimal accBalance;//账户余额
	private BigDecimal accStpayamount;//止付余额
	private BigDecimal accAbbalance;//可用余额
	private BigDecimal accIntbase;//余额积数
	private Integer accIntmode;//计算利息方式
	private BigDecimal accIntrate;//约定固定利率
	private String accltdate;//最后交易日期
	private String accCandate;//注销日期
	private String accCanteller;//注销操作员
	private String accConflag;//账户控制标志
	private String accBalcd;//账户余额标志
	private String accStatus;//账户状态
	private String mac;//检验位
	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getAccOpendate() {
		return accOpendate;
	}

	public void setAccOpendate(String accOpendate) {
		this.accOpendate = accOpendate;
	}

	public String getAccOpbranch() {
		return accOpbranch;
	}

	public void setAccOpbranch(String accOpbranch) {
		this.accOpbranch = accOpbranch;
	}

	public String getAccOpteller() {
		return accOpteller;
	}

	public void setAccOpteller(String accOpteller) {
		this.accOpteller = accOpteller;
	}

	public BigDecimal getAccYesbal() {
		return accYesbal;
	}

	public void setAccYesbal(BigDecimal accYesbal) {
		this.accYesbal = accYesbal;
	}

	public BigDecimal getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(BigDecimal accBalance) {
		this.accBalance = accBalance;
	}

	public BigDecimal getAccStpayamount() {
		return accStpayamount;
	}

	public void setAccStpayamount(BigDecimal accStpayamount) {
		this.accStpayamount = accStpayamount;
	}

	public BigDecimal getAccAbbalance() {
		return accAbbalance;
	}

	public void setAccAbbalance(BigDecimal accAbbalance) {
		this.accAbbalance = accAbbalance;
	}

	public BigDecimal getAccIntbase() {
		return accIntbase;
	}

	public void setAccIntbase(BigDecimal accIntbase) {
		this.accIntbase = accIntbase;
	}

	public Integer getAccIntmode() {
		return accIntmode;
	}

	public void setAccIntmode(Integer accIntmode) {
		this.accIntmode = accIntmode;
	}

	public BigDecimal getAccIntrate() {
		return accIntrate;
	}

	public void setAccIntrate(BigDecimal accIntrate) {
		this.accIntrate = accIntrate;
	}

	public String getAccltdate() {
		return accltdate;
	}

	public void setAccltdate(String accltdate) {
		this.accltdate = accltdate;
	}

	public String getAccCandate() {
		return accCandate;
	}

	public void setAccCandate(String accCandate) {
		this.accCandate = accCandate;
	}

	public String getAccCanteller() {
		return accCanteller;
	}

	public void setAccCanteller(String accCanteller) {
		this.accCanteller = accCanteller;
	}

	public String getAccConflag() {
		return accConflag;
	}

	public void setAccConflag(String accConflag) {
		this.accConflag = accConflag;
	}

	public String getAccBalcd() {
		return accBalcd;
	}

	public void setAccBalcd(String accBalcd) {
		this.accBalcd = accBalcd;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
