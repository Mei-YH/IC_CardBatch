package com.icsys.batch.business.acct.api;

import java.math.BigDecimal;

import com.icsys.batch.business.acctting.api.AcctingException;

/**
 * 用来完成账户生命周期管理的服务。 应用层调用的类
 * 
 * @author wangzheng
 * 
 */
public interface AcctManager {

	public static final int INTEREST_TYPE_FIXED_RATE = 0;

	/**
	 * 内部一般账户开户
	 * 
	 * @return 账务主档信息
	 * @throws NoSuchSubjectException
	 */
	public Account openInnerAcct(OpenAcctInfo info) throws AcctException;

	/**
	 * 内部标准账户开户(使用外部后缀的) 外部后缀必须大于 1000000
	 * 
	 * @return 账务主档信息
	 * @throws NoSuchSubjectException
	 */
	public Account openInnerAcct(OpenAcctInfo info, String acctNoSuffix)
			throws AcctException;

	/**
	 * 核算账户开户，帐号由系统生成
	 * 
	 * @return 账务主档信息
	 * @throws AcctException
	 * 
	 */
	public Account openAcct(OpenAcctInfo info) throws AcctException;

	/**
	 * 修改账户状态
	 * 
	 * @param acctNO
	 *            账号
	 * @param statu
	 *            账户状态
	 * @throws AcctException
	 */
	public void changeStatus(String acctNO, AcctStatus statu)
			throws AcctException;

	/**
	 * 部分止付 状态从 正常的状态到部分止付
	 * 
	 * @param acctNO
	 *            账号
	 * @param stopAmount
	 *            止付金额
	 */
	public void partialStopPayment(String acctNO, BigDecimal stopAmount)
			throws AcctException;

	/**
	 * 结清账户
	 * 
	 * @param caInput
	 *            结清账户输入信息
	 * @return 结清账户输出信息（包括结清余额和对方账户交易后余额）
	 * @throws AcctException
	 */
	public CloseAcctOutput closeAccount(CloseAcctInput caInput)
			throws AcctException, AcctingException;

	/**
	 * 注销账户
	 * 
	 * @param acctNO
	 *            账号
	 * @throws AcctException
	 */
	public void logoffAccount(String acctNO) throws AcctException;

	/**
	 * 账户状态查询
	 * 
	 * @param acctNO 账号
	 * @return 账户状态
	 * @throws AcctException
	 */
	public AcctStatus queryAcctStatus(String acctNO) throws AcctException;

	/**
	 * 机构撤并账务处理
	 * 
	 * @param closeBranch 撤销机构
	 * @param mergeBranch 并入机构
	 * @throws AcctingException
	 */
	public void branchMerger(String closeBranch, String mergeBranch)
			throws AcctingException;
}