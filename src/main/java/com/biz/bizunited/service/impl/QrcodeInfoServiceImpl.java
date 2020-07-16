package com.biz.bizunited.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.QrcodeInfoDao;
import com.biz.bizunited.entity.QrcodeInfoEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.service.QrcodeInfoService;
import com.biz.bizunited.service.QrcodeTransferServiceI;
import com.biz.bizunited.util.DateUtils;
import com.biz.bizunited.util.StringUtil;

@Service("qrcodeInfoService")
@Transactional
public class QrcodeInfoServiceImpl extends CommonServiceImpl implements QrcodeInfoService {
	
	public static final Logger logger = LoggerFactory.getLogger(QrcodeInfoServiceImpl.class);
	@Autowired
	private QrcodeInfoDao qrcodeInfoDao;
	@Autowired
	private QrcodeTransferServiceI qrcodeTransferService; 
	
	@Override
	public QrcodeInfoEntity getQrcode(String qrcode) throws CommonException, ParseException {
		//Qrcode qrcodeEntity = commonDao.findUniqueByProperty(Qrcode.class, "qrcode", qrcode);
		if(StringUtil.isEmpty(qrcode)){
			logger.info("qrcode 为空");
			throw new CommonException("二维码信息错误，连接地址不全，请重新扫码");
		}
		QrcodeInfoEntity qrcodeInfo = commonDao.findUniqueByProperty(QrcodeInfoEntity.class, "qrcode", qrcode);
		if(qrcodeInfo == null){
			qrcodeInfo = this.getQrcodeinfo(qrcode);
			qrcodeInfo.setCreateTime(DateUtils.getDate());
			commonDao.save(qrcodeInfo);
			/*if(qrcodeEntity != null){
				qrcodeInfo = new QrcodeInfoEntity();
				qrcodeInfo.setQrcode(qrcodeEntity.getQrcode());
				qrcodeInfo.setCustomerCode(qrcodeEntity.getCustomerCode());
				qrcodeInfo.setProductCode(qrcodeEntity.getProductCode());
				qrcodeInfo.setProduceTime(qrcodeEntity.getProduceTime());
				qrcodeInfo.setOutTime(qrcodeEntity.getDeliveryTime());
				qrcodeInfo.setCreateTime(DateUtils.getDate());
				commonDao.save(qrcodeInfo);
				return qrcodeInfo;
			}*/
		}
		return qrcodeInfo;
	}

	@Override
	public List<QrcodeInfoEntity> findListByStatus(String status, int page, int size) {
		return qrcodeInfoDao.findListByStatus(status, page, size);
	}

	@Override
	public void updateStatus(List<QrcodeInfoEntity> list) {
		if (list == null) {
			return;
		}
		for (QrcodeInfoEntity entity : list) {
			entity.setDataStatus("1");
			commonDao.saveOrUpdate(entity);
		}
	}
	
	public QrcodeInfoEntity getQrcodeinfo(String qrcode) throws CommonException, ParseException{
		QrcodeInfoEntity qrcodeInfo = new QrcodeInfoEntity();
		JSONObject result = qrcodeTransferService.getQrcode(qrcode);
		boolean success = result.getBoolean("RESULT");
		if(!success){
			String message = result.getString("DESC");
			logger.info("接口返回数据失败：code:{} , message:{}",qrcode,message);
			throw new CommonException("二维码信息错误，请重新扫码");
		}
		String r = result.toString();
		//产品编码
		String productCode = result.getString("SUBTYPENO");
		//出库时间
		String outTime = "";
		if(r.indexOf("INVON") > -1){
			outTime = result.getString("INVON");
		}
		//经销商编码
		String customerCode = "";
		if(r.indexOf("TONO") > -1){
			customerCode = result.getString("TONO");
		}
		//生产时间
		String produceTime = result.getString("MADEDATE");
		//箱外码
		String codeOutside = result.getString("OUTCODE");
		//箱内码
		String innerCode = result.getString("INNERCODE");
		if(StringUtil.isNotEmpty(codeOutside)){
			if(qrcode.equals(codeOutside)){
				logger.info("参数传递错误，codeOutside:{}",codeOutside);
				throw new CommonException("非法的二维码编号");
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date produceDate = format.parse(produceTime);
		Date deliveryTime = null;
		if(StringUtil.isNotEmpty(outTime)){
			deliveryTime = format.parse(outTime);
		}
		qrcodeInfo.setQrcode(innerCode);
		qrcodeInfo.setProductCode(productCode);
		qrcodeInfo.setCustomerCode(customerCode);
		qrcodeInfo.setProduceTime(produceDate);
		qrcodeInfo.setOutTime(deliveryTime);
		qrcodeInfo.setCodeOutside(codeOutside);
		return qrcodeInfo;
	}
}
