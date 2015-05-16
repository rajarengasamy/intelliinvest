package com.intelliinvest.dao;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.intelliinvest.data.model.NewsData;
import com.intelliinvest.util.HttpUtil;
import com.intelliinvest.util.IntelliInvestStore;

public class NewsDAO {
	private static Logger logger = Logger.getLogger(NewsDAO.class);

	static String GOOGLE_NEWS_URL = "https://www.google.com/finance/company_news?q=#EXCHANGE#:#CODE#&output=rss";
	static String GOOGLE_TOP_STORIES_NEWS_URL = "https://news.google.com/news/feeds?pz=1&cf=all&ned=in&hl=en&output=rss&topic=b";

	private static List<NewsData> extractNewsDatas(String response) throws Exception {
		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
		decoder.onMalformedInput(CodingErrorAction.REPLACE);
		decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
		ByteBuffer bb = ByteBuffer.wrap(response.getBytes());
		CharBuffer correctedResponse = decoder.decode(bb);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		List<NewsData> newsDatas = new ArrayList<NewsData>();

		Document document = builder.parse(new ByteArrayInputStream(correctedResponse.toString().getBytes()));
		NodeList items = document.getElementsByTagName("item");
		for (int count = 0; count < items.getLength(); count++) {
			Element item = (Element)items.item(count);
			String title = getIfElementExists(item, "title");
			String link = getIfElementExists(item, "link");
			String pubDate = getIfElementExists(item, "pubDate");
			String description = getIfElementExists(item, "description");
			NewsData newsData = new NewsData(count, title, pubDate, description, link);
			newsDatas.add(newsData);
		}
		return newsDatas;
	}
	
	private static List<NewsData> getErrorNewsDatas() {
		return Collections.singletonList(new NewsData(0, "Error", "", "Error fetching news", ""));
	}
	
	private static String getIfElementExists(Element item, String field){
		if(null!=item.getElementsByTagName(field)){
			return item.getElementsByTagName(field).item(0).getTextContent();
		}
		return "";
	}

	public static List<NewsData> getTopStoreisFromGoogle() {
		String response = "";
		try {
			response = HttpUtil.getFromHttpUrlAsString(GOOGLE_TOP_STORIES_NEWS_URL);
			return extractNewsDatas(response);
		} catch (Exception e) {
			logger.info("Error retreiving new from google  " + e.getMessage());
		}
		return getErrorNewsDatas();
	}

	public static List<NewsData> getStockNewsFromGoogle(String stockCode){
		String response = "";
		try {
			if (null != stockCode && !"".equals(stockCode)) {
				String exchange = "NSE";
				if (IntelliInvestStore.BSE_STOCKS.contains(stockCode)) {
					exchange = "BOM";
					stockCode = IntelliInvestStore.NSE_TO_BSE_MAP.get(stockCode);
				}
				String url = GOOGLE_NEWS_URL.replace("#CODE#", stockCode.replace("&", "%26")).replace("#EXCHANGE#",	exchange);
				response = HttpUtil.getFromHttpUrlAsString(url);
				return extractNewsDatas(response);
			}
		} catch (Exception e) {
			logger.info("Error retreiving new from google  " + e.getMessage());
		}

		return getErrorNewsDatas();
	}
}
