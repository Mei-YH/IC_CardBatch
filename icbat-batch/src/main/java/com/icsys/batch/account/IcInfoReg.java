package com.icsys.batch.account;
/** 
 * @author huangbaofa  
 * @version 创建时间：2011-4-12 上午11:10:22 
 * 类说明 ：对应IC_INFO_REG  IC卡登记簿
 */

public class IcInfoReg {

	/**
	*IC卡卡号
	*/
	private String cardNo;
	
	/**
	*IC卡卡序号
	*/
	private String cardIndex;
	
	/**
	 * IC卡产品号
	 */
	private String productNo;
	
	
	/**
	 * 发卡行
	 */
	private String issuerBranch;
	
	/**
	 * IC卡账户账号
	 */
	private String acctNoId;
	
	/**
	 * 持卡人客户号
	 */
	private String customerNo;
	
	/**
	 * 该卡年费参数
	 */
	private Integer annualFee;
	
	/**
	 * 磁条卡卡号
	 */
	private String magneticStripeCardNo;
	
	/**
	 * 附卡标志	
	 */
	private String supplementCardFlag;
	
	/**
	 * IC卡特性标志
	 */
	private String carterFlag;
	
	/**
	 * IC卡PIN码密文
	 */
	private String pinData;
	
	/**
	 * IC卡状态
	 */
	private String icStatus;
	
	/**
	 * 磁条卡状态
	 */
	private String magneticStatus;
	
	/**
	 * 发卡日期
	 */
	private String issuerDate;
	
	/**
	 * 状态变更日期
	 */
	private String statusChangeDate;
	
	/**
	 * 销卡日期
	 */
	private String cancelDate;
	
	/**
	 * 卡有效期限
	 */
	private String cardValidDate;
	
	/**
	 * 证书有效期
	 */
	private String certificateValidDate;
	
	/**
	 * 正常回收次数
	 */
	private Integer reclaimNumber;
	
	/**
	 * IC卡密码管理
	 */
	private String passwordManage;
	
	/**
	 * 校验位
	 */
	private String dac;

	//20110920 modify by pfq
	/**
	 * 标志位
	 */
	private String paramModFlag;
	

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}


	

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public String getAcctNoId() {
		return acctNoId;
	}

	public String getIssuerBranch() {
		return issuerBranch;
	}

	public void setIssuerBranch(String issuerBranch) {
		this.issuerBranch = issuerBranch;
	}

	public void setAcctNoId(String acctNoId) {
		this.acctNoId = acctNoId;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public Integer getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(Integer annualFee) {
		this.annualFee = annualFee;
	}

	public String getMagneticStripeCardNo() {
		return magneticStripeCardNo;
	}

	public void setMagneticStripeCardNo(String magneticStripeCardNo) {
		this.magneticStripeCardNo = magneticStripeCardNo;
	}

	public String getSupplementCardFlag() {
		return supplementCardFlag;
	}

	public void setSupplementCardFlag(String supplementCardFlag) {
		this.supplementCardFlag = supplementCardFlag;
	}

	public String getCarterFlag() {
		return carterFlag;
	}

	public void setCarterFlag(String carterFlag) {
		this.carterFlag = carterFlag;
	}

	public String getPinData() {
		return pinData;
	}

	public void setPinData(String pinData) {
		this.pinData = pinData;
	}


	public String getIcStatus() {
		return icStatus;
	}

	public void setIcStatus(String icStatus) {
		this.icStatus = icStatus;
	}

	public String getMagneticStatus() {
		return magneticStatus;
	}

	public void setMagneticStatus(String magneticStatus) {
		this.magneticStatus = magneticStatus;
	}

	public String getIssuerDate() {
		return issuerDate;
	}

	public void setIssuerDate(String issuerDate) {
		this.issuerDate = issuerDate;
	}

	public String getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(String statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCardValidDate() {
		return cardValidDate;
	}

	public void setCardValidDate(String cardValidDate) {
		this.cardValidDate = cardValidDate;
	}

	public String getCertificateValidDate() {
		return certificateValidDate;
	}

	public void setCertificateValidDate(String certificateValidDate) {
		this.certificateValidDate = certificateValidDate;
	}

	public Integer getReclaimNumber() {
		return reclaimNumber;
	}

	public void setReclaimNumber(Integer reclaimNumber) {
		this.reclaimNumber = reclaimNumber;
	}

	public String getPasswordManage() {
		return passwordManage;
	}

	public void setPasswordManage(String passwordManage) {
		this.passwordManage = passwordManage;
	}

	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
//20110920 modify by pfq
	public String getParamModFlag() {
		return paramModFlag;
	}

	public void setParamModFlag(String paramModFlag) {
		this.paramModFlag = paramModFlag;
	}

	@Override
	public String toString() {
		return "IcInfoReg [acctNoId=" + acctNoId + ", annualFee=" + annualFee
				+ ", cancelDate=" + cancelDate + ", cardIndex=" + cardIndex
				+ ", cardNo=" + cardNo + ", cardValidDate=" + cardValidDate
				+ ", carterFlag=" + carterFlag + ", certificateValidDate="
				+ certificateValidDate + ", customerNo=" + customerNo
				+ ", dac=" + dac + ", icStatus=" + icStatus + ", issuerBranch="
				+ issuerBranch + ", issuerDate=" + issuerDate
				+ ", magneticStatus=" + magneticStatus
				+ ", magneticStripeCardNo=" + magneticStripeCardNo
				+ ", paramModFlag=" + paramModFlag + ", passwordManage="
				+ passwordManage + ", pinData=" + pinData + ", productNO="
				+ productNo + ", reclaimNumber=" + reclaimNumber
				+ ", statusChangeDate=" + statusChangeDate
				+ ", supplementCardFlag=" + supplementCardFlag + "]";
	}

	
}
