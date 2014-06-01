package com.icsys.batch.task.offline.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.offline.bean.SuspenseAcctRegBean;
import com.icsys.batch.offline.dao.SuspenseAcctRegDao;
import com.icsys.batch.serial.PlatformSerialGennerator;
import com.icsys.batch.util.Utils;
import com.icsys.platform.dao.TXHelper;

/**
 * 挂账处理类
 * @author liuyb
 *
 */
public class SuspenseAcctImpl {
	private static final Logger LOG = Logger.getLogger(SuspenseAcctImpl.class);

	/**
	 * 挂账处理
	 * @param detail
	 * @param debitAcctNo
	 * @param creditAcctNo
	 * @param msg
	 * @throws Exception 
	 */
	public static void chargeAccount(ClearDetailBean detail,String debitAcctNo,String creditAcctNo,String msg,String sysDate) throws Exception{
		LOG.info("进入挂账处理...当前状态：" + detail.getStatus());
		detail.setStatus(Utils.union(detail.getStatus(), "0003"));
		detail.setLoseFlag("0");//0－未挂账，1－挂账
		
		String serial = detail.getPlatformSerial();
		TXHelper.beginTX();
		try{
			if(null == serial || "".equals(serial)){
				serial = PlatformSerialGennerator.getPlatformSerial(sysDate);
			}
			SuspenseAcctRegBean bean = new SuspenseAcctRegBean();
			bean.setSuaTrdate(sysDate);
			bean.setSuaPlserial(serial);
			bean.setSuaSerial(serial);
			bean.setSuaTrbranch(detail.getReceiveBranch().trim());
			bean.setIcCardno(detail.getAcctNo().trim());
			bean.setSuaSamount(new BigDecimal(detail.getTranAmount().trim()));
			bean.setSuaCtype(detail.getTag5f2a().trim());
			bean.setTransferFormAcctNo(debitAcctNo);
			bean.setSuaTstacctno(creditAcctNo);
			bean.setSuaTrtime(detail.getKey7().trim());
			bean.setSuaSystrno(detail.getKey11().trim());
			bean.setSuaPpid(detail.getKey32().trim());
			bean.setSuaSbid(detail.getKey33().trim());
			bean.setSuaDeflag("0");
			bean.setSuaRemark(msg);
			SuspenseAcctRegDao dao = new SuspenseAcctRegDao();
			dao.insertSusAcctReg(bean);
			TXHelper.commit();
			detail.setLoseFlag("1");
		}catch(Exception ex){
			TXHelper.rollback();
			LOG.error("挂账处理失败！" + ex.getMessage());
			throw ex;
		}finally{
			TXHelper.close();
		}
		LOG.info("挂账处理成功！");
	}
	
	public static String[] getCDAcctNo(ClearDetailBean detail,String clearDate,String isAccount) throws Exception {
		String[] cdAccounts = new String[] {};
		cdAccounts = new TranServiceImpl(clearDate).getOffLineCDAccount(detail,isAccount);// 获得借贷账户
		if (null == cdAccounts) {
			throw new Exception("获取借贷账户失败");
		}
		return cdAccounts;
	}
}
