<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
 <div id="doing" style="position: absolute;left: 0px;top: 0px;z-index: 1200;width: 100%;height: 100%;cursor: wait;display: none;" align="center">
<table height="100%" width="100%">
<tr><td valign="middle" align="center" >
<img  src="<%=path %>/images/loading/067.gif" border="0" />
</td></tr></table>
</div>
  </body>
</html>
