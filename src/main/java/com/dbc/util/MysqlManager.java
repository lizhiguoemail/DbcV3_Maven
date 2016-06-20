package com.dbc.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

/*
 * mysql数据库本身有数据备份和批量数据插入的命令，java代码可执行这些命令。

安装mysql后，需要设置环境变量：我的电脑右击--属性--高级--环境变量，增加MYSQL_HOME=“mysql安装路径”，然后path=%MYSQL_HOME%/bin
 */
public class MysqlManager {
	 public static void backup_window(HttpServletRequest request) {
	        try {
	        	String separ = File.separator;
	        	Dbc_common_config dcc=new Dbc_common_config();
	    		String dbname=dcc.getvalue("mysql_dbname");
	    		String mysql_user=dcc.getvalue("mysql_user");
	    		String mysql_password=dcc.getvalue("mysql_password");
	    		String mysql_service=dcc.getvalue("mysql_service");
	    		String mysql_bin_windows_path=dcc.getvalue("mysql_bin_linux_path");
	    		String mysqldumppath="mysqldump";
	    		if(mysql_bin_windows_path!=null&&!"".equals(mysql_bin_windows_path)){
	    			mysql_bin_windows_path=mysql_bin_windows_path.replace("/", separ);
	    			mysqldumppath=mysql_bin_windows_path+separ+"mysqldump";
	    		}	
	        	// 要用来做导入用的sql目标文件：
	            String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd-HH-mm-ss");
	            String outpath=request.getSession().getServletContext().getRealPath(separ)+"backup"+separ+"database"+separ+"mysql"+separ+nowdate+".sql";
	            String mysql = mysqldumppath+" -u"+mysql_user+" -p"+mysql_password+" -h "+mysql_service+" --set-charset=utf8 "+dbname+" > " + outpath;
	            java.lang.Runtime.getRuntime().exec("cmd /c " + mysql);
	            System.out.println("/* Output widosws OK!*/");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 
	 public static void backup_linux(HttpServletRequest request) {
		 try {
	        	String separ = File.separator;
	        	Dbc_common_config dcc=new Dbc_common_config();
	    		String dbname=dcc.getvalue("mysql_dbname");
	    		String mysql_user=dcc.getvalue("mysql_user");
	    		String mysql_password=dcc.getvalue("mysql_password");
	    		String mysql_service=dcc.getvalue("mysql_service");
	    		String mysql_bin_linux_path=dcc.getvalue("mysql_bin_linux_path");
	    		String mysqldumppath="mysqldump";
	    		if(mysql_bin_linux_path!=null&&!"".equals(mysql_bin_linux_path)){
	    			mysql_bin_linux_path=mysql_bin_linux_path.replace("/", separ);
	    			mysqldumppath=mysql_bin_linux_path+separ+"mysqldump";
	    		}	    		
	        	// 要用来做导入用的sql目标文件：
	            String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd-HH-mm-ss");
	            String outpath=request.getSession().getServletContext().getRealPath(separ)+"backup"+separ+"database"+separ+"mysql"+separ+nowdate+".sql";
	            String mysql = mysqldumppath+" -u"+mysql_user+" -p"+mysql_password+" -h "+mysql_service+" --set-charset=utf8 "+dbname+" > " + outpath;
	            java.lang.Runtime.getRuntime().exec(new String[] { "sh", "-c", mysql});
	            System.out.println("/* Output linux11 OK!*/");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static void backuptables_window(HttpServletRequest request,String[] tables,boolean isjiegou) {
		 try {
	        	String separ = File.separator;
	        	Dbc_common_config dcc=new Dbc_common_config();
	        	String dbname=dcc.getvalue("mysql_dbname");
	    		String mysql_user=dcc.getvalue("mysql_user");
	    		String mysql_password=dcc.getvalue("mysql_password");
	    		String mysql_service=dcc.getvalue("mysql_service");
	    		String mysql_bin_windows_path=dcc.getvalue("mysql_bin_linux_path");
	    		String mysqldumppath="mysqldump";
	    		if(mysql_bin_windows_path!=null&&!"".equals(mysql_bin_windows_path)){
	    			mysql_bin_windows_path=mysql_bin_windows_path.replace("/", separ);
	    			mysqldumppath=mysql_bin_windows_path+separ+"mysqldump";
	    		}	
	        	// 要用来做导入用的sql目标文件：
	            String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd-HH-mm-ss");
	            String outpath=request.getSession().getServletContext().getRealPath("/")+"backup"+separ+"database"+separ+"mysql"+separ+nowdate+".sql";
	            String tablesstr="";
	        	if(tables!=null){
	        		for(int i=0;i<tables.length;i++){
	        			tablesstr=tablesstr+" "+tables[i];
	        		}
	        	}
	            String mysql = mysqldumppath+" -u"+mysql_user+" -p"+mysql_password+" -h "+mysql_service+" --set-charset=utf8 "+dbname+" "+tablesstr+"> " + outpath;
	            if(isjiegou){
	            	mysql=mysqldumppath+" -u"+mysql_user+" -p"+mysql_password+" -h "+mysql_service+" -d --set-charset=utf8 "+dbname+" "+tablesstr+"> " + outpath;
	            }
	            java.lang.Runtime.getRuntime().exec("cmd /c " + mysql);
	            System.out.println("/* Output OK!*/");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static void backuptables_linux(HttpServletRequest request,String[] tables,boolean isjiegou) {
		 try {
	        	String separ = File.separator;
	        	Dbc_common_config dcc=new Dbc_common_config();
	    		String dbname=dcc.getvalue("mysql_dbname");
	    		String mysql_user=dcc.getvalue("mysql_user");
	    		String mysql_password=dcc.getvalue("mysql_password");
	    		String mysql_service=dcc.getvalue("mysql_service");
	    		String mysql_bin_linux_path=dcc.getvalue("mysql_bin_linux_path");
	    		String mysqldumppath="mysqldump";
	    		if(mysql_bin_linux_path!=null&&!"".equals(mysql_bin_linux_path)){
	    			mysql_bin_linux_path=mysql_bin_linux_path.replace("/", separ);
	    			mysqldumppath=mysql_bin_linux_path+separ+"mysqldump";
	    		}	   
	        	// 要用来做导入用的sql目标文件：
	            String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd-HH-mm-ss");
	            String outpath=request.getSession().getServletContext().getRealPath(separ)+"backup"+separ+"database"+separ+"mysql"+separ+nowdate+".sql";
	            String tablesstr="";
	        	if(tables!=null){
	        		for(int i=0;i<tables.length;i++){
	        			tablesstr=tablesstr+" "+tables[i];
	        		}
	        	}
	            String mysql = mysqldumppath+" -u"+mysql_user+" -p"+mysql_password+" -h "+mysql_service+" --set-charset=utf8 "+dbname+" "+tablesstr+"> " + outpath;
	            if(isjiegou){
	            	mysql=mysqldumppath+" -u"+mysql_user+" -p"+mysql_password+" -h "+mysql_service+" -d --set-charset=utf8 "+dbname+" "+tablesstr+"> " + outpath;
	            }
	            java.lang.Runtime.getRuntime().exec(new String[] { "sh", "-c", mysql });
	            System.out.println("/* Output OK!*/");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    /**
	     * 导入
	     * 导入的时候需要数据库已经建好。
	     */
	    public static void load_window(HttpServletRequest request,String sqlpath) {
	    	try {
	    		String separ = File.separator;
	    		Dbc_common_config dcc=new Dbc_common_config();
	    		String dbname=dcc.getvalue("mysql_dbname");
	    		String mysql_user=dcc.getvalue("mysql_user");
	    		String mysql_password=dcc.getvalue("mysql_password");
	    		String mysql_bin_windows_path=dcc.getvalue("mysql_bin_linux_path");
	    		String mysqlpath="mysql";
	    		if(mysql_bin_windows_path!=null&&!"".equals(mysql_bin_windows_path)){
	    			mysql_bin_windows_path=mysql_bin_windows_path.replace("/", separ);
	    			mysqlpath=mysql_bin_windows_path+separ+"mysql";
	    		}	
	        	String mysql = mysqlpath+"  -u"+mysql_user+" -p"+mysql_password+" "+dbname+" <"+sqlpath;
	            java.lang.Runtime.getRuntime().exec("cmd /c " + mysql);
	            System.out.println("/* Load OK! */");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * 导入
	     * 导入的时候需要数据库已经建好。
	     */
	    public static void load_linux(HttpServletRequest request,String sqlpath) {
	    	try {
	    		String separ = File.separator;
	    		Dbc_common_config dcc=new Dbc_common_config();
	    		String dbname=dcc.getvalue("mysql_dbname");
	    		String mysql_user=dcc.getvalue("mysql_user");
	    		String mysql_password=dcc.getvalue("mysql_password");
	    		String mysql_bin_linux_path=dcc.getvalue("mysql_bin_linux_path");
	    		String mysqlpath="mysql";
	    		if(mysql_bin_linux_path!=null&&!"".equals(mysql_bin_linux_path)){
	    			mysql_bin_linux_path=mysql_bin_linux_path.replace("/", separ);
	    			mysqlpath=mysql_bin_linux_path+separ+"mysql";
	    		}	   
	        	String mysql = mysqlpath+" -u"+mysql_user+" -p"+mysql_password+" "+dbname+" <"+sqlpath;
	        	java.lang.Runtime.getRuntime().exec(new String[] { "sh", "-c", mysql });
	            System.out.println("/* Load OK! */");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    /*
	     * 补充一下

			Mysql导出表结构及表数据 mysqldump用法
			命令行下具体用法如下：  mysqldump -u用户名 -p密码 -d 数据库名 表名 脚本名;
			
			    1、导出数据库为dbname的表结构（其中用户名为root,密码为dbpasswd,生成的脚本名为db.sql）
			    mysqldump -uroot -pdbpasswd -d dbname >db.sql;
			
			    2、导出数据库为dbname某张表(test)结构
			    mysqldump -uroot -pdbpasswd -d dbname test>db.sql;
			
			    3、导出数据库为dbname所有表结构及表数据（不加-d）
			    mysqldump -uroot -pdbpasswd  dbname >db.sql;
			
			    4、导出数据库为dbname某张表(test)结构及表数据（不加-d）
			    mysqldump -uroot -pdbpasswd dbname test>db.sql;
	     * **/
	    public static void main(String[] args) {
	         //backup();
	        //load();
	    }
	 
	}
