package com.dbc.service.Impl;

import java.util.List;

import com.dbc.dao.Dbc_userinfoDao;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_userinfoService;

public class Dbc_userinfoServiceImpl extends BaseServiceImpl implements Dbc_userinfoService{
	
	private Dbc_userinfoDao dbc_userinfodao;


	public Dbc_userinfoDao getDbc_userinfodao() {
		return dbc_userinfodao;
	}

	public void setDbc_userinfodao(Dbc_userinfoDao dbcUserinfodao) {
		dbc_userinfodao = dbcUserinfodao;
	}

	/**
	 * @Title FindUser
	 * @Description  查询用户
	 * @param fieldname
	 * @param content
	 * @param notfield
	 * @param notcontent
	 * @param orderfield
	 * @param order
	 * @return List
	 */
	public List FindUser(String fieldname, String content, String category,
			String notfield, String notcontent, String orderfield, String order) {
		return dbc_userinfodao.FindUser(fieldname, content, category, notfield, notcontent, orderfield, order);
	}
	
	/**
	 * @Title FindUser
	 * @Description  查询用户
	 * @param fieldname
	 * @param content
	 * @param notfield
	 * @param notcontent
	 * @param orderfield
	 * @param order
	 * @param isneedclose 是否开启session
	 * @return List
	 */
    public List FindUser(String fieldname,String content,String category,String notfield,String notcontent,String orderfield,String order,Boolean isneedclose){
    	return dbc_userinfodao.FindUser(fieldname, content, category, notfield, notcontent, orderfield, order,isneedclose);
    }

	public List FindUser(String fieldname, String content, String category,
			String notfield, List notlist, String orderfield, String order) {
		return dbc_userinfodao.FindUser(fieldname, content, category, notfield, notlist, orderfield, order);
	}
	
	public List FindUser(String fieldname, String content, String category,
			String notfield, List notlist, String orderfield, String order,Boolean isneedclose) {
		return dbc_userinfodao.FindUser(fieldname, content, category, notfield, notlist, orderfield, order,isneedclose);
	}

	/**
	 * @Title checkLogin
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @return UserBean
	 */
	public Dbc_userinfo checkLogin(String username, String userpwd,String usertype) {
		return dbc_userinfodao.checkLogin(username, userpwd,usertype);
	}
	
	/**
	 * @Title checkLogin
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @param isneedclose 是否开启session
	 * @return UserBean
	 */
	public Dbc_userinfo checkLogin(String username, String userpwd,String usertype,Boolean isneedclose){
		return dbc_userinfodao.checkLogin(username, userpwd, usertype, isneedclose);
	}

	/**
	 * @Title checkLoginByField
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @return UserBean
	 */
	public Dbc_userinfo checkLoginByField(String username, String userpwd,String usertype,String[] field, String[] content) {
		return dbc_userinfodao.checkLoginByField(username, userpwd,usertype,field, content);
	}
	
	/**
	 * @Title checkLoginByField
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @param isneedclose 是否开启session
	 * @return UserBean
	 */
	public Dbc_userinfo checkLoginByField(String username, String userpwd,String usertype,String[] field,String[] content,Boolean isneedclose){
		return dbc_userinfodao.checkLoginByField(username, userpwd, usertype, field, content, isneedclose);
	}

	/**
	 * @Title checkNickname
	 * @Description  呢称是否被使用
	 * @param string nickname 昵称
	 * @param String usertype 用户类型
	 * @return boolean
	 */
	public boolean checkNickname(String nickname,String usertype) {
		return dbc_userinfodao.checkNickname(nickname,usertype);
	}
	
	/**
	 * @Title checkNickname
	 * @Description  呢称是否被使用
	 * @param string nickname 昵称
	 * @param String usertype 用户类型
	 * @param isneedclose 是否开启session
	 * @return boolean
	 */
	public boolean checkNickname(String nickname,String usertype,Boolean isneedclose){
		return dbc_userinfodao.checkNickname(nickname, usertype, isneedclose);
	}

	/**
	 * @Title checkPwd
	 * @Description  验证密码是否正确
	 * @param username
	 * @param userpwd
	 * @param usertype
	 * @return Boolean
	 */
	public Boolean checkPwd(String username, String userpwd,String usertype) {
		return dbc_userinfodao.checkPwd(username, userpwd,usertype);
	}
	
	/**
	 * @Title checkPwd
	 * @Description  验证密码是否正确
	 * @param username
	 * @param userpwd
	 * @param usertype
	 * @param isneedclose 是否开启session
	 * @return Boolean
	 */
	public Boolean checkPwd(String username, String userpwd,String usertype,Boolean isneedclose){
		return dbc_userinfodao.checkPwd(username, userpwd, usertype, isneedclose);
	}

	/**
	 * @Title checkReg
	 * @Description  帐号是否被注册
	 * @param String username 用户名
	 * @param String usertype 用户类型 
	 * @return boolean
	 */
	public boolean checkReg(String username,String usertype) {
		return dbc_userinfodao.checkReg(username,usertype);
	}
	
	/**
	 * @Title checkReg
	 * @Description  帐号是否被注册
	 * @param String username 用户名
	 * @param String usertype 用户类型 
	 * @param isneedclose 是否开启session
	 * @return boolean
	 */
	public boolean checkReg(String username,String usertype,Boolean isneedclose){
		return dbc_userinfodao.checkReg(username, usertype, isneedclose);
	}

}
