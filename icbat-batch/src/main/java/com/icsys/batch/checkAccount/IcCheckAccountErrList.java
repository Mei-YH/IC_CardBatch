package com.icsys.batch.checkAccount;

import java.math.BigDecimal;

public class IcCheckAccountErrList {
	/**
	 * 交易日期
	 */
	private String cheTrdate;
	/**
	 * IC卡平台核心流水号
	 */
	private String cheSerial;
	/**
	 * 平台流水
	 */
	private String chePlserial;
	/**
	 * 主机流水号
	 */
	private String cheHserial;
	/**
	 * 交易机构号
	 */
	private String cheTrbranch;
	/**
	 * 交易卡号
	 */
	private String icCardno;
	/**
	 * 卡序号
	 */
	private String cardIndex;
	/**
	 * 交易前卡内余额
	 */
	private BigDecimal cheStamount;
	/**
	 * 交易标志
	 */
	private String cheTranFlag;
	/**
	 * 对账标志
	 */
	private String cheReclogo;
	/**
	 * 交易金额
	 */
	private BigDecimal cheTramount;
	/**
	 * 手续费
	 */
	private BigDecimal cheFee;
	/**
	 * 币种
	 */
	private String cheCurrtype;
	/**
	 * 交易账号
	 */
	private String cheAcctno;
	/**
	 * 主账户借贷标志
	 */
	private String lendingSign;
	/**
	 * 对方卡号
	 */
	private String cheCardno1;
	/**
	 * 对方账户账号
	 */
	private String cheAcctno1;
	/**
	 * 对方账户记账金额
	 */
	private BigDecimal cheAccamount1;
	/**
	 * 对方账户标志
	 */
	private String cheAccflag;
	/**
	 * 主机交易帐号
	 */
	private String cheHtaccno;
	/**
	 * 交易状态
	 */
	private String cheTrstatus;
	
	/**
	 * 核心冲正记录
	 */
	private String cheCrrecord;
	
	
	/**
	 * 核心日期
	 */
	private String cheHdate;
	
	
	/**
	 * 交易功能
	 */
	private String cheTrfuction;

	/**
	*写卡状态
	*/
	private String cheWcstate;
	
	/**
	*交易摘要
	*/
	private String cheRemark;

	/**
	*执行结果
	*/
	private String cheComresults;
	
	
	/**
	*渠道编号
	*/
	private String cheChno;

	/**
	 * 渠道交易日期
	 */
	private String cheCdate;
	
	/**
	*渠道流水号
	*/
	private String cheCserial;


	/**
	 * 柜员号
	 */
	private String cheOpetell;
	
	/**
	 * 终端号
	 */
	private String cheTerminalNo;
	
	/**
	 * 复核员
	 */
	private String cheCheckTeller;
	
	/**
	 * 授权员
	 */
	private String cheAuthorizeTeller;
	
	private String tranTime;
	
	private String tranDate;
	
	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	/**
	 * 处理标识
	 */
	private String cheDeflag;
	
	/**
	 * 处理策略
	 */
	private String cheDtactic;
	
	/**
	 * 处理状态
	 */
	private String cheDstatus;
	
	/**
	 * 处理原因
	 */
	private String cheDreason;
		
	/**
	 * 余额调整
	 */
	private String cheBatrflag;

	public String getCheTrdate() {
		return cheTrdate;
	}

	public void setCheTrdate(String cheTrdate) {
		this.cheTrdate = cheTrdate;
	}

	public String getCheSerial() {
		return cheSerial;
	}

	public void setCheSerial(String cheSerial) {
		this.cheSerial = cheSerial;
	}

	public String getChePlserial() {
		return chePlserial;
	}

	public void setChePlserial(String chePlserial) {
		this.chePlserial = chePlserial;
	}

	public String getCheHserial() {
		return cheHserial;
	}

	public void setCheHserial(String cheHserial) {
		this.cheHserial = cheHserial;
	}

	public String getCheTrbranch() {
		return cheTrbranch;
	}

	public void setCheTrbranch(String cheTrbranch) {
		this.cheTrbranch = cheTrbranch;
	}

	public String getIcCardno() {
		return icCardno;
	}

	public void setIcCardno(String icCardno) {
		this.icCardno = icCardno;
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public BigDecimal getCheStamount() {
		return cheStamount;
	}

	public void setCheStamount(BigDecimal cheStamount) {
		this.cheStamount = cheStamount;
	}

	public String getCheTranFlag() {
		return cheTranFlag;
	}

	public void setCheTranFlag(String cheTranFlag) {
		this.cheTranFlag = cheTranFlag;
	}

	public String getCheReclogo() {
		return cheReclogo;
	}

	public void setCheReclogo(String cheReclogo) {
		this.cheReclogo = cheReclogo;
	}

	public BigDecimal getCheTramount() {
		return cheTramount;
	}

	public void setCheTramount(BigDecimal cheTramount) {
		this.cheTramount = cheTramount;
	}

	public BigDecimal getCheFee() {
		return cheFee;
	}

	public void setCheFee(BigDecimal cheFee) {
		this.cheFee = cheFee;
	}

	public String getCheCurrtype() {
		return cheCurrtype;
	}

	public void setCheCurrtype(String cheCurrtype) {
		this.cheCurrtype = cheCurrtype;
	}

	public String getCheAcctno() {
		return cheAcctno;
	}

	public void setCheAcctno(String cheAcctno) {
		this.cheAcctno = cheAcctno;
	}

	public String getLendingSign() {
		return lendingSign;
	}

	public void setLendingSign(String lendingSign) {
		this.lendingSign = lendingSign;
	}

	public String getCheCardno1() {
		return cheCardno1;
	}

	public void setCheCardno1(String cheCardno1) {
		this.cheCardno1 = cheCardno1;
	}

	public String getCheAcctno1() {
		return cheAcctno1;
	}

	public void setCheAcctno1(String cheAcctno1) {
		this.cheAcctno1 = cheAcctno1;
	}

	public BigDecimal getCheAccamount1() {
		return cheAccamount1;
	}

	public void setCheAccamount1(BigDecimal cheAccamount1) {
		this.cheAccamount1 = cheAccamount1;
	}

	public String getCheAccflag() {
		return cheAccflag;
	}

	public void setCheAccflag(String cheAccflag) {
		this.cheAccflag = cheAccflag;
	}

	public String getCheHtaccno() {
		return cheHtaccno;
	}

	public void setCheHtaccno(String cheHtaccno) {
		this.cheHtaccno = cheHtaccno;
	}

	public String getCheTrstatus() {
		return cheTrstatus;
	}

	public void setCheTrstatus(String cheTrstatus) {
		this.cheTrstatus = cheTrstatus;
	}

	public String getCheCrrecord() {
		return cheCrrecord;
	}

	public void setCheCrrecord(String cheCrrecord) {
		this.cheCrrecord = cheCrrecord;
	}

	public String getCheHdate() {
		return cheHdate;
	}

	public void setCheHdate(String cheHdate) {
		this.cheHdate = cheHdate;
	}

	public String getCheTrfuction() {
		return cheTrfuction;
	}

	public void setCheTrfuction(String cheTrfuction) {
		this.cheTrfuction = cheTrfuction;
	}

	public String getCheWcstate() {
		return cheWcstate;
	}

	public void setCheWcstate(String cheWcstate) {
		this.cheWcstate = cheWcstate;
	}

	public String getCheRemark() {
		return cheRemark;
	}

	public void setCheRemark(String cheRemark) {
		this.cheRemark = cheRemark;
	}

	public String getCheComresults() {
		return cheComresults;
	}

	public void setCheComresults(String cheComresults) {
		this.cheComresults = cheComresults;
	}

	public String getCheChno() {
		return cheChno;
	}

	public void setCheChno(String cheChno) {
		this.cheChno = cheChno;
	}

	public String getCheCdate() {
		return cheCdate;
	}

	public void setCheCdate(String cheCdate) {
		this.cheCdate = cheCdate;
	}

	public String getCheCserial() {
		return cheCserial;
	}

	public void setCheCserial(String cheCserial) {
		this.cheCserial = cheCserial;
	}

	public String getCheOpetell() {
		return cheOpetell;
	}

	public void setCheOpetell(String cheOpetell) {
		this.cheOpetell = cheOpetell;
	}

	public String getCheTerminalNo() {
		return cheTerminalNo;
	}

	public void setCheTerminalNo(String cheTerminalNo) {
		this.cheTerminalNo = cheTerminalNo;
	}

	public String getCheCheckTeller() {
		return cheCheckTeller;
	}

	public void setCheCheckTeller(String cheCheckTeller) {
		this.cheCheckTeller = cheCheckTeller;
	}

	public String getCheAuthorizeTeller() {
		return cheAuthorizeTeller;
	}

	public void setCheAuthorizeTeller(String cheAuthorizeTeller) {
		this.cheAuthorizeTeller = cheAuthorizeTeller;
	}

	public String getCheDeflag() {
		return cheDeflag;
	}

	public void setCheDeflag(String cheDeflag) {
		this.cheDeflag = cheDeflag;
	}

	public String getCheDtactic() {
		return cheDtactic;
	}

	public void setCheDtactic(String cheDtactic) {
		this.cheDtactic = cheDtactic;
	}

	public String getCheDstatus() {
		return cheDstatus;
	}

	public void setCheDstatus(String cheDstatus) {
		this.cheDstatus = cheDstatus;
	}

	public String getCheDreason() {
		return cheDreason;
	}

	public void setCheDreason(String cheDreason) {
		this.cheDreason = cheDreason;
	}

	public String getCheBatrflag() {
		return cheBatrflag;
	}

	public void setCheBatrflag(String cheBatrflag) {
		this.cheBatrflag = cheBatrflag;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	/**
	 * 调账文件接口
	 * 调账标志 00－仅调整核心 01－仅调整IC卡 02－核心IC卡均调整 03－调整IC卡到补登
	 * 调整类型 0－补账（增加、转入）1－冲正（减少、转出）
	 * 交易日期:8
	 * 核心流水号:19
	 * 平台流水号:19
	 * 卡号:19
	 * 交易机构号:9
	 * 金额:15
	 * 手续费:15
	 * 摘要:30
	 */
	public String toString(){
		String logo = "\"02\",\"1\"";
		this.cheRemark = "核心IC卡均调整";
		if("11".equals(this.cheReclogo) && "3".equals(this.cheWcstate)){
			logo = "\"03\",\"0\"";
			this.cheRemark = "调整至IC卡暂挂户";
		}else if("01".equals(this.cheReclogo) || "21".equals(this.cheReclogo)){
			logo = "\"00\",\"1\"";
			this.cheRemark = "调整核心";
		}else if("10".equals(this.cheReclogo) || "12".equals(this.cheReclogo)){
			logo = "\"01\"";
			if("13".equals(this.cheTrfuction)){
				logo += ",\"0\"";
			}else{
				logo += ",\"1\"";
			}
			this.cheRemark = "调整IC卡";
		}
		return ("\"" + this.tranDate + "\",\"" + this.chePlserial + "\",\"" + this.icCardno + "\"," + logo + ",\"" +
				this.cheTrdate + this.tranTime + "\"," + (null==this.cheHdate?",":("\"" + this.cheHdate + "\",")) + 
				(null==this.cheHserial?",":("\"" + this.cheHserial + "\",")) + "\"" + this.cheTrbranch + "\"," + 
				this.cheTramount + "," + this.cheFee + ",\"01\"," + this.cheTramount + ",\""+ 
				this.cheRemark + "\"").replaceAll("\"null\"", "").replaceAll("\"NULL\"", "");
	}
}
