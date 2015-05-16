package com.intelliinvest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intelliinvest.dao.NewsDAO;

@Controller
public class HomeServlet {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = { "/", "/index", "/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		logger.info("Calling Home");
		ModelAndView model = new ModelAndView("home");
		model.addObject("title", "Home");
		model.addObject("newsHeading", "Top stories");
		model.addObject("newsList", NewsDAO.getTopStoreisFromGoogle());
		return model;
	}
	
}