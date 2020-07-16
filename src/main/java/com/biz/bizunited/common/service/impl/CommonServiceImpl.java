package com.biz.bizunited.common.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.CommonService;

@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	@Autowired
	public IGenericBaseCommonDao commonDao;

	@Override
	public <T> void delete(T entity) {
		commonDao.delete(entity);
	}

	@Override
	public <T> Serializable save(T entity) {
		return commonDao.save(entity);
	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);

	}

	@Override
	public <T> void batchSave(List<T> entitys) {
		commonDao.batchSave(entitys);

	}

	@Override
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteEntityById(entityName, id);

	}

	@Override
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return commonDao.findUniqueByProperty(entityClass, propertyName, value);
	}

	@Override
	public <T> T getEntity(Class entityName, Serializable id) {
		return commonDao.getEntity(entityName, id);
	}

	@Override
	public Session getSession() {
		return commonDao.getSession();
	}
	@Override
	public <T> void batchUpdate(List<T> entitys, Integer count){
    	commonDao.batchUpdate(entitys, count);
    }
	/**
	 * 按属性查找对象列表.
	 */
	@Override
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {

		return commonDao.findByProperty(entityClass, propertyName, value);
	}
	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql) {
		return commonDao.findByQueryString(hql);
	}

	@Override
	public Integer executeSql(String sql, Object... param) {
		return commonDao.executeSql(sql, param);
	}
}
