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
<title>幻灯片管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
  <script type="text/javascript">
  function openpic(url,name,w,h){
	    window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
	  }
    function tj(){
      document.forms[0].submit();
    }
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>幻灯片管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_slides.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${dbc_slides.id }" />
    <input type="hidden" name="createdate" value="${dbc_slides.createdate }" />
    <input type="hidden" name="createuser" value="${dbc_slides.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">标题：</td>
        <td>&nbsp;<input name="slides_title" type="text" id="slides_title" value="${dbc_slides.slides_title }" style="width:300px;"/></td>
      </tr>
      <tr>
        <td width="115px" align="right">分类：</td>
        <td>&nbsp;
         <select name="slides_type"  id="slides_type">
        	<c:forEach items="${huandengpian}" var="huandengpian">
        		<option value="${huandengpian.type_value }" <c:if test="${huandengpian.type_value eq dbc_slides.slides_type}">selected="selected"</c:if>>${huandengpian.type_name }</option>
        	</c:forEach>
        </select>
        【下拉框类别值：huandengpian】
       </td>
      </tr>
      <tr>
        <td width="115px" align="right">图片：</td>
        <td>&nbsp;<input name="slides_img" type="text" id="slides_img" value="${dbc_slides.slides_img }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-slides&textname=slides_img','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
      <tr>
        <td width="115px" align="right">链接：</td>
        <td>&nbsp;<input name="slides_link" type="text" id="slides_link" value="${dbc_slides.slides_link }" style="width:300px;"/></td>
      </tr>
      <tr>
        <td width="115px" align="right">显示/隐藏：</td>
        <td>
        &nbsp;;<input type="radio" name="ishidden" value="显示" <c:if test="${dbc_slides.ishidden ne '隐藏'}">checked="checked"</c:if> />显示
        &nbsp;<input type="radio" name="ishidden" value="隐藏" <c:if test="${dbc_slides.ishidden eq '隐藏'}">checked="checked"</c:if>  />隐藏
        </td>
      </tr>
      <tr>
        <td align="right">排序：</td>
        <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${dbc_slides.sortcode }"  onkeyup="value=value.replace(/[^\d\.]/g,'')"/> 数字越大越靠前</td>
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
