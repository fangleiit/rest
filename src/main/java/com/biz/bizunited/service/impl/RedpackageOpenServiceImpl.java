package com.biz.bizunited.service.impl;

import java.util.List;

import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.RedpackageOpenDao;
import com.biz.bizunited.entity.RedpackageOpenEntity;
import com.biz.bizunited.service.RedpackageOpenService;

@Service
@Transactional
public class RedpackageOpenServiceImpl extends CommonServiceImpl implements RedpackageOpenService {

	@Autowired
	private RedpackageOpenDao redpackageOpenDao;

	@Autowired
	private IGenericBaseCommonDao commonDao;

	@Override
	public List<RedpackageOpenEntity> findListByStatus(String status, int page, int size) {
		return redpackageOpenDao.findListByStatus(status, page, size);
	}

	@Override
	public void updateStatus(List<RedpackageOpenEntity> list) {
		if (list == null) {
			return;
		}
		for (RedpackageOpenEntity entity : list) {
			entity.setDataStatus("1");
			commonDao.saveOrUpdate(entity);
		}
	}

}
