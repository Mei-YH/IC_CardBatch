package com.icsys.batch.checkAccount;

import java.math.BigDecimal;

public class IcCheckAccountTxList {
	/**
	 * 交易日期
	 */
	private String tranDate;
	/**
	 * IC卡平台核心流水号
	 */
	private String icSerial;
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
	
	
	
	/**
	 * 核心日期
	 */
	private String hostDate;
	
	
	/**
	 * 交易功能
	 */
	private String tranFanction;

	/**
	*写卡操作
	*/
	private String writeCardState;
	
	/**
	*交易摘要
	*/
	private String remark;

	/**
	*执行结果
	*/
	private String commandResults;
	
	
	/**
	*渠道编号
	*/
	private String channelNo;

	/**
	 * 渠道交易日期
	 */
	private String channelDate;
	
	/**
	*渠道流水号
	*/
	private String channelSerial;


	/**
	 * 柜员号
	 */
	private String operator;
	
	/**
	 * 终端号
	 */
	private String terminalNo;
	
	/**
	 * 复核员
	 */
	private String checkTeller;
	
	/**
	 * 授权员
	 */
	private String authorizeTeller;
	
	
	/**
	 * 
	 */
	private String coreReversalRecord;
	
	private String tranTime;

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getCoreReversalRecord() {
		return coreReversalRecord;
	}

	public void setCoreReversalRecord(String coreReversalRecord) {
		this.coreReversalRecord = coreReversalRecord;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getCheckTeller() {
		return checkTeller;
	}

	public void setCheckTeller(String checkTeller) {
		this.checkTeller = checkTeller;
	}

	public String getAuthorizeTeller() {
		return authorizeTeller;
	}

	public void setAuthorizeTeller(String authorizeTeller) {
		this.authorizeTeller = authorizeTeller;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getIcSerial() {
		return icSerial;
	}

	public void setIcSerial(String icSerial) {
		this.icSerial = icSerial;
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

	public BigDecimal getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
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

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getChannelDate() {
		return channelDate;
	}

	public void setChannelDate(String channelDate) {
		this.channelDate = channelDate;
	}

	public String getChannelSerial() {
		return channelSerial;
	}

	public void setChannelSerial(String channelSerial) {
		this.channelSerial = channelSerial;
	}

	public String getHostDate() {
		return hostDate;
	}

	public void setHostDate(String hostDate) {
		this.hostDate = hostDate;
	}

	public String getTranFanction() {
		return tranFanction;
	}

	public void setTranFanction(String tranFanction) {
		this.tranFanction = tranFanction;
	}

	public String getWriteCardState() {
		return writeCardState;
	}

	public void setWriteCardState(String writeCardState) {
		this.writeCardState = writeCardState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCommandResults() {
		return commandResults;
	}

	public void setCommandResults(String commandResults) {
		this.commandResults = commandResults;
	}

	public String toString(){
		return "";
	}
}
