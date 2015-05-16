package com.intelliinvest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intelliinvest.dao.NewsDAO;
import com.intelliinvest.dao.SuggestionDAO;
import com.intelliinvest.data.model.SuggestionData;

@Controller
public class SuggestionsServlet {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = { "suggestions" }, method = RequestMethod.GET)
	public ModelAndView suggestions() {
		logger.info("Calling suggestions");
		ModelAndView model = new ModelAndView("suggestions");
		model.addObject("title", "Suggestions");
		List<SuggestionData> suggestions = SuggestionDAO.getSuggestions();
		model.addObject("suggestionsMap", getUniqueSuggestions(suggestions));
		model.addObject("newsHeading", "Top stories");
		model.addObject("newsList", NewsDAO.getTopStoreisFromGoogle());
		return model;
	}
	
	private Map<String, List<SuggestionData>> getUniqueSuggestions(List<SuggestionData> suggestions){
		Map<String, List<SuggestionData>> suggestionsMap = new HashMap<String, List<SuggestionData>>();
		for(SuggestionData suggestion : suggestions){
//			if(!suggestion.getSuggestionType().equals("Type1")){
//				continue;
//			}
			if(suggestionsMap.containsKey(suggestion.getSuggestionType())){
				List<SuggestionData> suggestionList = suggestionsMap.get(suggestion.getSuggestionType());
				suggestionList.add(suggestion);
			}else{
				List<SuggestionData> suggestionList = new ArrayList<SuggestionData>();
				suggestionList.add(suggestion);
				suggestionsMap.put(suggestion.getSuggestionType(), suggestionList);
			}
		}
		return suggestionsMap;
	}

}