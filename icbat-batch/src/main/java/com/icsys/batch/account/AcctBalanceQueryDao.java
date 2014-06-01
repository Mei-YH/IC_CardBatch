package com.icsys.batch.account;

import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class AcctBalanceQueryDao {

	
	public AcctBalanceQuery getAccount(String acctNo) {
		
        NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
        String sql = "select acc_no as acct_no,acc_balance as balance from gr_acc_acct where ACC_NO=#ACCT_NO#";
        AcctBalanceQuery account = new AcctBalanceQuery();
        account.setAcctNo(acctNo);
        AcctBalanceQuery result = null;
		try {
			result = template.queryRow(sql, account, AcctBalanceQuery.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return result;
    }
}
