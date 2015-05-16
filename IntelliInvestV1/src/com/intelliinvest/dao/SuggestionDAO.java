package com.intelliinvest.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.SuggestionData;

public class SuggestionDAO {
	
	private static Logger logger = Logger.getLogger(SuggestionDAO.class);
	
	private static String SUGGESTION_DELETE_QUERY = "DELETE from SUGGESTIONS_DATA_NEW";
	private static String SUGGESTION_INSERT_QUERY = "INSERT into SUGGESTIONS_DATA_NEW (CODE, SUGGESTION_TYPE, SIGNAL_TYPE, SIGNAL_PRICE) values (:code, :suggestionType, :signalType, :signalPrice)";
	private static String SUGGESTION_DATA_QUERY = "SELECT CODE as code, SUGGESTION_TYPE as suggestionType, SIGNAL_TYPE as signalType,"
												+ " SIGNAL_PRICE as signalPrice, SIGNAL_DATE as signalDate, STOP_LOSS_PRICE as stopLossPrice FROM SUGGESTIONS_DATA_NEW";
	
	
	//CREATE Table SUGGESTIONS_DATA_NEW( CODE varchar(100), SUGGESTION_TYPE varchar(10), SIGNAL_PRICE DOUBLE, SIGNAL_TYPE varchar(15), SIGNAL_DATE DATE, STOP_LOSS_PRICE DOUBLE);
	
	@SuppressWarnings("unchecked")
	public static List<SuggestionData> getSuggestions(){
		Session session = HibernateUtil.getSession();
		List<SuggestionData> suggestionDatas = session.createSQLQuery(SUGGESTION_DATA_QUERY).addEntity(SuggestionData.class).list();
		return suggestionDatas;
	}
	
	public static int deleteSuggestions() throws Exception{
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(SUGGESTION_DELETE_QUERY).executeUpdate();
		logger.info("Deleted " + deletedCount + "records in SUGGESTIONS_DATA_NEW");
		return deletedCount;
	}
	
	public static int insertSuggestions(SuggestionData suggestionData){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting SUGGESTIONS_DATA_NEW for " + suggestionData);
		int insertedRecord = session.createSQLQuery(SUGGESTION_INSERT_QUERY)
					.setParameter("code" , suggestionData.getCode())
					.setParameter("suggestionType" , suggestionData.getSuggestionType())
					.setParameter("signalType" , suggestionData.getSignalType())
					.setParameter("signalPrice" , suggestionData.getSignalPrice())
					.setParameter("signalDate" , suggestionData.getSignalDate())
					.setParameter("stopLossPrice" , suggestionData.getStopLossPrice())
					.executeUpdate();
		logger.info("Successfully inserted SUGGESTIONS_DATA_NEW for " + suggestionData);
		return insertedRecord;
	}
}
