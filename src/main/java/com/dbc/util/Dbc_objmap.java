package com.dbc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;

import com.dbc.pojo.Dbc_article;
import com.dbc.pojo.Dbc_community;
import com.dbc.pojo.Dbc_district;
import com.dbc.pojo.Dbc_street;
import com.dbc.pojo.Dbc_treeByCai;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.BaseService;
import com.dbc.util.Dbcutil;
import com.dbc.util.MyApplicationContextUtil;

public class Dbc_objmap {
	
	public static Map get_treeMap_article_list(List list) {
		ApplicationContext applicationcontext=MyApplicationContextUtil.getContext();
		BaseService baseservice=(BaseService) applicationcontext.getBean("baseservice");
		String ids="1";
		for(int i=0;i<list.size();i++){
			 Dbc_article s=(Dbc_article) list.get(i); //修改1处
          	 if(s.getTreeid()!=null){ //修改2处
          		ids=ids+","+s.getTreeid(); //修改3处
          	 }
       }
       List objlist=baseservice.selByOids(Dbc_treeByCai.class, Dbcutil.getarr(ids),null, null); //修改4处
       Map objMap=new HashMap();
       for(int i=0;i<objlist.size();i++){
    	   Dbc_treeByCai obj=(Dbc_treeByCai) objlist.get(i); //修改5处
       	   objMap.put(obj.getId(), obj);
       }
       return objMap;
	}
}
