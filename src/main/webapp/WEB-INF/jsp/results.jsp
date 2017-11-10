<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
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
			<h2>Load tests completed</h2>
			<a href="/" class="btn btn-primary" role="button">Main Page</a>
			<br><br>
		</div>

		<div class="starter-template">
        			<h3>Load tests report</h3>
        			<a href="/report" class="btn btn-default" role="button">Report</a>
        			<br><br>
        		</div>

		<div class="starter-template">
        			<h3>Grafana Tentacle Application Metrics for image-service deployment</h3>
        			<a href="http://grafana.tools.appdev.io/dashboard/file/Tentacle%20Application%20Metrics.json?orgId=1&var-namespace=development&var-deployment=image-service&var-pod=All&var-target=All"
        			class="btn btn-default" role="button">Show Grafana Metrics</a>
        			<br><br>
        		</div>

        <div class="starter-template">
                			<h3>Grafana Deployment Metrics for image-service</h3>
                			<a href="http://grafana.tools.appdev.io/dashboard/file/Deployment.json?refresh=10s&orgId=1&var-namespace=development&var-deployment=image-service&var-node=All"
                			class="btn btn-default" role="button">Show Grafana Metrics</a>
                		</div>

	</div>

	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>