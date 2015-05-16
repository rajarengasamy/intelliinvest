package com.intelliinvest.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.data.model.MagicNumberData;
import com.intelliinvest.util.HibernateUtil;

public class MagicNumberDAO {
	
	private static Logger logger = Logger.getLogger(MagicNumberDAO.class);
	
	private static String MAGIC_NUMBER_SELECT_QUERY = "SELECT SYMBOL as symbol, MOVING_AVERAGE as movingAverage, MAGIC_NUMBER as magicNumber, PNL as pnl from MAGIC_NUMBERS";
	
	private static String MAGIC_NUMBER_SELECT_SYMBOL_QUERY = "SELECT SYMBOL as symbol, MOVING_AVERAGE as movingAverage, MAGIC_NUMBER as magicNumber, PNL as pnl "
																+ " from MAGIC_NUMBERS"
																+ " where SYMBOL=:symbol and MOVING_AVERAGE=:movingAverage";
	
	private static String MAGIC_NUMBERS_DELETE_QUERY = "DELETE FROM MAGIC_NUMBERS WHERE SYMBOL=:symbol and MOVING_AVERAGE=:movingAverage";
	
	private static String MAGIC_NUMBERS_DELETE_MA_QUERY = "DELETE FROM MAGIC_NUMBERS WHERE MOVING_AVERAGE=:movingAverage";
	
	private static String MAGIC_NUMBERS_DELETE_SYMBOL_QUERY = "DELETE FROM MAGIC_NUMBERS WHERE SYMBOL=:symbol";
	
	private static String MAGIC_NUMBERS_INSERT_QUERY = "INSERT INTO MAGIC_NUMBERS (SYMBOL, MOVING_AVERAGE, MAGIC_NUMBER, PNL) values (:symbol, :movingAverage, :magicNumber, :pnl)";
	
	@SuppressWarnings("unchecked")
	public static List<MagicNumberData> getMagicNumbers(){
		Session session = HibernateUtil.getSession();
		List<MagicNumberData> magicNumberDatas = session.createSQLQuery(MAGIC_NUMBER_SELECT_QUERY).addEntity(MagicNumberData.class).list();
		return magicNumberDatas;
	}
	
	@SuppressWarnings("unchecked")
	public static List<MagicNumberData> getMagicNumber(String symbol, Integer movingAverage){
		Session session = HibernateUtil.getSession();
		List<MagicNumberData> magicNumberDatas = session.createSQLQuery(MAGIC_NUMBER_SELECT_SYMBOL_QUERY).addEntity(MagicNumberData.class)
														.setParameter("symbol" , symbol)
														.setParameter("movingAverage" , movingAverage)
														.list();
		return magicNumberDatas;
	}
	
	public static int deleteMagicNumber(String symbol, Integer movingAverage) throws Exception{
		Session session = HibernateUtil.getSession();
		return deleteMagicNumber(symbol, movingAverage, session);
	}
	
	private static int deleteMagicNumber(String symbol, Integer movingAverage,	Session session) {
		int deletedCount = session.createSQLQuery(MAGIC_NUMBERS_DELETE_QUERY)
								.setParameter("symbol" , symbol)
								.setParameter("movingAverage" , movingAverage)
								.executeUpdate();
		logger.info("Deleted " + deletedCount + "records in MAGIC_NUMBERS for symbol : " + symbol + " , movingAverage : " + movingAverage);
		return deletedCount;
	}
	
	public static int deleteMagicNumbers(String symbol) throws Exception{
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(MAGIC_NUMBERS_DELETE_SYMBOL_QUERY)
								.setParameter("symbol" , symbol)
								.executeUpdate();
		logger.info("Deleted " + deletedCount + "records in MAGIC_NUMBERS for symbol : " + symbol );
		return deletedCount;
	}
	
	public static int deleteMagicNumbers(Integer movingAverage) throws Exception{
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(MAGIC_NUMBERS_DELETE_MA_QUERY)
								.setParameter("movingAverage" , movingAverage)
								.executeUpdate();
		logger.info("Deleted " + deletedCount + "records in MAGIC_NUMBERS for movingAverage : " + movingAverage);
		return deletedCount;
	}
	
	public static int updateMagicNumber(MagicNumberData magicNumberData){
		Session session = HibernateUtil.getSession();
		int deletedCount =  deleteMagicNumber(magicNumberData.getSymbol(), magicNumberData.getMovingAverage(), session);
		int insertedRecord = insertMagicNumber(magicNumberData, session);
		return deletedCount + insertedRecord;
		
	}
	
	public static int insertMagicNumber(MagicNumberData magicNumberData){
		Session session = HibernateUtil.getSession();
		return insertMagicNumber(magicNumberData, session);
	}

	private static int insertMagicNumber(MagicNumberData magicNumberData,
			Session session) {
		logger.info("Inserting MAGIC_NUMBERS for " + magicNumberData);
		int insertedRecord = session.createSQLQuery(MAGIC_NUMBERS_INSERT_QUERY)
					.setParameter("symbol" , magicNumberData.getSymbol())
					.setParameter("magicNumber" , magicNumberData.getMagicNumber())
					.setParameter("movingAverage" , magicNumberData.getMovingAverage())
					.setParameter("pnl" , magicNumberData.getPnl())
					.executeUpdate();
		logger.info("Successfully inserted MAGIC_NUMBERS for " + magicNumberData);
		return insertedRecord;
	}
}
