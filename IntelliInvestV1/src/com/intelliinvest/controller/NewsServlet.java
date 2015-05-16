package com.intelliinvest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.intelliinvest.dao.NewsDAO;

@Controller
public class NewsServlet {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = { "news" }, method = RequestMethod.GET)
	public ModelAndView news(@RequestParam(value="stockCode", required = false) String stockCode) {
		logger.info("Calling news");
		ModelAndView model = new ModelAndView("news");
		model.addObject("newsHeading", "Related news for " + stockCode);
		model.addObject("newsList", NewsDAO.getStockNewsFromGoogle(stockCode));
		return model;
	}
	
}