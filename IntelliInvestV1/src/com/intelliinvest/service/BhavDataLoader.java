package com.intelliinvest.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.intelliinvest.dao.BhavDataDAO;
import com.intelliinvest.util.DateUtil;
import com.intelliinvest.util.HibernateUtil;


public class BhavDataLoader {
	
	private static Logger logger = Logger.getLogger(BhavDataLoader.class);
	
	public static void main(String[] args) {
		HibernateUtil.openSession();
		loadBhavData(DateUtil.getDate("20150401"), DateUtil.getDate("20150401"));
		HibernateUtil.closeSession();
	}
	
	public static String loadBhavData(Date fromDate, Date toDate) {
		logger.info("Loading Bhav data from " + " from " + fromDate + " to " + toDate);
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(fromDate);
		fromCal.set(Calendar.HOUR, 0);
		fromCal.set(Calendar.MINUTE, 0);
		fromCal.set(Calendar.SECOND, 0);
		fromCal.set(Calendar.MILLISECOND, 0);
		fromCal.set(Calendar.AM_PM, Calendar.AM);
		
		Calendar toCal = Calendar.getInstance();
		toCal.setTime(toDate);
		toCal.set(Calendar.HOUR, 0);
		toCal.set(Calendar.MINUTE, 0);
		toCal.set(Calendar.SECOND, 0);
		toCal.set(Calendar.MILLISECOND, 0);
		toCal.set(Calendar.AM_PM, Calendar.AM);
		
		while(toCal.getTime().compareTo(fromCal.getTime())>=0){
			Boolean status = BhavDataDAO.insertBhavData(fromCal.getTime());
			if(status){
				logger.info("Data uploaded successfully for " + fromCal.getTime());
			}else{
				logger.info("Error uploading data for " + fromCal.getTime());
			}
			fromCal.add(Calendar.DATE, 1);
		}
		return "Data upload completed. See logs for details";
	}

}
