/**
* @Project 源自 dbc
* @Title Dbc_districtAction.java
* @Package com.dbc.action.common
* @Description 地区action类
* @author caihuajun
* @date 2015-04-28
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
import com.dbc.pojo.Dbc_province;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_district;
import com.dbc.service.Dbc_districtService;
/**
* @ClassName Dbc_districtAction
* @Description 地区action类
* @author caihuajun
* @date 2015-04-28
*/
public class Dbc_districtAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_district dbc_district;

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
    	try{
	        Dbc_districtService dbc_districtservice=(Dbc_districtService) super.getInstence(request,"dbc_districtservice");
	        List provincelist=dbc_districtservice.selEntity(Dbc_province.class, null, null, null, null, null);
            Map provinceMap=new HashMap();
            for(int i=0;i<provincelist.size();i++){
            	Dbc_province province=(Dbc_province) provincelist.get(i);
            	provinceMap.put(province.getId(), province);
            }
            request.setAttribute("provinceMap", provinceMap);
            List citylist=dbc_districtservice.selEntity(Dbc_city.class, null, null, null, null, null);
            Map cityMap=new HashMap();
            for(int i=0;i<citylist.size();i++){
            	Dbc_city city=(Dbc_city) citylist.get(i);
            	cityMap.put(city.getId(), city);
            }
            request.setAttribute("cityMap", cityMap);
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        List pagelist=new ArrayList();
	        if("cityname".equals(sname)){
	        	String sqllen="select count(a.id) from Dbc_district a ,Dbc_city b where a.cityid=b.id and b.cityname like '%"+svalue+"%'  and (a.deletemark is null or a.deletemark!='1') and (b.deletemark is null or b.deletemark!='1')";
	        	String sql="select a from Dbc_district a,Dbc_city b where a.cityid=b.id and b.cityname like '%"+svalue+"%'  and (a.deletemark is null or a.deletemark!='1') and (b.deletemark is null or b.deletemark!='1') order by a.sortcode asc";
	        	pagelist=(List)dbc_districtservice.selEntityBySqlPage(sqllen,sql,pageparm);
	        }
	        else{
	        	pagelist=(List)dbc_districtservice.selEntityByPage(Dbc_district.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("asc"));
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
            logservice.saveLog(userinfo, ipaddress, "dbc_district", "list", "出现异常:"+e.toString());
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
	        Dbc_districtService dbc_districtservice=(Dbc_districtService) super.getInstence(request,"dbc_districtservice");
	       // Dbc_diquutil diquutil=new Dbc_diquutil();
	        //String cityData=diquutil.get_province_city_select();
	        //request.setAttribute("cityData", cityData);
	        if(id!=null&&!"".equals(id)){
	        	Dbc_district dbc_district=(Dbc_district) dbc_districtservice.selByOid(Dbc_district.class, id);
	        	request.setAttribute("dbc_district", dbc_district);
	        	request.setAttribute("isupdate", "1");
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_district", "toaddorupdate", "出现异常:"+e.toString());
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
	        Dbc_districtService dbc_districtservice=(Dbc_districtService) super.getInstence(request,"dbc_districtservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            dbc_district.setUpdatedate(nowdate);
	            dbc_district.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=dbc_districtservice.getSortCode_Double("Dbc_district");
	              dbc_district.setSortcode(sortcode);
	             }
	            dbc_districtservice.updateObject(dbc_district);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_district", "addorupdate", "修改地区");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          //String nowdateid=Dbcutil.getnowdateString("yyyyMMddhhmmss");
	          //dbc_district.setId(nowdateid);
	          dbc_district.setId(UUID.randomUUID().toString());
	          dbc_district.setCreatedate(nowdate);
	          dbc_district.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=dbc_districtservice.getSortCode_Double("Dbc_district");
	          	dbc_district.setSortcode(sortcode);
	          }
	          dbc_districtservice.saveObject(dbc_district);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_district", "addorupdate", "增加地区");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_district", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除地区
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_districtService dbc_districtservice=(Dbc_districtService) super.getInstence(request,"dbc_districtservice");
	        dbc_districtservice.deletedistrict(id);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_district", "deletebyid", "删除地区");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_district", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title delete
    * @Description 删除地区
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_districtService dbc_districtservice=(Dbc_districtService) super.getInstence(request,"dbc_districtservice");
            dbc_districtservice.deletedistricts(ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_district", "delete", "删除地区");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_district", "delete", "出现异常:"+e.toString());
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
        if(dbc_district == null){
          dbc_district = new Dbc_district();
        }
        return dbc_district;
     }

     public Dbc_district getDbc_district() {
        return dbc_district;
     }

     public void setDbc_district(Dbc_district dbc_district){ 
        this.dbc_district = dbc_district;
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