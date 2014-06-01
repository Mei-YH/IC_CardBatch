package com.icsys.batch.business.acct.impl;

import java.util.List;

import com.icsys.batch.business.acct.api.AcctSubject;
import com.icsys.batch.business.acct.api.AcctSubjectManager;
import com.icsys.batch.business.acct.api.NoSuchSubjectException;
import com.icsys.batch.business.acct.dao.SubjectDAO;
import com.icsys.platform.util.cache.CachableRepos;
import com.icsys.platform.util.cache.CacheUtils;
import com.icsys.platform.util.cache.NoSuchObjectException;

public class AcctSubjectManagerImpl {


	public AcctSubjectManagerImpl() {
		super();
//		this.subjectCache = CachableRepos.getInstanceByName("subject");
	}

	public AcctSubject getSubject(String currType, String subjectCode)
			throws NoSuchSubjectException {
//		try {
//			return (AcctSubject) this.subjectCache.getByKey(currType + ","
//					+ subjectCode);
//		} catch (NoSuchObjectException e) {
//			throw new NoSuchSubjectException(currType, subjectCode);
//		}
		
		return SubjectDAO.getSubject(currType, subjectCode);
	}

//	public String addSubject(AcctSubject subject) {
//		String key = this.subjectCache.add(subject);
//		return key;
//	}

	public void removeSubject(String currType, String subjectCode) {
//		String key = currType + "," + subjectCode;
//		this.subjectCache.delete(key);
		AcctSubject subject = new AcctSubject();
		subject.setCurrType(currType);
		subject.setSubjectCode(subjectCode);
		SubjectDAO.deleteSubject(subject);
	}

	public void updateSubject(AcctSubject subject) {
//		String key = subject.getCurrType() + "," + subject.getSubjectCode();
//		this.subjectCache.update(key, subject);
		SubjectDAO.updateSubject(subject);

	}

	public List<AcctSubject> getAllSubjects() {
		return SubjectDAO.getAllSubjects();
	}

	public boolean isSubjectAccounted(String currType, String subjectCode) {
		return SubjectDAO.isSubjectAccounted(currType, subjectCode);
	}

}
