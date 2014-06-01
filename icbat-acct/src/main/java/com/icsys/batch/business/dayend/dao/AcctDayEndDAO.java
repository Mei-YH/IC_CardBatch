package com.icsys.batch.business.dayend.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acctsys.AcctSysUtil;
import com.icsys.batch.business.acctting.impl.AcctingList;
import com.icsys.batch.util.DB;
import com.icsys.batch.util.DBUtils;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public abstract class AcctDayEndDAO {

	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;
	
	private static Logger LOG = Logger.getLogger(AcctDayEndDAO.class);

	public static NamedUpdateTemplate getNamedUpdateTemplate() {
		return TemplateManager.getNamedUpdateTemplate(DS_NAME);
	}

	public static NamedQueryTemplate getNamedQueryTemplate() {
		return TemplateManager.getNamedQueryTemplate(DS_NAME);
	}

	/**
	 * 生成动户快照表
	 * 
	 */
	public static void buildSnapShot(String clearDate) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "INSERT INTO gr_acc_chgbal " +
				"(acc_no,ch_bacd,ch_ydbalance,ch_balance) " +
				"(SELECT acc_no,acc_balcd,acc_yesbal,acc_balance " +
				"FROM gr_acc_acct " +
				" WHERE acc_ltdate = #acc_candate#)";
		Account acct = new Account();
		acct.setAccCandate(clearDate);
		template.update(sql, acct);
	}

	/**
	 * 按照发生机构检查分录借贷平衡
	 * 
	 */
	public static List<BranchBalanceRecord> getUnBalancedBranch(
			String noCurAccListTable) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT ACCTING_BRANCH,CURR_TYPE,DIFF_BALANCE FROM "
				+ " (SELECT SUM(AMT) AS DIFF_BALANCE ,ACCTING_BRANCH,CURR_TYPE FROM ( "
				+ " SELECT SUM(lis_amount) AS AMT, lis_accbranch as ACCTING_BRANCH,currency_code as CURR_TYPE FROM "
				+ noCurAccListTable
				+ " WHERE lending_sign='1' AND lis_sign='0' "
				+ "GROUP BY lis_accbranch,currency_code "
				+ " UNION "
				+ " SELECT -1*SUM(lis_amount) AS AMT, lis_accbranch as ACCTING_BRANCH,currency_code as CURR_TYPE FROM "
				+ noCurAccListTable
				+ " WHERE lending_sign='2' AND lis_sign='0' " +
				"GROUP BY lis_accbranch,currency_code) "
				+ " GROUP BY ACCTING_BRANCH,CURR_TYPE) "
				+ " WHERE DIFF_BALANCE <> 0 ";
		if(LOG.isDebugEnabled()){
			LOG.debug(sql);
		}
		List<BranchBalanceRecord> bbrs = template.query(sql, null,
				BranchBalanceRecord.class);
		return bbrs;

	}

	/**
	 * 检查指定机构的每套分录流水是否平衡
	 * 
	 */
	public static List<DetailBalanceRecord> getUnBalancedSerial(String branch,
			String noCurAccListTable) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT ACCTING_BRANCH ,TRAN_SERIAL,CURR_TYPE, DIFF_BALANCE FROM "
				+ " (SELECT SUM(AMT) AS DIFF_BALANCE ,ACCTING_BRANCH,TRAN_SERIAL,CURR_TYPE FROM ( "
				+ " SELECT SUM(lis_amount) AS AMT, lis_accbranch as ACCTING_BRANCH,lis_trserial as TRAN_SERIAL,currency_code as CURR_TYPE FROM "
				+ noCurAccListTable
				+ " WHERE lending_sign='1' AND lis_sign = '0' AND lis_accbranch = #ACCTING_BRANCH#"
				+ " GROUP BY lis_accbranch,lis_trserial,currency_code "
				+ " UNION "
				+ " SELECT -1*SUM(lis_amount) AS AMT,lis_accbranch as ACCTING_BRANCH,lis_trserial as TRAN_SERIAL,currency_code as CURR_TYPE FROM "
				+ noCurAccListTable
				+ " WHERE lending_sign='2' AND lis_sign = '0' AND lis_accbranch = #ACCTING_BRANCH#"
				+ " GROUP BY lis_accbranch,lis_trserial,currency_code) "
				+ " GROUP BY ACCTING_BRANCH,TRAN_SERIAL,CURR_TYPE) "
				+ " WHERE DIFF_BALANCE <> 0 ";
		
		if(LOG.isDebugEnabled()){
			LOG.debug(sql);
		}
		DetailBalanceRecord dbr = new DetailBalanceRecord();
		dbr.setAcctingBranch(branch);
		List<DetailBalanceRecord> dbrs = template.query(sql, dbr,
				DetailBalanceRecord.class);
		return dbrs;

	}

	/**
	 * 从分录流水表中统计日结数据
	 * 
	 */
	public static List<DailyClosingData> getDailyClosingDataList(
			String noCurAccListTable) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		// 借方子查询语句
		String dSql = " SELECT COUNT(lis_amount) AS DCOUNT, 0 AS CCOUNT, "
				+ "SUM(lis_amount) AS DAMOUNT, 0 AS CAMOUNT, "
				+ " lis_accbranch as ACCTING_BRANCH, currency_code as CURR_TYPE, sub_code as SUBJECT_CODE FROM "
				+ noCurAccListTable + " WHERE lending_sign = '1' "
				+ "AND lis_sign = '0' "
				+ " GROUP BY lis_accbranch, currency_code, sub_code ";

		// 贷方子查询语句
		String cSql = " SELECT 0 AS DCOUNT, COUNT(lis_amount) AS CCOUNT, "
				+ " 0 AS DAMOUNT, SUM(lis_amount) AS CAMOUNT, "
				+ " lis_accbranch as ACCTING_BRANCH, currency_code as CURR_TYPE, sub_code as SUBJECT_CODE FROM "
				+ noCurAccListTable + " WHERE lending_sign = '2' "
				+ "AND lis_sign = '0' "
				+ " GROUP BY lis_accbranch, currency_code, sub_code ";

		// 联合查询语句
		String sql = "SELECT SUM(DCOUNT) AS DEBIT_COUNT, SUM(CCOUNT) AS CREDIT_COUNT, "
				+ "SUM(DAMOUNT) AS DEBIT_SUM, SUM(CAMOUNT) AS CREDIT_SUM, "
				+ " ACCTING_BRANCH, CURR_TYPE, SUBJECT_CODE "
				+ " FROM ("
				+ dSql
				+ " UNION "
				+ cSql
				+ ") "
				+ " GROUP BY ACCTING_BRANCH, CURR_TYPE, SUBJECT_CODE";
		
		if(LOG.isDebugEnabled()){
			LOG.debug(sql);
		}
		List<DailyClosingData> dcds = template.query(sql, null,
				DailyClosingData.class);
		return dcds;

	}

	/**
	 * 快速清表
	 * 
	 * @param table
	 * 
	 */
	public static void truncate(String table) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "";
		if (DBUtils.getDatabaseType() == DB.DB2)
//			sql = "ALTER TABLE " + table
//					+ " ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE";
			sql = "delete from " + table;
		else
			sql = "TRUNCATE TABLE " + table;
		template.update(sql, null);
	}

	public static void moveNoCurrentAcctListToHistory(String noCurrentAcctListTableName){
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "INSERT INTO IC_ACC_LIST_H (SELECT * FROM "+noCurrentAcctListTableName+")";
		template.update(sql, null);
	}
	
	/**
	 * 获取科目字典信息
	 * 
	 * @param subjectCode科目代码
	 * @param currType币种
	 * @return
	 */
	public static Dictionary getSubDictionary(String subjectCode,
			String currType) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT CURRENCY_CODE AS CURR_TYPE, SUB_CODE AS SUBJECT_CODE, SUB_DES AS DESCRIPTION, SUB_CODE1, SUB_CODE2, SUB_CODE3, SUB_ACCTYPE AS ACCTING_TYPE, SUB_BALANCCD AS BALANCE_CD, SUB_FACODE AS PARENT_SUB_CODE, SUB_SCODE AS SUM_SUB_CODE, SUB_CONFLAG AS SUB_CONTROL_FLAG FROM gr_acc_sub WHERE currency_code = #CURR_TYPE# and sub_code = #SUBJECT_CODE#";
		Dictionary dictionary = new Dictionary();
		dictionary.setSubjectCode(subjectCode);
		dictionary.setCurrType(currType);
		Dictionary result = template
				.queryRow(sql, dictionary, Dictionary.class);
		return result;

	}

	/**
	 * 获取总账信息
	 * 
	 * @param branchCode机构代码
	 * @param currType币种
	 * @param subjectCode科目代码
	 * @return
	 */
	public static GeneralLedgerDay getGeneralLedger(String branchCode,
			String currType, String subjectCode) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT * FROM gr_acc_total " +
				"WHERE BRANCH_CODE = #BRANCH_CODE# AND CURR_TYPE = #CURR_TYPE# AND SUBJECT_CODE = #SUBJECT_CODE#";
		GeneralLedgerDay gengl = new GeneralLedgerDay();
		gengl.setBranchCode(branchCode);
		gengl.setSubjectCode(subjectCode);
		gengl.setCurrType(currType);
		GeneralLedgerDay result = template
				.queryRow(sql, gengl, GeneralLedgerDay.class);
		return result;

	}

	/**
	 * 获取月总账信息
	 * 
	 * @param branchCode机构代码
	 * @param currType币种
	 * @param subjectCode科目代码
	 * @param month月份
	 * @return
	 */
	public static GeneralLedgerMonth getGeneralLedgerM(String branchCode,
			String currType, String subjectCode, int month) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT BRANCH_CODE,currency_code as CURR_TYPE,SUBJECT_CODE,MONTHES,lending_sign as BALANCE_CD,gem_stbalance as START_BALANCE,gem_cdsum as CURRENT_DEBIT_SUM,gem_ccsum as CURRENT_CREDIT_SUM,BALANCE,gem_cdcount as CURRENT_DEBIT_COUNT,gem_cccount as CURRENT_CREDIT_COUNT FROM gr_acc_gem " +
				"WHERE BRANCH_CODE = #BRANCH_CODE# " +
				"AND currency_code = #CURR_TYPE# " +
				"AND SUBJECT_CODE = #SUBJECT_CODE# " +
				"AND MONTHES = #MONTHES#";
		GeneralLedgerMonth genglM = new GeneralLedgerMonth();
		genglM.setBranchCode(branchCode);
		genglM.setSubjectCode(subjectCode);
		genglM.setCurrType(currType);
		genglM.setMonthes(month);
		GeneralLedgerMonth result = template
				.queryRow(sql, genglM, GeneralLedgerMonth.class);
		return result;

	}
	
	/**
	 * 获取月总账中的所有记录
	 */
	public static List<GeneralLedgerMonth> findAllGeneralLedgerM(){
		NamedQueryTemplate template =  TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = " SELECT BRANCH_CODE,currency_code as CURR_TYPE,SUBJECT_CODE,MONTHES,lending_sign as BALANCE_CD,gem_stbalance as START_BALANCE,gem_cdsum as CURRENT_DEBIT_SUM,gem_ccsum as CURRENT_CREDIT_SUM,BALANCE,gem_cdcount as CURRENT_DEBIT_COUNT,gem_cccount as CURRENT_CREDIT_COUNT FROM gr_acc_gem ";
		return template.query(sql, new GeneralLedgerMonth(), GeneralLedgerMonth.class);
	}

	public static GeneralLedgerYear getGeneralLedgerY(String branchCode,
			String currType, String subjectCode, int year, int month) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT YEARS, gen_bracode as BRANCH_CODE, currency_code as CURR_TYPE, SUBJECT_CODE, MONTHES, gen_balcd as BALANCE_CD, gen_stbalance as START_BALANCE, gen_cdsum as CURRENT_DEBIT_SUM, gen_ccsum as CURRENT_CREDIT_SUM, BALANCE, gen_dcsum as CURRENT_DEBIT_COUNT, gen_ddsum as CURRENT_CREDIT_COUNT FROM gr_acc_genyear " +
				"WHERE gen_bracode = #BRANCH_CODE# " +
				"AND currency_code = #CURR_TYPE# " +
				"AND SUBJECT_CODE = #SUBJECT_CODE# " +
				"AND MONTHES = #MONTHES# " +
				"AND YEARS=#YEARS#";
		GeneralLedgerYear genglY = new GeneralLedgerYear();
		genglY.setBranchCode(branchCode);
		genglY.setSubjectCode(subjectCode);
		genglY.setCurrType(currType);
		String years = String.valueOf(year);
		genglY.setYears(years);
		genglY.setMonthes(month);
		GeneralLedgerYear result = template
				.queryRow(sql, genglY, GeneralLedgerYear.class);
		return result;
	}

	/**
	 * 增加科目日结或总账明细表数据
	 * 
	 * @param glRecordDetail总账明细数据
	 * @param type
	 *            1=总账明细表 2=科目日结表
	 */
	public static void addGeneralLedgerDetail(
			GeneralLedgerDetail glRecordDetail,
			int type) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		// 总账明细
		String table = "gr_acc_gedet";
		if (type == 2) {
			// 科目日结
			table = "ged_accdate";
		}
		String sql = "INSERT INTO "
				+ table
				+
				"(ged_accdate, BRANCH_CODE, CURR_TYPE, SUBJECT_CODE, ged_acctsn, lending_sign, ged_ydbal, ged_tdsum, ged_tcsum, BALANCE, ged_tdcount, ged_tccount) "
				+
				"VALUES(#ACCTING_DATE#, #BRANCH_CODE#, #CURR_TYPE#, #SUBJECT_CODE#, #ACCT_SN#, #BALANCE_CD#, #LAST_DAY_BALANCE#, #TODAY_DEBIT_SUM#, #TODAY_CREDIT_SUM#, #BALANCE#, #TODAY_DEBIT_COUNT#, #TODAY_CREDIT_COUNT#) ";
		template.update(sql, glRecordDetail);
	}

	/**
	 * 获取科目日结数据
	 * 
	 * @return
	 */
	public static List<GeneralLedgerDetail> getGeneralDetail() {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT ged_accdate as ACCTING_DATE,branch_code,curr_type,subject_code,ged_acctsn as ACCT_SN,lending_sign as BALANCE_CD,ged_ydbal as LAST_DAY_BALANCE,ged_tdsum as TODAY_DEBIT_SUM,ged_tcsum as TODAY_CREDIT_SUM,balance,ged_tdcount as TODAY_DEBIT_COUNT,ged_tccount as TODAY_CREDIT_COUNT FROM gr_acc_subdate";
		
		if(LOG.isDebugEnabled()){
			LOG.debug(sql);
		}
		GeneralLedgerDetail gld = new GeneralLedgerDetail();
		List<GeneralLedgerDetail> gds = template.query(sql, gld,
				GeneralLedgerDetail.class);
		return gds;
	}

	/**
	 * 修改总账数据
	 * 
	 * @param gengl
	 */
	public static void updateGeneralLedger(GeneralLedgerDay gengl) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "UPDATE gr_acc_total SET " +
				"START_BALANCE=#START_BALANCE#,MAX_SERIAL=#MAX_SERIAL#," +
				"DEBIT_SUM=#DEBIT_SUM#," +
				"CREDIT_SUM=#CREDIT_SUM#," +
				"BALANCE=#BALANCE#,BALANCE_CD=#BALANCE_CD#," +
				"DEBIT_COUNT=#DEBIT_COUNT#," +
				"CREDIT_COUNT=#CREDIT_COUNT# " +
				"WHERE BRANCH_CODE=#BRANCH_CODE# " +
				"AND CURR_TYPE=#CURR_TYPE# " +
				"AND SUBJECT_CODE=#SUBJECT_CODE# ";
		template.update(sql, gengl);
	}

	/**
	 * 修改月总账数据
	 * 
	 * @param genglM
	 */
	public static void updateGeneralLedgerM(GeneralLedgerMonth genglM) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "UPDATE gr_acc_gem SET " +
				"gem_stbalance=#START_BALANCE#," +
				"gem_cdsum=#CURRENT_DEBIT_SUM#," +
				"gem_ccsum=#CURRENT_CREDIT_SUM#," +
				"BALANCE=#BALANCE#,lending_sign=#BALANCE_CD#," +
				"gem_cdcount=#CURRENT_DEBIT_COUNT#," +
				"gem_cccount=#CURRENT_CREDIT_COUNT# " +
				"WHERE BRANCH_CODE=#BRANCH_CODE# " +
				"AND currency_code=#CURR_TYPE# " +
				"AND SUBJECT_CODE=#SUBJECT_CODE# " +
				"AND MONTHES=#MONTHES#";
		template.update(sql, genglM);
	}

	/**
	 * 修改月总账数据（月份变为下个月）
	 * modify by chenyc 20120109
	 *  修改为下一个月时，月初余额为上一月余额，交易笔数和金额均为0
	 * @param genglM
	 */
	public static void updateGeneralMNewMonth(GeneralLedgerMonth genglM) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();

		String sql = "UPDATE gr_acc_gem SET MONTHES=#NEXT_MONTH#, " +
		"gem_stbalance=#BALANCE#," +
		"gem_cdsum=0," +
		"gem_ccsum=0," +
		"gem_cdcount=0," +
		"gem_cccount=0 " +
		"WHERE BRANCH_CODE=#BRANCH_CODE# " +
		"AND currency_code=#CURR_TYPE# " +
		"AND SUBJECT_CODE=#SUBJECT_CODE# " +
		"AND MONTHES=#MONTHES#";
		
		template.update(sql, genglM);

	}

	/**
	 * 增加年总账数据
	 * 
	 * @param genglY年总账数据
	 */
	public static void addGeneralLedgerY(GeneralLedgerYear genglY) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "INSERT INTO gr_acc_genyear(YEARS, gen_bracode, currency_code, SUBJECT_CODE, MONTHES, gen_balcd, gen_stbalance, gen_cdsum, gen_ccsum, BALANCE, gen_dcsum, gen_ddsum) "
				+
				"VALUES(#YEARS#, #BRANCH_CODE#, #CURR_TYPE#, #SUBJECT_CODE#, #MONTHES#, #BALANCE_CD#, #START_BALANCE#, #CURRENT_DEBIT_SUM#, #CURRENT_CREDIT_SUM#, #BALANCE#, #CURRENT_DEBIT_COUNT#, #CURRENT_CREDIT_COUNT#) ";
		template.update(sql, genglY);
	}

	/**
	 * 修改年总账数据
	 * 
	 * @param genglY年总账数据
	 */
	public static void updateGeneralLedgerY(GeneralLedgerYear genglY) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "UPDATE gr_acc_genyear SET " +
				"gen_stbalance=#START_BALANCE#," +
				"gen_cdsum=#CURRENT_DEBIT_SUM#," +
				"gen_ccsum=#CURRENT_CREDIT_SUM#," +
				"BALANCE=#BALANCE#,gen_balcd=#BALANCE_CD#," +
				"gen_dcsum=#CURRENT_DEBIT_COUNT#," +
				"gen_ddsum=#CURRENT_CREDIT_COUNT# " +
				"WHERE gen_bracode=#BRANCH_CODE# " +
				"AND currency_code=#CURR_TYPE# " +
				"AND SUBJECT_CODE=#SUBJECT_CODE# " +
				"AND MONTHES=#MONTHES# AND YEARS=#YEARS#";
		template.update(sql, genglY);
	}

	/**
	 * 增加分录不平记录
	 * 
	 * @param dbr
	 */
	public static void addDetailBalance(DetailBalanceRecord dbr) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "INSERT INTO GR_DETAIL_BALANCE VALUES(#ROLL_OFF_DATE#,#TRAN_SERIAL#,#ACCTING_BRANCH#,#CURR_TYPE#,#DIFF_BALANCE#)";
		template.update(sql, dbr);

	}
	
	/**
	 * 删除分录不平记录
	 * 
	 * @param dbr
	 */
	public static void deleteDetailBalance(String rollOffDate) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "DELETE FROM GR_DETAIL_BALANCE WHERE ROLL_OFF_DATE = '"+rollOffDate+"'";
		template.update(sql, null);

	}
	
	
	/**
	 * 增加时序不平记录
	 * 
	 * @param sbr
	 */
	public static void addSequenceBalance(SeqBalanceRecord sbr) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "INSERT INTO GR_SEQUENCE_BALANCE VALUES(#ROLL_OFF_DATE#,#ACCT_NO#,#LAST_DAY_BALANCE#,#BALANCE#,#BALANCE_CD#,#CAL_BALANCE#)";
		template.update(sql, sbr);

	}
	
	/**
	 * 删除时序不平记录
	 * @param rollOffDate
	 */
	public static void deleteSequenceBalance(String rollOffDate) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "DELETE FROM  GR_SEQUENCE_BALANCE  WHERE ROLL_OFF_DATE = '"+rollOffDate+"'";
		template.update(sql, null);

	}
	
	

	/**
	 * 获取指定账号的动户余额快照
	 * 
	 * @param acctNO
	 * @return
	 */
	public static ChangeAccount getChangeAccountByKey(String acctNO) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		ChangeAccount ca = new ChangeAccount();
		String sql = "SELECT acc_no as ACCT_NO,ch_bacd as BALANCE_CD,ch_ydbalance as LAST_DAY_BALANCE,ch_balance as BALANCE FROM gr_acc_chgbal WHERE ACC_NO=#ACCT_NO#";
		ca.setAcctNO(acctNO);
		ChangeAccount result = template.queryRow(sql, ca, ChangeAccount.class);
		return result;
	}
	
	/**
	 * 在动户余额快照中，获取所有金额发生表动的账户
	 * @return
	 */
	public static List<ChangeAccount> getChangeAccounts() {
		NamedQueryTemplate template = getNamedQueryTemplate();
		ChangeAccount ca = new ChangeAccount();
		String sql = "SELECT acc_no as ACCT_NO,ch_bacd as BALANCE_CD,ch_ydbalance as LAST_DAY_BALANCE,ch_balance as BALANCE FROM gr_acc_chgbal ";
		return template.query(sql, ca, ChangeAccount.class);
	}
	
	public static List<AcctingList> getAcctListByAccount(String acctNo){
		NamedQueryTemplate template = getNamedQueryTemplate();
		String acctListTableName = AcctSysUtil.getInstance().getAccListTable(false);
		AcctingList al = new AcctingList();
		al.setAcctNO(acctNo);
		String select_list = "SELECT LIS_SERIAL AS SERIAL, LIS_TRSERIAL AS ORG_SERIAL LIS_ORGSERIAL, LIS_SN AS SN, LIS_ACCDATE AS ACCTING_DATE, LIS_TRBRANCH AS TRAN_BRANCH, LIS_ACCBRANCH AS ACCTING_BRANCH, LIS_TELLER AS OPERATOR, CURRENCY_CODE AS CURR_TYPE, ACC_NO AS ACCT_NO, SUB_CODE AS SUBJECT_CODE, LENDING_SIGN AS ACCT_CD, LIS_AMOUNT AS AMOUNT, LIS_REMARK AS REMARK, LIS_SIGN AS SERIAL_FLAG FROM ";
		String sql = select_list + acctListTableName+" WHERE ACC_NO=#ACCT_NO# and LIS_SIGN='0'";
		return template.query(sql, al, AcctingList.class);
	}

	/**
	 * 增加账务明细
	 * 
	 * @param accountDetail
	 */
	public static void addAccountDetail(AccountDetail accountDetail) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "INSERT INTO gr_acc_det VALUES ("
				+ "#ACCT_NO#, #TRAN_DATE#, #ACCTING_DATE#, "
				+ "#SERIAL#, #SN#, #TRAN_BRANCH#, "
				+ "#TRAN_CD#, #TRAN_AMOUNT#, #FEE#, #BALANCE#, "
				+ "#TRAN_TELLER#, #REMARK#)";
		template.update(sql, accountDetail);
	}

	/**
	 * 获取总账不平记录集
	 * 
	 * @return
	 */
	public static List<AcctBalanceRecord> getUnBalancedGeneralLedger() {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT BRANCH_CODE,CURR_TYPE, DIFF_BALANCE FROM "
				+ "(SELECT SUM(AMT) AS DIFF_BALANCE ,BRANCH_CODE,CURR_TYPE "
				+ " FROM ( "
				+ " SELECT SUM(BALANCE) AS AMT, BRANCH_CODE, CURR_TYPE FROM gr_acc_total "
				+ " WHERE BALANCE_CD='1' GROUP BY BRANCH_CODE,CURR_TYPE "
				+ " UNION "
				+ " SELECT -1*SUM(BALANCE) AS AMT, BRANCH_CODE, CURR_TYPE FROM gr_acc_total "
				+ " WHERE BALANCE_CD='2' GROUP BY BRANCH_CODE,CURR_TYPE "
				+ " ) "
				+ " GROUP BY BRANCH_CODE,CURR_TYPE "
				+ " ) " + "WHERE DIFF_BALANCE <> 0 ";
		List<AcctBalanceRecord> result = template.query(sql, null,
				AcctBalanceRecord.class);
		return result;

	}

	/**
	 * 增加账务不平记录
	 * 
	 * @param abr
	 */
	public static void addAccountBalance(AcctBalanceRecord abr) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "INSERT INTO gr_acc_accbal VALUES(#ROLL_OFF_DATE#,#BRANCH_CODE#,#CURR_TYPE#,#SUBJECT_CODE#,#DIFF_BALANCE#,#BALANCE#,#CAL_BALANCE#)";
		template.update(sql, abr);

	}
	/**
	 * 
	 * @param abr
	 */
	public static void deleteAccountBalance(String rollOffDate) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "DELETE FROM  gr_acc_accbal  WHERE bal_offdate = '"+rollOffDate+"'";
		template.update(sql, null);

	}

	/**
	 * 按照机构代码+币种+科目代码，统计帐务主档分户账余额合计
	 * 
	 * @return
	 */
	public static List<AcctBalanceRecord> getAcctDebitAmount() {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT SUM(AMT) AS CAL_BALANCE ,CURR_TYPE, BRANCH_CODE,SUBJECT_CODE "
				+ " FROM ( "
				+ " SELECT SUM(acc_yesbal) AS AMT, currency_code AS CURR_TYPE, acc_opbranch AS BRANCH_CODE,SUBJECT_CODE "
				+ " FROM gr_acc_acct "
				+ " WHERE acc_balcd='1' GROUP BY currency_code, acc_opbranch,SUBJECT_CODE "
				+ " UNION "
				+ " SELECT -1*SUM(acc_yesbal) AS AMT, currency_code AS CURR_TYPE, acc_opbranch AS BRANCH_CODE,SUBJECT_CODE "
				+ " FROM gr_acc_acct "
				+ " WHERE acc_balcd='2' GROUP BY currency_code, acc_opbranch,SUBJECT_CODE "
				+ ") "
				+ " GROUP BY CURR_TYPE, BRANCH_CODE,SUBJECT_CODE ";
		List<AcctBalanceRecord> result = template.query(sql, null,
				AcctBalanceRecord.class);
		return result;

	}
}
