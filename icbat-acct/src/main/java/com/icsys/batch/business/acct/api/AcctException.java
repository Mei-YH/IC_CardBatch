package com.icsys.batch.business.acct.api;

import com.icsys.platform.exceptions.AppException;

/**
 * 账户管理异常类
 * 
 * @author kittyuu
 *
 */
public class AcctException extends AppException {

	static {
		registerErrMsgs("META-INF/ACC_ERR.MSG");
	}

	/**
     * 
     */
	private static final long serialVersionUID = 6885654658130851774L;

	public AcctException() {
		super("000");
	}

	public AcctException(String errCode) {
		super("000");
		this.setErrorCode(errCode);
	}

}
