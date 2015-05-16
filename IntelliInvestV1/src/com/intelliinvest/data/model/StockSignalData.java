package com.intelliinvest.data.model;

import java.util.Date;


@SuppressWarnings("serial")
public class StockSignalData implements IntelliInvestData{
	String code;
	Date signalDate;
	String previousSignalType;
	String signalType;

	public StockSignalData() {
	}
	
	
	
	public StockSignalData(String code, Date signalDate, String previousSignalType, String signalType) {
		super();
		this.code = code;
		this.signalDate = signalDate;
		this.previousSignalType = previousSignalType;
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

	public String getPreviousSignalType() {
		return previousSignalType;
	}

	public void setPreviousSignalType(String previousSignalType) {
		this.previousSignalType = previousSignalType;
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
			+ "\"previousSignalType\":" + previousSignalType + "," 
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
		StockSignalData stockPriceData = (StockSignalData) obj;
		if(stockPriceData.code.equals(this.code) && stockPriceData.signalDate.equals(this.signalDate)){
			return true;
		}else{
			return false;
		}
	}
	
	public StockSignalData clone() {
		StockSignalData stockPriceData = new StockSignalData(code, signalDate, previousSignalType, previousSignalType);
		return stockPriceData;
	}
}
