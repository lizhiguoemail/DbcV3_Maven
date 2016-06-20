package com.dbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class Dbc_common_config {
	
	public static String getvalue(String key){
		String returnstr="";
		try {
			InputStream in = new ClassPathResource("common_config.properties").getInputStream();
			Properties property = new Properties();
	        property.load(in);
	        returnstr=property.getProperty(key);
//	        this.workpath_java =property.getProperty("workpath_java");
//	        this.workpath_jsp =property.getProperty("workpath_jsp");
//	        this.mysql_dbname =property.getProperty("mysql_dbname");
//	        this.mysql_user =property.getProperty("mysql_user");
//	        this.mysql_password =property.getProperty("mysql_password");
//	        this.mysql_service =property.getProperty("mysql_service");
//	        this.mysql_bin_windows_path =property.getProperty("mysql_bin_windows_path");
//	        this.mysql_bin_linux_path =property.getProperty("mysql_bin_linux_path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnstr;
	}
	
}
