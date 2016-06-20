/**
* @Project 源自 dbc
* @Title Dbc_adDao.java
* @Package com.dbc.dao
* @Description 广告dao类接口
* @author caihuajun
* @date 2015-05-12
* @version v2.0
*/
package com.dbc.dao;

import java.util.List;

import com.dbc.dao.BaseDao;

/**
* @ClassName Dbc_adDao
* @Description 广告dao类接口
* @author caihuajun
* @date 2015-05-12
*/
public interface Dbc_mysqlmanagerDao extends BaseDao{
	/**
	 * @Title getTablelist
	 * @Description 获取数据库表所有的表名和记录数
	 * @param table_schema  数据库名
	 * @return List  
	 * @throws
	 */
	public List getTablelist(String table_schema);
	
	/**
	 * @Title getcolumns
	 * @Description 获取数据库表结构
	 * @param tablename 数据库表名
	 * @return List  
	 * @throws
	 */
	public List getcolumns(String tablename);
	
	/**
	 * @Title showmysqlversion
	 * @Description 获取数据库版本
	 * @return List  
	 * @throws
	 */
	public List showmysqlversion();
	
	/**
	 * @Title getindex
	 * @Description 获取数据库表索引
	 * @param tablename 数据库表名
	 * @return List  
	 * @throws
	 */
	public List getindex(String tablename);
	
	/**
	 * @Title addindex
	 * @Description 创建数据库表索引
	 * @param tablename 数据库表名
	 * @throws
	 */
	public void addindex(String tablename,String column,String indexname);
}