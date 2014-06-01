package com.icsys.batch.task.checkaccount.proxy.dao;

import java.util.List;

import com.icsys.batch.checkAccount.BatchSQLDao;
import com.icsys.batch.checkAccount.IcCheckAccountErrList;
import com.icsys.batch.checkAccount.IcCheckAccountErrListDao;
import com.icsys.batch.checkAccount.IcCheckAccountTxList;
import com.icsys.batch.checkAccount.IcCheckAccountTxListDao;
import com.icsys.batch.checkAccount.IcTxListDao;
import com.icsys.batch.task.checkaccount.proxy.bean.GrCheckAcctDet;
import com.icsys.batch.task.checkaccount.proxy.bean.TCpsChkerrmsg;
import com.icsys.platform.util.SystemConstans;

/**
 * 如果IC卡平台流水对账状态为00－未对账,并且交易状态为:非成功,则更新其对账状态为11-平账
 * 
 * @author liuyb
 * 
 */
public class CheckAccountDao {
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;
	
//	private static Logger LOG = Logger.getLogger(CheckAccountDao.class);
	
	private IcCheckAccountTxListDao checkDao = new IcCheckAccountTxListDao();
	private GrCheckAcctDetDao grdao = new GrCheckAcctDetDao();

	/**
	 * 如果IC卡平台流水对账状态为00－未对账,并且交易状态为:非成功,则更新其对账状态为11-平账
	 * @param isCups
	 * @param channel
	 * @param checkDate
	 */
	public void updateOnPlatFormCloseOut(boolean isCups,String channel,String checkDate){
		checkDao.updateOnPlatFormCloseOut(isCups,channel,checkDate);
	}

	/*
	 * 入对账差错表
	 */
//	public void insertCheckErrList(IcCheckAccountTxList checkList)
//			throws Exception {
//		NamedUpdateTemplate template = TemplateManager
//				.getNamedUpdateTemplate(DS_NAME);
//		String tableName = SystemParamValue.getNotCurrentSerialTableName();
//		String sql = " insert into gr_bus_cheaccerrlist(che_serial,che_plserial,che_trdate,che_tramount,che_hserial,che_reclogo,che_remark)"
//				+ " select case when a.host_serial is null then b.txl_serial else a.host_serial end,platform_serial,"
//				+ "case when platform_date is null then host_date else platform_date end,tran_amount,host_serial,'01','流水不存在' from "
//				+ "from gr_checkacct_tmpdet a left join "
//				+ tableName
//				+ " b on(a.tran_type='0001' and a.platform_serial=b.txl_serial and a.platform_date=b.txl_trdate)"
//				+ "or (a.tran_type='0002' and a.host_serial=b.txl_hserial and a.host_date=b.txl_trdate) where txl_trstatus is null";
//		LOG.info("sql:[" + sql + "]");
//		template.update(sql, checkList);
//	}
	
	/**
	 * 根据平台流水号获取明细
	 */
	public List<IcCheckAccountTxList> getRecordByPlatformSerial(String serial){
		return checkDao.getRecordByPlatformSerial(serial);
	}
	
	/**
	 * 更新对账标志，对账使用
	 */
	public int updateRecFlag(IcCheckAccountTxList list){
		return checkDao.updateRecFlag(list);
	}
	
	/**
	 * 更新交易状态为成功且未对账的明细
	 */
	public void updateExtraRecordsOnPlatForm(boolean isCups,String checkDate,String channel){
		checkDao.updateExtraRecordsOnPlatForm(isCups, checkDate, channel);
	}
	
	/**
	 * 将对账不平记录拷贝到差错表
	 */
	public void copyUnBalanceRecords(boolean isCups,String channel,String checkDate){
		new IcCheckAccountErrListDao().copyUnBalanceRecordsToError(isCups, channel, checkDate);
	}

	public void insertAndDelCheckAccList(boolean isCups, String channelId, String lastDate,String checkDate)throws Exception {
		// TODO Auto-generated method stub
		checkDao.deleteCheckLastDate(isCups, channelId, lastDate);
		checkDao.insertCheckAcctList(isCups, channelId, checkDate);
	}

	public void moveRecordsToHistory(boolean isCups, String channelId, String checkDate)throws Exception {
		// TODO Auto-generated method stub
		/*转移交易流水*/
		IcTxListDao dao = new IcTxListDao();
		dao.insertHistory(isCups, channelId, checkDate);
		dao.deleteTxList(isCups, channelId, checkDate);
		/*转移操作日志*/
		new BatchSQLDao().moveTranLogRecordsToHistory(checkDate,isCups);
	}
	
	public List<IcCheckAccountErrList> getCheckAccErrList(boolean isCups, String channelId, String checkDate,String tranBranch,String tranDate){
		return new IcCheckAccountErrListDao().getRecordsByChannel(isCups, channelId, checkDate,tranBranch,tranDate);
	}
	
	public List<IcCheckAccountTxList> getCheckAccSuccList(boolean isCups, String channelId, String checkDate){
		return checkDao.getRecordsByChannel(isCups, channelId, checkDate);
	}
	
	public List<GrCheckAcctDet> queryDetail(String channel,String checkDate){
		return grdao.queryDetail(channel, checkDate);
	}
	
	public List<GrCheckAcctDet> queryResultDetail(String channel,String checkDate,String afterDate){
		return grdao.queryResultDetail(channel, checkDate,afterDate);
	}

	public List<GrCheckAcctDet> queryResultErrDetail(String channel,String checkDate,String afterDate){
		return grdao.queryResultErrDetail(channel, checkDate,afterDate);
	}
	
	public List<String> groupByTranBranch(){
		return new IcCheckAccountErrListDao().queryCityCode();
	}
	
	public List<TCpsChkerrmsg> queryReportDetail(String checkDate,String sysdate){
		return new TCpsChkerrmsgDao().queryErrResultDetail(checkDate,sysdate);
	}
	
	public void insertTcpsReportData(String channel,String checkDate,String jobDate){
		new TCpsChkerrmsgDao().insertData(channel, checkDate,jobDate);
	}
}
