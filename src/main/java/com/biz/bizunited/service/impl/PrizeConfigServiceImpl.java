package com.biz.bizunited.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.QrcodeInfoDao;
import com.biz.bizunited.entity.PrizeEntity;
import com.biz.bizunited.entity.PutinPrizeEntity;
import com.biz.bizunited.service.PrizeConfigService;
import com.biz.bizunited.util.CollectionUtil;

/** 
 * 奖项配置实现.
 * @author grover
 * @version v1.0
 */
@Service("prizeConfigService")
@Transactional
public class PrizeConfigServiceImpl extends CommonServiceImpl implements PrizeConfigService {
	
	@Autowired
	private QrcodeInfoDao qrcodeInfoDao;
	
	@Override
	public List<PrizeEntity> findListByStatus(String status, int page, int size) {
		return qrcodeInfoDao.findListByPrize(status, page, size);
	}

	@Override
	public void updateStatus(List<PrizeEntity> list) {
		if (CollectionUtil.listNotEmptyNotSizeZero(list)) {
			for (PrizeEntity prizeEntity : list) {
				prizeEntity.setDataStatus("1");
				commonDao.saveOrUpdate(prizeEntity);
			}
		}
	}
	
	@Override
	public List<PutinPrizeEntity> findPutinPrizeListByStatus(String status, int page, int size){
		return qrcodeInfoDao.findPutinPrizeListByStatus(status, page, size);
	}

	@Override
	public void updatePutinPrizStatus(List<PutinPrizeEntity> putinPrizeEntities) {
		if(CollectionUtil.listNotEmptyNotSizeZero(putinPrizeEntities)){
			for (PutinPrizeEntity putinPrizeEntity : putinPrizeEntities) {
				putinPrizeEntity.setDataStatus("1");
			}
			commonDao.batchUpdate(putinPrizeEntities, 20);
		}
	}
}
