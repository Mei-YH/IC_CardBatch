package com.icsys.batch.business.acct.api;

/**
 * 账户不存在异常类
 * 
 * @author kittyuu
 *
 */
public class NoSuchAcctException extends AcctException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5290865947371628932L;

	public NoSuchAcctException(String acctNo){
        super();
        this.setErrorCode("00007");
        this.setErrMsgArgs(new String[]{acctNo});
        
    }
    
}
