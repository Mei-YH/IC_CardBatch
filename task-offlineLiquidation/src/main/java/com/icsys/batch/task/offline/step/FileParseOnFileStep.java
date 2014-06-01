package com.icsys.batch.task.offline.step;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.offline.impl.FileServiceImpl;
//import com.icsys.batch.util.BufferedRandomAccessFile;
import com.icsys.batch.util.Constants;

/**
 * 
 * @author liuyb
 *
 */
public class FileParseOnFileStep implements Step {
	
	private static Logger LOG = Logger.getLogger(FileParseOnFileStep.class);

	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)throws JobException {
		if(LOG.isInfoEnabled()){
			LOG.info("文件解析入库开始...");
		}
		String dataFileName = (String) context.getAttribute("dataFileName");// 获得数据文件
//		BufferedRandomAccessFile braf = new BufferedRandomAccessFile();
		if (dataFileName == null || "".equals(dataFileName.trim())) {
			throw new JobException("文件获取失败,请检查文件目录.");// 文件获取失败
		}
		String batchNo = context.getJobNo().trim();
		String clearDate = context.getJobDate().trim();
		String fileSrc = (String) context.getAttribute("fileSrc");
		String billOrg = (String) context.getAttribute("billOrg");
		String cityCode = (String) context.getAttribute("cityCode");
		FileServiceImpl fileService = new FileServiceImpl();// 文件处理类，将文件解析为数据插入数据库表中
		try {
			fileService.saveTask(dataFileName, clearDate, batchNo,Constants.STATUS_EXECUTING,0,cityCode);
			// 以下增加代码为转换dataFileName(本行脱机消费文件)
//			if(95==dataFileName.length()) {
//				if(LOG.isInfoEnabled()){
//					LOG.info("文件["+dataFileName+"]长度为95没有明细数据不再解析.");
//				}
//				return;
//			}
//			boolean isAvailable = braf.validate(dataFileName, 95);
//			if (!isAvailable) {
//				createOKfile(dataFileName);// 文件解析成功生成ok文件
//				fileService.saveTask(dataFileName, clearDate, batchNo,Constants.STATUS_FAILURE);
//				throw new JobException("文件无效!");
//			}
			Integer success = fileService.resoluteFileRemember(batchNo, clearDate,dataFileName, fileSrc,billOrg,context);

			fileService.saveTask(dataFileName, clearDate, batchNo,Constants.STATUS_SUCCESS,success,cityCode);
			if(LOG.isInfoEnabled()){
				LOG.info("文件解析入库成功.");
			}
		} catch (Exception e) {
			try{
				fileService.saveTask(dataFileName, clearDate, batchNo,Constants.STATUS_FAILURE,0,cityCode);
			}catch(Exception ex){
				LOG.error("更新任务失败！");
			}
			throw new JobException("脱机消费文件入库异常,任务挂起.原因:", e.getMessage());
		}
	}

	/*
	private void createOKfile(String dataFileName) {
		File excutingFile = new File(dataFileName + ".executing");// 文件解析完毕，生成成功文件
		if (excutingFile.delete()) {
			File okFile = new File(dataFileName + ".ok");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(okFile);
				fos.write("文件解析成功,文件处理完毕.".getBytes());
				fos.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fos = null;
				}
			}
		}
	}*/
}
