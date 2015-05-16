package com.intelliinvest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.intelliinvest.util.HibernateUtil;

public class IntelliInvestFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(IntelliInvestFilter.class);
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("init In IntelliInvestFilter");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		logger.info("Intercepted In IntelliInvestFilter");
		try{
			HibernateUtil.openSession();
			filterChain.doFilter(request, response);
			HibernateUtil.closeSession();
		}catch(Exception e){
			HibernateUtil.rollbackSession();
		}
	}
	@Override
	public void destroy() {
		logger.info("destroy In IntelliInvestFilter");
		
	}
}
