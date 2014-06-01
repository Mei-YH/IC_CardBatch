package com.icsys.batch.task.report.proxy.bean;

public class VmIckltjBb {

	private String coreAreaNo;
	private String icBranch;
	private String icDate;
	private String icProdNo;
	private String prodDesc;
	private Integer zcCount;
	private Integer xkCount;
	private Integer gsCount;
	private Integer hkCount;
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
	public Integer getGsCount() {
		return gsCount;
	}
	public void setGsCount(Integer gsCount) {
		this.gsCount = gsCount;
	}
	public Integer getHkCount() {
		return hkCount;
	}
	public void setHkCount(Integer hkCount) {
		this.hkCount = hkCount;
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
	public Integer getXkCount() {
		return xkCount;
	}
	public void setXkCount(Integer xkCount) {
		this.xkCount = xkCount;
	}
	public Integer getZcCount() {
		return zcCount;
	}
	public void setZcCount(Integer zcCount) {
		this.zcCount = zcCount;
	}
	
	public String toString(){
		return "\"" +  this.coreAreaNo + "\",\"" + this.icBranch + "\",\"" + this.icDate + "\",\"" + this.icProdNo + "\",\"" + 
			this.prodDesc + "\"," + this.zcCount + "," + this.xkCount + "," + this.gsCount + "," + this.hkCount + ",\"" + 
			this.remark + "\",\"" + this.backup1 + "\",\"" + this.backup2 + "\"";
	}
}
