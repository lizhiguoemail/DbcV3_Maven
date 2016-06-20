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
<title>应用表列表</title>

<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/backstage/skin.css" />
<script type="text/javascript" src="<%=basePath %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/common/fun.js"></script>
<script>
$(function(){
	$('.showpic').hover(function(){
		var pic=http_pic($(this).attr('pic'));
		$(this).css('position','relative');
	    $(this).html('<img style="position:absolute;display:none;top:-50px;*top:0px; right:0px" src="'+pic+'" onload="imgAuto(this,300,300)"/>' );
	},function(){
		$(this).css('position','static');
	    $(this).html('查看');
	});
		
})
</script>

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
  document.forms[0].action='<%=basePath %>/dbc_app.action?methode=list';
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
      f.action="<%=basePath %>/dbc_app.action?methode=delete";
      f.submit();
    }
  }

   function deleteone(id){
     if(confirm('确认删除选中的条目吗')){
       var f = document.forms[0]; 
       f.action="<%=basePath %>/dbc_app.action?methode=deletebyid&id="+id;
       f.submit(); 
		 } 
    } 

    function ss(){ 
      var f = document.forms[0]; 
      f.action="<%=basePath %>/dbc_app.action?methode=list";
      f.submit();
    }

  </script>

</head>

    <body style="min-height:500px;"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="17" valign="top" background="<%=basePath %>/images/backstage/mail_leftbg.gif"><img src="<%=basePath %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
        <td valign="top" background="<%=basePath %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=basePath %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>应用表管理</b></span><i style="background:url('<%=basePath %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
        <td width="16" valign="top" background="<%=basePath %>/images/backstage/mail_rightbg.gif"><img src="<%=basePath %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td> 
      </tr> 
      <tr>
        <td valign="middle" background="<%=basePath %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
        <td valign="top" bgcolor="#F7F8F9"> 

      <!-- ----------------------------------------主体内容开始---------------------------------------------------- -->
      <form name="f" action="" method="post">
      <input type="hidden" name="nowpageString" id="nowpageString" value="${pageParm.nowpage }"/>
      <input type="hidden" name="gotopagetype" id="gotopagetype"/>
      <input type="hidden" name="gotopageString" id="gotopageString"/>
      <table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB;">
      <tr>
        <td width="20%">&nbsp;<img src="<%=basePath %>/images/backstage/arrow.gif" width="16" height="22" align="absmiddle" />&nbsp;<a href="<%=path %>/dbc_app.action?methode=toaddorupdate" class="link3">[新增应用表]</a> <a class="link3" href="javascript:setdelete();">[删除]</a></td>
        <td  align="right">
          <select name="sname" id="sname">
            <option value="name" <c:if test="${sname eq 'name'}">selected="selected"</c:if>>名称</option>
            <option value="apptype" <c:if test="${sname eq 'apptype'}">selected="selected"</c:if>>类型</option>
            <option value="tuijiandengji" <c:if test="${sname eq 'tuijiandengji'}">selected="selected"</c:if>>推荐等级</option>
            <option value="shouquanfangshi" <c:if test="${sname eq 'shouquanfangshi'}">selected="selected"</c:if>>授权方式</option>
            <option value="zuozhe" <c:if test="${sname eq 'zuozhe'}">selected="selected"</c:if>>作者</option>
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
        <th width="120px;">名称</th>
        <th width="120px;">类型</th>
        <th width="120px;">推荐等级</th>
        <th width="120px;">授权方式</th>
        <th width="120px;">浏览次数</th>
        <th width="120px;">支持分辨率</th>
        <th width="120px;">应用图标</th>
        <th width="120px;">访问地址</th>
        <th width="120px;">作者</th>
      <th width="100px">操作</th>
      </tr>
      <c:forEach items="${list}" var="app" varStatus="v">
      <tr class="hovertr">
        <td><input type='checkbox' name="checks"  value="${app.id }" /></td>

        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>
        <td>${app.name }</td>
        <td>${app.apptype }</td>
        <td>${app.tuijiandengji }</td>
        <td>${app.shouquanfangshi }</td>
        <td>${app.liulancishu }</td>
        <td>${app.fenbianlv }</td>
        <c:if test="${(fn:contains(app.smallpic,'http://')==true)||(fn:contains(app.smallpic,'https://')==true)}">
        	<td class="bigtext showpic" pic="${app.smallpic }">查看</td>
        </c:if>
        <c:if test="${(fn:contains(app.smallpic,'http://')==false)&&(fn:contains(app.smallpic,'https://')==false)}">
        	<td class="bigtext showpic" pic="<%=basePath %>/${app.smallpic }">查看</td>
        </c:if>
        <td>${app.ylurl }</td>
        <td>${app.zuozhe }</td>
        <td>
		&nbsp;<a href="<%=basePath %>/dbc_app.action?methode=toaddorupdate&id=${app.id }" class=link4>修改</a>
		&nbsp;<a href="javascript:deleteone('${app.id }')" class=link4>删除</a>
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
      <td background="<%=basePath %>/images/backstage/mail_rightbg.gif">&nbsp;</td>
    </tr>
   </table> 
 </body> 
</html>
