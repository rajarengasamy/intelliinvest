package com.intelliinvest.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static String getErrorStack(Throwable e){
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		e.printStackTrace(p);
		return "\n" + s.toString();
	}
}
