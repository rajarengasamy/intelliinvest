package com.intelliinvest.data.model;


@SuppressWarnings("serial")
public class NewsData implements IntelliInvestData{
	Integer id;
	String title;
	String pubDate;
	String description;
	String link;
	
	public NewsData() {
	}
	
	
	public NewsData(Integer id, String title, String pubDate, String description,
			String link) {
		super();
		this.id=id;
		this.title = title;
		this.pubDate = pubDate;
		this.description = description;
		this.link = link;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPubDate() {
		return pubDate;
	}


	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


}