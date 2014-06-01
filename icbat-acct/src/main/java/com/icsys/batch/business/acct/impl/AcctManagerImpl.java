/**
 * 
 */
package com.icsys.batch.business.acct.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acct.api.AcctException;
import com.icsys.batch.business.acct.api.AcctManager;
import com.icsys.batch.business.acct.api.AcctStatus;
import com.icsys.batch.business.acct.api.AcctSubject;
import com.icsys.batch.business.acct.api.AcctSubjectManager;
import com.icsys.batch.business.acct.api.CDFlag;
import com.icsys.batch.business.acct.api.ChangeStatusNotAllowedException;
import com.icsys.batch.business.acct.api.CloseAcctInput;
import com.icsys.batch.business.acct.api.CloseAcctOutput;
import com.icsys.batch.business.acct.api.LogoffAcctNotAllowedException;
import com.icsys.batch.business.acct.api.NoSuchAcctException;
import com.icsys.batch.business.acct.api.NoSuchSubjectException;
import com.icsys.batch.business.acct.api.OpenAcctException;
import com.icsys.batch.business.acct.api.OpenAcctInfo;
import com.icsys.batch.business.acct.api.OpenAcctNotAllowableException;
import com.icsys.batch.business.acct.dao.GeneralAcctDAO;
import com.icsys.batch.business.acctsys.AcctSysParameter;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctting.api.AcctingException;
import com.icsys.batch.business.acctting.api.CDAcctingInput;
import com.icsys.batch.business.acctting.api.CDAcctingOutput;
import com.icsys.batch.business.acctting.impl.AccounterImpl;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.exceptions.SystemException;
import com.icsys.platform.util.DateUtils;
import com.icsys.platform.util.cache.NoSuchObjectException;

/**
 * 账户的管理
 * 
 * @author 王铮
 * 
 */
public class AcctManagerImpl implements AcctManager {

	private static Logger LOG = Logger.getLogger(AcctManagerImpl.class);

	/**
	 * 核算账户开户，帐号由系统生成
	 * 
	 * @return 账务主档信息
	 * @throws NoSuchBranchException
	 * @throws NoSuchSubjectException
	 * 
	 */
	public Account openAcct(OpenAcctInfo info) throws AcctException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("开立客户账 input is [" + info + "]");
			}
			TXHelper.beginTX();
			AcctSubject subject = checkGetSubject(info);
			String acctSerial = this.getAcctSerial(info.getBranchCode(),
					info.getCurrencyType(), info.getSubjectCode())
					+ "";
			// 个人账户生成规则：机构代码后9位（不足后面补0）+3位货币代码+4位科目代码+7位账户序号（不足前面补0）
			String acctNO = StringUtils.rightPad(info.getBranchCode(), 9, "0")
					+ // 9位机构码补齐
					info.getCurrencyType() + info.getSubjectCode()
					+ StringUtils.leftPad(acctSerial, 7, "0");
			if (LOG.isDebugEnabled()) {
				LOG.debug(" 账号[" + acctNO + "]");

			}
			Account acct = saveAcct(info, acctNO, subject);
			TXHelper.commit();
			return acct;
		} catch (AcctException e) {
			LOG.error("开立客户账，系统错", e);
			TXHelper.rollback();
			throw e;
		} catch (Exception e) {
			LOG.error("开立客户账，系统错", e);
			TXHelper.rollback();
			throw new SystemException(e);
		} finally {
			TXHelper.close();
		}
	}

	/**
	 * 开立内部账户固定后缀的
	 */
	public Account openInnerAcct(OpenAcctInfo info, String acctNoSuffix)
			throws AcctException {
		try {
			TXHelper.beginTX();
			AcctSubject subject = checkGetSubject(info);
			if (acctNoSuffix.length() <= 6 || acctNoSuffix.length() > 16) {
				throw new AcctException("00006");
			}
			// 固定后缀机构账户生成规则：机构代码后9位（不足后面补0）+3位货币代码+4位科目代码+账户后缀（7-16位）
			String subAcctNO = StringUtils.rightPad(info.getBranchCode(), 9,
					"0")
					+ // 9位机构码补齐
					info.getCurrencyType()
					+ info.getSubjectCode()
					+ acctNoSuffix; // 外部传入序号
			String acctNO = subAcctNO;
			// 检查账户是否存在
			try {
				new AccountRepsitory().getAccount(acctNO);
				// 账户存在则抛错
				throw new OpenAcctException(acctNO);
			} catch (NoSuchAcctException e) {
				// 检查总账是否开立
				GeneralAcct ga = GeneralAcctDAO.get(info.getBranchCode(), info
						.getCurrencyType(), info.getSubjectCode());
				if (ga == null) {
					ga = openGeneralAcct(info.getBranchCode(), info
							.getCurrencyType(), info.getSubjectCode());
				}
				Account acct = this.saveAcct(info, acctNO, subject);
				TXHelper.commit();
				return acct;
			}
		} catch (AcctException e) {
			LOG.error("开立内部账户,系统错", e);
			TXHelper.rollback();
			throw e;
		} catch (Exception e) {
			LOG.error("开立内部账户,系统错", e);
			TXHelper.rollback();
			throw new SystemException(e);
		} finally {
			TXHelper.close();
		}
	}

	/**
	 * 开立内部账户，自动生成帐号的
	 */
	public Account openInnerAcct(OpenAcctInfo info) throws AcctException {

		try {
			TXHelper.beginTX();
			AcctSubject subject = checkGetSubject(info);
			long subAcctSerial = this.getAcctSerial(info.getBranchCode(), info
					.getCurrencyType(),
					info.getSubjectCode());
			// 机构账户生成规则：机构代码后9位（不足后面补0）+3位货币代码+4位科目代码+账户序号（6位）
			String subAcctNO = StringUtils.rightPad(info.getBranchCode(), 9,
					"0")
					+ // 9位机构码补齐
					info.getCurrencyType()
					+ info.getSubjectCode()
					+ StringUtils.leftPad(subAcctSerial + "", 6, "0"); // 6位标准序号
			String acctNO = subAcctNO;

			Account acct = this.saveAcct(info, acctNO, subject);
			TXHelper.commit();
			return acct;
		} catch (AcctException e) {
			LOG.error("开立内部账户,系统错", e);
			TXHelper.rollback();
			throw e;
		} catch (Exception e) {
			LOG.error("开立内部账户,系统错", e);
			TXHelper.rollback();
			throw new SystemException(e);
		} finally {
			TXHelper.close();
		}
	}

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
			throws AcctException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("修改账户状态 ACCTNO[" + acctNO + "] target status " + statu);
		}

		if (statu.equals(AcctStatus.PARTIAL_PAYMENT)) {
			// 部分止付 不能用此接口
			throw new AcctException("00005");
		}
		AccountRepsitory rep = new AccountRepsitory();
		try {
			TXHelper.beginTX();
			Account acct = rep.lockAccount(acctNO);
			if (acct.getAccStatus() == AcctStatus.CANCELD.getIntValue()
					|| acct.getAccStatus() == AcctStatus.CLEANED.getIntValue()) {
				// 待结清或注销账户不能变更状态
				throw new ChangeStatusNotAllowedException(acctNO, acct
						.getAccStatus());
			}
			acct.setAccStatus(statu.getIntValue());
			rep.updateAccount(acct);
			TXHelper.commit();
		} catch (AcctException e) {
			LOG.error("修改账户状态错", e);
			TXHelper.rollback();
			throw e;
		} catch (Exception e) {
			TXHelper.rollback();
			LOG.error("修改账户状态错", e);
			throw new SystemException(e);
		} finally {
			TXHelper.close();
		}
	}

	/**
	 * 部分止付 状态从 正常的状态到部分止付
	 * 
	 * @param acctNO
	 *            账号
	 * @param stopAmount
	 *            止付金额
	 */
	public void partialStopPayment(String acctNO, BigDecimal stopAmount)
			throws AcctException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("部分止付 ACCTNO[" + acctNO + "] 止付金额 " + stopAmount);
		}

		AccountRepsitory rep;
		Account acct;
		try {
			TXHelper.beginTX();
			rep = new AccountRepsitory();
			acct = rep.lockAccount(acctNO);
			if (acct.getAccStatus() == AcctStatus.CANCELD.getIntValue()
					|| acct.getAccStatus() == AcctStatus.CLEANED.getIntValue()) {
				// 待结清或注销账户不能变更状态
				throw new ChangeStatusNotAllowedException(acctNO, acct
						.getAccStatus());
			}
			BigDecimal spAmt = acct.getAccStpayamount();
			spAmt = spAmt.add(stopAmount);
			acct.setAccStpayamount(spAmt);
			if (spAmt.compareTo(BigDecimal.ZERO) == 0)
				acct.setAccStatus(AcctStatus.COMMON.getIntValue());
			else
				acct.setAccStatus(AcctStatus.PARTIAL_PAYMENT.getIntValue());
			rep.updateAccount(acct);
			TXHelper.commit();
		} catch (AcctException e) {
			LOG.error("部分止付出错", e);
			TXHelper.rollback();
			throw e;
		} catch (Exception e) {
			TXHelper.rollback();
			LOG.error("部分止付出错", e);
			throw new SystemException(e);
		} finally {
			TXHelper.close();
		}

	}

	/**
	 * 结清账户
	 * 
	 * @param caInput
	 *            结清账户输入信息
	 * @return 结清账户输出信息（包括结清余额和对方账户交易后余额）
	 * @throws AcctException
	 */
	public CloseAcctOutput closeAccount(CloseAcctInput caInput)
			throws AcctException, AcctingException {
		if (!(caInput.validate()).equals("")) {
			throw makeAcctException("00013", caInput.validate());
		}
		AccountRepsitory rep = new AccountRepsitory();
		// 获取结清账户主档信息
		Account acct = rep.getAccount(caInput.getCloseAcctNO());
		// 检查结清账户状态(正常和待结清才可做结清)
		CloseAcctOutput out = null;
		if ((AcctStatus.COMMON.getIntValue() == acct.getAccStatus())
				|| (AcctStatus.CLEANED.getIntValue() == acct.getAccStatus())) {
			// 准备记账输入信息
			CDAcctingInput cdInput = new CDAcctingInput();
			cdInput.setAmount(acct.getAccBalance());
			if ("1".equals(acct.getAccBalcd())) {
				cdInput.setDebitAcctNO(caInput.getOtherAcctNO());
				cdInput.setCreditAcctNO(caInput.getCloseAcctNO());
			} else {
				cdInput.setDebitAcctNO(caInput.getCloseAcctNO());
				cdInput.setCreditAcctNO(caInput.getOtherAcctNO());
			}
			cdInput.setOperator(caInput.getOperator());
			cdInput.setRemark(caInput.getRemark());
			cdInput.setTranBranch(caInput.getTranBranch());
			cdInput.setTranSerial(caInput.getTranSerial());
			// 调用通用转账
			CDAcctingOutput cdOutput = new AccounterImpl().account(cdInput);
			out = new CloseAcctOutput();
			out.setSerial(cdOutput.getSerial());
			out.setAcctingDate(cdOutput.getAcctingDate());
			if ("1".equals(acct.getAccBalcd())) {
				out.setCloseBalance(acct.getAccBalance());
				out.setOtherBalance(cdOutput.getDebitBalance());
			} else {
				out.setCloseBalance(acct.getAccBalance());
				out.setOtherBalance(cdOutput.getCreditBalance());
			}
		}
		return out;
	}

	/**
	 * 注销账户
	 * 
	 * @param acctNO
	 *            账号
	 * @throws AcctException
	 */
	public void logoffAccount(String acctNO) throws AcctException {
		try {
			// 检查账户状态及余额
			AccountRepsitory rep = new AccountRepsitory();
			Account acct = rep.getAccount(acctNO);
			int pos = acct.getAccStatus();
			if (pos > 4)
				pos = pos - 3;
			AcctStatus status = AcctStatus.values()[pos];
			BigDecimal balance = acct.getAccBalance();
			// 只有余额为0和状态为正常或待结清的账户允许注销
			if (balance.compareTo(BigDecimal.ZERO) == 0
					&& (status == AcctStatus.CLEANED
					|| status == AcctStatus.COMMON))
				rep.updateAcctLogoff(acctNO);
			else {
				throw new LogoffAcctNotAllowedException(acctNO, status, balance);
			}
		} catch (LogoffAcctNotAllowedException e) {
			throw e;
		} catch (Exception e) {
			AcctException ae = new AcctException("00008"); // 注销账户失败
			ae.setErrMsgArgs(new String[] { acctNO });
			throw ae;
		}

	}

	/**
	 * 账户状态查询
	 * 
	 * @param acctNO
	 * @return
	 * @throws AcctException
	 */
	public AcctStatus queryAcctStatus(String acctNO) throws AcctException {
		AccountRepsitory rep = new AccountRepsitory();
		Account acct = rep.getAccount(acctNO);
		int pos = acct.getAccStatus();
		// 因为不是连续的0-4之后就是8和9了，8和9特殊处理下
		if (pos > 4)
			pos = pos - 3;
		AcctStatus result;
		try {
			result = AcctStatus.values()[pos];
		} catch (Exception e) {
			AcctException ae = new AcctException("00010"); // 获取账户状态失败
			ae.setErrMsgArgs(new String[] { acctNO });
			throw ae;
		}
		return result;
	}

	private AcctSubject checkGetSubject(OpenAcctInfo info) throws AcctException {
		if (!(info.validate()).equals("")) {
			throw makeAcctException("00013", info.validate());
		}
		AcctSubjectManagerImpl subjects = new AcctSubjectManagerImpl();
		AcctSubject subject = null;

		subject = subjects.getSubject(info.getCurrencyType(), info
				.getSubjectCode());
		if (!subject.isOpenAcctAllowable()) {
			throw new OpenAcctNotAllowableException(info.getCurrencyType(),
					info.getSubjectCode());
		}
		return subject;

	}

	/**
	 * 从总帐表获取子账户序号
	 * 
	 * @return
	 * @throws NoSuchSubjectException
	 * @throws NoSuchObjectException
	 */
	private long getAcctSerial(String branchCode, String currType,
			String subjectCode) throws Exception {
		GeneralAcct ga = GeneralAcctDAO.get(branchCode, currType, subjectCode);
		if (ga == null) {
			TXHelper.beginNewTX();
			try {
				ga = openGeneralAcct(branchCode, currType, subjectCode);
				ga.setMaxSerial(1);
				GeneralAcctDAO.update(ga);
				TXHelper.commit();
				return 1;
			} catch (Exception e) {
				try {
					TXHelper.rollback();
				} catch (Exception e1) {
					LOG.error(e1.getMessage(), e1);
				}
				throw e;
			} finally {
				TXHelper.close();
			}

		} else
			return ga.getNextSubAcctSerial();
	}

	/**
	 * 开设总账户，年总账户，月总账户
	 * 
	 * @param branchCode
	 * @param province
	 * @param currType
	 * @param subjectCode
	 * @param dao
	 * @return
	 */
	private GeneralAcct openGeneralAcct(String branchCode, String currType,
			String subjectCode) {
		GeneralAcct ga;
		ga = new GeneralAcct();
		ga.setBranchCode(branchCode);
		ga.setCurrType(currType);
		ga.setSubjectCode(subjectCode);
		AcctSubject subject = null;
		try {
			subject = new AcctSubjectManagerImpl().getSubject(currType,
					subjectCode);
		} catch (NoSuchSubjectException e) {
			// never happened
		}
		if (subject != null)
			ga.setBalanceCD(subject.getBalanceCD());
		ga.setBalance(BigDecimal.ZERO);
		ga.setCreditCount(new Long(0));
		ga.setCreditSum(BigDecimal.ZERO);
		ga.setDebitCount(new Long(0));
		ga.setDebitSum(BigDecimal.ZERO);
		ga.setStartBalance(BigDecimal.ZERO);
		// 开总账
		GeneralAcctDAO.add(ga);
		// 获取记账日期
		Date sysDate = null;
		AcctSysParameter accSysParam = AcctSysUtil.getInstance()
				.getAccSysParam();
		if (null == accSysParam)
			sysDate = DateUtils.strToDate(DateUtils.getCurrentDate());
		else
			sysDate = DateUtils.strToDate(accSysParam.getAcctingDate());
		// 获得年月信息
		int month = DateUtils.getDayMonth(sysDate);
		int year = DateUtils.getDayYear(sysDate);
		String years = String.valueOf(year);
		ga.setYears(years);
		ga.setMonthes(0);
		// 开年总账（月份为0）
		GeneralAcctDAO.addY(ga);
		ga.setMonthes(month);
		// 开当前月总账及年总账（月份为当前月）
		GeneralAcctDAO.addM(ga);
		GeneralAcctDAO.addY(ga);
		return ga;
	}

	private Account saveAcct(OpenAcctInfo info, String acctNO,
			AcctSubject subject)
			throws AcctException {
		Account acct = info.toAccount();
		// 账务类型与科目字典表中一致
		acct.setAccType(subject.getAcctingType().toString());
		// 借贷标志也从科目字典表中获取
		acct.setAccBalcd(subject.getBalanceCD().toString());
		acct.setAccNo(acctNO);
		// 从科目字典表中获取科目状态和控制标志
		String subFlag = subject.getSubControlFlag();
		char status = subFlag.charAt(0);
		char control = subFlag.charAt(1);
		acct.setAccStatus(Integer.parseInt(status + ""));
		acct.setAccConflag(control + "000000000");
		new AccountRepsitory().saveAccount(acct);
		return acct;
	}

	private AcctException makeAcctException(String errCode, String msg) {
		AcctException ae = new AcctException(errCode);
		ae.setErrMsgArgs(new String[] { msg });
		return ae;
	}

	public void branchMerger(String closeBranch, String mergeBranch)
			throws AcctingException {
		AccountRepsitory rep = new AccountRepsitory();
		boolean bNew = false;
		TXHelper.beginTX();
		try {
			// 修改账户主档的启用机构
			rep.updateAcctOpenBranch(closeBranch, mergeBranch);
			// 获取撤销机构的总账信息
			List<GeneralAcct> gas = GeneralAcctDAO.getByBranch(closeBranch);
			for (GeneralAcct ga : gas) {
				String currType = ga.getCurrType();
				String subjectCode = ga.getSubjectCode();
				// 获取并入机构的总账信息，没有则开并入机构的总账、月总账及年总账户
				GeneralAcct gaNew = GeneralAcctDAO.get(mergeBranch, currType,
						subjectCode);
				if (gaNew == null) {
					gaNew = openGeneralAcct(mergeBranch, currType, subjectCode);
					bNew = true;
				}
				// 获取记账日期
				Date sysDate = null;
				AcctSysParameter accSysParam = AcctSysUtil.getInstance()
						.getAccSysParam();
				if (null == accSysParam)
					sysDate = DateUtils.strToDate(DateUtils
							.getCurrentDate());
				else
					sysDate = DateUtils.strToDate(accSysParam
							.getAcctingDate());
				// 获得年月信息
				int month = DateUtils.getDayMonth(sysDate);
				int year = DateUtils.getDayYear(sysDate);
				String years = String.valueOf(year);
				// 修改并入机构的总账
				gaNew = mergeGeneralAcct(ga, gaNew);
				if (bNew) {
					gaNew.setMaxSerial(0);
					gaNew.setStartBalance(BigDecimal.ZERO);
				}
				GeneralAcctDAO.update(gaNew);
				// 清除撤销机构的余额
				clearBalance(ga);
				GeneralAcctDAO.update(ga);
				// 修改并入机构的月总账
				GeneralAcct gaOld = GeneralAcctDAO.getM(closeBranch,
						currType,
						subjectCode, month);
				if (!bNew)
					gaNew = GeneralAcctDAO.getM(mergeBranch, currType,
							subjectCode, month);
				gaNew = mergeGeneralAcct(gaOld, gaNew);
				GeneralAcctDAO.updateM(gaNew);
				// 清除撤销机构的余额
				clearBalance(gaOld);
				GeneralAcctDAO.updateM(gaOld);
				// 修改并入机构的年总账（0月）
				gaNew.setMonthes(0);
				gaOld = GeneralAcctDAO.getY(closeBranch, currType,
						subjectCode, 0, years);
				if (!bNew)
					gaNew = GeneralAcctDAO.getY(mergeBranch, currType,
							subjectCode, 0, years);
				gaNew = mergeGeneralAcct(gaOld, gaNew);
				GeneralAcctDAO.updateY(gaNew);
				// 清除撤销机构的余额
				clearBalance(gaOld);
				GeneralAcctDAO.updateY(gaOld);
			}
			TXHelper.commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			TXHelper.rollback();
			throw new AcctingException("机构撤并异常!");
		}
		TXHelper.close();
	}

	private BigDecimal amountPlus(BigDecimal a1, BigDecimal a2) {
		if (a1 == null)
			a1 = BigDecimal.ZERO;
		if (a2 == null)
			a2 = BigDecimal.ZERO;
		return a1.add(a2);
	}

	private Long countPlus(Long c1, Long c2) {
		if (c1 == null)
			c1 = new Long(0);
		if (c2 == null)
			c2 = new Long(0);
		return c1 + c2;
	}

	private GeneralAcct mergeGeneralAcct(GeneralAcct closeGa,
			GeneralAcct mergeGa) {
		mergeGa.setBalance(amountPlus(mergeGa.getBalance(), closeGa
				.getBalance()));
		mergeGa.setCreditSum(amountPlus(mergeGa.getCreditSum(), closeGa
				.getCreditSum()));
		mergeGa.setCurrentCreditSum(amountPlus(mergeGa.getCurrentCreditSum(),
				closeGa.getCurrentCreditSum()));
		mergeGa.setDebitSum(amountPlus(mergeGa.getDebitSum(), closeGa
				.getDebitSum()));
		mergeGa.setCurrentDebitSum(amountPlus(mergeGa.getCurrentDebitSum(),
				closeGa.getCurrentDebitSum()));

		mergeGa.setCreditCount(countPlus(mergeGa.getCreditCount(), closeGa
				.getCreditCount()));
		mergeGa.setCurrentCreditCount(countPlus(
				mergeGa.getCurrentCreditCount(), closeGa
				.getCurrentCreditCount()));
		mergeGa
				.setDebitCount(countPlus(mergeGa.getDebitCount(), closeGa
				.getDebitCount()));
		mergeGa.setCurrentDebitCount(countPlus(mergeGa.getCurrentDebitCount(),
				closeGa
				.getCurrentDebitCount()));
		return mergeGa;
	}

	private void clearBalance(GeneralAcct closeGa) {
		// 将撤销机构的余额增加到相应的发生额上
		if (closeGa.getBalanceCD() == CDFlag.DEBIT.getFlag()) {
			if (closeGa.getCreditCount() != null)
				closeGa.setCreditCount(closeGa.getCreditCount() + 1);
			if (closeGa.getCreditSum() != null)
				closeGa.setCreditSum(closeGa.getCreditSum().add(
						closeGa.getBalance()));
			if (closeGa.getCurrentCreditCount() != null)
				closeGa
						.setCurrentCreditCount(closeGa.getCurrentCreditCount() + 1);
			if (closeGa.getCurrentCreditSum() != null)
				closeGa.setCurrentCreditSum(closeGa.getCurrentCreditSum().add(
						closeGa.getBalance()));

		} else {
			if (closeGa.getDebitCount() != null)
				closeGa.setDebitCount(closeGa.getDebitCount() + 1);
			if (closeGa.getDebitSum() != null)
				closeGa.setDebitSum(closeGa.getDebitSum().add(
						closeGa.getBalance()));
			if (closeGa.getCurrentDebitCount() != null)
				closeGa
						.setCurrentDebitCount(closeGa.getCurrentDebitCount() + 1);
			if (closeGa.getCurrentDebitSum() != null)
				closeGa.setCurrentDebitSum(closeGa.getCurrentDebitSum().add(
						closeGa.getBalance()));
		}
		// 清楚撤销机构的余额
		closeGa.setBalance(BigDecimal.ZERO);
	}
}
