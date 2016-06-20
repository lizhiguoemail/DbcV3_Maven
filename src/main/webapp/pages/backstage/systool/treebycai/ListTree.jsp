<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.dbc.pojo.Dbc_treeByCai"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>类别树列表</title>
	<link rel="StyleSheet" href="<%=path %>/css/common/dtree.css" type="text/css" />
	<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>   
	 <script type="text/javascript" src="<%=path %>/js/common/jquery.contextmenu.js"></script>
	<script type="text/javascript" src="<%=path %>/js/common/dtreeback.js"></script>
	
  <script type="text/javascript">
  
    dTree.prototype.isParentNode = function(id) {
	    var isParentNode = false;
		var n=0;
		for (n; n<this.aNodes.length; n++) 
		{	
			if (this.aNodes[n].pid == id) 
			{
			   isParentNode = true;
			   break;
			}
		}
		return isParentNode;
	};
	d = new dTree('d');
	//因为路径问题，只能把icon的配置放到前面
	d.icon = {
		
				root				: '<%=path %>/images/tree/base.gif',
		
				folder			: '<%=path %>/images/tree/folder.gif',
		
				folderOpen	: '<%=path %>/images/tree/folderopen.gif',
		
				node				: '<%=path %>/images/tree/page.gif',
		
				empty				: '<%=path %>/images/tree/empty.gif',
		
				line				: '<%=path %>/images/tree/line.gif',
		
				join				: '<%=path %>/images/tree/join.gif',
		
				joinBottom	: '<%=path %>/images/tree/joinbottom.gif',
		
				plus				: '<%=path %>/images/tree/plus.gif',
		
				plusBottom	: '<%=path %>/images/tree/plusbottom.gif',
		
				minus				: '<%=path %>/images/tree/minus.gif',
		
				minusBottom	: '<%=path %>/images/tree/minusbottom.gif',
		
				nlPlus			: '<%=path %>/images/tree/nolines_plus.gif',
		
				nlMinus			: '<%=path %>/images/tree/nolines_minus.gif'
		
			};
	
		<%List treelist=(List)request.getAttribute("treelist");
		for(int i=0;i<treelist.size();i++)
		{
			Dbc_treeByCai tree=(Dbc_treeByCai)treelist.get(i);
			if("0".equals(tree.getDeep()))
			{
				%>
				d.add(<%=tree.getId() %>,-1,' <%=tree.getNodename() %>','#','<%=tree.getNodename() %>','_self');
				<%
			}
			else
			{
				%>
				d.add(<%=tree.getId() %>,<%=tree.getParentid() %>,'<%=tree.getNodename() %>','#','<%=tree.getNodename() %>','_self');
				<%
			}
		}
		%>
    $(document).ready(function() {
      $('a').contextMenu('jqueryDtreeMenu', {
		onContextMenu: function(t) {
		return true;
        },
        bindings: {
         'up': function(t) {
            document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=paixu&t=up&oid="+t.name+"&treetype=${treetype}";
             document.getElementById('doing').style.display='block';
            document.forms[0].submit();
          },
         'down': function(t) {
            document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=paixu&t=down&oid="+t.name+"&treetype=${treetype}";
             document.getElementById('doing').style.display='block';
            document.forms[0].submit();
          },
           'top': function(t) {
            document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=paixu&t=top&oid="+t.name+"&treetype=${treetype}";
             document.getElementById('doing').style.display='block';
            document.forms[0].submit();
          },
           'buttom': function(t) {
            document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=paixu&t=buttom&oid="+t.name+"&treetype=${treetype}";
             document.getElementById('doing').style.display='block';
            document.forms[0].submit();
          },
          'add': function(t) {
            var arr=window.showModalDialog(encodeURI("<%=path %>/dbc_treebycai.action?methode=toAddsonnode&treeid="+t.name),"new","dialogHeight:200px;dialogWidth:450px;edge:Raised;center:Yes;help:No;resizable:no;status:no;"); 
            var nodename=document.getElementById("nodename");
            var description=document.getElementById("description");
            nodename.value=arr[0];
            description.value=arr[1];
            document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=addnode&pid="+t.name+"&treetype=${treetype}";
            document.getElementById('doing').style.display='block';
            document.forms[0].submit();
          },
          'update': function(t) {
           var arr=window.showModalDialog(encodeURI("<%=path %>/dbc_treebycai.action?methode=toUpdatenode&treeid="+t.name+"&nodename="+t.title),"new","dialogHeight:200px;dialogWidth:450px;edge:Raised;center:Yes;help:No;resizable:no;status:no;"); 
           var nodename=document.getElementById("nodename");
           var description=document.getElementById("description");
            nodename.value=arr[0];
            description.value=arr[1];
            document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=update&treeid="+t.name+"&treetype=${treetype}";
             document.getElementById('doing').style.display='block';
            document.forms[0].submit();
          },
          'delete': function(t) {
            if(confirm('确认删除该类别吗'))
            {
              document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=deletenode&oid="+t.name+"&treetype=${treetype}";
               document.getElementById('doing').style.display='block';
              document.forms[0].submit();
            }
          }
        }

      });
	  
    });
    
    function tianjiaroot()
{
  var rzhi=document.getElementById("rootname");
  var treetype=document.getElementById("treetype").value;
  if(rzhi.value==''||rzhi==null)
  {
  alert("不能输入空值");
  return;
  }
  else
  {
   document.forms[0].action="<%=path %>/dbc_treebycai.action?methode=addroot&treetype="+treetype;
    document.getElementById('doing').style.display='block';
   document.forms[0].submit();
  }
}    

  </script>


</head>

<body>

<jsp:include page="../../../common/doing.jsp" /> 

<form method="post">
<input type="hidden" id="nodename" name="nodename"/>
<input type="hidden" id="description" name="description"/>
  <input type="hidden" name="treetype" id="treetype" value="${treetype }"/>
  <c:if test="${empty treelist}">
  <input type="text" name="rootname" id="rootname" style="vertical-align: middle;"> <input type="button"  class="button" value="添加根节点" style="vertical-align: middle;" onclick="tianjiaroot()">
  </c:if>
   <c:if test="${not empty treelist}">
<div class="dtree">

	<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>

	<script type="text/javascript">
		<!--


		document.write(d);

		//-->
	</script>

</div>

  <div class="contextMenu" id="jqueryDtreeMenu">

    <ul>
      <li id="up"><div style="width: 100%;" align="center"><font style="font-size: 12px;font-weight: bold;">上移</font></div></li>
      <li id="down"><div style="width: 100%;" align="center"><font style="font-size: 12px;font-weight: bold;">下移</font></div></li>
      <li id="top"><div style="width: 100%;" align="center"><font style="font-size: 12px;font-weight: bold;">置顶</font></div></li>
      <li id="buttom"><div style="width: 100%;" align="center"><font style="font-size: 12px;font-weight: bold;">置底</font></div></li>
      <li id="add"><div style="width: 100%;" align="center"><font style="font-size: 12px;font-weight: bold;">添加</font></div></li>
      <li id="update"><div style="width: 100%;" align="center"><font style="font-size: 12px;font-weight: bold;">编辑</font></div></li>
      <li id="delete"><div style="width: 100%;" align="center"><font style="font-size: 12px;font-weight: bold;">删除</font></div></li>
    </ul>

  </div>
  </c:if>
  </form>
  </body>
</html>