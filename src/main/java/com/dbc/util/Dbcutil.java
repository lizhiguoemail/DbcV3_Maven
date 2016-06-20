/**
 * @Project 源自dbc
 * @Title Dbcutil.java
 * @Package com.dbc.util;
 * @Description  dbc工具类
 * @author caihuajun
 * @date 2009-11-01
 * @version v1.0
 * 2015-12-18 caihuajun 把java生成的uuid去除“-”,替换成“0”
 */
package com.dbc.util;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import com.dbc.pojo.Dbc_type;
import com.dbc.service.Dbc_typeService;
import com.dbc.util.ip.IPSeeker;

public class Dbcutil {
	
	public final static String searchchuli(String str)
	{
		//去除空格
		if(str==null)
		{
			str="";
		}
		else
		{
			str=str.replace("\'", "");
			str=str.trim();
		}
		
		return str;
	}
	
	/**
	 * @Title getIpByrequest
	 * @Description 通过request获取ip地址
	 * @param request
	 * @return String
	 */	
	public final static String getIpByrequest(HttpServletRequest request){
		String ipaddress=request.getRemoteAddr();
		if(ipaddress==null||"".equals(ipaddress))
		{
			ipaddress=request.getHeader("X-Real-IP");
		}
		return ipaddress;
	}
	
	/**
	 * @Title getIpByrequest
	 * @Description 通过request获取ip地址
	 * @param request
	 * @return String
	 */	
	public final static String[] getIpAddressAddressByrequest(HttpServletRequest request){
		String ipaddress=request.getRemoteAddr();
		if(ipaddress==null||"".equals(ipaddress))
		{
			ipaddress=request.getHeader("X-Real-IP");
		}
		String address[]=new String[3];
		address[0]=ipaddress;
		if("0:0:0:0:0:0:0:1".equals(ipaddress)){
			address[1]="本地";
			address[2]="本机";
		}
		else if(ipaddress.length()>16)
		{
			address[1]="未知v6IP地址";
			address[2]="未知v6IP地址";
		}
		else{
			try {
				String separ = File.separator;
				String path=request.getSession().getServletContext().getRealPath("/");
				path=path+separ+"util";
				String filename="qqwry.dat";
				IPSeeker seeker = new IPSeeker(filename,path);
				address[1]=seeker.getCountry(ipaddress);
				address[2]=seeker.getArea(ipaddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return address;
	}
	
	/**
	 * @Title getarr
	 * @Description 转化成字符串数组
	 * @param string
	 * @return String[]
	 */	
	public final static String[] getarr(String str){
		if(str==null||"".equals(str)){
			return null;
		}
		else{
			String[] arr=str.split(",");
			return arr;
		}
		
	}
	
	/**
	 * @Title setarrtoString
	 * @Description 转化成字符串数组
	 * @param arr
	 * @param split  分隔符
	 * @return String
	 */	
	public final static String setarrtoString(String[] arr,String split){
		String newstr="";
		if(arr!=null&&arr.length>0){
			for(int i=0;i<arr.length;i++){
				if(i==0){
					newstr=arr[0];
				}
				else{
					newstr=newstr+split+arr[i];
				}
			}
		}
		return newstr;
	}
	
	/**
	 * @Title getbooleanarr
	 * @Description 转化成boolean型数组
	 * @param string
	 * @return boolean[]
	 */	
	public final static boolean[] getbooleanarr(String str){
		if(str==null||"".equals(str)){
			return null;
		}
		else{
			String[] arr=str.split(",");
			boolean[] booleanarr=new boolean[arr.length];
			for(int i=0;i<arr.length;i++){
				if("true".equals(arr[i])){
					booleanarr[i]=true;
				}
				else{
					booleanarr[i]=false;
				}
			}
			return booleanarr;
		}
		
	}
	
	/**
	 * @Title getnowdateString
	 * @Description 获得时间字符串
	 * @param format  //时间格式
	 * @return String
	 */	
	public final static String getnowdateString(String format){
		 Date date=new Date();
		 SimpleDateFormat sf=new SimpleDateFormat(format);
		 String nowdateString=sf.format(date);
		 return nowdateString;
	}
	
	/**
	 * @Title getBase_typelist
	 * @Description 获得类型列表
	 * @param type_type  类别标识
	 * @return List
	 */	
	public final static List getBase_typelist(String type_type){
		 ApplicationContext applicationContext=MyApplicationContextUtil.getContext();
		 Dbc_typeService base_typeservice=(Dbc_typeService) applicationContext.getBean("dbc_typeservice");
		 List list=base_typeservice.selEntity(Dbc_type.class, Dbcutil.getarr("type_type") , Dbcutil.getarr(type_type), Dbcutil.getbooleanarr("false"), Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));
		 return list;
	}
	
	/**
	 * @Title ispc
	 * @Description 是否是pc端
	 * @param HttpServletRequest  request
	 * @return boolean
	 */	
	public final static boolean ispc(HttpServletRequest request){
		 String userAgent = request.getHeader("User-Agent");
		 String[] agents=new String[]{"Android", "iPhone","SymbianOS", "Windows Phone","iPad", "iPod"};
		 boolean flag = true;
		 for(int i=0;i<agents.length;i++){
			 if(userAgent.contains(agents[i])){
				 flag=false;
				 break;
			 }
		 }
		 return flag;
	}
	
	/**
	  * 判断字符串是否是整数
	  */
	 public static boolean isInteger(String value) {
	  try {
	   Integer.parseInt(value);
	   return true;
	  } catch (NumberFormatException e) {
	   return false;
	  }
	 }

	 /**
	  * 判断字符串是否是浮点数
	  */
	 public static boolean isDouble(String value) {
	  try {
	   Double.parseDouble(value);
	   if (value.contains("."))
	    return true;
	   return false;
	  } catch (NumberFormatException e) {
	   return false;
	  }
	 }

	 /**
	  * 判断字符串是否是数字
	  */
	 public static boolean isNumber(String value) {
	  return isInteger(value) || isDouble(value);
	 }
	 
	 /**
		 * @Title getsmallpic_path
		 * @Description 获得小图地址
		 * @return String
		 */	
		public static String getsmallpic_path(String bigpic_path) {
			String smallpic_path=bigpic_path;
			if(bigpic_path!=null&&!"".equals(bigpic_path)){
				File bigpic = new File(bigpic_path);
				if (bigpic.exists()) {
					String bigfileName=bigpic.getName();
					File smallpic=new File(bigpic_path.replace(bigfileName, "small_"+bigfileName));
					if (smallpic.exists()) {
						smallpic_path=bigpic_path.replace(bigfileName, "small_"+bigfileName);
					}
				}
			}
			return smallpic_path;
		}
		
		/**
		 * @Title getbigpic_path
		 * @Description 获得大图地址
		 * @return String
		 */	
		public static String getbigpic_path(String smallpic_path) {
			String bigpic_path=smallpic_path;
			if(smallpic_path!=null&&!"".equals(smallpic_path)){
				File smallpic = new File(smallpic_path);
				if (smallpic.exists()) {
					File bigpic=new File(smallpic_path.replace("small_", ""));
					if(bigpic.exists()){
						bigpic_path=smallpic_path.replace("small_", "");
					}
				}
			}
			return bigpic_path;
		}
		
		/**
		 * @Title getUUID
		 * @Description 获得UUID
		 * @return String
		 */	
		public static String getUUID() {
			String uuid=UUID.randomUUID().toString();
			String uuidstr=uuid.replace("-", "0");
			return uuidstr;
		}
		
		/**
		 * 获取uuid
		 */
		public static String getUUID(String repstr) {
			String uuid=UUID.randomUUID().toString();
			if(repstr!=null){
				uuid=uuid.replace("-", repstr);
			}
			return uuid;
		}
		
		/**
		 * 根据用户生日计算年龄
		 */
		public static int getAgeByBirthday(Date birthday) {
			Calendar cal = Calendar.getInstance();

			if (cal.before(birthday)) {
				throw new IllegalArgumentException(
				"The birthDay is before Now.It's unbelievable!");
			}
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(birthday);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
			int age = yearNow - yearBirth;
			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					// monthNow==monthBirth 
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					}
				} else {
					// monthNow>monthBirth 
					age--;
				}
			}
			return age;
		}
		
		/**
		 * 根据年龄计算出生日期
		 */
		public static String getBirthdayByAge(int age,boolean isneedmonth) {
			 Calendar mycalendar=Calendar.getInstance();//获取现在时间
			 int nowyear=mycalendar.get(Calendar.YEAR);
			 int byear=nowyear-age;
			 String birthday="";
			 if(isneedmonth==false){
				 birthday=byear+"-01-01";
			 }
			 else{
				 int bmonth=mycalendar.get(Calendar.MONTH)+1;
				 int bday=mycalendar.get(Calendar.DAY_OF_MONTH);
				 String bmonthstr="";
				 String bdaystr="";
				 if(bmonth<10){
					 bmonthstr="0"+bmonth;
				 }
				 else{
					 bmonthstr=""+bmonth;
				 }
				 if(bday<10){
					 bdaystr="0"+bday;
				 }
				 else{
					 bdaystr=""+bday;
				 }
				 birthday=byear+"-"+bmonthstr+"-"+bdaystr;
			 }
			 
			 return birthday;
		}
		
	/**
	 * 判断是否是手机号
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * 根据路径字符串截取文件名
	 */
	public static String getFilenamebystr(String str) {
		String fName = str.trim();  
        String fileName = fName.substring(fName.lastIndexOf("/")+1);
        //或者  
        //String fileName = fName.substring(fName.lastIndexOf("\\")+1);  
        return fileName;  
	}
		
		public static void main(String[] args) {
			String a=Dbcutil.getBirthdayByAge(30, true);
			System.out.println(a);
		}
}
