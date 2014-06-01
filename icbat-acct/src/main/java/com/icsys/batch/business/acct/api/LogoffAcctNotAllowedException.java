package com.icsys.batch.business.acct.api;

import java.math.BigDecimal;

/**
 * 不允许账户注销异常类
 * 
 * @author kittyuu
 *
 */
public class LogoffAcctNotAllowedException extends AcctException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -456102948900428188L;

	public LogoffAcctNotAllowedException(String acctNO, AcctStatus status,
			BigDecimal balance) {
		this.setErrorCode("00012");
		this.setErrMsgArgs(new String[] { acctNO, status.toString(),
				balance.toString() });
	}
}
