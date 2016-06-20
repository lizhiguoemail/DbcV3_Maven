<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>代码生成器（service版）</title>
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/backstage/skin.css" />
		<script type="text/javascript">
		function add()
		{
		  var table_name=document.getElementById('table_name');
		  var class_name=document.getElementById('class_name');
		  var class_description=document.getElementById('class_description');
		  var field_name=document.getElementsByName("field_name");
		  if(table_name.value=='')
		  {
		   alert('请输入表名');
		   return;
		  }
		  if(class_name.value=='')
		  {
		   alert('请输入类名');
		   return;
		  }
		   if(class_description.value=='')
		  {
		   alert('请输入类描述');
		   return;
		  }
		  for(var i=0;i<field_name.length;i++)
		  {
		     if(field_name[i].value=='')
		     {
		       alert('字段名不能为空');
		       return;
		     }
		  }
		  document.getElementById('doing').style.display='block';
		  document.forms[0].submit();
		}
		function goto(str)
		{
		  document.forms[0].action=str;
		  document.getElementById('doing').style.display='block';
		  document.forms[0].submit();
		}
		 
        function additemL1(s1){
         var div = document.createElement("div"); 
         div.innerHTML='字段名：<input type=\"text\" id=\"field_name\" name=\"field_name\" style=\"width: 80px;\"><font color=\"red\">*</font>&nbsp;&nbsp;'
						+'字段类型：<select id=\"field_type\" name=\"field_type\">'
						+'<option value=\"String\">String</option>'
						+'<option value=\"Integer\">Integer</option>'
						+'<option value=\"Double\">Double</option>'
						+'<option value=\"Long\">Long</option>'
						+'<option value=\"Clob\">Clob</option>'
						+'</select>&nbsp;&nbsp;'
						+'字段长度：<input type=\"text\" id=\"field_length\" name=\"field_length\" style=\"width: 30px;\">&nbsp;&nbsp;&nbsp;&nbsp;'
						+'允许为空：<select id=\"field_nullable\" name=\"field_nullable\">'
						+'<option value=\"1\">是</option>'
						+'<option value=\"0\">否</option>'
						+'</select>&nbsp;&nbsp;'
						+'表单类型：<select id=\"biaodan_type\" name=\"biaodan_type\">'
						+'<option value=\"text\">普通文本框</option>'
						+'<option value=\"select\">下拉框</option>'
						+'<option value=\"checkbox\">复选框</option>'
						+'<option value=\"radio\">单选框</option>'
						+'<option value=\"textarea\">文本区域</option>'
						+'<option value=\"text_data\">日期文本框</option>'
						+'<option value=\"text_email\">邮箱文本框</option>'
						+'<option value=\"text_phone\">电话文本框</option>'
						+'<option value=\"text_uploadpic\">图片上传框</option>'
						+'</select>&nbsp;&nbsp;'
						+'必输项：<select id=\"biaodan_nullable\" name=\"biaodan_nullable\">'
						+'<option value=\"0\">否</option>'
						+'<option value=\"1\">是</option>'
						+'</select>&nbsp;&nbsp;'
						+'字段描述：<input type=\"text\" id=\"field_description\" name=\"field_description\" style=\"width: 80px;\">&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"removeMe(this);\"><img src=\"<%=path %>/images/backstage/a_delete.gif\" border=\"0\" /></a>';
         document.getElementById(s1).appendChild(div);
         }
         
         function additemL2(s1){
         var div = document.createElement("div"); 
         div.innerHTML='关联字段：<input type=\"text\" id=\"gl_field_name\" name=\"gl_field_name\" style=\"width: 100px;\"><font color=\"red\">*</font>&nbsp;&nbsp;'
						+'关联字段：<input type=\"text\" id=\"gl_class\" name=\"gl_class\" style=\"width: 100px;\"><font color=\"red\">*</font>&nbsp;&nbsp;'
						+'类上级包路径：<input type=\"text\" id=\"gl_path\" name=\"gl_path\" style=\"width: 100px;\"><font color=\"green\">(同第一项)</font>&nbsp;'
						+'字段描述：<input type=\"text\" id=\"gl_field_description\" name=\"gl_field_description\">&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"removeMe(this);\"><img src=\"<%=path %>/images/backstage/a_delete.gif\" border=\"0\" /></a>';
         document.getElementById(s1).appendChild(div);
         }
         
        function removeMe(va){//删除模版
    		var tr=va.parentNode.parentNode;
    		tr.removeChild(va.parentNode);
    	}
    	
    	function gbgl(){
    	    var gltr1=document.getElementById("gltr1");
    	    var gltr2=document.getElementById("gltr2");
    		var New=document.getElementsByName("isgl");
    		var strNew;
		   for(var i=0;i<New.length;i++)
		   {
		     if(New.item(i).checked){
		         strNew=New.item(i).getAttribute("value");  
		         break;
		       }
		   }
		   if(strNew=="0"){
		     gltr1.style.display='none';
		     gltr2.style.display='none';
		   }
		  else if(strNew=="1"){
		   	gltr1.style.display="block";
		   	gltr2.style.display="block";
		   }    
	    }
    	function showhistory()
		{
    	  document.forms[0].action='<%=path %>/dbc_createcode.action?methode=listCreate';
  		  document.getElementById('doing').style.display='block';
  		  document.forms[0].submit();
		}
		</script>
	</head>
	<body class="ContentBody">
	 <jsp:include page="../../../common/doing.jsp" />
	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" valign="top" background="<%=path %>/images/backstage/mail_leftbg.gif"><img src="<%=path %>/images/backstage/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="<%=path %>/images/backstage/content-bg.gif"><span class="autol"><span style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left top transparent;"><b>代码生成器</b></span><i style="background:url('<%=path %>/images/backstage/top_bt.gif') no-repeat scroll left bottom transparent;"></i></span></td>
    <td width="16" valign="top" background="<%=path %>/images/backstage/mail_rightbg.gif"><img src="<%=path %>/images/backstage/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td valign="middle" background="<%=path %>/images/backstage/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
	 
	 
	  
		<form   action="<%=path %>/dbc_createcode.action?methode=createCode" method="post" >
		<input type="hidden" name="nowpage" id="nowpage" value="${nowpage }"/>
        <input type="hidden" name="gotopagetype" id="gotopagetype" value="${gotopagetype }"/>
        <input type="hidden" name="gotopageString" id="gotopageString" value="${gotopageString }"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
 <tr><td height="62" background="<%=path %>/images/backstage/nav04.gif" > </td></tr></table> 
			<div class="MainDiv">
				<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center" class="CContent">
					<tr>
						<td colspan="3" align="center">
							<fieldset style="height: 100%; width: 90%">
								<br>
								<legend>
									代码生成器
								</legend>
								<table border="0" style="width: 850px;font-size: 12px;" align="center" cellpadding="0" cellspacing="0" >
								 <tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											pojo包上级包路径：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="text" id="packageRoute" name="packageRoute" value="${packageRoute }" style="width: 200px;">  <font color="green">(例如：pojo包路径为：com.dbc.pojo，则输入：com.dbc ,默认值为com.dbc)</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											作者：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="text" id="author" name="author" value="${author }" style="width: 200px;"> <font color="green">(例如：caihuajun ，如不写作者默认为caihuajun)</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											版本号：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="text" id="edition" name="edition" value="${edition }" style="width: 200px;"> <font color="green">(例如：v1.0 ，如不写版本默认为v2.0)</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											生成数据库表名：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="text" id="table_name" name="table_name" value="${createcode.table_name }" style="width: 200px;"> <font color="red">*</font> <font color="green">(最好全部为大写，例如：TEST)</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											class类名：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="text" id="class_name" name="class_name" value="${createcode.class_name }" style="width: 200px;"> <font color="red">*</font> <font color="green">(最好头字母为大写，例如：Test)</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											类功能描述：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="text" id="class_description" name="class_description" value="${createcode.class_description }" style="width: 200px;"> <font color="red">*</font> <font color="green">(类注释，例如：测试类)</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											JSP存放路径：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="text" id="jsppath" name="jsppath" value="${createcode.jsppath }" style="width: 200px;">  <font color="green">默认为 pages/backstage/xxxx(xxxx:为以此类名为文件夹名称的文件夹)</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											排序方式：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="radio" name="sortmethode" value="sortcode" checked="checked"> 按排序码排序<font color="red">      （在添加和修改界面都可以自由更改排序码）</font><br/>
											<input type="radio" name="sortmethode" value="createdate"> 按创建日期排序<font color="red">（根据添加的日期进行倒叙）</font>
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											是否有外键：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
											<input type="radio" name="isgl" value="0" checked="checked" onclick="gbgl()">无
											<input type="radio" name="isgl" value="1" onclick="gbgl()">有
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											相关字段(类属性)：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 12px;">
										
										<td style="padding-top: 8px;" colspan="4" nowrap="nowrap">
											<div id="model1">
											字段名：<input type="text" id="field_name" name="field_name" style="width: 80px;"><font color="red">*</font>&nbsp;
											字段类型：<select id="field_type" name="field_type">
											<option value="String">String</option>
											<option value="Integer">Integer</option>
											<option value="Double">double</option>
											<option value="Long">long</option>
											<option value="Clob">clob</option>
											</select>&nbsp;
											字段长度：<input type="text" id="field_length" name="field_length" style="width: 30px;">&nbsp;&nbsp;&nbsp;
										         允许为空：<select id="field_nullable" name="field_nullable">
										    <option value="1">是</option>
											<option value="0">否</option>
											</select>&nbsp;
											表单类型：<select id="biaodan_type" name="biaodan_type">
											<option value="text">普通文本框</option>
											<option value="select">下拉选择框</option>
											<option value="checkbox">复选框</option>
											<option value="radio">单选框</option>
											<option value="textarea">文本区域</option>
											<option value="text_data">日期文本框</option>
											<option value="text_email">邮箱文本框</option>
											<option value="text_phone">电话文本框</option>
											<option value="text_uploadpic">图片上传框</option>
											</select>&nbsp;
											   必输项：<select id="biaodan_nullable" name="biaodan_nullable">
											<option value="0">否</option>
											<option value="1">是</option>
											</select>&nbsp;
										    字段描述：<input type="text" id="field_description" name="field_description" style="width: 80px;">&nbsp;
											</div>
											 <br/>
                                            <a href="javascript:additemL1('model1')"><img src="<%=path %>/images/backstage/a_add.gif " border="0"/></a>
										</td>
									</tr>
								
									<tr class="t_pad" style="font-size: 14px;display:none;" id="gltr1">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
											外键字段(关联类)：
										</td>
										<td style="padding-top: 8px;" colspan="3" nowrap="nowrap">
										</td>
									</tr>
									<tr class="t_pad" style="font-size: 14px;">
										<td style="padding-top: 8px;width: 150px;" align="right" nowrap="nowrap">
										</td>
										<td style="padding-top: 8px;display:none;" colspan="3" nowrap="nowrap" id="gltr2">
											<div id="model2">
											关联字段：<input type="text" id="gl_field_name" name="gl_field_name" style="width: 100px;"><font color="red">*</font>&nbsp;
											关联类：<input type="text" id="gl_class" name="gl_class" style="width: 100px;"><font color="red">*</font>&nbsp;
											类上级包路径：<input type="text" id="gl_path" name="gl_path" style="width: 100px;" ><font color="green">(同第一项)</font>&nbsp;
										       字段描述：<input type="text" id="gl_field_description" name="gl_field_description">
											</div>
											 <br/>
                                            <a href="javascript:additemL2('model2')"><img src="<%=path %>/images/backstage/a_add.gif " border="0"/></a>
										</td>
									</tr>
									</table>
								<br>
							</fieldset>
						</td>
					</tr>
					<tr style="font-size: 14px;">
						<td style="padding-top: 5px;" align="center">
							<input type="button" name="sub" value="开始生成" class="button" style="height: 25px;" onClick="add()" />
							<input type="button" name="sub" value="查看历史生成" class="button" style="height: 25px;" onClick="showhistory()" />
						</td>
					</tr>
					<tr style="font-size: 14px;">
						<td style="padding-top: 5px;padding-left: 30px;" align="left">
							<p style="color: red;">注：系统会自动生成主键，主键名为id,为string类型，生成方式为uuid。除此之外系统还会自动生成description（描述）、sortcode（排序码）、createdate(创建日期)、createuser(创建人)、updatedate(修改日期)、updateuser(修改人)、deletemark（删除标识），故无需再次生成</p>
						</td>
					</tr>
				</table>
				<br>
			</div>
		</form>
		
		
		
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

