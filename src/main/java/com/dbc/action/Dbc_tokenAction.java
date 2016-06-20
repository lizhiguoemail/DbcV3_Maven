/**
* @Project 源自 dbc
* @Title Dbc_tokenAction.java
* @Package com.dbc.action
* @Description token类action类
* @author caihuajun
* @date 2015-08-06
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
import com.dbc.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_token;
import com.dbc.service.Dbc_tokenService;
/**
* @ClassName Dbc_tokenAction
* @Description token类action类
* @author caihuajun
* @date 2015-08-06
*/
public class Dbc_tokenAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_token dbc_token;

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
        request.setAttribute("action","dbc_token");
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
            Dbc_tokenService dbc_tokenservice=(Dbc_tokenService) super.getInstence(request,"dbc_tokenservice");
            PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
            List pagelist=(List)dbc_tokenservice.selEntityByPage(Dbc_token.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
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
           logservice.saveLog(userinfo, ipaddress, "dbc_token", "list", "出现异常："+e.toString());
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
            	Dbc_tokenService dbc_tokenservice=(Dbc_tokenService) super.getInstence(request,"dbc_tokenservice");
        		Dbc_token dbc_token=(Dbc_token) dbc_tokenservice.selByOid(Dbc_token.class, id);
            	request.setAttribute("dbc_token", dbc_token);
            	request.setAttribute("isupdate", "1");
        }
        	return "addorupdate";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_token", "addorupdate", "出现异常："+e.toString());
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
            Dbc_tokenService dbc_tokenservice=(Dbc_tokenService) super.getInstence(request,"dbc_tokenservice");
            String isupdate=request.getParameter("isupdate");
            if("1".equals(isupdate)){
                dbc_token.setUpdatedate(nowdate);
                dbc_token.setUpdateuser(username);
                String sortcodestr=request.getParameter("sortcode");
                if(sortcodestr==null||"".equals(sortcodestr)){
                  Double sortcode=dbc_tokenservice.getSortCode_Double("Dbc_token");
                  dbc_token.setSortcode(sortcode);
                 }
                dbc_tokenservice.updateObject(dbc_token);
                //-----------------记录到日志---------------------------
                Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
                logservice.saveLog(userinfo, ipaddress, "dbc_token", "addorupdate", "修改token类");
                //-----------------记录日志结束--------------------------
            }
            else{
              dbc_token.setId(null);
              dbc_token.setCreatedate(nowdate);
              dbc_token.setCreateuser(username);
              String sortcodestr=request.getParameter("sortcode");
              if(sortcodestr==null||"".equals(sortcodestr)){
          	    Double sortcode=dbc_tokenservice.getSortCode_Double("Dbc_token");
          	    dbc_token.setSortcode(sortcode);
              }
              dbc_tokenservice.saveObject(dbc_token);
              //-----------------记录到日志---------------------------
              Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_token", "addorupdate", "增加token类");
              //-----------------记录日志结束--------------------------
            }
       		return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_token", "addorupdate", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title deletebyid
    * @Description 删除token类
    * @return 返回值
    */
    public String deletebyid(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String id=request.getParameter("id");
            Dbc_tokenService dbc_tokenservice=(Dbc_tokenService) super.getInstence(request,"dbc_tokenservice");
            dbc_tokenservice.deletebyfieldarr(Dbc_token.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_token", "deletebyid", "删除token类");
            //-----------------记录日志结束--------------------------
       	  return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_token", "deletebyid", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title delete
    * @Description 删除token类
    * @return 返回值
    */
    public String delete(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String[] ids=request.getParameterValues("checks");
            Dbc_tokenService dbc_tokenservice=(Dbc_tokenService) super.getInstence(request,"dbc_tokenservice");
            String[] setfieldnamearr=Dbcutil.getarr("deletemark");
            String[] setcontentarr=Dbcutil.getarr("1");
            dbc_tokenservice.setObjectbyids(Dbc_token.class, setfieldnamearr, setcontentarr, ids);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_token", "delete", "删除token类");
            //-----------------记录日志结束--------------------------
       	return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_token", "deletebyid", "出现异常："+e.toString());
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
        if(dbc_token == null){
          dbc_token = new Dbc_token();
        }
        return dbc_token;
     }

     public Dbc_token getDbc_token() {
        return dbc_token;
     }

     public void setDbc_token(Dbc_token dbc_token){ 
        this.dbc_token = dbc_token;
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