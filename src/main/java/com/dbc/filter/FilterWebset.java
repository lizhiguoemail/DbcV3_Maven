package com.dbc.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dbc.pojo.Dbc_nav;
import com.dbc.pojo.Dbc_permit;
import com.dbc.pojo.Dbc_webconfig;
import com.dbc.service.Dbc_navService;
import com.dbc.service.Dbc_permitService;
import com.dbc.service.Dbc_webconfigService;
import com.dbc.util.Dbcutil;


public class FilterWebset implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {  
    }  
    // doFilter方法  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
    	 //Webconfig配置
    	 HttpServletRequest request2=(HttpServletRequest)request;
		// Dbc_webconfig webconfig=(Dbc_webconfig) request2.getSession().getAttribute("webconfig");
		 if(request2.getSession().getAttribute("webconfig")==null){
			 Dbc_webconfig webconfig=new Dbc_webconfig();
			 ApplicationContext applicationcontext = WebApplicationContextUtils.getRequiredWebApplicationContext(request2.getSession().getServletContext());
			 Dbc_webconfigService webconfigservice=(Dbc_webconfigService)applicationcontext.getBean("dbc_webconfigservice");	 
		     webconfig=webconfigservice.getwebconfig();
		     request2.getSession().setAttribute("webconfig", webconfig);
	     }
		 //获取导航
		 //List navlist=(List) request2.getSession().getAttribute("navlist");
		 if(request2.getSession().getAttribute("navlist")==null){
			 List navlist=new ArrayList();
			 ApplicationContext applicationcontext = WebApplicationContextUtils.getRequiredWebApplicationContext(request2.getSession().getServletContext());
			 Dbc_navService navservice=(Dbc_navService)applicationcontext.getBean("dbc_navservice");	
			 navlist=navservice.selEntity(Dbc_nav.class, Dbcutil.getarr("hide"), Dbcutil.getarr("否"), null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));
			 request2.getSession().setAttribute("navlist", navlist);
		 }
		 //获取系统权限
		 String allpermitsstr=(String) request2.getSession().getAttribute("allpermitsstr");
		 if(allpermitsstr==null){
			 allpermitsstr="";
			 ApplicationContext applicationcontext = WebApplicationContextUtils.getRequiredWebApplicationContext(request2.getSession().getServletContext());
			 Dbc_permitService permitservice=(Dbc_permitService)applicationcontext.getBean("dbc_permitservice");
			 List permitlist=permitservice.selEntity(Dbc_permit.class, Dbcutil.getarr("isopen"), Dbcutil.getarr("开启"), null, null, null);
			 for(int i=0;i<permitlist.size();i++){
				 Dbc_permit permit=(Dbc_permit) permitlist.get(i);
				 if(i==0){
					 String permit_methode=permit.getPermit_methode();
					 String permit_action=permit.getPermit_action();
					 if(permit_methode.contains("|")){
						 /*
						  * 注意：除了使用“\\|”外，也可以用"[.]" 进行分隔!
							如：
							String[] paraStr = "6010|320100|A".split("[|]");
							String[] paraStr = "6010.320100.A".split("[.]");
						  */
						 String[] parr=permit_methode.split("\\|");
						 for(int k=0;k<parr.length;k++){
							 if(k==0){
								 allpermitsstr=","+permit_action+"_"+parr[k]+",";
							 }
							 else{
								 allpermitsstr=allpermitsstr+permit_action+"_"+parr[k]+",";
							 }
						 }
					 }
					 else{
						 allpermitsstr=","+permit.getAction_methode()+",";
					 }
				 }
				 else{
					 allpermitsstr=allpermitsstr+permit.getAction_methode()+",";
				 }
			 }
			 request2.getSession().setAttribute("allpermitsstr", allpermitsstr);
		 }
		 chain.doFilter(request, response);  
    }  
    public void destroy() {  
  
    }
}
