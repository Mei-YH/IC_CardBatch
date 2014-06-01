package com.icsys.batch.task.sleepAcctManageData.proxy.dao;


import com.icsys.batch.task.sleepAcctManageData.proxy.bean.SleepAcctManageBean;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class SleepAcctManageDao {
	public static final String DS_NAME = "IC_DATASOURCE";
//	private static final Logger LOG = Logger.getLogger(SleepAcctFormDao.class);
   /**
    * @param sleepAcctFormBean
    */
	public void insertData(SleepAcctManageBean sleepAcctFormBean) {
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO GR_SLEEP_ACCT_DETAIL(BATCH_NO,BATCH_SN,BATCH_DT,IC_NO,IC_CARD_STAT,IC_SLEEP_TIME," +
				"IC_ACCT_BALANCE,IC_REMOVE_BALANCE,CORE_REMOVE_STAT,IC_DATE,CORE_DATE,IC_PRO,IC_BRANCH) " +
				"VALUES(#batchNo#,#batchSn#,#batchDt#,#icNo#,#icCardStat#,#icSleepTime#,#icAcctBalance#,#icRemoveBalance#," +
				"#coreRemoveStat#,#icDate#,#coreDate#,#icPro#,#icBranch#)";
		template.update(sql, sleepAcctFormBean);
	} 	
}
