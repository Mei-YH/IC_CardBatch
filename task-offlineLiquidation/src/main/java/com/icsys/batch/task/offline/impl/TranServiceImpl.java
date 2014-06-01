package com.icsys.batch.task.offline.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.icsys.batch.account.CardAccounterImpl;
import com.icsys.batch.account.IcInfoReg;
import com.icsys.batch.account.IcInfoRegDao;
import com.icsys.batch.business.acctting.api.Accounter;
import com.icsys.batch.business.acctting.api.CDAcctingInput;
import com.icsys.batch.business.acctting.impl.AccounterImpl;
import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.serial.PlatformSerialGennerator;
import com.icsys.batch.util.Constants;
import com.icsys.batch.util.ICSTATUS;
import com.icsys.batch.util.Utils;
import com.icsys.platform.dao.TXHelper;

public class TranServiceImpl {
	private static final Logger LOG = Logger.getLogger(TranServiceImpl.class);
	CardAccounterImpl cardAccounter = new CardAccounterImpl();
//	Map<String, String> proOrgCoreMap = new HashMap<String, String>();// key:productNo
	ClearDetailServiceImpl clearService = new ClearDetailServiceImpl();
//	OnlineDetailServiceImpl onLineService = new OnlineDetailServiceImpl();
//	CardUtil util = new CardUtil();
	ClearDetailBean orginalDetail = null;
	private String sysdate;
	public TranServiceImpl(String sysdate){
		this.sysdate = sysdate;
	}

	/**
	 * 脱机消费获得借贷账号
	 * 
	 * @return
	 */
	public String[] getOffLineCDAccount(ClearDetailBean detail,String isAccount) {
		if(LOG.isInfoEnabled()){
			LOG.info("进入卡状态判断...");
		}
		String temp = getCardStatus(detail,isAccount);// 首先根据卡状态更新相应的信息
		if (null == temp) {
			return null;
		}
		String cardNo = detail.getAcctNo().trim();// 卡号
		String cardIndex = detail.getKey23();// 卡序列号
		String branch = detail.getAcctingBranch();// 卡的账务机构
		String debitAccount = "";// 借方账号
		String creditAccount = "";// 贷方账号
		String status = detail.getStatus();// 查询账户结果状态
		String tradeCode = detail.getTranCode();// 交易码是脱机消费还是脱机退货
//		BigDecimal sumReturnAmount = BigDecimal.ZERO;// 累计脱机退货金额
		if (Constants.OFFLINE_CONSUMPTION.equals(tradeCode)) {
			// 脱机消费(包括银联和本行)
			if (status.endsWith("8")) {//状态是正常、挂失
				// 借方账户取电子现金户，贷方账户取机构往来户
				try {
					detail.setStatus(detail.getStatus().replace("8", "0"));
					debitAccount = cardAccounter.getOfflineAcct(cardNo,
							cardIndex, Constants.ECUSH_APPID,
							Constants.CURR_TYPE);
					creditAccount = cardAccounter.getPlatformAcct(cardNo,
							cardIndex, Constants.ECUSH_APPID, branch,
							Constants.CURR_TYPE);
				} catch (Exception e) {
					detail.setStatus(Utils.union(detail.getStatus(), "0004"));
					clearService.updateDetail(detail);
					LOG.error("卡[" + cardNo + "]应用绑定关系异常,记入差错表.");
					return null;
				}

			}/* else if (status.endsWith("9")) {
				// 借方账户取待清算户，贷方账户取机构往来户
				try {
					detail.setStatus(detail.getStatus().replace("9", "0"));
					debitAccount = cardAccounter.getClearAcct(cardNo,
							cardIndex, Constants.ECUSH_APPID,
							Constants.CURR_TYPE);
					creditAccount = cardAccounter.getPlatformAcct(cardNo,
							cardIndex, Constants.ECUSH_APPID, branch,
							Constants.CURR_TYPE);
				} catch (Exception e) {
					detail.setStatus(Utils.union(detail.getStatus(), "0004"));
					clearService.updateDetail(detail);
					LOG.error("卡[" + cardNo + "]应用绑定关系异常,记入差错表.");
					return null;
				}			
			}*/
		} else if (Constants.OFFLINE_GOODSREJECT.equals(tradeCode)) {
			//脱机退货直接退到补登户判断原交易,并且只校验清算周期,清算周期校验不通过入差错表
			/*try {
				orginalDetail = clearService.getOrginalInfo(detail);
			} catch (Exception e) {
				detail.setStatus(Utils.union(detail.getStatus(), "0002"));
				clearService.updateDetail(detail);
				LOG.error("获取卡[" + cardNo + "]的原始交易信息出错." + e.getMessage());
				return null;
			}
			if (null == orginalDetail ) {
				// 没有找到原始交易或者原始交易出了差错,则把本交易明细也更新为错误状态
				detail.setStatus(Utils.union(detail.getStatus(), "0002"));
				clearService.updateDetail(detail);
				LOG.error("没找到卡[" + cardNo + "]的原始交易.");
				return null;
			}else if(orginalDetail.getStatus().charAt(3) != '1'){
				detail.setStatus(Utils.union(detail.getStatus(), "0002"));
				clearService.updateDetail(detail);
				LOG.error("卡[" + cardNo + "]的原始交易未清算.");
				return null;
			} else if(null == orginalDetail.getAmount() || "".equals(orginalDetail.getAmount().trim())){
				detail.setStatus(Utils.union(detail.getStatus(), "0002"));
				clearService.updateDetail(detail);
				LOG.error("卡[" + cardNo + "]的原始交易金额不存在.");
				return null;
			}
			if (null == orginalDetail.getSumReturnAmount() || "".equals(orginalDetail.getSumReturnAmount().trim())) {
				sumReturnAmount = BigDecimal.ZERO;
			}else{
				sumReturnAmount = new BigDecimal(orginalDetail.getSumReturnAmount());
			}
			BigDecimal tranAmount = new BigDecimal(orginalDetail.getAmount().trim());
			if (tranAmount.subtract(sumReturnAmount).compareTo(new BigDecimal(detail.getTranAmount().trim()))<0) {
					// 如果退货的金额大于原交易剩余的消费金额则记入差错表
				detail.setStatus(Utils.union(detail.getStatus(), "0002"));
				clearService.updateDetail(detail);
				LOG.error("卡[" + cardNo + "]的退货金额大于原交易金额.");
				return null;
			}
			orginalDetail.setSumReturnAmount(sumReturnAmount.toString());
			*/
			/*
			 * 判断卡状态
			 */
			boolean newOnLine = false;
			String newCardNo = null;
			String newCardIndex = null;
			try{
				IcInfoRegDao dao = new IcInfoRegDao(); 
				IcInfoReg cardInfo = dao.getIcInfo(cardNo.trim(),cardIndex);
				String icstatus = cardInfo.getIcStatus().substring(0,1);
				if(!ICSTATUS.NATURE.getFlag().equals(icstatus)){
					Object[] tmp = dao.getAbnNewCard(cardNo,cardIndex);
					while(null != tmp[0] && !"".equals((String)tmp[0])){
						newOnLine = true;
						newCardNo = (String)tmp[0];
						newCardIndex = (String)tmp[1];
						tmp = dao.getAbnNewCard(newCardNo,newCardIndex);
						if(null == tmp[0] || "".equals(tmp[0])) break;
					}
				}
			}catch(Exception e){
				detail.setStatus(Utils.union(detail.getStatus(),"0004"));
				clearService.updateDetail(detail);
				LOG.error("卡[" + cardNo + "]应用绑定关系异常,记入差错表.");
				return null;
			}
			// 如果原始交易是正常的并且能找到
			// 纯电子现金卡 复合卡 都退到补登户里  借方:机构往来户 贷方:补登户
			try {
				debitAccount = cardAccounter.getPlatformAcct(cardNo, cardIndex, Constants.ECUSH_APPID,
							branch, Constants.CURR_TYPE);
				creditAccount = cardAccounter.getOnlineAcct(newOnLine?newCardNo:cardNo,newOnLine?newCardIndex:cardIndex, 
						Constants.ECUSH_APPID,Constants.CURR_TYPE);
			} catch (Exception e) {
				detail.setStatus(Utils.union(detail.getStatus(),"0004"));
				clearService.updateDetail(detail);
				LOG.error("卡[" + cardNo + "]应用绑定关系异常,记入差错表.");
				return null;
			}
			newOnLine = false;
		}
		return new String[] { creditAccount, debitAccount };// 返回借贷账户进行本行转账
	}

	/**
	 * 根据判断到的卡状态,更改明细表(脱机交易)
	 * 
	 * @param detail
	 */
	public String getCardStatus(ClearDetailBean detail,String isAccount) {
		if (Constants.OFFLINE_CONSUMPTION.equals(detail.getTranCode())) {
			/*
			 * liuyb
			 * 如果是脱机消费需要有三个校验,只要有一个校验不通过,就存放到差错表里
			 * 山东农信不进行清算周期检验，TC检验状态3:脱机退货且TC为空时不进行检验
			 * ATC交易计数器不管检验成功与否均进行转账,因此这里就不进行判断
			 * TC验证失败是否转账的获取0-转账 1-不转账
			 */
//			if(('2'==detail.getStatus().charAt(1)) && !"0".equals(isAccount)){
//				if(LOG.isInfoEnabled()){
//					LOG.info("TC校验不通过且不进行转账.");
//				}
//				detail.setStatus(Utils.union(detail.getStatus(),"0002"));
//				clearService.updateDetail(detail);
//				return null;
//			}
//			flag = detail.getStatus().substring(2,3);
//			if ('1' != detail.getStatus().charAt(2)) {
//				if(LOG.isInfoEnabled()){
//					LOG.info("TC或交易计数器校验不通过.");
//				}
//				detail.setStatus(Utils.union(detail.getStatus(),"0002"));
//				clearService.updateDetail(detail);
//				return null;
//			}
		}else{
			return "0";
		}
		String cardNo = detail.getAcctNo().trim();
		if(LOG.isInfoEnabled()){
			LOG.info("当前卡状态为:" + detail.getStatus());
		}
		IcInfoReg cardInfo = null;
		if(null != cardNo) {
//			cardInfo = util.getIcCardInfo(detail.getAcctNo().trim());
			cardInfo = new IcInfoRegDao().getIcInfo(cardNo.trim(),detail.getKey23().trim().substring(detail.getKey23().trim().length() - 2));
		}
		if(null == cardInfo || "".equals(cardInfo) || null == cardNo) {
			LOG.error("当前明细的卡号不存在或此卡未启用.");
			detail.setStatus(Utils.union(detail.getStatus(), "0007"));// 卡号不存在
			clearService.updateDetail(detail);
			return null;
		}
		cardNo = cardNo.trim();
		String cardStatus = cardInfo.getIcStatus().trim().substring(0,1);
		String branch = cardInfo.getIssuerBranch().trim();
		detail.setProductNo(cardInfo.getProductNo().trim());// 设置该条明细主卡对应的产品号
		detail.setAcctingBranch(branch);// 设置该卡对应的发卡机构（分行）
//		String orgCoreAccount = proOrgCoreMap.get(productNo
//				+ cardInfo.getIssuerBranch());// 先在缓存里取机构集中户(集中户的获取)
		if(LOG.isInfoEnabled()){
			LOG.info("卡号:" + cardNo);
			LOG.info("产品号:" + detail.getProductNo());
			LOG.info("发卡机构:" + branch);
		}
		
			// 如果缓存里没有，则用dao在数据库中查，然后存入缓存中。(这里的电子现金应用编号取的是定值)
//			LOG.info("========缓存里没有该卡的机构集中户=========");
			try {
				String orgCoreAccount = cardAccounter.getHostAcct(/*cardNo, detail.getKey23(),*/detail.getProductNo(),
								Constants.ECUSH_APPID, branch, "156");
				if(LOG.isInfoEnabled()){
					LOG.info("卡[" + cardNo + "]的机构集中户为[" + orgCoreAccount + "]");
				}
//				proOrgCoreMap.put(productNo + cardInfo.getIssuerBranch(),
//						orgCoreAccount);
				detail.setOrgCoreAccount(orgCoreAccount);//
			} catch (Exception e) {
				LOG.error("查询机构集中户失败，卡号【" + cardNo
						+ "】，应用编号【" + Constants.ECUSH_APPID + "】，账务机构【"
						+ branch + "】");
				detail.setStatus(Utils.union(detail.getStatus(), "0006"));// 机构集中户查找失败
				clearService.updateDetail(detail);
				return null;
			}
		/*
		 * liuyb
		 * 山东农信:只要卡被发出没出现锁卡或销卡结清，借方账户均取电子现金户，因没有待清算户。
		 */
		/*if (ICSTATUS.NATURE.getFlag().equals(cardStatus)
				|| ICSTATUS.REPORTLOST.getFlag().equals(cardStatus)) {
			// 如果状态是正常、挂失，借方账户取电子现金户，贷方账户取机构往来户
			detail.setStatus(Utils.union(detail.getStatus(), "0008"));// 过渡状态,表明借贷账户要取的类型
		} else if (ICSTATUS.OVERDUE.getFlag().equals(cardStatus)
				|| ICSTATUS.BACK.getFlag().equals(cardStatus)
				|| ICSTATUS.REPEAL.getFlag().equals(cardStatus)		
				|| ICSTATUS.REPEAL_AFTER_RL.getFlag().equals(cardStatus)
				|| ICSTATUS.REPEAL_LOAD.getFlag().equals(cardStatus)) {
			// 如果状态是过期、收回、销卡、、挂失销卡、销卡圈提，借方账户取待清算户，贷方账户取机构往来户
			detail.setStatus(Utils.union(detail.getStatus(), "0009"));// 过渡状态,表明借贷账户要取的类型
			
		} else if(ICSTATUS.RENEW.getFlag().equals(cardStatus)
				|| ICSTATUS.RENEW_AFTER_RL.getFlag().equals(cardStatus)){ 
			//已换卡、挂失已补卡
			detail.setStatus(Utils.union(detail.getStatus(), "000a"));
		} else if (ICSTATUS.UNUSE.getFlag().equals(cardStatus)
				|| ICSTATUS.LOCKED.getFlag().equals(cardStatus)
				|| ICSTATUS.REPEAL_SETTLE.getFlag().equals(cardStatus)) {
			// 如果状态是未启用、锁卡.销卡结清，则记录到差错表中
			detail.setStatus(Utils.union(detail.getStatus(), "0005"));
			clearService.updateDetail(detail);
			return null;
		}*/
			//			|| ICSTATUS.LOCKED.getFlag().equals(cardStatus)			|| ICSTATUS.REPEAL_SETTLE.getFlag().equals(cardStatus)
		if (ICSTATUS.UNUSE.getFlag().equals(cardStatus)) {
				// 如果状态是未启用、锁卡.销卡结清，则记录到差错表中
			detail.setStatus(Utils.union(detail.getStatus(), "0005"));
			clearService.updateDetail(detail);
			return null;
		} else {
			detail.setStatus(Utils.union(detail.getStatus(), "0008"));
		}
		return "0";
	}

	/**
	 * 脱机交易的转账总接口
	 * 
	 * @param detail
	 *            :具体的交易明细
	 */
	public  void transation(ClearDetailBean detail,String isAccount) throws Exception {
		String[] cdAccounts = new String[] {};
		Accounter accounter = new AccounterImpl();
		cdAccounts = getOffLineCDAccount(detail,isAccount);// 获得借贷账户
		if (null == cdAccounts) {
			LOG.error("获取卡[" + detail.getAcctNo().trim() + "]的借贷账户失败,把该条明细记入差错表.");
			return;
		}
		TXHelper.beginTX();
//		String debitAcctNo = null;
//		String creditAcctNo = null;
//		boolean error = false;
		try {
			CDAcctingInput cdAcctingInput = initCDAcctingInput(detail);
			cdAcctingInput.setCreditAcctNO(cdAccounts[0]);// 贷方账户
			cdAcctingInput.setDebitAcctNO(cdAccounts[1]);// 借方账户
//			debitAcctNo = cdAcctingInput.getDebitAcctNO();
//			creditAcctNo = cdAcctingInput.getCreditAcctNO();
			if(LOG.isInfoEnabled()){
				LOG.info("借方账户为:"+cdAcctingInput.getDebitAcctNO());
				LOG.info("贷方账户为:"+cdAcctingInput.getCreditAcctNO());
				LOG.info("交易金额为:"+cdAcctingInput.getAmount());
			}
			cdAcctingInput.setRemark(Constants.OFFLINE_CONSUMPTION.equals(detail.getTranCode())?"消费":"退货");
			if(LOG.isInfoEnabled()){
				LOG.info(cdAcctingInput);
			}
			accounter.account(cdAcctingInput);// 调用转账接口
			detail.setStatus(Utils.union(detail.getStatus(), "0001"));// 记该明细记账成功
			detail.setPlatformSerial(cdAcctingInput.getTranSerial());//记录一下转账成功的流水号
			detail.setAmount(cdAcctingInput.getAmount().toString());
			clearService.updateDetail(detail);
			/*if (Constants.OFFLINE_GOODSREJECT.equals(detail.getTranCode())) {
				// 如果是脱机退货,需要记录一下其原交易的发生额和次数
//				orginalDetail = clearService.getOrginalInfo(detail);
				BigDecimal sumReturnAmount = new BigDecimal(orginalDetail.getSumReturnAmount())
						.add(new BigDecimal(detail.getAmount()));
				orginalDetail.setSumReturnAmount(sumReturnAmount + "");
				String num = orginalDetail.getReturnNumber();
				num = (num == null || "".equals(num))?"0":num;
				orginalDetail.setReturnNumber((Integer.valueOf(num)+1)+"");
				clearService.updateDetail(orginalDetail);
			}*/
			TXHelper.commit();
		} catch (Exception e) {
			e.printStackTrace();
			TXHelper.rollback();
			clearService.updateDetail(detail);
			throw e;
//			error = true;
//			try{
//				if (e instanceof AcctingException) {
//					if ("00002".equals(((AcctingException) e).getErrorCode())) {
//						SuspenseAcctImpl.chargeAccount(detail, debitAcctNo, creditAcctNo, e.getMessage(),this.sysdate);
//					}
//				} else {
//					LOG.error("转账过程中调用卡[" + detail.getAcctNo() + "]的转账接口失败.当前明细状态:" + detail.getStatus());
//					detail.setStatus(Utils.union(detail.getStatus(), "0002"));
//				}
////				clearService.updateDetail(detail);
//				LOG.error(e.getMessage(),e);
//			}catch(Exception ex){
//				LOG.error(ex.getMessage(),ex);
//			}
		}finally {
			TXHelper.close();
		}
//		if(error)
//			clearService.updateDetail(detail);

	}

	// 初始化一个转账入参(脱机消费)
	public CDAcctingInput initCDAcctingInput(ClearDetailBean detail) throws Exception {
		CDAcctingInput cdAcctingInput = new CDAcctingInput();
		cdAcctingInput.setAmount(new BigDecimal(detail.getTranAmount()));// 设置交易金额
		cdAcctingInput.setTranSerial(PlatformSerialGennerator.getPlatformSerial(sysdate));// 设置交易流水
		cdAcctingInput.setOperator("999999999");
		if (null == detail.getReceiveBranch() || "".equals(detail.getReceiveBranch().trim())) {
			cdAcctingInput.setTranBranch("000000000");// 发生机构
		} else {
			cdAcctingInput.setTranBranch(detail.getReceiveBranch());// 发生机构
		}
		return cdAcctingInput;
	}
	
/*
	// 初始化一个转账入参(联机退货)
	public CDAcctingInput initCDAcctingInput(OnlineDetailBean detail) {
		LOG.info("================初始化转账参数=================");
		CDAcctingInput cdAcctingInput = new CDAcctingInput();
		cdAcctingInput.setAmount(new BigDecimal(detail.getTranAmount()));// 设置交易金额
		String serial = String.valueOf(SerialGenerator.getSerial());
		cdAcctingInput.setTranSerial(serial);// 设置交易流水
		cdAcctingInput.setOperator("999999");
		if (detail.getReceiveBranch() == null) {
			cdAcctingInput.setTranBranch("1001");// 发生机构
		} else {
			cdAcctingInput.setTranBranch(detail.getReceiveBranch());// 发生机构
		}
		return cdAcctingInput;
	}
	/*
	 * 联机退货转账接口
	 
	public void transation(OnlineDetailBean detail) throws Exception {
		// TODO Auto-generated method stub
		String[] cdAccounts = new String[] {};
		Accounter accounter = new AccounterImpl();
		cdAccounts = getOnLineCDAccount(detail);// 获得借贷账户
		if (null == cdAccounts) {
			LOG.error("===========获取卡[" + detail.getAcctNo()
					+ "]的借贷账户失败,把该条明细记入差错表=========");
			return;
		}
		TXHelper.beginTX();
		try {
			CDAcctingInput cdAcctingInput = initCDAcctingInput(detail);
			LOG.info(cdAcctingInput);
			cdAcctingInput.setCreditAcctNO(cdAccounts[0]);// 贷方账户
			cdAcctingInput.setDebitAcctNO(cdAccounts[1]);// 借方账户
			LOG.info("借方账户为:"+cdAcctingInput.getDebitAcctNO());
			LOG.info("贷方账户为:"+cdAcctingInput.getCreditAcctNO());
			LOG.info("交易金额为:"+cdAcctingInput.getAmount());
			accounter.account(cdAcctingInput);// 调用转账接口
			detail.setStatus(Utils.union(detail.getStatus(), "0001"));// 记该明细记账成功
			detail.setPlatformSerial(cdAcctingInput.getTranSerial());//记录一下转账成功的流水号
			onLineService.updateDetail(detail);
//			if (Constants.OFFLINE_GOODSREJECT.equals(detail.getTranCode())) {
//				// 如果是脱机退货,需要记录一下其原交易的发生额
//				ClearDetailBean orginalDetail = onLineService
//						.getOrginalInfo(detail);
//				Double sumReturnAmount = Double.parseDouble(orginalDetail
//						.getSumReturnAmount())
//						+ Double.parseDouble(detail.getTranAmount());
//				orginalDetail.setSumReturnAmount(sumReturnAmount + "");
//				clearService.updateDetail(orginalDetail);
//			}
			TXHelper.commit();
		} catch (Exception e) {
			TXHelper.rollback();
			LOG.error("===========转账过程中调用卡[" + detail.getAcctNo()
						+ "]的转账接口失败============");
			LOG.info(detail.getStatus());
			detail.setStatus(Utils.union(detail.getStatus(), "0002"));
			onLineService.updateDetail(detail);
			LOG.error(e.getMessage(),e);
		} 
		finally {
			TXHelper.close();
		}
	}
	
	/**
	 * 联机退货获得借贷账号
	 * 
	 * @return
	 
	public String[] getOnLineCDAccount(OnlineDetailBean detail) {
		String temp = getCardStatus(detail);// 首先根据卡状态更新相应的信息
		if (null == temp) {
			LOG.info("================进入卡状态判断=================");
			return null;
		}
		String cardNo = detail.getAcctNo().trim();// 卡号
		String cardIndex = detail.getKey23();// 卡序列号
		String branch = detail.getAcctingBranch();// 卡的账务机构
		String debitAccount = "";// 借方账号
		String creditAccount = "";// 贷方账号
//		String status = detail.getStatus();// 查询账户结果状态
		String tradeCode = detail.getTranCode();// 交易码是否是联机退货
		
		//根据业务规则要求，联机退货是无需发卡方匹配原始交易
		if (Constants.ONLINE_GOODSREJECT.equals(tradeCode)) {
			// 联机退货	
			// 纯电子现金卡 复合卡 都退到补登户里  借方:机构往来户 贷方:补登户
			try {
				debitAccount = cardAccounter.getPlatformAcct(cardNo, cardIndex,
						Constants.ECUSH_APPID, branch, Constants.CURR_TYPE);
				creditAccount = cardAccounter.getOnlineAcct(cardNo, cardIndex,
						Constants.ECUSH_APPID, Constants.CURR_TYPE);
			} catch (Exception e) {
				detail.setStatus(Utils.union(detail.getStatus(), "0004"));
				onLineService.updateDetail(detail);
				LOG.error("=============卡[" + cardNo
						+ "]应用绑定关系异常,记入差错表===============");
				return null;
			}
		}
		return new String[] { creditAccount, debitAccount };// 返回借贷账户进行本行转账
	}

	/**
	 * 根据判断到的卡状态,更改明细表(联机退货交易)
	 * 
	 * @param detail
	 
	public String getCardStatus(OnlineDetailBean detail) {
//		if (Constants.OFFLINE_CONSUMPTION.equals(detail.getTranCode())) {
//			// 如果是脱机消费需要有三个校验,只要有一个校验不通过,就存放到差错表里
//			if (!"1110".equals(detail.getStatus())) {
//				LOG.info("============校验不通过==============");
//				detail.setStatus(Utils.union(detail.getStatus(),"0002"));
//				onLineService.updateDetail(detail);
//				return null;
//			}
//		} else {
//			return "0";
//		}
		String cardNo=detail.getAcctNo();
		LOG.info("============当前卡的状态为:"+detail.getStatus());
		IcInfoReg cardInfo = null;
		if(null!=cardNo) {
			cardInfo = util.getIcCardInfo(detail.getAcctNo().trim());
		}
		LOG.info("=============cardInfo==========["+cardInfo+"]");
		if(null==cardInfo||"".equals(cardInfo)||null==cardNo) {
			LOG.error("=========当前明细的卡号不存在=============");
			detail.setStatus(Utils.union(detail.getStatus(), "0007"));// 卡号不存在
			onLineService.updateDetail(detail);
			return null;
		}
		String cardStatus = cardInfo.getIcStatus();
		String productNo = cardInfo.getProductNO();
		detail.setProductNo(productNo);// 设置该条明细主卡对应的产品号
		detail.setAcctingBranch(cardInfo.getIssuerBranch());// 设置该卡对应的发卡机构（分行）
		String orgCoreAccount = proOrgCoreMap.get(productNo
				+ cardInfo.getIssuerBranch());// 先在缓存里取机构集中户(集中户的获取)
		LOG.info("=========卡号为:" + cardInfo.getCardNo());
		LOG.info("=========产品号为:" + cardInfo.getProductNO());
		LOG.info("=========发卡机构为:" + cardInfo.getIssuerBranch());
		LOG.info("orgCoreAccount:" + orgCoreAccount);
		if (orgCoreAccount == null) {
			// 如果缓存里没有，则用dao在数据库中查，然后存入缓存中。(这里的电子现金应用编号取的是定值)
			LOG.info("========缓存里没有该卡的机构集中户=========");
			try {
				LOG.info("===========开始获取机构集中户==============");
				orgCoreAccount = cardAccounter
						.getHostAcct(detail.getAcctNo(), detail.getKey23(),
								Constants.ECUSH_APPID, detail.getAcctingBranch(), "256");
				LOG.info("============卡["+detail.getAcctNo()+"]的机构集中户为["+orgCoreAccount+"]");
				proOrgCoreMap.put(productNo + cardInfo.getIssuerBranch(),
						orgCoreAccount);
			} catch (Exception e) {
				LOG.error("查询集中户失败，卡号为【" + detail.getAcctNo().trim()
						+ "】，应用编号为【" + Constants.ECUSH_APPID + "】，账务机构为【"
						+ detail.getAcctingBranch() + "】");
				detail.setStatus(Utils.union(detail.getStatus(), "0006"));// 机构集中户查找失败
				onLineService.updateDetail(detail);
				return null;
			}
		}
		detail.setOrgCoreAccount(orgCoreAccount);//
		if (ICSTATUS.NATURE.getFlag().equals(cardStatus)
				|| ICSTATUS.REPORTLOST.getFlag().equals(cardStatus)) {
			// 如果状态是正常、挂失，借方账户取电子现金户，贷方账户取机构往来户
			detail.setStatus(Utils.union(detail.getStatus(), "0008"));// 过渡状态,表明借贷账户要取的类型
			LOG.info("==================卡状态正常==============");
		} else if (ICSTATUS.OVERDUE.getFlag().equals(cardStatus)
				|| ICSTATUS.BACK.getFlag().equals(cardStatus)
				|| ICSTATUS.REPEAL.getFlag().equals(cardStatus)
				|| ICSTATUS.RENEW.getFlag().equals(cardStatus)
				|| ICSTATUS.RENEW_AFTER_RL.getFlag().equals(cardStatus)
				|| ICSTATUS.REPEAL_AFTER_RL.getFlag().equals(cardStatus)
				|| ICSTATUS.REPEAL_LOAD.getFlag().equals(cardStatus)) {
			// 如果状态是过期、收回、销卡、已换卡、挂失已补卡、挂失销卡、销卡圈提，借方账户取待清算户，贷方账户取机构往来户
			detail.setStatus(Utils.union(detail.getStatus(), "0009"));// 过渡状态,表明借贷账户要取的类型
			
		} else if (ICSTATUS.UNUSE.getFlag().equals(cardStatus)
				|| ICSTATUS.LOCKED.getFlag().equals(cardStatus)
				|| ICSTATUS.REPEAL_SETTLE.getFlag().equals(cardStatus)) {
			// 如果状态是未启用、锁卡.销卡结清，则记录到差错表中
			detail.setStatus(Utils.union(detail.getStatus(), "0005"));
			onLineService.updateDetail(detail);
			return null;
		}
		return "0";
	}*/
}
