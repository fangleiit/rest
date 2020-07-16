package com.biz.bizunited.service.impl;

import java.util.List;

import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.AppealDao;
import com.biz.bizunited.entity.AppealEntity;
import com.biz.bizunited.service.AppealService;

@Service
@Transactional
public class AppealServiceImpl extends CommonServiceImpl implements AppealService {

	@Autowired
	private AppealDao appealDao;

	@Autowired
	private IGenericBaseCommonDao commonDao;

	@Override
	public List<AppealEntity> findListByStatus(String status, int page, int size) {
		return appealDao.findListByStatus(status, page, size);
	}

	@Override
	public void updateStatus(List<AppealEntity> list) {
		if (list == null) {
			return;
		}
		for (AppealEntity entity : list) {
			entity.setDataStatus("1");
			commonDao.saveOrUpdate(entity);
		}
	}

}
