/**
* @Project 源自 dbc
* @Title Dbc_districtServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 地区service实现类
* @author caihuajun
* @date 2015-04-28
* @version v2.0
*/

package com.dbc.service.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_districtService;

import com.dbc.dao.Dbc_districtDao;

/**
* @ClassName Dbc_districtServiceImpl
* @Description 地区service实现类
* @author caihuajun
* @date 2015-04-28
*/
public class Dbc_districtServiceImpl extends BaseServiceImpl implements Dbc_districtService{

	private Dbc_districtDao dbc_districtdao;

	public Dbc_districtDao  getDbc_districtdao() {
    	return dbc_districtdao;
	}

	public void setDbc_districtdao(Dbc_districtDao  dbc_districtdao) {
    	this.dbc_districtdao = dbc_districtdao;
	}
	
	/**
	 * @Title deletedistricts
	 * @Description  批量删除城区
	 * @return void
	 */
	public void deletedistricts(String[] ids){
		if(ids!=null){
			String districtids=null;
			for(int i=0;i<ids.length;i++){
				if(i==0){
					districtids="'"+ids[i]+"'";
				}
				else{
					districtids=districtids+",'"+ids[i]+"'";
				}
			}
			Session session=this.getCurrentSession();
			String delhql1="delete from Dbc_community where  districtid in("+districtids+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where districtid in("+districtids+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  id in("+districtids+")";
			session.createQuery(delhql3).executeUpdate();
			session.close();
		}
		
	}
	
	/**
	 * @Title deletedistrict
	 * @Description  删除城区
	 * @return void
	 */
	public void deletedistrict(String districtid){
		if(districtid!=null&&districtid.length()>0){
			Session session=this.getCurrentSession();
			String delhql1="delete from Dbc_community where  districtid in("+districtid+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where districtid in("+districtid+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  id in("+districtid+")";
			session.createQuery(delhql3).executeUpdate();
		}
	}
}