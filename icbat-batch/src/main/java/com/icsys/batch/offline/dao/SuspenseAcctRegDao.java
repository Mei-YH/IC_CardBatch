package com.icsys.batch.offline.dao;

import com.icsys.batch.offline.bean.SuspenseAcctRegBean;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class SuspenseAcctRegDao {
	private NamedUpdateTemplate dm = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
	private NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);

	public int insertSusAcctReg(SuspenseAcctRegBean bean){
		String sql = "select * from gr_bis_suaccreg where sua_trtime=#sua_trtime# and sua_systrno=#sua_systrno# and sua_ppid=#sua_ppid# and sua_sbid=#sua_sbid#";
		SuspenseAcctRegBean temp = dq.queryRow(sql, bean,SuspenseAcctRegBean.class);
		if(null != temp){
			return updateSusAcctReg(bean);
		}else{
			sql = "insert into gr_bus_suaccreg (sua_trdate,sua_plserial,sua_serial,sua_trbranch,ic_cardno,sua_samount," +
			"sua_ctype,transfer_from_acct_no,sua_tstacctno,sua_trtime,sua_systrno,sua_ppid,sua_sbid,sua_deflag,sua_remark)" +
			" values(#suaTrdate#,#suaPlserial#,#suaSerial#,#suaTrbranch#,#icCardno#,#suaSamount#,#suaCtype#," +
			"#transferFormAcctNo#,#suaTstacctno#,#suaTrtime#,#suaSystrno#,#suaPpid#,#suaSbid#,#suaDeflag#,#suaRemark#)";
			return	dm.update(sql, bean);
		}
	}
	
	public SuspenseAcctRegBean getSusAcctReg(){
		String sql = "select * from gr_bus_suaccreg";
		return dq.queryRow(sql, null,SuspenseAcctRegBean.class);
	}
	
	public int updateSusAcctReg(SuspenseAcctRegBean bean){
		String sql = "update gr_bus_suaccreg set sua_trdate=#sua_trdate#,sua_plserial=#sua_plserial#,sua_serial=#sua_serial#,sua_trbranch=#sua_trbranch#," +
				"ic_cardno=#ic_cardno#,sua_samount=#sua_samount#,sua_ctype=#sua_ctype#,transfer_from_acct_no=#transfer_from_acct_no#," +
				"sua_tstacctno=#sua_tstacctno#,sua_trtime=#sua_trtime#,sua_systrno=#sua_systrno#,sua_ppid=#sua_ppid#,sua_sbid=#sua_sbid#," +
				"sua_deflag=#sua_deflag#,sua_remark=#sua_remark# where sua_trtime=#sua_trtime# and sua_systrno=#sua_systrno# and " +
				"sua_ppid=#sua_ppid# and sua_sbid=#sua_sbid#";
		return	dm.update(sql, bean);
	}
}
