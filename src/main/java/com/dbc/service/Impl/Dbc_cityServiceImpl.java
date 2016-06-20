/**
* @Project 源自 dbc
* @Title Dbc_cityServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 城市service实现类
* @author caihuajun
* @date 2015-04-28
* @version v2.0
*/

package com.dbc.service.Impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dbc.pojo.Dbc_city;
import com.dbc.pojo.Dbc_log;
import com.dbc.pojo.Dbc_province;
import com.dbc.service.Impl.BaseServiceImpl;

import com.dbc.service.Dbc_cityService;

import com.dbc.dao.Dbc_cityDao;

/**
* @ClassName Dbc_cityServiceImpl
* @Description 城市service实现类
* @author caihuajun
* @date 2015-04-28
*/
public class Dbc_cityServiceImpl extends BaseServiceImpl implements Dbc_cityService{

	private Dbc_cityDao dbc_citydao;

	public Dbc_cityDao  getDbc_citydao() {
    	return dbc_citydao;
	}

	public void setDbc_citydao(Dbc_cityDao  dbc_citydao) {
    	this.dbc_citydao = dbc_citydao;
	}
	
	/**
	 * @Title deletecitys
	 * @Description  批量删除城市
	 * @return void
	 */
	public void deletecitys(String[] ids){
		if(ids!=null){
			String cityids=null;
			for(int i=0;i<ids.length;i++){
				if(i==0){
					cityids="'"+ids[i]+"'";
				}
				else{
					cityids=cityids+",'"+ids[i]+"'";
				}
			}
			Session session=this.getCurrentSession();
			Transaction tx=session.beginTransaction();
			String delhql1="delete from Dbc_community where  cityid in("+cityids+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where cityid in("+cityids+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  cityid in("+cityids+")";
			session.createQuery(delhql3).executeUpdate();
			String delhql4="delete from Dbc_city where id in("+cityids+")";
			session.createQuery(delhql4).executeUpdate();
			tx.commit();
			session.close();
		}
		
	}
	
	/**
	 * @Title deletecitys
	 * @Description  批量删除城市
	 * @return void
	 */
	public void deletecitys(String[] ids,Dbc_log dbc_log){
		if(ids!=null){
			String cityids=null;
			for(int i=0;i<ids.length;i++){
				if(i==0){
					cityids="'"+ids[i]+"'";
				}
				else{
					cityids=cityids+",'"+ids[i]+"'";
				}
			}
			Session session=this.getCurrentSession();
			Transaction tx=session.beginTransaction();
			String delhql1="delete from Dbc_community where  cityid in("+cityids+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where cityid in("+cityids+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  cityid in("+cityids+")";
			session.createQuery(delhql3).executeUpdate();
			String delhql4="delete from Dbc_city where id in("+cityids+")";
			session.createQuery(delhql4).executeUpdate();
			session.save(dbc_log);
			tx.commit();
			session.close();
		}
		
	}
	
	/**
	 * @Title deletecity
	 * @Description  删除城市
	 * @return void
	 */
	public void deletecity(String cityid){
		if(cityid!=null&&cityid.length()>0){
			Session session=this.getCurrentSession();
			Transaction tx=session.beginTransaction();
			String delhql1="delete from Dbc_community where  cityid in("+cityid+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where cityid in("+cityid+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  cityid in("+cityid+")";
			session.createQuery(delhql3).executeUpdate();
			String delhql4="delete from Dbc_city where id='"+cityid+"'";
			session.createQuery(delhql4).executeUpdate();
			tx.commit();
			session.close();
		}
	}
	
	/**
	 * @Title deletecity
	 * @Description  删除城市
	 * @return void
	 */
	public void deletecity(String cityid,Dbc_log dbc_log){
		if(cityid!=null&&cityid.length()>0){
			Session session=this.getCurrentSession();
			Transaction tx=session.beginTransaction();
			String delhql1="delete from Dbc_community where  cityid in("+cityid+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where cityid in("+cityid+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  cityid in("+cityid+")";
			session.createQuery(delhql3).executeUpdate();
			String delhql4="delete from Dbc_city where id='"+cityid+"'";
			session.createQuery(delhql4).executeUpdate();
			session.save(dbc_log);
			tx.commit();
			session.close();
		}
	}
}