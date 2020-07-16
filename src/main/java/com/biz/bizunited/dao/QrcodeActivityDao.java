package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

import com.biz.bizunited.entity.AppealEntity;
import com.biz.bizunited.entity.RedpackageOpenEntity;
import com.biz.bizunited.vo.PrizeVo;
import com.biz.bizunited.vo.QrcodeActInfoVo;
import com.biz.bizunited.vo.QrcodeChannelVo;
import com.biz.bizunited.vo.QrcodeParamVo;
import com.biz.bizunited.vo.QrcodeUserInfoSeriesVo;
import com.biz.bizunited.vo.RedpackageOpenVo;
import com.biz.bizunited.vo.TerminalInfoVo;


@MiniDao
public interface QrcodeActivityDao {
	
	/**
	 * 红包记录
	 * @param qrcode
	 * @return
	 */
	@Sql("select id,qrcode,last_name lastName,take_name takeName,act_id actId,prize prize,bonus bonus,date_format(take_time,'%Y-%m-%d %H:%i:%s') takeTime,"
			+ "is_sendout isSendout,create_time createTime from QRCODE_REDPACKAGE_OPEN where qrcode=:qrcode")
	@Arguments({"qrcode"})
	public RedpackageOpenVo findRedpackageByQrcode(String qrcode);
	
	/**
	 * 查询是否需要填写信息
	 * @param actId
	 * @return
	 */
	@Sql("select ic.id,ic.name name,ic.count count,ic.url url from QRCODE_TERMINAL_INFO_CONFIG ic "
			+ "left join qrcode_act_series s on s.series_id=ic.id where s.act_id='${actId}' and ic.status='009' group by ic.id,ic.name,ic.count,ic.url")
	@Arguments({"actId"})
	public QrcodeUserInfoSeriesVo findInfoConfig(String actId);
	
	/**
	 * 根据用户openId查询信息
	 * @param openId
	 * @return
	 */
	@Sql("select id,openid,mobile_phone mobilePhone,channel,is_blacklist isBlacklist,is_yellowlist isYellowlist,"
			+ "store_name storeName,longitude,latitude,province,city,area,address,wechat_nikename wechatNikename,whitelist "
			+ "from qrcode_terminal_info where openid=:openId")
	@Arguments({"openId"})
	public TerminalInfoVo findTerminalInfoByOpenId(String openId);
	
	/**
	 * 根据活动查找奖项
	 * @param actId
	 * @return
	 */
	@Sql("select id,act_id actId,p_name pname,p_type ptype,remark remark,money_1 money1,money_2 money2,money_3 money3,"
			+ "money_4 money4,money_5 money5,count,out_count outCount,first,yellowlist,fixed,big,putin,price price "
			+ "from QRCODE_PRIZE_CONFIG where act_id=:actId and status='009' and putin=0")
	@Arguments({"actId"})
	public List<PrizeVo> findPrizeListByActId(String actId);
	
	/**
	 * 查询当天是否已中过大奖
	 * @param openId
	 * @param nowDate
	 * @return
	 */
	@Sql("select count(red.id) count from qrcode_redpackage_open red left join qrcode_prize_config p on "
			+ "p.id=red.prize where red.take_name=:openId and date_format(red.take_time,'%Y-%m-%d')=:nowDate and p.big=1")
	@Arguments({"openId","nowDate"})
	public Integer whetherBig(String openId,String nowDate);
	
	/**
	 * 查询手工投放的奖
	 * @param custCode
	 * @param area
	 * @param date
	 * @param actId
	 * @return
	 */
	/*@Sql("select pc.id id,pc.act_id actId,pc.p_name pname,pc.p_type ptype,pc.remark remark,pc.money_1 money1,pc.money_2 money2,"
			+ "pc.money_3 money3,pc.money_4 money4,pc.money_5 money5,pp.s_number count,pp.put_count-pp.s_number outCount from qrcode_prize_config pc "
			+ "left join qrcode_putin_prize pp on pp.p_id=pc.id where 1=1 and (pp.putin_people='${custCode}' "
			+ "<#if area ?exists && area ?length gt 0>"
			+ " or pp.id=(select putin_id from putin_area where area_name like '%${area}%')"
			+ "</#if>"
			+ ") and s_date<str_to_date('${date}','%Y-%m-%d %H:%i:%s') "
			+ "and e_date>str_to_date('${date}','%Y-%m-%d %H:%i:%s') and pc.act_id='${actId}' and pp.s_number>0")*/
	public static final String putinListSql = "select pc.id id,pc.act_id actId,pc.p_name pname,pc.p_type ptype,pc.remark remark,pc.price, "
			  +" pc.money_1 money1,pc.money_2 money2,pc.money_3 money3,pc.money_4 money4,"
			  +" pc.money_5 money5,pp.put_count count,pp.id putinId,pp.put_count - pp.s_number outCount"
			  +" from qrcode_putin_prize pp left join qrcode_prize_config pc ON pc.id = pp.p_id"
			  +" where 1 = 1 and pc.act_id=:actId and pp.s_number>0"
			  +" and (pp.s_date<str_to_date(:date,'%Y-%m-%d %H:%i:%s') "
			  +"  and pp.e_date>str_to_date(:date,'%Y-%m-%d %H:%i:%s')"
			  +"  and pp.how-1 <= (select count(o.id) from qrcode_redpackage_open o "
			  +" where o.take_time>pp.s_date and o.take_time<pp.e_date ))"
			  +" and (pp.putin_people=:phone "
			  + "<#if area ?exists && area ?length gt 0>"
			  + "or pp.id in (select putin_id from putin_area where 1=1"
			  + "<#if area ?exists && area ?length gt 0>"
			  + " and city like :city "
			  + "</#if>"
			  + " and area_name like :area)"
			  + "</#if>"
			  + " or pp.putin_cust=:custCode)";
	@Sql(putinListSql)
	@Arguments({"custCode","city","area","date","actId","phone"})
	public List<PrizeVo> findPutinPrizeList(String custCode,String city,String area,String date,String actId,String phone);
	
	/**
	 * 只查询普通奖，排除大奖
	 * @param actId
	 * @return
	 */
	@Sql("select id,act_id actId,p_name pname,p_type ptype,remark remark,money_1 money1,money_2 money2,money_3 money3,"
			+ "money_4 money4,money_5 money5,count,out_count outCount,first,yellowlist,fixed,big,putin,price price "
			+ "from QRCODE_PRIZE_CONFIG where act_id=:actId and status='009' and putin=0 and big=0")
	@Arguments({"actId"})
	public List<PrizeVo> findNoBigPrize(String actId);
	/**
	 * 查询首次中奖奖项
	 * @param actId
	 * @return
	 */
	@Sql("select id,act_id actId,p_name pname,p_type ptype,remark remark,money_1 money1,money_2 money2,money_3 money3,"
			+ "money_4 money4,money_5 money5,count,out_count outCount,first,yellowlist,fixed,big,putin,price price "
			+ "from QRCODE_PRIZE_CONFIG where act_id=:actId and status='009' and first=1")
	@Arguments({"actId"})
	public List<PrizeVo> findFirstPrize(String actId);
	
	/**
	 * 判断用户是否为第一次扫码
	 * @param openId
	 * @return
	 */
	@Sql("select * from QRCODE_REDPACKAGE_OPEN where last_name = :openId or take_name = :openId")
	@Arguments({"openId"})
	public List<RedpackageOpenVo> findRedpackageOpenByOpenId(String openId);
	
	/**
	 * 查询红包领取记录
	 * @param openId
	 * @param qrcode
	 * @return
	 */
	@Sql("select ro.id,ro.qrcode,ro.last_name lastName,ro.take_name takeName,ro.act_id actId,pc.id prizeId,pc.remark remark,"
			+ " pc.p_name prize,pc.p_type ptype,ro.bonus,date_format(ro.take_time,'%Y-%c-%d %H:%i:%s') takeTime,"
			+ " ro.is_sendout isSendout,ro.create_time createTime,ro.scan_code scanCode from qrcode_redpackage_open ro"
			+ " left join qrcode_prize_config pc on pc.id=ro.prize "
			+ " where 1=1 and ro.take_name = :paramVo.openId"
			+ "<#if paramVo.isSendout ?exists && paramVo.isSendout ?length gt 0>"
			+ " and ro.is_sendout = :paramVo.isSendout and pc.p_type=1 "
			+ "</#if>"
			+ "<#if paramVo.actId ?exists && paramVo.actId ?length gt 0>"
			+ " and ro.act_id = :paramVo.actId"
			+ "</#if>"
			+ "<#if paramVo.qrcode ?exists && paramVo.qrcode ?length gt 0>"
			+ " and ro.qrcode <> :paramVo.qrcode"
			+ "</#if>"
			+ " order by ro.take_time desc")
	/*@Sql("select id,qrcode,last_name lastName,take_name takeName,act_id actId,prize prize,bonus,take_time takeTime,"
			+ " is_sendout isSendout,create_time createTime,scan_code scanCode from qrcode_redpackage_open "
			+ " where take_name = '${paramVo.openId}' and is_sendout = 0 and qrcode <> '${paramVo.qrcode}' and act_id = '${paramVo.actId}'")*/
	@Arguments({"paramVo"})
	public List<RedpackageOpenVo> findRedpackageOpenList(QrcodeParamVo paramVo);
	
	/**
	 * 根据活动ID查询用户在该活动的扫码次数
	 * @param openId
	 * @param actId
	 * @return
	 */
	//@Sql("select count(id) count from qrcode_redpackage_open where act_id=:actId and take_name=:openId and 1=1")
	@Sql("select count(id) count from qrcode_redpackage_open where take_name= :openId and 1=1")
	@Arguments({"openId","actId"})
	public Integer findScanCodeByOpenId(String openId,String actId);
	/**
	 * 查询用户的扫码次数
	 * @param openId
	 * @return
	 */
	@Sql("select count(id) count from qrcode_redpackage_open where 1=1 and take_name=:openId ")
	@Arguments({"openId"})
	public Integer findScanCodeByOpenId(String openId);
	
	@Sql("select ro.id,ro.qrcode,ro.last_name lastName,ro.take_name takeName,ro.act_id actId,ro.prize prize,ro.bonus,ro.take_time takeTime,"
			+ " ro.is_sendout isSendout,ro.create_time createTime,ro.scan_code scanCode from qrcode_redpackage_open ro left join qrcode_prize_config pc on pc.id=ro.prize "
			+ " where ro.take_name = :paramVo.openId and ro.is_sendout = 0 and pc.p_type=1")
	@Arguments({"paramVo"})
	public List<RedpackageOpenEntity> findRedpackageOpenEntityList(QrcodeParamVo paramVo);
	
	@Sql("select ro.id,ro.qrcode,ro.last_name lastName,ro.take_name takeName,ro.act_id actId,ro.prize prize,ro.bonus,ro.take_time takeTime,"
			+ " ro.is_sendout isSendout,ro.create_time createTime,ro.scan_code scanCode from qrcode_redpackage_open ro left join qrcode_prize_config pc on pc.id=ro.prize "
			+ " where ro.take_name = :paramVo.openId and ro.is_sendout = 0 and pc.p_type=1")
	@Arguments({"paramVo"})
	public List<RedpackageOpenVo> findAmount(QrcodeParamVo paramVo);
	
	@Sql("select id,qrcode,last_name lastName,take_name takeName,act_id actId,prize prize,bonus,take_time takeTime,"
			+ " is_sendout isSendout,create_time createTime,scan_code scanCode from qrcode_redpackage_open where id=:redId")
	@Arguments({"redId"})
	public RedpackageOpenVo findAmountByRedId(String redId);
	
	/**
	 * 红包领取记录
	 * @param openId
	 * @return
	 */
	@Sql("select id,qrcode,last_name lastName,take_name takeName,act_id actId,prize prize,bonus,take_time takeTime,"
			+ " is_sendout isSendout,create_time createTime,scan_code scanCode from qrcode_redpackage_open "
			+ " where 1=1 "
			+ "<#if paramVo.openId ?exists && paramVo.openId ?length gt 0>"
			+ " and take_name = :paramVo.openId"
			+ "</#if>"
			+ "<#if paramVo.isSendout ?exists && paramVo.isSendout ?length gt 0>"
			+ " and is_sendout = :paramVo.isSendout"
			+ "</#if>"
			+ "<#if paramVo.actId ?exists && paramVo.actId ?length gt 0>"
			+ " and act_id = :paramVo.actId"
			+ "</#if>"
			+ " order by take_time desc")
			//+ " take_name = '${paramVo.openId}' and is_sendout = 0 and act_id = '${paramVo.actId}'")
	@Arguments({"paramVo"})
	public List<RedpackageOpenVo> redPackageRecord(QrcodeParamVo paramVo); 
	
	/**
	 * 中奖名单
	 * @return
	 */
	@Sql("select ro.take_time takeName,ro.bonus,pc.id prizeId,pc.p_name prize,pc.p_type ptype,pc.remark,qt.mobile_phone mobilePhone,wu.nickname wechatNikename"
			+ " from qrcode_redpackage_open ro "
			+ " left join qrcode_terminal_info qt on qt.openid = ro.take_name "
			+ " left join qrcode_prize_config pc on pc.id = ro.prize"
			+ " left join weixin_user wu on wu.openid=ro.take_name"
			+ " where ro.take_name is not null "
			+ "<#if actId ?exists && actId ?length gt 0>"
			+ " and ro.act_id=:actId"
			+ "</#if>"
			+ " order by ro.take_time desc limit 50")
	@Arguments({"actId"})
	public List<RedpackageOpenVo> winningNameList(String actId);
	
	/**
	 * 查询中奖排名
	 * @return
	 */
	@Sql("select @rowno:=@rowno+1 as rowno,t.* from (select ro.take_name takeName,wu.nickname wechatNikename,"
			+ " sum(ro.bonus) bonus,count(ro.take_name) total"
			+ " from qrcode_redpackage_open ro left join qrcode_terminal_info qt on qt.openid = ro.take_name "
			+ " left join weixin_user wu on wu.openid=qt.openid"
			+ " where ro.take_name is not null "
			+ "<#if actId ?exists && actId ?length gt 0>"
			+ " and ro.act_id=:actId"
			+ "</#if>"
			+ " GROUP BY ro.take_name order by sum(ro.bonus) desc) t,(select @rowno:=0) r")
	@Arguments({"actId"})
	public List<RedpackageOpenVo> rankingList(String actId);
	
	/**
	 * 终端渠道类型
	 * @return
	 */
	@Sql("select channel_code channelCode,channel_name channelName from qrcode_channel where status = '009'")
	public List<QrcodeChannelVo> channelList();
	
	/**
	 * 查询执行的活动
	 * @param custCode
	 * @param productCode
	 * @param deliveryTime
	 * @return
	 */
	/*@Sql("select distinct act.id,act.act_name actName,act.act_note actNote,act.start_time startTime,act.end_time endTime,"
			+ " act.out_s_time outSTime,act.out_e_time outETime from qrcode_act_info act left join qrcode_rate_config r on r.act_id=act.id "
			+ "  left join qrcode_rate_product p "
			+ " on p.rate_id=r.id where p.p_code like :productCode "
			+ " and (date_format(act.out_s_time,'%Y-%m-%d %H:%i:%s')<=:proTime "
			+ " and date_format(act.out_e_time,'%Y-%m-%d %H:%i:%s')>=:proTime)"
			+ " and date_format(act.start_time,'%Y-%m-%d %H:%i:%s')<=:nowDate"
			+ " and date_format(act.end_time,'%Y-%m-%d %H:%i:%s')>=:nowDate")*/
	// " and c.sap_code like '%' || :custCode || '%' "
	@Sql("select distinct act.id,act.act_name actName,act.act_note actNote,act.start_time startTime,act.end_time endTime,"
			+ " act.out_s_time outSTime,act.out_e_time outETime,act.relation_cust relationCust from qrcode_act_info act left join qrcode_rate_config r on r.act_id=act.id "
			+ " left join qrcode_rate_product p on p.rate_id=r.id left join qrcode_rate_cust c on c.rate_id = r.id"
			+ " where p.p_code = :productCode "
			+ " <#if custCode ?exists && custCode ?length gt 0>"
			+ " and c.sap_code like '%${custCode}%' "
			+ " </#if>"
			+ " and (date_format(act.out_s_time,'%Y-%m-%d %H:%i:%s')<=:proTime "
			+ " and date_format(act.out_e_time,'%Y-%m-%d %H:%i:%s')>=:proTime)"
			+ " and date_format(act.start_time,'%Y-%m-%d %H:%i:%s')<=:nowDate"
			+ " and date_format(act.end_time,'%Y-%m-%d %H:%i:%s')>=:nowDate")
	@Arguments({"custCode","productCode","proTime","nowDate"})
	public QrcodeActInfoVo findActInfo(String custCode,String productCode,String proTime,String nowDate);
	
	/**
	 * 根据openId查询红包累计总金额
	 * @param openId 用户openId
	 * @return
	 */
	@Sql("select ifnull(sum(bonus),0) from qrcode_redpackage_open where take_name = :openId")
	@Arguments({"openId"})
	public String getRedPackageAllAmt(String openId);
	
	/**
	 * 根据openId查询金额
	 * @param openId 用户openId
	 * @param sendout 是否已发,0:未发,1:已发
	 * @return
	 */
	@Sql("select ifnull(sum(bonus),0) from qrcode_redpackage_open where take_name = :openId and is_sendout = :sendout")
	@Arguments({"openId","sendout"})
	public String getRedPackageAmt(String openId,Integer sendout);
	
	/**
	 * 查询申诉信息
	 * @param redid
	 * @param openid
	 * @return
	 */
	public AppealEntity findAppealEntity(String redid,String openid);
	
	@Sql("select id,act_name actName,staff_phone staffPhone from qrcode_act_info where id=:id")
	@Arguments({"id"})
	public QrcodeActInfoVo getActInfoById(String id);
}
