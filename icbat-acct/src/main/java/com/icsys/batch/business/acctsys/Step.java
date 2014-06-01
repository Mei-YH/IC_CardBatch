package com.icsys.batch.business.acctsys;

import com.icsys.batch.business.dayend.impl.CheckAcctBalance;
import com.icsys.batch.business.dayend.impl.CheckDetailBalance;
import com.icsys.batch.business.dayend.impl.CreateAcctingDetail;
import com.icsys.batch.business.dayend.impl.CreateGeneral;
import com.icsys.batch.business.dayend.impl.CreateReports;
import com.icsys.batch.business.dayend.impl.DailyClosing;
import com.icsys.batch.business.dayend.impl.EndWork;
import com.icsys.batch.business.dayend.impl.Settlement;
import com.icsys.batch.business.dayend.impl.StartWork;
import com.icsys.batch.business.dayend.impl.WorkStep;

/**
 * 步骤
 * @author kittyuu
 *
 */
public enum Step {
	NO_CHECK_ACCTOUNTS(0,null), // 轧账未开始
	CHECK_ACCTOUNTS(1,new StartWork()), // 轧账开始
	SETTLE_ACCOUNTS(2,new Settlement()), // 日终清算
	CHECK_DETAIL_BALANCE(3,new CheckDetailBalance()), // 分录平衡检查
	DAILY_CLOSING(4,new DailyClosing()), // 科目日结
	CREATE_GENERAL(5,new CreateGeneral()), // 生成总账
	CREATE_GENERAL_DETAIL(6,new CreateAcctingDetail()), // 生成明细账
	CREATE_REPORT(7,new CreateReports()), // 生成报表凭证
	CHECK_ACCOUNT_BALANCE(8,new CheckAcctBalance()), // 账务平衡检查
	CHECK_ACCTOUNTS_OVER(9,new EndWork()); // 轧账结束

	private int position;
	
	private WorkStep workStep;

	Step(int position,WorkStep ws) {
		this.position = position;
		this.workStep = ws;
	}

	public int getPosition() {
		return this.position;
	}

	public WorkStep getWorkStep() {
		return workStep;
	}
}
