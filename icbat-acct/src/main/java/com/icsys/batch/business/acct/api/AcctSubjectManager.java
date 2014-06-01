package com.icsys.batch.business.acct.api;

import java.util.List;

public interface AcctSubjectManager {

	/**
	 * 获取指定科目信息
	 * 
	 * @param currType 币种
	 * @param subjectCode 科目代码
	 * @return 科目信息
	 * @throws NoSuchSubjectException
	 */
	public abstract AcctSubject getSubject(String currType, String subjectCode)
			throws NoSuchSubjectException;

	/**
	 * 获取所有科目信息
	 * 
	 * @return 科目列表
	 */
	public abstract List<AcctSubject> getAllSubjects();

	/**
	 * 增加科目
	 * 
	 * @param subject 科目对象
	 */
	public abstract String addSubject(AcctSubject subject);

	/**
	 * 修改科目
	 * 
	 * @param subject 科目对象
	 */
	public abstract void updateSubject(AcctSubject subject);

	/**
	 * 删除科目
	 * 
	 * @param currType 币种
	 * @param subjectCode 科目代码
	 */
	public abstract void removeSubject(String currType, String subjectCode);

	/**
	 * 科目是否已开户
	 * 
	 * @param currType 币种
	 * @param subjectCode 科目代码
	 * @return True代表科目已开户，False代表科目未开户
	 */
	public abstract boolean isSubjectAccounted(String currType,
			String subjectCode);

}