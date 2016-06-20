/**
* @Project 源自 dbc
* @Title Base_roleServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 角色service实现类
* @author caihuajun
* @date 2014-02-17
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_roleService;

import com.dbc.dao.Dbc_roleDao;

/**
* @ClassName Base_roleServiceImpl
* @Description 角色service实现类
* @author caihuajun
* @date 2014-02-17
*/
public class Dbc_roleServiceImpl extends BaseServiceImpl implements Dbc_roleService{

	private Dbc_roleDao dbc_roledao;

	public Dbc_roleDao getDbc_roledao() {
		return dbc_roledao;
	}

	public void setDbc_roledao(Dbc_roleDao dbcRoledao) {
		dbc_roledao = dbcRoledao;
	}

}