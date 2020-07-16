package com.biz.bizunited.service;

import org.jeecgframework.minidao.pojo.MiniDaoPage;

import com.biz.bizunited.entity.BankCardEntity;

/**
 * 用户
 * @Description: 
 *
 */
public interface BankCardService{
	public BankCardEntity  getBankCardById_sql(String id);
	public BankCardEntity  getBankCardById(String id);
	public MiniDaoPage<BankCardEntity> findBankCardPage(int page,int row);
	
}
