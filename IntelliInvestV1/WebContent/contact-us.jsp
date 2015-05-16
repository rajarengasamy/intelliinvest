<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:directive.include file="head.jsp" />

<!-- <body> -->
<body>

   <jsp:directive.include file="header.jsp" /> 

  
    
	<section id="contact-page">
        <div class="container">
        	<br></br><br></br>
            <div class="center">        
                <h2>Contact Us through Email</h2>
                <p class="lead">We will be more than happy to receive suggestions or respond to a query.</p>
            </div> 
            <div class="row contact-wrap"> 
                <div class="status alert alert-success" style="display: none"></div>
                <form id="main-contact-form" class="contact-form" name="contact-form" method="POST" action="sendemail">
                    <div class="col-sm-5 col-sm-offset-1">
                        <div class="form-group">
                            <label>Name *</label>
                            <input type="text" name="name" class="form-control" required="required">
                        </div>
                        <div class="form-group">
                            <label>Email *</label>
                            <input type="email" name="email" class="form-control" required="required">
                        </div>
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="number" name="phone" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Company Name</label>
                            <input type="text" name="company" class="form-control">
                        </div>                        
                    </div>
                    <div class="col-sm-5">
                        <div class="form-group">
                            <label>Subject *</label>
                            <input type="text" name="subject" class="form-control" required="required">
                        </div>
                        <div class="form-group">
                            <label>Message *</label>
                            <textarea name="message" id="message" required class="form-control" rows="8"></textarea>
                        </div>                        
                        <div class="form-group">
                            <button type="submit" name="submit" class="btn btn-primary btn-lg" required="required">Submit Message</button>
                        </div>
                    </div>
                </form> 
            </div><!--/.row-->
        </div><!--/.container-->
    </section><!--/#contact-page-->

	<section id="contact-info">
        <div class="center">                
            <h2>How to Reach Us?</h2>
        </div>
        <div class="gmap-area">
            <div class="container">
                <div class="row">
                        <ul class="row">
                            <li class="col-sm-6">
                                <address>
                                    <h2>Queries related to Site Usage / Demo</h2>
                                    <a>admin@intelliinvest.com</a>
                                </address>
                            </li>
                            
                            <li class="col-sm-6">
                                <address>
                                    <h2>Queries related to Plans / User creation</h2>
                                    <a>intellii@intelliinvest.com</a>
                                </address>
                            </li>
                        </ul>
                </div>
            </div>
        </div>
    </section>
    
    <jsp:directive.include file="bottom.jsp" />
    <jsp:directive.include file="footer.jsp" />
	<jsp:directive.include file="postloadjs.jsp" />
    
</body>
</html>