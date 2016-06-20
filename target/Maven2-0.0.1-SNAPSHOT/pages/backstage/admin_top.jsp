<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>页面管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language=JavaScript>
function logout(){
	if (confirm("您确定要退出控制面板吗？"))
	top.location = "<%=path %>/dbc_userinfo.action?methode=logout";
	return false;
}
</script>

<!-- 
<meta http-equiv="refresh" content="60">
<script language=JavaScript1.2>
function showsubmenu(sid) {
	var whichEl = eval("submenu" + sid);
	var menuTitle = eval("menuTitle" + sid);
	if (whichEl.style.display == "none"){
		eval("submenu" + sid + ".style.display=\"\";");
	}else{
		eval("submenu" + sid + ".style.display=\"none\";");
	}
}
</script>
 -->
 <base target="main">
<link href="<%=path %>/css/backstage/skin.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0">
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" class="admin_topbg">
  <tr>
    <td  height="64">
    	<div style="width:262px;height:64px;color:#fff;font-size:16px;font-family:微软雅黑;font-weight:bold;padding-left:60px;padding-top:10px;">
    		<c:if test="${not empty webconfig}">${webconfig.webtitle}</c:if>
    		<c:if test="${ empty webconfig}">DBC</c:if>
    	</div>
    </td>
    <td  valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td  height="38" class="admin_txt">管理员：<b>${backstage_user.username }</b> 您好,感谢登录使用！&nbsp;&nbsp;当前登录IP:${backstage_user.loginIp }&nbsp;&nbsp;&nbsp;&nbsp;上次登录IP：${backstage_user.loginIp }</td>
        <td width="90px" ><a href="<%=path %>/dbc_backstage.action?methode=tocenter" target="main" class="top-bu-bg" style="color:#b8d3eb;text-align:center;font-weight:bold;display:block;width:80px;height:20px;line-height:20px;">后台中心</a></td>
        <td width="90px"><a href="#" target="_self" onClick="logout();"><img src="<%=path %>/images/backstage/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td height="19" colspan="3">&nbsp;</td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>