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
    <title>网站基本设置</title>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
	<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/editor_config.js"></script>  
	<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/editor_all_min.js"></script>  
	<link rel="stylesheet" type="text/css" href="<%=path %>/ueditor/themes/default/ueditor.css"/> 
	<script type="text/javascript">
	function openpic(url,name,w,h){
    window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
  }
	
	function webconfig(){
	 //document.getElementById("webfoot").value=editor.getContent();
	 document.forms[0].submit();
	}
	</script>
  </head>
  
 <body style=" min-height:500px">
  <jsp:include page="../../common/doing.jsp" /> 
 <form   action="<%=path %>/dbc_webconfig.action?methode=update" method="post" >
  <input type="hidden" value="${webconfig.id }" name="id"/>
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="<%=path %>/images/backstage/content-bg.gif">
    	<span class="auto1"><span><b>基本设置</b></span><i></i></span>
    </td>
    <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
    
    
    
    <table width="95%" border="0" cellpadding="0" cellspacing="0" align="center" class="CContent" style="padding-top:10px;">
					
					<tr>
						<td colspan="3" align="center">
								<table border="0" style="width: 100%;font-size: 12px;" align="center" cellpadding="0" cellspacing="0" >
								<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;" colspan="4" nowrap="nowrap" align="center">
											<font color="red" style="font-size: 18px;font-weight: bold;">${msg }</font>
										</td>
									</tr>
								<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											网站开关：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap" align="left">
											<input type="radio" name="isopen" value="yes" <c:if test="${webconfig.isopen ne 'no' }">checked="checked"</c:if> />正常
											<input type="radio" name="isopen" value="no" <c:if test="${webconfig.isopen eq 'no' }">checked="checked"</c:if>/>关闭
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											关闭时显示的信息：
										</td>
										<td style="padding-top: 8px;"  align="left" colspan="3" nowrap="nowrap">
											<textarea rows="6" cols="70" name="closemsg"><c:if test="${empty webconfig.closemsg}">网站升级中。。。</c:if><c:if test="${not empty webconfig.closemsg}">${webconfig.closemsg}</c:if> </textarea>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											网站logo：
										</td>
										<td style="padding-top: 8px;" align="left" colspan="3" nowrap="nowrap">
											<input type="text" id="logo" name="logo" value="${webconfig.logo }" style="width: 300px;"/> <input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-common&textname=logo','upload','550','450')" />可直接添加网络地址
										</td>
										
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											网站名称：
										</td>
										<td style="padding-top: 8px;" align="left" colspan="3" nowrap="nowrap">
											<input type="text" id="webname" name="webname" value="${webconfig.webname }" style="width: 200px;"><font color="green">如：EDBC后台管理系统</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											网站昵称：
										</td>
										<td style="padding-top: 8px;" align="left" colspan="3" nowrap="nowrap">
											<input type="text" id="webnickname" name="webnickname" value="${webconfig.webnickname }" style="width: 200px;"><font color="green">如：EDBC</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;"  align="right" nowrap="nowrap">
											网站标题：
										</td>
										<td style="padding-top: 8px;" colspan="3" align="left" nowrap="nowrap">
											<input type="text" id="webtitle" name="webtitle" value="${webconfig.webtitle }" style="width: 200px;"/> <font color="green">首页标题</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											网站关键字：
										</td>
										<td style="padding-top: 8px;" colspan="3" align="left" nowrap="nowrap">
											<input type="text" id="webkey" name="webkey" value="${webconfig.webkey }" style="width: 200px;"/> <font color="green"> 采用半角 , 隔开</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											网站描述：
										</td>
										<td style="padding-top: 8px;" colspan="3" align="left" nowrap="nowrap">
											<textarea rows="3" cols="60" name="webdescription">${webconfig.webdescription} </textarea>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;"  align="right" nowrap="nowrap">
											话题设置：
										</td>
										<td style="padding-top: 8px;" colspan="3" align="left" nowrap="nowrap">
											<input type="text" id="topic" name="topic" value="${webconfig.topic }" style="width: 200px;"/> <font color="green">新浪微博话题</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											网址：
										</td>
										<td style="padding-top: 8px;" align="left" colspan="3" nowrap="nowrap">
											<input type="text" id="weburl" name="weburl" value="${webconfig.weburl }" style="width: 200px;"/>  <font color="green">如 www.shumili.com 不带http:// 后面不带/</font>
										</td>
									</tr>
									<tr>
									<td style="padding-top: 8px;width: 150px;"  align="right" nowrap="nowrap">
											网站底部：
										</td>
									<td style="padding-top: 8px;" align="left" colspan="3" >
									<textarea id="myEditor" style="width:650px;"></textarea>
									<input type="hidden" name="webfoot" id="webfoot" />
									    <script type="text/javascript">
											var option = { 
											initialContent: '${webconfig.webfoot }',//初始化编辑器的内容 
											textarea: 'content'//设置提交时编辑器内容的名字 
											} 
											var editor = new baidu.editor.ui.Editor(option);//new一个对象，通过对象创建编辑器 
											editor.render("myEditor"); 
											</script> 
									</td>
					</tr>
					<tr style="font-size: 14px;">
						<td style="padding-top: 25px;padding-left:200px;" align="left" colspan="2">
							<input type="button" name="sub" value="提交配置" class="button" style="height: 25px;" onClick="webconfig()" />
						</td>
					</tr>
					<tr><td colspan="2" style="height:50px;">&nbsp;</td></tr>
				</table>
    
    </td>
  </tr>

</table>
</td></tr></table></form>
  </body>
</html>
