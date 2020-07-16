package com.biz.bizunited.service;

import com.biz.bizunited.common.service.CommonService;

public interface QrcodeErrorService extends CommonService {
	
	public int deleteQrcodeErrorByOpenId(String openId);
}
