package com.dbc.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.dbc.pojo.Dbc_log;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_userinfoService;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import com.opensymphony.xwork2.ModelDriven;

public class Dbc_logAction extends BaseAction implements ModelDriven{
	
	private String methode;

	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String nowpageString;
	
	private String gotopagetype;
	
	private String gotopageString;
	
	private String pagesizeString="15";
	
	private Dbc_log log;
	
	private String sname;
	
	private String svalue;
	
	@Override
	public String execute(){
		String returnstr="";
		request = ServletActionContext.getRequest ();
		response= ServletActionContext.getResponse();
		if("list".equals(methode)){
			returnstr=this.list();
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
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
			PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
			List pagelist=(List)logservice.selEntityByPage(Dbc_log.class, Dbcutil.getarr(sname), Dbcutil.getarr(svalue), null, pageparm, Dbcutil.getarr("createdate"), Dbcutil.getarr("desc"));
			List list = (List) pagelist.get(0);
			PageParm pageParm=(PageParm) pagelist.get(1);
			request.setAttribute("listlog", list);
		    request.setAttribute("pageParm", pageParm);
		    request.setAttribute("sname", sname);
		    request.setAttribute("svalue", svalue);
			return "list";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_log", "list", "出现异常:"+e.toString());
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
		if(log == null){
			log = new Dbc_log();
	       }
	       return log;
	}

	public Dbc_log getLog() {
		return log;
	}

	public void setLog(Dbc_log log) {
		this.log = log;
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
