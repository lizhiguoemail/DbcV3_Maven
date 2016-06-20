/**
* @Project 源自 dbc
* @Title Base_typegroupAction.java
* @Package com.dbc.action.common
* @Description 分类字典组action类
* @author caihuajun
* @date 2013-12-04
* @version v1.0
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
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_typegroup;
import com.dbc.service.Dbc_typegroupService;
/**
* @ClassName Base_typegroupAction
* @Description 分类字典组action类
* @author caihuajun
* @date 2013-12-04
*/
public class Dbc_typegroupAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_typegroup base_typegroup;

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
	        Dbc_typegroupService base_typegroupservice=(Dbc_typegroupService) super.getInstence("dbc_typegroupservice");
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        List pagelist=(List)base_typegroupservice.selEntityByPage(Dbc_typegroup.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
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
            logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "list", "出现异常:"+e.toString());
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
	        	Dbc_typegroupService base_typegroupservice=(Dbc_typegroupService) super.getInstence("dbc_typegroupservice");
	        	Dbc_typegroup base_typegroup=(Dbc_typegroup) base_typegroupservice.selByOid(Dbc_typegroup.class, id);
	        	request.setAttribute("base_typegroup", base_typegroup);
	        	request.setAttribute("isupdate", "1");
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "toaddorupdate", "出现异常:"+e.toString());
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
	        Dbc_typegroupService base_typegroupservice=(Dbc_typegroupService) super.getInstence("dbc_typegroupservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            base_typegroup.setUpdatedate(nowdate);
	            base_typegroup.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=base_typegroupservice.getSortCode_Double("Dbc_typegroup");
	              base_typegroup.setSortcode(sortcode);
	             }
	            String oldtype=request.getParameter("oldtype");
	            base_typegroupservice.updatebase_type(base_typegroup,oldtype);
	            //base_typegroupservice.updateObject(base_typegroup);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "addorupdate", "修改分类字典组");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          base_typegroup.setId(null);
	          base_typegroup.setCreatedate(nowdate);
	          base_typegroup.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=base_typegroupservice.getSortCode_Double("Dbc_typegroup");
	          	base_typegroup.setSortcode(sortcode);
	          }
	          base_typegroupservice.saveObject(base_typegroup);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "addorupdate", "增加分类字典组");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除分类字典组
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_typegroupService base_typegroupservice=(Dbc_typegroupService) super.getInstence("dbc_typegroupservice");
	        base_typegroupservice.deletebyfieldarr(Dbc_typegroup.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "deletebyid", "删除分类字典组");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title delete
    * @Description 删除分类字典组
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_typegroupService base_typegroupservice=(Dbc_typegroupService) super.getInstence("dbc_typegroupservice");
	        String[] setfieldnamearr=Dbcutil.getarr("deletemark");
	        String[] setcontentarr=Dbcutil.getarr("1");
	        base_typegroupservice.setObjectbyids(Dbc_typegroup.class, setfieldnamearr, setcontentarr, ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "delete", "删除分类字典组");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_typegroup", "delete", "出现异常:"+e.toString());
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
        if(base_typegroup == null){
          base_typegroup = new Dbc_typegroup();
        }
        return base_typegroup;
     }

     public Dbc_typegroup getBase_typegroup() {
        return base_typegroup;
     }

     public void setBase_typegroup(Dbc_typegroup base_typegroup){ 
        this.base_typegroup = base_typegroup;
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