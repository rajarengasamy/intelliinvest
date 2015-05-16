package com.intelliinvest.dao;

import java.util.List;

import org.hibernate.Session;

import com.intelliinvest.util.HibernateUtil;
import com.intelliinvest.data.model.KeyValueData;

public class ValueDAO {
	
	private static String KEY_VALUE_SELECT_QUERY = "SELECT #VALUE_FIELD# as value from #TABLE_NAME# ORDER BY #VALUE_FIELD#"; 
	
	@SuppressWarnings("unchecked")
	public List<KeyValueData> getValues(String valueField, String tableName){
		Session session = HibernateUtil.getSession();
		List<KeyValueData> keyValueDatas = session.createSQLQuery(KEY_VALUE_SELECT_QUERY
																.replaceAll("#VALUE_FIELD#", valueField)
																.replaceAll("#TABLE_NAME#", tableName)
													).addEntity(KeyValueData.class)
												.list();
		return keyValueDatas;
	}
}
