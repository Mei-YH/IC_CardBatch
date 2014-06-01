package com.icsys.batch.task.report.step;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.report.proxy.bean.GrBusTrloglist;
import com.icsys.batch.task.report.proxy.bean.GrBusTxlist;
import com.icsys.batch.task.report.proxy.dao.DataSyncDao;
import com.icsys.batch.util.DateUtil;
import com.icsys.platform.dao.TXHelper;

public class DataSyncStep implements Step{

	private Logger LOG = Logger.getLogger(DataSyncStep.class);
	
	public long estimate(JobContext arg0) throws JobException {
		return 0;
	}

	
	public void perform(JobContext context, Executor executor) throws JobException {

		LOG.debug("生成日志表和流水表del文件开始：");	

		// 系统时间
		String sysdate = (String)context.getAttribute("sysDate");
		// 清理时间 13个月取395天
		String cleardate;
		try {
			cleardate = DateUtil.getDateStri(DateUtil.getAfterNDay(DateUtil.getDateFromShortStr(sysdate), -395));
			LOG.debug("删除数据日期：" + cleardate);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw new JobException("获取清理日期失败:" + e1.getMessage());
		}

		String sendpath = (String)context.getAttribute("sendpath");
		String listpath = (String)context.getAttribute("listpath");
		String res = "";
		String indicate = "";


		//查询操作日志表与交易流水
		DataSyncDao dao = new DataSyncDao();

		try {
			// 查询操作日志
			List<GrBusTrloglist> grbustrloglist= dao.queryGrBusTrloglistData(sysdate);
			LOG.debug("查询出的操作日志记录条数："+grbustrloglist.size());

			// 操作日志记录写入文件
			File trlog =new File(sendpath,sysdate + "/999/GR_BUS_TRLOGLIST.del");
	
			FileUtils.writeLines(trlog, "GBK", grbustrloglist);

			LOG.debug("GR_BUS_TRLOGLIST.del文件写入完成");
			// TODO
			// 删除日志表和历史表数据
			TXHelper.beginTX();
			try{
				dao.deleteGrBusTrloglistData(cleardate);
				dao.deleteGrBusTrloglistHisData(cleardate);
				TXHelper.commit();
				LOG.info("删除数据GR_BUS_TRLOGLIST成功！");
			}catch (Exception e) {
				// TODO: handle exception
				TXHelper.rollback();
				LOG.info("删除数据GR_BUS_TRLOGLIST失败！");
			}finally{
				TXHelper.close();
			}
			res += sendpath + " " + sysdate + "/999 " + trlog.getName() + " " + trlog.length() + "\n"; 
			indicate += trlog.getName() + " " + trlog.length() + "\n";
			// 查询交易流水
			List<GrBusTxlist> grbustxlist= dao.queryGrBusTxlistData(sysdate);
			LOG.debug("查询出的交易流水记录条数："+grbustxlist.size());
			// 交易流水记录写入文件
			File txlist = new File(sendpath,sysdate + "/999/GR_BUS_TXLIST.del");
		
			FileUtils.writeLines(txlist, "GBK", grbustxlist);
			
			LOG.debug("GR_BUS_TXLIST.del文件写入完成");
			// TODO
			// 删除交易流水表 A 、B 、历史表  数据
			TXHelper.beginTX();
			try{
				dao.deleteGrBusTxlistAData(cleardate);
				dao.deleteGrBusTxlistBData(cleardate);
				dao.deleteGrBusTxlistHisData(cleardate);
				TXHelper.commit();
				LOG.info("删除数据GR_BUS_TXLIST成功！");
			}catch (Exception e) {
				// TODO: handle exception
				TXHelper.rollback();
				LOG.info("删除数据GR_BUS_TXLIST失败！");
			}finally{
				TXHelper.close();
			}
			res += sendpath + " " + sysdate + "/999 " + txlist.getName() + " " + txlist.length() + "\n"; 
			indicate += txlist.getName() + " " + txlist.length() + "\n";
			
			File indi = new File(sendpath,sysdate + "/999/indicate.txt");
			FileUtils.writeStringToFile(indi, indicate, "GBK");
			res += sendpath + " " + sysdate + "/999 " + indi.getName() + " " + indi.length() + "\n"; 
			
			File listFile = new File(listpath,"ica_tosync_ods_" + sysdate + ".list");
			FileUtils.writeStringToFile(listFile, res, "GBK");
		}catch (Exception e) {
			LOG.error("任务挂起，异常:" + e.getMessage());
			throw new JobException("任务挂起，异常:" + e.getMessage());
		}
	}
}
