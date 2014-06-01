package com.icsys.batch.task.offline.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icsys.batch.offline.bean.SummaryBean;
import com.icsys.batch.offline.dao.IcAccNoRegDao;
import com.icsys.batch.offline.dao.IcCoreSummaryDao;
import com.icsys.batch.offline.dao.IcOfflineChkDetDao;
import com.icsys.batch.util.Constants;
import com.icsys.batch.util.Utils;

/**
 * 汇总表相关操作
 * @author 
 *
 */
public class SummarizingServiceImpl{
	private static final Logger LOG = Logger.getLogger(SummarizingServiceImpl.class);
	IcCoreSummaryDao dao  =  new IcCoreSummaryDao();
	IcOfflineChkDetDao offlineDao  = new IcOfflineChkDetDao();
//	IcOnlineChkDetDao onlineDao = new IcOnlineChkDetDao();
	IcAccNoRegDao icAccNoRegDao =  new IcAccNoRegDao();
	
	/**
	 * 根据清算日期生成核心记账文件
	 * @param clearDate 清算日期
	 * @return 核心记账文件
	 * File[0] 为核心记账文件，File[1]为收单系统所需的文件
	 */
//	public File createCoreSysFile(String clearDate,String coreSystemFilePath){
//		List<SummaryBean> result = dao.getSummaryInfo(clearDate);
//		StringBuffer sumFileString = new StringBuffer();
//		for(int i=0;i<result.size();i++){
//			SummaryBean bean = result.get(i);
//			sumFileString.append(bean.getSumFileString()+"\n");
//		}
//		File resultFile = null;
//		try {
//			resultFile = new File(coreSystemFilePath+"/"+clearDate);
//			FileOutputStream  fos = new FileOutputStream(resultFile);
//			fos.write(sumFileString.toString().getBytes());
//			fos.flush();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return resultFile;
//	}
	
	/**
	 * 根据清算日期生成收单系统记账文件
	 * @param clearDate 清算日期
	 * @param batchNo 批次号
	 * @param dataFileName 清算明细文件
	 * @return 收单系统记账文件
	 * 		   File[0] 为成功记录文件，File[1]为失败记录文件
	 */
	/*public File[] createBillSysFiles(String tftDir,String clearDate,String batchNo,String dataFileName,String head,String tail,String offlineCons,String onlineRejects) throws Exception{
		String dataFileNameBak = dataFileName;
		dataFileName = dataFileName.substring(dataFileName.lastIndexOf("/")+1);
		File successFile =new File(tftDir+Utils.getClearDate()+"/"+dataFileName+Constants.SUCCESS_SUFFIX);//修改加上清算日期的文件夹
		File failedFile =new File(tftDir+Utils.getClearDate()+"/"+dataFileName+Constants.FAILED_SUFFIX);
		LOG.info("成功文件名称："+successFile);
		LOG.info("失败文件名称："+failedFile);
		LOG.info("=================本行生成的成功文件名称为"+successFile+"=======================");
		LOG.info("=================本行生成的失败文件名称为"+failedFile+"=======================");
		if(!successFile.exists()){
			 String parent = successFile.getParent();
			 LOG.info("=================成功文件的上级目录为"+parent+"===================");
			 File dir = new File(parent);
			 dir.mkdirs();
		 }
		BufferedOutputStream sbops = null;//成功文件输出流
		BufferedOutputStream fbops = null;//失败文件输出流
		try {
			sbops =new BufferedOutputStream(new DataOutputStream(new FileOutputStream(successFile)));
			fbops =new BufferedOutputStream(new DataOutputStream(new FileOutputStream(failedFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Map<String ,Object> params = new HashMap<String,Object>();
		params.put("clearDate", clearDate);
		params.put("batchNo", batchNo);
		if(dataFileNameBak.startsWith(offlineCons)&&dataFileNameBak.contains(Constants.LocalBank)){
			//如果是本行脱机交易文件
			//step 1 处理成功的记录
			int totalCount = offlineDao.getCount(clearDate,batchNo,Constants.SUCCESS);
			int pageCount = 0;
			boolean writeSuccessHead = true;//判断是否已经写了成功文件的文件头
			boolean writeFailHead = true;//判断是否已经写了失败文件的文件头
			if(totalCount>0){
				pageCount = getPageCount(totalCount,Constants.QUERY_PAGE_LENGTH);
				for(int i=1;i<=pageCount;i++){
					List<ClearDetailBean> sucRecords = offlineDao.getOfflineBillSysFilesInfo(clearDate, batchNo, i, Constants.QUERY_PAGE_LENGTH, Constants.SUCCESS);
					for(ClearDetailBean bean : sucRecords){
						if(bean.getStatus().endsWith(Constants.SUCCESS)) {
				
							if(true==writeSuccessHead) {
								sbops.write(head.getBytes());//成功文件头
								writeSuccessHead=false;
							}
							String recordStr = bean.constructFile();
							sbops.write(recordStr.getBytes());
						}	
						else if(bean.getStatus().endsWith(Constants.FAILED)) {
							if(true==writeFailHead) {
								fbops.write(head.getBytes());//失败文件头
								writeFailHead=false;//失败文件头
							}
							String recordStr = bean.constructFile();
							fbops.write(recordStr.getBytes());
						}
						
				
					}
				}
				if(false==writeSuccessHead) {
					LOG.info("===============生成成功文件尾==============");
					sbops.write(tail.getBytes());
					writeSuccessHead=true;
				} 
				if(false==writeFailHead) {
					LOG.info("==============生成失败文件尾===========");
					fbops.write(tail.getBytes());
					writeFailHead=true;
				}
			}
			sbops.flush();
			sbops.close();
			fbops.flush();
			fbops.close();
		}/*else if(dataFileNameBak.startsWith(onlineRejects)&&dataFileNameBak.contains(Constants.LocalBank)){
			//如果是本行联机退货文件
			//step 1 处理成功的记录
			int totalCount = onlineDao.getCount(clearDate,batchNo,Constants.SUCCESS);
			int pageCount = getPageCount(totalCount,Constants.QUERY_PAGE_LENGTH);
			for(int i=1;i<=pageCount;i++){
				List<OnlineDetailBean> sucRecords = onlineDao.getOnlineBillSysFilesInfo(clearDate, batchNo, i, Constants.QUERY_PAGE_LENGTH, Constants.SUCCESS);
				for(OnlineDetailBean bean : sucRecords){
					String recordStr = bean.constructFile();
					sbops.write(recordStr.getBytes());
				}
			}
			sbops.flush();
			sbops.close();
			
			//step 1 处理成功的记失败记录
			totalCount = onlineDao.getCount(clearDate,batchNo,Constants.FAILED);
			pageCount = getPageCount(totalCount,Constants.QUERY_PAGE_LENGTH);
			for(int i=1;i<=pageCount;i++){
				List<OnlineDetailBean> sucRecords = onlineDao.getOnlineBillSysFilesInfo(clearDate, batchNo, i, Constants.QUERY_PAGE_LENGTH, Constants.FAILED);
				for(OnlineDetailBean bean : sucRecords){
					String recordStr = bean.constructFile();
					fbops.write(recordStr.getBytes());
				}
			}
			fbops.flush();
			fbops.close();
		}
		return new File[]{successFile,failedFile};
	}*/

	public int getPageCount(int totalCount,int pageLength){
		int left = totalCount%pageLength;
		int pageCount = totalCount/pageLength;
		if(left>0){
			pageCount ++;
		}
		return pageCount;
	}
	
	/**
	 * 向收单系统发送记账文件，并发送通知
	 * @param accountFile 核心记账文件 File[0]:successFile   File[1]:failedFile
	 * @param hostNo 在aixClient中配置的服务器的信息编号
	 * @param remoteFolder 在服务器中存放上传文件的路径
	 * @return 文件发送结果
	 */
	/*public String sendFileToBillSys(File[] accountFile,String hostNo,String remoteFolder,String outDir,String address,int port){
		String successFileName = accountFile[0].getName();//获得成功文件的文件名（全路径）
		String failedFileName = accountFile[1].getName();//获得失败文件的文件名（全路径）
		NoticeClient client = new NoticeClient();
		TFTClientOnAix tftClient = new TFTClientOnAix(hostNo);
		boolean sucNotice = false;
		boolean failNotice = false;
		if(!remoteFolder.endsWith("/")){
			remoteFolder = remoteFolder +"/";
		}
		LOG.info("=========successFileName=========="+successFileName);
		boolean sucSend = tftClient.uploadFile(remoteFolder+successFileName, outDir+Utils.getClearDate()+"/"+successFileName+" -t1259");
		if(sucSend){
			client.setServerAddress(address);
			client.setPort(port);
			sucNotice = client.sendNotice(Utils.getCurrentDate("yyyyMMdd"),successFileName, "成功文件", "成功交易记录");
			LOG.info("============通知前置成功!==============");
		}
		LOG.info("发送通知artery的地址："+address);
		LOG.info("发送通知artery的端口："+port);
		boolean failedSend = tftClient.uploadFile(remoteFolder+failedFileName, outDir+Utils.getClearDate()+"/"+failedFileName+" -t1259");
		//给核心系统发送通知
		if(failedSend){
			//failNotice = client.sendNotice(Utils.getCurrentDate("yyyyMMdd"), remoteFolder+failedFileName, "失败文件", "失败交易记录");
			LOG.info("=============失败文件发送成功,但不发送通知==================");
		}
		if(sucNotice&&failNotice){
			return Constants.SUCCESS;
		}else{
			return Constants.FAILED;
		}
	}*/
	
	
	/**
	 * 按清算日期，查询汇总机构往来户的交易金额 和核心账户的交易金额，用于生成对账文件
	 * @param clearDate 清算日期
	 * @param billSource 数据来源（银联/本行）
	 * @param tranMethod 交易方式（脱机/联机）
	 * @return   
	 */
	public void saveSummaryInfo(String clearDate,String batchNo,String fileSrc,String tranMethod) throws Exception{
		if(Constants.CLEARING_SELF.equals(fileSrc)){
			//如果文件来自本行
			if(Constants.TRAN_METHOD_OFFLINE.equals(tranMethod)){//如果是脱机交易
				System.out.println("脱机交易");
				List<Map<String,Object>> selfOfflineSum =offlineDao.getOfflineSummary(clearDate,batchNo,Constants.CLEARING_SELF);//本行脱机交易汇总
				System.out.println("大小:"+selfOfflineSum);
				for(int i=0;i<selfOfflineSum.size();i++){
					//本行脱机交易汇总，如果消费金额>退货金额 则转出账户为：机构集中户；转入账户为：商户待清算总户
					//如果消费金额<退货金额 则转出账户为：商户待清算总户；转入账户为：机构集中户
					Map<String,Object> tmpMap = selfOfflineSum.get(i);
					String tranAmt = tmpMap.get("SUM").toString();
					if("0".equals(tranAmt)) continue;
					String orgConAccount = tmpMap.get("ORG_CORE_ACCOUNT")+"";//机构集中户
					SummaryBean bean = new SummaryBean();
					bean = dao.initSummaryBean(bean);
					bean.setClearDate(clearDate);
					bean.setAcctBranch(orgConAccount.substring(0, 4));//机构集中户的规则，取机构号
					bean.setTranAmt(tranAmt);//交易金额String
					double amt = Double.parseDouble(tranAmt);//交易金额 Double
					if(amt>0){//消费金额>退货金额
						bean.setOutAcc(orgConAccount);//转出帐户
						bean.setOutAccName("机构集中户");//转出帐户名称
						bean.setInAcc(Utils.getMerChantAcct());//转入帐户
						bean.setInAccName("商户待清算总户");//转入帐户名称						
					}else{
						bean.setTranAmt(Math.abs(amt)+"");
						bean.setOutAcc(Utils.getMerChantAcct());//转出帐户
						bean.setOutAccName("商户待清算总户");//转出帐户名称
						bean.setInAcc(orgConAccount);//转入帐户
						bean.setInAccName("机构集中户");//转入帐户名称
					}
					dao.saveOrUpdateSummary(bean);
				}
			}/*else if(Constants.TRAN_METHOD_ONLINE.equals(tranMethod)){//如果是联机机交易
				List<Map<String,Object>> selfOnlineSum =onlineDao.getOnlineSummary(clearDate,batchNo,Constants.CLEARING_SELF);//本行联机退货汇总
				for(int i=0;i<selfOnlineSum.size();i++){
					//本行联机退货汇总，转出账户为：商户待清算总户；转入账户为：机构集中户
					Map<String,Object> tmpMap = selfOnlineSum.get(i);
					String orgConAccount = tmpMap.get("ORG_CORE_ACCOUNT").toString();//机构集中户
					String tranAmt = tmpMap.get("SUM").toString();
					if("0".equals(tranAmt)) continue;
//					String acctBranch = tmpMap.get("ACCTING_BRANCH")+"";
					SummaryBean bean = new SummaryBean();
					bean = dao.initSummaryBean(bean);
					bean.setAcctBranch(orgConAccount.substring(0,4));
					bean.setClearDate(clearDate);
					bean.setTranAmt(tranAmt);//交易金额
					bean.setOutAcc(Utils.getMerChantAcct());//转出帐户
					bean.setOutAccName("商户清算总户");//转出帐户名称
					bean.setInAcc(orgConAccount);//转入帐户
					bean.setInAccName("机构集中户");//转入帐户名称
					dao.saveOrUpdateSummary(bean);
				}
			}*/
		}else{
//			Map<String ,String> cacheMap = new HashMap<String,String>();
			//如果文件来自银联(广东农信专用)
			if(Constants.TRAN_METHOD_OFFLINE.equals(tranMethod)){//如果是脱机交易
				LOG.info("进入转账汇总步骤...");
				List<Map<String,Object>> unionOfflineSum =offlineDao.getOfflineSummary(clearDate,batchNo,Constants.CLEARING_UNIONPAY);//银联脱机交易汇总

				int batchSn=0;
				for(int i=0;i<unionOfflineSum.size();i++){
					//银联脱机交易汇总，如果消费金额>退货金额 则转出账户为：机构集中户；转入账户为：银联脱机清算总户
					//如果消费金额<退货金额 则转出账户为：银联脱机清算总户；转入账户为：机构集中户
					Map<String,Object> tmpMap = unionOfflineSum.get(i);
					String tranAmt = tmpMap.get("SUM").toString().trim();
					if("0".equals(tranAmt)) continue;
//					String acctBranch = tmpMap.get("ACCTING_BRANCH")+"";
					String orgConAccount = tmpMap.get("ORG_CORE_ACCOUNT")+"";//机构集中户
					SummaryBean bean = new SummaryBean();
					bean = dao.initSummaryBean(bean);
					bean.setBatchNo(batchNo);
					bean.setBatchSn((batchSn++)+"");
					bean.setClearDate(clearDate);
					bean.setAcctDate(clearDate);//对账单提交给核心系统的日期
					bean.setRemark("脱机清算转账汇总");
					bean.setTranAmt(tranAmt);//交易金额String
					bean.setAcctBranch(orgConAccount.substring(0, 6));//机构集中户的规则，取机构号
					BigDecimal amt = new BigDecimal(tranAmt);//交易金额 
					
					if(amt.compareTo(BigDecimal.ZERO)>0){//消费金额>退货金额
						LOG.info("消费金额大于退货金额.");
						bean.setOutAcc(orgConAccount);//转出帐户
						bean.setOutAccName("机构集中户");//转出帐户名称
						bean.setInAcc(Utils.getMerChantAcct());//转入帐户
						bean.setInAccName("银联资金往来户");//转入帐户名称
					}else{
						bean.setTranAmt(amt.negate().toString());
						bean.setOutAcc(Utils.getMerChantAcct());//转出帐户
						bean.setOutAccName("银联资金往来户");//转出帐户名称
						bean.setInAcc(orgConAccount);//转入帐户
						bean.setInAccName("机构集中户");//转入帐户名称
					}
					dao.saveOrUpdateSummary(bean);
				}
			}/*else if(Constants.TRAN_METHOD_ONLINE.equals(tranMethod)){//如果是联机机交易
				List<Map<String,Object>> unionOnlineSum =onlineDao.getOnlineSummary(clearDate,batchNo,Constants.CLEARING_UNIONPAY);//银联联机退货汇总
				for(int i=0;i<unionOnlineSum.size();i++){
					//银联联机退货汇总，转出账户为：银联清算总户；转入账户为：机构集中户
					Map<String,Object> tmpMap = unionOnlineSum.get(i);
					String branchCode = cacheMap.get("branchCode") ;//先从缓存中获取银联的清算账户
					SummaryBean bean = new SummaryBean();
					Object billOrg = tmpMap.get("BILL_ORG");//如果缓存中不存在，则获取银联的机构代码
//					String acctBranch = tmpMap.get("ACCTING_BRANCH")+"";
					
					if(branchCode==null){
						if(billOrg!=null){
							SpecialAccount outAcount = icAccNoRegDao.querySpecialAccount(Constants.UNIONPAY_INDEX,billOrg.toString());//根据银联的机构代码，查询银联的清算账户
							if(outAcount!=null){
								bean.setOutAccName(outAcount.getUseDescription());//转出帐户名称
								branchCode = outAcount.getAcctNo();
								cacheMap.put("branchCode", branchCode);//将查询上来的银联清算账户存入缓存中
								cacheMap.put(branchCode, outAcount.getUseDescription());
							}else{
								throw new Exception("代码为【"+billOrg.toString()+"】的银联机构不存在，系统挂起");
							}
						}
					}else{
						String outAccName = cacheMap.get(branchCode);
						if(outAccName==null){
							SpecialAccount outAcount = icAccNoRegDao.querySpecialAccount(Constants.UNIONPAY_INDEX,billOrg.toString());//根据银联的机构代码，查询银联的清算账户
							if(outAcount!=null){
								outAccName = outAcount.getUseDescription();
								cacheMap.put(branchCode,outAccName);//将账户名称存入缓存
							}else{
								throw new Exception("代码为【"+billOrg.toString()+"】的银联机构不存在，系统挂起");
							}
						}
						bean.setOutAccName(outAccName);//转出帐户名称
					}
					String tranAmt = tmpMap.get("SUM").toString();
					if("0".equals(tranAmt)) continue;
					String orgConAccount = tmpMap.get("ORG_CORE_ACCOUNT").toString();//机构集中户
					bean.setAcctBranch(orgConAccount.substring(0, 4));
					bean = dao.initSummaryBean(bean);
					bean.setClearDate(clearDate);
					bean.setOutAcc(branchCode);//转出帐户
					bean.setInAcc(orgConAccount);//转入帐户
					bean.setInAccName("机构集中户");//转入帐户名称
					bean.setTranAmt(tranAmt);//交易金额
					dao.saveOrUpdateSummary(bean);
				}	
			}*/
		}
	}
}