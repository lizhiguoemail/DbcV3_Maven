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
<title>权限管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
  <script type="text/javascript">
    function tj(){
      var permit_name=document.getElementById("permit_name");
      var permit_action=document.getElementById("permit_action");
      var permit_methode=document.getElementById("permit_methode");
      if(permit_name.value==""){
          alert("请输入权限名称");
      }
      if(permit_action.value==""){
          alert("请输入对应action");
      }
      document.forms[0].submit();
    }
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>权限管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_permit.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${dbc_permit.id }" />
    <input type="hidden" name="createdate" value="${dbc_permit.createdate }" />
    <input type="hidden" name="createuser" value="${dbc_permit.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">权限名称：</td>
        <td>&nbsp;<input name="permit_name" type="text" id="permit_name" value="${dbc_permit.permit_name }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">对应action：</td>
        <td>&nbsp;<input name="permit_action" type="text" id="permit_action" value="${dbc_permit.permit_action }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">对应methode：</td>
        <td>&nbsp;<input name="permit_methode" type="text" id="permit_methode" value="${dbc_permit.permit_methode }" /> 如对应多个方法，中间用|分隔，例如：delete|deletebyid；如留空不填，则代表所有方法，用allmethode取代;</td>
      </tr>
      <tr>
        <td width="115px" align="right">是否开启：</td>
        <td>&nbsp; <input type="radio" name="isopen" value="开启" <c:if test="${dbc_permit.isopen ne '关闭'}">checked="checked"</c:if>/>开启
      			   <input type="radio" name="isopen" value="关闭" <c:if test="${dbc_permit.isopen eq '关闭'}">checked="checked"</c:if>/>关闭
        </td>
      </tr>
      <tr>
        <td align="right">排序：</td>
        <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${dbc_permit.sortcode }"  onkeyup="value=value.replace(/[^\d\.]/g,'')"/> 数字越大越靠前</td>
      </tr>
      <c:if test="${isupdate eq '1'}">
      <tr>
        <td width="115px" align="right">权限标识：</td>
        <td style="color: red;font-size: 14px;">&nbsp;&nbsp;${dbc_permit.action_methode }</td>
      </tr>
      </c:if>
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
