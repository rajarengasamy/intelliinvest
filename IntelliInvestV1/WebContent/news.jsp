<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2>${newsHeading}</h2>
<div class="accordion">
    <div class="panel-group" id="accordion1">
<c:forEach var="news" items="${newsList}">
    <div class="panel panel-default">
      <div class="panel-heading active">
        <h3 class="panel-title">
          <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion1" href="#collapseOne${news.id}">
            <i class="fa fa-angle-right pull-right"></i>${news.title}
          </a>
        </h3>
      </div>

      <div id="collapseOne${news.id}" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="media accordion-inner">
<!--                                         <div class="pull-left"> -->
<!--                                             <img class="img-responsive" src="images/accordion1.png"> -->
<!--                                         </div> -->
                  <div class="media-body">
                       <p>${news.pubDate}</p>
                       <p>${news.description}</p>
                       <a href="${news.link}">more...</a>
                  </div>
            </div>
        </div>
      </div>
    </div>
  </c:forEach>
  </div><!--/#accordion1-->
</div><!--/#accordion-->
                       