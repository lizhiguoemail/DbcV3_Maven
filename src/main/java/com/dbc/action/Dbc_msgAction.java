/**
* @Project 源自 dbc
* @Title Dbc_msgAction.java
* @Package com.dbc.action.common
* @Description 消息表action类
* @author caihuajun
* @date 2014-12-23
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
import com.dbc.service.Dbc_userinfoService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_msg;
import com.dbc.service.Dbc_msgService;
/**
* @ClassName Dbc_msgAction
* @Description 消息表action类
* @author caihuajun
* @date 2014-12-23
*/
public class Dbc_msgAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_msg dbc_msg;

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
      else if("tosendorshow".equals(methode)){
        returnstr=this.tosendorshow();
      }
      else if("send".equals(methode)){
        returnstr=this.send();
      }
      else if("deletebyid".equals(methode)){
        returnstr=this.deletebyid();
      }
      else if("delete".equals(methode)){
        returnstr=this.delete();
      }
      else if("listforreceive".equals(methode)){
    	  returnstr=this.listforreceive();
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
	    	Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        Dbc_msgService dbc_msgservice=(Dbc_msgService) super.getInstence(request,"dbc_msgservice");
	        PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
	        //List pagelist=(List)dbc_msgservice.selEntityByPage(Dbc_msg.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
	        String sql=" and (receiveid='"+userinfo.getId()+"' or sendid='"+userinfo.getId()+"') ";
	        if(sname!=null&&!"".equals(sname)&&svalue!=null&&!"".equals(svalue)){
				 sql=sql+"  and "+sname+" like '%"+svalue+"%' ";
			}
	        List pagelist=(List)dbc_msgservice.selEntityBySqlPage(Dbc_msg.class,sql,pageparm,Dbcutil.getarr("createdate"),Dbcutil.getarr("desc"));
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
            logservice.saveLog(userinfo, ipaddress, "dbc_msg", "list", "出现异常:"+e.toString());
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
	        	Dbc_msgService dbc_msgservice=(Dbc_msgService) super.getInstence(request,"dbc_msgservice");
	        	Dbc_msg dbc_msg=(Dbc_msg) dbc_msgservice.selByOid(Dbc_msg.class, id);
	        	dbc_msg.setState("已读");
	        	String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
	        	dbc_msg.setOpentime(nowdate);
	        	request.setAttribute("dbc_msg", dbc_msg);
	        	request.setAttribute("isshow", "1");
	        }
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_msg", "toaddorupdate", "出现异常:"+e.toString());
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
	        Dbc_msgService dbc_msgservice=(Dbc_msgService) super.getInstence(request,"dbc_msgservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            dbc_msg.setUpdatedate(nowdate);
	            dbc_msg.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=dbc_msgservice.getSortCode_Double("Dbc_msg");
	              dbc_msg.setSortcode(sortcode);
	             }
	            dbc_msgservice.updateObject(dbc_msg);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_msg", "addorupdate", "修改消息表");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          dbc_msg.setId(null);
	          dbc_msg.setCreatedate(nowdate);
	          dbc_msg.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=dbc_msgservice.getSortCode_Double("Dbc_msg");
	          	dbc_msg.setSortcode(sortcode);
	          }
	          dbc_msgservice.saveObject(dbc_msg);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_msg", "addorupdate", "增加消息表");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_msg", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    /**
     * @Title tosendorshow
     * @Description 进入发消息页面或查看页面
     * @return string  返回值
     */
     public String tosendorshow(){
    	 try{
	         String id=request.getParameter("id");
	         if(id!=null&&!"".equals(id)){
	        	Dbc_msgService dbc_msgservice=(Dbc_msgService) super.getInstence(request,"dbc_msgservice");
	         	Dbc_msg dbc_msg=(Dbc_msg) dbc_msgservice.selByOid(Dbc_msg.class, id);
	         	dbc_msg.setState("已读");
	         	String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
	         	dbc_msg.setOpentime(nowdate);
	         	dbc_msgservice.updateObject(dbc_msg);
	         	request.setAttribute("dbc_msg", dbc_msg);
	         	request.setAttribute("isshow", "1");
	         }
	         return "addorupdate";
    	 } catch (Exception e) {
 			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
 		    String ipaddress=Dbcutil.getIpByrequest(request);
 			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
             logservice.saveLog(userinfo, ipaddress, "dbc_msg", "tosendorshow", "出现异常:"+e.toString());
 			e.printStackTrace();
 			return "Exception";
 		}
     }

     /**
     * @Title send
     * @Description 发送消息
     * @return string 返回值
     */
     public String send(){
    	 try{
	         Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	         String ipaddress=Dbcutil.getIpByrequest(request);
	         String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
	         String username=userinfo.getUsername();
	         Dbc_msgService dbc_msgservice=(Dbc_msgService) super.getInstence(request,"dbc_msgservice");
	         String isupdate=request.getParameter("isupdate");
	           dbc_msg.setId(null);
	           dbc_msg.setCreatedate(nowdate);
	           dbc_msg.setCreateuser(username);
	           dbc_msg.setState("未读");
	           String sortcodestr=request.getParameter("sortcode");
	           if(sortcodestr==null||"".equals(sortcodestr)){
	           	Double sortcode=dbc_msgservice.getSortCode_Double("Dbc_msg");
	           	dbc_msg.setSortcode(sortcode);
	           }
	           dbc_msgservice.saveObject(dbc_msg);
	           //-----------------记录到日志---------------------------
	           Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	           logservice.saveLog(userinfo, ipaddress, "dbc_msg", "send", "增加消息表");
	           //-----------------记录日志结束--------------------------
	         return "redirect-list";
    	 } catch (Exception e) {
  			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_msg", "send", "出现异常:"+e.toString());
  			e.printStackTrace();
  			return "Exception";
  		}
     }

    /**
    * @Title deletebyid
    * @Description 删除消息表
    * @return 返回值
    */
    public String deletebyid(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_msgService dbc_msgservice=(Dbc_msgService) super.getInstence(request,"dbc_msgservice");
	        dbc_msgservice.deletebyfieldarr(Dbc_msg.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_msg", "deletebyid", "删除消息表");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
  			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_msg", "deletebyid", "出现异常:"+e.toString());
  			e.printStackTrace();
  			return "Exception";
  		}
    }

    /**
    * @Title delete
    * @Description 删除消息表
    * @return 返回值
    */
    public String delete(){
    	try{
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_msgService dbc_msgservice=(Dbc_msgService) super.getInstence(request,"dbc_msgservice");
	        String[] setfieldnamearr=Dbcutil.getarr("deletemark");
	        String[] setcontentarr=Dbcutil.getarr("1");
	        dbc_msgservice.setObjectbyids(Dbc_msg.class, setfieldnamearr, setcontentarr, ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence(request,"dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_msg", "delete", "删除消息表");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
  			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_msg", "delete", "出现异常:"+e.toString());
  			e.printStackTrace();
  			return "Exception";
  		}
    }
    
    /**
	 * @Title listforreceive
	 * @Description  收件人选择列表
	 * @return string  返回值
	 */
	public String listforreceive(){
		try{
			Dbc_userinfo backstage_user=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
			Dbc_userinfoService userinfoservice=(Dbc_userinfoService) super.getInstence("dbc_userinfoservice");
			PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
			String sql=" and id !='"+backstage_user.getId()+"'  ";
			if(sname!=null&&!"".equals(sname)&&svalue!=null&&!"".equals(svalue)){
				 sql=sql+"  and "+sname+" like '%"+svalue+"%' ";
			}
			List pagelist=(List)userinfoservice.selEntityBySqlPage(Dbc_userinfo.class,sql, pageparm, Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));
			List list = (List) pagelist.get(0);
			PageParm pageParm=(PageParm) pagelist.get(1);
			request.setAttribute("listuser", list);
			request.setAttribute("pageParm", pageParm);
			request.setAttribute("sname", sname);
		    request.setAttribute("svalue", svalue);
			return "listforreceive";
		} catch (Exception e) {
  			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
  		    String ipaddress=Dbcutil.getIpByrequest(request);
  			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
              logservice.saveLog(userinfo, ipaddress, "dbc_msg", "listforreceive", "出现异常:"+e.toString());
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
        if(dbc_msg == null){
          dbc_msg = new Dbc_msg();
        }
        return dbc_msg;
     }

     public Dbc_msg getDbc_msg() {
        return dbc_msg;
     }

     public void setDbc_msg(Dbc_msg dbc_msg){ 
        this.dbc_msg = dbc_msg;
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