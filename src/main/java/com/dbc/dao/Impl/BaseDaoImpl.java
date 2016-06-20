/**
 * @Project 源自dbc
 * @Title BaseDao.java
 * @Package com.dbc.dao;
 * @Description  基类DAO
 * @author caihuajun
 * @date 2009-11-01
 * @version v1.0
 * 2009-11-10 caihuajun 因为删除方法都改为了逻辑删除，所以查询的时候需要对deletemark过滤，deletemark为1的都视为已经被删除
 * 2011-06-14 caihuajun 脑子开窍，增加了基类的查询方法 分别为selObject，selObjectByNum，selObjectpage 大大节省了别的dao类的代码量，以后就不用重复写这样的代码了
 * 2011-07-18 caihuajun 添加了一些优化查询的方法，在方法名后面增加arr的方法都是今天增加的，优化方面：减少了一次数据库查询。
 * 2011-09-05 caihuajun 添加了ext分页模式的查询
 * 2011-09-08 caihuajun 改进了查询模式，添加searcharr参数
 * 2011-10-26 caihuajun 修改了每个方法的注释，使得调用的方法能更容易明白参数的意思
 * 2011-10-27 caihuajun 优化了查询方法，去除了查询列表总数的排序
 * 2012-03-30 caihuajun 郁闷，在linux环境下太变态了，大小写这么严格，于是把getSortCode和getSortCode_Double这两个方法的表名都统一强制变成小写的
 * 2012-03-31 caihuajun 优化了获取最大排序码的方法
 * 2012-03-31 caihuajun 增加了获得某张表某个字段的最大值或者最小值的三个方法，分别对应integer，long，double三个类型
 * 2012-05-29 caihuajun 增加了selbyonefieldarrvalue这一系列的方法，还增加了deletebyfieldarr方法
 * 2012-05-30 caihuajun 修改了增删改的方法的返回值，都设置成void，并且把try catch的方法去掉了，不然事务不能回滚
 * 2012-06-01 caihuajun 在saveObject和updateObject方法中添加了super.getHibernateTemplate().flush();解决了在事务中这些操作不能提交的问题
 * 2012-06-12 caihuajun 增加了setbyonefieldarrvalue(Class clazz,String fieldname,String[] contentarr,String setfieldname,String setvalue,String orderfieldname,String order,boolean islike)方法
 * 2013-04-22 caihuajun 增加了selObjectbyarrEXT相关的三个方法，扩展了按多个条件查询的方法
 * 2013-05-05 caihuajun 增加selObjectbysearcharrpage,selObjectbysearcharrorarrpage方法
 * 2013-07-10 caihuajun 又多了个selObjectEXT，用来过滤条件用的fieldnamemust[]和valuemust[] islike[]
 * 2014-10-14 caihuajun 创建v2版本，以前写的方法大改版，分页参数合并到一个对象里面
 * 2014-11-01 caihuajun 又多了个selObjectEXT，用来过滤条件用的fieldnamemust[]和valuemust[] islike[]
 * 2014-11-08 caihuajun 增加了根据自定义sql语句查询列表的2个方法（分页，不分页）
 * 2014-04-10 caihuajun 删除增加了and和or的判断deletebyfieldarrforandor，以后方法要加and或者or，就在方法名后面增加forandor
 * 2014-05-29 caihuajun 增加了deletaAll方法
 * 2015-08-06 caihuajun 增加方法 deletebysql
 * 2015-10-30 把 where (deletemark is null || deletemark!='1') 改成了 where deletemark!='1' ，因为我把deletemark字段设置成了非空字段，默认值为0，要不然索引用不上
 * 2015-11-25 caihuajun 增加方法deletebyids
 * 2015-12-08 caihuajun 终于搞清楚了为什么数据库变更lucene的索引没有变更的原因，是因为bulkUpdate方法不会触发索引的变更，能触发索引变更的是super.getHibernateTemplate().saveOrUpdate，super.getHibernateTemplate().save，super.getHibernateTemplate().update，super.getHibernateTemplate().delete
 */
package com.dbc.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import com.dbc.dao.BaseDao;
import com.dbc.util.Page;
import com.dbc.util.PageParm;


public class BaseDaoImpl implements BaseDao{
	
	private SessionFactory sessionFactory;
    
	/**
	 * @Title saveObject
	 * @Description  保存对象  
	 * @param  obj 保存的对象
	 * @return void  
	 */
	public void saveObject(Object obj) {
		this.getCurrentSession().save(obj);
	}
	
	/**
	 * @Title updateObject
	 * @Description   修改对象
	 * @param  obj 修改的对象
	 * @return void  
	 */
	public void updateObject(Object obj) {
		this.getCurrentSession().update(obj);
	}
	
	/**
	 * @Title saveObject
	 * @Description  保存对象 
	 * @param  obj 保存的对象
	 * @return void  
	 */
	public void saveorupdateObject(Object obj) {
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	/**
	 * @Title deletObject
	 * @Description   删除对象
	 * @param  obj 要删除的对象
	 * @return void  
	 */
	public void deletObject(Object obj) {
		this.getCurrentSession().delete(obj);
	}
	
	/**
	 * @Title setObjectbyidsold  这个方法不支持索引更新
	 * @Description   根据ids批量修改对象
	 * @param  clazz 类名
     * @param  setfieldnamearr 要修改的字段组
     * @param  setcontenarr  要修改的值组
	 * @param ids  要修改对象的id
	 * @return void
	 */
	public void setObjectbyids(Class clazz,String[] setfieldnamearr,String[] setcontentarr,String[] ids) {
		String hql=" update "+clazz.getSimpleName();
		if(setfieldnamearr!=null){
			for(int i=0;i<setfieldnamearr.length;i++)
			{
				if(i==0)
				{
					if("null".equals(setcontentarr[i]))
					{
						hql=hql+" set "+setfieldnamearr[i]+" = null ";
					}
					else
					{
						hql=hql+" set "+setfieldnamearr[i]+" = '"+setcontentarr[i]+"'";
					}
				}
				else
				{
					if("null".equals(setcontentarr[i]))
					{
						hql=hql+" , "+setfieldnamearr[i]+" = null";
					}
					else
					{
						hql=hql+" , "+setfieldnamearr[i]+" = '"+setcontentarr[i]+"'";
					}
				}
			}
		}
		hql=hql+" where id in (:ids)";
		Query query = this.getCurrentSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.executeUpdate();
	}
	
	/**
	 * @Title deletebyids
	 * @Description   根据ids批量修改对象
	 * @param  clazz 类名
	 * @param ids  要修改对象的id
	 * @return void
	 */
	public void deletebyids(Class clazz,String[] ids,boolean islogic) {
		String hql = "delete "+clazz.getSimpleName()+" where id in (:ids) ";
		if(islogic){
			  hql = "update "+clazz.getSimpleName()+" set deletemark='1' where id in (:ids) ";
		}
       Query query = this.getCurrentSession().createQuery(hql);
       query.setParameterList("ids", ids);
       query.executeUpdate();
	}
	
	/**
	 * @Title setObjectbyarr
	 * @Description   根据某个字段修改对象
	 * @param  clazz 类名
     * @param  setfieldnamearr 要修改的字段数组
     * @param  setcontentarr 要修改的值数组
     * @param  fieldnamearr 根据修改的字段数组
	 * @param contentarr  根据修改的值数组
	 * @return void
	 */
	public void setObjectbyarr(Class clazz,String[] setfieldnamearr,String[] setcontentarr,String[] fieldnamearr,String[] contentarr) {
		String hql=" update "+clazz.getSimpleName();
		if(setfieldnamearr!=null){
			for(int i=0;i<setfieldnamearr.length;i++)
			{
				if(i==0)
				{
					if("null".equals(setcontentarr[i]))
					{
						hql=hql+" set "+setfieldnamearr[i]+" = null ";
					}
					else
					{
						hql=hql+" set "+setfieldnamearr[i]+" = '"+setcontentarr[i]+"'";
					}
				}
				else
				{
					if("null".equals(setcontentarr[i]))
					{
						hql=hql+" , "+setfieldnamearr[i]+" = null";
					}
					else
					{
						hql=hql+" , "+setfieldnamearr[i]+" = '"+setcontentarr[i]+"'";
					}
				}
			}
		}
		if(fieldnamearr!=null){
			for(int i=0;i<fieldnamearr.length;i++)
			{
				if(i==0)
				{
					hql=hql+" where "+fieldnamearr[i]+" = '"+contentarr[i]+"'";
				}
				else
				{
					hql=hql+" and "+fieldnamearr[i]+" = '"+contentarr[i]+"'";
				}
			}
		}
        Query query = this.getCurrentSession().createQuery(hql);
        query.executeUpdate();
	}
	
	
	/**
	 * @Title deletebyfieldarr
	 * @Description   根据某指定字段值数组批量删除对象,条件是or的关系
	 * @param  clazz 类名
     * @param  fieldnamearr 根据的字段
     * @param  contentarr  数组值
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deletebyfieldarr(Class clazz,String[] fieldnamearr,String[] contentarr,boolean islogic) {
		String hql = "";
		 if(islogic){
				hql=" update "+clazz.getSimpleName()+" set deletemark='1' where 1=2 ";
				if(fieldnamearr!=null&&contentarr!=null&&fieldnamearr.length==contentarr.length)
				{
					for(int i=0;i<contentarr.length;i++)
					{
						hql=hql+" or "+fieldnamearr[i]+"='"+contentarr[i]+"' ";
					}
				}
			}
			else{
				hql=" delete from  "+clazz.getSimpleName()+"  where 1=2 ";
				if(fieldnamearr!=null&&contentarr!=null&&fieldnamearr.length==contentarr.length)
				{
					for(int i=0;i<contentarr.length;i++)
					{
						hql=hql+" or "+fieldnamearr[i]+"='"+contentarr[i]+"' ";
					}
				}
			}
          Query query = this.getCurrentSession().createQuery(hql);
          query.executeUpdate();
	}
	
	/**
	 * @Title deletebyfieldarrforandor
	 * @Description   根据某指定字段值数组批量删除对象,条件是or或者and选择的关系
	 * @param  clazz 类名
     * @param  fieldnamearr 根据的字段
     * @param  contentarr  数组值
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deletebyfieldarrforandor(Class clazz,String[] fieldnamearr,String[] contentarr,String andor,boolean islogic) {
		String hql="";
    	if(islogic){
			if("or".equals(andor)){
				hql=" update "+clazz.getSimpleName()+" set deletemark='1' where 1=2 ";
				if(fieldnamearr!=null&&contentarr!=null&&fieldnamearr.length==contentarr.length)
				{
					for(int i=0;i<contentarr.length;i++)
					{
						hql=hql+" or "+fieldnamearr[i]+"='"+contentarr[i]+"' ";
					}
				}
			}
			else{
				hql=" update "+clazz.getSimpleName()+" set deletemark='1' where  1=1 ";
				if(fieldnamearr!=null&&contentarr!=null&&fieldnamearr.length==contentarr.length)
				{
					for(int i=0;i<contentarr.length;i++)
					{
						hql=hql+" and "+fieldnamearr[i]+"='"+contentarr[i]+"' ";
					}
				}
			}
			
		}
		else{
			if("or".equals(andor)){
				 hql=" delete from  "+clazz.getSimpleName()+"  where 1=2 ";
				if(fieldnamearr!=null&&contentarr!=null&&fieldnamearr.length==contentarr.length)
				{
					for(int i=0;i<contentarr.length;i++)
					{
						hql=hql+" or "+fieldnamearr[i]+"='"+contentarr[i]+"' ";
					}
				}
			}
			else{
				hql=" delete from  "+clazz.getSimpleName()+"  where 1=1";
				if(fieldnamearr!=null&&contentarr!=null&&fieldnamearr.length==contentarr.length)
				{
					for(int i=0;i<contentarr.length;i++)
					{
						hql=hql+" and "+fieldnamearr[i]+"='"+contentarr[i]+"' ";
					}
				}
			}
		}
    	this.getCurrentSession().createQuery(hql).executeUpdate();
	}
	
	/**
	 * @Title deleteAll
	 * @Description   删除所有
	 * @param  clazz 类名
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deleteAll(Class clazz,boolean islogic) {
		if(islogic){
			String hql=" update "+clazz.getSimpleName()+" set deletemark='1'";
			this.getCurrentSession().createQuery(hql).executeUpdate();
		}
		else{
			String hql=" delete from  "+clazz.getSimpleName()+" ";
			this.getCurrentSession().createQuery(hql).executeUpdate();
		}
	}
	
	/**
	 * @Title deletebysql
	 * @Description   根据sql条件删除
	 * @param  clazz 类名
	 * @param  where  条件
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deletebysql(Class clazz,String where,boolean islogic) {
		String wherestr=" where 1=2 ";
		if(where!=null&&!"".equals(where)){
			wherestr=" where "+where;
		}
		if(islogic){
			String hql=" update "+clazz.getSimpleName()+" set deletemark='1'"+wherestr;
			this.getCurrentSession().createQuery(hql).executeUpdate();
		}
		else{
			String hql=" delete from  "+clazz.getSimpleName()+wherestr;
			this.getCurrentSession().createQuery(hql).executeUpdate();
		}
	}
	
	/**
	 * @Title selByOid
	 * @Description  查找指定ID的对象
	 * @param  clazz 类名
	 * @param  oid 对象id
	 * @return Object  对象
	 */
	public Object selByOid(Class clazz, String oid) {
		Object obj=null;
		Session session=this.openSession();
		try{
			session.beginTransaction();
			String hql=" from "+clazz.getSimpleName()+" where id='"+oid+"'";
			List result=session.createQuery(hql).list();
			session.getTransaction().commit();
			session.close();
			if(result!=null&&result.size()>0){
				obj=result.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * @Title selByOid
	 * @Description  查找指定ID的对象
	 * @param  clazz 类名
	 * @param  oid 对象id
	 * @parm   isneedclose 是否开启session
	 * @return Object  对象
	 */
	public Object selByOid(Class clazz, String oid,Boolean isneedclose) {
		Object obj=null;
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		try{
			String hql=" from "+clazz.getSimpleName()+" where id='"+oid+"'";
			List result=session.createQuery(hql).list();
			if(result!=null&&result.size()>0){
				obj=result.get(0);
			}
			if(isneedclose){
				session.getTransaction().commit();
				session.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * @Title selByOids
	 * @Description  查找指定IDS的对象
	 * @param  clazz 类名
	 * @param  oids 对象ids
     * @param orderfieldname 排序字段
	 * @param order 顺序
	 * @return List  对象集合
	 */
	public List selByOids(Class clazz,String[] oids,String[] orderfieldnamearr,String[] orderarr) {
		List list=new ArrayList();
		Session session=this.openSession();
		session.beginTransaction();
		String hql="from "+clazz.getSimpleName()+" where deletemark='0' and (1=2 ";
		String ids="";
		if(oids!=null&&oids.length>0)
		{
			for(int i=0;i<oids.length;i++)
			{
				if(i<oids.length-1){
				    ids+="'"+oids[i]+"',";
				}else{
					ids+="'"+oids[i]+"'";
				}
			}
		}
		if(!"".equals(ids))
		{
			hql=hql+" or id in("+ids+") )";
			if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length){
				for(int i=0;i<orderfieldnamearr.length;i++)
		    	{
		    		if(i==0)
		    		{
		    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
		    		}
		    		else
		    		{
		    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
		    		}
		    	}
			}
			list=session.createQuery(hql).list();
			session.getTransaction().commit();
			session.close();
		}
		return list;
	}
	
	/**
	 * @Title selByOids
	 * @Description  查找指定IDS的对象
	 * @param  clazz 类名
	 * @param  oids 对象ids
     * @param orderfieldname 排序字段
	 * @param order 顺序
	 * @param isneedclose 是否开启session
	 * @return List  对象集合
	 */
	public List selByOids(Class clazz,String[] oids,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose) {
		List list=new ArrayList();
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String hql="from "+clazz.getSimpleName()+" where deletemark='0' and (1=2 ";
		String ids="";
		if(oids!=null&&oids.length>0)
		{
			for(int i=0;i<oids.length;i++)
			{
				if(i<oids.length-1){
				    ids+="'"+oids[i]+"',";
				}else{
					ids+="'"+oids[i]+"'";
				}
			}
		}
		if(!"".equals(ids))
		{
			hql=hql+" or id in("+ids+") )";
			if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length){
				for(int i=0;i<orderfieldnamearr.length;i++)
		    	{
		    		if(i==0)
		    		{
		    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
		    		}
		    		else
		    		{
		    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
		    		}
		    	}
			}
			list=session.createQuery(hql).list();
			if(isneedclose){
				session.getTransaction().commit();
				session.close();
			}
		}
		return list;
	}
	
	/**
	 * @Title selEntity
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntity(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				 hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				 hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    List list=new ArrayList();
		list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title selEntity
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 * @throws
	 */
	public List selEntity(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose) {
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				 hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				 hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    List list=new ArrayList();
		list=session.createQuery(hql).list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return list;
	}
	
	/**
	 * @Title selEntityBySql
	 * @Description  根据sql语句查找对象列表
	 * @param  clazz 类名
	 * @param  sql sql语句
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  列表
	 */
	public List selEntityBySql(Class clazz, String sql,String[] orderfieldnamearr,String[] orderarr) {
		Session session=this.openSession();
		session.beginTransaction();
	    String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(sql!=null&&!"".equals(sql)){
	    	hql+="  "+sql;
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
		List list=new ArrayList();
		list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title selEntityBySql
	 * @Description  根据sql语句查找对象列表
	 * @param  clazz 类名
	 * @param  sql sql语句
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  列表
	 */
	public List selEntityBySql(Class clazz, String sql,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose) {
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
	    String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(sql!=null&&!"".equals(sql)){
	    	hql+="  "+sql;
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
		List list=new ArrayList();
		list=session.createQuery(hql).list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return list;
	}
	
	/**
	 * @Title selEntityBySqlnum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param  sql sql语句
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntityBySqlnum(Class clazz,String sql,String[] orderfieldnamearr,String[] orderarr,int num) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(sql!=null&&!"".equals(sql)){
	    	hql+="  "+sql;
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query=session.createQuery(hql);
	    query.setFirstResult(0);
		query.setMaxResults(num);
        List list=new ArrayList();
	    list=this.getListForPage(hql,0, num,session);
	    session.getTransaction().commit();
	    session.close();
		return list;
	}
	
	/**
	 * @Title selEntityBySqlnum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param  sql sql语句
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySqlnum(Class clazz,String sql,String[] orderfieldnamearr,String[] orderarr,int num,Boolean isneedclose) {
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(sql!=null&&!"".equals(sql)){
	    	hql+="  "+sql;
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query=session.createQuery(hql);
	    query.setFirstResult(0);
		query.setMaxResults(num);
        List list=new ArrayList();
	    list=this.getListForPage(hql,0, num,session);
	    if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return list;
	}
	
	/**
	 * @Title selBySql
	 * @Description  根据sql语句查找
	 * @param  sql sql语句
	 * @return List  列表
	 */
	public List selBySql(String sql) {
		Session session=this.openSession();
		session.beginTransaction();
		Query query= session.createSQLQuery(sql);
		session.getTransaction().commit();
		session.close();
		return query.list();
	}
	
	/**
	 * @Title selBySql
	 * @Description  根据sql语句查找
	 * @param  sql sql语句
	 * @param isneedclose 是否需要关闭session
	 * @return List  列表
	 */
	public List selBySql(String sql,Boolean isneedclose){
		List list=new ArrayList();
		if(isneedclose){
			Session session=this.openSession();
			session.beginTransaction();
			Query query= session.createSQLQuery(sql);
			list=query.list();
			session.getTransaction().commit();
			session.close();
		}
		else{
			Session session=this.getCurrentSession();
			Query query= session.createSQLQuery(sql);
			list=query.list();
		}
		return list;
	}
	
	/**
	 * @Title selBySqlPage
	 * @Description  根据sql语句查找
	 * @param  lensql 获取长度sql
	 * @param  sql  sql语句
	 * @param pageparm 分页参数
	 * @return List  列表
	 */
	public List selBySqlPage(String lensql,String sql,PageParm pageparm) {
		List returnlist=new ArrayList();
		Session session=this.openSession();
		session.beginTransaction();
		Query lenquery= session.createSQLQuery(lensql);
		int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
		PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
        Query query=session.createSQLQuery(sql);
	    query.setFirstResult((pageparm.getNowpage()-1)*pageparm.getPagesize());//设置从第几条开始查,pageParm.getNowpage() 为页码
		query.setMaxResults(pageparm.getPagesize());//最大显示的条数
		List objectlist=query.list();
		returnlist.add(0, objectlist);
		returnlist.add(1, pageParm);
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title selBySqlPage
	 * @Description  根据sql语句查找
	 * @param  lensql 获取长度sql
	 * @param  sql  sql语句
	 * @param pageparm 分页参数
	 * @param isneedclose 是否开启session
	 * @return List  列表
	 */
	public List selBySqlPage(String lensql,String sql,PageParm pageparm,Boolean isneedclose) {
		List returnlist=new ArrayList();
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		Query lenquery= session.createSQLQuery(lensql);
		int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
		PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
        Query query=session.createSQLQuery(sql);
	    query.setFirstResult((pageparm.getNowpage()-1)*pageparm.getPagesize());//设置从第几条开始查,pageParm.getNowpage() 为页码
		query.setMaxResults(pageparm.getPagesize());//最大显示的条数
		List objectlist=query.list();
		returnlist.add(0, objectlist);
		returnlist.add(1, pageParm);
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return returnlist;
	}
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询对象分页列表   
	 * @param  clazz
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntityBySqlPage(Class clazz,String sql,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr) {
		List returnlist=new ArrayList();
		Session session=this.openSession();
		session.beginTransaction();
		String lensql="select count(id) from "+clazz.getSimpleName()+"  where deletemark='0'  ";   
	    String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(sql!=null&&!"".equals(sql)){
	    	hql+=" "+sql;
	    	lensql+=" "+sql;
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length){
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	      }
	    Query lenquery=session.createQuery(lensql);
        int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
        PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
	    list=this.getListForPage(hql,(pageparm.getNowpage()-1)*pageparm.getPagesize(), pageParm.getPagesize(),session);
	    returnlist.add(0, list);
		returnlist.add(1, pageParm);
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询对象分页列表   
	 * @param  clazz
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySqlPage(Class clazz,String sql,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose) {
		List returnlist=new ArrayList();
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String lensql="select count(id) from "+clazz.getSimpleName()+"  where deletemark='0'  ";   
	    String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(sql!=null&&!"".equals(sql)){
	    	hql+=" "+sql;
	    	lensql+=" "+sql;
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length){
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	      }
	    Query lenquery=session.createQuery(lensql);
        int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
        PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
	    list=this.getListForPage(hql,(pageparm.getNowpage()-1)*pageparm.getPagesize(), pageParm.getPagesize(),session);
	    returnlist.add(0, list);
		returnlist.add(1, pageParm);
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return returnlist;
	}
	
	/**
	 * @Title selEntityBySql
	 * @Description 根据sql语句查询分页列表   
	 * @param sql 自定义sql
	 * @return List  
	 * @throws
	 */
	public List selEntityBySql(String sql) {
		List returnlist=new ArrayList();
		Session session=this.openSession();
		session.beginTransaction();
	    Query q =session.createQuery(sql);
	    returnlist=q.list();
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title selEntityBySql
	 * @Description 根据sql语句查询分页列表   
	 * @param sql 自定义sql
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySql(String sql,Boolean isneedclose) {
		List returnlist=new ArrayList();
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
        Query q =session.createQuery(sql);
        returnlist=q.list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return returnlist;
	}
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询分页列表   
	 * @param lensql 自定义sqlcount
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntityBySqlPage(String lensql,String sql,PageParm pageparm) {
		List returnlist=new ArrayList();
		Session session=this.openSession();
		session.beginTransaction();
	    Query lenquery=session.createQuery(lensql);
        int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
        PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
	    list=this.getListForPage(sql,(pageparm.getNowpage()-1)*pageparm.getPagesize(), pageParm.getPagesize(),session);
	    returnlist.add(0, list);
		returnlist.add(1, pageParm);
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询分页列表   
	 * @param lensql 自定义sqlcount
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySqlPage(String lensql,String sql,PageParm pageparm,Boolean isneedclose) {
		List returnlist=new ArrayList();
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
	    Query lenquery=session.createQuery(lensql);
        int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
        PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
	    list=this.getListForPage(sql,(pageparm.getNowpage()-1)*pageparm.getPagesize(), pageParm.getPagesize(),session);
	    returnlist.add(0, list);
		returnlist.add(1, pageParm);
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return returnlist;
	}
	
	/**
	 * @Title selEntityByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntityByPage(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr) {
		List returnlist=new ArrayList();
		Session session=this.openSession();
		session.beginTransaction();
		String lensql="select count(id) from "+clazz.getSimpleName()+"  where deletemark='0' ";
		String hql="from "+clazz.getSimpleName()+"  where  deletemark!='1' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    				hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				lensql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    				hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
    				hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query lenquery=session.createQuery(lensql);
        int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
        PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
	    list=this.getListForPage(hql,(pageparm.getNowpage()-1)*pageparm.getPagesize(), pageParm.getPagesize(),session);
	    returnlist.add(0, list);
		returnlist.add(1, pageParm);
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title selEntityByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 * @throws
	 */
	public List selEntityByPage(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose) {
		List returnlist=new ArrayList();
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String lensql="select count(id) from "+clazz.getSimpleName()+"  where deletemark='0' ";
		String hql="from "+clazz.getSimpleName()+"  where  deletemark!='1' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    				hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				lensql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    				hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
    				hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query lenquery=session.createQuery(lensql);
        int len=Integer.parseInt(lenquery.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
        PageParm pageParm=page.getnowpage(len, pageparm);
        List list=new ArrayList();
	    list=this.getListForPage(hql,(pageparm.getNowpage()-1)*pageparm.getPagesize(), pageParm.getPagesize(),session);
	    returnlist.add(0, list);
		returnlist.add(1, pageParm);
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return returnlist;
	}
	
	
	
	/**
	 * @Title selEntityBynum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selEntityBynum(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,int num) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	 	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
		 	    		hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
 	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query=session.createQuery(hql);
	    query.setFirstResult(0);
		query.setMaxResults(num);
        List list=new ArrayList();
	    list=this.getListForPage(hql,0, num,session);
	    session.getTransaction().commit();
	    session.close();
		return list;
	}
	
	/**
	 * @Title selEntityBynum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBynum(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,int num,Boolean isneedclose) {
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		String hql="from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	 	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
		 	    		hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
 	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query=session.createQuery(hql);
	    query.setFirstResult(0);
		query.setMaxResults(num);
        List list=new ArrayList();
	    list=this.getListForPage(hql,0, num,session);
	    if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return list;
	}
	
	/**
	 * @Title selObject
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selObject(Class clazz,String selstr,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr) {
		Session session=this.openSession();
		session.beginTransaction();
		if(selstr==null||"".equals(selstr)){
			selstr="id,deletemark";
		}
		String hql="select "+selstr+" from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				 hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				 hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query=session.createSQLQuery(hql);
		List list=query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title selObject
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selObject(Class clazz,String selstr,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose) {
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		if(selstr==null||"".equals(selstr)){
			selstr="id,deletemark";
		}
		String hql="select "+selstr+" from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				 hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				 hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			hql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			hql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query=session.createSQLQuery(hql);
		List list=query.list();
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return list;
	}
	
	/**
	 * @Title selObjectByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selObjectByPage(Class clazz,String selstr, String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr) {
		List returnlist=new ArrayList();
		Session session=this.openSession();
		session.beginTransaction();
		if(selstr==null||"".equals(selstr)){
			selstr="id,deletemark";
		}
		String lensql="select count(id) from "+clazz.getSimpleName()+"  where deletemark='0' ";   
	    String relsql="select "+selstr+" from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	 	    			relsql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				lensql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
		 	    		relsql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
 	    			relsql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			relsql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			relsql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query2=session.createSQLQuery(lensql);
        int len=Integer.parseInt(query2.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
	    PageParm pageParm=page.getnowpage(len, pageparm);
    	Query query=session.createSQLQuery(relsql);
	    query.setFirstResult((pageparm.getNowpage()-1)*pageparm.getPagesize());//设置从第几条开始查,pageParm.getNowpage() 为页码
		query.setMaxResults(pageparm.getPagesize());//最大显示的条数
		List objectlist=query.list();
		returnlist.add(0, objectlist);
		returnlist.add(1, pageParm);
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	
	/**
	 * @Title selObjectByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selObjectByPage(Class clazz,String selstr, String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose) {
		List returnlist=new ArrayList();
		Session session=null;
		if(isneedclose){
			session=this.openSession();
			session.beginTransaction();
		}
		else{
			session=this.getCurrentSession();
		}
		if(selstr==null||"".equals(selstr)){
			selstr="id,deletemark";
		}
		String lensql="select count(id) from "+clazz.getSimpleName()+"  where deletemark='0' ";   
	    String relsql="select "+selstr+" from "+clazz.getSimpleName()+"  where  deletemark='0' ";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	 	    			relsql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				lensql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
		 	    		relsql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
    				lensql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
 	    			relsql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
	   
	    if(orderfieldnamearr!=null&&orderarr!=null&&orderfieldnamearr.length==orderarr.length)
	    {
	    	for(int i=0;i<orderfieldnamearr.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			relsql+=" order by "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    		else
	    		{
	    			relsql+=" , "+orderfieldnamearr[i]+" "+orderarr[i];
	    		}
	    	}
	    }
	    Query query2=session.createSQLQuery(lensql);
        int len=Integer.parseInt(query2.list().get(0).toString());
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
	    PageParm pageParm=page.getnowpage(len, pageparm);
    	Query query=session.createSQLQuery(relsql);
	    query.setFirstResult((pageparm.getNowpage()-1)*pageparm.getPagesize());//设置从第几条开始查,pageParm.getNowpage() 为页码
		query.setMaxResults(pageparm.getPagesize());//最大显示的条数
		List objectlist=query.list();
		returnlist.add(0, objectlist);
		returnlist.add(1, pageParm);
		if(isneedclose){
			session.getTransaction().commit();
			session.close();
		}
		return returnlist;
	}
	
	 /**
	   * @Title getSortCode_Double
	   * @Description 获得最大排序码
	   * @param tablename 表名
	   * @return Double
	   */
	public Double getSortCode_Double(String tablename) {
		Session session=this.getCurrentSession();
		tablename=tablename.toLowerCase();
		String hql="select max(sortcode) from "+tablename+" where deletemark='0' ";
		double maxsortcode=1;
		Query query=session.createSQLQuery(hql);
		List list=query.list();
	    if(list.size()>0){
		  String num=""+list.get(0);
		  if(num!=null&&!"".equals(num)&&!"null".equals(num)){
			  maxsortcode=Double.parseDouble(num)+1;
			}
		}
		return maxsortcode;
	}
	
	/**
	* 使用hql 语句进行操作
	* @param hql     需要执行的hql语句
	* @param offset  设置开始位置
	* @param length  设置读取数据的记录条数
	* @return List   返回所需要的集合。
	*/
	public List getListForPage(final String hql, final int offset,final int length,Session session) {
    	 Query q =session.createQuery(hql);
         q.setFirstResult(offset);
         q.setMaxResults(length);
         List<?> list = q.list();
         return list;
	}
	
    public long createIndexByHibernateSearch(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr){
    	long startTime = new Date().getTime();
    	int BATCH_SIZE = 1000;
    	Session session=this.openSession();
    	FullTextSession fulltextsession = Search.getFullTextSession(session);
    	try{
    		 String hql="from "+clazz.getSimpleName()+" where deletemark='0'";
    		    if(islikearr!=null){
    		    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
    		    		 for(int i=0;i<fieldnamearr.length;i++){
    		    			 if(islikearr[i]){
    		    				 hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
    		    			 }
    		    			 else{
    		    				 hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
    		    			 }
    		    		 }
    		    	 }
    		    }
    		    else{
    		    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
    		    		for(int i=0;i<fieldnamearr.length;i++){
    		    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
    		    		 }
    		    	}
    		    }
    		    ScrollableResults results=fulltextsession.createQuery(hql).setFetchSize(BATCH_SIZE).scroll(ScrollMode.FORWARD_ONLY);
                int index=0;
                while (results.next()) { 
                	index++;
    	            System.out.println("开始插入"+clazz.getSimpleName()+"索引第"+index+"条");
    	            fulltextsession.index(results.get(0));
    	            if (index % BATCH_SIZE == 0) {  
    	            	fulltextsession.flush();
                    	fulltextsession.clear();
    	             }
    	        } 
                fulltextsession.clear();
    	        long endTime = new Date().getTime();  
                System.out.println("建立"+clazz.getSimpleName()+"索引 ， 这花费了" + (endTime - startTime) + " 毫秒来把文档增加到索引里面去!");
                return endTime - startTime;
    	}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
        finally {
        	fulltextsession.close();
        }
    }
    
   //删除索引  
    public long deleteIndexByHibernateSearch(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr){  
    	Session session=this.openSession();
    	session.beginTransaction();
        long startTime = new Date().getTime();  
        int BATCH_SIZE = 1000;  
        FullTextSession s = Search.getFullTextSession(session);  
        String hql="from "+clazz.getSimpleName()+" where deletemark!='1'";
	    if(islikearr!=null){
	    	 if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length&&fieldnamearr.length==islikearr.length){
	    		 for(int i=0;i<fieldnamearr.length;i++){
	    			 if(islikearr[i]){
	    				 hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    			 }
	    			 else{
	    				 hql+="and  "+fieldnamearr[i]+" = '"+valuearr[i]+"' ";
	    			 }
	    		 }
	    	 }
	    }
	    else{
	    	if(fieldnamearr!=null&&valuearr!=null&&fieldnamearr.length==valuearr.length){
	    		for(int i=0;i<fieldnamearr.length;i++){
	    			hql+="and  "+fieldnamearr[i]+" like '%"+valuearr[i]+"%' ";
	    		 }
	    	}
	    }
        s.setFlushMode(FlushMode.MANUAL);  
        s.setCacheMode(CacheMode.IGNORE);  
        ScrollableResults results = s.createQuery(hql).setFetchSize(BATCH_SIZE).scroll(ScrollMode.FORWARD_ONLY);  
        int index = 0;  
        while (results.next()) {
         index++;
         System.out.println("开始删除"+clazz.getSimpleName()+"索引第"+index+"条");
         s.delete(results.get(0)); // index each element 
         if (index % BATCH_SIZE == 0) {  
          // s.flushToIndexes(); //apply changes to indexes  
          s.clear(); // clear since the queue is processed  
         }  
        }  
        s.clear();
        session.beginTransaction();
        session.close();
        long endTime = new Date().getTime();  
        String msg="建立"+clazz.getSimpleName()+"索引 ， 这花费了" + (endTime - startTime) + " 毫秒来把文档从索引删除!";
        System.out.println(msg);
        return (endTime - startTime);
        // tr.commit();  
    }
    
   /* *//**
	* 全文索引检索
	* @param clazz    类
	* @param filename 检索字段
	* @param keyword  关键字
	* @return List   返回所需要的集合。
	*/
    public List search(Class clazz, String[] filenames,String[] keywords,String[] flags,String[] filed_sort,String[] fild_type,boolean[] isasc) {
    	try {
    		Session session = this.openSession();
    		session.beginTransaction();
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            org.apache.lucene.search.Query luceneqQuery=null;
            if(filenames.length==1){
            	QueryParser queryParser = new QueryParser(filenames[0], new StandardAnalyzer());  
                luceneqQuery = queryParser.parse(keywords[0]);  
            }
            else{
            	BooleanClause.Occur[] bo=new BooleanClause.Occur[flags.length];
            	for(int i=0;i<flags.length;i++){
            		if("and".equals(flags[i])){
            			bo[i]=BooleanClause.Occur.MUST;
            		}
            		else if("or".equals(flags[i])){
            			bo[i]=BooleanClause.Occur.SHOULD;
            		}
            		else if("not".equals(flags[i])){
            			bo[i]=BooleanClause.Occur.MUST_NOT;
            		}
            	}
            	StandardAnalyzer sa=new StandardAnalyzer();
            	luceneqQuery = MultiFieldQueryParser.parse(keywords, filenames, bo, sa);
            }
        
        //执行检索，得到结果集  
        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneqQuery, clazz);
        if(filed_sort!=null&&fild_type!=null&&isasc!=null){
        	if(filed_sort.length>1&&fild_type.length>1&&isasc.length>1){
        		SortField[] sortfieldarr=new SortField[filed_sort.length];
        		for(int i=0;i<filed_sort.length;i++){
        			Type sortcode=this.getSortcode_type(fild_type[i]);
        			sortfieldarr[i]=new SortField( filed_sort[i],sortcode,isasc[i]);
        		}
        		Sort sort = new Sort(sortfieldarr);
        		fullTextQuery.setSort(sort);
        	}
        	else{
        		Type sortcode=this.getSortcode_type(fild_type[0]);
        		Sort sort = new Sort(new SortField( filed_sort[0],sortcode,isasc[0]));
        		fullTextQuery.setSort(sort);
        	}
        }
        List list =new ArrayList(); 
        list=fullTextQuery.list();  
        session.getTransaction().commit();
        session.close();
        return list;
      }catch (Exception e) {  
            e.printStackTrace();
            return null;
       }  
    }
    
    /**
	* 全文索引检索
	* @param clazz    类
	* @param filename 检索字段
	* @param keyword  关键字
	* @return List   返回所需要的集合。
	*/
    public List searchByPage(Class clazz, String[] filenames,String[] keywords,String[] flags,String[] filed_sort,String[] fild_type,boolean[] isasc, PageParm pageparm) {  
    	try {
    	Session session = this.openSession();
    	session.beginTransaction();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        org.apache.lucene.search.Query luceneqQuery=null;
        if(filenames.length==1){
        	QueryParser queryParser = new QueryParser(filenames[0], new StandardAnalyzer());  
            luceneqQuery = queryParser.parse(keywords[0]);  
        }
        else{
        	BooleanClause.Occur[] bo=new BooleanClause.Occur[flags.length];
        	for(int i=0;i<flags.length;i++){
        		if("and".equals(flags[i])){
        			bo[i]=BooleanClause.Occur.MUST;
        		}
        		else if("or".equals(flags[i])){
        			bo[i]=BooleanClause.Occur.SHOULD;
        		}
        		else if("not".equals(flags[i])){
        			bo[i]=BooleanClause.Occur.MUST_NOT;
        		}
        	}
        	StandardAnalyzer sa=new StandardAnalyzer();
        	luceneqQuery = MultiFieldQueryParser.parse(keywords, filenames, bo, sa);
        }
        //执行检索，得到结果集  
        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneqQuery, clazz);
        
      // 排序 int sortcode=this.getSortcode_type(fild_type);
    	//false 代表desc    true代表asc
       // Sort sort = new Sort( new SortField( filed_sort,sortcode,false));
       // Sort sort2 = new Sort(new SortField[]{new SortField("title"),new SortField("name")},false);
        if(filed_sort!=null&&fild_type!=null&&isasc!=null){
        	if(filed_sort.length>1&&fild_type.length>1&&isasc.length>1){
        		SortField[] sortfieldarr=new SortField[filed_sort.length];
        		for(int i=0;i<filed_sort.length;i++){
        			Type sortcode=this.getSortcode_type(fild_type[i]);
        			sortfieldarr[i]=new SortField( filed_sort[i],sortcode,isasc[i]);
        		}
        		Sort sort = new Sort(sortfieldarr);
        		fullTextQuery.setSort(sort);
        	}
        	else{
        		Type sortcode=this.getSortcode_type(fild_type[0]);
        		Sort sort = new Sort(new SortField( filed_sort[0],sortcode,isasc[0]));
        		fullTextQuery.setSort(sort);
        	}
        }
        int len=fullTextQuery.getResultSize();
        Page page=new Page();
        if(pageparm!=null&&pageparm.getPagesize()==0){
        	pageparm.setPagesize(len);
        }
        ArrayList list=new ArrayList();
	    PageParm pageParm=page.getnowpage(len, pageparm);
	    fullTextQuery.setFirstResult((pageparm.getNowpage()-1)*pageparm.getPagesize());//设置从第几条开始查,pageParm.getNowpage() 为页码
	    fullTextQuery.setMaxResults(pageparm.getPagesize());//最大显示的条数
        List pagelist = fullTextQuery.list();
        list.add(pagelist);
        list.add(pageParm);
        session.getTransaction().commit();
        session.close();
        fullTextSession.close();
        return list;
	 }catch (Exception e) {  
         e.printStackTrace();
         return null;
    }  
    }
    
	//获得排序类型
    public Type getSortcode_type(String fild_type){
    	 Type sortcode_type=SortField.Type.STRING;
    	 if("int".equals(fild_type)){
    		 sortcode_type=SortField.Type.INT;
         }
         else if("double".equals(fild_type)){
        	 sortcode_type=SortField.Type.DOUBLE;
         }
         else if("byte".equals(fild_type)){
        	 sortcode_type=SortField.Type.BYTES;
         }
         else if("string".equals(fild_type)){
        	 sortcode_type=SortField.Type.STRING;
         }
         else if("custom".equals(fild_type)){
        	 sortcode_type=SortField.Type.CUSTOM;
         }
         else if("doc".equals(fild_type)){
        	 sortcode_type=SortField.Type.DOC;
         }
         else if("float".equals(fild_type)){
        	 sortcode_type=SortField.Type.FLOAT;
         }
         else if("long".equals(fild_type)){
        	 sortcode_type=SortField.Type.LONG;
         }
         else if("score".equals(fild_type)){
        	 sortcode_type=SortField.Type.SCORE;
         }
    	 return sortcode_type;
    }

	public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
	}
	
	// 增删改使用的session
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	// 查询使用的session
	public Session openSession() {
		return sessionFactory.openSession();
	}
}
