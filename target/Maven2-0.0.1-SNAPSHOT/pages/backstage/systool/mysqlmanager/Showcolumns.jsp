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
<title>数据库表</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
<script type="text/javascript" language="javascript">

  function setall(v){
    var f = document.forms[0];
    for (i=0;i<f.elements.length;i++)
    if (f.elements[i].name=="checks"){
      f.elements[i].checked = v;
    }
    f.elements["clickall"].checked = v;
  }

  function setdelete(){
    if(confirm('确认删除选中的条目吗')){
      var f = document.forms[0];
      f.action="<%=path %>/dbc_ad.action?methode=delete";
      f.submit();
    }
  }

   function deleteone(id){
     if(confirm('确认删除选中的条目吗')){
       var f = document.forms[0]; 
       f.action="<%=path %>/dbc_ad.action?methode=deletebyid&id="+id;
       f.submit(); 
		 } 
    } 

    function ss(){ 
      var f = document.forms[0]; 
      f.action="<%=path %>/dbc_ad.action?methode=list";
      f.submit();
    }

    function showjiegou(tablename){
    	window.showModalDialog(encodeURI("<%=path %>/dbc_util.action?methode=showjiegou&tablename="+tablename,"new","dialogHeight:450px;dialogWidth:450px;edge:Raised;center:Yes;help:No;resizable:no;status:no;")); 
     }

    function addsuoyin(columnsname){
        if(confirm('确定要为列 '+columnsname+' 创建索引吗')){
            var f = document.forms[0]; 
            f.action="<%=path %>/dbc_util.action?methode=addindex&column="+columnsname+"&indexname="+columnsname;
            f.submit(); 
     		 } 
     }

  </script>
</head>

    <body style="min-height:500px;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
        <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>表${tablename }结构</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
        <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td> 
      </tr> 
      <tr>
        <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
        <td valign="top" bgcolor="#F7F8F9"> 

      <!-- ----------------------------------------主体内容开始---------------------------------------------------- -->
      <form name="f" action="" method="post">
      <input type="hidden" name="nowpageString" id="nowpageString" value="${pageParm.nowpage }"/>
 	  <input type="tablename" name="tablename" id="${tablename }"/>
      <input type="hidden" name="gotopagetype" id="gotopagetype"/>
      <input type="hidden" name="gotopageString" id="gotopageString"/>
      <table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB;">
      <tr>
        <td width="20%">&nbsp;
        </td>
        <td  align="right">
        </td>
        <td width="150px" align="right"></td>
      </tr>
    </table>
    <table id="listtable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <th width="40px">序号</th>
        <th width="120px;">列名</th>
        <th width="120px;">数据类型</th>
        <!-- <th width="80px;">操作</th>  -->
      </tr>
      <c:forEach items="${columns}" var="columns" varStatus="v">
      <tr class="hovertr">
        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>
        <td>${columns[0] }</td>
        <td>${columns[1] }</td>
       <!-- <td><a href="javascript:addsuoyin('${columns[0] }')">创建索引</a></td> -->
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
