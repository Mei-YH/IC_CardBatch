package com.icsys.batch.business.acctting.api;

import com.icsys.platform.exceptions.AppException;

/**
 * 记账异常类
 * 
 * @author kittyuu
 *
 */
public class AcctingException extends AppException{

	static {
		registerErrMsgs("META-INF/ACC_ERR.MSG");
	}
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3359901376959902100L;

	public AcctingException() {
        super("001");
    }

    public AcctingException(String errCode ) {
        super("001");
        this.setErrorCode(errCode);
    }
}
