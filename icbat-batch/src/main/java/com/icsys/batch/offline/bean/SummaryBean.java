package com.icsys.batch.offline.bean;

import java.io.OutputStream;

/**
 * 核心系统汇总入账文件 对应的bean
 * @author Administrator
 * 数据库中对应的表名:IC_CORE_SUMMARY
 */
public class SummaryBean {
	private String batchNo;//批次号
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getBatchSn() {
		return batchSn;
	}
	public void setBatchSn(String batchSn) {
		this.batchSn = batchSn;
	}
	private String batchSn;//批次序号
	private String outAccType;//转出帐户类型
	private String outAcc;//转出帐户
	private String outAccAttr;//转出帐户性质
	private String outCurrency; //转出货币代号
	private String outFCFlag;//转出钞汇标志
	private String outAccName;//转出帐户名称
	private String outOsAcc;//转出对方帐户
	private String inAccType;//转入帐户类型
	private String inAcc;//转入帐户
	private String inAccAttr;//转入帐户性质
	private String inCurrency;//转入货币代号
	private String inFCFlag;//转入钞汇标志
	private String inAccName;//转入帐户名称
	private String inOsAcc;//转入对方帐户
	private String tranAmt;//交易金额
	private String fee;//手续费金额
	private String remark;//备注信息
	private String proFlag;//处理标识（1为已提交，0为未提交）
	private String acctDate;//对账单提交给核心系统的日期
	private String clearDate;//清算日期
	private String coreDate;//核心处理日期
	private String coreSerial;//核心流水号
	private String acctBranch;//账务机构
	private String coreStatus;//核心处理状态
	private String errorReason;//错误原因
	private String errorMsg;//错误信息
	public String getOutAccType() {
		return outAccType;
	}
	public void setOutAccType(String outAccType) {
		this.outAccType = outAccType;
	}
	public String getOutAcc() {
		return outAcc;
	}
	public void setOutAcc(String outAcc) {
		this.outAcc = outAcc;
	}
	public String getOutAccAttr() {
		return outAccAttr;
	}
	public void setOutAccAttr(String outAccAttr) {
		this.outAccAttr = outAccAttr;
	}
	public String getOutCurrency() {
		return outCurrency;
	}
	public void setOutCurrency(String outCurrency) {
		this.outCurrency = outCurrency;
	}
	public String getOutFCFlag() {
		return outFCFlag;
	}
	public void setOutFCFlag(String outFCFlag) {
		this.outFCFlag = outFCFlag;
	}
	public String getOutAccName() {
		return outAccName;
	}
	public void setOutAccName(String outAccName) {
		this.outAccName = outAccName;
	}
	public String getOutOsAcc() {
		return outOsAcc;
	}
	public void setOutOsAcc(String outOsAcc) {
		this.outOsAcc = outOsAcc;
	}
	public String getInAccType() {
		return inAccType;
	}
	public void setInAccType(String inAccType) {
		this.inAccType = inAccType;
	}
	public String getInAcc() {
		return inAcc;
	}
	public void setInAcc(String inAcc) {
		this.inAcc = inAcc;
	}
	public String getInAccAttr() {
		return inAccAttr;
	}
	public void setInAccAttr(String inAccAttr) {
		this.inAccAttr = inAccAttr;
	}
	public String getInCurrency() {
		return inCurrency;
	}
	public void setInCurrency(String inCurrency) {
		this.inCurrency = inCurrency;
	}
	public String getInFCFlag() {
		return inFCFlag;
	}
	public void setInFCFlag(String inFCFlag) {
		this.inFCFlag = inFCFlag;
	}
	public String getInAccName() {
		return inAccName;
	}
	public void setInAccName(String inAccName) {
		this.inAccName = inAccName;
	}
	public String getInOsAcc() {
		return inOsAcc;
	}
	public void setInOsAcc(String inOsAcc) {
		this.inOsAcc = inOsAcc;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProFlag() {
		return proFlag;
	}
	public void setProFlag(String proFlag) {
		this.proFlag = proFlag;
	}
	public String getAcctDate() {
		return acctDate;
	}
	public void setAcctDate(String acctDate) {
		this.acctDate = acctDate;
	}

	public String getClearDate() {
		return clearDate;
	}
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
	
	public String getCoreDate() {
		return coreDate;
	}
	public void setCoreDate(String coreDate) {
		this.coreDate = coreDate;
	}
	public String getCoreSerial() {
		return coreSerial;
	}
	public void setCoreSerial(String coreSerial) {
		this.coreSerial = coreSerial;
	}
	public String getAcctBranch() {
		return acctBranch;
	}
	public void setAcctBranch(String acctBranch) {
		this.acctBranch = acctBranch;
	}
	public String getCoreStatus() {
		return coreStatus;
	}
	public void setCoreStatus(String coreStatus) {
		this.coreStatus = coreStatus;
	}
	
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public  String getSumFileString(){
		StringBuffer strb = new StringBuffer();
		strb.append((this.getOutAccType()==null?"":this.getOutAccType())+"|")//转出帐户类型
		.append((this.getOutAcc()==null?"":this.getOutAcc())+"|")//转出帐户
		.append((this.getOutAccAttr()==null?"":this.getOutAccAttr())+"|")//转出帐户性质
		.append((this.getOutCurrency()==null?"":this.getOutCurrency())+"|")//转出货币代号
		.append((this.getOutFCFlag()==null?"":this.getOutFCFlag())+"|")//转出钞汇标志
		.append((this.getOutAccName()==null?"":this.getOutAccName())+"|")//转出帐户名称
		.append((this.getOutOsAcc()==null?"":this.getOutOsAcc())+"|")//转出对方帐户
		.append((this.getInAccType()==null?"":this.getInAccType())+"|")//转入帐户类型
		.append((this.getInAcc()==null?"":this.getInAcc())+"|")//转入帐户
		
		.append((this.getInAccAttr()==null?"":this.getInAccAttr())+"|")//转入帐户性质
		.append((this.getInCurrency()==null?"":this.getInCurrency())+"|")//转入货币代号
		
		.append((this.getInFCFlag()==null?"":this.getInFCFlag())+"|")//转入钞汇标志
		.append((this.getInAccName()==null?"":this.getInAccName())+"|")//转入帐户名称
		.append((this.getInOsAcc()==null?"":this.getInOsAcc())+"|")//转入对方帐户
		.append((this.getTranAmt()==null?"":this.getTranAmt())+"|")//交易金额
		.append((this.getFee()==null?"":this.getFee())+"|")//手续费金额
		.append((this.getRemark()==null?"":this.getRemark())+"|");//备注信息
		return strb.toString();
	}
	public void assemble(OutputStream out,String charset) throws Exception{
		out.write((this.getOutAccType()==null?"":this.getOutAccType()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getOutAcc()==null?"":this.getOutAcc()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getOutAccAttr()==null?"":this.getOutAccAttr()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getOutCurrency()==null?"":this.getOutCurrency()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getOutFCFlag()==null?"":this.getOutFCFlag()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getOutAccName()==null?"":this.getOutAccName()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getOutOsAcc()==null?"":this.getOutOsAcc()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getInAccType()==null?"":this.getInAccType()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getInAcc()==null?"":this.getInAcc()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getInAccAttr()==null?"":this.getInAccAttr()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getInCurrency()==null?"":this.getInCurrency()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getInFCFlag()==null?"":this.getInFCFlag()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getInAccName()==null?"":this.getInAccName()).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getInOsAcc()==null?"":this.getInOsAcc()).getBytes(charset));
		out.write("|".getBytes(charset));
		//TODO
		out.write((String.format("%.2f", Double.valueOf(this.getTranAmt()==null?"0":this.getTranAmt()))).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((String.format("%.2f", Double.valueOf(this.getFee()==null?"0":this.getFee()))).getBytes(charset));
		out.write("|".getBytes(charset));
		out.write((this.getRemark()==null?"":this.getRemark()).getBytes(charset));
		out.write("|".getBytes(charset));
		//TODO 预留字段
		out.write("|".getBytes(charset));
		out.write(new byte[]{10});
	}
}
