/**
* @Project 源自 dbc
* @Title Dbc_permit.java
* @Package com.dbc.pojo
* @Description 权限
* @author caihuajun
* @date 2015-05-14
* @version V2.0
*/
package com.dbc.pojo;

import java.io.Serializable;
import java.util.List;

/**
* @ClassName Dbc_permit
* @Description 权限
* @author caihuajun
* @date 2015-05-14
*/
public class Dbc_permit implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  permit_name; //权限名称

private String  permit_action; //对应action

private String  permit_methode; //对应methode

private String  action_methode; //权限标识

private String isopen; //是否开启   开启\不开启

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

public String getPermit_name() {
    return permit_name;
}

public void setPermit_name(String permit_name) {
    this.permit_name = permit_name;
}

public String getPermit_action() {
    return permit_action;
}

public void setPermit_action(String permit_action) {
    this.permit_action = permit_action;
}

public String getPermit_methode() {
    return permit_methode;
}

public void setPermit_methode(String permit_methode) {
    this.permit_methode = permit_methode;
}

public String getAction_methode() {
    return action_methode;
}


public void setAction_methode(String action_methode) {
    this.action_methode = action_methode;
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

public String getIsopen() {
	return isopen;
}

public void setIsopen(String isopen) {
	this.isopen = isopen;
}


}

