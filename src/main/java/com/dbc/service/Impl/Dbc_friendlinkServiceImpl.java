/**
* @Project 源自 dbc
* @Title FriendlinkServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 友情链接service实现类
* @author caihuajun
* @date 2014-01-23
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_friendlinkService;

import com.dbc.dao.Dbc_friendlinkDao;

/**
* @ClassName FriendlinkServiceImpl
* @Description 友情链接service实现类
* @author caihuajun
* @date 2014-01-23
*/
public class Dbc_friendlinkServiceImpl extends BaseServiceImpl implements Dbc_friendlinkService{

	private Dbc_friendlinkDao dbc_friendlinkdao;

	public Dbc_friendlinkDao getDbc_friendlinkdao() {
		return dbc_friendlinkdao;
	}

	public void setDbc_friendlinkdao(Dbc_friendlinkDao dbcFriendlinkdao) {
		dbc_friendlinkdao = dbcFriendlinkdao;
	}

	
}