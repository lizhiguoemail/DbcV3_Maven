/**
* @Project 源自 dbc
* @Title Dbc_streetServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 地区街道service实现类
* @author caihuajun
* @date 2015-07-22
* @version v2.0
*/

package com.dbc.service.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_streetService;

import com.dbc.dao.Dbc_streetDao;

/**
* @ClassName Dbc_streetServiceImpl
* @Description 地区街道service实现类
* @author caihuajun
* @date 2015-07-22
*/
public class Dbc_streetServiceImpl extends BaseServiceImpl implements Dbc_streetService{

	private Dbc_streetDao dbc_streetdao;

	public Dbc_streetDao  getDbc_streetdao() {
    	return dbc_streetdao;
	}

	public void setDbc_streetdao(Dbc_streetDao  dbc_streetdao) {
    	this.dbc_streetdao = dbc_streetdao;
	}
	
	/**
	 * @Title deletestreets
	 * @Description  批量删除街道
	 * @return void
	 */
	public void deletestreets(String[] ids){
		if(ids!=null){
			String streetids=null;
			for(int i=0;i<ids.length;i++){
				if(i==0){
					streetids="'"+ids[i]+"'";
				}
				else{
					streetids=streetids+",'"+ids[i]+"'";
				}
			}
			Session session=this.getCurrentSession();
			String delhql1="delete from Dbc_community where  streetid in("+streetids+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where id in("+streetids+")";
			session.createQuery(delhql2).executeUpdate();
			session.close();
		}
		
	}
	
	/**
	 * @Title deletestreet
	 * @Description  删除街道
	 * @return void
	 */
	public void deletestreet(String streetid){
		if(streetid!=null&&streetid.length()>0){
			Session session=this.getCurrentSession();
			String delhql1="delete from Dbc_community where  streetid in("+streetid+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where id in("+streetid+")";
			session.createQuery(delhql2).executeUpdate();
			session.close();
		}
	}
}