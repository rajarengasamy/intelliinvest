package com.intelliinvest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.data.model.LatestStockSignalData;
import com.intelliinvest.util.HibernateUtil;

public class LatestStockSignalDAO {
	
	private static Logger logger = Logger.getLogger(LatestStockSignalDAO.class);
	
	private static String LATEST_STOCK_SIGNAL_SELECT_CODE_QUERY = "select s.CODE as code, s.SIGNAL_TYPE as signalType, b.CLOSE as signalPrice, s.SIGNAL_DATE as signalDate"
			+ " from"
			+ " (select a.CODE, a.SIGNAL_TYPE, a.SIGNAL_DATE from STOCK_SIGNAL_DETAILS a"
			+ " join"
			+ " (select CODE, max(SIGNAL_DATE) as SIGNAL_DATE from STOCK_SIGNAL_DETAILS group by CODE) a1"
			+ " on a.CODE=a1.CODE and a.SIGNAL_DATE=a1.SIGNAL_DATE )s,"
			+ " BHAV_DATA b where s.SIGNAL_DATE = b.TIMESTAMP and s.CODE=b.SYMBOL order by s.CODE";
	
	
	@SuppressWarnings("unchecked")
	public static List<LatestStockSignalData> getLatestStockDetails(){
		Session session = HibernateUtil.getSession();
		List<LatestStockSignalData> stockSignalDatas = session.createSQLQuery(LATEST_STOCK_SIGNAL_SELECT_CODE_QUERY).addEntity(LatestStockSignalData.class).list();
		logger.info("List of data for LatestStockSignalData returned " + stockSignalDatas.size() + " rows");
		return stockSignalDatas;
	}
	
	public static Map<String, LatestStockSignalData> getLatestStockDetailsAsMap(){
		Map<String, LatestStockSignalData> latestStockSignalDataMap = new HashMap<String, LatestStockSignalData>();
		List<LatestStockSignalData> latestStockSignalDatas = getLatestStockDetails();
		for(LatestStockSignalData latestStockSignalData : latestStockSignalDatas){
			latestStockSignalDataMap.put(latestStockSignalData.getCode(), latestStockSignalData);
		}
		return latestStockSignalDataMap;
	}
}
