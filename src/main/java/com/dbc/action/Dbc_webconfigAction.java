package com.dbc.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_webconfig;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_webconfigService;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.opensymphony.xwork2.ModelDriven;

public class Dbc_webconfigAction extends BaseAction implements ModelDriven{
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String methode;

	private Dbc_webconfig webconfig;
	
	public String execute(){
		String returnstr="";
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		if("show".equals(methode)){
			returnstr=this.show();
		}
		else if("update".equals(methode)){
			returnstr=this.update();
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
	 * @Title show
	 * @Description  进入网站基本设置
	 * @return string  返回值
	 */
	public String show(){
		try{
			Dbc_webconfigService webconfigservice=(Dbc_webconfigService) super.getInstence("dbc_webconfigservice");
			List list=webconfigservice.selEntityBynum(Dbc_webconfig.class, null, null, null, null, null, 1);
	        if(list.size()>0){
	        	Dbc_webconfig webconfig=(Dbc_webconfig) list.get(0);
	            request.setAttribute("webconfig", webconfig);
	        }
	        request.setAttribute("show_left_nav", "left_xtpz_jbsz");
			return "show_1";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_webconfig", "show", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	/**
	 * @Title update
	 * @Description  修改网站基本设置
	 * @return string  返回值
	 */
	public String update(){
		try{
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			//获取IP地址
			String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_webconfigService webconfigservice=(Dbc_webconfigService) super.getInstence("dbc_webconfigservice");
			if("".equals(webconfig.getId())||webconfig.getId()==null){
				webconfig.setId(null);
				webconfigservice.saveObject(webconfig);
				request.getSession().setAttribute("webconfig", webconfig);
			}
			else{
				webconfigservice.updateObject(webconfig);
				request.getSession().setAttribute("webconfig", webconfig);
			}
			request.setAttribute("show_left_nav", "left_xtpz_jbsz");
			request.setAttribute("msg", "更新成功！");
			//-----------------记录到日志---------------------------
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
			logservice.saveLog(userinfo, ipaddress, "dbc_webconfig", "update", "修改网站基本设置");
			//-----------------记录日志结束--------------------------
			return "show_1";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_webconfig", "update", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

    public String getMethode() {
   	 	return methode;
	 }
	
	 public void setMethode(String methode) {
	 	this.methode = methode;
	 }

	public Dbc_webconfig getWebconfig() {
		return webconfig;
	}

	public void setWebconfig(Dbc_webconfig webconfig) {
		this.webconfig = webconfig;
	}

	public Object getModel() {
		if(webconfig == null){
			webconfig = new Dbc_webconfig();
	       }
	    return webconfig;
	}
	
}
