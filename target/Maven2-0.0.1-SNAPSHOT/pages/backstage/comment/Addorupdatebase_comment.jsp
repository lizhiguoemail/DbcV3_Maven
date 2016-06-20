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
<title>评论通用类管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
  <script type="text/javascript">
    function tj(){
      document.forms[0].submit();
    }
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>评论通用类管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_comment.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${base_comment.id }" />
    <input type="hidden" name="createdate" value="${base_comment.createdate }" />
    <input type="hidden" name="createuser" value="${base_comment.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">评论对象id：</td>
        <td>&nbsp;<input name="objid" type="text" id="objid" value="${base_comment.objid }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">评论对象名称：</td>
        <td>&nbsp;<input name="objname" type="text" id="objname" value="${base_comment.objname }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">是否是注册用户：</td>
        <td>&nbsp;<input name="isreg" type="text" id="isreg" value="${base_comment.isreg }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">楼层：</td>
        <td>&nbsp;<input name="floor" type="text" id="floor" value="${base_comment.floor }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">评论类型：</td>
        <td>&nbsp;<input name="type" type="text" id="type" value="${base_comment.type }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">邮箱：</td>
        <td>&nbsp;<input name="email" type="text" id="email" value="${base_comment.email }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">个人主页：</td>
        <td>&nbsp;<input name="gerenzhuye" type="text" id="gerenzhuye" value="${base_comment.gerenzhuye }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">评论内容：</td>
        <td>&nbsp;<input name="neirong" type="text" id="neirong" value="${base_comment.neirong }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">用户名：</td>
        <td>&nbsp;<input name="uname" type="text" id="uname" value="${base_comment.uname }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">ip：</td>
        <td>&nbsp;<input name="ip" type="text" id="ip" value="${base_comment.ip }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">ip详细地址：</td>
        <td>&nbsp;<input name="ipaddress" type="text" id="ipaddress" value="${base_comment.ipaddress }" /></td>
      </tr>
      <tr>
        <td align="right">排序：</td>
        <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${base_comment.sortcode }" /> 数字越大越靠前</td>
      </tr>
      <tr>
        <td align="right">&nbsp;</td>
        <td>&nbsp;<input type="button" class="sub" name="sub" value=" 保 存 " onclick="tj()"/></td>
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
