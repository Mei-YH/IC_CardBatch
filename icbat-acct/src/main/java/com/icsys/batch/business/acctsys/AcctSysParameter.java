package com.icsys.batch.business.acctsys;

/**
 * 对应账务系统参数表
 * 
 * @author kittyuu
 * 
 */
public class AcctSysParameter {
	/**
	 * 记账日期
	 */
	private String acctingDate;

	/**
	 * 轧账日期
	 */
	private String rollOffDate;

	/**
	 * 系统状态
	 */
	private Integer systemStatus;

	/**
	 * 当前分录流水表
	 */
	private Integer currentAccList;

	/**
	 * 日终轧帐挂账账号
	 */
	private String loseAcctNO;

	/**
	 * 轧账步骤状态
	 */
	private Integer stepState;

	/**
	 * 步骤动作
	 */
	private String stepAction;

	/**
	 * 轧账步骤
	 */
	private Integer step;

	public String getAcctingDate() {
		return acctingDate;
	}

	public void setAcctingDate(String acctingDate) {
		this.acctingDate = acctingDate;
	}

	public String getRollOffDate() {
		return rollOffDate;
	}

	public void setRollOffDate(String rollOffDate) {
		this.rollOffDate = rollOffDate;
	}

	public Integer getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(Integer systemStatus) {
		this.systemStatus = systemStatus;
	}

	public Integer getCurrentAccList() {
		return currentAccList;
	}

	public void setCurrentAccList(Integer currentAccList) {
		this.currentAccList = currentAccList;
	}

	public String getLoseAcctNO() {
		return loseAcctNO;
	}

	public void setLoseAcctNO(String loseAcctNO) {
		this.loseAcctNO = loseAcctNO;
	}

	public Integer getStepState() {
		return stepState;
	}

	public void setStepState(Integer stepState) {
		this.stepState = stepState;
	}

	public String getStepAction() {
		return stepAction;
	}

	public void setStepAction(String stepAction) {
		this.stepAction = stepAction;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "AcctSysParameter [acctingDate=" + acctingDate
				+ ", currentAccList=" + currentAccList + ", loseAcctNO="
				+ loseAcctNO + ", rollOffDate=" + rollOffDate + ", step="
				+ step + ", stepAction=" + stepAction + ", stepState="
				+ stepState + ", systemStatus=" + systemStatus
				+ "]";
	}

}
