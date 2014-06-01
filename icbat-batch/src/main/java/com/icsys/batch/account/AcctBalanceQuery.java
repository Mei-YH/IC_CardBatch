package com.icsys.batch.account;

import java.math.BigDecimal;

public class AcctBalanceQuery {

	/**
     * 账号（账户标识）
     */
    private String acctNO;
    
    
    /**
     * 账户种类
     */
    private String acctClass;
    
    /**
     * 子账户名称
     */
    private String acctName;
    
   /**
    * 账户类型
    */
    private String acctType;
    
    
    /**
     * 币别代码
     */
    private String currType;
    /**
     * 科目号
     */
    private String subjectCode;  
    
    /**
     * 启用日期
     */
    private String openDate;
    
    /**
     * 启用机构
     */
    private String openBranch;
    
    /**
     * 启用操作员
     */
    private String openTeller;
    /**
     * 前日余额
     */
    private BigDecimal lastDayBalance = new BigDecimal(0.00);
    
    /**
     * 账户余额
     */
    private BigDecimal balance= new BigDecimal(0.00);
    
    /**
     * 止付金额
     */
    private BigDecimal stopPayAmount= new BigDecimal(0.00);
    
    /**
     * 可用余额
     */
    private BigDecimal usableBalance = new BigDecimal(0.00);

	  /**
     * 余额基数
     */
    private BigDecimal interestBase = new BigDecimal(0.00);
    
    /**
     * 计算利息方式
     */
    private Integer interestMode;
    
    
    /**
     * 约定固定利率
     */
    private BigDecimal interestRate;
    
    
    
    /**
     * 最后交易日期
     */
    private String lastTranDate;
    
    /**
     * 注销日期
     */
    private String cancelDate;
    /**
     * 注销柜员
     */
    private String cancelTeller;
    
   /**
     * 账户余额方向
     */
    private String balanceCD;
    
    /**
     * 账户控制标志  
     */
    private String acctControlFlag;
  
    /**
     * 账户状态
     */
    private String status;
    
    /**
     * 校验位
     */
    private String dac;
	public String getAcctNo() {
		return acctNO;
	}

	public void setAcctNo(String acctNo) {
		this.acctNO = acctNo;
	}

	public String getAcctClass() {
		return acctClass;
	}

	public void setAcctClass(String acctClass) {
		this.acctClass = acctClass;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

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

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getOpenBranch() {
		return openBranch;
	}

	public void setOpenBranch(String openBranch) {
		this.openBranch = openBranch;
	}

	public String getOpenTeller() {
		return openTeller;
	}

	public void setOpenTeller(String openTeller) {
		this.openTeller = openTeller;
	}

	public BigDecimal getLastDayBalance() {
		return lastDayBalance;
	}

	public void setLastDayBalance(BigDecimal lastDayBalance) {
		this.lastDayBalance = lastDayBalance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getStopPayAmount() {
		return stopPayAmount;
	}

	public void setStopPayAmount(BigDecimal stopPayAmount) {
		this.stopPayAmount = stopPayAmount;
	}

	public BigDecimal getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(BigDecimal usableBalance) {
		this.usableBalance = usableBalance;
	}

	public BigDecimal getInterestBase() {
		return interestBase;
	}

	public void setInterestBase(BigDecimal interestBase) {
		this.interestBase = interestBase;
	}

	public Integer getInterestMode() {
		return interestMode;
	}

	public void setInterestMode(Integer interestMode) {
		this.interestMode = interestMode;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public String getLastTranDate() {
		return lastTranDate;
	}

	public void setLastTranDate(String lastTranDate) {
		this.lastTranDate = lastTranDate;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelTeller() {
		return cancelTeller;
	}

	public void setCancelTeller(String cancelTeller) {
		this.cancelTeller = cancelTeller;
	}

	public String getBalanceCD() {
		return balanceCD;
	}

	public void setBalanceCD(String balanceCD) {
		this.balanceCD = balanceCD;
	}

	public String getAcctControlFlag() {
		return acctControlFlag;
	}

	public void setAcctControlFlag(String acctControlFlag) {
		this.acctControlFlag = acctControlFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcctNO() {
		return acctNO;
	}

	public void setAcctNO(String acctNO) {
		this.acctNO = acctNO;
	}

	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}

	@Override
	public String toString() {
		return "AcctBalanceQuery [acctClass=" + acctClass
				+ ", acctControlFlag=" + acctControlFlag + ", acctNO=" + acctNO
				+ ", acctName=" + acctName + ", acctType=" + acctType
				+ ", balance=" + balance + ", balanceCD=" + balanceCD
				+ ", cancelDate=" + cancelDate + ", cancelTeller="
				+ cancelTeller + ", currType=" + currType + ", dac=" + dac
				+ ", interestBase=" + interestBase + ", interestMode="
				+ interestMode + ", interestRate=" + interestRate
				+ ", lastDayBalance=" + lastDayBalance + ", lastTranDate="
				+ lastTranDate + ", openBranch=" + openBranch + ", openDate="
				+ openDate + ", openTeller=" + openTeller + ", status="
				+ status + ", stopPayAmount=" + stopPayAmount
				+ ", subjectCode=" + subjectCode + ", usableBalance="
				+ usableBalance + "]";
	}

	
   
}
