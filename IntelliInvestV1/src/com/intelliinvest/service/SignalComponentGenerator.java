package com.intelliinvest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.intelliinvest.dao.BhavDataDAO;
import com.intelliinvest.dao.SignalComponentDAO;
import com.intelliinvest.data.model.BhavData;
import com.intelliinvest.data.model.SignalComponentData;
import com.intelliinvest.data.model.StockDetailData;
import com.intelliinvest.util.DateUtil;
import com.intelliinvest.util.IntelliInvestStore;

public final class SignalComponentGenerator {
	
	public static void generateSignals(int movingAverage) {
		for(StockDetailData stockDetailData : IntelliInvestStore.STOCK_DETAILS_MAP.values()){
			generateSignals(movingAverage, stockDetailData.getCode() );
		}
	}
	
	public static void generateSignals(int movingAverage, String symbol) {
		SignalComponentDAO.deleteSignalComponents(movingAverage, symbol);
		List<SignalComponentData> signalComponentDatas = getSignalComponents(movingAverage, symbol);
		SignalComponentDAO.insertSignalComponenets(signalComponentDatas);
	}
	
	public static List<SignalComponentData> getSignalComponents(Integer movingAverage, String symbol){
		List<BhavData> bhavDatas = BhavDataDAO.getBhavData(symbol, IntelliInvestStore.BHAV_DATA_START_DATE, new Date());
		return getSignalComponents(movingAverage, symbol, bhavDatas, null, null);
	}
	public static List<SignalComponentData> getSignalComponents(Integer movingAverage, String symbol, 
			List<BhavData> bhavDatas, List<SignalComponentData> signalPresentList, Map<Date, BhavData> priceMap){
		Integer magicNumber = IntelliInvestStore.getMagicNumber(movingAverage, symbol);
		List<SignalComponentData> signalComponents = new ArrayList<SignalComponentData>();
		SignalComponentsEnhancer signalComponentsEnhancer = new SignalComponentsEnhancer(movingAverage);
		SignalComponentData signalComponent = null;
		SignalComponentData previousSignalComponent = null;
		for(int count=1; count<bhavDatas.size(); count++){
			if(count <= movingAverage){
				signalComponent = signalComponentsEnhancer.init(bhavDatas.get(count), bhavDatas.get(count-1));
			}else{
				if(count==movingAverage+1){
					signalComponent = signalComponentsEnhancer.init(magicNumber, bhavDatas.subList(count-movingAverage+1, count), signalComponents);
				}else{
					signalComponent = signalComponentsEnhancer.init(magicNumber, bhavDatas.subList(count-movingAverage+1, count), previousSignalComponent);
				}
			}
			if(null!=signalComponent){
				signalComponents.add(signalComponent);
				previousSignalComponent = signalComponent;
				if(signalComponent.getSignalPresent().equalsIgnoreCase("Y")){
					priceMap.put(bhavDatas.get(count-1).getTimeStamp(), bhavDatas.get(count-1));
					signalPresentList.add(signalComponent);
				}
			}
		}
		return signalComponents;
	}
		
	
	public static void generateSignals(int movingAverage, Date fromDate) {
		for(StockDetailData stockDetailData : IntelliInvestStore.STOCK_DETAILS_MAP.values()){
			SignalComponentDAO.deleteSignalComponents(movingAverage, stockDetailData.getCode(), fromDate);
			List<SignalComponentData> signalComponentDatas = getSignalComponents(movingAverage, stockDetailData.getCode(), fromDate);
			SignalComponentDAO.insertSignalComponenets(signalComponentDatas);
		}
	}
	
	public static List<SignalComponentData> getSignalComponents(Integer movingAverage, String symbol, Date fromDate){
		Integer magicNumber = IntelliInvestStore.getMagicNumber(movingAverage, symbol);
		Date previousDate = DateUtil.getPreviousDate(fromDate);
		
		List<BhavData> bhavDatas = BhavDataDAO.getBhavData(symbol, fromDate, new Date());
		
		List<BhavData> bhavDatasExtra = BhavDataDAO.getBhavData(symbol, previousDate, movingAverage);
		previousDate = bhavDatasExtra.get(bhavDatasExtra.size()-1).getTimeStamp();
		bhavDatas.addAll(0, bhavDatasExtra);
		
		List<SignalComponentData> signalComponents = new ArrayList<SignalComponentData>();
		SignalComponentsEnhancer signalComponentsEnhancer = new SignalComponentsEnhancer(movingAverage);
		SignalComponentData signalComponent = null;
		SignalComponentData previousSignalComponent = SignalComponentDAO.getSignalComponents(movingAverage, symbol, previousDate);
		for(int count=movingAverage; count<bhavDatas.size(); count++){
			signalComponent = signalComponentsEnhancer.init(magicNumber, bhavDatas.subList(count-movingAverage, count), previousSignalComponent);
			if(null!=signalComponent){
				signalComponents.add(signalComponent);
				previousSignalComponent = signalComponent;
			}
		}
		return signalComponents;
	}
	
}
