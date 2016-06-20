/**
* @Project 源自 dbc
* @Title Base_typeServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 所有l类型的类别service实现类
* @author caihuajun
* @date 2013-12-04
* @version v1.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_typeService;

import com.dbc.dao.Dbc_typeDao;

/**
* @ClassName Base_typeServiceImpl
* @Description 所有l类型的类别service实现类
* @author caihuajun
* @date 2013-12-04
*/
public class Dbc_typeServiceImpl extends BaseServiceImpl implements Dbc_typeService{

	private Dbc_typeDao dbc_typedao;

	public Dbc_typeDao getDbc_typedao() {
		return dbc_typedao;
	}

	public void setDbc_typedao(Dbc_typeDao dbcTypedao) {
		dbc_typedao = dbcTypedao;
	}

	
}