/**
* @Project 源自 dbc
* @Title Base_type.java
* @Package com.dbc.pojo
* @Description 所有l类型的类别
* @author caihuajun
* @date 2013-12-04
* @version v1.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Base_type
* @Description 所有l类型的类别
* @author caihuajun
* @date 2013-12-04
*/
public class Dbc_type implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  type_name; //类别显示名

private String type_value; //类别值

private String  type_type; //类别标识

private String type_groupname; //组名

private String description; //备注

private Double sortcodebygroup; // 分组排序码

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

public String getType_name() {
    return type_name;
}

public void setType_name(String type_name) {
    this.type_name = type_name;
}

public String getType_type() {
    return type_type;
}

public void setType_type(String type_type) {
    this.type_type = type_type;
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

public String getType_groupname() {
	return type_groupname;
}

public void setType_groupname(String type_groupname) {
	this.type_groupname = type_groupname;
}

public Double getSortcodebygroup() {
	return sortcodebygroup;
}

public void setSortcodebygroup(Double sortcodebygroup) {
	this.sortcodebygroup = sortcodebygroup;
}

public String getType_value() {
	return type_value;
}

public void setType_value(String type_value) {
	this.type_value = type_value;
}

}

