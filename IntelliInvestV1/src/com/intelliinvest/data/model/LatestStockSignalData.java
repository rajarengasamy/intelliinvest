package com.intelliinvest.data.model;

import java.util.Date;


@SuppressWarnings("serial")
public class LatestStockSignalData implements IntelliInvestData{
	String code;
	Date signalDate;
	Double signalPrice;
	String signalType;

	public LatestStockSignalData() {
	}
	
	
	
	public LatestStockSignalData(String code, Date signalDate, Double signalPrice, String signalType) {
		super();
		this.code = code;
		this.signalDate = signalDate;
		this.signalPrice = signalPrice;
		this.signalType = signalType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSignalDate() {
		return signalDate;
	}

	public void setSignalDate(Date signalDate) {
		this.signalDate = signalDate;
	}

	public Double getSignalPrice() {
		return signalPrice;
	}
	
	public void setSignalPrice(Double signalPrice) {
		this.signalPrice = signalPrice;
	}

	public String getSignalType() {
		return signalType;
	}

	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}

	@Override
	public String toString() {		
		try{
			return 
			"{" 
			+ "\"code\":\"" + code + "\","
			+ "\"signalDate\":" + signalDate + "," 
			+ "\"signalPrice\":" + signalPrice + "," 
			+ "\"signalType\":" + signalType + "" 
			+ "}";			
		}catch (Exception e) {
			return null;
		}
		finally{			
		}
	}
	@Override
	public boolean equals(Object obj) {
		LatestStockSignalData stockPriceData = (LatestStockSignalData) obj;
		if(stockPriceData.code.equals(this.code) && stockPriceData.signalDate.equals(this.signalDate)){
			return true;
		}else{
			return false;
		}
	}
	
	public LatestStockSignalData clone() {
		LatestStockSignalData stockPriceData = new LatestStockSignalData(code, signalDate, signalPrice, signalType);
		return stockPriceData;
	}
}
