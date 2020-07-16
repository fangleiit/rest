package com.biz.bizunited.common.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public interface CommonService {
	
	public <T> void delete(T entity);
	
	public <T> Serializable save(T entity);

	public <T> void saveOrUpdate(T entity);
	
	public <T> void batchSave(List<T> entitys);
	
	/**
	 * 删除实体主键删除
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id);
	
	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value);
	
	/**
	 * 根据实体名称和主键获取实体
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T getEntity(Class entityName, Serializable id);
	
	public Session getSession();
	
	public <T> void batchUpdate(List<T> entitys, Integer count);
	
	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);
	
	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql);
	
	public Integer executeSql(String sql, Object... param);
}
