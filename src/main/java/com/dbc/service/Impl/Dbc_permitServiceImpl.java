/**
* @Project 源自 dbc
* @Title Dbc_permitServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 权限service实现类
* @author caihuajun
* @date 2015-05-14
* @version V2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_permitService;

import com.dbc.dao.Dbc_permitDao;

/**
* @ClassName Dbc_permitServiceImpl
* @Description 权限service实现类
* @author caihuajun
* @date 2015-05-14
*/
public class Dbc_permitServiceImpl extends BaseServiceImpl implements Dbc_permitService{

	private Dbc_permitDao dbc_permitdao;

	public Dbc_permitDao  getDbc_permitdao() {
    	return dbc_permitdao;
	}

	public void setDbc_permitdao(Dbc_permitDao  dbc_permitdao) {
    	this.dbc_permitdao = dbc_permitdao;
	}
}