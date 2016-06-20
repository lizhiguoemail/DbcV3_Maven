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
<title>权限列表</title>
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
  document.forms[0].action='<%=path %>/dbc_permit.action?methode=list';
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
      f.action="<%=path %>/dbc_permit.action?methode=delete";
      f.submit();
    }
  }

   function deleteone(id){
     if(confirm('确认删除选中的条目吗')){
       var f = document.forms[0]; 
       f.action="<%=path %>/dbc_permit.action?methode=deletebyid&id="+id;
       f.submit(); 
		 } 
    } 

    function ss(){ 
      var f = document.forms[0]; 
      f.action="<%=path %>/dbc_permit.action?methode=list";
      f.submit();
    }

  </script>
</head>

    <body style="min-height:500px;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
        <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>权限管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
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
        <td width="20%">&nbsp;<img src="<%=path %>/images/backstage/arrow.gif" width="16" height="22" align="absmiddle" />&nbsp;<a href="<%=path %>/dbc_permit.action?methode=toaddorupdate" class="link3">[新增权限]</a> <a class="link3" href="javascript:setdelete();">[删除]</a></td>
        <td  align="right">
                    权限名称：<input type="text" value="${permit_name}" name="permit_name" id="permit_name"/>
                    对应action：<input type="text" value="${permit_action}" name="permit_action" id="permit_action"/>
                    对应methode：<input type="text" value="${permit_methode}" name="permit_methode" id="permit_methode"/>
           <select name="select_isopen" id="select_isopen">
           		<option value="" >全部状态</option>
           		<option value="开启" <c:if test="${select_isopen eq '开启'}">selected="selected"</c:if>>开启的权限</option>
           		<option value="不开启" <c:if test="${select_isopen eq '不开启'}">selected="selected"</c:if>>关闭的权限</option>
           </select>
          <input type="button" value="搜索" onclick="ss()" class="sub"/>
        </td>
        <td width="150px" align="right">共有 <b>${pageParm.total }</b> 条记录&nbsp;&nbsp;</td>
      </tr>
    </table>
    <table id="listtable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <th width="3%"><input type="checkbox" onclick="setall(this.checked)" name="clickall" title="全选" /></th>
        <th width="40px">序号</th>
        <th width="120px;">权限名称</th>
        <th width="120px;">对应action</th>
        <th width="120px;">对应methode</th>
        <th width="120px;">权限标识</th>
        <th width="120px;">权限开启状态</th>
        <th width="120px;">排序码</th>
      <th width="100px">操作</th>
      </tr>
      <c:forEach items="${list}" var="dbc_permit" varStatus="v">
      <tr class="hovertr">
        <td><input type='checkbox' name="checks"  value="${dbc_permit.id }" /></td>

        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>
        <td>${dbc_permit.permit_name }</td>
        <td>${dbc_permit.permit_action }</td>
        <td>${dbc_permit.permit_methode }</td>
        <td style="color: red;">${dbc_permit.action_methode }</td>
        <td>
        	<c:if test="${dbc_permit.isopen ne '关闭'}"><img src="<%=path %>/images/backstage/pic21.gif" title="权限已开启" style="vertical-align: middle;"/></c:if>
        	<c:if test="${dbc_permit.isopen eq '关闭'}"><img src="<%=path %>/images/backstage/pic22.gif" title="权限已关闭" style="vertical-align: middle;"/></c:if>
        </td>
         <td>${dbc_permit.sortcode }</td>
         <td>
        &nbsp;<a href="<%=path %>/dbc_permit.action?methode=toaddorupdate&id=${dbc_permit.id }" class=link4>修改</a>
        &nbsp;<a href="javascript:deleteone('${dbc_permit.id }')" class=link4>删除</a>
       </td>
      </tr>
    </c:forEach> 
    <c:if test="${empty list}">
      <tr bgcolor="FFFFFF"><td align="center" height="50" colspan="12"><font style="size: 14px;color: black">无结果集</font></td></tr>
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
