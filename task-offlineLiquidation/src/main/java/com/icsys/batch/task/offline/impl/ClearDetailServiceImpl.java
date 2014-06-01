package com.icsys.batch.task.offline.impl;

import java.util.List;

import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.offline.dao.IcOfflineChkDetDao;
import com.icsys.platform.dao.TXHelper;

/**
 * 脱机交易清算明细表操作服务实现类
 * @author Administrator
 *
 */
public class ClearDetailServiceImpl{
	private IcOfflineChkDetDao dao  = new IcOfflineChkDetDao();

	/**
	 * 根据清算日期查询交易明细
	 * @param clearingDate 清算日期
	 * @param pageLength 每页长度
	 * @param pageNo  页码
	 * @return 脱机消费明细
	 */
	public List<ClearDetailBean> getClearingDetailBean(String clearDate,String batchNo,String tranCode,int pageNo,int pageLength) {
		return dao.getClearingDetailBean(clearDate,batchNo,tranCode,pageNo,pageLength);
	}

	/**
	 * @param clearDate 清算日期
	 * 根据清算日期，获得总记录条数
	 * @return
	 */
	public int getTotalCount(String clearDate,String batchNo,String tranCode) {
		return dao.getTotalCount(clearDate,batchNo,tranCode);
	}

	/**
	 * 更新交易明细
	 * @param detail 新的交易明细对象
	 */
	public void updateDetail(ClearDetailBean detail) {
		TXHelper.beginNewTX();
		try {
			dao.updateDetail(detail);
			TXHelper.commit();
		} catch (Exception e) {
			TXHelper.rollback();
			e.printStackTrace();
		}finally{
			TXHelper.close();
		}
	}
	
	/**
	 * 复制差错信息到ERR表中
	 * @param clearDate 清算日期
	 * @param batchNo 批次号
	 * @throws Exception 
	 */
	public void copyErr(String clearDate,String batchNo) throws Exception{
			dao.copyErr(clearDate,batchNo);
	}

	public void deleteClearDetails(String clearDate, String batchNo) {
			dao.deleteDetails(clearDate,batchNo);
	}

	public ClearDetailBean getOrginalInfo(ClearDetailBean detail) {
		return dao.getOrginalInfo(detail);
	}
	
	public void moveToHis(String clearDate,String batchNo) throws Exception{
		dao.moveToHistory(clearDate,batchNo);
	}
	
	public void deleteClearDetails(){
		TXHelper.beginTX();
		try {
			dao.delClearDetail();
			TXHelper.commit();
		} catch (Exception e) {
			e.printStackTrace();
			TXHelper.rollback();
		}finally{
			TXHelper.close();
		}
	}
}
