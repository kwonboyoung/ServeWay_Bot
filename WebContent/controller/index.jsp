<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Righteous" />
<!DOCTYPE html>
<html>
<head>
<style>
	@import url('<c:url value="/resources/css/bubbles.css" />');
</style>
<script src="<c:url value="/resources/js/jquery-3.2.1.js" />"> </script>
<script type="text/javascript">

$(function(){
	
	 $.post("controller.jsp",{msg:$('#msg').val()})
	.done(function(data) {
		$('#chatList').append('<p class="triangle-border left">' + '<img src="../resources/img/logo.jpg" width="530" height=""><br>'+
				'Hello guys!!<br>'+
				'I am servewaybot for your order. I am glad to help you. just below this message is a text input field for you to type in. Try it out!! :) '+
				'you can choose way of order. which do you prefer? '+'<br><br>★★★★★★★★★★★★★★★★★★★★★★★★★★<br><br>' +
				' - <a href="http://70.12.50.61:90/serveWay/index.jsp"> order in webpage click here! </a> or you just say "webpage"<br>'+
				' - order to me! Just say order!</p>');
	}); 
	
	
	$("#msg").keypress(function (e) {
	        if (e.which == 13){
	        	if ($("#msg").val()=='') {
	        		alert('값을 입력하셔야됩니다.');
	        	} else {
	        	   	$('#chatList').append('<p class="triangle-border right">' + $("#msg").val()  + '</p>');
		        	
					$.post("controller.jsp",{msg:$('#msg').val()})
					.done(function(data) {
						$('#chatList').append('<p class="triangle-border left">'+ data 
							+ '</p>');
						document.body.scrollTop = document.body.scrollHeight;
					});
					$('#msg').val('');
					//$("#mydiv").scrollTop($("#mydiv")[0].scrollHeight);
					//$("#mydiv").scrollTop($(document).hight());
	        	}
	        }
	    });
	
});
</script>
<meta charset="UTF-8">
<title> WATSON CONVERSATION TEST </title>
<link rel="shortcut icon" href="../favicon.ico" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div class="row" id="mydiv">
	
		<div id="chatList"></div>
		<input type="text" size="40" id="msg" class="triangle-border center" autofocus> 
	
</div>
</body>
</html>