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
<title>Update a Person</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	<div class="jumbotron">
	<h1>Update Person</h1>
	</div>
	<form action="personupdate" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<label for="firstname">New FirstName</label>
			<input id="firstname" name="firstname" value="">
		</p>
		<p>
			<label for="lastname">New LastName</label>
			<input id="lastname" name="lastname" value="">
		</p>
		<p>
			<label for="password">New Password</label>
			<input id="password" name="password" value="">
		</p>
		<p>
			<label for="email">New Email</label>
			<input id="email" name="email" value="">
		</p>
		<p>
			<label for="phone">New Phone</label>
			<input id="phone" name="phone" value="">
		</p>
		<p>
			<input type="submit" class="btn btn-lg btn-primary">
		</p>
			<a href="/CrimeBuster">
			<input type="button" class="btn btn-lg btn-primary" value="Back">
			</a>
	</form>
	<br/><br/>
	<p>
		<div class="alert alert-success" role="alert">
		<span id="successMessage"><b>${messages.success}</b></span>
		</div>
	</p>
	</div>
		<!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>