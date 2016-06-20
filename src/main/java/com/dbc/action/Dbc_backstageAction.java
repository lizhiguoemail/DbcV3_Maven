/**
* @Project 源自 dbc
* @Title Dbc_backstageAction.java
* @Package com.dbc.action
* @Description 后台基本action类
* @author caihuajun
* @date 2015-05-12
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
import com.dbc.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;
import com.dbc.service.Dbc_logService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_ad;
import com.dbc.service.Dbc_adService;
/**
* @ClassName Dbc_adAction
* @Description 广告action类
* @author caihuajun
* @date 2015-05-12
*/
public class Dbc_backstageAction extends BaseAction{

    private String methode;

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
      if("tocenter".equals(methode)){
        returnstr=this.tocenter();
      }
      else{
    	  request.setAttribute("action","dbc_backstage");
		  request.setAttribute("methode",methode);
		  request.setAttribute("exception", Dbc_custom_constants.nomethode);
		  returnstr="Exception";
      }
      return returnstr;
    }

    /**
	 * @Title tocenter
	 * @Description  后台中心
	 * @return string  返回值
	 */
	public String tocenter(){
		return "right";
	}

     public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
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