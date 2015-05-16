package com.intelliinvest.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.StockPriceData;

public class WorldStockPriceDAO {
	
	private static Logger logger = Logger.getLogger(WorldStockPriceDAO.class);
	
	private static String STOCK_CURRENT_PRICE_SELECT_QUERY = "SELECT CODE as code, PRICE as price, CP as cp from  WORLD_STOCK_PRICE ORDER BY CODE";
	private static String STOCK_CURRENT_PRICE_INSERT_QUERY = "INSERT into WORLD_STOCK_PRICE (CODE, PRICE, CP) values(:code, :price, :cp)";
	private static String STOCK_CURRENT_PRICE_DELETE_QUERY = "DELETE from WORLD_STOCK_PRICE";
	
	@SuppressWarnings("unchecked")
	public List<StockPriceData> getCurrentStockPrices(){
		Session session = HibernateUtil.getSession();
		List<StockPriceData> stockPriceDatas = session.createSQLQuery(STOCK_CURRENT_PRICE_SELECT_QUERY).addEntity(StockPriceData.class).list();
		return stockPriceDatas;
	}
	
	public List<StockPriceData> updateCurrentStockPrices(List<StockPriceData> currentPrices){
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(STOCK_CURRENT_PRICE_DELETE_QUERY).executeUpdate();
		logger.info("Deleted " + deletedCount + "records in WORLD_STOCK_PRICE");
		int insertedRecords = 0;
		logger.info("Updating current prices for all stocks ");
		for( StockPriceData currentPriceData : currentPrices){
			int insertedRecord = session.createSQLQuery(STOCK_CURRENT_PRICE_INSERT_QUERY)
					.setParameter("code" , currentPriceData.getCode())
					.setParameter("price" , currentPriceData.getPrice())
					.setParameter("cp" , currentPriceData.getCp())
					.executeUpdate();
			insertedRecords = insertedRecords + insertedRecord;
		}
		logger.info("Inserted " + insertedRecords + "records in WORLD_STOCK_PRICE");
		return getCurrentStockPrices();
	}
}
