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
  <title>出现异常</title>
  </head>
  
  <body>
  <div style="padding-top:20px;">
  	<font style="padding-left:10px;font:size:14px;">程序出现异常，异常信息如下：</font><br/>
  	<div style="padding-left:50px;margin-top:20px;">类：${action } </div><br/> 
  	<div style="padding-left:50px;margin-top:20px;">方法：${methode } </div><br/> 
  	<div style="padding-left:50px;margin-top:20px;">异常原因：${e}</div>
  </div>
  </body>
</html>
