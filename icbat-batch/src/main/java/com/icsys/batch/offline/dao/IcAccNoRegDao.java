package com.icsys.batch.offline.dao;

import java.util.Map;

import com.icsys.batch.offline.bean.SpecialAccount;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class IcAccNoRegDao {
	
	private NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
	
	/**
	 * 根据主键查询专用账户
	 * @param params
	 * @return
	 */
	public SpecialAccount querySpecialAccount(SpecialAccount params){
		String sql = "select * from IC_ACC_NO_REG t where t.USE_INDEX=#USE_INDEX# and BRANCH_CODE=#BRANCH_CODE#";
		SpecialAccount specialAccount  = dq.queryRow(sql, params, SpecialAccount.class);
		return specialAccount;
	}
	
	
	/**
	 * 根据主键查询专用账户
	 * @param params
	 * @return
	 */
	public SpecialAccount querySpecialAccount(String useIndex,String branchCode){
		SpecialAccount params = new SpecialAccount();
		params.setUseIndex(useIndex);
		params.setBranchCode(branchCode);
		String sql = "select * from IC_ACC_NO_REG t where t.USE_INDEX=#USE_INDEX# and BRANCH_CODE=#BRANCH_CODE#";
		SpecialAccount specialAccount  = dq.queryRow(sql, params, SpecialAccount.class);
		return specialAccount;
	}
	
	/**
	 * 根据账户查询描述信息
	 * @param acctNo
	 * @return
	 */
	public String getDescByAcctNo(String acctNo){
		String desc = "";
		String querySql = "select use_description desc from IC_ACC_NO_REG t where t.acct_no='"+acctNo+"'";
		Map<String,Object> result = dq.queryRow(querySql, null);
		if(result!=null){
			desc = result.get("desc")+"";
		}
		return desc;
	}

}
