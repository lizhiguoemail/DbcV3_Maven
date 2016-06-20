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
<title>代码生成历史</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
<script type="text/javascript" language="javascript">

function gotonew(str)
{
  window.open(str);
}

function copytoworkspace(str){
	if(confirm('您当前设置的java文件copy根路径是否是${workpath_java}，jsp文件copy根路径是否是${workpath_jsp}，如果不是请更改src下的配置文件common_config中createcode_copypath_java和createcode_copypath_jsp参数，然后再进行此操作，否则无效')){
		var f = document.forms[0]; 
	     f.action="<%=basePath %>/dbc_createcode.action?methode=tocopytoworkspace&class_name="+str;
	     document.getElementById('doing').style.display='block';
	     f.submit();
	}
}
function deletehistory(str){
	if(confirm('您确定要删除吗')){
		var f = document.forms[0]; 
	     f.action="<%=basePath %>/dbc_createcode.action?methode=deleteCreate&class_name="+str;
	     document.getElementById('doing').style.display='block';
	     f.submit();
	}
}

  </script>
</head>

    <body style="min-height:500px;"> 
    <jsp:include page="../../../common/doing.jsp" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
        <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>代码生成历史</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
        <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td> 
      </tr> 
      <tr>
        <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
        <td valign="top" bgcolor="#F7F8F9"> 

      <!-- ----------------------------------------主体内容开始---------------------------------------------------- -->
      <form name="f" action="" method="post">
      <input type="hidden" name="nowpageString" id="nowpageString" value="${pageParm.nowpage }"/>

      <input type="hidden" name="gotopagetype" id="gotopagetype"/>
      <input type="hidden" name="gotopageString" id="gotopageString"/>
      <table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#fff;">
      <tr>
        <td style="height:25px;font-size: 12px;">
        	&nbsp;&nbsp;<font color="green">当前设置的java文件copy根路径：</font> <font color="red">${workpath_java }</font>，
        	<font color="green">当前设置的jsp文件copy根路径： </font><font color="red">${workpath_jsp }</font>，
        	<font color="green">相关配置文件及参数：</font><font color="red">common_config</font>中<font color="red">createcode_copypath_java</font>和<font color="red">createcode_copypath_jsp</font>
        </td>
        
        <td width="50px" align="right">&nbsp;&nbsp;</td>
      </tr>
    </table>
    <table id="listtable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <th width="3%"><input type="checkbox" onclick="setall(this.checked)" name="clickall" title="全选" value="1"/></th>
        <th width="40px">序号</th>
        <th width="120px;">名称</th>
        <th width="120px;">操作</th>
      </tr>
      <c:forEach items="${listzip}" var="c" varStatus="v">
      <tr class="hovertr">
        <td><input type='checkbox' name="checks"  value="${c}" /></td>

        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>
        <td>${c }</td>
        <td>
        	&nbsp;<a href="<%=path %>/dbc_createcode.action?methode=showCreate&class_name=${c}">查看结构</a>
        	&nbsp;<a href="javascript:gotonew('<%=path %>/createcode/${c }.zip')">下载源码包</a>
        	&nbsp;<a href="javascript:copytoworkspace('${c }')">拷贝到工程目录</a>
        	&nbsp;<a href="javascript:deletehistory('${c }')">删除生成历史</a>
        </td>
        
      </tr>
    </c:forEach> 
    
    </table>
   </form>
 <!--------------------------------------------------- 主体内容结束--------------------------------------------------- -->
      <td background="<%=path %>/images/backstage/mail_rightbg.gif">&nbsp;</td>
    </tr>
   </table> 
 </body> 
</html>
