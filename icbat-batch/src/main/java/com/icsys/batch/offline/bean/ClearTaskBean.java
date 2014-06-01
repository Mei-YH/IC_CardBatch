package com.icsys.batch.offline.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ClearTaskBean implements Serializable {

	private static final long serialVersionUID = -4013037665921635468L;
	// 清算日期
	private String clearDate;
	// 批次号
	private Integer batchNo;
	// 交易日期
	private String tranDate;
	// 提交机构号
	private String submitBranch;
	// 提交柜员号
	private String submitTeller;
	// 原始文件名
	private String originalFile;
	// 原始文件总笔数
	private Integer originalFileCount;
	// 原始文件总金额
	private BigDecimal originalFileSumAmount;
	// 清算文件名
	private String clearFile;
	// 清算文件总笔数
	private Integer clearFileCount;
	// 清算文件总金额
	private BigDecimal clearFileSumAmount;
	// 失败笔数
	private Integer failCount;
	// 失败金额
	private BigDecimal failAmount;
	// 清算类型
	private String clearType;
	// 阶段
	private String treatmentStage;
	// 状态
	private String status;
	// 提交省市代码
	private String branchPro;

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public Integer getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getSubmitBranch() {
		return submitBranch;
	}

	public void setSubmitBranch(String submitBranch) {
		this.submitBranch = submitBranch;
	}

	public String getSubmitTeller() {
		return submitTeller;
	}

	public void setSubmitTeller(String submitTeller) {
		this.submitTeller = submitTeller;
	}

	public String getOriginalFile() {
		return originalFile;
	}

	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}

	public Integer getOriginalFileCount() {
		return originalFileCount;
	}

	public void setOriginalFileCount(Integer originalFileCount) {
		this.originalFileCount = originalFileCount;
	}

	public BigDecimal getOriginalFileSumAmount() {
		return originalFileSumAmount;
	}

	public void setOriginalFileSumAmount(BigDecimal originalFileSumAmount) {
		this.originalFileSumAmount = originalFileSumAmount;
	}

	public String getClearFile() {
		return clearFile;
	}

	public void setClearFile(String clearFile) {
		this.clearFile = clearFile;
	}

	public Integer getClearFileCount() {
		return clearFileCount;
	}

	public void setClearFileCount(Integer clearFileCount) {
		this.clearFileCount = clearFileCount;
	}

	public BigDecimal getClearFileSumAmount() {
		return clearFileSumAmount;
	}

	public void setClearFileSumAmount(BigDecimal clearFileSumAmount) {
		this.clearFileSumAmount = clearFileSumAmount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public BigDecimal getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(BigDecimal failAmount) {
		this.failAmount = failAmount;
	}

	public String getClearType() {
		return clearType;
	}

	public void setClearType(String clearType) {
		this.clearType = clearType;
	}

	public String getTreatmentStage() {
		return treatmentStage;
	}

	public void setTreatmentStage(String treatmentStage) {
		this.treatmentStage = treatmentStage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBranchPro() {
		return branchPro;
	}

	public void setBranchPro(String branchPro) {
		this.branchPro = branchPro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString(){
		return "ClearTaskBean [clearDate="+clearDate+",batchNo="+batchNo+",tranDate="+tranDate+",submitBranch="+submitBranch
		+",submitTeller="+submitTeller+",originalFile="+originalFile+",originalFileCount="+originalFileCount+",originalFileSumAmount="+originalFileSumAmount
		+",clearFile="+clearFile+",clearFileCount="+clearFileCount+",clearFileSumAmount="+clearFileSumAmount+",failCount="+failCount
		+",failAmount="+failAmount+",clearType="+clearType+",treatmentStage="+treatmentStage+",status="+status
		+",branchPro="+branchPro+ "]";
	}
}
