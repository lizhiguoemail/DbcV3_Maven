<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>用户管理</title>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
	<script type="text/javascript">
		function tj(){
		    var pwd=document.getElementById("password");
		    	if(pwd.value==''){
		    		alert('密码不能为空！');
		    		return;
		    	}
		    	if(pwd.value.length<6){
		    		alert('密码长度不能少于6位!')
		    		return;
		    	}
		    document.forms[0].submit();
		}
		
		function tjforupdate(){
		    var pwdcheck=document.getElementById("pwd");
		    if(pwdcheck.checked){
		    	var password=document.getElementById("password");
		    	if(pwd.value==''){
		    		alert('密码不能为空！');
		    		return;
		    	}
		    	if(pwd.value.length<6){
		    		alert('密码长度不能少于6位!')
		    		return;
		    	}
		    }
		    document.forms[0].submit();
		}
	</script>
  </head>
  
 <body style="min-height:500px;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>用户管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
    <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
    
    
    <form action="<%=path %>/dbc_userinfo.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${user.id }" />
    <input type="hidden" name="usertype" value="${user.usertype }${usertype}" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
<table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
<c:if test="${not empty errormsg}">
<tr class="t_pad" style="font-size: 14px;">
	<td style="padding-top: 8px;" colspan="4" nowrap="nowrap" align="center">
		<font color="red" style="font-size: 18px;font-weight: bold;">${errormsg }</font>
	</td>
</tr>
</c:if>	
  <tr>
    <td width="115px" align="right">用户名：</td>
    <td>&nbsp;<input name="username" type="text" id="username" value="${user.username }" /></td>
  </tr>
   <tr>
    <td width="115px" align="right">昵称：</td>
    <td>&nbsp;<input name="nickname" type="text" id="nickname" value="${user.nickname }" /></td>
  </tr>
  <tr>
  	<td width="115px" align="right">密码：</td>
    <td>&nbsp;<input name="password" type="password" id="password" />
    <c:if test="${isupdate eq '1'}">
    	<input type="checkbox" id="pwd" name="pwd" value="jihuo"/>点击激活修改
    </c:if>
    </td>
  </tr>
   <tr>
    <td width="115px" align="right">真实姓名：</td>
    <td>&nbsp;<input name="tname" type="text" id="tname" value="${user.tname }" /></td>
  </tr>
  <tr>
    <td align="right">邮箱：</td>
    <td>&nbsp;<input name="email" type="text" id="email" value="${user.email }"/></td>
  </tr>
  <tr>
    <td align="right">QQ：</td>
    <td>&nbsp;<input name="qq" type="text" id="qq" value="${user.qq }" /></td>
  </tr>
  
  <tr>
    <td align="right">排序：</td>
    <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${user.sortcode }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/> 数字越大越靠前</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>&nbsp;<input type="button" class="sub" name="sub" value=" 保 存 " <c:if test="${isupdate eq '1'}">onclick="tjforupdate()"</c:if> <c:if test="${isupdate ne '1'}">onclick="tj()"</c:if> /></td>
  </tr>
</table>
</form>
       
       
       <td background="<%=path %>/images/backstage/mail_rightbg.gif">&nbsp;</td>
  </tr>
<tr>
    <td valign="bottom" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/buttom_left2.gif" width="17" height="17" /></td>
    <td background="<%=path %>/images/backstage/buttom_bgs.gif"><img src="<%=path %>/images/backstage/buttom_bgs.gif" width="17" height="17" /></td>
    <td valign="bottom" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/buttom_right2.gif" width="16" height="17" /></td>
  </tr>
</table>
  </body>
</html>
