package com.biz.bizunited.service.impl;

import java.util.List;

import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.DrawShiWuDao;
import com.biz.bizunited.entity.DrawShiWuEntity;
import com.biz.bizunited.service.DrawShiWuService;

@Service
@Transactional
public class DrawShiWuServiceImpl extends CommonServiceImpl implements DrawShiWuService {

	@Autowired
	private DrawShiWuDao drawShiWuDao;

	@Autowired
	private IGenericBaseCommonDao commonDao;

	@Override
	public List<DrawShiWuEntity> findListByStatus(String status, int page, int size) {
		return drawShiWuDao.findListByStatus(status, page, size);
	}

	@Override
	public void updateStatus(List<DrawShiWuEntity> list) {
		if (list == null) {
			return;
		}
		for (DrawShiWuEntity drawShiWuEntity : list) {
			drawShiWuEntity.setDataStatus("1");
			commonDao.saveOrUpdate(drawShiWuEntity);
		}
	}

}
