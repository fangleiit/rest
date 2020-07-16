package org.jeecgframework.minidao.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * 
 * 类描述：DAO层泛型基类接口
 * 
 * @author: jeecg
 * @date： 日期：2012-12-8 时间：下午05:37:33
 * @version 1.0
 */
public interface IGenericBaseCommonDao {

	public <T> void delete(T entitie);

	/**
	 * 根据主键删除指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void deleteEntityById(Class<?> entityName, Serializable id);

	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value);

	public <T> T get(Class<T> entityClass, final Serializable id);

	public <T> T get(T entitie);

	public Session getSession();

	public <T> List<T> loadAll(T entitie);

	public <T> Serializable save(T entity);

	public <T> void saveOrUpdate(T entity);
	
	public <T> void batchSave(List<T> entitys);
	/**
     * 根据sql更新
     * 
     * @param query
     * @return
     */
    public int updateBySqlString(String sql);
    
    /**
     * 根据实体名称和主键获取实体
     * 
     * @param <T>
     * 
     * @param <T>
     * @param entityName
     * @param id
     * @return
     */
    public <T> T getEntity(Class entityName, Serializable id);
    
    public <T> void batchUpdate(List<T> entitys, Integer count);
    
    /**
     * 按属性查找对象列表.
     */
    public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);
    public <T> List<T> findByQueryString(final String hql);
    /**
     * 执行SQL
     */
    public Integer executeSql(String sql, Object... param);
    
    public <T> void merge(T entity);
}
