package com.icsys.batch.daySwitch.impl;

import java.util.List;

import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;
import com.icsys.platform.util.SystemProperties;

public class ParamManagerImpl implements ParamManager {
	
	NamedQueryTemplate queryTemp = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
	
	NamedUpdateTemplate updateTemp = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);

	public void addParameter(IcSysPara param) throws Exception {
		// TODO Auto-generated method stub

	}

	public IcSysPara getParameter(String index) throws Exception {
		// TODO Auto-generated method stub
		IcSysPara tmp = new IcSysPara();
		tmp.setParameterIndex(index);
		String sql = "SELECT  prm_index as parameter_index,prm_name as parameter_name,prm_value as parameter_value," +
				"prm_desc as parameter_description,prm_cont as parameter_control FROM GR_SYS_PARA where prm_index=#parameter_index# ";
		return queryTemp.queryRow(sql, tmp,IcSysPara.class);
	}

	public IcSysPara getParameterByLock(String index) throws Exception {
		// TODO Auto-generated method stub
		IcSysPara para = new IcSysPara();
		para.setParameterIndex(index);
		String dbName = SystemProperties.get("database", "DB2");
	    String endSql = "";
	    if (("DB2".equals(dbName)) || ("db2".equals(dbName)) || ("Db2".equals(dbName))) {
	      endSql = " with RS";
	    }
		String sql = "SELECT prm_index as parameter_index,prm_name as parameter_name,prm_value as parameter_value," +
				"prm_desc as parameter_description,prm_cont as parameter_control FROM GR_SYS_PARA " + 
				"WHERE PRM_INDEX=#parameter_index# FOR UPDATE" + endSql;

		IcSysPara result = queryTemp.queryRow(sql, para, IcSysPara.class);
		return result;
	}

	public List<IcSysPara> searchAllParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.icsys.task.proxy.ParamManager#setParameterValue(java.lang.String, java.lang.String)
	 */
	public void setParameterValue(String index, String value) throws Exception {
		// TODO Auto-generated method stub
		IcSysPara tmp = new IcSysPara();
		tmp.setParameterIndex(index);
		tmp.setParameterValue(value);
		String sql = "update gr_sys_para set prm_value=#parameter_value# where prm_index=#parameter_index# ";
		updateTemp.update(sql, tmp);
	}

}
