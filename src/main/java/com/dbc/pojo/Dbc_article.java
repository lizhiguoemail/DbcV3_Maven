/**
* @Project 源自 dbc
* @Title Article.java
* @Package com.dbc.pojo
* @Description 文章
* @author caihuajun
* @date 2013-11-26
* @version v2.0
*/
package com.dbc.pojo;

import java.io.Serializable;

/**
* @ClassName Article
* @Description 文章
* @author caihuajun
* @date 2013-11-26
*/
public class Dbc_article implements Serializable{

private static final long serialVersionUID = 1L;

private String id;  //主键

private String  title; //文章标题

private String  treeid; //栏目id

private String  content; //文章内容

private String  source; //文章来源

private String  keywords; //关键字

private String  filepath; //附件位置

private String  istop; //是否置顶

private String  isindex; //是否首页显示

private Long  readnum; //阅读次数

private String  author; //作者

private String  ishasttachment; //是否存在附件

private String  ishot; //是否为热点

private String  ishidden; //是否隐藏

private String  isrecommend; //是否热荐

private String  isnew; //是否是新文章

private String  article_flag; //文章标识

private String  title_pic; //文章标题图片

private String  summary; //摘要

private String  isshowsummary; //是否显示摘要

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

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getTreeid() {
    return treeid;
}

public void setTreeid(String treeid) {
    this.treeid = treeid;
}

public String getContent() {
    return content;
}

public void setContent(String content) {
    this.content = content;
}

public String getSource() {
    return source;
}

public void setSource(String source) {
    this.source = source;
}

public String getKeywords() {
    return keywords;
}

public void setKeywords(String keywords) {
    this.keywords = keywords;
}

public String getFilepath() {
    return filepath;
}

public void setFilepath(String filepath) {
    this.filepath = filepath;
}

public String getIstop() {
    return istop;
}

public void setIstop(String istop) {
    this.istop = istop;
}

public String getIsindex() {
    return isindex;
}

public void setIsindex(String isindex) {
    this.isindex = isindex;
}


public Long getReadnum() {
	return readnum;
}

public void setReadnum(Long readnum) {
	this.readnum = readnum;
}

public String getAuthor() {
    return author;
}

public void setAuthor(String author) {
    this.author = author;
}

public String getIshasttachment() {
    return ishasttachment;
}

public void setIshasttachment(String ishasttachment) {
    this.ishasttachment = ishasttachment;
}

public String getIshot() {
    return ishot;
}

public void setIshot(String ishot) {
    this.ishot = ishot;
}

public String getIshidden() {
    return ishidden;
}

public void setIshidden(String ishidden) {
    this.ishidden = ishidden;
}

public String getIsrecommend() {
    return isrecommend;
}

public void setIsrecommend(String isrecommend) {
    this.isrecommend = isrecommend;
}

public String getIsnew() {
    return isnew;
}

public void setIsnew(String isnew) {
    this.isnew = isnew;
}

public String getArticle_flag() {
    return article_flag;
}

public void setArticle_flag(String article_flag) {
    this.article_flag = article_flag;
}

public String getTitle_pic() {
    return title_pic;
}

public void setTitle_pic(String title_pic) {
    this.title_pic = title_pic;
}

public String getSummary() {
    return summary;
}

public void setSummary(String summary) {
    this.summary = summary;
}

public String getIsshowsummary() {
    return isshowsummary;
}

public void setIsshowsummary(String isshowsummary) {
    this.isshowsummary = isshowsummary;
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

