package com.icys.batch.test.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.SystemParamValue;

/**
 * @author admin
 * <br/> ���������ļ�̽��
 */
public class TaskSenor {

	/**
	 * </br> 01-ƽ̨��������
	 * @param sysdate ϵͳʱ��
	 * @param workfilePath �����ļ�·��
	 * @return 
	 */
	public static String dayEndSensor (String sysdate, String workfilePath ){

		String noticPath = getValue("core_notice_path",workfilePath);
		try{
			File notic = new File(noticPath,sysdate);
			notic = new File(notic,"ISDADCF.FCRECDT.PS");
			if(notic.exists()){				
				return "���ֺ��� T1�����ļ��������С�";
			}
			return "δ�����ļ�ISDADCF.FCRECDT.PS���ܽ������С�";
		}catch (Exception e) {
			return "00-ƽ̨�������� �ļ�̽���쳣";
		}

	}

	/**
	 * </br> 02-���м���������
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String checkAccountSensor (String sysdate, String workfilePath ){
		String corePath = getValue("core_file_path",workfilePath);
		String localPath = getValue("local_file_path",workfilePath);
		if (null == corePath || "".equals(corePath)) {
			return "���Ķ����ļ����·��ֵΪNULL";
		}
		if (null == localPath || "".equals(localPath)) {
			return "���ض����ļ����·��ֵΪNULL";
		}

		List<File> checkFileList = new ArrayList<File>();
		try {
			File coreFile = new File(corePath);
			File filePath = new File(localPath,sysdate);
			if(!filePath.exists()){
				filePath.mkdir();
			}
			final String lastDate = BatchDateUtil.getLastDateWithStringFormat(sysdate);
			File[] chkFile = coreFile.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(lastDate.substring(4) + ".chk");
				}
			});
			for (File temp : chkFile) {
				List<?> records = FileUtils.readLines(temp, "GBK");
				for (Object o : records) {
					if ("".equals(o.toString().trim())) {
						continue;
					}
					String[] split = o.toString().trim().split(" ");
					File tempCheck = new File(corePath, split[0]);
					/*��֤�����ļ�*/
					if (tempCheck.exists() && Long.valueOf(split[1]) == tempCheck.length()) {
						checkFileList.add(tempCheck);
					}
				}
			}
			if (checkFileList.size() < 1) {
				return "û��̽�⵽��Ҫ����Ķ����ļ�!";
			} else {
				return "���ҵ��ˡ�" + checkFileList.size()+ "�����ļ�.�����ļ�̽�����...";
			}
		} catch (Exception e) {
			return "02-���м����������ļ�̽���쳣";
		}
	}



	/**
	 * </br>03-�ѻ�����
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String offlineTranSensor (String sysdate, String workfilePath ){

		/*
		 * ���FTS�����ļ�������
		 * ɽ��ũ��ֻ�����������ļ�����ʱ�����Ǳ��������ļ�
		 */
		boolean isExist = false;
		List<File> cpList = new ArrayList<File>();
		Map<String,String> destFile = new HashMap<String,String>();


		String path = getValue("offlineTran_offlinePath",workfilePath);
		String localPath =getValue("offlineTran_UnionpayPath",workfilePath);
		localPath = localPath.endsWith(File.separator)?localPath:localPath + File.separator;
		String date = null;
		try{
			// 0004 ��������
			date  = DataBaseUtil.sysPrmValue("000004");

			System.out.println(localPath);

			String lastDate = BatchDateUtil.getLastDateWithStringFormat(date);
			File[] dirList = new File(path,lastDate).listFiles(new FileFilter(){
				public boolean accept(File pathName){
					return pathName.isDirectory();/*��������*/
				}
			});
			if(null == dirList){
				return "����������[" + lastDate + "]Ŀ¼��";
			}
			if(dirList.length != 17){
				return "��������Ŀ¼����[" + dirList.length + "]��Ŀ����ȷ��";
			}
			for(File dir:dirList){
				File[] fileList = dir.listFiles(new FileFilter(){
					public boolean accept(File pathName){
						return pathName.getName().endsWith(".chk");
					}
				});
				if(fileList.length != 2){
					return "��������[" + dir.getName() + "]�ļ���Ŀ����ȷ��";
				}
			}
			if(!new File(localPath + date).exists()){
				new File(localPath + date).mkdir();
			}
			for(File dir : dirList){
				File[] fileList = dir.listFiles(new FileFilter(){
					public boolean accept(File pathName){
						return pathName.getName().endsWith(".chk");
					}
				});
				for(File file:fileList){
					List records = FileUtils.readLines(file);
					for(Object o:records){
						String line = o.toString().trim();
						if("".equals(line)){
							continue;
						}
						File re = new File(file.getParent(),line.substring(0,line.indexOf(" ")));
						isExist = re.exists();
						if(isExist && Long.parseLong(line.substring(line.lastIndexOf(" ") + 1).trim()) == re.length()){
							if(re.length()%526 == 95 && re.length()/526 > 0){
								cpList.add(re);
							}
						}else{
							return "��������[" + dir.getName() + "]�ļ��쳣��";
							//isExist = false;
							//break;
						}
					}
					if(isExist){
						for(File result:cpList){
							File toFile = new File(localPath + date + File.separator + result.getParentFile().getName(),result.getName());
							if(!toFile.getParentFile().exists()){
								toFile.getParentFile().mkdirs();
							}
							FileUtils.copyFile(result, toFile);
							FileUtils.touch(new File(toFile.getParent(),toFile.getName() + ".ok"));
							String org = destFile.get(result.getParentFile().getName());
							if(null != org){
								org = org + "|" + toFile.getAbsolutePath();
							}else{
								org = toFile.getAbsolutePath();
							}
							destFile.put(result.getParentFile().getName(),org);
							result.delete();
						}
						file.delete();
					}
					cpList.clear();
					isExist = false;
				}
			}
		}catch(Exception e){
			return "ִ��̽�������쳣��ԭ��:" + e.getMessage();
		}
		Iterator<Map.Entry<String, String>> dataFileName = destFile.entrySet().iterator();
		// �ж������õ�
		List<String>ListJop = new ArrayList<String>(); 
		while (dataFileName.hasNext()) {
			Map.Entry<String, String> entry = dataFileName.next();
			String[] split = entry.getValue().split("\\|");
			for(String fn:split){
				ListJop.add(fn);
			}
		}
		if (ListJop.size() < 1) {
			return "û���ҵ����Դ���ı����ѻ������ļ�,̽�����.";
		} else{
			return "���ҵ��ˡ�" + ListJop.size()+ "�����ļ�.�ѻ������ļ�̽�����...";
		}
	}


	/**
	 * </br>11-�����ѻ�����
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String offlineResultSensor (String sysdate, String workfilePath){

		String localPath = getValue("UnionpayPath",workfilePath);
		localPath = localPath.endsWith(File.separator)?localPath:localPath + File.separator;
		try{
			// 0004 ��������
			String date  = DataBaseUtil.sysPrmValue("000004");
			File[] list = new File(localPath + date).listFiles(new FileFilter() {

				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});
			if(null == list){
				return "δִ��������[" + date + "]��������";
			}
			boolean isOK = false;
			for(File file:list){
				File[] ok = file.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname.getName().endsWith(".ok");
					}
				});
				if(ok.length > 0){
					isOK = true;
					break;
				}
			}
			if(!isOK){
				return "�ѻ��������������������,�������ļ�����";

			}
			return "�ѻ���������������δ���...";
		}catch (Exception e) {
			e.printStackTrace();
			return "11-�����ѻ����� �ļ�̽������쳣...";
		}	
	}


	/**
	 * </br>08-ת����������
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String profitDataSensor (String sysdate, String workfilePath){



		String minLimit = null;
		String date = null;
		String last_date = null;
		try {
			minLimit  = DataBaseUtil.sysPrmValue("100001");// �˻������Ͷ��
			date = SystemParamValue.getSystemDate();
			last_date = BatchDateUtil.getLastDateWithStringFormat(date);

		} catch (Exception e) {
			return "��ȡϵͳ�����쳣";
		}
		final String lastDate = last_date;
		BigDecimal limit = new BigDecimal((minLimit == null || ""
				.equals(minLimit)) ? "0" : minLimit);
		if (limit.compareTo(BigDecimal.ZERO) <= 0) {
			return  "�˻������Ͷ��Ϊ0��null��ִ��";
		}

		String filePath = getValue("profit_filePath",workfilePath);
		String localPath =getValue("profit_localPath",workfilePath);
		List<File> fileList = new ArrayList<File>();

		try {
			if (filePath != null) {

				File[] files = new File(filePath).listFiles(new FileFilter() {

					public boolean accept(File pathname) {
						return "FCRD854R".equals(pathname.getName()
								.substring(8, 16))
								&& pathname.getName().endsWith(lastDate + ".CHK");
					}

				});
				if(files.length != 17){
					return "�ļ���������ȷ��";
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
				 * ����ж��ļ���34��������ж�.CHK�ļ����м�飬����context����ִ��
				 */
				// List<File> fileList =
				// CheckFileUtil.getCheckSuccessFiles(filePackage,endOfCheckFile,histroyFile);
				// }else{
				// LOG.debug("�õ����ݸ���ֻ��"+files.length+"��,������34����");
				// return new JobContext[]{};
				// }
			} else {
				return "�ļ�·�������ڣ���������Ϊ��";

			}
			if (fileList.size() > 0 && null != fileList) {// ��������д�����ļ�
				List<String> listJop = new ArrayList<String>();
				for (File f : fileList) {
					if( f.length() > 0){
						listJop.add(f.getName());
					}
				}
				//  TODO ��Ҫ��ϸ�ж�
				if (listJop.size()>0){
					return "true���ҵ��ˡ�" + listJop.size()+ "�����ļ�.";
				} else {
					return "����û����Ҫ������ļ�";
				}

			} else {
				return "����û����Ҫ������ļ�";
			}
		} catch (Exception e) {
			return "08-ת���������� �ļ�̽������쳣";
		}
	}

	/**
	 * </br>09-˯���˻����ս������
	 * @param sysdate
	 * @param workfilePath
	 * @return
	 */
	public static String sleepAcctManageDataSensor (String sysdate, String workfilePath){

		String minLimit = null;
		String cycle = null;
		String date = null;
		String last_date = null;
		try {
			minLimit = DataBaseUtil.sysPrmValue("100001");// �˻������Ͷ��
			cycle = DataBaseUtil.sysPrmValue("100002");// ��ҵ����ʱ��
			// ϵͳʱ�� 0003
			date = sysdate;
			last_date = BatchDateUtil.getLastDateWithStringFormat(date);
		} catch (Exception e) {
			return "��ȡϵͳ�����쳣";
		}
		final String lastDate = last_date;
		int iCycle = Integer.valueOf(cycle);
		BigDecimal limit = new BigDecimal((minLimit == null || ""
				.equals(minLimit)) ? "0" : minLimit);

		if (limit.compareTo(BigDecimal.ZERO) == 0) {
			return  "�˻������Ͷ��Ϊ0��null��ִ��";
		}

		// String endOfCheckFile = ".CHK";//����ļ���β��ʶ
		String filePath = getValue("sleep_filePath",workfilePath);
		String localPath =getValue("sleep_localPath",workfilePath);
		List<File> fileList = new ArrayList<File>();
		try {
			if (filePath != null) {
				File[] files = new File(filePath).listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return "FCR2201E".equals(pathname.getName().substring(8, 16)) && pathname.getName().endsWith(lastDate + ".CHK");
					}

				});
				if(files.length != 17){
					return "�ļ���������ȷ��";
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
			} else {
				return "�ļ�·�������ڣ���������Ϊ��";
			}
			if (fileList.size() > 0 && null != fileList) {// ��������д�����ļ�
				List<String> listJop = new ArrayList<String>();
				for (File f : fileList) {
					if(f.length() > 0){
						listJop.add(f.getName());
					}
				}
				//  TODO ��Ҫ��ϸ�ж�
				if (listJop.size()>0){
					return "true���ҵ��ˡ�" + listJop.size()+ "�����ļ�.";
				} else {
					return "����û����Ҫ������ļ�";
				}
			} else {
				return "����û����Ҫ������ļ�";

			}
		} catch (Exception e) {
			return "09-˯���˻����ս������ �ļ�̽���쳣";
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


}
