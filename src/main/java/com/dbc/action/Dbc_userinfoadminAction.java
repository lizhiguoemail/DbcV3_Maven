/**
 * @Project 源自dbcV2
 * @Title Dbc_userinfoAction.java
 * @Package com.dbc.action;
 * @Description  用户类action
 * @author caihuajun
 * @date 2015-05-12 修改
 * @version v2.0
 * 2009-11-10 caihuajun 设置了用户最好一次登录的ip地址以及当前ip地址放入用户session中
 */
package com.dbc.action;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.dbc.pojo.Dbc_permit;
import com.dbc.pojo.Dbc_role;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_roleService;
import com.dbc.service.Dbc_userinfoService;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.singlee.sda.security.SecurityUtils;

public class Dbc_userinfoadminAction extends BaseAction implements ModelDriven{
	
	private String methode;

	private Dbc_userinfo userinfo;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String nowpageString;
	
	private String gotopagetype;
	
	private String gotopageString;
	
	private String pagesizeString="15";
	
	private String sname;
	
	private String svalue;
	
	@Override
	public String execute(){
		String returnstr="";
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		if("listadmin".equals(methode)){
			returnstr=this.listadmin();
		}
		else if("toaddorupdate".equals(methode)){
			returnstr=this.toaddorupdate();
		}
		else if("addorupdate".equals(methode)){
			returnstr=this.addorupdate();
		}
		else if("toupdateuser_role".equals(methode)){
			returnstr=this.toupdateuser_role();
		}
		else if("updateuser_role".equals(methode)){
			returnstr=this.updateuser_role();
		}
		else if("deletebyid".equals(methode)){
			returnstr=this.deletebyid();
		}
		else if("delete".equals(methode)){
			returnstr=this.delete();
		}
		else if("toupdateuser_permit".equals(methode)){
	          returnstr=this.toupdateuser_permit();
	      }
	    else if("updateuser_permit".equals(methode)){
	          returnstr=this.updateuser_permit();
	      }
		else{
			request.setAttribute("action","dbc_userinfoadmin");
			request.setAttribute("methode",methode);
			request.setAttribute("exception", Dbc_custom_constants.nomethode);
			returnstr="Exception";
		}
		return returnstr;
	}
	
	/**
	 * @Title listadmin
	 * @Description  管理员列表
	 * @return string  返回值
	 */
	public String listadmin(){
		try{
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
			String sname2="usertype";
			String svalue2="backstage";
			if(sname!=null&&!"".equals(sname)){
				sname2=sname+",usertype";
			}
			if(svalue!=null&&!"".equals(svalue)){
				svalue2=svalue+",backstage";
			}
			List pagelist=(List)userinfoservice.selEntityByPage(Dbc_userinfo.class,Dbcutil.getarr(sname2), Dbcutil.getarr(svalue2), null, pageparm, Dbcutil.getarr("regdate"), Dbcutil.getarr("desc"));
			List list = (List) pagelist.get(0);
			PageParm pageParm=(PageParm) pagelist.get(1);
			request.setAttribute("listuser", list);
			request.setAttribute("pageParm", pageParm);
			request.setAttribute("sname", sname);
		    request.setAttribute("svalue", svalue);
			return "listadmin";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "listadmin", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	/**
	 * @Title toaddorupdate
	 * @Description  进入添加或修改用户页面
	 * @return string  返回值
	 */
	public String toaddorupdate(){
		try{
			String id=request.getParameter("id");
			if(id!=null&&!"".equals(id)){
				Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
				Dbc_userinfo userinfo=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class,id);
				request.setAttribute("user", userinfo);
				request.setAttribute("isupdate", "1");
			}
			else{
				String usertype=request.getParameter("usertype");
				request.setAttribute("usertype", usertype);
			}
			return "addorupdate";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "toaddorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	
	/**
	 * @Title addorupdate
	 * @Description  添加或修改用户
	 * @return string  返回值
	 */
	public String addorupdate(){
		try{
			Dbc_userinfo backstage_user=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			String ipaddress=Dbcutil.getIpByrequest(request);
			String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
			String username=userinfo.getUsername();
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			String isupdate=request.getParameter("isupdate");
			String errormsg="";
			if("1".equals(isupdate)){
				Dbc_userinfo user=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class, userinfo.getId());
				user.setUpdatedate(nowdate);
				user.setUpdateuser(username);
				user.setTname(userinfo.getTname());
				user.setEmail(userinfo.getEmail());
				user.setNickname(userinfo.getNickname());
				user.setQq(userinfo.getQq());
				String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=userinfoservice.getSortCode_Double("Dbc_userinfo");
	              user.setSortcode(sortcode);
	             }
	            String[] pwd=request.getParameterValues("pwd");
	            int pwdnum=0;
	            if(pwd!=null){
	            	for(int i=0;i<pwd.length;i++){
	                	if("jihuo".equals(pwd[i])){
	                		pwdnum=pwdnum+1;
	                	}
	                }
	            }
	            if(pwdnum>0){
	            	String password=SecurityUtils.md5(userinfo.getPassword());
	            	user.setPassword(password);
	            }
	            
	            userinfoservice.updateObject(user);
				//-----------------记录到日志---------------------------
				Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
				logservice.saveLog(backstage_user, ipaddress, "dbc_userinfo", "addorupdate", "修改用户");
				//-----------------记录日志结束--------------------------
			}
			else{
				userinfo.setId(UUID.randomUUID().toString());
				userinfo.setCreatedate(nowdate);
				userinfo.setCreateuser(username);
				userinfo.setRegdate(nowdate);
				String sortcodestr=request.getParameter("sortcode");
		        if(userinfoservice.checkReg(userinfo.getUsername(),null)){
					errormsg="该用户名已存在";
					request.setAttribute("errormsg", errormsg);
					request.setAttribute("user", userinfo);
					return "addorupdate";
				}
				/*if(userinfoservice.checkNickname(userinfo.getNickname(),null)){
					errormsg="该用户昵称已存在";
					request.setAttribute("errormsg", errormsg);
					request.setAttribute("user", userinfo);
					return "addorupdate";
				}*/
				if(sortcodestr==null||"".equals(sortcodestr)){
		            Double sortcode=userinfoservice.getSortCode_Double("Dbc_userinfo");
		            userinfo.setSortcode(sortcode);
		         }
				String password=SecurityUtils.md5(userinfo.getPassword());
				userinfo.setPassword(password);
		        userinfoservice.saveObject(userinfo);
				//-----------------记录到日志---------------------------
				Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
				logservice.saveLog(backstage_user, ipaddress, "dbc_userinfo", "addorupdate", "增加用户");
				//-----------------记录日志结束--------------------------
			}
			String returnstr="redirect-listuser";
			if("backstage".equals(userinfo.getUsertype())){
				returnstr="redirect-listadmin";
			}
			return returnstr;
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	/**
	 * @Title toupdateuser_role
	 * @Description  进入用户角色配置页面
	 * @return string  返回值
	 */
	public String toupdateuser_role(){
		try{
			String id=request.getParameter("id");
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			Dbc_userinfo user=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class,id);
			request.setAttribute("user", user);
			List rolelist=userinfoservice.selEntity(Dbc_role.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));
			request.setAttribute("rolelist", rolelist);
			String rolestr=",";
			List rlist=user.getRoles();
	     	if(rlist.size()>0){
	     		for(int i=0;i<rlist.size();i++){
	     			Dbc_role role=(Dbc_role) rlist.get(i);
	     			rolestr=rolestr+role.getId()+",";
	     		}
	     	}
	     	request.setAttribute("rolestr", rolestr);
			return "updateuser_role";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "toupdateuser_role", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	/**
	 * @Title updateuser_role
	 * @Description  用户角色配置
	 * @return string  返回值
	 */
	public String updateuser_role(){
		try{
			String id=request.getParameter("id");
			String[] roleids=request.getParameterValues("roleid");
			String[] isadmin=request.getParameterValues("isadmin");
			String[] bbs_admin=request.getParameterValues("bbs_admin");
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			Dbc_userinfo user=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class,id);
			List rlist=userinfoservice.selByOids(Dbc_role.class, roleids, Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));
			user.setRoles(rlist);
			if(isadmin!=null){
				int isadminnum=0;
				for(int i=0;i<isadmin.length;i++){
					if("1".equals(isadmin[i])){
						isadminnum=isadminnum+1;
					}
				}
				if(isadminnum>0){
					user.setIsadmin("1");
				}
				else{
					user.setIsadmin("0");
				}
			}
			else{
				user.setIsadmin("0");
			}
			if(bbs_admin!=null){
				int isbbs_adminnum=0;
				for(int i=0;i<bbs_admin.length;i++){
					if("1".equals(bbs_admin[i])){
						isbbs_adminnum=isbbs_adminnum+1;
					}
				}
				if(isbbs_adminnum>0){
					user.setBbs_admin("1");
				}
				else{
					user.setBbs_admin("0");
				}
			}
			else{
				user.setBbs_admin("0");
			}
			userinfoservice.updateObject(user);
			request.setAttribute("user", user);
			List rolelist=userinfoservice.selEntity(Dbc_role.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));
			request.setAttribute("rolelist", rolelist);
	     	request.setAttribute("msg", "角色配置提交成功!");
	     	String rolestr=",";
	     	if(rlist.size()>0){
	     		for(int i=0;i<rlist.size();i++){
	     			Dbc_role role=(Dbc_role) rlist.get(i);
	     			rolestr=rolestr+role.getId()+",";
	     		}
	     	}
	     	request.setAttribute("rolestr", rolestr);
	     	this.updatesession(userinfoservice, user.getId(),rlist);
			return "updateuser_role";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "updateuser_role", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	 /**
     * @Title toupdateuser_permit
     * @Description 进入用户权限配置页面
     * @return string  返回值
     */
     public String toupdateuser_permit(){
  	   try{
           String id=request.getParameter("id");
           Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
           Dbc_userinfo user=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class, id);
           List permitlist=userinfoservice.selEntity(Dbc_permit.class, Dbcutil.getarr("isopen"), Dbcutil.getarr("开启"), null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
           List permits=user.getPermits();
           String permitsarr=",";
	       	for(int i=0;i<permits.size();i++){
	       		Dbc_permit dp=(Dbc_permit) permits.get(i);
	       		permitsarr=permitsarr+dp.getId()+",";
	       	}
	       request.setAttribute("permitsarr", permitsarr);
   		   request.setAttribute("permitlist", permitlist);
           request.setAttribute("user", user);
           return "updateuserpermit";
  	   } catch (Exception e) {
   			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
   		    String ipaddress=Dbcutil.getIpByrequest(request);
   			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "tosetuserpermit", "出现异常:"+e.toString());
   			e.printStackTrace();
 			return "Exception";
 		}
     }
    
    /**
     * @Title updateuser_permit
     * @Description 设置用户权限
     * @return string
     */
     public String updateuser_permit(){
  	   try{
	      	String[] permitids=request.getParameterValues("permitid");
	      	String userid=request.getParameter("userid");
	      	Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
	        Dbc_userinfo user=(Dbc_userinfo) userinfoservice.selByOid(Dbc_userinfo.class, userid);
	        List permitlist=userinfoservice.selByOids(Dbc_permit.class, permitids, null, null);
	        user.setPermits(permitlist);
	        userinfoservice.updateObject(user);
	       	request.setAttribute("msg", "提交配置成功！!");
	        String permitsarr=",";
		    for(int i=0;i<permitlist.size();i++){
		       Dbc_permit dp=(Dbc_permit) permitlist.get(i);
		       permitsarr=permitsarr+dp.getId()+",";
		    }
	       	request.setAttribute("permitsarr", permitsarr);
	        List permitlistall=userinfoservice.selEntity(Dbc_permit.class, Dbcutil.getarr("isopen"), Dbcutil.getarr("开启"), null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
	   		request.setAttribute("permitlist", permitlistall);
	        request.setAttribute("user", user);
	        this.updatesession(userinfoservice, user.getId());
	        return "updateuserpermit";
  	   } catch (Exception e) {
   			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
   		    String ipaddress=Dbcutil.getIpByrequest(request);
   			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "setuserpermit", "出现异常:"+e.toString());
   			e.printStackTrace();
   			return "Exception";
  	   }
     }
	
	/**
	 * @Title deletebyid
	 * @Description  删除导航
	 * @return string  返回值
	 */
	public String deletebyid(){
		try{
			Dbc_userinfo backstage_user=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			String ipaddress=Dbcutil.getIpByrequest(request);
			String id=request.getParameter("id");
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			userinfoservice.deletebyfieldarr(Dbc_userinfo.class, Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
			//-----------------记录到日志---------------------------
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
			logservice.saveLog(backstage_user, ipaddress, "dbc_userinfo", "deletebyid", "删除用户");
			//-----------------记录日志结束--------------------------
			return "redirect-list";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	/**
	 * @Title delete
	 * @Description  删除导航
	 * @return string  返回值
	 */
	public String delete(){
		try{
			Dbc_userinfo backstage_user=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			String ipaddress=Dbcutil.getIpByrequest(request);
			String[] ids=request.getParameterValues("checks");
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			String[] setfieldnamearr=Dbcutil.getarr("deletemark");
			String[] setcontentarr=Dbcutil.getarr("1");
			userinfoservice.setObjectbyids(Dbc_userinfo.class, setfieldnamearr, setcontentarr, ids);
			//-----------------记录到日志---------------------------
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
			logservice.saveLog(backstage_user, ipaddress, "dbc_userinfo", "delete", "删除用户");
			//-----------------记录日志结束--------------------------
			return "redirect-list";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "delete", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	public void updatesession(Dbc_userinfoService dbc_userinfoservice,String userid){
    	//------更新session中的权限--------------------------------------
        Dbc_userinfo user=(Dbc_userinfo) dbc_userinfoservice.selByOid(Dbc_userinfo.class, userid);
        String alluserpermit="";
        List roles=user.getRoles();
		if(roles!=null){
			for(int i=0;i<roles.size();i++){
				Dbc_role role=(Dbc_role) roles.get(i);
				if("是".equals(role.getIssysadmin())){
					user.setIsadmin("1");
				}
				List permitlist=role.getPermits();
				if(permitlist!=null){
					for(int j=0;j<permitlist.size();j++){
						Dbc_permit permit=(Dbc_permit) permitlist.get(j);
						 if(j==0){
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
										 alluserpermit=","+permit_action+"_"+parr[k]+",";
									 }
									 else{
										 alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
									 }
								 }
							 }
							 else{
								 alluserpermit=","+permit.getAction_methode()+",";
							 }
						 }
						 else{
							 alluserpermit=alluserpermit+permit.getAction_methode()+",";
						 }
					}
				}
			}
		}
		List userpermitlist=user.getPermits();
		if(userpermitlist!=null){
			for(int j=0;j<userpermitlist.size();j++){
				Dbc_permit permit=(Dbc_permit) userpermitlist.get(j);
				 if(j==0){
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
								 alluserpermit=","+permit_action+"_"+parr[k]+",";
							 }
							 else{
								 alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
							 }
						 }
					 }
					 else{
						 alluserpermit=","+permit.getAction_methode()+",";
					 }
				 }
				 else{
					 alluserpermit=alluserpermit+permit.getAction_methode()+",";
				 }
				
			}
		}
		user.setAllpermits(alluserpermit);
		request.getSession().setAttribute("backstage_user", user);
		System.out.println("当前用户所有权限："+user.getAllpermits());
    }

	public void updatesession(Dbc_userinfoService dbc_userinfoservice,String userid,List roles){
    	//------更新session中的权限--------------------------------------
        Dbc_userinfo user=(Dbc_userinfo) dbc_userinfoservice.selByOid(Dbc_userinfo.class, userid);
        String alluserpermit="";
		if(roles!=null){
			for(int i=0;i<roles.size();i++){
				Dbc_role role=(Dbc_role) roles.get(i);
				if("是".equals(role.getIssysadmin())){
					user.setIsadmin("1");
				}
				List permitlist=role.getPermits();
				if(permitlist!=null){
					for(int j=0;j<permitlist.size();j++){
						Dbc_permit permit=(Dbc_permit) permitlist.get(j);
						 if(j==0){
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
										 alluserpermit=","+permit_action+"_"+parr[k]+",";
									 }
									 else{
										 alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
									 }
								 }
							 }
							 else{
								 alluserpermit=","+permit.getAction_methode()+",";
							 }
						 }
						 else{
							 alluserpermit=alluserpermit+permit.getAction_methode()+",";
						 }
					}
				}
			}
		}
		List userpermitlist=user.getPermits();
		if(userpermitlist!=null){
			for(int j=0;j<userpermitlist.size();j++){
				Dbc_permit permit=(Dbc_permit) userpermitlist.get(j);
				 if(j==0){
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
								 alluserpermit=","+permit_action+"_"+parr[k]+",";
							 }
							 else{
								 alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
							 }
						 }
					 }
					 else{
						 alluserpermit=","+permit.getAction_methode()+",";
					 }
				 }
				 else{
					 alluserpermit=alluserpermit+permit.getAction_methode()+",";
				 }
				
			}
		}
		user.setAllpermits(alluserpermit);
		request.getSession().setAttribute("backstage_user", user);
		System.out.println("当前用户所有权限："+user.getAllpermits());
    }


	public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
	}

	public Object getModel() {
		if(userinfo == null){
	           userinfo = new Dbc_userinfo();
	       }
	    return userinfo;
	}

	public Dbc_userinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Dbc_userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getNowpageString() {
		return nowpageString;
	}

	public void setNowpageString(String nowpageString) {
		this.nowpageString = nowpageString;
	}

	public String getGotopagetype() {
		return gotopagetype;
	}

	public void setGotopagetype(String gotopagetype) {
		this.gotopagetype = gotopagetype;
	}

	public String getGotopageString() {
		return gotopageString;
	}

	public void setGotopageString(String gotopageString) {
		this.gotopageString = gotopageString;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}
}
