package com.biz.bizunited.service.impl;

import java.util.List;

import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.PictrueDao;
import com.biz.bizunited.entity.PictrueEntity;
import com.biz.bizunited.service.PictrueService;

@Service
@Transactional
public class PictrueServiceImpl extends CommonServiceImpl implements PictrueService {

	@Autowired
	private PictrueDao pictrueDao;

	@Autowired
	private IGenericBaseCommonDao commonDao;

	@Override
	public List<PictrueEntity> findListByStatus(String status, int page, int size) {
		return pictrueDao.findListByStatus(status, page, size);
	}

	@Override
	public void updateStatus(List<PictrueEntity> list) {
		if (list == null) {
			return;
		}
		for (PictrueEntity entity : list) {
			entity.setDataStatus("1");
			commonDao.saveOrUpdate(entity);
		}
	}

}
