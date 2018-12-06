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
<title>Find a Person</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	
	<form action="findperson" method="post">
		<h1>Search for a Person by FirstName</h1>
		<p>
			<label for="firstname">FirstName</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<h3><div id="personCreate"><a href="personcreate">Create Person</a></div></h3>
	<br/>
	<h1>Matching Persons</h1>
      
       <table border="1">
       	<tr>
       		<th>UserName</th>
       		<th>FirstName</th>
       		<th>LastName</th>
       		<th>Email</th>
       		<th>Phone</th>
       		<th>Delete Person</th>
       		<th>Update Person</th>
       	</tr>
       	<c:forEach items="${persons}" var="person">
       		<tr>
       			<td><c:out value="${person.getUserName()}" /></td>
       			<td><c:out value="${person.getFirstName()}" /></td>
       			<td><c:out value="${person.getLastName()}" /></td>
       			<td><c:out value="${person.getEmail()}" /></td>
       			<td><c:out value="${person.getPhone()}" /></td>
       			<td><a href="persondelete?username=<c:out value="${person.getUserName()}" />">Delete Person</a></td>
       			<td><a href="personupdate?username=<c:out value="${person.getUserName()}" />">Update Person</a></td>
       		</tr>
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
