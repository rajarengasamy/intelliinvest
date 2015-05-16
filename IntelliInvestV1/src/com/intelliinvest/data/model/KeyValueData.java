package com.intelliinvest.data.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KeyValueData implements Serializable, IntelliInvestData{
	public String key;
	public String value;

	public KeyValueData() {
	}
	
	public KeyValueData(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
		+ "\"key\":\"" + key + "\"," 
		+ "\"value\":\"" + value + "\"" 
		+ "}";
	}
}
