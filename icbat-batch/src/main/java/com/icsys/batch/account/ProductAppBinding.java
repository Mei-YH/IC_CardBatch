package com.icsys.batch.account;

/**
 * 产品应用绑定类
 * 
 * @author kitty
 * 
 */
public class ProductAppBinding {

	/**
	 * 产品号 (主键) PRODUCT_NO(6 not null)
	 */
	private String productNo;

	/**
	 * 应用编号 (主键) AID(16 not null)
	 */
	private String aid;

	/**
	 * 应用核算类型 '0'-共享核算账户 '1'-主应用 APPLICATION_ACCOUNT_TYPE(1 not null)
	 */
	private String applicationAccountType;

	/**
	 * 主要应用AID(应用核算类型为0有效) MAIN_AID(16 null)
	 */
	private String mainAid;

	/**
	 * 项目主管机构 BRANCH_CODE(11 null)
	 */
	private String branchCode;

	/**
	 * 机构账号类型 '0'-全行应用 '1'-专管 BRANCH_FLAG(1 not null)
	 */
	private String branchFlag;

	/**
	 * 主管行平台核算账号 PLATFORM_ACCT_NO(32 not null)
	 */
	private String platformAcctNo;

	/**
	 * 主管行核心核算账号 (标准应用为账号后缀) HOST_ACCT_NO(32 not null)
	 */
	private String hostAcctNo;

	/**
	 * 手续费核算账号 FEE_ACCT_NO(32 null)
	 */
	private String feeAcctNo;

	/**
	 * 挂账账号 LOSE_ACCT_NO(32 null)
	 */
	private String loseAcctNo;

	/**
	 * 状态 '0'-正常，'1'-暂停，'2'-取消 STATUS(1 not nul)
	 */
	private String status;

	/**
	 * 校验位 DAC(32 not null)
	 */
	private String dac;

	// ------------------------------------

	public String getProductNo() {
		return productNo;
	}

	/**
	 * 产品号 (主键) PRODUCT_NO(6 not null)
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getAid() {
		return aid;
	}

	/**
	 * 应用编号 (主键) APPLICATION_NO(16 not null)
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getApplicationAccountType() {
		return applicationAccountType;
	}

	/**
	 * 应用核算类型 '0'-共享核算账户 '1'-主应用 APPLICATION_ACCOUNT_TYPE(1 not null)
	 */
	public void setApplicationAccountType(String applicationAccountType) {
		this.applicationAccountType = applicationAccountType;
	}

	public String getMainAid() {
		return mainAid;
	}

	/**
	 * 主要应用AID(应用核算类型为0有效) MAIN_AID(16 null)
	 */
	public void setMainAid(String mainAid) {
		this.mainAid = mainAid;
	}

	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * 项目主管机构 BRANCH_CODE(11 null)
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchFlag() {
		return branchFlag;
	}

	/**
	 * 机构账号类型 '0'-全行应用 '1'-专管 BRANCH_FLAG(1 not null)
	 */
	public void setBranchFlag(String branchFlag) {
		this.branchFlag = branchFlag;
	}

	public String getPlatformAcctNo() {
		return platformAcctNo;
	}

	/**
	 * 主管行平台核算账号 PLATFORM_ACCT_NO(32 not null)
	 */
	public void setPlatformAcctNo(String platformAcctNo) {
		this.platformAcctNo = platformAcctNo;
	}

	public String getHostAcctNo() {
		return hostAcctNo;
	}

	/**
	 * 主管行核心核算账号 (标准应用为账号后缀) HOST_ACCT_NO(32 not null)
	 */
	public void setHostAcctNo(String hostAcctNo) {
		this.hostAcctNo = hostAcctNo;
	}

	public String getFeeAcctNo() {
		return feeAcctNo;
	}

	/**
	 * 手续费核算账号 FEE_ACCT_NO(32 null)
	 */
	public void setFeeAcctNo(String feeAcctNo) {
		this.feeAcctNo = feeAcctNo;
	}

	public String getLoseAcctNo() {
		return loseAcctNo;
	}

	/**
	 * 挂账账号 LOSE_ACCT_NO(32 null)
	 */
	public void setLoseAcctNo(String loseAcctNo) {
		this.loseAcctNo = loseAcctNo;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * 状态 '0'-正常，'1'-暂停，'2'-取消 STATUS(1 not nul)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDac() {
		return dac;
	}

	/**
	 * 校验位 DAC(32 not null)
	 */
	public void setDac(String dac) {
		this.dac = dac;
	}

	// ------------------------------------

	/**
	 * 枚举-应用核算类型 '0'-共享核算账户 '1'-主应用
	 */
	public enum ApplicationAccountType {
				/**
		 * '0'-共享核算账户
		 */
		SHARE_ACCOUNTING_ACCT("0"),

		/**
		 * '1'-主应用
		 */
		MAIN_APPLICATION("1");

		private final String accountType;

		ApplicationAccountType(String accountType) {
			this.accountType = accountType;
		}

		public String getAccountType() {
			return this.accountType;
		}
	}

	/**
	 * 检查应用核算类型是否为共享核算账户('0'-共享核算账户)
	 */
	public boolean isShareAccountingAcct() {
		return ApplicationAccountType.SHARE_ACCOUNTING_ACCT.getAccountType()
				.equals(this.applicationAccountType);
	}

	/**
	 * 枚举-机构账号类型 '0'-全行应用 '1'-专管
	 */
	public enum BranchFlag {
				/**
		 * '0'-全行应用
		 */
		ALL_BANK_APPLICATION("0"),

		/**
		 * '1'-专管
		 */
		SPECIAL_MANAGE("1");

		private final String flag;

		BranchFlag(String flag) {
			this.flag = flag;
		}

		public String getFlag() {
			return this.flag;
		}
	}

	/**
	 * 检查机构账号类型是否为全行应用
	 */
	public boolean isAllBankApplication() {
		return BranchFlag.ALL_BANK_APPLICATION.getFlag()
				.equals(this.branchFlag);
	}

	/**
	 * 枚举-应用状态 '0'-正常 '1'-暂停 '2'-取消
	 */
	public enum Status {
				/**
		 * '0'-正常
		 */
		NORMAL("0"),
				/**
		 * '1'-暂停
		 */
		PAUSE("1"),
				/**
		 * '2'-取消
		 */
		CANCEL("2");

		private final String status;

		Status(String status) {
			this.status = status;
		}

		public String getStatus() {
			return this.status;
		}
	}

	/**
	 * 检查状态是否为正常
	 * 
	 * @return true-为正常
	 */
	public boolean isNormalStatus() {
		return Status.NORMAL.getStatus().equals(this.status);
	}

	/**
	 * 检查状态是否为暂停
	 * 
	 * @return true-为暂停
	 */
	public boolean isPauseStatus() {
		return Status.PAUSE.getStatus().equals(this.status);
	}

	/**
	 * 检查状态是否为取消
	 * 
	 * @return true-为取消
	 */
	public boolean isCancelStatus() {
		return Status.CANCEL.getStatus().equals(this.status);
	}

	@Override
	public String toString() {
		return "IcProdApp [" +
				"productNo=" + productNo +
				",aid=" + aid +
				",applicationAccountType=" + applicationAccountType +
				",mainAid=" + mainAid +
				",branchCode=" + branchCode +
				",branchFlag=" + branchFlag +
				",platformAcctNo=" + platformAcctNo +
				",hostAcctNo=" + hostAcctNo +
				",feeAcctNo=" + feeAcctNo +
				",loseAcctNo=" + loseAcctNo +
				",status=" + status +
				",dac=" + dac +
				"]";
	}

	/**
	 * 生成DAC
	 */
	public String toDac() {
		return "00000000000000000000000000000000";
	}

}
