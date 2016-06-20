/**
* @Project 源自 dbc
* @Title AppServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 应用表service类
* @author caihuajun
* @date 2013-10-29
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Dbc_appService;

import com.dbc.dao.Dbc_appDao;

/**
* @ClassName AppServiceImpl
* @Description 应用表service类
* @author caihuajun
* @date 2013-10-29
*/
public class Dbc_appServiceImpl extends BaseServiceImpl implements Dbc_appService{

	private Dbc_appDao dbc_appdao;

	public Dbc_appDao getDbc_appdao() {
		return dbc_appdao;
	}

	public void setDbc_appdao(Dbc_appDao dbcAppdao) {
		dbc_appdao = dbcAppdao;
	}

	
}