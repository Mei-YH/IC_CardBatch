package com.icsys.batch.task.profit.proxy.dao;

import java.util.List;

import com.icsys.batch.task.profit.proxy.bean.IcCardabnReq;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;
/**
 * 
 * @author SDNX
 *
 */
public class GrCardabnReqDao {
//	public static final String DS_NAME = "IC_DATASOURCE";
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;
	/**
	 */
	public List<IcCardabnReq> showCardabn() {
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from IC_CARDABN_REQ where ABN_STAT ='0' and end_stat in(0,1,2)";
		IcCardabnReq al = new IcCardabnReq();
		List<IcCardabnReq> result = template.query(sql, al, IcCardabnReq.class);
		return result;
	}
}
