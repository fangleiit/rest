package com.biz.bizunited.service;

import com.biz.bizunited.wechat.payment.res.TransferResponse;

/**
 * 微信转账
 *
 */
public interface TransferService {
	/**
	 * 微信转账
	 */
	public boolean transfer();
	
	public TransferResponse payment(String appid,String openid,int amount,String orderId,String desc);
	/**
	 * 微信转账查询
	 */
	public void transferQuery();
}
