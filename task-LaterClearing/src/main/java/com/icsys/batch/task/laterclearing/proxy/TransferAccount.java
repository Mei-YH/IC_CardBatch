package com.icsys.batch.task.laterclearing.proxy;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acctting.api.CDAcctingInput;
import com.icsys.batch.business.acctting.api.CDAcctingOutput;
import com.icsys.batch.business.acctting.impl.AccounterImpl;
import com.icsys.batch.checkAccount.IcTxList;
import com.icsys.batch.checkAccount.IcTxListDao;
import com.icsys.batch.task.laterclearing.proxy.bean.KeepAccoutBean;
import com.icsys.batch.task.laterclearing.proxy.dao.LaterClearDao;
import com.icsys.batch.util.SystemParamValue;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.util.DateUtils;

/**
 * @author 作者:lyq 转账类,标准的借和贷
 */
public class TransferAccount {
	/**
	 * 账务往来方法,标准的借和贷 借贷，在法律意义上，是指由贷方与借方成立一项“借贷契约”
	 * 贷方将金钱所有权移转给借方(借)，到期时由借方返还同额的钱(贷)。 这里的机构号和柜员取得是异常登记簿中的机构号和柜员
	 * 
	 * @throws Exception
	 */
	private static Logger LOG = Logger.getLogger(TransferAccount.class);

	// ProductRepository productApp=new ProductRepository();//得到平台和核心的机构往来账号
	// IcAccNoRegDao icAccNoRegDao=new IcAccNoRegDao();//查询内部核算专用账户登记簿
	// CardQueryService
	// cardQuery=ServiceLocator.getSerivce(CardQueryService.class);
	// IcCoreSummaryDao coreSummary=new IcCoreSummaryDao();//核心汇总文件表
	public void keepAccout(KeepAccoutBean keepAccout,String txDt,String sysdate)
			throws Exception {
		TXHelper.beginTX();
		try {
			AccounterImpl accounter = new AccounterImpl();
			CDAcctingInput input = new CDAcctingInput();
			// AppTranSerial
			// app=ServiceLocator.getSerivce(AppTranSerial.class);//记交易流水
			input.setAmount(keepAccout.getAmount());// 转账金额
			input.setTranBranch(keepAccout.getBranchNo());// 机构号
			input.setCreditAcctNO(keepAccout.getCreditAcctNO());// 贷方账号
			input.setDebitAcctNO(keepAccout.getDebitAcctNO());// 借方账号
			input.setOperator(keepAccout.getOperator());// 柜员
			input.setRemark(keepAccout.getReMark());// 备注
			input.setTranSerial(keepAccout.getSerial());// 流水号
			CDAcctingOutput out = accounter.accountDebitOverdrawing(input);
			LOG.info("转账成功!");
			IcTxList tx = createIcTxList(keepAccout,out.getSerial());// 流水信息
			new IcTxListDao().addIcTxList(tx, SystemParamValue
					.getCurrentSerialTableName());
			//更新异常表状态
			new LaterClearDao().updateAbnInfo(keepAccout.getCardNo(), keepAccout.getCardIndex(), "7",txDt,sysdate,keepAccout.getSerial(), keepAccout.getBranchNo());
			
			TXHelper.commit();
			LOG.info("流水记入成功!");
		} catch (Exception e) {
			try {
				TXHelper.rollback();
			} catch (Exception e2) {
				LOG.debug(e2.getMessage(), e2);
			}
			throw e;
		} finally {
			try {
				TXHelper.close();
			} catch (Exception e2) {
				LOG.debug(e2.getMessage(), e2);
			}
		}
	}

	/**
	 * 创建交易流水
	 * 
	 * @param account
	 * @return
	 * @throws SerialGenneratorException
	 * @throws SysParamException
	 */
	public IcTxList createIcTxList(KeepAccoutBean account,String serial) throws Exception {
		IcTxList txList = new IcTxList();
		txList.setTranDate(DateUtils.getCurrentDate());// 交易日期
		// IC卡平台核心流水号
		txList.setPlatformSerial(account.getSerial());
		txList.setSerial(serial);
		txList.setTranBranch(account.getBranchNo());// 处理机构号
		txList.setCardNo(account.getCardNo());
		txList.setCardIndex("00");
		txList.setTranFlag("125");// 交易标志
		txList.setReconciliationLogo("99");// 对账标识
		txList.setTranAmount(account.getAmount());// 交易金额
		txList.setFee(new BigDecimal("0"));// 手续费
		txList.setCurrType("156");// 币种
		txList.setAcctNo(account.getDebitAcctNO());// 交易账号
		txList.setCardNo1("");// 对方卡号
		txList.setAcctCd("1");// 主账户借贷标志
		txList.setAcctNo1(account.getCreditAcctNO());// 对方账户账号
		txList.setAcctAmount1(account.getAmount());// 对方账户记账金额
		txList.setTranStatus("0");// 交易状态
		return txList;
	}
}
