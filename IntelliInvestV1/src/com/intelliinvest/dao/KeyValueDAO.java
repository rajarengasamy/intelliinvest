package com.intelliinvest.dao;

import java.util.List;

import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.KeyValueData;

public class KeyValueDAO {
	
	private static String KEY_VALUE_SELECT_QUERY = "SELECT #KEY_FIELD# as key, #VALUE_FIELD# as value from #TABLE_NAME# ORDER BY #ORDER_FIELD#"; 
	
	public List<KeyValueData> getKeyValues(String keyField, String valueField, String tableName){
		return getKeyValues(keyField, valueField, tableName, keyField);
	}
	
	@SuppressWarnings("unchecked")
	public List<KeyValueData> getKeyValues(String keyField, String valueField, String tableName, String orderField){
		Session session = HibernateUtil.getSession();
		List<KeyValueData> keyValueDatas = session.createSQLQuery(KEY_VALUE_SELECT_QUERY
																.replaceAll("#KEY_FIELD#", keyField)
																.replaceAll("#VALUE_FIELD#", valueField)
																.replaceAll("#TABLE_NAME#", tableName)
																.replaceAll("#ORDER_FIELD#", orderField)
													).addEntity(KeyValueData.class)
												.list();
		return keyValueDatas;
	}
}
