/**
* @Project 源自 dbc
* @Title Dbc_ad.java
* @Package com.dbc.pojo
* @Description 广告
* @author caihuajun
* @date 2015-05-12
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;


/**
* @ClassName Dbc_ad
* @Description 广告
* @author caihuajun
* @date 2015-05-12
*/
public class Dbc_ad implements Serializable {

private static final long serialVersionUID = 1L;

private Integer id;  //主键

private String  tag; //广告标识

private String  adtype; //广告位置

private String  title; //广告说明

private String  moshi; //模式选择

private String  img; //图片

private String  height; //高度

private String  width; //宽度

private String  bgcolor; //背景色

private String  link; //链接

private String  content; //自定义代码

private String  diaoyongfangshi; //调用方式

private String  edate; //到期时间

private String description; //备注

private Double sortcode; // 排序码

private String createdate; //创建时间

private String createuser; //创建人

private String updatedate; //修改时间

private String updateuser; //修改人

private String deletemark="0"; //删除标识

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getTag() {
    return tag;
}

public void setTag(String tag) {
    this.tag = tag;
}

public String getAdtype() {
    return adtype;
}

public void setAdtype(String adtype) {
    this.adtype = adtype;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getMoshi() {
    return moshi;
}

public void setMoshi(String moshi) {
    this.moshi = moshi;
}public String getDiaoyongfangshi() {
	return diaoyongfangshi;
}

public void setDiaoyongfangshi(String diaoyongfangshi) {
	this.diaoyongfangshi = diaoyongfangshi;
}


public String getImg() {
    return img;
}

public void setImg(String img) {
    this.img = img;
}

public String getHeight() {
    return height;
}

public void setHeight(String height) {
    this.height = height;
}

public String getWidth() {
    return width;
}

public void setWidth(String width) {
    this.width = width;
}

public String getBgcolor() {
    return bgcolor;
}

public void setBgcolor(String bgcolor) {
    this.bgcolor = bgcolor;
}

public String getLink() {
    return link;
}

public void setLink(String link) {
    this.link = link;
}

public String getContent() {
    return content;
}

public void setContent(String content) {
    this.content = content;
}

public String getEdate() {
    return edate;
}

public void setEdate(String edate) {
    this.edate = edate;
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

