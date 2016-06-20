/**
* @Project 源自 dbc
* @Title ArticleServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 文章service实现类
* @author caihuajun
* @date 2013-11-26
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_articleService;

import com.dbc.dao.Dbc_articleDao;

/**
* @ClassName ArticleServiceImpl
* @Description 文章service实现类
* @author caihuajun
* @date 2013-11-26
*/
public class Dbc_articleServiceImpl extends BaseServiceImpl implements Dbc_articleService{

	private Dbc_articleDao dbc_articledao;

	public Dbc_articleDao getDbc_articledao() {
		return dbc_articledao;
	}

	public void setDbc_articledao(Dbc_articleDao dbcArticledao) {
		dbc_articledao = dbcArticledao;
	}

}