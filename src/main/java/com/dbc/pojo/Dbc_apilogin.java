/**
* @Project 源自 dbc
* @Title Apilogin.java
* @Package com.dbc.pojo
* @Description 等三方登录
* @author caihuajun
* @date 2013-11-13
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Apilogin
* @Description 等三方登录
* @author caihuajun
* @date 2013-11-13
*/
public class Dbc_apilogin implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  userid; //用户id

private String  web_id; //对应网站的id

private String  web_name; //对应网站的名称

private String  web_belong; //所属第三方

private String description; //备注

private Double sortcode; // 排序码

private String createdate; //创建时间

private String createuser; //创建人

private String updatedate; //修改时间

private String updateuser; //修改人

private String deletemark="0"; //删除标识

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

public String getWeb_id() {
	return web_id;
}

public void setWeb_id(String web_id) {
	this.web_id = web_id;
}

public String getWeb_name() {
	return web_name;
}

public void setWeb_name(String web_name) {
	this.web_name = web_name;
}

public String getWeb_belong() {
	return web_belong;
}

public void setWeb_belong(String web_belong) {
	this.web_belong = web_belong;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public Double getSortcode() {
	return sortcode;
}

public void setSortcode(Double sortcode) {
	this.sortcode = sortcode;
}

public String getCreatedate() {
	return createdate;
}

public void setCreatedate(String createdate) {
	this.createdate = createdate;
}

public String getCreateuser() {
	return createuser;
}

public void setCreateuser(String createuser) {
	this.createuser = createuser;
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

public String getDeletemark() {
	return deletemark;
}

public void setDeletemark(String deletemark) {
	this.deletemark = deletemark;
}



}

