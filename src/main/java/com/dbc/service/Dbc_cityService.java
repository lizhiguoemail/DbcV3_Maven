/**
* @Project 源自 dbc
* @Title Dbc_cityService.java
* @Package com.dbc.service
* @Description 城市service类接口
* @author caihuajun
* @date 2015-04-28
* @version v2.0
*/
package com.dbc.service;

import com.dbc.pojo.Dbc_log;
import com.dbc.service.BaseService;

/**
* @ClassName Dbc_cityService
* @Description 城市service类接口
* @author caihuajun
* @date 2015-04-28
*/
public interface Dbc_cityService extends BaseService{
	/**
	 * @Title deletecitys
	 * @Description  批量删除城市
	 * @return void
	 */
	public void deletecitys(String[] ids);
	
	/**
	 * @Title deletecity
	 * @Description  删除城市
	 * @return void
	 */
	public void deletecity(String cityid);
	
	/**
	 * @Title deletecity
	 * @Description  删除城市
	 * @return void
	 */
	public void deletecity(String cityid,Dbc_log dbc_log);
	
	/**
	 * @Title deletecitys
	 * @Description  批量删除城市
	 * @return void
	 */
	public void deletecitys(String[] ids,Dbc_log dbc_log);

}