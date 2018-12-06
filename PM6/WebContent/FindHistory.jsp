<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Find Edit History for an Administrator</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
    
	<form action="findhistory" method="post">
	    <div class="jumbotron">
		<h1>Search for an Edit History</h1>
		</div>
		<p>
			<h2><label for="userName">UserName</label></h2>
			<input id="userName" name="userName" value="${fn:escapeXml(param.userName)}">
		</p>
		<p>
			<input type="submit" class="btn btn-lg btn-primary">
			<br/><br/>
		</p>
	</form>
	<br/>
	<div class="alert alert-info" role="alert">
	<h2><span id="successMessage"><b>${messages.success}</b></span></h2>
	</div>
	<br/>
	<h1>Matching Edit History</h1>
        <table class="table table-striped">
            <thead><tr>
                <th>EditHistoryId</th>
                <th>UserName</th>
                <th>ReportId Time</th>
                <th>EditTime</th>
                <th>EditComment</th>
            </tr></thead>
            <c:forEach items="${editHistory}" var="editHistory" >
                <tbody><tr>
                    <td><c:out value="${editHistory.getEditHistoryId()}" /></td>
                    <td><c:out value="${editHistory.getUser().getUserName()}" /></td>
                    <td><c:out value="${editHistory.getReportId()}" /></td>
                    <td><fmt:formatDate value="${editHistory.getEditTime()}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${editHistory.getEditHistoryId()}" /></td>
                </tr></tbody>
            </c:forEach>
       </table>
       
    </div>
     
    <!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>