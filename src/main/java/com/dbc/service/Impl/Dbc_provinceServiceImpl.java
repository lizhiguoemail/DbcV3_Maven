/**
* @Project 源自 dbc
* @Title Dbc_provinceServiceImpl.java
* @Package com.dbc.service.Impl
* @Description 省份service实现类
* @author caihuajun
* @date 2015-04-27
* @version v2.0
*/

package com.dbc.service.Impl;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dbc.pojo.Dbc_city;
import com.dbc.pojo.Dbc_community;
import com.dbc.pojo.Dbc_district;
import com.dbc.pojo.Dbc_province;
import com.dbc.pojo.Dbc_street;
import com.dbc.service.Impl.BaseServiceImpl;
import com.dbc.service.Dbc_provinceService;
import com.dbc.util.Dbcutil;
import com.dbc.dao.Dbc_provinceDao;

/**
* @ClassName Dbc_provinceServiceImpl
* @Description 省份service实现类
* @author caihuajun
* @date 2015-04-27
*/
public class Dbc_provinceServiceImpl extends BaseServiceImpl implements Dbc_provinceService{

	private Dbc_provinceDao dbc_provincedao;

	public Dbc_provinceDao  getDbc_provincedao() {
    	return dbc_provincedao;
	}

	public void setDbc_provincedao(Dbc_provinceDao  dbc_provincedao) {
    	this.dbc_provincedao = dbc_provincedao;
	}
	
	/**
	 * @Title deleteprovinces
	 * @Description  批量删除省份
	 * @return void
	 */
	public void deleteprovinces(String[] ids){
		if(ids!=null){
			String provinceids=null;
			for(int i=0;i<ids.length;i++){
				if(i==0){
					provinceids="'"+ids[i]+"'";
				}
				else{
					provinceids=provinceids+",'"+ids[i]+"'";
				}
			}
			Session session=this.getCurrentSession();
			String delhql1="delete from Dbc_community where  provinceid in("+provinceids+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where provinceid in("+provinceids+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  provinceid in("+provinceids+")";
			session.createQuery(delhql3).executeUpdate();
			String delhql4="delete from Dbc_city where provinceid in("+provinceids+")";
			session.createQuery(delhql4).executeUpdate();
			String delhql5="delete from Dbc_province where id in("+provinceids+")";
			session.createQuery(delhql5).executeUpdate();
		}
	}
	
	/**
	 * @Title deleteprovince
	 * @Description  删除省份
	 * @return void
	 */
	public void deleteprovince(String provinceid)
	{
		if(provinceid!=null&&provinceid.length()>0){
			Session session=this.getCurrentSession();
			String delhql1="delete from Dbc_community where  provinceid in("+provinceid+")";
			session.createQuery(delhql1).executeUpdate();
			String delhql2="delete from Dbc_street where provinceid in("+provinceid+")";
			session.createQuery(delhql2).executeUpdate();
			String delhql3="delete from Dbc_district where  provinceid in("+provinceid+")";
			session.createQuery(delhql3).executeUpdate();
			String delhql4="delete from Dbc_city where provinceid in("+provinceid+")";
			session.createQuery(delhql4).executeUpdate();
			String delhql5="delete from Dbc_province where id in("+provinceid+")";
			session.createQuery(delhql5).executeUpdate();
			session.close();
		}
	}
	
	
	/**
	 * @Title adddiqubyxml
	 * @Description  导入数据
	 * @return void
	 */
	public void adddiqubyxml(HttpServletRequest request)
	{
		String separ = File.separator;
		String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd hh:mm:ss");
		Session session=this.getCurrentSession();
		
		String delhql1="delete from Dbc_province";
		String delhql2="delete from Dbc_city";
		String delhql3="delete from Dbc_district";
		String delhql4="delete from Dbc_street";
		String delhql5="delete from Dbc_community";
		session.createQuery(delhql5).executeUpdate();
		session.createQuery(delhql4).executeUpdate();
		session.createQuery(delhql3).executeUpdate();
		session.createQuery(delhql2).executeUpdate();
		session.createQuery(delhql1).executeUpdate();
		
		Element element1 = null;
		Element element2 = null;
		Element element3 = null;
		Element element4 = null;
		Element element5 = null;
		// 可以使用绝对路劲
		File f1 = new File(request.getRealPath("data"+separ+"xml"+separ+"diqu"+separ+"Provinces.xml"));
		File f2 = new File(request.getRealPath("data"+separ+"xml"+separ+"diqu"+separ+"Citys.xml"));
		File f3 = new File(request.getRealPath("data"+separ+"xml"+separ+"diqu"+separ+"Districts.xml"));
		File f4 = new File(request.getRealPath("data"+separ+"xml"+separ+"diqu"+separ+"Streets.xml"));
		File f5 = new File(request.getRealPath("data"+separ+"xml"+separ+"diqu"+separ+"Communitys.xml"));
		 // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		 DocumentBuilder db = null;
		 DocumentBuilderFactory dbf = null;
		 try {
		   // 返回documentBuilderFactory对象
		   dbf = DocumentBuilderFactory.newInstance();
		   // 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
		   db = dbf.newDocumentBuilder();
		   // 得到一个DOM并返回给document对象
		   Document dt1 = db.parse(f1);
		   Document dt2 = db.parse(f2);
		   Document dt3 = db.parse(f3);
		   Document dt4 = db.parse(f4);
		   Document dt5 = db.parse(f5);
		   // 得到一个elment根元素
		   element1 = dt1.getDocumentElement();
		   element2 = dt2.getDocumentElement();
		   element3 = dt3.getDocumentElement();
		   element4 = dt4.getDocumentElement();
		   element5 = dt5.getDocumentElement();
		   // 获得根节点
		   System.out.println("根元素1：" + element1.getNodeName());
		   System.out.println("根元素2：" + element2.getNodeName());
		   System.out.println("根元素3：" + element3.getNodeName());
		   System.out.println("根元素4：" + element4.getNodeName());
		   System.out.println("根元素5：" + element5.getNodeName());
		   // 获得根元素下的子节点
		   NodeList childNodes1 = element1.getChildNodes();
		   NodeList childNodes2 = element2.getChildNodes();
		   NodeList childNodes3 = element3.getChildNodes();
		   NodeList childNodes4 = element4.getChildNodes();
		   NodeList childNodes5 = element5.getChildNodes();
		   // 遍历这些子节点
		   //----------------------------------------------省份开始----------------------------------------------------
		   for (int i = 0; i < childNodes1.getLength(); i++) {
		    // 获得每个对应位置i的结点
		    Node node1 = childNodes1.item(i);
		    if ("Province".equals(node1.getNodeName())) {
		     System.out.println("导入省份: " + node1.getAttributes().getNamedItem("ProvinceName").getNodeValue() + "，省份ID:"+node1.getAttributes().getNamedItem("ID").getNodeValue()+ "，sortcode:"+node1.getAttributes().getNamedItem("sortcode").getNodeValue());
		     Dbc_province p=new Dbc_province();
		     p.setId(node1.getAttributes().getNamedItem("ID").getNodeValue());
		     p.setProvincename(node1.getAttributes().getNamedItem("ProvinceName").getNodeValue());
		     String sortcodestr="0";
		     if(node1.getAttributes().getNamedItem("sortcode").getNodeValue()!=null&&!"".equals(node1.getAttributes().getNamedItem("sortcode").getNodeValue())){
		    	 sortcodestr=node1.getAttributes().getNamedItem("sortcode").getNodeValue();
		     }
		     Double sortcode=Double.parseDouble(sortcodestr);
		     p.setSortcode(sortcode);
		     p.setCreatedate(nowdate);
		     session.save(p);
		     /*if ( i % 200 == 0 ){    
          		 //每20条为一批插入数据库并清空缓存         
          		  session.flush();        
          		  session.clear();   
          	  }*/
		    }
		   }
		   //----------------------------------------------省份结束----------------------------------------------------
		   
		   //----------------------------------------------城市开始----------------------------------------------------
		   for (int i = 0; i < childNodes2.getLength(); i++) {
			    // 获得每个对应位置i的结点
			    Node node2 = childNodes2.item(i);
			    if ("City".equals(node2.getNodeName())) {
			     System.out.println("导入城市: " + node2.getAttributes().getNamedItem("CityName").getNodeValue() + "，城市ID:"+node2.getAttributes().getNamedItem("ID").getNodeValue()+ "，城市邮编:"+node2.getAttributes().getNamedItem("ZipCode").getNodeValue()+ "，省份id:"+node2.getAttributes().getNamedItem("PID").getNodeValue()+ "，sortcode:"+node2.getAttributes().getNamedItem("sortcode").getNodeValue());
			     Dbc_city c=new Dbc_city();
			     c.setId(node2.getAttributes().getNamedItem("ID").getNodeValue());
			     c.setCityname(node2.getAttributes().getNamedItem("CityName").getNodeValue());
			     c.setZipcode(node2.getAttributes().getNamedItem("ZipCode").getNodeValue());
			     c.setProvinceid(node2.getAttributes().getNamedItem("PID").getNodeValue());
			     String sortcodestr="0";
			     if(node2.getAttributes().getNamedItem("sortcode").getNodeValue()!=null&&!"".equals(node2.getAttributes().getNamedItem("sortcode").getNodeValue())){
			    	 sortcodestr=node2.getAttributes().getNamedItem("sortcode").getNodeValue();
			     }
			     Double sortcode=Double.parseDouble(sortcodestr);
			     c.setSortcode(sortcode);
			     c.setCreatedate(nowdate);
			     session.save(c);
			     /*if ( i % 200 == 0 ){    
	          		 //每20条为一批插入数据库并清空缓存         
	          		  session.flush();        
	          		  session.clear();   
	          	  }*/
			    }
			   }
		   
		   //----------------------------------------------城市结束----------------------------------------------------
		   
		   //----------------------------------------------地区开始----------------------------------------------------
		   for (int i = 0; i < childNodes3.getLength(); i++) {
			    // 获得每个对应位置i的结点
			    Node node3 = childNodes3.item(i);
			    if ("District".equals(node3.getNodeName())) {
			     System.out.println("导入地区: " + node3.getAttributes().getNamedItem("DistrictName").getNodeValue() + "，地区ID:"+node3.getAttributes().getNamedItem("CID").getNodeValue()+ "，城市ID:"+node3.getAttributes().getNamedItem("ID").getNodeValue()+ "，sortcode:"+node3.getAttributes().getNamedItem("sortcode").getNodeValue());
			     Dbc_district d=new Dbc_district();
			     d.setId(node3.getAttributes().getNamedItem("ID").getNodeValue());
			     d.setDistrictname(node3.getAttributes().getNamedItem("DistrictName").getNodeValue());
			     d.setCityid(node3.getAttributes().getNamedItem("CID").getNodeValue());
			     d.setProvinceid(node3.getAttributes().getNamedItem("PID").getNodeValue());
			     String sortcodestr="0";
			     if(node3.getAttributes().getNamedItem("sortcode").getNodeValue()!=null&&!"".equals(node3.getAttributes().getNamedItem("sortcode").getNodeValue())){
			    	 sortcodestr=node3.getAttributes().getNamedItem("sortcode").getNodeValue();
			     }
			     Double sortcode=Double.parseDouble(sortcodestr);
			     d.setSortcode(sortcode);
			     d.setCreatedate(nowdate);
			     session.save(d);
			     /*if ( i % 200 == 0 ){    
	          		 //每20条为一批插入数据库并清空缓存         
	          		  session.flush();        
	          		  session.clear();   
	          	  }*/
			    }
			   }
		   //----------------------------------------------地区结束----------------------------------------------------
		   
		   //----------------------------------------------街道开始----------------------------------------------------
		   for (int i = 0; i < childNodes4.getLength(); i++) {
			    // 获得每个对应位置i的结点
			    Node node4 = childNodes4.item(i);
			    if ("Street".equals(node4.getNodeName())) {
			     System.out.println("\r\n找到街道: " + node4.getAttributes().getNamedItem("StreetName").getNodeValue() + "，地区ID:"+node4.getAttributes().getNamedItem("DID").getNodeValue()+ "，街道ID:"+node4.getAttributes().getNamedItem("ID").getNodeValue()+ "，sortcode:"+node4.getAttributes().getNamedItem("sortcode").getNodeValue());
			     Dbc_street street=new Dbc_street();
			     street.setId(node4.getAttributes().getNamedItem("ID").getNodeValue());
			     street.setStreetname(node4.getAttributes().getNamedItem("StreetName").getNodeValue());
			     street.setDistrictid(node4.getAttributes().getNamedItem("DID").getNodeValue());
			     street.setCityid(node4.getAttributes().getNamedItem("CID").getNodeValue());
			     street.setProvinceid(node4.getAttributes().getNamedItem("PID").getNodeValue());
			     String sortcodestr="0";
			     if(node4.getAttributes().getNamedItem("sortcode").getNodeValue()!=null&&!"".equals(node4.getAttributes().getNamedItem("sortcode").getNodeValue())){
			    	 sortcodestr=node4.getAttributes().getNamedItem("sortcode").getNodeValue();
			     }
			     Double sortcode=Double.parseDouble(sortcodestr);
			     street.setSortcode(sortcode);
			     street.setCreatedate(nowdate);
			     session.save(street);
			     /*if ( i % 200 == 0 ){    
	          		 //每20条为一批插入数据库并清空缓存         
	          		  session.flush();        
	          		  session.clear();   
	          	  }*/
			    }
			   }
		   //----------------------------------------------街道结束----------------------------------------------------
		   
		   //----------------------------------------------社区开始----------------------------------------------------
		   for (int i = 0; i < childNodes5.getLength(); i++) {
			    // 获得每个对应位置i的结点
			    Node node5 = childNodes5.item(i);
			    if ("Community".equals(node5.getNodeName())) {
			     System.out.println("\r\n找到社区: " + node5.getAttributes().getNamedItem("CommunityName").getNodeValue() + "，街道ID:"+node5.getAttributes().getNamedItem("SID").getNodeValue()+ "，社区ID:"+node5.getAttributes().getNamedItem("ID").getNodeValue()+ "，sortcode:"+node5.getAttributes().getNamedItem("sortcode").getNodeValue());
			     Dbc_community c=new Dbc_community();
			     c.setId(node5.getAttributes().getNamedItem("ID").getNodeValue());
			     c.setCommunityname(node5.getAttributes().getNamedItem("CommunityName").getNodeValue());
			     c.setStreetid(node5.getAttributes().getNamedItem("SID").getNodeValue());
			     c.setDistrictid(node5.getAttributes().getNamedItem("DID").getNodeValue());
			     c.setCityid(node5.getAttributes().getNamedItem("CID").getNodeValue());
			     c.setProvinceid(node5.getAttributes().getNamedItem("PID").getNodeValue());
			     String sortcodestr="0";
			     if(node5.getAttributes().getNamedItem("sortcode").getNodeValue()!=null&&!"".equals(node5.getAttributes().getNamedItem("sortcode").getNodeValue())){
			    	 sortcodestr=node5.getAttributes().getNamedItem("sortcode").getNodeValue();
			     }
			     Double sortcode=Double.parseDouble(sortcodestr);
			     c.setSortcode(sortcode);
			     c.setCreatedate(nowdate);
			     session.save(c);
			     /*if ( i % 200 == 0 ){    
	          		 //每20条为一批插入数据库并清空缓存         
	          		  session.flush();        
	          		  session.clear();   
	          	  }*/
			    }
			   }
		   //----------------------------------------------社区结束----------------------------------------------------
		  }
		
		  catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	
	/**
	 * @Title updatebycity
	 * @Description  更新关联数据
	 * @return void
	 */
	public void updatebycity()
	{
		List citylist=dbc_provincedao.selEntity(Dbc_city.class, null, null, null, null, null);
		Session session=this.getCurrentSession();
		for(int i=0;i<citylist.size();i++){
			Dbc_city city=(Dbc_city) citylist.get(i);
			String hql1="update Dbc_district set provinceid='"+city.getProvinceid()+"' where cityid='"+city.getId()+"'";
			session.createQuery(hql1).executeUpdate();
		}
	    session.close();
	}
	
	/**
	 * @Title updatebydistrict
	 * @Description  更新关联数据
	 * @return void
	 */
	public void updatebydistrict()
	{
		List districtlist=dbc_provincedao.selEntity(Dbc_district.class, null, null, null, null, null);
		Session session=this.getCurrentSession();
		for(int i=0;i<districtlist.size();i++){
			Dbc_district district=(Dbc_district) districtlist.get(i);
			String hql1="update Dbc_street set provinceid='"+district.getProvinceid()+"',cityid='"+district.getCityid()+"' where districtid='"+district.getId()+"'";
			session.createQuery(hql1).executeUpdate();
		}
	    session.close();
	}
	
	/**
	 * @Title updatebystreet
	 * @Description  更新关联数据
	 * @return void
	 */
	public void updatebystreet()
	{
		List streetlist=dbc_provincedao.selEntity(Dbc_street.class, null, null, null, null, null);
		Session session=this.getCurrentSession();
		for(int i=0;i<streetlist.size();i++){
			Dbc_street street=(Dbc_street) streetlist.get(i);
			String hql1="update Dbc_community set provinceid='"+street.getProvinceid()+"',cityid='"+street.getCityid()+"', districtid='"+street.getDistrictid()+"' where streetid='"+street.getId()+"'";
			session.createQuery(hql1).executeUpdate();
		}
	    session.close();
	}
	
}
