package com.icsys.batch.task.sleepAcctManageData.proxy.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 传来的文件对应的都有两个，一个是主文件，一个是主文件.chk的检查文件
 * 
 * @author SDNX
 *
 */
public class CheckFileUtil {

	private static final Logger LOG = Logger.getLogger(CheckFileUtil.class);
	/**
	 * 返回检查通过的文件
	 * @param file(装载传输来文件的文件夹) 
	 * @return
	 */
	public static List<File> getCheckSuccessFiles(File filePackage,String endOfCheckFile,String profitFormHistroy) {
		File[] files = filePackage.listFiles();
		List<File> returnList = new ArrayList<File>();
		LOG.debug("文件夹大小："+files.length);
		if(files.length>0&&null!=files){
			for(File file1 : files){
				if(file1.getName().endsWith(endOfCheckFile)){//一次找出以.chk结尾的
					String fileName = file1.getAbsolutePath().substring(0,file1.getAbsolutePath().length()-endOfCheckFile.length());
					LOG.debug("文件名："+fileName);
					File file2 = new File(fileName);
					if(file2.exists()){
						BufferedReader br = null;
						String line = null;
						try {
							br = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
							line = br.readLine();//读取检查文件中的数据和主文件进行检查
							if(null!=line&&line.length()>file2.getName().length()){
								String fileNameInCheckFile = line.substring(0, (int)file2.getName().length());
								String fileSize = line.substring((int)file2.getName().length()+1);
								if(fileNameInCheckFile.equals(file2.getName())&&fileSize.equals(file2.length()+"")) {
									if(checkAdressOfFileName(file2)){//进一步检查文件名字是否符合要求
										returnList.add(file2);
									}else{
										LOG.debug("文件："+file2.getAbsolutePath()+"文件名不符合地势标准,不处理");
									};
								}
							}else{
								LOG.debug("文件："+file2.getAbsolutePath()+"检查不通过！");
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							if(null!=br){
								try {
									br.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		/**
		 * 只将文件夹下检查通过需要处理的文件留下，其他的检查文件和检查不通的文件都移入历史文件夹
		 * 再将要处理的文件返回
		 */

		for(File oneFile : files){
			if(!returnList.contains(oneFile)) {
				File historyFile = new File(profitFormHistroy+oneFile.getName());
				historyFile.getParentFile().mkdirs();
				LOG.debug("移入文件名"+historyFile.getAbsolutePath());
				if(historyFile.exists()){
					//如果存在时移不了的，直接删除原来的文件在转移
					historyFile.delete();
				}
				boolean b = oneFile.renameTo(historyFile);
				if(b==true){
					LOG.debug("文件移动成功");
				}else{
					LOG.debug("文件移动失败");
				}
			}
		}
		return returnList;
	}

	private static boolean checkAdressOfFileName(File file){
		boolean flag = false;
		List<String> adressList = new ArrayList<String>();
		String adressOfFileName = file.getName().substring(4,6);
		adressList.add("JN");
		adressList.add("QD");
		adressList.add("ZB");
		adressList.add("ZZ");
		adressList.add("DY");
		adressList.add("YT");
		adressList.add("WF");
		adressList.add("JL");
		adressList.add("TA");
		adressList.add("WH");
		adressList.add("RZ");
		adressList.add("LW");
		adressList.add("BZ");
		adressList.add("DZ");
		adressList.add("LC");
		adressList.add("LY");
		adressList.add("HZ");
		for(String adress : adressList) {
			if(adressOfFileName.equals(adress)){
				flag = true;
			}
		}
		return flag;
	}
}
