package com.biz.bizunited.service.impl;

import java.util.List;

import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.TerminalInfoDao;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.service.TerminalInfoService;

@Service
@Transactional
public class TerminalInfoServiceImpl extends CommonServiceImpl implements TerminalInfoService {

	@Autowired
	private TerminalInfoDao terminalInfoDao;

	@Autowired
	private IGenericBaseCommonDao commonDao;

	@Override
	public List<TerminalInfoEntity> findListByStatus(String status, int page, int size) {
		return terminalInfoDao.findListByStatus(status, page, size);
	}

	@Override
	public void updateStatus(List<TerminalInfoEntity> list) {
		if (list == null) {
			return;
		}
		for (TerminalInfoEntity entity : list) {
			entity.setDataStatus("1");
			commonDao.saveOrUpdate(entity);
		}
	}

}
