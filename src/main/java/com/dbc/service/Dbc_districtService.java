/**
* @Project 源自 dbc
* @Title Dbc_districtService.java
* @Package com.dbc.service
* @Description 地区service类接口
* @author caihuajun
* @date 2015-04-28
* @version v2.0
*/
package com.dbc.service;

import com.dbc.service.BaseService;

/**
* @ClassName Dbc_districtService
* @Description 地区service类接口
* @author caihuajun
* @date 2015-04-28
*/
public interface Dbc_districtService extends BaseService{
	
	/**
	 * @Title deletedistricts
	 * @Description  批量删除城区
	 * @return void
	 */
	public void deletedistricts(String[] ids);
	
	/**
	 * @Title deletedistrict
	 * @Description  删除城区
	 * @return void
	 */
	public void deletedistrict(String districtid);

}