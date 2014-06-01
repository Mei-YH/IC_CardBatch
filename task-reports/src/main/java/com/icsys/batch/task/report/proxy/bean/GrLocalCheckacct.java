package com.icsys.batch.task.report.proxy.bean;

import java.math.BigDecimal;

public class GrLocalCheckacct {
	private String icSysdate;
	private String oldiccardseq;
	private String icNo;
	private String icIndex;
	private String transtype;
	private String accttztype;
	private String ictrantime;
	private String htrantime;
	private String oldcoreseq;
	private String transbrc;
	private BigDecimal circleamount;
	private BigDecimal circleamountfee;
	private String branchname;
	private String customname;
	private String currcod;
	private BigDecimal elcashincard;
	private String localtype;
	private String remark;
	public String getAccttztype() {
		return accttztype;
	}
	public void setAccttztype(String accttztype) {
		this.accttztype = accttztype;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public BigDecimal getCircleamount() {
		return circleamount;
	}
	public void setCircleamount(BigDecimal circleamount) {
		this.circleamount = circleamount;
	}
	public BigDecimal getCircleamountfee() {
		return circleamountfee;
	}
	public void setCircleamountfee(BigDecimal circleamountfee) {
		this.circleamountfee = circleamountfee;
	}
	public String getCurrcod() {
		return currcod;
	}
	public void setCurrcod(String currcod) {
		this.currcod = currcod;
	}
	public String getCustomname() {
		return customname;
	}
	public void setCustomname(String customname) {
		this.customname = customname;
	}
	public BigDecimal getElcashincard() {
		return elcashincard;
	}
	public void setElcashincard(BigDecimal elcashincard) {
		this.elcashincard = elcashincard;
	}
	public String getHtrantime() {
		return htrantime;
	}
	public void setHtrantime(String htrantime) {
		this.htrantime = htrantime;
	}
	public String getIcIndex() {
		return icIndex;
	}
	public void setIcIndex(String icIndex) {
		this.icIndex = icIndex;
	}
	public String getIcNo() {
		return icNo;
	}
	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}
	public String getIcSysdate() {
		return icSysdate;
	}
	public void setIcSysdate(String icSysdate) {
		this.icSysdate = icSysdate;
	}
	public String getIctrantime() {
		return ictrantime;
	}
	public void setIctrantime(String ictrantime) {
		this.ictrantime = ictrantime;
	}
	public String getLocaltype() {
		return localtype;
	}
	public void setLocaltype(String localtype) {
		this.localtype = localtype;
	}
	public String getOldcoreseq() {
		return oldcoreseq;
	}
	public void setOldcoreseq(String oldcoreseq) {
		this.oldcoreseq = oldcoreseq;
	}
	public String getOldiccardseq() {
		return oldiccardseq;
	}
	public void setOldiccardseq(String oldiccardseq) {
		this.oldiccardseq = oldiccardseq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTransbrc() {
		return transbrc;
	}
	public void setTransbrc(String transbrc) {
		this.transbrc = transbrc;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	
	public String toString(){
		return "\"" + this.icSysdate + "\",\"" + this.oldiccardseq + "\",\"" + this.icNo + "\",\"" + this.icIndex + "\",\"" + 
			this.transtype + "\",\"" + this.accttztype + "\",\"" + this.ictrantime + "\",\"" + this.htrantime + "\",\"" + 
			this.oldcoreseq + "\",\"" + this.transbrc + "\"," + this.circleamount + "," + this.circleamountfee + ",\"" + 
			this.branchname + "\",\"" + this.customname + "\",\"" + this.currcod + "\"," + this.elcashincard + ",\"" + 
			this.localtype + "\",\"" + this.remark + "\"";
	}
}
