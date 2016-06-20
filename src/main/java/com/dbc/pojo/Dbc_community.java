/**
* @Project 源自 dbc
* @Title Dbc_community.java
* @Package com.dbc.pojo
* @Description 社区
* @author caihuajun
* @date 2015-07-24
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Dbc_community
* @Description 社区
* @author caihuajun
* @date 2015-07-24
*/
public class Dbc_community implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  communityname; //社区名称

private String  streetid; //街道id

private String  districtid; //地区id

private String  cityid; //城市id

private String provinceid; //省份id

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

public String getCommunityname() {
    return communityname;
}

public void setCommunityname(String communityname) {
    this.communityname = communityname;
}

public String getStreetid() {
    return streetid;
}

public void setStreetid(String streetid) {
    this.streetid = streetid;
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

public String getDistrictid() {
	return districtid;
}

public void setDistrictid(String districtid) {
	this.districtid = districtid;
}

public String getCityid() {
	return cityid;
}

public void setCityid(String cityid) {
	this.cityid = cityid;
}

public String getProvinceid() {
	return provinceid;
}

public void setProvinceid(String provinceid) {
	this.provinceid = provinceid;
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

