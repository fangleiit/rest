package com.biz.bizunited.service;

import javax.servlet.http.HttpServletRequest;

import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.wechat.bean.AccessToken;

public interface WeiXinAccessTokenService extends CommonService{
	
	/**
     * 获取access_token
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
	public AccessToken getAccessToken(String appid,String appsecret );
	
	/**
	 * 调用微信author2.0 通用方法
	 * @param request    前台请求
	 * @param paramsMap  请求页面带的参数
	 * @param accountId    微信公众账号信息ID
	 * @return
	 */
	public String callWeixinAuthor2ReturnUrl(HttpServletRequest request,String tagetUrl,String appId,String appSecret);
	
}
