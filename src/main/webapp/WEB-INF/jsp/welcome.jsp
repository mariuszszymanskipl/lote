<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>
<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">Home</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
				<!--
					<li class="active"><a href="#">Results</a></li>
					<li><a href="#about">Logs</a></li>
				-->
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="starter-template">
			<h2>1. Image Service & Thumbor Simple Load Tests</h2>
			<p>Scenario 1: small image, 10 users, loop 10x, posting + getting + resizing + deleting</p>
			<p>Scenario 2: medium image, 10 users, loop 10x, posting + getting + resizing + deleting</p>
			<a href="start1" class="btn btn-primary" role="button">Start Load Test</a>
			<br><br>
		</div>

		<div class="starter-template">
        			<h2>2. Image Service Simple Load Test</h2>
                    <p>Scenario: medium image, 10 users, loop 10x, posting + getting + resizing + deleting</p>
        			<a href="start2" class="btn btn-primary" role="button">Start Load Test</a>
        			<br><br>
        		</div>

<div class="starter-template">
        			<h2>3. Image Service Load Tests with parameters</h2>
                    <p>Scenario: medium image, users: 10(default), duration: 10sec(default), posting + getting + resizing + deleting</p>

        			<form:form method="post" modelAttribute="param" action="start3">
                    		Number of users: <form:input path="UsersNumber" type="text" />
                    		Duration (in sec): <form:input path="Duration" type="text" />
                    		<input class="btn btn-primary" type="submit" value="Start Load Test">

                    	</form:form>
        			<br><br>
        		</div>

	</div>

	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>