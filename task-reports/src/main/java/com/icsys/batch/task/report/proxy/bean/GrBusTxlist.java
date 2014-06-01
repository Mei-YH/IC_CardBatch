package com.icsys.batch.task.report.proxy.bean;

import java.math.BigDecimal;

public class GrBusTxlist {
	private String  txlSerial;           
	private String  txlPlserial;           
	private String  txlHserial;                
	private String  txlTrbranch;               
	private String  icCardno;                
	private String  cardIndex;                    
	private BigDecimal txlSamount;             
	private String txlTrflag;                    
	private String  txlRecflag;               
	private BigDecimal txlTramount;                      
	private BigDecimal txlFee;                      
	private String  currencyCode;                  
	private String  txlAcctno;                  
	private String  lendingSign;               
	private String  txlCardno1;                   
	private String   txlAcctno1;                   
	private BigDecimal txlAccamount1;                  
	private String  txlAccflag;                 
	private String  txlHtaccno;                 
	private String txlTrstatus;                      
	private String  txlCorrecord;                     
	private String  txlTrdate;  

	public String getTxlSerial() {
		return txlSerial;
	}
	public void setTxlSerial(String txlSerial) {
		this.txlSerial = txlSerial;
	}
	public String getTxlPlserial() {
		return txlPlserial;
	}
	public void setTxlPlserial(String txlPlserial) {
		this.txlPlserial = txlPlserial;
	}
	public String getTxlHserial() {
		return txlHserial;
	}
	public void setTxlHserial(String txlHserial) {
		this.txlHserial = txlHserial;
	}
	public String getTxlTrbranch() {
		return txlTrbranch;
	}
	public void setTxlTrbranch(String txlTrbranch) {
		this.txlTrbranch = txlTrbranch;
	}
	public String getIcCardno() {
		return icCardno;
	}
	public void setIcCardno(String icCardno) {
		this.icCardno = icCardno;
	}
	public String getCardIndex() {
		return cardIndex;
	}
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}
	public BigDecimal getTxlSamount() {
		return txlSamount;
	}
	public void setTxlSamount(BigDecimal txlSamount) {
		this.txlSamount = txlSamount;
	}
	public String getTxlTrflag() {
		return txlTrflag;
	}
	public void setTxlTrflag(String txlTrflag) {
		this.txlTrflag = txlTrflag;
	}
	public String getTxlRecflag() {
		return txlRecflag;
	}
	public void setTxlRecflag(String txlRecflag) {
		this.txlRecflag = txlRecflag;
	}
	public BigDecimal getTxlTramount() {
		return txlTramount;
	}
	public void setTxlTramount(BigDecimal txlTramount) {
		this.txlTramount = txlTramount;
	}
	public BigDecimal getTxlFee() {
		return txlFee;
	}
	public void setTxlFee(BigDecimal txlFee) {
		this.txlFee = txlFee;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTxlAcctno() {
		return txlAcctno;
	}
	public void setTxlAcctno(String txlAcctno) {
		this.txlAcctno = txlAcctno;
	}
	public String getLendingSign() {
		return lendingSign;
	}
	public void setLendingSign(String lendingSign) {
		this.lendingSign = lendingSign;
	}
	public String getTxlCardno1() {
		return txlCardno1;
	}
	public void setTxlCardno1(String txlCardno1) {
		this.txlCardno1 = txlCardno1;
	}
	public String getTxlAcctno1() {
		return txlAcctno1;
	}
	public void setTxlAcctno1(String txlAcctno1) {
		this.txlAcctno1 = txlAcctno1;
	}
	public BigDecimal getTxlAccamount1() {
		return txlAccamount1;
	}
	public void setTxlAccamount1(BigDecimal txlAccamount1) {
		this.txlAccamount1 = txlAccamount1;
	}
	public String getTxlAccflag() {
		return txlAccflag;
	}
	public void setTxlAccflag(String txlAccflag) {
		this.txlAccflag = txlAccflag;
	}
	public String getTxlHtaccno() {
		return txlHtaccno;
	}
	public void setTxlHtaccno(String txlHtaccno) {
		this.txlHtaccno = txlHtaccno;
	}
	public String getTxlTrstatus() {
		return txlTrstatus;
	}
	public void setTxlTrstatus(String txlTrstatus) {
		this.txlTrstatus = txlTrstatus;
	}
	public String getTxlCorrecord() {
		return txlCorrecord;
	}
	public void setTxlCorrecord(String txlCorrecord) {
		this.txlCorrecord = txlCorrecord;
	}
	public String getTxlTrdate() {
		return txlTrdate;
	}
	public void setTxlTrdate(String txlTrdate) {
		this.txlTrdate = txlTrdate;
	}

	public String toString(){
		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append(addStr(this.txlSerial));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlPlserial));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlHserial));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlTrbranch));
		sBuilder.append(",");
		sBuilder.append(addStr(this.icCardno));
		sBuilder.append(",");
		sBuilder.append(addStr(this.cardIndex));
		sBuilder.append(",");
		// 1
		sBuilder.append(this.txlSamount);
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlTrflag));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlRecflag));
		sBuilder.append(",");
		// 2
		sBuilder.append(this.txlTramount);
		sBuilder.append(",");
		// 3
		sBuilder.append(this.txlFee);
		sBuilder.append(",");
		sBuilder.append(addStr(this.currencyCode));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlAcctno));
		sBuilder.append(",");
		sBuilder.append(addStr(this.lendingSign));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlCardno1));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlAcctno1));
		sBuilder.append(",");
		// 4
		sBuilder.append(this.txlAccamount1);
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlAccflag));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlHtaccno));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlTrstatus));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlCorrecord));
		sBuilder.append(",");
		sBuilder.append(addStr(this.txlTrdate));
		// 换行
		sBuilder.append("\n");
		return sBuilder.toString();
	}
	/**
	 * @param 给字符串添加转义字符 \"
	 * @return 返回带引号的字符串
	 */
	private String addStr (String str ){

		if (str==null){
			return "";
		}
		return "\""+str+"\"";
	}
}