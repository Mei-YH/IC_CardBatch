package com.icsys.batch.task.profit.proxy.bean;

import java.math.BigDecimal;

/**
 * 卡登记表
 * @author SDNX
 *
 */
public class IcCardinfoReq {
	private String icNo;//IC卡卡号
	private String icIndex;//应用卡序号
	private String icClass;//IC卡产品号
	private String icPro;//发卡省市代码
	private String icBranch;//发卡行
	private String icAddflag;//主附卡标志
	private String acctNo;//账户标识
	private String icCid;//持卡人客户号
	private BigDecimal yearFCode;//该卡年费参数
	private String cardNo;//磁条卡卡号
	private BigDecimal addFlag;//附卡标志
	private String icFlag;//IC卡特性标志
	private String icPin;//IC卡PIN码密文
	private String icStat;//IC卡状态
	private String useDt;//发卡日期
	private String chgDt;//状态变更日期
	private String cancDt;//销卡日期
	private String icValidDt;//卡有效期限
	private String cfValidDt;//证书有效期
	private BigDecimal reclNum;//正常回收次数
	private String reloadPin;//IC卡密码管理
	private String dac;//校验位
	public String getIcNo() {
		return icNo;
	}
	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}
	public String getIcIndex() {
		return icIndex;
	}
	public void setIcIndex(String icIndex) {
		this.icIndex = icIndex;
	}
	public String getIcClass() {
		return icClass;
	}
	public void setIcClass(String icClass) {
		this.icClass = icClass;
	}
	public String getIcPro() {
		return icPro;
	}
	public void setIcPro(String icPro) {
		this.icPro = icPro;
	}
	public String getIcBranch() {
		return icBranch;
	}
	public void setIcBranch(String icBranch) {
		this.icBranch = icBranch;
	}
	public String getIcAddflag() {
		return icAddflag;
	}
	public void setIcAddflag(String icAddflag) {
		this.icAddflag = icAddflag;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getIcCid() {
		return icCid;
	}
	public void setIcCid(String icCid) {
		this.icCid = icCid;
	}
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getIcFlag() {
		return icFlag;
	}
	public void setIcFlag(String icFlag) {
		this.icFlag = icFlag;
	}
	public String getIcPin() {
		return icPin;
	}
	public void setIcPin(String icPin) {
		this.icPin = icPin;
	}
	public String getIcStat() {
		return icStat;
	}
	public void setIcStat(String icStat) {
		this.icStat = icStat;
	}
	public String getUseDt() {
		return useDt;
	}
	public void setUseDt(String useDt) {
		this.useDt = useDt;
	}
	public String getChgDt() {
		return chgDt;
	}
	public void setChgDt(String chgDt) {
		this.chgDt = chgDt;
	}
	public String getCancDt() {
		return cancDt;
	}
	public void setCancDt(String cancDt) {
		this.cancDt = cancDt;
	}
	public String getIcValidDt() {
		return icValidDt;
	}
	public void setIcValidDt(String icValidDt) {
		this.icValidDt = icValidDt;
	}
	public String getCfValidDt() {
		return cfValidDt;
	}
	public void setCfValidDt(String cfValidDt) {
		this.cfValidDt = cfValidDt;
	}

	public BigDecimal getYearFCode() {
		return yearFCode;
	}
	public void setYearFCode(BigDecimal yearFCode) {
		this.yearFCode = yearFCode;
	}
	public BigDecimal getAddFlag() {
		return addFlag;
	}
	public void setAddFlag(BigDecimal addFlag) {
		this.addFlag = addFlag;
	}
	public BigDecimal getReclNum() {
		return reclNum;
	}
	public void setReclNum(BigDecimal reclNum) {
		this.reclNum = reclNum;
	}
	public String getReloadPin() {
		return reloadPin;
	}
	public void setReloadPin(String reloadPin) {
		this.reloadPin = reloadPin;
	}
	public String getDac() {
		return dac;
	}
	public void setDac(String dac) {
		this.dac = dac;
	}
	
	

}
