package com.icsys.batch.business.acctting.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acct.api.AcctStatus;
import com.icsys.batch.business.acct.api.AcctSubject;
import com.icsys.batch.business.acct.api.CDFlag;
import com.icsys.batch.business.acct.api.NoSuchAcctException;
import com.icsys.batch.business.acct.api.NoSuchSubjectException;
import com.icsys.batch.business.acct.impl.AccountRepsitory;
import com.icsys.batch.business.acct.impl.AcctSubjectManagerImpl;
import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctsys.ErrorDef;
import com.icsys.batch.business.acctting.api.Accounter;
import com.icsys.batch.business.acctting.api.AcctingContext;
import com.icsys.batch.business.acctting.api.AcctingException;
import com.icsys.batch.business.acctting.api.AcctingInput;
import com.icsys.batch.business.acctting.api.AcctingOutput;
import com.icsys.batch.business.acctting.api.CDAcctingInput;
import com.icsys.batch.business.acctting.api.CDAcctingOutput;
import com.icsys.batch.business.acctting.dao.AcctingListDAO;
import com.icsys.batch.serial.AcctingSerialGennerator;
import com.icsys.batch.util.SystemStatus;
import com.icsys.platform.dao.TXHelper;

public class AccounterImpl implements Accounter {

	private static Logger LOG = Logger.getLogger(AccounterImpl.class);

	AccountRepsitory acctRep = new AccountRepsitory();

	/**
	 * 通用记账（多借多贷）
	 * 
	 * @param input
	 *            通用记账输入信息
	 * @return 通用记账输出信息
	 * @throws AcctingException
	 */
	public AcctingOutput account(AcctingInput inputs) throws AcctingException {
		// 检查输入信息合法性
		if (!(inputs.validate()).equals("")) {
			throw makeAcctingException("00013", inputs.validate());
		}
		// 需要自己生成记账流水号,调用记账流水号生成器
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctingException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		String serial = null;
		try {
			serial = AcctingSerialGennerator.getSerial(accSysParam
					.getAcctingDate());
			if (null == serial)
				throw new AcctingException("00009"); // 生成记账流水号失败
		} catch (Exception e) {
			throw new AcctingException("00009"); // 生成记账流水号失败
		}
		// 获取记账日期
		String acctingDate = accSysParam.getAcctingDate();
		AcctingOutput out = new AcctingOutput();
		List<BigDecimal> results = new ArrayList<BigDecimal>();
		Integer sn = 1;
		try {
			TXHelper.beginTX();
			for (AcctingContext ac : inputs.getAcctingContext()) {
				// 准备单笔记账的输入信息
				InnerAcctingInput input = new InnerAcctingInput();
				try {
					BeanUtils.copyProperties(input, inputs);
				} catch (Exception e) {
					throw new AcctingException("00011"); // 生成单笔记账输入信息失败
				}
				input.setAcctNO(ac.getAcctNO());
				input.setAcctingCD(ac.getAcctingCD());
				input.setAmount(ac.getAmount());
				BigDecimal result = normalAccount(input, serial, sn,
						acctingDate, true, false);
				results.add(result);
				sn++;
			}
			out.setSerial(serial);
			out.setAcctingDate(acctingDate);
			out.setBalanceList(results);
			TXHelper.commit();
		} catch (AcctingException e) {
			LOG.error("db operation err", e);
			TXHelper.rollback();
			throw e;
		} finally {
			TXHelper.close();
		}
		return out;
	}

	/**
	 * 通用转账记账（一借一贷）
	 * 
	 * @param inputs
	 *            借贷记输入信息
	 * @return 借贷记输出信息
	 * @throws AcctingException
	 */
	public CDAcctingOutput account(CDAcctingInput inputs)
			throws AcctingException {
		return baseCDAccount(inputs, false, false);
	}

	/**
	 * 转账记账（贷方透支自动零余额转款）
	 * 
	 * @param inputs
	 *            借贷记输入信息
	 * @return 透支自动零余额转款输出信息
	 * @throws AcctingException
	 */
	public CDAcctingOutput accountCreditOverdrawing(
			CDAcctingInput inputs) throws AcctingException {
		return baseCDAccount(inputs, true, true);
	}

	/**
	 * 转账记账（借方透支自动零余额转款）
	 * @param inputs 借贷记输入信息
	 * @return 透支自动零余额转款输出信息
	 * @throws AcctingException
	 */
	public CDAcctingOutput accountDebitOverdrawing(
			CDAcctingInput inputs) throws AcctingException {
		return baseCDAccount(inputs, true, false);
	}

	/**
	 * 冲正
	 * 
	 * @param tranSerial原交易流水号
	 * @throws AcctingException
	 */
	public void reversal(String tranSerial) throws AcctingException {
		// 获取待冲正记录
		List<AcctingList> als = new AcctingListDAO()
				.getAcctListNoReversalByTranSerial(tranSerial);
		if(als.size()!=2)
		{
			LOG.error("找不到原交易流水["+tranSerial+"]或者传入原交易流水不对");
			throw makeAcctingException(ErrorDef.ACCT_RECORD_NOT_FOUND,"找不到原交易流水["+tranSerial+"]或者传入原交易流水不对");
		}
		try {
			TXHelper.beginTX();
			for (AcctingList al : als) {
				// 冲正标志
				al.setSerialFlag("1");
				new AcctingListDAO().updateReversal(al);
				// 发生额变负
				al.setAmount(al.getAmount().negate());
				Account acct;
				InnerAcctingInput input = new InnerAcctingInput();
				// 准备数据
				BeanUtils.copyProperties(input, al);
				if (1 == al.getAcctCD())
					input.setAcctingCD(CDFlag.DEBIT);
				else
					input.setAcctingCD(CDFlag.CREDIT);

				try {
					acct = new AccountRepsitory()
							.lockAccount(input.getAcctNO());
				} catch (NoSuchAcctException e1) {
					throw makeAcctingException(input, "00001");
				}
				// --------------检查账户状态------------------------
				if (acct.getAccStatus() == AcctStatus.NORECEIVE_NOPAYMENT
						.getIntValue()
						|| acct.getAccStatus() == AcctStatus.CANCELD.getIntValue()) {
					throw makeAcctingException(input, "00003");
				}
				AcctSubject subject = getSubject(acct);
				updateAccount(input, acct, subject, al.getAcctingDate(), false); // 更新账务主档

			}
			TXHelper.commit();
		} catch (AcctingException e) {
			TXHelper.rollback();
			LOG.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			TXHelper.rollback();
			LOG.error("db operation err", e);
			throw new AcctingException(ErrorDef.OPERATE_DATABASE_ERROR);
		} finally {
			TXHelper.close();
		}
	}

	private AcctSubject getSubject(Account acct) {
		String currType = acct.getCurrencyCode();
		String subjectCode = acct.getSubjectCode();
		AcctSubject subject = null;
		try {
			subject = new AcctSubjectManagerImpl().getSubject(currType,
					subjectCode);
		} catch (NoSuchSubjectException e) {
			// never happened
		}
		return subject;
	}

	/**
	 * 更新账户主档
	 * 
	 * @param input
	 * @param acct
	 * @param subject
	 * @throws AcctingException
	 */
	private void updateAccount(InnerAcctingInput input, Account acct,
			AcctSubject subject, String acctingDate, boolean bAutoZeroBalance)
			throws AcctingException {
		BigDecimal balance = acct.getAccBalance();
		BigDecimal usableBalance = acct.getAccAbbalance();
		if (subject.getBalanceCD() == input.getAcctingCD().getFlag()) {

			if (acct.getAccStatus() == AcctStatus.NORECEIVE.getIntValue()) {
				throw makeAcctingException(acct, "00005");// 只付不收
			}
			if (acct.getAccStatus() == AcctStatus.CLEANED.getIntValue()) {
				throw makeAcctingException(acct, "00006");// 结清账户只能余额清0
			}

			// 计算余额和可用余额
			acct.setAccBalance(balance.add(input.getAmount()));
			acct.setAccAbbalance(usableBalance.add(input.getAmount()));
		} else {

			if (acct.getAccStatus() == AcctStatus.NOPAYMENT.getIntValue()) {
				throw makeAcctingException(acct, "00004");// 只收不付
			}
			if (acct.getAccStatus() == AcctStatus.CLEANED.getIntValue()) {
				if (balance.compareTo(input.getAmount()) != 0)
					throw makeAcctingException(acct, "00006");// 结清账户只能余额清0
			}

			// 计算余额和可用余额
			usableBalance = usableBalance.subtract(input.getAmount());

			try {
				if (usableBalance.doubleValue() < 0
						&& !acct.gotSubject().isOverdrawingAllowable()) {// 不能透支
					if (bAutoZeroBalance) {// 允许透支自动零余额记账
						input.setAmount(acct.getAccAbbalance());
						balance = balance.subtract(acct.getAccAbbalance());
						if (balance.doubleValue() < 0)
							balance = BigDecimal.ZERO;
						usableBalance = BigDecimal.ZERO;
					} else
						throw makeAcctingException(acct, "00002");
				} else
					balance = balance.subtract(input.getAmount());
				acct.setAccBalance(balance);
				acct.setAccAbbalance(usableBalance);
			} catch (NoSuchSubjectException e) {
				// never happen
			}
		}

		acct.setAccLtdate(acctingDate);

		acctRep.updateAccount(acct);
	}

	private AcctingException makeAcctingException(String errCode, String msg) {
		AcctingException ae = new AcctingException(errCode);
		ae.setErrMsgArgs(new String[] { msg });
		return ae;
	}

	private AcctingException makeAcctingException(Account acct, String errCode) {
		AcctingException ae = new AcctingException(errCode);
		ae.setErrMsgArgs(new String[] { acct.getAccNo() });
		return ae;
	}

	private AcctingException makeAcctingException(InnerAcctingInput acct,
			String errCode) {
		AcctingException ae = new AcctingException(errCode);
		ae.setErrMsgArgs(new String[] { acct.getAcctNO() });
		return ae;
	}

	/**
	 * 借贷输入信息检查
	 * @param inputs 输入信息
	 * @param bCredit 是否先贷后借
	 * @return
	 * @throws AcctingException
	 */
	private InnerAcctingInput[] prepareAcctingInput(CDAcctingInput inputs,
			boolean bCredit) throws AcctingException {
		// 检查输入信息合法性
		if (!(inputs.validate()).equals("")) {
			throw makeAcctingException("00013", inputs.validate());
		}
		InnerAcctingInput dcs[] = inputs.split();
		InnerAcctingInput dInput = dcs[0];
		InnerAcctingInput cInput = dcs[1];
		if (bCredit) {
			dcs[0] = cInput;
			dcs[1] = dInput;
		}
		return dcs;
	}

	/**
	 * 基本转账记账（一借一贷）
	 * @param inputs 借贷输入信息
	 * @param bAutoZeroBalance 透支自动零余额标志
	 * @param bCredit 贷方透支标志
	 * @return
	 * @throws AcctingException
	 */
	private CDAcctingOutput baseCDAccount(CDAcctingInput inputs,
			boolean bAutoZeroBalance, boolean bCredit) throws AcctingException {
		InnerAcctingInput dcs[] = prepareAcctingInput(inputs, bCredit);
		CDAcctingOutput out = new CDAcctingOutput();
		BigDecimal result[] = new BigDecimal[2];
		// 需要自己生成记账流水号,调用记账流水号生成器
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctingException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		String serial = null;
		try {
			serial = AcctingSerialGennerator.getSerial(accSysParam
					.getAcctingDate());
			if (null == serial)
				throw new AcctingException("00009"); // 生成记账流水号失败
		} catch (Exception e) {
			throw new AcctingException("00009"); // 生成记账流水号失败
		}
		// 获取记账日期
		String acctingDate = accSysParam.getAcctingDate();
		TXHelper.beginTX();
		try {
			result[0] = normalAccount(dcs[0], serial, 1, acctingDate, true,
					bAutoZeroBalance);
			if (bAutoZeroBalance && result[0].compareTo(BigDecimal.ZERO) == 0) {
				out.setAcctingAmount(dcs[0].getAmount());
				out.setOverdrawingAmount(dcs[1].getAmount().subtract(
						dcs[0].getAmount()));
				dcs[1].setAmount(dcs[0].getAmount());

			} else {
				out.setAcctingAmount(dcs[0].getAmount());
				out.setOverdrawingAmount(BigDecimal.ZERO);
			}
			result[1] = normalAccount(dcs[1], serial, 2, acctingDate, true,
					false);
			out.setAcctingDate(acctingDate);
			out.setSerial(serial);
			if (bCredit) {
				out.setDebitBalance(result[1]);
				out.setCreditBalance(result[0]);
			} else {
				out.setDebitBalance(result[0]);
				out.setCreditBalance(result[1]);
			}
			TXHelper.commit();
		} catch (AcctingException e) {
			LOG.error("db operation err", e);
			TXHelper.rollback();
			throw e;
		} finally {
			TXHelper.close();
		}
		return out;
	}

	/**
	 * 标准记账（适用正常记账和日终记账）
	 * 
	 * @param input
	 * @param bCurrAccList
	 * @return
	 * @throws AcctingException
	 */
	private BigDecimal normalAccount(InnerAcctingInput input, String serial,
			Integer sn, String acctingDate, boolean bCurrAccList,
			boolean bAutoZeroBalance)
			throws AcctingException {

		Account acct;
		// 检查系统状态(正在日切不能记账)
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw makeAcctingException(input, ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		if (SystemStatus.DATE_SWITCHING.getValue() == accSysParam
				.getSystemStatus()) {
			throw makeAcctingException(input, "00010"); // 正在日切不能记账
		}
//		if (new AcctingListDAO(bCurrAccList).getAcctListByKey(serial, sn) != null) {
//			AcctingException duplicateSerialErr = new AcctingException();
//			duplicateSerialErr.setErrorCode("00006");
//			duplicateSerialErr.setErrMsgArgs(new String[] { serial, sn + "" });
//			throw duplicateSerialErr;
//		}
		try {
			acct = new AccountRepsitory().lockAccount(input.getAcctNO());
		} catch (NoSuchAcctException e1) {
			throw makeAcctingException(input, "00001");
		}
		// --------------检查账户状态------------------------
		if (acct.getAccStatus() == AcctStatus.NORECEIVE_NOPAYMENT
				.getIntValue()
				|| acct.getAccStatus() == AcctStatus.CANCELD.getIntValue()) {
			throw makeAcctingException(input, "00003");
		}
		AcctSubject subject = getSubject(acct);
		updateAccount(input, acct, subject, acctingDate, bAutoZeroBalance); // 更新账务主档
		AcctingList list;
		try {
			list = input.toAcctingList();
		} catch (NoSuchAcctException e) {
			throw makeAcctingException(input, "00001");
		} catch (NoSuchSubjectException e) {
			throw makeAcctingException(input, "00001");
		}
		list.setSerial(serial);
		list.setSn(sn);
		list.setAcctingDate(acctingDate);
		try{
			new AcctingListDAO(bCurrAccList).add(list);
		}catch(Exception e){
			AcctingException duplicateSerialErr = new AcctingException();
			//modify by chenyuchang 20120202 修复判断是否检查分录流水重复的逻辑中抛的异常代码
			duplicateSerialErr.setErrorCode("00020");
			duplicateSerialErr.setErrMsgArgs(new String[] { serial, sn + "" });
			throw duplicateSerialErr;
		}
		return acct.getAccBalance();

	}

	/**
	 * 清算记账
	 * 
	 * @param input
	 * @return
	 * @throws AcctingException
	 */
	public AcctingOutput settlementAccount(InnerAcctingInput input)
			throws AcctingException {
		// 需要自己生成记账流水号,调用记账流水号生成器
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctingException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		String serial = null;
		try {
			serial = AcctingSerialGennerator.getSerial(accSysParam
					.getAcctingDate());
			if (null == serial)
				throw new AcctingException("00009"); // 生成记账流水号失败
		} catch (Exception e) {
			throw new AcctingException("00009"); // 生成记账流水号失败
		}
		// 获取记账日期
		String sysDate = accSysParam.getRollOffDate();
		AcctingOutput out = new AcctingOutput();
		List<BigDecimal> results = new ArrayList<BigDecimal>();
		// 记账（获取非当前分录流水表）
		results.add(normalAccount(input, serial, 1, sysDate, false, false));
		out.setSerial(serial);
		out.setAcctingDate(sysDate);
		out.setBalanceList(results);
		return out;
	}

	/**
	 * 日终轧账挂账
	 * 
	 * @param input
	 * @throws AcctingException
	 */
	public void runUpAccount(RunUpAcctInput input) throws AcctingException {
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam) {
			throw new AcctingException(ErrorDef.ACCT_SYS_PARAM_NOT_FOUND);
		}
		// 需要自己生成记账流水号,调用记账流水号生成器
		String serial = null;
		try {
			serial = AcctingSerialGennerator.getSerial(accSysParam
					.getAcctingDate());
			if (null == serial)
				throw new AcctingException("00009"); // 生成记账流水号失败
		} catch (Exception e) {
			throw new AcctingException("00009"); // 生成记账流水号失败
		}
		// 获取记账日期
		String sysDate = accSysParam.getRollOffDate();
		// 获取挂账账号
		String acctNo = accSysParam.getLoseAcctNO();
		// 获取挂账机构
		String branch = input.getTranBranch();
		// 准备单笔记账的输入信息
		InnerAcctingInput innerInput = new InnerAcctingInput();
		innerInput.setOperator("999999999");
		innerInput.setTranSerial(serial);
		innerInput.setTranBranch(branch);
		innerInput.setAcctNO(acctNo);
		innerInput.setAcctingCD(input.getAcctingCD());
		innerInput.setAmount(input.getAmount());
		// 记账（获取非当前分录流水表）
		normalAccount(innerInput, serial, 1, sysDate, false, false);
	}

}
