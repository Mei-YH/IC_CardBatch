package com.icsys.batch.offline.dao;

import com.icsys.batch.offline.bean.TxCounterBean;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class IcOfflineRepRecordDao {
	
	private static final String SQL_COUNTER_INSERT = "insert into gr_bus_offlatc (ola_cldate,card_no,card_index,tag9f36)"
			+ " values(#clearDate#, #iccardno#,#cardIndex#,#Tag9F36#) ";

	public String ds_name=SystemConstans.DB_SOURCE_NAME;

	public void addTxCounter(TxCounterBean txCounter) throws Exception{
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(ds_name);
		TXHelper.beginTX();
		try{
			template.update(SQL_COUNTER_INSERT, txCounter);
			TXHelper.commit();
		}catch (Exception e){
			TXHelper.rollback();
			throw e;
		}finally{
			TXHelper.close();
		}
	}
}
