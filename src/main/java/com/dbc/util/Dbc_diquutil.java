package com.dbc.util;

import java.util.List;
import org.springframework.context.ApplicationContext;
import com.dbc.pojo.Dbc_city;
import com.dbc.pojo.Dbc_community;
import com.dbc.pojo.Dbc_district;
import com.dbc.pojo.Dbc_province;
import com.dbc.pojo.Dbc_street;
import com.dbc.service.Dbc_provinceService;
/**
 * 专门为地区下拉框做的工具类
 * @author caihuajun
 * 2015-07-23
 */
public class Dbc_diquutil {
	
	/*
	 * 获得城市级下拉框多级联动
	 */
	public String get_province_city_select() {
		ApplicationContext app=MyApplicationContextUtil.getContext();
		Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) app.getBean("dbc_provinceservice");
		List listprovince=dbc_provinceservice.selEntity(Dbc_province.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listcity=dbc_provinceservice.selEntity(Dbc_city.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		String cityData="{";
		for(int i=0;i<listprovince.size();i++){
    		Dbc_province province=(Dbc_province) listprovince.get(i);
    		if(i==0){
    			cityData=cityData+"\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    		else{
    			cityData=cityData+",\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    	   // List listcity=dbc_provinceservice.selEntity(Dbc_city.class, Dbcutil.getarr("provinceid"), Dbcutil.getarr(province.getId()), Dbcutil.getbooleanarr("false"), Dbcutil.getarr("sortcode"), Dbcutil.getarr("desc"));	
    		int total_city=0;
    		if(listcity!=null&&listcity.size()>0){
    			int num_city=0;
    			for(int j=0;j<listcity.size();j++){
    				Dbc_city city=(Dbc_city) listcity.get(j);
    				if(province.getId().equals(city.getProvinceid())){
    					total_city++;
    					if(num_city==0){
    						cityData=cityData+",\"cell\":{";
        					cityData=cityData+"\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
        				else{
        					cityData=cityData+",\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
        				num_city++;
        				cityData=cityData+"}";
    				}
    			}
    			if(num_city>0){
    				cityData=cityData+"}}";
    			}
    		}
    		if(total_city==0){
    			cityData=cityData+"}";
    		}
    		System.out.println("获得城市级下拉框多级联动,第"+i+"条");
    	}
		cityData=cityData+"}";
    	return cityData;
	}
	
	/*
	 * 获得城区级下拉框多级联动
	 */
	public String get_province_district_select() {
		ApplicationContext app=MyApplicationContextUtil.getContext();
		Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) app.getBean("dbc_provinceservice");
		List listprovince=dbc_provinceservice.selEntity(Dbc_province.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listcity=dbc_provinceservice.selEntity(Dbc_city.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listdistrict=dbc_provinceservice.selEntity(Dbc_district.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		String districtData="{";
    	for(int i=0;i<listprovince.size();i++){
    		Dbc_province province=(Dbc_province) listprovince.get(i);
    		if(i==0){
    			districtData=districtData+"\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    		else{
    			districtData=districtData+",\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    		int total_city=0;
    		if(listcity!=null&&listcity.size()>0){
    			int num_city=0;
    			for(int j=0;j<listcity.size();j++){
    				Dbc_city city=(Dbc_city) listcity.get(j);
    				if(province.getId().equals(city.getProvinceid())){
    					total_city++;
        				if(num_city==0){
        					districtData=districtData+",\"cell\":{";
        					districtData=districtData+"\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
        				else{
        					districtData=districtData+",\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
        				int total_district=0;
        				if(listdistrict!=null&&listdistrict.size()>0){
        					int num_district=0;
        					for(int k=0;k<listdistrict.size();k++){
        						Dbc_district district=(Dbc_district)listdistrict.get(k);
        						if(city.getId().equals(district.getCityid())){
        							total_district++;
        							if(num_district==0){
        								districtData=districtData+",\"cell\":{";
                    					districtData=districtData+"\""+district.getId()+"\":{\"name\":\""+district.getDistrictname()+"\"}";
                    				}
                    				else{
                    					districtData=districtData+",\""+district.getId()+"\":{\"name\":\""+district.getDistrictname()+"\"}";
                    				}
        							num_district++;
        						}
        					}
        					if(num_district>0){
        						districtData=districtData+"}}";
        					}
        				}
        				if(total_district==0){
        					districtData=districtData+"}";
        				}
        				num_city++;
    				}
    			}
    			if(num_city>0){
    				districtData=districtData+"}}";
    			}
    		}
    		if(total_city==0){
    			districtData=districtData+"}";
    		}
    		System.out.println("获得城区级下拉框多级联动,第"+i+"条");
    	}
    	districtData=districtData+"}";
    	return districtData;
	}
	
	/*
	 * 获得街道级下拉框多级联动
	 */
	public String get_province_street_select() {
		ApplicationContext app=MyApplicationContextUtil.getContext();
		Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) app.getBean("dbc_provinceservice");
		List listprovince=dbc_provinceservice.selEntity(Dbc_province.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listcity=dbc_provinceservice.selEntity(Dbc_city.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listdistrict=dbc_provinceservice.selEntity(Dbc_district.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List liststreet=dbc_provinceservice.selEntity(Dbc_street.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		String districtData="{";
    	for(int i=0;i<listprovince.size();i++){
    		Dbc_province province=(Dbc_province) listprovince.get(i);
    		if(i==0){
    			districtData=districtData+"\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    		else{
    			districtData=districtData+",\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    		int total_city=0;
    		if(listcity!=null&&listcity.size()>0){
    			int num_city=0;
    			for(int j=0;j<listcity.size();j++){
    				Dbc_city city=(Dbc_city)listcity.get(j);
    				if(province.getId().equals(city.getProvinceid())){
    					total_city++;
        				if(num_city==0){
        					districtData=districtData+",\"cell\":{";
        					districtData=districtData+"\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
        				else{
        					districtData=districtData+",\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
        				int total_district=0;
        				if(listdistrict!=null&&listdistrict.size()>0){
        					int district_num=0;
        					for(int k=0;k<listdistrict.size();k++){
        						Dbc_district district=(Dbc_district)listdistrict.get(k);
        						if(city.getId().equals(district.getCityid())){
        							total_district++;
             						if(district_num==0){
             							districtData=districtData+",\"cell\":{";
                    					districtData=districtData+"\""+district.getId()+"\":{\"name\":\""+district.getDistrictname()+"\"";
                    				}
                    				else{
                    					districtData=districtData+",\""+district.getId()+"\":{\"name\":\""+district.getDistrictname()+"\"";
                    				}
             						int total_street=0;
            	    				if(liststreet!=null&&liststreet.size()>0){
            							int street_num=0;
            							for(int h=0;h<liststreet.size();h++){
            	    						Dbc_street street=(Dbc_street)liststreet.get(h);
            	    						if(district.getId().equals(street.getDistrictid())){
            	    							total_street++;
            	    							if(street_num==0){
            	    								districtData=districtData+",\"cell\":{";
                	            					districtData=districtData+"\""+street.getId()+"\":{\"name\":\""+street.getStreetname()+"\"}";
                	            				}
                	            				else{
                	            					districtData=districtData+",\""+street.getId()+"\":{\"name\":\""+street.getStreetname()+"\"}";
                	            				}
            	    							street_num++;
            	    						}
        								}
            							if(street_num>0){
            								districtData=districtData+"}}";
            							}
            						}
            	    				if(total_street==0){
            	    					districtData=districtData+"}";
            	    				}
            	    				district_num++;
        						}
        					}
        					if(district_num>0){
        						districtData=districtData+"}}";
        					}
        				}
        				if(total_district==0){
        					districtData=districtData+"}";
        				}
        				num_city++;
    				}
    			}
    			if(num_city>0){
    				districtData=districtData+"}}";
    			}
    		}
    		if(total_city==0){
    			districtData=districtData+"}";
    		}
    		System.out.println("获得街道级下拉框多级联动,第"+i+"条");
    	}
    	districtData=districtData+"}";
    	return districtData;
	}
	
	/*
	 * 获得社区级下拉框多级联动
	 */
	public String get_province_community_select() {
		ApplicationContext app=MyApplicationContextUtil.getContext();
		Dbc_provinceService dbc_provinceservice=(Dbc_provinceService) app.getBean("dbc_provinceservice");
		List listprovince=dbc_provinceservice.selEntity(Dbc_province.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listcity=dbc_provinceservice.selEntity(Dbc_city.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listdistrict=dbc_provinceservice.selEntity(Dbc_district.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List liststreet=dbc_provinceservice.selEntity(Dbc_street.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		List listcommunity=dbc_provinceservice.selEntity(Dbc_community.class, null, null, null, Dbcutil.getarr("sortcode"), Dbcutil.getarr("asc"));
		String districtData="{";
    	for(int i=0;i<listprovince.size();i++){
    		Dbc_province province=(Dbc_province) listprovince.get(i);
    		if(i==0){
    			districtData=districtData+"\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    		else{
    			districtData=districtData+",\""+province.getId()+"\":{\"name\":\""+province.getProvincename()+"\"";
    		}
    		int total_city=0;
    		if(listcity!=null&&listcity.size()>0){
    			int city_num=0;
    			for(int j=0;j<listcity.size();j++){
    				Dbc_city city=(Dbc_city)listcity.get(j);
    				if(province.getId().equals(city.getProvinceid())){
    					total_city++;
    					if(city_num==0){
    						districtData=districtData+",\"cell\":{";
        					districtData=districtData+"\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
        				else{
        					districtData=districtData+",\""+city.getId()+"\":{\"name\":\""+city.getCityname()+"\"";
        				}
    					int total_district=0;
        				if(listdistrict!=null&&listdistrict.size()>0){
        					int district_num=0;
        					for(int k=0;k<listdistrict.size();k++){
        						Dbc_district district=(Dbc_district) listdistrict.get(k);
        						if(city.getId().equals(district.getCityid())){
        							total_district++;
        							if(district_num==0){
        								districtData=districtData+",\"cell\":{";
                    					districtData=districtData+"\""+district.getId()+"\":{\"name\":\""+district.getDistrictname()+"\"";
                    				}
                    				else{
                    					districtData=districtData+",\""+district.getId()+"\":{\"name\":\""+district.getDistrictname()+"\"";
                    				}
        							int totalstreet=0;
            	    				if(liststreet!=null&&liststreet.size()>0){
            							int street_num=0;
            							for(int h=0;h<liststreet.size();h++){
            	    						Dbc_street street=(Dbc_street)liststreet.get(h);
            	    						if(district.getId().equals(street.getDistrictid())){
            	    							totalstreet++;
            	 	    						if(street_num==0){
            	 	    							districtData=districtData+",\"cell\":{";
                	            					districtData=districtData+"\""+street.getId()+"\":{\"name\":\""+street.getStreetname()+"\"";
                	            				}
                	            				else{
                	            					districtData=districtData+",\""+street.getId()+"\":{\"name\":\""+street.getStreetname()+"\"";
                	            				}
                	    						//---------------------------------------------------
            	 	    						int totalcommunity=0;
                	    	    				if(listcommunity!=null&&listcommunity.size()>0){
                	    							districtData=districtData+",\"cell\":{";
                	    							int community_num=0;
                	    							for(int g=0;g<listcommunity.size();g++){
                	    	    						Dbc_community community=(Dbc_community)listcommunity.get(g);
                	    	    						if(street.getId().equals(community.getStreetid())){
                	    	    							totalcommunity++;
                	    	    							if(community_num==0){
                    	    	            					districtData=districtData+"\""+community.getId()+"\":{\"name\":\""+community.getCommunityname()+"\"}";
                    	    	            				}
                    	    	            				else{
                    	    	            					districtData=districtData+",\""+community.getId()+"\":{\"name\":\""+community.getCommunityname()+"\"}";
                    	    	            				}
                	    	    							community_num++;
                	    	    						}
                									}
                	    							if(community_num>0){
                	    								districtData=districtData+"}}";
                	    							}
                	    						}
                	    	    				if(totalcommunity==0){
                	    	    					districtData=districtData+"}";
                	    	    				}
                	    	    				street_num++;
                	    						//------------------------------------------------------
            	    						}
           
        								}
            							if(street_num>0){
            								districtData=districtData+"}}";
            							}
            						}
            	    				if(totalstreet==0){
            	    					districtData=districtData+"}";
            	    				}
            	    				district_num++;
        						}
        					}
        					if(district_num>0){
        						districtData=districtData+"}}";
        					}
        				}
        				if(total_district==0){
        					districtData=districtData+"}";
        				}
        				city_num++;
    				}
    			}
    			if(city_num>0){
    				districtData=districtData+"}}";
    			}
    		}
    		if(total_city==0){
    			districtData=districtData+"}";
    		}
    		System.out.println("获得社区级下拉框多级联动,第"+i+"条");
    	}
    	districtData=districtData+"}";
    	return districtData;
	}

}
