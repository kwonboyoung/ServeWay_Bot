<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
@import url('<c:url value="/resources/css/bubbles.css" />'); 
</style>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#btn_isay').click(function(){
		var is = $('#txt_isay').val();

		var isay = '<p class="triangle-border right">' + 
					is +
				   '</p>';
	  	$('#said').append(isay);
		$('#txt_isay').val('');

		$.get("watsonsay?isay=" + is)
		 .done(function(data) {
				var watsonsay = '<p class="triangle-border left">' + 
				data.say +
			   '</p>';
			 
			 $('#said').append(watsonsay);
			 document.body.scrollTop = document.body.scrollHeight;
		 });
	});
});
</script>
</head>
<body>
<form id="myform">
<div id="said"></div>

<input type="text" id="txt_isay" class="triangle-border center" />
<div class="container">

	<button id="btn_isay" type="button" class="btn btn-default" >전송</button>
</div>
</form>
</body>
</html>