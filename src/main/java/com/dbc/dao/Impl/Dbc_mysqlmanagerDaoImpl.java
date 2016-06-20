/**
* @Project 源自 dbc
* @Title Dbc_mysqlmanagerDaoImpl.java
* @Package com.dbc.dao.Impl
* @Description  mysql管理dao实现类
* @author caihuajun
* @date 2015-06-25
* @version v2.0
*/

package com.dbc.dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dbc.dao.Impl.BaseDaoImpl;
import com.dbc.dao.Dbc_mysqlmanagerDao;
import com.dbc.util.Dbc_common_config;

/**
* @ClassName Dbc_mysqlmanagerDaoImpl
* @Description mysql管理dao实现类
* @author caihuajun
* @date 2015-06-25
*/
public class Dbc_mysqlmanagerDaoImpl extends BaseDaoImpl implements Dbc_mysqlmanagerDao{
	
	/**
	 * @Title getTablelist
	 * @Description 获取数据库表所有的表名和记录数
	 * @param table_schema  数据库名
	 * @return List  
	 * @throws
	 */
	public List getTablelist(String table_schema){
		//String sql="select table_name,table_rows from tables where TABLE_SCHEMA='"+table_schema+"";
		//Data_free 是碎片但是，我目前怎么optimize都没法将Data_free变为0
		List returnlist=new ArrayList();
		Session session=null;
		session=this.openSession();
		session.beginTransaction();
		String dbname=Dbc_common_config.getvalue("mysql_dbname");
		String sql="select table_name,table_rows,Data_free,engine from information_schema.tables where table_schema='"+dbname+"' and table_type='base table'";
		Query query=session.createSQLQuery(sql);
		returnlist=query.list();
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title getcolumns
	 * @Description 获取数据库表结构
	 * @param tablename 数据库表名
	 * @return List  
	 * @throws
	 */
	public List getcolumns(String tablename){
		//oracle：
		//SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '表名' ORDER BY COLUMN_ID 
		//sqlserver：
		//select name from syscolumns where id=object_id('表名');
		List returnlist=new ArrayList();
		Session session=null;
		session=this.openSession();
		session.beginTransaction();
		String sql="select COLUMN_NAME,DATA_TYPE from information_schema.columns where table_name='"+tablename+"'"; //mysql
		Query query=session.createSQLQuery(sql);
		returnlist=query.list();
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title getindex
	 * @Description 获取数据库表索引
	 * @param tablename 数据库表名
	 * @return List  
	 * @throws
	 */
	public List getindex(String tablename){
		List returnlist=new ArrayList();
		Session session=null;
		session=this.openSession();
		session.beginTransaction();
		String sql="SELECT DISTINCT lower(index_name) index_name,lower(index_type) type,COLUMN_NAME FROM information_schema.statistics WHERE table_name='"+tablename+"'"; //mysql
		Query query=session.createSQLQuery(sql);
		returnlist=query.list();
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title addindex
	 * @Description 创建数据库表索引
	 * @param tablename 数据库表名
	 * @throws
	 */
	public void addindex(String tablename,String column,String indexname){
		String sql= "create index "+indexname+" on "+tablename+"("+column+")"; //mysql
		this.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	/**
	 * @Title showmysqlversion
	 * @Description 获取数据库版本
	 * @return List  
	 * @throws
	 */
	public List showmysqlversion(){
		//oracle：
		//SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '表名' ORDER BY COLUMN_ID 
		//sqlserver：
		//select name from syscolumns where id=object_id('表名');
		List returnlist=new ArrayList();
		Session session=null;
		session=this.openSession();
		session.beginTransaction();
		String sql="select version()"; //mysql
		Query query=session.createSQLQuery(sql);
		returnlist=query.list();
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}

}