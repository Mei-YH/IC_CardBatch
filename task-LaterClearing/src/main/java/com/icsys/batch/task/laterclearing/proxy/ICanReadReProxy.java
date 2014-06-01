package com.icsys.batch.task.laterclearing.proxy;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.account.CardAccounterImpl;
import com.icsys.batch.serial.PlatformSerialGennerator;
import com.icsys.batch.task.laterclearing.proxy.bean.IcanReadBean;
import com.icsys.batch.task.laterclearing.proxy.bean.KeepAccoutBean;
import com.icsys.batch.task.laterclearing.proxy.dao.LaterClearDao;
import com.icsys.batch.util.DateUtil;
import com.icsys.batch.util.SystemParamValue;

public class ICanReadReProxy {
	private static Logger LOG = Logger.getLogger(ICanReadReProxy.class);

	public void dowork(String sysdate)throws Exception {
		Integer cycle = Integer.parseInt(SystemParamValue.getOffClearCycle());
		String tranDate = DateUtil.getDateStri(DateUtil.getAfterNDay(DateUtil.getDateFromShortStr(sysdate), -cycle));
		LOG.info("处理日期[" + tranDate + "]");
		List<IcanReadBean> canReads = new LaterClearDao().queryIcanRead(tranDate);
		//AcctProxyImpl acctProxy = new AcctProxyImpl();
		if (canReads.size() == 0) {
			LOG.info("该批次没有要处理的数据!");
			return;
		}
		CardAccounterImpl cardAccount=new CardAccounterImpl();
		//CardAccounterImpl cardAccount = new CardAccounterImpl();// 通过卡的应用编号和卡号来得到该卡的补登账户
		TransferAccount tran = new TransferAccount();

		for (IcanReadBean canRead : canReads) {
			if(LOG.isDebugEnabled()){
				LOG.debug("异常卡卡号:"+canRead.getIcNo());
				LOG.debug("机构号:" + canRead.getBranch());
				LOG.debug("新卡补登账户:" + canRead.getNewOn());
			}
			BigDecimal old_on_bal = cardAccount.getAcctBalance(canRead.getOldOn());
			BigDecimal old_off_bal = cardAccount.getAcctBalance(canRead.getOldOff());
			
			if (old_on_bal.compareTo(BigDecimal.ZERO) > 0 && null != canRead.getNewOn()) {
				KeepAccoutBean keepAccoutBean = new KeepAccoutBean();
				 keepAccoutBean.setAmount(old_on_bal);
				keepAccoutBean.setBranchNo(canRead.getBranch());// 机构号
				keepAccoutBean.setCardNo(canRead.getIcNo());
				keepAccoutBean.setCardIndex(canRead.getIcIndex());
				keepAccoutBean.setDebitAcctNO(canRead.getOldOn());// 借方账号,异常卡的补登账户
				keepAccoutBean.setCreditAcctNO(canRead.getNewOn());//贷方得到新卡的补登账户(
				keepAccoutBean.setOperator(canRead.getTellerNo());// 柜员
				keepAccoutBean.setReMark("转账");
				keepAccoutBean.setSerial(PlatformSerialGennerator.getPlatformSerial(sysdate));// 流水号
				tran.keepAccout(keepAccoutBean,canRead.getTxDt(),sysdate);
			}
			if (old_off_bal.compareTo(BigDecimal.ZERO) > 0) {
				KeepAccoutBean keepAccoutBean = new KeepAccoutBean();
				 keepAccoutBean.setAmount(old_off_bal);
				keepAccoutBean.setBranchNo(canRead.getBranch());// 机构号
				keepAccoutBean.setCardNo(canRead.getIcNo());
				keepAccoutBean.setCardIndex(canRead.getIcIndex());
				keepAccoutBean.setDebitAcctNO(canRead.getOldOff());// 借方账号,异常卡的电子现金账户
				keepAccoutBean.setCreditAcctNO(null==canRead.getNewOn()?canRead.getOldOn():canRead.getNewOn());//贷方得到新卡的补登账户
				keepAccoutBean.setOperator(canRead.getTellerNo());// 柜员
				keepAccoutBean.setReMark("转账");
				keepAccoutBean.setSerial(PlatformSerialGennerator.getPlatformSerial(sysdate));// 流水号
				tran.keepAccout(keepAccoutBean,canRead.getTxDt(),sysdate);
			}
		}
		LOG.info("周期后处理成功!");
	}
}
