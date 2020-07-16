package com.biz.bizunited.service.impl;

import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.bizunited.dao.BankCardDao;
import com.biz.bizunited.entity.BankCardEntity;
import com.biz.bizunited.service.BankCardService;

/**
 * 用户实现
 * @Description: 
 *
 */
@Service("bankCardServiceImpl")
public class BankCardServiceImpl implements BankCardService{
	
	@Autowired
	private BankCardDao bankCardDao;

	@Override
	public BankCardEntity getBankCardById_sql(String id) {
		return bankCardDao.getBankCardById_sql(id);
	}

	@Override
	public BankCardEntity getBankCardById(String id) {
		// TODO Auto-generated method stub
		return bankCardDao.getByIdHiber(BankCardEntity.class, id);
	}

	@Override
	public MiniDaoPage<BankCardEntity> findBankCardPage(int page, int row) {
		// TODO Auto-generated method stub
		return bankCardDao.findBankCardPage(page, row);
	}

	
}
