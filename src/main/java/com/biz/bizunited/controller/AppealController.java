package com.biz.bizunited.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.entity.AppealEntity;
import com.biz.bizunited.service.QrcodeActivityService;
import com.biz.bizunited.util.EmojiFilter;
import com.biz.bizunited.util.MapsUtil;
import com.biz.bizunited.vo.AddressVo;
import com.biz.bizunited.wechat.util.WeixinUtil;

/**
 * 申诉
 * @author xiaogang
 *
 */
@Controller
@RequestMapping("appeal")
public class AppealController extends BaseController{

	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	
	
	/**
	 * 申诉主页
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView appeal(HttpServletRequest request) {
		logger.info("name");
		ModelAndView mv = new ModelAndView("appeal/appeal");
		String redId = request.getParameter("redId");
		mv.addObject("redId", redId);
		String openid = WeixinUtil.getUserOpenId();
		
		
		//AppealEntity entity = qrcodeActivityService.findUniqueByProperty(AppealEntity.class, "redid", redId);
		AppealEntity entity = qrcodeActivityService.findAppealEntity(redId, openid);
		if (entity != null) {
			mv.setViewName("redirect:/appeal/save_success.do");
			return mv;
		}
		
		super.setWxCfg2MV(request, mv);
		
		return mv;
	}
	
	/**
	 * 保存申诉数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request,@RequestParam MultipartFile[] files,AppealEntity appealEntity) {
		logger.info("save");
		ModelAndView mv = new ModelAndView("redirect:/appeal/save_success.do");
		
		try {
			String openid = WeixinUtil.getUserOpenId();
			appealEntity.setOpenid(openid );
			qrcodeActivityService.uploadPicture(appealEntity, files, request);
		} catch (IOException e) {
			e.printStackTrace();
			mv.addObject("msg", "系统错误，请重试");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	/**
	 * 保存申诉数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveAppeal", method = RequestMethod.POST)
	public ModelAndView saveAppeal(HttpServletRequest request,String[] refId,AppealEntity appealEntity) {
		logger.info("save");
		ModelAndView mv = new ModelAndView("redirect:/appeal/save_success.do");
		try {
			String openid = WeixinUtil.getUserOpenId();
			String note = EmojiFilter.filterEmoji2(appealEntity.getNote());
			appealEntity.setOpenid(openid);
			appealEntity.setNote(note);
			qrcodeActivityService.saveAppeal(appealEntity, refId);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "系统错误，请重试");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/save_success", method = RequestMethod.GET)
	public ModelAndView save_success(HttpServletRequest request) {
		logger.info("appeal");
		ModelAndView mv = new ModelAndView("appeal/save_success");
		super.setWxCfg2MV(request, mv);
		return mv;
	}
	
	@RequestMapping(value = "/getAddress", method = RequestMethod.POST)
	@ResponseBody
	public AddressVo getAddress(HttpServletRequest request, String longitude, String latitude) {
		logger.info("getAddress");
		AddressVo address = MapsUtil.getAddress(longitude, latitude);
		
		return address;
	}
}
