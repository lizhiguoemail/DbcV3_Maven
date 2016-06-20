<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>抱歉！您需要重新登录</title>
  </head>
  
  <body>
  <div style="padding-top:50px;">
  	<font style="padding-left:20px;font:size:24px;font-family: 微软雅黑;">您太长时间没有操作了，请重新登录再操作。</font><br/>
  </div>
  </body>
</html>
