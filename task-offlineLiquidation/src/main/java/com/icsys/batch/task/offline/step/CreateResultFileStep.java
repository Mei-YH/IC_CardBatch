package com.icsys.batch.task.offline.step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.offline.dao.GrAreacodeInfoDao;
import com.icsys.batch.offline.dao.IcOfflineChkTaskDao;
import com.icsys.batch.task.offline.impl.FileServiceImpl;

/**
 * 出一个差错文件（脱机和联机的）
 * 差错文件的格式为：
 * @author liuyb
 * 
 */
public class CreateResultFileStep implements Step {
	private static final Logger LOG = Logger.getLogger(CreateResultFileStep.class);
//	IcOfflineChkDetErrorDao dao = new IcOfflineChkDetErrorDao();

	public long estimate(JobContext context) throws JobException {

		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		LOG.info("生成脱机清算结果文件开始...");
		String clearDate = context.getJobDate();
		String resultFilePath = (String) context.getAttribute("resultFilePath");// 要生成结果文件的路径
		String listPath = (String)context.getAttribute("listpath");
		
		resultFilePath = resultFilePath.endsWith(File.separator)?resultFilePath:resultFilePath + File.separator;
		FileServiceImpl fileServiceImpl = new FileServiceImpl();
		GrAreacodeInfoDao areaDao = new GrAreacodeInfoDao();
		List<String> areas = areaDao.queryCityCode();
		try{
			List<String> res = new ArrayList<String>();
			for(String cityCode : areas){
				int sn = Integer.valueOf(new IcOfflineChkTaskDao().querySumByCityCode(clearDate,cityCode));
				String billOrg = areaDao.queryBillOrg(cityCode).getCupsBranchCode();
				fileServiceImpl.makeResultFile(clearDate, billOrg, resultFilePath,cityCode,sn,res);
			}
			FileUtils.writeLines(new File(listPath,"ica_tooffline_cbod_" + clearDate + ".list"), res);
			LOG.info("生成脱机清算结果文件成功！");
		}catch(Exception e){
			LOG.error("生成结果文件失败，" + e.getMessage());
			throw new JobException("生成结果文件失败，任务挂起，原因：" + e.getMessage());
		}
	}
}
