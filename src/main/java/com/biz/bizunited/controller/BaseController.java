/**
 * @Description: 
 * @ClassName: com.biz.bizunited.controller.BaseController
 * @author: TRivers.chen(Administrator)
 * @date: 2016年4月28日 上午10:14:32 
 */
package com.biz.bizunited.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.exception.CommonRuntimeException;
import com.biz.bizunited.exception.ExceptionCode;
import com.biz.bizunited.json.JSONResult;
import com.biz.bizunited.service.WeiXinAccessTokenService;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.wechat.bean.AccessToken;
import com.biz.bizunited.wechat.util.WeixinUtil;

/**
 * 公共Ctroller
 * 主要用来验证参数/转换 错误信息反写
 * @Description: 
 * @ClassName: com.biz.bizunited.controller.BaseController
 * @author: TRivers.chen(Administrator)
 * @date: 2016年4月28日 上午10:14:32 
 *
 */
@Controller
public class BaseController {

	@Autowired
	private WeiXinAccessTokenService accessTokenService;
	
	@Value("${weixin.appid}")
    protected String weixinAppId;

    @Value("${weixin.appsecret}")
    protected String weixinAppSecret;
    
    @Value("${validate_agent}")
    protected Boolean validateAgent;
	
	protected Logger logger = null;
	
	{
		logger = LoggerFactory.getLogger(getClass());
	}
	
	protected void setWxCfg2MV(HttpServletRequest request,ModelAndView mv) {
		Map<String, Object> wxCfgMap = getWXCfgMap(request);
		mv.addObject("wxConfig", wxCfgMap);
	}
	
	//获取微信JS配置
	protected Map<String, Object> getWXCfgMap(HttpServletRequest request) {
		//当前时间
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		//随机字符串
		String nonceStr = UUID.randomUUID().toString();
		//当前请求的URL
		String url = request.getScheme()+"://"+request.getServerName()+request.getRequestURI();
		//获取请求地址参数
		String queryParam = request.getQueryString();
		if(!StringUtil.isEmpty(queryParam)){
			url+="?"+queryParam;
		}
		//获取AccessToken
		AccessToken accessToken = accessTokenService.getAccessToken(weixinAppId, weixinAppSecret);
		//获取js调用凭证
		String jsapi_ticket = accessToken.getJsapiticket();
		//拼装需要签名的数据
		String need_make_string = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
		//获取签名
		String signature = WeixinUtil.getJspAPISignature(need_make_string);
		//拼装数据并回返
		Map<String, Object> wxConfig = new HashMap<String, Object>();
		wxConfig.put("timestamp", timestamp);
		wxConfig.put("nonceStr", nonceStr);
		wxConfig.put("signature", signature);
		wxConfig.put("appid", weixinAppId);
		return wxConfig;
	}
	
    private static final Logger commonExceptionLog =
        LoggerFactory.getLogger("com.depotnearby.rest.CommonException");

    private static final Logger exceptionLog =
        LoggerFactory.getLogger("com.depotnearby.rest.exception");

    /**
     * 异常处理 如果是 CommonException 应该是主动抛出异常,有错误代码,否则是期望外的异常.应该分别记录日志
     *
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class}) public ModelAndView exception(Exception e) {
    	ModelAndView view = new ModelAndView("common/error");
    	String msg = "";
         if (e instanceof CommonException) {
            CommonException ge = (CommonException) e;
            commonExceptionLog
                .debug("CommonException code:{} Message:{}", ge.getCode(), ge.getMessage(), e);
            msg = ge.getMessage();
        } else if (e instanceof CommonRuntimeException) {
            CommonRuntimeException ge = (CommonRuntimeException) e;
            commonExceptionLog
                .debug("CommonRuntimeException code:{} Message:{}", ge.getCode(), ge.getMessage(), e);
            msg = ge.getMessage();
        } else {
            exceptionLog.warn("{} Message:{}", e.getClass().getName(), e.getMessage(), e);
            msg = e.getMessage();
        }
        view.addObject("msg", msg);
        return view;
    }

}
