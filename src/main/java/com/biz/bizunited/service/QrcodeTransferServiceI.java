package com.biz.bizunited.service;

import com.alibaba.fastjson.JSONObject;

import com.biz.bizunited.common.service.CommonService;

/**
 * @ClassName: QrcodeTransferServiceI 
 * @Description: 获取二维码信息
 * @author ian.zeng
 * @date 2017年9月11日 上午11:28:48
 */
public interface QrcodeTransferServiceI extends CommonService{
	
	public JSONObject getQrcode(String qrcode);
}
