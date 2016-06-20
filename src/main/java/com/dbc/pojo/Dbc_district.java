/**
* @Project 源自 dbc
* @Title Dbc_district.java
* @Package com.dbc.pojo
* @Description 地区
* @author caihuajun
* @date 2015-04-28
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;
import java.util.List;

/**
* @ClassName Dbc_district
* @Description 地区
* @author caihuajun
* @date 2015-04-28
*/
public class Dbc_district implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  districtname; //地区名称

private String  cityid; //城市id

private String provinceid; //身份id

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

public String getDistrictname() {
    return districtname;
}

public void setDistrictname(String districtname) {
    this.districtname = districtname;
}

public String getCityid() {
    return cityid;
}

public void setCityid(String cityid) {
    this.cityid = cityid;
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

public String getProvinceid() {
	return provinceid;
}

public void setProvinceid(String provinceid) {
	this.provinceid = provinceid;
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

