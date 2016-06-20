<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>评论</title>
<script language="javascript">

function gotopage(gototype,page)
{
	 var gotopagetype=document.getElementById("gotopagetype");
     var gotopageString=document.getElementById("gotopageString");
     var yeshu=document.getElementById("yeshu").value;
     if(gototype=='first'){
       gotopagetype.value='first';
     }
     if(gototype=='last'){
       gotopagetype.value='last';
     }
     if(gototype=='previous'){
       gotopagetype.value='previous';
     }
     if(gototype=='next'){
       gotopagetype.value='next';
     }
     if(gototype=='gotopage'){
       gotopagetype.value='gotopage';
       gotopageString.value='page';
     }
     if(gototype=='tiaozhuan'){
       if(yeshu==''){
         alert('请输入页数');
         return;
	       }
	     else{
	       gotopagetype.value='gotopage';
	       gotopageString.value=yeshu;
	     }
	  }
	document.forms[0].action="<%=path %>/dbc_comment.action?methode=listpinglun";
	document.forms[0].submit();
}

function tijiao()
{
var t=document.getElementById("status");
var uname=document.getElementById("uname");
var yiming=document.getElementById("yiming");

if(uname.value==''&&yiming.checked==false)
{
alert("请输入用户名或选择佚名");
return;
}
if(yiming.checked)
{
uname.value='佚名';
}
if(t.value=='')
{
alert("请填写评论");
return;
}
var username=document.getElementById("username");
if(uname.value==username.value)
{
var isreg=document.getElementById("isreg");
    isreg.value='是';
}
document.forms[0].action="<%=path %>/dbc_comment.action?methode=addpinglun";
document.forms[0].submit();
}

 function countChar(textareaName,spanName)
{ 
var s=document.getElementById(textareaName);
if(280-s.value.length<0)
{
document.getElementById(spanName).innerHTML =0;
}
else
{
document.getElementById(spanName).innerHTML = '<font color="green">'+(280 - s.value.length)+'<font>';
}
if(s.value.length>280)
{
s.value=s.value.substring(0,280);
}
} 

</script>

<style type="text/css">
.body {
	margin:0 auto;
	padding:0px;
	font-size:12px;
}

.a {
border:1px solid #D5C5B5;
background-color:#764B30;
line-height:5px;
padding:2px 10px;
text-decoration: none;
color:#D4B78C;
font-size: 12px;
font-family: cursive;
font-style:inherit;
font-weight: lighter;
width: 30px;
height: 20px;
}
</style>
</head>
<body class="body">
<form method="post" action="<%=path %>/dbc_comment.action?methode=addpinglun">
 <input type="hidden" name="nowpageString" id="nowpageString" value="${pageParm.nowpage }"/>
  <input type="hidden" name="gotopagetype" id="gotopagetype"/>
  <input type="hidden" name="gotopageString" id="gotopageString"/>
  <input type="hidden" name="objid" id="objid" value="${objid }"/>
    <input type="hidden" name="objname" id="objname" value="${objname }"/>
   <input type="hidden" name="huifutext" id="huifutext" />
    <input type="hidden" name="isreg" id="isreg" value="否"/>
     <input type="hidden" name="type" id="type" value="${type }"/>
     <input type="hidden" name="username" id="username" value="${userinfo.username }"/>
     <c:if test="${ empty pinglunlist}">
     <div align="center" style="padding-top: 10px;padding-bottom: 10px;">暂无评论</div>
     </c:if>
   <c:if test="${not empty pinglunlist}">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-top:#7c5127 solid 2px; height:43px; line-height:43px; background:#f6f3e7; margin-top:20px;">
    <tr>
      <td style="font-size:18px; color:#ba2901; font-weight:bold;  padding-left:20px;">相关评论</td>
    </tr>
  </table>
   <c:forEach items="${pinglunlist}" var="i">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:20px; border-top:2px #d6c9bc solid;">
          <tr>
            <td style="border-bottom:1px #d6c9bc solid; line-height:40px; color:#7a7979" height="40"><span style="font-size:24px; font-weight:bold">&nbsp;${i.floor }</span>楼：<span style="color:#ba2901"><c:if test="${i.uname eq '佚名'}">${i.uname }</c:if><c:if test="${i.uname ne '佚名'}">${i.uname }</c:if> <c:if test="${i.isreg!='是'&& i.uname ne '佚名'}">【未注册用户】</c:if> </span>  发表于：${i.createdate }</td>
          </tr>
          <tr>
            <td style="line-height:25px; padding-top:15px;"><c:if test="${i.isguolv eq 'y' }"><font color="red">此评论已被管理员屏蔽</font></c:if><c:if test="${i.isguolv ne 'y' }">${i.neirong }</c:if></td>
          </tr>
        </table>
       	  </c:forEach>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:20px; border-top:2px #d6c9bc solid;">
          <tr>
            <td align="center" style="line-height:25px; padding-top:15px;"><a href="javascript:gotopage('first','0')" style="color:#714d27;text-decoration: none;">首 页</a>    <a href="javascript:gotopage('previous','0')" style="color:#714d27;text-decoration: none;">上一页</a>     <a href="javascript:gotopage('next','0')" style="color:#714d27;text-decoration: none;">下一页</a>     <a href="javascript:gotopage('last','0')" style="color:#714d27;text-decoration: none;">末 页</a>            &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="yeshu" id="yeshu" maxlength="5" style="width: 25px;" value="${pageParm.nowpage }" onkeyup="value=value.replace(/[^\d]/g,'') "  onbeforepaste="clipboardData.setData" />&nbsp;页&nbsp;<a href="javascript:gotopage('last','0')" style="color:#714d27;text-decoration: none;">GO</a>   &nbsp;&nbsp;&nbsp;&nbsp;       共${pageParm.total }条 评论    ${pageParm.nowpage }/${pageParm.totalpage } 页 </td>
          </tr>
        </table>
        </c:if>
        <table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#cccccc" style="margin-bottom:20px;">
          <tr>
            <td bgcolor="#f6f3e7"><font color="red">（*）</font><span style="color:#744210">用户名：
              <label>
                <input type="text" name="uname" id="uname" <c:if test="${not empty userinfo}">value="${userinfo.username}"</c:if> />
              </label>
              <label>
                <input type="checkbox" type="checkbox" value="佚名" id="yiming" />
              </label>
            佚名</span></td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF" style="color:#744210"><font color="red">（*）评论内容</font>，最大能输<font color="blue">280</font>个汉字，目前还能输入<span id="counter"><font color="green">280</font></span>个字
              <p>
                <label>
                  <textarea  id="status" style="width: 600px;"   cols="45" rows="5" name="neirong" onkeydown='countChar("status","counter");' onkeyup='countChar("status","counter");' ></textarea>
                </label>
                <label style="padding-left: 10px;">
              <input type="button" name="button" id="button" value="发表评论" onclick="tijiao()" style="background:url(<%=path %>/images/common/fabiaopinlun.gif); width:82px; height:25px; border:none; color:#FFF; cursor:hand;" />
            </label>
              </p></td>
          </tr>
        </table>
</form>
</body>
</html>
