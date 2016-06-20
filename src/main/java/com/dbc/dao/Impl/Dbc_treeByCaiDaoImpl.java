/**
 * @Project dbc
 * @Title TreeByCaiDaoImpl.java
 * @Package org.dbc.dao;
 * @Description 蔡式树形结构表Dao
 * @author caihuajun
 * @date 2011-06-07
 * @version v1.0
 * 2014-03-18 增加方法selbypatharr 方便以后查多个id的值
 */
package com.dbc.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import com.dbc.dao.Dbc_treeByCaiDao;
import org.hibernate.Query;
import org.hibernate.Session;

import com.dbc.pojo.Dbc_treeByCai;

/**
 * @ClassName TreeByCaiDao
 * @Description 蔡式树形结构表dao
 * @author caihuajun
 * @date 2011-06-07
 */
public class Dbc_treeByCaiDaoImpl extends BaseDaoImpl implements Dbc_treeByCaiDao {
	
	/**
	 * @Title selSonNum
	 * @Description 查询有多少以此ID为父ID的节点
	 * @param pid
	 * @return Integer
	 */
	public Integer selSonNum(String pid) {
		Session session=this.openSession();
		session.beginTransaction();
		String lensql="select count(*) from Dbc_treeByCai where deletemark='0'  and parentid='"+pid+"' ";
		Query query= session.createSQLQuery(lensql);
		int len=Integer.parseInt(query.list().get(0).toString());
		session.getTransaction().commit();
		session.close();
		return len;
	}
	
	/**
	 * @Title selByDeepPid
	 * @Description 获得同父节点同级的节点的最大或者最小的排序码
	 * @param treetype
	 * @param deep
	 * @param parentid
	 * @param order
	 * @return List
	 */
	public List selByDeepPid(String treetype, String deep,String parentid,String order) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql=" from Dbc_treeByCai where deletemark='0' and treetype='"+treetype+"' and deep='"+deep+"' and parentid='"+parentid+"' order by sortcode "+order;
		List list=new ArrayList();
		list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title selByPidPre
	 * @Description 获得同父节点同级的节点的最大或者最小的排序码
	 * @param treetype
	 * @param deep
	 * @param ordersort
	 * @param compare
	 * @param parentid
	 * @param order
	 * @return List
	 */
	public List selByPidPre(String treetype, String deep,Double sortcode,String compare,String parentid,String order) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql=" from Dbc_treeByCai where deletemark ='0' and treetype='"+treetype+"' and deep='"+deep+"' and sortcode"+compare+sortcode+" and parentid='"+parentid+"' order by  sortcode "+order;
		List list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title selbypath
	 * @Description 根据parentpath查询
	 * @param treetype
	 * @param path
	 * @param order
	 * @return List
	 */
	public List selbypath(String treetype, String path, String order) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql="from Dbc_treeByCai where deletemark='0'  and treetype='"+treetype+"' and (parentpath like '%/"+path+"/%' or id='"+path+"') order by sortcode "+order;
		List list= new ArrayList();
		list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title selbypatharr
	 * @Description 根据parentpath数组查询
	 * @param treetype
	 * @param patharr
	 * @param order
	 * @return List
	 */
	public List selbypatharr(String treetype, String[] patharr, String order) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql="from Dbc_treeByCai where deletemark='0' and treetype='"+treetype+"'";
		hql=hql+" and(1=2 ";
		// and (parentpath like '%/"+path+"/%' or id='"+path+"') order by sortcode "+order;
		if(patharr!=null){
			for(int i=0;i<patharr.length;i++){
				hql=hql+" or (parentpath like '%/"+patharr[i]+"/%' or id='"+patharr[i]+"')";
			}
		}
		hql=hql+" ) order by sortcode "+order;
		List list=new ArrayList();
		list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title setbypath
	 * @Description 根据parentpath设置
	 * @param path
	 * @param fieldname
	 * @param content
	 * @return void
	 */
	public void setbypath(String path, String fieldname,String content) {
		String hql="update Dbc_treeByCai set " +fieldname+" = '"+content+"' ";
			   hql+=" where  parentpath like '%/"+path+"/%' or id='"+path+"'";
	     this.getCurrentSession().createQuery(hql).executeUpdate();
	}
	
	/**
	 * @Title setorderbycha
	 * @Description 更新排序码
	 * @param list
	 * @param fangshi
	 * @param cha
	 * @return void
	 */
	public void setorderbycha( List list,String fangshi, int cha) {
		String hql=" update Dbc_treeByCai set sortcode=sortcode"+fangshi+cha+" where 1=2";
		for(int i=0;i<list.size();i++)
		{
			Dbc_treeByCai treeByCai=(Dbc_treeByCai) list.get(i);
			hql=hql+" or id='"+treeByCai.getId()+"'";
		}
		this.getCurrentSession().createQuery(hql).executeUpdate();
	}
	
	/**
	 * @Title setorderbycha
	 * @Description 更新排序码
	 * @param treetype
	 * @param sortcode
	 * @param compare
	 * @param list
	 * @param fangshi
	 * @param cha
	 * @return void
	 */
	public void setorderbycha(String treetype,Double sortcode, String compare,List list,String fangshi, double cha) {
		String hql=" update Dbc_treeByCai set sortcode=sortcode"+fangshi+cha+" where treetype='"+treetype+"' and  sortcode "+compare+sortcode;
		for(int i=0;i<list.size();i++)
		{
			Dbc_treeByCai treeByCai=(Dbc_treeByCai) list.get(i);
			hql=hql+" and id!='"+treeByCai.getId()+"'";
		}
		this.getCurrentSession().createQuery(hql).executeUpdate();
	}
	
	/**
	 * @Title selbyupjishu
	 * @Description 查找上级节点
	 * @param treetype
	 * @param pid
	 * @param deep
	 * @param compare
	 * @param sortcode
	 * @param order
	 * @return List
	 */
	public List selbyupjishu(String treetype, String pid, String deep,String compare,Integer sortcode,String order) {
		Session session=this.openSession();
		session.beginTransaction();
		String hql="from Dbc_treeByCai where deletemark ='0'  and treetype='"+treetype+"' and deep='"+deep+"' and parentid='"+pid+"' and sortcode "+compare+sortcode+" and order by sortcode "+order;
		List list=new ArrayList();
		list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title updatefortianjia
	 * @Description 为添加更新
	 * @param treetype
	 * @param ordersort
	 * @return void
	 */
	public void updatefortianjia(String treetype,double sortcode) {
		String hql=" update Dbc_treeByCai set sortcode=sortcode+2 where 1=1";
		hql=hql+" and treetype='"+treetype+"' and sortcode>"+sortcode;
		this.getCurrentSession().createQuery(hql).executeUpdate();
	}
	
	/**
	 * @Title selBytreetype
	 * @Description 查询指定类别
	 * @param treetype
	 * @return List
	 */
	public List selBytreetype(String treetype)
	{
		Session session=this.openSession();
		session.beginTransaction();
		String hql=" from Dbc_treeByCai where deletemark='0' and treetype='"+treetype+"' order by sortcode asc";
		List list=new ArrayList();
		list=session.createQuery(hql).list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	/**
	 * @Title deletebypath
	 * @Description 根据parentpath删除
	 * @param path
	 * @return void
	 */
	public void deletebypath(String path) {
		Session session=this.getCurrentSession();
		String deletepermit="delete Permit where departmentid in(select id from Dbc_treeByCai m where  m.parentpath like '%/"+path+"/%' or m.id='"+path+"')";
		String hql="delete Dbc_treeByCai where  parentpath like '%/"+path+"/%' or id='"+path+"'";
		session.createQuery(deletepermit).executeUpdate();
		session.createQuery(hql).executeUpdate();
	}
}
