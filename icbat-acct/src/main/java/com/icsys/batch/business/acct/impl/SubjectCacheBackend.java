package com.icsys.batch.business.acct.impl;

import org.apache.log4j.Logger;

import com.icsys.batch.business.acct.api.AcctSubject;
import com.icsys.batch.business.acct.dao.SubjectDAO;
import com.icsys.platform.util.cache.PeristenceBackend;

public class SubjectCacheBackend implements PeristenceBackend {

	private static Logger LOG = Logger.getLogger(SubjectCacheBackend.class);

	public Object getFromPersitence(String key) {
		String[] temp = key.split(",");

		String currType = temp[0];
		String subjectCode = temp[1];
		if(LOG.isDebugEnabled()){
		LOG.debug("currType:" + currType);
		LOG.debug("code:" + subjectCode);
		}
		AcctSubject sub = SubjectDAO.getSubject(currType, subjectCode);
		return sub;
	}

	public String addToPersistence(Object obj) {
		AcctSubject subject = (AcctSubject) obj;
		try {
			String key = subject.getCurrType() + "," + subject.getSubjectCode();
			AcctSubject sub = (AcctSubject) getFromPersitence(key);
			if (sub == null) {
				SubjectDAO.addSubject(subject);
				return key;
			} else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

	public void deleteFromPeristence(String key) {
		String[] temp = key.split(",");
		String currType = temp[0];
		String subjectCode = temp[1];
		AcctSubject subject = new AcctSubject();
		subject.setCurrType(currType);
		subject.setSubjectCode(subjectCode);
		SubjectDAO.deleteSubject(subject);

	}

	public String getName() {
		return "subject";
	}

	public void saveOrUpdateToPersistence(String key, Object obj) {
		if (this.getFromPersitence(key) == null) {
			SubjectDAO.addSubject((AcctSubject) obj);
		} else {
			SubjectDAO.updateSubject((AcctSubject) obj);
		}
	}

}
