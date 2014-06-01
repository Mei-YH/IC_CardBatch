package com.icsys.batch.business.acct.api;

/**
 * 不允许开户异常类
 * 
 * @author kittyuu
 *
 */
public class OpenAcctNotAllowableException extends AcctException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2052434218292597832L;

	public OpenAcctNotAllowableException(String currType,String subjectCode){
        this.setErrorCode("00001");
        this.setErrMsgArgs(new String[]{currType,subjectCode});
    }
}
