package com.biz.bizunited.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.minidao.util.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.common.AjaxJson;
import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.entity.QrcodeErrorEntity;
import com.biz.bizunited.entity.QrcodeInfoEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.service.QrcodeActivityService;
import com.biz.bizunited.service.QrcodeInfoService;
import com.biz.bizunited.service.ValidateRuleService;
import com.biz.bizunited.service.WeiXinAccessTokenService;
import com.biz.bizunited.util.DateUtils;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.vo.QrcodeActInfoVo;
import com.biz.bizunited.vo.QrcodeParamVo;
import com.biz.bizunited.vo.QrcodeUserInfoSeriesVo;
import com.biz.bizunited.vo.RedpackageOpenVo;
import com.biz.bizunited.wechat.util.WeixinUtil;


/**
 * @Description 扫码活动入口
 * @author knight
 */
@Controller("homeIndexController")
@RequestMapping("/home")
public class HomeIndexController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(HomeIndexController.class);
	

	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	
	@Autowired
	private WeiXinAccessTokenService accessTokenService;
	@Autowired
	private ValidateRuleService validateRuleService;
	@Autowired
	private QrcodeInfoService qrcodeInfoService;
	@Autowired
	private CommonService commonService;
	/**
	 * 扫描二维码调用接口,用于获取扫码人的openId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void index(HttpServletRequest request,HttpServletResponse response){
		String backUrl = WeixinUtil.getRequestUrlWithParams(request);
		String openid = WeixinUtil.getUserOpenId();
		logger.info("openid:{}",openid);
		//如果不验证浏览器访问来源，则默认一个openid,以供开发测试使用
		if(!validateAgent){
			openid = "testopenid11";
			request.getSession().setAttribute(Constants.USER_OPENID, openid);
		}
		//跳转出链接
		String outer_link_deal = null;
		//如果为空则调用author2.0接口
		if(StringUtils.isEmpty(openid)){
			 outer_link_deal = accessTokenService.callWeixinAuthor2ReturnUrl(request, backUrl,weixinAppId,weixinAppSecret);
		}
		if(StringUtils.isEmpty(outer_link_deal)){
			String queryStr = request.getQueryString();
			outer_link_deal = request.getContextPath()+"/home/toHomePage.do?"+queryStr;//跳转到活动页
		}
		logger.info("outer_link_deal {}",outer_link_deal);
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
	
	/**
	 * 跳转到首页页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toHomePage",method = RequestMethod.GET)
	public ModelAndView toHomePage(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 活动结束跳转防篡平台start（活动开始删除该代码，打开下面//注释）
		 */
//		ModelAndView view = new ModelAndView();
//		String qrcode = request.getParameter("qrcode");
//		if(true){
//			String sysurl = String.format(Constants.SYS_URL,qrcode);
//			view.setViewName("redirect:" + sysurl);
//		}
//		return view;
		/**
		 * 活动结束跳转防篡平台end
		 */
		
		logger.info("toLotteryPage Request");
		ModelAndView view = new ModelAndView();
		String openId = WeixinUtil.getUserOpenId();
		if(StringUtil.isEmpty(openId)){
			openId = request.getParameter("openId");
		}
		String productId = request.getParameter("productId");
		String shopId = request.getParameter("shopId");
		//获取出库时间
		String deliveryTime = request.getParameter("deliveryTime");
		String actId = request.getParameter("actId");
		String qrcode = request.getParameter("qrcode");
		try {
			validateRuleService.validationUser(openId,qrcode);
			QrcodeInfoEntity qrcodeInfo = qrcodeInfoService.getQrcode(qrcode);
			if(qrcodeInfo != null) {
				validateRuleService.validationQrcode(qrcodeInfo, openId);
				//查询活动
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String produceTime = dateformat.format(qrcodeInfo.getProduceTime());
				String cust = "";
				if(qrcodeInfo.getCustomerCode() != null){
					cust = qrcodeInfo.getCustomerCode().trim();
				}
				String pcode = qrcodeInfo.getProductCode().trim();
				QrcodeActInfoVo actInfoVo = qrcodeActivityService.findActInfo(cust, pcode, produceTime);
				if(actInfoVo == null){
					String sysurl = String.format(Constants.SYS_URL,qrcode);
					view.setViewName("redirect:" + sysurl);
					return view;
				}
				actId = actInfoVo.getId();
			}else{
				QrcodeErrorEntity errorq= new QrcodeErrorEntity();
				errorq.setOpenid(openId);
				errorq.setQrcode(qrcode);
				errorq.setCreateTime(DateUtils.getDate());
				commonService.save(errorq);
				validateRuleService.validateionCount(openId);
				String msg = "二维码信息错误，请重新扫码！";
				view.addObject("msg", msg);
				view.setViewName("common/error");
				return view;
			}
		} catch (CommonException e) {
			e.printStackTrace();
			logger.error("进入首页异常：" + e);
			String msg = e.getMessage();
			view.addObject("msg", msg);
			view.setViewName("common/error");
			return view;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("进入首页异常：" + e);
			String msg = "系统内部错误,请重新扫码";
			view.addObject("msg", msg);
			view.setViewName("common/error");
			return view;
		}
		logger.info("productId {}, shopId {}, deliveryTime {}, openId {}",productId,shopId,deliveryTime,openId);
		//ModelAndView view = new ModelAndView("index/index");
		view.addObject("productId", productId);
		view.addObject("shopId", shopId);
		view.addObject("deliveryTime", deliveryTime);
		view.addObject("actId", actId);
		view.addObject("qrcode", qrcode);
		view.setViewName("index/index");
		//获取微信地理位置信息示例
		
		/*Map<String, Object> wxConfig = super.getWXCfgMap(request);
		view.addObject("wxConfig", wxConfig);*/
		return view;
	}
	/**
	 * 获取所有未领取红包
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getRedPackageAll")
	@ResponseBody
	public AjaxJson getRedPackageAll(QrcodeParamVo paramVo,HttpServletRequest request){
		logger.info("未领取红包 getRedPackageAll Request");
		AjaxJson j = new AjaxJson();
		String openId = WeixinUtil.getUserOpenId();
		paramVo.setOpenId(openId);
		List<RedpackageOpenVo> openVos = qrcodeActivityService.findRedpackageOpenList(paramVo);
		j.setObj(openVos);
		return j;
	}
	/**
	 * 领取红包
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getRedPackageBonus")
	@ResponseBody
	public AjaxJson getRedPackageBonus(QrcodeParamVo paramVo,HttpServletRequest request){
		logger.info("领取红包getRedPackageBonus Request");
		AjaxJson j = new AjaxJson();
		String openId = WeixinUtil.getUserOpenId();
		//判断是否需要填写信息
		QrcodeUserInfoSeriesVo infoSeriesVo = qrcodeActivityService.findInfoConfig(paramVo.getActId());
		//根据活动查询用户在此活动已扫过多少次码
		int count = qrcodeActivityService.findScanCodeByOpenId(openId, paramVo.getActId());
		int num = infoSeriesVo.getCount()-1;
		if(count == num){
			//需要填写信息
			String url = infoSeriesVo.getUrl();
			j.setType(1);
			j.setObj(url);
		}else{
			paramVo.setOpenId(openId);
			try {
				qrcodeActivityService.getRedPackageBonus(paramVo);
			} catch (CommonException e) {
				e.printStackTrace();
			}
		}
		return j;
	}
	/**
	 * 非微信扫码跳转页面
	 * @return
	 */
	@RequestMapping(value = "errorPage",method = RequestMethod.GET)
	public String errorPage(){
		return "index/errorPage";
	}
	
	
	/**
	 * 中奖纪录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "record",method = RequestMethod.GET)
	public String record(HttpServletRequest request,HttpServletResponse response){
		logger.info("中奖纪录请求 record Request");
		QrcodeParamVo paramVo = new QrcodeParamVo();
		String openId = WeixinUtil.getUserOpenId();
		paramVo.setOpenId(openId);
		List<RedpackageOpenVo> openVos = qrcodeActivityService.findRedpackageOpenList(paramVo);
		
		//这个需要替换成中奖纪录页面
		return "index/errorPage";
	}
	/**
	 * 保存用户信息
	 * @param infoEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveInfo")
	@ResponseBody
	public AjaxJson saveTerminalInfo(TerminalInfoEntity infoEntity,QrcodeParamVo paramVo,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String openId = WeixinUtil.getUserOpenId();
		String message = "用户信息保存成功";
		TerminalInfoEntity entity = null;
		entity = qrcodeActivityService.findUniqueByProperty(TerminalInfoEntity.class, "openid", openId);
		try {
			//保存用户信息
			/*if(entity != null){
				MyBeanUtils.copyBeanNotNull2Bean(infoEntity, entity);
				qrcodeActivityService.saveOrUpdate(entity);
			}else{
				if(infoEntity.getMobilePhone() != null && infoEntity.getMobilePhone() != ""){
					entity = qrcodeActivityService.findUniqueByProperty(TerminalInfoEntity.class, "mobile_phone", infoEntity.getMobilePhone());
					if(entity != null){
						qrcodeActivityService.saveOrUpdate(entity);
					}
				}
				qrcodeActivityService.saveOrUpdate(infoEntity);
			}*/
			//保存用户信息
			if(entity == null){
				if(infoEntity.getMobilePhone() != null && infoEntity.getMobilePhone() != ""){
					entity = qrcodeActivityService.findUniqueByProperty(TerminalInfoEntity.class, "mobilePhone", infoEntity.getMobilePhone());
				}
			}
			MyBeanUtils.copyBeanNotNull2Bean(infoEntity, entity);
			entity.setOpenid(openId);
			qrcodeActivityService.saveOrUpdate(infoEntity);
			//领取红包
			paramVo.setOpenId(openId);
			qrcodeActivityService.getRedPackageBonus(paramVo);
		} catch (Exception e) {
			e.printStackTrace();
			j.setCode(0);
			j.setMsg("系统内部错误，红包领取失败！");
		}
		return j;
	}
	
}
