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
		// ���ݵ���������
		String taskName ="offlineTranResult";


		// ϵͳ����
		String sysdate = DataBaseUtil.sysPrmValue("000003");
		// �����ļ� workParam.properties
		String workfilePath = "conf/workParam.properties";
		//
		//		System.out.println(date);
		//		System.out.println(getValue("sleep_localPath" ,filePath ));

		// �жϵ�ǰ�����Ƿ���Ҫ�ļ�̽��
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
	 * @param filePath �����ļ�·��
	 * @return
	 */
	public static String getValue (String key, String filePath){

		InputStream in = null;
		Properties p = null;
		// �������ļ���ȡ����valueֵ
		String strValue = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			p = new Properties();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// ������Դ
		try {
			p.load(in);
			strValue = (String) p.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strValue;
	}


	/**
	 * @param taskName ��������
	 * @return true����false
	 * <br> �жϵ�ǰ�����Ƿ���Ҫ�ļ�̽��
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
