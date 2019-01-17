<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Crime Report Comments</title>
</head>
<body>
	<div class="container theme-showcase" role="main">

	<div class="jumbotron">
	<h1>${messages.reportId}</h1>
	</div>
        <table class="table table-striped">
            <thead><tr>
                <th>CommentId</th>
                <th>UserName</th>
                <th>ReportId</th>
                <th>Content</th>
                <th>Created</th>
            </tr></thead>
            <c:forEach items="${comments}" var="comment" >
                <tbody><tr>
                    <td><c:out value="${comment.getCommentId()}" /></td>
                    <td><c:out value="${comment.getUser().getUserName()}" /></td>
                    <td><c:out value="${comment.getReport().getReportId()}" /></td>
                    <td><c:out value="${comment.getCommentContent()}" /></td>
                    <td><fmt:formatDate value="${comment.getCommentTimestamp()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>
                </tr></tbody>
            </c:forEach>
       </table>
       		<a href="/CrimeBuster">
			<input type="button" class="btn btn-lg btn-primary" value="Back">
			</a>
	</div>

    <!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>