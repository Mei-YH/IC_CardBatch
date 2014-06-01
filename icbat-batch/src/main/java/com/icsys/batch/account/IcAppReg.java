package com.icsys.batch.account;

import java.math.BigDecimal;

/**
 * @author Runpu Hu
 * @version 创建时间：2011-4-22 下午05:59:05 
 * 类说明 ：对应IC卡应用绑定表
 */

public class IcAppReg {

	/**
	 * 客户账户标识
	 */
	private String acctNoId;
	
	/**
	 * 应用编号
	 */
	private String aid;
	
	/**
	 * 应用标识
	 */
	private String applicationCardLogo;
	
	/**
	 * 应用脱机子账号
	 */
	private String applicationOfflineSubAcct;
	
	/**
	 * 应用联机子账号
	 */
	private String applicationOnlineSubAcct;
	
	/**
	 * 应用营销机构
	 */
	private String businessBranch;
	
	/**
	 * 应用卡内金额上限
	 */
	private BigDecimal amountMax;
	
	/**
	 * 交易单笔最高限额
	 */
	private BigDecimal tradeAmountMax;
	
//	/**
//	 * 应用税率规则
//	 */
//	private int applicationRateRule;
//	
//	/**
//	 * 应用积分规则
//	 */
//	private int applicationIntegralRule;
	
	/**
	 * 应用状态
	 */
	private String applicationState;
	
	

	
	
	public BigDecimal getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(BigDecimal amountMax) {
		this.amountMax = amountMax;
	}

	public BigDecimal getTradeAmountMax() {
		return tradeAmountMax;
	}

	public void setTradeAmountMax(BigDecimal tradeAmountMax) {
		this.tradeAmountMax = tradeAmountMax;
	}

	public String getBusinessBranch() {
		return businessBranch;
	}

	public void setBusinessBranch(String businessBranch) {
		this.businessBranch = businessBranch;
	}

	public String getAcctNoId() {
		return acctNoId;
	}

	public void setAcctNoId(String acctNoId) {
		this.acctNoId = acctNoId;
	}


	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getApplicationCardLogo() {
		return applicationCardLogo;
	}

	public void setApplicationCardLogo(String applicationCardLogo) {
		this.applicationCardLogo = applicationCardLogo;
	}

	public String getApplicationOfflineSubAcct() {
		return applicationOfflineSubAcct;
	}

	public void setApplicationOfflineSubAcct(String applicationOfflineSubAcct) {
		this.applicationOfflineSubAcct = applicationOfflineSubAcct;
	}

	public String getApplicationOnlineSubAcct() {
		return applicationOnlineSubAcct;
	}

	public void setApplicationOnlineSubAcct(String applicationOnlineSubAcct) {
		this.applicationOnlineSubAcct = applicationOnlineSubAcct;
	}


	public String getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(String applicationState) {
		this.applicationState = applicationState;
	}

	@Override
	public String toString() {
		return "IcAppReg [acctNoId=" + acctNoId + ", applicationCardLogo="
				+ applicationCardLogo + ", aid=" + aid
				+ ", applicationOfflineSubAcct=" + applicationOfflineSubAcct
				+ ", applicationOnlineSubAcct=" + applicationOnlineSubAcct
				+ ", applicationState=" + applicationState
				+ ", businessBranch=" + businessBranch + "]";
	}
	

}
