package com.biz.bizunited.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.QrcodeErrorDao;
import com.biz.bizunited.service.QrcodeErrorService;

@Service("qrcodeErrorService")
public class QrcodeErrorServiceImpl extends CommonServiceImpl implements QrcodeErrorService {

	@Autowired
	private QrcodeErrorDao qrcodeErrorDao;

	@Override
	public int deleteQrcodeErrorByOpenId(String openId) {
		return qrcodeErrorDao.deleteQrcodeErrorByOpenId(openId);
	}
}
