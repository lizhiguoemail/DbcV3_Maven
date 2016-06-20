/**
* @Project 源自 dbc
* @Title Dbc_permitAction.java
* @Package com.dbc.action
* @Description 权限action类
* @author caihuajun
* @date 2015-05-14
* @version V2.0
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
import com.dbc.service.Dbc_roleService;
import com.dbc.pojo.Dbc_role;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_permit;
import com.dbc.service.Dbc_permitService;
/**
* @ClassName Dbc_permitAction
* @Description 权限action类
* @author caihuajun
* @date 2015-05-14
*/
public class Dbc_permitAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_permit dbc_permit;

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
    	  	String select_isopen=request.getParameter("select_isopen");
    	  	String permit_name=request.getParameter("permit_name");
    	  	String permit_action=request.getParameter("permit_action");
    	  	String permit_methode=request.getParameter("permit_methode");
    	  	String sname2="deletemark";
    	  	String svalue2="0";
    	  	String islike2="false";
    	  	if(permit_name!=null&&!"".equals(permit_name)){
    	  		sname2=sname2+",permit_name";
    	  		svalue2=svalue2+","+permit_name;
    	  		islike2=islike2+",true";
    	  	}
    	  	if(permit_action!=null&&!"".equals(permit_action)){
    	  		sname2=sname2+",permit_action";
    	  		svalue2=svalue2+","+permit_action;
    	  		islike2=islike2+",false";
    	  	}
    	  	if(permit_methode!=null&&!"".equals(permit_methode)){
    	  		sname2=sname2+",permit_methode";
    	  		svalue2=svalue2+","+permit_methode;
    	  		islike2=islike2+",false";
    	  	}
    	  	if(select_isopen!=null&&!"".equals(select_isopen)){
    	  		sname2=sname2+",isopen";
    	  		svalue2=svalue2+","+select_isopen;
    	  		islike2=islike2+",false";
    	  	}
            Dbc_permitService dbc_permitservice=(Dbc_permitService) super.getInstence(request,"dbc_permitservice");
            PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
            List pagelist=(List)dbc_permitservice.selEntityByPage(Dbc_permit.class,Dbcutil.getarr(sname2),Dbcutil.getarr(svalue2),Dbcutil.getbooleanarr(islike2),pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("asc"));
            List list = (List) pagelist.get(0);
            PageParm pageParm=(PageParm) pagelist.get(1);
            request.setAttribute("list", list);
            request.setAttribute("pageParm", pageParm);
            request.setAttribute("permit_name", permit_name);
            request.setAttribute("permit_action", permit_action);
            request.setAttribute("permit_methode", permit_methode);
            request.setAttribute("select_isopen", select_isopen);
            return "list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_permit", "list", "出现异常："+e.toString());
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
            	Dbc_permitService dbc_permitservice=(Dbc_permitService) super.getInstence(request,"dbc_permitservice");
            	Dbc_permit dbc_permit=(Dbc_permit) dbc_permitservice.selByOid(Dbc_permit.class, id);
            	request.setAttribute("dbc_permit", dbc_permit);
            	request.setAttribute("isupdate", "1");
	        }
	        return "addorupdate";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_permit", "addorupdate", "出现异常："+e.toString());
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
            Dbc_permitService dbc_permitservice=(Dbc_permitService) super.getInstence(request,"dbc_permitservice");
            String isupdate=request.getParameter("isupdate");
            if(dbc_permit.getPermit_methode()==null||"".equals(dbc_permit.getPermit_methode())){
            	dbc_permit.setPermit_methode("allmethode");
            }
            if("1".equals(isupdate)){
                dbc_permit.setUpdatedate(nowdate);
                dbc_permit.setUpdateuser(username);
                String sortcodestr=request.getParameter("sortcode");
                if(sortcodestr==null||"".equals(sortcodestr)){
                  Double sortcode=dbc_permitservice.getSortCode_Double("Dbc_permit");
                  dbc_permit.setSortcode(sortcode);
                 }
                dbc_permit.setAction_methode(dbc_permit.getPermit_action()+"_"+dbc_permit.getPermit_methode());
                dbc_permitservice.updateObject(dbc_permit);
                //-----------------记录到日志---------------------------
                Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
                logservice.saveLog(userinfo, ipaddress, "dbc_permit", "addorupdate", "修改权限");
                //-----------------记录日志结束--------------------------
            }
            else{
              dbc_permit.setId(null);
              dbc_permit.setCreatedate(nowdate);
              dbc_permit.setCreateuser(username);
              String sortcodestr=request.getParameter("sortcode");
              if(sortcodestr==null||"".equals(sortcodestr)){
          	    Double sortcode=dbc_permitservice.getSortCode_Double("Dbc_permit");
          	    dbc_permit.setSortcode(sortcode);
              }
              dbc_permit.setAction_methode(dbc_permit.getPermit_action()+"_"+dbc_permit.getPermit_methode());
              dbc_permitservice.saveObject(dbc_permit);
              //-----------------记录到日志---------------------------
              Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_permit", "addorupdate", "增加权限");
              //-----------------记录日志结束--------------------------
            }
            this.updatesession(dbc_permitservice,userinfo);
            return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_permit", "addorupdate", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title deletebyid
    * @Description 删除权限
    * @return 返回值
    */
    public String deletebyid(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String id=request.getParameter("id");
            Dbc_permitService dbc_permitservice=(Dbc_permitService) super.getInstence(request,"dbc_permitservice");
            dbc_permitservice.deletebyfieldarr(Dbc_permit.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_permit", "deletebyid", "删除权限");
            //-----------------记录日志结束--------------------------
            this.updatesession(dbc_permitservice,userinfo);
            return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_permit", "deletebyid", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }

    /**
    * @Title delete
    * @Description 删除权限
    * @return 返回值
    */
    public String delete(){
      try{
            Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
            String ipaddress=Dbcutil.getIpByrequest(request);
            String[] ids=request.getParameterValues("checks");
            Dbc_permitService dbc_permitservice=(Dbc_permitService) super.getInstence(request,"dbc_permitservice");
            String[] setfieldnamearr=Dbcutil.getarr("deletemark");
            String[] setcontentarr=Dbcutil.getarr("1");
            dbc_permitservice.setObjectbyids(Dbc_permit.class, setfieldnamearr, setcontentarr, ids);
            //-----------------记录到日志---------------------------
            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_permit", "delete", "删除权限");
            //-----------------记录日志结束--------------------------
            this.updatesession(dbc_permitservice,userinfo);
            return "redirect-list";
       } catch (Exception e) {
           Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
           String ipaddress=Dbcutil.getIpByrequest(request);
           Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
           logservice.saveLog(userinfo, ipaddress, "dbc_permit", "deletebyid", "出现异常："+e.toString());
           e.printStackTrace();
           return "Exception";
       }
    }
    
    public void updatesession(Dbc_permitService dbc_permitservice,Dbc_userinfo userinfo){
    	//------更新session中的权限--------------------------------------
        String allpermitsstr="";
        List syspermitlist=dbc_permitservice.selEntity(Dbc_permit.class, Dbcutil.getarr("isopen"), Dbcutil.getarr("开启"), null, null, null);
		 for(int i=0;i<syspermitlist.size();i++){
			 Dbc_permit permit=(Dbc_permit) syspermitlist.get(i);
			 if(i==0){
				 String permit_methode=permit.getPermit_methode();
				 String permit_action=permit.getPermit_action();
				 if(permit_methode.contains("|")){
					 /*
					  * 注意：除了使用“\\|”外，也可以用"[.]" 进行分隔!
						如：
						String[] paraStr = "6010|320100|A".split("[|]");
						String[] paraStr = "6010.320100.A".split("[.]");
					  */
					 String[] parr=permit_methode.split("\\|");
					 for(int k=0;k<parr.length;k++){
						 if(k==0){
							 allpermitsstr=","+permit_action+"_"+parr[k]+",";
						 }
						 else{
							 allpermitsstr=allpermitsstr+permit_action+"_"+parr[k]+",";
						 }
					 }
				 }
				 else{
					 allpermitsstr=","+permit.getAction_methode()+",";
				 }
			 }
			 else{
				 allpermitsstr=allpermitsstr+permit.getAction_methode()+",";
			 }
		 }
		request.getSession().setAttribute("allpermitsstr", allpermitsstr);
		String alluserpermit=",";
		List roles=userinfo.getRoles();
		if(roles!=null){
			for(int i=0;i<roles.size();i++){
				Dbc_role role=(Dbc_role) roles.get(i);
				if("是".equals(role.getIssysadmin())){
					userinfo.setIsadmin("1");
				}
				List permitlist=role.getPermits();
				if(permitlist!=null){
					for(int j=0;j<permitlist.size();j++){
						Dbc_permit permit=(Dbc_permit) permitlist.get(j);
					    String permit_methode=permit.getPermit_methode();
						String permit_action=permit.getPermit_action();
						 if(permit_methode.contains("|")){
							 /*
							  * 注意：除了使用“\\|”外，也可以用"[.]" 进行分隔!
								如：
								String[] paraStr = "6010|320100|A".split("[|]");
								String[] paraStr = "6010.320100.A".split("[.]");
							  */
							 String[] parr=permit_methode.split("\\|");
							 for(int k=0;k<parr.length;k++){
								alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
							 }
						 }
						 else{
							 alluserpermit=alluserpermit+permit.getAction_methode()+",";
						 }
					}
				}
			}
		}
		List userpermitlist=userinfo.getPermits();
		if(userpermitlist!=null){
			for(int j=0;j<userpermitlist.size();j++){
			Dbc_permit permit=(Dbc_permit) userpermitlist.get(j);
				 String permit_methode=permit.getPermit_methode();
				 String permit_action=permit.getPermit_action();
				 if(permit_methode.contains("|")){
					 /*
					  * 注意：除了使用“\\|”外，也可以用"[.]" 进行分隔!
						如：
						String[] paraStr = "6010|320100|A".split("[|]");
						String[] paraStr = "6010.320100.A".split("[.]");
					  */
					 String[] parr=permit_methode.split("\\|");
					 for(int k=0;k<parr.length;k++){
						alluserpermit=alluserpermit+permit_action+"_"+parr[k]+",";
					 }
				 }
				 else{
					 alluserpermit=alluserpermit+permit.getAction_methode()+",";
				 }
			}
		}
		userinfo.setAllpermits(alluserpermit);
		request.getSession().setAttribute("backstage_user", userinfo);
    }
    
    public String getMethode() {
       return methode;
    }

    public void setMethode(String methode) {
       this.methode = methode;
    }

     public Object getModel(){
        if(dbc_permit == null){
          dbc_permit = new Dbc_permit();
        }
        return dbc_permit;
     }

     public Dbc_permit getDbc_permit() {
        return dbc_permit;
     }

     public void setDbc_permit(Dbc_permit dbc_permit){ 
        this.dbc_permit = dbc_permit;
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