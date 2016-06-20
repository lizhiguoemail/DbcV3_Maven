package com.dbc.interceptot;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;
import com.dbc.pojo.Dbc_userinfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor{
	@Override  
    public String intercept(ActionInvocation invocation) throws Exception {  
        // 取得请求相关的ActionContext实例  
        ActionContext ctx = invocation.getInvocationContext();  
        Map session = ctx.getSession();
        Dbc_userinfo user = (Dbc_userinfo) session.get("backstage_user");
        if(user!=null){
        	return invocation.invoke();  
        }
        else{
        	String act=invocation.getInvocationContext().getName();
            Map parameters=invocation.getInvocationContext().getParameters();
            String[] methodearr= (String[]) parameters.get("methode");
            if(methodearr!=null){
            	String methode=methodearr[0];
            	InputStream in = new ClassPathResource("backstage_login.properties").getInputStream();
                Properties property = new Properties();
    			property.load(in);
    		    String astring =property.getProperty("Action");
    		    String mstring =property.getProperty("Methode");
    		    String allastring =property.getProperty("AllAction");
    		    String allmstring=property.getProperty("Allmethode");
    		    String[] aarr=(astring+",").split(",");
    		    String[] marr=(mstring+",").split(",");
    		    if((","+allastring+",").indexOf(","+act+",")>0||(","+allmstring+",").indexOf(","+methode+",")>0){
    		    	return invocation.invoke();
    		    }
    		    else{
    		    	if(aarr!=null&&marr!=null&&aarr.length==marr.length){
    		    		int isallnum=0;
    		    		for(int i=0;i<aarr.length;i++){
    		    			if(act.equals(aarr[i])&&methode.equals(marr[i])){
    		    				isallnum=isallnum+1;
    		    			}
    		    		}
    		    		if(isallnum>0){
    		    			return invocation.invoke();
        		    	}
        		    	else{
           	        	 ctx.put("tip", "请重新登录！");  
           	             return "nosession";  
        		    	}
    		    	}
    		    	else{
    		    		ctx.put("tip", "请重新登录！");  
          	            return "nosession";  
    		    	}
    		    }
            }
            else{
            	 ctx.put("tip", "参数名不合法！");  
                 return "Exception";  
            }
        }
    }  
}
