package com.icsys.batch.task.profit.proxy.bean;
/**
 * 异常卡登记簿
 * @author SDNX
 *
 */
public class IcCardabnReq {
	private String ic_No;//IC卡卡号
	private String ic_Index;//应用卡序号
	private String tx_Dt;//首次处理日期
	private String tx_Tm;//首次处理时间
	private String tx_Seqno;//流水号
	private String branch_Pro;//机构省市代码
	private String branch;//处理机构
	private String teller_No;//处理操作员
	private Integer abn_Stat;//异常状态
	private Integer end_Stat;//后续处理状态
	private String end_Dt;//后续处理日期
	private String end_Tm;//后续处理时间
	private String end_Seqno;//后续处理流水号
	private String end_Branch_Code;//后续处理机构
	private String end_Teller_No;//后续处理操作员
	private String new_Ic_No;//IC卡新卡卡号
	private String reason;//异常处理原因说明
	
	public String getIc_No() {
		return ic_No;
	}
	public void setIc_No(String icNo) {
		ic_No = icNo;
	}
	public String getIc_Index() {
		return ic_Index;
	}
	public void setIc_Index(String icIndex) {
		ic_Index = icIndex;
	}
	public String getTx_Dt() {
		return tx_Dt;
	}
	public void setTx_Dt(String txDt) {
		tx_Dt = txDt;
	}
	public String getTx_Tm() {
		return tx_Tm;
	}
	public void setTx_Tm(String txTm) {
		tx_Tm = txTm;
	}
	public String getTx_Seqno() {
		return tx_Seqno;
	}
	public void setTx_Seqno(String txSeqno) {
		tx_Seqno = txSeqno;
	}
	public String getBranch_Pro() {
		return branch_Pro;
	}
	public void setBranch_Pro(String branchPro) {
		branch_Pro = branchPro;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getTeller_No() {
		return teller_No;
	}
	public void setTeller_No(String tellerNo) {
		teller_No = tellerNo;
	}

	public Integer getAbn_Stat() {
		return abn_Stat;
	}
	public void setAbn_Stat(Integer abnStat) {
		abn_Stat = abnStat;
	}
	public Integer getEnd_Stat() {
		return end_Stat;
	}
	public void setEnd_Stat(Integer endStat) {
		end_Stat = endStat;
	}
	public String getEnd_Dt() {
		return end_Dt;
	}
	public void setEnd_Dt(String endDt) {
		end_Dt = endDt;
	}
	public String getEnd_Tm() {
		return end_Tm;
	}
	public void setEnd_Tm(String endTm) {
		end_Tm = endTm;
	}
	public String getEnd_Seqno() {
		return end_Seqno;
	}
	public void setEnd_Seqno(String endSeqno) {
		end_Seqno = endSeqno;
	}
	public String getEnd_Branch_Code() {
		return end_Branch_Code;
	}
	public void setEnd_Branch_Code(String endBranchCode) {
		end_Branch_Code = endBranchCode;
	}
	public String getEnd_Teller_No() {
		return end_Teller_No;
	}
	public void setEnd_Teller_No(String endTellerNo) {
		end_Teller_No = endTellerNo;
	}
	public String getNew_Ic_No() {
		return new_Ic_No;
	}
	public void setNew_Ic_No(String newIcNo) {
		new_Ic_No = newIcNo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "IcCardabnReq [ic_No=" + ic_No + ", ic_Index="+ ic_Index +",tx_Dt="+ tx_Dt +", tx_Tm="
				+ tx_Tm + ", tx_Seqno=" + tx_Seqno + ", branch_Pro="
				+ branch_Pro + ", branch=" + branch + ", teller_No="
				+ teller_No + ", abn_Stat=" + abn_Stat + ", end_Stat=" + end_Stat
				+ ", end_Dt=" + end_Dt + ", end_Tm=" + end_Tm
				+ ", end_Seqno=" + end_Seqno + ", end_Branch_Code="
				+ end_Branch_Code + ", end_Teller_No="+ end_Teller_No+" , new_Ic_No="+new_Ic_No +" ,reason="+reason +"]";
	}
	

}
