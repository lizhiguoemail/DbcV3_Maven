/**
* @Project 源自 dbc
* @Title ApiloginServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 等三方登录service实现类
* @author caihuajun
* @date 2013-11-13
* @version v2.0
*/

package com.dbc.service.Impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dbc.pojo.Dbc_apilogin;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_apiloginService;
import com.dbc.service.Dbc_userinfoService;
import com.dbc.util.Dbcutil;

import com.dbc.dao.Dbc_apiloginDao;
import com.qq.connect.javabeans.qzone.UserInfoBean;

/**
* @ClassName ApiloginServiceImpl
* @Description 等三方登录service实现类
* @author caihuajun
* @date 2013-11-13
*/
public class Dbc_apiloginServiceImpl extends BaseServiceImpl implements Dbc_apiloginService{

	private Dbc_apiloginDao dbc_apilogindao;
	
	public Dbc_apiloginDao getDbc_apilogindao() {
		return dbc_apilogindao;
	}

	public void setDbc_apilogindao(Dbc_apiloginDao dbcApilogindao) {
		dbc_apilogindao = dbcApilogindao;
	}

	/**
	 * @Title qqlogin
	 * @Description   qq登录
	 * @param   openID
	 * @param   userInfoBean
	 * @param   request
	 * @return void  
	 */
	public void qqlogin(String openID,UserInfoBean userInfoBean,HttpServletRequest request){
		if (userInfoBean.getRet() == 0) {
        	List listapiuser=dbc_apilogindao.selEntity(Dbc_apilogin.class, Dbcutil.getarr("web_id,web_belong"), Dbcutil.getarr(openID+",qq"), null, null, null);
        	if(listapiuser.size()>0){
        		Dbc_apilogin apilogin=(Dbc_apilogin) listapiuser.get(0);
        		Dbc_userinfo userinfo=(Dbc_userinfo) dbc_apilogindao.selByOid(Dbc_userinfo.class, apilogin.getUserid());
        		userinfo.setLastip(Dbcutil.getIpByrequest(request));
        		userinfo.setLogincount(userinfo.getLogincount()+1);
        		dbc_apilogindao.updateObject(userinfo);
        		request.getSession().setAttribute("userinfo", userinfo);
        	}
        	else{
        		Dbc_userinfo userinfo=new Dbc_userinfo();
        		userinfo.setNickname(userInfoBean.getNickname());
        		userinfo.setUsername(userInfoBean.getNickname());
        		userinfo.setRegdate(Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss"));
        		userinfo.setUsertype("person");
        		userinfo.setLogincount(1);
        		userinfo.setLoginIp(Dbcutil.getIpByrequest(request));
        		userinfo.setIpaddress(Dbcutil.getIpByrequest(request));
        		userinfo.setIswanshan("0");
        		dbc_apilogindao.saveObject(userinfo);
        		Dbc_apilogin apilogin=new Dbc_apilogin();
        		apilogin.setUserid(userinfo.getId());
        		apilogin.setWeb_id(openID);
        		apilogin.setWeb_belong("qq");
        		apilogin.setCreatedate(Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss"));
        		dbc_apilogindao.saveObject(apilogin);
        		request.getSession().setAttribute("userinfo", userinfo);
        	}
		}
	}
}