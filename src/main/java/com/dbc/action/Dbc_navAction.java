/**
 * @Project 源自dbc
 * @Title NavAction.java
 * @Package com.action.common;
 * @Description  导航action类
 * @author caihuajun
 * @date 2013-11-01
 * @version v1.0
 * 2014-07-15 caihuajun 补上了导航可以选择父导航的功能
 */
package com.dbc.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.dbc.pojo.Dbc_nav;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_navService;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import com.opensymphony.xwork2.ModelDriven;

public class Dbc_navAction extends BaseAction implements ModelDriven{
	
	private String methode;

	private Dbc_nav nav;
	
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
		if(!"list".equals(methode)){
			request.getSession().setAttribute("navlist", null);
		}
		if("list".equals(methode)){
			returnstr=this.list();
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
			request.setAttribute("action","dbc_permit");
			request.setAttribute("methode",methode);
			request.setAttribute("exception", Dbc_custom_constants.nomethode);
			returnstr="Exception";
		}
	    return returnstr;
	}
	
	/**
	 * @Title list
	 * @Description  用户列表
	 * @return string  返回值
	 */
	public String list(){
		try{
			Dbc_navService navservice=(Dbc_navService) super.getInstence("dbc_navservice");
			PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
			List pagelist=(List)navservice.selEntityByPage(Dbc_nav.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
			List list = (List) pagelist.get(0);
			PageParm pageParm=(PageParm) pagelist.get(1);
			request.setAttribute("listnav", list);
		    request.setAttribute("pageParm", pageParm);
		    request.setAttribute("sname", sname);
		    request.setAttribute("svalue", svalue);
			return "list";
		 } catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_nav", "list", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	/**
	 * @Title toaddorupdate
	 * @Description  进入添加或修改导航页面
	 * @return string  返回值
	 */
	public String toaddorupdate(){
		try{
			String id=request.getParameter("id");
			Dbc_navService navservice=(Dbc_navService) super.getInstence("dbc_navservice");
			List navlist=new ArrayList();
			if(id!=null&&!"".equals(id)){
				Dbc_nav nav=(Dbc_nav) navservice.selByOid(Dbc_nav.class,id);
				request.setAttribute("nav", nav);
				navlist=navservice.selEntityBySql(Dbc_nav.class, "and id !='"+id+"' and (pid is null or pid='' or pid='0')", Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
				request.setAttribute("isupdate", "1");
			}
			else{
				navlist=navservice.selEntityBySql(Dbc_nav.class, " and (pid is null or pid='' or pid='0')", Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
			}
			request.setAttribute("navlist", navlist);
			return "addorupdate";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_nav", "toaddorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	
	/**
	 * @Title addorupdate
	 * @Description  添加或修改导航
	 * @return string  返回值
	 */
	public String addorupdate(){
		try{
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			String ipaddress=Dbcutil.getIpByrequest(request);
			String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
			String username=userinfo.getUsername();
			Dbc_navService navservice=(Dbc_navService) super.getInstence("dbc_navservice");
			String isupdate=request.getParameter("isupdate");
			if("1".equals(isupdate)){
				nav.setUpdatedate(nowdate);
				nav.setUpdateuser(username);
				String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=navservice.getSortCode_Double("Dbc_nav");
	              nav.setSortcode(sortcode);
	             }
				navservice.updateObject(nav);
				//-----------------记录到日志---------------------------
				Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
				logservice.saveLog(userinfo, ipaddress, "dbc_nav", "addorupdate", "修改导航栏");
				//-----------------记录日志结束--------------------------
			}
			else{
				nav.setId(null);
				nav.setCreatedate(nowdate);
				nav.setCreateuser(username);
				String sortcodestr=request.getParameter("sortcode");
		        if(sortcodestr==null||"".equals(sortcodestr)){
		            Double sortcode=navservice.getSortCode_Double("Dbc_nav");
		            nav.setSortcode(sortcode);
		         }
				navservice.saveObject(nav);
				//-----------------记录到日志---------------------------
				Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
				logservice.saveLog(userinfo, ipaddress, "dbc_nav", "addorupdate", "增加导航栏");
				//-----------------记录日志结束--------------------------
			}
			request.getSession().removeAttribute("navlist");
			return "redirect-list";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_nav", "addorupdate", "出现异常:"+e.toString());
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
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			String ipaddress=Dbcutil.getIpByrequest(request);
			String id=request.getParameter("id");
			Dbc_navService navservice=(Dbc_navService) super.getInstence("dbc_navservice");
			navservice.deletebyfieldarr(Dbc_nav.class, Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
			//-----------------记录到日志---------------------------
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
			logservice.saveLog(userinfo, ipaddress, "dbc_nav", "delete", "删除导航栏");
			//-----------------记录日志结束--------------------------
			request.getSession().removeAttribute("navlist");
			return "redirect-list";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_nav", "deletebyid", "出现异常:"+e.toString());
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
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			String ipaddress=Dbcutil.getIpByrequest(request);
			String[] ids=request.getParameterValues("checks");
			Dbc_navService navservice=(Dbc_navService) super.getInstence("dbc_navservice");
			String[] setfieldnamearr=Dbcutil.getarr("deletemark");
			String[] setcontentarr=Dbcutil.getarr("1");
			navservice.setObjectbyids(Dbc_nav.class, setfieldnamearr, setcontentarr, ids);
			//-----------------记录到日志---------------------------
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
			logservice.saveLog(userinfo, ipaddress, "dbc_nav", "delete", "删除导航栏");
			//-----------------记录日志结束--------------------------
			request.getSession().removeAttribute("navlist");
			return "redirect-list";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_nav", "delete", "出现异常:"+e.toString());
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

	public Object getModel() {
		if(nav == null){
	           nav = new Dbc_nav();
	       }
	    return nav;
	}

	public Dbc_nav getNav() {
		return nav;
	}

	public void setNav(Dbc_nav nav) {
		this.nav = nav;
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
