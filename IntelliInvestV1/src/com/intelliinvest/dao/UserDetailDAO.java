package com.intelliinvest.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.UserDetailData;

public class UserDetailDAO {
	
	private static Logger logger = Logger.getLogger(UserDetailDAO.class);
	
	private static String USER_DETAIL_SELECT_QUERY = "SELECT USER_ID, USERNAME, PASSWORD, MAIL,PHONE, PLAN, USERTYPE, ACTIVE, ACTIVATION_CODE"
			+ ", CREATION_DATE, RENEWAL_DATE, EXPIRY_DATE, LAST_LOGIN_DATE, SEND_NOTIFICATION "	
			+ " FROM USER_DETAILS";
	
	private static String USER_DETAIL_SELECT_USER_QUERY = "SELECT USER_ID, USERNAME, PASSWORD, MAIL,PHONE, PLAN, USERTYPE, ACTIVE, ACTIVATION_CODE"
			+ ", CREATION_DATE, RENEWAL_DATE, EXPIRY_DATE, LAST_LOGIN_DATE, SEND_NOTIFICATION  FROM USER_DETAILS WHERE MAIL=:mail";
	
	private static String USER_DETAIL_INSERT_QUERY = "INSERT INTO USER_DETAILS (USER_ID, USERNAME, MAIL,PHONE, PASSWORD, PLAN, USERTYPE, ACTIVE, ACTIVATION_CODE, CREATION_DATE, RENEWAL_DATE, EXPIRY_DATE, LAST_LOGIN_DATE, SEND_NOTIFICATION)"
			+ " VALUES (:userId, :username, :mail, :phone, :password, :plan, :userType, :active, :activationCode,"
			+ " :creationDate, :renewalDate, :expiryDate, :lastLoginDate, :sendNotification )";
	
	private static String USER_DETAIL_DELETE_QUERY = "DELETE FROM USER_DETAILS where MAIL=:mail";
	
	private static String USER_DETAIL_UPDATE_QUERY = "UPDATE USER_DETAILS set USER_ID=:userId, USERNAME=:username, PASSWORD=:password, PHONE=:phone, PLAN=:plan,"
			+ " USERTYPE=:userType, ACTIVE=:active, ACTIVATION_CODE=:activationCode, RENEWAL_DATE=:renewalDate, EXPIRY_DATE=:expiryDate"
			+ " LAST_LOGIN_DATE=:lastLoginDate, SEND_NOTIFICATION=:sendNotification where MAIL=:mail";
	
	@SuppressWarnings("unchecked")
	public List<UserDetailData> getUserDetail(){
		Session session = HibernateUtil.getSession();
		List<UserDetailData> userDetailDatas = session.createSQLQuery(USER_DETAIL_SELECT_QUERY).addEntity(UserDetailData.class).list();
		return userDetailDatas;
	}
	
	@SuppressWarnings("unchecked")
	public UserDetailData getUserDetail(String mail){
		Session session = HibernateUtil.getSession();
		List<UserDetailData> userDetailDatas = session.createSQLQuery(USER_DETAIL_SELECT_USER_QUERY).addEntity(UserDetailData.class).setParameter("mail", mail).list();
		if(!userDetailDatas.isEmpty()){
			return userDetailDatas.get(0);
		}
		return null;
	}
	
	public static int deleteWatchList(String mail) throws Exception{
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(USER_DETAIL_DELETE_QUERY).setParameter("mail", mail).executeUpdate();
		logger.info("Deleted " + deletedCount + "records in USER_DETAILS");
		return deletedCount;
	}
	
	public int updateStockDetails(UserDetailData userDetailData){
		Session session = HibernateUtil.getSession();
		logger.info("Updating USER_DETAILS for " + userDetailData);
		int updatedRecord = session.createSQLQuery(USER_DETAIL_UPDATE_QUERY)
					.setParameter("username" , userDetailData.getUserId())
					.setParameter("username" , userDetailData.getUsername())
					.setParameter("password" , userDetailData.getPassword())
					.setParameter("phone" , userDetailData.getPhone())
					.setParameter("plan" , userDetailData.getPlan())
					.setParameter("userType" , userDetailData.getUserType())
					.setParameter("active" , userDetailData.getActive())
					.setParameter("activationCode" , userDetailData.getActivationCode())
					.setParameter("renewalDate" , userDetailData.getRenewalDate())
					.setParameter("expiryDate" , userDetailData.getExpiryDate())
					.setParameter("lastLoginDate" , userDetailData.getLastLoginDate())
					.setParameter("sendNotification" , userDetailData.getSendNotification())
					.setParameter("mail" , userDetailData.getMail())
					.executeUpdate();
		logger.info("Successfully updated USER_DETAILS for " + userDetailData);
		return updatedRecord;
	}
	
	public int insertStockDetails(UserDetailData userDetailData){
		Session session = HibernateUtil.getSession();
		logger.info("Updating USER_DETAILS for " + userDetailData);
		int insertedRecord = session.createSQLQuery(USER_DETAIL_INSERT_QUERY)
							.setParameter("userId" , userDetailData.getUserId())
							.setParameter("username" , userDetailData.getUsername())
							.setParameter("mail" , userDetailData.getMail())
							.setParameter("phone" , userDetailData.getPhone())
							.setParameter("password" , userDetailData.getPassword())
							.setParameter("plan" , userDetailData.getPlan())
							.setParameter("userType", userDetailData.getUserType())
							.setParameter("active", userDetailData.getActive())
							.setParameter("activationCode", userDetailData.getActivationCode())
							.setParameter("creationDate", userDetailData.getCreationDate())
							.setParameter("renewalDate", userDetailData.getRenewalDate())
							.setParameter("expiryDate", userDetailData.getExpiryDate())
							.setParameter("lastLoginDate", userDetailData.getLastLoginDate())
							.setParameter("sendNotification", userDetailData.getSendNotification())
							.executeUpdate();
		logger.info("Successfully inserted USER_DETAILS for " + userDetailData);
		return insertedRecord;
	}
}
