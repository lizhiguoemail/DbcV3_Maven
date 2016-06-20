/**
 * @Project 源自dbc
 * @Title ILogDao.java
 * @Package org.dbc.interDao;
 * @Description  日志DAO接口
 * @author caihuajun
 * @date 2010-06-14
 * @version v1.0
 */
package com.dbc.dao;

import java.util.List;

import com.dbc.pojo.Dbc_log;
import com.dbc.pojo.Dbc_userinfo;;

/**
 * @ClassName ILogDao
 * @Description 操作DAO接口
 * @author caihuajun
 * @date 2010-06-14
 */
public interface Dbc_logDao extends BaseDao {
	
	/**
	 * @Title saveLog
	 * @Description  添加日志
	 * @param Dbc_log
	 * @return void
	 */
	public void saveLog(Dbc_log log);
	
	/**
	 * @Title selLog
	 * @Description  查询日志
	 * @param content
	 * @param logtype
	 * @param begindate
	 * @param enddate
	 * @param nowpage
	 * @param pagesize
	 * @param gotopagetype
	 * @param gotopage
	 * @return List
	 */
	public List selLog(String content,String logtype,String begindate,String enddate,int nowpage, 
			int pagesize, String gotopagetype, int gotopage);
	
	/**
	 * @Title selLogEXT
	 * @Description  查询日志
	 * @param getarr
	 * @param content
	 * @param logtype
	 * @param begindate
	 * @param enddate
	 * @param start
	 * @param limit
	 * @return List
	 */
	public List selLogEXT(String[] getarr,String content,String logtype,String begindate,String enddate,int start,int limit);
}
