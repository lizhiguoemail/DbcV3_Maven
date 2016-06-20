/**
 * @Project 源自 dbc
 * @Title Page.java
 * @Package org.dbc.util;
 * @Description  分页算法
 * @author caihuajun
 * @date 2010-11-01
 * @version v1.0
 */
package com.dbc.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName Page
 * @Description 分页算法
 * @author caihuajun
 * @date 2010-11-01
 */
public class Page {
	
	/**
	 * @Title getnowpage
	 * @Description 根据总长度获得分页参数
	 * @param  len
	 * @param pagesize
	 * @param pagetype
	 * @param nowpage
	 * @param gotopage
	 * @return PageParm
	 */	
	public PageParm getnowpage(int len,int pagesize,String pagetype,int nowpage,int gotopage)
	{
		int total=len;
		int totalpage=total/pagesize;
		if(total%pagesize!=0)
		{
			totalpage=totalpage+1;
		}
		if(totalpage==0)
		{
		  totalpage=1;
		}
		if("first".equals(pagetype))
		{
			nowpage=1;
		}
		if("last".equals(pagetype))
		{
			
			nowpage=totalpage;
		}
		if("gotopage".equals(pagetype))
		{
			if(gotopage<1)
			{
				gotopage=1;
			}
			
			if(totalpage<gotopage)
			{
				nowpage=totalpage;
			}
			if(totalpage>=gotopage)
			{
				nowpage=gotopage;
			}
		}
		if("previous".equals(pagetype))
		{
			if(nowpage>1)
			{
				nowpage=nowpage-1;
			}
		}
		if("next".equals(pagetype))
		{
			
			if(nowpage<totalpage)
			{
				nowpage=nowpage+1;
			}
		}
		PageParm p=new PageParm();
		p.setNowpage(nowpage);
		p.setTotal(""+total);
		p.setTotalpage(""+totalpage);
		return p;
	}
	
	/**
	 * @Title getnowpage
	 * @Description 根据总长度获得分页参数
	 * @param  len
	 * @param pagesize
	 * @param pagetype
	 * @param nowpage
	 * @param gotopage
	 * @return PageParm
	 */	
	public PageParm getnowpage(int len,PageParm pageparm)
	{
		int pagesize=pageparm.getPagesize();
		int nowpage=pageparm.getNowpage();
		String pagetype=pageparm.getPagetype();
		int gotopage=pageparm.getGotopage();
		int total=len;
		int totalpage=0;
		if(pagesize>0){
			 totalpage=total/pagesize;
			 if(total%pagesize!=0){
				totalpage=totalpage+1;
			}
		}
		if(totalpage==0)
		{
		  totalpage=1;
		}
		if("first".equals(pagetype))
		{
			nowpage=1;
		}
		if("last".equals(pagetype))
		{
			
			nowpage=totalpage;
		}
		if("gotopage".equals(pagetype))
		{
			if(gotopage<1)
			{
				gotopage=1;
			}
			
			if(totalpage<gotopage)
			{
				nowpage=totalpage;
			}
			if(totalpage>=gotopage)
			{
				nowpage=gotopage;
			}
		}
		if("previous".equals(pagetype))
		{
			if(nowpage>1)
			{
				nowpage=nowpage-1;
			}
		}
		if("next".equals(pagetype))
		{
			
			if(nowpage<totalpage)
			{
				nowpage=nowpage+1;
			}
		}
		//PageParm p=new PageParm();
		pageparm.setNowpage(nowpage);
		pageparm.setTotal(""+total);
		pageparm.setTotalpage(""+totalpage);
		return pageparm;
	}
	
	/**
	 * @Title setparm
	 * @Description 页面传参
	 * @param  request
	 * @return void
	 */	
	public void setparm(HttpServletRequest request)
	{
		String nowpage=request.getParameter("nowpage");
		String gotopagetype=request.getParameter("gotopagetype");
		String gotopageString =request.getParameter("gotopageString");
		request.setAttribute("nowpage", nowpage);
		request.setAttribute("gotopagetype", gotopagetype);
		request.setAttribute("gotopageString", gotopageString);
	}
}
