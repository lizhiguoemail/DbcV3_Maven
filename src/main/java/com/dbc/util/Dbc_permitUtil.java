/**
 * @Project 源自dbcV2
 * @Title Dbc_permit.java
 * @Package com.dbc.util;
 * @Description  dbc权限判断类
 * @author caihuajun
 * @date 2015-12-18
 * @version v2.0
 */
package com.dbc.util;

import java.io.InputStream;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

import com.dbc.pojo.Dbc_userinfo;

public class Dbc_permitUtil {
	/**
	 * @Title getishavepermit
	 * @Description 判断是否有权限
	 * @return String
	 */	
	public final static boolean getishavepermit(Dbc_userinfo user,String act,String methode,String allpermitsstr){
		boolean ishave=true;
		if(user!=null){
        	if(allpermitsstr!=null&&methode!=null&&act!=null){
        		if(!"".equals(allpermitsstr)){
        			if(!"".equals(methode)){
        				if(check_syspermit(act,methode,allpermitsstr)){
            				if(check_userpermit(methode,user,act)==false){
            					ishave=false;
    	       				 }
            			}
        			}
        		}
        	}
		}
		return ishave;
	}
	
	//判断是否是系统权限，返回true说明这个权限已经开启
	public final static boolean check_syspermit(String act,String methode,String allpermitsstr){
    	boolean isopen=false;
    	if(allpermitsstr.contains(","+act+"_allmethode,")==true||allpermitsstr.contains(","+act+"_"+methode+",")==true){
    		isopen=true;
    	}
    	return isopen;
    }
    
	//判断这个用户是否有次权限，返回true证明有权限
	public final static boolean check_userpermit(String methode,Dbc_userinfo user,String act){
    	String alluserpermit=user.getAllpermits();
    	if("1".equals(user.getIsadmin())){
    		return true;
    	}
    	else{
    		if(alluserpermit.contains(","+act+"_allmethode,")==true||alluserpermit.contains(","+act+"_"+methode+",")==true){
        		return true;
        	}
        	else{
        		return false;
        	}
    	}
    }
    
    //判断是否在backstage_permit.properties设置不需要验证的方法，返回true，说明这个方法在后台有设置，可以通过。
	public static boolean check_backstage_permit(String act,String methode){
    	try {
            if(methode!=null){
            	InputStream in = new ClassPathResource("backstage_permit.properties").getInputStream();
                Properties property = new Properties();
    			property.load(in);
    		    String astring =property.getProperty("Action");
    		    String mstring =property.getProperty("Methode");
    		    String allastring =property.getProperty("AllAction");
    		    String allmstring=property.getProperty("Allmethode");
    		    String[] aarr=(astring+",").split(",");
    		    String[] marr=(mstring+",").split(",");
    		    if((","+allastring+",").indexOf(","+act+",")>0||(","+allmstring+",").indexOf(","+methode+",")>0){
    		    	return true;
    		    }
    		    else{
    		    	if(aarr!=null&&marr!=null&&aarr.length==marr.length){
    		    		int isallnum=0;
    		    		for(int i=0;i<aarr.length;i++){
    		    			if(act.equals(aarr[i])&&methode.equals(marr[i])){
    		    				isallnum=isallnum+1;
    		    			}
    		    		}
    		    		if(isallnum>0){
    		    			return true;
        		    	}
        		    	else{
           	             return false;  
        		    	}
    		    	}
    		    	else{
          	            return false;  
    		    	}
    		    }
            }
            else{
            	return false;  
            }
		} catch (Exception e) {
			return false;
		}
}
}
