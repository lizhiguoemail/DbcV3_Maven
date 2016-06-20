package com.dbc;

import java.io.File;

import com.dbc.util.Dbcutil;

public class Test {
public static void main(String[] args) {
	//获得eclipse下的workspace下的工程路径
	  System.out.println(System.getProperty("user.dir"));
	  String smallpath="D://work//apache-tomcat-6.0.29//webapps//dbcV2//upload//images//app//small_1-backward.png";
	  //String bigpath="D://work//apache-tomcat-6.0.29//webapps//dbcV2//upload//images//app//1-backward.png";
	  //System.out.println("small:"+Dbcutil.getsmallpic_path(bigpath));
	  System.out.println("big:"+Dbcutil.getbigpic_path(smallpath));
}
}
