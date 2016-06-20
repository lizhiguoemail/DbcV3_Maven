/**
* @Project 源自 dbc
* @Title Base_commentAction.java
* @Package com.dbc.action.common
* @Description 评论通用类action类
* @author caihuajun
* @date 2014-03-21
* @version v2.0
*/
package com.dbc.action;

import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_comment;
import com.dbc.service.Dbc_commentService;
/**
* @ClassName Base_commentAction
* @Description 评论通用类action类
* @author caihuajun
* @date 2014-03-21
*/
public class Dbc_commentAction extends BaseAction{

    private String methode;

    private Dbc_comment base_comment;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String nowpageString;

    private String gotopagetype;

    private String gotopageString;

    private String pagesizeString="15";

    private String sname;

    private String svalue;
    
    private String objid;
    
    private String type;
    
    private String uid;

    public String execute(){
      String returnstr="";
      request = ServletActionContext.getRequest();
      response= ServletActionContext.getResponse();
      if("list".equals(methode)){
        returnstr=this.list();
      }
      else if("listpinglun".equals(methode)){
          returnstr=this.listpinglun();
        }
      else if("addpinglun".equals(methode)){
          returnstr=this.addpinglun();
        }
      else if("toaddorupdate".equals(methode)){
        returnstr=this.toaddorupdate();
      }
      else if("addorupdate".equals(methode)){
        returnstr=this.addorupdate();
      }
      else if("pingbibyid".equals(methode)){
          returnstr=this.pingbibyid();
        }
      else if("pingbi".equals(methode)){
          returnstr=this.pingbi();
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
	        Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        String sname2="deletemark";
	        String svalue2="0";
	        String islike2="false";
	        if(objid!=null&&!"".equals(objid)){
	        	sname2=sname2+",objid";
	        	svalue2=svalue2+","+objid;
	        	islike2=islike2+",false";
	        }
	        if(type!=null&&!"".equals(type)){
	        	sname2=sname2+",type";
	        	svalue2=svalue2+","+type;
	        	islike2=islike2+",false";
	        }
	        if(uid!=null&&!"".equals(uid)){
	        	sname2=sname2+",uid";
	        	svalue2=svalue2+","+uid;
	        	islike2=islike2+",false";
	        }
	        List pagelist=(List)base_commentservice.selEntityByPage(Dbc_comment.class,Dbcutil.getarr(sname2),Dbcutil.getarr(svalue2),Dbcutil.getbooleanarr(islike2),pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
	        List list = (List) pagelist.get(0);
	        PageParm pageParm=(PageParm) pagelist.get(1);
	        request.setAttribute("list", list);
	        request.setAttribute("pageParm", pageParm);
	        request.setAttribute("sname", sname);
	        request.setAttribute("svalue", svalue);
	        request.setAttribute("type", type);
	        request.setAttribute("objid", objid);
	        return "list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "list", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    /**
	 * @Title Listpinglun
	 * @Description 前台评论展示 
	 * @return string
	 */	
	public String listpinglun(){
		try{
			Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
			String type=request.getParameter("type");
			String objid=request.getParameter("objid");
			String objname=request.getParameter("objname");
			List pagelist=(List)base_commentservice.selEntityByPage(Dbc_comment.class,Dbcutil.getarr("type,objid"),Dbcutil.getarr(type+","+objid),null,pageparm,Dbcutil.getarr("createdate"),Dbcutil.getarr("desc"));
			List list = (List) pagelist.get(0);
	        PageParm pageParm=(PageParm) pagelist.get(1);
			request.setAttribute("pinglunlist", list);
			request.setAttribute("pageParm", pageParm);
			request.setAttribute("type", type);
			request.setAttribute("objid", objid);
			request.setAttribute("objname", objname);
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "listpinglun", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
		return "listpinglun";
	}
	
	/**
	 * @Title addpinglun
	 * @Description 添加评论
	 * @return string
	 */	
	public String addpinglun(){
		try{
			Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
			String type=request.getParameter("type");
			String objid=request.getParameter("objid");
			String uname=request.getParameter("uname");
			String neirong=request.getParameter("neirong");
			String objname=request.getParameter("objname");
			String isreg=request.getParameter("isreg");
			String uid=request.getParameter("uid");
			List list=base_commentservice.selEntity(Dbc_comment.class, Dbcutil.getarr("type,objid"), Dbcutil.getarr(type+","+objid), null,Dbcutil.getarr("createdate"),Dbcutil.getarr("desc"));
			Date date=new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sf.format(date);
			Dbc_comment pinglun=new Dbc_comment();
			pinglun.setUname(uname);
			pinglun.setCreatedate(time);
			pinglun.setNeirong(neirong);
			pinglun.setType(type);
			pinglun.setObjid(objid);
			pinglun.setObjname(objname);
			pinglun.setIsreg(isreg);
			pinglun.setFloor(list.size()+1);
			pinglun.setUid(uid);
			String[] ipall=Dbcutil.getIpAddressAddressByrequest(request);
			pinglun.setIp(ipall[0]);
			pinglun.setIpzone(ipall[1]);
			pinglun.setIpaddress(ipall[2]);
			base_commentservice.saveObject(pinglun);
			request.setAttribute("type", type);
			request.setAttribute("objid", objid);
			request.setAttribute("objname", objname);
		} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "addpinglun", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
		return this.listpinglun();
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
	        	Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	        	Dbc_comment base_comment=(Dbc_comment) base_commentservice.selByOid(Dbc_comment.class, id);
	        	request.setAttribute("base_comment", base_comment);
	        	request.setAttribute("isupdate", "1");
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "toaddorupdate", "出现异常:"+e.toString());
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
	        Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            base_comment.setUpdatedate(nowdate);
	            base_comment.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=base_commentservice.getSortCode_Double("Dbc_comment");
	              base_comment.setSortcode(sortcode);
	             }
	            base_commentservice.updateObject(base_comment);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "addorupdate", "修改评论通用类");
	            //-----------------记录日志结束--------------------------
	            request.setAttribute("type", type);
		        request.setAttribute("objid", objid);
	        }
	        else{
	          base_comment.setId(null);
	          base_comment.setCreatedate(nowdate);
	          base_comment.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=base_commentservice.getSortCode_Double("Dbc_comment");
	          	base_comment.setSortcode(sortcode);
	          }
	          base_commentservice.saveObject(base_comment);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_comment", "addorupdate", "增加评论通用类");
	          //-----------------记录日志结束--------------------------
	          request.setAttribute("type", type);
		      request.setAttribute("objid", objid);
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除评论通用类
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	        base_commentservice.deletebyfieldarr(Dbc_comment.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_comment", "deletebyid", "删除评论");
	        //-----------------记录日志结束--------------------------
	        request.setAttribute("type", type);
	        request.setAttribute("objid", objid);
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title delete
    * @Description 删除评论通用类
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	        String[] setfieldnamearr=Dbcutil.getarr("deletemark");
	        String[] setcontentarr=Dbcutil.getarr("1");
	        base_commentservice.setObjectbyids(Dbc_comment.class, setfieldnamearr, setcontentarr, ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_comment", "delete", "删除评论");
	        //-----------------记录日志结束--------------------------
	        request.setAttribute("type", type);
	        request.setAttribute("objid", objid);
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_comment", "delete", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    /**
     * @Title pingbibyid
     * @Description 屏蔽评论通用类
     * @return 返回值
     */
     public String pingbibyid(){
    	 try{
	         Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	         String ipaddress=Dbcutil.getIpByrequest(request);
	         String id=request.getParameter("id");
	         String v=request.getParameter("v");
	         Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	         base_commentservice.setObjectbyids(Dbc_comment.class, Dbcutil.getarr("isguolv"),Dbcutil.getarr(v), Dbcutil.getarr(id));
	         //-----------------记录到日志---------------------------
	         Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	         logservice.saveLog(userinfo, ipaddress, "dbc_comment", "pingbibyid", "屏蔽评论");
	         //-----------------记录日志结束--------------------------
	         request.setAttribute("type", type);
		     request.setAttribute("objid", objid);
	         return "redirect-list";
    	 } catch (Exception e) {
 			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
 		    String ipaddress=Dbcutil.getIpByrequest(request);
 			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
             logservice.saveLog(userinfo, ipaddress, "dbc_comment", "pingbibyid", "出现异常:"+e.toString());
 			e.printStackTrace();
 			return "Exception";
 		}
     }

     /**
     * @Title pingbi
     * @Description 屏蔽评论通用类
     * @return 返回值
     */
     public String pingbi(){
    	 try{
	         Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	         String ipaddress=Dbcutil.getIpByrequest(request);
	         String[] ids=request.getParameterValues("checks");
	         String v=request.getParameter("v");
	         Dbc_commentService base_commentservice=(Dbc_commentService) super.getInstence(request,"dbc_commentservice");
	         String[] setfieldnamearr=Dbcutil.getarr("isguolv");
	         String[] setcontentarr=Dbcutil.getarr(v);
	         if(ids!=null){
	        	 base_commentservice.setObjectbyids(Dbc_comment.class, setfieldnamearr, setcontentarr, ids);
	         }
	         //-----------------记录到日志---------------------------
	         Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	         logservice.saveLog(userinfo, ipaddress, "dbc_comment", "pingbi", "屏蔽评论");
	         //-----------------记录日志结束--------------------------
	         request.setAttribute("type", type);
		     request.setAttribute("objid", objid);
	         return "redirect-list";
    	 } catch (Exception e) {
  			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_comment", "pingbi", "出现异常:"+e.toString());
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

     public Dbc_comment getBase_comment() {
        return base_comment;
     }

     public void setBase_comment(Dbc_comment base_comment){ 
        this.base_comment = base_comment;
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

	public String getPagesizeString() {
		return pagesizeString;
	}

	public void setPagesizeString(String pagesizeString) {
		this.pagesizeString = pagesizeString;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}