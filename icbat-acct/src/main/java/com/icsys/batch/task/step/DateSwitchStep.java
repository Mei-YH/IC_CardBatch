package com.icsys.batch.task.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.daySwitch.impl.ParamManager;
import com.icsys.batch.task.proxy.IcDayEndImpl;
import com.icsys.batch.util.SystemStatus;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.util.ServiceLocator;

/**
 * 日切
 * @author xinyh
 *
 */
public class DateSwitchStep implements Step {

//	private static String PLATFORM_SERIAL_INDEX = "ICDEALLOGSEQ";
	
//	private static String PLATFORM_SERIAL_VALUE = "1";
	
	private static String BATCH_SERIAL_INDEX = "200002";
	
//	private static String PLATFORM_SERIAL_TABLE = "EOS_UNIQUE_TABLE";
	
	private static String BATCH_SERIAL_INI_VALUE = "1";
	
	private static Logger LOG = Logger.getLogger(DateSwitchStep.class);
	public long estimate(JobContext context) throws JobException {
		return 0;
	}
	public void perform(JobContext context, Executor executor) throws JobException {
		try {
			LOG.info("日切步骤开始...");
			
			checkSystemState();
			
			TXHelper.beginTX();
			try{
				IcDayEndImpl  icDayEnd = new IcDayEndImpl();
				
				//平台日切
				icDayEnd.icDateSwitch();
				
				//TODO 项目中特定要重置的值
				//重置任务批次号
				resetSerial(BATCH_SERIAL_INDEX,BATCH_SERIAL_INI_VALUE);
				//重置BTP平台流水号
//				resetSerial(PLATFORM_SERIAL_INDEX,PLATFORM_SERIAL_TABLE,PLATFORM_SERIAL_VALUE);

				/*山东农信不需要轧账处理，系统状态需要更改为正常*/
				AcctSysUtil.getInstance().updateSystemStatus(SystemStatus.NORMAL);
				TXHelper.commit();				
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
				TXHelper.rollback();
				throw new JobException(e.getMessage(),e);
			}finally{
				TXHelper.close();
			}
//			try{
//		        resetSerialGen();
//		    } catch (Exception e) {
//		    	LOG.error(e.getMessage(), e);
//		    	throw new Exception("重置流水号异常!");
//		    }
			LOG.info("日切步骤结束.");
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new JobException(e.getMessage(),e);
		}
		
	}
	
//	private void resetSerialGen() throws Exception
//	{
//		SerialManager.notifyReload();
//	}
	
	/**
	 * 校验系统状态是否正常
	 * @throws JobException 
	 */
	private void checkSystemState() throws JobException{
		int systemState=AcctSysUtil.getInstance().getAccSysParam().getSystemStatus();
		if(SystemStatus.NORMAL.getValue()!=systemState){
			throw new JobException("请检查系统状态是否正常");
		}
		String acctDate=AcctSysUtil.getInstance().getAccSysParam().getAcctingDate();
		String rollOffDate=AcctSysUtil.getInstance().getAccSysParam().getRollOffDate();
		if(!acctDate.equals(rollOffDate)) {
			throw new JobException("请检查账务日期与轧帐日期是否相同");
		}
	}


	private void resetSerial(String index,String value) throws Exception{
		//TODO 是不是应该先加锁？？
		ParamManager paraManager = ServiceLocator.getSerivce(ParamManager.class);
		paraManager.setParameterValue(index, value);
	}
	
	/*
	 * 重置BTP平台流水号
	 */
//	private void resetSerial(String index,String table,String value) throws Exception{
//		SerialBeanDao dao = new SerialBeanDao();
//		dao.updateEosUnique(index, table, value);
//	}
}
