package com.icsys.batch.util;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class TFTClient {
	
	
	public TFTClient(String remoteHost,int remotePort){
		this.remoteHost  = remoteHost;
		this.remotePort = remotePort;
	}
	public TFTClient(){
	}
	
	private static Logger LOG = Logger.getLogger(TFTClient.class);
	
//	private static String TFTCLIENT_APPLICATION_NAME = "cmdtftclient.exe";
	private static String OPERATION_TYPE_UPLOAD = "up";
	private static String OPERATION_TYPE_DOWNLOAD = "down";
	
	private static String SPLIT = " ";
	
	private static String CHARSET = "GBK";
	
	private static String SUCCESS_RESPONSE_FIELD = "Transfer Success!";
	
	private String remoteHost = "10.200.68.13";
	private int remotePort = 7776;
	private String tftClientCmdPath = "D:\\项目文档\\光大银行\\0610\\tft\\tftclient\\tftclient\\cmdtftclient.exe";
	
	
	public String getRemoteHost() {
		return remoteHost;
	}
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	public int getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
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
		String commonCmd = getCommonCmd(OPERATION_TYPE_UPLOAD);
		String cmd = commonCmd + SPLIT + remote + SPLIT + local;
		try {
			result = exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
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
		String commonCmd = getCommonCmd(OPERATION_TYPE_DOWNLOAD);
		String cmd = commonCmd + SPLIT + remote + SPLIT + local;
		LOG.debug("CMD IS:["+cmd+"]");
		try {
			result = exec(cmd);
			if(result)
				LOG.info("文件已经成功下载到:["+local+"]");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(),e);
		}
		return result;
	}
	
	private String getCommonCmd(String operationType){
		String cmdPath = getTftClientCmdPath();
		String host = getRemoteHost();
		String port = String.valueOf(getRemotePort());
		String cmd = cmdPath  + SPLIT + operationType + SPLIT + host + SPLIT + port + SPLIT;
		return cmd;
	}
	
	private boolean exec(String cmd) throws Exception{
		boolean result = false;
		System.out.println("command is:"+cmd);
		Process process = Runtime.getRuntime().exec(cmd);
		process.waitFor();
		
		InputStream in = null;
		try{
			in = process.getInputStream();
			byte[] rcvBuf = new byte[SUCCESS_RESPONSE_FIELD.getBytes(CHARSET).length];
			in.read(rcvBuf);
			if(new String(rcvBuf,CHARSET).equals(SUCCESS_RESPONSE_FIELD)){
				result = true;
			}
		}finally{
			if(null != in)
				in.close();
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		TFTClient client = new TFTClient();
		client.downloadFile("CEBCARD_20110704.zip", "D:\\项目文档\\光大银行\\0610\\tft\\download\\CEBCARD_20110704.zip");
	}
}


