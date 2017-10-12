<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse"%>
<%@page import="controller.WatsonCon"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String msg=request.getParameter("msg");
	System.out.println("input text : "+msg);
	
	String watson = "";
	String nodevalue = "";
	
	WatsonCon wc = WatsonCon.getInstance();

	if(msg.equals("webpage")){
		watson = "<a href='http://70.12.50.61:90/serveWay/index.jsp'> order in webpage click here! </a>";
	}else if(!msg.equals("")){
		watson = wc.startConversation(msg.trim());
		nodevalue = WatsonCon.getInstance().getDialognodevalue();

		System.out.println("watson : "+watson);
		System.out.println("nodevalue : "+nodevalue);
	}
	
	try {
		String imgs = "";
		
		switch (nodevalue) {
		case "OrderStart":
			imgs = "<br><br><img src='../resources/img/menu.png' width='530'>";
			break;
		
		case "Menu":
			imgs = "<br><br><img src='../resources/img/bread.png' width='530'>";
			break;
		
		case "Bread":
			imgs = "<br><br><img src='../resources/img/vege3.jpg' width='530'>";
			break;
		
		case "Vege":
			imgs = "<br><br><img src='../resources/img/sauce.jpg' width='530'>";
			break;
		
		case "SideMenu":
			imgs = "<br><br><img src='../resources/img/cookie.png' width='530'>";
			break;

		case "Cookie":
			imgs = "<br><br><img src='../resources/img/drink.png' width='530'>";
			break;
				
		default:
				
			break;
		
		}
		
		if(watson.contains("ORDER CONFIRMATION")){
			imgs = "<br><br><br><img src='../resources/img/cookiefriend.gif' width='530'>";
		}
		if(watson.startsWith("Your order")){
			imgs = "<br><br><br><img src='../resources/img/subway_friend.jpg' width='530'>";
		}
		
		out.println(watson+imgs);
		
	} catch(Exception e)	{
		out.println("I didn't understand. You can try rephasing.");
	}
	
%>