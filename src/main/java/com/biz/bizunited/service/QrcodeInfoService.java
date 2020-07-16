package com.biz.bizunited.service;

import java.text.ParseException;

import com.biz.bizunited.entity.QrcodeInfoEntity;
import com.biz.bizunited.exception.CommonException;

/**
 * 
 * @ClassName: QrcodeInfoService 
 * @Description: 查询二维码信息
 * @author ian.zeng
 * @date 2017年7月28日 下午5:15:44
 */
public interface QrcodeInfoService extends DatatransferService<QrcodeInfoEntity>{
	public QrcodeInfoEntity getQrcode(String qrcode) throws CommonException, ParseException;
}
