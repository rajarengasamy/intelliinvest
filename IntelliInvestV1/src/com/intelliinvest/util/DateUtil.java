package com.intelliinvest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date getDate(String date){
		try{
			return new SimpleDateFormat("yyyyMMdd").parse(date);
		}catch(Exception e){
			return new Date();
		}
		
	}
	
	public static Date getDate(String format, String date){
		try{
			return new SimpleDateFormat(format).parse(date);
		}catch(Exception e){
			return new Date();
		}
		
	}
	
	public static Date getPreviousDate(Date date){
		Calendar previousDateCal = Calendar.getInstance();
		previousDateCal.setTime(date);
		previousDateCal.add(Calendar.DATE, 1);
		Date previousDate = previousDateCal.getTime();
		return previousDate;
	}
}
