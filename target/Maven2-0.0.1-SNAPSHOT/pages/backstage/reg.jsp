<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>网站注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	function reg(){
		document.forms[0].submit();
	}
	</script>
	</head>
	<body>
	<form method="post" action="<%=path %>/dbc_userinfo.action?methode=reg">	
	<input type="hidden" name="usertype" value="backstage" />
	<table>
		<tr><td colspan="2">${errormsg }</td></tr>
		<tr><td>用户名：</td><td><input type="text" name="username"></td></tr>
		<tr><td>昵称：</td><td><input type="text" name="nickname"></td></tr>
		<tr><td>密码</td><td><input type="password" name="password"></td></tr>
		<tr><td>确认密码</td><td><input type="password" name="password2"></td></tr>
		<tr><td>邮箱：</td><td><input type="text" name="email"></td></tr>
		<tr><td>QQ：</td><td><input type="text" name="qq"></td></tr>
		<tr><td>联系电话：</td><td><input type="text" name="mobile"></td></tr>
		<tr><td colspan="2" align="center"><input type="button" value="注册" onclick="reg()"></td></tr>
	</table>
	</form>	
	</body>