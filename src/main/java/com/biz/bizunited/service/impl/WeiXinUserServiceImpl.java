package com.biz.bizunited.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.dao.WeiXinUserDao;
import com.biz.bizunited.entity.AppealEntity;
import com.biz.bizunited.entity.WeiXinUserEntity;
import com.biz.bizunited.service.WeiXinAccessTokenService;
import com.biz.bizunited.service.WeiXinUserService;
import com.biz.bizunited.util.CollectionUtil;
import com.biz.bizunited.util.DateUtils;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.wechat.bean.AccessToken;
import com.biz.bizunited.wechat.payment.WeChatPayFactory;
import com.biz.bizunited.wechat.util.WeixinUtil;

import freemarker.template.utility.DateUtil;

/**
 * 用户实现
 * @Description: 
 *
 */
@Service("weiXinUserServiceImpl")
public class WeiXinUserServiceImpl extends CommonServiceImpl implements WeiXinUserService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WeiXinUserDao weiXinUserDao;
	@Autowired
	private WeiXinAccessTokenService accessTokenService;
	
	@Value("${weixin.appid}")
    protected String appid;

    @Value("${weixin.appsecret}")
    protected String appsecret;
    
	@Override
	public WeiXinUserEntity findWeiXinUserInfoByOpenId(String openId) {
		return this.findUniqueByProperty(WeiXinUserEntity.class,"openid", openId);
	}

	@Override
	public List<WeiXinUserEntity> findListByStatus(String status, int page, int size) {
		return weiXinUserDao.findListByStatus(status, page, size);
	}

	@Override
	public void updateStatus(List<WeiXinUserEntity> list) {
		/*if (list == null) {
			return;
		}
		for (WeiXinUserEntity entity : list) {
			entity.setDataStatus("1");
			commonDao.saveOrUpdate(entity);
		}*/
		if(CollectionUtil.listNotEmptyNotSizeZero(list)){
			for (WeiXinUserEntity entity : list) {
				entity.setDataStatus("1");
			}
			commonDao.batchUpdate(list, 20);
		}
	}

	@Transactional
	@Override
	public void updateSubscribe() {
		logger.info("开始更新微信用户关注状态");
		//获取appId
		//获取appsecret
		//查询出所有未关注的
		List<WeiXinUserEntity> list = this.findByQueryString("from WeiXinUserEntity where subscribe != '1' or subscribe is null ");
		logger.info("需要更新的用户条数{}",list.size());
		for (WeiXinUserEntity user : list) {
			if(StringUtil.isEmpty(user.getOpenid())){
				continue;
			}
			AccessToken accessToken = accessTokenService.getAccessToken(appid, appsecret);
			if(null == accessToken){
				break;
			}
			JSONObject json = WeixinUtil.getUserInfoBase(accessToken.getToken(), user.getOpenid());
			if(json.containsKey("errmsg") || json.containsKey("errcode")){
				logger.info("opneid【\""+user.getOpenid()+"\"】,获取用户信息失败,errcode:{},errmsg{}", json.containsKey("errmsg"), json.containsKey("errcode"));
				continue;
			}
			String subscribe = "";
			if(json.containsKey("subscribe")){
				subscribe = json.getString("subscribe");
			}
			String subscribe_time = "";
			if(json.containsKey("subscribe_time")){
				subscribe_time = json.getString("subscribe_time");
			}
			user.setDataStatus("0");
			user.setSubscribe(subscribe);
			user.setSubscribe_time(subscribe_time);
			this.saveOrUpdate(user);
		}
		logger.info("结束更新微信用户关注状态");
	}
	
	@Transactional
	@Override
	public void updateSubscribe(WeiXinUserEntity user) {
		logger.info("开始更新微信用户关注状态");
		AccessToken accessToken = accessTokenService.getAccessToken(appid, appsecret);
		if(null == accessToken){
			return;
		}
		JSONObject json = WeixinUtil.getUserInfoBase(accessToken.getToken(), user.getOpenid());
		if(json.containsKey("errmsg") || json.containsKey("errcode")){
			logger.info("opneid【\""+user.getOpenid()+"\"】,获取用户信息失败,errcode:{},errmsg{}", json.containsKey("errmsg"), json.containsKey("errcode"));
			return;
		}
		String subscribe = "";
		if(json.containsKey("subscribe")){
			subscribe = json.getString("subscribe");
		}
		//获取到的关注状态和数据库存在的是否一样,不一样才去更新状态，并且准备把数据推送到tpm
		if(!subscribe.equals(user.getSubscribe())){
			String subscribe_time = "";
			if(json.containsKey("subscribe_time")){
				subscribe_time = DateUtils.unixTimestampToDateStr(json.getString("subscribe_time"),DateUtils.datetimeFormat);
			}
			user.setDataStatus("0");
			user.setSubscribe(subscribe);
			user.setSubscribe_time(subscribe_time);
		}
		user.setLast_check_time(new Date());
		this.saveOrUpdate(user);
	}
	
}