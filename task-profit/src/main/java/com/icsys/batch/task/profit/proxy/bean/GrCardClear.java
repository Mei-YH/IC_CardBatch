package com.icsys.batch.task.profit.proxy.bean;

public class GrCardClear {
	private String icCardno;//应用卡号
	private String icIndex;//应用卡序号
	private String appId;//应用编号
	private String cagOffacct;//脱机待清算帐号
	private String ccCanmoney;//回收余额
	public String getIcCardno() {
		return icCardno;
	}
	public void setIcCardno(String icCardno) {
		this.icCardno = icCardno;
	}
	public String getIcIndex() {
		return icIndex;
	}
	public void setIcIndex(String icIndex) {
		this.icIndex = icIndex;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCagOffacct() {
		return cagOffacct;
	}
	public void setCagOffacct(String cagOffacct) {
		this.cagOffacct = cagOffacct;
	}
	public String getCcCanmoney() {
		return ccCanmoney;
	}
	public void setCcCanmoney(String ccCanmoney) {
		this.ccCanmoney = ccCanmoney;
	}

}
