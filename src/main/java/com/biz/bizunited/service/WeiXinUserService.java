package com.biz.bizunited.service;

import com.biz.bizunited.entity.WeiXinUserEntity;

public interface WeiXinUserService extends DatatransferService<WeiXinUserEntity>{
	
	public WeiXinUserEntity findWeiXinUserInfoByOpenId(String openId);
	
	/**
	 * 定时更新微信是否关注了公众号
	 */
	public void updateSubscribe();
	
	public void updateSubscribe(WeiXinUserEntity entity);
}
