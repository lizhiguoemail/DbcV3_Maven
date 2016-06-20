/**
* @Project 源自 dbc
* @Title Webconfig.java
* @Package org.dbc.pojo
* @Description 网站基本参数设置
* @author caihuajun
* @date 2013-05-06
* @version v1.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Webconfig
* @Description 网站基本参数设置
* @author caihuajun
* @date 2013-05-06
*/
public class Dbc_webconfig implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  isopen; //网站开关

private String  closemsg; //网站关闭时显示的消息

private String  logo; //网站logo

private String  webname; //网站名称

private String  webnickname; //网站昵称

private String  webtitle; //网站标题

private String  webkey; //网站关键字

private String  webdescription; //网站描述

private String  weburl; //网址

private String  webfoot; //网站底部

private String topic; //方便接入新浪微博的话题直播

private String is_static_street; //是否使用静态街道

private String is_static_community; //是否使用静态社区

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

public String getIsopen() {
    return isopen;
}

public void setIsopen(String isopen) {
    this.isopen = isopen;
}

public String getClosemsg() {
    return closemsg;
}

public void setClosemsg(String closemsg) {
    this.closemsg = closemsg;
}

public String getLogo() {
    return logo;
}

public void setLogo(String logo) {
    this.logo = logo;
}

public String getWebname() {
    return webname;
}

public void setWebname(String webname) {
    this.webname = webname;
}

public String getWebnickname() {
    return webnickname;
}

public void setWebnickname(String webnickname) {
    this.webnickname = webnickname;
}

public String getWebtitle() {
    return webtitle;
}

public void setWebtitle(String webtitle) {
    this.webtitle = webtitle;
}

public String getWebkey() {
    return webkey;
}

public void setWebkey(String webkey) {
    this.webkey = webkey;
}

public String getWebdescription() {
    return webdescription;
}

public void setWebdescription(String webdescription) {
    this.webdescription = webdescription;
}

public String getWeburl() {
    return weburl;
}

public void setWeburl(String weburl) {
    this.weburl = weburl;
}

public String getWebfoot() {
    return webfoot;
}

public void setWebfoot(String webfoot) {
    this.webfoot = webfoot;
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

public String getUpdatedate() {
    return updatedate;
}

public void setUpdatedate(String updatedate) {
    this.updatedate = updatedate;
}

public String getDeletemark() {
    return deletemark;
}

public void setDeletemark(String deletemark) {
    this.deletemark = deletemark;
}

public String getCreateuser() {
	return createuser;
}

public void setCreateuser(String createuser) {
	this.createuser = createuser;
}

public String getUpdateuser() {
	return updateuser;
}

public void setUpdateuser(String updateuser) {
	this.updateuser = updateuser;
}

public String getTopic() {
	return topic;
}

public void setTopic(String topic) {
	this.topic = topic;
}

public String getIs_static_street() {
	return is_static_street;
}

public void setIs_static_street(String isStaticStreet) {
	is_static_street = isStaticStreet;
}

public String getIs_static_community() {
	return is_static_community;
}

public void setIs_static_community(String isStaticCommunity) {
	is_static_community = isStaticCommunity;
}

}

