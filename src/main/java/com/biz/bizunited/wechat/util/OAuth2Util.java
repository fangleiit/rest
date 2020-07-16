package com.biz.bizunited.wechat.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;

import jodd.util.StringUtil;

public class OAuth2Util {
	
	//弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息
	public static final String SNSAPI_USERINFO = "snsapi_userinfo";
	//不弹出授权页面，直接跳转，只能获取用户openid
	public static final String SNSAPI_BASE = "snsapi_base";

	/**
	 * 方法描述: 获取redirect的URL地址
	 * 作    者： Administrator
	 * 日    期： 2015年1月11日-下午12:36:32
	 * @param targetUrl
	 * @param appid
	 * @return
	 * @throws UnsupportedEncodingException 
	 * 返回类型： String
	 */
	public static String obtainWeixinOAuth2Url(String targetUrl, String appid,String scope) {
		String shareurl = "";
		String encodeTargetURL = "";
		try {
			encodeTargetURL = URLEncoder.encode(targetUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//这种一般不做处理
			e.printStackTrace();
		}
		shareurl = WeixinUtil.AUTHORIZE_CODE_URL.replace("APPID", appid).replace("REDIRECT_URI", encodeTargetURL).replace("SCOPE", scope);
		return shareurl;
	}
}
