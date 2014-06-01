package com.icsys.batch.checkAccount;

import java.math.BigDecimal;

/**
 * @author huangbaofa  
 * @version 创建时间：2011-5-13 下午04:05:27 
 * 类说明 ： 交易流水记录
 *20111013 增加卡序号
 */
public class IcTxList {

	/**
	 * 交易日期
	 */
	private String tranDate;
	/**
	 * IC卡平台核心流水号
	 */
	private String serial;
	/**
	 * 平台流水
	 */
	private String platformSerial;
	/**
	 * 主机流水号
	 */
	private String hostSerial;
	/**
	 * 交易机构号
	 */
	private String tranBranch;
	/**
	 * 交易卡号
	 */
	private String cardNo;
	/**
	 * 交易卡序号
	 */
	private String cardIndex;
	/**
	 * 交易前卡内余额
	 */
	private BigDecimal startAmount;
	/**
	 * 交易标志
	 */
	private String tranFlag;
	/**
	 * 对账标志
	 */
	private String reconciliationLogo;
	/**
	 * 交易金额
	 */
	private BigDecimal tranAmount;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * 币种
	 */
	private String currType;
	/**
	 * 交易账号
	 */
	private String acctNo;
	/**
	 * 主账户借贷标志
	 */
	private String acctCd;
	/**
	 * 对方卡号
	 */
	private String cardNo1;
	/**
	 * 对方账户账号
	 */
	private String acctNo1;
	/**
	 * 对方账户记账金额
	 */
	private BigDecimal acctAmount1;
	/**
	 * 对方账户标志
	 */
	private String acctFlag;
	/**
	 * 主机交易帐号
	 */
	private String hostTranAcctNo;
	/**
	 * 交易状态
	 */
	private String tranStatus;
	
	/**
	 * 核心冲正记录 CORE_REVERSAL_RECORD　char(1)null
	 */
	private String coreReversalRecord;
	
	private Integer rowCount;
	
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getPlatformSerial() {
		return platformSerial;
	}
	public void setPlatformSerial(String platformSerial) {
		this.platformSerial = platformSerial;
	}
	public String getHostSerial() {
		return hostSerial;
	}
	public void setHostSerial(String hostSerial) {
		this.hostSerial = hostSerial;
	}
	public String getTranBranch() {
		return tranBranch;
	}
	public void setTranBranch(String tranBranch) {
		this.tranBranch = tranBranch;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardIndex() {
		return cardIndex;
	}
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}
	public String getTranFlag() {
		return tranFlag;
	}
	public void setTranFlag(String tranFlag) {
		this.tranFlag = tranFlag;
	}
	public String getReconciliationLogo() {
		return reconciliationLogo;
	}
	public void setReconciliationLogo(String reconciliationLogo) {
		this.reconciliationLogo = reconciliationLogo;
	}
	public String getCurrType() {
		return currType;
	}
	public void setCurrType(String currType) {
		this.currType = currType;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctCd() {
		return acctCd;
	}
	public void setAcctCd(String acctCd) {
		this.acctCd = acctCd;
	}
	public String getCardNo1() {
		return cardNo1;
	}
	public void setCardNo1(String cardNo1) {
		this.cardNo1 = cardNo1;
	}
	public String getAcctNo1() {
		return acctNo1;
	}
	public void setAcctNo1(String acctNo1) {
		this.acctNo1 = acctNo1;
	}
	
	public BigDecimal getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}
	public BigDecimal getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getAcctAmount1() {
		return acctAmount1;
	}
	public void setAcctAmount1(BigDecimal acctAmount1) {
		this.acctAmount1 = acctAmount1;
	}
	public String getAcctFlag() {
		return acctFlag;
	}
	public void setAcctFlag(String acctFlag) {
		this.acctFlag = acctFlag;
	}
	public String getHostTranAcctNo() {
		return hostTranAcctNo;
	}
	public void setHostTranAcctNo(String hostTranAcctNo) {
		this.hostTranAcctNo = hostTranAcctNo;
	}
	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}
	public String getCoreReversalRecord() {
		return coreReversalRecord;
	}
	
	/**
	 * 核心冲正记录 CORE_REVERSAL_RECORD　char(1)null
	 */
	public void setCoreReversalRecord(String coreReversalRecord) {
		this.coreReversalRecord = coreReversalRecord;
	}
	@Override
	public String toString() {
		return "IcTxList [acctAmount1=" + acctAmount1 + ", acctCd=" + acctCd
				+ ", acctFlag=" + acctFlag + ", acctNo=" + acctNo
				+ ", acctNo1=" + acctNo1 + ", cardNo=" + cardNo + ", cardIndex=" + cardIndex + ", cardNo1="
				+ cardNo1 + ", coreReversalRecord=" + coreReversalRecord
				+ ", currType=" + currType + ", fee=" + fee + ", hostSerial="
				+ hostSerial + ", hostTranAcctNo=" + hostTranAcctNo
				+ ", platformSerial=" + platformSerial
				+ ", reconciliationLogo=" + reconciliationLogo + ", rowCount="
				+ rowCount + ", serial=" + serial + ", startAmount="
				+ startAmount + ", tranAmount=" + tranAmount + ", tranBranch="
				+ tranBranch + ", tranDate=" + tranDate + ", tranFlag="
				+ tranFlag + ", tranStatus=" + tranStatus + "]";
	}
	
	
	
	
}
