package com.biz.bizunited.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.biz.bizunited.constants.Constants;

/**
 * ????????????????????????????
 *
 */
public class AgentInerceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AgentInerceptor.class);
    
    @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
//    	String qrcode = request.getParameter("qrcode");
//    	String sysurl = String.format(Constants.SYS_URL,qrcode);
//    	response.sendRedirect(sysurl); 
//    	return false;
    	//设置活动ID
    	String actId = request.getParameter("actId");
    	String uri = request.getRequestURI();
    	if (uri.contains("/home/toHomePage")) {
    		request.getSession().setAttribute("actId", actId);
		}
        boolean spider = false;
        String agent = request.getHeader("User-Agent");
        logger.debug("agent is {}", agent);
        if (agent != null) {
            agent = agent.toLowerCase();
            if (agent.contains("bot")) {
                spider = true;
            } else if (agent.contains("spider")) {
                spider = true;
            } else if (agent.contains("yahoo")) {
                spider = true;
            }
            if (spider) {
                request.setAttribute(Constants.ATTR_AGENT_SPIDER, true);
            } else {
                if (agent.contains("micromessenger")) {
                    request.setAttribute(Constants.ATTR_IS_WEIXIN, true);
                }
                if (agent.contains("qq/")) {
                    request.setAttribute(Constants.ATTR_IS_QQ, true);
                }
                if (agent.contains("mqqbrowser")) {
                    request.setAttribute(Constants.ATTR_IS_MOBILE_QQ_BROWSER, true);
                }
                if (agent.contains("ucbrowser")) {
                    request.setAttribute(Constants.ATTR_IS_MOBILE_UC_BROWSER, true);
                }
                if (StringUtils.containsIgnoreCase(agent, "android")) {
                    request.setAttribute(Constants.ATTR_AGENT_ANDROID, true);
                    request.setAttribute(Constants.ATTR_IS_MOBILE, true);
                } else if (StringUtils.containsIgnoreCase(agent, "iphone") || StringUtils
                    .containsIgnoreCase(agent, "ipod") || StringUtils
                    .containsIgnoreCase(agent, "ipad")) {
                    request.setAttribute(Constants.ATTR_AGENT_IOS, true);
                    request.setAttribute(Constants.ATTR_IS_MOBILE, true);
                } else if (StringUtils.containsIgnoreCase(agent, "Mobile")) {
                    request.setAttribute(Constants.ATTR_IS_MOBILE, true);
                } else if (StringUtils.containsIgnoreCase(agent, "AppleWebKit") && !StringUtils
                    .containsIgnoreCase(agent, "Mac") && !StringUtils
                    .containsIgnoreCase(agent, "Windows")) {
                    request.setAttribute(Constants.ATTR_IS_MOBILE, true);
                }
            }
        }
        return true;
    }


}
