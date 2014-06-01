package com.icsys.batch.task.report.proxy.bean;

public class GrIcgqzsyDet {

	private String batchDt;
	private Integer batchNo;
	private Integer batchSn;                       
	private String	icNo;                      
	private String	icCardStat;                
	private String	icDestroyTime;             
	private String	icAcctBalance;             
	private String	icProfitBalance;           
	private String	coreRemoveStat;            
	private String	coreFailReason;            
	private String	icDate;                    
	private String	coreDate;
	private String icPro;
	private String icBranch;
	public String getBatchDt() {
		return batchDt;
	}
	public Integer getBatchNo() {
		return batchNo;
	}
	public Integer getBatchSn() {
		return batchSn;
	}
	public String getCoreDate() {
		return coreDate;
	}
	public String getCoreFailReason() {
		return coreFailReason;
	}
	public String getCoreRemoveStat() {
		return coreRemoveStat;
	}
	public String getIcAcctBalance() {
		return icAcctBalance;
	}
	public String getIcCardStat() {
		return icCardStat;
	}
	public String getIcDate() {
		return icDate;
	}
	public String getIcDestroyTime() {
		return icDestroyTime;
	}
	public String getIcNo() {
		return icNo;
	}
	public String getIcPro() {
		return icPro;
	}
	public String getIcProfitBalance() {
		return icProfitBalance;
	}
	public void setBatchDt(String batchDt) {
		this.batchDt = batchDt;
	}
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	public void setBatchSn(Integer batchSn) {
		this.batchSn = batchSn;
	}
	public void setCoreDate(String coreDate) {
		this.coreDate = coreDate;
	}
	public void setCoreFailReason(String coreFailReason) {
		this.coreFailReason = coreFailReason;
	}
	public void setCoreRemoveStat(String coreRemoveStat) {
		this.coreRemoveStat = coreRemoveStat;
	}
	public void setIcAcctBalance(String icAcctBalance) {
		this.icAcctBalance = icAcctBalance;
	}
	public void setIcCardStat(String icCardStat) {
		this.icCardStat = icCardStat;
	}
	public void setIcDate(String icDate) {
		this.icDate = icDate;
	}
	public void setIcDestroyTime(String icDestroyTime) {
		this.icDestroyTime = icDestroyTime;
	}
	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}
	public void setIcPro(String icPro) {
		this.icPro = icPro;
	}
	public void setIcProfitBalance(String icProfitBalance) {
		this.icProfitBalance = icProfitBalance;
	}
         
	
	public String toString(){
		return ("" + this.batchNo + "," + this.batchSn + ",\"" + this.batchDt + "\",\"" + this.icNo + "\",\"" + this.icCardStat + "\",\"" + 
			this.icDestroyTime + "\",\"" + this.icAcctBalance + "\",\"" + this.icProfitBalance + "\",\"" + this.coreRemoveStat + "\",\"" + 
			this.coreFailReason + "\",\"" + this.icDate + "\",\"" + this.coreDate + "\",\"" + this.icPro + "\",\"" + 
			this.icBranch + "\"").replaceAll("\"null\"", "").replaceAll("\"NULL\"", "");
	}
	public String getIcBranch() {
		return icBranch;
	}
	public void setIcBranch(String icBranch) {
		this.icBranch = icBranch;
	}
}
