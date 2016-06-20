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

public class Dbc_userinfoAction extends BaseAction implements ModelDriven{
	
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
	
	public String execute(){
		String returnstr="";
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		if("login".equals(methode)){
			returnstr=this.login();
		}
		else if("logout".equals(methode)){
			returnstr=this.loginout();
		}
		else if("toBackIndex".equals(methode)){
			returnstr=this.toBackIndex();
		}
		else if("toreg".equals(methode)){
			returnstr=this.toreg();
		}
		else if("reg".equals(methode)){
			returnstr=this.reg();
		}
		else if("listuser".equals(methode)){
			returnstr=this.listuser();
		}
		else if("toaddorupdate".equals(methode)){
			returnstr=this.toaddorupdate();
		}
		else if("addorupdate".equals(methode)){
			returnstr=this.addorupdate();
		}
		else if("deletebyid".equals(methode)){
			returnstr=this.deletebyid();
		}
		else if("delete".equals(methode)){
			returnstr=this.delete();
		}
		else{
			request.setAttribute("action","dbc_userinfo");
			request.setAttribute("methode",methode);
			request.setAttribute("exception", Dbc_custom_constants.nomethode);
			returnstr="Exception";
		}
		return returnstr;
	}
	
	/**
	 * @Title toBackIndex
	 * @Description  用户登录
	 * @return string  返回值
	 */
	public String toBackIndex(){
		Dbc_userinfo u=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		if(u==null){
			request.setAttribute("tip", "请先登录！");
			return "login";
		}
		else{
			return "index";
		}
	}
	
	/**
	 * @Title login
	 * @Description  用户登录
	 * @return string  返回值
	 */
	public String login(){
		try{
			request.getSession().removeAttribute("backstage_user");
			//获取IP地址
			String ipaddress=Dbcutil.getIpByrequest(request);
			String usertype=request.getParameter("usertype");
			String username=userinfo.getUsername();
			//String password=SecurityUtils.md5(userinfo.getPassword());
			String password=request.getParameter("password");
			if(password==null||"".equals(password)){
				request.setAttribute("tip", "请输入密码!");
				return "login";
	     	 }
			else{
				password=SecurityUtils.md5(password);
			}
			String captcha=request.getParameter("captcha");
			String sysValidation=(String) request.getSession().getAttribute("sysValidation");
			if(captcha==null||sysValidation==null||"".equals(captcha)||"".equals(sysValidation)||!sysValidation.equals(captcha)){
				request.setAttribute("tip", "验证码错误!");
				return "login";
	     	 }
			else{
				Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
				String[] fieldname=new String[1];
				fieldname[0]="usertype";
				String[] valuename=new String[1];
				valuename[0]=usertype;
				Dbc_userinfo userinfo=userinfoservice.checkLoginByField(username, password,null,fieldname, valuename);
				if(userinfo!=null){
					String lastip=userinfo.getLastip();
					String lastviewdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
					userinfo.setLastvisit(lastviewdate);
					int logincount=1;
					if(userinfo.getLogincount()!=null){
						logincount=userinfo.getLogincount()+1;
					}
					userinfo.setLogincount(logincount);
					userinfo.setLastip(ipaddress);
					userinfoservice.updateObject(userinfo);
					userinfo.setLastip(lastip);
					userinfo.setLoginIp(ipaddress);
					//request.getSession().setAttribute("backstage_user", userinfo);
					String alluserpermit=",";
					List roles=userinfo.getRoles();
					if(roles!=null){
						for(int i=0;i<roles.size();i++){
							Dbc_role role=(Dbc_role) roles.get(i);
							if("是".equals(role.getIssysadmin())){
								userinfo.setIsadmin("1");
							}
							List permitlist=role.getPermits();
							if(permitlist!=null){
								for(int j=0;j<permitlist.size();j++){
									Dbc_permit permit=(Dbc_permit) permitlist.get(j);
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
											alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
										 }
									 }
									 else{
										 alluserpermit=alluserpermit+permit.getAction_methode()+",";
									 }
								}
							}
						}
					}
					List userpermitlist=userinfo.getPermits();
					if(userpermitlist!=null){
						for(int j=0;j<userpermitlist.size();j++){
						Dbc_permit permit=(Dbc_permit) userpermitlist.get(j);
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
									alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
								 }
							 }
							 else{
								 alluserpermit=alluserpermit+permit.getAction_methode()+",";
							 }
						}
					}
					userinfo.setAllpermits(alluserpermit);
					Map session=ActionContext.getContext().getSession();
					session.put("backstage_user", userinfo);
					//-----------------记录到日志---------------------------
					Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
					logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "login", "登录");
					//-----------------记录日志结束--------------------------
					return "redirect-index";
				}
				else{
					request.setAttribute("tip", "帐号或密码错误!");
					return "login";
				}
			}
			
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "login", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
		
	}
	
	/**
	 * @Title loginout
	 * @Description 注销用户
	 * @return String
	 * @throws  
	 */	
	public String loginout(){
		request.getSession().removeAttribute("backstage_user");
		return "login";
	}
	
	/**
	 * @Title toreg
	 * @Description  用户注册
	 * @return string  返回值
	 */
	public String toreg(){
		return "reg";
	}
	
	/**
	 * @Title reg
	 * @Description  用户注册
	 * @return string  返回值
	 */
	public String reg(){
		try{
			String password=SecurityUtils.md5(userinfo.getPassword());
			userinfo.setId(Dbcutil.getUUID("0"));
			userinfo.setPassword(password);
			String errormsg="";
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			String ipaddress=Dbcutil.getIpByrequest(request);
			if(userinfoservice.checkReg(userinfo.getUsername(),null)){
				errormsg="该用户名已存在";
				request.setAttribute("errormsg", errormsg);
				return "reg";
			}
			if(userinfoservice.checkNickname(userinfo.getNickname(),null)){
				errormsg="该用户昵称已存在";
				request.setAttribute("errormsg", errormsg);
				return "reg";
			}
			userinfo.setIpaddress(ipaddress);
		    String regdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
		    userinfo.setRegdate(regdate);
		    userinfo.setCreatedate(regdate);
			userinfoservice.saveObject(userinfo);
			//-----------------记录到日志---------------------------
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
			logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "reg", "注册");
			//-----------------记录日志结束--------------------------
			return "regsuccess";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_userinfo", "reg", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	/**
	 * @Title listuser
	 * @Description  用户列表
	 * @return string  返回值
	 */
	public String listuser(){
		try{
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
			String sname2="usertype";
			String svalue2="member";
			if(sname!=null&&!"".equals(sname)){
				sname2=sname+",usertype";
			}
			if(svalue!=null&&!"".equals(svalue)){
				svalue2=svalue+",member";
			}
			List pagelist=(List)userinfoservice.selEntityByPage(Dbc_userinfo.class,Dbcutil.getarr(sname2), Dbcutil.getarr(svalue2), null, pageparm, Dbcutil.getarr("regdate"), Dbcutil.getarr("desc"));
			List list = (List) pagelist.get(0);
			PageParm pageParm=(PageParm) pagelist.get(1);
			request.setAttribute("listuser", list);
			request.setAttribute("pageParm", pageParm);
			request.setAttribute("sname", sname);
		    request.setAttribute("svalue", svalue);
			return "listuser";
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
        String alluserpermit=",";
		List roles=userinfo.getRoles();
		if(roles!=null){
			for(int i=0;i<roles.size();i++){
				Dbc_role role=(Dbc_role) roles.get(i);
				if("是".equals(role.getIssysadmin())){
					userinfo.setIsadmin("1");
				}
				List permitlist=role.getPermits();
				if(permitlist!=null){
					for(int j=0;j<permitlist.size();j++){
						Dbc_permit permit=(Dbc_permit) permitlist.get(j);
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
								alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
							 }
						 }
						 else{
							 alluserpermit=alluserpermit+permit.getAction_methode()+",";
						 }
					}
				}
			}
		}
		List userpermitlist=userinfo.getPermits();
		if(userpermitlist!=null){
			for(int j=0;j<userpermitlist.size();j++){
			Dbc_permit permit=(Dbc_permit) userpermitlist.get(j);
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
						alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
					 }
				 }
				 else{
					 alluserpermit=alluserpermit+permit.getAction_methode()+",";
				 }
			}
		}
		userinfo.setAllpermits(alluserpermit);
		request.getSession().setAttribute("backstage_user", user);
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
