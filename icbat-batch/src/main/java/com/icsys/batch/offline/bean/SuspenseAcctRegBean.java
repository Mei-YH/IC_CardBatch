package com.icsys.batch.offline.bean;

import java.math.BigDecimal;

public class SuspenseAcctRegBean {

	private String suaTrdate;
	private String suaPlserial;
	private String suaSerial;
	private String suaTrbranch;
	private String icCardno;
	private BigDecimal suaSamount;
	private String suaCtype;
	private String transferFormAcctNo;
	private String suaTstacctno;
	private String suaTrtime;
	private String suaSystrno;
	private String suaPpid;
	private String suaSbid;
	private String suaDeflag;
	private String suaRemark;
	
	public String getSuaTrdate(){
		return this.suaTrdate;
	}
	public void setSuaTrdate(String suaTrdate){
		this.suaTrdate = suaTrdate;
	}
	public String getSuaPlserial(){
		return this.suaPlserial;
	}
	public void setSuaPlserial(String suaPlserial){
		this.suaPlserial = suaPlserial;
	}
	public String getSuaSerial(){
		return this.suaSerial;
	}
	public void setSuaSerial(String suaSerial){
		this.suaSerial = suaSerial;
	}
	public String getSuaTrbranch(){
		return this.suaTrbranch;
	}
	public void setSuaTrbranch(String suaTrbranch){
		this.suaTrbranch = suaTrbranch;
	}
	public String getIcCardno(){
		return this.icCardno;
	}
	public void setIcCardno(String icCardno){
		this.icCardno = icCardno;
	}
	public BigDecimal getSuaSamount(){
		return this.suaSamount;
	}
	public void setSuaSamount(BigDecimal suaSamount){
		this.suaSamount = suaSamount;
	}
	public String getSuaCtype(){
		return this.suaCtype;
	}
	public void setSuaCtype(String suaCtype){
		this.suaCtype = suaCtype;
	}
	public String getTransferFormAcctNo(){
		return this.transferFormAcctNo;
	}
	public void setTransferFormAcctNo(String transferFormAcctNo){
		this.transferFormAcctNo = transferFormAcctNo;
	}
	public String getSuaTstacctno(){
		return this.suaTstacctno;
	}
	public void setSuaTstacctno(String suaTstacctno){
		this.suaTstacctno = suaTstacctno;
	}
	public String getSuaTrtime(){
		return this.suaTrtime;
	}
	public void setSuaTrtime(String suaTrtime){
		this.suaTrtime = suaTrtime;
	}
	public String getSuaSystrno(){
		return this.suaSystrno;
	}
	public void setSuaSystrno(String suaSystrno){
		this.suaSystrno = suaSystrno;
	}
	public String getSuaPpid(){
		return this.suaPpid;
	}
	public void setSuaPpid(String suaPpid){
		this.suaPpid = suaPpid;
	}
	public String getSuaSbid(){
		return this.suaSbid;
	}
	public void setSuaSbid(String suaSbid){
		this.suaSbid = suaSbid;
	}
	public String getSuaDeflag(){
		return this.suaDeflag;
	}
	public void setSuaDeflag(String suaDeflag){
		this.suaDeflag = suaDeflag;
	}
	public String getSuaRemark(){
		return this.suaRemark;
	}
	public void setSuaRemark(String suaRemark){
		this.suaRemark = suaRemark;
	}
	@Override
	public String toString() {
		return "SuspenseAcctRegBean [icCardno=" + icCardno + ", suaCtype="
				+ suaCtype + ", suaDeflag=" + suaDeflag + ", suaPlserial="
				+ suaPlserial + ", suaPpid=" + suaPpid + ", suaRemark="
				+ suaRemark + ", suaSamount=" + suaSamount + ", suaSbid="
				+ suaSbid + ", suaSerial=" + suaSerial + ", suaSystrno="
				+ suaSystrno + ", suaTrbranch=" + suaTrbranch + ", suaTrdate="
				+ suaTrdate + ", suaTrtime=" + suaTrtime + ", suaTstacctno="
				+ suaTstacctno + ", transferFormAcctNo=" + transferFormAcctNo
				+ "]";
	}
}
