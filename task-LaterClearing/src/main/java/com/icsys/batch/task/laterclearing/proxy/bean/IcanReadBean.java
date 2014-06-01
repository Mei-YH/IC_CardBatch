package com.icsys.batch.task.laterclearing.proxy.bean;

/**
 * @author  作者:lyq 
 */
public class IcanReadBean {
	/**
	 * 老卡脱机账号
	 */
	private String oldOff;
	/**
	 * 老卡联机账号
	 */
	private String oldOn;
	/**
	 * 新卡脱机账号
	 */
	private String newOff;
	/**
	 * 新卡联机账号
	 */
	private String newOn;
	/**
	 * 卡应用编号
	 */
	private String aid;
	/**
	 * 处理机构
	 */
	private String branch;
	/**
	 * 处理柜员
	 */
	private String tellerNo;
	/**
	 * 旧卡卡号
	 */
	private String icNo;
	/**
	 * 新卡卡号
	 */
	private String newIcNo;
	
	/*
	 *IC卡序列号
	 * */
	private String icIndex;
	
	/*
	 * 异常状态
	 */
	private String abnState;
	
	private String txDt;
	
	public String getTxDt() {
		return txDt;
	}
	public void setTxDt(String txDt) {
		this.txDt = txDt;
	}
	/**
	 * @return the old_off_acct
	 */
	public String getOldOff() {
		return oldOff;
	}
	/**
	 * @param old_off_acct the old_off_acct to set
	 */
	public void setOldOff(String old_off_acct) {
		this.oldOff = old_off_acct;
	}
	/**
	 * @return the old_on_acct
	 */
	public String getOldOn() {
		return oldOn;
	}
	/**
	 * @param old_on_acct the old_on_acct to set
	 */
	public void setOldOn(String old_on_acct) {
		this.oldOn = old_on_acct;
	}
	/**
	 * @return the new_off_acct
	 */
	public String getNewOff() {
		return newOff;
	}
	/**
	 * @param new_off_acct the new_off_acct to set
	 */
	public void setNewOff(String new_off_acct) {
		this.newOff = new_off_acct;
	}
	/**
	 * @return the new_on_acct
	 */
	public String getNewOn() {
		return newOn;
	}
	/**
	 * @param new_on_acct the new_on_acct to set
	 */
	public void setNewOn(String new_on_acct) {
		this.newOn = new_on_acct;
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
	 * 处理机构
	 * @return the tran_branch
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * 处理机构
	 * @param tran_branch the tran_branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * 处理柜员
	 * @return the tran_teller
	 */
	public String getTellerNo() {
		return tellerNo;
	}
	/**
	 * 处理柜员
	 * @param tran_teller the tran_teller to set
	 */
	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}
	/**
	 * 旧卡卡号
	 * @return the card_no
	 */
	public String getIcNo() {
		return icNo;
	}
	/**
	 * 旧卡卡号
	 * @param card_no the card_no to set
	 */
	public void setIcNo(String ic_no) {
		this.icNo = ic_no;
	}
	/**
	 * 新卡卡号
	 * @return the new_card_no
	 */
	public String getNewIcNo() {
		return newIcNo;
	}
	/**
	 * 新卡卡号
	 * @param new_card_no the new_card_no to set
	 */
	public void setNewIcNo(String new_card_no) {
		this.newIcNo = new_card_no;
	}
	
    /**
     * IC卡序列号
     * @return
     */
	public String getIcIndex() {
		return icIndex;
	}
	
	/**
	 * IC卡序列号
	 * @param cardIndex
	 */
	public void setIcIndex(String cardIndex) {
		this.icIndex = cardIndex;
	}
	
	/**
	 * 异常状态
	 * @return
	 */
	public String getAbnState(){
		return abnState;
	}
	
	/**
	 * 异常状态
	 * @return abnState
	 */
	public void setAbnState(String abnState){
		this.abnState = abnState;
	}
}
