package com.biz.bizunited.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.biz.bizunited.service.TransferService;
import com.biz.bizunited.wechat.payment.WeChatPayFactory;
import com.biz.bizunited.wechat.payment.lang.CheckName;
import com.biz.bizunited.wechat.payment.req.Transfer;
import com.biz.bizunited.wechat.payment.req.TransferQuery;
import com.biz.bizunited.wechat.payment.res.TransferQueryResponse;
import com.biz.bizunited.wechat.payment.res.TransferResponse;

@Service("transferServiceImpl")
public class TransferServiceImpl implements TransferService {
	private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
	//@Value("${weixin.appid}")
	private String appId = "wxa1749b457ecebe34";
	private String openid = "oI3WwwSfJK--uzmpFisN_wfN8rDI";
	private Integer amount = 100;//单位是分
	private String desc = "转账测试";
	@Override
	public boolean transfer() {
		WeChatPayFactory weChatPayFactory = WeChatPayFactory.newInstance();
		Transfer transfer = weChatPayFactory.newTransfer(appId, "297eaed85d5e1231015d5e1392800000", openid, amount, desc, CheckName.NO_CHECK, null);
		TransferResponse transferResponse = transfer.execute();
		logger.debug("Get response from wechat: {}",transferResponse.getProperties());
		if (transferResponse.isProcessSuccess()) {
			//转账成功,需要记录日志
			return true;
		}
		return false;
	}
	/**
	 * 调用微信支付接口
	 */
	@Override
	public TransferResponse payment(String appid,String openid,int amount,String orderId,String desc){
		WeChatPayFactory weChatPayFactory = WeChatPayFactory.newInstance();
		//微信转账
		Transfer transfer = weChatPayFactory.newTransfer(appid, orderId, openid, amount, desc, CheckName.NO_CHECK, null);
		TransferResponse transferResponse = transfer.execute();
		logger.debug("Get response from wechat: {}",transferResponse.getProperties());
		if (transferResponse.isProcessSuccess()) {
			//转账成功,需要记录日志
			return transferResponse;
		}
		return null;
	}
	@Override
	public void transferQuery() {
		// TODO Auto-generated method stub
		WeChatPayFactory weChatPayFactory = WeChatPayFactory.newInstance();
		TransferQuery transferQuery = weChatPayFactory.newTransferQuery(appId, "297eaed85d5e1231015d5e1392800001");
		TransferQueryResponse queryResponse = transferQuery.execute();
		logger.debug("Get response from wechat: {}",queryResponse.getProperties());
		if (queryResponse.isProcessSuccess()) {
			//
		}
	}

}
