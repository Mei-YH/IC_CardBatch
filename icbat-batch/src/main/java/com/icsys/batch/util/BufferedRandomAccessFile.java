package com.icsys.batch.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class BufferedRandomAccessFile {
	private  int bufSize = 1024;//缓冲区长度
	private  String srcFilePath="E:/XDMZ_EverBrightBank/test/INF11072951C";//原文件路径
	private  String orgFilePath="E:/XDMZ_EverBrightBank/test/INF11072951C.dat";//切割后的文件路径
	
	/**
	 * 
	 * @param startCharPos 起始字节： 46(基本文件头的长度为46个字节)
	 * @param endCharPos 终止字节： 文件的长度-49(节本文件尾的长度为49个字节)
	 */
	public void copy(int startCharPos,int endCharPos){
		System.err.println("copy start:"+new Date());
		try {
			File srcFile = new File(srcFilePath);//原文件（要切割的文件）
			File orgFile = new File(orgFilePath);//目标文件（切割后的文件）
			int length = (int) srcFile.length();
			endCharPos = length-endCharPos;
			RandomAccessFile raf = new RandomAccessFile(srcFile, "r");
			RandomAccessFile waf = new RandomAccessFile(orgFile, "rw");
			raf.seek(startCharPos);
			while(raf.getFilePointer()<endCharPos){
				int left = (int) (endCharPos-raf.getFilePointer());
				if(left<bufSize){
					bufSize = left;
				}
				byte[] result = new byte[bufSize];
				raf.read(result,0,bufSize);
				waf.write(result);
			}
			raf.close();
			waf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("copy end:"+new Date());
	}
	
	/**
	 * 获取文件头信息
	 * @return
	 */
	public String getHead(){
		String head = "";
		File srcFile = new File(srcFilePath);//原文件
		RandomAccessFile raf  = null;
		try {
			raf = new RandomAccessFile(srcFile, "r");//获取一个文件读入对象
			byte[] result = new byte[46];
			raf.read(result,0,46);//读取46个字节（头信息）
			head = new String(result);//将头信息转换为字符串，返回
			raf.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(raf!=null){
				raf = null;
			}
		}
		return head;
	}
	
	/**
	 * 获取文件尾信息
	 * @return
	 */
	public String getTail(){
		String tail = "";
		File srcFile = new File(srcFilePath);//原文件
		int length = (int) srcFile.length();
		RandomAccessFile raf  = null;
		try {
			raf = new RandomAccessFile(srcFile, "r");//获取一个文件读入对象
			raf.skipBytes(length-49);//跳过尾信息前面的部分
			byte[] result = new byte[49];
			raf.read(result,0,49);//读取49个字节（尾信息）
			tail = new String(result);//将尾信息转换为字符串
			raf.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(raf!=null){
				raf = null;
			}
		}
		return tail;
	}
		
	/**
	 * 根据文件的长度判断文件的有效性
	 * @param srcFile 数据文件的路径
	 * @param length  最小长度
	 * @return 校验结果  false：无效文件  true：有效文件
	 */
	public boolean validate(String srcFilePath ,long length){
		File srcFile = new File(srcFilePath);//原文件 
		long fileLength = srcFile.length();//获取文件长度
		if(fileLength<=length){
			return false;
		}
		return true;
	}
	
	
	public String getSrcFilePath() {
		return srcFilePath;
	}

	public void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}

	public String getOrgFilePath() {
		return orgFilePath;
	}

	public void setOrgFilePath(String orgFilePath) {
		this.orgFilePath = orgFilePath;
	}

	public int getBufSize() {
		return bufSize;
	}

	public void setBufSize(int bufSize) {
		this.bufSize = bufSize;
	}	
}