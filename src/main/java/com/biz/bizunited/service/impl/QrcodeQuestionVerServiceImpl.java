package com.biz.bizunited.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.entity.QuestionVerEntity;
import com.biz.bizunited.service.QrcodeQuestionVerService;

@Service("qrcodeQuestionVerService")
@Transactional
public class QrcodeQuestionVerServiceImpl  extends CommonServiceImpl implements QrcodeQuestionVerService{
	
	@Autowired
	private CommonService commonService;
	
	private static final Logger logger = LoggerFactory.getLogger(QrcodeQuestionVerServiceImpl.class);
	
	@Override
	public QuestionVerEntity findQuestionVerByopenId(String openId) {
		return this.findUniqueByProperty(QuestionVerEntity.class,"openid", openId);
	}

	@Override
	public void saveOrUpdateQuestionVerEntity(QuestionVerEntity questionVerEntity) {
		commonService.saveOrUpdate(questionVerEntity);
	}

}
