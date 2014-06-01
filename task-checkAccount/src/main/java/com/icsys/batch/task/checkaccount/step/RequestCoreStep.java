package com.icsys.batch.task.checkaccount.step;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.serial.PlatformSerialGennerator;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.platform.util.DateUtils;

public class RequestCoreStep implements Step {
	
	private static Logger LOG = Logger.getLogger(RequestCoreStep.class);

	private static final int TIME_OUT = 60000;

	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		// TODO Auto-generated method stub
		try{
			String coreIp = (String)context.getAttribute("core_ip");
			String channel = (String)context.getAttribute("channel");
			String queueName = (String)context.getAttribute("queue_name");
			String port = (String)context.getAttribute("port");
			String ccsidStr = (String)context.getAttribute("ccsid");
			String requestName = (String)context.getAttribute("request_name");
			String responseName = (String)context.getAttribute("response_name");
			String sysdate = context.getJobDate();
			if(!notify(coreIp, port, ccsidStr, channel, queueName, requestName, responseName, sysdate)){
				throw new JobException("请求主机失败!");
			}
			LOG.info("请求主机成功！");
		}catch(Exception e){
			LOG.error(e.getMessage());
			throw new JobException("请求主机异常，任务挂起！");
		}
	}

	/**
	 * 下发通知，请求日期到核心生成对账文件
	 */
	@SuppressWarnings("unchecked")
	private boolean notify(String coreIp,String port,String ccsidStr,String channel,String queueName,String requestName,
			String responseName,String sysdate){
		MQQueueManager mqQueueManager = null;
		try {
			MQEnvironment.hostname = coreIp;
			MQEnvironment.port = Integer.valueOf(port);
			MQEnvironment.channel = channel;
			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
			if(Integer.valueOf(ccsidStr) > 0){
				MQEnvironment.CCSID = Integer.valueOf(ccsidStr);
			}

			mqQueueManager = new MQQueueManager(queueName);
			
			String lastDate = BatchDateUtil.getLastDateWithStringFormat(sysdate);
			String serial = PlatformSerialGennerator.getPlatformSerial(sysdate);
			String sendStr = encodePub(serial,lastDate);
			LOG.info("发送主机报文：");
			LOG.info(sendStr);
			byte[] messageId = sendMessage(mqQueueManager, responseName, sendStr, requestName);
			MQMessage message = receiveMessage(mqQueueManager, responseName, messageId);
			byte[] bytes = new byte[message.getMessageLength()];
			message.readFully(bytes);
			String result = new String(bytes, "UTF-8");//核心的编码方式是UTF-8
			LOG.info("接收核心报文：");
			LOG.info(result);
			Document doc = DocumentHelper.parseText(result);
			Element response = doc.getRootElement().element("Service_Header").element("service_response");
			
			String status = response.elementText("status");
			if("COMPLETE".equals(status)){
				return true;
			}
			status = response.elementText("code");
			if("EEX14".equals(status)){
				return true;
			}
			return false;
		}catch(MQException e){
			String msg = getErrMsg(e.reasonCode);
			LOG.error(msg, e);
		}catch(Exception ex){
			LOG.error(ex.getMessage());
		}finally{
			if(null != mqQueueManager){
				try{
					mqQueueManager.disconnect();
					mqQueueManager.close();
				}catch(Exception e){
					
				}
			}
		}
		return false;
	}
	
	/**
	 * 发送
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws MQException 
	 */
	private byte[] sendMessage(MQQueueManager mqQueueManager,String responseName,String sendStr,String queueName) throws UnsupportedEncodingException, IOException, MQException{
		MQPutMessageOptions mpm = new MQPutMessageOptions();
		mpm.options = mpm.options + MQC.MQPMO_NEW_MSG_ID;
		mpm.options = mpm.options + MQC.MQPMO_SYNCPOINT;
		MQMessage message = new MQMessage();
		message.format = MQC.MQFMT_STRING;
		message.messageFlags = MQC.MQMT_REQUEST;
		message.replyToQueueName = responseName;
		message.write(sendStr.getBytes("UTF-8"));
		MQQueue queue = mqQueueManager.accessQueue(queueName, MQC.MQOO_OUTPUT|MQC.MQOO_FAIL_IF_QUIESCING);
		queue.put(message,mpm);
		mqQueueManager.commit();
		queue.close();
		return message.messageId;
	}
	
	/**
	 * 接收
	 */
	private MQMessage receiveMessage(MQQueueManager mqQueueManager,String queueName,byte[] messageId)throws Exception{
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;
		gmo.options = gmo.options + MQC.MQGMO_WAIT;
		gmo.options = gmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;
		gmo.matchOptions = MQC.MQMO_MATCH_MSG_ID;
		if(TIME_OUT > 0){
			gmo.waitInterval = TIME_OUT;
		}
		MQMessage message = new MQMessage();
		message.messageId = messageId;
		MQQueue queue = mqQueueManager.accessQueue(queueName, MQC.MQOO_INPUT_SHARED | MQC.MQOO_FAIL_IF_QUIESCING, null, null, null);
		queue.get(message, gmo);
		mqQueueManager.commit();
		queue.close();
		return message;
	}
	
	/**
	 * 组报文
	 */
	private String encodePub(String serviceSn,String lastDate){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Service");
		Element head = root.addElement("Service_Header");
		Element body = root.addElement("Service_Body");
		Element request = body.addElement("request");
		Element ext = body.addElement("ext_attributes");
		head.addElement("service_sn").setText(serviceSn);
		head.addElement("service_id").setText("00010000808902");
		head.addElement("requester_id").setText("0091");
		head.addElement("branch_id").setText("900001000");
		head.addElement("channel_id").setText("96");
		head.addElement("service_time").setText(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());
		head.addElement("verion_id").setText("01");
		request.addElement("BUSSINESS-TYPE").setText("0");
		request.addElement("REFERENCE-TYPE").setText("0001");
		request.addElement("DATA-PGM-ID").setText("00090001");
		request.addElement("WK-DATE").setText(lastDate);
		request.addElement("DUE-DT").setText(lastDate);
		request.addElement("DOC-NO").setText(lastDate);
		ext.addElement("INM-TERM-TYPE").setText("A");
		ext.addElement("INM-BRANCH-ID").setText("900001000");
		ext.addElement("INM-CHANEL-FLG").setText("96");
		return doc.asXML();
	}
	
	/**
	 * 获取错误信息
	 */
	private String getErrMsg(int errCode){
		String msg = "";
		switch(errCode){
			case 2033:
				msg = "队列为空或超时！";
				break;
			case 2085:
				msg = "队列名称不正确！";
				break;
			case 2058:
				msg = "队列管理器名称不正确";
				break;
			case 2059:
				msg = "连接异常，主机或端口不正确！";
				break;
			case 2540:
				msg = "通道异常，或通道不正确！";
				break;
			default:
				msg = "未知错误！";
				break;
		}
		return msg;
	}
}
