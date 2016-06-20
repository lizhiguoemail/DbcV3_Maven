/**
* @Project 源自 dbc
* @Title Base_typeAction.java
* @Package com.dbc.action.common
* @Description 所有l类型的类别action类
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
import com.dbc.pojo.Dbc_typegroup;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_type;
import com.dbc.service.Dbc_typeService;
/**
* @ClassName Base_typeAction
* @Description 所有l类型的类别action类
* @author caihuajun
* @date 2013-12-04
*/
public class Dbc_typeAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_type base_type;

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
	        Dbc_typeService base_typeservice=(Dbc_typeService) super.getInstence("dbc_typeservice");
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        List pagelist=(List)base_typeservice.selEntityByPage(Dbc_type.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcodebygroup,sortcode"),Dbcutil.getarr("desc,desc"));
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
            logservice.saveLog(userinfo, ipaddress, "dbc_type", "list", "出现异常:"+e.toString());
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
	        Dbc_typeService base_typeservice=(Dbc_typeService) super.getInstence("dbc_typeservice");
	        List base_typegrouplist=base_typeservice.selEntity(Dbc_typegroup.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));
	        if(id!=null&&!"".equals(id)){
	        	Dbc_type base_type=(Dbc_type) base_typeservice.selByOid(Dbc_type.class, id);
	        	request.setAttribute("base_type", base_type);
	        	request.setAttribute("isupdate", "1");
	        }
	        request.setAttribute("base_typegrouplist", base_typegrouplist);
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_type", "toaddorupdate", "出现异常:"+e.toString());
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
	        Dbc_typeService base_typeservice=(Dbc_typeService) super.getInstence("dbc_typeservice");
	        String isupdate=request.getParameter("isupdate");
	        String groupid=request.getParameter("groupid");
	        Dbc_typegroup btg=(Dbc_typegroup) base_typeservice.selByOid(Dbc_typegroup.class, groupid);
	        if(btg!=null){
	        	base_type.setType_type(btg.getFlag());
	        	base_type.setType_groupname(btg.getName());
	        	base_type.setSortcodebygroup(btg.getSortcode());
	        }
	        if("1".equals(isupdate)){
	            base_type.setUpdatedate(nowdate);
	            base_type.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=base_typeservice.getSortCode_Double("Dbc_type");
	              base_type.setSortcode(sortcode);
	             }
	            base_typeservice.updateObject(base_type);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_type", "addorupdate", "修改所有l类型的类别");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          base_type.setId(null);
	          base_type.setCreatedate(nowdate);
	          base_type.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=base_typeservice.getSortCode_Double("Dbc_type");
	          	base_type.setSortcode(sortcode);
	          }
	          base_typeservice.saveObject(base_type);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_type", "addorupdate", "增加所有l类型的类别");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_type", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除所有l类型的类别
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_typeService base_typeservice=(Dbc_typeService) super.getInstence("dbc_typeservice");
	        base_typeservice.deletebyfieldarr(Dbc_type.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_type", "deletebyid", "删除所有l类型的类别");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_type", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title delete
    * @Description 删除所有l类型的类别
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_typeService base_typeservice=(Dbc_typeService) super.getInstence("dbc_typeservice");
	        String[] setfieldnamearr=Dbcutil.getarr("deletemark");
	        String[] setcontentarr=Dbcutil.getarr("1");
	        base_typeservice.setObjectbyids(Dbc_type.class, setfieldnamearr, setcontentarr, ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_type", "delete", "删除所有类型的类别");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_type", "delete", "出现异常:"+e.toString());
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
        if(base_type == null){
          base_type = new Dbc_type();
        }
        return base_type;
     }

     public Dbc_type getBase_type() {
        return base_type;
     }

     public void setBase_type(Dbc_type base_type){ 
        this.base_type = base_type;
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