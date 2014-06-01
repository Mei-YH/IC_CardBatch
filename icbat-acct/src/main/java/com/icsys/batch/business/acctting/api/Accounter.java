package com.icsys.batch.business.acctting.api;



public interface Accounter {

	/**
	 * 通用记账（多借多贷）
	 * @param input 通用记账输入信息
	 * @return 通用记账输出信息
	 * @throws AcctingException
	 */
	public AcctingOutput account(AcctingInput inputs) throws AcctingException;

	/**
	 * 通用转账记账（一借一贷）
	 * @param inputs 借贷记输入信息
	 * @return 借贷记输出信息
	 * @throws AcctingException
	 */
	public CDAcctingOutput account(CDAcctingInput inputs) throws AcctingException;

	/**
	 * 转账记账（借方透支自动零余额转款）
	 * @param inputs 借贷记输入信息
	 * @return 透支自动零余额转款输出信息
	 * @throws AcctingException
	 */
	public CDAcctingOutput accountDebitOverdrawing(CDAcctingInput inputs) throws AcctingException;
	
	/**
	 * 转账记账（贷方透支自动零余额转款）
	 * @param inputs 借贷记输入信息
	 * @return 透支自动零余额转款输出信息
	 * @throws AcctingException
	 */
	public CDAcctingOutput accountCreditOverdrawing(CDAcctingInput inputs) throws AcctingException;
	
	/**
	 * 冲正
	 * 
	 * @param tranSerial 原交易流水号
	 * @throws AcctingException
	 */
	public void reversal(String tranSerial) throws AcctingException;
}
