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
<title>消息表管理</title>
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

    function addreceive(){
    	 var arr=window.showModalDialog(encodeURI("<%=path %>/dbc_msg.action?methode=listforreceive","new","dialogHeight:450px;dialogWidth:450px;edge:Raised;center:Yes;help:No;resizable:no;status:no;")); 
    	 var receiveid=document.getElementById("receiveid");
         var receiveusername=document.getElementById("receiveusername");
         receiveid.value=arr[0];
         receiveusername.value=arr[1];
     }
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>消息表管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_msg.action?methode=send" method="post" name="form1">
    <input type="hidden" name="id" value="${dbc_msg.id }" />
    <input type="hidden" name="createdate" value="${dbc_msg.createdate }" />
    <input type="hidden" name="createuser" value="${dbc_msg.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <input type="hidden" name="sendid" value="${backstage_user.id }" />
    <input type="hidden" name="sendusername" value="${backstage_user.username }" />
    <input type="hidden" name="receiveid" id="receiveid" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">收件人：</td>
        <td>&nbsp;<input name="receiveusername" type="text" id="receiveusername" value="${dbc_msg.receiveusername }" onclick="addreceive()" readonly="readonly"/><a href="javascript:addreceive();"></a></td>
      </tr>
      <tr>
        <td width="115px" align="right">标题：</td>
        <td>&nbsp;<input name="title" type="text" id="title" value="${dbc_msg.title }" style="width:300px;"/></td>
      </tr>
      <tr>
        <td width="115px" align="right">内容：</td>
        <td>&nbsp;
        <textarea name="content" id="content">${dbc_msg.content }</textarea>
      </tr>
      <c:if test="${isshow ne '1'}">
      <tr>
        <td align="right">&nbsp;</td>
        <td>&nbsp;<input type="button" class="sub" name="sub" value="发送 " onclick="tj()"/></td>
      </tr>
      </c:if>
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
