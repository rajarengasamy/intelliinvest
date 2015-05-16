package com.intelliinvest.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.WatchListData;

public class WatchListDAO {
	
	private static Logger logger = Logger.getLogger(WatchListDAO.class);
	
	private static String WATCH_LIST_SELECT_QUERY = "SELECT USER_ID as userId, CODE as code from  WATCH_LIST ORDER BY USER_ID, CODE";
	private static String WATCH_LIST_SELECT_USER_QUERY = "SELECT USER_ID as userId, CODE as code from  WATCH_LIST where USER_ID=:userId ORDER BY USER_ID, CODE";
	private static String WATCH_LIST_INSERT_QUERY = "INSERT INTO WATCH_LIST (USER_ID, CODE) values (:userId, :code)";
	private static String WATCH_LIST_DELETE_QUERY = "DELETE from WATCH_LIST where CODE=:code and USER_ID=:userId";
	
	@SuppressWarnings("unchecked")
	public static List<WatchListData> getWatchList(){
		Session session = HibernateUtil.getSession();
		List<WatchListData> stockPriceDatas = session.createSQLQuery(WATCH_LIST_SELECT_QUERY).addEntity(WatchListData.class).list();
		return stockPriceDatas;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WatchListData> getWatchList(String userId){
		Session session = HibernateUtil.getSession();
		List<WatchListData> stockPriceDatas = session.createSQLQuery(WATCH_LIST_SELECT_USER_QUERY).addEntity(WatchListData.class).setParameter("userId", userId).list();
		return stockPriceDatas;
	}
	
	public static int deleteWatchList(WatchListData watchListData) throws Exception{
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(WATCH_LIST_DELETE_QUERY).setParameter("userId", watchListData.getUserId()).setParameter("code", watchListData.getCode()).executeUpdate();
		logger.info("Deleted " + deletedCount + "records in WATCH_LIST");
		return deletedCount;
	}
	
	public static int updateStockDetails(WatchListData watchListData){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting WATCH_LIST for " + watchListData);
		int insertedRecord = session.createSQLQuery(WATCH_LIST_INSERT_QUERY)
					.setParameter("userId" , watchListData.getUserId())
					.setParameter("code" , watchListData.getCode())
					.executeUpdate();
		logger.info("Successfully inserted WATCH_LIST for " + watchListData);
		return insertedRecord;
	}
}
