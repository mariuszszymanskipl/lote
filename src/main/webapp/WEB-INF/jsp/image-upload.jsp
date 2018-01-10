<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html lang="en">

<html>
<head>
<title>Upload Single Image</title>
<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<script type='text/javascript'>

function getMeta(url){
    var img = new Image();
    img.addEventListener("load", function(){
    $("#file-dimensions").replaceWith('<div id="file-dimensions"><p>File original dimensions: ' + this.naturalWidth + ' x ' + this.naturalHeight + '</p></div>');
    });
    img.src = url;
}

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
        var dataUri = e.target.result;
            $('#image').replaceWith('<img id="image" src=' + dataUri + ' width="300" height="auto"/>');
            getMeta(dataUri);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

</script>



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


<form method="POST" action="uploadSingleImage" enctype="multipart/form-data">
    <label class="btn btn-primary" for="ImageFile">
        <input id="ImageFile" type="file" name="file" style="display:none">
        Add new image
    </label>
    <span id="image-name"></span>
    <span id="UploadButton"></span>
</form>

<br><br>
<span id="image"></span>
<br><br>
<span id="file-content"></span>
<br>
<span id="file-dimensions"></span>

		<script type="text/javascript">
                    $('#ImageFile').bind('change', function() {

                    var fileName = this.files[0].name;
                    $("#image-name").replaceWith('<input id="image-name" type="text" name="name" value="' + fileName +'">');
                    $("#UploadButton").replaceWith('<input class="btn btn-primary" type="submit" value="Upload">');
                    $("#file-content").replaceWith('<div id="file-content"><p>File original size: ' + this.files[0].size + ' bytes</p></div>');
                    readURL(this);
                    });
        </script>






<!--
		<script type="text/javascript">
                    $('#image-file').bind('change', function() {
                    var _URL = window.URL || window.webkitURL;
                    var input, file;
                    file = this.files[0];


                    if (file.size > 1024*1024) {
                        alert('This file size is: ' + file.size/1024/1024 + "MB");
                        }
                        bodyAppend("p", "File " + file.name + " is " + file.size + " bytes in size");

                    readURL(this);
                    });
                </script>
-->

</div>

</body>
</html>