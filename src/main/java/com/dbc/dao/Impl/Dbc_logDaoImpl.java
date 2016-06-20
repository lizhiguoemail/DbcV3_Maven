/**
 * @Project 源自dbc
 * @Title LogDao.java
 * @Package org.dbc.dao;
 * @Description  日志DAO
 * @author caihuajun
 * @date 2010-06-14
 * @version v1.0
 */
package com.dbc.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import com.dbc.dao.Dbc_logDao;
import com.dbc.pojo.Dbc_log;
import com.dbc.util.Dbcutil;
import com.dbc.util.Page;
import com.dbc.util.PageParm;

/**
 * @ClassName LogDao
 * @Description 操作DAO
 * @author caihuajun
 * @date 2010-06-14
 */
public class Dbc_logDaoImpl extends BaseDaoImpl implements Dbc_logDao {
	
	/**
	 * @Title saveLog
	 * @Description  添加日志
	 * @param Dbc_log
	 * @return void
	 */
	public void saveLog(Dbc_log log)
	{
		Session session=this.getCurrentSession();
		session.save(log);
	}
	
	/**
	 * @Title selLog
	 * @Description  查询日志
	 * @param content
	 * @param logtype
	 * @param begindate
	 * @param enddate
	 * @param nowpage
	 * @param pagesize
	 * @param gotopagetype
	 * @param gotopage
	 * @return List
	 */
	public List selLog(String content,String logtype,String begindate,String enddate,int nowpage,int pagesize, String gotopagetype, int gotopage){
	 List returnlist=new ArrayList();
	 Session session=this.openSession();
	 session.beginTransaction();
	 content=Dbcutil.searchchuli(content);
	 begindate=Dbcutil.searchchuli(begindate);
	 enddate=Dbcutil.searchchuli(enddate);
	 String lensql="select count(*) from Dbc_log where deletemark='0' ";   
	 String relsql="select id,logtype from Dbc_log where deletemark='0' "; 
	 if(logtype!=null&&!"".equals(logtype))
	 {
		 lensql+=" and (logtype ='"+logtype+"') ";
		 relsql+=" and (logtype ='"+logtype+"') ";
	 }
	if(begindate!=null&&!"".equals(begindate))
	 {
		 lensql+=" and (createdate>='"+begindate+"') ";
		 relsql+=" and (createdate>='"+begindate+"') ";
	 }
	 
   if(enddate!=null&&!"".equals(enddate))
	 {
		 lensql+=" and (createdate<='"+enddate+"' or createdate like '%"+enddate+"%') ";
		 relsql+=" and (createdate<='"+enddate+"' or createdate like '%"+enddate+"%') ";
	 }
   if(content!=null&&!"".equals(content))
	 {
		 lensql+=" and (username like '%"+content+"%' or logtype like '%"+content+"%' or processname like '%"+content+"%' " +
		 		" or methodname like '%"+content+"%' or parameters like '%"+content+"%' or ipaddress like '%"+content+"%' " +
		 	    " or weburl like '%"+content+"%' or description like '%"+content+"%' or createdate like '%"+content+"%' ) ";
		 relsql+=" and (username like '%"+content+"%' or logtype like '%"+content+"%' or processname like '%"+content+"%' " +
	 		" or methodname like '%"+content+"%' or parameters like '%"+content+"%' or ipaddress like '%"+content+"%' " +
	 	    " or weburl like '%"+content+"%' or description like '%"+content+"%' or createdate like '%"+content+"%' ) ";
	 }
	 relsql+=" order by createdate desc";
	 Query query2=session.createQuery(lensql);
     int len=Integer.parseInt(query2.list().get(0).toString());
     Page page=new Page();
	    PageParm pageParm=page.getnowpage(len, pagesize, gotopagetype, nowpage, gotopage);
 	    Query query=this.openSession().createQuery(relsql);
	    query.setFirstResult((pageParm.getNowpage()-1)*pagesize);//设置从第几条开始查,pageParm.getNowpage() 为页码
		query.setMaxResults(pagesize);//最大显示的条数
		List temp=query.list();
		String ids="";
		for(int i=0;i<temp.size();i++){
			if(i<temp.size()-1){
			    ids+="'"+((Object[]) temp.get(i))[0]+"',";
			}else{
				ids+="'"+((Object[]) temp.get(i))[0]+"'";
			}
		}
		String hql="from Log";	
		if(!"".equals(ids)){
			hql+=" where id in ("+ids+") ";
		}else{
			hql+=" where 1=2";
		}
		hql+=" order by createdate desc";
		List list=session.createQuery(hql).list();
		returnlist.add(list);
		returnlist.add(pageParm);
		session.getTransaction().commit();
		session.close();
		return returnlist;
	}
	
	/**
	 * @Title selLogEXT
	 * @Description  查询日志
	 * @param getarr
	 * @param content
	 * @param logtype
	 * @param begindate
	 * @param enddate
	 * @param start
	 * @param limit
	 * @return List
	 */
	public List selLogEXT(String[] getarr,String content,String logtype,String begindate,String enddate,int start,int limit)
	{
	 List returnlist=new ArrayList();
	 Session session=this.openSession();
	 session.beginTransaction();
	 content=Dbcutil.searchchuli(content);
	 begindate=Dbcutil.searchchuli(begindate);
	 enddate=Dbcutil.searchchuli(enddate);
	 String getfield="";
		if(getarr!=null)
		{
			for(int i=0;i<getarr.length;i++)
			{
				if(getarr[i]!=null&&!"".equals(getarr[i]))
				{
					getfield=getfield+","+getarr[i];
				}
				
			}
		}
	 String lensql="select count(*) from Dbc_log where deletemark='0' ";   
	 String relsql="select id"+getfield+",deletemark from Dbc_log where deletemark='0'  "; 
	 if(logtype!=null&&!"".equals(logtype))
	 {
		 lensql+=" and (logtype ='"+logtype+"') ";
		 relsql+=" and (logtype ='"+logtype+"') ";
	 }
	if(begindate!=null&&!"".equals(begindate))
	 {
		 lensql+=" and (createdate>='"+begindate+"') ";
		 relsql+=" and (createdate>='"+begindate+"') ";
	 }
	 
   if(enddate!=null&&!"".equals(enddate))
	 {
		 lensql+=" and (createdate<='"+enddate+"' or createdate like '%"+enddate+"%') ";
		 relsql+=" and (createdate<='"+enddate+"' or createdate like '%"+enddate+"%') ";
	 }
   if(content!=null&&!"".equals(content))
	 {
		 lensql+=" and (username like '%"+content+"%' or logtype like '%"+content+"%' or processname like '%"+content+"%' " +
		 		" or methodname like '%"+content+"%' or parameters like '%"+content+"%' or ipaddress like '%"+content+"%' " +
		 	    " or weburl like '%"+content+"%' or description like '%"+content+"%' or createdate like '%"+content+"%' ) ";
		 relsql+=" and (username like '%"+content+"%' or logtype like '%"+content+"%' or processname like '%"+content+"%' " +
	 		" or methodname like '%"+content+"%' or parameters like '%"+content+"%' or ipaddress like '%"+content+"%' " +
	 	    " or weburl like '%"+content+"%' or description like '%"+content+"%' or createdate like '%"+content+"%' ) ";
	 }
	 relsql+=" order by createdate desc";
	 Query query2= session.createQuery(lensql);
     int len=Integer.parseInt(query2.list().get(0).toString());
 	 Query query=session.createQuery(relsql);
     query.setFirstResult(start);//设置从第几条开始查,pageParm.getNowpage() 为页码
	 query.setMaxResults(limit);//最大显示的条数
	 List list=query.list();
	 returnlist.add(len);
	 returnlist.add(list);
	 session.getTransaction().commit();
	 session.close();
	 return returnlist;
	}

}
