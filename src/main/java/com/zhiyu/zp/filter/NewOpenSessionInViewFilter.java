package com.zhiyu.zp.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

public class NewOpenSessionInViewFilter extends OpenSessionInViewFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
		try {
			Session session = sessionFactory.openSession();
            session.setFlushMode(FlushMode.AUTO);
            return session;
		} catch (Exception ex) {
			logger.debug("openSession exception:{}",ex);
			throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
		}
	}

	
}
