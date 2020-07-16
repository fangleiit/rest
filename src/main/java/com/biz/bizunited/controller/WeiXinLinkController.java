package com.biz.bizunited.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.service.QrcodeActivityService;
import com.biz.bizunited.service.WeiXinAccessTokenService;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.vo.QrcodeParamVo;
import com.biz.bizunited.vo.RedpackageVo;
import com.biz.bizunited.wechat.util.WeixinUtil;


@Controller
@RequestMapping("link")
public class WeiXinLinkController extends BaseController{
	
	@Autowired
	private WeiXinAccessTokenService accessTokenService;
	
	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	/**
	 * 对外提供访问地址
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void link(HttpServletRequest request,HttpServletResponse response){
		logger.info("获取需要跳转的地址 -----queryString:{}",request.getQueryString());
		String backUrl = WeixinUtil.getRequestUrlWithParams(request);
		String openid = WeixinUtil.getUserOpenId();
		logger.info("openid:{}",openid);
		//如果不验证浏览器访问来源，则默认一个openid,以供开发测试使用
		if(!validateAgent){
			openid = "oI3WwwcU1QA6cXPQpTK0h0POvhWs";
			request.getSession().setAttribute(Constants.USER_OPENID, openid);
		}
		//跳转出链接
		String outer_link_deal = null;
		//如果为空则调用author2.0接口
		if(StringUtils.isEmpty(openid)){
			 outer_link_deal = accessTokenService.callWeixinAuthor2ReturnUrl(request, backUrl,weixinAppId,weixinAppSecret);
		}
		if(StringUtils.isEmpty(outer_link_deal)){
			String query = "";
			if(StringUtil.isNotEmpty(request.getQueryString())){
				query = request.getQueryString().split("&")[0];
			}
			outer_link_deal = request.getContextPath()+query;
		}
		logger.info("outer_link_deal:{}",outer_link_deal);
		try {
			//---update-begin--author:scott-----date:20151127-----for:参数加签名----------------------------------
			if(outer_link_deal.indexOf("https://open.weixin.qq.com")!=-1){
				//针对调整到auth2.0链接不加签名
				response.sendRedirect(outer_link_deal);
			}else{
				response.sendRedirect(outer_link_deal);
			}
			//---update-end--author:scott-----date:20151127-----for:参数加签名----------------------------------
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="redPacketRankingList",method=RequestMethod.GET)
	public ModelAndView redPacketRankingList(HttpServletRequest request,HttpServletResponse response){
		String openid = WeixinUtil.getUserOpenId();
		logger.info("openid:{},redPacketRankingList Request",openid);
		ModelAndView view = new ModelAndView("link/redPacket_rankingList");
		//根据openId查询红包总金额、未领取金额、红包领取记录
		RedpackageVo redpackageVo = qrcodeActivityService.getRedpackageByOpenId(openid);
		view.addObject("vo", redpackageVo);
		return view;
	}
	
	/**
	 * 提现
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView send(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/award/send_success.do");
		QrcodeParamVo paramVo = new QrcodeParamVo();
		paramVo.setOpenId( WeixinUtil.getUserOpenId());
		paramVo.setFlag("true");
		try {
			String bonus = request.getParameter("bonus");
			mv.addObject("bonusValue", bonus);
			qrcodeActivityService.getRedPackageBonus(paramVo);
		} catch (CommonException e) {
			e.printStackTrace();
			String msg = "红包提现失败,请您稍后继续！";
			mv.addObject("msg", msg);
			mv.setViewName("common/error");
		}
		return mv;
	}
}
