/**
* @Project 源自 dbc
* @Title Dbc_mysqlmanagerServiceImpl.java
* @Package com.dbc.service.Impl
* @Description mysql实现类
* @author caihuajun
* @date 2015-06-25
* @version v2.0
*/

package com.dbc.service.Impl;

import java.util.List;

import com.dbc.dao.Dbc_mysqlmanagerDao;
import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_adService;
import com.dbc.service.Dbc_mysqlmanagerService;

/**
* @ClassName Dbc_mysqlmanagerServiceImpl
* @Description mysql实现类
* @author caihuajun
* @date 2015-05-12
*/
public class Dbc_mysqlmanagerServiceImpl extends BaseServiceImpl implements Dbc_mysqlmanagerService{
	
	private Dbc_mysqlmanagerDao dbc_mysqlmanagerdao;
	
	public Dbc_mysqlmanagerDao getDbc_mysqlmanagerdao() {
		return dbc_mysqlmanagerdao;
	}

	public void setDbc_mysqlmanagerdao(Dbc_mysqlmanagerDao dbcMysqlmanagerdao) {
		dbc_mysqlmanagerdao = dbcMysqlmanagerdao;
	}

	/**
	 * @Title getTablelist
	 * @Description 获取数据库表所有的表名和记录数
	 * @param table_schema  数据库名
	 * @return List  
	 * @throws
	 */
	public List getTablelist(String table_schema){
		return dbc_mysqlmanagerdao.getTablelist(table_schema);
	}
	
	/**
	 * @Title getcolumns
	 * @Description 获取数据库表结构
	 * @param tablename 数据库表名
	 * @return List  
	 * @throws
	 */
	public List getcolumns(String tablename){
		return dbc_mysqlmanagerdao.getcolumns(tablename);
	}
	
	/**
	 * @Title showmysqlversion
	 * @Description 获取数据库版本
	 * @return List  
	 * @throws
	 */
	public List showmysqlversion(){
		return dbc_mysqlmanagerdao.showmysqlversion();
	}
	
	/**
	 * @Title getindex
	 * @Description 获取数据库表索引
	 * @param tablename 数据库表名
	 * @return List  
	 * @throws
	 */
	public List getindex(String tablename){
		return dbc_mysqlmanagerdao.getindex(tablename);
	}
	
	/**
	 * @Title addindex
	 * @Description 创建数据库表索引
	 * @param tablename 数据库表名
	 * @throws
	 */
	public void addindex(String tablename,String column,String indexname){
		dbc_mysqlmanagerdao.addindex(tablename, column, indexname);
	}

}