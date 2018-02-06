<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
					<li><a href="image">Images</a></li>
					<li><a href="jMeterTest">Tests</a></li>
				</ul>
			</div>
		</div>
</nav>

<div class="container">
    <div class="page-header">
        <h2>Image Service Load Test with jMeter <small>Demo version</small></h2>
    </div>
    <div class="row">
        <div class="col-sm-8">

             <form:form class="form-horizontal" method="post" modelAttribute="param" action="demotest/execute" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="selectTest" class="col-sm-3 control-label">Test:</label>
                    <div class="col-sm-9">
                        <form:select class="form-control" id="selectTest" path="jMeterTest">
                            <form:option selected="selected" value="" label="Select jMeter load test" />
                            <form:options items="${jMeterTests}" />
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="selectImage" class="col-sm-3 control-label">Image:</label>
                    <div class="col-sm-9">
                        <form:select class="form-control" id="selectImage" path="imageName">
                            <form:option selected="selected" value="" label="Select the image for the load test" />
                            <form:options items="${images}" itemValue="name" itemLabel="name" />
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputNumberOfUsers" class="col-sm-3 control-label">Number of users:</label>
                    <div class="col-sm-4">
                        <form:input class="form-control" id="inputNumberOfUsers" placeholder="Number of users (threads)" path="numberOfUsers" type="text" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputDuration" class="col-sm-3 control-label">Duration (in sec):</label>
                    <div class="col-sm-4">
                        <form:input class="form-control" id="inputDuration" placeholder="Duration (in sec)" path="duration" type="number" />
                    </div>
                </div>

             <input class="btn btn-primary btn-lg btn-block" formtarget="_blank" type="submit" onclick="startRequest();" value="Start Load Test">

             </form:form>

             <br><br>

            <!-- <input type="button" value="Send request for status" onclick="startRequest();"/> -->
            <div id="requestStatus"></div>
            <br>
        </div>

        <div class="col-sm-4">
        </div>

    </div>
</div>




	<script type="text/javascript" >
                        /*<![CDATA[*/
                        function startRequest() {
                            var xhr = new XMLHttpRequest();
                            xhr.open("POST", "/start", true);

                            xhr.onreadystatechange = function() {
                                if(xhr.readyState === 4 && xhr.status === 200) {
                                    console.log("Start long polling.");
                                    document.getElementById('requestStatus').innerHTML = xhr.responseText;
                                    getStatus();
                                }
                            }
                            xhr.send();
                        }

                        function getStatus() {
                            var xhr = new XMLHttpRequest();

                            xhr.open("GET", "/status", true);

                            xhr.onreadystatechange = function() {
                                if(xhr.readyState == 4 && xhr.status == 200) {
                                    document.getElementById('requestStatus').innerHTML = xhr.responseText;
                                    if (xhr.responseText !== "DONE") {
                                        poll();
                                    } else {
                                        console.log("Stop polling");
                                    }
                                }
                            }
                            xhr.send();
                        }

                        function poll() {
                            setTimeout(getStatus, 1000);
                        }
                        /*]]>*/
                    </script>


	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>