package com.icsys.batch.task.checkaccount.step;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.checkaccount.proxy.CheckAccountConstants;
import com.icsys.batch.task.checkaccount.proxy.bean.GrCheckAcctDet;
import com.icsys.batch.task.checkaccount.proxy.dao.GrCheckAcctDetDao;
import com.icsys.batch.util.DateUtil;
import com.icsys.platform.dao.TXHelper;

/**
 * 导入对账明细
 * @author liuyb
 *
 */
public class CheckAccountImportDataDetStep implements Step {
	
	private static Logger LOG = Logger.getLogger(CheckAccountImportDataDetStep.class);
	/*对账文件位图*/
	private static final int[] positions = new int[]{8,19,8,30,8,16,16,32,18,18,18,18,18,10,6,11,11,2,9,9,42,19,9,15,6,19,
		16,16,16,16,16,9,9,8,15,1,4,16,1,1,1,8,8,8,8,8,9,1,9,8,2,1,2,28,1,20,20,1,11,28,28,1,8,4,1,3,2,8,8,3,88,1};
	
	private static final int BATCH_COUNT = 500;

	private GrCheckAcctDetDao dao = new GrCheckAcctDetDao();
	
	public long estimate(JobContext context) throws JobException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		// TODO Auto-generated method stub
		String fileName = (String) context.getAttribute(CheckAccountConstants.FILE_NAME);// 对账文件
		String checkDate =(String)context.getAttribute("check_date");
		String channelId = (String)context.getAttribute("channel_id");
		boolean isCups = (Boolean)context.getAttribute("is_cups");
		try{
			/*删除对账明细表前180天当前渠道数据*/
			String lastDate = DateUtil.getDateStri(DateUtil.getAfterNDay(DateUtil.getDateFromShortStr((isCups?context.getJobDate().substring(0,8-checkDate.length()) + checkDate:checkDate)),-180));

			/*删除对账明细表前一天当前渠道数据*/
//			String lastDate = BatchDateUtil.getLastDateWithStringFormat(isCups?context.getJobDate().substring(0,8-checkDate.length()) + checkDate:checkDate);
			lastDate = isCups?lastDate.substring(8-checkDate.length()):lastDate;
			
			dao.deleteDet(channelId, lastDate);
		}catch(Exception e){
			LOG.error("任务挂起，异常:" + e.getMessage());
			throw new JobException("任务挂起，异常:" + e.getMessage());
		}
		TXHelper.beginTX();        
		BufferedReader reader = null;
		try {			
			File fileTemp = new File(fileName);
            reader = new BufferedReader(new FileReader(fileTemp));
            String line = null;
			int count = 0;
			while((line = reader.readLine()) != null){
				if ("".equals(line.trim())) {
					continue;
				}
				importData(line, channelId, checkDate, ++count);
				if(count % BATCH_COUNT == 0){
					TXHelper.commit();
					TXHelper.beginTX();
				}
			}
			reader.close();
			TXHelper.commit();
			LOG.info("文件入库完成...");
		} catch (Exception e) {
			TXHelper.rollback();
			LOG.error("任务挂起，异常:" + e.getMessage());
			throw new JobException("任务挂起，异常:" + e.getMessage());
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					
				}
			}
			TXHelper.close();
		}
	}

	private void importData(String line,String channel,String checkDate,int count)throws Exception{
		if(line.length() != 876){
			throw new Exception("对账文件明细格式异常！");
		}
//		String[] fields = new String[positions.length];
		int index = 0;
		GrCheckAcctDet det = new GrCheckAcctDet();
		for(int i=0;i<positions.length;i++){
//			fields[i] = line.substring(index,index += positions[i]);
			Method setDet = det.getClass().getMethod("setDet" + (i + 1), new Class[]{String.class});
			setDet.invoke(det,  new Object[] {line.substring(index,index += positions[i])});
		}
		det.setCheckId(count);
		det.setChannel(channel);
		det.setCheckDate(checkDate);
		if(dao.insertDet(det) != 1){
			throw new Exception("明细入库失败！");
		}
	}
}
