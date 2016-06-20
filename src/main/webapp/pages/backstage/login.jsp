<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
<base href="<%=basePath%>">
    
    <title>系统管理员登陆</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript">
	function doLogin(){
		var username=document.getElementById("username");
		var password=document.getElementById("password");
		var captcha=document.getElementById("captcha");
		if(captcha.value==''){
			alert("请输入验证码");
			return;
		}
		if(username.value==''){
			alert('请输入帐号');
			return;
		}
		if(password.value==''){
			alert('请输入密码');
			return;
		}
		document.forms[0].submit();
	}
	
	function reg(){
		window.location.href="<%=path %>/dbc_userinfo.action?methode=toreg";
	}

	//验证码刷新
	  function reLoadImage(){
		  document.getElementById("valimg").src="<%=path %>/pages/common/image.jsp?r="+ Math.random();
		} 
	</script>
<style>
html {font-size:100.01%;}
body{ 
background:url(<%=path %>/images/backstage/login/login_bg.jpg) no-repeat center top; 
background-color:#b5c9ea;
text-align:center;
font-family:"Helvetica Neue", Arial, Helvetica, sans-serif;
}
.login{ width:400px; 
margin:0 auto; 
padding:300px 0 0 370px; 
text-align:left; 
font-size:12px;
}
</style>

  </head>
  <body>
<div class="login">
<form id="loginForm" name="loginForm" action="<%=path %>/dbc_userinfo.action?methode=login"  method="post" >
<input type="hidden" name="usertype" value="backstage" />
    <table border="0">
    <tr><td colspan="2" align="center" style="color:red;font-size: 12px;font-family: 微软雅黑;">${tip}</td></tr>
      <tr>
        <td nowrap="nowrap"><img src="<%=path %>/images/backstage/login/user.png" alt="username" width="30" height="30" align="absmiddle" />&nbsp;帐号</td>
        <td><input type="text" name="username" onkeydown ="if (event.keyCode==13) $('password').focus() " id="username" style="width: 120px;" tabindex="1" value="${username }"/></td>
      </tr>
      <tr>
        <td><img src="<%=path %>/images/backstage/login/password.png" width="30" height="30" align="absmiddle" />&nbsp;密码</td>
        <td>
        	<input type="password" name="password" id="password" onkeydown="if (event.keyCode==13) doLogin();"style="width: 120px;" tabindex="2" />
        </td>
      </tr>
       <tr>
        <td><img src="<%=path %>/images/backstage/login/password.png" width="30" height="30" align="absmiddle" />&nbsp;验证码</td>
        <td>
        	<input  type="text" onkeydown="if (event.keyCode==13) doLogin();"style="width: 120px;" tabindex="3"  name="captcha"  id="captcha" /> <img src="<%=path %>/pages/common/image.jsp" title="点击刷新" name="valimg" id="valimg" onclick="reLoadImage()" style="vertical-align: middle;">
        </td>
      </tr>
      <tr>
        <td align="center" colspan="2" ><input type="button" value="登录" onclick="doLogin()"> <input type="button" value="注册" onclick="reg()"></td>
      </tr>
    </table>
</form>
</div>
</body>