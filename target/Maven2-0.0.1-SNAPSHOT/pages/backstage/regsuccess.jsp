<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>注册成功</title>
	</head>
	<body>
	<div align="center" style="padding-top: 150px;">
	注册成功！ <a href="<%=path %>/pages/backstage/login.jsp">点击进入登录页面</a>  
	</div>
	</body>
</html>

