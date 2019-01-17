<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Find a Report</title>
</head>
<body style="background-image: url('image/background0.jpg')">
	<div class="container theme-showcase" role="main">
    
	<form action="findreport" method="post">
	    <div class="jumbotron">
		<h1>CrimeReport Search</h1>
		</div>
		<p>
			<h2><label for="zipcodeId">ZipcodeId</label></h2>
			<input id="zipcodeId" name="zipcodeId" value="${fn:escapeXml(param.zipcodeId)}">
		</p>
		<p>
			<input type="submit" class="btn btn-lg btn-primary">
			<br/><br/>
		</p>
	</form>
	<h3><div id="reportadd"><a href="reportadd">Add a CrimeReport</a></div></h3>
	<br/>
	<div class="alert alert-info" role="alert">
	<h2><span id="successMessage"><b>${messages.success}</b></span></h2>
	</div>
	<br/>
	<h1>Matching CrimeReports</h1>
        <table class="table table-striped">
            <thead><tr>
                <th>ReportID</th>
                <th>UserName</th>
                <th>Occurred Time</th>
                <th>Report Time</th>
                <th>Initial Call Type</th>
                <th>Final Call Type</th>
                <th>Beat Sector</th>
                <th>Neighborhood</th>
                <th>Zipcode</th>
                <th>Comments</th>
                <th>Delete CrimeReport</th>
            <!--     <th>Comments</th> -->
            </tr></thead>
            <c:forEach items="${crimeReports}" var="crimeReport" >
                <tbody><tr>
                    <td><c:out value="${crimeReport.getReportId()}" /></td>
                    <td><c:out value="${crimeReport.getUser().getUserName()}" /></td>
                    <td><fmt:formatDate value="${crimeReport.getOccurredTimeStamp()}" pattern="yyyy-MM-dd"/></td>
                    <td><fmt:formatDate value="${crimeReport.getReportedTimeStamp()}" pattern="yyyy-MM-dd"/></td>
                		<td><c:out value="${crimeReport.getInitialCallType().getCrimeCategoryId()}" /></td>
                		<td><c:out value="${crimeReport.getFinalCallType().getCrimeCategoryId()}" /></td>
                		<td><c:out value="${crimeReport.getBeat().getBeatId()}" /></td>
                		<td><c:out value="${crimeReport.getNeighborhood().getNeighborhoodId()}" /></td>
                		<td><c:out value="${crimeReport.getZipcode().getZipcodeId()}" /></td>
                		<td><a href="reportcomments?reportId=<c:out value="${crimeReport.getReportId()}"/>">Report Comments</a></td>
                    <td><a href="reportdelete?reportId=<c:out value="${crimeReport.getReportId()}"/>">Delete</a>
     <%--            		<td><a href="reportcomments?zipcodeId=<c:out value="${crimeReport.getZipcode()}"/>">ReportComments</a></td> --%>
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