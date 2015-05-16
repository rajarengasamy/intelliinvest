package com.intelliinvest.data.model;

import java.util.Date;

@SuppressWarnings("serial")
public class ManagePortfolioData implements IntelliInvestData{
	String id;
	String userId;
	String code;
	Date tradeDate;
	String direction;
	Integer quantity;
	Double price;
	
	public ManagePortfolioData() {
	}

	
	public ManagePortfolioData(String id, String userId, String code, Date tradeDate, String direction, Integer quantity, Double price) {
		super();
		this.id = id;
		this.userId = userId;
		this.code = code;
		this.tradeDate = tradeDate;
		this.direction = direction;
		this.quantity = quantity;
		this.price = price;
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public Date getTradeDate() {
		return tradeDate;
	}


	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	@Override
	public String toString() {		
		try{
			return 
			"{" 
			+ "\"managePortfolioId\":\"" + id + "\","
			+ "\"userId\":\"" + userId + "\","
			+ "\"code\":\"" + code + "\","
			+ "\"price\":" + price + "," 
			+ "\"tradeDate\":\"" + tradeDate + "\","
			+ "\"direction\":\"" + direction + "\","
			+ "\"quantity\":" +  quantity + "," 
			+ "\"price\":" +  price + "" +
			"}";			
		}catch (Exception e) {
			return null;
		}
		finally{			
		}
	}
	@Override
	public boolean equals(Object obj) {
		ManagePortfolioData managePortfolioData = (ManagePortfolioData) obj;
		if(managePortfolioData.id.equals(this.id)){
			return true;
		}else{
			return false;
		}
	}
	
	public ManagePortfolioData clone() {
		ManagePortfolioData managePortfolioData = new ManagePortfolioData(id, userId, code, tradeDate, direction, quantity, price);
		return managePortfolioData;
	}
}
