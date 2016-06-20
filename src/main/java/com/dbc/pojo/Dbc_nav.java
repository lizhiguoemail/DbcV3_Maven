/**
* @Project 源自 dbc
* @Title Nav.java
* @Package com.dbc.pojo.pojo
* @Description 导航栏
* @author caihuajun
* @date 2013-10-18
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Nav
* @Description 导航栏
* @author caihuajun
* @date 2013-10-18
*/
public class Dbc_nav implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  title; //标题

private String  url; //自定义链接 http://

private String  tip; //浮动标记

private String  hide; //是否隐藏

private String  type; //类型

private String  auto; //auto

private String  target; //跳转方式

private String  custom; //custom

private String  nav_act; //模块action

private String  nav_mod; //方法

private String  tag; //标记

private String  pid; //父id

private String  alt; //alt

private String  sys; //是否是系统模块

private String  plugin; //是否是插件应用

private String state; //无 new hot

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

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getUrl() {
    return url;
}

public void setUrl(String url) {
    this.url = url;
}

public String getTip() {
    return tip;
}

public void setTip(String tip) {
    this.tip = tip;
}

public String getHide() {
    return hide;
}

public void setHide(String hide) {
    this.hide = hide;
}

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public String getAuto() {
    return auto;
}

public void setAuto(String auto) {
    this.auto = auto;
}

public String getTarget() {
    return target;
}

public void setTarget(String target) {
    this.target = target;
}

public String getCustom() {
    return custom;
}

public void setCustom(String custom) {
    this.custom = custom;
}


public String getNav_act() {
	return nav_act;
}

public void setNav_act(String nav_act) {
	this.nav_act = nav_act;
}

public String getNav_mod() {
	return nav_mod;
}

public void setNav_mod(String nav_mod) {
	this.nav_mod = nav_mod;
}

public String getTag() {
    return tag;
}

public void setTag(String tag) {
    this.tag = tag;
}

public String getPid() {
    return pid;
}

public void setPid(String pid) {
    this.pid = pid;
}

public String getAlt() {
    return alt;
}

public void setAlt(String alt) {
    this.alt = alt;
}

public String getSys() {
    return sys;
}

public void setSys(String sys) {
    this.sys = sys;
}

public String getPlugin() {
    return plugin;
}

public void setPlugin(String plugin) {
    this.plugin = plugin;
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

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
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



}

