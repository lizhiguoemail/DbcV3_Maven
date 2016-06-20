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
<title>类别管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
  <script type="text/javascript">
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>系统环境</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="" method="post" name="form1">
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="155px" align="right">操作系统：</td>
        <td>&nbsp;${dbc_systeminfo.os_name }&nbsp;</td>
      </tr>
       <tr>
        <td width="155px" align="right">系统类型：</td>
        <td>&nbsp;${dbc_systeminfo.os_arch }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">系统版本号：</td>
        <td>&nbsp;${dbc_systeminfo.os_version }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">系统IP：</td>
        <td>&nbsp;${dbc_systeminfo.os_ip }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">系统MAC地址：</td>
        <td>&nbsp;${dbc_systeminfo.os_mac }&nbsp;</td>
      </tr>
      <!--  
      <tr>
        <td width="155px" align="right">系统时间：</td>
        <td>&nbsp;${dbc_systeminfo.os_date }&nbsp;</td>
      </tr>
      -->
       <tr>
        <td width="155px" align="right">系统CPU个数：</td>
        <td>&nbsp;${dbc_systeminfo.os_cpus }&nbsp;</td>
      </tr>
      <!--  
       <tr>
        <td width="155px" align="right">系统用户名：</td>
        <td>&nbsp;${dbc_systeminfo.os_user_name }&nbsp;</td>
      </tr>
      -->
       <tr>
        <td width="155px" align="right">当前工作目录：</td>
        <td>&nbsp;${dbc_systeminfo.os_user_dir }&nbsp;</td>
      </tr>
      <!--  
      <tr>
        <td width="155px" align="right">主目录：</td>
        <td>&nbsp;${dbc_systeminfo.os_user_home }&nbsp;</td>
      </tr>
      -->
      <tr>
        <td width="155px" align="right">Java的运行环境版本：</td>
        <td>&nbsp;${dbc_systeminfo.java_version }&nbsp;</td>
      </tr>
      <!--  
      <tr>
        <td width="155px" align="right">java默认的临时文件路径：</td>
        <td>&nbsp;${dbc_systeminfo.java_io_tmpdir }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">java 平台：</td>
        <td>&nbsp;${dbc_systeminfo.sun_desktop }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">文件分隔符：</td>
        <td>&nbsp;${dbc_systeminfo.file_separator }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">路径分隔符：</td>
        <td>&nbsp;${dbc_systeminfo.path_separator }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">行分隔符：</td>
        <td>&nbsp;${dbc_systeminfo.line_separator }&nbsp;</td>
      </tr>
      <tr>
        <td width="155px" align="right">行分隔符：</td>
        <td>&nbsp;${dbc_systeminfo.line_separator }&nbsp;</td>
      </tr>
      -->
       <tr>
        <td width="155px" align="right">服务context：</td>
        <td>&nbsp;${dbc_systeminfo.server_context }&nbsp;</td>
      </tr>
       <tr>
        <td width="155px" align="right">服务器名：</td>
        <td>&nbsp;${dbc_systeminfo.server_name }&nbsp;</td>
      </tr>
       <tr>
        <td width="155px" align="right">服务器端口：</td>
        <td>&nbsp;${dbc_systeminfo.server_port }&nbsp;</td>
      </tr>
       <tr>
        <td width="155px" align="right">服务器地址：</td>
        <td>&nbsp;${dbc_systeminfo.server_addr }&nbsp;</td>
      </tr>
       <tr>
        <td width="155px" align="right">服务协议：</td>
        <td>&nbsp;${dbc_systeminfo.server_protocol }&nbsp;</td>
      </tr>
    </table>
    </form></td>

    <td background="<%=path %>/images/backstage/mail_rightbg.gif">&nbsp;</td>
  </tr>
 </table>
 </body>
</html>
