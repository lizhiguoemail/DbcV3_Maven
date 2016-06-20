/**
* @Project 源自 dbc
* @Title Dbc_token.java
* @Package com.dbc.pojo
* @Description token类
* @author caihuajun
* @date 2015-08-06
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Dbc_token
* @Description token类
* @author caihuajun
* @date 2015-08-06
*/
public class Dbc_token implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  tokenvalue; //秘钥值

private String  objid; //对象id

private Long  optime; //操作时间

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

public String getTokenvalue() {
    return tokenvalue;
}

public void setTokenvalue(String tokenvalue) {
    this.tokenvalue = tokenvalue;
}

public String getObjid() {
    return objid;
}

public void setObjid(String objid) {
    this.objid = objid;
}

public Long getOptime() {
    return optime;
}

public void setOptime(Long optime) {
    this.optime = optime;
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

