/**
* @Project 源自 dbc
* @Title Dbc_provinceService.java
* @Package com.dbc.service
* @Description 省份service类接口
* @author caihuajun
* @date 2015-04-27
* @version v2.0
*/
package com.dbc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dbc.pojo.Dbc_province;
import com.dbc.service.BaseService;

/**
* @ClassName Dbc_provinceService
* @Description 省份service类接口
* @author caihuajun
* @date 2015-04-27
*/
public interface Dbc_provinceService extends BaseService{
	
	/**
	 * @Title deleteprovinces
	 * @Description  批量删除省份
	 * @return void
	 */
	public void deleteprovinces(String[] ids);
	
	/**
	 * @Title deleteprovince
	 * @Description  删除省份
	 * @return void
	 */
	public void deleteprovince(String provinceid);
	
	
	/**
	 * @Title adddiqubyxml
	 * @Description  导入数据
	 * @return void
	 */
	public void adddiqubyxml(HttpServletRequest request);
	
	/**
	 * @Title updatebycity
	 * @Description  更新关联数据
	 * @return void
	 */
	public void updatebycity();
	
	/**
	 * @Title updatebydistrict
	 * @Description  更新关联数据
	 * @return void
	 */
	public void updatebydistrict();
	
	/**
	 * @Title updatebystreet
	 * @Description  更新关联数据
	 * @return void
	 */
	public void updatebystreet();

}