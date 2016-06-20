/**
* @Project 源自 dbc
* @Title WebconfigServiceImpl.java
* @Package org.dbc.service
* @Description 网站基本参数设置service类
* @author caihuajun
* @date 2013-05-06
* @version v1.0
*/

package com.dbc.service.Impl;

import com.dbc.dao.Dbc_webconfigDao;
import com.dbc.dao.Impl.Dbc_webconfigDaoImpl;
import com.dbc.pojo.Dbc_webconfig;
import com.dbc.service.Dbc_webconfigService;

/**
* @ClassName WebconfigServiceImpl
* @Description 网站基本参数设置service类
* @author caihuajun
* @date 2013-05-06
*/
public class Dbc_webconfigServiceImpl extends BaseServiceImpl implements Dbc_webconfigService{

	private Dbc_webconfigDaoImpl dbc_webconfigdao;

	
	
	public Dbc_webconfigDaoImpl getDbc_webconfigdao() {
		return dbc_webconfigdao;
	}



	public void setDbc_webconfigdao(Dbc_webconfigDaoImpl dbcWebconfigdao) {
		dbc_webconfigdao = dbcWebconfigdao;
	}



	/**
	 * @Title getwebconfig
	 * @Description   获取web配置
	 * @return Webconfig  
	 */
	public Dbc_webconfig getwebconfig(){
		return dbc_webconfigdao.getwebconfig();
	}

}