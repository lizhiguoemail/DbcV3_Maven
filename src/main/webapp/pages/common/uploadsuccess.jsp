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
  <title>上传成功</title>
   <script type="text/javascript">
	function closeme(){
	this.window.close();
	}
	function queren(){
	window.opener.document.getElementById("${textname}").value=document.getElementById("uploadpath").value;
	closeme();
	}
	</script>
  </head>
  
  <body onload="queren()">
	<input type="text" name="uploadpath" id="uploadpath" value="${uploadpath}">
  </body>
</html>
