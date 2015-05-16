package com.intelliinvest.data.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ValueData implements Serializable, IntelliInvestData{
	public String value;

	public ValueData() {
	}
	
	public ValueData(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return 
		"{" 
		+ "\"value\":\"" + value + "\"" 
		+ "}";
	}
}
