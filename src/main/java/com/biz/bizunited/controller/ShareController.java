package com.biz.bizunited.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.entity.WeiXinUserEntity;
import com.biz.bizunited.service.QrcodeActivityService;
import com.biz.bizunited.service.WeiXinUserService;
import com.biz.bizunited.vo.RedpackageOpenVo;
import com.biz.bizunited.wechat.util.WeixinUtil;

@Controller
@RequestMapping(value="share")
public class ShareController extends BaseController{

	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	
	@Autowired
	private WeiXinUserService weiXinUserService;
	
	@RequestMapping( method = RequestMethod.GET)
	public ModelAndView share(HttpServletRequest request) {
		logger.info("share");
		ModelAndView mv = new ModelAndView("share/share");
		//通过openid（传过来的）获取该用户的
				//1.昵称
				//2.一共获取多少钱的红包
				//3.排名多少位
		
		String actId = (String) request.getSession().getAttribute("actId");
		
		//rankingList
		List<RedpackageOpenVo> rankingList = qrcodeActivityService.rankingList(actId);
		
		String userOpenId = request.getParameter("userOpenId");
		//String userOpenId = WeixinUtil.getUserOpenId();
		
		RedpackageOpenVo user = new RedpackageOpenVo();
		for (RedpackageOpenVo redpackageOpenVo : rankingList) {
			String takeName = redpackageOpenVo.getTakeName();
			if (userOpenId.equals(takeName)) {
				user = redpackageOpenVo;
				break;
			}
		}
		
		//1.昵称
		WeiXinUserEntity weiXinUserEntity = weiXinUserService.findWeiXinUserInfoByOpenId(userOpenId);
		String nickname = weiXinUserEntity == null ? "" : weiXinUserEntity.getNickname();
		//2.一共获取多少钱的红包
		BigDecimal userBonus = user.getBonus();
		//3.排名多少位
		Integer userRowno = user.getRowno();
		
		mv.addObject("nickname", nickname);
		mv.addObject("userRowno", userRowno);
		
		String type = request.getParameter("type");
		String own = "";
		if ("cash".equals(type)) {
			own = "<span>"+userBonus+"</span>元红包";
		}else{
			own = "<span>"+"ipadmini实物大奖"+"</span>";
		}
		mv.addObject("own", own);
		mv.addObject("type", type);
		mv.addObject("openId", userOpenId);
		super.setWxCfg2MV(request, mv);
		String shareUrl =request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/share.do?userOpenId="+userOpenId+"&type=cash";
		mv.addObject("shareUrl",shareUrl);
		return mv;
	}
	
}
