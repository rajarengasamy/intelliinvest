package com.intelliinvest.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public class KeyStoreDAO {

	private static Logger logger = Logger.getLogger(KeyStoreDAO.class);
	private static String ID_GENERATOR = "SELECT value from KEY_STORE where type=:type";
	private static String ID_INCREMENTOR = "UPDATE KEY_STORE set value=value+1  where type=:type";
	
	
	private KeyStoreDAO() {
	}
	
	@SuppressWarnings("unchecked")
	public synchronized Integer generateKey(String type, Session session) throws Exception{
		try{
			logger.info("Incrementing id for " + type);
			List<Integer> newKey = session.createSQLQuery(ID_GENERATOR).setParameter("type", type).list();
			session.createSQLQuery(ID_INCREMENTOR).setParameter("type", type).executeUpdate();
			if(newKey.size()>0){
				return newKey.get(0);
			}else{
				throw new RuntimeException("Error retreiving key from Key Generator");
			}
		}catch(Exception e){
			throw new RuntimeException("Error retreiving key from Key Generator" , e);
		}
	}

}
