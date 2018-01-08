<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html lang="en">

<html>
<head>
<title>Upload Single JMeterTest</title>
<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<c:url value="src/main/resources/static.css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

<style></style>

</head>
<body>

<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">Home</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="image">Images</a></li>
					<li><a href="jMeterTest">Tests</a></li>
				</ul>
			</div>
		</div>
</nav>

<div class="container">

<form method="POST" action="uploadJMeterTest" enctype="multipart/form-data">
    <label class="btn btn-primary" for="JMeterTestFile">
        <input id="JMeterTestFile" type="file" name="file" style="display:none">
        Add new test
    </label>
    <span id="JMeterTest-name"></span>
    <span id="UploadButton"></span>
</form>

		<script type="text/javascript">
                    $('#JMeterTestFile').bind('change', function() {
                    var fileName = this.files[0].name;
                    $("#JMeterTest-name").replaceWith('<input id="JMeterTest-name" type="text" name="name" value="' + fileName +'">');
                    $("#UploadButton").replaceWith('<input class="btn btn-primary" type="submit" value="Upload">');
                    });
        </script>

	<div id="file-content"></div>

</div>

</body>
</html>