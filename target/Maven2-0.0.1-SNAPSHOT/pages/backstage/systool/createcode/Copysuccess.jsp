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
		<title>代码生成器</title>
		<link rel="stylesheet" href="<%=path %>/css/backstage/style.css" type="text/css" />
		<script type="text/javascript">
		function fugai()
		{
		  var f = document.forms[0]; 
	      f.action="<%=path %>/dbc_createcode.action?methode=copytoworkspace";
	      document.getElementById('doing').style.display='block';
	      f.submit();
		}
		
		</script>
	</head>
	<body class="ContentBody">
	<jsp:include page="../../../common/doing.jsp" />
		<form   action="" method="post" >
		<input type="hidden" name="class_name" id="class_name" value="${class_name }"/>
			<div class="MainDiv">
				<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center" class="CContent">
					<tr class="CTitle">
						<th class="tablestyle_title">
							代码拷贝
						</th>
					</tr>
					<tr>
						<td colspan="3" align="center">
							<fieldset style="height: 100%; width: 90%">
								<br>
								<legend>
									class_name代码拷贝
								</legend>
								<div align="center">
									<c:if test="${havefile eq ''||empty havefile }">
										<img src="<%=path %>/images/backstage/icon2_089.png" />&nbsp;&nbsp;<font color="blue" style="font-size: 14px;">${class_name }代码已成功部署到指定项目位置，请在Eclipse中刷新项目即可！</font>
									</c:if>
									<c:if test="${havefile ne '' && not empty havefile}">
										<font color="red" style="font-size: 14px;">系统发现${havefile }文件已在指定项目存在，是否需要覆盖？</font>
										<br/><br/>
										<input type="button" onclick="fugai()" value="是的，请继续帮我覆盖"/>
										<input type="button" onclick="history.go(-1);" value="不覆盖，返回"/>
									</c:if>
								</div>
								
				<br></fieldset></td></tr></table></div></form>
	</body>
</html>

