function getNews(stockCode){
	$("#newsPanel").html("Fetching latest news for " + stockCode);
	$.ajax({
		type: "GET",
		url: "news",
		data: "stockCode="+ stockCode
	}).done(function(data){
		$("#newsPanel").html(data);
	});
}