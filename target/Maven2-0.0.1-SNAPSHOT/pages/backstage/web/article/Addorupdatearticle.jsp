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
<title>文章管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript"  src="<%=path %>/ueditor/editor_config.js"></script>  
<script type="text/javascript"  src="<%=path %>/ueditor/editor_all_min.js"></script>  
<link rel="stylesheet" type="text/css" href="<%=path %>/ueditor/themes/default/css/ueditor.css"/> 
<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />

  <script type="text/javascript">
  
  function openpic(url,name,w,h){
	    window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
	}
  
    function tj(){
    var title=document.getElementById("title");
	 if(title.value==''){
		 alert('请输入文章标题');
		 return;
	 }
      document.getElementById("content").value=editor.getContent();
      document.forms[0].submit();
    }
  </script>
</head>
<body style="min-height:500px;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
      <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>文章管理</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
      <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
    </tr>
    <tr>
      <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
      <td valign="top" bgcolor="#F7F8F9">

    <form action="<%=path %>/dbc_article.action?methode=addorupdate" method="post" name="form1">
    <input type="hidden" name="id" value="${article.id }" />
    <input type="hidden" name="createdate" value="${article.createdate }" />
    <input type="hidden" name="createuser" value="${article.createuser }" />
    <input type="hidden" name="isupdate" value="${isupdate }" />
    <table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
      <tr>
        <td width="115px" align="right">文章标题：</td>
        <td>&nbsp;<input name="title" type="text" id="title" value="${article.title }" style="width:350px;"/>&nbsp;<font color="red">*</font></td>
      </tr>
      <tr>
        <td width="115px" align="right">所属栏目：</td>
        <td>&nbsp;<select id="treeid" name="treeid">
        <c:forEach items="${treebycailist}" var="treebycai">
          <option value="${treebycai.id }" <c:if test="${article.treeid eq treebycai.id}">selected="selected"</c:if>>
           <c:if test="${treebycai.deep==0 }">
           	 --${treebycai.nodename }--
           </c:if>
           <c:if test="${treebycai.deep==1 }">
              &nbsp;┝${treebycai.nodename }
           </c:if>
           <c:if test="${treebycai.deep==2 }">
            &nbsp;&nbsp;&nbsp;┝${treebycai.nodename }
           </c:if>
            <c:if test="${treebycai.deep==3 }">
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┝${treebycai.nodename }
           </c:if>
            <c:if test="${treebycai.deep==4 }">
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┝${treebycai.nodename }
           </c:if>
            <c:if test="${treebycai.deep==5 }">
             &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┝${treebycai.nodename }
           </c:if>
          </option>
        </c:forEach>
        </select>
        </td>
      </tr>
      <tr>
        <td width="115px" align="right">缩略图：</td>
        <td>&nbsp;<input name="title_pic" type="text" id="title_pic" value="${article.title_pic }" /><input class="sub" type="button" value="上传图片" onclick="javascript:openpic('<%=path %>/fun.action?methode=touploadpic&picpath=upload-images-article&textname=title_pic','upload','550','450')" /> 可直接添加网络地址</td>
      </tr>
       <tr>
        <td width="115px" align="right">文章来源：</td>
        <td>&nbsp;<input name="source" type="text" id="source" value="${article.source }" />&nbsp;为空默认为：${webconfig.webnickname }</td>
      </tr>
       <tr>
        <td width="115px" align="right">关键字：</td>
        <td>&nbsp;<input name="keywords" type="text" id="keywords" value="${article.keywords }" />&nbsp;使用半角逗号“,”隔开</td>
      </tr>
      <tr>
        <td width="115px" align="right">摘要：</td>
        <td>&nbsp;
        <textarea rows="3" cols="80" name="summary" id="summary" style="margin-top:5px;margin-bottom: 5px;">${article.summary }</textarea>
       </td>
      </tr>
       <tr>
        <td align="right">排序：</td>
        <td>&nbsp;<input name="sortcode" type="text" id="sortcode" value="${article.sortcode }" /> 数字越大越靠前</td>
      </tr>
      <tr>
        <td width="115px" align="right">文章内容：</td>
        <td>
        &nbsp;<textarea name="myEditor"  id="myEditor"></textarea>
        <input type="hidden" name="content" id="content" />
				    <script type="text/javascript">
						var option = { 
						initialContent: '${article.content }',//初始化编辑器的内容 
						textarea: 'content'//设置提交时编辑器内容的名字 
						} 
						var editor = new baidu.editor.ui.Editor(option);//new一个对象，通过对象创建编辑器 
						editor.render("myEditor"); 
				</script> 
        </td>
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
