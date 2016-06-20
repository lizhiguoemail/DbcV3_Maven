/**
* @Project 源自 dbc
* @Title WebconfigDao.java
* @Package org.dbc.interDao
* @Description 网站基本参数设置dao类接口
* @author caihuajun
* @date 2013-05-06
* @version v1.0
*/
package com.dbc.dao;

import com.dbc.pojo.Dbc_webconfig;

/**
* @ClassName WebconfigDao
* @Description 网站基本参数设置dao类接口
* @author caihuajun
* @date 2013-05-06
*/
public interface Dbc_webconfigDao extends BaseDao{
	
	/**
	 * @Title getwebconfig
	 * @Description   获取web配置
	 * @return Webconfig  
	 */
	public Dbc_webconfig getwebconfig();

}