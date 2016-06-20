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

  function tj(){
	var backupname=document.getElementById("backupname");
	if(backupname.value==''){
		alert('请选择要还原的备份文件！');
		return;
	}
    if(confirm('确认还原选中的备份文件吗？请谨慎操作！')){
      var f = document.forms[0];
      f.action="<%=path %>/dbc_util.action?methode=restore_mysql";
      document.getElementById('doing').style.display='block';
      f.submit();
    }
  }
  
    function xz(){
	var backupname=document.getElementById("backupname");
	if(backupname.value==''){
		alert('请选择要下载的备份文件！');
		return;
	}
    if(confirm('确认下载选中的备份文件吗？请谨慎操作！')){
      window.open('<%=path %>/backup/database/mysql/'+backupname.value+'.sql');
    }
  }

   function deleteone(id){
     if(confirm('确认删除选中的条目吗')){
       var f = document.forms[0]; 
       f.action="<%=path %>/dbc_ad.action?methode=deletebyid&id="+id;
       f.submit(); 
		 } 
    } 

    function backup(){ 
      var f = document.forms[0]; 
      f.action="<%=path %>/dbc_util.action?methode=backup_mysql";
      document.getElementById('doing').style.display='block';
      f.submit();
    }

    function showjiegou(tablename){
    	window.showModalDialog(encodeURI("<%=path %>/dbc_util.action?methode=showcolumns&tablename="+tablename,"new","dialogHeight:550px;dialogWidth:750px;edge:Raised;center:Yes;help:No;resizable:no;status:no;")); 
     }

    function showsuoyin(tablename){
    	window.showModalDialog(encodeURI("<%=path %>/dbc_util.action?methode=showindex&tablename="+tablename,"new","dialogHeight:550px;dialogWidth:750px;edge:Raised;center:Yes;help:No;resizable:no;status:no;")); 
     }
    
     function opensc(url,name,w,h){
	    window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
	  }

  </script>
</head>

    <body style="min-height:500px;"> 
    <jsp:include page="../../../common/doing.jsp" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
        <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>数据库管理【MYSQL】</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
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
      <table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB;">
      <tr>
        <td width="160px">
        	&nbsp;&nbsp;MYSQL数据库版本： <font color="red"><c:forEach items="${version}" var="version" varStatus="v">${version }</c:forEach></font>
        </td>
        <td>
        	&nbsp;&nbsp;<input type="checkbox" name="onlyjiegou" value="1"/>只备份表结构&nbsp;&nbsp;<input type="button" value="开始备份" onclick="backup()"/>
        	&nbsp;&nbsp;<font color="red">${msg }</font>
        </td>
        <td  align="right">
        	数据还原：
          <select name="backupname" id="backupname" style="width: 160px;">
           <option value="" ></option>
          	<c:forEach items="${listbackup}" var="backup">
            <option value="${backup}" >${backup}</option>
            </c:forEach>
          </select>
          <input type="button" value="还原此备份" onclick="tj()"/>
          <input type="button" value="下载此备份" onclick="xz()"/>
          <input type="button" value="上传备份文件" onclick="opensc('<%=path %>/dbc_fun.action?methode=touploadsql&sqlpath=backup-database-mysql','upload','550','450')""/>
        </td>
        <td width="60px" align="right">&nbsp;&nbsp;</td>
      </tr>
    </table>
    <table id="listtable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <th width="3%"><input type="checkbox" onclick="setall(this.checked)" name="clickall" title="全选" value="1"/></th>
        <th width="40px">序号</th>
        <th width="120px;">表名</th>
        <th width="120px;">记录数</th>
        <th width="120px;">多余碎片</th>
        <th width="120px;">存储引擎</th>
        <th width="120px;">操作</th>
      </tr>
      <c:forEach items="${tablelist}" var="tablelist" varStatus="v">
      <tr class="hovertr">
        <td><input type='checkbox' name="checks"  value="${tablelist[0] }" /></td>

        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>
        <td>${tablelist[0] }</td>
        <td>${tablelist[1] }</td>
        <td>${tablelist[2]/1024 }KB</td>
        <td>${tablelist[3] }</td>
        <td>
            <a href="javascript:showsuoyin('${tablelist[0] }')">查看索引</a>
        	<a href="javascript:showjiegou('${tablelist[0] }')">查看结构</a>
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
