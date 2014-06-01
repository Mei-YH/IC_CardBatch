package com.icsys.batch.util;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class TFTClientOnAix {
	
	
	
	private static Logger LOG = Logger.getLogger(TFTClient.class);
	
	private static String TFTCLIENT_APPLICATION_NAME = "tftclient.aix";
	private static String OPERATION_TYPE_UPLOAD = "-dup";
	private static String OPERATION_TYPE_DOWNLOAD = "-ddown";
	
	private static String SPLIT = " ";
	
	private static String CHARSET = "GBK";
	
	private static String SUCCESS_RESPONSE_FIELD = "File transfer SUCCESS";
	
	private String hostNo = "-h";
	private String tftClientCmdPath = "";
	
	public TFTClientOnAix(String hostNo,String tftClientCmdPath){
		this.hostNo  += hostNo;
		this.tftClientCmdPath = tftClientCmdPath;
	}
	
	public TFTClientOnAix(String hostNo){
		this.hostNo  += hostNo;
	}
	public String getTftClientCmdPath() {
		return tftClientCmdPath;
	}
	public void setTftClientCmdPath(String tftClientCmdPath) {
		this.tftClientCmdPath = tftClientCmdPath;
	}
	
	/**
	 *  上传文件
	 * @param remote
	 * @param local
	 * @return
	 */
	public boolean uploadFile(String remote,String local){
		boolean result = false;
		remote = "-r" + remote;
		String commonCmd = getCommonCmd(OPERATION_TYPE_UPLOAD);
		String cmd = commonCmd + SPLIT + remote + SPLIT + local;
		LOG.info("=====================cmd:【"+cmd+"】======================");
		try {
			result = exec(cmd);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 下载文件
	 * @param remote
	 * @param local
	 * @return
	 */
	public boolean downloadFile(String remote,String local){
		//TODO 添加对于相对路径和绝对路径的判断
		boolean result = false;
		remote = "-r" + remote;
		String commonCmd = getCommonCmd(OPERATION_TYPE_DOWNLOAD);
		String cmd = commonCmd + SPLIT + remote + SPLIT + local;
		LOG.info("CMD IS:["+cmd+"]");
		try {
			result = exec(cmd);
			if(result)
				LOG.info("文件已经成功下载到:["+local+"]");
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return result;
	}
	
	private String getCommonCmd(String operationType){
		String cmd = tftClientCmdPath  + TFTCLIENT_APPLICATION_NAME+ SPLIT + operationType + SPLIT + hostNo + SPLIT ;
		return cmd;
	}
	
	private boolean exec(String cmd) throws Exception{
		boolean result = false;
		Process process = Runtime.getRuntime().exec(cmd);
		process.waitFor();
		InputStream in = null;
		try{
			in = process.getErrorStream();
			byte[] rcvBuf = new byte[1024];
			int len = in.read(rcvBuf);
			LOG.debug("TFT RESP IS:["+new String(rcvBuf,0,len)+"]");
			if(new String(rcvBuf,0,len,CHARSET).contains(SUCCESS_RESPONSE_FIELD)){
				result = true;
			}
		}finally{
			if(null != in)
				in.close();
		}
		
		return result;
	}
	
}
