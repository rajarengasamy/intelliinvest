package com.intelliinvest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutUsServlet {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = { "about-us" }, method = RequestMethod.GET)
	public ModelAndView aboutUs() {
		logger.info("Calling About us");
		ModelAndView model = new ModelAndView("about-us");
		model.addObject("title", "About Us");
		return model;
	}
	
}