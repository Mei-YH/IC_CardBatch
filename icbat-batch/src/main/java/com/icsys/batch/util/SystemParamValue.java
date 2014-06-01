package com.icsys.batch.util;

import com.icsys.batch.daySwitch.impl.ParamManagerImpl;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.util.SysParamConstant;

public class SystemParamValue {
	
	private static ParamManagerImpl param = new ParamManagerImpl();
	
	public static String getIcSysPara(String index) throws Exception{
		return param.getParameter(index).getParameterValue();
	}
	
	public static String getIcSysParaByLock(String index) throws Exception{
		return param.getParameterByLock(index).getParameterValue();
	}
	
	public static String getSystemDate() throws Exception{
		return getIcSysPara(SysParamConstant.SYSTEM_DATE);
	}
	
	public static String getCurrSerialTableFlag() throws Exception{
		return getIcSysPara(SysParamConstant.CURR_SERIAL_TABLE);
	}
	
	public static String getClearDate() throws Exception{
		return getIcSysPara(SysParamConstant.CLEARING_DATE);
	}
	
	public static String getBatchNo() throws Exception{
		TXHelper.beginTX();
		try{
			String batchStr = getIcSysParaByLock(SysParamConstant.BATCH_NO_INDEX);		
			setIcSysPara(SysParamConstant.BATCH_NO_INDEX,(Integer.parseInt(batchStr) + 1) + "");//批次号
			TXHelper.commit();
			return batchStr;
		}catch(Exception e){
			TXHelper.rollback();
			throw e;
		}finally{
			TXHelper.close();
		}
	}
	
	public static String getOffClearCycle() throws Exception{
		return getIcSysPara(SysParamConstant.OFF_CLEARING_CYCLE);
	}
	
	//取不是当前表的交易流水表名
	public static String getNotCurrentSerialTableName() throws Exception{
		String serialTable = SystemParamValue.getCurrSerialTableFlag();
		if(serialTable.equals("1"))
			return "GR_BUS_TXLISTA";
		else
			return "GR_BUS_TXLISTB";
	}

	//取不是当前表的交易流水表名
	public static String getCurrentSerialTableName() throws Exception{
		String serialTable = SystemParamValue.getCurrSerialTableFlag();
		if(serialTable.equals("0"))
			return "GR_BUS_TXLISTA";
		else
			return "GR_BUS_TXLISTB";
	}
	
	public static void setIcSysPara(String index,String value)throws Exception{
		param.setParameterValue(index, value);
	}

}
