package com.icsys.batch.task.report.proxy.bean;

import java.math.BigDecimal;

public class VmIcjyltjBb {

	private String coreAreaNo;
	private String icBranch;
	private String icDate;
	private String icProdNo;
	private String prodDesc;
	private Integer xjCount;
	private BigDecimal xjSum;
	private Integer zzCount;
	private BigDecimal zzSum;
	private Integer xfCount;
	private BigDecimal xfSum;
	private String remark;
	private String backup1;
	private String backup2;
	public String getBackup1() {
		return backup1;
	}
	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	public String getBackup2() {
		return backup2;
	}
	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	public String getCoreAreaNo() {
		return coreAreaNo;
	}
	public void setCoreAreaNo(String coreAreaNo) {
		this.coreAreaNo = coreAreaNo;
	}
	public String getIcBranch() {
		return icBranch;
	}
	public void setIcBranch(String icBranch) {
		this.icBranch = icBranch;
	}
	public String getIcDate() {
		return icDate;
	}
	public void setIcDate(String icDate) {
		this.icDate = icDate;
	}
	public String getIcProdNo() {
		return icProdNo;
	}
	public void setIcProdNo(String icProdNo) {
		this.icProdNo = icProdNo;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getXfCount() {
		return xfCount;
	}
	public void setXfCount(Integer xfCount) {
		this.xfCount = xfCount;
	}
	public BigDecimal getXfSum() {
		return xfSum;
	}
	public void setXfSum(BigDecimal xfSum) {
		this.xfSum = xfSum;
	}
	public Integer getXjCount() {
		return xjCount;
	}
	public void setXjCount(Integer xjCount) {
		this.xjCount = xjCount;
	}
	public BigDecimal getXjSum() {
		return xjSum;
	}
	public void setXjSum(BigDecimal xjSum) {
		this.xjSum = xjSum;
	}
	public Integer getZzCount() {
		return zzCount;
	}
	public void setZzCount(Integer zzCount) {
		this.zzCount = zzCount;
	}
	public BigDecimal getZzSum() {
		return zzSum;
	}
	public void setZzSum(BigDecimal zzSum) {
		this.zzSum = zzSum;
	}
	
	public String toString(){
		return "\"" + this.coreAreaNo + "\",\"" + this.icBranch + "\",\"" + this.icDate + "\",\"" + this.icProdNo + "\",\"" +
			this.prodDesc + "\"," + this.xjCount + "," + this.xjSum + "," + this.zzCount + "," + this.zzSum + "," + 
			this.xfCount + "," + this.xfSum + ",\"" + this.remark + "\",\"" + this.backup1 + "\",\"" + this.backup2 + "\"";
	}
}
