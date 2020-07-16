package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.hibernate.MiniDaoSupportHiber;
import org.jeecgframework.minidao.pojo.MiniDaoPage;

import com.biz.bizunited.entity.BankCardEntity;

/**
 * @Description: 用于测试
 * @ClassName: com.biz.bizunited.dao.AdminDao
 */
@MiniDao("bankCardDao")
public interface BankCardDao extends MiniDaoSupportHiber<BankCardEntity>{
	
	/**
	 * 根据用户名获取用户信息
	 * @Title: getByUserName
	 * @param username
	 */
	public static final String getBankCardById= "select * from BANK_CARD where id = :id ";
	@Sql(getBankCardById)
	@Arguments("id")
	public BankCardEntity  getBankCardById_sql(String id);
	/**
	 * 查询会员列表
	 * @param pageable
	 * @return
	 */
	@Sql("select * from BANK_CARD")
	@Arguments({"page","row"})
	@ResultType(BankCardEntity.class)
	public MiniDaoPage<BankCardEntity> findBankCardPage(int page,int row);

	@Arguments("bankCard")
	public List<BankCardEntity> findBankCardEntityByBankCard(BankCardEntity bankCard);
}
