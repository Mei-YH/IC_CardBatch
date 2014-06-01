package com.icsys.batch.business.acct.api;

/**
 * 账户状态
 * 
 * @author wangzheng
 *
 */
public enum AcctStatus {
    
    COMMON(0), 
    NORECEIVE_NOPAYMENT(1), //不收不付
    NOPAYMENT(2),           //只收不付
    NORECEIVE(3),           //只付不收
    PARTIAL_PAYMENT(4),     //部分只付
    CLEANED(8),             //已经清算
    CANCELD(9)              //已经注销
    ;
   
    private final int intVlaue;
    
    private AcctStatus(int key) {  
        this.intVlaue = key;  
   }  
    
   public int getIntValue(){
       return this.intVlaue;
   }
}
