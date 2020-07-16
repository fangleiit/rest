package com.biz.bizunited.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.dao.WeiXinAccessTokenDao;
import com.biz.bizunited.entity.WeiXinAccessTokenEntity;
import com.biz.bizunited.entity.WeiXinUserEntity;
import com.biz.bizunited.service.WeiXinAccessTokenService;
import com.biz.bizunited.service.WeiXinUserService;
import com.biz.bizunited.util.DateUtils;
import com.biz.bizunited.util.EmojiFilter;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.wechat.bean.AccessToken;
import com.biz.bizunited.wechat.util.OAuth2Util;
import com.biz.bizunited.wechat.util.WeixinUtil;


/**
 * 获取微信公众号AccessToken
 * @Description: 
 *
 */
@Service("weixinccessTokenServiceImpl")
public class WeiXinAccessTokenServiceImpl extends CommonServiceImpl implements WeiXinAccessTokenService{
	private static final Logger logger = LoggerFactory.getLogger(WeiXinAccessTokenServiceImpl.class);
	@Autowired
	private WeiXinAccessTokenDao accessTokenDao;
	@Autowired
	private WeiXinUserService weiXinUserService;
	
	@Transactional
	@Override
	public AccessToken getAccessToken(String appid,String appsecret ) {
		
		//查询WeiXinAccessToken表中有无输数据
		List<WeiXinAccessTokenEntity> list = this.findByQueryString("from WeiXinAccessTokenEntity");
		//有数据
		if(!list.isEmpty()){
			//得到WeiXinAccessToken
			WeiXinAccessTokenEntity accessTocken = list.get(0);
    		java.util.Date end = new java.util.Date();
    		//判断当前时间-添加时间大于过期时间是
    		if(end.getTime()-accessTocken.getAddTime().getTime()>accessTocken.getExpires_in()*1000){
    			 AccessToken accessToken = null;
    			 //调用微信接口获取token
    			 JSONObject jsonObject = WeixinUtil.getAccessToken(appid, appsecret);
    			 //容错判断
				 if (null != jsonObject) {
					 //获取token失败，记录日志，方便在控制台查看
					 if (jsonObject.getBoolean("errcode") && jsonObject.getIntValue("errcode") != 0) {
						 accessToken = null;
						 logger.info("很抱歉，系统异常，请联系管理员!　错误码:{}",jsonObject.toString());
					 }else{
						 try {
							 //拼装数据
							 accessToken = new AccessToken();
	                         accessToken.setToken(jsonObject.getString("access_token"));
	                         accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
	                         accessTocken.setExpires_in(jsonObject.getIntValue("expires_in"));
	                         accessTocken.setAccess_token(jsonObject.getString("access_token"));
	                         accessTocken.setAddTime(DateUtils.getTimestamp());
	                         String jsapiticket = null;
	                         //调用微信接口JS调用凭证
	                    	 JSONObject jsapi_ticket_json = WeixinUtil.getJsapiTicket(jsonObject.getString("access_token"));
	                    	 //容错判断
	                    	 if (null != jsapi_ticket_json) {
								 jsapiticket = jsapi_ticket_json.getString("ticket");
								 accessToken.setJsapiticket(jsapiticket);
								 accessTocken.setJsapiticket(jsapiticket);
							 }
	                    	 //保存数据
							 accessTokenDao.updateByHiber(accessTocken);
	                     } catch (Exception e) {
	                    	 //容错
	                    	 accessToken = null;
	                         String wrongMessage = "获取token失败 errcode:{} errmsg:{}"+jsonObject.getIntValue("errcode")+jsonObject.getString("errmsg");
	                         logger.info(wrongMessage);
	                     }
					 }
                 }
				 //返回数据
                 return accessToken;
    		}else{
    			//还未过期，直接读取数据库中的数据，并返回数据
       		 	AccessToken  accessToken = new AccessToken();
                accessToken.setToken(accessTocken.getAccess_token());
                accessToken.setExpiresIn(accessTocken.getExpires_in());
                accessToken.setJsapiticket(accessTocken.getJsapiticket());
                return accessToken;
    		}
		}else{
			//如果数据库，不存在AccessToken数据
   		    AccessToken accessToken = null;
   		    //调用微信接口获取token
   		    JSONObject jsonObject = WeixinUtil.getAccessToken(appid, appsecret);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                	//拼装数据
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
                    WeiXinAccessTokenEntity atyw = new WeiXinAccessTokenEntity();
                    atyw.setExpires_in(jsonObject.getIntValue("expires_in"));
                    atyw.setAccess_token(jsonObject.getString("access_token"));
                    atyw.setAddTime(DateUtils.getTimestamp());
                    String jsapiticket = null;
                    //调用接口获取JS调用凭证
	               	JSONObject jsapi_ticket_json = WeixinUtil.getJsapiTicket(jsonObject.getString("access_token"));
	               	if (null != jsapi_ticket_json) {
						jsapiticket = jsapi_ticket_json.getString("ticket");
						atyw.setJsapiticket(jsapiticket);
		               	accessToken.setJsapiticket(jsapiticket);
					}
                    accessTokenDao.saveByHiber(atyw);
                } catch (Exception e) {
                	//容错
                    accessToken = null;
                    String wrongMessage = "获取token失败 errcode:{} errmsg:{}"+jsonObject.getIntValue("errcode")+jsonObject.getString("errmsg");
                    logger.info(wrongMessage);
                }
            }
            //返回数据
            return accessToken;
		}
	}
	
	@Transactional
	@Override
	public String callWeixinAuthor2ReturnUrl(HttpServletRequest request, String tagetUrl, String appId,
			String appSecret) {
		/**通过Oauth2.0获取openid_end**/
		String openId = request.getParameter("openid");
		if(StringUtils.isEmpty(openId)){
			openId = WeixinUtil.getUserOpenId();
		}
		if(StringUtil.isEmpty(openId)){
			//-------------------------------------------------------------------------------------------------------------------------------------
			String code = request.getParameter("code");
			logger.info("code的值="+code);
			//1.判断是否有code值,没有则跳转到授权地址
			if(StringUtil.isEmpty(code)){
				logger.info("targetURL的值="+tagetUrl);
				String redirectURL = OAuth2Util.obtainWeixinOAuth2Url(tagetUrl,appId,OAuth2Util.SNSAPI_USERINFO);
				return redirectURL;
			}
			// 2.不用用户同意即可获取了code的值
			if (!"authdeny".equals(code)) {
				JSONObject josn= WeixinUtil.getOauth2AccessToken(appId,appSecret, code);
				openId = josn.getString("openid");
				if(!StringUtil.isEmpty(openId)){
					WeiXinUserEntity  user = weiXinUserService.findWeiXinUserInfoByOpenId(openId);
					JSONObject userInfoJson = WeixinUtil.getUserInfo(josn.getString("access_token"),josn.getString("openid"));
					if(null == user){
						user = new WeiXinUserEntity();
					}
					if(null != userInfoJson.get("errcode")){
						logger.info("error_msg:{},error_code{}",userInfoJson.get("errmsg"),userInfoJson.get("errcode"));
					}else{
						user.setSex(userInfoJson.getString("sex"));
						user.setOpenid(userInfoJson.getString("openid"));
						user.setNickname(new String(EmojiFilter.filterEmoji2(userInfoJson.getString("nickname"))));
						user.setCity(userInfoJson.getString("city"));
						user.setProvince(userInfoJson.getString("province"));
						user.setCountry(userInfoJson.getString("country"));
						user.setHeadimgurl(userInfoJson.getString("headimgurl"));
						//user.setSubscribe_time(userInfoJson.getString("subscribe_time"));
						weiXinUserService.saveOrUpdate(user);
					}
				}
			}
			request.getSession().setAttribute(Constants.USER_OPENID, openId);
		}
		return null;
	
	}
}