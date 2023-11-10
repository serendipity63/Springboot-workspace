<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String data1_p1 = (String) request.getAttribute("data1");
String data1_p2 = (String) session.getAttribute("data1");
String data1_p3 = request.getParameter("data1");

String data1_a1 = (String) request.getAttribute("data2");
String data1_a2 = (String) session.getAttribute("data2");
String data1_a3 = request.getParameter("data2");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% pageContext.include("header.jsp"); %>

	data1_p1 : <%=data1_p1%>:${requestScope.data1}:${data1 } <br /> 
	data1_p2 : <%=data1_p2%>:${sessionScope.data1}:${data1 }<br /> 
	data1_p3 : <%=data1_p3%>:${param.data1}:${data1 }<br />
<hr/>
	data1_a1 : <%=data1_a1%>:${requestScope.data2}:${data2 }<br /> 
	data1_a2 : <%=data1_a2%>:${sessionScope.data2}:${data2 }<br /> 
	data1_a3 : <%=data1_a3%>:${param.data2}:${data2 }<br />

</body>
</html>