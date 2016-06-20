/**
* @Project 源自 dbc
* @Title Dbc_communityServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 社区service实现类
* @author caihuajun
* @date 2015-07-24
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_communityService;

import com.dbc.dao.Dbc_communityDao;

/**
* @ClassName Dbc_communityServiceImpl
* @Description 社区service实现类
* @author caihuajun
* @date 2015-07-24
*/
public class Dbc_communityServiceImpl extends BaseServiceImpl implements Dbc_communityService{

	private Dbc_communityDao dbc_communitydao;

	public Dbc_communityDao  getDbc_communitydao() {
    	return dbc_communitydao;
	}

	public void setDbc_communitydao(Dbc_communityDao  dbc_communitydao) {
    	this.dbc_communitydao = dbc_communitydao;
	}
}