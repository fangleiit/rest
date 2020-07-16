package com.biz.bizunited.wechat.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.Gson;


public abstract class RemoteWeixinMethodBase {
	private static final Logger logger = LoggerFactory.getLogger(RemoteWeixinMethodBase.class);
	/**
	 * 通用调用远程方法
	 * @param url
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> callWeixinRemoteMethod(String url,Object pojo){
		JSONObject json2 =	WeixinUtil.httpRequest(url, "POST", JSONUtils.toJSONString(pojo));
		Gson gson = new Gson();
		Map<String,Object> ruleResut = gson.fromJson(json2.toString(), Map.class);
		logger.info("获取openId返回信息 {}",ruleResut);
		return ruleResut;
	}
}
