/**
* @Project 源自 dbc
* @Title Dbc_cityAction.java
* @Package com.dbc.action.common
* @Description 城市action类
* @author caihuajun
* @date 2015-04-28
* @version v2.0
*/
package com.dbc.action;

import java.io.IOException;

import com.dbc.util.Dbc_custom_constants;
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
import com.dbc.service.Dbc_provinceService;
import com.dbc.pojo.Dbc_community;
import com.dbc.pojo.Dbc_province;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_city;
import com.dbc.service.Dbc_cityService;
/**
* @ClassName Dbc_cityAction
* @Description 城市action类
* @author caihuajun
* @date 2015-04-28
*/
public class Dbc_cityAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_city dbc_city;

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
	        Dbc_cityService dbc_cityservice=(Dbc_cityService) super.getInstence(request,"dbc_cityservice");
	        List provincelist=dbc_cityservice.selEntity(Dbc_province.class, null, null, null, null, null);
            Map provinceMap=new HashMap();
            for(int i=0;i<provincelist.size();i++){
            	Dbc_province province=(Dbc_province) provincelist.get(i);
            	provinceMap.put(province.getId(), province);
            }
            request.setAttribute("provinceMap", provinceMap);
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        List pagelist=new ArrayList();
	        if("provincename".equals(sname)){
	        	String sqllen="select count(a.id) from Dbc_city a ,Dbc_province b where a.provinceid=b.id and b.provincename like '%"+svalue+"%'  and (a.deletemark is null or a.deletemark!='1') and (b.deletemark is null or b.deletemark!='1')";
	        	String sql="select a from Dbc_city a,Dbc_province b where a.provinceid=b.id and b.provincename like '%"+svalue+"%'  and (a.deletemark is null or a.deletemark!='1') and (b.deletemark is null or b.deletemark!='1') order by a.sortcode asc";
	        	pagelist=(List)dbc_cityservice.selEntityBySqlPage(sqllen,sql,pageparm);
	        }
	        else{
	        	 pagelist=(List)dbc_cityservice.selEntityByPage(Dbc_city.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("asc"));
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
            logservice.saveLog(userinfo, ipaddress, "dbc_city", "list", "出现异常:"+e.toString());
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
	        String provinceid=request.getParameter("provinceid");
	        Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence(request,"dbc_provinceservice");
	        List provincelist=dbc_provinceservice.selEntity(Dbc_province.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
	        request.setAttribute("provincelist", provincelist);
	        if(id!=null&&!"".equals(id)){
	        	Dbc_cityService dbc_cityservice=(Dbc_cityService) super.getInstence(request,"dbc_cityservice");
	        	Dbc_city dbc_city=(Dbc_city) dbc_cityservice.selByOid(Dbc_city.class, id);
	        	request.setAttribute("dbc_city", dbc_city);
	        	request.setAttribute("isupdate", "1");
	        }
	        if(provinceid!=null&&!"".equals(provinceid)){
	        	Dbc_province dbc_province=(Dbc_province) dbc_provinceservice.selByOid(Dbc_province.class, provinceid);
	        	request.setAttribute("dbc_province", dbc_province);
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_city", "toaddorupdate", "出现异常:"+e.toString());
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
	        Dbc_cityService dbc_cityservice=(Dbc_cityService) super.getInstence(request,"dbc_cityservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            dbc_city.setUpdatedate(nowdate);
	            dbc_city.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=dbc_cityservice.getSortCode_Double("Dbc_city");
	              dbc_city.setSortcode(sortcode);
	             }
	            dbc_cityservice.updateObject(dbc_city);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_city", "addorupdate", "修改城市");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          //String nowdateid=Dbcutil.getnowdateString("yyyyMMddhhmmss");
	          dbc_city.setId(UUID.randomUUID().toString());
	          dbc_city.setCreatedate(nowdate);
	          dbc_city.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=dbc_cityservice.getSortCode_Double("Dbc_city");
	          	dbc_city.setSortcode(sortcode);
	          }
	          dbc_cityservice.saveObject(dbc_city);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_city", "addorupdate", "增加城市");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_city", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除城市
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_cityService dbc_cityservice=(Dbc_cityService) super.getInstence(request,"dbc_cityservice");
	        dbc_cityservice.deletecity(id);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_city", "deletebyid", "删除城市");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_city", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title delete
    * @Description 删除城市
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_cityService dbc_cityservice=(Dbc_cityService) super.getInstence(request,"dbc_cityservice");
	        dbc_cityservice.deletecitys(ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_city", "delete", "删除城市");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_city", "delete", "出现异常:"+e.toString());
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
        if(dbc_city == null){
          dbc_city = new Dbc_city();
        }
        return dbc_city;
     }

     public Dbc_city getDbc_city() {
        return dbc_city;
     }

     public void setDbc_city(Dbc_city dbc_city){ 
        this.dbc_city = dbc_city;
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