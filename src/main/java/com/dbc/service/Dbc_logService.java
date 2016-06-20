package com.dbc.service;

import com.dbc.pojo.Dbc_userinfo;

public interface Dbc_logService extends BaseService{
	
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
	public void saveLog(Dbc_userinfo userinfo,String ipaddress,String processname,String methodname,String description);

}
