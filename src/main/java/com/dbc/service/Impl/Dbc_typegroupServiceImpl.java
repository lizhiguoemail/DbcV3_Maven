/**
* @Project 源自 dbc
* @Title Base_typegroupServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 分类字典组service实现类
* @author caihuajun
* @date 2013-12-04
* @version v1.0
* 2014-07-23 caihuajun  增加修改分类组的同时，修改分类的方法
*/

package com.dbc.service.Impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.dbc.pojo.Dbc_typegroup;
import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_typegroupService;

import com.dbc.dao.Dbc_typegroupDao;

/**
* @ClassName Base_typegroupServiceImpl
* @Description 分类字典组service实现类
* @author caihuajun
* @date 2013-12-04
*/
public class Dbc_typegroupServiceImpl extends BaseServiceImpl implements Dbc_typegroupService{

	private Dbc_typegroupDao dbc_typegroupdao;
	
	public Dbc_typegroupDao getDbc_typegroupdao() {
		return dbc_typegroupdao;
	}

	public void setDbc_typegroupdao(Dbc_typegroupDao dbcTypegroupdao) {
		dbc_typegroupdao = dbcTypegroupdao;
	}



	/**
	 * @Title updatebase_type
	 * @Description   修改分类组
	 * @param  Dbc_typegroup 修改的分类组
	 * @return void  
	 */
	public void updatebase_type(Dbc_typegroup base_typegroup,String oldtype){
		Session session=this.getCurrentSession();
		session.update(base_typegroup);
		String delhql="update  Dbc_type set type_groupname='"+base_typegroup.getName()+"',type_type='"+base_typegroup.getFlag()+"' where type_type='"+oldtype+"'";
		session.createQuery(delhql).executeUpdate();
		session.close();
	}
}