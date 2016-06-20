/**
* @Project 源自 dbc
* @Title Base_typegroupService.java
* @Package com.dbc.service
* @Description 分类字典组service类接口
* @author caihuajun
* @date 2013-12-04
* @version v1.0
*/
package com.dbc.service;

import com.dbc.pojo.Dbc_typegroup;
import com.dbc.service.BaseService;

/**
* @ClassName Base_typegroupService
* @Description 分类字典组service类接口
* @author caihuajun
* @date 2013-12-04
*/
public interface Dbc_typegroupService extends BaseService{
	
	/**
	 * @Title updatebase_type
	 * @Description   修改分类组
	 * @param  Dbc_typegroup 修改的分类组
	 * @return void  
	 */
	public void updatebase_type(Dbc_typegroup base_typegroup,String oldtype);

}