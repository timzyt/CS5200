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
	<h1>Insert Crime Report</h1>
	<div>
		<h1>Insert Report</h1>
		
		<form action="reportcreate" method="post">
			<p>
				<h2><label for="username">UserName</label></h2>
				<input id="username" name = "username" value ="">
			</p>
			<p>
				<input type="text" id="datepicker" value = "${picker.toString()}">
			</p>
			
			<p>
				<div class="btn-group">
					<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					Crime Category
					</button>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="">First Crime</a>
						<a class="dropdown-item" href="">Second Crime</a>
						<a class="dropdown-item" href="">Third Crime</a>
					</div>
				</div>
			</p>
			
			<p>
				<h2><label for="beatsector">BeatSector</label></h2>
			 	<input id="beatsector" name="beatsector" value="">
			</p>
			<p>
				<h2><label for="neighborhood">Neighborhood</label></h2>
			 	<input id="neighborhood" name="neighborhood" value="">
			</p>
			<p>
				<h2><label for="zipcode">Zipcode</label></h2>
			 	<input id="zipcode" name="zipcode" value="">
			</p>
			
		</form>	
	</div>
	
	
	
	
	<!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
     <script src="js/datetime.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/pikaday/pikaday.js"></script>
     <script src="pikaday.js"></script>
<script>
    var picker = new Pikaday({ field: document.getElementById('datepicker') });
</script>
</body>
</html>