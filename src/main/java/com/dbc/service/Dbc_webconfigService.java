/**
* @Project 源自 dbc
* @Title WebconfigService.java
* @Package org.dbc.interService
* @Description 网站基本参数设置service类接口
* @author caihuajun
* @date 2013-05-06
* @version v1.0
*/
package com.dbc.service;

import com.dbc.pojo.Dbc_webconfig;

/**
* @ClassName WebconfigService
* @Description 网站基本参数设置service类接口
* @author caihuajun
* @date 2013-05-06
*/
public interface Dbc_webconfigService extends BaseService{
	
	/**
	 * @Title getwebconfig
	 * @Description   获取web配置
	 * @return Webconfig  
	 */
	public Dbc_webconfig getwebconfig();

}