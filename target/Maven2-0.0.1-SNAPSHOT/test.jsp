<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/mytaglib" prefix="dbctag"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <form action="http://115.29.151.74:7777/dbcV2_66810/sq_api.action?methode=uploadfile" method="post" enctype="multipart/form-data">
   maxfilesize:<input type="text" name="maxfilesize" value=""/>
    filetype:<input type="text" name="filetype" value="image"/>
    token:<input type="text" name="token" value="f97fad16b86b33895a1968c0900aeb24"/>
    <input type="file" name="myFile" >
    <input type="submit" value="提交" />
  </form>

  </body>
</html>
