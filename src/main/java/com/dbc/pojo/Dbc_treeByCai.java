/**
 * @Project dbc
 * @Title TreeByCai.java
 * @Package org.dbc.pojo;
 * @Description 蔡式树形结构表
 * @author caihuajun
 * @date 2011-06-07
 * @version v1.0
 */
package com.dbc.pojo;

import java.io.Serializable;

/**
 * @ClassName TreeByCai
 * @Description 蔡式树形结构表
 * @author caihuajun
 * @date 2011-06-07
 */
public class Dbc_treeByCai implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id; //主键
	
	private String treetype; //树类型，例如物质是zyml,非遗是fy_zyml,景区是whjd
	
	private String nodename; //节点名称
	
	private String parentid; //父节点
	
	private String deep; //深度
	
	private String nodepic;  //节点图片
	
	private String nodeurl; //节点链接
	
	private Integer childnum; //子节点数
	
	private String parentpath; //节点路径
	
	private Double sortcode; //排序码
	
	private String description;  //描述
	
	private String deletemark="0"; //删除标记
	
	private String createdate; // 创建时间	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public String getNodepic() {
		return nodepic;
	}

	public void setNodepic(String nodepic) {
		this.nodepic = nodepic;
	}

	public Integer getChildnum() {
		return childnum;
	}

	public void setChildnum(Integer childnum) {
		this.childnum = childnum;
	}

	public String getParentpath() {
		return parentpath;
	}

	public void setParentpath(String parentpath) {
		this.parentpath = parentpath;
	}

	public Double getSortcode() {
		return sortcode;
	}

	public void setSortcode(Double sortcode) {
		this.sortcode = sortcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeletemark() {
		return deletemark;
	}

	public void setDeletemark(String deletemark) {
		this.deletemark = deletemark;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getTreetype() {
		return treetype;
	}

	public void setTreetype(String treetype) {
		this.treetype = treetype;
	}

	public String getNodeurl() {
		return nodeurl;
	}

	public void setNodeurl(String nodeurl) {
		this.nodeurl = nodeurl;
	}
	
}
