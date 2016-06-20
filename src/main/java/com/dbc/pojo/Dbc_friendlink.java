/**
* @Project 源自 dbc
* @Title Friendlink.java
* @Package com.dbc.pojo
* @Description 友情链接
* @author caihuajun
* @date 2014-01-23
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Friendlink
* @Description 友情链接
* @author caihuajun
* @date 2014-01-23
*/
public class Dbc_friendlink implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  name; //链接名称

private String  linkurl; //链接地址

private String  linktype; //链接类型

private String  picurl; //链接图片

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

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getLinkurl() {
    return linkurl;
}

public void setLinkurl(String linkurl) {
    this.linkurl = linkurl;
}

public String getLinktype() {
    return linktype;
}

public void setLinktype(String linktype) {
    this.linktype = linktype;
}

public String getPicurl() {
    return picurl;
}

public void setPicurl(String picurl) {
    this.picurl = picurl;
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

