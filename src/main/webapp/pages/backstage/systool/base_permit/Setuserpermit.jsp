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
<title>角色权限配置</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
</head>
<script type="text/javascript">
function tj(){
	document.forms[0].action="<%=path %>/dbc_permit.action?methode=setuserpermit";
	document.forms[0].submit();
}

</script>
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

    <form action="<%=path %>/dbc_permit.action?methode=setsyspermit" method="post" name="form1">
    <input type="hidden" name="userid" value="${user.id }" />
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

    <td align="right" width="120px">权限列表：</td>

    <td style="padding-left:5px">

		 <!-- 网站常规管理开始 -->
        <div class="permitdiv permit1">
       		<label>
		         <c:if test="${fn:contains(user.permitids,',01,')==false}"><input type="checkbox" name="permitid" value="01"/>&nbsp;网站常规管理&nbsp; <img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				 <c:if test="${fn:contains(user.permitids,',01,')==true}"><input type="checkbox" name="permitid" value="01" checked="checked"/>&nbsp;网站常规管理&nbsp; <img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
	    <div class="permitdiv permit2">
	    	<label >
	    		 <c:if test="${fn:contains(user.permitids,',0101,')==false}">└─<input class="base"  type="checkbox" name="permitid" value="0101" />&nbsp;基本设置&nbsp;<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				 <c:if test="${fn:contains(user.permitids,',0101,')==true}">└─<input class="base"  type="checkbox" name="permitid" value="0101" checked="checked"/>&nbsp;基本设置&nbsp;<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
	    	</label>
	    </div>
		<div class="permitdiv permit2">
			<label >
				<c:if test="${fn:contains(user.permitids,',0102,')==false}">└─<input class="base"  type="checkbox" name="permitid" value="0102" />&nbsp;导航设置<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',0102,')==true}">└─<input class="base"  type="checkbox" name="permitid" value="0102" checked="checked"/>&nbsp;导航设置<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		<div class="permitdiv permit3">
			<label >
				<c:if test="${fn:contains(user.permitids,',010201,')==false}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010201" />&nbsp;添加导航<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',010201,')==true}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010201" checked="checked"/>&nbsp;添加导航<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		<div class="permitdiv permit3">
			<label >
				<c:if test="${fn:contains(user.permitids,',010202,')==false}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010202" />&nbsp;修改导航<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',010202,')==true}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010202" checked="checked"/>&nbsp;修改导航<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		<div class="permitdiv permit3">
			<label >
				<c:if test="${fn:contains(user.permitids,',010203,')==false}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010203" />&nbsp;删除导航<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',010203,')==true}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010203" checked="checked"/>&nbsp;删除导航<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		<div class="permitdiv permit2">
			<label >
				<c:if test="${fn:contains(user.permitids,',0103,')==false}">└─<input class="base"  type="checkbox" name="permitid" value="0103" />&nbsp;友情链接<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',0103,')==true}">└─<input class="base"  type="checkbox" name="permitid" value="0103" checked="checked"/>&nbsp;友情链接<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		<div class="permitdiv permit3">
			<label >
				<c:if test="${fn:contains(user.permitids,',010301,')==false}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010301" />&nbsp;添加友情链接<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',010301,')==true}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010301" checked="checked"/>&nbsp;添加友情链接<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		<div class="permitdiv permit3">
			<label >
				<c:if test="${fn:contains(user.permitids,',010302,')==false}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010302" />&nbsp;修改友情链接<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',010302,')==true}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010302" checked="checked"/>&nbsp;修改友情链接<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		<div class="permitdiv permit3">
			<label >
				<c:if test="${fn:contains(user.permitids,',010303,')==false}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010303" />&nbsp;删除友情链接<img src="<%=path %>/images/backstage/pic22.gif" title="权限已失效" style="vertical-align: middle;"/></c:if>
				<c:if test="${fn:contains(user.permitids,',010303,')==true}">&nbsp;&nbsp;&nbsp;&nbsp;└─<input class="base"  type="checkbox" name="permitid" value="010303" checked="checked"/>&nbsp;删除友情链接<img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
			</label>
		</div>
		       
		<!-- 网站常规管理结束 -->

    	 

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
