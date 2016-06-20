/**
* @Project 源自 dbc
* @Title AppService.java
* @Package com.dbc.service
* @Description 应用表service类接口
* @author caihuajun
* @date 2013-10-29
* @version v2.0
*/
package com.dbc.service;

import java.util.List;

import com.dbc.service.BaseService;

/**
* @ClassName AppService
* @Description 应用表service类接口
* @author caihuajun
* @date 2013-10-29
*/
public interface Dbc_treeByCaiService extends BaseService{
	
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