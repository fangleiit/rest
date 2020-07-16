package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

import com.biz.bizunited.entity.PictrueEntity;

@MiniDao
public interface QrcodeErrorDao {

	/**
	 * 根据openID删除数据
	 * @param openId
	 * @return
	 */
	@Sql("delete from qrcode_error where openid = '${openId}'")
	@Arguments("openId")
	public int deleteQrcodeErrorByOpenId(String openId);
	
}
