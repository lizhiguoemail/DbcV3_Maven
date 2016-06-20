/**
 * @Project 源自dbc
 * @Title Dbc_logServiceImpl.java
 * @Package com.dbc.service.Impl;
 * @Description  日志实现service类
 * @author caihuajun
 * @date 2014-02-25
 * @version v2.0
 * 2015-07-15 caihuajun 修改了根据ip查询归属地的方法，用以适应在linux系统中不出现乱码
 */
package com.dbc.service.Impl;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import com.dbc.dao.Dbc_logDao;
import com.dbc.pojo.Dbc_log;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_logService;
import com.dbc.util.Dbcutil;
import com.dbc.util.ip.IPSeeker;
import com.opensymphony.xwork2.ActionContext;

public class Dbc_logServiceImpl extends BaseServiceImpl implements Dbc_logService{
	
	private Dbc_logDao dbc_logdao;
	

	public Dbc_logDao getDbc_logdao() {
		return dbc_logdao;
	}


	public void setDbc_logdao(Dbc_logDao dbcLogdao) {
		dbc_logdao = dbcLogdao;
	}


	/**
	 * @Title saveLog
	 * @Description  添加日志
	 * @param userinfo
	 * @param logtype //操作类型
	 * @param processname //服务名称
	 * @param methodname
	 * @param parameters
	 * @param ipaddress
	 * @param weburl
	 * @param description
	 * @param path
	 * @return void
	 */
	public void saveLog(Dbc_userinfo userinfo,String ipaddress,String processname,String methodname,String description)
	{
		Session session=dbc_logdao.openSession();
		session.beginTransaction();
		String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd HH:mm:ss");
		Dbc_log log=new Dbc_log();
		if(userinfo!=null)
		{
			log.setUserid(userinfo.getId());
			log.setUsername(userinfo.getUsername());
		}
		log.setProcessname(processname);
		log.setMethodname(methodname);
		log.setIpaddress(ipaddress);
		log.setDescription(description);
		log.setCreatedate(nowdate);
		if("0:0:0:0:0:0:0:1".equals(ipaddress)){
			log.setZone("本地");
			log.setDetailAddress("本机");
		}
		else if("0:0:0:0:0:0:0:1%0".equals(ipaddress)){
			log.setZone("本地");
			log.setDetailAddress("本机");
		}
		else if(ipaddress.length()>16)
		{
			log.setZone("未知v6IP地址");
			log.setDetailAddress("未知v6IP地址");
		}
		else
		{
			try {
				String separ = File.separator;
				ActionContext ac = ActionContext.getContext();
				HttpServletRequest request =(HttpServletRequest)ac.get(ServletActionContext.HTTP_REQUEST);
				String path2=request.getSession().getServletContext().getRealPath(separ);
				path2=path2+separ+"util";
				String filename="qqwry.dat";
				IPSeeker seeker = new IPSeeker(filename,path2);
				log.setZone(seeker.getCountry(ipaddress));
				log.setDetailAddress(seeker.getArea(ipaddress));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		session.save(log);
		session.getTransaction().commit();
		session.close();
	}

}
