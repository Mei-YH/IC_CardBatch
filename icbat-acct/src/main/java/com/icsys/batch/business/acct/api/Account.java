package com.icsys.batch.business.acct.api;

import java.math.BigDecimal;

import com.icsys.batch.business.acct.impl.AcctSubjectManagerImpl;

/**
 * 对应到帐务主档表
 * 
 * @author wangzheng
 * 
 */
public class Account {

	/**
	 * 账号（账户标识）
	 */
	private String accNo;

	/**
	 * 账户种类
	 */
	private String appId;

	/**
	 * 账务类型
	 */
	private String accType;

	/**
	 * 账户名称
	 */
	private String accName;

	/**
	 * 币别代码
	 */
	private String currencyCode;

	/**
	 * 科目号
	 */
	private String subjectCode;

	/**
	 * 启用日期
	 */
	private String accOpendate;

	/**
	 * 启用机构
	 */
	private String accOpbranch;

	/**
	 * 撤销机构
	 */
	private String closeBranch;

	/**
	 * 获取撤销机构
	 * 
	 * @return 撤销机构
	 */
	public String getCloseBranch() {
		return closeBranch;
	}

	/**
	 * 设置撤销机构
	 * 
	 * @param closeBranch
	 *            撤销机构
	 */
	public void setCloseBranch(String closeBranch) {
		this.closeBranch = closeBranch;
	}

	/**
	 * 启用操作员
	 */
	private String accOpteller;
	/**
	 * 前日余额
	 */
	private BigDecimal accYesbal = new BigDecimal(0.00);
	/**
	 * 账户余额
	 */
	private BigDecimal accBalance = new BigDecimal(0.00);

	/**
	 * 可用余额
	 */
	private BigDecimal accAbbalance = new BigDecimal(0.00);
	/**
	 * 止付金额
	 */
	private BigDecimal accStpayamount = new BigDecimal(0.00);

	/**
	 * 余额基数
	 */
	private BigDecimal accIntbase = new BigDecimal(0.00);
	/**
	 * 最后交易日期
	 */
	private String accLtdate;

	/**
	 * 计算利息方式
	 */
	private Integer accIntmode;

	/**
	 * 约定固定利率
	 */
	private BigDecimal accIntrate;
	/**
	 * 注销日期
	 */
	private String accCandate;
	/**
	 * 注销柜员
	 */
	private String accCanteller;

	/**
	 * 账户控制标志
	 */
	private String accConflag;

	/**
	 * 账户余额标志
	 */
	private String accBalcd;

	/**
	 * 账户状态
	 */
	private Integer accStatus = AcctStatus.COMMON.getIntValue();

	/**
	 * 获取账户种类
	 * 
	 * @return 账户种类
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * 设置账户种类
	 * 
	 * @param acctClass
	 *            账户种类
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * 获取账户控制标志
	 * 
	 * @return 账户控制标志
	 */
	public String getAccConflag() {
		return accConflag;
	}

	/**
	 * 设置账户控制标志
	 * 
	 * @param acctControlFlag
	 *            账户控制标志
	 */
	public void setAccConflag(String accConflag) {
		this.accConflag = accConflag;
	}

	/**
	 * 获取余额方向
	 * 
	 * @return 余额方向
	 */
	public String getAccBalcd() {
		return accBalcd;
	}

	/**
	 * 设置余额方向
	 * 
	 * @param balanceCD
	 *            余额方向
	 */
	public void setAccBalcd(String accBalcd) {
		this.accBalcd = accBalcd;
	}

	/**
	 * 获取账号
	 * 
	 * @return 账号
	 */
	public String getAccNo() {
		return accNo;
	}

	/**
	 * 设置账号
	 * 
	 * @param acctNO
	 *            账号
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	/**
	 * 获取账务类型
	 * 
	 * @return 账务类型
	 */
	public String getAccType() {
		return accType;
	}

	/**
	 * 设置账务类型
	 * 
	 * @param acctType
	 *            账务类型
	 */
	public void setAccType(String accType) {
		this.accType = accType;
	}

	/**
	 * 获取账户名称
	 * 
	 * @return 账户名称
	 */
	public String getAccName() {
		return accName;
	}

	/**
	 * 设置账户名称
	 * 
	 * @param acctName
	 *            账户名称
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * 获取币种
	 * 
	 * @return 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置币种
	 * 
	 * @param currType
	 *            币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 获取科目代码
	 * 
	 * @return 科目代码
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * 设置科目代码
	 * 
	 * @param subjectCode
	 *            科目代码
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * 获取开户日期
	 * 
	 * @return 开户日期
	 */
	public String getAccOpendate() {
		return accOpendate;
	}

	/**
	 * 设置开户日期
	 * 
	 * @param openDate
	 *            开户日期
	 */
	public void setAccOpendate(String accOpendate) {
		this.accOpendate = accOpendate;
	}

	/**
	 * 获取开户机构
	 * 
	 * @return 开户机构
	 */
	public String getAccOpbranch() {
		return accOpbranch;
	}

	/**
	 * 设置开户机构
	 * 
	 * @param openBranch
	 *            开户机构
	 */
	public void setAccOpbranch(String accOpbranch) {
		this.accOpbranch = accOpbranch;
	}

	/**
	 * 获取启用操作员
	 * 
	 * @return 启用操作员
	 */
	public String getAccOpteller() {
		return accOpteller;
	}

	/**
	 * 设置启用操作员
	 * 
	 * @param openTeller
	 *            启用操作员
	 */
	public void setAccOpteller(String accOpteller) {
		this.accOpteller = accOpteller;
	}

	/**
	 * 获取前日余额
	 * 
	 * @return 前日余额
	 */
	public BigDecimal getAccYesbal() {
		return accYesbal;
	}

	/**
	 * 设置前日余额
	 * 
	 * @param lastDayBalance
	 *            前日余额
	 */
	public void setAccYesbal(BigDecimal accYesbal) {
		this.accYesbal = accYesbal;
	}

	/**
	 * 获取余额
	 * 
	 * @return 余额
	 */
	public BigDecimal getAccBalance() {
		return accBalance;
	}

	/**
	 * 设置余额
	 * 
	 * @param balance
	 *            余额
	 */
	public void setAccBalance(BigDecimal accBalance) {
		this.accBalance = accBalance;
	}

	/**
	 * 获取可用余额
	 * 
	 * @return 可用余额
	 */
	public BigDecimal getAccAbbalance() {
		return accAbbalance;
	}

	/**
	 * 设置可用余额
	 * 
	 * @param usableBalance
	 *            可用余额
	 */
	public void setAccAbbalance(BigDecimal accAbbalance) {
		this.accAbbalance = accAbbalance;
	}

	/**
	 * 获取止付金额
	 * 
	 * @return 止付金额
	 */
	public BigDecimal getAccStpayamount() {
		return accStpayamount;
	}

	/**
	 * 设置止付金额
	 * 
	 * @param stopPayAmount
	 *            止付金额
	 */
	public void setAccStpayamount(BigDecimal accStpayamount) {
		this.accStpayamount = accStpayamount;
	}

	/**
	 * 获取余额基数
	 * 
	 * @return 余额基数
	 */
	public BigDecimal getAccIntbase() {
		return accIntbase;
	}

	/**
	 * 设置余额基数
	 * 
	 * @param interestBase
	 *            余额基数
	 */
	public void setAccIntbase(BigDecimal accIntbase) {
		this.accIntbase = accIntbase;
	}

	/**
	 * 获取最后交易日期
	 * 
	 * @return 最后交易日期
	 */
	public String getAccLtdate() {
		return accLtdate;
	}

	/**
	 * 设置最后交易日期
	 * 
	 * @param lastTranDate
	 *            最后交易日期
	 */
	public void setAccLtdate(String accLtdate) {
		this.accLtdate = accLtdate;
	}

	/**
	 * 获取计算利息方式
	 * 
	 * @return 计算利息方式
	 */
	public Integer getAccIntmode() {
		return accIntmode;
	}

	/**
	 * 设置计算利息方式
	 * 
	 * @param interestMode
	 *            计算利息方式
	 */
	public void setAccIntmode(Integer accIntmode) {
		this.accIntmode = accIntmode;
	}

	/**
	 * 获取约定固定利率
	 * 
	 * @return 约定固定利率
	 */
	public BigDecimal getAccIntrate() {
		return accIntrate;
	}

	/**
	 * 设置约定固定利率
	 * 
	 * @param interestRate
	 *            约定固定利率
	 */
	public void setAccIntrate(BigDecimal accIntrate) {
		this.accIntrate = accIntrate;
	}

	/**
	 * 获取注销日期
	 * 
	 * @return 注销日期
	 */
	public String getAccCandate() {
		return accCandate;
	}

	/**
	 * 设置注销日期
	 * 
	 * @param cancelDate
	 *            注销日期
	 */
	public void setAccCandate(String accCandate) {
		this.accCandate = accCandate;
	}

	/**
	 * 获取注销操作员
	 * 
	 * @return 注销操作员
	 */
	public String getAccCanteller() {
		return accCanteller;
	}

	/**
	 * 设置注销操作员
	 * 
	 * @param cancelTeller
	 *            注销操作员
	 */
	public void setAccCanteller(String accCanteller) {
		this.accCanteller = accCanteller;
	}

	/**
	 * 获取状态
	 * 
	 * @return 状态
	 */
	public Integer getAccStatus() {
		return accStatus;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 *            状态
	 */
	public void setAccStatus(Integer accStatus) {
		this.accStatus = accStatus;
	}

	@Override
	/*public String toString() {
		return "Account [acctClass=" + acctClass + ", acctControlFlag="
				+ acctControlFlag + ", acctNO=" + acctNO + ", acctName="
				+ acctName + ", acctType=" + acctType + ", balance=" + balance
				+ ", balanceCD=" + balanceCD + ", cancelDate=" + cancelDate
				+ ", cancelTeller=" + cancelTeller + ", currType=" + currType
				+ ", interestBase=" + interestBase + ", interestMode="
				+ interestMode + ", interestRate=" + interestRate
				+ ", lastDayBalance=" + lastDayBalance + ", lastTranDate="
				+ lastTranDate + ", openBranch=" + openBranch + ", openDate="
				+ openDate + ", openTeller=" + openTeller + ", status="
				+ status + ", stopPayAmount=" + stopPayAmount
				+ ", subjectCode=" + subjectCode + ", usableBalance="
				+ usableBalance + "]";
	}*/


	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accNo == null) ? 0 : accNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accNo == null) {
			if (other.accNo != null)
				return false;
		} else if (!accNo.equals(other.accNo))
			return false;
		return true;
	}

	/**
	 * 获取科目
	 * @return 科目代码
	 * @throws NoSuchSubjectException
	 */
	public AcctSubject gotSubject() throws NoSuchSubjectException {
		return new AcctSubjectManagerImpl().getSubject(getCurrencyCode(), this
				.getSubjectCode());
	}

}
