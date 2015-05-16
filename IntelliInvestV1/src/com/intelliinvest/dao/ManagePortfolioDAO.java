package com.intelliinvest.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.ManagePortfolioData;

public class ManagePortfolioDAO {
	
	private static Logger logger = Logger.getLogger(ManagePortfolioDAO.class);
	
	private static String MANAGE_PORTFOLIO_RETRIEVE_QUERY = "SELECT ID as id, USER_ID as userId, CODE as code, TRADE_DATE as tradeDate, DIRECTION as direction, QUANTITY as quantity, PRICE as price from  MANAGE_PORTFOLIO_DETAILS where USER_ID=:userId ORDER BY CODE, DATE";
	private static String MANAGE_PORTFOLIO_CODE_RETRIEVE_QUERY = "SELECT ID as id, USER_ID as userId, CODE as code, TRADE_DATE as tradeDate, DIRECTION as direction, QUANTITY as quantity, PRICE as price from  MANAGE_PORTFOLIO_DETAILS where USER_ID=:userId and CODE=:code ORDER BY CODE, DATE";
	private static String MANAGE_PORTFOLIO_INSERT_QUERY = "INSERT INTO MANAGE_PORTFOLIO_DETAILS (ID, USER_ID, CODE, TRADE_DATE, DIRECTION, QUANTITY, PRICE) values (:id, :userId, :code, :tradeDate, :direction, :quantity, :price)";
	private static String MANAGE_PORTFOLIO_UPDATE_QUERY = "UPDATE MANAGE_PORTFOLIO_DETAILS set TRADE_DATE=:tradeDate, DIRECTION=:direction, QUANTITY=:quantity, PRICE=:price where ID=:id";


	@SuppressWarnings("unchecked")
	public List<ManagePortfolioData> getManagePortfolioData(String userId){
		Session session = HibernateUtil.getSession();
		List<ManagePortfolioData> managePortfolioDatas  = session.createSQLQuery(MANAGE_PORTFOLIO_RETRIEVE_QUERY).addEntity(ManagePortfolioData.class).setParameter("userId" , userId).list();
		return managePortfolioDatas;
	}
	
	@SuppressWarnings("unchecked")
	public List<ManagePortfolioData> getManagePortfolioData(String userId, String code){
		Session session = HibernateUtil.getSession();
		List<ManagePortfolioData> managePortfolioDatas  = session.createSQLQuery(MANAGE_PORTFOLIO_CODE_RETRIEVE_QUERY).addEntity(ManagePortfolioData.class)
				.setParameter("userId" , userId)
				.setParameter("code", code)
				.list();
		return managePortfolioDatas;
	}
	
	public int insertManagePortfolioData(String userId, ManagePortfolioData managePortfolioData){
		Session session = HibernateUtil.getSession();
		int insertRecord = session.createSQLQuery(MANAGE_PORTFOLIO_INSERT_QUERY)
				.setParameter("id" , managePortfolioData.getId())
					.setParameter("userId" , userId)
					.setParameter("code" , managePortfolioData.getCode())
					.setParameter("tradeDate" , managePortfolioData.getTradeDate())
					.setParameter("direction" , managePortfolioData.getDirection())
					.setParameter("quantity" , managePortfolioData.getQuantity())
					.setParameter("price" , managePortfolioData.getPrice())
					.executeUpdate();
		logger.info("Inserted " + insertRecord + " record successfully into MANAGE_PORTFOLIO_DETAILS - " +  managePortfolioData);
		return insertRecord;
	}
	
	public int updateManagePortfolioData(String userId, ManagePortfolioData managePortfolioData){
		Session session = HibernateUtil.getSession();
		int updatedRecord = session.createSQLQuery(MANAGE_PORTFOLIO_UPDATE_QUERY)
														.setParameter("tradeDate" , managePortfolioData.getTradeDate())
			 											.setParameter("direction" , managePortfolioData.getDirection())
			 											.setParameter("quantity" , managePortfolioData.getQuantity())
			 											.setParameter("price" , managePortfolioData.getPrice())
			 											.setParameter("id" , managePortfolioData.getId())
			 											.executeUpdate();
		logger.info("Updated " + updatedRecord + " record successfully into MANAGE_PORTFOLIO_DETAILS - " +  managePortfolioData);
		return updatedRecord;
	}
	
}
