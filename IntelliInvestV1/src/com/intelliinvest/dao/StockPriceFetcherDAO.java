package com.intelliinvest.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.intelliinvest.data.model.EODVolumeChartData;
import com.intelliinvest.data.model.StockDetailData;
import com.intelliinvest.data.model.StockPriceData;
import com.intelliinvest.util.HttpUtil;
import com.intelliinvest.util.IntelliInvestStore;

public class StockPriceFetcherDAO {

private static Logger logger = Logger.getLogger(StockPriceFetcherDAO.class);
	
	final static String GOOGLE_QUOTE_URL = "https://www.google.com/finance/info?q=#CODE#";
	
	final static String GOOGLE_REALTIME_QUOTE_URL = "http://www.google.com/finance/getprices?q=#CODE#&x=#EXCHANGE#&i=120&p=1d&f=d,c,v&df=cpct";
	
	public static void main(String[] args) {
		IntelliInvestStore.STOCK_DETAILS_MAP.put("INFY", new StockDetailData("INFY", "INFY"));
		IntelliInvestStore.STOCK_DETAILS_MAP.put("BHARTIARTL", new StockDetailData("BHARTIARTL", "BHARTIARTL"));
		System.out.println(getCurrentPrices());
		IntelliInvestStore.WORLD_STOCK_DETAILS_MAP.put("INDEXNASDAQ:NDX", "NDX");
		System.out.println(getWorldStockPrices());
	}
	
	public static List<StockPriceData> getCurrentPrices(){
		List<String> stockCodes  = new ArrayList<String>(IntelliInvestStore.STOCK_DETAILS_MAP.keySet());
		List<StockPriceData> stockPriceDatasNSE = getCurrentPrices("NSE", stockCodes);
		List<StockPriceData> stockPriceDatasBSE = getBomCodesForFailedStocks(stockPriceDatasNSE);
		List<StockPriceData> stockPriceDatas = new ArrayList<StockPriceData>();
		stockPriceDatas.addAll(stockPriceDatasNSE);
		stockPriceDatas.addAll(stockPriceDatasBSE);
		return stockPriceDatas;
	}
	
	public static List<StockPriceData> getCurrentPrices(String exchange, List<String> stockCodes){
		
		List<StockPriceData> stockPriceDatas = new ArrayList<StockPriceData>(); 
		int start = -10;
		int end = 0;
		while(end<stockCodes.size()){
			start = start + 10;
			end = end + 10;
			if(end>stockCodes.size()){
				end = stockCodes.size();
			}
			stockPriceDatas.addAll(getStockPrice(exchange, stockCodes.subList(start, end)));
		}
		return stockPriceDatas;
	}
	
	private static List<StockPriceData> getBomCodesForFailedStocks(List<StockPriceData> stockPriceDatas){
		List<String> stockCodes = new ArrayList<String>();
		stockCodes.addAll(IntelliInvestStore.STOCK_DETAILS_MAP.keySet());
		for(StockPriceData stockPriceData : stockPriceDatas){
			stockCodes.remove(stockPriceData.getCode());
		}
		
		List<String> bseStockCodes = new ArrayList<String>();
		for(String stockCode : stockCodes){
			if(IntelliInvestStore.BSE_TO_NSE_MAP.containsKey(stockCode)){
				bseStockCodes.add(IntelliInvestStore.BSE_TO_NSE_MAP.get(stockCode).replace("&", "%26"));
			}
		}
		
		List<StockPriceData> stockPriceFromBSE = getCurrentPrices("BSE", bseStockCodes);
		for(StockPriceData stockPriceData : stockPriceFromBSE){
			stockPriceData.setCode(IntelliInvestStore.BSE_TO_NSE_MAP.get(stockPriceData.getCode()));
		}
		return stockPriceFromBSE;
	}
	
	public static List<StockPriceData> getStockPrice(String exchange, List<String> stockCodes) {
		List<StockPriceData> stockCurrentPriceList = new ArrayList<StockPriceData>(); 
		String codes = "";
		try{
			for(String stock :stockCodes){
				stock = exchange + ":"+ stock;
				codes = codes + stock  + ",";
			}
			String response = HttpUtil.getFromHttpUrlAsString(GOOGLE_QUOTE_URL.replace("#CODE#",  codes.replace("&", "%26")));
			stockCurrentPriceList.addAll(getPriceFromJSON(codes, response));
			return stockCurrentPriceList;
		}catch(Exception e){
			logger.info("Error fetching stock price in getStockPrice " + codes);
			return new ArrayList<StockPriceData>();
		}
	}
	
	private static List<StockPriceData> getPriceFromJSON(String codes, String response){
		List<StockPriceData> stockCurrentPriceList = new ArrayList<StockPriceData>(); 
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, hh:mmaa z");
		JSONArray jsonArray = JSONArray.fromObject( response.replaceFirst("//", "").trim() );  
		try{
			Calendar currentCal = Calendar.getInstance();
			currentCal.setTime(format.parse(format.format(new Date())));
			currentCal.add(Calendar.MONTH, -1);
			for(int i=0; i<jsonArray.size();i++){
				JSONObject stockObject = (JSONObject)jsonArray.get(i);
				String code = stockObject.getString("t").replace("\\x26", "&");
				try{
					Double price =  new Double(stockObject.getString("l_fix").replaceAll(",", ""));
					Double cp = new Double(stockObject.getString("cp").replaceAll(",", ""));
					String lt = stockObject.getString("lt");
					Date ltDate = format.parse(lt);
					if(currentCal.getTime().compareTo(ltDate)>0){
						continue;
					}
					stockCurrentPriceList.add(new StockPriceData(code, price, cp));
				}catch(Exception e){
					logger.error("Error fetching stock price for " + code, e);
				}
			}
		}catch(Exception e){
			logger.info("Error fetching stock price for " + codes);
		}
		return stockCurrentPriceList;
	}
	
	public static List<StockPriceData> getWorldStockPrices() {
		List<StockPriceData> stockCurrentPriceList = new ArrayList<StockPriceData>(); 
		try{
			for(String key : IntelliInvestStore.WORLD_STOCK_DETAILS_MAP.keySet()){
				String stockCode = key;
				String response = HttpUtil.getFromHttpUrlAsString(GOOGLE_QUOTE_URL.replace("#CODE#", stockCode.replace("&", "%26")));
				JSONArray jsonArray = JSONArray.fromObject( response.replaceFirst("//", "").trim() );  
				for(int j=0; j<jsonArray.size();j++){
					try{
						JSONObject stockObject = (JSONObject)jsonArray.get(j);
						Double price = new Double(stockObject.getString("l_fix").replaceAll(",", ""));
						Double cp = new Double(stockObject.getString("cp").replaceAll(",", ""));
						stockCurrentPriceList.add(new StockPriceData(IntelliInvestStore.WORLD_STOCK_DETAILS_MAP.get(key), price, cp));
					}catch(Exception e){
						logger.info("Error fetching stock price for " + key);
					}
				}
			}
			return stockCurrentPriceList;
		}catch(Exception e){
			logger.info("Error fetching World stock prices");
			return new ArrayList<StockPriceData>();
		}
	}
	
	public static List<EODVolumeChartData> getIntraDayPriceVolumeData(String code) {
		List<EODVolumeChartData> volumeChartDataList = getIntraDayPriceVolumeData(code, "NSE");
		if(volumeChartDataList.size()==0){
			if(IntelliInvestStore.NSE_TO_BSE_MAP.containsKey(code)){
				String bseCode = IntelliInvestStore.NSE_TO_BSE_MAP.get(code);
				getIntraDayPriceVolumeData(bseCode, "BOM");
			}
		}
		return volumeChartDataList;
	}
	
	public static List<EODVolumeChartData> getIntraDayPriceVolumeData(String code, String exchange) {
		List<EODVolumeChartData> volumeChartDataList = new ArrayList<EODVolumeChartData>(); 
		try{
			String response = HttpUtil.getFromHttpUrlAsString(GOOGLE_REALTIME_QUOTE_URL.replace("#CODE#", code.replace("&", "%26")).replace("#EXCHANGE#", exchange));
			String[] values = response.split("\n");
			Date baseDate = null;
			Integer interval = 120;
			for(String value : values){
				Date date = null;
				if(value.startsWith("COLUMNS")){
					continue;
				}else if(value.split(",").length==3){
					String[] datas = value.split(",");
					if(datas[0].startsWith("a")){
						baseDate = new Date(new Long(datas[0].replace("a", ""))*1000L);
						date = baseDate;
					}else{
						date = new Date(baseDate.getTime() + (new Long(datas[0])*interval*1000L));
					}
					Double price = new Double(datas[1]);
					Long volume = new Long(datas[2]);
					volumeChartDataList.add(new EODVolumeChartData(code, date, price, volume));
				}else if(value.startsWith("INTERVAL")){
					interval = new Integer(value.replace("INTERVAL=", ""));
				}
			}
			return volumeChartDataList;
		}catch(Exception e){
			logger.info("Error fetching Intra Day Price Volume Data");
			return new ArrayList<EODVolumeChartData>();
		}
	}
}
