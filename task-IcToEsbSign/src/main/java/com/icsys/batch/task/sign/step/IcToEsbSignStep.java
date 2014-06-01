package com.icsys.batch.task.sign.step;

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

public class IcToEsbSignStep implements Step {

	private static Logger LOG = Logger.getLogger(IcToEsbSignStep.class);
//	private static SimpleDateFormat fullFormat = new SimpleDateFormat("HHmmss");
	private static String BRH_ID = null;

	private static final int TIME_OUT = 60000;

	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)
	throws JobException {
		// TODO Auto-generated method stub
		String coreIp = (String)context.getAttribute("core_ip");
		String channel = (String)context.getAttribute("channel");
		String queueName = (String)context.getAttribute("queue_name");
		String port = (String)context.getAttribute("port");
		String ccsidStr = (String)context.getAttribute("ccsid");
		String requestName = (String)context.getAttribute("request_name");
		String responseName = (String)context.getAttribute("response_name");
		BRH_ID = (String)context.getAttribute("brh_id");
		String sysdate = context.getJobDate();
		if(!icToEsbSign(coreIp, port, ccsidStr, channel, queueName, requestName, responseName, sysdate)){
			throw new JobException("请求主机失败,任务挂起！");
		}
	}

	/**
	 * ic卡向esb发送签到报文，esb响应返回
	 */
	@SuppressWarnings("unchecked")
	private boolean icToEsbSign(String coreIp,String port,String ccsidStr,String channel,String queueName,String requestName,String responseName,String sysdate){
		MQQueueManager mqQueueManager = null;
		try {
			LOG.debug("开始搭建MQ环境。。");
			int ccsid = Integer.valueOf(ccsidStr);
			MQEnvironment.addConnectionPoolToken();
			MQEnvironment.hostname = coreIp;
			MQEnvironment.port = Integer.valueOf(port);
			MQEnvironment.channel = channel;
			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
			if(ccsid > 0){
				MQEnvironment.CCSID = ccsid;
			}
			mqQueueManager = new MQQueueManager(queueName);
		} catch (MQException e) {
			LOG.error("MQ连接异常：" + e.getMessage());
			return false;
		}
		try{
//			String sysFullDate = sysdate + fullFormat.format(new Date());
//			String serial = PlatformSerialGennerator.getPlatformSerial(sysdate);//平台流水
			String sendStr = encodePub(null,null);
			LOG.info("发送主机报文：");
			LOG.info(sendStr);
			byte[] messageId = sendMessage(mqQueueManager, responseName, sendStr, requestName);
			MQMessage message = receiveMessage(mqQueueManager, responseName, messageId);
			byte[] bytes = new byte[message.getMessageLength()];
			message.readFully(bytes);
			String result = new String(bytes, "UTF-8");//核心的编码方式是UTF-8
			LOG.info("接收主机报文：");
			LOG.info(result);
			Document doc = DocumentHelper.parseText(result);

			String status = doc.getRootElement().element("Service_Body").element("response").elementText("RspCode");
			/**
			 * 核心返回报文后判断是否签到成功不然继续给核心发送报文
			 */
			return "000000".equals(status);
			/*if("COMPLETE".equals(status)){
				Element response = doc.getRootElement().element("Service_Body").element("response");
				String pik = response.element("PIK").getText().trim();
				String mak = response.element("MAC").getText().trim();
//				service.savaOrUpdateData(pik, mac);
				new PinMakStoreService().StorePikMak(BRH_ID, pik, mak);
				return true;
			}
//			status = service_response.elementText("code");
//			if("EEX14".equals(status)){
//				return true;
//			}
			return false;*/
		}catch (MQException e) {
			// TODO: handle exception
			String msg = getErrMsg(e.reasonCode);
			LOG.error(msg, e);
		}catch(Exception e){
			LOG.error(e.getMessage());
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
	 */
	private byte[] sendMessage(MQQueueManager mqQueueManager,String responseName,String sendStr,String queueName)throws Exception{
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
	private String encodePub(String serviceSn,String sysDate){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Service");
		Element body = root.addElement("Service_Body");
		Element request = body.addElement("request");
		/*Element head = root.addElement("Service_Header");
		Element attributes = body.addElement("ext_attributes");
		head.addElement("service_sn").setText(serviceSn);
		head.addElement("service_id").setText("00910000000001");
		head.addElement("requester_id").setText("0091");
		head.addElement("branch_id").setText("900001000");
		head.addElement("channel_id").setText("96");
		head.addElement("service_time").setText(sysDate);
		head.addElement("verion_id").setText("01");
		attributes.addElement("SIGN_FLAG").setText("1");*/
		request.addElement("TxCode").setText("2835");
		request.addElement("KEY_LABEL").setText(BRH_ID);
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
