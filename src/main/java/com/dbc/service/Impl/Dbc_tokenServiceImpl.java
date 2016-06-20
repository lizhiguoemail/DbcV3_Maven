/**
* @Project 源自 dbc
* @Title Dbc_tokenServiceImpl.java
* @Package com.dbc.service.Impl
* @Description token类service实现类
* @author caihuajun
* @date 2015-08-06
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_tokenService;

import com.dbc.dao.Dbc_tokenDao;

/**
* @ClassName Dbc_tokenServiceImpl
* @Description token类service实现类
* @author caihuajun
* @date 2015-08-06
*/
public class Dbc_tokenServiceImpl extends BaseServiceImpl implements Dbc_tokenService{

	private Dbc_tokenDao dbc_tokendao;

	public Dbc_tokenDao  getDbc_tokendao() {
    	return dbc_tokendao;
	}

	public void setDbc_tokendao(Dbc_tokenDao  dbc_tokendao) {
    	this.dbc_tokendao = dbc_tokendao;
	}
	
	
}