package com.icsys.batch.business.acctsys;

import com.icsys.batch.util.SystemConstans;
import com.icsys.batch.util.SystemStatus;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class AcctSysUtil {
	
	
//	private CachableRepos acctsysParamCache = null;
	
	private static AcctSysUtil instance;

	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	public static NamedUpdateTemplate getNamedUpdateTemplate() {
		return TemplateManager.getNamedUpdateTemplate(DS_NAME);
	}

	public static NamedQueryTemplate getNamedQueryTemplate() {
		return TemplateManager.getNamedQueryTemplate(DS_NAME);
	}

	private static AcctSysParameter accSysParam;

	/**
	 * 构造子
	 */
	private AcctSysUtil() {
//		acctsysParamCache = CachableRepos.getInstanceByName("accSysParam");
	}

	public static AcctSysUtil getInstance() {
		if (instance == null) {
			instance = new AcctSysUtil();
		}
		return instance;
	}

	/**
	 * 从缓存中取
	 * @return
	 */
	public AcctSysParameter getAccSysParam() {
//		if(accSysParam==null)
//		{
//			accSysParam=(AcctSysParameter)acctsysParamCache.getByKey(null);
//		}
		//每次从数据库中取保持一致性
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql = "SELECT sys_accdate as ACCTING_DATE,SYS_RODATE AS ROLL_OFF_DATE,SYS_STATUS AS SYSTEM_STATUS,SYS_CURLIST AS CURRENT_ACC_LIST,SYS_ACCTNO AS LOSE_ACCT_NO,SYS_STEPSTATE AS STEP_STATE,SYS_STEPACTION AS STEP_ACTION,SYS_STEP AS STEP FROM GR_ACCT_SYS ";
		accSysParam = template.queryRow(sql, null,
				AcctSysParameter.class);
		
		return accSysParam;
	}

	public static void setAccSysParam(AcctSysParameter accSysParam) {
		AcctSysUtil.accSysParam = accSysParam;
	}

	/**
	 * 切换当前分录流水表
	 * 
	 * @return
	 */
	public void switchCurrAccList() {
		if (null != accSysParam) {
			Integer newCurrAccList = 0;
			if (0 == accSysParam.getCurrentAccList()) {
				newCurrAccList = 1;
			}
			//TODO 如果切换数据库发生异常，缓存是否应该还原为切换之前的值?类似的情况在切换系统日期也存在
			accSysParam.setCurrentAccList(newCurrAccList);
			String sql = "UPDATE GR_ACCT_SYS SET sys_curlist=#CURRENT_ACC_LIST#";
			updateParameter(accSysParam,sql);
//			NamedUpdateTemplate template = getNamedUpdateTemplate();
//			String sql = "UPDATE IC_ACCT_SYS SET CURRENT_ACC_LIST=#CURRENT_ACC_LIST#";
//			template.update(sql, accSysParam);
			
		}
	}

	/**
	 * 获取分录流水表名
	 * 
	 * @param isCurrent
	 *            true当前 false非当前
	 * @param accSysParam账务系统参数
	 * @return
	 */
	public String getAccListTable(boolean isCurrent) {
		if (null == accSysParam)
			return null;
		Integer currentAcctingEntry = accSysParam.getCurrentAccList();
		String table = "GR_ACC_LIST";
		if (isCurrent) {
			if (currentAcctingEntry == 0) {
				table += "A";
			} else {
				table += "B";
			}
		} else {
			if (currentAcctingEntry == 0) {
				table += "B";
			} else {
				table += "A";
			}
		}
		return table;
	}

	/**
	 * 根据余额方向判断是否借方
	 * 
	 * @param balanceCD
	 * @return
	 */
	public boolean isDebit(String balanceCD) {
		if (null != balanceCD && balanceCD.equals("1")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 修改日期
	 * 
	 * @param date
	 * @param bAcctingDate日期标志
	 *            ，true为记账日期，false为轧账日期
	 */
	public void updateSysDate(String date, Boolean bAcctingDate) {
		String tmp = null;
		if (bAcctingDate) {
			tmp = "sys_accdate=#ACCTING_DATE#";
			accSysParam.setAcctingDate(date);

		} else {
			tmp = "sys_rodate=#ROLL_OFF_DATE#";
			accSysParam.setRollOffDate(date);
		}
		String sql = "UPDATE GR_ACCT_SYS SET  "+tmp;
		updateParameter(accSysParam,sql);
	}

	/**
	 * 修改系统状态
	 * 
	 * @param ss
	 */
	public void updateSystemStatus(SystemStatus ss) {
		updateSysInfo("SYS_STATUS", ss.getValue());
	}

	/**
	 * 修改步骤状态
	 * 
	 * @param bSuccess
	 */
	public void updateStepState(boolean bSuccess) {
		updateSysInfo("SYS_STEPSTATE", bSuccess ? 0 : 1);
	}

	/**
	 * 修改步骤
	 * 
	 * @param s
	 */
	public void updateStep(Step s) {
		updateSysInfo("SYS_STEP", s.ordinal());
	}

	/**
	 * 修改相关参数（系统状态、步骤状态、步骤）
	 * 
	 * @param status
	 */
	private void updateSysInfo(String column, Integer value) {
		StringBuilder sb = new StringBuilder("UPDATE GR_ACCT_SYS SET ");
		sb.append(column).append("=").append(value);
		// 若设置步骤，则缺省步骤状态为失败
		if ("SYS_STEP".equalsIgnoreCase(column)) {
			sb.append(",SYS_STEPSTATE=1");
		}
//		template.update(sb.toString(), null);
		updateParameter(accSysParam,sb.toString());
	}
	
	/**
     * 更新系统参数
     * @param parameter
     */
    private void updateParameter(AcctSysParameter parameter,String sql) {
    	
//    	try {
//    		acctsysParamCache.update(null, parameter);
//		} catch (RuntimeException r) {
//			r.printStackTrace();
//			throw new SystemException(r);
//		}
    	NamedUpdateTemplate template = getNamedUpdateTemplate();
    	template.update(sql, parameter);
    }
	
}
