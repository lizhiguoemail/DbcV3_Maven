/**
 * @Project 源自dbc
 * @Title BaseDao.java
 * @Package org.dbc.dao;
 * @Description  基类DAO
 * @author caihuajun
 * @date 2009-11-01
 * @version v1.0
 * 2009-11-10 caihuajun 因为删除方法都改为了逻辑删除，所以查询的时候需要对deletemark过滤，deletemark为1的都视为已经被删除
 * 2011-06-14 caihuajun 脑子开窍，增加了基类的查询方法 分别为selObject，selObjectByNum，selObjectpage 大大节省了别的dao类的代码量，以后就不用重复写这样的代码了
 * 2011-07-18 caihuajun 添加了一些优化查询的方法，在方法名后面增加arr的方法都是今天增加的，优化方面：减少了一次数据库查询。
 * 2011-09-05 caihuajun 添加了ext分页模式的查询
 * 2011-09-08 caihuajun 改进了查询模式，添加searcharr参数
 * 2011-10-26 caihuajun 修改了每个方法的注释，使得调用的方法能更容易明白参数的意思
 * 2011-10-27 caihuajun 优化了查询方法，去除了查询列表总数的排序
 * 2012-03-30 caihuajun 郁闷，在linux环境下太变态了，大小写这么严格，于是把getSortCode和getSortCode_Double这两个方法的表名都统一强制变成小写的
 * 2012-03-31 caihuajun 优化了获取最大排序码的方法
 * 2012-03-31 caihuajun 增加了获得某张表某个字段的最大值或者最小值的三个方法，分别对应integer，long，double三个类型
 * 2012-05-29 caihuajun 增加了selbyonefieldarrvalue这一系列的方法，还增加了deletebyfieldarr方法
 * 2012-05-30 caihuajun 修改了增删改的方法的返回值，都设置成void，并且把try catch的方法去掉了，不然事务不能回滚
 * 2012-06-01 caihuajun 在saveObject和updateObject方法中添加了super.getHibernateTemplate().flush();解决了在事务中这些操作不能提交的问题
 * 2012-06-12 caihuajun 增加了setbyonefieldarrvalue(Class clazz,String fieldname,String[] contentarr,String setfieldname,String setvalue,String orderfieldname,String order,boolean islike)方法
 * 2013-04-22 caihuajun 增加了selObjectbyarrEXT相关的三个方法，扩展了按多个条件查询的方法
 * 2013-05-05 caihuajun 增加selObjectbysearcharrpage,selObjectbysearcharrorarrpage方法
 * 2013-07-10 caihuajun 又多了个selObjectEXT，用来过滤条件用的fieldnamemust[]和valuemust[] islike[]
 * 2013-11-08 caihuajun 正式改成jsp版的dbc项目，大部分方法全部重写，痛苦的一天要开始了
 */
package com.dbc.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.SortField.Type;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dbc.util.PageParm;

public interface BaseDao {
	
	
	/**
	 * @Title saveObject
	 * @Description  保存对象  
	 * @param  obj 保存的对象
	 * @return void  
	 */
	public void saveObject(Object obj);
	
	/**
	 * @Title updateObject
	 * @Description   修改对象
	 * @param  obj 修改的对象
	 * @return void  
	 */
	public void updateObject(Object obj);
	
	/**
	 * @Title saveObject
	 * @Description  保存对象 
	 * @param  obj 保存的对象
	 * @return void  
	 */
	public void saveorupdateObject(Object obj);
	
	/**
	 * @Title setObjectbyids
	 * @Description   根据ids批量修改对象
	 * @param  clazz 类名
     * @param  setfieldnamearr 要修改的字段组
     * @param  setcontenarr  要修改的值组
	 * @param ids  要修改对象的id
	 * @return void
	 */
	public void setObjectbyids(Class clazz,String[] setfieldnamearr,String[] setcontentarr,String[] ids);

	
	/**
	 * @Title setObjectbyarr
	 * @Description   根据某个字段修改对象
	 * @param  clazz 类名
     * @param  setfieldnamearr 要修改的字段数组
     * @param  setcontentarr 要修改的值数组
     * @param  fieldnamearr 根据修改的字段数组
	 * @param contentarr  根据修改的值数组
	 * @return void
	 */
	public void setObjectbyarr(Class clazz,String[] setfieldnamearr,String[] setcontentarr,String[] fieldnamearr,String[] contentarr);
	
	/**
	 * @Title deletObject
	 * @Description   删除对象
	 * @param  obj 要删除的对象
	 * @return void  
	 */
	public void deletObject(Object obj);
	
	/**
	 * @Title deletebyfieldarr
	 * @Description   根据某指定字段值数组批量删除对象
	 * @param  clazz 类名
     * @param  fieldnamearr 根据的字段
     * @param  contentarr  数组值
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deletebyfieldarr(Class clazz,String[] fieldnamearr,String[] contentarr,boolean islogic);
	
	/**
	 * @Title deleteAll
	 * @Description   删除所有
	 * @param  clazz 类名
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deleteAll(Class clazz,boolean islogic);
	
	/**
	 * @Title deletebysql
	 * @Description   根据sql条件删除
	 * @param  clazz 类名
	 * @param  where  条件
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deletebysql(Class clazz,String where,boolean islogic);
	
	/**
	 * @Title deletebyids
	 * @Description   根据ids批量修改对象
	 * @param  clazz 类名
	 * @param ids  要修改对象的id
	 * @return void
	 */
	public void deletebyids(Class clazz,String[] ids,boolean islogic);
	
	/**
	 * @Title deletebyfieldarrforandor
	 * @Description   根据某指定字段值数组批量删除对象,条件是or或者and选择的关系
	 * @param  clazz 类名
     * @param  fieldnamearr 根据的字段
     * @param  contentarr  数组值
     * @param  islogic  是否是逻辑删除
	 * @return void
	 */
	public void deletebyfieldarrforandor(Class clazz,String[] fieldnamearr,String[] contentarr,String andor,boolean islogic);

	
	/**
	 * @Title selByOid
	 * @Description  查找指定ID的对象
	 * @param  clazz 类名
	 * @param  oid 对象id
	 * @return Object  对象
	 */
	public Object selByOid(Class clazz, String oid);
	
	/**
	 * @Title selByOid
	 * @Description  查找指定ID的对象
	 * @param  clazz 类名
	 * @param  oid 对象id
	 * @param isneedclose 是否开启session
	 * @return Object  对象
	 */
	public Object selByOid(Class clazz, String oid,Boolean isneedclose);
	
	/**
	 * @Title selByOids
	 * @Description  查找指定IDS的对象
	 * @param  clazz 类名
	 * @param  oids 对象ids
     * @param orderfieldname 排序字段
	 * @param order 顺序
	 * @return List  对象集合
	 */
	public List selByOids(Class clazz,String[] oids,String[] orderfieldnamearr,String[] orderarr);
	
	/**
	 * @Title selByOids
	 * @Description  查找指定IDS的对象
	 * @param  clazz 类名
	 * @param  oids 对象ids
     * @param orderfieldname 排序字段
	 * @param order 顺序
	 * @param isneedclose 是否开启session
	 * @return List  对象集合
	 */
	public List selByOids(Class clazz,String[] oids,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose);
	
	/**
	 * @Title selEntity
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selEntity(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr);
	
	/**
	 * @Title selEntity
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntity(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose);
	
	/**
	 * @Title selEntityBySql
	 * @Description  根据sql语句查找对象列表
	 * @param  clazz 类名
	 * @param  sql sql语句
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  列表
	 */
	public List selEntityBySql(Class clazz, String sql,String[] orderfieldnamearr,String[] orderarr);
	
	/**
	 * @Title selEntityBySql
	 * @Description  根据sql语句查找对象列表
	 * @param  clazz 类名
	 * @param  sql sql语句
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  列表
	 */
	public List selEntityBySql(Class clazz, String sql,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose);
	
	/**
	 * @Title selEntityBynum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selEntityBynum(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,int num);
	
	/**
	 * @Title selEntityBynum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBynum(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,int num,Boolean isneedclose);
	
	/**
	 * @Title selEntityBySqlnum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param  sql sql语句
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntityBySqlnum(Class clazz,String sql,String[] orderfieldnamearr,String[] orderarr,int num);
	
	/**
	 * @Title selEntityBySqlnum
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param  sql sql语句
	 * @param num 获取条数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySqlnum(Class clazz,String sql,String[] orderfieldnamearr,String[] orderarr,int num,Boolean isneedclose);
	
	/**
	 * @Title selBySql
	 * @Description  根据sql语句查找
	 * @param  sql sql语句
	 * @return List  列表
	 */
	public List selBySql(String sql);
	
	/**
	 * @Title selBySql
	 * @Description  根据sql语句查找
	 * @param  sql sql语句
	 * @param isneedclose 是否需要关闭session
	 * @return List  列表
	 */
	public List selBySql(String sql,Boolean isneedclose);
	
	/**
	 * @Title selBySqlPage
	 * @Description  根据sql语句查找
	 * @param  lensql 获取长度sql
	 * @param  sql  sql语句
	 * @param pageparm 分页参数
	 * @param isneedclose 是否开启session
	 * @return List  列表
	 */
	public List selBySqlPage(String lensql,String sql,PageParm pageparm);
	
	/**
	 * @Title selBySqlPage
	 * @Description  根据sql语句查找
	 * @param  lensql 获取长度sql
	 * @param  sql  sql语句
	 * @param pageparm 分页参数
	 * @param isneedclose 是否开启session
	 * @return List  列表
	 */
	public List selBySqlPage(String lensql,String sql,PageParm pageparm,Boolean isneedclose);
	
	/**
	 * @Title selEntityByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selEntityByPage(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr);
	
	/**
	 * @Title selEntityBySql
	 * @Description 根据sql语句查询分页列表   
	 * @param sql 自定义sql
	 * @return List  
	 * @throws
	 */
	public List selEntityBySql(String sql);
	
	/**
	 * @Title selEntityBySql
	 * @Description 根据sql语句查询分页列表   
	 * @param sql 自定义sql
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySql(String sql,Boolean isneedclose);
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询对象分页列表   
	 * @param  clazz
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySqlPage(Class clazz,String sql,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose);
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询分页列表   
	 * @param lensql 自定义sqlcount
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntityBySqlPage(String lensql,String sql,PageParm pageparm);
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询分页列表   
	 * @param lensql 自定义sqlcount
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selEntityBySqlPage(String lensql,String sql,PageParm pageparm,Boolean isneedclose);
	
	
	/**
	 * @Title selEntityByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 * @throws
	 */
	public List selEntityByPage(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose);
	
	/**
	 * @Title selEntityBySqlPage
	 * @Description 根据sql语句查询对象分页列表   
	 * @param  clazz
	 * @param sql 自定义sql
	 * @param pageparm 分页参数
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 * @throws
	 */
	public List selEntityBySqlPage(Class clazz,String sql,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr);
	
	/**
	 * @Title selObject
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selObject(Class clazz,String selstr,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr);
	
	/**
	 * @Title selObject
	 * @Description 查询对象列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selObject(Class clazz,String selstr,String[] fieldnamearr,String[] valuearr,boolean[] islikearr,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose);
	
	/**
	 * @Title selObjectByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @return List  
	 */
	public List selObjectByPage(Class clazz,String selstr, String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr);
	
	/**
	 * @Title selObjectByPage
	 * @Description 查询对象分页列表   
	 * @param  clazz
	 * @param selstr
	 * @param fieldnamearr 字段名
	 * @param valuearr 字段值
	 * @param islike 是否使用like
	 * @param PageParm 分页参数类
	 * @param orderfieldnamearr 排序字段
	 * @param orderarr 顺序
	 * @param isneedclose 是否开启session
	 * @return List  
	 */
	public List selObjectByPage(Class clazz,String selstr, String[] fieldnamearr,String[] valuearr,boolean[] islikearr,PageParm pageparm,String[] orderfieldnamearr,String[] orderarr,Boolean isneedclose);
	
	/**
	   * @Title getSortCode_Double
	   * @Description 获得最大排序码
	   * @param tablename 表名
	   * @return Double
	   */
	public Double getSortCode_Double(String tablename);
	
	/**
	* 全文索引检索
	* @param clazz    类
	* @param filename 检索字段
	* @param keyword  关键字
	* @return List   返回所需要的集合。
	*/
    public List search(Class clazz, String[] filenames,String[] keywords,String[] flags,String[] filed_sort,String[] fild_type,boolean[] isasc);
	
    /**
	* 全文索引检索
	* @param clazz    类
	* @param filename 检索字段
	* @param keyword  关键字
	* @return List   返回所需要的集合。
	*/
    public List searchByPage(Class clazz, String[] filenames,String[] keywords,String[] flags,String[] filed_sort,String[] fild_type,boolean[] isasc, PageParm pageparm);
	
  //执行索引  
    public long createIndexByHibernateSearch(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr);
    
    //删除索引
    public long deleteIndexByHibernateSearch(Class clazz,String[] fieldnamearr,String[] valuearr,boolean[] islikearr);
    
    // 增删改使用的session
	public Session getCurrentSession();
	
	// 查询使用的session
	public Session openSession();
	
	//获得排序类型
	public Type getSortcode_type(String fild_type);
	
}
