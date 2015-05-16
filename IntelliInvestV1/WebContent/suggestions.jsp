<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<jsp:directive.include file="head.jsp" />

<!-- <body> -->
<body>

   <jsp:directive.include file="header.jsp" /> 

    <section id="content" class="shortcode-item">
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-7">
                    <h2>Suggestions</h2> 
                    <p>
                    Our experts panel will help you to get you those life-changing stocks, tomorrow's bluechips today. Find hidden gems that will gain steadily, and safely here. 
                    </p>
                    <div class="tab-wrap">
                        <div class="media">
                            <div class="parrent pull-left">
                                <ul class="nav nav-tabs nav-stacked">
                                	<c:set var="tabCount" scope="page" value="0"/>
                                	<c:forEach var="suggestionEntry" items="${suggestionsMap}">
                                		<c:choose>
										      <c:when test="${tabCount==0}">
												<li class="active"><a href="#${suggestionEntry.key}" data-toggle="tab" class="analistic-01">${suggestionEntry.key}</a></li>
										      </c:when>
										      <c:otherwise>
										      	<li class=""><a href="#${suggestionEntry.key}" data-toggle="tab" class="analistic-01">${suggestionEntry.key}</a></li>
										      </c:otherwise>
										</c:choose>
	                                    <c:set var="tabCount" value="${tabCount + 1}" scope="page"/>
                                    </c:forEach>
                                </ul>
                            </div>

                            <div class="parrent media-body">
                                <div class="tab-content">
                                	<c:set var="suggestionEntryCount" scope="page" value="0"/>
                                	<c:forEach var="suggestionEntry" items="${suggestionsMap}">
                                	<c:choose>
								      <c:when test="${suggestionEntryCount==0}">
										<div class="tab-pane active in" id="${suggestionEntry.key}">
								      </c:when>
								      <c:otherwise>
								      	<div class="tab-pane" id="${suggestionEntry.key}">
								      </c:otherwise>
									</c:choose>
                                        <div class="media">
                                        	<div class="media-body">
                                        	<div id="suggestion-slider${suggestionEntry.key}">
												<div id="carousel-slider${suggestionEntry.key}" class="carousel slide" data-ride="carousel">
													<!-- Indicators -->
													<c:set var="corouselCount" scope="page" value="0"/>
												  	<ol class="carousel-indicators visible-xs">
												  		<c:forEach var="suggestion" items="${suggestionEntry.value}">
													  		<c:choose>
														      <c:when test="${corouselCount==0}">
																<li data-target="#carousel-slider${suggestionEntry.key}" data-slide-to="${corouselCount}" class="active"></li>
														      </c:when>
														      <c:otherwise>
														      	<li data-target="#carousel-slider${suggestionEntry.key}" data-slide-to="${corouselCount}"></li>
														      </c:otherwise>
															</c:choose>
														    <c:set var="corouselCount" value="${corouselCount + 1}" scope="page"/>
													     </c:forEach>
												  	</ol>
												  	<c:set var="suggestionCount" scope="page" value="0"/>
												  	<div class="carousel-inner">
													<c:forEach var="suggestion" items="${suggestionEntry.value}">
														<c:choose>
													      <c:when test="${suggestionCount==0}">
															<div class="item active">
													      </c:when>
													      <c:otherwise>
													      	<div class="item">
													      </c:otherwise>
														</c:choose>
														<table>
															<tr><td>Code</td><td>${suggestion.code}</td></tr>
															<tr><td>Signal Type</td><td>${suggestion.signalType}</td></tr>
															<tr><td>Suggestion</td><td>${suggestion.suggestionType}</td></tr>
															<tr><td>Signal Date</td><td><fmt:formatDate pattern="MM/dd/yyyy" value="${suggestion.signalDate}" /></td></tr>
															<tr><td>Signal Price</td><td>${suggestion.signalPrice}</td></tr>
															<tr><td>Stop Loss Price	</td><td>${suggestion.stopLossPrice}</td></tr>
														</table>
			                                            <li class="plan-action">
								                            <a id="suggestionButton" href="javascript:getNews('${suggestion.code}')" class="btn btn-primary btn-lg">View related news</a>
								                        </li>
								                        </div><!--/#item-->
													 <c:set var="suggestionCount" value="${suggestionCount + 1}" scope="page"/>
													 </c:forEach>
													 </div><!--/#carousel-inner-->
													<!-- <a class="left carousel-control hidden-xs" href="#carousel-slider${suggestionEntry.key}" data-slide="prev">
														<i class="fa fa-angle-left"></i> 
													</a>
													
													<a class=" right carousel-control hidden-xs" href="#carousel-slider${suggestionEntry.key}" data-slide="next">
														<i class="fa fa-angle-right"></i> 
													</a> -->
												</div> <!--/#carousel-slider-->
											</div><!--/#suggestion-slider-->
											</div><!--/.media-body--> 
                                        </div><!--/.media--> 
                                    </div><!--/.tab-pane--> 
                                    <c:set var="suggestionEntryCount" value="${suggestionEntryCount + 1}" scope="page"/>
                                    </c:forEach>
                                </div> <!--/.tab-content-->  
                            </div> <!--/.parent media-body--> 
                        </div> <!--/.media-->     
                    </div><!--/.tab-wrap-->               
                </div><!--/.col-sm-6-->

                <div class="col-xs-12 col-sm-5">
                	<div id="newsPanel">
                   		<jsp:directive.include file="news.jsp" />
                    </div><!-- #newsPanel -->
                </div><!-- #col-sm-5 -->
            </div><!--/.row-->
        </div><!--/.container-->
    </section><!--/#content-->

    
    <jsp:directive.include file="bottom.jsp" />
    <jsp:directive.include file="footer.jsp" />
	<jsp:directive.include file="postloadjs.jsp" />
    
</body>
</html>