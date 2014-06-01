package com.icsys.batch.offline.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.icsys.batch.offline.bean.GrAreacodeInfo;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class GrAreacodeInfoDao {

	public GrAreacodeInfo getCoreArea(String cupsCode){
		NamedQueryTemplate tempQuery = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select * from gr_areacode_info where cups_branch_code=#cupsBranchCode#";
		GrAreacodeInfo area = new GrAreacodeInfo();
		area.setCupsBranchCode(cupsCode);
		return tempQuery.queryRow(sql, area, GrAreacodeInfo.class);
	}
	
	public List<String> queryCityCode(){
		NamedQueryTemplate tempQuery = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select CORE_AREA from GR_AREACODE_INFO";
		List<Map<String,Object>> lists = tempQuery.query(sql, null);
		List<String> res = new ArrayList<String>();
		for(Map<String,Object> temp:lists){
			res.add(String.valueOf(temp.get("CORE_AREA")));
		}
		return res;
	}

	public GrAreacodeInfo queryBillOrg(String coreArea){
		NamedQueryTemplate tempQuery = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select * from GR_AREACODE_INFO where CORE_AREA=#coreArea#";
		GrAreacodeInfo area = new GrAreacodeInfo();
		area.setCoreArea(coreArea);
		return tempQuery.queryRow(sql, area, GrAreacodeInfo.class);
	}
}
