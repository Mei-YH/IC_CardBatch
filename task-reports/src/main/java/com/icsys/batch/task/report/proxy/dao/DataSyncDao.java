package com.icsys.batch.task.report.proxy.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icsys.batch.task.report.proxy.bean.GrBusTrloglist;
import com.icsys.batch.task.report.proxy.bean.GrBusTxlist;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class DataSyncDao {
	/**
	 * 数据源
	 */
	public static final String DATASOURCE_NAME = "IC_DATASOURCE";

	/**
	 * @param sysdate
	 * @return 查询操作日志list
	 */
	public List<GrBusTrloglist> queryGrBusTrloglistData(String sysdate){
		NamedQueryTemplate queryTemp = TemplateManager.getNamedQueryTemplate(DATASOURCE_NAME);
		String sql =" select * from GR_BUS_TRLOGLIST where log_hdate=#logHdate# union all select * from GR_BUS_TRLOGLISTHIS where log_hdate=#logHdate#";
		GrBusTrloglist para = new GrBusTrloglist();

		para.setLogHdate(sysdate);
		return queryTemp.query(sql, para, GrBusTrloglist.class);
	}

	/**
	 * 删除指定时间操作日志表里数据
	 * @param cleardate
	 */
	public void deleteGrBusTrloglistData(String cleardate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DATASOURCE_NAME);
		String sql = "delete from GR_BUS_TRLOGLIST where log_hdate=#logHdate#";

		Map<String,Object> para = new HashMap<String, Object>();
		para.put("logHdate", cleardate);

		template.update(sql, para);
	}

	/**
	 * 删除指定时间操作日志历史表里数据
	 * @param cleardate
	 */
	public void deleteGrBusTrloglistHisData(String cleardate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DATASOURCE_NAME);
		String sql = "delete from GR_BUS_TRLOGLISTHIS where log_hdate=#logHdate#" ;

		Map<String,Object> para = new HashMap<String, Object>();
		para.put("logHdate", cleardate);

		template.update(sql, para);
	}
	/**
	 * @param sysdate
	 * @return 查询交易流水list
	 */
	public List<GrBusTxlist> queryGrBusTxlistData(String sysdate){
		NamedQueryTemplate queryTemp = TemplateManager.getNamedQueryTemplate(DATASOURCE_NAME);
		StringBuilder sql = new StringBuilder();
		sql.append("select * from GR_BUS_TXLISTA where TXL_PLSERIAL in(select log_plserial from GR_BUS_TRLOGLIST where log_hdate='"+sysdate+"' union all select log_plserial from GR_BUS_TRLOGLISTHIS where log_hdate='"+sysdate+"')");
		sql.append(" union all select * from GR_BUS_TXLISTB  where TXL_PLSERIAL in(select log_plserial from GR_BUS_TRLOGLIST where log_hdate='"+sysdate+"' union all select log_plserial from GR_BUS_TRLOGLISTHIS where log_hdate='"+sysdate+"')");
		sql.append(" union all select * from GR_BUS_TXLISTHIS where TXL_PLSERIAL in (select log_plserial from GR_BUS_TRLOGLIST where log_hdate='"+sysdate+"' union all select log_plserial from GR_BUS_TRLOGLISTHIS where log_hdate='"+sysdate+"')");
			
		return queryTemp.query(sql.toString(), null, GrBusTxlist.class);
	}

	/**
	 * 删除指定时间交易A流水表里数据
	 * @param cleardate
	 */
	public void deleteGrBusTxlistAData(String cleardate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DATASOURCE_NAME);
		String sql="delete from GR_BUS_TXLISTA where TXL_PLSERIAL in(select log_plserial from GR_BUS_TRLOGLIST where log_hdate=#logHdate# union all select log_plserial from GR_BUS_TRLOGLISTHIS where log_hdate=#logHdate#)";
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("logHdate", cleardate);

		template.update(sql, para);
	}

	/**
	 * 删除指定时间交易B流水表里数据
	 * @param cleardate
	 */
	public void deleteGrBusTxlistBData(String cleardate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DATASOURCE_NAME);
		String sql="delete from GR_BUS_TXLISTB where TXL_PLSERIAL in(select log_plserial from GR_BUS_TRLOGLIST where log_hdate=#logHdate# union all select log_plserial from GR_BUS_TRLOGLISTHIS where log_hdate=#logHdate#)";
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("logHdate", cleardate);

		template.update(sql, para);
	}

	/**
	 * 删除指定时间交易历史流水表里数据
	 * @param cleardate
	 */
	public void deleteGrBusTxlistHisData(String cleardate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DATASOURCE_NAME);
		String sql="delete from GR_BUS_TXLISTHIS where TXL_PLSERIAL in(select log_plserial from GR_BUS_TRLOGLIST where log_hdate=#logHdate# union all select log_plserial from GR_BUS_TRLOGLISTHIS where log_hdate=#logHdate#)";
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("logHdate", cleardate);

		template.update(sql, para);
	}
}
