package com.icsys.batch.business.acctting.dao;

import java.util.List;

import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctting.impl.AcctingList;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class AcctingListDAO {
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	private String acctListTable;
	
	private String select_list = "SELECT LIS_SERIAL AS SERIAL, LIS_TRSERIAL AS ORG_SERIAL LIS_ORGSERIAL, LIS_SN AS SN, LIS_ACCDATE AS ACCTING_DATE, LIS_TRBRANCH AS TRAN_BRANCH, LIS_ACCBRANCH AS ACCTING_BRANCH, LIS_TELLER AS OPERATOR, CURRENCY_CODE AS CURR_TYPE, ACC_NO AS ACCT_NO, SUB_CODE AS SUBJECT_CODE, LENDING_SIGN AS ACCT_CD, LIS_AMOUNT AS AMOUNT, LIS_REMARK AS REMARK, LIS_SIGN AS SERIAL_FLAG FROM ";

	public AcctingListDAO() {
		super();
		// 获取当前分录流水表
		setAcctListTable(AcctSysUtil.getInstance().getAccListTable(true));
	}

	public AcctingListDAO(boolean bCurrent) {
		super();
		// 日终记账必须取非当前分录流水表
		setAcctListTable(AcctSysUtil.getInstance().getAccListTable(bCurrent));
	}

	public void add(AcctingList alist) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String table = getAcctListTable();
		String sql = "INSERT INTO "
				+ table
				+ " (lis_serial, lis_trserial, lis_orgserial, lis_sn, lis_accdate, lis_trbranch, lis_accbranch, lis_teller, currency_code, acc_no, sub_code, lending_sign, lis_amount, lis_remark, lis_sign) "
				+
				"VALUES(#SERIAL#, #TRAN_SERIAL#, #ORG_SERIAL#, #SN#, #ACCTING_DATE#, #TRAN_BRANCH#, #ACCTING_BRANCH#, #OPERATOR#, #CURR_TYPE#, #ACCT_NO#, #SUBJECT_CODE#, #ACCT_CD#, #AMOUNT#, #REMARK#, #SERIAL_FLAG#)";

		template.update(sql, alist);
	}

	/**
	 * 冲正分录记录修改
	 * @param alist
	 */
	public void updateReversal(AcctingList alist) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String table = getAcctListTable();
		String sql = "UPDATE "
				+ table
				+ " SET lis_amount=#AMOUNT#, lis_sign=#SERIAL_FLAG# "
				+
				"WHERE lis_serial=#SERIAL# AND lis_sn=#SN# ";

		template.update(sql, alist);
	}

	public List<AcctingList> getAcctListBySerial(String serial) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = select_list + getAcctListTable()
				+ " where lis_serial=#SERIAL# ";
		AcctingList al = new AcctingList();
		al.setSerial(serial);
		return template.query(sql, al, AcctingList.class);
	}

	public AcctingList getAcctListByKey(String serial,Integer sn) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = select_list
				+ getAcctListTable()
				+ " where lis_serial=#SERIAL# and lis_sn=#SN#";
		AcctingList al = new AcctingList();
		al.setSerial(serial);
		al.setSn(sn);
		return template.queryRow(sql, al, AcctingList.class);
	}

	/**
	 * 根据交易流水号获取分录流水记录集
	 * 
	 * @param tranSerial
	 * @return
	 */
	public List<AcctingList> getAcctListByTranSerial(String tranSerial) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = select_list + getAcctListTable()
				+ " where lis_trserial=#TRAN_SERIAL# ";
		AcctingList al = new AcctingList();
		al.setTranSerial(tranSerial);
		return template.query(sql, al, AcctingList.class);
	}

	/**
	 * 根据交易流水号获取未冲正的分录流水记录集
	 * 
	 * @param tranSerial
	 * @return
	 */
	public List<AcctingList> getAcctListNoReversalByTranSerial(String tranSerial) {
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql = select_list + getAcctListTable()
		+ " where lis_trserial=#TRAN_SERIAL# and lis_sign = '0' ";
		AcctingList al = new AcctingList();
		al.setTranSerial(tranSerial);
		return template.query(sql, al, AcctingList.class);
	}
	
	/**
	 * 按账号+记账流水号+分录序号排序，获取所有分录流水记录（过滤掉冲正流水）
	 * 
	 * @return
	 */
	public List<AcctingList> getAllOrderAcctList() {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = select_list + getAcctListTable()
				+ " where lis_sign = '0' order by ACC_NO,lis_serial,lis_sn ";
		return template.query(sql, null, AcctingList.class);
	}

	public void setAcctListTable(String acctListTable) {
		this.acctListTable = acctListTable;
	}

	public String getAcctListTable() {
		return this.acctListTable;
	}
}
