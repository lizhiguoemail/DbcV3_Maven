/**
* @Project 源自 dbc
* @Title ApiloginService.java
* @Package com.dbc.service
* @Description 等三方登录service类接口
* @author caihuajun
* @date 2013-11-13
* @version v2.0
*/
package com.dbc.service;

import javax.servlet.http.HttpServletRequest;

import com.dbc.service.BaseService;
import com.qq.connect.javabeans.qzone.UserInfoBean;

/**
* @ClassName ApiloginService
* @Description 等三方登录service类接口
* @author caihuajun
* @date 2013-11-13
*/
public interface Dbc_apiloginService extends BaseService{
	
	/**
	 * @Title qqlogin
	 * @Description   qq登录
	 * @param   openID
	 * @param   userInfoBean
	 * @param   request
	 * @return void  
	 */
	public void qqlogin(String openID,UserInfoBean userInfoBean,HttpServletRequest request);

}