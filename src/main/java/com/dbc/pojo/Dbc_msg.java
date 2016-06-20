/**
* @Project 源自 dbc
* @Title Dbc_msg.java
* @Package com.dbc.pojo
* @Description 消息表
* @author caihuajun
* @date 2014-12-23
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Dbc_msg
* @Description 消息表
* @author caihuajun
* @date 2014-12-23
*/
public class Dbc_msg implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  receiveid; //收件人id

private String  receiveusername; //收件人名称

private String  sendid; //发件人id

private String  sendusername; //发件人名称

private String  title; //标题

private String  content; //内容

private String  opentime; //打开时间

private String  state; //状态

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

public String getReceiveid() {
    return receiveid;
}

public void setReceiveid(String receiveid) {
    this.receiveid = receiveid;
}

public String getReceiveusername() {
    return receiveusername;
}

public void setReceiveusername(String receiveusername) {
    this.receiveusername = receiveusername;
}

public String getSendid() {
    return sendid;
}

public void setSendid(String sendid) {
    this.sendid = sendid;
}

public String getSendusername() {
    return sendusername;
}

public void setSendusername(String sendusername) {
    this.sendusername = sendusername;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getContent() {
    return content;
}

public void setContent(String content) {
    this.content = content;
}

public String getOpentime() {
    return opentime;
}

public void setOpentime(String opentime) {
    this.opentime = opentime;
}

public String getState() {
    return state;
}

public void setState(String state) {
    this.state = state;
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

