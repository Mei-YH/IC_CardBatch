package com.icys.batch.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author liuyb
 * @date 2014-05-14
 *
 */
public class JPTaskInfo{

	private static JMXConnector connector = null;

	private static MBeanServerConnection remoteServer;

	private static ObjectName managerName;

	private static ObjectName trackerName;

	public static void retryConnect(String strUrl){
		JMXServiceURL url = null;
		try {
			url = new JMXServiceURL(strUrl);
			connector = JMXConnectorFactory.connect(url, null);
			remoteServer = connector.getMBeanServerConnection();
			String domain = remoteServer.getDefaultDomain();
			managerName = new ObjectName(domain
					+ ":Module=task-manager,Type=TaskManager");
			trackerName = new ObjectName(domain
					+ ":Module=task-manager,Type=TaskTracker");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	public static void close(){
		try{
			if(null != connector){
				connector.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void forceDetect(String taskName) throws Exception {
		Map params = new HashMap();
		try {
			String[] signatures = new String[] { String.class.getName(),
					String.class.getName(), Map.class.getName() };
			remoteServer.invoke(managerName, "forceDetect", new Object[] {
					taskName, null, params }, signatures);
		} catch (Exception e) {
			throw e;
		}
	}

	public static void executeTask(int flag, String taskName, String taskId)
			throws Exception {
		Integer stageNo = null;
		Integer stepNo = null;
		if (flag == 0) {
			taskId = null;
			stageNo = 0;
			stepNo = -1;
		} else if (flag == 1 && null != taskId) {
		} else {
			throw new Exception("task[" + taskName + "] flag is error");
		}

		Map params = new HashMap();
		try {
			String[] signatures = new String[] { String.class.getName(),
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName(), Map.class.getName() };
			remoteServer.invoke(managerName, "executeTask", new Object[] {
					taskName, taskId, stageNo, stepNo, params }, signatures);
		} catch (Exception e) {
			throw e;
		}
	}

	public static String searchTask(String taskName, String strDate)
			throws Exception {
		String res = "";
		Date date = null;
		if (null != strDate && !"".equals(strDate)) {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			date = format.parse(strDate);
		}else{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			date = calendar.getTime();
		}
		List<JSONObject> taskRes = searchTask(taskName, date);
		for (JSONObject object : taskRes) {
			res += "taskName  " + fillBlank(taskName,30," ",false) + " " + batStatus(object.getInt("batStatus")) + "\n";
			List<JSONObject> stageRes = taskStage(object.getString("batId"));
			for (JSONObject stage : stageRes) {
				String temp = stageOrStepStatus(stage.getInt("stageStatus"));
				res += "stageName " + fillBlank(stage.getString("stageName"),30," ",false)
						+ " " + temp + " "
						+ ("1".equals(temp)?stage.getString("errorMessage"):"") + "\n";
				List<JSONObject> stepRes = taskStep(object.getString("batId"),
						stage.getInt("stageNo"));
				for (JSONObject step : stepRes) {
					temp = stageOrStepStatus(step.getInt("stepStatus"));
					res += "stepName  " + fillBlank(step.getString("stepName"),30," ",false)
							+ " " + temp + " "
							+ ("1".equals(temp)?step.getString("errorMessage"):"")+ "\n";
				}
			}
		}
		return res;
	}

	private static List<JSONObject> searchTask(String taskName, Date date)
			throws Exception {
		String[] signature = new String[] { String.class.getName(),
				Date.class.getName() };
		String json = (String) remoteServer.invoke(trackerName, "searchByDate",
				new Object[] { taskName, date }, signature);
		return josnToList(json);
	}

	private static List<JSONObject> taskStage(String taskId) throws Exception {
		String[] signature = new String[] { String.class.getName() };
		String json = (String) remoteServer.invoke(trackerName,
				"getTaskStages", new Object[] { taskId }, signature);
		return josnToList(json);
	}

	private static List<JSONObject> taskStep(String taskId, int stageNo)
			throws Exception {
		String[] signature = new String[] { String.class.getName(),int.class.getName() };
		String json = (String) remoteServer.invoke(trackerName, "getTaskSteps",
				new Object[] { taskId, stageNo }, signature);
		return josnToList(json);
	}

	private static List<JSONObject> josnToList(String json) throws Exception {
		List<JSONObject> res = new ArrayList<JSONObject>();
		JSONArray attr = new JSONArray(json);
		for (int i = 0; i < attr.length(); i++) {
			res.add((JSONObject) attr.get(i));
		}
		return res;
	}

	private static String batStatus(int batStauts) {
		if (batStauts == 7 || batStauts == 2) {
			return "0";// succ
		} else if (batStauts == 4 || batStauts == 5 || batStauts == 6) {
			return "1";// fail
		} else {
			return "2";
		}
	}

	private static String stageOrStepStatus(int status) {
		if (status == 3) {
			return "0";
		} else if (status == 1 || status == 2) {
			return "1";
		} else {
			return "2";
		}
	}
	
	public static String fillBlank(String value,int length,String blank,boolean isLeft) throws Exception{
		StringBuffer sb = new StringBuffer();
		int len = 0;
		if(null != value){
			len = value.getBytes("GB2312").length;
		}
		if(len > length){
			throw new Exception("value's length is longer!");
		}
		if(len == length) return value;
		if(isLeft){
			for(int i=0;i<length - len;i++){
				sb.append(blank);
			}
			sb.append(value);
		}else{
			sb.append(value);
			for(int i=0;i<length - len;i++){
				sb.append(blank);
			}
		}
		return sb.toString();
	}
}
