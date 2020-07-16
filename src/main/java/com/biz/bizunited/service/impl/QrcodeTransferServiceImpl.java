package com.biz.bizunited.service.impl;

import java.util.ResourceBundle;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.service.QrcodeTransferServiceI;
import com.biz.bizunited.util.HttpClientUtil;

@Service("qrcodeTransferService")
public class QrcodeTransferServiceImpl extends CommonServiceImpl implements QrcodeTransferServiceI {
	
	@Override
	public JSONObject getQrcode(String qrcode) {
		ResourceBundle bundle = ResourceBundle.getBundle("transfer");
		String url = bundle.getString("URL");
		String userName = bundle.getString("userName");
		String passwd = bundle.getString("passwd");
		String realUrl = String.format(url,qrcode,userName,passwd);
		JSONObject relust = HttpClientUtil.sendGet(realUrl, null, null);
		return relust;
	}
	
}
