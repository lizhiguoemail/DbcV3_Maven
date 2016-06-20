/**
* @Project 源自 dbc
* @Title Dbc_adServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 广告service实现类
* @author caihuajun
* @date 2015-05-12
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_adService;

import com.dbc.dao.Dbc_adDao;

/**
* @ClassName Dbc_adServiceImpl
* @Description 广告service实现类
* @author caihuajun
* @date 2015-05-12
*/
public class Dbc_adServiceImpl extends BaseServiceImpl implements Dbc_adService{

	private Dbc_adDao dbc_addao;

	public Dbc_adDao  getDbc_addao() {
    	return dbc_addao;
	}

	public void setDbc_addao(Dbc_adDao  dbc_addao) {
    	this.dbc_addao = dbc_addao;
	}
}