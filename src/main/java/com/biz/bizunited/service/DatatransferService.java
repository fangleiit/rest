package com.biz.bizunited.service;

import java.util.List;

import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.entity.PutinPrizeEntity;

public interface DatatransferService<T> extends CommonService {
	public List<T> findListByStatus(String status, int page, int size);

	public void updateStatus(List<T> list);
	
}
