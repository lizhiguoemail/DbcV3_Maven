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
    <title>导航管理</title>
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
    <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>导航管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
    <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
    
    
    <form action="<%=path %>/dbc_nav.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${nav.id }" />
    <input type="hidden" name="createdate" value="${nav.createdate }" />
    <input type="hidden" name="createuser" value="${nav.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
<table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
  <tr>
    <td width="115px" align="right">名称：</td>
    <td>&nbsp;<input name="title" type="text" id="title" value="${nav.title }" /></td>
  </tr>
  <tr>
    <td align="right">模块：</td>
    <td>&nbsp;<input name="nav_act" type="text" id="nav_act" value="${nav.nav_act }"/></td>
  </tr>
  <tr>
    <td align="right">行为：</td>
    <td>&nbsp;<input name="nav_mod" type="text" id="nav_mod" value="${nav.nav_mod }" /></td>
  </tr>
  <tr>
    <td align="right">导航标记：</td>
    <td>&nbsp;<input name="tag" type="text" id="tag" value="${nav.tag }"/> <a style="color:#FF6600; cursor:pointer; text-decoration:underline" id="sm" >设置向导</a> 用于模板导航关联</td>
  </tr>
  <tr>
    <td align="right">目标：</td>
    <td>&nbsp;
    <select id="target" name="target">
    	<option value="_self" <c:if test="${nav.target ne '_blank'}">selected="selected"</c:if>>本页面</option>
    	<option value="_blank" <c:if test="${nav.target eq '_blank'}">selected="selected"</c:if>>新页面</option>
    </select>
    </td>
  </tr>
  <tr>
    <td align="right">是否隐藏：</td>
    <td>&nbsp;
     <select id="hide" name="hide">
    	<option value="否" <c:if test="${nav.hide ne '是'}">selected="selected"</c:if>>否</option>
    	<option value="是" <c:if test="${nav.hide eq '是'}">selected="selected"</c:if>>是</option>
    </select>
    </td>
  </tr>
  <tr>
    <td align="right">是否是应用：</td>
    <td>&nbsp;
     <select id="plugin" name="plugin">
    	<option value="否" <c:if test="${nav.plugin ne '是'}">selected="selected"</c:if>>否</option>
    	<option value="是" <c:if test="${nav.plugin eq '是'}">selected="selected"</c:if>>是</option>
    </select> 此导航是否是一个应用插件</td>
  </tr>
  <tr>
    <td align="right">状态：</td>
    <td>&nbsp;
    <input type="radio" name="state" value="无" <c:if test="${nav.state ne 'new'||nav.state ne 'hot'}">checked="checked"</c:if>/>无
    <input type="radio" name="state" value="new" <c:if test="${nav.state eq 'new'}">checked="checked"</c:if>/>new
    <input type="radio" name="state" value="hot" <c:if test="${nav.state eq 'hot'}">checked="checked"</c:if>/>hot
    </td>
  </tr>
  <tr>
    <td align="right">自定义连接：</td>
    <td>&nbsp;<input name="url" type="text" id="url" value="${nav.url}" style="width:300px" /> 以http://开头，添加绝对地址</td>
  </tr>
  <tr>
    <td align="right">是否提示登陆：</td>
    <td>&nbsp;
     <input type="radio" name="tip" value="否" <c:if test="${nav.tip ne '是'}">checked="checked"</c:if> />否 
    <input type="radio" name="tip" value="是"  <c:if test="${nav.tip eq '是'}">checked="checked"</c:if> />是
     用于自定义链接，当自定义链接为空是，此项无效</td>
  </tr>
  <tr>
    <td align="right">父导航：</td>
    <td>&nbsp;
    	<select name="pid">
    	 <option value="0" <c:if test="${empty nav.pid || nav.pid eq '0'}">selected="selected"</c:if>>无</option>
    	 <c:forEach var="n" items="${navlist}">
    	 	<option value="${n.id }" <c:if test="${ nav.pid eq n.id}">selected="selected"</c:if>>${n.title }</option>
    	 </c:forEach>
    	</select>
  </tr>
  <tr>
    <td align="right">短说明：</td>
    <td>&nbsp;<input name="alt" type="text" id="alt" value="${nav.alt }" /> 只适用于子导航</td>
  </tr>
  <tr>
    <td align="right">排序：</td>
    <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${nav.sortcode }" /> 数字越大越靠前</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>&nbsp;<input type="button" class="sub" name="sub" value=" 保 存 " onclick="tj()"/></td>
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
