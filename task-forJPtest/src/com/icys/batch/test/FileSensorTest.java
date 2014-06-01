package com.icys.batch.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.icys.batch.test.util.DataBaseUtil;
import com.icys.batch.test.util.TaskSenor;

public class FileSensorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 传递的任务名称
		String taskName ="offlineTranResult";


		// 系统日期
		String sysdate = DataBaseUtil.sysPrmValue("000003");
		// 属性文件 workParam.properties
		String workfilePath = "conf/workParam.properties";
		//
		//		System.out.println(date);
		//		System.out.println(getValue("sleep_localPath" ,filePath ));

		// 判断当前任务是否需要文件探测
		if (taskJudge(taskName)) {
			if (taskName.equals("daySwitch")){
				String returnInfo = TaskSenor.dayEndSensor(sysdate, workfilePath);
				System.out.println(returnInfo);
			} else if (taskName.equals("checkAccount")) {
				String returnInfo = TaskSenor.checkAccountSensor(sysdate, workfilePath);
				System.out.println(returnInfo);
			} else if (taskName.equals("offlineTran")) {
				String returnInfo = TaskSenor.offlineTranSensor(sysdate, workfilePath);
				System.out.println(returnInfo);
			} else if (taskName.equals("offlineTranResult")) {
				String returnInfo = TaskSenor.offlineResultSensor(sysdate, workfilePath);
				System.out.println(returnInfo);
			} else if (taskName.equals("profitData")) {
				String returnInfo = TaskSenor.profitDataSensor(sysdate, workfilePath);
				System.out.println(returnInfo);
			} else if (taskName.equals("sleepAcctManageData")) {
				String returnInfo = TaskSenor.sleepAcctManageDataSensor(sysdate, workfilePath);
				System.out.println(returnInfo);
			}
		}
	}

	/**
	 * @param key 
	 * @param filePath 属性文件路径
	 * @return
	 */
	public static String getValue (String key, String filePath){

		InputStream in = null;
		Properties p = null;
		// 从属性文件里取出的value值
		String strValue = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			p = new Properties();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 加载资源
		try {
			p.load(in);
			strValue = (String) p.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strValue;
	}


	/**
	 * @param taskName 任务名称
	 * @return true或者false
	 * <br> 判断当前任务是否需要文件探测
	 */
	public static boolean taskJudge (String taskName){


		List<String> taskSensor =new ArrayList<String>();
		taskSensor.add("daySwitch");
		taskSensor.add("checkAccount");
		taskSensor.add("offlineTran");
		taskSensor.add("offlineTranResult");
		taskSensor.add("profitData");
		taskSensor.add("sleepAcctManageData");

		boolean flg = false;
		if (taskSensor.contains(taskName)){
			flg =true;
		}
		return flg;
	}


}
