package com.intelliinvest.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intelliinvest.dao.LatestStockSignalDAO;
import com.intelliinvest.dao.WatchListDAO;
import com.intelliinvest.data.model.LatestStockSignalData;
import com.intelliinvest.data.model.WatchListData;

@Controller
public class WatchListServlet {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = { "watchList" }, method = RequestMethod.GET)
	public ModelAndView watchList() {
		logger.info("Calling Watch List");
		ModelAndView model = new ModelAndView("watch-list");
		model.addObject("title", "Watch List");
		return model;
	}
	@RequestMapping(value = { "watchList/getAll" }, method = RequestMethod.GET)
	public ModelAndView getAll() {
		logger.info("Calling watch list getAll");
		List<WatchListData> watchList = WatchListDAO.getWatchList("rrangasw");
		Map<String, LatestStockSignalData> latestSignals = LatestStockSignalDAO.getLatestStockDetailsAsMap();
		for(WatchListData data : watchList){
			
		}
		ModelAndView model = new ModelAndView("watch-list");
		model.addObject("title", "Watch List");
		return model;
	}
	@RequestMapping(value = { "watchList/add" }, method = RequestMethod.GET)
	public ModelAndView add() {
		logger.info("Calling suggestions");
		ModelAndView model = new ModelAndView("watch-list");
		model.addObject("title", "Watch List");
		return model;
	}
	
	@RequestMapping(value = { "watchList/delete" }, method = RequestMethod.GET)
	public ModelAndView suggestions() {
		logger.info("Calling suggestions");
		ModelAndView model = new ModelAndView("watch-list");
		model.addObject("title", "Watch List");
		return model;
	}
	

}