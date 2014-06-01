package com.icsys.batch.task.offline.stage;

import java.io.File;

import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Stage;
import com.icsys.batch.offline.bean.GrAreacodeInfo;
import com.icsys.batch.offline.dao.GrAreacodeInfoDao;
//import com.icsys.batch.util.BufferedRandomAccessFile;
import com.icsys.batch.util.Constants;
import com.icsys.batch.util.SystemParamValue;

/**
 * 
 * @author liuyb
 *
 */
public class OfflineStage implements Stage {
	
	private static final Logger LOG = Logger.getLogger(OfflineStage.class);
	
	public void prepare(JobContext context) throws JobException {
		if(LOG.isInfoEnabled()){
			LOG.info("进入脱机消费处理的阶段...");
		}
		String dataFileName = (String) context.getAttribute("dataFileName");
		if(null == dataFileName){
			throw new JobException("要处理的文件名为:[null],任务挂起!");
		}
		
		String date = (String)context.getAttribute("jobDate");

		try{
			if(null == date){
				date = SystemParamValue.getClearDate();
			}
			context.setJobDate(date);
			String batchNo = SystemParamValue.getBatchNo();
			context.setJobNo(batchNo);//将批次号放入到上下文中
			context.setAttribute("tranMethod", Constants.TRAN_METHOD_OFFLINE);
			String fileSrc = "2";//文件来源（银联/本行）
			if(LOG.isInfoEnabled()){
				LOG.info("要处理的文件名为:" + dataFileName);
			}
			//根据文件名来判断文件来自本行还是银联
			if(dataFileName.contains(Constants.LocalBank)){
				fileSrc = Constants.CLEARING_SELF;//文件来自本行
			}else if(dataFileName.contains(Constants.Unionpay)){
				fileSrc = Constants.CLEARING_UNIONPAY;//文件来自银联
			}
			context.setAttribute("fileSrc", fileSrc);//将文件来源放入任务上下文
//			BufferedRandomAccessFile braf =  new BufferedRandomAccessFile();
//			boolean isAvailable = braf.validate(dataFileName, 95);
			if(/*!isAvailable || */(new File(dataFileName)).length()%526 != 95 || (new File(dataFileName)).length()/526 < 1){
				if(LOG.isInfoEnabled()){
					LOG.info("据文件的长度判断文件的有效性为:无效!");
				}
				throw new JobException("文件无效!");
			}
//			braf.setSrcFilePath(dataFileName);
//			String  head = braf.getHead();
//			String tail = braf.getTail();
//			context.setAttribute("head", head);//将脱机交易文件的头信息放入上下文中
//			context.setAttribute("tail", tail);//将脱机交易文件的尾信息放入上下文

			String isAccount = "0";
			try {
				isAccount = SystemParamValue.getIcSysPara("000010");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("获取是否转账参数异常." + e.getMessage());
			}
			context.setAttribute("isAccount", isAccount);
			String billOrg = (String)context.getAttribute("billOrg");
			GrAreacodeInfo area = new GrAreacodeInfoDao().getCoreArea(billOrg);
			if(null == area || null == area.getCoreArea() || "".equals(area.getCoreArea())){
				throw new Exception("获取地区简码失败！入网机构号：" + billOrg);
			}
			context.setAttribute("cityCode", area.getCoreArea());
			context.setAttribute("tranCode", "300");		
			if(LOG.isInfoEnabled()){
				LOG.info("完成脱机消费阶段处理.");
			}
		}catch(Exception e){
			throw new JobException("阶段处理异常,任务挂起!原因:" + e.getMessage());
		}
	}
}
