/**
 * @Project 源自dbc
 * @Title UserinfoDaoImpl.java
 * @Package com.dbc.Impl;
 * @Description  用户类DAO实现类
 * @author caihuajun
 * @date 2009-11-01
 * @version v1.0
 * 2014-08-06 caihuajun 在一些方法中都增加了usertype参数，例如checkReg等
 */
package com.dbc.dao.Impl;

import java.util.List;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.util.Dbcutil;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dbc.dao.BaseDao;
import com.dbc.dao.Dbc_userinfoDao;
import com.singlee.sda.security.SecurityUtils;

public class Dbc_userinfoDaoImpl extends BaseDaoImpl implements Dbc_userinfoDao{
	
	/**
	 * @Title checkReg
	 * @Description  帐号是否被注册
	 * @param String username 用户名
	 * @param String usertype 用户类型 
	 * @return boolean
	 */
	public boolean checkReg(String username,String usertype) {
		username=Dbcutil.searchchuli(username);
		Session session=this.openSession();
		session.beginTransaction();
		try{
			String hql=" from Dbc_userinfo where  deletemark='0' and username='"+username+"'";
			if(usertype!=null&&!"".equals(usertype)){
				hql=hql+" and usertype='"+usertype+"'";
			}
			List userlist=session.createQuery(hql).list();
			session.getTransaction().commit();
			session.close();
			if(userlist.size()>0){
				return true;//存在
			}else{
				return false;//不存在
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Title checkReg
	 * @Description  帐号是否被注册
	 * @param String username 用户名
	 * @param String usertype 用户类型 
	 * @param isneedclose 是否开启session
	 * @return boolean
	 */
	public boolean checkReg(String username,String usertype,Boolean isneedclose) {
		username=Dbcutil.searchchuli(username);
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		try{
			String hql=" from Dbc_userinfo where  deletemark='0' and username='"+username+"'";
			if(usertype!=null&&!"".equals(usertype)){
				hql=hql+" and usertype='"+usertype+"'";
			}
			List userlist=session.createQuery(hql).list();
			if(isneedclose){
				session.getTransaction().commit();
				session.close();
			}
			if(userlist.size()>0){
				return true;//存在
			}else{
				return false;//不存在
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * @Title checkNickname
	 * @Description  呢称是否被使用
	 * @param string nickname 昵称
	 * @param String usertype 用户类型
	 * @return boolean
	 */
	public boolean checkNickname(String nickname,String usertype) {
		nickname=Dbcutil.searchchuli(nickname);
		Session session=this.openSession();
		session.beginTransaction();
		try{
			String hql=" from Dbc_userinfo where  deletemark='0' and nickname='"+nickname+"'";
			if(usertype!=null&&!"".equals(usertype)){
				hql=hql+" and usertype='"+usertype+"'";
			}
			List userlist=session.createQuery(hql).list();
			session.getTransaction().commit();
			session.close();
			if(userlist.size()>0){
				return true;//存在
			}else{
				return false;//不存在
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
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
		nickname=Dbcutil.searchchuli(nickname);
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		try{
			String hql=" from Dbc_userinfo where  deletemark='0' and nickname='"+nickname+"'";
			if(usertype!=null&&!"".equals(usertype)){
				hql=hql+" and usertype='"+usertype+"'";
			}
			List userlist=session.createQuery(hql).list();
			if(isneedclose){
				session.getTransaction().commit();
				session.close();
			}
			if(userlist.size()>0){
				return true;//存在
			}else{
				return false;//不存在
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Title checkLogin
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @return UserBean
	 */
	public Dbc_userinfo checkLogin(String username, String userpwd,String usertype) {
		username=Dbcutil.searchchuli(username);
		userpwd=Dbcutil.searchchuli(userpwd);
		Session session=this.openSession();
		session.beginTransaction();
		String hql=" from Dbc_userinfo where  deletemark='0' and username='"+username+"' and password='"+userpwd+"'";
		if(usertype!=null&&!"".equals(usertype)){
			hql=hql+" and usertype='"+usertype+"'";
		}
		List userlist=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		if(userlist.size()>0)
		{
			return (Dbc_userinfo)userlist.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * @Title checkLogin
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @param isneedclose 是否开启session
	 * @return UserBean
	 */
	public Dbc_userinfo checkLogin(String username, String userpwd,String usertype,Boolean isneedclose) {
		username=Dbcutil.searchchuli(username);
		userpwd=Dbcutil.searchchuli(userpwd);
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String hql=" from Dbc_userinfo where  deletemark='0' and username='"+username+"' and password='"+userpwd+"'";
		if(usertype!=null&&!"".equals(usertype)){
			hql=hql+" and usertype='"+usertype+"'";
		}
		List userlist=session.createQuery(hql).list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		if(userlist.size()>0)
		{
			return (Dbc_userinfo)userlist.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * @Title checkLoginByField
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @return UserBean
	 */
	public Dbc_userinfo checkLoginByField(String username, String userpwd,String usertype,String[] field,String[] content) {
		username=Dbcutil.searchchuli(username);
		userpwd=Dbcutil.searchchuli(userpwd);
		Session session=this.openSession();
		session.beginTransaction();
		String hql=" from Dbc_userinfo where  deletemark='0'  and username='"+username+"' and password='"+userpwd+"'";
		if(usertype!=null&&!"".equals(usertype)){
			hql=hql+" and usertype='"+usertype+"'";
		}
		if(field!=null&&content!=null&&field.length==content.length)
		{
			for(int i=0;i<field.length;i++)
			{
				hql=hql+" and "+field[i]+" ='"+content[i]+"' ";
			}
		}
		List userlist=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		if(userlist.size()>0)
		{
			return (Dbc_userinfo)userlist.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * @Title checkLoginByField
	 * @Description  验证是否登录成功，并返回用户信息，如果不成功返回null
	 * @param isneedclose 是否开启session
	 * @return UserBean
	 */
	public Dbc_userinfo checkLoginByField(String username, String userpwd,String usertype,String[] field,String[] content,Boolean isneedclose) {
		username=Dbcutil.searchchuli(username);
		userpwd=Dbcutil.searchchuli(userpwd);
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String hql=" from Dbc_userinfo where  deletemark='0'  and username='"+username+"' and password='"+userpwd+"'";
		if(usertype!=null&&!"".equals(usertype)){
			hql=hql+" and usertype='"+usertype+"'";
		}
		if(field!=null&&content!=null&&field.length==content.length)
		{
			for(int i=0;i<field.length;i++)
			{
				hql=hql+" and "+field[i]+" ='"+content[i]+"' ";
			}
		}
		List userlist=session.createQuery(hql).list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		if(userlist.size()>0)
		{
			return (Dbc_userinfo)userlist.get(0);
		}
		else
		{
			return null;
		}
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
		Session session=this.openSession();
		session.beginTransaction();
		username=Dbcutil.searchchuli(username);
		userpwd=Dbcutil.searchchuli(userpwd);
		String password=SecurityUtils.md5(userpwd);
		String hql=" from Dbc_userinfo where  deletemark='1' and username='"+username+"' and password='"+password+"'";
		if(usertype!=null&&!"".equals(usertype)){
			hql=hql+" and usertype='"+usertype+"'";
		}
		List userlist=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		if(userlist.size()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
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
	public Boolean checkPwd(String username, String userpwd,String usertype,Boolean isneedclose) {
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		username=Dbcutil.searchchuli(username);
		userpwd=Dbcutil.searchchuli(userpwd);
		String password=SecurityUtils.md5(userpwd);
		String hql=" from Dbc_userinfo where  deletemark='1' and username='"+username+"' and password='"+password+"'";
		if(usertype!=null&&!"".equals(usertype)){
			hql=hql+" and usertype='"+usertype+"'";
		}
		List userlist=session.createQuery(hql).list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		if(userlist.size()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
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
    public List FindUser(String fieldname,String content,String category,String notfield,String notcontent,String orderfield,String order){
    	Session session=this.openSession();
		session.beginTransaction();
    	String HQL="from Dbc_userinfo where deletemark='0' ";
		if(!"".equals(notfield)&&notfield!=null&&notcontent!=null&&!"".equals(notcontent))
     	{
			HQL+="and  "+notfield+"!='"+notcontent+"' ";
		}
		if(!"".equals(category)&&category!=null){
			HQL+=" and category='"+category+"' ";
		}
		if(!"".equals(fieldname)&&fieldname!=null&&content!=null&&!"".equals(content))
     	{
			HQL+="and  "+fieldname+" like '%"+content+"%' ";
		}
		if(!"".equals(orderfield)&&orderfield!=null)
		{
		HQL+=" order by "+orderfield+" "+order;
		}
		List list=session.createQuery(HQL).list();
		session.getTransaction().commit();
		session.close();
    	return list;
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
    	Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
    	String HQL="from Dbc_userinfo where deletemark='0' ";
		if(!"".equals(notfield)&&notfield!=null&&notcontent!=null&&!"".equals(notcontent))
     	{
			HQL+="and  "+notfield+"!='"+notcontent+"' ";
		}
		if(!"".equals(category)&&category!=null){
			HQL+=" and category='"+category+"' ";
		}
		if(!"".equals(fieldname)&&fieldname!=null&&content!=null&&!"".equals(content))
     	{
			HQL+="and  "+fieldname+" like '%"+content+"%' ";
		}
		if(!"".equals(orderfield)&&orderfield!=null)
		{
		HQL+=" order by "+orderfield+" "+order;
		}
		List list=session.createQuery(HQL).list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
    	return list;
    }
    
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
    public List FindUser(String fieldname,String content,String category,String notfield,List notlist,String orderfield,String order){
    	Session session=this.openSession();
		session.beginTransaction();
    	String str="";
    	for(int i=0;i<notlist.size();i++)
    	{
    		if(i==0)
    		{
    			str="'"+notlist.get(i)+"'";
    		}
    		else
    		{
    			str=str+",'"+notlist.get(i)+"'";
    		}
    	}
		String HQL="from Dbc_userinfo where deletemark='0' ";
		if(!"".equals(category)&&category!=null){
			HQL+=" and category='"+category+"' ";
		}
		if(!"".equals(fieldname)&&fieldname!=null&&content!=null&&!"".equals(content))
     	{
			HQL+="and  "+fieldname+" like '%"+content+"%' ";
		}
		if(!"".equals(notfield)&&notfield!=null&&!"".equals(str))
     	{
			HQL+="and  "+notfield+" not in("+str+")";
		}
		if(!"".equals(orderfield)&&orderfield!=null)
		{
		HQL+=" order by "+orderfield+" "+order;
		}
		List list=session.createQuery(HQL).list();
		session.getTransaction().commit();
		session.close();
    	return list;
    }
    
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
    public List FindUser(String fieldname,String content,String category,String notfield,List notlist,String orderfield,String order,Boolean isneedclose){
    	Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}

    	String str="";
    	for(int i=0;i<notlist.size();i++)
    	{
    		if(i==0)
    		{
    			str="'"+notlist.get(i)+"'";
    		}
    		else
    		{
    			str=str+",'"+notlist.get(i)+"'";
    		}
    	}
		String HQL="from Dbc_userinfo where deletemark='0' ";
		if(!"".equals(category)&&category!=null){
			HQL+=" and category='"+category+"' ";
		}
		if(!"".equals(fieldname)&&fieldname!=null&&content!=null&&!"".equals(content))
     	{
			HQL+="and  "+fieldname+" like '%"+content+"%' ";
		}
		if(!"".equals(notfield)&&notfield!=null&&!"".equals(str))
     	{
			HQL+="and  "+notfield+" not in("+str+")";
		}
		if(!"".equals(orderfield)&&orderfield!=null)
		{
		HQL+=" order by "+orderfield+" "+order;
		}
		List list=session.createQuery(HQL).list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
    	return list;
    }
}
