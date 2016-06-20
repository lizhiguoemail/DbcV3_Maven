package com.dbc.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;

import com.dbc.pojo.Dbc_log;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_logService;
import com.dbc.util.Dbc_common_config;
import com.dbc.util.FileUtil;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.Zip;

/**
 * @Project 源自 dbc
 * @Title CreateCodeAction.java
 * @Package com.dbc.common.action;
 * @Description  代码生成器
 * @author caihuajun
 * @date 2011-08-17
 * @version v1.0
 * 2011-10-17 caihuajun 增加了生成jsp界面的代码
 * 2011-10-19 caihuajun 生成action增加了抛出异常的方法
 * 2011-10-30 caihuajun 生成listjsp的时候，增加了导出excel的功能，增加了每页显示N条的功能
 * 2011-11-02 caihuajun 列表增加了中文插件js
 * 2011-11-21 caihuajun 修改sortcode为double类型
 * 2011-12-02 caihuajun 修改了添加，删除，查看页面的按钮位置，按钮一直显示在新开window的右下角，不会随滚动条而移动，使得操作更加人性化。
 * 2012-03-20 caihuajun 生成添加jsp,把ansi格式转化成utf-8,解决了复制jsp文件要改编码的问题
 * 2012-03-30 caihuajun 生成xml的时候，表名统一改成小写，这样解决了linux中mysql表名严格区分大写的问题
 * 2012-04-12 caihuajun 在createlistjsp方法中，在add和update页面关闭事件中添加了"Ext.getCmp(\"content\").focus();\r\n"，目的是为了解决ie浏览器失去焦点后无法编辑的问题
 * 2012-04-19 caihuajun 发现方法batchdeleteEXT中缺少this.setparam(request);\r\n
 * 2012-09-11 caihuajun 增加生成edbc扩展项目的模块
 * 2012-09-11 caihuajun 把为能在linux和windos上路径通用，定义了String separ = File.separator;，把\\符号都用separ代替了
 * 2015-05-11 caihujaun 增加了获取异常信息的日志功能
 * 2015-06-27 caihuajun 增加了直接把生成的代码拷贝到项目中的功能，并且修改了生成代码的结构，增加了上层src以及jsp结构改成WebRoot
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Properties;


/**
 * @ClassName CreateCodeAction
 * @Description 代码生成器
 * @author caihuajun
 * @date 2011-08-17
 * 2015-07-21 caihuajun 获取配置文件参数换个新方法试试,以后一些配置参数都通过common_config这个类调用
 */
public class Dbc_createCodeAction extends BaseAction{
	
	private String methode;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	public String execute() throws Exception {
		String returnstr="";
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		if("toCreateCode".equals(methode)){
			returnstr=this.toCreateCode();
		}
		else if("createCode".equals(methode)){
			returnstr=this.createCode();
		}
		else if("listCreate".equals(methode)){
			returnstr=this.listCreate();
		}
		else if("showCreate".equals(methode)){
			returnstr=this.showCreate();
		}
		else if("copytoworkspace".equals(methode)){
			returnstr=this.copytoworkspace();
		}
		else if("tocopytoworkspace".equals(methode)){
			returnstr=this.tocopytoworkspace();
		}
		else if("deleteCreate".equals(methode)){
			returnstr=this.deleteCreate();
		}
		 else{
	    	  request.setAttribute("action","dbc_createcode");
			  request.setAttribute("methode",methode);
			  request.setAttribute("exception", Dbc_custom_constants.nomethode);
			  returnstr="Exception";
	      }
		return returnstr;
	}
	
	/**
	 * @Title toCreateCode
	 * @Description 进入代码生成器页面 
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String toCreateCode() {
		return "createcode";	
	}
	
	/**
	 * @Title createCode
	 * @Description 生成代码 
	 * @return String
	 * @throws
	 */	
	public String createCode(){
		try{
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			String separ = File.separator;
			Date date=new Date();
			String datetime=sf.format(date);
			String table_name=request.getParameter("table_name");
			String class_name=request.getParameter("class_name");
			String class_description=request.getParameter("class_description");
			String packageRoute=request.getParameter("packageRoute");
			String author=request.getParameter("author");
			String edition=request.getParameter("edition");
			String jsppath=request.getParameter("jsppath");
			String sortmethode=request.getParameter("sortmethode");
			if(packageRoute==null||"".equals(packageRoute))
			{
				packageRoute="com.dbc";
			}
			
			if(author==null||"".equals(author))
			{
				author="caihuajun";
			}
			
			if(edition==null||"".equals(edition))
			{
				edition="v2.0";
			}
			if(jsppath==null||"".equals(jsppath))
			{
				jsppath="backstage/"+class_name.toLowerCase();
			}
			String[] field_names=request.getParameterValues("field_name"); 
			String[] field_types=request.getParameterValues("field_type"); 
			String[] field_lengths=request.getParameterValues("field_length"); 
			String[] field_isPKs=request.getParameterValues("field_isPK"); 
			String[] field_nullables=request.getParameterValues("field_nullable"); 
			String[] field_descriptions=request.getParameterValues("field_description");
			String isgl=request.getParameter("isgl");
			String[] gl_field_name=request.getParameterValues("gl_field_name"); 
			String[] gl_class=request.getParameterValues("gl_class"); 
			String[] gl_field_description=request.getParameterValues("gl_field_description");
			String[] gl_path=request.getParameterValues("gl_path");
			//2015-06-27 增加的字段
			String[] biaodan_type=request.getParameterValues("biaodan_type");
			String[] biaodan_nullable=request.getParameterValues("biaodan_nullable");
			
			
			FileUtil c=new FileUtil();
			String dirname=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name;
			String dirnamesrc=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src";
			String dirnamepojo=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"pojo";
			String dirnamemappings=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"pojo"+separ+"mappings";
			String dirnamedao=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"dao";
			String dirnamedaoimpl=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"dao"+separ+"Impl";
			String dirnameaction=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"action";
			//String dirnameconfig=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"config";
			String dirnamejsp=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"WebRoot";
			String dirnameservice=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"service";
			String dirnameserviceimpl=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"service"+separ+"Impl";
			String dirnamespring=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"spring";
			String dirnamestruts=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"struts";
			c.createDir(dirname);
			c.createDir(dirnamesrc);
			c.createDir(dirnamepojo);
			c.createDir(dirnamemappings);
			c.createDir(dirnamedao);
			c.createDir(dirnamedaoimpl);
			c.createDir(dirnameaction);
			//c.createDir(dirnameconfig);
			c.createDir(dirnamejsp);
			c.createDir(dirnameservice);
			c.createDir(dirnameserviceimpl);
			c.createDir(dirnamespring);
			c.createDir(dirnamestruts);
			String[] jsppatharr=jsppath.split("/");
			String dirnamejsppath=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"WebRoot";
			for(int i=0;i<jsppatharr.length;i++){
			    dirnamejsppath=dirnamejsppath+separ+jsppatharr[i];
				c.createDir(dirnamejsppath);
			}
			this.createpojo(request, class_name, packageRoute, class_description, author, datetime, edition, field_names, field_types, field_descriptions,isgl,gl_field_name,gl_class,gl_field_description);
			this.createpojoxml(request, class_name, table_name, packageRoute, class_description, author, datetime, edition, field_names, field_types, field_descriptions, field_lengths, field_isPKs, field_nullables,isgl,gl_field_name,gl_class,gl_path);
			this.createidao(request, class_name, packageRoute, class_description, author, datetime, edition);
			this.createdao(request, class_name, packageRoute, class_description, author, datetime, edition);
			this.createiservice(request, class_name, packageRoute, class_description, author, datetime, edition);
			this.createservice(request, class_name, packageRoute, class_description, author, datetime, edition);
			this.createaction(request, class_name, packageRoute, class_description, author, datetime, edition,field_names,sortmethode,isgl,gl_field_name,gl_class,gl_path,biaodan_type);
			//this.createconfig(request, class_name,class_description,packageRoute,jsppath);
			this.createspring(request, class_name, packageRoute,jsppath);
			this.createstruts(request, class_name, packageRoute,jsppath);
			this.createlistjsp(request, class_name, packageRoute, class_description, field_names, field_descriptions,jsppath,sortmethode,biaodan_type);
			this.createaddorupdatejsp(request, class_name, packageRoute, class_description,jsppath,field_names,field_types, field_descriptions,sortmethode,isgl,gl_field_name,gl_class,gl_field_description,biaodan_type,biaodan_nullable);
			Zip zip=new Zip();
			String zipPath = request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+".zip";      
		    String filePath = request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name;    
		    zip.zipFolder(zipPath, filePath);//压缩文件夹      
			request.setAttribute("class_name", class_name);
			/*----换个新方法试试,以后一些配置参数都通过common_config这个类调用
			InputStream in = new ClassPathResource("common_config.properties").getInputStream();
	        Properties property = new Properties();
	        property.load(in);
	        String workpath_java =property.getProperty("createcode_copypath_java");
	        String workpath_jsp =property.getProperty("createcode_copypath_jsp");
	        */
			String workpath_java= Dbc_common_config.getvalue("createcode_copypath_java");
			String workpath_jsp= Dbc_common_config.getvalue("createcode_copypath_jsp");
	        request.setAttribute("workpath_java", workpath_java);
	        request.setAttribute("workpath_jsp", workpath_jsp);
			return "createsuccess";	
		}catch (Exception e) {
		   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_createcode", "createCode", "出现异常："+e.getMessage());
           request.setAttribute("action","dbc_createcode");
           request.setAttribute("methode","createCode");
           request.setAttribute("e", e.toString());
           e.printStackTrace();
           return "Exception";
		}
		
	}
	
	
	/**
	 * @Title createpojo
	 * @Description 生成pojo类 
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @param class_description
	 * @param author
	 * @param datetime
	 * @param edition
	 * @param field_names
	 * @return field_types
	 * @return void
	 */	
	private void createpojo(HttpServletRequest request,String class_name,String packageRoute,String class_description,
			String author,String datetime,String edition,String[] field_names,String[] field_types,String[] field_descriptions,String isgl,String[] gl_field_name,String[] gl_class,String[] gl_field_description)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"pojo"+separ+class_name+".java";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String pojostr="/**\r\n"
			+"* @Project 源自 dbc\r\n"
			+"* @Title "+class_name+".java\r\n"
			+"* @Package "+packageRoute+".pojo\r\n"
			+"* @Description "+class_description+"\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"* @version "+edition+"\r\n"
			+"*/\r\n"
			+"package "+packageRoute+".pojo;\r\n\r\n"
			+"import java.io.Serializable;\r\n\r\n"
			+"/**\r\n"
			+"* @ClassName "+class_name+"\r\n"
			+"* @Description "+class_description+"\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"*/\r\n"
			+"public class "+class_name+" implements Serializable{\r\n\r\n"
			+"private static final long serialVersionUID = 1L;\r\n\r\n"
			+"private String id;  //主键\r\n\r\n";
			if(field_names!=null)
			{
				for(int i=0;i<field_names.length;i++)
				{
					if("Clob".equals(field_types[i])){
						field_types[i]="String";
					}
					if(field_descriptions[i]!=null&&!"".equals(field_descriptions[i]))
					{
						pojostr=pojostr+"private "+field_types[i]+"  "+field_names[i]+"; //"+field_descriptions[i]+"\r\n\r\n";
					}
					else
					{
						pojostr=pojostr+"private "+field_types[i]+"  "+field_names[i]+";\r\n\r\n";
					}
				}
				if("1".equals(isgl)){
					if(gl_field_name!=null&&gl_class!=null&&gl_field_name.length==gl_class.length){
						for(int i=0;i<gl_class.length;i++){
							if(gl_class[i]!=null&&!"".equals(gl_class[i]))
							{
								pojostr=pojostr+"private "+gl_class[i]+"  "+gl_class[i].toLowerCase()+"; //"+gl_field_description[i]+"\r\n\r\n";
							}
						}
					}
				}
				pojostr=pojostr+"private String description; //备注\r\n\r\n"
				               +"private Double sortcode; // 排序码\r\n\r\n"
				               +"private String createdate; //创建时间\r\n\r\n"
				               +"private String createuser; //创建人\r\n\r\n"
				               +"private String updatedate; //修改时间\r\n\r\n"
				               +"private String updateuser; //修改人\r\n\r\n"
				               +"private String deletemark=\"0\"; //删除标识\r\n\r\n"
				               +"public String getId() {\r\n    "
				               +"return id;\r\n"
				               +"}\r\n\r\n"
				               +"public void setId(String id) {\r\n    "
				               +"this.id = id;\r\n"
				               +"}\r\n\r\n";
				
				for(int i=0;i<field_names.length;i++)
				{
					if("Clob".equals(field_types[i])){
						field_types[i]="String";
					}
					Character bigfirst=Character.toUpperCase(field_names[i].charAt(0));
					String newbig=bigfirst.toString()+(field_names[i].toLowerCase()).substring(1, field_names[i].length());
					pojostr=pojostr+"public "+field_types[i]+" get"+newbig+"() {\r\n    "
							+"return "+field_names[i]+";\r\n"
							+"}\r\n\r\n"
							+"public void set"+newbig+"("+field_types[i]+" "+field_names[i]+") {\r\n    "
							+"this."+field_names[i]+" = "+field_names[i]+";\r\n"
							+"}\r\n\r\n";
				}
				if("1".equals(isgl)){
					if(gl_field_name!=null&&gl_class!=null&&gl_field_name.length==gl_class.length){
						for(int i=0;i<gl_class.length;i++){
							Character bigfirst=Character.toUpperCase(gl_class[i].charAt(0));
							String newbig=bigfirst.toString()+(gl_class[i].toLowerCase()).substring(1, gl_class[i].length());
							pojostr=pojostr+"public "+gl_class[i]+" get"+newbig+"() {\r\n    "
							+"return "+gl_class[i].toLowerCase()+";\r\n"
							+"}\r\n\r\n"
							+"public void set"+newbig+"("+gl_class[i]+" "+gl_class[i].toLowerCase()+") {\r\n    "
							+"this."+gl_class[i].toLowerCase()+" = "+gl_class[i].toLowerCase()+";\r\n"
							+"}\r\n\r\n";
						}
					}
				}	
				pojostr=pojostr+"public String getDescription() {\r\n    "
				               +"return description;\r\n"
				               +"}\r\n\r\n"
				               +"public void setDescription(String description) {\r\n    "
				               +"this.description = description;\r\n"
				               +"}\r\n\r\n"
				               +"public Double getSortcode() {\r\n    "
				               +"return sortcode;\r\n"
				               +"}\r\n\r\n"
				               +"public void setSortcode(Double sortcode) {\r\n    "
				               +"this.sortcode = sortcode;\r\n"
				               +"}\r\n\r\n"
				               +"public String getCreatedate() {\r\n    "
				               +"return createdate;\r\n"
				               +"}\r\n\r\n"
				               +"public void setCreatedate(String createdate) {\r\n    "
				               +"this.createdate = createdate;\r\n"
				               +"}\r\n\r\n"
				               +"public String getCreateuser() {\r\n    "
				               +"return createuser;\r\n"
				               +"}\r\n\r\n"
				               +"public void setCreateuser(String createuser) {\r\n    "
				               +"this.createuser = createuser;\r\n"
				               +"}\r\n\r\n"
				               +"public String getUpdatedate() {\r\n    "
				               +"return updatedate;\r\n"
				               +"}\r\n\r\n"
				               +"public void setUpdatedate(String updatedate) {\r\n    "
				               +"this.updatedate = updatedate;\r\n"
				               +"}\r\n\r\n"
				               +"public String getUpdateuser() {\r\n    "
				               +"return updateuser;\r\n"
				               +"}\r\n\r\n"
				               +"public void setUpdateuser(String updateuser) {\r\n    "
				               +"this.updateuser = updateuser;\r\n"
				               +"}\r\n\r\n"
				               +"public String getDeletemark() {\r\n    "
				               +"return deletemark;\r\n"
				               +"}\r\n\r\n"
				               +"public void setDeletemark(String deletemark) {\r\n    "
				               +"this.deletemark = deletemark;\r\n"
				               +"}\r\n\r\n";
			}
			pojostr=pojostr+"}\r\n\r\n";
			bw.write(pojostr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createpojoxml
	 * @Description 生成pojoxml 
	 * @param request
	 * @param class_name
	 * @param table_name
	 * @param packageRoute
	 * @param class_description
	 * @param author
	 * @param datetime
	 * @param edition
	 * @param field_names
	 * @return field_types
	 * @param field_lengths
	 * @return field_isPKs
	 * @return field_nullables
	 * @return void
	 */	
	private void createpojoxml(HttpServletRequest request,String class_name,String table_name,String packageRoute,String class_description,
			String author,String datetime,String edition,String[] field_names,String[] field_types,String[] field_descriptions,String[] field_lengths,
			String[] field_isPKs,String [] field_nullables,String isgl,String[] gl_field_name,String[] gl_class,String[] gl_path) {
		String separ = File.separator;
		try {
			if(field_names!=null)
			{
				String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"pojo"+separ+"mappings"+separ+class_name+".hbm.xml";
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
				BufferedWriter bw = new BufferedWriter(out);  
				String pojoxmlstr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+"<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\r\n"
				+"\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\r\n"
				+"<hibernate-mapping>\r\n"
				+"    <class name=\""+packageRoute+".pojo."+class_name+"\" table=\""+table_name.toLowerCase()+"\">\r\n"
				+"        <id name=\"id\" column=\"ID\" type=\"java.lang.String\">\r\n"
				+"            <generator class=\"uuid.hex\"></generator>\r\n"
				+"        </id>\r\n";
				for(int i=0;i<field_names.length;i++)
				{
					if("Clob".equals(field_types[i])){
						field_types[i]="text";
					}
					 pojoxmlstr=pojoxmlstr+"        <property name=\""+field_names[i]+"\" column=\""+field_names[i].toUpperCase()+"\" type=\""+field_types[i].toLowerCase()+"\"";
					 if("0".equals(field_nullables))
					  {
					    pojoxmlstr=pojoxmlstr+" not-null=\"true\""; 
					  }
					 if(!"".equals(field_lengths[i]))
					 {
						 pojoxmlstr=pojoxmlstr+" length=\""+field_lengths[i]+"\""; 
					 }
					 pojoxmlstr=pojoxmlstr+" lazy=\"true\"/>\r\n";
				}
				pojoxmlstr=pojoxmlstr+"        <property name=\"description\" column=\"DESCRIPTION\" type=\"string\" lazy=\"true\"/>\r\n" 
						             +"        <property name=\"sortcode\" column=\"SORTCODE\" type=\"double\" lazy=\"true\"/>\r\n"
						             +"        <property name=\"createdate\" column=\"CREATEDATE\" type=\"string\" lazy=\"true\"/>\r\n"
						             +"        <property name=\"createuser\" column=\"CREATEUSER\" type=\"string\" lazy=\"true\"/>\r\n"
						             +"        <property name=\"updatedate\" column=\"UPDATEDATE\" type=\"string\" lazy=\"true\"/>\r\n"
						             +"        <property name=\"updateuser\" column=\"UPDATEUSER\" type=\"string\" lazy=\"true\"/>\r\n"
						             +"        <property name=\"deletemark\" column=\"DELETEMARK\" type=\"string\" lazy=\"true\" not-null=\"true\" />\r\n";
				if("1".equals(isgl)&&gl_class!=null&&gl_path!=null&&gl_field_name!=null&&(gl_class.length==gl_field_name.length)&&(gl_class.length==gl_path.length)){
					for(int i=0;i<gl_class.length;i++){
						pojoxmlstr=pojoxmlstr+"<many-to-one name=\""+gl_class[i].toLowerCase()+"\" class=\""+gl_path[i]+".pojo."+gl_class[i]+"\" column=\""+gl_field_name[i].toUpperCase()+"\"  cascade=\"none\" insert=\"false\" update=\"false\"  lazy=\"proxy\" ></many-to-one>\r\n";
					}
				}
				pojoxmlstr=pojoxmlstr+"    </class>\r\n</hibernate-mapping>";
				bw.write(pojoxmlstr);
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @Title createidao
	 * @Description 生成dao接口类 
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @param class_description
	 * @param author
	 * @param datetime
	 * @param edition
	 * @return void
	 */	
	private void createidao(HttpServletRequest request,String class_name,String packageRoute,String class_description,String author,String datetime,String edition)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"dao"+separ+class_name+"Dao.java";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String idaostr="/**\r\n"
			+"* @Project 源自 dbc\r\n"
			+"* @Title "+class_name+"Dao.java\r\n"
			+"* @Package "+packageRoute+".dao\r\n"
			+"* @Description "+class_description+"dao类接口\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"* @version "+edition+"\r\n"
			+"*/\r\n"
			+"package "+packageRoute+".dao;\r\n\r\n"
			+"import com.dbc.dao.BaseDao;\r\n\r\n"
			+"/**\r\n"
			+"* @ClassName "+class_name+"Dao\r\n"
			+"* @Description "+class_description+"dao类接口\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"*/\r\n"
			+"public interface "+class_name+"Dao extends BaseDao{\r\n\r\n"
			+"}";
			bw.write(idaostr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createiservice
	 * @Description 生成service接口类 
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @param class_description
	 * @param author
	 * @param datetime
	 * @param edition
	 * @return void
	 */	
	private void createiservice(HttpServletRequest request,String class_name,String packageRoute,String class_description,String author,String datetime,String edition)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"service"+separ+class_name+"Service.java";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String idaostr="/**\r\n"
			+"* @Project 源自 dbc\r\n"
			+"* @Title "+class_name+"Service.java\r\n"
			+"* @Package "+packageRoute+".service\r\n"
			+"* @Description "+class_description+"service类接口\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"* @version "+edition+"\r\n"
			+"*/\r\n"
			+"package "+packageRoute+".service;\r\n\r\n"
			+"import com.dbc.service.BaseService;\r\n\r\n"
			+"/**\r\n"
			+"* @ClassName "+class_name+"Service\r\n"
			+"* @Description "+class_description+"service类接口\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"*/\r\n"
			+"public interface "+class_name+"Service extends BaseService{\r\n\r\n"
			+"}";
			bw.write(idaostr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createdao
	 * @Description 生成dao类 
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @param class_description
	 * @param author
	 * @param datetime
	 * @param edition
	 * @return void
	 */	
	private void createdao(HttpServletRequest request,String class_name,String packageRoute,String class_description,String author,String datetime,String edition)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"dao"+separ+"Impl"+separ+class_name+"DaoImpl.java";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String idaostr="/**\r\n"
			+"* @Project 源自 dbc\r\n"
			+"* @Title "+class_name+"DaoImpl.java\r\n"
			+"* @Package "+packageRoute+".dao.Impl\r\n"
			+"* @Description "+class_description+"dao实现类\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"* @version "+edition+"\r\n"
			+"*/\r\n\r\n"
			+"package "+packageRoute+".dao.Impl;\r\n\r\n"
			+"import com.dbc.dao.Impl.BaseDaoImpl;\r\n\r\n"
			+"import "+packageRoute+".dao."+class_name+"Dao;\r\n\r\n"
			+"/**\r\n"
			+"* @ClassName "+class_name+"DaoImpl\r\n"
			+"* @Description "+class_description+"dao实现类\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"*/\r\n"
			+"public class "+class_name+"DaoImpl extends BaseDaoImpl implements "+class_name+"Dao{\r\n\r\n"
			+"}";
			bw.write(idaostr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createservice
	 * @Description 生成service类 
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @param class_description
	 * @param author
	 * @param datetime
	 * @param edition
	 * @return void
	 */	
	private void createservice(HttpServletRequest request,String class_name,String packageRoute,String class_description,String author,String datetime,String edition)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"service"+separ+"Impl"+separ+class_name+"ServiceImpl.java";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String servicestr="/**\r\n"
			+"* @Project 源自 dbc\r\n"
			+"* @Title "+class_name+"ServiceImpl.java\r\n"
			+"* @Package "+packageRoute+".service.Impl\r\n"
			+"* @Description "+class_description+"service实现类\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"* @version "+edition+"\r\n"
			+"*/\r\n\r\n"
			+"package "+packageRoute+".service.Impl;\r\n\r\n"
			+"import com.dbc.service.Impl.BaseServiceImpl;\r\n\r\n"
			+"import "+packageRoute+".service."+class_name+"Service;\r\n\r\n"
			+"import "+packageRoute+".dao."+class_name+"Dao;\r\n\r\n"
			+"/**\r\n"
			+"* @ClassName "+class_name+"ServiceImpl\r\n"
			+"* @Description "+class_description+"service实现类\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"*/\r\n"
			+"public class "+class_name+"ServiceImpl extends BaseServiceImpl implements "+class_name+"Service{\r\n\r\n"
			+"	private "+class_name+"Dao "+class_name.toLowerCase()+"dao;\r\n\r\n";
			Character bigfirst=Character.toUpperCase(class_name.charAt(0));
			String firstbig=bigfirst.toString()+class_name.substring(1, class_name.length()).toLowerCase();
			String newbig=bigfirst.toString()+class_name.substring(1, class_name.length()).toLowerCase()+"dao";
			servicestr=servicestr+"	public "+firstbig+"Dao  get"+newbig+"() {\r\n    "
					+"	return "+class_name.toLowerCase()+"dao;\r\n"
					+"	}\r\n\r\n"
					+"	public void set"+newbig+"("+firstbig+"Dao  "+class_name.toLowerCase()+"dao) {\r\n    "
					+"	this."+class_name.toLowerCase()+"dao = "+class_name.toLowerCase()+"dao;\r\n"
					+"	}\r\n";
			servicestr=servicestr+"}";
			bw.write(servicestr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @Title createaction
	 * @Description 生成action类 
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @param class_description
	 * @param author
	 * @param datetime
	 * @param edition
	 * @return void
	 */	
	private void createaction(HttpServletRequest request,String class_name,String packageRoute,String class_description,String author,String datetime,String edition,String[] field_names,String sortmethode,String isgl,String[] gl_fieldname,String[] gl_class,String[] gl_path,String[] biaodan_type)
	{
		String separ = File.separator;
		try {
			String sort="desc";
			if("createdate".equals(sortmethode))
			{
				sort="desc";
			}
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"action"+separ+class_name+"Action.java";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);
			String setstr="";
			 if(field_names!=null){
					for(int i=0;i<field_names.length;i++)
					{
						if("checkbox".equals(biaodan_type[i])){
							Character bigfirst=Character.toUpperCase(field_names[i].charAt(0));
							String newbig=bigfirst.toString()+field_names[i].substring(1, field_names[i].length()).toLowerCase();
							setstr=setstr+"            if("+class_name.toLowerCase()+".get"+newbig+"()!=null){\r\n"
										 +"            	 "+class_name.toLowerCase()+".set"+newbig+"(\",\"+"+class_name.toLowerCase()+".get"+newbig+"()+\",\");\r\n"
										 +"             }\r\n";		
						}
				}
			}
			String actionstr="/**\r\n"
			+"* @Project 源自 dbc\r\n"
			+"* @Title "+class_name+"Action.java\r\n"
			+"* @Package "+packageRoute+".action\r\n"
			+"* @Description "+class_description+"action类\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"* @version "+edition+"\r\n"
			+"*/\r\n"
			+"package "+packageRoute+".action;\r\n\r\n"
			+"import com.dbc.util.Dbc_custom_constants;\r\n"
			+"import com.dbc.util.Dbcutil;\r\n"
			+"import com.dbc.util.PageParm;\r\n"
			+"import java.util.List;\r\n"
			+"import javax.servlet.http.HttpServletRequest;\r\n"
			+"import javax.servlet.http.HttpServletResponse;\r\n"
			+"import org.apache.log4j.Logger;\r\n"
			+"import org.apache.struts2.ServletActionContext;\r\n"
			+"import com.dbc.action.BaseAction;\r\n"
			+"import com.opensymphony.xwork2.ModelDriven;\r\n"
			+"import com.dbc.pojo.Dbc_log;\r\n"
			+"import com.dbc.pojo.Dbc_userinfo;\r\n"
			+"import "+packageRoute+".pojo."+class_name+";\r\n"
			+"import "+packageRoute+".service."+class_name+"Service;\r\n";
			if("1".equals(isgl)&&gl_path!=null&&gl_class!=null&&gl_fieldname!=null&&(gl_path.length==gl_class.length)&&(gl_class.length==gl_fieldname.length)){
				for(int i=0;i<gl_path.length;i++){
					actionstr=actionstr+"import "+gl_path[i]+".pojo."+gl_class[i]+";\r\n";
				}
			}
			actionstr=actionstr+"/**\r\n"
			+"* @ClassName "+class_name+"Action\r\n"
			+"* @Description "+class_description+"action类\r\n"
			+"* @author "+author+"\r\n"
			+"* @date "+datetime+"\r\n"
			+"*/\r\n"
			+"public class "+class_name+"Action extends BaseAction implements ModelDriven{\r\n\r\n"
			+"    private static final long serialVersionUID = 1L;\r\n\r\n"
			+"    private Logger logger=Logger.getLogger("+class_name+"Action.class);\r\n\r\n"
			+"    private String methode;\r\n\r\n"
			+"    private "+class_name+" "+class_name.toLowerCase()+";\r\n\r\n"
			+"    private HttpServletRequest request;\r\n\r\n"
			+"    private HttpServletResponse response;\r\n\r\n"
			+"    private String nowpageString;\r\n\r\n"
			+"    private String gotopagetype;\r\n\r\n"
			+"    private String gotopageString;\r\n\r\n"
			+"    private String pagesizeString=\"15\";\r\n\r\n"
			+"    private String sname;\r\n\r\n"
			+"    private String svalue;\r\n\r\n"
			+"    public String execute(){\r\n"
			+"      String returnstr=\"\";\r\n"
			+"      request = ServletActionContext.getRequest();\r\n"
			+"      response= ServletActionContext.getResponse();\r\n"
			+"      if(\"list\".equals(methode)){\r\n"
			+"        returnstr=this.list();\r\n"
			+"      }\r\n"
			+"      else if(\"toaddorupdate\".equals(methode)){\r\n"
			+"        returnstr=this.toaddorupdate();\r\n"
			+"      }\r\n"
			+"      else if(\"addorupdate\".equals(methode)){\r\n"
			+"        returnstr=this.addorupdate();\r\n"
			+"      }\r\n"
			+"      else if(\"deletebyid\".equals(methode)){\r\n"
			+"        returnstr=this.deletebyid();\r\n"
			+"      }\r\n"
			+"      else if(\"delete\".equals(methode)){\r\n"
			+"        returnstr=this.delete();\r\n"
			+"      }\r\n"
			+"      else{\r\n"
			+"        request.setAttribute(\"action\",\""+class_name.toLowerCase()+"\");\r\n"
			+"        request.setAttribute(\"methode\",methode);\r\n"
			+"        request.setAttribute(\"exception\", Dbc_custom_constants.nomethode);\r\n"
			+"        returnstr=\"Exception\";\r\n"
			+"      }\r\n"
			+"      return returnstr;\r\n"
			+"    }\r\n\r\n"
			+"    /**\r\n"
			+"    * @Title list\r\n"
			+"    * @Description 进入列表页面\r\n"
			+"    * @return string\r\n"
			+"    */\r\n"
			+"    public String list(){\r\n"
			+"      try{\r\n"
			+"          "+class_name+"Service "+class_name.toLowerCase()+"service=("+class_name+"Service) super.getInstence(\""+class_name.toLowerCase()+"service\");\r\n"
			+"          PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);\r\n"
			+"          List pagelist=(List)"+class_name.toLowerCase()+"service.selEntityByPage("+class_name+".class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr(\"sortcode\"),Dbcutil.getarr(\"desc\"));\r\n"
			+"          List list = (List) pagelist.get(0);\r\n"
			+"          PageParm pageParm=(PageParm) pagelist.get(1);\r\n"
			+"          request.setAttribute(\"list\", list);\r\n"
			+"          request.setAttribute(\"pageParm\", pageParm);\r\n"
			+"          request.setAttribute(\"sname\", sname);\r\n"
			+"          request.setAttribute(\"svalue\", svalue);\r\n"
			+"          return \"list\";\r\n"
			+"       }catch (Exception e) {\r\n"
			+"           logger.error(\"error\",e);\r\n"
			+"           e.printStackTrace();\r\n"
			+"           return null;\r\n"
			+"       }\r\n"
			+"    }\r\n\r\n"
			+"    /**\r\n"
			+"    * @Title toaddorupdate\r\n"
			+"    * @Description 进入添加或修改页面\r\n"
			+"    * @return string  返回值\r\n"
			+"    */\r\n"
			+"    public String toaddorupdate(){\r\n"
			+"      try{\r\n"
			+"            String id=request.getParameter(\"id\");\r\n";
			if("1".equals(isgl)&&gl_path!=null&&gl_class!=null&&gl_fieldname!=null&&(gl_path.length==gl_class.length)&&(gl_class.length==gl_fieldname.length)){
				actionstr=actionstr+"            "+class_name+"Service "+class_name.toLowerCase()+"service=("+class_name+"Service) super.getInstence(\""+class_name.toLowerCase()+"service\");\r\n";
				for(int i=0;i<gl_path.length;i++){
					actionstr=actionstr+"            List "+gl_class[i].toLowerCase()+"list="+class_name.toLowerCase()+"service.selEntity("+gl_class[i]+".class, null, null, null, Dbcutil.getarr(\"sortcode\"), Dbcutil.getarr(\"desc\"));\r\n";
				}
			}
			actionstr=actionstr+"            if(id!=null&&!\"\".equals(id)){\r\n";
			if(!"1".equals(isgl)||gl_path==null||gl_class==null||gl_fieldname==null||(gl_path.length!=gl_class.length)||(gl_class.length!=gl_fieldname.length)){
				actionstr=actionstr+"            	"+class_name+"Service "+class_name.toLowerCase()+"service=("+class_name+"Service) super.getInstence(\""+class_name.toLowerCase()+"service\");\r\n";
			}
			actionstr=actionstr+"        		"+class_name+" "+class_name.toLowerCase()+"=("+class_name+") "+class_name.toLowerCase()+"service.selByOid("+class_name+".class, id);\r\n"
			+setstr
			+"            	request.setAttribute(\""+class_name.toLowerCase()+"\", "+class_name.toLowerCase()+");\r\n"
			+"            	request.setAttribute(\"isupdate\", \"1\");\r\n"
			+"        	}\r\n";
			if("1".equals(isgl)&&gl_path!=null&&gl_class!=null&&gl_fieldname!=null&&(gl_path.length==gl_class.length)&&(gl_class.length==gl_fieldname.length)){
				for(int i=0;i<gl_path.length;i++){
					actionstr=actionstr+"           request.setAttribute(\""+gl_class[i].toLowerCase()+"list\", "+gl_class[i].toLowerCase()+"list);\r\n";
				}
			}
			actionstr=actionstr+"        	return \"addorupdate\";\r\n";
			actionstr=actionstr+"       } catch (Exception e) {\r\n"
			+"           logger.error(\"error\",e);\r\n"
			+"           e.printStackTrace();\r\n"
			+"           return \"Exception\";\r\n"
			+"       }\r\n"
			+"    }\r\n\r\n"
			+"    /**\r\n"
			+"    * @Title addorupdate\r\n"
			+"    * @Description 添加或修改\r\n"
			+"    * @return string 返回值\r\n"
			+"    */\r\n"
			+"    public String addorupdate(){\r\n"
			+"      try{\r\n"
			+"            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute(\"backstage_user\");\r\n"
			+"            String ipaddress=Dbcutil.getIpByrequest(request);\r\n"
			+"            String nowdate=Dbcutil.getnowdateString(\"yyyy-MM-dd hh:mm:ss\");\r\n"
			+"            String username=userinfo.getUsername();\r\n"
			+"            "+class_name+"Service "+class_name.toLowerCase()+"service=("+class_name+"Service) super.getInstence(\""+class_name.toLowerCase()+"service\");\r\n"
			+"            String isupdate=request.getParameter(\"isupdate\");\r\n"
			+"            if(\"1\".equals(isupdate)){\r\n"
			+"                "+class_name.toLowerCase()+".setUpdatedate(nowdate);\r\n"
			+"                "+class_name.toLowerCase()+".setUpdateuser(username);\r\n"
			+"                String sortcodestr=request.getParameter(\"sortcode\");\r\n"
			+"                if(sortcodestr==null||\"\".equals(sortcodestr)){\r\n"
			+"                  Double sortcode="+class_name.toLowerCase()+"service.getSortCode_Double(\""+class_name+"\");\r\n"
			+"                  "+class_name.toLowerCase()+".setSortcode(sortcode);\r\n"
			+"                 }\r\n"
			+"				  Dbc_log dbc_log=new Dbc_log(userinfo, ipaddress, \""+class_name.toLowerCase()+"\", \"addorupdate\", \"修改"+class_description+"\");\r\n"
			+"                "+class_name.toLowerCase()+"service.updateObject("+class_name.toLowerCase()+",dbc_log);\r\n"
			+"            }\r\n"
			+"            else{\r\n"
			+"              "+class_name.toLowerCase()+".setId(null);\r\n"
			+"              "+class_name.toLowerCase()+".setCreatedate(nowdate);\r\n"
			+"              "+class_name.toLowerCase()+".setCreateuser(username);\r\n"
			+"              String sortcodestr=request.getParameter(\"sortcode\");\r\n"
			+"              if(sortcodestr==null||\"\".equals(sortcodestr)){\r\n"
			+"          	    Double sortcode="+class_name.toLowerCase()+"service.getSortCode_Double(\""+class_name+"\");\r\n"
			+"          	    "+class_name.toLowerCase()+".setSortcode(sortcode);\r\n"
			+"              }\r\n"
			+"				Dbc_log dbc_log=new Dbc_log(userinfo, ipaddress, \""+class_name.toLowerCase()+"\", \"addorupdate\", \"增加"+class_description+"\");\r\n"
			+"              "+class_name.toLowerCase()+"service.saveObject("+class_name.toLowerCase()+",dbc_log);\r\n"
			+"            }\r\n"
			+"       		return \"redirect-list\";\r\n"
			+"       } catch (Exception e) {\r\n"
			+"           logger.error(\"error\",e);\r\n"
			+"           e.printStackTrace();\r\n"
			+"           return null;\r\n"
			+"       }\r\n"
			+"    }\r\n\r\n"
			+"    /**\r\n"
			+"    * @Title deletebyid\r\n"
			+"    * @Description 删除"+class_description+"\r\n"
			+"    * @return 返回值\r\n"
			+"    */\r\n"
			+"    public String deletebyid(){\r\n"
			+"      try{\r\n"
			+"          Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute(\"backstage_user\");\r\n"
			+"          String ipaddress=Dbcutil.getIpByrequest(request);\r\n"
			+"          String id=request.getParameter(\"id\");\r\n"
			+"          "+class_name+"Service "+class_name.toLowerCase()+"service=("+class_name+"Service) super.getInstence(\""+class_name.toLowerCase()+"service\");\r\n"
			+"		 Dbc_log dbc_log=new Dbc_log(userinfo, ipaddress, \""+class_name.toLowerCase()+"\", \"deletebyid\", \"删除"+class_description+"\");\r\n"
			+"          "+class_name.toLowerCase()+"service.deletebyids("+class_name+".class,Dbcutil.getarr(id),true,dbc_log);\r\n"
			+"       	return \"redirect-list\";\r\n"
			+"       } catch (Exception e) {\r\n"
			+"          logger.error(\"error\",e);\r\n"
			+"          e.printStackTrace();\r\n"
			+"          return null;\r\n"
			+"       }\r\n"
			
			+"    }\r\n\r\n"
			+"    /**\r\n"
			+"    * @Title delete\r\n"
			+"    * @Description 删除"+class_description+"\r\n"
			+"    * @return 返回值\r\n"
			+"    */\r\n"
			+"    public String delete(){\r\n"
			+"      try{\r\n"
			+"          Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute(\"backstage_user\");\r\n"
			+"          String ipaddress=Dbcutil.getIpByrequest(request);\r\n"
			+"          String[] ids=request.getParameterValues(\"checks\");\r\n"
			+"          "+class_name+"Service "+class_name.toLowerCase()+"service=("+class_name+"Service) super.getInstence(\""+class_name.toLowerCase()+"service\");\r\n"
			+"	       Dbc_log dbc_log=new Dbc_log(userinfo, ipaddress, \""+class_name.toLowerCase()+"\", \"delete\", \"删除"+class_description+"\");\r\n"
			+"          "+class_name.toLowerCase()+"service.deletebyids("+class_name+".class,ids,true,dbc_log);\r\n"
			+"       	return \"redirect-list\";\r\n"
			+"       } catch (Exception e) {\r\n"
			+"           logger.error(\"error\",e);\r\n"
			+"           e.printStackTrace();\r\n"
			+"           return null;\r\n"
			+"       }\r\n"
			
			+"    }\r\n\r\n"
			+"    public String getMethode() {\r\n"
			+"       return methode;\r\n"
			+"    }\r\n\r\n"
			+"    public void setMethode(String methode) {\r\n"
			+"       this.methode = methode;\r\n"
			+"    }\r\n\r\n"
			+"     public Object getModel(){\r\n"
			+"        if("+class_name.toLowerCase()+" == null){\r\n"
			+"          "+class_name.toLowerCase()+" = new "+class_name+"();\r\n"
			+"        }\r\n"
			+"        return "+class_name.toLowerCase()+";\r\n"
			+"     }\r\n\r\n"
			+"     public "+class_name+" get"+class_name+"() {\r\n"
			+"        return "+class_name.toLowerCase()+";\r\n"
			+"     }\r\n\r\n"
			+"     public void set"+class_name+"("+class_name+" "+class_name.toLowerCase()+"){ \r\n"
			+"        this."+class_name.toLowerCase()+" = "+class_name.toLowerCase()+";\r\n"
			+"     }\r\n"
			+"     public String getNowpageString(){\r\n"
			+"        return nowpageString;\r\n"
			+"     }\r\n\r\n"
			+"     public void setNowpageString(String nowpageString){\r\n"
			+"        this.nowpageString = nowpageString;\r\n"
			+"     }\r\n\r\n"
			+"     public String getGotopagetype(){\r\n"
			+"       return gotopagetype;\r\n"
			+"     }\r\n\r\n"
			+"     public void setGotopagetype(String gotopagetype){\r\n"
			+"       this.gotopagetype = gotopagetype;\r\n"
			+"     }\r\n\r\n"
			+"     public String getGotopageString(){\r\n"
			+"       return gotopageString;\r\n"
			+"     }\r\n\r\n"
			+"     public void setGotopageString(String gotopageString){\r\n"
			+"       this.gotopageString = gotopageString;\r\n"
			+"     }\r\n\r\n"
			+"     public String getSname(){\r\n"
			+"       return sname;\r\n"
			+"     }\r\n\r\n"
			+"     public void setSname(String sname){\r\n"
			+"       this.sname = sname;\r\n"
			+"     }\r\n\r\n"
			+"     public String getSvalue(){\r\n"
			+"       return svalue;\r\n"
			+"     }\r\n\r\n"
			+"     public void setSvalue(String svalue){\r\n"
			+"       this.svalue = svalue;\r\n"
			+"     }\r\n\r\n"
			+"}";
			bw.write(actionstr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @Title createconfig
	 * @Description 生成配置
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @return void
	 */	
	private void createconfig(HttpServletRequest request,String class_name,String class_description,String packageRoute,String jsppath)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"config"+separ+class_name+"config.txt";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String configstr="\r\n--------------------第一步：struts相关配置--------------------\r\n\r\n"
			+"  在struts.xml文件中插入以下代码：\r\n\r\n"
			+"    <include file=\""+packageRoute.replace(".", "/")+"/struts/struts-"+class_name.toLowerCase()+".xml\"></include>\r\n\r\n"
			+"    \r\n--------------------第二步：spring相关配置--------------------\r\n\r\n"
			+"  在applicationContext-beans.xml文件中插入以下代码：\r\n\r\n"
			+"    <!-- "+class_description+" -->\r\n"
			+"    <import resource=\""+packageRoute.replace(".", "/")+"/spring/"+class_name.toLowerCase()+".xml\" />\r\n\r\n";
			
			bw.write(configstr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createspring
	 * @Description 生成spring配置文件
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @return void
	 */	
	private void createspring(HttpServletRequest request,String class_name,String packageRoute,String jsppath)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"spring"+separ+"spring-"+class_name.toLowerCase()+".xml";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String springstr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
			+"<beans xmlns=\"http://www.springframework.org/schema/beans\"\r\n"
			+"        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
			+"        xmlns:aop=\"http://www.springframework.org/schema/aop\"\r\n"
			+"        xmlns:tx=\"http://www.springframework.org/schema/tx\"\r\n"
			+"        xsi:schemaLocation=\"\r\n"
			+"            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd\r\n"
			+"            http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd\r\n"
			+"            http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd\">\r\n"
			+"           <bean id=\""+class_name.toLowerCase()+"dao\" class=\""+packageRoute+".dao.Impl."+class_name+"DaoImpl\">\r\n"
			+"            <property name=\"sessionFactory\">\r\n"
			+"               <ref bean=\"sessionFactory\" />\r\n"
			+"            </property>\r\n"
			+"        </bean>\r\n"
			+"        <bean id=\""+class_name.toLowerCase()+"service\" class=\""+packageRoute+".service.Impl."+class_name+"ServiceImpl\">\r\n"
			+"           <property name=\""+class_name.toLowerCase()+"dao\">\r\n"
			+"               <ref bean=\""+class_name.toLowerCase()+"dao\" />\r\n"
			+"           </property>\r\n"
			+"           <property name=\"basedao\">\r\n"
			+"               <ref bean=\"basedao\" />\r\n"
			+"           </property>\r\n"
			+"         </bean>\r\n"
			+" </beans>\r\n";
			bw.write(springstr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createstruts
	 * @Description 生成struts配置文件
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @return void
	 */	
	private void createstruts(HttpServletRequest request,String class_name,String packageRoute,String jsppath)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src"+separ+"struts"+separ+"struts-"+class_name.toLowerCase()+".xml";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String strutsstr="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n"
			+"<!DOCTYPE struts PUBLIC \r\n"
			+"        \"-//Apache Software Foundation//DTD Struts Configuration 2.0//EN\" \r\n"
			+"        \"http://struts.apache.org/dtds/struts-2.0.dtd\"> \r\n\r\n\r\n"
			+"<struts>\r\n"
			+"  <package name=\""+class_name.toLowerCase()+"\" extends=\"backstage\">\r\n"
			+"     <action  name=\""+class_name.toLowerCase()+"\" class=\""+packageRoute+".action."+class_name+"Action\" method=\"execute\">\r\n"
			+"         <result name=\"list\">/"+jsppath+"/List"+class_name.toLowerCase()+".jsp</result>\r\n"
			+"         <result name=\"addorupdate\">/"+jsppath+"/Addorupdate"+class_name.toLowerCase()+".jsp</result>\r\n"
			+"         <result name=\"redirect-list\" type=\"redirectAction\">\r\n"
			+"           <param name=\"actionName\">"+class_name.toLowerCase()+"</param>\r\n"
			+"           <param name=\"methode\">list</param>\r\n"
			+"         </result>\r\n"
			+"      </action>\r\n"
			+"  </package>\r\n"
			+"</struts>\r\n";
			bw.write(strutsstr);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createlistjsp
	 * @Description 生成列表jsp
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @return void
	 */	
	private void createlistjsp(HttpServletRequest request,String class_name,String packageRoute,String class_description,String[] field_names,String[] field_descriptions,String jsppath,String sortmethode,String[] biaodan_type)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"WebRoot"+separ+jsppath+separ+"List"+class_name.toLowerCase()+".jsp";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);
			int ishavepic=0;
			String listjspstrstart="<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"utf-8\"%>\r\n" 
					         +"<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>\r\n"
							 +"<%@ taglib prefix=\"fn\" uri=\"http://java.sun.com/jsp/jstl/functions\" %>\r\n"
							 +"<%\r\n"
							 +"String path = request.getContextPath();\r\n"
							 +"String basePath = request.getScheme()+\"://\"+request.getServerName()+\":\"+request.getServerPort()+path+\"/\";\r\n"
							 +"%>\r\n\r\n"
							 +"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n"
							 +"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
							 +"<head>\r\n"
							 +"<base href=\"<%=basePath%>\">\r\n"
							 +"<title>"+class_description+"列表</title>\r\n"
							 +"<meta http-equiv=\"pragma\" content=\"no-cache\" />\r\n"
							 +"<meta http-equiv=\"cache-control\" content=\"no-cache\" />\r\n"
							 +"<meta http-equiv=\"expires\" content=\"0\" />    \r\n"
							 +"<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\" />\r\n"
							 +"<meta http-equiv=\"description\" content=\"This is my page\" />\r\n"
							 +"<link rel=\"stylesheet\" type=\"text/css\" href=\"<%=path %>/css/backstage/skin.css\" />\r\n";
			String scriptsrc="";
			String scriptbegin="<script type=\"text/javascript\" language=\"javascript\">\r\n";
			String scriptneirong="  function gotopage(gototype,page){\r\n"
							 +"     var gotopagetype=document.getElementById(\"gotopagetype\");\r\n"
							 +"     var gotopageString=document.getElementById(\"gotopageString\");\r\n"
							 +"     var yeshu=document.getElementById(\"yeshu\").value;\r\n"
							 +"     if(gototype=='first'){\r\n"
							 +"       gotopagetype.value='first';\r\n"
							 +"     }\r\n"
							 +"     if(gototype=='last'){\r\n"
							 +"       gotopagetype.value='last';\r\n"
							 +"     }\r\n"
							 +"     if(gototype=='previous'){\r\n"
							 +"       gotopagetype.value='previous';\r\n"
							 +"     }\r\n"
							 +"     if(gototype=='next'){\r\n"
							 +"       gotopagetype.value='next';\r\n"
							 +"     }\r\n"
							 +"     if(gototype=='gotopage'){\r\n"
							 +"       gotopagetype.value='gotopage';\r\n"
							 +"       gotopageString.value='page';\r\n"
							 +"     }\r\n"
							 +"     if(gototype=='tiaozhuan'){\r\n"
							 +"       if(yeshu==''){\r\n"
							 +"         alert('请输入页数');\r\n"
							 +"         return;\r\n"
							 +"       }\r\n"
							 +"     else{\r\n"
							 +"       gotopagetype.value='gotopage';\r\n"
							 +"       gotopageString.value=yeshu;\r\n"
							 +"     }\r\n"
							 +"  }\r\n"
							 +"  document.forms[0].action='<%=path %>/"+class_name.toLowerCase()+".action?methode=list';\r\n"
							 +"  document.forms[0].submit();\r\n"
							 +"  }\r\n\r\n"
							 +"  function setall(v){\r\n"
							 +"    var f = document.forms[0];\r\n"
							 +"    for (i=0;i<f.elements.length;i++)\r\n"
							 +"    if (f.elements[i].name==\"checks\"){\r\n"
							 +"      f.elements[i].checked = v;\r\n"
							 +"    }\r\n"
							 +"    f.elements[\"clickall\"].checked = v;\r\n"
							 +"  }\r\n\r\n"
							 +"  function setdelete(){\r\n"
							 +"    if(confirm('确认删除选中的条目吗')){\r\n"
							 +"      var f = document.forms[0];\r\n"
							 +"      f.action=\"<%=path %>/"+class_name.toLowerCase()+".action?methode=delete\";\r\n"
							 +"      f.submit();\r\n"
							 +"    }\r\n"
							 +"  }\r\n\r\n"
							 +"   function deleteone(id){\r\n"
							 +"     if(confirm('确认删除选中的条目吗')){\r\n"
							 +"       var f = document.forms[0]; \r\n"
							 +"       f.action=\"<%=path %>/"+class_name.toLowerCase()+".action?methode=deletebyid&id=\"+id;\r\n"
							 +"       f.submit(); \r\n"
							 +"		 } \r\n"
							 +"    } \r\n\r\n"
							 +"    function ss(){ \r\n"
							 +"      var f = document.forms[0]; \r\n"
							 +"      f.action=\"<%=path %>/"+class_name.toLowerCase()+".action?methode=list\";\r\n"
							 +"      f.submit();\r\n"
							 +"    }\r\n\r\n";
			 String scriptend="  </script>\r\n";
			 String listjspstr="</head>\r\n\r\n"
							 +"    <body style=\"min-height:500px;\"> \r\n"
							 +"    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
							 +"      <tr>\r\n"
							 +"        <td width=\"17\" valign=\"top\" background=\"<%=path %>/images/backstage/mail_leftbg.gif\"><img src=\"<%=path %>/images/backstage/left-top-right.gif\" width=\"17\" height=\"29\" /></td>\r\n"
							 +"        <td valign=\"top\" background=\"<%=path %>/images/backstage/content-bg.gif\"><span class=\"auto1\"><span><b>"+class_description+"管理</b></span><i></i></span></td>\r\n"
							 +"        <td width=\"16\" valign=\"top\" background=\"<%=path %>/images/backstage/mail_rightbg.gif\"><img src=\"<%=path %>/images/backstage/nav-right-bg.gif\" width=\"16\" height=\"29\" /></td> \r\n"
							 +"      </tr> \r\n"
							 +"      <tr>\r\n"
							 +"        <td valign=\"middle\" background=\"<%=path %>/images/backstage/mail_leftbg.gif\">&nbsp;</td>\r\n"
							 +"        <td valign=\"top\" bgcolor=\"#F7F8F9\"> \r\n\r\n"
							 +"      <!-- ----------------------------------------主体内容开始---------------------------------------------------- -->\r\n"
							 +"      <form name=\"f\" action=\"\" method=\"post\">\r\n"
							 +"      <input type=\"hidden\" name=\"nowpageString\" id=\"nowpageString\" value=\"${pageParm.nowpage }\"/>\r\n\r\n"
							 +"      <input type=\"hidden\" name=\"gotopagetype\" id=\"gotopagetype\"/>\r\n"
							 +"      <input type=\"hidden\" name=\"gotopageString\" id=\"gotopageString\"/>\r\n"
							 +"      <table cellspacing=\"0\" width=\"100%\" style=\"border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB;\">\r\n"
							 +"      <tr>\r\n"
							 +"        <td width=\"20%\">&nbsp;<img src=\"<%=path %>/images/backstage/arrow.gif\" width=\"16\" height=\"22\" align=\"absmiddle\" />&nbsp;<a href=\"<%=path %>/"+class_name.toLowerCase()+".action?methode=toaddorupdate\" class=\"link3\">[新增"+class_description+"]</a> <a class=\"link3\" href=\"javascript:setdelete();\">[删除]</a></td>\r\n"
							 +"        <td  align=\"right\">\r\n"
							 +"          <select name=\"sname\" id=\"sname\">\r\n";
									 if(field_names!=null){
											for(int i=0;i<field_names.length;i++)
											{
										      listjspstr=listjspstr+"            <option value=\""+field_names[i]+"\" <c:if test=\"${sname eq '"+field_names[i]+"'}\">selected=\"selected\"</c:if>>"+field_descriptions[i]+"</option>\r\n";
										}
									}
		 
					 			listjspstr+="          </select>\r\n"
							 +"          <input type=\"text\" value=\"${svalue}\" name=\"svalue\" id=\"svalue\"/>\r\n"
							 +"          <input type=\"button\" value=\"搜索\" onclick=\"ss()\" class=\"sub\"/>\r\n"
							 +"        </td>\r\n"
							 +"        <td width=\"150px\" align=\"right\">共有 <b>${pageParm.total }</b> 条记录&nbsp;&nbsp;</td>\r\n"
							 +"      </tr>\r\n"
							 +"    </table>\r\n"
							 +"    <table id=\"listtable\" border=1 cellpadding=0 cellspacing=0 bordercolor=\"#dddddd\">\r\n"
							 +"      <tr>\r\n"
							 +"        <th width=\"3%\"><input type=\"checkbox\" onclick=\"setall(this.checked)\" name=\"clickall\" title=\"全选\" /></th>\r\n"
							 +"        <th width=\"40px\">序号</th>\r\n";
							 if(field_names!=null){
									for(int i=0;i<field_names.length;i++)
									{
								      listjspstr=listjspstr+"        <th width=\"120px;\">"+field_descriptions[i]+"</th>\r\n";
								}
							}
							 
							 listjspstr+="      <th width=\"100px\">操作</th>\r\n"
							 +"      </tr>\r\n"
							 +"      <c:forEach items=\"${list}\" var=\""+class_name.toLowerCase()+"\" varStatus=\"v\">\r\n"
							 +"      <tr class=\"hovertr\">\r\n"
							 +"        <td><input type='checkbox' name=\"checks\"  value=\"${"+class_name.toLowerCase()+".id }\" /></td>\r\n\r\n"
							 +"        <td>${v.count+(pageParm.nowpage-1)*(pageParm.pagesize)}</td>\r\n";
							 if(field_names!=null){
									for(int i=0;i<field_names.length;i++)
									{
										if("text_uploadpic".equals(biaodan_type[i])){
											listjspstr=listjspstr+"         <c:if test=\"${(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",'http://')==true)||(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",'https://')==true)}\">\r\n"
																 +"            <td class=\"bigtext showpic\" pic=\"${"+class_name.toLowerCase()+" }\">查看</td>\r\n"
																 +"         </c:if>\r\n"
																 +"         <c:if test=\"${(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",'http://')==false)&&(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",'https://')==false)}\">\r\n"
																 +"            <td class=\"bigtext showpic\" pic=\"<%=basePath %>/${"+class_name.toLowerCase()+"."+field_names[i]+" }\">查看</td>\r\n"
																 +"         </c:if>\r\n";
											ishavepic++;
										}
										else{
											listjspstr=listjspstr+"        <td>${"+class_name.toLowerCase()+"."+field_names[i]+" }</td>\r\n";
										}
								}
							}
							 if(ishavepic>0){
								 scriptsrc=scriptsrc+"<script type=\"text/javascript\" src=\"<%=basePath %>/js/common/jquery.js\"></script>\r\n"
								 					+"<script type=\"text/javascript\" src=\"<%=basePath %>/js/common/fun.js\"></script>\r\n"
								 					+"<script>\r\n"
								 					+" $(function(){\r\n"
								 					+"   $('.showpic').hover(function(){\r\n"
								 					+"     var pic=http_pic($(this).attr('pic'));\r\n"
								 					+"     $(this).css('position','relative');\r\n"
								 					+"     $(this).html('<img style=\"position:absolute;display:none;top:-50px;*top:0px; right:0px\" src=\"'+pic+'\" onload=\"imgAuto(this,300,300)\"/>' );\r\n"
								 					+"   },function(){\r\n"
								 					+"     $(this).css('position','static');\r\n"
								 					+"       $(this).html('查看');\r\n"
								 					+"   });\r\n"
								 					+" })\r\n\r\n"
								 					+"</script>\r\n";
							 }
							 listjspstr+="         <td>\r\n"
							 +"        &nbsp;<a href=\"<%=path %>/"+class_name.toLowerCase()+".action?methode=toaddorupdate&id=${"+class_name.toLowerCase()+".id }\" class=link4>修改</a>\r\n"
							 +"        &nbsp;<a href=\"javascript:deleteone('${"+class_name.toLowerCase()+".id }')\" class=link4>删除</a>\r\n"
							 +"       </td>\r\n"
							 +"      </tr>\r\n"
							 +"    </c:forEach> \r\n"
							 +"    <c:if test=\"${empty list}\">\r\n"
							 +"      <tr bgcolor=\"FFFFFF\"><td align=\"center\" height=\"50\" colspan=\"12\"><font style=\"size: 14px;color: black\">无结果集</font></td></tr>\r\n"
							 +"    </c:if>\r\n"
							 +"    <c:if test=\"${not empty list}\">\r\n"
							 +"      <tr bgcolor=\"EEEEEE\"><td colspan=\"20\" style=\"padding:10px;\"><div align=\"center\"><a id=\"LtotalSY\" href=\"javascript:gotopage('first','0')\" class=\"right-font08\">首页</a>&nbsp;&nbsp;<a id=\"LtotalSYY\" href=\"javascript:gotopage('previous','0')\" class=\"right-font08\">上一页</a>&nbsp;&nbsp;<a id=\"LtotalXYY\" href=\"javascript:gotopage('next','0')\" class=\"right-font08\">下一页</a>&nbsp;&nbsp;<a id=\"LtotalMY\" href=\"javascript:gotopage('last','0')\" class=\"right-font08\">末页</a>&nbsp;&nbsp;<input type=\"text\" name=\"yeshu\" id=\"yeshu\" maxlength=\"5\" style=\"width: 45px;\" value=\"${pageParm.nowpage }\" onkeyup=\"value=value.replace(/[^\\d]/g,'') \"  onbeforepaste=\"clipboardData.setData\" />页&nbsp;&nbsp;<input type=\"button\" class=\"button\" style=\"width: 35px;\" value=\"GO\" onclick=\"gotopage('tiaozhuan','0')\"></div>\r\n"
							 +"        <div align=\"center\">\r\n"
							 +"          <p>共${pageParm.total }条 ${pageParm.nowpage }/${pageParm.totalpage }页</p>\r\n"
							 +"       </div></td></tr>\r\n"
							 +"      </c:if>\r\n"
							 +"    </table>\r\n"
							 +"   </form>\r\n"
							 +" <!--------------------------------------------------- 主体内容结束--------------------------------------------------- -->\r\n"
							 +"      <td background=\"<%=path %>/images/backstage/mail_rightbg.gif\">&nbsp;</td>\r\n"
							 +"    </tr>\r\n"
							 +"   </table> \r\n"
							 +" </body> \r\n"
							 +"</html>\r\n";
			String allstr=listjspstrstart+scriptsrc+scriptbegin+scriptneirong+scriptend+listjspstr;
			bw.write(allstr);
			bw.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createaddorupdatejsp
	 * @Description 生成添加jsp,把ansi格式转化成utf-8
	 * @param request
	 * @param class_name
	 * @param packageRoute
	 * @return void
	 */	
	private void createaddorupdatejsp(HttpServletRequest request,String class_name,String packageRoute,String class_description,String jsppath,String[] field_names,String[] field_types,String[] field_descriptions,String sortmethode,String isgl,String[] gl_fieldname,String[] gl_class,String[] gl_field_description,String[] biaodan_type,String[] biaodan_nullable)
	{
		String separ = File.separator;
		try {
			String path=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"WebRoot"+separ+jsppath+separ+"Addorupdate"+class_name.toLowerCase()+".jsp";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
			BufferedWriter bw = new BufferedWriter(out);  
			String addjspstrstart="<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"utf-8\"%>\r\n" 
							 +"<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>\r\n"
							 +"<%@ taglib prefix=\"fn\" uri=\"http://java.sun.com/jsp/jstl/functions\" %>\r\n"
							 +"<%\r\n"
							 +"String path = request.getContextPath();\r\n"
							 +"String basePath = request.getScheme()+\"://\"+request.getServerName()+\":\"+request.getServerPort()+path+\"/\";\r\n"
							 +"%>\r\n\r\n"
							 +"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n"
							 +"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
							 +"<head>\r\n"
							 +"<base href=\"<%=basePath%>\">\r\n"
							 +"<title>"+class_description+"管理</title>\r\n"
							 +"<meta http-equiv=\"pragma\" content=\"no-cache\" />\r\n"
							 +"<meta http-equiv=\"cache-control\" content=\"no-cache\" />\r\n"
							 +"<meta http-equiv=\"expires\" content=\"0\" />    \r\n"
							 +"<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\" />\r\n"
							 +"<meta http-equiv=\"description\" content=\"This is my page\" />\r\n";
			String addlinksrc="<link rel=\"stylesheet\" type=\"text/css\" href=\"<%=path %>/css/backstage/skin.css\" />\r\n";
			String addscriptsrc="";
			String addscriptstrstart="  <script type=\"text/javascript\">\r\n";
			String addscriptstr="";
			String addscripttjstart="    function tj(){\r\n";
			String addscripttjdingyi="";
			String addscripttjpanduan="";
			String addscripttjend="      document.forms[0].submit();\r\n"
							 +"    }\r\n";
			String addscriptstrend="  </script>\r\n";
			String addjspstr="</head>\r\n"
			 				 +"<body style=\"min-height:500px;\">\r\n"
							 +"  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n"	
							 +"    <tr>\r\n"	
							 +"      <td width=\"17\" valign=\"top\" background=\"<%=path %>/images/backstage/mail_leftbg.gif\"><img src=\"<%=path %>/images/backstage/left-top-right.gif\" width=\"17\" height=\"29\" /></td>\r\n"	
							 +"      <td valign=\"top\" background=\"<%=path %>/images/backstage/content-bg.gif\"><span class=\"auto1\"><span><b>"+class_description+"管理</b></span><i></i></span></td>\r\n"
							 +"      <td width=\"16\" valign=\"top\" background=\"<%=path %>/images/backstage/mail_rightbg.gif\"><img src=\"<%=path %>/images/backstage/nav-right-bg.gif\" width=\"16\" height=\"29\" /></td>\r\n"
							 +"    </tr>\r\n"
							 +"    <tr>\r\n"	
							 +"      <td valign=\"middle\" background=\"<%=path %>/images/backstage/mail_leftbg.gif\">&nbsp;</td>\r\n"	
							 +"      <td valign=\"top\" bgcolor=\"#F7F8F9\">\r\n\r\n"	
							 +"    <form action=\"<%=path %>/"+class_name.toLowerCase()+".action?methode=addorupdate\" method=\"post\" name=\"form1\">\r\n"	
							 +"    <input type=\"hidden\" name=\"id\" value=\"${"+class_name.toLowerCase()+".id }\" />\r\n"	
							 +"    <input type=\"hidden\" name=\"createdate\" value=\"${"+class_name.toLowerCase()+".createdate }\" />\r\n"	
							 +"    <input type=\"hidden\" name=\"createuser\" value=\"${"+class_name.toLowerCase()+".createuser }\" />\r\n"	
							 +"    <input type=\"hidden\" name=\"isupdate\" value=\"${isupdate }\" />\r\n"	
							 +"    <table id=\"addeditable\" border=1 cellpadding=0 cellspacing=0 bordercolor=\"#dddddd\">\r\n"	;
							 if(field_names!=null){
								 if("1".equals(isgl)&&gl_class!=null&&gl_fieldname!=null&&gl_field_description!=null&&(gl_class.length==gl_field_description.length)&&(gl_class.length==gl_fieldname.length)){
									 int ishavejqsrc=0;
									 int ishavetext_data=0;
									 int ishavetext_email=0;
									 int ishavetext_phone=0;
									 int ishavepic=0;
									for(int i=0;i<field_names.length;i++){
										int ishavegl=0;
										for(int j=0;j<gl_class.length;j++){
											if(field_names[i].equals(gl_fieldname[j])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+gl_field_description[j]+"：</td>\r\n"	
											      +"        <td>&nbsp;<select id=\""+field_names[i]+"\" name=\""+field_names[i]+"\" style=\"width:130px;\">\r\n"
											      +"        <c:forEach items=\"${"+gl_class[j].toLowerCase()+"list}\" var=\""+gl_class[j].toLowerCase()+"\">\r\n"	
											      +"          <option value=\"${"+gl_class[j].toLowerCase()+".id }\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq "+gl_class[j].toLowerCase()+".id}\">selected=\"selected\"</c:if>>${"+gl_class[j].toLowerCase()+".id }（替换id值）</option>\r\n"	
											      +"        </c:forEach>\r\n"	
											      +"        </select>\r\n"	
											      +"        </td>\r\n"	
												  +"      </tr>\r\n";	
												ishavegl++;
											}
										}
										if(ishavegl==0){
											if("text".equals(biaodan_type[i])){
												if("Integer".equals(field_types[i])||"Long".equals(field_types[i])){
													addjspstr=addjspstr+"      <tr>\r\n"
												      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
												      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" onkeyup=\"value=value.replace(/[^\\d]/g,'')\"/></td>\r\n"	
													  +"      </tr>\r\n";	
												}
												else if("Double".equals(field_types[i])){
													addjspstr=addjspstr+"      <tr>\r\n"
												      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
												      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" onkeyup=\"value=value.replace(/[^\\d\\.]/g,'')\"/></td>\r\n"	
													  +"      </tr>\r\n";	
												}
												else{
													addjspstr=addjspstr+"      <tr>\r\n"
												      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
												      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" /></td>\r\n"	
													  +"      </tr>\r\n";	
												}
											}
											else if("text_data".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" onclick=\"lhgcalendar();\" style=\"width:182px;\"/></td>\r\n"	
												  +"      </tr>\r\n";
												addscriptstr=addscriptstr+"      $(function(){\r\n" 
														+"          $('#"+field_names[i]+"').calendar({ format:'yyyy-MM-dd HH:mm:ss',btnBar:true});\r\n"
														+"      });\r\n\r\n";
												ishavetext_data++;
												ishavejqsrc++;
											}
											else if("text_email".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\"  style=\"width:182px;\"/></td>\r\n"	
												  +"      </tr>\r\n";
												addscripttjdingyi=addscripttjdingyi+"      var "+field_names[i]+"=document.getElementById(\""+field_names[i]+"\");\r\n";
												if("1".equals(biaodan_nullable[i])){
													addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value==''){\r\n"
																						 +"          alert('请输入"+field_descriptions[i]+"');\r\n"
																						 +"          return;\r\n"
													 									 +"       }\r\n";
													addscripttjpanduan=addscripttjpanduan+"      if(isEmail("+field_names[i]+".value)==false){\r\n"
													 									 +"          alert('邮箱格式错误');\r\n"
													 									 +"          return;\r\n"
													 									 +"      }\r\n";
												}
												else{
													addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value!=''){\r\n"
																						 +"          if(isEmail("+field_names[i]+".value)==false){\r\n"
																						 +"            alert('邮箱格式错误');\r\n"
																						 +"            return;\r\n"
																						 +"        }\r\n"
																						 +"     }\r\n";
												}
												ishavetext_email++;
											}
											else if("text_phone".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\"  style=\"width:182px;\"/></td>\r\n"	
												  +"      </tr>\r\n";
												addscripttjdingyi=addscripttjdingyi+"      var "+field_names[i]+"=document.getElementById(\""+field_names[i]+"\");\r\n";
												if("1".equals(biaodan_nullable[i])){
													addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value==''){\r\n"
													 +"          alert('请输入"+field_descriptions[i]+"');\r\n"
													 +"          return;\r\n"
													 +"      }\r\n";
													addscripttjpanduan=addscripttjpanduan+"      if(checkMobile("+field_names[i]+".value)==false&&checkPhone("+field_names[i]+".value)==false){\r\n"
													 +"          alert('号码格式错误');\r\n"
													 +"          return;\r\n"
													 +"      	}\r\n"
													 +"      }\r\n";
												}
												else{
													addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value!=''){\r\n"
																						 +"          if(checkMobile("+field_names[i]+".value)==false&&checkPhone("+field_names[i]+".value)==false){\r\n"
																						 +"            alert('号码格式错误');\r\n"
																						 +"            return;\r\n"
																						 +"        }\r\n"
																						 +"      }\r\n"
																						 +"    }\r\n";
												}
												ishavetext_phone++;
											}
											else if("select".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;\r\n"
											      +"            <select id=\""+field_names[i]+"\" name=\""+field_names[i]+"\">\r\n"
											      +"                <option value=\"自定义1\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义1' || empty "+class_name.toLowerCase()+"."+field_names[i]+"}\">selected=\"selected\"</c:if>>自定义1</option>\r\n"
											      +"                <option value=\"自定义2\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义2'}\">selected=\"selected\"</c:if>>自定义2</option>\r\n"
											      +"            </select>\r\n"
												  +"        </td>\r\n"
												  +"      </tr>\r\n";
											}
											else if("checkbox".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;\r\n"
											      +"          <input type=\"checkbox\" name=\""+field_names[i]+"\" value=\"自定义1\" <c:if test=\"${(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",',自定义1')==true)||(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",', 自定义1')==true)}\">checked=\"checked\"</c:if> />自定义1&nbsp;&nbsp;\r\n"
											      +"          <input type=\"checkbox\" name=\""+field_names[i]+"\" value=\"自定义2\" <c:if test=\"${(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",',自定义2')==true)||(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",', 自定义2')==true)}\">checked=\"checked\"</c:if> />自定义2&nbsp;&nbsp;\r\n"
											      +"        </td>\r\n"
												  +"      </tr>\r\n";
											}
											else if("radio".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
												  +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;\r\n"
											      +"          <input type=\"radio\" name=\""+field_names[i]+"\" value=\"自定义1\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义1' || empty "+class_name.toLowerCase()+"."+field_names[i]+"}\">checked=\"checked\"</c:if> />自定义1&nbsp;&nbsp;\r\n"
											      +"          <input type=\"radio\" name=\""+field_names[i]+"\" value=\"自定义2\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义2'}\">checked=\"checked\"</c:if> />自定义2&nbsp;&nbsp;\r\n"
											      +"        </td>\r\n"
												  +"      </tr>\r\n";
											}
											else if("textarea".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;\r\n"
											      +"           <textarea name=\""+field_names[i]+"\" id=\""+field_names[i]+"\" style=\"margin: 5px;\">${"+class_name.toLowerCase()+"."+field_names[i]+"}</textarea>\r\n"	
											      +"        </td>\r\n"
												  +"      </tr>\r\n";
											}
											else if("text_uploadpic".equals(biaodan_type[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;\r\n"
											      +"           <input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+"}\" style=\"width:300px;\"/><input class=\"sub\" type=\"button\" value=\"上传图片\" onclick=\"javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-"+class_name.toLowerCase()+"&textname="+field_names[i]+"','upload','550','450')\" /> 可直接添加网络地址\r\n"	
											      +"        </td>\r\n"
												  +"      </tr>\r\n";
												ishavepic++;
											}
											
										}
										if("1".equals(biaodan_nullable[i])&&!"text_email".equals(biaodan_type[i])&&!"text_phone".equals(biaodan_type[i])&&!"checkbox".equals(biaodan_type[i])&&!"radio".equals(biaodan_type[i])){
											addscripttjdingyi=addscripttjdingyi+"      var "+field_names[i]+"=document.getElementById(\""+field_names[i]+"\");\r\n";
											addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value==''){\r\n"
																				 +"          alert('请输入"+field_descriptions[i]+"');\r\n"
																				 +"          return;\r\n"
																				 +"      }\r\n";
										}
									}
									if(ishavejqsrc>0){
										addscriptsrc=addscriptsrc+"<script type=\"text/javascript\" src=\"<%=path %>/js/common/jquery.js\"></script>\r\n";
									}
									if(ishavetext_data>0){
										addlinksrc=addlinksrc+"<link rel=\"stylesheet\" type=\"text/css\" href=\"<%=path %>/css/common/lhgcalendar.css\" />\r\n";
										addscriptsrc=addscriptsrc+"<script type=\"text/javascript\" src=\"<%=path %>/js/common/lhgcalendar.js\"></script>\r\n";
									}
									if(ishavetext_email>0||ishavetext_phone>0){
										addscriptsrc=addscriptsrc+"<script type=\"text/javascript\" src=\"<%=path %>/js/common/yanzheng.js\"></script>\r\n";
									}
									if(ishavepic>0){
										addscriptstr=addscriptstr+"      function openpic(url,name,w,h){\r\n" 
																 +"          window.open(url,name,\"top=100,left=400,width=\" + w + \",height=\" + h + \",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no\"); \r\n"
																 +"      }\r\n\r\n";
									}
								 }
								 else{
									 //暂时想不出解决重复代码的判断，考虑是否不要设置外键字段，感觉没多大用
									 int ishavejqsrc=0;
									 int ishavetext_data=0;
									 int ishavetext_email=0;
									 int ishavetext_phone=0;
									 int ishavepic=0;
									for(int i=0;i<field_names.length;i++){
										if("text".equals(biaodan_type[i])){
											if("Integer".equals(field_types[i])||"Long".equals(field_types[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" onkeyup=\"value=value.replace(/[^\\d]/g,'')\"/></td>\r\n"	
												  +"      </tr>\r\n";	
											}
											else if("Double".equals(field_types[i])){
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" onkeyup=\"value=value.replace(/[^\\d\\.]/g,'')\"/></td>\r\n"	
												  +"      </tr>\r\n";	
											}
											else{
												addjspstr=addjspstr+"      <tr>\r\n"
											      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
											      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" /></td>\r\n"	
												  +"      </tr>\r\n";	
											}
										}
										else if("text_data".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
										      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\" onclick=\"lhgcalendar();\" style=\"width:182px;\"/></td>\r\n"	
											  +"      </tr>\r\n";
											addscriptstr=addscriptstr+"      $(function(){\r\n" 
													+"          $('#"+field_names[i]+"').calendar({ format:'yyyy-MM-dd HH:mm:ss',btnBar:true});\r\n"
													+"      });\r\n\r\n";
											ishavetext_data++;
											ishavejqsrc++;
										}
										else if("text_email".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
										      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\"  style=\"width:182px;\"/></td>\r\n"	
											  +"      </tr>\r\n";
											addscripttjdingyi=addscripttjdingyi+"      var "+field_names[i]+"=document.getElementById(\""+field_names[i]+"\");\r\n";
											if("1".equals(biaodan_nullable[i])){
												addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value==''){\r\n"
												 +"          alert('请输入"+field_descriptions[i]+"');\r\n"
												 +"          return;\r\n"
												 +"      }\r\n";
												addscripttjpanduan=addscripttjpanduan+"      if(isEmail("+field_names[i]+".value)==false){\r\n"
												 +"          alert('邮箱格式错误');\r\n"
												 +"          return;\r\n"
												 +"      }\r\n";
											}
											else{
												addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value!=''){\r\n"
																					 +"          if(isEmail("+field_names[i]+".value)==false){\r\n"
																					 +"            alert('邮箱格式错误');\r\n"
																					 +"            return;\r\n"
																					 +"      }\r\n";
											}
											ishavetext_email++;
										}
										else if("text_phone".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
										      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;<input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+" }\"  style=\"width:182px;\"/></td>\r\n"	
											  +"      </tr>\r\n";
											addscripttjdingyi=addscripttjdingyi+"      var "+field_names[i]+"=document.getElementById(\""+field_names[i]+"\");\r\n";
											if("1".equals(biaodan_nullable[i])){
												addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value==''){\r\n"
												 +"          alert('请输入"+field_descriptions[i]+"');\r\n"
												 +"          return;\r\n"
												 +"      }\r\n";
												addscripttjpanduan=addscripttjpanduan+"      if(checkMobile("+field_names[i]+".value)==false&&checkPhone("+field_names[i]+".value)==false){\r\n"
												 +"          alert('号码格式错误');\r\n"
												 +"          return;\r\n"
												 +"        }\r\n"
												 +"      }\r\n";
											}
											else{
												addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value!=''){\r\n"
																					 +"          if(checkMobile("+field_names[i]+".value)==false&&checkPhone("+field_names[i]+".value)==false){\r\n"
																					 +"            alert('号码格式错误');\r\n"
																					 +"            return;\r\n"
																					 +"        }\r\n"
																					 +"      }\r\n";
											}
											ishavetext_phone++;
										}
										else if("select".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
										      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;\r\n"
										      +"            <select id=\""+field_names[i]+"\" name=\""+field_names[i]+"\">\r\n"
										      +"                <option value=\"自定义1\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义1' || empty "+class_name.toLowerCase()+"."+field_names[i]+"}\">selected=\"selected\"</c:if>>自定义1</option>\r\n"
										      +"                <option value=\"自定义2\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义2'}\">selected=\"selected\"</c:if>>自定义2</option>\r\n"
										      +"            </select>\r\n"
											  +"        </td>\r\n"
											  +"      </tr>\r\n";
										}
										else if("checkbox".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
										      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;\r\n"
										      +"          <input type=\"checkbox\" name=\""+field_names[i]+"\" value=\"自定义1\" <c:if test=\"${(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",',自定义1')==true)||(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",', 自定义1')==true)}\">checked=\"checked\"</c:if> />自定义1&nbsp;&nbsp;\r\n"
										      +"          <input type=\"checkbox\" name=\""+field_names[i]+"\" value=\"自定义2\" <c:if test=\"${(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",',自定义2')==true)||(fn:contains("+class_name.toLowerCase()+"."+field_names[i]+",', 自定义2')==true)}\">checked=\"checked\"</c:if> />自定义2&nbsp;&nbsp;\r\n"
										      +"        </td>\r\n"
											  +"      </tr>\r\n";
										}
										else if("radio".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
											  +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;\r\n"
										      +"          <input type=\"radio\" name=\""+field_names[i]+"\" value=\"自定义1\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义1' || empty "+class_name.toLowerCase()+"."+field_names[i]+"}\">checked=\"checked\"</c:if> />自定义1&nbsp;&nbsp;\r\n"
										      +"          <input type=\"radio\" name=\""+field_names[i]+"\" value=\"自定义2\" <c:if test=\"${"+class_name.toLowerCase()+"."+field_names[i]+" eq '自定义2'}\">checked=\"checked\"</c:if> />自定义2&nbsp;&nbsp;\r\n"
										      +"        </td>\r\n"
											  +"      </tr>\r\n";
										}
										else if("textarea".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
										      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;\r\n"
										      +"           <textarea name=\""+field_names[i]+"\" id=\""+field_names[i]+"\" style=\"margin: 5px;\">${"+class_name.toLowerCase()+"."+field_names[i]+"}</textarea>\r\n"	
										      +"        </td>\r\n"
											  +"      </tr>\r\n";
										}
										else if("text_uploadpic".equals(biaodan_type[i])){
											addjspstr=addjspstr+"      <tr>\r\n"
										      +"        <td width=\"115px\" align=\"right\">"+field_descriptions[i]+"：</td>\r\n"	
										      +"        <td>&nbsp;\r\n"
										      +"           <input name=\""+field_names[i]+"\" type=\"text\" id=\""+field_names[i]+"\" value=\"${"+class_name.toLowerCase()+"."+field_names[i]+"}\" style=\"width:300px;\"/><input class=\"sub\" type=\"button\" value=\"上传图片\" onclick=\"javascript:openpic('<%=path %>/dbc_fun.action?methode=touploadpic&picpath=upload-images-"+class_name.toLowerCase()+"&textname="+field_names[i]+"','upload','550','450')\" /> 可直接添加网络地址\r\n"	
										      +"        </td>\r\n"
											  +"      </tr>\r\n";
											ishavepic++;
										}
										if("1".equals(biaodan_nullable[i])&&!"text_email".equals(biaodan_type[i])&&!"text_phone".equals(biaodan_type[i])&&!"checkbox".equals(biaodan_type[i])&&!"radio".equals(biaodan_type[i])){
											addscripttjdingyi=addscripttjdingyi+"      var "+field_names[i]+"=document.getElementById(\""+field_names[i]+"\");\r\n";
											addscripttjpanduan=addscripttjpanduan+"      if("+field_names[i]+".value==''){\r\n"
																				 +"          alert('请输入"+field_descriptions[i]+"');\r\n"
																				 +"          return;\r\n"
																				 +"      }\r\n";
										}
									}
									if(ishavejqsrc>0){
										addscriptsrc=addscriptsrc+"<script type=\"text/javascript\" src=\"<%=path %>/js/common/jquery.js\"></script>\r\n";
									}
									if(ishavetext_data>0){
										addlinksrc=addlinksrc+"<link rel=\"stylesheet\" type=\"text/css\" href=\"<%=path %>/css/common/lhgcalendar.css\" />\r\n";
										addscriptsrc=addscriptsrc+"<script type=\"text/javascript\" src=\"<%=path %>/js/common/lhgcalendar.js\"></script>\r\n";
									}
									if(ishavetext_email>0||ishavetext_phone>0){
										addscriptsrc=addscriptsrc+"<script type=\"text/javascript\" src=\"<%=path %>/js/common/yanzheng.js\"></script>\r\n";
									}
									if(ishavepic>0){
										addscriptstr=addscriptstr+"      function openpic(url,name,w,h){\r\n" 
																 +"          window.open(url,name,\"top=100,left=400,width=\" + w + \",height=\" + h + \",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no\"); \r\n"
																 +"      }\r\n\r\n";
									}
								 }
		
							}
							 addjspstr+="      <tr>\r\n"	
							 +"        <td align=\"right\">排序：</td>\r\n"	
							 +"        <td>&nbsp;<input name=\"sortcode\" type=\"text\" id=\"sortcode\" value=\"${"+class_name.toLowerCase()+".sortcode }\"  onkeyup=\"value=value.replace(/[^\\d\\.]/g,'')\"/> 数字越大越靠前</td>\r\n"	
							 +"      </tr>\r\n"	
							 +"      <tr>\r\n"	
							 +"        <td align=\"right\">&nbsp;</td>\r\n"	
							 +"        <td>&nbsp;<input type=\"button\" class=\"sub\" name=\"sub\" value=\" 保 存 \" onclick=\"tj()\"/></td>\r\n"	
							 +"      </tr>\r\n"	
							 +"    </table>\r\n"	
							 +"    </form></td>\r\n\r\n\r\n"
							 +"    <td background=\"<%=path %>/images/backstage/mail_rightbg.gif\">&nbsp;</td>\r\n"
							 +"  </tr>\r\n"
							 +"  <tr>\r\n"
							 +"    <td valign=\"bottom\" background=\"<%=path %>/images/backstage/mail_leftbg.gif\"><img src=\"<%=path %>/images/backstage/buttom_left2.gif\" width=\"17\" height=\"17\" /></td>\r\n"
							 +"    <td background=\"<%=path %>/images/backstage/buttom_bgs.gif\"><img src=\"<%=path %>/images/backstage/buttom_bgs.gif\" width=\"17\" height=\"17\" /></td>\r\n"
							 +"    <td valign=\"bottom\" background=\"<%=path %>/images/backstage/mail_rightbg.gif\"><img src=\"<%=path %>/images/backstage/buttom_right2.gif\" width=\"16\" height=\"17\" /></td>\r\n"
							 +"  </tr>\r\n"
							 +" </table>\r\n"
							 +" </body>\r\n"
							 +"</html>\r\n";
			String allstr=addjspstrstart+addlinksrc+addscriptsrc+addscriptstrstart+addscriptstr+addscripttjstart+addscripttjdingyi+addscripttjpanduan+addscripttjend+addscriptstrend+addjspstr;
			bw.write(allstr);
			bw.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title createMANIFEST
	 * @Description 生成项目MANIFEST.MF文件
	 * @param path
	 * @param author
  	 * @param edition
	 * @return void
	 */	
	private void createMANIFEST(String path,String author,String edition) {
		String separ = File.separator;
		try {
				FileWriter fw=new FileWriter(path+separ+"MANIFEST.MF");
				BufferedWriter bw = new BufferedWriter(fw);  
				String MANIFEST="Manifest-Version: "+edition+"\r\n"
				+"Class-Path: \r\n"
				+"Created-By:"+author+"\r\n";
				bw.write(MANIFEST);
				bw.close();
				fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title listCreate
	 * @Description 读取createcode文件夹下的文件  
	 * @return String
	 * @throws
	 */	
	public String listCreate() {
		try{
			String separ = File.separator;
			List list=new ArrayList();
			String filePath = request.getSession().getServletContext().getRealPath(separ)+"createcode";
			File dir = new File(filePath);
			if(!dir.isDirectory()){
				System.out.println(filePath + " -- 这不是一个文件夹");
				}else{
				File[] fileList = dir.listFiles();
				for(int i = 0;i < fileList.length;i++){
					if(fileList[i].isFile()){
						String fname=fileList[i].getName();
						if("zip".equals(fname.substring(fname.lastIndexOf(".")+1)));
						{
							list.add(fname.subSequence(0, fname.lastIndexOf(".")));
							System.out.println(fileList[i].getName());
						}
						}
				}
				/*InputStream in = new ClassPathResource("common_config.properties").getInputStream();
	            Properties property = new Properties();
	            property.load(in);*/
		        request.setAttribute("workpath_java", Dbc_common_config.getvalue("createcode_copypath_java"));
		        request.setAttribute("workpath_jsp", Dbc_common_config.getvalue("createcode_copypath_jsp"));
//	            String workpath_java =property.getProperty("createcode_copypath_java");
//	            String workpath_jsp =property.getProperty("createcode_copypath_jsp");
//	            request.setAttribute("workpath_java", workpath_java);
//	            request.setAttribute("workpath_jsp", workpath_jsp);
				request.setAttribute("listzip", list);
				}
				return "listcreatecode";	
			}catch (Exception e) {
			   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	           String ipaddress=Dbcutil.getIpByrequest(request);
	           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_createcode", "listCreate", "出现异常："+e.getMessage());
	           request.setAttribute("action","dbc_createcode");
	           request.setAttribute("methode","listCreate");
	           request.setAttribute("e", e.getMessage());
	           e.printStackTrace();
	           return "Exception";
		}
		
	}
	
	/**
	 * @Title showCreate
	 * @Description 查看生成信息
	 * @return String
	 * @throws
	 */	
	public String showCreate(){
	    String class_name=request.getParameter("class_name");
	    request.setAttribute("class_name", class_name);
	    return "showcreatecode";	
	}
	
	/**
	 * @Title tocopytoworkspace
	 * @Description 拷贝代码到项目中,先判断项目中是否已经存在
	 * @return String
	 * @throws
	 */	
	public String tocopytoworkspace(){
		try{
			String separ = File.separator;
			String class_name=request.getParameter("class_name");
	        String workpath_java =Dbc_common_config.getvalue("createcode_copypath_java");
            workpath_java=workpath_java.replace("/", separ);
            String havefile="";
			String filePath_java = workpath_java+separ+"pojo"+separ+class_name+".java";
			File file_java = new File(filePath_java);
			if (file_java.exists()) {
				havefile=havefile+class_name+".java";
			}
			String filePath_hbm = workpath_java+separ+"pojo"+separ+"mappings"+class_name+".hbm.xml";
			File file_hbm = new File(filePath_hbm);
			if (file_hbm.exists()) {
				havefile=havefile+"，"+class_name+".hbm.xml";
			}
			String filePath_dao = workpath_java+separ+"dao"+separ+class_name+"Dao.java";
			File file_dao = new File(filePath_dao);
			if (file_dao.exists()) {
				havefile=havefile+"，"+class_name+"Dao.java";
			}
			String filePath_daoimpl = workpath_java+separ+"dao"+separ+"Impl"+separ+class_name+"DaoImpl.java";
			File file_daoimpl = new File(filePath_daoimpl);
			if (file_daoimpl.exists()) {
				havefile=havefile+"，"+class_name+"DaoImpl.java";
			}
			String filePath_service = workpath_java+separ+"service"+separ+class_name+"Service.java";
			File file_service = new File(filePath_service);
			if (file_service.exists()) {
				havefile=havefile+"，"+class_name+"Service.java";
			}
			String filePath_serviceimpl = workpath_java+separ+"Service"+separ+"Impl"+separ+class_name+"ServiceImpl.java";
			File file_serviceimpl = new File(filePath_serviceimpl);
			if (file_serviceimpl.exists()) {
				havefile=havefile+"，"+class_name+"ServiceImpl.java";
			}
			String filePath_action = workpath_java+separ+"action"+separ+class_name+"Action.java";
			File file_action = new File(filePath_action);
			if (file_action.exists()) {
				havefile=havefile+"，"+class_name+"Action.java";
			}
			String filePath_struts = workpath_java+separ+"struts"+separ+"struts-"+class_name+".xml";
			File file_struts = new File(filePath_struts);
			if (file_struts.exists()) {
				havefile=havefile+"，struts-"+class_name+".xml";
			}
			String filePath_spring = workpath_java+separ+"spring"+separ+"spring-"+class_name+".xml";
			File file_spring = new File(filePath_spring);
			if (file_spring.exists()) {
				havefile=havefile+"，spring"+class_name+".xml";
			}
			if("".equals(havefile)){
				return this.copytoworkspace();
			}
			else{
				request.setAttribute("havefile", havefile);
				request.setAttribute("class_name", class_name);
				return "copysuccess";
			}
		}catch (Exception e) {
			// TODO: handle exception
		   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_createcode", "tocopytoworkspace", "出现异常："+e.getMessage());
           request.setAttribute("action","dbc_createcode");
           request.setAttribute("methode","tocopytoworkspace");
           request.setAttribute("e", e.getMessage());
           e.printStackTrace();
           return "Exception";
		}
	}
	
	/**
	 * @Title copytoworkspace
	 * @Description 拷贝代码到项目中
	 * @return String
	 * @throws
	 */	
	public String copytoworkspace(){
		try{
			String separ = File.separator;
			String class_name=request.getParameter("class_name");
		   /* InputStream in = new ClassPathResource("common_config.properties").getInputStream();
	        Properties property = new Properties();
	        property.load(in);
	        String workpath_java =property.getProperty("createcode_copypath_java");
            String workpath_jsp =property.getProperty("createcode_copypath_jsp");*/
			String workpath_java =Dbc_common_config.getvalue("createcode_copypath_java");
            String workpath_jsp =Dbc_common_config.getvalue("createcode_copypath_jsp");
            workpath_java=workpath_java.replace("/", separ);
            workpath_jsp=workpath_jsp.replace("/", separ);
		    FileUtil c=new FileUtil();
		    String source_java=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"src";
		    String source_jsp=request.getSession().getServletContext().getRealPath(separ)+"createcode"+separ+class_name+separ+"WebRoot";
		    c.copyDirectiory(source_java, workpath_java);
		    c.copyDirectiory(source_jsp, workpath_jsp);
		    request.setAttribute("class_name", class_name);
		    return "copysuccess";	
		}catch (Exception e) {
		   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_createcode", "copytoworkspace", "出现异常："+e.getMessage());
           request.setAttribute("action","dbc_createcode");
           request.setAttribute("methode","copytoworkspace");
           request.setAttribute("e", e.getMessage());
           e.printStackTrace();
           return "Exception";
		}
	    
	    
	}
	
	/**
	 * @Title deleteCreate
	 * @Description 删除生成的代码  
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return string
	 * @throws
	 */	
	public String deleteCreate() {
			String separ = File.separator;
		    String class_name=request.getParameter("class_name");
		    String filePath=request.getSession().getServletContext().getRealPath(separ)+"createcode";
		    File dir = new File(filePath+separ+class_name+".zip");
		    dir.delete();
		    FileUtil c=new FileUtil();
		    c.delFolder(filePath+separ+class_name);
		    return "redirect-listcreatecode";
	}
	
	private static void printFileList(File[] fileList) {
		for(int i = 0;i < fileList.length;i++){
		if(fileList[i].isFile()){
		System.out.println("文件名字.文件类型: " + fileList[i].getAbsolutePath() +"----"+ fileList[i].getName());
		System.out.println("文件大小: " + fileList[i].length() + " 字节");
		}
		else if(fileList[i].isDirectory()){
		File[] newFileList = fileList[i].listFiles();
		printFileList(newFileList);
		} 
	  }
   }
	
	

    public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
	}

}
