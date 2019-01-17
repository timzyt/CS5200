<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link type="text/css" href="css/bootstrap.min.css" />
<link type="text/css" href="css/bootstrap-timepicker.min.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/pikaday/css/pikaday.css">

<title>Insert Crime Report</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	<div class="jumbotron">
	<h1>Insert Crime Report</h1>
	</div>
	
	<div>
		<form action="reportadd" method="post">
			<p>
				<label for="username">UserName</label>
				<input id="username" name = "username" value ="">
			</p>
			<p>
				<label for="occurredTimeStamp">Occurred Time</label>
				<input id="occurredTimeStamp" name = "occurredTimeStamp" value ="">
			</p>
			
			<p>
				<label for="initialCallTypeId">Crime Type</label>
				<input id="initialCallTypeId" name = "initialCallTypeId" value ="">
			</p>
			
			<p>
				<label for="beatsectorId">BeatSector</label>
			 	<input id="beatsectorId" name="beatsectorId" value="">
			</p>
			<p>
				<label for="neighborhoodId">Neighborhood</label>
			 	<input id="neighborhoodId" name="neighborhoodId" value="">
			</p>
			<p>
				<label for="zipcodeId">Zipcode</label>
			 	<input id="zipcodeId" name="zipcodeId" value="">
			</p>
			
			<p>
			<input type="submit" class="btn btn-lg btn-primary">
			</p>
				<a href="/CrimeBuster">
			<input type="button" class="btn btn-lg btn-primary" value="Back">
			</a>
			
		</form>	
	</div>
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