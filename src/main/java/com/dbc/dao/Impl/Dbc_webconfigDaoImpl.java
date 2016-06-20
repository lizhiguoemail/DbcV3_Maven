/**
* @Project 源自 dbc
* @Title WebconfigDaoImpl.java
* @Package org.dbc.dao
* @Description 网站基本参数设置dao类
* @author caihuajun
* @date 2013-05-06
* @version v1.0
*/

package com.dbc.dao.Impl;

import java.util.List;

import com.dbc.dao.Dbc_webconfigDao;
import com.dbc.pojo.Dbc_webconfig;

/**
* @ClassName WebconfigDaoImpl
* @Description 网站基本参数设置dao类
* @author caihuajun
* @date 2013-05-06
*/
public class Dbc_webconfigDaoImpl extends BaseDaoImpl implements Dbc_webconfigDao{
	
	/**
	 * @Title getwebconfig
	 * @Description   获取web配置
	 * @return Webconfig  
	 */
	public Dbc_webconfig getwebconfig() {
		Dbc_webconfig webconfig=null;
		try{
			String hql=" from Dbc_webconfig where deletemark='0'";
			List result=this.openSession().createQuery(hql).list();
			if(result!=null&&result.size()>0){
				webconfig=(Dbc_webconfig) result.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return webconfig;
	}

}