/**
* @Project 源自 dbc
* @Title Dbc_msgServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 消息表service实现类
* @author caihuajun
* @date 2014-12-23
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_msgService;

import com.dbc.dao.Dbc_msgDao;

/**
* @ClassName Dbc_msgServiceImpl
* @Description 消息表service实现类
* @author caihuajun
* @date 2014-12-23
*/
public class Dbc_msgServiceImpl extends BaseServiceImpl implements Dbc_msgService{

	private Dbc_msgDao dbc_msgdao;

	public Dbc_msgDao  getDbc_msgdao() {
    	return dbc_msgdao;
	}

	public void setDbc_msgdao(Dbc_msgDao  dbc_msgdao) {
    	this.dbc_msgdao = dbc_msgdao;
	}
}