package com.icsys.batch.business.acct.api;

/**
 * 账户已存在不能开户
 * 
 * @author kittyuu
 *
 */
public class OpenAcctException extends AcctException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -234081044620295408L;

	public OpenAcctException(String acctNO){
        super();
        this.setErrorCode("00011");
        this.setErrMsgArgs(new String[]{acctNO});
    }
}
