package com.icsys.batch.business.dayend.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acct.impl.AccountRepsitory;
import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.acctting.dao.AcctingListDAO;
import com.icsys.batch.business.acctting.impl.AcctingList;
import com.icsys.batch.business.dayend.api.AcctDayEndException;
import com.icsys.batch.business.dayend.dao.AccountDetail;
import com.icsys.batch.business.dayend.dao.AcctDayEndDAO;
import com.icsys.batch.business.dayend.dao.ChangeAccount;
import com.icsys.batch.business.dayend.dao.SeqBalanceRecord;
import com.icsys.platform.dao.TXHelper;

/**
 * 日终生成账务明细
 * 
 * @author kittyuu
 * 
 */
public class CreateAcctingDetail implements WorkStep, ErrorDef {

	private static Logger LOG = Logger.getLogger(CreateAcctingDetail.class);

	public void work(boolean bWorkFlag) throws AcctDayEndException {
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctDayEndException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		// 获取轧账日期
		String rollOffDate = accSysParam.getRollOffDate();
		try {
			//从动户快照中获取所有账户记录
			List<ChangeAccount> accounts = AcctDayEndDAO.getChangeAccounts();
			for(ChangeAccount changeAccount : accounts){
				//对每个帐户进行处理
				List<AcctingList> lists = AcctDayEndDAO.getAcctListByAccount(changeAccount.getAcctNO());
				BigDecimal lastBalance =  changeAccount.getLastDayBalance();;
				for (AcctingList acctingList : lists) {
					//对帐户的当天发生的每笔交易进行处理
					if (null == changeAccount)
						continue;
					/**
					 * 这里是一个bug,取上一日余额应在循环外，否则就会有问题的。
					 * 
					 */
				//	lastBalance = changeAccount.getLastDayBalance(); 
					// 检查分录流水的借贷标志与账户余额方向是否一致
					String balanceCD = changeAccount.getBalanceCD();
					String cdFlag = acctingList.getAcctCD().toString();
					if (null != balanceCD && null != cdFlag
							&& balanceCD.equalsIgnoreCase(cdFlag)) {
						lastBalance = lastBalance.add(acctingList.getAmount());
					} else {
						lastBalance = lastBalance.subtract(acctingList.getAmount());
					}
					calAcctDetail(acctingList, lastBalance);
				}
				// 检查最后一笔明细余额是否等于该账户的今日最后余额
				if ((null != lastBalance) && (null != changeAccount)) {
					if (lastBalance.compareTo(changeAccount.getBalance()) == 0) {
						// 更新帐务主档上日余额
						AccountRepsitory accountRepsitory = new AccountRepsitory();
						Account account = new Account();
						account.setAccNo(changeAccount.getAcctNO());
						account.setAccYesbal(lastBalance);
						accountRepsitory.updateLastBalance(account);
					} else {
						
						// 时序不平衡则抛出异常
						if (LOG.isDebugEnabled()) {
							LOG.debug("LastBalance=[" + lastBalance + "]; "
									+ "AccountBalance=["
									+ changeAccount.getBalance() + "]");
						}
						//TODO 单独加事务,重做的时候怎么处理,数据什么时候清除？？？
						TXHelper.beginNewTX();
						try{
							AcctDayEndDAO.deleteSequenceBalance(rollOffDate);
							SeqBalanceRecord record = createSeqBalanceRec(
									changeAccount, lastBalance);
							record.setRollOffDate(rollOffDate);
							AcctDayEndDAO.addSequenceBalance(record);
							TXHelper.commit();
						}catch(Exception e){
							try {
								TXHelper.rollback();
							} catch (Exception e1) {
								LOG.error(e1.getMessage(),e1);
							}
						}finally{
							TXHelper.close();
						}
						//throw new AcctDayEndException(CHECK_SEQUENCE_NOT_BALANCE); // 时序平衡检查不平
					}
				}
			}
			
			// 获取按照发生顺序排序的分录流水
			/*List<AcctingList> alist = new AcctingListDAO(false)
					.getAllOrderAcctList();
			BigDecimal lastBalance = null;
			ChangeAccount changeAccount = null;
			for (AcctingList acctingList : alist) {
				changeAccount = AcctDayEndDAO
						.getChangeAccountByKey(acctingList.getAcctNO());
				if (null == changeAccount)
					continue;
				lastBalance = changeAccount.getLastDayBalance();
				// 检查分录流水的借贷标志与账户余额方向是否一致
				String balanceCD = changeAccount.getBalanceCD();
				String cdFlag = acctingList.getAcctCD().toString();
				if (null != balanceCD && null != cdFlag
						&& balanceCD.equalsIgnoreCase(cdFlag)) {
					lastBalance = lastBalance.add(acctingList.getAmount());
				} else {
					lastBalance = lastBalance.subtract(acctingList.getAmount());
				}
				calAcctDetail(acctingList, lastBalance);
			}
			// 检查最后一笔明细余额是否等于该账户的今日最后余额
			if ((null != lastBalance) && (null != changeAccount)) {
				if (lastBalance.compareTo(changeAccount.getBalance()) == 0) {
					// 更新帐务主档上日余额
					AccountRepsitory accountRepsitory = new AccountRepsitory();
					Account account = new Account();
					account.setAcctNO(changeAccount.getAcctNO());
					account.setLastDayBalance(lastBalance);
					accountRepsitory.updateLastBalance(account);
				} else {
					
					// 时序不平衡则抛出异常
					if (LOG.isDebugEnabled()) {
						LOG.debug("LastBalance=[" + lastBalance + "]; "
								+ "AccountBalance=["
								+ changeAccount.getBalance() + "]");
					}
					//TODO 单独加事务,重做的时候怎么处理,数据什么时候清除？？？
					TXHelper.beginNewTX();
					try{
						SeqBalanceRecord record = createSeqBalanceRec(
								changeAccount, lastBalance);
						record.setRollOffDate(rollOffDate);
						AcctDayEndDAO.addSequenceBalance(record);
						TXHelper.commit();
					}catch(Exception e){
						try {
							TXHelper.rollback();
						} catch (Exception e1) {
							LOG.error(e1.getMessage(),e1);
						}
					}finally{
						TXHelper.close();
					}
					throw new AcctDayEndException(CHECK_SEQUENCE_NOT_BALANCE); // 时序平衡检查不平
				}
			}*/
//		} catch (AcctDayEndException e) {
//			LOG.error(e.getMessage(),e);
//			throw e;
		} catch (RuntimeException e) {
			LOG.error(e.getMessage(),e);
			throw new AcctDayEndException(CREATE_ACCTING_DETAIL_ERROR);
		}

	}

	/**
	 * 增加账户明细表数据
	 * 
	 * @param acctingList
	 * @param balance
	 */
	private void calAcctDetail(AcctingList acctingList, BigDecimal balance) {
		AccountDetail accountDetail = createAccountDetail(acctingList, balance);
		AcctDayEndDAO.addAccountDetail(accountDetail);
	}

	/**
	 * 创建账户明细数据
	 * 
	 * @param acctingList
	 * @param balance
	 * @return
	 */
	private AccountDetail createAccountDetail(AcctingList acctingList,
			BigDecimal balance) {
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setSerial(acctingList.getSerial());
		accountDetail.setAcctingDate(acctingList.getAcctingDate());
		accountDetail.setAcctNO(acctingList.getAcctNO());
		accountDetail.setTranAmount(acctingList.getAmount());//交易金额
		accountDetail.setBalance(balance);//当前账号余额
		accountDetail.setTranBranch(acctingList.getTranBranch());
		accountDetail.setTranCD(acctingList.getAcctCD());
		accountDetail.setRemark(acctingList.getRemark());
		accountDetail.setFee(new BigDecimal(0));
		accountDetail.setTranDate(acctingList.getAcctingDate());
		accountDetail.setSN(acctingList.getSn());
		return accountDetail;
	}

	/**
	 * 创建时序平衡检查报告数据
	 * 
	 * @param acc
	 * @param calBalance
	 * @return
	 */
	private SeqBalanceRecord createSeqBalanceRec(ChangeAccount acc,
			BigDecimal calBalance) {
		SeqBalanceRecord record = new SeqBalanceRecord();
		record.setAcctNO(acc.getAcctNO());
		record.setBalance(acc.getBalance());
		record.setBalanceCD(acc.getBalanceCD());
		record.setLastDayBalance(acc.getLastDayBalance());
		record.setCalBalance(calBalance);
		return record;
	}
}
