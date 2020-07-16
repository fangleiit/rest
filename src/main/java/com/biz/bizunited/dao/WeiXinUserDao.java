package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.hibernate.MiniDaoSupportHiber;

import com.biz.bizunited.entity.WeiXinUserEntity;

/**
 *微信用户信息
 *@class com.biz.bizunited.dao.AccessTokenDao
 */
@MiniDao("wiXinUserDao")
public interface WeiXinUserDao extends MiniDaoSupportHiber<WeiXinUserEntity>{
	
	@Sql("select * from weixin_user where data_status is null or data_status = ${status} limit ${(page - 1) * size},${size}")
	@Arguments(value={"status","page","size"})
	public List<WeiXinUserEntity> findListByStatus(String status,int page,int size);
}
