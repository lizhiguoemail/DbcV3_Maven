/**
* @Project 源自 dbc
* @Title Dbc_slidesServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 幻灯片service实现类
* @author caihuajun
* @date 2015-06-23
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_slidesService;

import com.dbc.dao.Dbc_slidesDao;

/**
* @ClassName Dbc_slidesServiceImpl
* @Description 幻灯片service实现类
* @author caihuajun
* @date 2015-06-23
*/
public class Dbc_slidesServiceImpl extends BaseServiceImpl implements Dbc_slidesService{

	private Dbc_slidesDao dbc_slidesdao;

	public Dbc_slidesDao  getDbc_slidesdao() {
    	return dbc_slidesdao;
	}

	public void setDbc_slidesdao(Dbc_slidesDao  dbc_slidesdao) {
    	this.dbc_slidesdao = dbc_slidesdao;
	}
}