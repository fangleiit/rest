package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

import com.biz.bizunited.entity.PrizeEntity;

@MiniDao
public interface RecyclingPrizeDao {
	
	/**
	 * 查询已经失效的手工投奖记录
	 * @param nowdate 当前时间
	 * @return
	 */
	@Sql("select distinct pc.id,pc.act_id actId,pc.p_name pname,pc.p_type ptype,pc.remark remark,pc.price price,pc.money_1 money1,"
			+ " pc.money_2 money2,pc.money_3 money3,pc.money_4 money4,pc.money_5 money5,pc.count count,pc.out_count outCount,"
			+ " pc.first first,pc.yellowlist yellowlist,pc.fixed,pc.big,pc.putin,pc.status from qrcode_prize_config pc "
			+ " left join qrcode_putin_prize p on p.p_id=pc.id where date_format(p.e_date,'%Y-%m-%d %H:%i:%s') < :nowdate "
			+ " and p.s_number>0 and pc.status='009'")
	@Arguments({"nowdate"})
	public List<PrizeEntity> findLosePrizeList(String nowdate);
}
