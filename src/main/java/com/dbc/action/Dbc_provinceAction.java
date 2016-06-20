/**
* @Project 源自 dbc
* @Title Dbc_provinceAction.java
* @Package com.dbc.action.common
* @Description 省份action类
* @author caihuajun
* @date 2015-04-27
* @version v2.0
*/
package com.dbc.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbc_diquutil;
import com.dbc.util.Dbcutil;
import com.dbc.util.FileUtil;
import com.dbc.util.PageParm;
import com.dbc.util.Zip;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.dbc.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.pojo.Dbc_city;
import com.dbc.pojo.Dbc_community;
import com.dbc.pojo.Dbc_district;
import com.dbc.pojo.Dbc_street;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_province;
import com.dbc.service.Dbc_provinceService;
/**
* @ClassName Dbc_provinceAction
* @Description 省份action类
* @author caihuajun
* @date 2015-04-27
*/
public class Dbc_provinceAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_province dbc_province;

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
      else if("tomanage".equals(methode)){
          returnstr=this.tomanage();
       }
      else if("adddiqubyxml".equals(methode)){
          returnstr=this.adddiqubyxml();
       }
      else if("creatediqujs".equals(methode)){
          returnstr=this.creatediqujs();
       }
      else if("exportdiquxml".equals(methode)){
          returnstr=this.exportdiquxml();
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
	        Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence(request,"dbc_provinceservice");
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        List pagelist=(List)dbc_provinceservice.selEntityByPage(Dbc_province.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("asc"));
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
            logservice.saveLog(userinfo, ipaddress, "dbc_province", "list", "出现异常:"+e.toString());
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
	        	Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence(request,"dbc_provinceservice");
	        	Dbc_province dbc_province=(Dbc_province) dbc_provinceservice.selByOid(Dbc_province.class, id);
	        	request.setAttribute("dbc_province", dbc_province);
	        	request.setAttribute("isupdate", "1");
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_province", "toaddorupdate", "出现异常:"+e.toString());
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
	        Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence(request,"dbc_provinceservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            dbc_province.setUpdatedate(nowdate);
	            dbc_province.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=dbc_provinceservice.getSortCode_Double("Dbc_province");
	              dbc_province.setSortcode(sortcode);
	             }
	            dbc_provinceservice.updateObject(dbc_province);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_province", "addorupdate", "修改省份");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          //String nowdateid=Dbcutil.getnowdateString("yyyyMMddhhmmss");
	          //dbc_province.setId(nowdateid);
	          dbc_province.setId(UUID.randomUUID().toString());
	          dbc_province.setCreatedate(nowdate);
	          dbc_province.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=dbc_provinceservice.getSortCode_Double("Dbc_province");
	          	dbc_province.setSortcode(sortcode);
	          }
	          dbc_provinceservice.saveObject(dbc_province);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_province", "addorupdate", "增加省份");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_province", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除省份
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence(request,"dbc_provinceservice");
	        dbc_provinceservice.deleteprovince(id);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_province", "deletebyid", "删除省份");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_province", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title delete
    * @Description 删除省份
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence(request,"dbc_provinceservice");
	        dbc_provinceservice.deleteprovinces(ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_province", "delete", "删除省份");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_province", "delete", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    /**
     * @Title adddiqubyxml
     * @Description 导入数据
     * @return 返回值
     */
     public String tomanage(){
    	 return "manage";
     }
     
     /**
      * @Title adddiqubyxml
      * @Description 导入数据
      * @return 返回值
      */
      public String adddiqubyxml(){
     	 try{
 	         Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
 	         String ipaddress=Dbcutil.getIpByrequest(request);
 	         Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence("dbc_provinceservice");
 	         dbc_provinceservice.adddiqubyxml(request);
 	        /* 下面的代码是以前没有关联的时候关联起来
 	         dbc_provinceservice.updatebycity();
 	         dbc_provinceservice.updatebydistrict();
 	         dbc_provinceservice.updatebystreet();
 	         */
 	         //-----------------记录到日志---------------------------
 	         Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
 	         logservice.saveLog(userinfo, ipaddress, "dbc_province", "adddiqubyxml", "批量导入省份、城市、地区等");
 	         //-----------------记录日志结束--------------------------
 	         return "redirect-list";
     	 } catch (Exception e) {
  			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_province", "adddiqubyxml", "出现异常:"+e.toString());
  			e.printStackTrace();
  			return "Exception";
  		}
      }
    
     
     /**
      * @Title exportdiquxml
      * @Description 导出地区xml格式的数据
      * @return 返回值
      */
      public String exportdiquxml(){
    	  try {
    	  Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	      String ipaddress=Dbcutil.getIpByrequest(request);
		  String separ = File.separator;
		  FileUtil c=new FileUtil();
		  String province_path=request.getSession().getServletContext().getRealPath(separ)+"export"+separ+"diqu"+separ+"Provinces.xml";
		  String city_path=request.getSession().getServletContext().getRealPath(separ)+"export"+separ+"diqu"+separ+"Citys.xml";
		  String district_path=request.getSession().getServletContext().getRealPath(separ)+"export"+separ+"diqu"+separ+"Districts.xml";
		  String street_path=request.getSession().getServletContext().getRealPath(separ)+"export"+separ+"diqu"+separ+"Streets.xml";
		  String community_path=request.getSession().getServletContext().getRealPath(separ)+"export"+separ+"diqu"+separ+"Communitys.xml";
		  OutputStreamWriter province_out = new OutputStreamWriter(new FileOutputStream(province_path),"UTF-8");
		  OutputStreamWriter city_out = new OutputStreamWriter(new FileOutputStream(city_path),"UTF-8");
		  OutputStreamWriter district_out = new OutputStreamWriter(new FileOutputStream(district_path),"UTF-8");
		  OutputStreamWriter street_out = new OutputStreamWriter(new FileOutputStream(street_path),"UTF-8");
		  OutputStreamWriter community_out = new OutputStreamWriter(new FileOutputStream(community_path),"UTF-8");
		  BufferedWriter bwprovince = new BufferedWriter(province_out);
		  BufferedWriter bwcity = new BufferedWriter(city_out);  
		  BufferedWriter bwdistrict = new BufferedWriter(district_out);  
		  BufferedWriter bwstreet = new BufferedWriter(street_out);  
		  BufferedWriter bwcommunity = new BufferedWriter(community_out);  
		  String provincestr="<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n<Provinces>\r\n";
		  String citystr="<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n<Cities>\r\n";
		  String districtstr="<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n<Districts>\r\n";
		  String streetstr="<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n<Streets>\r\n";
		  String communitystr="<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n<Communitys>\r\n";
		  Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) super.getInstence(request,"dbc_provinceservice");
	      List provinces=dbc_provinceservice.selEntity(Dbc_province.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
	      for(int i=0;i<provinces.size();i++){
	    	  Dbc_province p=(Dbc_province) provinces.get(i);
	    	  provincestr=provincestr+"  <Province ID=\""+p.getId()+"\" ProvinceName=\""+p.getProvincename()+"\" sortcode=\""+p.getSortcode()+"\">"+p.getProvincename()+"</Province>\r\n";
	      }
	      provincestr=provincestr+"</Provinces>";
	      bwprovince.write(provincestr);
		  bwprovince.close();
	      
	      List citys=dbc_provinceservice.selEntity(Dbc_city.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
	      for(int i=0;i<citys.size();i++){
	    	  Dbc_city city=(Dbc_city) citys.get(i);
	    	  citystr=citystr+"  <City ID=\""+city.getId()+"\" CityName=\""+city.getCityname()+"\" PID=\""+city.getProvinceid()+"\"  ZipCode=\""+city.getZipcode()+"\" sortcode=\""+city.getSortcode()+"\">"+city.getCityname()+"</City>\r\n";
	      }
	      citystr=citystr+"</Cities>";
	      bwcity.write(citystr);
	      bwcity.close();
	      
	      List districts=dbc_provinceservice.selEntity(Dbc_district.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
	      for(int i=0;i<districts.size();i++){
	    	  Dbc_district district=(Dbc_district) districts.get(i);
	    	  districtstr=districtstr+"  <District ID=\""+district.getId()+"\" DistrictName=\""+district.getDistrictname()+"\" CID=\""+district.getCityid()+"\" PID=\""+district.getProvinceid()+"\" sortcode=\""+district.getSortcode()+"\">"+district.getDistrictname()+"</District>\r\n";
	      }
	      districtstr=districtstr+"</Districts>";
	      bwdistrict.write(districtstr);
	      bwdistrict.close();
	      
	      List streets=dbc_provinceservice.selEntity(Dbc_street.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
	      for(int i=0;i<streets.size();i++){
	    	  Dbc_street street=(Dbc_street) streets.get(i);
	    	  streetstr=streetstr+"  <Street ID=\""+street.getId()+"\" StreetName=\""+street.getStreetname()+"\"  DID=\""+street.getDistrictid()+"\"  CID=\""+street.getCityid()+"\"  PID=\""+street.getProvinceid()+"\" sortcode=\""+street.getSortcode()+"\">"+street.getStreetname()+"</Street>\r\n";
	      }
	      streetstr=streetstr+"</Streets>";
	      bwstreet.write(streetstr);
	      bwstreet.close();
	      
	      List communitys=dbc_provinceservice.selEntity(Dbc_community.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
	      for(int i=0;i<communitys.size();i++){
	    	  Dbc_community community=(Dbc_community) communitys.get(i);
	    	  communitystr=communitystr+"  <Community ID=\""+community.getId()+"\" CommunityName=\""+community.getCommunityname()+"\" SID=\""+community.getStreetid()+"\" DID=\""+community.getDistrictid()+"\" CID=\""+community.getCityid()+"\" PID=\""+community.getProvinceid()+"\" sortcode=\""+community.getSortcode()+"\">"+community.getCommunityname()+"</Community>\r\n";
	      }
	      communitystr=communitystr+"</Communitys>";
	      bwcommunity.write(communitystr);
	      bwcommunity.close();
	      
	      Zip zip=new Zip();
		  String zipPath = request.getSession().getServletContext().getRealPath(separ)+"export"+separ+"diqu"+separ+"diqu.zip";      
	      File f1=new File(province_path);
          File f2=new File(city_path);
          File f3=new File(district_path);
          File f4=new File(street_path);
          File f5=new File(community_path);
          File[] srcfile={f1,f2,f3,f4,f5};
          File zipfile=new File(zipPath);
          zip.zipFiles(srcfile, zipfile);
	      request.setAttribute("msg", "导出成功！");
	      return "export_diqu_success";
   		} catch (Exception e) {
   		// TODO Auto-generated catch block
     		 Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
   		    String ipaddress=Dbcutil.getIpByrequest(request);
   			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
             logservice.saveLog(userinfo, ipaddress, "dbc_province", "exportdiquxml", "出现异常:"+e.toString());
   			e.printStackTrace();
   			return "Exception";
		}
      }
    
     /**
      * @Title creatediqujs
      * @Description 导入数据
      * @return 返回值
      */
      public String creatediqujs(){
    	 try {
    		 Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	         String ipaddress=Dbcutil.getIpByrequest(request);
			 String separ = File.separator;
			 Dbc_diquutil diquutil=new Dbc_diquutil();
			 FileUtil c=new FileUtil();
			 //生成province_city的js文件
			 String province_city_path=request.getSession().getServletContext().getRealPath(separ)+"data"+separ+"js"+separ+"diqu"+separ+"province_city.js";
			 OutputStreamWriter province_cityout = new OutputStreamWriter(new FileOutputStream(province_city_path),"UTF-8");
			 BufferedWriter bwprovince_city = new BufferedWriter(province_cityout);  
			 String province_citystr="var province_citydata=";
		     String province_cityData=diquutil.get_province_city_select();
		     province_citystr=province_citystr+province_cityData+";";
		     bwprovince_city.write(province_citystr);
		     bwprovince_city.close();
		   //生成province_district的js文件
		     String province_district_path=request.getSession().getServletContext().getRealPath(separ)+"data"+separ+"js"+separ+"diqu"+separ+"province_district.js";
			 OutputStreamWriter province_districtout = new OutputStreamWriter(new FileOutputStream(province_district_path),"UTF-8");
			 BufferedWriter bwprovince_district = new BufferedWriter(province_districtout);  
			 String province_districtstr="var province_districtdata=";
		     String province_districtData=diquutil.get_province_district_select();
		     province_districtstr=province_districtstr+province_districtData+";";
		     bwprovince_district.write(province_districtstr);
		     bwprovince_district.close();
		   //生成province_street的js文件
		     String province_street_path=request.getSession().getServletContext().getRealPath(separ)+"data"+separ+"js"+separ+"diqu"+separ+"province_street.js";
			 OutputStreamWriter province_streetout = new OutputStreamWriter(new FileOutputStream(province_street_path),"UTF-8");
			 BufferedWriter bwprovince_street = new BufferedWriter(province_streetout);  
			 String province_streetstr="var province_streetdata=";
		     String province_streetData=diquutil.get_province_street_select();
		     province_streetstr=province_streetstr+province_streetData+";";
		     bwprovince_street.write(province_streetstr);
		     bwprovince_street.close();
		   //生成province_community的js文件
		     String province_community_path=request.getSession().getServletContext().getRealPath(separ)+"data"+separ+"js"+separ+"diqu"+separ+"province_community.js";
			 OutputStreamWriter province_communityout = new OutputStreamWriter(new FileOutputStream(province_community_path),"UTF-8");
			 BufferedWriter bwprovince_community = new BufferedWriter(province_communityout);  
			 String province_communitystr="var province_communitydata=";
		     String province_communityData=diquutil.get_province_community_select();
		     province_communitystr=province_communitystr+province_communityData+";";
		     bwprovince_community.write(province_communitystr);
		     bwprovince_community.close();
		   //-----------------记录到日志---------------------------
	         Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	         logservice.saveLog(userinfo, ipaddress, "dbc_province", "creatediqujs", "生成省份、城市、地区等js文件");
	         //-----------------记录日志结束--------------------------
	         request.setAttribute("msg", "更新成功！");
	         return "manage";
    	 } catch (IOException e) {
 			// TODO Auto-generated catch block
    		 Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_province", "creatediqujs", "出现异常:"+e.toString());
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
        if(dbc_province == null){
          dbc_province = new Dbc_province();
        }
        return dbc_province;
     }

     public Dbc_province getDbc_province() {
        return dbc_province;
     }

     public void setDbc_province(Dbc_province dbc_province){ 
        this.dbc_province = dbc_province;
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