package com.icsys.batch.business.dayend.api;

import com.icsys.platform.exceptions.AppException;

/**
 * 日终异常类
 * 
 * @author kittyuu
 *
 */
public class AcctDayEndException extends AppException {

	static {
		registerErrMsgs("META-INF/ACC_ERR.MSG");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5456755533171172765L;

	public AcctDayEndException() {
        super("004");
    }

    public AcctDayEndException(String errCode ) {
        super("004");
        this.setErrorCode(errCode);
    }
}
