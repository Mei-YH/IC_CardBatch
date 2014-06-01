package com.icsys.batch.business.acct.dao;

import java.util.Collections;
import java.util.List;

import com.icsys.batch.business.acct.api.Account;
import com.icsys.batch.business.acct.api.AcctSubject;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public abstract class SubjectDAO {

	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;
	
	private static String selectlist = "SELECT CURRENCY_CODE AS CURR_TYPE, SUB_CODE AS SUBJECT_CODE, SUB_DES AS DESCRIPTION," +
			" SUB_CODE1, SUB_CODE2, SUB_CODE3, SUB_ACCTYPE AS ACCTING_TYPE, SUB_BALANCCD AS BALANCE_CD, " +
			"SUB_FACODE AS PARENT_SUB_CODE, SUB_SCODE AS SUM_SUB_CODE, SUB_CONFLAG AS SUB_CONTROL_FLAG FROM ";

	public static void addSubject(AcctSubject subject) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO gr_acc_sub(currency_code, sub_code, sub_des, SUB_CODE1, SUB_CODE2, SUB_CODE3, sub_acctype, sub_balanccd, sub_facode, sub_scode, sub_conflag) "
				+
				"VALUES(#CURR_TYPE#, #SUBJECT_CODE#, #DESCRIPTION#, #SUB_CODE1#, #SUB_CODE2#, #SUB_CODE3#, #ACCTING_TYPE#, #BALANCE_CD#, #PARENT_SUB_CODE#, #SUM_SUB_CODE#, #SUB_CONTROL_FLAG#)";
		template.update(sql, subject);
	}

	public static void updateSubject(AcctSubject subject) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "UPDATE gr_acc_sub SET sub_des=#DESCRIPTION#, SUB_CODE1=#SUB_CODE1#, SUB_CODE2=#SUB_CODE2#, SUB_CODE3=#SUB_CODE3#, "
				+
				"sub_acctype=#ACCTING_TYPE#, sub_balanccd=#BALANCE_CD#, sub_facode=#PARENT_SUB_CODE#, sub_scode=#SUM_SUB_CODE#, sub_conflag=#SUB_CONTROL_FLAG#"
				+
				"WHERE currency_code=#CURR_TYPE# AND sub_code=#SUBJECT_CODE#";
		template.update(sql, subject);
	}

	public static void deleteSubject(AcctSubject subject) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "DELETE gr_acc_sub "
				+ "WHERE currency_code=#CURR_TYPE# AND sub_code=#SUBJECT_CODE#";
		template.update(sql, subject);
	}

	public static List<AcctSubject> getAllSubjects() {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = selectlist + " gr_acc_sub";
		return template.query(sql, Collections.emptyMap(),
				AcctSubject.class);
	}

	public static AcctSubject getSubject(String currType, String subjectCode) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = selectlist + " gr_acc_sub where currency_code =#curr_type# and sub_code=#subject_code#";
		AcctSubject sub = new AcctSubject();
		sub.setCurrType(currType);
		sub.setSubjectCode(subjectCode);
		return template.queryRow(sql, sub, AcctSubject.class);
	}

	public static boolean isSubjectAccounted(String currType, String subjectCode) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from gr_acc_acct where currency_code =#curr_type# and subject_code=#subject_code#";
		AcctSubject sub = new AcctSubject();
		sub.setCurrType(currType);
		sub.setSubjectCode(subjectCode);
		return template.queryRow(sql, sub, Account.class)!=null;

	}
}
