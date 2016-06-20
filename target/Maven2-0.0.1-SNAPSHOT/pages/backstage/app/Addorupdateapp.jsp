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
<title>应用表管理</title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
  <script type="text/javascript">
  
  function openpic(url,name,w,h){
    window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
  }
  
    function tj(){
     var name=document.getElementById("name");
	 if(name.value==''){
		 alert('请输入应用名称');
		 return;
	 }
      document.forms[0].submit();
    }
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>应用表管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
  	<td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_app.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${app.id }" />
    <input type="hidden" name="createdate" value="${app.createdate }" />
    <input type="hidden" name="createuser" value="${app.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">名称：</td>
        <td>&nbsp;<input name="name" type="text" id="name" value="${app.name }" />&nbsp;<font color="red">*</font></td>
      </tr>
      <tr>
        <td width="115px" align="right">类型：</td>
        <td>&nbsp;
        <select name="apptype"  id="apptype">
        	<c:forEach items="${apptypelist}" var="apptype">
        		<option value="${apptype.type_value }" <c:if test="${apptype.type_value eq app.apptype}">selected="selected"</c:if>>${apptype.type_name }</option>
        	</c:forEach>
        </select>
        </td>
      </tr>
      <tr>
        <td width="115px" align="right">推荐等级：</td>
        <td>&nbsp;
        <select name="tuijiandengji"  id="tuijiandengji">
        	<c:forEach items="${tjdjlist}" var="tjdj">
        		<option value="${tjdj.type_value }" <c:if test="${tjdj.type_value eq app.tuijiandengji}">selected="selected"</c:if>>${tjdj.type_name }</option>
        	</c:forEach>
        </select>
       </td>
      </tr>
      <tr>
        <td width="115px" align="right">授权方式：</td>
        <td>&nbsp;
        <select name="shouquanfangshi" id="shouquanfangshi">
        	<c:forEach items="${sqfslist}" var="sqfs">
        		<option value="${sqfs.type_value }" <c:if test="${sqfs.type_value eq app.shouquanfangshi}">selected="selected"</c:if>>${sqfs.type_name }</option>
        	</c:forEach>
        </select>
        </td>
      </tr>
      <tr>
        <td width="115px" align="right">支持分辨率：</td>
        <td>&nbsp;<input name="fenbianlv" type="text" id="fenbianlv" value="${app.fenbianlv }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">应用图标：</td>
        <td>&nbsp;<input name="smallpic" type="text" id="smallpic" value="${app.smallpic }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-app&textname=smallpic','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">应用截图1：</td>
        <td>&nbsp;<input name="slt1" type="text" id="slt1" value="${app.slt1 }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-app&textname=slt1','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">应用截图2：</td>
        <td>&nbsp;<input name="slt2" type="text" id="slt2" value="${app.slt2 }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-app&textname=slt2','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">应用截图3：</td>
         <td>&nbsp;<input name="slt3" type="text" id="slt3" value="${app.slt3 }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-app&textname=slt3','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">应用截图4：</td>
         <td>&nbsp;<input name="slt4" type="text" id="slt4" value="${app.slt4 }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-app&textname=slt4','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">应用截图5：</td>
         <td>&nbsp;<input name="slt5" type="text" id="slt5" value="${app.slt5 }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-app&textname=slt5','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">附件：</td>
         <td>&nbsp;<input name="fj" type="text" id="fj" value="" style="width:300px;"/><input class="sub" type="button" value="上传附件" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadatt&attpath=upload-data-import&textname=fj','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">访问地址：</td>
        <td>&nbsp;<input name="ylurl" type="text" id="ylurl" value="${app.ylurl }" />可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">作者：</td>
        <td>&nbsp;<input name="zuozhe" type="text" id="zuozhe" value="${app.zuozhe }"  /></td>
      </tr>
      <tr>
        <td align="right">排序：</td>
        <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${app.sortcode }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/> 数字越大越靠前</td>
        <!-- onkeyup="value=value.replace(/[^\d]/g,'')" 这个是不带小数点的 -->
      </tr>
      <tr>
        <td width="115px" align="right">简介：</td>
        <td>
      <textarea name="jianjie"  id="jianjie" style="width:500px;height:200px;margin:10px;"></textarea>
        </td>
      </tr>
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
