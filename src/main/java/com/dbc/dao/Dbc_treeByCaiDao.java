/**
 * @Project dbc
 * @Title TreeByCaiDao.java
 * @Package com.dbc.dao;
 * @Description 蔡式树形结构表Dao接口
 * @author caihuajun
 * @date 2011-06-07
 * @version v1.0
 */
package com.dbc.dao;

import java.util.List;

/**
 * @ClassName TreeByCaiDao
 * @Description 蔡式树形结构表dao接口
 * @author caihuajun
 * @date 2011-06-07
 */
public interface Dbc_treeByCaiDao extends BaseDao {
	/**
	 * @Title selSonNum
	 * @Description 查询有多少以此ID为父ID的节点
	 * @param pid
	 * @return int
	 */
	public Integer selSonNum(String pid);
	
	/**
	 * @Title selByDeepPid
	 * @Description 获得同父节点同级的节点的最大或者最小的排序码
	 * @param treetype
	 * @param deep
	 * @param parentid
	 * @param order
	 * @return List
	 */
	public List selByDeepPid(String treetype, String deep,String parentid,String order);
	
	/**
	 * @Title selByPidPre
	 * @Description 获得同父节点同级的节点的最大或者最小的排序码
	 * @param treetype
	 * @param deep
	 * @param ordersort
	 * @param compare
	 * @param parentid
	 * @param order
	 * @return List
	 */
	public List selByPidPre(String treetype, String deep,Double sortcode,String compare,String parentid,String order);
	
	/**
	 * @Title selbypath
	 * @Description 根据parentpath查询
	 * @param treetype
	 * @param path
	 * @param order
	 * @return List
	 */
	public List selbypath(String treetype,String path,String order);
	
	/**
	 * @Title selbypatharr
	 * @Description 根据parentpath数组查询
	 * @param treetype
	 * @param patharr
	 * @param order
	 * @return List
	 */
	public List selbypatharr(String treetype, String[] patharr, String order);
	
	/**
	 * @Title setbypath
	 * @Description 根据parentpath设置
	 * @param path
	 * @param fieldname
	 * @param content
	 * @return void
	 */
	public void setbypath(String path, String fieldname,String content);
	
	
	/**
	 * @Title setorderbycha
	 * @Description 更新排序码
	 * @param list
	 * @param fangshi
	 * @param cha
	 * @return void
	 */
	public void setorderbycha(List list,String fangshi,int cha);
	
	/**
	 * @Title setorderbycha
	 * @Description 更新排序码
	 * @param treetype
	 * @param sortcode
	 * @param compare
	 * @param list
	 * @param fangshi
	 * @param cha
	 * @return void
	 */
	public void setorderbycha(String treetype,Double sortcode, String compare,List list,String fangshi, double cha);
	
	/**
	 * @Title selbyupjishu
	 * @Description 查找上级节点
	 * @param treetype
	 * @param pid
	 * @param deep
	 * @param compare
	 * @param sortcode
	 * @param order
	 * @return List
	 */
	public List selbyupjishu(String treetype, String pid, String deep,String compare,Integer sortcode,String order);
	
	
	/**
	 * @Title updatefortianjia
	 * @Description 为添加更新
	 * @param treetype
	 * @param ordersort
	 * @return void
	 */
	public void updatefortianjia(String treetype,double sortcode);
	
	/**
	 * @Title selBytreetype
	 * @Description 查询指定类别
	 * @param treetype
	 * @return List
	 */
	public List selBytreetype(String treetype);
	
}