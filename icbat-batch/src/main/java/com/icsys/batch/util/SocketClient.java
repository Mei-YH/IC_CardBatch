package com.icsys.batch.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;


public class SocketClient {
	
	private static Logger LOG = Logger.getLogger(SocketClient.class);
	private static String CHARSET = "GBK";
	/**
	 * 报文长度
	 */
	private static int MSG_LEN = 4;
	private static int BUFFER_SIZE = 1024;
	
	
	Socket client = null;
	
	/**
	 * 创建SOCKET
	 * 
	 * @param hostname
	 * @param port
	 * @throws Exception
	 */
	public SocketClient(String hostname,int port) throws Exception{
		LOG.debug("hostname is:["+hostname+"],prot is:["+port+"]");
		client  = new Socket(hostname, port);
		//TODO 设置超时时间
		client.setSoTimeout(60*1000);
//		client.connect(new InetSocketAddress(hostname, port), timeout)
	}
	
	/**
	 * 发送制卡文件请求
	 * 
	 * 没有报文长度
	 * @param date
	 * @throws Exception
	 */
	public byte[] sendFileNotify(String date) throws Exception{
		String sendStr = "0001999"+date;
		OutputStream out = null;
		InputStream in = null;
		try{
			out = client.getOutputStream();
			in  = client.getInputStream();
			LOG.debug("SEND MSG IS:["+sendStr+"]");
			out.write(sendStr.getBytes(CHARSET));
			out.flush();
			
			byte[] respBuf = new byte[4];
			in.read(respBuf);
			LOG.debug("RECV MSG IS:["+new String(respBuf)+"]");
			return respBuf;
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}finally{
			if(null != out)
				out.close();
			if(null != in)
				in.close();
		}
	}
	
	
	/**
	 * 发送请求
	 * 
	 * 发送格式为：
	 * 4个字节的ascii长度 + 实际发送的字节
	 * 
	 * @param sendBuf（实际发送的字节）
	 * @return
	 * @throws Exception
	 */
	public byte[] sendCore(byte[] sendBuf) throws Exception{
		byte[] tempBuf = new byte[BUFFER_SIZE];
		OutputStream out = null;
		InputStream in = null;
		try{
			out = client.getOutputStream();
			in = client.getInputStream();
			//发送总长度，4个自己长度域 　+　实际发送的字节
			ByteBuffer totalBuf = ByteBuffer.allocate(sendBuf.length + MSG_LEN);
			//报文长度
			byte[] lenBuf = String.format("%0"+MSG_LEN+"d", sendBuf.length).getBytes();
			totalBuf.put(lenBuf);
			totalBuf.put(sendBuf);
			if(LOG.isDebugEnabled())
				LOG.debug("SEND MSG IS:["+new String(totalBuf.array(),CHARSET)+"]");
			out.write(totalBuf.array());
			out.flush();
			
			//获取4位报文长度
			lenBuf = new byte[MSG_LEN];
			if((in.read(lenBuf)) != 4)
					throw new Exception("读取报文长度异常!");
			int len = Integer.parseInt(new String(lenBuf));
			
			
			byte[] respBuf = new byte[len];
			int num = 0,realLen = 0;
			while((num = in.read(tempBuf)) != -1  && (realLen < len)){
				System.arraycopy(tempBuf, 0, respBuf, realLen, num);
				realLen += num;
			}
			if(LOG.isDebugEnabled())
				LOG.debug("RECV MSG IS:["+new String(respBuf,CHARSET)+"]");
			return respBuf;
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}finally{
			if(null != out)
				out.close();
			if(null != in)
				in.close();
		}
	}
	
	public byte[] send(byte[] sendBuf) throws Exception{
		byte[] tempBuf = new byte[BUFFER_SIZE];
		OutputStream out = null;
		InputStream in = null;
		try{
			out = client.getOutputStream();
			in = client.getInputStream();
			//发送总长度，4个自己长度域 　+　实际发送的字节
			ByteBuffer totalBuf = ByteBuffer.allocate(sendBuf.length + MSG_LEN);
			//报文长度
			byte[] lenBuf = String.format("%0"+MSG_LEN+"d", sendBuf.length).getBytes();
			totalBuf.put(lenBuf);
			totalBuf.put(sendBuf);
			if(LOG.isDebugEnabled())
				LOG.debug("SEND MSG IS:["+new String(totalBuf.array(),CHARSET)+"]");
			out.write(totalBuf.array());
			out.flush();
			
			//获取4位报文长度
			lenBuf = new byte[MSG_LEN];
			if((in.read(lenBuf)) != 4)
					throw new Exception("读取报文长度异常!");
			int len = Integer.parseInt(new String(lenBuf));
			
			
			byte[] respBuf = new byte[len];
			int num = 0,realLen = 0;
			while((num = in.read(tempBuf)) != -1  && (realLen < len)){
				System.arraycopy(tempBuf, 0, respBuf, realLen, num);
				realLen += num;
			}
			if(LOG.isDebugEnabled())
				LOG.debug("RECV MSG IS:["+new String(respBuf,CHARSET)+"]");
			return respBuf;
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}finally{
			if(null != out)
				out.close();
			if(null != in)
				in.close();
		}
	}
	
	/**
	 * 发送请求
	 * 
	 * 发送格式为：
	 * 4个字节的ascii长度 + 实际发送的字节
	 * 
	 * @param sendBuf（实际发送请求字符串）
	 * @return
	 * @throws Exception
	 */
	public byte[] send(String msg) throws Exception{
		return send(msg.getBytes(CHARSET));
	}

}
