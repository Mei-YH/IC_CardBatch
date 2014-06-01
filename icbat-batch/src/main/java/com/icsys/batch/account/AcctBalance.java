package com.icsys.batch.account;

import java.math.BigDecimal;

public class AcctBalance {
	
	/**
     * 账号（账户标识）
     */
    private String acctNO;
    
    /**
     * 账户余额方向
     */
    private String balanceCD;
    
    /**
     * 账户余额
     */
    private BigDecimal acctBalance= new BigDecimal(0.00);

    /**
     * 止付金额
     */
    private BigDecimal stopPayBalance= new BigDecimal(0.00);
    
    /**
     * 可用余额
     */
    private BigDecimal usableBalance = new BigDecimal(0.00);
    
    /**
     * 账户控制标志  
     */
    private String acctControlFlag;
    
    /**
     * 账户状态
     */
    private String status;

	public String getAcctNO() {
		return acctNO;
	}

	public void setAcctNO(String acctNO) {
		this.acctNO = acctNO;
	}

	public String getBalanceCD() {
		return balanceCD;
	}

	public void setBalanceCD(String balanceCD) {
		this.balanceCD = balanceCD;
	}

	public BigDecimal getAcctBalance() {
		return acctBalance;
	}

	public void setAcctBalance(BigDecimal acctBalance) {
		this.acctBalance = acctBalance;
	}

	public BigDecimal getStopPayBalance() {
		return stopPayBalance;
	}

	public void setStopPayBalance(BigDecimal stopPayBalance) {
		this.stopPayBalance = stopPayBalance;
	}

	public BigDecimal getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(BigDecimal usableBalance) {
		this.usableBalance = usableBalance;
	}

	public String getAcctControlFlag() {
		return acctControlFlag;
	}

	public void setAcctControlFlag(String acctControlFlag) {
		this.acctControlFlag = acctControlFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AcctBalance [acctBalance=" + acctBalance + ", acctNO=" + acctNO
				+ ", acct_control_flag=" + acctControlFlag + ", balance_cd="
				+ balanceCD + ", status=" + status + ", stopPayBalance="
				+ stopPayBalance + ", usableBalance=" + usableBalance + "]";
	}
    
    
}
