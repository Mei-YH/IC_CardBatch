package com.icsys.batch.task.laterclearing.proxy.bean;

import java.math.BigDecimal;

/**
 * @author  作者:lyq
 * 账务往来的一个bean
 * 主要是用来封装流水信息和转账数据
 */
public class KeepAccoutBean {
	/**
     * 流水号
     */
    private String serial;

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
    private String branchNo;

    /**
     * 柜员
     */
    private String operator;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 摘要
     */
    private String  reMark;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 应用编号ID
     */
    private String aid;
    /**
     * 核心的集中户
     */
    private String hostAcctNo;
    
    /*
     * 交易日期
     */
    private String tranDate;
    
    private String cardIndex;
    
    
    public String getCardIndex() {
		return cardIndex;
	}
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}
	public String getTranDate(){
    	return tranDate;
    }
    public void setTranDate(String tranDate){
    	this.tranDate = tranDate;
    }
	 /**
	  * 核心的集中户
	 * @return the hostAcctNo
	 */
	public String getHostAcctNo() {
		return hostAcctNo;
	}

	/**
	 * 核心的集中户
	 * @param hostAcctNo the hostAcctNo to set
	 */
	public void setHostAcctNo(String hostAcctNo) {
		this.hostAcctNo = hostAcctNo;
	}

	/**
	  * 应用编号ID
	 * @return the aid
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * 应用编号ID
	 * @param aid the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	  * 卡号
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * 卡号
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the serial
	 * 流水号
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 * 流水号
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * @return the debitAcctNO
	 * 借方账号
	 */
	public String getDebitAcctNO() {
		return debitAcctNO;
	}

	/**
	 * @param debitAcctNO the debitAcctNO to set
	 * 借方账号
	 */
	public void setDebitAcctNO(String debitAcctNO) {
		this.debitAcctNO = debitAcctNO;
	}

	/**
	 * @return the creditAcctNO
	 * 贷方账号
	 */
	public String getCreditAcctNO() {
		return creditAcctNO;
	}

	/**
	 * @param creditAcctNO the creditAcctNO to set
	 * 贷方账号
	 */
	public void setCreditAcctNO(String creditAcctNO) {
		this.creditAcctNO = creditAcctNO;
	}

	/**
	 * @return the branchNo
	 * 机构号
	 */
	public String getBranchNo() {
		return branchNo;
	}

	/**
	 * @param branchNo the branchNo to set
	 * 机构号
	 */
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	/**
	 * @return the operator
	 * 柜员
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 * 柜员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the amount
	 * 转账金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 * 转账金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the reMark
	 * 备注
	 */
	public String getReMark() {
		return reMark;
	}

	/**
	 * @param reMark the reMark to set
	 * 备注
	 */
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
}
