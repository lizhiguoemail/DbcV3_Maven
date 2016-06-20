package com.dbc.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_webconfig;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_webconfigService;
import com.dbc.util.Dbcutil;
import com.opensymphony.xwork2.ModelDriven;
import com.singlee.sda.security.SecurityUtils;

@SuppressWarnings("serial")
public class Dbc_chongzhiAction extends BaseAction implements ModelDriven{
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String methode;

	private Dbc_webconfig webconfig;
	
	@Override
	public String execute() throws Exception {
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		String offer_name=request.getParameter("offer_name");
		String uid=request.getParameter("uid");
		String vcpoints=request.getParameter("vcpoints");
		String tid=request.getParameter("tid");
		//String key="shumili";
		String key="cuzheng";
		String pass=SecurityUtils.md5(uid+vcpoints+tid+key);
		request.setAttribute("offer_name", offer_name);
		request.setAttribute("uid", uid);
		request.setAttribute("vcpoints", vcpoints);
		request.setAttribute("tid", tid);
		request.setAttribute("key", key);
		request.setAttribute("pass", pass);
		return "pass2";
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
	
	public static void main(String[] args) {
		String offer_name="[招募] 平安陆金所-彩票没中不要紧,投资陆金所还能赚";
		String uid="fanlilichj5005452";
		String uid2="5005452";
		String vcpoints="13";
		String tid="v81d881a330ad431f487f82545016619";
		String key="shumili";
		String pass=SecurityUtils.md5(uid+vcpoints+tid+key);
		String pass2=SecurityUtils.md5(uid2+vcpoints+tid+key);
		System.out.println(pass);
		System.out.println(pass2);
	}
	
}
