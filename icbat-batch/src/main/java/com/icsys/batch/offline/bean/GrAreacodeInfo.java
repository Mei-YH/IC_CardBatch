package com.icsys.batch.offline.bean;

public class GrAreacodeInfo {
	/*地区编号*/
	private String coreAreaNo;
	/*地区简称*/
	private String coreArea;
	/*备注*/
	private String remark;
	/*银联入网机构号*/
	private String cupsBranchCode;
	
	public String getCoreAreaNo() {
		return coreAreaNo;
	}
	public void setCoreAreaNo(String coreAreaNo) {
		this.coreAreaNo = coreAreaNo;
	}
	public String getCoreArea() {
		return coreArea;
	}
	public void setCoreArea(String coreArea) {
		this.coreArea = coreArea;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCupsBranchCode() {
		return cupsBranchCode;
	}
	public void setCupsBranchCode(String cupsBranchCode) {
		this.cupsBranchCode = cupsBranchCode;
	}
}
