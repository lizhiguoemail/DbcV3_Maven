/**
* @Project 源自 dbc
* @Title Dbc_slides.java
* @Package com.dbc.pojo
* @Description 幻灯片
* @author caihuajun
* @date 2015-06-23
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Dbc_slides
* @Description 幻灯片
* @author caihuajun
* @date 2015-06-23
*/
public class Dbc_slides implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  slides_title; //标题

private String  slides_type; //分类

private String  slides_img; //图片

private String  slides_link; //链接

private String  ishidden; //是否隐藏

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

public String getSlides_title() {
    return slides_title;
}

public void setSlides_title(String slides_title) {
    this.slides_title = slides_title;
}

public String getSlides_type() {
    return slides_type;
}

public void setSlides_type(String slides_type) {
    this.slides_type = slides_type;
}

public String getSlides_img() {
    return slides_img;
}

public void setSlides_img(String slides_img) {
    this.slides_img = slides_img;
}

public String getSlides_link() {
    return slides_link;
}

public void setSlides_link(String slides_link) {
    this.slides_link = slides_link;
}

public String getIshidden() {
    return ishidden;
}

public void setIshidden(String ishidden) {
    this.ishidden = ishidden;
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

