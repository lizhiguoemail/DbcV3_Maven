/**
* @Project 源自 dbc
* @Title Base_permitAction.java
* @Package com.dbc.action.common
* @Description 权限action类
* @author caihuajun
* @date 2014-02-17
* @version v2.0
*/
package com.dbc.action;

import java.io.IOException;

import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_appService;
import com.dbc.service.Dbc_paramService;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_userinfoService;
import com.dbc.pojo.Dbc_param;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_role;
import com.dbc.service.Dbc_roleService;
/**
* @ClassName Base_permitAction
* @Description 角色action类
* @author caihuajun
* @date 2014-02-17
*/
public class Dbc_permitoldAction extends BaseAction{

    private String methode;

    private HttpServletRequest request;

    private HttpServletResponse response;

    public String execute(){
      String returnstr="";
      request = ServletActionContext.getRequest();
      response= ServletActionContext.getResponse();
      if("tosetpermit".equals(methode)){
        returnstr=this.tosetpermit();
      }
      else if("setsystempermit".equals(methode)){
          returnstr=this.setsystempermit();
      }
      else if("tosetrolepermit".equals(methode)){
          returnstr=this.tosetrolepermit();
      }
      /*else if("setrolepermit".equals(methode)){
          returnstr=this.setrolepermit();
      }*/
      else{
    	  request.setAttribute("action","dbc_permit");
		  request.setAttribute("methode",methode);
		  request.setAttribute("exception", Dbc_custom_constants.nomethode);
		  returnstr="Exception";
      }
      return returnstr;
    }

    /**
    * @Title tosetpermit
    * @Description 进入权限管理页面
    * @return string
    */
    public String tosetpermit(){
    	try{
	    	Dbc_paramService base_paremservice=(Dbc_paramService) super.getInstence("dbc_paramservice");
	    	List base_paramlist=base_paremservice.selEntity(Dbc_param.class, null, null, null, null, null);
	    	Dbc_param base_param=new Dbc_param();
	    	if(base_paramlist.size()>0){
	    		base_param=(Dbc_param) base_paramlist.get(0);
	    	}
	    	request.setAttribute("base_param", base_param);
	        return "setsystempermit";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_permit", "tosetpermit", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    /**
     * @Title setsystempermit
     * @Description 设置系统权限
     * @return string
     */
     public String setsystempermit(){
    	try{
	    	String[] permitids=request.getParameterValues("permitid");
	    	Dbc_paramService base_paremservice=(Dbc_paramService) super.getInstence("dbc_paramservice");
	     	List base_paramlist=base_paremservice.selEntity(Dbc_param.class, null, null, null, null, null);
	     	Dbc_param base_param=new Dbc_param();
	     	if(base_paramlist.size()>0){
	     		base_param=(Dbc_param) base_paramlist.get(0);
	     		base_param.setPermitids(","+Dbcutil.setarrtoString(permitids, ",")+",");
	     		base_paremservice.updateObject(base_param);
	     	}
	     	else{
	     		base_param.setPermitids(","+Dbcutil.setarrtoString(permitids, ",")+",");
	     		base_paremservice.saveObject(base_param);
	     	}
	     	request.setAttribute("base_param", base_param);
	     	String num="0";
	     	if(permitids!=null&&permitids.length>0){
	     		num=""+permitids.length;
	     	}
	     	request.setAttribute("msg", "提交配置成功！本次共开启权限 "+num+"  个!");
	        return "setsystempermit";
    	 } catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_permit", "setsystempermit", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
     }
     
     /**
      * @Title tosetrolepermit
      * @Description 进入角色权限配置页面
      * @return string  返回值
      */
      public String tosetrolepermit(){
    	  try{
	          String id=request.getParameter("id");
	          Dbc_roleService base_roleservice=(Dbc_roleService) super.getInstence(request,"dbc_roleservice");
	       	  Dbc_role base_role=(Dbc_role) base_roleservice.selByOid(Dbc_role.class, id);
	       	  request.setAttribute("base_role", base_role);
	          return "setrolepermit";
    	  } catch (Exception e) {
  			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_permit", "tosetrolepermit", "出现异常:"+e.toString());
  			e.printStackTrace();
  			return "Exception";
  		}
      }
     
     /**
      * @Title setrolepermit
      * @Description 设置角色权限
      * @return string
      *//*
      public String setrolepermit(){
    	  try{
	     	String[] permitids=request.getParameterValues("permitid");
	     	String roleid=request.getParameter("roleid");
	     	Dbc_roleService base_roleservice=(Dbc_roleService) super.getInstence(request,"dbc_roleservice");
	     	Dbc_role role=(Dbc_role) base_roleservice.selByOid(Dbc_role.class, roleid);
	     	role.setPermits(","+Dbcutil.setarrtoString(permitids, ",")+",");
	     	base_roleservice.updateObject(role);;
	      	request.setAttribute("base_role", role);
	      	String num="0";
	      	if(permitids!=null&&permitids.length>0){
	      		num=""+permitids.length;
	      	}
	      	request.setAttribute("msg", "提交配置成功！本次共开启权限 "+num+"  个!");
	         return "setrolepermit";
    	  } catch (Exception e) {
    			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
    		    String ipaddress=Dbcutil.getIpByrequest(request);
    			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
                logservice.saveLog(userinfo, ipaddress, "dbc_permit", "setrolepermit", "出现异常:"+e.toString());
    			e.printStackTrace();
    			request.setAttribute("action","dbc_permit");
    			request.setAttribute("methode","setrolepermit");
    			request.setAttribute("e", e.toString());
    			return "Exception";
    		}
	   }*/
      
      /**
       * @Title tosetuserpermit
       * @Description 进入用户权限配置页面
       * @return string  返回值
       */
       public String tosetuserpermit(){
    	   try{
	           String id=request.getParameter("id");
	           Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
	           Dbc_userinfo user=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class, id);
	           request.setAttribute("user", user);
	           return "setuserpermit";
    	   } catch (Exception e) {
	   			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	   		    String ipaddress=Dbcutil.getIpByrequest(request);
	   			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	               logservice.saveLog(userinfo, ipaddress, "dbc_permit", "tosetuserpermit", "出现异常:"+e.toString());
	   			e.printStackTrace();
   			return "Exception";
   		}
       }
      
      /**
       * @Title setuserpermit
       * @Description 设置用户权限
       * @return string
       */
       public String setuserpermit(){
    	   try{
		      	String[] permitids=request.getParameterValues("permitid");
		      	String userid=request.getParameter("userid");
		      	Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
		        Dbc_userinfo user=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class, userid);
		      	user.setPermitids(","+Dbcutil.setarrtoString(permitids, ",")+",");
		      	userinfoservice.updateObject(user);;
		       	request.setAttribute("user", user);
		       	String num="0";
		       	if(permitids!=null&&permitids.length>0){
		       		num=""+permitids.length;
		       	}
		       	request.setAttribute("msg", "提交配置成功！本次共开启权限 "+num+"  个!");
		        return "setuserpermit";
    	   } catch (Exception e) {
	   			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	   		    String ipaddress=Dbcutil.getIpByrequest(request);
	   			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	               logservice.saveLog(userinfo, ipaddress, "dbc_permit", "setuserpermit", "出现异常:"+e.toString());
	   			e.printStackTrace();
	   			return "Exception";
    	   }
       }

       public String getMethode() {
   		return methode;
	   	}
	
	   	public void setMethode(String methode) {
	   		this.methode = methode;
	   	}

}