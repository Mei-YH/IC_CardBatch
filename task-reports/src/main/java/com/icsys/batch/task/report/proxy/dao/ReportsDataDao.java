package com.icsys.batch.task.report.proxy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.icsys.batch.task.report.proxy.bean.GrIcgqzsyDet;
import com.icsys.batch.task.report.proxy.bean.GrSleepAcctDetail;
import com.icsys.batch.task.report.proxy.bean.VmIcjyltjBb;
import com.icsys.batch.task.report.proxy.bean.VmIckltjBb;
import com.icsys.batch.task.report.proxy.bean.VmIczhjytjBb;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class ReportsDataDao {

	private NamedQueryTemplate queryTemp = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
	
	public List<String> queryCityCode(){
		String sql = "select CORE_AREA_NO from GR_AREACODE_INFO";
		List<Map<String,Object>> lists = queryTemp.query(sql, null);
		List<String> res = new ArrayList<String>();
		for(Map<String,Object> temp:lists){
			res.add(String.valueOf(temp.get("CORE_AREA_NO")));
		}
		return res;
	}
	
	public List<VmIcjyltjBb> queryVMJYLData(String date,String cityCode){
//		String sql = "select * from vm_icjyltj_bb where ic_date=#icDate# and CORE_AREA_NO=#coreAreaNo#";
		String sql = "{call vm_icjyltj_bb(#icDate#,#coreAreaNo#)}";
		VmIcjyltjBb para = new VmIcjyltjBb();
		para.setIcDate(date);
		para.setCoreAreaNo(cityCode);
		return queryTemp.queryProc(sql, para, VmIcjyltjBb.class);
	}
	
	public List<VmIckltjBb> queryVMKLData(String date,String cityCode){
//		String sql = "select * from vm_ickltj_bb where ic_date=#icDate# and CORE_AREA_NO=#coreAreaNo#";
		String sql = "{call vm_ickltj_bb(#icDate#,#coreAreaNo#)}";
		VmIckltjBb para = new VmIckltjBb();
		para.setIcDate(date);
		para.setCoreAreaNo(cityCode);
		return queryTemp.queryProc(sql, para, VmIckltjBb.class);
	}
	
	public List<VmIczhjytjBb> queryVMZHJYData(String date,String cityCode){
//		String sql = "select * from vm_iczhjytj_bb where ic_date=#icDate# and CORE_AREA_NO=#coreAreaNo#";
		String sql = "{call vm_iczhjytj_bb(#icDate#,#coreAreaNo#)}";
		VmIczhjytjBb para = new VmIczhjytjBb();
		para.setIcDate(date);
		para.setCoreAreaNo(cityCode);
		return queryTemp.queryProc(sql, para, VmIczhjytjBb.class);
	}
	
	public List<GrIcgqzsyDet> queryICGQZSYData(String date,String cityCode){
		String sql = "select * from gr_icgqzsy_det where ic_date=#icDate# and ic_pro=#icPro#";
		GrIcgqzsyDet para = new GrIcgqzsyDet();
		para.setIcDate(date);
		para.setIcPro(cityCode);
		return queryTemp.query(sql, para, GrIcgqzsyDet.class);
	}

//	public List<GrLocalCheckacct> queryCheckAcctData(String date,String cityCode){
//		String sql = "select * from gr_local_checkacct where ic_sysdate=#icSysdate# and substr(TRANSBRC,2,2)=#transbrc#";
//		GrLocalCheckacct para = new GrLocalCheckacct();
//		para.setIcSysdate(date);
//		para.setTransbrc(cityCode);
//		return queryTemp.query(sql, para, GrLocalCheckacct.class);
//	}

	public List<GrSleepAcctDetail> querySleepDetailData(String date,String cityCode){
		String sql = "select * from gr_sleep_acct_detail where ic_date=#icDate# and ic_pro=#icPro#";
		GrSleepAcctDetail para = new GrSleepAcctDetail();
		para.setIcDate(date);
		para.setIcPro(cityCode);
		return queryTemp.query(sql, para, GrSleepAcctDetail.class);
	}
}
