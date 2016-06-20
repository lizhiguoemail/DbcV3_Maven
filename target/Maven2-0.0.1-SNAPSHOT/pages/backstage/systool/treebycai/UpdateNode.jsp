<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改类别名称</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    
	<script type="text/javascript">
	function closeme(){
	this.window.close();
	}
	function queren(){
	var arr=new Array(2);  
	arr[0]=document.getElementById("leibiename").value;
	arr[1]=document.getElementById("description").value;
	window.returnValue=arr;
	closeme();
	}
	</script>
	
  </head>
  <body>
  <table align="center">
  <tr align="center"><td height="80px;">
  <font size="2"></td><td><input type="text" id="leibiename"  style="width: 240px;" value="${nodename }"/>
  </td></tr>
   <tr>
  <td><font size="2">描述：</font></td><td><textarea name="description" id="description" cols="30" rows="4">${description }</textarea></td>
  </tr>
  <tr align="center"><td height="40px;" colspan="2">
  <input type="button"  class="button" value="确认" onclick="queren()">
  <input type="button"  class="button" value="取消" onclick="closeme()">
  </td></tr></table>
  </body>
</html>
