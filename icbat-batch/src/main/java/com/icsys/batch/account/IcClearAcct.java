package com.icsys.batch.account;

import java.math.BigDecimal;

/** 
 * @author Runpu Hu  
 * @version 创建时间：2011-4-22 下午06:01:46 
 * 类说明 ：IC卡异常卡待清算账户表
 */

public class IcClearAcct {
	
	/**
	 * 卡号
	 */
	private String cardNo;
	
	/**
	*IC卡卡序号
	*/
	private String cardIndex;
	
	/**
	 * 应用编号
	 */
	private String aid;
	
	/**
	 * 脱机清算子账号
	 */
	private String offlineClearSubAcct;
	
	/**
	 * 回收金额
	 */
	private BigDecimal amount;
	
	

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getOfflineClearSubAcct() {
		return offlineClearSubAcct;
	}

	public void setOfflineClearSubAcct(String offlineClearSubAcct) {
		this.offlineClearSubAcct = offlineClearSubAcct;
	}

	@Override
	public String toString() {
		return "IcClearAcct [aid=" + aid + ", amount=" + amount
				+ ", cardIndex=" + cardIndex + ", cardNo=" + cardNo
				+ ", offlineClearSubAcct=" + offlineClearSubAcct + "]";
	}

}
