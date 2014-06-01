package com.icsys.batch.task.report.step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.report.proxy.dao.ReportsDataDao;

public class CreateReportsDataStep implements Step {
	
	private Logger LOG = Logger.getLogger(CreateReportsDataStep.class);

	public long estimate(JobContext arg0) throws JobException {
		// TODO 自动生成方法存根
		return 0;
	}

	public void perform(JobContext context, Executor arg1) throws JobException {
		// TODO 自动生成方法存根
		LOG.info("批量报表步骤开始...");
		String sysdate = (String)context.getAttribute("sysDate");
		String sendpath = (String)context.getAttribute("sendpath");
		String listpath = (String)context.getAttribute("listpath");
		File file = new File(sendpath,sysdate);
		if(!file.exists()){
			file.mkdir();
		}
		File listFile = new File(listpath);
		if(!listFile.exists()){
			listFile.mkdir();
		}
		ReportsDataDao dao = new ReportsDataDao();
		try{
			LOG.info("查询数据生成文件开始...");
			List<String> datalist = new ArrayList<String>();
			List<String> res = new ArrayList<String>();
			List<String> cityCodes = dao.queryCityCode();
			File temp = null;
			for(String cityCode:cityCodes){
				writeFile(dao.queryVMJYLData(sysdate,cityCode), file,"9" + cityCode,"GR_ICJYLTJ_BB.del",res,datalist);
				
				writeFile(dao.queryVMKLData(sysdate,cityCode), file,"9" + cityCode,"GR_ICKLTJ_BB.del",res,datalist);
				
				writeFile(dao.queryVMZHJYData(sysdate,cityCode), file,"9" + cityCode,"GR_ICZHJYTJ_BB.del",res,datalist);
				
//				writeFile(dao.queryCheckAcctData(sysdate,cityCode), file,"9" + cityCode,"GR_LOCAL_CHECKACCT.del",res,datalist);
				
				writeFile(dao.querySleepDetailData(sysdate,cityCode), file,"9" + cityCode,"GR_SLEEP_ACCT_DETAIL.del",res,datalist);
				
				writeFile(dao.queryICGQZSYData(sysdate,cityCode), file,"9" + cityCode,"GR_ICGQZSY_DET.del",res,datalist);
				
				FileUtils.writeLines(temp = new File(file + File.separator + "9" + cityCode,"indicate.txt"),"GBK",datalist,true);
				
				res.add(sendpath + " " + sysdate + "/9" + cityCode + " indicate.txt " + temp.length());			
				
				datalist.clear();
			}
			FileUtils.writeLines(new File(listFile,"ica_toreport_ods_" + sysdate + ".list"),res);
			LOG.info("查询数据生成文件结束.");
		}catch (Exception e) {
			LOG.error("生成del文件失败!任务挂起!" + e.getMessage());
			throw new JobException("生成del文件失败!任务挂起!" + e.getMessage());
		}
		LOG.info("批量报表步骤结束.");
	}

	private void writeFile(List<?> data,File file,String dir,String name,List<String> res,List<String> list)throws Exception{
		String re = file.getParent() + " " + file.getName() + File.separator + dir + " " + name;
		file = new File(file + File.separator + dir,name);
		FileUtils.writeLines(file,"GBK", data);
		res.add(re + " " + file.length());
		list.add(name + " " + file.length());
	}
}
