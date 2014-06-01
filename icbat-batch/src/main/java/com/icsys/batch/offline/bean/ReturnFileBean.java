package com.icsys.batch.offline.bean;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 给本行和银联返回文件的格式封装bean
 * 成功文件格式:交易传输时间|系统跟踪号|代理机构标识码|发送机构标识码|卡号|金额|开户机构|IC卡平台交易日期|IC卡平台流水号|
 * 失败文件格式:交易传输时间|系统跟踪号|代理机构标识码|发送机构标识码|卡号|金额|开户机构|响应码|响应信息|
 * 
 * @author liuy
 * 
 */
public class ReturnFileBean {
	private String ocdTrcode;
	private BigDecimal ocdTramount;
	private String ocdAcctNo;
	private String ocdAccdate;
	private String ocdPlaserial;
	private String ocdStatus = "0000";
	private String ocdCldate;
	private Integer ocdBatchno;
	private String tag9a;
	private String orgTag9a;
	private String tranTime;
	private String ocdKey11;
	private String ocdKey42;
	private String ocdKey43;
	private String ocdFee;
	private String remake;
	private String orgInfo;
	
	public String getOcdCldate() {
		return ocdCldate;
	}


	public void setOcdCldate(String ocdCldate) {
		this.ocdCldate = ocdCldate;
	}


	public Integer getOcdBatchno() {
		return ocdBatchno;
	}


	public void setOcdBatchno(Integer ocdBatchno) {
		this.ocdBatchno = ocdBatchno;
	}


	public String getOcdTrcode() {
		return ocdTrcode;
	}


	public void setOcdTrcode(String ocdTrcode) {
		this.ocdTrcode = ocdTrcode;
	}


	public BigDecimal getOcdTramount() {
		return ocdTramount;
	}


	public void setOcdTramount(BigDecimal ocdTramount) {
		this.ocdTramount = ocdTramount;
	}


	public String getOcdAcctNo() {
		return ocdAcctNo;
	}


	public void setOcdAcctNo(String ocdAcctNo) {
		this.ocdAcctNo = ocdAcctNo;
	}


	public String getOcdAccdate() {
		return ocdAccdate;
	}


	public void setOcdAccdate(String ocdAccdate) {
		this.ocdAccdate = ocdAccdate;
	}


	public String getOcdPlaserial() {
		return ocdPlaserial;
	}


	public void setOcdPlaserial(String ocdPlaserial) {
		this.ocdPlaserial = ocdPlaserial;
	}


	public String getOcdStatus() {
		return ocdStatus;
	}

	public void setOcdStatus(String ocdStatus) {
		this.ocdStatus = ocdStatus;
	}	
	
	public String getTag9a() {
		return tag9a;
	}


	public void setTag9a(String tag9a) {
		this.tag9a = tag9a;
	}


	public String getOrgTag9a() {
		return orgTag9a;
	}


	public void setOrgTag9a(String orgTag9a) {
		this.orgTag9a = orgTag9a;
	}


	public String getTranTime() {
		return tranTime;
	}


	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}


	public String getOcdKey11() {
		return ocdKey11;
	}


	public void setOcdKey11(String ocdKey11) {
		this.ocdKey11 = ocdKey11;
	}


	public String getOcdKey42() {
		return ocdKey42;
	}


	public void setOcdKey42(String ocdKey42) {
		this.ocdKey42 = ocdKey42;
	}


	public String getOcdKey43() {
		return ocdKey43;
	}


	public void setOcdKey43(String ocdKey43) {
		this.ocdKey43 = ocdKey43;
	}


	public String getOcdFee() {
		return ocdFee;
	}


	public void setOcdFee(String ocdFee) {
		this.ocdFee = ocdFee;
	}


	public String getRemake() {
		return remake;
	}


	public void setRemake(String remake) {
		this.remake = remake;
	}


	public String getOrgInfo() {
		return orgInfo;
	}


	public void setOrgInfo(String orgInfo) {
		this.orgInfo = orgInfo;
	}


	private String status(String ocdStatus){
		String tmp = ocdStatus.substring(ocdStatus.length() - 1);
		return "1".equals(tmp)?"0":"1";
	}

	public String toString(){
		return StringUtils.rightPad(this.ocdAcctNo, 19, " ") + this.ocdTrcode + 
		StringUtils.leftPad(this.ocdTramount.movePointRight(2).toString(), 15, "0") + 
		status(this.ocdStatus.trim()) + StringUtils.rightPad(this.tag9a,8," ") + 
		("300".equals(this.ocdTrcode)?StringUtils.leftPad("",8," "):this.orgInfo.substring(3, 13)) +
		"      " + StringUtils.rightPad(this.ocdKey11,6," ") + StringUtils.rightPad(this.ocdKey42,15," ") +
		("300".equals(this.ocdTrcode)?StringUtils.leftPad(this.ocdFee,12,"0"):StringUtils.leftPad("",12," ")) +
		(!"300".equals(this.ocdTrcode)?StringUtils.leftPad(this.ocdFee,12,"0"):StringUtils.leftPad("",12," ")) +
		"    ";
	}
}
