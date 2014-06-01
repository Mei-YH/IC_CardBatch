package com.icsys.batch.task.profit.proxy.bean;
import java.math.BigDecimal;
/**
 * 应用账户绑定表
 * @author SDNX
 *
 */
public class GrCardAppreg {
	private String cagAccrid;//账户标识
	private String appId;//应用编号
	private String cagOffacct;//应用脱机帐号
	private String cagOnacct;//应用联机帐号
	private String cagBranch;//启用机构
	private String cagState;//应用状态
	private BigDecimal cagTramax;//金额上限
	private BigDecimal cagOnemax;//交易单笔最高限
	public String getCagAccrid() {
		return cagAccrid;
	}
	public void setCagAccrid(String cagAccrid) {
		this.cagAccrid = cagAccrid;
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
	public String getCagOnacct() {
		return cagOnacct;
	}
	public void setCagOnacct(String cagOnacct) {
		this.cagOnacct = cagOnacct;
	}
	public String getCagBranch() {
		return cagBranch;
	}
	public void setCagBranch(String cagBranch) {
		this.cagBranch = cagBranch;
	}
	public String getCagState() {
		return cagState;
	}
	public void setCagState(String cagState) {
		this.cagState = cagState;
	}
	public BigDecimal getCagTramax() {
		return cagTramax;
	}
	public void setCagTramax(BigDecimal cagTramax) {
		this.cagTramax = cagTramax;
	}
	public BigDecimal getCagOnemax() {
		return cagOnemax;
	}
	public void setCagOnemax(BigDecimal cagOnemax) {
		this.cagOnemax = cagOnemax;
	}
	

}
