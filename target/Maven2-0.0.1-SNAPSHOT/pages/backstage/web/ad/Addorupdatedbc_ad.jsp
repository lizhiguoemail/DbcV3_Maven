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
<title>广告管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/common/lhgcalendar.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/lhgcalendar.js"></script>
<style type="text/css">
.table_hidden{
display: none;
}
.table_show{
display: block;
}
</style>
  <script type="text/javascript">
  $(function(){
	  /*1.限制日期的范围是 2012-03-08到2012-05-27 (注意minDate和maxDate的格式一定要是yyyy-MM-dd) 例如 $.calendar({ minDate:'2012-03-08', maxDate:'2012-05-27' });
	  	2.是否显示按钮栏 $.calendar({ btnBar:false });
	  	3.前面的日期不能大于后面的日期
		  	$.calendar({ maxDate:'#inp12' });
		  	$.calendar({ minDate:'#inp11' });
	  	4.前面的日期不能大于后面的日期(targetFormat参数示例)
	  		$.calendar({ maxDate:'#inp14', format:'yyyyMMdd', targetFormat:'yyyy年MM月dd日' });
	  		$.calendar({ minDate:'#inp13', format:'yyyy年MM月dd日', targetFormat:'yyyyMMdd' });
	  		具体详细的说明访问域名加上dbcV2/demo/lhgcalendar/_demo/demo.html
	  */
	    $('#edate').calendar({ format:'yyyy-MM-dd HH:mm:ss',btnBar:true});
	});
	
    function tj(){
      document.forms[0].submit();
    }

    function showgaibian(){
    	 var moshi=document.getElementsByName("moshi");
    	 var changgui_table=document.getElementById("changgui_table");
    	 var zidingyi_table=document.getElementById("zidingyi_table");
    	 var moshistr="";
    	 for(var i=0;i<moshi.length;i++){
   	     if(moshi[i].checked)
   	         moshistr=moshi[i].value;
   	   	 }
   	   	 if(moshistr=='自定义模式'){
	   	   	 zidingyi_table.className="table_show";
	    	 changgui_table.className="table_hidden";
   	   	 }
   	   	 else{
	   	   	 zidingyi_table.className="table_hidden";
	    	 changgui_table.className="table_show";
   	   	 }
    }
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>广告管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_ad.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${dbc_ad.id }" />
    <input type="hidden" name="createdate" value="${dbc_ad.createdate }" />
    <input type="hidden" name="createuser" value="${dbc_ad.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">广告位置：</td>
        <td>&nbsp;<input name="adtype" type="text" id="adtype" value="${dbc_ad.adtype }" style="width:300px;"/>&nbsp;如：首页导航下</td>
      </tr>
      <tr>
        <td width="115px" align="right">广告说明：</td>
        <td>&nbsp;<input name="title" type="text" id="title" value="${dbc_ad.title }" style="width:300px;"/></td>
      </tr>
      <tr>
        <td width="115px" align="right">模式选择：</td>
        <td>&nbsp;<input type="radio" name="moshi" value="常规模式" <c:if test="${dbc_ad.moshi ne '自定义模式'}">checked="checked"</c:if> onchange="showgaibian()"/>常规规模&nbsp;<input type="radio" name="moshi" value="自定义模式" <c:if test="${dbc_ad.moshi eq '自定义模式'}">checked="checked"</c:if>  onchange="showgaibian()"/>自定义模式</td>
      </tr>
      <tr>
      <td colspan="2">
      	<table <c:if test="${dbc_ad.moshi eq '自定义模式'}">class="table_hidden"</c:if> id="changgui_table"  >
	      <tr>
	        <td width="115px" align="right">图片：</td>
	        <td>&nbsp;<input name="img" type="text" id="img" value="${dbc_ad.img }" style="width:300px;"/><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-ad&textname=img','upload','550','450')" /> 可直接添加网络地址</td>
	      </tr>
	      <tr>
	        <td width="115px" align="right">高度：</td>
	        <td>&nbsp;<input name="height" type="text" id="height" value="${dbc_ad.height }" style="width:60px;"/>&nbsp;px(单位：像素)</td>
	      </tr>
	      <tr>
	        <td width="115px" align="right">宽度：</td>
	        <td>&nbsp;<input name="width" type="text" id="width" value="${dbc_ad.width }" style="width:60px;"/>&nbsp;px(单位：像素)</td>
	      </tr>
	       <tr >
	        <td width="115px" align="right">链接：</td>
	        <td>&nbsp;<input name="link" type="text" id="link" value="${dbc_ad.link }" style="width:300px;"/></td>
	      </tr>
      	</table>
      	<table <c:if test="${dbc_ad.moshi ne '自定义模式'}">class="table_hidden"</c:if> id="zidingyi_table" >
      		<tr>
		       <td width="115px" align="right" >自定义代码：</td>
		       <td>&nbsp;
		        <textarea name="content" id="content" style="margin: 5px;">${dbc_ad.content }</textarea>
		        如果添加的是js代码，不要有“//”，“&lt;!--”，“//--&gt;”等注释符号。
		       </td>
		     </tr>
      	</table>
      </td>
      </tr>
      
      <!--
      <tr>
        <td width="115px" align="right">背景色：</td>
        <td>&nbsp;<input name="bgcolor" type="text" id="bgcolor" value="${dbc_ad.bgcolor }" style="width:80px;"/>&nbsp;填写颜色代码，如：#f00</td>
      </tr>
      -->
     
       <!--
       <tr>
        <td width="115px" align="right">调用方式：</td>
        <td>&nbsp;<input type="radio" name="diaoyongfangshi" value="js"  <c:if test="${dbc_ad.diaoyongfangshi ne 'jsp'}">checked="checked"</c:if>/>js方式&nbsp;<input type="radio" name="diaoyongfangshi" value="jsp" <c:if test="${dbc_ad.diaoyongfangshi eq 'jsp'}">checked="checked"</c:if> />jsp方式    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;广告代码的调用方式，js方式可站外调用，php方式兼容复杂代码</td>
      </tr>
       -->
      <tr>
        <td width="115px" align="right">到期时间：</td>
        <td>&nbsp;<input name="edate" type="text" id="edate" value="${dbc_ad.edate }" onclick="lhgcalendar();" style="width:182px;"/></td>
      </tr>
      <tr <c:if test="${empty dbc_ad.id || dbc_ad.id eq ''}">style="display:none;"</c:if>>
        <td width="115px" align="right">调用代码：</td>
        <td>&nbsp;<input type="text" value="&lt;dbctag:showad adid=&quot;${dbc_ad.id }&quot;&gt;"  style="width:182px;"/></td>
      </tr>
      <tr>
        <td align="right">排序：</td>
        <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${dbc_ad.sortcode }"  onkeyup="value=value.replace(/[^\d\.]/g,'')"/> 数字越大越靠前</td>
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
