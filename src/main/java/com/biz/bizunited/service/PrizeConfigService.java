package com.biz.bizunited.service;

import java.util.List;

import com.biz.bizunited.entity.PrizeEntity;
import com.biz.bizunited.entity.PutinPrizeEntity;

/**
 * 奖项配置service.
 * @author grover
 * @version v1.0
 */
public interface PrizeConfigService extends DatatransferService<PrizeEntity>{
	public List<PutinPrizeEntity> findPutinPrizeListByStatus(String status, int page, int size);
	
	public void updatePutinPrizStatus(List<PutinPrizeEntity> putinPrizeEntities);
}
