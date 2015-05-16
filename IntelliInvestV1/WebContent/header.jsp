    <header id="header">
        <div class="top-bar">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 col-xs-4">
                        <div class="top-number"><p><i class="fa fa-phone-square"></i>  admin@intelliinvest.com</p></div>
                    </div>
                    <div class="col-sm-6 col-xs-8">
                       <div class="social">
                            <ul class="social-share">
                                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                                <li><a href="#"><i class="fa fa-linkedin"></i></a></li> 
                                <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                                <li><a href="#"><i class="fa fa-skype"></i></a></li>
                            </ul>
                            <div class="search">
                                <form role="form">
                                    <input type="text" class="search-form" autocomplete="off" placeholder="Search">
                                    <i class="fa fa-search"></i>
                                </form>
                           </div>
                       </div>
                    </div>
                </div>
            </div><!--/.container-->
        </div><!--/.top-bar-->

        <nav class="navbar navbar-inverse" role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="home"><img src="images/intelliinvest_logo.png" alt="logo"></a>
                </div>
				
                <div class="collapse navbar-collapse navbar-right">
                    <ul class="nav navbar-nav">
                        <li <c:if test="${title =='Home'}">class="active"</c:if>><a href="home">Home</a></li>
                        <li <c:if test="${title =='About Us'}">class="active"</c:if>><a href="about-us">About Us</a></li>
                        <li class="dropdown<c:if test="${title =='Risk Return Matrix' || title =='Interactive Chart' }"> active</c:if>">
                         <a href="#" class="dropdown-toggle" data-toggle="dropdown">Tools<i class="fa fa-angle-down"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="riskreturnmatrix.html">Risk Return Matrix</a></li>
                                <li><a href="interactivechart.html">Interactive Chart</a></li>
                            </ul>
                        </li>
                        <li class="dropdown<c:if test="${title =='Watch List' || title =='Portfolio' }"> active</c:if>">
                         <a href="#" class="dropdown-toggle" data-toggle="dropdown">My Account <i class="fa fa-angle-down"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="blog-item.html">Portfolio</a></li>
                                <li><a href="watchList">Watch List</a></li>
                            </ul>
                        </li>
                        <li <c:if test="${title =='Suggestions'}">class="active"</c:if>><a href="suggestions">Suggestions</a></li>
                        <li <c:if test="${title =='Plans'}">class="active"</c:if>><a href="plans">Plans</a></li>
                        <li <c:if test="${title =='Contact Us'}">class="active"</c:if>><a href="contact-us">Contact Us</a></li>
                        <li><a href="blog.html">Blog</a></li> 
                        
                    </ul>
                </div>
            </div><!--/.container-->
        </nav><!--/nav-->
	</header><!--/header-->