<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/common/fun.js"></script>


<script type="text/javascript">

function setpicWH(ImgD,w,h)
{
	var image=new Image();
	image.src=ImgD.src;
	if(image.width>0 && image.height>0)
	{
		flag=true;
		if(image.width/image.height>= w/h)
		{
			if(image.width>w)
			{
				ImgD.width=w;
				ImgD.height=(image.height*w)/image.width;
				ImgD.style.display="block";
			}else{
				ImgD.width=image.width;
				ImgD.height=image.height;
				ImgD.style.display="block";
			}
		}else{
			if(image.height>h)
			{
				ImgD.height=h;
				ImgD.width=(image.width*h)/image.height;
				ImgD.style.display="block";
			}else{
				ImgD.width=image.width;
				ImgD.height=image.height;
				ImgD.style.display="block";
			}
		}
	}
}
</script>
  </head>
  
  <body>
<div style=" width:430px; height:auto; background:#CCCCCC; border:#999999 4px solid; margin:auto; margin-top:30px; padding-top:10px">

<form name="upload" method="post" action="<%=path %>/dbc_fun.action?methode=uploadpic" enctype="multipart/form-data" onSubmit="return doCheck();">
<input type="hidden" name="picpath" value="${picpath }">
<input type="hidden" name="textname" value="${textname }">

<div style="text-align:center">查看图片</div>
<table cellpadding="2" cellspacing="1" class="table_form" style="width:300px; margin:auto; text-align:center">
  <tr>
     <td align="center" style=" padding-top:20px;">
<img id="previewpic" onload="setpicWH(this,300,300)">
<script type="text/javascript">
var oldpic=window.opener.document.getElementById("${textname}").value;
if(oldpic!='')
{
	if(oldpic.indexOf('http://')>-1||oldpic.indexOf('https://')>-1){
		$("#previewpic").attr("src", oldpic); 
	}
	else{
	 	$("#previewpic").attr("src", "<%=path %>/"+oldpic); 
	}
}
else
{
	$("#previewpic").attr("src","<%=path %>/images/common/nopic.jpg"); 
}
</script>
			 </td>
   </tr>

</table>
</form>
</div>
  </body>
</html>
