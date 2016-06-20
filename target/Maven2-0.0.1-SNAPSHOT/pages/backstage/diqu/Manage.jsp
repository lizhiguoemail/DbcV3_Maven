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
<title>数据导入管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
  <script type="text/javascript">
    function tj(){
    	if(confirm('确认要初始化数据吗？初始化后，省份、城市、地区、街道、社区的数据都将恢复为系统自带默认数据')){
    		 document.getElementById('doing').style.display='block';
    	     document.forms[0].submit();
        }
    }
    function gengxin(){
    	if(confirm('确定要更新缓存数据吗？')){
    		 document.getElementById('doing').style.display='block';
    		 document.forms[0].action='<%=path %>/dbc_province.action?methode=creatediqujs';
    	     document.forms[0].submit();
        }
    }

    function daochu(){
    	if(confirm('确定要导出初始数据吗？')){
    		 document.getElementById('doing').style.display='block';
    		 document.forms[0].action='<%=path %>/dbc_province.action?methode=exportdiquxml';
    	     document.forms[0].submit();
        }
    }
  </script>
</head>
<body style="min-height:500px;">
<jsp:include page="../../common/doing.jsp" />
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>地区数据管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_province.action?methode=adddiqubyxml" method="post" name="form1">
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
    <tr>
        <td ><font style="color:red;margin-left: 30px;">${msg }</font></td>
      </tr>
      <tr>
        <td style="padding-left: 10px;">
       &nbsp;<input type="button" value="地区数据缓存更新" onclick="gengxin()"/>
       &nbsp;<input type="button" value="地区数据初始化" onclick="tj()"/>
       &nbsp;<input type="button" value="导出初始数据文件" onclick="daochu()"/>
       </td>
        </tr>
       <tr>
        <td >&nbsp;</td>
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
