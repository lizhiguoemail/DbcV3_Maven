package com.dbc.util;

public class Dbc_code {
	public String flag="dbc";
	
	public long interval=36000000; //时间间隔 1分钟等于60000毫秒，15分钟等于900000，1小时等于3600000，10小时等36000000
	
	public String getKeyByUserid_interval(String userid){
		String returnkey=null;
		try{
			if(!"".equals(flag)&&flag!=null&&userid!=null&&!"".equals(userid)){
				 String nowtime=""+System.currentTimeMillis();
				 String keystr=userid+"_*_"+nowtime;
				 DesUtils des = new DesUtils(flag);//自定义密钥   
				 String newkey=des.encrypt(keystr);
				 returnkey=newkey;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return returnkey;
	}
	
	public String getUseridByKey_interval(String key){
		String returnkey=null;
		try{
			if(!"".equals(key)&&key!=null){
				 DesUtils des = new DesUtils(flag);//自定义密钥   
				 String keystr=des.decrypt(key);
				 String[] strarr=keystr.split("_*_");
				 if(strarr.length>1){
					 String userid=strarr[0];
					 Long time=Long.parseLong(strarr[1]);
					 Long nowtime=System.currentTimeMillis();
					 if(time-nowtime>interval){
						 returnkey= "过期";
					 }
					 else{
						 if(time<nowtime){
							 returnkey=null;
						 }
						 else{
							 returnkey=userid;
						 }
					 }
				 }
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return returnkey;
	}
	
	public String getKeyByUserid(String userid) throws Exception{
		if(!"".equals(flag)&&flag!=null&&userid!=null&&!"".equals(userid)){
			 String keystr=userid;
			 DesUtils des = new DesUtils(flag);//自定义密钥   
			 String newkey=des.encrypt(keystr);
			 return newkey;
		}
		else{
			return null;
		}
	}
	
	public String getUseridByKey(String key) throws Exception{
		if(!"".equals(key)&&key!=null){
			 DesUtils des = new DesUtils(flag);//自定义密钥   
			 String userid=des.decrypt(key);
			 return userid;
		}
		else{
			return null;
		}
	}

}
