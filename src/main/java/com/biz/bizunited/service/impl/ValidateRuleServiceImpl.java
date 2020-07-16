package com.biz.bizunited.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


import com.alibaba.fastjson.JSONObject;
import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.controller.HomeIndexController;
import com.biz.bizunited.dao.ValidateRuleDao;
import com.biz.bizunited.entity.QrcodeErrorEntity;
import com.biz.bizunited.entity.QrcodeInfoEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.service.ValidateRuleService;
import com.biz.bizunited.util.CollectionUtil;
import com.biz.bizunited.util.DateUtils;
import com.biz.bizunited.util.HttpClientUtil;
import com.biz.bizunited.util.MapsUtil;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.vo.PublicVo;
import com.biz.bizunited.vo.RulesTipulaVo;
import com.biz.bizunited.vo.TerminalInfoVo;

@Service("validateRuleService")
@Transactional
public class ValidateRuleServiceImpl extends CommonServiceImpl implements ValidateRuleService {
	
	public static final Logger logger = LoggerFactory.getLogger(ValidateRuleServiceImpl.class);
	
	@Autowired
	private ValidateRuleDao validateRuleDao;
	@Autowired
	private IGenericBaseCommonDao commonDao;
	
	private static Map<Object, Long> timemap = new HashMap<Object, Long>();
	
	@Override
	public List<RulesTipulaVo> findRule() {
		//实际上只会存在一条数据，还是用list接收
		List<RulesTipulaVo> tipulaVos = validateRuleDao.findRule();
		return tipulaVos;
	}
	
	public RulesTipulaVo getRulesTipulaVo(){
		List<RulesTipulaVo> tipulaVos = validateRuleDao.findRule();
		RulesTipulaVo rule = new RulesTipulaVo();
		if(CollectionUtil.listNotEmptyNotSizeZero(tipulaVos)){
			for (RulesTipulaVo rulesTipulaVo : tipulaVos) {
				rule = rulesTipulaVo;
				break;
			}
		}
		return rule;
	}
	@Override
	public void joinYellowList(TerminalInfoVo terminalInfoVo){
		RulesTipulaVo rule = getRulesTipulaVo();
		String openid = terminalInfoVo.getOpenid();
		if(terminalInfoVo.getLongitude() != null && !"".equals(terminalInfoVo.getLongitude()) 
				&& terminalInfoVo.getLatitude() != null && !"".equals(terminalInfoVo.getLatitude())){
			//根据设置的距离将周围达到条件的openid加入黄名单
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			String nowdstr = dateformat.format(new Date());
			int count = Integer.parseInt(rule.getNumScancode());
			PublicVo publicVo = validateRuleDao.findScanCodeCountByOpenid(openid, nowdstr, count-1);
			
			List<PublicVo> publicVos = validateRuleDao.findScanCodeCountList(openid,nowdstr, count);
			//范围
			int scancodeScope = Integer.parseInt(rule.getScancodeScope());
			//人数
			int scancodePeoNum = Integer.parseInt(rule.getScancodePeoNum());
			String lonstr1 = "0";
			String latstr1 = "0";
			String lonstr2 = "0";
			String latstr2 = "0";
			String openids = "";
			int pnum = 0;
			if(publicVo != null){
				openids +=  "'" + publicVo.getOpenid() + "',";
				if(StringUtil.isNotEmpty(publicVo.getLongitude())){
					lonstr1 = publicVo.getLongitude();
				}
				if(StringUtil.isNotEmpty(publicVo.getLatitude())){
					latstr1 = publicVo.getLatitude();
				}
				for (PublicVo publicVo2 : publicVos) {
					if(StringUtil.isNotEmpty(publicVo2.getLongitude())){
						lonstr2 = publicVo2.getLongitude();
					}
					if(StringUtil.isNotEmpty(publicVo2.getLatitude())){
						latstr2 = publicVo2.getLatitude();
					}
					int dis = MapsUtil.getDistance(lonstr1, latstr1, lonstr2, latstr2);
					if(scancodeScope >= dis){
						openids += "'"+publicVo2.getOpenid()+"',";
						pnum ++;
					}
				}
				if(pnum >= scancodePeoNum){
					if(StringUtil.isNotEmpty(openids)){
						openids = openids.substring(0, openids.length()-1);
					}
					List<TerminalInfoEntity> infoEntities = validateRuleDao.findTerminalInfoList(openids);
					for (TerminalInfoEntity terminalInfoEntity : infoEntities) {
						String note = "周边人达到黄名单条件";
						terminalInfoEntity.setIsYellowlist(1);
						terminalInfoEntity.setDataStatus("0");
						terminalInfoEntity.setNote(note);
						//记录状态变更日志
						changeUserStatusLog(terminalInfoEntity.getOpenid(),"IS_YELLOWLIST");
					}
					commonDao.batchUpdate(infoEntities, 10);
				}
			}
		}
		//如果一个用户连续x天扫码次数达到x次,则加入黄名单
		TerminalInfoEntity infoEntity = commonDao.findUniqueByProperty(TerminalInfoEntity.class, "openid", openid);
		if(infoEntity != null){
			if(infoEntity.getIsYellowlist() == 0){
				int daynum = Integer.parseInt(rule.getSeriesNumDay());
				int seriesDayNum = Integer.parseInt(rule.getSeriesDayNum());
				List<PublicVo> list = validateRuleDao.findScanCodeCountByRule(openid, seriesDayNum-1);
				if(list.size() >= daynum){
					String note = "连续"+daynum+"扫码次数达到"+seriesDayNum+"次，自动加入黑名单";
					infoEntity.setIsYellowlist(1);
					infoEntity.setDataStatus("0");
					infoEntity.setNote(note);
					commonDao.saveOrUpdate(infoEntity);
					//记录状态变更日志
					changeUserStatusLog(openid,"IS_YELLOWLIST");
				}
			}
		}
	}

	@Override
	public void joinBlackList(String openid) {
		TerminalInfoEntity infoEntity = commonDao.findUniqueByProperty(TerminalInfoEntity.class, "openid", openid);
		if(infoEntity != null){
			if(infoEntity.getWhitelist() == 0){
				infoEntity.setIsBlacklist(1);
				infoEntity.setIsYellowlist(0);
				infoEntity.setDataStatus("0");
				commonDao.saveOrUpdate(infoEntity);
			}
		}else{
			infoEntity = new TerminalInfoEntity();
			infoEntity.setOpenid(openid);
			infoEntity.setIsBlacklist(1);
			infoEntity.setCreateTime(DateUtils.getDate());
			commonDao.save(infoEntity);
		}
		//记录状态变更日志
		changeUserStatusLog(openid,"IS_BLACKLIST");
	}
	
	@Override
	public void validateionCount(String openid){
		RulesTipulaVo rule = getRulesTipulaVo();
		if(rule.getDisErrorNum() != null && !"".equals(rule.getDisErrorNum())){
			
			int disErrorNum = Integer.parseInt(rule.getDisErrorNum());
			List<QrcodeErrorEntity> list = validateRuleDao.findErrorQrcode(openid);
			if(list.size() >= disErrorNum){
				this.joinBlackList(openid);
			}
		}
	}
	@Override
	public void validationQrcode(QrcodeInfoEntity qrcodeInfo,String openid) throws CommonException{
		if(qrcodeInfo.getProduceTime() == null){
			//无生产日期，异常二维码，加入黑名单
			saveOrUpdateTerminalInfo(openid, "black");
			changeUserStatusLog(openid,"IS_BLACKLIST");
			throw new CommonException("异常二维码");
		}
		/*if(qrcodeInfo.getOutTime() == null){
			//无出库时间，直接设置黄名单
			saveOrUpdateTerminalInfo(openid, "yellow");
			changeUserStatusLog(openid,"IS_YELLOWLIST");
		}*/
	}
	
	public void saveOrUpdateTerminalInfo(String openid,String status){
		TerminalInfoEntity infoEntity = commonDao.findUniqueByProperty(TerminalInfoEntity.class, "openid", openid);
		if(infoEntity != null){
			if("yellow".equals(status)){
				infoEntity.setIsYellowlist(1);
				infoEntity.setIsBlacklist(0);
				infoEntity.setDataStatus("0");
				infoEntity.setNote("扫无出库时间二维码，直接加入黄名单");
			}
			if("black".equals(status)){
				infoEntity.setIsBlacklist(1);
				infoEntity.setIsYellowlist(0);
				infoEntity.setDataStatus("0");
				infoEntity.setNote("扫无生产时间二维码，加入黑名单");
			}
			commonDao.saveOrUpdate(infoEntity);
		}else{
			infoEntity = new TerminalInfoEntity();
			infoEntity.setOpenid(openid);
			if("yellow".equals(status)){
				infoEntity.setIsYellowlist(1);
				infoEntity.setNote("扫无出库时间二维码，直接加入黄名单");
			}
			if("black".equals(status)){
				infoEntity.setIsBlacklist(1);
				infoEntity.setNote("扫无生产时间二维码，加入黑名单");
			}
			commonDao.save(infoEntity);
		}
	}

	@Override
	public boolean validationBlacklistByOpenid(String openid) {
		TerminalInfoEntity infoEntity = commonDao.findUniqueByProperty(TerminalInfoEntity.class, "openid", openid);
		if(infoEntity != null){
			if(infoEntity.getIsBlacklist() == 1 && infoEntity.getWhitelist() == 0){
				return false;
			}
		}
		return true;
	}

	@Override
	public void validationUser(String openid,String qrcode) throws CommonException, ParseException {
		//验证是否在扫码时间内扫码
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置时间格式
		String ndstr = df.format(new Date());
		Date nd = df.parse(ndstr);
		Date sd = df.parse(Constants.S_DATE);
		Date ed = df.parse(Constants.E_DATE);
		if(nd.getTime() > ed.getTime() || nd.getTime() < sd.getTime()){
			String msg = "每天"+Constants.E_DATE+"至凌晨"+Constants.S_DATE+"为非扫码时间，请于"+Constants.S_DATE+"后重新扫码";
			throw new CommonException(msg);
		}
		//黑名单用户拦截
		boolean b = this.validationBlacklistByOpenid(openid);
		if(!b){
			throw new CommonException("异常用户，不能参与活动");
		}
		//拦截扫码次数以达到x次的用户
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = dateformat.format(new Date());
		int num = validateRuleDao.searchCount(openid, qrcode, nowDate);
		RulesTipulaVo rule = getRulesTipulaVo();
		if(rule.getNumPlace() != null && !"".equals(rule.getNumPlace())){
			int numPlace = Integer.parseInt(rule.getNumPlace());
			if(num >= numPlace){
				throw new CommonException("每天最多可参与" + numPlace + "次活动，您已经参与了" + num + "次，请明天再继续参与活动");
			}
		}
		//拦截扫码间隔时间未达到x秒的用户
		if(timemap.get(openid) == null){
			timemap.put(openid, System.currentTimeMillis());
		}else{
			long ddd = System.currentTimeMillis();
			long a = (ddd-timemap.get(openid))/1000;
			if(rule.getTimeSpace() != null && !"".equals(rule.getTimeSpace())){
				long timeSpace = Integer.parseInt(rule.getTimeSpace());
						if(a > timeSpace){
							timemap.put(openid, ddd);
						}else{
							throw new CommonException("您的操作太频繁啦！请稍后再试！");
						}
			}
		}
	}
	/**
	 * 用户状态变更日志记录
	 * @param openId
	 * @param changeStatus 变更状态
	 */
	public void changeUserStatusLog(String openId,String changeStatus){
		//获取日志记录接口url
		String url = ResourceBundle.getBundle("transfer").getString("eisp.changeUserStatusLog.url");
		String paramJson = "openId="+openId+"&changeStatus="+changeStatus+"&deleteQrcodeError=false";
		JSONObject relust = HttpClientUtil.sendPost(url, paramJson, null);
		logger.info("success : " + relust.get("success"));
		logger.info("message : " + relust.get("msg"));
	}
}
