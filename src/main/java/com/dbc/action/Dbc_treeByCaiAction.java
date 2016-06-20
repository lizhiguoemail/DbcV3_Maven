package com.dbc.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.dbc.dao.Dbc_treeByCaiDao;
import com.dbc.pojo.Dbc_treeByCai;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.pojo.Dbc_webconfig;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_treeByCaiService;
import com.dbc.service.Dbc_webconfigService;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.opensymphony.xwork2.ModelDriven;
import com.singlee.sda.security.SecurityUtils;

public class Dbc_treeByCaiAction extends BaseAction implements ModelDriven{
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String methode;

	private Dbc_webconfig webconfig;
	
	public String execute(){
		String returnstr="";
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		if("listtree".equals(methode)){
			returnstr=this.listtree();
		}
		else if("listtreeforsucai".equals(methode)){
			returnstr=this.listtreeforsucai();
		}
		else if("toAddsonnode".equals(methode)){
			returnstr=this.toAddsonnode();
		}
		else if("addnode".equals(methode)){
			returnstr=this.addnode();
		}
		else if("addroot".equals(methode)){
			returnstr=this.addroot();
		}
		else if("toUpdatenode".equals(methode)){
			returnstr=this.toUpdatenode();
		}
		else if("update".equals(methode)){
			returnstr=this.update();
		}
		else if("deletenode".equals(methode)){
			returnstr=this.deletenode();
		}
		else if("paixu".equals(methode)){
			returnstr=this.paixu();
		}
		else if("listtreeEXT".equals(methode)){
			returnstr=this.listtreeEXT();
		}
		return returnstr;
	}
	
	/**
	 * @Title listtree
	 * @Description 列表显示类别  
	 * @return String
	 * @throws
	 */	
	@SuppressWarnings("unchecked")
	public String listtree() {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
		//String treetype="tree_sucai";
		String treetype=request.getParameter("treetype");
		List treelist=treebycaiservice.selBytreetype(treetype);
		request.setAttribute("treelist",treelist);
		request.setAttribute("treetype", treetype);
		return "treelist";	
	}
	
	/**
	 * @Title listtreeforsucai
	 * @Description 在素材页面列表显示类别  
	 * @return String
	 * @throws
	 */	
	@SuppressWarnings("unchecked")
	public String listtreeforsucai() {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
		String treetype=request.getParameter("treetype");
		List treelist=treebycaiservice.selBytreetype(treetype);
		request.setAttribute("treelist",treelist);
		request.setAttribute("treetype", treetype);
		return "tree";	
	}
	
	 /**
	 * @Title toAddsonnode
	 * @Description 为类别树添加子类别
	 * @return String
	 */
	public String toAddsonnode(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String pname=request.getParameter("pname");
		request.setAttribute("pname", pname);
		return "addsonnode";
	}
	
	/**
	 * @Title addnode
	 * @Description  添加类别
	 * @return String
	 * @throws
	 */	
	public String addnode(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String treetype=request.getParameter("treetype");
		String pid=request.getParameter("pid");
		String nodename=request.getParameter("nodename");
		String description=request.getParameter("description");
		Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
		Dbc_treeByCai ptree=(Dbc_treeByCai) treebycaiservice.selByOid(Dbc_treeByCai.class, pid);
		List slist=treebycaiservice.selbypath(treetype, pid, "desc");
		Dbc_treeByCai maxtree=(Dbc_treeByCai) slist.get(0);
		double sortcode=maxtree.getSortcode()+1;
		Dbc_treeByCai treeByCai=new Dbc_treeByCai();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2=new SimpleDateFormat("yyyyMMddhhmmss");
		Date date=new Date();
		treeByCai.setId(sf2.format(date));
		treeByCai.setNodename(nodename);
		treeByCai.setDescription(description);
		treeByCai.setTreetype(treetype);
		treeByCai.setDeep(""+(Integer.parseInt(ptree.getDeep())+1));
		treeByCai.setParentid(ptree.getId());
		treeByCai.setParentpath(ptree.getParentpath()+ptree.getId()+"/");
		treeByCai.setCreatedate(sf.format(date));
		treeByCai.setSortcode(sortcode);
		treeByCai.setChildnum(0);
		Integer childnum=ptree.getChildnum();
		if(childnum!=null)
		{
		ptree.setChildnum(childnum);
		}
		treebycaiservice.updatefortianjia(treetype,sortcode-1);
		treebycaiservice.saveObject(treeByCai);
		treebycaiservice.updateObject(ptree);
		 request.setAttribute("paixutree", treeByCai);
		 String str=request.getContextPath()+"/dbc_treebycai.action?methode=listtree&treetype="+treetype;
		try {
			response.sendRedirect(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Title addroot
	 * @Description  添加根节点
	 * @return String
	 * @throws
	 */	
	public String addroot(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String treetype=request.getParameter("treetype");
		String rootname=request.getParameter("rootname");
		Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
		Dbc_treeByCai treeByCai=new Dbc_treeByCai();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2=new SimpleDateFormat("yyyyMMddhhmmss");
		Date date=new Date();
		treeByCai.setId(sf2.format(date));
		treeByCai.setNodename(rootname);
		treeByCai.setTreetype(treetype);
		treeByCai.setDeep("0");
		treeByCai.setSortcode(Double.parseDouble("0"));
		treeByCai.setParentpath("/-1/");
		treeByCai.setCreatedate(sf.format(date));
		treeByCai.setChildnum(0);
		treebycaiservice.saveObject(treeByCai);
		String str=request.getContextPath()+"/dbc_treebycai.action?methode=listtree&treetype="+treetype;
		try {
			response.sendRedirect(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /**
	 * @Title toUpdatesonnode
	 * @Description 修改类别名称
	 * @return String
	 * @throws
	 */
	public String toUpdatenode(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
		String treeid=request.getParameter("treeid");
		Dbc_treeByCai treeByCai=(Dbc_treeByCai)treebycaiservice.selByOid(Dbc_treeByCai.class,treeid);
		String nodename=treeByCai.getNodename();
		String description=treeByCai.getDescription();
		request.setAttribute("nodename", nodename);
		request.setAttribute("description", description);
		return "updatenode";
	}
	
	/**
	 * @Title update
	 * @Description  修改类别
	 * @return String
	 */	
	public String update(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String treetype=request.getParameter("treetype");
		Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
		String treeid=request.getParameter("treeid");
		Dbc_treeByCai treeByCai=(Dbc_treeByCai)treebycaiservice.selByOid(Dbc_treeByCai.class,treeid);
		String nodename=request.getParameter("nodename");
		String description=request.getParameter("description");
		treeByCai.setNodename(nodename);
		treeByCai.setDescription(description);
		treebycaiservice.updateObject(treeByCai);
		String str=request.getContextPath()+"/dbc_treebycai.action?methode=listtree&treetype="+treetype;
		try {
			response.sendRedirect(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Title deletenode
	 * @Description  删除节点
	 * @return String
	 */	
	public String deletenode(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String oid=request.getParameter("oid");
		String treetype=request.getParameter("treetype");
		Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
		treebycaiservice.setbypath(oid, "deletemark","1");
		String str=request.getContextPath()+"/dbc_treebycai.action?methode=listtree&treetype="+treetype;
		try {
			response.sendRedirect(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Title paixu
	 * @Description  排序
	 * @return String
	 */	
	public String paixu(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
			String treetype=request.getParameter("treetype");
			Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
			Dbc_treeByCai treeByCai=(Dbc_treeByCai)treebycaiservice.selByOid(Dbc_treeByCai.class,request.getParameter("oid"));
			String t=request.getParameter("t");
			if("top".equals(t)) //当排序为置顶
			{
				String deep=treeByCai.getDeep(); //获得级数
				List treelist=treebycaiservice.selByDeepPid(treetype,deep,treeByCai.getParentid(),"asc"); //获得同父节点同级的节点列表
				Dbc_treeByCai toptree=new Dbc_treeByCai();
				if(treelist.size()>0){
					toptree=(Dbc_treeByCai) treelist.get(0); //获得列表最小排序码节点
				}
				else{
					toptree=treeByCai;
				}
				if(!toptree.getId().equals(treeByCai.getId())) //如果最小节点不等于此节点
				{
					List yidonglist=treebycaiservice.selbypath(treetype, treeByCai.getId(), "asc");//获得与此节点相关的节点，包括本身
					double topsort=toptree.getSortcode();
					Dbc_treeByCai maxtree=(Dbc_treeByCai) yidonglist.get(yidonglist.size()-1);
					double cha=maxtree.getSortcode()-topsort;
					treebycaiservice.setorderbycha(treetype,toptree.getSortcode(),">=",yidonglist, "+", cha+1);
				}
			}
			else if("buttom".equals(t))
			{
				String deep=treeByCai.getDeep(); //获得级数
				List treelist=treebycaiservice.selByDeepPid(treetype,deep,treeByCai.getParentid(),"desc");//获得同父节点同级的节点列表
				Dbc_treeByCai buttomtree=new Dbc_treeByCai();//获得列表最大排序码节点
				if(treelist.size()>0){
					buttomtree=(Dbc_treeByCai) treelist.get(0); //获得列表最小排序码节点
				}
				else{
					buttomtree=treeByCai;
				}
				if(!buttomtree.getId().equals(treeByCai.getId())) //如果最大节点不等于此节点
				{
					List yidonglist=treebycaiservice.selbypath(treetype, treeByCai.getId(),"asc");
					List zuidilist=treebycaiservice.selbypath(treetype, buttomtree.getId(), "desc");
					Dbc_treeByCai buttomtree2=(Dbc_treeByCai) zuidilist.get(0);
					Dbc_treeByCai topyidong=(Dbc_treeByCai) yidonglist.get(0);
					double cha=buttomtree2.getSortcode()-topyidong.getSortcode();
					treebycaiservice.setorderbycha(treetype,buttomtree2.getSortcode(),"<=",yidonglist, "-", cha+1);
				}
			}
			else if("up".equals(t))
			{
				String deep=treeByCai.getDeep(); //获得级数
				List treelist=treebycaiservice.selByPidPre(treetype,deep,treeByCai.getSortcode(),"<",treeByCai.getParentid(),"desc"); //获得同父节点同级的节点列表
				if(treelist.size()>0) //如果最小节点不等于此节点
				{
					Dbc_treeByCai pretree=(Dbc_treeByCai)treelist.get(0); //获得列表最小排序码节点
					List yidonglist=treebycaiservice.selbypath(treetype,treeByCai.getId(),"asc");//获得与此节点相关的节点，包括本身
					double topsort=pretree.getSortcode();
					Dbc_treeByCai maxtree=(Dbc_treeByCai) yidonglist.get(yidonglist.size()-1);
					double cha=maxtree.getSortcode()-topsort;
					treebycaiservice.setorderbycha(treetype,pretree.getSortcode(),">=",yidonglist, "+", cha+1);
				}
			}
			else if("down".equals(t))
			{
				String deep=treeByCai.getDeep(); //获得级数
				List treelist=treebycaiservice.selByPidPre(treetype,deep,treeByCai.getSortcode(),">",treeByCai.getParentid(),"asc"); //获得同父节点同级的节点列表
				if(treelist.size()>0) //如果最大节点不等于此节点
				{
					Dbc_treeByCai nexttree=(Dbc_treeByCai)treelist.get(0);//获得列表最大排序码节点
					List yidonglist=treebycaiservice.selbypath(treetype, treeByCai.getId(), "asc");
					List zuidilist=treebycaiservice.selbypath(treetype, nexttree.getId(), "desc");
					Dbc_treeByCai buttomtree2=(Dbc_treeByCai) zuidilist.get(0);
					Dbc_treeByCai topyidong=(Dbc_treeByCai) yidonglist.get(0);
					double cha=buttomtree2.getSortcode()-topyidong.getSortcode();
					treebycaiservice.setorderbycha(treetype,buttomtree2.getSortcode(),"<=",yidonglist, "-",cha+1);
				}
			}
			request.setAttribute("paixutree", treeByCai);
			request.setAttribute("treetype", treetype);
			return this.listtree();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	 /**
     * @Title listtreeEXT
     * @Description 查询类别
     * @return String
     */
    public String listtreeEXT() {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/xml;charset=utf-8");
			String treetype=request.getParameter("treetype");
			String deep=request.getParameter("deep");
			if(deep==null||"".equals(deep)){
				deep="1";
			}
			Dbc_treeByCaiService treebycaiservice=(Dbc_treeByCaiService)super.getInstence("dbc_treebycaiservice");
	    	List list=treebycaiservice.selBytreetype(treetype);
	    	if(list.size()>0){
	    		 String json="{\"success\":true,\"totalCount\":"+list.size();
		         for(int i=0;i<list.size();i++){
		        	 Dbc_treeByCai treebc=(Dbc_treeByCai)list.get(i);
		            if(i==0){
		               json=json+",\"rows\":[{\"id\":'"+treebc.getId()+"'";
		            }
		            else{
		               json=json+",{\"id\":'"+treebc.getId()+"'";
		            }
		            String str="";
		            int num=Integer.parseInt(treebc.getDeep());
		            for (int j = 1; j <= num; j++) {
		            	str+="—";
					}
		              json=json+",\"name\":'"+str+treebc.getNodename()+"'";
		              json=json+",\"sortcode\":2}";
		           }
		           if(list.size()>0){
		             json=json+"]";
		           }
		           json=json+"}";
		           response.getWriter().print(json);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

    public String getMethode() {
   	 	return methode;
	 }
	
	 public void setMethode(String methode) {
	 	this.methode = methode;
	 }

	public Dbc_webconfig getWebconfig() {
		return webconfig;
	}

	public void setWebconfig(Dbc_webconfig webconfig) {
		this.webconfig = webconfig;
	}

	public Object getModel() {
		if(webconfig == null){
			webconfig = new Dbc_webconfig();
	       }
	    return webconfig;
	}
	
}
