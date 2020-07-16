package com.biz.bizunited.service;

import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.entity.QuestionVerEntity;

public interface QrcodeQuestionVerService extends CommonService{
	public QuestionVerEntity findQuestionVerByopenId(String openId);
	
	public void saveOrUpdateQuestionVerEntity(QuestionVerEntity questionVerEntity);
}
