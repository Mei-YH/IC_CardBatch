package com.icsys.batch.util;

public enum SystemStatus {

	NORMAL(0), // 正常
	DATE_SWITCHING(1), // 正在日切
	DATE_SWITCHED(2), // 日切完成
	CHECK_ACCOUNTS(3);// 正在轧账
	private int flag;

	SystemStatus(int value) {
		this.flag = value;
	}

	public int getValue() {
		return this.flag;
	}
}
