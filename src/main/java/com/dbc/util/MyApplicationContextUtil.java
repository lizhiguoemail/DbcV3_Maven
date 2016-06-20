package com.dbc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyApplicationContextUtil  implements ApplicationContextAware{
	
	private static ApplicationContext context;//声明一个静态变量保存   
	
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {   
		this.context=applicationcontext;   
	}   
	
	public static ApplicationContext getContext(){   
		return context;   
	}   

}
