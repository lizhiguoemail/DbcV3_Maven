/**
* @Project 源自 dbc
* @Title Dbc_adAction.java
* @Package com.dbc.action.common
* @Description 广告action类
* @author caihuajun
* @date 2015-05-12
* @version v2.0
*/
package com.dbc.action;

import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.dbc.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_ad;
import com.dbc.service.Dbc_adService;
/**
* @ClassName Dbc_adAction
* @Description 广告action类
* @author caihuajun
* @date 2015-05-12
*/
public class Dbc_adAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 1L; 
	
	private Logger logger  =  Logger.getLogger(Dbc_adAction.class); 

    private String methode;

    private Dbc_ad dbc_ad;

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
      try{
            Dbc_adService dbc_adservice=(Dbc_adService) super.getInstence(request,"dbc_adservice");
            PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
            List pagelist=(List)dbc_adservice.selEntityByPage(Dbc_ad.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
            List list = (List) pagelist.get(0);
            PageParm pageParm=(PageParm) pagelist.get(1);
            request.setAttribute("list", list);
            request.setAttribute("pageParm", pageParm);
            request.setAttribute("sname", sname);
            request.setAttribute("svalue", svalue);
            request.setAttribute("svalue", svalue);
            return "list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_ad", "list", "出现异常："+e.getMessage());
           logger.error("error",e);
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
      try{
            String id=request.getParameter("id");
            if(id!=null&&!"".equals(id)){
            	Dbc_adService dbc_adservice=(Dbc_adService) super.getInstence(request,"dbc_adservice");
            	Dbc_ad dbc_ad=(Dbc_ad) dbc_adservice.selByOid(Dbc_ad.class, id);
            	request.setAttribute("dbc_ad", dbc_ad);
            	request.setAttribute("isupdate", "1");
        }
        return "addorupdate";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_ad", "addorupdate", "出现异常："+e.toString());
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
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
            String username=userinfo.getUsername();
            Dbc_adService dbc_adservice=(Dbc_adService) super.getInstence(request,"dbc_adservice");
            String isupdate=request.getParameter("isupdate");
            if("1".equals(isupdate)){
                dbc_ad.setUpdatedate(nowdate);
                dbc_ad.setUpdateuser(username);
                String sortcodestr=request.getParameter("sortcode");
                if(sortcodestr==null||"".equals(sortcodestr)){
                  Double sortcode=dbc_adservice.getSortCode_Double("Dbc_ad");
                  dbc_ad.setSortcode(sortcode);
                 }
                dbc_adservice.updateObject(dbc_ad);
                //-----------------记录到日志---------------------------
                Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
                logservice.saveLog(userinfo, ipaddress, "dbc_ad", "addorupdate", "修改广告");
                //-----------------记录日志结束--------------------------
            }
            else{
              dbc_ad.setId(null);
              dbc_ad.setCreatedate(nowdate);
              dbc_ad.setCreateuser(username);
              String sortcodestr=request.getParameter("sortcode");
              if(sortcodestr==null||"".equals(sortcodestr)){
          	    Double sortcode=dbc_adservice.getSortCode_Double("Dbc_ad");
          	    dbc_ad.setSortcode(sortcode);
              }
              dbc_adservice.saveObject(dbc_ad);
              //-----------------记录到日志---------------------------
              Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_ad", "addorupdate", "增加广告");
              //-----------------记录日志结束--------------------------
            }
            return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_ad", "addorupdate", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title deletebyid
    * @Description 删除广告
    * @return 返回值
    */
    public String deletebyid(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String id=request.getParameter("id");
            Dbc_adService dbc_adservice=(Dbc_adService) super.getInstence(request,"dbc_adservice");
            dbc_adservice.deletebyfieldarr(Dbc_ad.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_ad", "deletebyid", "删除广告");
            //-----------------记录日志结束--------------------------
       	  return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_ad", "deletebyid", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title delete
    * @Description 删除广告
    * @return 返回值
    */
    public String delete(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String[] ids=request.getParameterValues("checks");
            Dbc_adService dbc_adservice=(Dbc_adService) super.getInstence(request,"dbc_adservice");
            String[] setfieldnamearr=Dbcutil.getarr("deletemark");
            String[] setcontentarr=Dbcutil.getarr("1");
            dbc_adservice.setObjectbyids(Dbc_ad.class, setfieldnamearr, setcontentarr, ids);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_ad", "delete", "删除广告");
            //-----------------记录日志结束--------------------------
       	return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_ad", "deletebyid", "出现异常："+e.toString());
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
        if(dbc_ad == null){
          dbc_ad = new Dbc_ad();
        }
        return dbc_ad;
     }

     public Dbc_ad getDbc_ad() {
        return dbc_ad;
     }

     public void setDbc_ad(Dbc_ad dbc_ad){ 
        this.dbc_ad = dbc_ad;
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