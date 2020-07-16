package com.biz.bizunited.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.entity.QuestionVerEntity;
import com.biz.bizunited.entity.RedpackageOpenEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.service.QrcodeActivityService;
import com.biz.bizunited.service.QrcodeQuestionVerService;
import com.biz.bizunited.util.SMSUtil;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.util.ValidCodeUtil;
import com.biz.bizunited.vo.QrcodeParamVo;
import com.biz.bizunited.vo.RedpackageOpenVo;
import com.biz.bizunited.wechat.util.WeixinUtil;

@Controller
@RequestMapping(value="award")
public class AwardController extends BaseController {

	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	@Autowired
	private QrcodeQuestionVerService qrcodeQuestionVerService;
	
	
	
	@Autowired
	private CommonService commonService;
	/**
	 * 中奖纪录页面
	 * @param request
	 * @param paramVo 
	 * @return
	 */
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public ModelAndView record(HttpServletRequest request, QrcodeParamVo paramVo) {
		logger.info("record");
		String openId = WeixinUtil.getUserOpenId();
		paramVo.setOpenId(openId );
		List<RedpackageOpenVo> cashGift = qrcodeActivityService.findRedpackageOpenList(paramVo);
		
		ModelAndView mv = new ModelAndView("award/record");
		mv.addObject("dataList",cashGift);
		super.setWxCfg2MV(request, mv);
		return mv;
	}
	
	/**
	 * 红包排行榜
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rank", method = RequestMethod.GET)
	public ModelAndView rank(HttpServletRequest request) {
		logger.info("rank");
		ModelAndView mv = new ModelAndView("award/rank");
		
		try {
			String actId = (String) request.getSession().getAttribute("actId");
			
			//rankingList
			List<RedpackageOpenVo> rankingList = qrcodeActivityService.rankingList(actId);
			//中奖名单
			List<RedpackageOpenVo> winningNameList = qrcodeActivityService.winningNameList(actId);
			mv.addObject("one", rankingList.size() > 0 ? rankingList.get(0) : null);
			mv.addObject("two", rankingList.size() > 1 ? rankingList.get(1) : null);
			mv.addObject("three", rankingList.size() > 2 ? rankingList.get(2) : null);
			
			String openId = WeixinUtil.getUserOpenId();
			RedpackageOpenVo myRank = new RedpackageOpenVo();
			for (RedpackageOpenVo redpackageOpenVo : rankingList) {
				String takeName = redpackageOpenVo.getTakeName();
				if (openId.equals(takeName)) {
					myRank = redpackageOpenVo;
					break;
				}
			}
			mv.addObject("myRank", myRank);
			
			List<RedpackageOpenVo> secondList = new ArrayList<>();
			for (RedpackageOpenVo redpackageOpenVo : winningNameList) {
				String mobilePhone = redpackageOpenVo.getMobilePhone();
				if (mobilePhone != null && mobilePhone.length() > 10) {
//					String ad = "三全食品";
					String ad = "****";
					mobilePhone = mobilePhone.substring(0, 3)+ad+mobilePhone.substring(7, mobilePhone.length());
				}
				redpackageOpenVo.setMobilePhone(mobilePhone);
				secondList.add(redpackageOpenVo);
			}
			mv.addObject("winningNameList", secondList);
			mv.addObject("openId", openId);
			super.setWxCfg2MV(request, mv);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			mv.addObject("msg", "系统异常");
			mv.setViewName("common/error");
		}
		return mv;
	}
	
	/**
	 * 提现
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView send(QrcodeParamVo paramVo,HttpServletRequest request) {
		logger.info("award");
		ModelAndView mv = new ModelAndView("redirect:/award/send_success.do");
		try {
			//跳转成功之前校验是否已经提现
			String redId = paramVo.getRedId();
			if (StringUtil.isNotEmpty(redId)) {
				RedpackageOpenEntity redpackage = qrcodeActivityService.getEntity(RedpackageOpenEntity.class, redId);
				if (StringUtil.isNotEmpty(redpackage) && StringUtil.isNotEmpty(redpackage.getIsSendout()) && 1==redpackage.getIsSendout()) {
					mv = new ModelAndView("common/error");
					mv.addObject("msg", "该红包已经提现");
					return mv;
				}
			}
			//判断用户是否选中了连以前未提现的红包一起领取
			String flag = paramVo.getFlag();
			if (flag != null && !"".equals(flag.trim())  && !flag.equals("false")) {
				paramVo.setFlag("true");
			}else{
				paramVo.setFlag("false");
			}
			String phone = request.getParameter("phone");
			String channel = request.getParameter("channel");
			String validCode = request.getParameter("validCode");
			String bonus = request.getParameter("bonus");
			String longitude = request.getParameter("longitude");
			String latitude = request.getParameter("latitude");
			String questionVer = request.getParameter("questionVer");
			mv.addObject("bonus", bonus);
			mv.addObject("bonusValue", bonus);
			try {
				String sql="insert into qrcode_order_time (id,openid,time) values (?,?,?)";
				commonService.executeSql(sql,paramVo.getUuid(),WeixinUtil.getUserOpenId(),System.currentTimeMillis());
			} catch (Exception e) {
				return mv;
			}
			TerminalInfoEntity infoEntity = new TerminalInfoEntity();
			String openId = WeixinUtil.getUserOpenId();
			infoEntity.setOpenid(openId);
			paramVo.setOpenId(openId);
			infoEntity.setMobilePhone(phone);
			infoEntity.setChannel(channel);
			infoEntity.setLongitude(longitude);
			infoEntity.setLatitude(latitude);
			if (Double.parseDouble(bonus) < 1) {
				//String openId = WeixinUtil.getUserOpenId();
				paramVo.setOpenId(openId);
				paramVo.setBool("false");
				qrcodeActivityService.changeTakeName(paramVo,infoEntity);
				QuestionVerEntity questionVerEntity1 = new QuestionVerEntity();
				questionVerEntity1.setOpenid(openId);
				questionVerEntity1.setQuestionver(questionVer);
				qrcodeQuestionVerService.saveOrUpdateQuestionVerEntity(questionVerEntity1);
				mv.addObject("msg", "由于微信支付低于一元无法提现限制,您的红包金额<span>"+bonus+"</span>元 ,已经放入红包余额。请再扫一箱吧！");
				super.setWxCfg2MV(request, mv);
				mv.setViewName("common/notice");
				return mv;
			}
			qrcodeActivityService.saveTerminalInfo(infoEntity, paramVo);
			//更新用户填写问卷版本
			try {
				QuestionVerEntity questionVerEntity1 = new QuestionVerEntity();
				questionVerEntity1.setOpenid(openId);
				questionVerEntity1.setQuestionver(questionVer);
				qrcodeQuestionVerService.saveOrUpdateQuestionVerEntity(questionVerEntity1);
			} catch (Exception e) {
				e.printStackTrace();
				mv.addObject("msg", e.getMessage());
				mv.setViewName("common/error");
				return mv;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "红包提现失败,请重新扫码！";
			if(StringUtil.isNotEmpty(e.getMessage())){
				if("该红包已属于其他人，请重新扫码".equals(e.getMessage())){
					msg = e.getMessage();
				}
			}
			mv.addObject("msg", msg);
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	/**
	 * 发送成功
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send_success", method = RequestMethod.GET)
	public ModelAndView send_success(HttpServletRequest request) {
		logger.info("send_success");
		ModelAndView mv = new ModelAndView("award/send");
		//正常情况
		String bonus = request.getParameter("bonusValue");
		mv.addObject("bonus", bonus);
		super.setWxCfg2MV(request, mv);
		return mv;
	}
	
	
	@RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePhone(HttpServletRequest request) {
		logger.info("updatePhone");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String phone = request.getParameter("phone");
			String redId = request.getParameter("redId");
			qrcodeActivityService.updatePhone(id, phone,redId);
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
			logger.error(e.toString());
		}
		return map;
	}
	
	/**
	 * 发送验证码到手机
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendValidCode(String phone) {
		logger.info("sendValidCode");
		Map<String, Object> map = new HashMap<String, Object>();
		if(phone != null && !"".equals(phone.trim())){
			int code = ValidCodeUtil.obtainCodeByPhone(phone);
			String respMsg = SMSUtil.sendValidCode(phone, code);
			map.put("msg", respMsg);
		}
		return map;
	}
	
	/**
	 * 校验验证码
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/validCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validCode(String phone,String code) {
		logger.info("validCode");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = ValidCodeUtil.validCode(phone, code);
		} catch (Exception e) {
			logger.error("校验短信验证码出错,错误原因:{}",e);
		}
		return map;
	}
	/**
	 * 补充门店信息
	 * @param paramVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/terminalInfo", method = RequestMethod.POST)
	public ModelAndView terminalInfoSave(QrcodeParamVo paramVo,HttpServletRequest request){
		ModelAndView view = new ModelAndView("redirect:/award/send_success.do");
		String redId = paramVo.getRedId();
		try {
			if (StringUtil.isNotEmpty(redId)) {
				RedpackageOpenEntity redpackage = qrcodeActivityService.getEntity(RedpackageOpenEntity.class, redId);
				if (StringUtil.isNotEmpty(redpackage) && StringUtil.isNotEmpty(redpackage.getIsSendout()) && 1==redpackage.getIsSendout()) {
					view = new ModelAndView("common/error");
					view.addObject("msg", "该红包已经提现");
					return view;
				}
			}
			//判断用户是否选中了连以前未提现的红包一起领取
			String flag = paramVo.getFlag();
			if (flag != null && !"".equals(flag.trim())  && !flag.equals("false")) {
				paramVo.setFlag("true");
			}else{
				paramVo.setFlag("false");
			}
			String channel = request.getParameter("channel");
			String bonus = request.getParameter("bonus");
			String storeName = request.getParameter("storeName");
			String storeCity = request.getParameter("storeCity");
			String storeDistrict = request.getParameter("storeDistrict");
			String contacts = request.getParameter("contacts");
			String terminalId = request.getParameter("terminalId");
			String questionVer = request.getParameter("questionVer");
			//TerminalInfoEntity terminalInfoEntity = new TerminalInfoEntity();
			TerminalInfoEntity terminalInfoEntity = commonService.getEntity(TerminalInfoEntity.class, terminalId);
			terminalInfoEntity.setChannel(channel);
			terminalInfoEntity.setStoreName(storeName);
			terminalInfoEntity.setStoreCity(storeCity);
			terminalInfoEntity.setStoreDistrict(storeDistrict);
			terminalInfoEntity.setContacts(contacts);
			terminalInfoEntity.setDataStatus("0");
			view.addObject("bonus", bonus);
			view.addObject("bonusValue", bonus);
			try {
				String sql="insert into qrcode_order_time (id,openid,time) values (?,?,?)";
				commonService.executeSql(sql,paramVo.getUuid(),WeixinUtil.getUserOpenId(),System.currentTimeMillis());
			} catch (Exception e) {
				return view;
			}
			String openId = WeixinUtil.getUserOpenId();
			paramVo.setOpenId(openId);
			paramVo.setStatus("update");
			if (Double.parseDouble(bonus) < 1) {
				//String openId = WeixinUtil.getUserOpenId();
				paramVo.setBool("false");
				qrcodeActivityService.changeTakeName(paramVo,terminalInfoEntity);
				//更新用户填写问卷版本
				QuestionVerEntity questionVerEntity = qrcodeQuestionVerService.findQuestionVerByopenId(openId);
				questionVerEntity.setQuestionver(questionVer);
				qrcodeQuestionVerService.saveOrUpdateQuestionVerEntity(questionVerEntity);
				view.addObject("msg", "由于微信支付低于一元无法提现限制,您的红包金额<span>"+bonus+"</span>元 ,已经放入红包余额。请再扫一箱吧！");
				super.setWxCfg2MV(request, view);
				view.setViewName("common/notice");
				return view;
			}
			qrcodeActivityService.updateTerminalInfo(terminalInfoEntity, paramVo);
			
			//更新用户填写问卷版本
			QuestionVerEntity questionVerEntity = qrcodeQuestionVerService.findQuestionVerByopenId(openId);
			questionVerEntity.setQuestionver(questionVer);
			qrcodeQuestionVerService.saveOrUpdateQuestionVerEntity(questionVerEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "红包提现失败,请重新扫码！";
			if(StringUtil.isNotEmpty(e.getMessage())){
				if("该红包已属于其他人，请重新扫码".equals(e.getMessage())){
					msg = e.getMessage();
				}
			}
			view.addObject("msg", msg);
			view.setViewName("common/error");
		}
		return view;
	}
}
