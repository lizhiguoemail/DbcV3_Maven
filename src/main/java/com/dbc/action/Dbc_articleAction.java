/**
* @Project 源自 dbc
* @Title ArticleAction.java
* @Package com.dbc.action.common
* @Description 文章action类
* @author caihuajun
* @date 2013-11-26
* @version v2.0
*/
package com.dbc.action;

import java.io.IOException;

import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbc_objmap;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_treeByCaiService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_article;
import com.dbc.pojo.Dbc_webconfig;
import com.dbc.service.Dbc_articleService;
import com.dbc.pojo.Dbc_treeByCai;
/**
* @ClassName ArticleAction
* @Description 文章action类
* @author caihuajun
* @date 2013-11-26
*/
public class Dbc_articleAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_article article;

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
      else if("setbyid".equals(methode)){
          returnstr=this.setbyid();
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
    		Dbc_articleService articleservice=(Dbc_articleService) super.getInstence("dbc_articleservice");
            PageParm pageparm=new PageParm(nowpageString,gotopagetype,gotopageString,pagesizeString);
            List pagelist=(List)articleservice.selEntityByPage(Dbc_article.class,Dbcutil.getarr(sname),Dbcutil.getarr(svalue),null,pageparm,Dbcutil.getarr("sortcode"),Dbcutil.getarr("desc"));
            List list = (List) pagelist.get(0);
            PageParm pageParm=(PageParm) pagelist.get(1);
            request.setAttribute("list", list);
            request.setAttribute("pageParm", pageParm);
            request.setAttribute("sname", sname);
            request.setAttribute("svalue", svalue);
            Map treeMap=new HashMap();
            treeMap=Dbc_objmap.get_treeMap_article_list(list);
            request.setAttribute("treeMap", treeMap);
            return "list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_article", "list", "出现异常:"+e.toString());
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
	        Dbc_articleService articleservice=(Dbc_articleService) super.getInstence("dbc_articleservice");
	        Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService) super.getInstence("dbc_treebycaiservice");
	        List treebycailist=treebycaiservice.selBytreetype("article_type");
	        if(id!=null&&!"".equals(id)){
	        	Dbc_article article=(Dbc_article) articleservice.selByOid(Dbc_article.class, id);
	        	request.setAttribute("article", article);
	        	request.setAttribute("isupdate", "1");
	        }
	        request.setAttribute("treebycailist", treebycailist);
	        return "addorupdate";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_article", "toaddorupdate", "出现异常:"+e.toString());
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
    	try {
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        Dbc_webconfig webconfig=(Dbc_webconfig) request.getSession().getAttribute("webconfig");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
	        String username=userinfo.getUsername();
	        Dbc_articleService articleservice=(Dbc_articleService) super.getInstence("dbc_articleservice");
	        String isupdate=request.getParameter("isupdate");
	        if("1".equals(isupdate)){
	            article.setUpdatedate(nowdate);
	            article.setUpdateuser(username);
	            String sortcodestr=request.getParameter("sortcode");
	            if(sortcodestr==null||"".equals(sortcodestr)){
	              Double sortcode=articleservice.getSortCode_Double("Dbc_article");
	              article.setSortcode(sortcode);
	             }
	            if(article.getSource()==null||"".equals(article.getSource())){
	            	article.setSource(webconfig.getWebnickname());
	            }
	            articleservice.updateObject(article);
	            //-----------------记录到日志---------------------------
	            Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	            logservice.saveLog(userinfo, ipaddress, "dbc_article", "addorupdate", "修改文章");
	            //-----------------记录日志结束--------------------------
	        }
	        else{
	          article.setId(null);
	          article.setCreatedate(nowdate);
	          article.setCreateuser(username);
	          String sortcodestr=request.getParameter("sortcode");
	          if(sortcodestr==null||"".equals(sortcodestr)){
	          	Double sortcode=articleservice.getSortCode_Double("Dbc_article");
	          	article.setSortcode(sortcode);
	          }
	          if(article.getSource()==null||"".equals(article.getSource())){
	          	article.setSource(webconfig.getWebnickname());
	          }
	          articleservice.saveObject(article);
	          //-----------------记录到日志---------------------------
	          Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	          logservice.saveLog(userinfo, ipaddress, "dbc_article", "addorupdate", "增加文章");
	          //-----------------记录日志结束--------------------------
	        }
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_article", "addorupdate", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }

    /**
    * @Title deletebyid
    * @Description 删除文章
    * @return 返回值
    */
    public String deletebyid(){
    	try {
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String id=request.getParameter("id");
	        Dbc_articleService articleservice=(Dbc_articleService) super.getInstence("dbc_articleservice");
	        articleservice.deletebyfieldarr(Dbc_article.class,Dbcutil.getarr("id"),Dbcutil.getarr(id), true);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_article", "deletebyid", "删除文章");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
		    String ipaddress=Dbcutil.getIpByrequest(request);
			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
            logservice.saveLog(userinfo, ipaddress, "dbc_article", "deletebyid", "出现异常:"+e.toString());
			e.printStackTrace();
			return "Exception";
		}
    }
    
    /**
     * @Title setbyid
     * @Description 修改文章属性
     * @return 返回值
     */
     public String setbyid(){
    	 try {
	         Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	         String ipaddress=Dbcutil.getIpByrequest(request);
	         String id=request.getParameter("id");
	         String f=request.getParameter("f");
	         String v=request.getParameter("v");
	         Dbc_articleService articleservice=(Dbc_articleService) super.getInstence("dbc_articleservice");
	         articleservice.setObjectbyarr(Dbc_article.class, Dbcutil.getarr(f), Dbcutil.getarr(v), Dbcutil.getarr("id"), Dbcutil.getarr(id));
	         //-----------------记录到日志---------------------------
	         Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	         logservice.saveLog(userinfo, ipaddress, "dbc_article", "setbyid", "设置文章属性");
	         //-----------------记录日志结束--------------------------
	         return "redirect-list";
    	 } catch (Exception e) {
 			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
 		    String ipaddress=Dbcutil.getIpByrequest(request);
 			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
             logservice.saveLog(userinfo, ipaddress, "dbc_article", "setbyid", "出现异常:"+e.toString());
 			e.printStackTrace();
 			return "Exception";
 		}
     }

    /**
    * @Title delete
    * @Description 删除文章
    * @return 返回值
    */
    public String delete(){
    	try {
	        Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
	        String ipaddress=Dbcutil.getIpByrequest(request);
	        String[] ids=request.getParameterValues("checks");
	        Dbc_articleService articleservice=(Dbc_articleService) super.getInstence("dbc_articleservice");
	        String[] setfieldnamearr=Dbcutil.getarr("deletemark");
	        String[] setcontentarr=Dbcutil.getarr("1");
	        articleservice.setObjectbyids(Dbc_article.class, setfieldnamearr, setcontentarr, ids);
	        //-----------------记录到日志---------------------------
	        Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
	        logservice.saveLog(userinfo, ipaddress, "dbc_article", "delete", "删除文章");
	        //-----------------记录日志结束--------------------------
	        return "redirect-list";
    	} catch (Exception e) {
 			Dbc_userinfo userinfo=(Dbc_userinfo) request.getSession().getAttribute("backstage_user");
 		    String ipaddress=Dbcutil.getIpByrequest(request);
 			Dbc_logService logservice=(Dbc_logService) super.getInstence("dbc_logservice");
             logservice.saveLog(userinfo, ipaddress, "dbc_article", "delete", "出现异常:"+e.toString());
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
        if(article == null){
          article = new Dbc_article();
        }
        return article;
     }

     public Dbc_article getArticle() {
        return article;
     }

     public void setArticle(Dbc_article article){ 
        this.article = article;
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