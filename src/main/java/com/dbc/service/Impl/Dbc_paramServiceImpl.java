/**
* @Project 源自 dbc
* @Title Base_paramServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 参数类service实现类
* @author caihuajun
* @date 2014-02-19
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_paramService;

import com.dbc.dao.Dbc_paramDao;

/**
* @ClassName Base_paramServiceImpl
* @Description 参数类service实现类
* @author caihuajun
* @date 2014-02-19
*/
public class Dbc_paramServiceImpl extends BaseServiceImpl implements Dbc_paramService{

	private Dbc_paramDao dbc_paramdao;

	public Dbc_paramDao getDbc_paramdao() {
		return dbc_paramdao;
	}

	public void setDbc_paramdao(Dbc_paramDao dbcParamdao) {
		dbc_paramdao = dbcParamdao;
	}

	
}