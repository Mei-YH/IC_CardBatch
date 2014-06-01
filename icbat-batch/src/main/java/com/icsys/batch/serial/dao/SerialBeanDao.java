package com.icsys.batch.serial.dao;

import org.apache.log4j.Logger;

import com.icsys.batch.serial.bean.SerialBean;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

/**
 * @author Runpu Hu
 * @version 创建时间：2011-5-3 下午05:04:16 类说明 ：
 */

public class SerialBeanDao {
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	private static Logger LOG = Logger.getLogger(SerialBeanDao.class);

	/**
	 * 根据流水号index来取流水控制记录
	 * 
	 * @param appNo
	 *            应用编号
	 * @return
	 */
	public SerialBean getSerialBeanByIndex(String index, String table) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from " + table
				+ " where SERIAL_INDEX=#SERIAL_INDEX#";
		SerialBean sb = new SerialBean();
		sb.setSerialIndex(index);
		LOG.debug("SELECT SERIAL_CTRL index IS:" + index);
		SerialBean result = template.queryRow(sql, sb, SerialBean.class);
		return result;

	}

	/**
	 * 根据应用编号来取一条记录（更新以后释放锁）
	 * 
	 * @param appNo
	 *            应用编号
	 * @return
	 */
	public SerialBean getSerialBeanForUpdate(String index, String table) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from " + table
				+ " where SERIAL_INDEX=#SERIAL_INDEX# for update";
		SerialBean sb = new SerialBean();
		sb.setSerialIndex(index);
		LOG.debug("SELECT GR_SYS_SERCTRL index IS:" + index);
		SerialBean result = template.queryRow(sql, sb, SerialBean.class);
		return result;

	}

	public void addIcSerialCtrl(SerialBean serialCtrl) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "insert into SERIAL_CTRL (SERIAL_INDEX,SERIAL_LIMIT,CACHE_SUM,SERIAL_LENGTH,REMARK) values "
				+ "(#SERIAL_INDEX#,#SERIAL_LIMIT#,#CACHE_SUM#,#SERIAL_LENGTH#,#REMARK#)";
		LOG.debug("add SERIAL_CTRL" + serialCtrl);
		template.update(sql, serialCtrl);
	}

	/**
	 * 根据应用编号得到流水号上限
	 * 
	 * @param appNo
	 * @return
	 */
	public long getSerialLimit(String index, String table) {
		SerialBean temp = getSerialBeanByIndex(index, table);
		if (null == temp) {
			LOG.error("NO THIS index" + index);
			return -1;
		}
		return Long.parseLong(temp.getSerialLimit());
	}

	/**
	 * 根据应用编号更新当前流水号上限
	 * 
	 * @param appNo
	 * @param limit
	 */
	public void updateLimitByApp(String index, String table, String limit) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update " + table + " set SERIAL_LIMIT=#SERIAL_LIMIT# where SERIAL_INDEX=#SERIAL_INDEX#";
		SerialBean sb = new SerialBean();
		sb.setSerialIndex(index);
		// IcSerialCtrl temp = getIcSerialCtrlByApp(appNo);
		// if (null == temp) {
		// LOG.error("NO THIS " + appNo);
		// return;
		// }
		// long currentLimit = Long.parseLong(temp.getSerialLimit());
		// long cache_sum = Long.parseLong(temp.getCacheSum());
		// String serialLimit = (currentLimit + cache_sum) + "";
		sb.setSerialLimit(limit);
		template.update(sql, sb);
	}
	
	/*
	 * 根据序列号名称取值
	 */
	public String getEosUnique(String table,String index){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select code as serial_limit from " + table + " where name=#SERIAL_INDEX# for update";
		SerialBean sb = new SerialBean();
		sb.setSerialIndex(index);
		SerialBean result = template.queryRow(sql, sb, SerialBean.class);
		return result.getSerialLimit();
	}
	
	/*
	 * 根据序列号更新值
	 */
	public void updateEosUnique(String index, String table, String limit){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);		
		String sql = "update " + table + " set code=#SERIAL_LIMIT# where name=#SERIAL_INDEX#";
		SerialBean sb = new SerialBean();
		sb.setSerialIndex(index);
		sb.setSerialLimit(limit);
		template.update(sql, sb);
	}
}