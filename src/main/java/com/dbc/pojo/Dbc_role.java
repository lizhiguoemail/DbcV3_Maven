/**
* @Project 源自 dbc
* @Title Base_role.java
* @Package com.dbc.pojo
* @Description 角色
* @author caihuajun
* @date 2014-02-17
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;
import java.util.List;

/**
* @ClassName Base_role
* @Description 角色
* @author caihuajun
* @date 2014-02-17
*/
public class Dbc_role implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  rolename; //角色名

private String  category; //角色分类

private List  permits; //角色对应的权限

private String issysadmin; //是否是系统管理员  是/否

private String isbbsadmin; //是否是论坛管理员  是/否

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

public String getRolename() {
    return rolename;
}

public void setRolename(String rolename) {
    this.rolename = rolename;
}

public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public List getPermits() {
	return permits;
}

public void setPermits(List permits) {
	this.permits = permits;
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

public String getIssysadmin() {
	return issysadmin;
}

public void setIssysadmin(String issysadmin) {
	this.issysadmin = issysadmin;
}

public String getIsbbsadmin() {
	return isbbsadmin;
}

public void setIsbbsadmin(String isbbsadmin) {
	this.isbbsadmin = isbbsadmin;
}


}

