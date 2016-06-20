/**
* @Project 源自 dbc
* @Title Dbc_slidesAction.java
* @Package com.dbc.action
* @Description 幻灯片action类
* @author caihuajun
* @date 2015-06-23
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
import com.dbc.pojo.Dbc_log;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_slides;
import com.dbc.service.Dbc_slidesService;
/**
* @ClassName Dbc_slidesAction
* @Description 幻灯片action类
* @author caihuajun
* @date 2015-06-23
*/
public class Dbc_slidesAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_slides dbc_slides;

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
        request.setAttribute("action","dbc_slides");
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
        Dbc_slidesService dbc_slidesservice=(Dbc_slidesService) super.getInstence("dbc_slidesservice");
        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
        List pagelist=(List)dbc_slidesservice.selEntityByPage(Dbc_slides.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
        List list = (List) pagelist.get(0);
        PageParm pageParm=(PageParm) pagelist.get(1);
        request.setAttribute("list", list);
        request.setAttribute("pageParm", pageParm);
        request.setAttribute("sname", sname);
        request.setAttribute("svalue", svalue);
        return "list";
       } catch (Exception e) {
    	e.printStackTrace();
    	return null;
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
        List huandengpianlist=Dbcutil.getBase_typelist("huandengpian");
        request.setAttribute("huandengpian", huandengpianlist);
        if(id!=null&&!"".equals(id)){
        	Dbc_slidesService dbc_slidesservice=(Dbc_slidesService) super.getInstence(request,"dbc_slidesservice");
    		Dbc_slides dbc_slides=(Dbc_slides) dbc_slidesservice.selByOid(Dbc_slides.class, id);
        	request.setAttribute("dbc_slides", dbc_slides);
        	request.setAttribute("isupdate", "1");
        }
    	return "addorupdate";
       } catch (Exception e) {
           e.printStackTrace();
           return null;
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
            Dbc_slidesService dbc_slidesservice=(Dbc_slidesService) super.getInstence(request,"dbc_slidesservice");
            String isupdate=request.getParameter("isupdate");
            if("1".equals(isupdate)){
                dbc_slides.setUpdatedate(nowdate);
                dbc_slides.setUpdateuser(username);
                String sortcodestr=request.getParameter("sortcode");
                if(sortcodestr==null||"".equals(sortcodestr)){
                  Double sortcode=dbc_slidesservice.getSortCode_Double("Dbc_slides");
                  dbc_slides.setSortcode(sortcode);
                 }
                Dbc_log dbc_log=new Dbc_log(userinfo,ipaddress,"dbc_slides","addorupdate","修改幻灯片");
                dbc_slidesservice.updateObject(dbc_slides,dbc_log);
            }
            else{
              dbc_slides.setId(null);
              dbc_slides.setCreatedate(nowdate);
              dbc_slides.setCreateuser(username);
              String sortcodestr=request.getParameter("sortcode");
              if(sortcodestr==null||"".equals(sortcodestr)){
          	    Double sortcode=dbc_slidesservice.getSortCode_Double("Dbc_slides");
          	    dbc_slides.setSortcode(sortcode);
              }
              Dbc_log dbc_log=new Dbc_log(userinfo,ipaddress,"dbc_slides","addorupdate","增加幻灯片");
              dbc_slidesservice.saveObject(dbc_slides,dbc_log);
            }
       		return "redirect-list";
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }

    /**
    * @Title deletebyid
    * @Description 删除幻灯片
    * @return 返回值
    */
    public String deletebyid(){
      try{
        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
        String ipaddress=Dbcutil.getIpByrequest(request);
        String id=request.getParameter("id");
        Dbc_slidesService dbc_slidesservice=(Dbc_slidesService) super.getInstence(request,"dbc_slidesservice");
        dbc_slidesservice.deletebyfieldarr(Dbc_slides.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
        Dbc_log dbc_log=new Dbc_log(userinfo, ipaddress, "dbc_slides", "deletebyid", "删除幻灯片");
        dbc_slidesservice.deletebyids(Dbc_slides.class,Dbcutil.getarr("id"),true,dbc_log);
       	return "redirect-list";
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }

    /**
    * @Title delete
    * @Description 删除幻灯片
    * @return 返回值
    */
    public String delete(){
      try{
        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
        String ipaddress=Dbcutil.getIpByrequest(request);
        String[] ids=request.getParameterValues("checks");
        Dbc_slidesService dbc_slidesservice=(Dbc_slidesService) super.getInstence(request,"dbc_slidesservice");
        Dbc_log dbc_log=new Dbc_log(userinfo, ipaddress, "dbc_slides", "delete", "删除幻灯片");
        dbc_slidesservice.deletebyids(Dbc_slides.class,ids,true,dbc_log);
        return "redirect-list";
       } catch (Exception e) {
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
        if(dbc_slides == null){
          dbc_slides = new Dbc_slides();
        }
        return dbc_slides;
     }

     public Dbc_slides getDbc_slides() {
        return dbc_slides;
     }

     public void setDbc_slides(Dbc_slides dbc_slides){ 
        this.dbc_slides = dbc_slides;
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