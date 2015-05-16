package com.intelliinvest.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	
	private static final ThreadLocal<Session> local = new ThreadLocal<Session>(); 
	
	private static Logger logger = Logger.getLogger(HibernateUtil.class);
	
	private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

	static {
		try {
			logger.info("Initialising hibernate sessionFactory ");
			Configuration configuration = new Configuration();
		    configuration.configure();
		    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
		    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		    logger.info("Hibernate sessionFactory Created");
		}catch (Throwable ex) {
			logger.error("Initial SessionFactory creation failed." , ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static void openSession() {
		Session session = sessionFactory.openSession(); 
		session.beginTransaction();
		local.set(session); 
	}
	public static Session getSession() {
		Session session = local.get(); 
		if(session == null) { 
			openSession();
			session = local.get(); 
		} 
		return session;
	}	
	
	public static void closeSession() {
		Session session = local.get(); 
		session.flush();
		session.getTransaction().commit();
		session.close();
	}	
	
	public static void rollbackSession() {
		Session session = local.get(); 
		session.getTransaction().rollback();
		session.close();
	}	
	
}