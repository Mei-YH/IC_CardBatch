package com.icsys.batch.task.profitdata.proxy.dao;


import com.icsys.batch.task.profitdata.proxy.bean.ProfitDataBean;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class ProfitDataDao {
	public static final String DS_NAME = "IC_DATASOURCE";
//	private static final Logger LOG = Logger.getLogger(ProfitFormsDao.class);
   /**
    * @param profitFormsBean
    */
	public void insertData(ProfitDataBean profitFormsBean) {
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO GR_ICGQZSY_DET(BATCH_NO,BATCH_SN,BATCH_DT,IC_NO,IC_CARD_STAT,IC_DESTROY_TIME," +
				"IC_ACCT_BALANCE,IC_PROFIT_BALANCE,CORE_REMOVE_STAT,IC_DATE,CORE_DATE,IC_PRO,IC_BRANCH) VALUES(#batchNo#," +
				"#batchSn#,#batchDt#,#icNo#,#icCardStat#,#icDestroyTime#,#icAcctBalance#,#icProfitBalance#," +
				"#coreRemoveStat#,#icDate#,#coreDate#,#icPro#,#icBranch#)";
		template.update(sql, profitFormsBean);
	} 	
}