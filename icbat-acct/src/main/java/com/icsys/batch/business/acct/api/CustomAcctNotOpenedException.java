package com.icsys.batch.business.acct.api;
/**
 * 客户帐号未开，不能追加子账户
 * 
 * @author wangzheng
 *
 */
public class CustomAcctNotOpenedException extends AcctException{
     /**
     * 
     */
    private static final long serialVersionUID = -6755926467898723311L;

    public  CustomAcctNotOpenedException(String acctNO){
         this.setErrorCode("00004");
         this.setErrMsgArgs(new String[]{acctNO});
     }
     
     
}
