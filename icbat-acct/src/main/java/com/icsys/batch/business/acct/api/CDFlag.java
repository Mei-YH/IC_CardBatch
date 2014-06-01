package com.icsys.batch.business.acct.api;

/**
 * 借贷标志
 * 
 * @author wangzheng
 *
 */
public enum CDFlag {
    
    DEBIT(1), CREDIT(2);

    private int flag;

    private CDFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return this.flag;
    }
}
