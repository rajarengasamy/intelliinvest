package com.intelliinvest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.intelliinvest.dao.BhavDataDAO;
import com.intelliinvest.dao.MagicNumberDAO;
import com.intelliinvest.data.model.BhavData;
import com.intelliinvest.data.model.MagicNumberData;
import com.intelliinvest.data.model.SignalComponentData;
import com.intelliinvest.data.model.StockDetailData;
import com.intelliinvest.util.IntelliInvestStore;

public class MagicNumberGenerator {
	
	private static Logger logger = Logger.getLogger(MagicNumberGenerator.class);
	
	public static void generateMagicNumber(int movingAverage) {
		for(StockDetailData stockDetailData : IntelliInvestStore.STOCK_DETAILS_MAP.values()){
			generateMagicNumber(movingAverage, stockDetailData.getCode() );
		}
	}
	public static void generateMagicNumber(int movingAverage, String symbol) {
		MagicNumberData magicNumberData = getMagicNumber(movingAverage, symbol);
		MagicNumberDAO.updateMagicNumber(magicNumberData);
	}
	
	public static MagicNumberData getMagicNumber(int movingAverage, String symbol){
		Integer magicNumber = 45;
		Double PNL = 0D;
		try{
			List<BhavData> bhavDatas = BhavDataDAO.getBhavData(symbol, IntelliInvestStore.BHAV_DATA_START_DATE, new Date());
			Map<Integer, Double> maPnlMap = new HashMap<Integer, Double>();
			for(int i=20; i<=60;i++){
				Map<Date, BhavData> priceMap = new HashMap<Date, BhavData>();
				List<SignalComponentData> signalPresentList = new ArrayList<SignalComponentData>();
				List<SignalComponentData> signalComponents = SignalComponentGenerator.getSignalComponents(movingAverage, symbol, bhavDatas, signalPresentList, priceMap);
				Double pnl = getPnl(priceMap, signalPresentList, signalComponents.get(signalComponents.size()-1), bhavDatas.get(bhavDatas.size()-1));
				maPnlMap.put(i, pnl);
			}
			magicNumber = getMaxPnl(maPnlMap);
			PNL =  maPnlMap.get(magicNumber);
			logger.info("Magic number for " + symbol + " is " + magicNumber + " with pnl " + maPnlMap.get(magicNumber));
		}catch(Exception e){
			logger.info("Error generating Magic number for " + symbol );
		}
		return new MagicNumberData(symbol, movingAverage, magicNumber, PNL);
	}
	
	private static Integer getMaxPnl(Map<Integer, Double> maPnlMap){
		Double maxPnl = 0D;
		Integer magicNumberWithMaxPNL = -1;
		boolean isFirst = true;
		for(Integer magicNumber : maPnlMap.keySet()){
			if(isFirst){
				maxPnl = maPnlMap.get(magicNumber);
				magicNumberWithMaxPNL = magicNumber;
				isFirst = false;
			}
			if(maPnlMap.get(magicNumber)>maxPnl){
				maxPnl = maPnlMap.get(magicNumber);
				magicNumberWithMaxPNL = magicNumber;
			}
		}
		return magicNumberWithMaxPNL;
	}
	private static Double getPnl(Map<Date, BhavData> priceMap, List<SignalComponentData> signalPresentList, SignalComponentData signalComponent, BhavData lastBhav){
		Double pnl = 0D;
		SignalComponentData prevSignalComponents = null;
		for(SignalComponentData signalComponents : signalPresentList){
			if(!signalComponents.getSignalType().equalsIgnoreCase("Buy") && null==prevSignalComponents){
				
			}else if(signalComponents.getSignalType().equalsIgnoreCase("Buy")){
				prevSignalComponents = signalComponents;
				Double price = priceMap.get(signalComponents.getSignalDate()).getClose() * signalComponents.getSplitMultiplier();
				pnl = pnl - price;
			}else if(!signalComponents.getSignalType().equalsIgnoreCase("Buy")){
				prevSignalComponents = signalComponents;
				Double price = priceMap.get(signalComponents.getSignalDate()).getClose() * signalComponents.getSplitMultiplier();
				pnl = pnl + price;
			}
		}
		
		if(null!=prevSignalComponents && prevSignalComponents.getSignalType().equalsIgnoreCase("Buy")){
			Double price = lastBhav.getClose() * signalComponent.getSplitMultiplier();
			pnl = pnl + price;
		}
		return pnl;
	}
}
