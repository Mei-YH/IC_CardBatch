package com.icsys.batch.business.acct.api;

/**
 * 科目不存在异常类
 * 
 * @author kittyuu
 *
 */
public class NoSuchSubjectException extends AcctException{
    /**
     * 
     */
    private static final long serialVersionUID = -1423732062969237401L;
   
  
  
    public NoSuchSubjectException(String currType, String subjectCode) {
        super();
        this.setErrorCode("00002");
        this.setErrMsgArgs(new String[]{currType,subjectCode});
    }
   
    
}
