/**
* @Project 源自 dbc
* @Title Dbc_communityAction.java
* @Package com.dbc.action
* @Description 社区action类
* @author caihuajun
* @date 2015-07-24
* @version v2.0
*/
package com.dbc.action;

import java.io.IOException;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbc_diquutil;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.dbc.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.pojo.Dbc_city;
import com.dbc.pojo.Dbc_district;
import com.dbc.pojo.Dbc_province;
import com.dbc.pojo.Dbc_street;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_community;
import com.dbc.service.Dbc_communityService;
/**
* @ClassName Dbc_communityAction
* @Description 社区action类
* @author caihuajun
* @date 2015-07-24
*/
public class Dbc_communityAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_community dbc_community;

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
        request.setAttribute("action","dbc_community");
        request.setAttribute("methode",methode);
        request.setAttribute("exception", "Dbc_custom_constants.nomethode");
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
            Dbc_communityService dbc_communityservice=(Dbc_communityService) super.getInstence(request,"dbc_communityservice");
            List provincelist=dbc_communityservice.selEntity(Dbc_province.class, null, null, null, null, null);
            Map provinceMap=new HashMap();
            for(int i=0;i<provincelist.size();i++){
            	Dbc_province province=(Dbc_province) provincelist.get(i);
            	provinceMap.put(province.getId(), province);
            }
            request.setAttribute("provinceMap", provinceMap);
            List citylist=dbc_communityservice.selEntity(Dbc_city.class, null, null, null, null, null);
            Map cityMap=new HashMap();
            for(int i=0;i<citylist.size();i++){
            	Dbc_city city=(Dbc_city) citylist.get(i);
            	cityMap.put(city.getId(), city);
            }
            request.setAttribute("cityMap", cityMap);
            List districtlist=dbc_communityservice.selEntity(Dbc_district.class, null, null, null, null, null);
            Map districtMap=new HashMap();
            for(int i=0;i<districtlist.size();i++){
            	Dbc_district district=(Dbc_district) districtlist.get(i);
            	districtMap.put(district.getId(), district);
            }
            request.setAttribute("districtMap", districtMap);
            List streetlist=dbc_communityservice.selEntity(Dbc_street.class, null, null, null, null, null);
            Map streetMap=new HashMap();
            for(int i=0;i<streetlist.size();i++){
            	Dbc_street street=(Dbc_street) streetlist.get(i);
            	streetMap.put(street.getId(), street);
            }
            request.setAttribute("streetMap", streetMap);
            PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
            List pagelist=new ArrayList();
	        if("streetname".equals(sname)){
	        	String sqllen="select count(a.id) from Dbc_community a ,Dbc_street b where a.streetid=b.id and b.streetname like '%"+svalue+"%'  and (a.deletemark is null or a.deletemark!='1') and (b.deletemark is null or b.deletemark!='1')";
	        	String sql="select a from Dbc_community a ,Dbc_street b where a.streetid=b.id and b.streetname like '%"+svalue+"%'  and (a.deletemark is null or a.deletemark!='1') and (b.deletemark is null or b.deletemark!='1') order by a.sortcode asc";
	        	pagelist=(List)dbc_communityservice.selEntityBySqlPage(sqllen,sql,pageparm);
	        }
	        else{
	        	pagelist=(List)dbc_communityservice.selEntityByPage(Dbc_community.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("asc"));
	        }
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
           logservice.saveLog(userinfo, ipaddress, "dbc_community", "list", "出现异常："+e.toString());
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
            Dbc_communityService dbc_communityservice=(Dbc_communityService) super.getInstence(request,"dbc_communityservice");
           // Dbc_diquutil diquutil=new Dbc_diquutil();
	        //String streetData=diquutil.get_province_street_select();
	        //request.setAttribute("streetData", streetData);
            if(id!=null&&!"".equals(id)){
        		Dbc_community dbc_community=(Dbc_community) dbc_communityservice.selByOid(Dbc_community.class, id);
            	request.setAttribute("dbc_community", dbc_community);
            	request.setAttribute("isupdate", "1");
        }
        	return "addorupdate";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_community", "addorupdate", "出现异常："+e.toString());
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
            Dbc_communityService dbc_communityservice=(Dbc_communityService) super.getInstence(request,"dbc_communityservice");
            String isupdate=request.getParameter("isupdate");
            if("1".equals(isupdate)){
                dbc_community.setUpdatedate(nowdate);
                dbc_community.setUpdateuser(username);
                String sortcodestr=request.getParameter("sortcode");
                if(sortcodestr==null||"".equals(sortcodestr)){
                  Double sortcode=dbc_communityservice.getSortCode_Double("Dbc_community");
                  dbc_community.setSortcode(sortcode);
                 }
                dbc_communityservice.updateObject(dbc_community);
                //-----------------记录到日志---------------------------
                Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
                logservice.saveLog(userinfo, ipaddress, "dbc_community", "addorupdate", "修改社区");
                //-----------------记录日志结束--------------------------
            }
            else{
              //String nowdateid=Dbcutil.getnowdateString("yyyyMMddhhmmss");
              dbc_community.setId(UUID.randomUUID().toString());
              //dbc_community.setId(nowdateid);
              dbc_community.setCreatedate(nowdate);
              dbc_community.setCreateuser(username);
              String sortcodestr=request.getParameter("sortcode");
              if(sortcodestr==null||"".equals(sortcodestr)){
          	    Double sortcode=dbc_communityservice.getSortCode_Double("Dbc_community");
          	    dbc_community.setSortcode(sortcode);
              }
              dbc_communityservice.saveObject(dbc_community);
              //-----------------记录到日志---------------------------
              Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_community", "addorupdate", "增加社区");
              //-----------------记录日志结束--------------------------
            }
       		return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_community", "addorupdate", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title deletebyid
    * @Description 删除社区
    * @return 返回值
    */
    public String deletebyid(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String id=request.getParameter("id");
            Dbc_communityService dbc_communityservice=(Dbc_communityService) super.getInstence(request,"dbc_communityservice");
            dbc_communityservice.deletebyfieldarr(Dbc_community.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_community", "deletebyid", "删除社区");
            //-----------------记录日志结束--------------------------
       	  return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_community", "deletebyid", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title delete
    * @Description 删除社区
    * @return 返回值
    */
    public String delete(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String[] ids=request.getParameterValues("checks");
            Dbc_communityService dbc_communityservice=(Dbc_communityService) super.getInstence(request,"dbc_communityservice");
            String[] setfieldnamearr=Dbcutil.getarr("deletemark");
            String[] setcontentarr=Dbcutil.getarr("1");
            dbc_communityservice.setObjectbyids(Dbc_community.class, setfieldnamearr, setcontentarr, ids);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_community", "delete", "删除社区");
            //-----------------记录日志结束--------------------------
       	return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_community", "deletebyid", "出现异常："+e.toString());
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
        if(dbc_community == null){
          dbc_community = new Dbc_community();
        }
        return dbc_community;
     }

     public Dbc_community getDbc_community() {
        return dbc_community;
     }

     public void setDbc_community(Dbc_community dbc_community){ 
        this.dbc_community = dbc_community;
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