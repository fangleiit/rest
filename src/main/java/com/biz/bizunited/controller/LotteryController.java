package com.biz.bizunited.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.common.AjaxJson;
import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.entity.DrawShiWuEntity;
import com.biz.bizunited.entity.QuestionVerEntity;
import com.biz.bizunited.entity.RedpackageOpenEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.service.QrcodeActivityService;
import com.biz.bizunited.service.QrcodeQuestionVerService;
import com.biz.bizunited.util.CollectionUtil;
import com.biz.bizunited.util.SMSUtil;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.vo.QrcodeChannelVo;
import com.biz.bizunited.vo.QrcodeParamVo;
import com.biz.bizunited.vo.QrcodeUserInfoSeriesVo;
import com.biz.bizunited.vo.RedpackageOpenVo;
import com.biz.bizunited.wechat.util.WeixinUtil;

/**
 * 红包开奖控制器
 * 
 * @author knight
 *
 */
@Controller("lotteryController")
@RequestMapping("lottery")
public class LotteryController extends BaseController {

	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	
	@Autowired
	private QrcodeQuestionVerService qrcodeQuestionVerService;
	
	@Autowired
	private CommonService commonService;

	@Value("${weixin.appid}")
    protected String weixinAppId;

    @Value("${weixin.appsecret}")
    protected String weixinAppSecret;
    
    @Value("${validate_agent}")
    protected Boolean validateAgent;
	
	/**
	 * 跳转到开奖页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toLotteryPage(QrcodeParamVo paramVo, HttpServletRequest request, HttpServletResponse response) {
		logger.info("toLotteryPage Request");
		ModelAndView mv = new ModelAndView();
		try {
			String openId = WeixinUtil.getUserOpenId();
			if(StringUtil.isEmpty(openId)){
				openId = request.getParameter("openId");
			}
			logger.info("productId {}, shopId {}, deliveryTime {}, openId {}", openId);
			mv.addObject("paramVo", paramVo);
			
			paramVo.setOpenId(openId);
			
			
			AjaxJson result = qrcodeActivityService.openRedpackage(paramVo);
			Integer status = result.getCode();
//			Integer status = 9;
			// 1为正常，-1为黑名单,0当前扫码人领取，2为非当前扫码人领取,9特等奖
			switch (status) {
			case 1:
				//正常奖设置返回页面 ：setViewName
				UUID uuid = UUID.randomUUID();
				mv.setViewName("cashGift/openCashGift");
				mv.addObject("uuid",uuid);
				
				Object obj = result.getObj();
				Field field = obj.getClass().getDeclaredField("bonus");
				Field redId = obj.getClass().getDeclaredField("redId");
				field.setAccessible(true);
				redId.setAccessible(true);
				
				//Object bonusValue = field.get(obj);
				Object redIdValue = redId.get(obj);
				RedpackageOpenEntity openEntity = commonService.getEntity(RedpackageOpenEntity.class, redIdValue.toString());
				BigDecimal bonus = openEntity.getBonus();
				mv.addObject("bonusValue", bonus);
				mv.addObject("redIdValue", redIdValue);
				
				
				Integer isSendout = paramVo.getIsSendout();
				//查询出所有未领取的红包
				paramVo.setIsSendout(0);
				//需求变更后红包余额不加活动限制，将参数设置为空，传入前端需还原actId值
				String actId = paramVo.getActId();
				List<RedpackageOpenVo> notGetList = qrcodeActivityService.findRedpackageOpenList(paramVo);
				paramVo.setActId(actId);
				BigDecimal balance = null;
				for (RedpackageOpenVo redpackageOpenVo : notGetList) {
					if (balance == null) {
						balance = new BigDecimal(0);
					}
					balance = balance.add(redpackageOpenVo.getBonus());
				}
				mv.addObject("balance", balance);
				
				//还原当前的发放状态
				paramVo.setIsSendout(isSendout);
				
				
				break;
			case 2:
				Object object = result.getObj();
				Field redIdField = object.getClass().getDeclaredField("redId");
				redIdField.setAccessible(true);
				Object redIdVal = redIdField.get(object);
				mv.addObject("result", result);
				mv.addObject("redId", redIdVal);
				//设置返回页面
				mv.setViewName("cashGift/alreadyGet");
				break;
			case 0:
				mv.addObject("result", result);
				Field field1 = result.getObj().getClass().getDeclaredField("bonus");
				field1.setAccessible(true);
				Object bonusValue1 = field1.get(result.getObj());
				mv.addObject("bonus", bonusValue1);
				super.setWxCfg2MV(request, mv);
				mv.addObject("msg", result.getMsg());
				mv.setViewName("cashGift/sameTakePerson");
				break;
			case -1:

				break;
			case 9:
				Field declaredField = result.getObj().getClass().getDeclaredField("redId");
				declaredField.setAccessible(true);
				
				Object object2 = declaredField.get(result.getObj());
				boolean flag = true;
				List<DrawShiWuEntity> shiwuList = qrcodeActivityService.findByProperty(DrawShiWuEntity.class, "redid", object2);
				if(CollectionUtil.listNotEmptyNotSizeZero(shiwuList)){
					flag = false;
				}
				mv.addObject("redId", object2);
				mv.addObject("flag", flag);
				mv.setViewName("cashGift/surprise");
				break;
			default:
				mv.setViewName("../../error");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入开奖页面异常：" + e);
			logger.info("toLotteryPage,开奖页面出现错误,openid：" + paramVo.getOpenId());
			mv.addObject("msg", "进入开奖页面异常：" + e.getMessage());
			mv.setViewName("common/error");
			String opt = "开奖";
			sendErrorSMS(opt);
		}

		return mv;
	}
	/**
	 * 开奖发送错误时短信通知
	 * @param className
	 * @param templateCode
	 * @param content
	 */
	public void sendErrorSMS(String opt){
		logger.info("开奖出错，发送短信");
		String mobilephone = ResourceBundle.getBundle("bizunited").getString("mobilephone");
		String templateCode = ResourceBundle.getBundle("bizunited").getString("error.templateCode");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(new Date());
		String className = "LotteryController";
		String content = "{\"date\":\""+date+"\", \"className\":\""+className+"\",\"opt\":\""+opt+"\"}";
		SMSUtil.sendSms(mobilephone, content, templateCode);
	}
	/**
	 * 完善资料，领取实物大奖
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/obtainSurprise", method = RequestMethod.GET)
	public ModelAndView obtainSurprise(HttpServletRequest request) {
		logger.info("obtainSurprise");
		ModelAndView mv = new ModelAndView();
		String redid = request.getParameter("redId");
		
		String openid = WeixinUtil.getUserOpenId();
		TerminalInfoEntity drawShiWu = new TerminalInfoEntity();
		try {
			drawShiWu = qrcodeActivityService.drawShiWu(redid, openid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("短信发送失败：" + e);
			mv.addObject("msg", "短信发送失败");
			mv.setViewName("common/error");
			return mv;
		}
		mv.setViewName("cashGift/obtainSurprise");
		mv.addObject("info", drawShiWu);
		mv.addObject("redId", redid);
		super.setWxCfg2MV(request, mv);
		return mv;
	}
	
	/**
	 * 修改红包归属人
	 * @param request
	 * @param paramVo
	 * @return
	 */
	@RequestMapping(value = "/changeOwn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeOwn(HttpServletRequest request, QrcodeParamVo paramVo) {
		logger.info("changeOwn");
		Map<String, Object> map = new HashMap<>();
		boolean flag = false;
		try {
			String openId = WeixinUtil.getUserOpenId();
			paramVo.setOpenId(openId);
			qrcodeActivityService.changeTakeName(paramVo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更改领取人异常：",e);
			flag = false;
			map.put("msg", "修改领取人失败");
		}
		map.put("result", flag);
		map.put("msg", "修改领取人成功");
		return map;
	}
	
	/**
	 * 完善信息领取红包
	 * 
	 * @param paramVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/obtainCash", method = RequestMethod.GET)
	public ModelAndView obtainCash(QrcodeParamVo paramVo, HttpServletRequest request) {
		logger.info("obtainCash paramVo:{}",paramVo);
		String actId = paramVo.getActId();
		
		ModelAndView mv = new ModelAndView();
		try {
			String openId = WeixinUtil.getUserOpenId();
			if(StringUtil.isEmpty(openId)){
				openId = request.getParameter("openId");
			}
			Object bonusValue = request.getParameter("bonus");
			Object redIdValue = request.getParameter("redId");
			
			//跳转成功之前校验是否已经提现
			String redId = paramVo.getRedId();
			if (StringUtil.isNotEmpty(redId)) {
				RedpackageOpenEntity redpackage = qrcodeActivityService.getEntity(RedpackageOpenEntity.class, redId);
				//if (StringUtil.isNotEmpty(redpackage) && StringUtil.isNotEmpty(redpackage.getIsSendout()) && 1==redpackage.getIsSendout()) 
				if (StringUtil.isNotEmpty(redpackage)) {
					if(StringUtil.isNotEmpty(redpackage.getIsSendout()) && 1==redpackage.getIsSendout()){
						mv = new ModelAndView("common/error");
						mv.addObject("msg", "该红包已经提现");
						return mv;
					}
					if(StringUtil.isNotEmpty(redpackage.getTakeName()) && openId.equals(redpackage.getTakeName())){
						SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String takeTime = dateformat.format(redpackage.getTakeTime());
						mv.addObject("bonus", bonusValue);
						super.setWxCfg2MV(request, mv);
						mv.addObject("msg", "您已于" + takeTime + "领取过此红包");
						mv.setViewName("cashGift/sameTakePerson");
						return mv;
					}
				}
			}
			
			mv.addObject("paramVo", paramVo);
			//判断是否需要提取未领取的其它红包,如果不为空，值为未提现的总金额
			String flag = paramVo.getFlag();
			
			
			BigDecimal bon = bonusValue != null ? new BigDecimal(bonusValue+"") : new BigDecimal(0);
			
			if (flag != null && flag != "false" && !"".equals(flag.trim())) {
				BigDecimal d = new BigDecimal(flag);
				BigDecimal total = bon.add(d);
				bonusValue = total;
				paramVo.setFlag("true");
			}
			mv.addObject("bonusValue", bonusValue);
			
			Double bv = Double.valueOf(bonusValue.toString());
			
			List<QrcodeChannelVo> channel = qrcodeActivityService.findChannel();
			mv.addObject("redIdValue", redIdValue);
			mv.addObject("channelList", channel);

			try {
				String sql="insert into qrcode_order_time (id,openid,time) values (?,?,?)";
				commonService.executeSql(sql,paramVo.getUuid(),WeixinUtil.getUserOpenId(),System.currentTimeMillis());
			} catch (Exception e) {
				mv.setViewName("redirect:/award/send_success.do");
				return mv;
			}
				//查询活动配置
				QrcodeUserInfoSeriesVo infoConfig = qrcodeActivityService.findInfoConfig(actId);
				Integer actCount = 0;
				if(infoConfig != null){
					actCount = infoConfig.getCount();
				}
				//Integer actCount = infoConfig.getCount();
				
				//查询扫码次数
				Integer scanCount = qrcodeActivityService.findScanCodeByOpenId(openId, actId);
				scanCount = scanCount + 1;
				Integer firstCount = qrcodeActivityService.findScanCodeByOpenId(openId);
				//获取该用户填写问卷版本
				QuestionVerEntity questionVerEntity = qrcodeQuestionVerService.findQuestionVerByopenId(openId);
				
				//扫码次数如果大于0，并且扫码次数不超过活动配置的次数，那么不用填写领奖信息。否则直接提现红包
				if(firstCount == 0){
					Map<String, Object> wxConfig = super.getWXCfgMap(request);
					mv.addObject("wxConfig", wxConfig);
					UUID uuid = UUID.randomUUID();
					mv.addObject("uuid",uuid);
					//第一次需要填写信息
					mv.setViewName("cashGift/obtainCash");
				}else if (scanCount >= actCount && !questionVerEntity.getQuestionver().equals("WJ002")) {
					/*Map<String, Object> wxConfig = super.getWXCfgMap(request);
					mv.addObject("wxConfig", wxConfig);*/
					TerminalInfoEntity infoEntity = commonService.findUniqueByProperty(TerminalInfoEntity.class, "openid", openId);
					String jspUrl = infoConfig.getUrl();
					mv.addObject("infoEntity",infoEntity);
					UUID uuid = UUID.randomUUID();
					mv.addObject("uuid",uuid);
					mv.setViewName(jspUrl);
				}else{
					try {
						if (bv < 1) {
							//String openId = WeixinUtil.getUserOpenId();
							paramVo.setOpenId(openId);
							qrcodeActivityService.changeTakeName(paramVo);
							mv.addObject("msg", "由于微信支付低于一元无法提现限制,您的红包金额<span>"+bv+"</span>元 ,已经放入红包余额。请再扫一箱吧！");
							super.setWxCfg2MV(request, mv);
							mv.setViewName("common/notice");
							return mv;
						}
						//提现红包，跳转到提现成功的页面
						paramVo.setOpenId(WeixinUtil.getUserOpenId());
						qrcodeActivityService.getRedPackageBonus(paramVo);
					} catch (CommonException e1) {
						e1.printStackTrace();
						mv.addObject("msg", e1.getMessage());
						mv.setViewName("common/error");
						return mv;
					}
					mv.setViewName("redirect:/award/send_success.do");
				}
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "红包领取失败，请重新扫码！";
			mv.addObject("msg", msg);
			mv.setViewName("common/error");
		}

		return mv;
	}

}
