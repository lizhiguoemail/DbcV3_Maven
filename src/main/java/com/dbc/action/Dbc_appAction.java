/**
* @Project 源自 dbc
* @Title AppAction.java
* @Package com.dbc.action.common
* @Description 应用表action类
* @author caihuajun
* @date 2013-10-29
* @version v2.0
*/
package com.dbc.action;

import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_app;
import com.dbc.service.Dbc_appService;
/**
* @ClassName AppAction
* @Description 应用表action类
* @author caihuajun
* @date 2013-10-29
*/
public class Dbc_appAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_app app;

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
    * @Description 进入列表页面
    * @return string
    */
    public String list(){
    	try {
    		Dbc_appService appservice=(Dbc_appService) super.getInstence("dbc_appservice");
            PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
            List pagelist=(List)appservice.selEntityByPage(Dbc_app.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
            List list = (List) pagelist.get(0);
            PageParm pageParm=(PageParm) pagelist.get(1);
            request.setAttribute("list", list);
            request.setAttribute("pageParm", pageParm);
            request.setAttribute("sname", sname);
            request.setAttribute("svalue", svalue);
            return "list";
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_app", "list", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title toaddorupdate
    * @Description 进入添加或修改页面
    * @return string  返回值
    */
    public String toaddorupdate(){
    	try {
	        String id=request.getParameter("id");
	        List apptypelist=Dbcutil.getBase_typelist("apptype");
	        List sqfslist=Dbcutil.getBase_typelist("sqfs");
	        List tjdjlist=Dbcutil.getBase_typelist("tjdj");
	        request.setAttribute("apptypelist", apptypelist);
	        request.setAttribute("sqfslist", sqfslist);
	        request.setAttribute("tjdjlist", tjdjlist);
	        if(id!=null&&!"".equals(id)){
	        	Dbc_appService appservice=(Dbc_appService) super.getInstence("dbc_appservice");
	        	Dbc_app app=(Dbc_app) appservice.selByOid(Dbc_app.class, id);
	        	request.setAttribute("app", app);
	        	request.setAttribute("isupdate", "1");
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_app", "toaddorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title addorupdate
    * @Description 添加或修改
    * @return string 返回值
    */
    public String addorupdate(){
    	try {
	       // Userinfo userinfo=(Userinfo) request.getSession().getAttribute("backstage_user");
	    	Map session=ActionContext.getContext().getSession();
	    	Dbc_userinfo userinfo=(Dbc_userinfo) session.get("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
	        String username=userinfo.getUsername();
	        Dbc_appService appservice=(Dbc_appService) super.getInstence("dbc_appservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            app.setUpdatedate(nowdate);
	            app.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=appservice.getSortCode_Double("Dbc_app");
	              app.setSortcode(sortcode);
	             }
	            appservice.saveObject(app);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_app", "addorupdate", "修改应用表");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          app.setId(null);
	          app.setCreatedate(nowdate);
	          app.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=appservice.getSortCode_Double("Dbc_app");
	          	app.setSortcode(sortcode);
	          }
	          appservice.saveObject(app);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_app", "addorupdate", "增加应用表");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_app", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除应用表
    * @return 返回值
    */
    public String deletebyid(){
    	try {
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_appService appservice=(Dbc_appService) super.getInstence("dbc_appservice");
	        appservice.deletebyfieldarr(Dbc_app.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_app", "deletebyid", "删除应用表");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_app", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    /**
     * @Title delete
     * @Description 删除应用表
     * @return 返回值
     */
    public String delete(){
    	try {
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_appService appservice=(Dbc_appService) super.getInstence("dbc_appservice");
	        String[] setfieldnamearr=Dbcutil.getarr("deletemark");
	        String[] setcontentarr=Dbcutil.getarr("1");
	        appservice.setObjectbyids(Dbc_app.class, setfieldnamearr, setcontentarr, ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_app", "delete", "删除应用表");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_app", "delete", "出现异常:"+e.toString());
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

     public Object getModel(){
        if(app == null){
          app = new Dbc_app();
        }
        return app;
     }

     public Dbc_app getApp() {
        return app;
     }

     public void setApp(Dbc_app app){ 
        this.app = app;
     }
     public String getNowpageString(){
        return nowpageString;
     }

     public void setNowpageString(String nowpageString){
        this.nowpageString = nowpageString;
     }

     public String getGotopagetype(){
       return gotopagetype;
     }

     public void setGotopagetype(String gotopagetype){
       this.gotopagetype = gotopagetype;
     }

     public String getGotopageString(){
       return gotopageString;
     }

     public void setGotopageString(String gotopageString){
       this.gotopageString = gotopageString;
     }

     public String getSname(){
       return sname;
     }

     public void setSname(String sname){
       this.sname = sname;
     }

     public String getSvalue(){
       return svalue;
     }

     public void setSvalue(String svalue){
       this.svalue = svalue;
     }

}