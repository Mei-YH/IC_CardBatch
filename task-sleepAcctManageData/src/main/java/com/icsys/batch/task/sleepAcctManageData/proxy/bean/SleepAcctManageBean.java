package com.icsys.batch.task.sleepAcctManageData.proxy.bean;

/**
 * 睡眠账户批处理扣收明细表GR_SLEEP_ACCT_DETAIL
 * @author SDNX
 *
 */
public class SleepAcctManageBean {
	private String batchDt;
	private Integer batchNo;
	private Integer batchSn;              
	private String icNo;             
	private String icCardStat;       
	private String icSleepTime;      
	private String icAcctBalance;    
	private String icRemoveBalance;  
	private String coreRemoveStat;   
	private String coreFailReason;   
	private String icDate;           
	private String coreDate;
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
	public String getIcNo() {
		return icNo;
	}
	public String getIcPro() {
		return icPro;
	}
	public String getIcRemoveBalance() {
		return icRemoveBalance;
	}
	public String getIcSleepTime() {
		return icSleepTime;
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
	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}
	public void setIcPro(String icPro) {
		this.icPro = icPro;
	}
	public void setIcRemoveBalance(String icRemoveBalance) {
		this.icRemoveBalance = icRemoveBalance;
	}
	public void setIcSleepTime(String icSleepTime) {
		this.icSleepTime = icSleepTime;
	}
	public String getIcBranch() {
		return icBranch;
	}
	public void setIcBranch(String icBranch) {
		this.icBranch = icBranch;
	}

}
