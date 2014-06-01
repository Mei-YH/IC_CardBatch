package com.icsys.batch.offline.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.checkAccount.Count;
import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;


public class IcOfflineChkDetErrorDao {
	private static final Logger LOG = Logger.getLogger(IcOfflineChkDetErrorDao.class);
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;
	
	/**
	 * 查询总记录条数
	 * @return 总记录条数
	 */
	public int getTotalCount() {
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql = "select count(*) rowCount from ic_offline_chk_det_err ";
        Count count=new Count();
		Count c=template.queryRow(sql,count,Count.class);
		return c.getRowCount();
	}
	/**
	 * 分页查询脱机清算信息
	 * @return
	 */
	public List<ClearDetailBean> getIcOfflineChkDetError(String start,String end){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from (select b.* ,ROWNUM rnum from (select * from ic_offline_chk_det_err order by clear_date desc) b where ROWNUM<="+end+") p where p.rnum>="+start;
		ClearDetailBean errList = new ClearDetailBean();
		return template.query(sql, errList, ClearDetailBean.class);
	}
	/**
	 * 按条件模糊查询的记录总数
	 * @return
	 */
	public int getAllErrorLikeNum(String status,String clearDate,String tranCode,String acctingDate,String batchNo){
		String con=this.getSqlContains(status, clearDate, tranCode, acctingDate, batchNo);
		String sql="select count(*) rowCount from ic_offline_chk_det_err "+con;
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
        Count count=new Count();
		Count c=template.queryRow(sql,count,Count.class);
		
		return c.getRowCount();
	}
	/**
	 * 根据查询条件分页查询信息
	 * @param start
	 * @param end
	 * @param status
	 * @param clearDate
	 * @param tranCode
	 * @param acctingDate
	 * @param batchNo
	 * @return
	 */
	public List<ClearDetailBean> getIcOfflineChkDetErrorByCons(String start,String end,String status,String clearDate,String tranCode,String acctingDate,String batchNo){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String con=this.getSqlContains(status, clearDate, tranCode, acctingDate, batchNo);
		String sql = "select * from (select b.* ,ROWNUM rnum from (select * from ic_offline_chk_det_err "+con+" order by clear_date desc) b where ROWNUM<="+end+") p where p.rnum>="+start;
		ClearDetailBean errList = new ClearDetailBean();
		return template.query(sql, errList, ClearDetailBean.class);
	}
	/**
	 * 组装查询条件
	 * @param status
	 * @param clearDate
	 * @param tranCode
	 * @param acctingDate
	 * @param batchNo
	 * @return
	 */
	public String getSqlContains(String status,String clearDate,String tranCode,String acctingDate,String batchNo){
		String sql=" where 1=1 ";
		if(!"____".equals(status)){
			sql=sql+" and STATUS like '"+status+"' ";
		}
		if(null!=clearDate&&!"".equals(clearDate)){
			sql=sql +" and clear_date='"+clearDate+"' ";
		}
		if(null!=tranCode&&!"".equals(tranCode)){
			sql=sql+" and tran_code='"+tranCode+"' ";
		}
		if(null!=acctingDate&&!"".equals(acctingDate)){
			sql=sql+" and accting_date='"+acctingDate+"' ";
		}
		if(null!=batchNo&&!"".equals(batchNo)){
			sql =sql +"  and batch_no="+batchNo+" ";
		}
		return sql;
	}
	/**
	 * 按条件模糊查询
	 * @param con
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ClearDetailBean> getIcOfflineChkDetError(String con,String start,String end){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from (select b.* ,ROWNUM rnum from (select * from ic_offline_chk_det_err order by clear_date desc) b where ROWNUM<="+end+") p where p.rnum>="+start;
		ClearDetailBean errList = new ClearDetailBean();
		return template.query(sql, errList, ClearDetailBean.class);
	}
	
	/**
	 * 根据清算日期和批次号获得脱机清算的差错明细
	 * @param clearDate
	 * @param batchNo
	 * @return
	 */
	public List<ClearDetailBean> getErrRecord(String clearDate) {
		NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql="select * from IC_OFFLINE_CHK_DET_ERR M,IC_ONLINE_CHK_DET_ERR N where M.CLEAR_DATE='"+clearDate+"' or N.CLEAR_DATE='"+clearDate+"'";
		ClearDetailBean errList = new ClearDetailBean();
		List<ClearDetailBean> results=dq.query(sql, errList, ClearDetailBean.class);
		LOG.info("=============当天共有"+results.size()+"条差错================");
		return results;
	}
}
