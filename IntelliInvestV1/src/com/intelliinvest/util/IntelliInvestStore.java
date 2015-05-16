package com.intelliinvest.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.intelliinvest.data.model.MagicNumberData;
import com.intelliinvest.data.model.StockDetailData;
import com.intelliinvest.data.model.StockPriceData;

public class IntelliInvestStore {
	public static Map<String, StockDetailData> STOCK_DETAILS_MAP = new HashMap<String, StockDetailData>();
	public static Map<String, StockPriceData> CURRENT_STOCK_PRICE_MAP = new HashMap<String, StockPriceData>();
	public static Map<String, String> WORLD_STOCK_DETAILS_MAP = new HashMap<String, String>();
	public static Map<String, StockPriceData> WORLD_STOCK_PRICE_MAP = new HashMap<String, StockPriceData>();
	public static Map<String, String> NSE_TO_BSE_MAP = new HashMap<String, String>();
	public static Map<String, String> BSE_TO_NSE_MAP = new HashMap<String, String>();
	public static Integer DEFAULT_MAGIC_NUMBER = 45;
	public static Date BHAV_DATA_START_DATE = DateUtil.getDate("20090101");
	public static Integer SIGNAL_DELTA = 5;
	public static List<MagicNumberData> MAGIC_NUMBERS = new ArrayList<MagicNumberData>();
	public static Set<String> BSE_STOCKS = new HashSet<String>();
	public static Properties properties = null;
	
	public static Integer getMagicNumber(Integer movingAverage, String symbol){
		Integer magicNumber = IntelliInvestStore.DEFAULT_MAGIC_NUMBER;
		int index = IntelliInvestStore.MAGIC_NUMBERS.indexOf(new MagicNumberData(symbol, movingAverage, 0, 0D));
		if(index>=0){
			magicNumber = IntelliInvestStore.MAGIC_NUMBERS.get(index).getMagicNumber();
		}
		return magicNumber;
	}
}
