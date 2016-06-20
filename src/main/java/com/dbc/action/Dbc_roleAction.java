/**
* @Project 源自 dbc
* @Title Base_roleAction.java
* @Package com.dbc.action.common
* @Description 角色action类
* @author caihuajun
* @date 2014-02-17
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
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_permitService;
import com.dbc.pojo.Dbc_permit;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_role;
import com.dbc.service.Dbc_roleService;
/**
* @ClassName Base_roleAction
* @Description 角色action类
* @author caihuajun
* @date 2014-02-17
*/
public class Dbc_roleAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_role base_role;

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
	        Dbc_roleService base_roleservice=(Dbc_roleService) super.getInstence(request,"dbc_roleservice");
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        List pagelist=(List)base_roleservice.selEntityByPage(Dbc_role.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
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
            logservice.saveLog(userinfo, ipaddress, "dbc_role", "list", "出现异常:"+e.toString());
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
    		Dbc_roleService base_roleservice=(Dbc_roleService) super.getInstence(request,"dbc_roleservice");
    		List permitlist=base_roleservice.selEntity(Dbc_permit.class, Dbcutil.getarr("isopen"), Dbcutil.getarr("开启"), null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
    		request.setAttribute("permitlist", permitlist);
	        String id=request.getParameter("id");
	        if(id!=null&&!"".equals(id)){
	        	Dbc_role base_role=(Dbc_role) base_roleservice.selByOid(Dbc_role.class, id);
	        	List permits=base_role.getPermits();
	        	String permitsarr=",";
	        	for(int i=0;i<permits.size();i++){
	        		Dbc_permit dp=(Dbc_permit) permits.get(i);
	        		permitsarr=permitsarr+dp.getId()+",";
	        	}
	        	request.setAttribute("permitsarr", permitsarr);
	        	request.setAttribute("base_role", base_role);
	        	request.setAttribute("isupdate", "1");
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_role", "toaddorupdate", "出现异常:"+e.getMessage());
			e.printStackTrace();
			request.setAttribute("action","dbc_role");
			request.setAttribute("methode","toaddorupdate");
			request.setAttribute("e", e.toString());
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
	        String[] permitids=request.getParameterValues("permitid");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
	        String username=userinfo.getUsername();
	        String isupdate=request.getParameter("isupdate");
	        Dbc_roleService base_roleservice=(Dbc_roleService) super.getInstence(request,"dbc_roleservice");
	        List permitlist=base_roleservice.selByOids(Dbc_permit.class, permitids, null, null);
	        if("1".equals(isupdate)){
	            base_role.setUpdatedate(nowdate);
	            base_role.setUpdateuser(username);
	            base_role.setPermits(permitlist);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=base_roleservice.getSortCode_Double("Dbc_role");
	              base_role.setSortcode(sortcode);
	             }
	            base_roleservice.updateObject(base_role);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_role", "addorupdate", "修改角色");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          base_role.setId(null);
	          base_role.setCreatedate(nowdate);
	          base_role.setCreateuser(username);
	          base_role.setPermits(permitlist);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=base_roleservice.getSortCode_Double("Dbc_role");
	          	base_role.setSortcode(sortcode);
	          }
	          base_roleservice.saveObject(base_role);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_role", "addorupdate", "增加角色");
	          //-----------------记录日志结束--------------------------
	        }
	        this.updatesession(base_roleservice, userinfo.getId());
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_role", "toaddorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除角色
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_roleService base_roleservice=(Dbc_roleService) super.getInstence(request,"dbc_roleservice");
	        base_roleservice.deletebyfieldarr(Dbc_role.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_role", "deletebyid", "删除角色");
	        //-----------------记录日志结束--------------------------
	        this.updatesession(base_roleservice, userinfo.getId());
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_role", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title delete
    * @Description 删除角色
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_roleService base_roleservice=(Dbc_roleService) super.getInstence(request,"dbc_roleservice");
	        String[] setfieldnamearr=Dbcutil.getarr("deletemark");
	        String[] setcontentarr=Dbcutil.getarr("1");
	        base_roleservice.setObjectbyids(Dbc_role.class, setfieldnamearr, setcontentarr, ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_role", "delete", "删除角色");
	        //-----------------记录日志结束--------------------------
	        this.updatesession(base_roleservice, userinfo.getId());
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_role", "delete", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    public void updatesession(Dbc_roleService base_roleservice,String userid){
    	//------更新session中的权限--------------------------------------
        Dbc_userinfo user=(Dbc_userinfo) base_roleservice.selByOid(Dbc_userinfo.class, userid);
        String alluserpermit=",";
		List roles=user.getRoles();
		if(roles!=null){
			for(int i=0;i<roles.size();i++){
				Dbc_role role=(Dbc_role) roles.get(i);
				if("是".equals(role.getIssysadmin())){
					user.setIsadmin("1");
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
		List userpermitlist=user.getPermits();
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
		user.setAllpermits(alluserpermit);
		request.getSession().setAttribute("backstage_user", user);
    }

    public String getMethode() {
   	 return methode;
	 }
	
	 public void setMethode(String methode) {
	 	this.methode = methode;
	 }

     public Object getModel(){
        if(base_role == null){
          base_role = new Dbc_role();
        }
        return base_role;
     }

     public Dbc_role getBase_role() {
        return base_role;
     }

     public void setBase_role(Dbc_role base_role){ 
        this.base_role = base_role;
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