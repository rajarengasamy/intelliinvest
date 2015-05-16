package com.intelliinvest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.data.model.StockDetailData;
import com.intelliinvest.util.HibernateUtil;

public class StockDetailDAO {
	
	private static Logger logger = Logger.getLogger(StockDetailDAO.class);
	
	private static String STOCK_DETAILS_SELECT_QUERY = "SELECT CODE as code, NAME as name from STOCK_DETAILS ORDER BY CODE"; 
	private static String STOCK_DETAILS_INSERT_QUERY = "INSERT into STOCK_DETAILS (CODE, NAME) values(:code, :name)";
	private static String STOCK_DETAILS_DELETE_QUERY = "DELETE from STOCK_DETAILS"; 
	
	@SuppressWarnings("unchecked")
	public static List<StockDetailData> getStockDetails(){
		Session session = HibernateUtil.getSession();
		List<StockDetailData> stockDetailDatas = session.createSQLQuery(STOCK_DETAILS_SELECT_QUERY).addEntity(StockDetailData.class).list();
		return stockDetailDatas;
	}
	
	public static List<StockDetailData> updateStockDetails(List<StockDetailData> stockDetailDatas){
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(STOCK_DETAILS_DELETE_QUERY).executeUpdate();
		logger.info("Deleted " + deletedCount + "records in STOCK_DETAILS");
		int insertedRecords = 0;
		logger.info("Updating current prices for all stocks ");
		for( StockDetailData stockDetailData : stockDetailDatas){
			int insertedRecord = session.createSQLQuery(STOCK_DETAILS_INSERT_QUERY)
						.setParameter("code" , stockDetailData.getCode())
						.setParameter("name" , stockDetailData.getName())
						.executeUpdate();
			insertedRecords = insertedRecords + insertedRecord;
		}
		logger.info("Inserted " + insertedRecords + "records in STOCK_DETAILS");
		return getStockDetails();
	}
	
	public static List<StockDetailData> updateStockDetails(Map<String, String> stockDetailsMap){
		List<StockDetailData> stockDetailDatas = new ArrayList<StockDetailData>();
		for(Entry<String, String> entry : stockDetailsMap.entrySet()){
			stockDetailDatas.add(new StockDetailData(entry.getKey(), entry.getValue()));
		}	
		return updateStockDetails(stockDetailDatas);
	}
}
