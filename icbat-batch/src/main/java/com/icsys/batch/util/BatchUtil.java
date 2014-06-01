package com.icsys.batch.util;

import com.icsys.batch.daySwitch.impl.ParamManager;
import com.icsys.platform.util.ServiceLocator;

public class BatchUtil {
	

	/**
	 * 获取批处理日期
	 * @return
	 * @throws Exception
	 */
	public static String getTranDate() throws Exception{
		ParamManager paramManager = ServiceLocator.getSerivce(ParamManager.class);
		return paramManager.getParameter(BatchConstants.PALTFORM_TRAN_DATE_INDEX).getParameterValue();
	}
	
	public static String getBankCode() throws Exception{
		ParamManager paramManager = ServiceLocator.getSerivce(ParamManager.class);
		return paramManager.getParameter(BatchConstants.BANK_CODE_INDEX).getParameterValue();
	}
	/**
	 * 获取核心系统日期
	 * @return
	 * @throws Exception
	 */
	public static String getHostDate() throws Exception{
		ParamManager paramManager = ServiceLocator.getSerivce(ParamManager.class);
		return paramManager.getParameter(BatchConstants.HOST_DATE_INDEX).getParameterValue();
	}

}
