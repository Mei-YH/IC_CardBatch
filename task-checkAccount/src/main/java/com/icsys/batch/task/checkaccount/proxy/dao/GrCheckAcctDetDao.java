package com.icsys.batch.task.checkaccount.proxy.dao;

import java.util.List;

import com.icsys.batch.task.checkaccount.proxy.bean.GrCheckAcctDet;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

/**
 * 
 * @author liuyb
 *
 */
public class GrCheckAcctDetDao {

	public int insertDet(GrCheckAcctDet det){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "insert into gr_check_acct_det(check_id,channel,check_date,det1,det2,det3,det4,det5,det6,det7,det8,det9,det10,det11,det12," +
				"det13,det14,det15,det16,det17,det18,det19,det20,det21,det22,det23,det24,det25,det26,det27,det28,det29,det30,det31,det32,det33," +
				"det34,det35,det36,det37,det38,det39,det40,det41,det42,det43,det44,det45,det46,det47,det48,det49,det50,det51,det52,det53,det54," +
				"det55,det56,det57,det58,det59,det60,det61,det62,det63,det64,det65,det66,det67,det68,det69,det70,det71,det72) " +
				"values(#checkId#,#channel#,#checkDate#,#det1#,#det2#,#det3#,#det4#,#det5#,#det6#,#det7#,#det8#,#det9#,#det10#,#det11#,#det12#," +
				"#det13#,#det14#,#det15#,#det16#,#det17#,#det18#,#det19#,#det20#,#det21#,#det22#,#det23#,#det24#,#det25#,#det26#,#det27#,#det28#," +
				"#det29#,#det30#,#det31#,#det32#,#det33#,#det34#,#det35#,#det36#,#det37#,#det38#,#det39#,#det40#,#det41#,#det42#,#det43#,#det44#," +
				"#det45#,#det46#,#det47#,#det48#,#det49#,#det50#,#det51#,#det52#,#det53#,#det54#,#det55#,#det56#,#det57#,#det58#,#det59#,#det60#," +
				"#det61#,#det62#,#det63#,#det64#,#det65#,#det66#,#det67#,#det68#,#det69#,#det70#,#det71#,#det72#)";
		return template.update(sql, det);
	}
	
	public void deleteDet(String channel,String lastDate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "delete from gr_check_acct_det where channel=#channel# and check_date=#checkDate#";
		GrCheckAcctDet det = new GrCheckAcctDet();
		det.setChannel(channel);
		det.setCheckDate(lastDate);
		template.update(sql, det);
	}
	
	public List<GrCheckAcctDet> queryDetail(String channel,String checkDate){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select * from gr_check_acct_det where channel=#channel# and " +
				"(check_date=#checkDate# and trim(det5)=check_date)";
		GrCheckAcctDet detail = new GrCheckAcctDet();
		detail.setChannel(channel);
		detail.setCheckDate(checkDate);
		return template.query(sql, detail, GrCheckAcctDet.class);
	}
	
	public List<GrCheckAcctDet> queryResultDetail(String channel,String checkDate,String arterDate){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select a.* from gr_check_acct_det a inner join gr_bus_cheacctxlist b " +
				"on trim(a.det4)=b.platform_serial where a.channel=#channel# and a.check_date=#checkDate# " +
				"and b.reconciliation_logo='11' and a.det72='0'";
		GrCheckAcctDet detail = new GrCheckAcctDet();
		detail.setChannel(channel);
		detail.setCheckDate(checkDate);
		return template.query(sql, detail, GrCheckAcctDet.class);
	}
	
	public List<GrCheckAcctDet> queryResultErrDetail(String channel,String checkDate,String arterDate){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select a.* from gr_check_acct_det a inner join gr_bus_cheacctxlist b " +
				"on trim(a.det4)=b.platform_serial where a.channel=#channel# and a.check_date=#checkDate# " +
				"and (b.reconciliation_logo<>'11' or a.det72='1') union all " +
				"select * from gr_check_acct_det where channel=#channel# and check_date=#checkDate# and trim(det5)=#det1#";
		GrCheckAcctDet detail = new GrCheckAcctDet();
		detail.setChannel(channel);
		detail.setCheckDate(checkDate);
		detail.setDet1(arterDate);
		return template.query(sql, detail, GrCheckAcctDet.class);
	}
}
