package com.intelliinvest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlansServlet {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = { "plans" }, method = RequestMethod.GET)
	public ModelAndView news() {
		logger.info("Calling Plans");
		ModelAndView model = new ModelAndView("plans");
		model.addObject("title", "Plans");
		return model;
	}
	
}