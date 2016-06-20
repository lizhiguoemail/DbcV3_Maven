package com.dbc.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_adService;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_mysqlmanagerService;
import com.dbc.util.Dbc_systeminfo;
import com.dbc.util.Dbcutil;
import com.dbc.util.MyApplicationContextUtil;
import com.dbc.util.MysqlManager;

/**
 * @Project 源自 dbc
 * @Title Dbc_utilAction.java
 * @Package com.dbc.action;
 * @Description  工具类action
 * @author caihuajun
 * @date 2016-06-25
 * @version v2.0
 */


/**
 * @ClassName Dbc_utilAction
 * @Description 工具类action
 * @author caihuajun
 * @date 2015-06-25
 */
public class Dbc_utilAction extends BaseAction{
	
	private String methode;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String tablename;
	
	public String execute() throws Exception {
		String returnstr="";
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		if("tomanagerdatabase".equals(methode)){
			returnstr=this.tomanagerdatabase();
		}
		else if("showcolumns".equals(methode)){
			returnstr=this.showcolumns();
		}
		else if("showindex".equals(methode)){
			returnstr=this.showindex();
		}
		else if("addindex".equals(methode)){
			returnstr=this.addindex();
		}
		else if("backup_mysql".equals(methode)){
			returnstr=this.backup_mysql();
		}
		else if("restore_mysql".equals(methode)){
			returnstr=this.restore_mysql();
		}
		else if("showsystem".equals(methode)){
			returnstr=this.showsystem();
		}
		return returnstr;
	}
	
	/**
	 * @Title tomanagerdatabase
	 * @Description 进入mysql管理页面 
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String tomanagerdatabase() {
		try{
			Dbc_mysqlmanagerService dbc_mysqlmanagerservice=(Dbc_mysqlmanagerService) super.getInstence(request,"dbc_mysqlmanagerservice");
	        List tablelist=dbc_mysqlmanagerservice.getTablelist("dbcv2");
	        request.setAttribute("tablelist", tablelist);
	        List version=dbc_mysqlmanagerservice.showmysqlversion();
			request.setAttribute("version", version);
			String isbeifen=request.getParameter("isbeifen");
			String isrestore=request.getParameter("isrestore");
			if("1".equals(isbeifen)){
				request.setAttribute("msg", "备份成功!");
			}
			if("1".equals(isrestore)){
				request.setAttribute("msg", "还原成功!");
			}
			List listbackup=new ArrayList();
			String separ = File.separator;
			String filePath = request.getSession().getServletContext().getRealPath(separ)+"backup"+separ+"database"+separ+"mysql";
			File dir = new File(filePath);
			if(!dir.isDirectory()){
				System.out.println(filePath + " -- 这不是一个文件夹");
			}else{
				File[] fileList = dir.listFiles();
				for(int i = fileList.length-1;i >-1;i--){
					if(fileList[i].isFile()){
						String fname=fileList[i].getName();
						if("sql".equals(fname.substring(fname.lastIndexOf(".")+1)));
						{
							listbackup.add(fname.subSequence(0, fname.lastIndexOf(".")));
							System.out.println(fileList[i].getName());
						}
						}
					}
				}
				//printFileList(fileList);
				request.setAttribute("listbackup", listbackup);
			return "managerdatabase";
		}catch (Exception e) {
			   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	           String ipaddress=Dbcutil.getIpByrequest(request);
	           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_util", "tomanagerdatabase", "出现异常："+e.getMessage());
	           request.setAttribute("action","dbc_util");
	           request.setAttribute("methode","tomanagerdatabase");
	           request.setAttribute("e", e.toString());
	           e.printStackTrace();
	           return "Exception";
		}
	}
	
	/**
	 * @Title showjiegou
	 * @Description 查看指定表结构
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String showcolumns() {
		try{
			String tablename=request.getParameter("tablename");
			Dbc_mysqlmanagerService dbc_mysqlmanagerservice=(Dbc_mysqlmanagerService) super.getInstence(request,"dbc_mysqlmanagerservice");
			List columns=dbc_mysqlmanagerservice.getcolumns(tablename);
			request.setAttribute("columns", columns);
			request.setAttribute("tablename",tablename);
			return "showcolumns";
		}catch (Exception e) {
			   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	           String ipaddress=Dbcutil.getIpByrequest(request);
	           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_util", "showcolumns", "出现异常："+e.getMessage());
	           request.setAttribute("action","dbc_util");
	           request.setAttribute("methode","showcolumns");
	           request.setAttribute("e", e.toString());
	           e.printStackTrace();
	           return "Exception";
		}
	}
	
	/**
	 * @Title showindex
	 * @Description 查看指定表索引
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String showindex() {
		try{
			String tablename=request.getParameter("tablename");
			Dbc_mysqlmanagerService dbc_mysqlmanagerservice=(Dbc_mysqlmanagerService) super.getInstence(request,"dbc_mysqlmanagerservice");
			List indexs=dbc_mysqlmanagerservice.getindex(tablename);
			request.setAttribute("indexs", indexs);
			request.setAttribute("tablename",tablename);
			return "showindex";
		}catch (Exception e) {
			   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	           String ipaddress=Dbcutil.getIpByrequest(request);
	           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_util", "showindex", "出现异常："+e.getMessage());
	           e.printStackTrace();
	           return "Exception";
		}
	}
	
	/**
	 * @Title addindex
	 * @Description 创建指定表索引
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String addindex() {
		try{
			String tablename=request.getParameter("tablename");
			String indexname=request.getParameter("indexname");
			String column=request.getParameter("column");
			Dbc_mysqlmanagerService dbc_mysqlmanagerservice=(Dbc_mysqlmanagerService) super.getInstence(request,"dbc_mysqlmanagerservice");
			dbc_mysqlmanagerservice.addindex(tablename,column,indexname);
			request.setAttribute("column", column);
			request.setAttribute("tablename",tablename);
			return "redirect-showcolumns";
		}catch (Exception e) {
			   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	           String ipaddress=Dbcutil.getIpByrequest(request);
	           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_util", "showindex", "出现异常："+e.getMessage());
	           e.printStackTrace();
	           return "Exception";
		}
	}
	
	/**
	 * @Title backup_mysql
	 * @Description mysql数据库备份
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String backup_mysql() {
		try{
			String[] clickall=request.getParameterValues("clickall");
			String[] tables=request.getParameterValues("checks");
			String[]  onlyjiegou=request.getParameterValues("onlyjiegou");
			boolean isjiegou=false;
			if(onlyjiegou!=null){
				for(int i=0;i<onlyjiegou.length;i++){
					if("1".equals(onlyjiegou[i])){
						isjiegou=true;
					}
				}
			}
			boolean isall=false;
			if(clickall!=null){
				for(int i=0;i<clickall.length;i++){
					if("1".equals(clickall[i])){
						isall=true;
					}
				}
			}
			MysqlManager mm=new MysqlManager();
			if(isjiegou){
				if((Dbc_systeminfo.getInstance().getOs_name()).toLowerCase().startsWith("win")){
					mm.backuptables_window(request, tables, isjiegou);
				}
				else{
					mm.backuptables_linux(request, tables, isjiegou);
				}
				
			}
			else{
				if(isall){
					if((Dbc_systeminfo.getInstance().getOs_name()).toLowerCase().startsWith("win")){
						mm.backup_window(request);
					}
					else{
						mm.backup_linux(request);
					}
				}
				else{
					if((Dbc_systeminfo.getInstance().getOs_name()).toLowerCase().startsWith("win")){
						mm.backuptables_window(request, tables, isjiegou);
					}
					else{
						mm.backuptables_linux(request, tables, isjiegou);
					}
				}
			}
			return "redirect-managerdatabase-backup";
		}catch (Exception e) {
			   Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	           String ipaddress=Dbcutil.getIpByrequest(request);
	           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_util", "backup_mysql", "出现异常："+e.getMessage());
	           request.setAttribute("action","dbc_util");
	           request.setAttribute("methode","backup_mysql");
	           request.setAttribute("e", e.toString());
	           e.printStackTrace();
	           return "Exception";
		}
	}
	
	/**
	 * @Title restore_mysql
	 * @Description mysql数据库还原
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String restore_mysql() {
		try{
			String separ = File.separator;
			String backupname=request.getParameter("backupname");
			MysqlManager mm=new MysqlManager();
			String sqlpath=request.getSession().getServletContext().getRealPath(separ)+"backup"+separ+"database"+separ+"mysql"+separ+backupname+".sql";
			if((Dbc_systeminfo.getInstance().getOs_name()).toLowerCase().startsWith("win")){
				mm.load_window(request,sqlpath);
			}
			else{
				mm.load_linux(request,sqlpath);
			}
			return "redirect-managerdatabase-restore";
		}catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	           String ipaddress=Dbcutil.getIpByrequest(request);
	           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_util", "restore_mysql", "出现异常："+e.getMessage());
	           request.setAttribute("action","dbc_util");
	           request.setAttribute("methode","restore_mysql");
	           request.setAttribute("e", e.toString());
	           e.printStackTrace();
	           return "Exception";
		}
	}
	
	/**
	 * @Title showsystem
	 * @Description 查看服务器环境
	 * @param  mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */	
	public String showsystem() {
		Dbc_systeminfo dbc_systeminfo=Dbc_systeminfo.getInstance(request);
		request.setAttribute("dbc_systeminfo", dbc_systeminfo);
		return "showsystem";
	}
	
	
    public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}
