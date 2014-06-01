package com.icsys.batch.business.dayend.impl;

import com.icsys.batch.business.dayend.api.AcctDayEndException;

public interface WorkStep {
	public void work(boolean bWorkFlag) throws AcctDayEndException;
}
