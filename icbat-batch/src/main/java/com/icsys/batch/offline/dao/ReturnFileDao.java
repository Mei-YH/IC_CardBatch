package com.icsys.batch.offline.dao;

import java.util.List;
import java.util.Map;

import com.icsys.batch.offline.bean.ReturnFileBean;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class ReturnFileDao {
//	private static final Logger LOG = Logger.getLogger(ReturnFileDao.class);

	/**
	 * 根据清算日期和批次号获得脱机清算明细
	 * 
	 * @param clearDate
	 * @param batchNo
	 * @return
	 */
	public List<ReturnFileBean> getDetailRecord(String clearDate, String batchNo) {
		NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
//		String sql1 = "ocd_trcode,ocd_tramount,ocd_acct_no,ocd_accdate,ocd_plaserial,ocd_status";
//		String sql2 = "det_key7,det_key11,det_key32,det_tramount,det_key33,ic_cardno,det_accbranch,det_icadate,det_plserial,det_status";
//		String sql = "select " + sql1
//				+ " from gr_bus_offchkdet where ocd_cldate='" + clearDate
//				+ "' AND ocd_batchno=" + batchNo + " union select " + sql2
//				+ " from gr_bus_onchkdet where det_cldate='" + clearDate
//				+ "' AND det_batch_no=" + batchNo;
		
		String sql = "select ocd_trcode,ocd_tramount,ocd_acct_no,ocd_accdate,ocd_plaserial,ocd_status,tag9a,ocd_orgonf as org_info," +
				"ocd_key11,ocd_key42,ocd_key43,ocd_fee from gr_bus_offchkdet " +
				"where ocd_cldate=#ocdCldate# AND ocd_batchno=#ocdBatchno# and substr(ocd_status,4,1) in('1','3')";
		ReturnFileBean bean = new ReturnFileBean();
		bean.setOcdCldate(clearDate);
		bean.setOcdBatchno(Integer.parseInt(batchNo));
		List<ReturnFileBean> results = dq.query(sql, bean, ReturnFileBean.class);
		return results;
	}

	/**
	 * 根据清算日期和批次号获得脱机清算文件的路径(包含文件名)
	 * 
	 * @param clearDate
	 * @param batchNo
	 * @return
	 */
	public String getClearFilePath(String clearDate,String batchNo) {
	   NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
	   String sql="select ota_ofile as CLEAR_FILE from gr_bus_offtask where ota_cldate='"+clearDate+"' and ota_batno="+batchNo;
	   Map<String, Object> results=dq.queryRow(sql, null);
	   System.out.println(results);
	   String clearFilePath="";
	   if(null!=results||"".equals(results)){
    	   clearFilePath=getFileName((String)results.get("CLEAR_FILE"));
       }
	   return clearFilePath;
   }
	
	/**
	 * 通过文件的绝对路径获得文件名(目前只支持"/home/fileName"这种形式的)
	 * 
	 * @param filePath
	 * @return
	 */
	private String getFileName(String filePath) {
		int subLocate = filePath.lastIndexOf("/") + 1;
		String fileName = filePath.substring(subLocate, filePath.length());
		return fileName;
	}
}
