<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>用户角色配置</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
</head>
<script type="text/javascript">
function tj(){
	document.forms[0].action="<%=path %>/dbc_userinfoadmin.action?methode=updateuser_role";
	document.forms[0].submit();
}

</script>
<body style="min-height:500px;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>用户角色配置</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="" method="post" name="form1">
    <input type="hidden" name="id" value="${user.id }" />
		<table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">

  <tr>

    <td colspan="2">&nbsp;<div style="color:red;font-size: 14px;font-family: 微软雅黑;font-weight: bold;line-height: 24px;padding-left: 20px;padding-bottom: 5px;">${msg }</div></td>

  </tr>
<tr>
<td align="right">用户名：</td>
<td align="left">
&nbsp;${user.username }</td>
</tr>
  <tr>

    <td align="right" width="120px">自定义角色：</td>

    <td style="padding-left:5px">
		<c:forEach items="${rolelist}" var="role">
			<div style="float: left;line-height: 24px;padding-left: 5px;">
				<input type="checkbox" name="roleid" value="${role.id }" <c:if test="${fn:contains(rolestr,role.id)==true}">checked="checked"</c:if>>${role.rolename }
			</div>
		</c:forEach>
    </td>
  </tr>
  
   <tr>
	 <td align="right" width="120px">系统角色：</td>

    <td style="padding-left:5px">
			<div style="float: left;line-height: 24px;padding-left: 5px;color: red;">
				<input type="checkbox" name="isadmin" value="1" <c:if test="${user.isadmin eq '1'}">checked="checked"</c:if>>超级管理员
			</div>
			<!--  
			<div style="float: left;line-height: 24px;padding-left: 5px;">
				<input type="checkbox" name="bbs_admin" value="1" <c:if test="${user.bbs_admin eq '1'}">checked="checked"</c:if>>论坛管理员
			</div>
			-->
    </td>
  </tr>

  <tr>

    <td align="right">&nbsp;</td>

    <td>&nbsp;<input type="button" class="sub" name="sub" onclick="tj()" value=" 保存设置 " />
    </td>

  </tr>

</table>

		 
    </form></td>


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
