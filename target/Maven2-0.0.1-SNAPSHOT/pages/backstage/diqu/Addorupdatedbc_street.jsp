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
<title>街道管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script src="<%=path %>/js/common/comm.js"></script>
<script src="<%=path %>/js/common/linkagesel-min.js"></script>
<script src="<%=path %>/data/js/diqu/province_district.js"></script>
  <script type="text/javascript">
	$(document).ready(function(){
		var opts12 = {
				data: province_districtdata,
				selStyle: 'margin-left: 3px;',
				select: ['#diqu'],
				//fixWidth: 120,			// 固定宽度
				//head: '---请选择--'	// 自定义
				//root:[15],             // 深层数据入口
				defVal:[${dbc_street.provinceid},${dbc_street.cityid},${dbc_street.districtid}], //默认值
				head: "----请选择----"
		};
		var linkageSel = new LinkageSel(opts12);
		$('#tj').click(function() {
		   //var v = linkageSel.getSelectedValue(); //获得最后一个有效选项
		    var arr = linkageSel.getSelectedArr();  //获得各级菜单所选值
		    //alert(arr.join(','));
			var streetname=document.getElementById("streetname");
		    var districtid=document.getElementById("districtid");
		    var cityid=document.getElementById("cityid");
		    var provinceid=document.getElementById("provinceid");
		      if(streetname.value==''){
		          alert('街道名称不能为空');
		          return;
		       }
		       if(arr[0]==''||arr[0]==null){
		    	   alert("请选择省份");
			       return;
			   }
		       if(arr[1]==''||arr[1]==null){
		    	   alert("请选择城市");
			       return;
			   }
		       if(arr[2]==null||arr[2]==''){
				   alert("请选择地区");
			       return;
		       }
		       provinceid.value=arr[0];
		       cityid.value=arr[1];
		       districtid.value=arr[2];
			   document.forms[0].submit();
		      
		});
	});
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>地区管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_street.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${dbc_street.id }" />
    <input type="hidden" id="districtid" name="districtid" value="${dbc_street.districtid }" />
    <input type="hidden" id="cityid" name="cityid" value="${dbc_street.cityid }" />
    <input type="hidden" id="provinceid" name="provinceid" value="${dbc_street.provinceid }" />
    <input type="hidden" name="createdate" value="${dbc_street.createdate }" />
    <input type="hidden" name="createuser" value="${dbc_street.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">街道名称：</td>
        <td>&nbsp;<input name="streetname" type="text" id="streetname" value="${dbc_street.streetname }" /></td>
      </tr>
      <tr>
        <td width="115px" align="right">所在地区：</td>
        <td>&nbsp;
        	<select id="diqu" ></select>
        </td>
      </tr>
      
      <tr>
        <td align="right">排序：</td>
        <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${dbc_street.sortcode }"  onkeyup="value=value.replace(/[^\d\.]/g,'')"/> 数字越大越靠前</td>
      </tr>
      <tr>
        <td align="right">&nbsp;</td>
        <td>&nbsp;<input type="button" class="sub" name="sub" value=" 保 存 " id="tj"/></td>
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
