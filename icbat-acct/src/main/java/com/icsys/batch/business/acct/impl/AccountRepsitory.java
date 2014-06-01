package com.icsys.batch.business.acct.impl;

import java.util.List;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acct.api.NoSuchAcctException;
import com.icsys.batch.business.acct.dao.AccountDAO;
import com.icsys.platform.util.cache.ThreadLocalCache;

public class AccountRepsitory {
	/**
	 * 持久化对象，用于第一次调用
	 * 
	 * @param acct
	 */
	public void saveAccount(Account account) {
		AccountDAO accountDAO = new AccountDAO();
		accountDAO.addAccount(account);
	}

	/**
	 * 根据账号查询记录
	 * 
	 * @param acctNO
	 * @param subAcctNO
	 * @return
	 * @throws NoSuchAcctException
	 */
	public Account getAccount(String acctNO) throws NoSuchAcctException {
		Account account = null;
			AccountDAO accountDAO = new AccountDAO();
			account = accountDAO.getAccount(acctNO);

			if (account == null) {
				throw new NoSuchAcctException(acctNO);
			}
		return account;
	}

	/**
	 * 更新账户主档
	 * 
	 * @param account
	 */
	public void updateAccount(Account account) {
		AccountDAO accountDAO = new AccountDAO();
		accountDAO.updateAccount(account);
	}

	/**
	 * 更新账户主档昨日余额
	 * 
	 * @param account
	 */
	public void updateLastBalance(Account account) {
		AccountDAO accountDAO = new AccountDAO();
		accountDAO.updateLastBalance(account);
	}

	/**
	 * 注销账户
	 * 
	 * @param acctNO
	 */
	public void updateAcctLogoff(String acctNO) {
		AccountDAO accountDAO = new AccountDAO();
		accountDAO.updateAcctLogoff(acctNO);
	}

	/**
	 * 更新账户启用机构
	 * 
	 * @param closeBranch
	 * @param mergeBranch
	 */
	public void updateAcctOpenBranch(String closeBranch, String mergeBranch) {
		AccountDAO accountDAO = new AccountDAO();
		accountDAO.updateAcctOpenBranch(closeBranch, mergeBranch);
	}

	/**
	 * 锁住账户
	 * 
	 * @param account
	 * @throws NoSuchAcctException
	 */
	public Account lockAccount(String acctNo) throws NoSuchAcctException {
		AccountDAO accountDAO = new AccountDAO();
		Account result = accountDAO.lockAcct(acctNo);
		if (result == null) {
			throw new NoSuchAcctException(acctNo);
		}
		return result;
	}

	/**
	 * 根据账户类型查找帐务主档表
	 * 
	 * @param acctType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Account> findAccountsByType(String acctType) {
		// 检查缓存里是否已存在
		List<Account> result = null;
		result = (List<Account>) ThreadLocalCache.get(acctType);
		if (result == null) {
			AccountDAO accountDAO = new AccountDAO();
			result = accountDAO.getAcctListByAcctType(acctType);
			// 放入缓存
			ThreadLocalCache.put(acctType, result);
		}
		return result;
	}

	/**
	 * 该方法主要是得到机构往来户的集合
	 * 
	 * @param branch
	 *            机构号
	 * @param currType
	 *            货币代号
	 * @param subjectCode
	 *            科目代号
	 * @return
	 */
	public List<Account> getBrachNos(String branch, String currType,
			String subjectCode) {
		AccountDAO accountDAO = new AccountDAO();
		return accountDAO.getBatchNos(branch, currType, subjectCode);
	}
}
