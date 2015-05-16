package com.intelliinvest.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.SignalComponentData;

public class SignalComponentDAO {
	
	private static Logger logger = Logger.getLogger(SignalComponentDAO.class);
	
	private static String SIGNAL_COMPONENTS_SELECT_SYMBOL_CODE_QUERY = "select SYMBOL as symbol, SIGNAL_DATE as signalDate, MOVING_AVERAGE as movingAverage, "
														+ " TR as TR, PLUS_DM_1 as plusDM1, MINUS_DM_1 as minusDM1, TR_N as TRn, PLUS_DM_N as plusDMn,"
														+ "	MINUS_DM_N as minusDMn, DIFF_DI_N as diffDIn, SUM_DI_N as sumDIn, DX as DX, ADX_N as ADXn, "
														+ " SPLIT_MULTIPLIER as splitMultiplier, PREVIOUS_SIGNAL_TYPE as previousSignalType, SIGNAL_TYPE as signalType, "
														+ " SIGNAL_PRESENT as signalPresent "
													+ " from SIGNAL_COMPONENTS "
													+ " where SYMBOL=:symbol and MOVING_AVERAGE=:movingAverage and SIGNAL_DATE=:signalDate";
	
	private static String SIGNAL_COMPONENTS_SELECT_DTAE_QUERY = "select SYMBOL as symbol, SIGNAL_DATE as signalDate, MOVING_AVERAGE as movingAverage, "
														+ "TR as TR, PLUS_DM_1 as plusDM1, MINUS_DM_1 as minusDM1, TR_N as TRn, PLUS_DM_N as plusDMn,"
														+ "	MINUS_DM_N as minusDMn, DIFF_DI_N as diffDIn, SUM_DI_N as sumDIn, DX as DX, ADX_N as ADXn, "
														+ "SPLIT_MULTIPLIER as splitMultiplier, PREVIOUS_SIGNAL_TYPE as previousSignalType, SIGNAL_TYPE as signalType, "
														+ "SIGNAL_PRESENT as signalPresent "
													+ " from SIGNAL_COMPONENTS "
													+ " where MOVING_AVERAGE=:movingAverage and SIGNAL_DATE>=:startDate and SIGNAL_DATE<=:endDate ORDER BY SIGNAL_DATE DESC";

	private static String SIGNAL_COMPONENTS_INSERT_QUERY = "INSERT INTO SIGNAL_COMPONENTS (SYMBOL, SIGNAL_DATE, MOVING_AVERAGE, TR, PLUS_DM_1, MINUS_DM_1, TR_N, PLUS_DM_N,"
																+ "	MINUS_DM_N, DIFF_DI_N, SUM_DI_N, DX, ADX_N, SPLIT_MULTIPLIER, "
																+ " PREVIOUS_SIGNAL_TYPE, SIGNAL_TYPE, SIGNAL_PRESENT) "
															+ "values (:symbol, :signalDate, :movingAverage, :TR, :plusDM1, :minusDM1, :TRn, :plusDMn,"
																+ "	:minusDMn, :diffDIn, :sumDIn, :DX, :ADXn, :splitMultiplier, "
																+ "	:previousSignalType, :signalType, :signalPresent)";
	
	private static String SIGNAL_COMPONENTS_DELETE_SYMBOL_QUERY = "DELETE FROM SIGNAL_COMPONENTS where SYMBOL=:symbol and MOVING_AVERAGE=:movingAverage";
	
	private static String SIGNAL_COMPONENTS_DELETE_SYMBOL_DATE_QUERY = "DELETE FROM SIGNAL_COMPONENTS where SYMBOL=:symbol and MOVING_AVERAGE=:movingAverage and SIGNAL_DATE>=:signalDate";
			
	private static String SIGNAL_COMPONENTS_MAX_DATE_QUERY = "select MAX(SIGNAL_DATE) from SIGNAL_COMPONENTS where MOVING_AVERAGE=:movingAverage";
	
	
	
	@SuppressWarnings("unchecked")
	public static Date getSignalComponentsMaxDate(Integer movingAverage){
		Session session = HibernateUtil.getSession();
		List<Date> maxDateList = session.createSQLQuery(SIGNAL_COMPONENTS_MAX_DATE_QUERY).addEntity(SignalComponentData.class)
																.setParameter("movingAverage" , movingAverage)
																.list();
		if(maxDateList.size()>0){
			return maxDateList.get(0);
		}
		return new Date();
	}
	
	@SuppressWarnings("unchecked")
	public static SignalComponentData getSignalComponents(Integer movingAverage, String symbol, Date signalDate){
		Session session = HibernateUtil.getSession();
		List<SignalComponentData> signalComponentDatas = session.createSQLQuery(SIGNAL_COMPONENTS_SELECT_SYMBOL_CODE_QUERY).addEntity(SignalComponentData.class)
																.setParameter("movingAverage" , movingAverage)
																.setParameter("symbol" , symbol)
																.setParameter("signalDate" , signalDate)
																.list();
		if(signalComponentDatas.size()>0){
			return signalComponentDatas.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SignalComponentData> getSignalComponents(Integer movingAverage, Date startDate, Date endDate){
		Session session = HibernateUtil.getSession();
		List<SignalComponentData> signalComponentDatas = session.createSQLQuery(SIGNAL_COMPONENTS_SELECT_DTAE_QUERY).addEntity(SignalComponentData.class)
																.setParameter("movingAverage" , movingAverage)
																.setParameter("startDate" , startDate)
																.setParameter("endDate" , endDate)
																.list();
		return signalComponentDatas;
	}
	
	public static int deleteSignalComponents(Integer movingAverage, String symbol, Date signalDate){
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(SIGNAL_COMPONENTS_DELETE_SYMBOL_DATE_QUERY)
									.setParameter("movingAverage" , movingAverage)
									.setParameter("symbol" , symbol)
									.setParameter("signalDate" , signalDate)
									.executeUpdate();
		logger.info("Deleted " + deletedCount + "records in SIGNAL_COMPONENTS for movingAverage : " + movingAverage + ", symbol : " + symbol);
		return deletedCount;
	}
	
	public static int deleteSignalComponents(Integer movingAverage, String symbol){
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(SIGNAL_COMPONENTS_DELETE_SYMBOL_QUERY)
									.setParameter("movingAverage" , movingAverage)
									.setParameter("symbol" , symbol)
									.executeUpdate();
		logger.info("Deleted " + deletedCount + "records in SIGNAL_COMPONENTS for movingAverage : " + movingAverage + ", symbol : " + symbol);
		return deletedCount;
	}
	
	public static int insertSignalComponenets(List<SignalComponentData> signalComponents){
		logger.info("Inserting SIGNAL_COMPONENTS for " + signalComponents.size() + " signals");
		int insertRecords = 0;
		for(SignalComponentData signalComponent : signalComponents){
			insertRecords = insertRecords + insertSignalComponenet(signalComponent);
		}
		logger.info("Inserted SIGNAL_COMPONENTS for " + signalComponents.size() + " signals");
		return insertRecords;
	}
	
	public static int insertSignalComponenet(SignalComponentData signalComponent){
		Session session = HibernateUtil.getSession();
		logger.info("Inserting SIGNAL_COMPONENTS for " + signalComponent);
		int insertRecord = session.createSQLQuery(SIGNAL_COMPONENTS_INSERT_QUERY)
				.setParameter("symbol", signalComponent.getSymbol())
				.setParameter("signalDate", signalComponent.getSignalDate()) 
				.setParameter("movingAverage", signalComponent.getMovingAverage()) 
				.setParameter("TR", signalComponent.getTR()) 
				.setParameter("plusDM1", signalComponent.getPlusDM1()) 
				.setParameter("minusDM1", signalComponent.getMinusDM1()) 
				.setParameter("TRn", signalComponent.getTRn()) 
				.setParameter("plusDMn", signalComponent.getPlusDMn())
				.setParameter("minusDMn", signalComponent.getMinusDMn()) 
				.setParameter("diffDIn", signalComponent.getDiffDIn()) 
				.setParameter("sumDIn", signalComponent.getSumDIn()) 
				.setParameter("DX", signalComponent.getDX()) 
				.setParameter("ADXn", signalComponent.getADXn()) 
				.setParameter("splitMultiplier", signalComponent.getSplitMultiplier())
				.setParameter("previousSignalType", signalComponent.getPreviousSignalType()) 
				.setParameter("signalType", signalComponent.getSignalType()) 
				.setParameter("signalPresent", signalComponent.getSignalPresent())
		 		.executeUpdate();
		logger.info("Successfully inserted SIGNAL_COMPONENTS for " + signalComponent);
		return insertRecord;
	}
}
