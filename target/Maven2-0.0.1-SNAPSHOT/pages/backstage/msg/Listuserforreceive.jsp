<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>用户列表</title>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
<script type="text/javascript" language="javascript">
	function gotopage(gototype,page)
		{
		var gotopagetype=document.getElementById("gotopagetype");
		var gotopageString=document.getElementById("gotopageString");
		var yeshu=document.getElementById("yeshu").value;
		if(gototype=='first')
		{
		gotopagetype.value='first';
		}
		if(gototype=='last')
		{
		gotopagetype.value='last';
		}
		if(gototype=='previous')
		{
		gotopagetype.value='previous';
		}
		if(gototype=='next')
		{
		gotopagetype.value='next';
		}
		if(gototype=='gotopage')
		{
		gotopagetype.value='gotopage';
		gotopageString.value='page';
		}
		if(gototype=='tiaozhuan')
		{
		if(yeshu=='')
		{
		alert("请输入页数");
		return;
		}
		else
		{
		gotopagetype.value='gotopage';
		gotopageString.value=yeshu;
		}
		}
		document.forms[0].action='<%=path %>/dbc_userinfo.action?methode=list';
		document.forms[0].submit();
		}
	
		function closeme(){
			this.window.close();
		}
		
		function set(id,username){
		 if(confirm('确定是此收件人吗')){
			 var arr=new Array(2);  
			 arr[0]=id;
			 arr[1]=username;
			 window.returnValue=arr;
			 closeme();
		 } 
		}
		
		function ss(){
			var f = document.forms[0];
			 f.action="<%=path %>/dbc_userinfo.action?methode=list";
			 f.submit();
		}
	</script>
  </head>
  
  <body>
 <body style="min-height:500px">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>选择收件人</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
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
	<table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB">
        <tr>
             <td width="20%">&nbsp;</td>
               <td  align="right">
              	<select name="sname" id="sname">
              		<option value="username" <c:if test="${sname eq 'username'}">selected="selected"</c:if>>用户名</option>
              	</select>
              	<input type="text" value="${svalue}" name="svalue" id="svalue"/>
              	<input type="button" value="搜索" onclick="ss()"/>
              </td>
              <td width="150px" align="right">共有 <b>${pageParm.total }</b> 条记录&nbsp;&nbsp;</td>
            </tr>
      </table>
      <table id="listtable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
                    <tr>
                      <th width="40px">序号</th>
                      <th >用户名</th>
                      <th width="230px">操作</th>
                    </tr>
                    <c:forEach items="${listuser}" var="user" varStatus="v">
					  <tr class="hovertr">
                        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>
                        <td>${user.username }</td>
                        <td>&nbsp;<input type="button" value="选中" onclick="set('${user.id }','${user.username }')"/></td>
					  </tr>
					</c:forEach>
					
					<c:if test="${empty listuser}">
					  <tr bgcolor="FFFFFF"><td align="center" height="50" colspan="12"><font style="size: 14px;color: black">找不到相关用户</font></td></tr>
					 </c:if>
					 <c:if test="${not empty listuser}">
					  <tr bgcolor="EEEEEE"><td colspan="12" style="padding:10px;"><div align="center"><a id="LtotalSY" href="javascript:gotopage('first','0')" class="right-font08">首页</a>&nbsp;&nbsp;<a id="LtotalSYY" href="javascript:gotopage('previous','0')" class="right-font08">上一页</a>&nbsp;&nbsp;<a id="LtotalXYY" href="javascript:gotopage('next','0')" class="right-font08">下一页</a>&nbsp;&nbsp;<a id="LtotalMY" href="javascript:gotopage('last','0')" class="right-font08">末页</a>&nbsp;&nbsp;<input type="text" name="yeshu" id="yeshu" maxlength="5" style="width: 45px;" value="${pageParm.nowpage }" onkeyup="value=value.replace(/[^\d]/g,'') "  onbeforepaste="clipboardData.setData" />页&nbsp;&nbsp;<input type="button" class="button" style="width: 35px;" value="GO" onclick="gotopage('tiaozhuan','0')"></div>
					                <div align="center">
					                  <p>共${pageParm.total }条 ${pageParm.nowpage }/${pageParm.totalpage }页</p>
					                  
					            </div></td></tr>
					  </c:if>
		</table>
       </form>
       
       
       <td background="images/mail_rightbg.gif">&nbsp;</td>
  </tr>

</table>
  </body>
</html>
