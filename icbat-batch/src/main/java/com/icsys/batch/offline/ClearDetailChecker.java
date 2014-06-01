package com.icsys.batch.offline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.icsys.batch.encryptor.ARPCOutput;
import com.icsys.batch.encryptor.ARQCInput;
import com.icsys.batch.encryptor.SDNXEncryptorImpl;
import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.offline.bean.TxCounterBean;
import com.icsys.batch.offline.dao.IcOfflineRepRecordDao;
import com.icsys.batch.util.Constants;
import com.icsys.batch.util.SystemParamValue;
import com.icsys.batch.util.Utils;

/**
 * 校验交易明细
 * @author liuyb
 */
public class ClearDetailChecker {
	
	private static final Logger LOG = Logger.getLogger(ClearDetailChecker.class);
	
	private ClearDetailBean detail;
//    CardQueryServiceImpl cardService=ServiceLocator.getSerivce(CardQueryService.class);//验证ATC所用到的数据库操作类
	/**
	 * 校验交易明细 ，更改交易明细（ClearDetailBean）的状态位。
	 * 交易明细状态位说明：第一位 清算周期；第二位 TC密文；第三位 計數器 ；第四位 是否记账 。
	 * 都通过时状态为 1111
	 * @param clearDate 清算日期 
	 */
	public  void  check(String clearDate) throws Exception{
		/*
		 * liuyb
		 * 山东农信不进行清算周期验证，直接返回成功
		 * 脱机退货只需要验证清算周期。
		 */
		/*if(LOG.isInfoEnabled()){
			LOG.info("开始校验清算日期...");
		}
		checkTxDate(clearDate);*/
		if(Constants.OFFLINE_GOODSREJECT.equals(detail.getTranCode())){
			if(LOG.isInfoEnabled()){
				LOG.info("山东农信脱机退货不进行任何检验。");
			}
			return;
		}
		if(LOG.isInfoEnabled()){
			LOG.info("开始校验TC密文...");
		}
		checkTC();
		if(LOG.isInfoEnabled()){
			LOG.info("开始校验交易计数器...");
		}
		checkCounter();	
	}
	
	/**
	 * 步骤一:校验明细的电子现金原始tag9A交易日期与IC卡平台的交易日期的差额，是否超过清算周期。
	 * @param clearDate 清算日期
	 */
	public void checkTxDate(String clearDate){
		clearDate = clearDate.substring(2,clearDate.length());
		//LOG.debug("检查电子现金原始交易日期是否超过清算周期");
//        boolean flag = false;//校验结果
        String txdate = detail.getTag9a();//获得交易日期
        //LOG.debug("txDate:"+txdate);
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");//日期格式
        try {
            Date from = format.parse(txdate);//交易日期
            Date to = format.parse(clearDate);//清算日期
            LOG.debug("============交易日期为:["+from+"]================");
            LOG.debug("============清算日期为:["+to+"]============");
            if (Utils.isInsidePale(from, to, Integer.parseInt(SystemParamValue.getOffClearCycle()))) {
//            	flag = true;
           	 	detail.setStatus(Utils.union(detail.getStatus(),"1000"));
            }else{
            	//超过清算周期
            	detail.setStatus(Utils.union(detail.getStatus(),"3000"));
            }
            return;
        } catch (ParseException e) {
            //LOG.error("日期转换失败，交易日期或者清算日期取值可能为空或者有误");
            e.printStackTrace();
        } catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		detail.setStatus(Utils.union(detail.getStatus(),"2000"));
	}

	/**
	 * 步骤二:校验tc
	 * String hsmIp,int hsmPort,int hsmTimeout,String hsmStr,String hsmKeyName
	 */
    private void checkTC() throws Exception{
    	try {    		
        	String cardNo = detail.getAcctNo().trim();
        	String panTail = detail.getKey23();
        	String keyIndex=detail.getTag9f10().substring(2, 4);//取得keyIndex
//        	int keyVersion = Integer.parseInt(keyIndex);
        	
        	if(panTail==null||panTail.trim().length()<2){
        		detail.setCheckTcResults("2");
            	detail.setStatus(Utils.union(detail.getStatus(),"0200"));
            	return;
        	}
        	panTail = panTail.trim().length()>2?panTail.trim().substring(panTail.trim().length() -2):panTail.trim();
        	String pan = cardNo.substring(cardNo.length() - 14, cardNo.length()) + panTail;
        	String processGene = detail.getTag9f36();//交易计数器
        	String version = detail.getTag9f10().substring(4,6);//取得密文类型
        	String otherMoney = detail.getTag9f03()==null || "".equals(detail.getTag9f03().trim()) ? "000000000000":detail.getTag9f03();
        	String aqdt = "";//元数据
            //处理密文版本为01的情况
            if(version.equals("01")){
            	aqdt = detail.getTag9f02()+otherMoney+"0"+detail.getTag9f1a()+detail.getTag95()+"0"+
            	detail.getTag5f2a()+detail.getTag9a()+detail.getTag9c()+detail.getTag9f37()+detail.getTag82()+
            	detail.getTag9f36()+detail.getTag9f10().substring(6, 14);
            //处理密文版本为17的情况(针对QPBOC)
            }else if(version.equals("17")){
            	aqdt = detail.getTag9f02() + detail.getTag9f37() + detail.getTag9f36() + detail.getTag9f10().substring(8,10);
            }else{
            	LOG.error("解析出的密文版本号为：["+version+"]");
            	throw new Exception("解析出的密文版本号为：["+version+"]");
            }
        	String ARQC = detail.getTag9f26();//应用密文
//
//        	if(Constants.OFFLINE_GOODSREJECT.equals(detail.getTranCode()) && (ARQC == null || "".equals(ARQC))){
//        		if(LOG.isInfoEnabled()){
//        			LOG.info("脱机退货应用密文为空不进行TC检验，按正常处理!");
//        		}
//				detail.setCheckTcResults("3");
//				detail.setStatus(Utils.union(detail.getStatus(),"0300"));
//				return;
//        	}
        	
        	/*
        	int iccType = 0;
        	Engine api;
        	try {
    			api = new Engine(hsmIp,hsmPort,hsmTimeout,hsmStr);
        	}catch (Exception e) {
        		LOG.error("================TC校验器初始化失败===================挂起");
    			e.printStackTrace();
    			throw e;
    		}
        	try{
        		hsmKeyName=hsmKeyName.replace("gdICCard", cardNo.substring(0, 6));//安全平台增加cardBin
        		if(LOG.isDebugEnabled()){
    			LOG.debug("fullName【"+hsmKeyName+"】");
    			LOG.debug("keyVersion【"+keyVersion+"】");
    			LOG.debug("pan【"+pan+"】");
    			LOG.debug("processGene【"+processGene+"】");
    			LOG.debug("aqdt【"+aqdt+"】");
    			LOG.debug("ARQC【"+ARQC+"】");
    			LOG.debug("iccType【"+iccType+"】");
        		}
//    			boolean result = true;
    			//TODO TC校验
    			int ret = api.UnionVerifyARQCUsingDerivedKey(hsmKeyName,keyVersion,pan,processGene,8,aqdt,ARQC,0);
*/
        	
        	
        	ARQCInput input = new ARQCInput();
        	input.setAcctNo(pan);
        	input.setAppNO(keyIndex);
            // 生成4位交易计数器
        	input.setIcTrnCnt(processGene);
        	input.setArqc(ARQC);
        	input.setAqdt(aqdt);
        	input.setArc("3030");
        	input.setTrnMod("01");
        	ARPCOutput output = SDNXEncryptorImpl.getInstance().verifyARQC(input);
        	if("000000".equals(output.getThdRetCod())){
        	
    			
//    			ret=3;//暂且让TC也校验 通过
//        		if(!(ret < 0)){
        			if(LOG.isInfoEnabled()){
        				LOG.info("TC校验成功.");
        			}
    				detail.setCheckTcResults("1");
    				detail.setStatus(Utils.union(detail.getStatus(),"0100"));
    			}else{
    				if(LOG.isInfoEnabled()){
    					LOG.info("TC校验失败.");
    				}
    				detail.setCheckTcResults("2");
    				detail.setStatus(Utils.union(detail.getStatus(),"0200"));
    			}
    		} catch (Exception e) {
    			LOG.error("TC校验异常." + e.getMessage());
    			detail.setCheckTcResults("2");
            	detail.setStatus(Utils.union(detail.getStatus(),"0200"));
    		}
    		
    		/*test*/
			detail.setCheckTcResults("1");
			detail.setStatus(Utils.union(detail.getStatus(),"0100"));
//		} catch (Throwable e) {
//			e.getStackTrace();
//		}
    	
	}
	
	/**
	 * 步骤三：校验计数器是否重复
	 */
	public void checkCounter() {
        try {
            //检查交易计数器
            check(detail);
            //计数器检查通过更改明细状
            detail.setStatus(Utils.union(detail.getStatus(),"0010"));
            if(LOG.isInfoEnabled()){
            	LOG.info("交易计数器校验成功.");
            }
        }catch (Exception e) {
        	//计数器插入失败，
        	detail.setStatus(Utils.union(detail.getStatus(),"0020"));
        	if(LOG.isInfoEnabled()){
        		LOG.info("交易计数器校验失败.");
        	}
        }
            
    }
	
    private void check(ClearDetailBean detail) throws Exception {
        //获取交易计数器，并插入，如果插入成功，表示没有重复的计数器
        //插入交易计数器
        TxCounterBean txCounter = new TxCounterBean();
        txCounter.setTag9F36(detail.getTag9f36());
        txCounter.setCleardate(detail.getClearDate());
        txCounter.setIccardno(detail.getAcctNo());
        txCounter.setCardIndex(detail.getKey23());
        new IcOfflineRepRecordDao().addTxCounter(txCounter);
    }    
	
	public ClearDetailBean getDetail() {
		return detail;
	}

	public void setDetail(ClearDetailBean detail) {
		this.detail = detail;
	}
}
