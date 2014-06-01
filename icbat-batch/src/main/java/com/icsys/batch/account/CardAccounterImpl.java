package com.icsys.batch.account;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CardAccounterImpl  {

	private static Logger LOG = Logger.getLogger(CardAccounterImpl.class);
	


	public String getOnlineAcct(String cardNo, String cardIndex , String aid, String currType) throws Exception {
		try {
			IcInfoRegDao dao = new IcInfoRegDao();
			IcAppRegDao rep = new IcAppRegDao();
			String acctNoId = dao.getIcInfo(cardNo, cardIndex).getAcctNoId();
			String acctNo = rep.getIcAppReg(acctNoId, aid).getApplicationOnlineSubAcct();
			return acctNo;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}


	public BigDecimal getAcctBalance(String acctNo) throws Exception {
		AcctBalanceQueryDao acctQuery = new AcctBalanceQueryDao();
		try {
			return acctQuery.getAccount(acctNo).getBalance();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public BigDecimal getOfflineAcctBalance(String cardNo, String cardIndex,String appNo, String currType)
			throws Exception {
//		AcctProxyImpl acctProxy = new AcctProxyImpl();
//		return acctProxy.getOfflineAcctBalance(cardNo, cardIndex,appNo);
		return null;
	}

	public BigDecimal getOnlineAcctBalance(String cardNo,String cardIndex, String appNo, String currType)
			throws Exception {
//		AcctProxyImpl acctProxy = new AcctProxyImpl();
//		return acctProxy.getOnlineAcctBalance(cardNo,cardIndex, appNo);
		return null;
	}
	
	public String getOfflineAcct(String cardNo, String cardIndex,String aid, String currType) throws Exception {
		try {
			IcInfoRegDao dao = new IcInfoRegDao();
			IcAppRegDao rep = new IcAppRegDao();
			String acctNoId = dao.getIcInfo(cardNo, cardIndex).getAcctNoId();
			String acctNo = rep.getIcAppReg(acctNoId, aid).getApplicationOfflineSubAcct();
			return acctNo;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	public String getPlatformAcct(String cardNo, String cardIndex,String aid, String branch, String currType) throws Exception {
		try {
			IcInfoRegDao dao = new IcInfoRegDao();
//			IcAppRegDao rep = new IcAppRegDao();
			IcInfoReg info = dao.getIcInfo(cardNo, cardIndex);
//			String acctNoId = info.getAcctNoId();
//			String branchNo = rep.getIcAppReg(acctNoId, aid).getBusinessBranch();
			String productNo = info.getProductNo();
			
			ProductAppBinding icProdApp = searchAppConfig(productNo, aid,"156");//多币种  暂时取156
			//取机构往来账机构需要后补0到九位
//			if (icProdApp.getBranchFlag().equalsIgnoreCase("0"))
//				return StringUtils.rightPad(branchNo, 9, "0")
//						+ icProdApp.getPlatformAcctNo();
//			else
				return StringUtils.rightPad(icProdApp.getBranchCode(), 9, "0")
						+ icProdApp.getPlatformAcctNo();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	public ProductAppBinding searchAppConfig(String productNo,String aid, String currType)
	throws Exception{
	/*入参检查*/
	if(null == productNo || productNo.trim().equals(""))
		throw new Exception("产品号为空！");
	if(null == aid || aid.trim().equals(""))
		throw new Exception("AID为空！");
	
	IcProdAppDao dao = new IcProdAppDao();
	ProductAppBinding appConfig = null;
	
	try {
		if(LOG.isDebugEnabled())
			LOG.debug("检索产品应用配置input is ["+productNo+","+aid+"]");
		
		
		/*检查配置状态是否正常,非正常状态配置值无法正常使用*/
		/*
		if(!isConfigcNormalStatus(productNo,aid))
			throw new NotUseValueAbnormalApplicConfigException(productNo,aid);
		*/
			
		//获得产品应用配置
		appConfig = dao.getConfigByPorductNoAid(productNo, aid);
		if(null==appConfig || null==appConfig.getProductNo()||null==appConfig.getAid()){
			if(LOG.isDebugEnabled())
				LOG.debug("检索产品应用配置,无此配置！");
			throw new Exception("检索产品应用配置,无此配置！");
		}
		
		/****************************/	
		/*检查配置状态是否正常,非正常状态配置值无法正常使用*/
		boolean isNORMAL = false;//是否正常- true状态正常
		isNORMAL = appConfig.isNormalStatus();
		
		if(!isNORMAL)
			throw new Exception("检索产品应用配置状态不正常！");
		/****************************/	
		
		if(LOG.isDebugEnabled())
			LOG.debug("检索产品应用配置,成功");
	}catch (Exception r) {
		
		LOG.error("检索产品应用配置,系统错", r);
		throw new Exception(r);
	}
	return appConfig;
}
	

	public String getClearAcct(String cardNo, String cardIndex,String aid,String currType) throws Exception {
		IcClearAcctDao dao = new IcClearAcctDao();
		IcClearAcct result = dao.getIcClearAcct(cardNo, cardIndex, aid);	
		return result.getOfflineClearSubAcct();
	}
	

	public String getHostAcct(/*String cardNo,String cardIndex, */String productNo,String aid, String branch, String currType) throws Exception {
		try {
//			IcInfoRegDao dao = new IcInfoRegDao();
//			IcInfoReg icInfo = dao.getIcInfo(cardNo, cardIndex);
//			if (icInfo == null) {
//				throw new Exception(cardNo);
//			}
//			String productNo = icInfo.getProductNo();
			
			ProductAppBinding icProdApp = searchAppConfig(productNo, aid,currType);
//			IcAppRegRepository appRep = new IcAppRegRepository();
//			String branchNo = appRep.getIcAppRegByCardNo(cardNo, appNo)
//					.getBusinessBranch();
//			if (icProdApp.getBranchFlag().equalsIgnoreCase("0"))
//				return branch + icProdApp.getHostAcctNo();
//			else
				return StringUtils.rightPad(icProdApp.getBranchCode(), 9, "0") + icProdApp.getHostAcctNo();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
}
