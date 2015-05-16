package com.intelliinvest.data.model;

@SuppressWarnings("serial")
public class MagicNumberData implements IntelliInvestData{
	String symbol;
	Integer movingAverage;
	Integer magicNumber;
	Double pnl;
	
	public MagicNumberData() {
	}
    
	public MagicNumberData(String symbol, Integer movingAverage, Integer magicNumber, Double pnl) {
		super();
		this.symbol = symbol;
		this.movingAverage = movingAverage;
		this.magicNumber = magicNumber;
		this.pnl = pnl;
	}


	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getMovingAverage() {
		return movingAverage;
	}

	public void setMovingAverage(Integer movingAverage) {
		this.movingAverage = movingAverage;
	}

	public Integer getMagicNumber() {
		return magicNumber;
	}

	public void setMagicNumber(Integer magicNumber) {
		this.magicNumber = magicNumber;
	}

	public Double getPnl() {
		return pnl;
	}

	public void setPnl(Double pnl) {
		this.pnl = pnl;
	}

	@Override
	public String toString() {		
		try{
			return 
			"{" 
			+ "\"symbol\":\"" + symbol + "\","
			+ "\"movingAverage\":\"" + movingAverage + "\","
			+ "\"magicNumber\":\"" +  magicNumber + "," 
			+ "\"pnl\":" +  pnl + ""
			+ "}";			
		}catch (Exception e) {
			return null;
		}
		finally{			
		}
	}
	@Override
	public boolean equals(Object obj) {
		MagicNumberData simulationData = (MagicNumberData) obj;
		if(simulationData.symbol.equals(this.symbol) && simulationData.movingAverage.equals(this.movingAverage)){
			return true;
		}else{
			return false;
		}
	}
	
	public MagicNumberData clone() {
		MagicNumberData simulationData = new MagicNumberData(symbol, movingAverage, magicNumber, pnl); 
		return simulationData;
	}
}
