package com.icys.batch.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * 
 * @author liuyb
 * @date 2014-05-14
 *
 */
public class JPStarter{

	public static void main(String[] args) {
		if(args.length > 0 && args[0].equals("--help")){
			System.out.println("Usage:java [-Dflag={0:sensor,1:start,2:continue,3:search}] [-DoutFile=结果文件] [-DtaskName=任务名称] [-DjmxUrl=JMX地址] [-DtaskId=任务ID] [-DsearchDate=查询日期]");
			System.exit(1);
		}
		String res = "";
		File file = null;
		try {
			String strFlag = System.getProperty("flag");
			if(null == strFlag){
				throw new Exception("flag is not null");
			}
			String strFile = System.getProperty("outFile");
			if(null == strFile){
				throw new Exception("outFile is not null");
			}
			String jmxUrl = System.getProperty("jmxUrl");
			if(null == jmxUrl){
				throw new Exception("jmxUrl is not null");
			}
			String taskName = System.getProperty("taskName");
			if(null == taskName){
				throw new Exception("taskName is not null");
			}
			int flag = Integer.parseInt(strFlag);
			file = new File(strFile);
			res = "taskName  " + JPTaskInfo.fillBlank(taskName,30," ",true);
			JPTaskInfo.retryConnect(jmxUrl);
			if (flag == 0) {
				JPTaskInfo.forceDetect(taskName);
				res += " 0\n";
			} else if (flag == 1) {
				JPTaskInfo.executeTask(0, taskName, null);
				res += " 0\n";
			} else if (flag == 2) {
				String taskId = System.getProperty("taskId");
				if(null == taskId){
					throw new Exception("taskId is not null");
				}
				JPTaskInfo.executeTask(1, taskName, taskId);
				res += " 0\n";
			} else if (flag == 3) {
				String date = System.getProperty("searchDate");
				res = JPTaskInfo.searchTask(taskName, date);
			} else {
				res += " 1 falg is error\n";
			}
		} catch (Exception e) {
			res += " 1 " + e.getMessage() + "\n";
		} finally {
			writeFile(file, res);
			JPTaskInfo.close();
		}
	}

	private static void writeFile(File file, String str) {
		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file),"gb2312"));
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println("写入结果文件[" + file.getAbsolutePath() + "]失败:" + e.getMessage());
		}
	}
}
