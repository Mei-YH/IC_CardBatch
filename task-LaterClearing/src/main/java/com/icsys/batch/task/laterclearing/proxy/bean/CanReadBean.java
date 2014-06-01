package com.icsys.batch.task.laterclearing.proxy.bean;

/**
 * @author  作者:lyq 
 */
public class CanReadBean {
	/**
	 * 脱机清算账号
	 */
	private String offline_clear_sub_acct;
	/**
	 * 处理机构
	 */
	private String tran_branch;
	/**
	 * 处理柜员
	 */
	private String tran_teller;
	/**
	 * 发卡机构
	 */
	private String issuer_Branch;
	/**
	 * IC卡产品号
	 */
    private String product_No;
    /**
     * 卡应用编号
     */
    private String aid;
    /**
	 * IC卡卡号
	 */
    private String card_No;
    
	/*
	 *IC卡序列号
	 * */
	private String cardIndex;
	
	/**
	 * IC卡序列号
	 * @return
	 */
	public String getCardIndex() {
		return cardIndex;
	}
	
	/**
	 * IC卡序列号
	 * @param cardIndex
	 */
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}
	/**
	 * IC卡卡号
	 * @return the card_No
	 */
	public String getCard_No() {
		return card_No;
	}
	/**
	 * IC卡卡号
	 * @param card_No the card_No to set
	 */
	public void setCard_No(String card_No) {
		this.card_No = card_No;
	}
	/**
	 * IC卡产品号
	 * @return the product_No
	 */
	public String getProduct_No() {
		return product_No;
	}
	/**
	 * IC卡产品号
	 * @param product_No the product_No to set
	 */
	public void setProduct_No(String product_No) {
		this.product_No = product_No;
	}
	/**
	 * 卡应用编号
	 * @return the aid
	 */
	public String getAid() {
		return aid;
	}
	/**
	 * 卡应用编号
	 * @param aid the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}
	/**
	 * 发卡机构
	 * @return the issuer_Branch
	 */
	public String getIssuer_Branch() {
		return issuer_Branch;
	}
	/**
	 * 发卡机构
	 * @param issuer_Branch the issuer_Branch to set
	 */
	public void setIssuer_Branch(String issuer_Branch) {
		this.issuer_Branch = issuer_Branch;
	}
	/**
	 * 脱机清算账号
	 * @return the offline_clear_sub_acct
	 */
	public String getOffline_clear_sub_acct() {
		return offline_clear_sub_acct;
	}
	/**
	 * 脱机清算账号
	 * @param offline_clear_sub_acct the offline_clear_sub_acct to set
	 */
	public void setOffline_clear_sub_acct(String offline_clear_sub_acct) {
		this.offline_clear_sub_acct = offline_clear_sub_acct;
	}
	/**
	 * 处理机构
	 * @return the tran_branch
	 */
	public String getTran_branch() {
		return tran_branch;
	}
	/**
	 * 处理机构
	 * @param tran_branch the tran_branch to set
	 */
	public void setTran_branch(String tran_branch) {
		this.tran_branch = tran_branch;
	}
	/**
	 * 处理柜员
	 * @return the tran_teller
	 */
	public String getTran_teller() {
		return tran_teller;
	}
	/**
	 * 处理柜员
	 * @param tran_teller the tran_teller to set
	 */
	public void setTran_teller(String tran_teller) {
		this.tran_teller = tran_teller;
	}

}
