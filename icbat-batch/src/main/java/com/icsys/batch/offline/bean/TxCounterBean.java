package com.icsys.batch.offline.bean;

public class TxCounterBean {

	/**
	 * 清算日期
	 */
	private String cleardate;

	/**
	 * IC卡号
	 */
	private String iccardno;
	
	/**
	 * 卡序号
	 */
	private String cardIndex;

	/**
	 * 应用交易计数器
	 */
	private String tag9F36;
	
	

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public String getCleardate() {
		return cleardate;
	}

	public void setCleardate(String cleardate) {
		this.cleardate = cleardate;
	}

	public String getIccardno() {
		return iccardno;
	}

	public void setIccardno(String iccardno) {
		this.iccardno = iccardno;
	}

	public String getTag9F36() {
		return tag9F36;
	}

	public void setTag9F36(String tag9f36) {
		tag9F36 = tag9f36;
	}

	@Override
	public String toString() {
		return "TxCounterBean [cleardate=" + cleardate + ", iccardno="
				+ iccardno + ", tag9F36=" + tag9F36 + "]";
	}
}
