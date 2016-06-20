/**
* @Project 源自 dbc
* @Title App.java
* @Package com.dbc.pojo
* @Description 应用表
* @author caihuajun
* @date 2013-10-29
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName App
* @Description 应用表
* @author caihuajun
* @date 2013-10-29
*/
public class Dbc_app implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  name; //名称

private String  apptype; //类型

private String  tuijiandengji; //推荐等级

private String  shouquanfangshi; //授权方式

private String  liulancishu; //浏览次数

private String  fenbianlv; //支持分辨率

private String  jianjie; //简介

private String  smallpic; //应用图标

private String  ylurl; //预览地址

private String  zuozhe; //作者

private String  slt1; //缩略图1

private String  slt2; //缩略图2

private String  slt3; //缩略图3

private String  slt4; //缩略图4

private String  slt5; //缩略图5

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

public String getApptype() {
    return apptype;
}

public void setApptype(String apptype) {
    this.apptype = apptype;
}

public String getTuijiandengji() {
    return tuijiandengji;
}

public void setTuijiandengji(String tuijiandengji) {
    this.tuijiandengji = tuijiandengji;
}

public String getShouquanfangshi() {
    return shouquanfangshi;
}

public void setShouquanfangshi(String shouquanfangshi) {
    this.shouquanfangshi = shouquanfangshi;
}

public String getLiulancishu() {
    return liulancishu;
}

public void setLiulancishu(String liulancishu) {
    this.liulancishu = liulancishu;
}

public String getFenbianlv() {
    return fenbianlv;
}

public void setFenbianlv(String fenbianlv) {
    this.fenbianlv = fenbianlv;
}

public String getJianjie() {
    return jianjie;
}

public void setJianjie(String jianjie) {
    this.jianjie = jianjie;
}

public String getSmallpic() {
    return smallpic;
}

public void setSmallpic(String smallpic) {
    this.smallpic = smallpic;
}

public String getYlurl() {
    return ylurl;
}

public void setYlurl(String ylurl) {
    this.ylurl = ylurl;
}

public String getZuozhe() {
    return zuozhe;
}

public void setZuozhe(String zuozhe) {
    this.zuozhe = zuozhe;
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
public String getSlt1() {
	return slt1;
}

public void setSlt1(String slt1) {
	this.slt1 = slt1;
}

public String getSlt2() {
	return slt2;
}

public void setSlt2(String slt2) {
	this.slt2 = slt2;
}

public String getSlt3() {
	return slt3;
}

public void setSlt3(String slt3) {
	this.slt3 = slt3;
}

public String getSlt4() {
	return slt4;
}

public void setSlt4(String slt4) {
	this.slt4 = slt4;
}

public String getSlt5() {
	return slt5;
}

public void setSlt5(String slt5) {
	this.slt5 = slt5;
}

}

