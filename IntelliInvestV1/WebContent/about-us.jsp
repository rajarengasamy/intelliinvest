<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:directive.include file="head.jsp" />

<!-- <body> -->
<body>

   <jsp:directive.include file="header.jsp" /> 

    <section id="about-us">
        <div class="container">
			<div class="center wow fadeInDown">
				<h2>About Intelliinvest</h2>
				<p class="lead">This site helps you manage your equity portfolio and provides you trading signals based on technical analysis. It also provides you all information related to the stocks of your choice to make investment decisions.</p>
			</div>
			
			<!-- about us slider -->
			<div id="about-slider">
				<div id="carousel-slider" class="carousel slide" data-ride="carousel">
					<!-- Indicators -->
				  	<ol class="carousel-indicators visible-xs">
					    <li data-target="#carousel-slider" data-slide-to="0" class="active"></li>
					    <li data-target="#carousel-slider" data-slide-to="1"></li>
					    <li data-target="#carousel-slider" data-slide-to="2"></li>
				  	</ol>

					<div class="carousel-inner">
						<div class="item active">
							<img src="images/BullBear1.jpg"  class="img-responsive"> 
					   </div>
					   <div class="item">
							<img src="images/Daily Suggestions.jpg" class="img-responsive" alt=""> 
					   </div> 
					   <div class="item">
							<img src="images/Intelliinvest.jpg" width="400" class="img-responsive" alt=""> 
					   </div> 
					</div>
					
					<a class="left carousel-control hidden-xs" href="#carousel-slider" data-slide="prev">
						<i class="fa fa-angle-left"></i> 
					</a>
					
					<a class=" right carousel-control hidden-xs"href="#carousel-slider" data-slide="next">
						<i class="fa fa-angle-right"></i> 
					</a>
				</div> <!--/#carousel-slider-->
			</div><!--/#about-slider-->
			
		</div><!--/.container-->
    </section><!--/about-us-->

    <jsp:directive.include file="bottom.jsp" />
    <jsp:directive.include file="footer.jsp" />
	<jsp:directive.include file="postloadjs.jsp" />
    
</body>
</html>