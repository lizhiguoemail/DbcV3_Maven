/**
* @Project 源自 dbc
* @Title Base_commentServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 评论通用类service实现类
* @author caihuajun
* @date 2014-03-21
* @version v2.0
*/

package com.dbc.service.Impl;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_commentService;

import com.dbc.dao.Dbc_commentDao;

/**
* @ClassName Base_commentServiceImpl
* @Description 评论通用类service实现类
* @author caihuajun
* @date 2014-03-21
*/
public class Dbc_commentServiceImpl extends BaseServiceImpl implements Dbc_commentService{

	private Dbc_commentDao dbc_commentdao;

	public Dbc_commentDao getDbc_commentdao() {
		return dbc_commentdao;
	}

	public void setDbc_commentdao(Dbc_commentDao dbcCommentdao) {
		dbc_commentdao = dbcCommentdao;
	}

	
}