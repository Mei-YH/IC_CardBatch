package com.icsys.batch.business.acct.impl;

import java.math.BigDecimal;

import com.icsys.batch.business.acct.dao.GeneralAcctDAO;
import com.icsys.platform.dao.TXHelper;

/**
 * 总账
 * 
 * @author wangzheng
 * 
 */
public class GeneralAcct {

	private String branchCode;

	private String currType;

	private String subjectCode;

	private Integer maxSerial;

	private BigDecimal startBalance;

	private BigDecimal creditSum;

	private BigDecimal currentCreditSum;

	private BigDecimal debitSum;

	private BigDecimal currentDebitSum;

	private BigDecimal balance;
	
	private Integer balanceCD;

	private Long debitCount;

	private Long currentDebitCount;

	private Long creditCount;

	private Long currentCreditCount;

	private String years;

	private Integer monthes;

	public Integer getBalanceCD() {
		return balanceCD;
	}

	public void setBalanceCD(Integer balanceCD) {
		this.balanceCD = balanceCD;
	}

	public BigDecimal getCurrentCreditSum() {
		return currentCreditSum;
	}

	public void setCurrentCreditSum(BigDecimal currentCreditSum) {
		this.currentCreditSum = currentCreditSum;
	}

	public BigDecimal getCurrentDebitSum() {
		return currentDebitSum;
	}

	public void setCurrentDebitSum(BigDecimal currentDebitSum) {
		this.currentDebitSum = currentDebitSum;
	}

	public Long getCurrentDebitCount() {
		return currentDebitCount;
	}

	public void setCurrentDebitCount(Long currentDebitCount) {
		this.currentDebitCount = currentDebitCount;
	}

	public Long getCurrentCreditCount() {
		return currentCreditCount;
	}

	public void setCurrentCreditCount(Long currentCreditCount) {
		this.currentCreditCount = currentCreditCount;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public Integer getMonthes() {
		return monthes;
	}

	public void setMonthes(Integer monthes) {
		this.monthes = monthes;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public Integer getMaxSerial() {
		return maxSerial;
	}

	public void setMaxSerial(Integer maxSerial) {
		this.maxSerial = maxSerial;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getCreditSum() {
		return creditSum;
	}

	public void setCreditSum(BigDecimal creditSum) {
		this.creditSum = creditSum;
	}

	public BigDecimal getDebitSum() {
		return debitSum;
	}

	public void setDebitSum(BigDecimal debitSum) {
		this.debitSum = debitSum;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getDebitCount() {
		return debitCount;
	}

	public void setDebitCount(Long debitCount) {
		this.debitCount = debitCount;
	}

	public Long getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Long creditCount) {
		this.creditCount = creditCount;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * 获取分户账序号
	 * 
	 * @return
	 */
	public long getNextSubAcctSerial() {

		try {
			TXHelper.beginNewTX();
			GeneralAcct acct = GeneralAcctDAO.getForUpdate(branchCode,
					currType,
					subjectCode);
			Integer temp = acct.getMaxSerial();
			acct.setMaxSerial(temp + 1);
			GeneralAcctDAO.update(acct);
			TXHelper.commit();
			return temp + 1;
		} catch (Exception e) {
			TXHelper.rollback();
			throw new RuntimeException("fail to get sub acct serial");
		} finally {
			TXHelper.close();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchCode == null) ? 0 : branchCode.hashCode());
		result = prime * result
				+ ((currType == null) ? 0 : currType.hashCode());
		result = prime * result
				+ ((subjectCode == null) ? 0 : subjectCode.hashCode());
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
		GeneralAcct other = (GeneralAcct) obj;
		if (branchCode == null) {
			if (other.branchCode != null)
				return false;
		} else if (!branchCode.equals(other.branchCode))
			return false;
		if (currType == null) {
			if (other.currType != null)
				return false;
		} else if (!currType.equals(other.currType))
			return false;
		if (subjectCode == null) {
			if (other.subjectCode != null)
				return false;
		} else if (!subjectCode.equals(other.subjectCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GeneralAcct [balance=" + balance + ", balanceCD=" + balanceCD
				+ ", branchCode=" + branchCode + ", creditCount=" + creditCount
				+ ", creditSum=" + creditSum + ", currType=" + currType
				+ ", currentCreditCount=" + currentCreditCount
				+ ", currentCreditSum=" + currentCreditSum
				+ ", currentDebitCount=" + currentDebitCount
				+ ", currentDebitSum=" + currentDebitSum + ", debitCount="
				+ debitCount + ", debitSum=" + debitSum + ", maxSerial="
				+ maxSerial + ", monthes=" + monthes + ", startBalance="
				+ startBalance + ", subjectCode=" + subjectCode + ", years="
				+ years + "]";
	}

}
