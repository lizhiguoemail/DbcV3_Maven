/**
 * @Project 源自 dbc
 * @Title PageParm.java
 * @Package org.dbc.util;
 * @Description  分页参数
 * @author caihuajun
 * @date 2010-11-01
 * @version v1.0
 */
package com.dbc.util;

/**
 * @ClassName PageParm
 * @Description 分页参数
 * @author caihuajun
 * @date 2010-11-01
 */
public class PageParm {
	
	private String totalpage; //总页数
	
	private String total; //总记录数
	
	private int nowpage=1; //当前页数
	
	private int pagesize=0;
	
	private int gotopage=1;
	
	private String pagetype="first";
	

	public String getPagetype() {
		return pagetype;
	}

	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}

	public PageParm(){
		
	}
	
	public PageParm(String nowpageString,String gotopagetypeString,String gotopageString,String pagesizeString){
		if(nowpageString!=null&&!"".equals(nowpageString))
		{
			nowpage=Integer.parseInt(nowpageString);
		}
		if(gotopagetypeString!=null&&!"".equals(gotopagetypeString))
		{
			pagetype=gotopagetypeString;
		}
 		if(gotopageString!=null&&!"".equals(gotopageString))
		{
			gotopage=Integer.parseInt(gotopageString);
			pagetype="gotopage";
		}
 		if(pagesizeString!=null&&!"".equals(pagesizeString))
		{
			pagesize=Integer.parseInt(pagesizeString);
		}
	}
	
	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getGotopage() {
		return gotopage;
	}

	public void setGotopage(int gotopage) {
		this.gotopage = gotopage;
	}

	public String getTotalpage() {
		return totalpage;
	}
	
	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
}
