package com.dbc.tag;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;

import com.dbc.pojo.Dbc_ad;
import com.dbc.service.Dbc_adService;
import com.dbc.util.Dbcutil;
import com.dbc.util.MyApplicationContextUtil;

public class Dbc_adTag extends TagSupport{
	
	private String  adid;
	
	//在开始标签属性设置后调用，如果返回SKIP_BODY则忽略标签之中的内容，如果返回EVAL_BODY_INCLUDE则将标签体的内容进行输出
	@Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            if(adid == null) {
                out.println("No ad Found...");
                return SKIP_BODY;
            }
            ApplicationContext applicationcontext=MyApplicationContextUtil.getContext();
            Dbc_adService dbc_adservice=(Dbc_adService) applicationcontext.getBean("dbc_adservice");
            Dbc_ad ad=(Dbc_ad) dbc_adservice.selByOid(Dbc_ad.class, adid);
            Date date=new Date();
            String edate=ad.getEdate();
            boolean isshow=true;
            if(edate!=null&&!"".equals(edate)){
            	SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            	Date e=sdf.parse(edate);
            	long elong=e.getTime();
            	long nowlong=date.getTime();
            	if(nowlong>elong){
            			isshow=false;
            			System.out.println("已经过期了，不需要显示出来");
            	}
            }
            if(isshow){
                if("常规模式".equals(ad.getMoshi())){
                	String width="1";
                	String height="1";
                	if(ad.getWidth()!=null&&!"".equals(ad.getWidth())){
                		width=ad.getWidth();
                	}
                	if(ad.getHeight()!=null&&!"".equals(ad.getHeight())){
                		height=ad.getHeight();
                	}
                	
                	if(ad.getImg().contains("http")){
                		if(ad.getLink()!=null&&!"".equals(ad.getLink())){
                			out.println("<a href=\""+ad.getLink()+"\" title=\""+ad.getTitle()+"\"><img src=\""+ad.getImg()+"\" style=\"width:"+width+"px;height:"+height+"px;\"/></a>");
                		}
                		else{
                			out.println("<img src=\""+ad.getImg()+"\" style=\"width:"+width+"px;height:"+height+"px;\"/>");
                		}
                		
                	}
                	else{
                		if(ad.getLink()!=null&&!"".equals(ad.getLink())){
                			out.println("<a href=\""+ad.getLink()+"\" title=\""+ad.getTitle()+"\"><img src=\"<%=path %>/"+ad.getImg()+"\" style=\"width:"+width+"px;height:"+height+"px;\"/></a>");
                		}
                		else{
                			out.println("<img src=\"<%=path %>/"+ad.getImg()+"\" style=\"width:"+width+"px;height:"+height+"px;\"/>");
                		}
                	}
                }
                else{
                	out.println(ad.getContent());
                }
            }
      
        } catch(Exception e) {

            throw new JspException(e.getMessage());

        }
        return SKIP_BODY;

    }

	//在结束标签之前调用，返回SKIP_PAGE跳过整个jsp页面后面的输出，返回EVAL_PAGE执行页面余下部分
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
    
    //生命周期结束时调用
    @Override
    public void release() {
        super.release();
        this.adid=null;
    }

	public String getAdid() {
		return adid;
	}

	public void setAdid(String adid) {
		this.adid = adid;
	}
}
