package com.intelliinvest.data.model;


@SuppressWarnings("serial")
public class WatchListData implements IntelliInvestData{
	String userId;
	String code;

	public WatchListData() {
	}
	
	public WatchListData(String userId, String code) {
		this.userId = userId;
		this.code = code;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {		
		try{
			return 
			"{" 
			+ "\"userId\":\"" + userId + "\","
			+ "\"code\":\"" + code + "\"" 
			+ "}";			
		}catch (Exception e) {
			return null;
		}
		finally{			
		}
	}
	@Override
	public boolean equals(Object obj) {
		WatchListData watchListData = (WatchListData) obj;
		if(watchListData.userId.equals(this.userId) && watchListData.code.equals(this.code)){
			return true;
		}else{
			return false;
		}
	}
	
	public WatchListData clone() {
		WatchListData stockPriceData = new WatchListData(userId, code);
		return stockPriceData;
	}
}
