package com.icsys.batch.task.report.proxy.bean;

import java.math.BigDecimal;

public class VmIczhjytjBb {

	private String coreAreaNo;
	private String icBranch;
	private String icDate;
	private String icProdNo;
	private String prodDesc;
	private Integer tjJcount;
	private BigDecimal tjJsum;
	private Integer tjDcount;
	private BigDecimal tjDsum;
	private Integer ljJcount;
	private BigDecimal ljJsum;
	private Integer ljDcount;
	private BigDecimal ljDsum;
	private Integer dqJcount;
	private BigDecimal dqJsum;
	private Integer dqDcount;
	private BigDecimal dqDsum;
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
	public Integer getDqDcount() {
		return dqDcount;
	}
	public void setDqDcount(Integer dqDcount) {
		this.dqDcount = dqDcount;
	}
	public BigDecimal getDqDsum() {
		return dqDsum;
	}
	public void setDqDsum(BigDecimal dqDsum) {
		this.dqDsum = dqDsum;
	}
	public Integer getDqJcount() {
		return dqJcount;
	}
	public void setDqJcount(Integer dqJcount) {
		this.dqJcount = dqJcount;
	}
	public BigDecimal getDqJsum() {
		return dqJsum;
	}
	public void setDqJsum(BigDecimal dqJsum) {
		this.dqJsum = dqJsum;
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
	public Integer getLjDcount() {
		return ljDcount;
	}
	public void setLjDcount(Integer ljDcount) {
		this.ljDcount = ljDcount;
	}
	public BigDecimal getLjDsum() {
		return ljDsum;
	}
	public void setLjDsum(BigDecimal ljDsum) {
		this.ljDsum = ljDsum;
	}
	public Integer getLjJcount() {
		return ljJcount;
	}
	public void setLjJcount(Integer ljJcount) {
		this.ljJcount = ljJcount;
	}
	public BigDecimal getLjJsum() {
		return ljJsum;
	}
	public void setLjJsum(BigDecimal ljJsum) {
		this.ljJsum = ljJsum;
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
	public Integer getTjDcount() {
		return tjDcount;
	}
	public void setTjDcount(Integer tjDcount) {
		this.tjDcount = tjDcount;
	}
	public BigDecimal getTjDsum() {
		return tjDsum;
	}
	public void setTjDsum(BigDecimal tjDsum) {
		this.tjDsum = tjDsum;
	}
	public Integer getTjJcount() {
		return tjJcount;
	}
	public void setTjJcount(Integer tjJcount) {
		this.tjJcount = tjJcount;
	}
	public BigDecimal getTjJsum() {
		return tjJsum;
	}
	public void setTjJsum(BigDecimal tjJsum) {
		this.tjJsum = tjJsum;
	}
	
	public String toString(){
		return "\"" + this.coreAreaNo + "\",\"" + this.icBranch + "\",\"" + this.icDate + "\",\"" + this.icProdNo + "\",\"" + 
			this.prodDesc + "\"," + this.tjJcount + "," + this.tjJsum + "," + this.tjDcount + "," + this.tjDsum + "," + 
			this.ljJcount + "," + this.ljJsum + "," + this.ljDcount + "," + this.ljDsum + "," + this.dqJcount + "," + 
			this.dqJsum + "," + this.dqDcount + "," + this.dqDsum + ",\"" + this.remark + "\",\"" + this.backup1 + "\",\"" + 
			this.backup2 + "\"";
	}
}
