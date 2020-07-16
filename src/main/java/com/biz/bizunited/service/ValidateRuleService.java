package com.biz.bizunited.service;

import java.text.ParseException;
import java.util.List;

import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.entity.QrcodeInfoEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.vo.RulesTipulaVo;
import com.biz.bizunited.vo.TerminalInfoVo;

/**
 * @ClassName: ValidateRuleService 
 * @Description: 规则验证业务逻辑
 * @author ian.zeng
 * @date 2017年7月26日 上午11:34:23
 */
public interface ValidateRuleService extends CommonService{
	/**
	 * 规格查询
	 * @return
	 */
	public List<RulesTipulaVo> findRule();
	/**
	 * 验证是否加入黄名单
	 */
	public void joinYellowList(TerminalInfoVo terminalInfoVo);
	
	/**
	 * 加入黑名单
	 * @param openid
	 */
	public void joinBlackList(String openid);
	
	/**
	 * 根据openid查询已出现错误码次数
	 * @param openid
	 */
	public void validateionCount(String openid);
	/**
	 * 验证二维码是否正常
	 * @param qrcodeInfo
	 * @param openid
	 * @throws CommonException
	 */
	public void validationQrcode(QrcodeInfoEntity qrcodeInfo,String openid) throws CommonException;
	/**
	 * 查看用户是否是黑名单
	 * @param openid
	 * @throws CommonException 
	 */
	public boolean validationBlacklistByOpenid(String openid);
	/**
	 * 验证用户
	 * @param openid
	 * @throws CommonException 
	 * @throws ParseException 
	 */
	public void validationUser(String openid,String qrcode) throws CommonException, ParseException;
}
