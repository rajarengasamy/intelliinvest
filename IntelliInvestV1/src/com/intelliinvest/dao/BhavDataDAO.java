package com.intelliinvest.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.intelliinvest.data.model.BhavData;
import com.intelliinvest.util.DateUtil;
import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.util.HttpUtil;
import com.intelliinvest.util.IntelliInvestStore;

public class BhavDataDAO {
	
	private static Logger logger = Logger.getLogger(BhavDataDAO.class);
	
	private static String BHAV_DATA_SELECT_QUERY_SYMBOL_DATE = "SELECT EXCHANGE as exchange, SYMBOL as symbol,SERIES as series,OPEN as open,HIGH as high,LOW as low,CLOSE as close"
			+ ",LAST as last,PREVCLOSE as prevClose,TOTTRDQTY as totTrdQty,TOTTRDVAL as totTrdVal,TIMESTAMP as timeStamp,TOTALTRADES as totalTrades,ISIN as isin FROM "
			+ "BHAV_DATA where SYMBOL=:symbol and TIMESTAMP>=:fromDate and TIMESTAMP<=:toDate order by TIMESTAMP ASC";
	
	private static String BHAV_DATA_SELECT_QUERY_DATE = "SELECT EXCHANGE as exchange, SYMBOL as symbol,SERIES as series,OPEN as open,HIGH as high,LOW as low,CLOSE as close"
			+ ",LAST as last,PREVCLOSE as prevClose,TOTTRDQTY as totTrdQty,TOTTRDVAL as totTrdVal,TIMESTAMP as timeStamp,TOTALTRADES as totalTrades,ISIN as isin FROM "
			+ "BHAV_DATA where TIMESTAMP=:date";
	
	private static String BHAV_DATA_SELECT_DATE_SYMBOL_QUERY = "SELECT EXCHANGE as exchange, SYMBOL as symbol,SERIES as series,OPEN as open,HIGH as high,LOW as low,CLOSE as close"
			+ ",LAST as last,PREVCLOSE as prevClose,TOTTRDQTY as totTrdQty,TOTTRDVAL as totTrdVal,TIMESTAMP as timeStamp,TOTALTRADES as totalTrades,ISIN as isin FROM "
			+ "BHAV_DATA where SYMBOL=:symbol and TIMESTAMP=:date";
	
	private static String BHAV_DATA_INSERT_QUERY = "INSERT INTO BHAV_DATA (EXCHANGE, SYMBOL,SERIES,OPEN,HIGH,LOW,CLOSE,LAST,PREVCLOSE,TOTTRDQTY,TOTTRDVAL,TIMESTAMP,TOTALTRADES,ISIN)"
			+ "values (:EXCHANGE, :SYMBOL, :SERIES, :OPEN, :HIGH, :LOW, :CLOSE, :LAST, :PREVCLOSE, :TOTTRDQTY, :TOTTRDVAL, :TIMESTAMP, :TOTALTRADES, :ISIN)";
	
	private static String BHAV_DATA_DELETE_QUERY = "DELETE FROM BHAV_DATA where TIMESTAMP=:date";
	
	private static String MAX_DATE_BHAV_DATA = " SELECT MAX(TIMESTAMP) from BHAV_DATA";
	
	public static List<BhavData> getBhavData(String symbol){
		try{
			return getBhavData(symbol, IntelliInvestStore.BHAV_DATA_START_DATE, new Date());
		}catch(Exception e){
			logger.error("Error parsing date " + IntelliInvestStore.BHAV_DATA_START_DATE);
			return new ArrayList<BhavData>();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<BhavData> getBhavData(String symbol, Date date){
		Session session = HibernateUtil.getSession();
		List<BhavData> bhavDataList = session.createSQLQuery(BHAV_DATA_SELECT_DATE_SYMBOL_QUERY)
											.setParameter("symbol", symbol)
											.setParameter("date", date)
											.list();
		return bhavDataList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<BhavData> getBhavData(String symbol, Date fromDate, Date toDate){
		Session session = HibernateUtil.getSession();
		List<BhavData> bhavDataList = session.createSQLQuery(BHAV_DATA_SELECT_QUERY_SYMBOL_DATE).setParameter("symbol", symbol).setParameter("fromDate", fromDate).setParameter("toDate", toDate).list();
		return bhavDataList;
	}
	
	public static List<BhavData> getBhavData(String symbol, Date date, Integer days){
		Date minDate = IntelliInvestStore.BHAV_DATA_START_DATE;
		List<BhavData> bhavDatas = new ArrayList<BhavData>();
		int count = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while(count<days && cal.after(minDate)){
			List<BhavData> bhavDatasForOneDay = getBhavData(symbol, date);
			if(!bhavDatasForOneDay.isEmpty()){
				bhavDatas.addAll(bhavDatasForOneDay);
				count++;
			}
			cal.add(Calendar.DATE, -1);
		}
			
		Collections.reverse(bhavDatas);
		
		return bhavDatas;
	}
	
	@SuppressWarnings("unchecked")
	static List<BhavData> getBhavData(Date date){
		Session session = HibernateUtil.getSession();
		List<BhavData> bhavDataList = session.createSQLQuery(BHAV_DATA_SELECT_QUERY_DATE).setParameter("date", date).list();
		return bhavDataList;
	}
	
	private static int insertBhavData(String[] bhavDataValues){
		Session session = HibernateUtil.getSession();
		int numberOfRecordsInserted = session.createSQLQuery(BHAV_DATA_INSERT_QUERY)
				.setParameter("EXCHANGE", "NSE")
				.setParameter("SYMBOL", bhavDataValues[0]) 
				.setParameter("SERIES", bhavDataValues[1]) 
				.setParameter("OPEN", new Double(bhavDataValues[2]))
				.setParameter("HIGH", new Double(bhavDataValues[3])) 
				.setParameter("LOW", new Double(bhavDataValues[4])) 
				.setParameter("CLOSE", new Double(bhavDataValues[5])) 
				.setParameter("LAST", new Double(bhavDataValues[6])) 
				.setParameter("PREVCLOSE", new Double(bhavDataValues[7])) 
				.setParameter("TOTTRDQTY", new Long(bhavDataValues[8]))
				.setParameter("TOTTRDVAL", new Double(bhavDataValues[9]))
				.setParameter("TIMESTAMP", DateUtil.getDate("dd-MMM-yyyy",bhavDataValues[10]))
				.setParameter("TOTALTRADES", new Long(bhavDataValues[11]))
				.setParameter("ISIN", bhavDataValues[12]).executeUpdate();
		return numberOfRecordsInserted;
	}
	
	public static int deleteBhavData(Date date){
		Session session = HibernateUtil.getSession();
		int deletedCount = session.createSQLQuery(BHAV_DATA_DELETE_QUERY).setParameter("date", date).executeUpdate();
		logger.info("Deleted " + deletedCount + "records in BHAV_DATA");
		return deletedCount;
	}
	
	@SuppressWarnings("rawtypes")
	public static Date getBhavDataMaxDate(){
		Session session = HibernateUtil.getSession();
		Date maxDate = new Date();
		List maxDateL = session.createSQLQuery(MAX_DATE_BHAV_DATA).list();
		if(maxDateL.size()>0){
			maxDate = (Date)maxDateL.get(0);
		}
		logger.info("getBhavDataMaxDate returned " + maxDate);
		return maxDate;
	}
	
	public static boolean insertBhavData(Date date){
		try{
			String bhavDataContent = getBhavDataFromNSE(date);
			String[] bhavDatas = bhavDataContent.split("\n");
			logger.info("Deleting records in BHAV_DATA for date " + date);
			int deletedRecords = deleteBhavData(date);
			logger.info("Deleted " + deletedRecords + " records in BHAV_DATA for date " + date);
			int insertRecords = 0;
			for(String bhavData : bhavDatas ){
				String[] bhavDataValues = bhavData.split(",");
				if(bhavDataValues.length>=13 && (bhavDataValues[1].equalsIgnoreCase("EQ") || bhavDataValues[1].equalsIgnoreCase("BE"))){
					int insertedRecord = insertBhavData(bhavDataValues);
					insertRecords = insertRecords + insertedRecord;
				}
			}
			logger.info("Inserted " + insertRecords + " successfully in BHAV_DATA");
		}catch(Exception e){
			logger.info("Error loading data in BHAV_DATA for date " + date);
			return false;
		}
		
		return true;
	}
	
	private static String getBhavDataFromNSE(Date date) throws Exception{
		ZipInputStream zis = null;
		ByteArrayOutputStream bos = null;
		try{
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("MMM");
			SimpleDateFormat format3 = new SimpleDateFormat("ddMMMyyyy");
			String url = "http://nseindia.com/content/historical/EQUITIES/"
								+ format1.format(date).toUpperCase() + "/"
								+ format2.format(date).toUpperCase() + "/"
								+ "cm" + format3.format(date).toUpperCase() + "bhav.csv.zip";
			
			logger.info("Processing data for Bhav for date " + date + "from url " + url);
			
			byte[] bytes = HttpUtil.getFromUrlAsBytes(url);
			zis = new ZipInputStream(new ByteArrayInputStream(bytes));
			ZipEntry ze = zis.getNextEntry();
			byte[] buffer = new byte[1024];
	    	while(ze!=null){
	    	   String fileName = ze.getName();
	    	   if(fileName.equalsIgnoreCase("cm" + format3.format(date) + "bhav.csv")){
	    		   bos = new ByteArrayOutputStream();             
	    		   int len;
	    		   while ((len = zis.read(buffer)) > 0) {
	    			   bos.write(buffer, 0, len);
	    		   }
	    		   String fileContent = bos.toString();
	    		   return fileContent;
	    	   }
	    	ze = zis.getNextEntry();
	    	}
		}catch(Exception e){
			logger.info("Error while fetching Bhav data for " + date + " Error " + e.getMessage());
			return "";
		}
    	finally{
    		if(null!=zis){
    			zis.closeEntry();
    			zis.close();
    		}
    		if(null!=bos){
    			bos.close();
    		}
    	}
		logger.info("Not able to fetch Bhav data for " + date);
		return "";
	}
}
