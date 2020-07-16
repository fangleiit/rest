package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.hibernate.MiniDaoSupportHiber;
import org.jeecgframework.minidao.pojo.MiniDaoPage;

import com.biz.bizunited.entity.WeiXinAccessTokenEntity;
import com.biz.bizunited.entity.BankCardEntity;

/**
 *微信AccessToken
 *@class com.biz.bizunited.dao.AccessTokenDao
 */
@MiniDao("accessTokenDao")
public interface WeiXinAccessTokenDao extends MiniDaoSupportHiber<WeiXinAccessTokenEntity>{
	
}
