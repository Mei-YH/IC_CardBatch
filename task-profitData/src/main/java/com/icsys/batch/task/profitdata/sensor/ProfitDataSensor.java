package com.icsys.batch.task.profitdata.sensor;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Sensor;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.SystemParamValue;

public class ProfitDataSensor implements Sensor {
	private Logger LOG = Logger.getLogger(ProfitDataSensor.class);

	public JobContext[] detect(JobContext context) throws JobException {
		// TODO Auto-generated method stub

		String minLimit = null;
		String date = null;
		String last_date = null;
		try {
			minLimit = SystemParamValue.getIcSysPara("100001");// 账户余额最低额度
			date = SystemParamValue.getSystemDate();
			last_date = BatchDateUtil.getLastDateWithStringFormat(date);
			
		} catch (Exception e) {
			LOG.error("获取系统参数异常");
			return new JobContext[] {};
		}
		final String lastDate = last_date;
		BigDecimal limit = new BigDecimal((minLimit == null || ""
				.equals(minLimit)) ? "0" : minLimit);
		context.setAttribute("minLimit", limit);
		if (limit.compareTo(BigDecimal.ZERO) <= 0) {
			return new JobContext[] {};// 账户余额最低额度为0或null则不执行
		}

		// String endOfCheckFile = ".CHK";//检查文件结尾标识
		String filePath = (String) context.getAttribute("filePath");// 获取文件夹
		// String histroyFile =
		// (String)context.getAttribute("histroyFilePath");//操作完成，文件需要移入的历史文件夹
		String localPath = (String) context.getAttribute("localPath");
		List<File> fileList = new ArrayList<File>();

		try {
			if (filePath != null) {

					File[] files = new File(filePath).listFiles(new FileFilter() {

						public boolean accept(File pathname) {
							// TODO 自动生成方法存根
							return "FCRD854R".equals(pathname.getName()
									.substring(8, 16))
									&& pathname.getName().endsWith(lastDate + ".CHK");
						}

					});
					if(files.length != 17){
						LOG.debug("文件个数不正确。");
						return new JobContext[] {};
					}
					for (File temp : files) {
						List<?> records = FileUtils.readLines(temp);
						for (Object o : records) {
							if ("".equals(o.toString().trim())) {
								continue;
							}
							String[] split = o.toString().trim().split(" ");
							File f = new File(temp.getParent(), split[0]);
							if (f.exists()&& Long.valueOf(split[1]) == f.length()) {
								fileList.add(f);
							}
						}
					}
					// if(files.length==34){
					/**
					 * 如果判断文件有34个则进行判断.CHK文件进行检查，返回context进行执行
					 */
					// List<File> fileList =
					// CheckFileUtil.getCheckSuccessFiles(filePackage,endOfCheckFile,histroyFile);
					// }else{
					// LOG.debug("得到数据个数只有"+files.length+"个,必须是34个！");
					// return new JobContext[]{};
					// }
			} else {
				LOG.debug("文件路径不存在！参数不能为空");
				return new JobContext[] {};
			}
			if (fileList.size() > 0 && null != fileList) {// 如果返回有处理的文件
				List<JobContext> jobs = new ArrayList<JobContext>();
				for (File f : fileList) {
					if( f.length() > 0){
						FileUtils.copyFile(f, new File(localPath
								+ File.separator + date, f.getName()));
						JobContext jobContext = new JobContext();
						jobContext.setAttribute("fileName", new File(
								localPath + File.separator + date, f
										.getName()).getAbsolutePath());
						jobs.add(jobContext);
					}
					f.delete();
					new File(f.getParent(), f.getName() + ".CHK")
							.delete();
				}
				LOG.info("共找到了【" + jobs.size()+ "】个文件.");
				return jobs.toArray(new JobContext[jobs.size()]);
			} else {
				LOG.debug("检查后没有需要处理的文件");
				return new JobContext[] {};
			}
		} catch (Exception e) {
			return new JobContext[] {};
		}
	}

}
