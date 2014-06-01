package com.icsys.batch.task.profit.proxy.dao;

import java.util.List;

import com.icsys.batch.task.profit.proxy.bean.GrCardClear;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class GrCardClearDao {
	public static final String DS_NAME = "IC_DATASOURCE";
	public List<GrCardClear> showCardClear(String icCardno,String icIndex,String appId) {
		// TODO Auto-generated method stub
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
String sql = "select CAG_OFFACCT from GR_CARD_CLEAR where IC_CARDNO=#icCardno# and IC_INDEX=#icIndex# and APP_ID=#appId#";
GrCardClear al = new GrCardClear();
al.setIcCardno(icCardno);
al.setIcIndex(icIndex);
al.setAppId(appId);
List<GrCardClear> result=template.query(sql, al, GrCardClear.class);
return result;
	}

}
