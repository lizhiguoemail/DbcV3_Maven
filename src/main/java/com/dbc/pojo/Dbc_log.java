/**
 * @Project dbc
 * @Title Log.java
 * @Package org.dbc.pojo;
 * @Description 日志表
 * @author caihuajun
 * @date 2011-06-02
 * @version v1.0
 */
package com.dbc.pojo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.dbc.util.Dbcutil;
import com.dbc.util.ip.IPSeeker;
import com.opensymphony.xwork2.ActionContext;

/**
 * @ClassName UserBean
 * @Description 用户属性类
 * @author caihuajun
 * @date 2011-05-31
 */
public class Dbc_log {
	
	private String id; //主键
	
	private String userid; //操作人ID
	
	private String username; //操作人姓名
	
	private String logtype; //日志类别
	
	private String processid; //服务
	
	private String processname; //服务名称
	
	private String methodname; //操作名称
	
	private String parameters; //操作参数
	
	private String ipaddress; //IP地址
	
	private String zone;
	
	private String detailAddress;  
	
	private String weburl; //网址
	
	private String description; //备注
	
	private String createdate; //创建时间

	private String createuser; //创建人

	private String updatedate; //修改时间

	private String updateuser; //修改人
	
	private Double sortcode; //排序码

	private String deletemark="0"; //删除标记
	
	public Dbc_log(){
		
	}
	
	public Dbc_log(Dbc_userinfo userinfo,String ipaddress,String processname,String methodname,String description) {
		String nowdate=Dbcutil.getnowdateString("yyyy-MM-dd HH:mm:ss");
		if(userinfo!=null)
		{
			this.userid=userinfo.getId();
			this.username=userinfo.getUsername();
		}
		this.processname=processname;
		this.methodname=methodname;
		this.ipaddress=ipaddress;
		this.description=description;
		this.createdate=nowdate;
		if("0:0:0:0:0:0:0:1".equals(ipaddress)){
			this.zone="本地";
			this.detailAddress="本机";
		}
		else if("0:0:0:0:0:0:0:1%0".equals(ipaddress)){
			this.zone="本地";
			this.detailAddress="本机";
		}
		else if(ipaddress.length()>16)
		{
			this.zone="未知v6IP地址";
			this.detailAddress="未知v6IP地址";
		}
		else
		{
			try {
				String separ = File.separator;
				ActionContext ac = ActionContext.getContext();
				HttpServletRequest request =(HttpServletRequest)ac.get(ServletActionContext.HTTP_REQUEST);
				String path2=request.getSession().getServletContext().getRealPath(separ);
				path2=path2+separ+"util";
				String filename="qqwry.dat";
				IPSeeker seeker = new IPSeeker(filename,path2);
				this.zone=seeker.getCountry(ipaddress);
				this.detailAddress=seeker.getArea(ipaddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public Double getSortcode() {
		return sortcode;
	}

	public void setSortcode(Double sortcode) {
		this.sortcode = sortcode;
	}

	public String getDeletemark() {
		return deletemark;
	}

	public void setDeletemark(String deletemark) {
		this.deletemark = deletemark;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
}