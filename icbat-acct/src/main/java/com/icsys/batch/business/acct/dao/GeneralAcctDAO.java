package com.icsys.batch.business.acct.dao;

import java.util.List;

import com.icsys.batch.business.acct.impl.GeneralAcct;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public abstract class GeneralAcctDAO {

	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	public static NamedUpdateTemplate getNamedUpdateTemplate() {
		return TemplateManager.getNamedUpdateTemplate(DS_NAME);
	}

	public static NamedQueryTemplate getNamedQueryTemplate() {
		return TemplateManager.getNamedQueryTemplate(DS_NAME);
	}

	/**
	 * 开总账
	 * 
	 * @param acct
	 */
	public static void add(GeneralAcct acct) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "insert into gr_acc_total "
				+ " values (#BRANCH_CODE#,#CURR_TYPE#,#SUBJECT_CODE#,0,#BALANCE_CD#,0,0,0,0,0,0)";
		template.update(sql, acct);
	}

	/**
	 * 开月总账
	 * 
	 * @param acct
	 */
	public static void addM(GeneralAcct acct) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "insert into gr_acc_gem values (#BRANCH_CODE#,#CURR_TYPE#,#SUBJECT_CODE#,#MONTHES#,#BALANCE_CD#,0,0,0,0,0,0)";
		template.update(sql, acct);
	}

	/**
	 * 开年总账
	 * 
	 * @param acct
	 */
	public static void addY(GeneralAcct acct) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "insert into gr_acc_genyear values (#YEARS#,#BRANCH_CODE#,#CURR_TYPE#,#SUBJECT_CODE#,#MONTHES#,#BALANCE_CD#,0,0,0,0,0,0)";
		template.update(sql, acct);
	}

	public static void update(GeneralAcct acct) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "update gr_acc_total set MAX_SERIAL= #MAX_SERIAL#,"
				+ "START_BALANCE= #START_BALANCE#,CREDIT_SUM= #CREDIT_SUM#,DEBIT_SUM= #DEBIT_SUM#,"
				+ "CREDIT_COUNT= #CREDIT_COUNT#,DEBIT_COUNT= #DEBIT_COUNT#,BALANCE= #BALANCE# "
				+ "where BRANCH_CODE=#BRANCH_CODE# and CURR_TYPE=#CURR_TYPE# "
				+ " and SUBJECT_CODE=#SUBJECT_CODE# ";
		template.update(sql, acct);
	}

	public static void updateM(GeneralAcct acct) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "update gr_acc_gem set "
			+ "gem_stbalance= #START_BALANCE#,gem_ccsum= #CURRENT_CREDIT_SUM#,gem_cdsum= #CURRENT_DEBIT_SUM#,"
			+ "gem_cccount= #CURRENT_CREDIT_COUNT#,gem_cdcount= #CURRENT_DEBIT_COUNT#,BALANCE= #BALANCE# "
			+ "where BRANCH_CODE=#BRANCH_CODE# and currency_code=#CURR_TYPE# "
			+ " and SUBJECT_CODE=#SUBJECT_CODE# and MONTHES=#MONTHES#";
		template.update(sql, acct);
	}
	
	public static void updateY(GeneralAcct acct) {
		NamedUpdateTemplate template = getNamedUpdateTemplate();
		String sql = "update gr_acc_genyear set "
			+ "gen_stbalance= #START_BALANCE#,gen_ccsum= #CURRENT_CREDIT_SUM#,gen_cdsum= #CURRENT_DEBIT_SUM#,"
			+ "gen_dcsum= #CURRENT_CREDIT_COUNT#,gen_ddsum= #CURRENT_DEBIT_COUNT#,BALANCE= #BALANCE# "
			+ "where BRANCH_CODE=#BRANCH_CODE# and currency_code=#CURR_TYPE# "
			+ " and SUBJECT_CODE=#SUBJECT_CODE# and MONTHES=#MONTHES# and YEARS=#YEARS#";
		template.update(sql, acct);
	}
	
	public static GeneralAcct getForUpdate(String branchCode,
			String currType, String subjectCode) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "select * from gr_acc_total where "
				+ "BRANCH_CODE=#BRANCH_CODE# " + "and CURR_TYPE=#CURR_TYPE# "
				+ "and SUBJECT_CODE=#SUBJECT_CODE# for update";
		GeneralAcct account = new GeneralAcct();
		account.setBranchCode(branchCode);
		account.setCurrType(currType);
		account.setSubjectCode(subjectCode);
		GeneralAcct result = template.queryRow(sql, account, GeneralAcct.class);
		return result;
	}

	public static GeneralAcct get(String branchCode, String currType,
			String subjectCode) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "select * from gr_acc_total where "
				+ "BRANCH_CODE=#BRANCH_CODE# " + "and CURR_TYPE=#CURR_TYPE# "
				+ "and SUBJECT_CODE=#SUBJECT_CODE# ";
		GeneralAcct account = new GeneralAcct();
		account.setBranchCode(branchCode);
		account.setCurrType(currType);
		account.setSubjectCode(subjectCode);
		GeneralAcct result = template.queryRow(sql, account, GeneralAcct.class);
		return result;

	}

	public static GeneralAcct getM(String branchCode, String currType,
			String subjectCode,Integer monthes) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT BRANCH_CODE,currency_code as CURR_TYPE,SUBJECT_CODE,MONTHES,lending_sign as BALANCE_CD,gem_stbalance as START_BALANCE,gem_cdsum as CURRENT_DEBIT_SUM,gem_ccsum as CURRENT_CREDIT_SUM,BALANCE,gem_cdcount as CURRENT_DEBIT_COUNT,gem_cccount as CURRENT_CREDIT_COUNT FROM gr_acc_gem where "
			+ "BRANCH_CODE=#BRANCH_CODE# " + "and currency_code=#CURR_TYPE# "
			+ "and SUBJECT_CODE=#SUBJECT_CODE# and MONTHES=#MONTHES#";
		GeneralAcct account = new GeneralAcct();
		account.setBranchCode(branchCode);
		account.setCurrType(currType);
		account.setSubjectCode(subjectCode);
		account.setMonthes(monthes);
		GeneralAcct result = template.queryRow(sql, account, GeneralAcct.class);
		return result;
		
	}
	
	public static GeneralAcct getY(String branchCode, String currType,
			String subjectCode,Integer monthes,String years) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "SELECT YEARS, gen_bracode as BRANCH_CODE, currency_code as CURR_TYPE, SUBJECT_CODE, MONTHES, gen_balcd as BALANCE_CD, gen_stbalance as START_BALANCE, gen_cdsum as CURRENT_DEBIT_SUM, gen_ccsum as CURRENT_CREDIT_SUM, BALANCE, gen_dcsum as CURRENT_DEBIT_COUNT, gen_ddsum as CURRENT_CREDIT_COUNT FROM gr_acc_genyear where "
			+ "gen_bracode=#BRANCH_CODE# " + "and currency_code=#CURR_TYPE# "
			+ "and SUBJECT_CODE=#SUBJECT_CODE# and MONTHES=#MONTHES# and YEARS=#YEARS#";
		GeneralAcct account = new GeneralAcct();
		account.setBranchCode(branchCode);
		account.setCurrType(currType);
		account.setSubjectCode(subjectCode);
		account.setMonthes(monthes);
		account.setYears(years);
		GeneralAcct result = template.queryRow(sql, account, GeneralAcct.class);
		return result;
		
	}
	
	/**
	 * 根据机构代码获取总账信息
	 * 
	 * @param branchCode机构代码
	 * @return
	 */
	public static List<GeneralAcct> getByBranch(String branchCode) {
		NamedQueryTemplate template = getNamedQueryTemplate();
		String sql = "select * from gr_acc_total where "
				+ "BRANCH_CODE=#BRANCH_CODE# ";
		GeneralAcct account = new GeneralAcct();
		account.setBranchCode(branchCode);
		return template.query(sql, account, GeneralAcct.class);

	}
}
