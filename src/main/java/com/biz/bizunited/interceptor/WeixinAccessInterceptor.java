package com.biz.bizunited.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.wechat.util.WeixinUtil;

/**
 * @ClassName: WeixinAccessInterceptor 
 * @Description: 微信登录拦截器 
 * @author knight.xie
 * @date 2017年9月5日 下午12:00:24
 */
public class WeixinAccessInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WeixinAccessInterceptor.class);
    private static final String ERROR_PAGE = "/home/errorPage.do";
    private static final String HOME_PAGE = "/home.do";
    private static final String SHARE_PAGE = "/share.do";
    private static final String LINK_PAGE = "/link.do";
    @Value("${validate_agent}")
    protected Boolean validateAgent;
    
    private List<String> excludeUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("weixin access interceptor");
        String reqUrl = request.getRequestURI();
        logger.info("access path :{}",reqUrl);
        //如果访问源不来自于微信
        if(reqUrl.contains("dataTransfer.do") || reqUrl.contains("deleteqrcodeError.do")) {
        	logger.info("数据传输接口，忽略登陆检查");
        	return true;
        } else if(validateAgent && request.getAttribute(Constants.ATTR_IS_WEIXIN) == null && !reqUrl.contains(ERROR_PAGE)){
        	logger.debug("来自外部浏览器访问，非微信浏览器访问");
        	response.sendRedirect(request.getContextPath()+ERROR_PAGE); 
        	return false;
        } else {
        	logger.debug("来自微信浏览器访问");
        	if(reqUrl.contains(SHARE_PAGE) || reqUrl.contains(LINK_PAGE)){
        		return true;
        	}
        	String openid =  WeixinUtil.getUserOpenId();
        	if(null == openid && !reqUrl.contains(HOME_PAGE) && !reqUrl.contains(ERROR_PAGE)){
        		logger.debug("没有获取到openid,重定向到首页");
        		response.sendRedirect(request.getContextPath()+HOME_PAGE); 
        		return false;
        	}else{
        		return true;
        	}
        }
    }

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
    
}