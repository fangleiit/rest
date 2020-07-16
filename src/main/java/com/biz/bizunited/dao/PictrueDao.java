package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

import com.biz.bizunited.entity.PictrueEntity;

@MiniDao
public interface PictrueDao {

	@Sql("select * from qrcode_pictrue where data_status is null or data_status = ${status} limit ${(page - 1) * size},${size}")
	@Arguments(value={"status","page","size"})
	public List<PictrueEntity> findListByStatus(String status,int page,int size);
	
}
