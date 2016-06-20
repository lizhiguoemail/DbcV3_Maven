/**
* @Project 源自 dbc
* @Title NavServiceImpl.java
* @Package com.dbc.pojo.service
* @Description 导航栏service类
* @author caihuajun
* @date 2013-10-18
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.dao.Dbc_navDao;
import com.dbc.service.Dbc_navService;


/**
* @ClassName NavServiceImpl
* @Description 导航栏service类
* @author caihuajun
* @date 2013-10-18
*/
public class Dbc_navServiceImpl extends BaseServiceImpl implements Dbc_navService{

	private Dbc_navDao dbc_navdao;

	public Dbc_navDao getDbc_navdao() {
		return dbc_navdao;
	}

	public void setDbc_navdao(Dbc_navDao dbcNavdao) {
		dbc_navdao = dbcNavdao;
	}

	
}