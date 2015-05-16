package com.intelliinvest.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.StockSignalData;

public class StockSignalDAO {
	
	private static Logger logger = Logger.getLogger(StockSignalDAO.class);
	
	
	private static String STOCK_SIGNAL_SELECT_CODE_QUERY = "SELECT CODE as code, SIGNAL_DATE as date, SIGNAL_TYPE as signalType FROM STOCK_SIGNAL_DETAILS where CODE=:code";
	
	
	private static String STOCK_SIGNAL_DELETE_QUERY = "DELETE FROM STOCK_SIGNAL_DETAILS";
	private static String STOCK_SIGNAL_DELETE_CODE_QUERY = "DELETE FROM STOCK_SIGNAL_DETAILS where CODE=:code";
	private static String STOCK_SIGNAL_DELETE_DATE_QUERY = "DELETE FROM STOCK_SIGNAL_DETAILS where SIGNAL_DATE=:signalDate";
	private static String STOCK_SIGNAL_DELETE_CODE_DATE_QUERY = "DELETE FROM STOCK_SIGNAL_DETAILS where CODE=:code and SIGNAL_DATE=:signalDate";
	
	private static String STOCK_SIGNAL_INSERT_QUERY = "INSERT INTO STOCK_SIGNAL_DETAILS values (:code, :signalDate, :previousSignalType, :signalType)";
	private static String SIGNAL_INSERT_MA_QUERY = "INSERT INTO STOCK_SIGNAL_DETAILS "
			+ "(select SYMBOL, SIGNAL_DATE, PREVIOUS_SIGNAL_TYPE, SIGNAL_TYPE from SIGNAL_COMPONENTS where MOVING_ANERAGE=:movingAverage and SIGNAL_PRESENT='Y')";
	private static String SIGNAL_INSERT_MA_DATE_QUERY = "INSERT INTO STOCK_SIGNAL_DETAILS "
			+ "(select SYMBOL, SIGNAL_DATE, PREVIOUS_SIGNAL_TYPE, SIGNAL_TYPE from SIGNAL_COMPONENTS where MOVING_ANERAGE=:movingAverage and SIGNAL_DATE=:date and SIGNAL_PRESENT='Y')";
	private static String SIGNAL_INSERT_MA_CODE_QUERY = "INSERT INTO STOCK_SIGNAL_DETAILS "
			+ "(select SYMBOL, SIGNAL_DATE, PREVIOUS_SIGNAL_TYPE, SIGNAL_TYPE from SIGNAL_COMPONENTS where MOVING_ANERAGE=:movingAverage and SYMBOL=:code and SIGNAL_PRESENT='Y')";
	private static String SIGNAL_INSERT_MA_CODE_DATE_QUERY = "INSERT INTO STOCK_SIGNAL_DETAILS "
			+ "(select SYMBOL, SIGNAL_DATE, PREVIOUS_SIGNAL_TYPE, SIGNAL_TYPE from SIGNAL_COMPONENTS where MOVING_ANERAGE=:movingAverage and SYMBOL=:symbol and SIGNAL_DATE=:date and SIGNAL_PRESENT='Y')";
	
	
	@SuppressWarnings("unchecked")
	public List<StockSignalData> getStockDetails(){
		Session session = HibernateUtil.getSession();
		List<StockSignalData> stockSignalDatas = session.createSQLQuery(STOCK_SIGNAL_SELECT_CODE_QUERY).addEntity(StockSignalData.class).list();
		return stockSignalDatas;
	}
	
	public static int insertStockSignal(StockSignalData stockSignalData){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting STOCK_SIGNAL_DETAILS for " + stockSignalData);
		int insertRecord = session.createSQLQuery(STOCK_SIGNAL_INSERT_QUERY)
					.setParameter("code" , stockSignalData.getCode())
					.setParameter("signalDate" , stockSignalData.getSignalDate())
					.setParameter("previousSignalType" , stockSignalData.getPreviousSignalType())
					.setParameter("signalType" , stockSignalData.getSignalType())
					.executeUpdate();
		logger.info("Successfully inserted " + insertRecord + " STOCK_SIGNAL_DETAILS for " + stockSignalData);
		return insertRecord;
	}
	
	public static int insertStockSignalFromSignalComponenets(Integer movingAverage){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage );
		int insertRecord = session.createSQLQuery(SIGNAL_INSERT_MA_QUERY)
									.setParameter("movingAverage" , movingAverage)
									.executeUpdate();
		logger.info("Successfully inserted " + insertRecord + " STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage );
		return insertRecord;
	}
	
	public static int insertStockSignalFromSignalComponenets(Integer movingAverage, String code){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage + ", code : " + code );
		int insertRecord = session.createSQLQuery(SIGNAL_INSERT_MA_CODE_QUERY)
									.setParameter("movingAverage" , movingAverage)
									.setParameter("code" , code)
									.executeUpdate();
		logger.info("Successfully inserted " + insertRecord + " STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage + ", code : " + code);
		return insertRecord;
	}
	
	public static int insertStockSignalFromSignalComponenets(Integer movingAverage, Date signalDate){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage + ", signalDate : " + signalDate);
		int insertRecord = session.createSQLQuery(SIGNAL_INSERT_MA_DATE_QUERY)
									.setParameter("movingAverage" , movingAverage)
									.setParameter("signalDate" , signalDate)
									.executeUpdate();
		logger.info("Successfully inserted " + insertRecord + " STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage + ", signalDate : " + signalDate);
		return insertRecord;
	}
	
	public static int insertStockSignalFromSignalComponenets(Integer movingAverage, String code, Date signalDate){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage + ", code : " + code + ", signalDate : " + signalDate);
		int insertRecord = session.createSQLQuery(SIGNAL_INSERT_MA_CODE_DATE_QUERY)
									.setParameter("movingAverage" , movingAverage)
									.setParameter("code" , code)
									.setParameter("signalDate" , signalDate)
									.executeUpdate();
		logger.info("Successfully inserted " + insertRecord + " STOCK_SIGNAL_DETAILS from SIGNAL_COMPONENTS with movingAverage" + movingAverage + ", code : " + code + ", signalDate : " + signalDate);
		return insertRecord;
	}
	
	public static int insertStockSignal(String[] stockSignalData) throws Exception{
		return insertStockSignal(new StockSignalData(stockSignalData[1], new SimpleDateFormat("yyyy-MM-dd").parse(stockSignalData[1]), stockSignalData[2], stockSignalData[3]));
	}
	
	public static int deleteStockSignals(){
		Session session = HibernateUtil.getSession();
		logger.info("Dleteing all data from STOCK_SIGNAL_DETAILS");
		int deletedRecords = session.createSQLQuery(STOCK_SIGNAL_DELETE_QUERY).executeUpdate();
		logger.info("Successfully deleted all " + deletedRecords + " STOCK_SIGNAL_DETAILS");
		return deletedRecords;
	}
	
	public static int deleteStockSignals(String code, Date signalDate){
		Session session = HibernateUtil.getSession();
		logger.info("Dleteing data from STOCK_SIGNAL_DETAILS for code : " + code + "signalDate : " + signalDate);
		int deletedRecords = session.createSQLQuery(STOCK_SIGNAL_DELETE_CODE_DATE_QUERY)
									.setParameter("code" , code)
									.setParameter("signalDate" , signalDate)
									.executeUpdate();
		logger.info("Successfully deleted " + deletedRecords + " STOCK_SIGNAL_DETAILS for code : " + code + "signalDate : " + signalDate);
		return deletedRecords;
	}
	
	public static int deleteStockSignals(String code){
		Session session = HibernateUtil.getSession();
		logger.info("Dleteing data from STOCK_SIGNAL_DETAILS for code : " + code);
		int deletedRecords = session.createSQLQuery(STOCK_SIGNAL_DELETE_CODE_QUERY)
									.setParameter("code" , code)
									.executeUpdate();
		logger.info("Successfully deleted " + deletedRecords + " STOCK_SIGNAL_DETAILS for code : " + code);
		return deletedRecords;
	}
	
	public static int deleteStockSignals(Date signalDate){
		Session session = HibernateUtil.getSession();
		logger.info("Dleteing data from STOCK_SIGNAL_DETAILS for signalDate : " + signalDate);
		int deletedRecords = session.createSQLQuery(STOCK_SIGNAL_DELETE_DATE_QUERY)
									.setParameter("signalDate" , signalDate)
									.executeUpdate();
		logger.info("Successfully deleted " + deletedRecords + " STOCK_SIGNAL_DETAILS for signalDate : " + signalDate);
		return deletedRecords;
	}
	
}
