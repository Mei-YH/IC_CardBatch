package com.icsys.batch.encryptor;

import java.util.List;

import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

/**
 * 
 * @author liuyb
 *
 */
public class TbTsmDao {
	
	public List<TbTsm> getAllTbTsm(){
		NamedQueryTemplate queryTemp = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "select * from tb_tsm";
		return queryTemp.query(sql, null, TbTsm.class);
	}
}
