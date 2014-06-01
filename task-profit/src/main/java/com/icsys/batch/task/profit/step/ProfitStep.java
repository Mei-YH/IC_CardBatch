package com.icsys.batch.task.profit.step;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.profit.proxy.bean.GrAccAcct;
import com.icsys.batch.task.profit.proxy.bean.GrCardAppreg;
import com.icsys.batch.task.profit.proxy.bean.IcCardabnReq;
import com.icsys.batch.task.profit.proxy.bean.IcCardinfoReq;
import com.icsys.batch.task.profit.proxy.dao.GrAccAcctDao;
import com.icsys.batch.task.profit.proxy.dao.GrCardAppregDao;
import com.icsys.batch.task.profit.proxy.dao.GrCardabnReqDao;
import com.icsys.batch.task.profit.proxy.dao.IcCardinfoReqDao;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.DateUtil;
import com.icsys.batch.util.SystemParamValue;

public class ProfitStep implements Step {

	private Logger LOG = Logger.getLogger(ProfitStep.class);
	public long estimate(JobContext arg0) throws JobException {
		return 0;
	}
	public void perform(JobContext context, Executor executor) throws JobException {
		LOG.debug("转收益任务开始执行");
		String path = (String) context.getAttribute("resultFilePath");// 要生成结果文件的路径
		String list = (String) context.getAttribute("listPath");
		String sysDateStr = context.getJobDate();//系统日期
		/**
		 * 初始化装文件名和装类容的集合
		 */
		Map<String, String> adressMap = MapInit.getAdressMap();
		Map<String, StringBuilder> fileContentMap = MapInit.getFileContent();
		Set<String> adressSet = adressMap.keySet();//两个集合的keySet都是一样的
		GrCardabnReqDao cardAbn = new GrCardabnReqDao();
		List<IcCardabnReq> abnlist = cardAbn.showCardabn();
		int clearCyc = 0;
		try {
			clearCyc = Integer.parseInt(SystemParamValue.getOffClearCycle());//清算周期30天
		} catch (Exception e) {
			throw new JobException("获取清算周期失败！");
		}

		//循环判断
		for(IcCardabnReq abn:abnlist){
			//异常状态为0，为挂失状态//后续处理状态为0：未处理1:换卡 2：销卡就进行下一步处理
			IcCardinfoReqDao cardInfo=new IcCardinfoReqDao();
			IcCardinfoReq icCadinfoReq = cardInfo.showCardInfo(abn.getIc_No(),abn.getIc_Index());//获得卡登记表
			String cfDateStr = icCadinfoReq.getCfValidDt();//获得系统的证书有效期
			Date cfDate=DateUtil.getDateFromShortStr(cfDateStr);
			try {	
				Date sysDate=DateUtil.getDateFromShortStr(BatchDateUtil.getLastDateWithStringFormat(sysDateStr));//使用的是yyyyMMdd这个形式
				if(sysDate.equals(DateUtil.getAfterNDay(cfDate,clearCyc))){//判断系统日期是否等于(证书有效期+30)
					GrCardAppreg cardAppList = new GrCardAppregDao().showCardApp(icCadinfoReq.getAcctNo(), "A000000333010106");
					GrAccAcct offAccList = new GrAccAcctDao().showAccAcct(cardAppList.getCagOffacct(),"A000000333010106");
					BigDecimal offAccBalance = offAccList.getAccBalance();//电子钱包余额
					if(offAccBalance.compareTo(BigDecimal.ZERO) > 0){//判断电子金额是否大于0,然后进行规定的拼接
						StringBuilder sb = new StringBuilder();
						sb.append(StringUtils.rightPad(icCadinfoReq.getIcNo(), 19, " "));//卡号
						sb.append(icCadinfoReq.getIcStat());//卡状态
						sb.append(abn.getTx_Dt());//销卡日期
						sb.append(StringUtils.leftPad(offAccBalance.movePointRight(2).toString(),15,"0"));//电子账户余额
						sb.append(StringUtils.leftPad(offAccBalance.movePointRight(2).toString(),15,"0"));//转收益额
						//得到卡属地势
						/**
						 * 依次比较看卡属地市属于哪个地区，在将内容放入相应的文件中
						 */
						String key = MapInit.getAreaMap().get(icCadinfoReq.getIcPro());
						fileContentMap.get(key).append(sb.append("\n").toString());
						fileContentMap.put(key, fileContentMap.get(key));
					}else{
						LOG.info("条件不满足:余额小于零");
					}
				}else{
					LOG.info("条件不满足:系统日期不等于(证书有效期[" + cfDateStr + "]+"+clearCyc+")");
				}
			} catch (Exception e) {
				throw new JobException("任务异常挂起，" + e.getMessage());
			}
	}
		//写入本地文件

		try {	
			List<String> res = new ArrayList<String>();
			for(String key : adressSet){
				StringBuilder fileContent =  fileContentMap.get(key);
//				if(!fileContent.toString().equals("")){
						//主要数据文件
							File resultFile = new File(path + File.separator + sysDateStr + File.separator + key,adressMap.get(key) + sysDateStr);//ICSleepAcctDeductFile_8位日期
							if (!resultFile.exists()) {
								resultFile.getParentFile().mkdirs();
								resultFile.createNewFile();
							}
							BufferedWriter resultFileWriter = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(resultFile),"GBK"));
							resultFileWriter.write(fileContent.toString());
							resultFileWriter.flush();
							resultFileWriter.close();
							FileInputStream fis = new FileInputStream(resultFile);
							int size = fis.available();//得到主文件大小
							res.add(path + File.separator + sysDateStr + " " + key + " " + resultFile.getName() + " " + size);
							//生成验证文件
							File chkFile = new File(resultFile.getAbsolutePath() + ".CHK");//ICSleepAcctDeductFile_8.CHK位日期
							if (!chkFile.exists()) {
								chkFile.getParentFile().mkdirs();
								chkFile.createNewFile();
							}
							BufferedWriter chkFileWriter = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(chkFile),"GBK"));
							String chkResult = resultFile.getName() + " " + size + "\n";
							chkFileWriter.write(chkResult);
							chkFileWriter.flush();
							chkFileWriter.close();
							res.add(path + File.separator + sysDateStr + " " + key + " " + chkFile.getName() + " " + new FileInputStream(resultFile).available());
//				}
			}
			FileUtils.writeLines(new File(list,"ica_toprofit_cbod_" + sysDateStr + ".list"), res);
			} catch (Exception ex) {
				throw new JobException("写入文件失败！任务挂起。" + ex.getMessage());
			}
			LOG.debug("转收益任务执行结束");

	}


}
