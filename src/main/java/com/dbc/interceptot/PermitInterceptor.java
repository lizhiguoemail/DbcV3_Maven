package com.dbc.interceptot;

import java.util.Map;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.util.Dbc_permitUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PermitInterceptor extends AbstractInterceptor{
	
	public void init() {
		//Init方法在拦截器类被创建之后，在对Action镜像拦截之前调用，
		//相当于一个post-constructor方法，使用这个方法可以给拦截器类做必要的初始话操作。
    }
   
    public void destroy() {
    	//Destroy方法在拦截器被垃圾回收之前调用，用来回收init方法初始化的资源。
    }
	
    /**
     * Intercept是拦截器的主要拦截方法，如果需要调用后续的Action或者拦截器，
     * 只需要在该方法中调用invocation.invoke()方法即可，在该方法调用的前后可以插入Action调用前后拦截器需要做的方法。
     * 如果不需要调用后续的方法，则返回一个String类型的对象即可，例如Action.SUCCESS。
     */
    public String intercept(ActionInvocation invocation) throws Exception{
    	// 取得请求相关的ActionContext实例 
    	String returnstr=null;
        ActionContext ctx = invocation.getInvocationContext();  
        Map session = ctx.getSession();
        String allpermitsstr=(String) session.get("allpermitsstr");
        String act=invocation.getInvocationContext().getName();
        Map parameters=invocation.getInvocationContext().getParameters();
        Dbc_userinfo user = (Dbc_userinfo) session.get("backstage_user");
        String[] methodearr= (String[]) parameters.get("methode");
        if(methodearr!=null&&Dbc_permitUtil.check_backstage_permit(act,methodearr[0])){
    		return invocation.invoke(); 
    	}
    	else{
            if(user!=null){
            	if(allpermitsstr!=null&&methodearr!=null){
            		if("".equals(allpermitsstr)){
            			return invocation.invoke(); 
            		}
            		else{
            			if(Dbc_permitUtil.check_syspermit(act, methodearr[0], allpermitsstr)==false){
            				return invocation.invoke(); 
            			}
            			else{
            				 if(Dbc_permitUtil.check_userpermit(methodearr[0], user, act)){
            					 return invocation.invoke(); 
            				 }
            				 else{
            					 ctx.put("tip", "您无此权限！");  
       							 returnstr="nopermit"; 
            				 }
            			}
            		}
            	}
            	else{
            		/* ctx.put("e", "获取权限数据异常！");
    	        	 ctx.put("action",act);
    	        	 ctx.put("methode","未知");
                     return "Exception";*/
            		ctx.put("tip", "您无此权限！");  
					returnstr="nopermit";
            	}
            }
            else{
            	ctx.put("tip", "您无此权限！");  
				returnstr="nopermit";
            }
    	}
		return returnstr;

    }
}