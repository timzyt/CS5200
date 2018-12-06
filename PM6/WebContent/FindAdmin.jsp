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
<title>Find an Administrator</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	
	<form action="findadmin" method="post">
		<div class="jumbotron">
		<h1>Search for an Administrator by Level</h1>
		</div>
		<p>
			<h2><label for="level">Level</label></h2>
			<input id="level" name="level" value="${fn:escapeXml(param.level)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<h2><span id="successMessage"><b>${messages.success}</b></span></h2>
		</p>
	</form>
	<br/>
	<br/>
	<h1>Matching Admins</h1>
      
       <table class="table table-striped">
       	<tr>
       		<th>UserName</th>
       		<th>FirstName</th>
       		<th>LastName</th>
       		<th>Email</th>
       		<th>Phone</th>
       		<th>Level</th>
       		<th>EditHistory</th>
       		<th>Delete Admin</th>
       		<th>Update Admin</th>
       	</tr>
       	<c:forEach items="${admins}" var="admin">
       		<tbody><tr>
       			<td><c:out value="${admin.getUserName()}" /></td>
       			<td><c:out value="${admin.getFirstName()}" /></td>
       			<td><c:out value="${admin.getLastName()}" /></td>
       			<td><c:out value="${admin.getEmail()}" /></td>
       			<td><c:out value="${admin.getPhone()}" /></td>
       			<td><a href="findhistory?username=<c:out value="${admin.getUserName()}" />">EditHistory</a></td>
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
