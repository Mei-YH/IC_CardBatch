package com.icsys.batch.business.acctsys;

public enum StepAction {

	PASS(0),  //跳过
	DONE(1),  //必做
	RUNUP(2); //自动挂账
	
	private int flag;
	
	StepAction(int value) {
		this.flag = value;
	}

	public int getValue() {
		return this.flag;
	}

}
