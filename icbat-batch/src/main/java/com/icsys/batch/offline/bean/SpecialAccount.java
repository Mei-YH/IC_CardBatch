package com.icsys.batch.offline.bean;

/**
 * 内部核算专用账户,包含比如银联账户等
 * @author Administrator
 *
 */
public class SpecialAccount {
	/**
	 * 用途索引
	 */
	private String useIndex;
	
	/**
	 * 用途描述
	 */
	private String useDescription;
	
	/**
	 * 账号
	 */
	private String acctNo;
	
	/**
	 * 使用机构
	 */
	private String branchCode;
	
	public String getUseIndex() {
		return useIndex;
	}
	public void setUseIndex(String useIndex) {
		this.useIndex = useIndex;
	}
	public String getUseDescription() {
		return useDescription;
	}
	public void setUseDescription(String useDescription) {
		this.useDescription = useDescription;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
}
