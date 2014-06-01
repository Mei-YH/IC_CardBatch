package com.icsys.batch.business.dayend.api;


/**
 * IC卡日终处理
 * 
 * @author kittyuu
 * 
 */
public interface AccountDayEnd {

	/**
	 * 日切检查(日切第一步) 日期检查
	 * 
	 * @param acctingDate 记账日期
	 */
	public void acctDateSwitchCheck(String acctingDate) throws AcctDayEndException;
	/**
	 * 日切数据清理(日切第二步)
	 * 清理动户快照表和科目日结表
	 * @throws AcctDayEndException
	 */
	public void acctDateSwitchCleanData() throws AcctDayEndException;
	
	/**
	 * 日切(第三步)
	 *1、 生成动户快照
	 *2、切换AB表
	 *3、系统日切
	 * @param acctingDate 记账日期
	 * @throws AcctDayEndException
	 */
	public void acctDateSwitch(String acctingDate) throws AcctDayEndException;

	/**
	 * 轧账
	 * @throws AcctDayEndException
	 */
	public void checkAccounts() throws AcctDayEndException;

}
