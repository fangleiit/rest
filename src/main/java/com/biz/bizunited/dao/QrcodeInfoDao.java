package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

import com.biz.bizunited.entity.PrizeEntity;
import com.biz.bizunited.entity.PutinPrizeEntity;
import com.biz.bizunited.entity.QrcodeInfoEntity;

@MiniDao
public interface QrcodeInfoDao {

	@Sql("select id AS id,qrcode AS qrcode,customer_code AS customerCode,product_code AS productCode,out_time AS outTime,create_time AS createTime,produce_time AS produceTime,code_outside codeOutside "
			+" from qrcode_info where data_status is null or data_status = ${status} limit ${(page - 1) * size},${size}")
	@Arguments(value={"status","page","size"})
	public List<QrcodeInfoEntity> findListByStatus(String status,int page,int size);
	
	/**
	 * 获取需要推送的奖项配置.
	 * @author grover
	 * @param status
	 * 		推送状态
	 * @param page
	 * 		当前页
	 * @param size
	 * 		页大小
	 * @return
	 */
	@Sql("SELECT pc.id , pc.act_id actId , pc.p_name pname , pc.p_type ptype , pc.remark remark , pc.price price , pc.money_1 money1"
		+", pc.money_2 money2 , pc.money_3 money3 , pc.money_4 money4 , pc.money_5 money5 , pc.count count , pc.out_count outCount"
		+", pc.first first , pc.yellowlist yellowlist , pc.fixed , pc.big , pc.putin , pc.status"
		+" FROM qrcode_prize_config pc WHERE pc.data_status IS NULL OR pc.data_status = ${status} LIMIT ${(page - 1) * size},${size}")
	@Arguments(value={"status","page","size"})
	public List<PrizeEntity> findListByPrize(String status,int page,int size);
	
	/**
	 * 获取需要推送的数据
	 * @param status
	 * @param page
	 * @param size
	 * @return
	 */
	@Sql("select id,p_id pId,put_count putCount,s_number sNumber,putin_people putinPeople,s_date sDate,e_date eDate,putin_cust putinCust,how how, "
			+ "status from qrcode_putin_prize where data_status = :status LIMIT ${(page - 1) * size},${size}")
	@Arguments(value={"status","page","size"})
	public List<PutinPrizeEntity> findPutinPrizeListByStatus(String status,int page,int size);
}
