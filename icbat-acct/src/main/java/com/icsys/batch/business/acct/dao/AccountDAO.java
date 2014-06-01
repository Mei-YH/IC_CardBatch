package com.icsys.batch.business.acct.dao;

import java.util.List;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acct.api.AcctStatus;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;
import com.icsys.platform.util.SystemProperties;

public class AccountDAO {
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	public void addAccount(Account account) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO GR_ACC_ACCT"
				+
				"(ACC_NO, APP_ID, ACC_NAME, ACC_TYPE, CURRENCY_CODE, "
				+
				"SUBJECT_CODE, ACC_OPENDATE, ACC_OPBRANCH, ACC_OPTELLER, "
				+
				"ACC_YESBAL, ACC_BALANCE, ACC_STPAYAMOUNT, "
				+
				"ACC_ABBALANCE, ACC_INTBASE, ACC_INTMODE, "
				+
				"ACC_INTRATE, "
				+
				"ACC_CONFLAG, ACC_BALCD, ACC_STATUS) "
				+
				"VALUES(#ACC_NO#, #APP_ID#, #ACC_NAME#, #ACC_TYPE#, #CURRENCY_CODE#, "
				+
				"#SUBJECT_CODE#, #ACC_OPENDATE#, #ACC_OPBRANCH#, #ACC_OPTELLER#, "
				+
				"#ACC_YESBAL#, #ACC_BALANCE#, #ACC_STPAYAMOUNT#, "
				+
				"#ACC_ABBALANCE#, #ACC_INTBASE#, #ACC_INTMODE#, "
				+
				"#ACC_INTRATE#, "
				+
				"#ACC_CONFLAG#, #ACC_BALCD#, #ACC_STATUS#)";
		template.update(sql, account);
	}

	public void updateAccount(Account account) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "UPDATE GR_ACC_ACCT "
				+
				"SET APP_ID=#APP_ID#, ACC_NAME=#ACC_NAME#, "
				+
				"ACC_TYPE=#ACC_TYPE#, CURRENCY_CODE=#CURRENCY_CODE#, SUBJECT_CODE=#SUBJECT_CODE#, "
				+
				"ACC_OPENDATE=#ACC_OPENDATE#, ACC_OPBRANCH=#ACC_OPBRANCH#, ACC_OPTELLER=#ACC_OPTELLER#, "
				+
				"ACC_YESBAL=#ACC_YESBAL#, ACC_BALANCE=#ACC_BALANCE#, "
				+
				"ACC_STPAYAMOUNT=#ACC_STPAYAMOUNT#, ACC_ABBALANCE=#ACC_ABBALANCE#, "
				+
				"ACC_INTBASE=#ACC_INTBASE#, ACC_INTMODE=#ACC_INTMODE#, "
				+
				"ACC_INTRATE=#ACC_INTRATE#, acc_ltdate=#acc_ltdate#, "
				+
				"acc_candate=#acc_candate#, acc_canteller=#acc_canteller#, "
				+
				"ACC_CONFLAG=#ACC_CONFLAG#, ACC_BALCD=#ACC_BALCD#, ACC_STATUS=#ACC_STATUS# "
				+
				"WHERE  ACC_NO=#ACC_NO#";
		template.update(sql, account);
	}

	/**
	 * 修改账务主档昨日余额
	 * 
	 * @param account
	 */
	public void updateLastBalance(Account account) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "UPDATE GR_ACC_ACCT"
				+ " SET ACC_YESBAL = #ACC_YESBAL# "
				+ " WHERE ACC_NO = #ACC_NO#";
		template.update(sql, account);
	}

	/**
	 * 修改账务主档状态为注销
	 * 
	 * @param acctNO
	 */
	public void updateAcctLogoff(String acctNO) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "UPDATE GR_ACC_ACCT"
				+ " SET ACC_STATUS = #ACC_STATUS# "
				+ " WHERE ACC_NO = #ACC_NO#";
		Account account = new Account();
		account.setAccNo(acctNO);
		account.setAccStatus(9); // 注销状态为9
		template.update(sql, account);
	}

	/**
	 * 根据账号和子账号查找帐务主档表
	 * 
	 * @param acctNO
	 * @param subAcctNO
	 * @return
	 */
	public Account getAccount(String acctNO) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from GR_ACC_ACCT where ACC_NO=#ACC_NO#";
		Account account = new Account();
		account.setAccNo(acctNO);
		Account result = template.queryRow(sql, account, Account.class);
		return result;
	}

	public List<Account> getAcctListByAcctNO(String acctNo) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from GR_ACC_ACCT where ACC_NO=#ACC_NO# ";
		Account account = new Account();
		account.setAccNo(acctNo);
		List<Account> accts = template.query(sql, account, Account.class);
		return accts;
	}

	/**
	 * 根据账务类型查找账务主档
	 * 
	 * @param acctType账务类型
	 * @return
	 */
	public List<Account> getAcctListByAcctType(String acctType) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from GR_ACC_ACCT where ACC_TYPE=#ACC_TYPE#";
		Account account = new Account();
		account.setAccType(acctType);
		List<Account> accts = template.query(sql, account, Account.class);
		return accts;
	}

	/**
	 * 根据账号查找帐务主档表,同时锁住该条记录
	 * 
	 * @param acctNO
	 * @param subAcctNO
	 * @return
	 */
	public Account lockAcct(String AcctNO) {
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String dbName = SystemProperties.get("database", "DB2");
	    String endSql = "";
	    if (("DB2".equals(dbName)) || ("db2".equals(dbName)) || ("Db2".equals(dbName))) {
	      endSql = " with RS";
	    }
		String sql = "select * from GR_ACC_ACCT where ACC_NO=#ACC_NO# for update " + endSql;
		Account account = new Account();
		account.setAccNo(AcctNO);
		Account result = template.queryRow(sql, account, Account.class);
		return result;
	}

	/**
	 * 撤销机构未销户的账户的启用机构更新成并入机构
	 * 
	 * @param closeBranch撤销机构
	 * @param mergeBranch并入机构
	 */
	public void updateAcctOpenBranch(String closeBranch, String mergeBranch) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "UPDATE GR_ACC_ACCT"
				+ " SET acc_opbranch = #acc_opbranch# "
				+ " WHERE acc_opbranch = #CLOSE_BRANCH# AND ACC_STATUS<>#ACC_STATUS#";
		Account account = new Account();
		account.setAccOpbranch(mergeBranch);
		account.setCloseBranch(closeBranch);
		account.setAccStatus(AcctStatus.CANCELD.getIntValue());
		template.update(sql, account);
	}
	/**
	 * 该方法主要是得到机构往来户的集合
	 * @param branch 机构号
	 * @param currType 货币代号
	 * @param subjectCode 科目代号
	 * @return
	 */
	public List<Account> getBatchNos(String branch,String currType,String subjectCode) {
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql="select * from GR_ACC_ACCT where ACC_NO like '"+branch+"00000"+currType+subjectCode+"%'";
		return template.query(sql, null, Account.class);
	}

}
