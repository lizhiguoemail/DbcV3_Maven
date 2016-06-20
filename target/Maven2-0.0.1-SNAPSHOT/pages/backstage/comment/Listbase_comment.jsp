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
<title>评论通用类列表</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
<script type="text/javascript" language="javascript">
  function gotopage(gototype,page){
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
  document.forms[0].action='<%=path %>/dbc_comment.action?methode=list';
  document.forms[0].submit();
  }

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
      f.action="<%=path %>/dbc_comment.action?methode=delete";
      f.submit();
    }
  }

   function deleteone(id,v){
     if(confirm('确认删除选中的条目吗')){
       var f = document.forms[0]; 
       f.action="<%=path %>/dbc_comment.action?methode=deletebyid&id="+id;
       f.submit(); 
		 } 
    } 

   function setpingbi(v){
	   var str='确认屏蔽选中的条目吗';
	   if(v!='1'){
		  str= '确认取消屏蔽选中的条目吗';
	   }
	    if(confirm(str)){
	      var f = document.forms[0];
	      f.action="<%=path %>/dbc_comment.action?methode=pingbi&v="+v;
	      f.submit();
	    }
	  }

	   function pingbione(id,v){
		   var str='确认屏蔽选中的条目吗';
		   if(v!='1'){
			  str= '确认取消屏蔽选中的条目吗';
		   }
	     if(confirm(str)){
	       var f = document.forms[0]; 
	       f.action="<%=path %>/dbc_comment.action?methode=pingbibyid&id="+id+"&v="+v;
	       f.submit(); 
			 } 
	    } 

    function ss(){ 
      var f = document.forms[0]; 
      f.action="<%=path %>/dbc_comment.action?methode=list";
      f.submit();
    }

  </script>
</head>

    <body style="min-height:500px;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
        <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>评论管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
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
      <input type="hidden" name="objid" value="${objid }"/>
      <input type="hidden" name="type" value="${type }"/>
      <table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB;">
      <tr>
        <td width="30%">&nbsp;<img src="<%=path %>/images/backstage/arrow.gif" width="16" height="22" align="absmiddle" />&nbsp; <a class="link3" href="javascript:setpingbi('1');">[屏蔽评论]</a>&nbsp;<a class="link3" href="javascript:setpingbi('0');">[取消屏蔽]</a>&nbsp; <a class="link3" href="javascript:setdelete();">[删除评论]</a></td>
        <td  align="right">
          <select name="sname" id="sname">
            <option value="objname" <c:if test="${sname eq 'objname'}">selected="selected"</c:if>>评论对象名称</option>
            <option value="type" <c:if test="${sname eq 'type'}">selected="selected"</c:if>>评论类型</option>
            <option value="neirong" <c:if test="${sname eq 'neirong'}">selected="selected"</c:if>>评论内容</option>
            <option value="uname" <c:if test="${sname eq 'uname'}">selected="selected"</c:if>>用户名</option>
            <option value="ip" <c:if test="${sname eq 'ip'}">selected="selected"</c:if>>ip</option>
          </select>
          <input type="text" value="${svalue}" name="svalue" id="svalue"/>
          <input type="button" value="搜索" onclick="ss()"/>
        </td>
        <td width="150px" align="right">共有 <b>${pageParm.total }</b> 条记录&nbsp;&nbsp;</td>
      </tr>
    </table>
    <table id="listtable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <th width="3%"><input type="checkbox" onclick="setall(this.checked)" name="clickall" title="全选" /></th>
        <th width="40px">序号</th>
        <th width="60px;">评论类型</th>
        <th width="70px;">对象名称</th>
        <th width="60px;">是否注册</th>
        <th width="80px;">用户名</th>
        <th width="70px;">楼层</th>
        <th width="80px;">ip</th>
        <th width="80px;">ip地域</th>
        <th width="120px;">ip详细地址</th>
        <th width="">评论内容</th>
        <th width="50px">屏蔽状态</th>
      <th width="180px">操作</th>
      </tr>
      <c:forEach items="${list}" var="base_comment" varStatus="v">
      <tr  <c:if test="${base_comment.isguolv eq '1' }">style="background:gray;</c:if> class="hovertr" >
        <td><input type='checkbox' name="checks"  value="${base_comment.id }" /></td>

        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>
        <td>${base_comment.type }</td>
        <td>${base_comment.objname }</td>
        <td>${base_comment.isreg }</td>
        <td>${base_comment.uname }</td>
        <td>${base_comment.floor }</td>
        <td>${base_comment.ip }</td>
        <td>${base_comment.ipzone }</td>
        <td>${base_comment.ipaddress }</td>
        <td>${base_comment.neirong }</td>
        <td>
       	<c:if test="${base_comment.isguolv eq '1' }"><div style="color:red;">被屏蔽</div></c:if><c:if test="${base_comment.isguolv ne '1' }">未屏蔽</c:if>
        </td>
         <td>
        &nbsp;<a href="<%=path %>/dbc_comment.action?methode=toaddorupdate&id=${base_comment.id }" class=link4>查看</a>
        &nbsp;<a href="javascript:pingbione('${base_comment.id }','1')" class=link4>屏蔽</a>
        &nbsp;<a href="javascript:pingbione('${base_comment.id }','0')" class=link4>取消屏蔽</a>
        &nbsp;<a href="javascript:deleteone('${base_comment.id }')" class=link4>删除</a>
       </td>
      </tr>
    </c:forEach> 
    <c:if test="${empty list}">
      <tr bgcolor="FFFFFF"><td align="center" height="50" colspan="19"><font style="size: 14px;color: black">无结果集</font></td></tr>
    </c:if>
    <c:if test="${not empty list}">
      <tr bgcolor="EEEEEE"><td colspan="20" style="padding:10px;"><div align="center"><a id="LtotalSY" href="javascript:gotopage('first','0')" class="right-font08">首页</a>&nbsp;&nbsp;<a id="LtotalSYY" href="javascript:gotopage('previous','0')" class="right-font08">上一页</a>&nbsp;&nbsp;<a id="LtotalXYY" href="javascript:gotopage('next','0')" class="right-font08">下一页</a>&nbsp;&nbsp;<a id="LtotalMY" href="javascript:gotopage('last','0')" class="right-font08">末页</a>&nbsp;&nbsp;<input type="text" name="yeshu" id="yeshu" maxlength="5" style="width: 45px;" value="${pageParm.nowpage }" onkeyup="value=value.replace(/[^\d]/g,'') "  onbeforepaste="clipboardData.setData" />页&nbsp;&nbsp;<input type="button" class="button" style="width: 35px;" value="GO" onclick="gotopage('tiaozhuan','0')"></div>
        <div align="center">
          <p>共${pageParm.total }条 ${pageParm.nowpage }/${pageParm.totalpage }页</p>
       </div></td></tr>
      </c:if>
    </table>
   </form>
 <!--------------------------------------------------- 主体内容结束--------------------------------------------------- -->
      <td background="<%=path %>/images/backstage/mail_rightbg.gif">&nbsp;</td>
    </tr>
   </table> 
 </body> 
</html>
