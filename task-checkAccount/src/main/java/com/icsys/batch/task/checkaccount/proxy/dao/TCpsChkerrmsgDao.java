package com.icsys.batch.task.checkaccount.proxy.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icsys.batch.task.checkaccount.proxy.bean.TCpsChkerrmsg;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class TCpsChkerrmsgDao {

	public List<TCpsChkerrmsg> queryErrResultDetail(String checkDate,String sysData){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select * from t_cps_chkerrmsg where trim(stldate)=#chkdate# and workdate=#workdate#";
		TCpsChkerrmsg para = new TCpsChkerrmsg();
		para.setChkdate(checkDate);
		para.setWorkdate(sysData);
		return template.query(sql, para, TCpsChkerrmsg.class);
	}
	
	public void insertData(String channel,String checkDate,String jobDate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "insert into t_cps_chkerrmsg(workdate,serialno,chkdate,chkstu,chkmsg,srctable,cboddate,cbodsn,cbodamt,inacbank,inisbank,isbkfeout," +
				"isbkfein,acbkfee,custfee1,cny,acbank,isbank,dramt,cramt,dscrpcod,sendtype,stlbrno,chlflag,transtu,txtm,posno,possqno,authno,trandate," +
				"merno,trantype2,gcrddate,drgndate,drgnsqno,gcrdsqno,busndate,procod,tranout,tranin,trancod,stldate,cardno," +
				
				"acctno,amount,chargefee,payablefee,recvbank1,accpbank,sendbank,sysserno,thirdsystime) select '" + jobDate + "',det4,det3," +
				"case when che_reclogo='11' and che_wcstate='3' then '8' else case when che_reclogo='01' then '9' else case when che_reclogo='10' then '7' " +
				"else case when che_reclogo='21' then 'A' else case when che_reclogo='22' then 'B' else '' end end end end end as chkstu," +
				
				"case when che_reclogo='11' and che_wcstate='3' then '交易成功写卡失败' else case when che_reclogo='01' then '银联无核心有IC无' " +
				"else case when che_reclogo in('10','12') then '银联无核心无IC有' else case when che_reclogo='21' then '核心有IC无金额不符' " +
				"else case when che_reclogo='22' then 'IC金额不符' else '' end end end end end as chkmsg," +
				"'T_CPS_CBODCHKINFO',det1,det2,det9,det19,det20,det10,det11,det12,det29,det53,det33,det32,det27,det28,det37,det41,det49,det51,det72," +
				"det47,det22,det23,det25,det34,det35,det36,det42,det43,det44,det45,det50,det59,det60,det61,det62,det63,det26," +
				
				"det8,decimal(det9),decimal(det11)+decimal(det12),decimal(det10)+decimal(det13)," +
				"(select cups_branch_code from gr_areacode_info where core_area_no=substr(det20,2,2))," +
				"det16,det17,det15,det14 from gr_check_acct_det a " +
				"inner join gr_bus_cheaccerrlist b on trim(a.det4)=b.che_plserial where a.channel=#channel# and a.check_date=#checkDate#";
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("channel", channel);
		para.put("checkDate", checkDate);
		template.update(sql, para);
	}
}
