/**
* @Project 源自 dbc
* @Title Dbc_streetService.java
* @Package com.dbc.service
* @Description 地区街道service类接口
* @author caihuajun
* @date 2015-07-22
* @version v2.0
*/
package com.dbc.service;

import com.dbc.service.BaseService;

/**
* @ClassName Dbc_streetService
* @Description 地区街道service类接口
* @author caihuajun
* @date 2015-07-22
*/
public interface Dbc_streetService extends BaseService{
	
	/**
	 * @Title deletestreets
	 * @Description  批量删除街道
	 * @return void
	 */
	public void deletestreets(String[] ids);
	
	/**
	 * @Title deletestreet
	 * @Description  删除街道
	 * @return void
	 */
	public void deletestreet(String streetid);

}