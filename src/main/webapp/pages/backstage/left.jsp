<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
<base href="<%=basePath%>">
    <title>后台管理系统</title>
	<script src="<%=path %>/js/backstage/prototype.lite.js" type="text/javascript"></script>
	<script src="<%=path %>/js/backstage/moo.fx.js" type="text/javascript"></script>
	<script src="<%=path %>/js/backstage/moo.fx.pack.js" type="text/javascript"></script>
<style>
body {
	font:12px Arial, Helvetica, sans-serif;
	color: #000;
	background-color: #EEF2FB;
	margin: 0px;
}
#container {
	width: 182px;
}
H1 {
	font-size: 12px;
	margin: 0px;
	width: 240px;
	cursor: pointer;
	height: 30px;
	line-height: 20px;	
}
H1 a {
	display: block;
	width: 182px;
	color: #000;
	height: 30px;
	text-decoration: none;
	moz-outline-style: none;
	background-image: url(<%=path %>/images/backstage/menu_bgs.gif);
	background-repeat: no-repeat;
	line-height: 30px;
	text-align: center;
	margin: 0px;
	padding: 0px;
}
.content{
	width: 182px;
	height: 26px;
	
}
.MM ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	display: block;
}
.MM li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	list-style-type: none;
	display: block;
	text-decoration: none;
	height: 26px;
	width: 182px;
	padding-left: 0px;
}
.MM {
	width: 182px;
	margin: 0px;
	padding: 0px;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
	clip: rect(0px,0px,0px,0px);
}
.MM a:link {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(<%=path %>/images/backstage/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}
.MM a:visited {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(<%=path %>/images/backstage/menu_bg1.gif);
	background-repeat: no-repeat;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}
.MM a:active {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(<%=path %>/images/backstage/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}
.MM a:hover {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	font-weight: bold;
	color: #006600;
	background-image: url(<%=path %>/images/backstage/menu_bg2.png);
	background-repeat: no-repeat;
	text-align: center;
	display: block;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}
</style>
</head>

<body>
<table width="100%" height="280" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEF2FB">
  <tr>
    <td width="182" valign="top"><div id="container">
      <h1 class="type"><a href="javascript:void(0)">网站常规管理</a></h1>
      <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
          </tr>
        </table>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_webconfig.action?methode=show" target="main">基本设置</a></li>
          <li><a href="<%=path %>/dbc_nav.action?methode=list" target="main">导航设置</a></li>
          <li><a href="<%=path %>/dbc_ad.action?methode=list" target="main">广告管理</a></li>
          <li><a href="<%=path %>/dbc_slides.action?methode=list" target="main">幻灯片管理</a></li>
          <li><a href="<%=path %>/dbc_friendlink.action?methode=list" target="main">友情链接</a></li>
        </ul>
      </div>
      
      <h1 class="type"><a href="javascript:void(0)">文章管理</a></h1>
      <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
          </tr>
        </table>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_treebycai.action?methode=listtree&treetype=article_type" target="main">文章栏目</a></li>
          <li><a href="<%=path %>/dbc_article.action?methode=list" target="main">文章管理</a></li>
        </ul>
      </div>
      
      <h1 class="type"><a href="javascript:void(0)">用户管理</a></h1>
      <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
          </tr>
        </table>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_userinfo.action?methode=listuser" target="main">用户列表</a></li>
          <li><a href="<%=path %>/dbc_comment.action?methode=list" target="main">评论管理</a></li>
        </ul>
      </div>
      
       <h1 class="type"><a href="javascript:void(0)">消息管理</a></h1>
      <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
          </tr>
        </table>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_msg.action?methode=list" target="main">消息列表</a></li>
        </ul>
      </div>
      
      
      <h1 class="type"><a href="javascript:void(0)">地区配置</a></h1>
      <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
          </tr>
        </table>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_province.action?methode=list" target="main">省份</a></li>
        </ul>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_city.action?methode=list" target="main">城市</a></li>
        </ul>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_district.action?methode=list" target="main">城区</a></li>
        </ul>
         <ul class="MM">
          <li><a href="<%=path %>/dbc_street.action?methode=list" target="main">街道</a></li>
        </ul>
         <ul class="MM">
          <li><a href="<%=path %>/dbc_community.action?methode=list" target="main">社区</a></li>
        </ul>
         <ul class="MM">
          <li><a href="<%=path %>/dbc_province.action?methode=tomanage" target="main">数据维护</a></li>
        </ul>
      </div>
      
      
      
    <h1 class="type"><a href="javascript:void(0)">下拉框配置</a></h1>
      <div class="content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
            </tr>
          </table>
       <ul class="MM">
          <li><a href="<%=path %>/dbc_typegroup.action?methode=list" target="main">下拉框组管理</a></li>
        </ul>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_type.action?methode=list" target="main">下来列表管理</a></li>
        </ul>
      </div>
       <h1 class="type"><a href="javascript:void(0)">管理员</a></h1>
      <div class="content">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
            </tr>
          </table>
         <ul class="MM">
          <li><a href="<%=path %>/dbc_userinfoadmin.action?methode=listadmin" target="main">管理员列表</a></li>
          <li><a href="<%=path %>/dbc_role.action?methode=list" target="main">角色管理</a></li>
          <li><a href="<%=path %>/dbc_permit.action?methode=list" target="main">权限管理</a></li>
        </ul>
      </div>
      <h1 class="type"><a href="javascript:void(0)">系统工具</a></h1>
      <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=path %>/images/backstage/menu_topline.gif" width="182" height="5" /></td>
          </tr>
        </table>
        <ul class="MM">
          <li><a href="<%=path %>/dbc_createcode.action?methode=toCreateCode" target="main">代码生成器</a></li>
          <li><a href="<%=path %>/dbc_log.action?methode=list" target="main">日志管理</a></li>
          <li><a href="<%=path %>/dbc_util.action?methode=tomanagerdatabase" target="main">数据库管理</a></li>
          <li><a href="<%=path %>/dbc_app.action?methode=list" target="main">应用管理</a></li>
          <li><a href="<%=path %>/dbc_util.action?methode=showsystem" target="main">系统环境</a></li>
          <li><a href="<%=path %>/dbc_token.action?methode=list" target="main">秘钥token</a></li>
        </ul>
      </div>
        <script type="text/javascript">
		var contents = document.getElementsByClassName('content');
		var toggles = document.getElementsByClassName('type');
	
		var myAccordion = new fx.Accordion(
			toggles, contents, {opacity: true, duration: 400}
		);
		myAccordion.showThisHideOpen(contents[0]);
	</script>
        </td>
  </tr>
</table>
</body>
</html>
