package com.icsys.batch.task.checkaccount.proxy;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.checkAccount.IcCheckAccountErrList;
import com.icsys.batch.checkAccount.IcCheckAccountErrListDao;
import com.icsys.batch.checkAccount.IcCheckAccountTxList;
import com.icsys.batch.task.checkaccount.proxy.bean.GrCheckAcctDet;
import com.icsys.batch.task.checkaccount.proxy.dao.CheckAccountDao;
import com.icsys.platform.dao.TXHelper;

public class CheckAccountProxy{
	
	private static Logger LOG = Logger.getLogger(CheckAccountProxy.class);

	private CheckAccountDao dao=new CheckAccountDao();

	public void dowork(GrCheckAcctDet detail,boolean isCups) throws Exception{
		TXHelper.beginTX();
		try{
			LOG.debug("明细处理"+detail.getDet2());
			parseLine(detail,isCups);
			TXHelper.commit();
		}catch(Exception e){
			TXHelper.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			TXHelper.close();
		}
	}	
	/**
	 *  IC平台日期|IC平台流水号|核心日期|核心流水号|充值金额|手续费|交易类型
	 *  处理单笔流水记录
	 * @param line
	 * @param workDate
	 * @throws Exception
	 */
	private void parseLine(GrCheckAcctDet detail,boolean isCups) throws Exception {		
		String oriTranDate = isCups?detail.getDet5().trim():detail.getDet3().trim();//IC平台日期或银联日期
		String oriSerial = detail.getDet4().trim();//IC平台流水号
		String coreTranDate = detail.getDet1(); //核心日期
		String coreSerial = detail.getDet2();   //核心流水
		BigDecimal amount = new BigDecimal(detail.getDet9()).movePointLeft(2);//充值金额
		BigDecimal fee = new BigDecimal(detail.getDet13()).movePointLeft(2);//手续费
		String flag = detail.getDet72();
		String checkDate = detail.getCheckDate();
		if("1".equals(flag)){
			/**
			 * flag 0－成功，1－冲正，2－撤消
			 * 撤消按成功状态进行对账，因撤消会有两条对账流水，原流水状态为撤消，新流水为成功或失败
			 * 冲正不进行对账
			 */
			return;
		}
		
		//TODO 判断原交易日期 是否正确(正常情况下原IC卡交易日期只应该为：T-1或T-2)
		if(!oriTranDate.equals(checkDate)){
			LOG.error("明细["+oriSerial+"]日期异常");
			IcCheckAccountErrList errRecord = serialLineToErrRecord(oriTranDate, oriSerial, coreTranDate, coreSerial, amount);
			errRecord.setCheRemark("原IC卡平台交易日期为:["+oriTranDate+"]异常,对账日期为:["+checkDate+"]!");
			registerErrRecord(errRecord);
			return;
		}
		
		//TODO 这次调整，为什么还是没有加根据平台日期和流水来获取交易明细？？
		List<IcCheckAccountTxList> records = dao.getRecordByPlatformSerial(oriSerial);
		//在IC卡平台未找到对应流水
		if (null == records || records.size() < 1 ) {
			LOG.error("IC卡平台未找到对应的流水["+oriSerial+"]");
			IcCheckAccountErrList errRecord = serialLineToErrRecord(oriTranDate, oriSerial, coreTranDate, coreSerial, amount);
			errRecord.setCheReclogo("01");
			errRecord.setCheRemark("在IC卡系统中，未找到平台流水为:["+oriSerial+"],对账日期为:["+oriTranDate+"]的记录!");
			registerErrRecord(errRecord);
		}else if(records.size() > 1){
			throw new Exception("根据平台流水为:["+oriSerial+"],对账日期为:["+oriTranDate+"]获取到多条明细异常!");
		}else {
			IcCheckAccountTxList record = records.get(0);
			String status = record.getTranStatus();
			LOG.debug("解析成功明细[" + oriSerial + "]状态[" + status + "]");
			if(!"00".equals(record.getReconciliationLogo())){
				LOG.debug("此明细已经对账");
				return;
			}
			//对账状态
			String checkFlag = "00";
			//金额状态
			boolean amountEqualsFlag = true;
			BigDecimal oriTranAmount = record.getTranAmount();
			BigDecimal oriFee = record.getFee();
			/*不管什么情况均更新金额手续费为核心COM档数据*/
			record.setTranAmount(amount);
			record.setFee(fee);
			
			/*针对ATM及银联渠道的现金圈存交易金额进行特别处理*/
			boolean isCash = ("06".equals(detail.getDet51().trim())||isCups)&&"26".equals(record.getTranFanction().trim());
			if(isCash){
				amount = amount.subtract(fee);
			}
			
			if(oriTranAmount.compareTo(amount) != 0){
				//判断充值金额
				amountEqualsFlag = false;
			}
			if(oriFee.compareTo(fee) != 0){
				//判断手续费
				amountEqualsFlag = false;
			}
			if ("0".equals(status) || "4".equals(status) || "5".equals(status)) {
				if(amountEqualsFlag)
					checkFlag = "11";
				else
					checkFlag = "22"; //金额不符
			}  else if ("1".equals(status) || "2".equals(status) || "3".equals(status)) {
				/*IC卡无账务情况下不比较金额*/
//				if(amountEqualsFlag)
					checkFlag = "01";
					record.setHostSerial(coreSerial.trim());
					record.setHostDate(coreTranDate.trim());
//				else 
//					checkFlag = "21";
			} else {
				// TODO
				LOG.error("流水号:["+record.getPlatformSerial()+"],交易状态为:["+status+"]异常!");
//				throw new Exception("流水号:["+record.getPlatformSerial()+"],交易状态为:["+status+"]异常!");
			}
			record.setReconciliationLogo(checkFlag);
			//TODO 底层流水操作类添加根据平台日期，平台流水更新流水
			LOG.debug("流水[" + oriSerial + "]的对账状态为[" + checkFlag + "]");
			if(dao.updateRecFlag(record) != 1){
				throw new Exception("更新流水号为：["+record.getPlatformSerial()+"],的交易状态为:["+status+"]时，数据库异常");
			}
		}

	}
	
	private IcCheckAccountErrList serialLineToErrRecord(String tranDate,String serial,String hostDate,String hostSerial,BigDecimal tranAmount) {
		IcCheckAccountErrList errRecord = new IcCheckAccountErrList();
		errRecord.setCheTrdate(tranDate);
		errRecord.setChePlserial(serial);
		errRecord.setCheSerial(serial);
		errRecord.setCheHserial(hostSerial);
		errRecord.setCheHdate(hostDate);
		errRecord.setCheTramount(tranAmount);
		return errRecord;
	}

	private void registerErrRecord(IcCheckAccountErrList errRecord){
		new IcCheckAccountErrListDao().addRecord(errRecord);
	}
	
	
}
