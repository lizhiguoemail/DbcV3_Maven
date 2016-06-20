/**
* @Project 源自 dbc
* @Title ApiloginAction.java
* @Package com.dbc.action.common
* @Description 等三方登录action类
* @author caihuajun
* @date 2013-11-13
* @version v2.0
*/
package com.dbc.action;

import java.io.IOException;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_userinfoService;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_apilogin;
import com.dbc.service.Dbc_apiloginService;
/**
* @ClassName ApiloginAction
* @Description 等三方登录action类
* @author caihuajun
* @date 2013-11-13
*/
public class Dbc_apiloginAction extends BaseAction implements ModelDriven{

    private String methode;

    private Dbc_apilogin apilogin;

    private HttpServletRequest request;

    private HttpServletResponse response;

    public String execute(){
      String returnstr="";
      request = ServletActionContext.getRequest();
      response= ServletActionContext.getResponse();
      if("qqlogin".equals(methode)){
        returnstr=this.qqlogin();
      }
      else if("toqqlogin".equals(methode)){
        returnstr=this.toqqlogin();
      }
      return returnstr;
    }
    
    public String toqqlogin(){
	 try {
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return null;
    }
    
    public String qqlogin(){
    	try {
		  AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
	      String accessToken   = null,
	             openID        = null;
	        long tokenExpireIn = 0L;
	      if (accessTokenObj.getAccessToken().equals("")) {
//	                我们的网站被CSRF攻击了或者用户取消了授权
//	                做一些数据统计工作
	         System.out.print("没有获取到响应参数");
	        } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                Dbc_apiloginService apiloginservice=(Dbc_apiloginService) super.getInstence("dbc_apiloginservice");
                apiloginservice.qqlogin(openID, userInfoBean, request);
              }
	      }catch(Exception e){
	    	  System.out.println("获得QQ权限异常"+e);
	      }
    	return "redirect-index";
    }
   
    public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
	}

     public Object getModel(){
        if(apilogin == null){
          apilogin = new Dbc_apilogin();
        }
        return apilogin;
     }

     public Dbc_apilogin getApilogin() {
        return apilogin;
     }

     public void setApilogin(Dbc_apilogin apilogin){ 
        this.apilogin = apilogin;
     }
}