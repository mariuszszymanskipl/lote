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




function showFileSize() {
    var input, file;

    if (!window.FileReader) {
        alert("The file API isn't supported on this browser yet.");
        return;
    }

    input = document.getElementById('image-file');
    if (!input) {
        bodyAppend("p", "Um, couldn't find the fileinput element.");
    }
    else if (!input.files) {
        bodyAppend("p", "This browser doesn't seem to support the `files` property of file inputs.");
    }
    else if (!input.files[0]) {
        bodyAppend("p", "Please select a file before clicking 'Load'");
    }
    else {
        file = input.files[0]; console.log(file);
        bodyAppend("p", "File " + file.name + " is " + file.size + " bytes in size");
    }
}
function bodyAppend(tagName, innerHTML) {
    var elm;

    elm = document.createElement(tagName);
    elm.innerHTML = innerHTML;
    document.body.appendChild(elm);
}

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#image').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
</script>



</head>
<body>
	<form method="POST" action="uploadSingleImage" enctype="multipart/form-data">

		File to upload: <input id='image-file' type="file" name="file">

		<script type="text/javascript">
                    $('#image-file').bind('change', function() {
                    var _URL = window.URL || window.webkitURL;
                    var input, file;
                    file = this.files[0];

                     <!--

                    var img = new Image();
                    img = _URL.createObjectURL(file);
                    var width = img.width,
                        height = img.height;
                         -->

                    $("#image-name").replaceWith(
                    '<input id="image-name" type="text" name="name" value="' + file.name +'">'
                    );

                    $("#file-content").replaceWith(
                    '<div id="file-content">' +
                    '<p>File name: ' + file.name + ' </p>' +
                    '<p>File size: ' + file.size + ' bytes</p>' +
                    <!--'<p>Original dimensions: ' + width + 'x' + height + '</p>' +  -->
                    '</div>');
                    if (file.size > 1024*1024) {
                        alert('This file size is: ' + file.size/1024/1024 + "MB");
                        }
                        <!-- bodyAppend("p", "File " + file.name + " is " + file.size + " bytes in size"); -->

                    readURL(this);
                    });
                </script>

		Name: <input id="image-name" type="text" name="name" value="">

		<input type='button' id='btnLoad' value='Get Size' onclick='showFileSize();'>

		<input type="submit" value="Upload"> Press here to upload the file!
	</form>
	<div id="file-content"></div>

	<img id="image" src="#" width="300" height="auto" alt="your image" />


</body>
</html>