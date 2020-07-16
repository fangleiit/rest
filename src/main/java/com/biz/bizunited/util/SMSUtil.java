package com.biz.bizunited.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;



/**
 * 短信发送工具
 * 
 * @author xiaogang
 *
 */
public class SMSUtil {
	/**
	 * 你的accessKeyId,参考本文档步骤2
	 */
	private final static String accessKeyId = "LTAIlYxUaF5K9z1l";
	/**
	 * 你的accessKeySecret，参考本文档步骤2
	 */
	private final static String accessKeySecret = "HrmkGYOH1l559fWD4iAOJkeBcJSm2I";
	
	/**
	 * 必填:短信签名-可在短信控制台中找到
	 */
	private final static String SignName = "三全餐饮";
	
	/**
	 * 必填:短信模板-可在短信控制台中找到
	 */
	private final static String TemplateCode = "SMS_79380018";
	
	/**
	 * 中奖人员电话信息变更
	 */
	public final static String PHONE_CHANGE = "SMS_89710069";
	
	/**
	 * 
	 * @param phone 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
	 * @param code  必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	 * @return responseCode 验证短信发送成功与否的结果
	 */
	public static String sendValidCode(String phone, Serializable code) {
		logger.info("sendValidCode,phone:{},validCode:{}",phone,code);
		String responseCode = null;
		try {
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", ConnectTimeout);
			System.setProperty("sun.net.client.defaultReadTimeout", ReadTimeout);
			// 初始化ascClient需要的几个参数
			final String product = "Dysmsapi";// 短信API产品名称
			final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名
			// 初始化ascClient,暂时不支持多region
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(SignName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(TemplateCode);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam("{\"code\":\""+code+"\"}");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			responseCode = sendSmsResponse.getCode();
			logger.info("sendValidCode respCode:{},respMsg:{}",responseCode,respCodeAndMsg.get(responseCode));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sendValidCode error:"+e);
		}
		return responseCode;
	}
	
	/**
	 * 用户抽中实物奖时短信通知工作人员
	 * @param phone
	 * @return
	 */
	public static String sendNotice(String province,String city,String phone,String staffPhone){
		logger.info("sendNotice,phone:{}",staffPhone);
		String responseCode = null;
		try {
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", ConnectTimeout);
			System.setProperty("sun.net.client.defaultReadTimeout", ReadTimeout);
			// 初始化ascClient需要的几个参数
			final String product = "Dysmsapi";// 短信API产品名称
			final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名
			// 初始化ascClient,暂时不支持多region
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(staffPhone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(SignName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode("SMS_86420018");
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam("{\"province\":\""+province+"\", \"city\":\""+city+"\",\"phone\":\""+phone+"\"}");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			responseCode = sendSmsResponse.getCode();
			logger.info("sendValidCode respCode:{},respMsg:{}",responseCode,respCodeAndMsg.get(responseCode));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sendNotice error:"+e);
		}
		return responseCode;
	}
	
	/**
	 * 发送短信
	 * @param mobilephone
	 * 		手机号
	 * @param content
	 * 		发送内容(需要json格式)
	 * @param templateCode
	 * 		模板编号(阿里云短信模板编号)
	 * @return
	 */
	public static String sendSms(String mobilephone,String content,String templateCode){
		logger.info("sendNotice,phone:{}",mobilephone);
		String responseCode = null;
		try {
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", ConnectTimeout);
			System.setProperty("sun.net.client.defaultReadTimeout", ReadTimeout);
			// 初始化ascClient需要的几个参数
			final String product = "Dysmsapi";// 短信API产品名称
			final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名
			// 初始化ascClient,暂时不支持多region
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(mobilephone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(SignName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(templateCode);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam(content);
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			responseCode = sendSmsResponse.getCode();
			logger.info("sendValidCode respCode:{},respMsg:{}",responseCode,respCodeAndMsg.get(responseCode));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sendNotice error:"+e);
		}
		return responseCode;
	}
	
	private SMSUtil() {
	}

	private static Logger logger = LoggerFactory.getLogger(SMSUtil.class);
	
	/**
	 * 设置超时时间-可自行调整
	 */
	private final static String ConnectTimeout = "10000";
	/**
	 * 设置超时时间-可自行调整
	 */
	private final static String ReadTimeout = "10000";
	
	
	private static Map<String, String> respCodeAndMsg = new HashMap<String, String>();
	static{
		respCodeAndMsg.put("OK","请求成功");
		respCodeAndMsg.put("isp.RAM_PERMISSION_DENY",	"RAM权限DENY");
		respCodeAndMsg.put("isv.OUT_OF_SERVICE",	"业务停机");
		respCodeAndMsg.put("isv.PRODUCT_UN_SUBSCRIPT",	"未开通云通信产品的阿里云客户");
		respCodeAndMsg.put("isv.PRODUCT_UNSUBSCRIBE",	"产品未开通");
		respCodeAndMsg.put("isv.ACCOUNT_NOT_EXISTS",	"账户不存在");
		respCodeAndMsg.put("isv.ACCOUNT_ABNORMAL",	"账户异常");
		respCodeAndMsg.put("isv.SMS_TEMPLATE_ILLEGAL",	"短信模板不合法");
		respCodeAndMsg.put("isv.SMS_SIGNATURE_ILLEGAL",	"短信签名不合法");
		respCodeAndMsg.put("isv.INVALID_PARAMETERS",	"参数异常");
		respCodeAndMsg.put("isp.SYSTEM_ERROR",	"系统错误");
		respCodeAndMsg.put("isv.MOBILE_NUMBER_ILLEGAL",	"非法手机号");
		respCodeAndMsg.put("isv.MOBILE_COUNT_OVER_LIMIT",	"手机号码数量超过限制");
		respCodeAndMsg.put("isv.TEMPLATE_MISSING_PARAMETERS",	"模板缺少变量");
		respCodeAndMsg.put("isv.BUSINESS_LIMIT_CONTROL",	"业务限流");
		respCodeAndMsg.put("isv.INVALID_JSON_PARAM",	"JSON参数不合法，只接受字符串值");
		respCodeAndMsg.put("isv.BLACK_KEY_CONTROL_LIMIT",	"黑名单管控");
		respCodeAndMsg.put("isv.PARAM_LENGTH_LIMIT",	"参数超出长度限制");
		respCodeAndMsg.put("isv.PARAM_NOT_SUPPORT_URL",	"不支持URL");
		respCodeAndMsg.put("isv.AMOUNT_NOT_ENOUGH",	"账户余额不足");
	}
	public static void main(String[] args) {
		JSONObject content = new JSONObject();
		content.put("code", "123456");
		String sms = sendSms("18608116390", content.toString(), "SMS_79380018");
		System.out.println(sms);
	}
}
