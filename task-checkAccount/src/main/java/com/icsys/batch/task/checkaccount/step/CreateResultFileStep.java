package com.icsys.batch.task.checkaccount.step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.checkAccount.IcCheckAccountErrList;
import com.icsys.batch.task.checkaccount.proxy.CheckAccountConstants;
import com.icsys.batch.task.checkaccount.proxy.bean.GrCheckAcctDet;
import com.icsys.batch.task.checkaccount.proxy.bean.TCpsChkerrmsg;
import com.icsys.batch.task.checkaccount.proxy.dao.CheckAccountDao;
import com.icsys.batch.util.BatchDateUtil;

/**
 * 生成对账成功、失败结果文件
 * 银联：提供成功文件给支付平台进行对账，失败文件进行调账
 * 本行：提供失败文件进行调账
 * @author liuyb
 *
 */
public class CreateResultFileStep implements Step {
	
	private static Logger LOG = Logger.getLogger(CreateResultFileStep.class);

	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		// TODO Auto-generated method stub
		String checkDate =(String)context.getAttribute("check_date");
		String channelId = (String)context.getAttribute("channel_id");
		String fileName = (String)context.getAttribute(CheckAccountConstants.FILE_NAME);
		boolean isCups = (Boolean)context.getAttribute("is_cups");
		
		String listods = (String)context.getAttribute("odslist");
		String listcups = (String)context.getAttribute("cupslist");
		String resultPath1 = (String) context.getAttribute(CheckAccountConstants.FILE_RESULT_CUPS_PATH);
		resultPath1 = resultPath1.endsWith(File.separator)?resultPath1:resultPath1 + File.separator;
		String resultPath2 = (String) context.getAttribute(CheckAccountConstants.FILE_RESULT_LOCAL_PATH);
		resultPath2 = resultPath2.endsWith(File.separator)?resultPath2:resultPath2 + File.separator;
		
		CheckAccountDao dao = new CheckAccountDao();
		try{
//			String lastDate = BatchDateUtil.getLastDateWithStringFormat(tranDate);
			List<String> res = new ArrayList<String>();
			File tempFile = null;
			if(isCups){
				String lastDate = BatchDateUtil.getLastDateWithStringFormat(context.getJobDate());
				fileName = new File(fileName).getName() + "_SUCC";
				String afterDate = BatchDateUtil.getAfterDateWithStringFormat(context.getJobDate().substring(0,8-checkDate.length()) + checkDate);
				List<GrCheckAcctDet> succDetail = dao.queryResultDetail(channelId, checkDate,afterDate.substring(8-checkDate.length()));
				File resSuccFile = new File(resultPath1 + lastDate + "/900",fileName);
				FileUtils.writeLines(resSuccFile, "GBK", succDetail);
				res.add(resultPath1 + lastDate + "/900 . " + resSuccFile.getName() + " " + resSuccFile.length());
				FileUtils.writeStringToFile(tempFile = new File(resultPath1 + lastDate + "/900",resSuccFile.getName() + ".chk"), 
						resSuccFile.getName() + " " + resSuccFile.length() + "\n","GBK");
				res.add(resultPath1 + lastDate + "/900 . " + tempFile.getName() + " " + tempFile.length());
								
				fileName = fileName.replace("_SUCC", "_FAIL");
				succDetail = dao.queryResultErrDetail(channelId, checkDate,afterDate.substring(8-checkDate.length()));
				resSuccFile = new File(resultPath1 + lastDate + "/900",fileName);
				FileUtils.writeLines(resSuccFile, "GBK", succDetail);
				res.add(resultPath1 + lastDate + "/900 . " + resSuccFile.getName() + " " + resSuccFile.length());
				FileUtils.writeStringToFile(tempFile = new File(resultPath1 + lastDate + "/900",resSuccFile.getName() + ".chk"), 
						resSuccFile.getName() + " " + resSuccFile.length() + "\n","GBK");
				res.add(resultPath1 + lastDate + "/900 . " + tempFile.getName() + " " + tempFile.length());
				/*FTS传输清单文件*/
				FileUtils.writeLines(new File(listcups,"ica_to900_cups_" + checkDate + ".list"),"GBK", res);
				res.clear();

				List<TCpsChkerrmsg> errDetail = dao.queryReportDetail(checkDate,context.getJobDate());
				File resErrFile = new File(resultPath2 + lastDate + "/900","T_CPS_CHKERRMSG.del");
				FileUtils.writeLines(resErrFile, "GBK", errDetail);
				res.add(resultPath2 + " " + lastDate + "/900 " + resErrFile.getName() + " " + resErrFile.length());
				FileUtils.writeStringToFile(tempFile = new File(resultPath2 + lastDate + "/900","indicate.txt"), 
						resErrFile.getName() + " " + resErrFile.length() + "\n","GBK");
				res.add(resultPath2 + " " + lastDate + "/900 " + tempFile.getName() + " " + tempFile.length());
				
				/*FTS传输清单文件*/
				FileUtils.writeLines(new File(listods,"ica_to900_ods_" + lastDate + ".list"), res);
			}else{
				fileName = "GR_LOCAL_CHECKACCT.del";
				List<String> branchs = dao.groupByTranBranch();
				for(String temp:branchs){
					List<IcCheckAccountErrList> errList = dao.getCheckAccErrList(isCups, channelId, checkDate,temp,context.getJobDate());
					File resFile = new File(resultPath2 + checkDate + File.separator + temp,fileName);
					FileUtils.writeLines(resFile, "GBK", errList);
					res.add(resultPath2 + " " + checkDate + File.separator + temp + " " + resFile.getName() + " " + resFile.length());
					FileUtils.writeStringToFile(tempFile = new File(resultPath2 + checkDate + File.separator + temp,"indicate.txt"),
							fileName + " " + resFile.length() + "\n","GBK");
					res.add(resultPath2 + " " + checkDate + File.separator + temp + " " + tempFile.getName() + " " + tempFile.length());
				}			
				/*FTS传输清单文件*/
				FileUtils.writeLines(new File(listods,"ica_tocheckerr_ods_" + checkDate + ".list"), res);	
			}
			LOG.info("生成对账结果文件结束!");
		}catch(Exception e){
			LOG.error("生成对账结果文件失败，任务挂起！" + e.getMessage());
			throw new JobException("生成对账结果文件失败，任务挂起！" + e.getMessage());
		}
	}
}
