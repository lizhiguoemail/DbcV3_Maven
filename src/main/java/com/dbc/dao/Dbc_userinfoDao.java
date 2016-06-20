package com.dbc.dao;

import java.util.List;

import com.dbc.pojo.Dbc_userinfo;
import com.dbc.util.Dbcutil;
import com.singlee.sda.security.SecurityUtils;

public interface Dbc_userinfoDao extends BaseDao{
	
	/**
	 * @Title checkReg
	 * @Description  帐号是否被注册
	 * @param String username 用户名
	 * @param String usertype 用户类型 
	 * @return boolean
	 */
	public boolean checkReg(String username,String usertype);
	
	/**
	 * @Title checkReg
	 * @Description  帐号是否被注册
	 * @param String username 用户名
	 * @param String usertype 用户类型 
	 * @param isneedclose 是否开启session
	 * @return boolean
	 */
	public boolean checkReg(String username,String usertype,Boolean isneedclose);
	
	/**
	 * @Title checkNickname
	 * @Description  呢称是否被使用
	 * @param string nickname 昵称
	 * @param String usertype 用户类型
	 * @return boolean
	 */
	public boolean checkNickname(String nickname,String usertype);
	
	/**
	 * @Title checkNickname
	 * @Description  呢称是否被使用
	 * @param string nickname 昵称
	 * @param String usertype 用户类型
	 * @param isneedclose 是否开启session
	 * @return boolean
	 */
	public boolean checkNickname(String nickname,String usertype,Boolean isneedclose);
	
	/**
	 * @Title checkLogin
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @return UserBean
	 */
	public Dbc_userinfo checkLogin(String username, String userpwd,String usertype);
	
	/**
	 * @Title checkLogin
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @param isneedclose 是否开启session
	 * @return UserBean
	 */
	public Dbc_userinfo checkLogin(String username, String userpwd,String usertype,Boolean isneedclose);
	
	/**
	 * @Title checkLoginByField
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @return UserBean
	 */
	public Dbc_userinfo checkLoginByField(String username, String userpwd,String usertype,String[] field,String[] content);
	
	/**
	 * @Title checkLoginByField
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @param isneedclose 是否开启session
	 * @return UserBean
	 */
	public Dbc_userinfo checkLoginByField(String username, String userpwd,String usertype,String[] field,String[] content,Boolean isneedclose);
	
	/**
	 * @Title checkPwd
	 * @Description  验证密码是否正确
	 * @param username
	 * @param userpwd
	 * @param usertype
	 * @return Boolean
	 */
	public Boolean checkPwd(String username, String userpwd,String usertype);
	
	/**
	 * @Title checkPwd
	 * @Description  验证密码是否正确
	 * @param username
	 * @param userpwd
	 * @param usertype
	 * @param isneedclose 是否开启session
	 * @return Boolean
	 */
	public Boolean checkPwd(String username, String userpwd,String usertype,Boolean isneedclose);
	
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
    public List FindUser(String fieldname,String content,String category,String notfield,String notcontent,String orderfield,String order);
    
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
    public List FindUser(String fieldname,String content,String category,String notfield,String notcontent,String orderfield,String order,Boolean isneedclose);
    
    /**
	 * @Title FindUser
	 * @Description  查询用户
	 * @param fieldname
	 * @param content
	 * @param notfield
	 * @param notlist
	 * @param orderfield
	 * @param order
	 * @return List
	 */
    public List FindUser(String fieldname,String content,String category,String notfield,List notlist,String orderfield,String order);
    
    /**
	 * @Title FindUser
	 * @Description  查询用户
	 * @param fieldname
	 * @param content
	 * @param notfield
	 * @param notlist
	 * @param orderfield
	 * @param order
	 * @param isneedclose 是否开启session
	 * @return List
	 */
    public List FindUser(String fieldname,String content,String category,String notfield,List notlist,String orderfield,String order,Boolean isneedclose);

}
