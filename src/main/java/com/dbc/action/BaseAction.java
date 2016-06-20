package com.dbc.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.dbc.pojo.Dbc_webconfig;
import com.dbc.service.Dbc_webconfigService;
import com.dbc.util.MyApplicationContextUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	public Object getInstence(HttpServletRequest request, String str){
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		return applicationContext.getBean(str);
	}
	
	public Object getInstence(String str){
		ApplicationContext applicationContext = MyApplicationContextUtil.getContext();
		return applicationContext.getBean(str);
	}
	
	// 获取Request  
	protected HttpServletRequest getRequest() {  
	  return ServletActionContext.getRequest();  
	}  

	// 获取Response  
	protected HttpServletResponse getResponse() {  
	  return ServletActionContext.getResponse();  
	}  

	// 获取ServletContext  
	protected ServletContext getServletContext() {  
	  return ServletActionContext.getServletContext();  
	}  

	// 获取Attribute  
	protected Object getAttribute(String name) {  
	  return ServletActionContext.getRequest().getAttribute(name);  
	}  

	// 设置Attribute  
	protected void setAttribute(String name, Object value) {  
	  ServletActionContext.getRequest().setAttribute(name, value);  
	}  

	// 获取Parameter  
	protected String getParameter(String name) {  
	  return ServletActionContext.getRequest().getParameter(name);  
	}  

	// 获取Parameter数组  
	protected String[] getParameterValues(String name) {  
	  return ServletActionContext.getRequest().getParameterValues(name);  
	}  

	// 获取Session  
	protected Object getSession(String name) {  
	  ActionContext actionContext = ActionContext.getContext();  
	  Map<String, Object> session = actionContext.getSession();  
	  return session.get(name);  
	}  

	// 设置Session  
	protected void setSession(String name, Object value) {  
	  ActionContext actionContext = ActionContext.getContext();  
	  Map<String, Object> session = actionContext.getSession();  
	  session.put(name, value);  
	}  

	// 移除Session  
	protected void removeSession(String name) {  
	  ActionContext actionContext = ActionContext.getContext();  
	  Map<String, Object> session = actionContext.getSession();  
	  session.remove(name);  
	}
	
	//下载
	public void download(String path, HttpServletResponse response) {  
        try {  
            // path是指欲下载的文件的路径。  
            File file = new File(path);  
            // 取得文件名。  
            String filename = file.getName();  
            // 以流的形式下载文件。  
            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
            byte[] buffer = new byte[fis.available()];  
            fis.read(buffer);  
            fis.close();  
            // 清空response  
            response.reset();  
            // 设置response的Header  
            response.addHeader("Content-Disposition", "attachment;filename="  
                    + new String(filename.getBytes()));  
            response.addHeader("Content-Length", "" + file.length());  
            OutputStream toClient = new BufferedOutputStream(  
                    response.getOutputStream());  
            response.setContentType("application/vnd.ms-excel;charset=gb2312");  
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    }  

}
