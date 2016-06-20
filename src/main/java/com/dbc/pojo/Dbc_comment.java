/**
* @Project 源自 dbc
* @Title Base_comment.java
* @Package com.dbc.pojo
* @Description 评论通用类
* @author caihuajun
* @date 2014-03-21
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Base_comment
* @Description 评论通用类
* @author caihuajun
* @date 2014-03-21
*/
public class Dbc_comment implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String source_userid; //来源用户id

private String  objid; //评论对象id

private String  objname; //评论对象名称

private String  isreg; //是否是注册用户

private Integer  floor; //楼层

private String  type; //评论类型

private String  email; //邮箱

private String  gerenzhuye; //个人主页

private String  neirong; //评论内容

private String  uname; //用户名

private String uid; //用户id

private String  ip; //ip

private String ipzone; //ip地域

private String  ipaddress; //ip详细地址

private String isguolv;  //是否被过滤

private String iszuijia;  //是否是最佳评论

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

public String getObjid() {
    return objid;
}

public void setObjid(String objid) {
    this.objid = objid;
}

public String getObjname() {
    return objname;
}

public String getUid() {
	return uid;
}

public void setUid(String uid) {
	this.uid = uid;
}

public void setObjname(String objname) {
    this.objname = objname;
}

public String getIsreg() {
    return isreg;
}

public void setIsreg(String isreg) {
    this.isreg = isreg;
}


public Integer getFloor() {
	return floor;
}

public void setFloor(Integer floor) {
	this.floor = floor;
}

public String getIszuijia() {
	return iszuijia;
}

public void setIszuijia(String iszuijia) {
	this.iszuijia = iszuijia;
}

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getGerenzhuye() {
    return gerenzhuye;
}

public void setGerenzhuye(String gerenzhuye) {
    this.gerenzhuye = gerenzhuye;
}

public String getNeirong() {
    return neirong;
}

public void setNeirong(String neirong) {
    this.neirong = neirong;
}

public String getUname() {
    return uname;
}

public void setUname(String uname) {
    this.uname = uname;
}

public String getIp() {
    return ip;
}

public void setIp(String ip) {
    this.ip = ip;
}

public String getIpaddress() {
    return ipaddress;
}

public void setIpaddress(String ipaddress) {
    this.ipaddress = ipaddress;
}

public String getIpzone() {
	return ipzone;
}

public void setIpzone(String ipzone) {
	this.ipzone = ipzone;
}

public String getIsguolv() {
	return isguolv;
}

public void setIsguolv(String isguolv) {
	this.isguolv = isguolv;
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

public String getSource_userid() {
	return source_userid;
}

public void setSource_userid(String sourceUserid) {
	source_userid = sourceUserid;
}



}

