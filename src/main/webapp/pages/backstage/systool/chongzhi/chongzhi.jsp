<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>chongzhi</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
	function tijiao(){
	  document.forms[0].submit();
	}
	</script>
  </head>
  
  <body>
    <form action="dbc_chongzhi.action" method="post">
	 offer_name:<input type="text" name="offer_name" style="width:400px;"><br/>
	uid:<input type="text"  name="uid"><br/>
	vcpoints:<input type="text"  name="vcpoints"><br/>
	tid:<input type="text"  name="tid" style="width:300px;"><br/>
	<input type="button" value="tijiao" onclick="tijiao()">
	</form>
  </body>
</html>
