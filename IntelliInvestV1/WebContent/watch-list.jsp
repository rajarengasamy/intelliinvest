<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:directive.include file="head.jsp" />

<body>
	<script>
        $(document).ready(function() {              
            //setup the jtable that will display the results
            $('#ExpenseTableContainer').jtable({
                title: 'Table of Expenses',
                selecting: true, //Enable selecting 
                paging: true, //Enable paging
                pageSize: 10, //Set page size (default: 10)
                sorting: true, //Enable sorting
                actions: {
                    listAction: '/watchList/getAll',
                    createAction: '/watchList/add',
                    deleteAction: '/watchList/delete'
                },
                fields: {
                    code: {
                    	title: 'Code',
                        key: true,
                        list: true,
                        create: false,
                        edit: false
                    },
                    name: {
                        title: 'Name',
                    },
                    eodPrice: {
                        title: 'EOD Price',
                        width: '15%'
                    },
                    currentPrice: {
                        title: 'Current Price',
                        width: '15%'
                    },
                    signalType: {
                        title: 'Signal',
                    },
                    signalPrice: {
                        title: 'Signal Price',
                        width: '15%'
                    },
                    signalDate: {
                        title: 'Signal Date',
                        width: '15%'
                    },
                    strategyROI: {
                        title: 'Strategy ROI',
                        width: '15%'
                    },
                    chat: {
                        title: 'Chart',
                    }
                },
                rowInserted: function (event, data) {
                    //if (data.record.Name.indexOf('Andrew') >= 0) {
                        $('#ExpenseTableContainer').jtable('selectRows', data.row);
                        console.log("records inserted");
                        //$('#PeopleTableContainer').jtable('load');
                    //}
                },
                //Register to selectionChanged event to hanlde events                                     
                recordAdded: function(event, data){
                    //after record insertion, reload the records
                    $('#ExpenseTableContainer').jtable('load');
                },
                recordUpdated: function(event, data){
                    //after record updation, reload the records
                    $('#ExpenseTableContainer').jtable('load');
                }
            });
            $('#ExpenseTableContainer').jtable('load');              

        });    
    </script>
        
    <jsp:directive.include file="header.jsp" /> 
    <section id="watchList">
        <div class="container">
            <div class="center">
               <h2>Watchlist</h2>
               <div id="WatchListContainer"></div>
            </div>
        </div>
    </section><!--/#portfolio-item-->
    
    <jsp:directive.include file="bottom.jsp" />
    <jsp:directive.include file="footer.jsp" />
	<jsp:directive.include file="postloadjs.jsp" />
    
</body>
</html>    