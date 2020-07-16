package com.biz.bizunited.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

import com.biz.bizunited.entity.QrcodeErrorEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.vo.PublicVo;
import com.biz.bizunited.vo.RulesTipulaVo;

/**
 * 
 * @ClassName: ValidateRuleDao 
 * @Description: 验证规则
 * @author ian.zeng
 * @date 2017年7月26日 下午3:52:22
 */
@MiniDao
public interface ValidateRuleDao {
	
	public static final String ruleSql="select r.id,r.num_place numPlace,r.time_space timeSpace,r.dis_error_num disErrorNum,"
			+ "num_scancode numScancode,r.scancode_scope scancodeScope,r.scancode_peo_num scancodePeoNum,"
			+ "r.series_num_day SeriesNumDay,r.series_day_num seriesDayNum,r.sms_send_day smsSendDay from qrcode_rule_stipula r";
	/**
	 * 规格查询
	 * @return
	 */
	@Sql(ruleSql)
	public List<RulesTipulaVo> findRule();
	
	public static final String scanByOpenidSql = "select * from (select ro.last_name openid,count(ro.last_name) count,"
			+ " date_format(ro.create_time,'%Y-%m-%d') scanTime,fo.longitude,fo.latitude"
			+ " from qrcode_redpackage_open ro left join qrcode_terminal_info fo on fo.openid=ro.last_name"
			+ " where ro.last_name = '${openid}' and date_format(ro.create_time,'%Y-%m-%d')='${nowdstr}'"
			+ " group by ro.last_name,date_format(ro.create_time,'%Y-%m-%d')) t where t.count>=${count}";
	/**
	 * 根据openid查询扫码次数是否已达到验证黄名单要求
	 * @param openid
	 * @param nowdstr 当前日期字符串
	 * @param count 配置验证次数
	 * @return
	 */
	@Sql(scanByOpenidSql)
	@Arguments({"openid","nowdstr","count"})
	public PublicVo findScanCodeCountByOpenid(String openid,String nowdstr,int count);
	
	public static final String scanCodeCountListSql = "select * from (select ro.last_name openid,count(ro.last_name) count,"
			+ " date_format(ro.create_time,'%Y-%m-%d') scanTime,fo.longitude,fo.latitude"
			+ " from qrcode_redpackage_open ro left join qrcode_terminal_info fo on fo.openid=ro.last_name "
			+ " where 1=1 and date_format(ro.create_time,'%Y-%m-%d')='${nowdstr}' and ro.last_name != '${openid}'"
			+ " group by ro.last_name,date_format(ro.create_time,'%Y-%m-%d')) t where t.count>=${count}";
	/**
	 * 查询所有已达到扫码次数的人
	 * @param nowdstr
	 * @param count
	 * @return
	 */
	@Sql(scanCodeCountListSql)
	@Arguments({"openid","nowdstr","count"})
	public List<PublicVo> findScanCodeCountList(String openid,String nowdstr,int count);
	
	
	public static final String userSql = "select * from qrcode_terminal_info where openid in (${openids})";
	/**
	 * 查询所有符合黄名单条件的用户
	 * @param openids
	 * @return
	 */
	@Sql(userSql)
	@Arguments({"openids"})
	public List<TerminalInfoEntity> findTerminalInfoList(String openids);
	
	public static final String countDaySql = "select * from (select ro.last_name openid,date_format(ro.create_time,'%Y-%m-%d') scanCount,"
			+ " count(ro.last_name) count from qrcode_redpackage_open ro where ro.last_name = '${openid}'"
			+ " group by ro.last_name,date_format(ro.create_time,'%Y-%m-%d')) t where t.count>=${count}";
	/**
	 * 查询是否已达到黄名单每天扫码量
	 * @param openid
	 * @param count
	 * @return
	 */
	@Sql(countDaySql)
	@Arguments({"openid","count"})
	public List<PublicVo> findScanCodeCountByRule(String openid,int count);
	
	public static final String errorSql = "select * from qrcode_error where openid='${openid}'";
	
	@Sql(errorSql)
	@Arguments({"openid"})
	public List<QrcodeErrorEntity> findErrorQrcode(String openid);
	
	/**
	 * 查询当天扫码次数
	 * @param openid
	 * @param qrcode
	 * @param date
	 * @return
	 */
	@Sql("select count(ro.id) num from qrcode_redpackage_open ro where ro.last_name='${openid}' "
			+ "and ro.qrcode!='${qrcode}' and date_format(ro.create_time,'%Y-%m-%d')='${date}'")
	@Arguments({"openid","qrcode","date"})
	public int searchCount(String openid,String qrcode,String date);
}
