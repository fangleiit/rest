package com.biz.bizunited.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.vo.VerificationCodeVo;

/**
 * 验证码工具
 * 
 * @author xiaogang
 *
 */
public class ValidCodeUtil {

	private static Logger logger = LoggerFactory.getLogger(ValidCodeUtil.class);

	private static Map<String, VerificationCodeVo> map = new HashMap<>();


	/**
	 * 通过手机号，获取验证码,
	 * 
	 * @param phone
	 * @return
	 */
	public static int obtainCodeByPhone(String phone) {
		// 验证码对象
		VerificationCodeVo verificationCodeVo = new VerificationCodeVo();
		verificationCodeVo.setPhone(phone);
		// 生成验证码
		int code = generateCode();
		verificationCodeVo.setCode(code);
		verificationCodeVo.setProduceTime(new Date());
		verificationCodeVo.setInvalidTime(new Date(new Date().getTime() + Constants.TIME_OUT));
		map.put(phone, verificationCodeVo);
		return code;
	}

	/**
	 * 验证手机验证码是否正确
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	public static Map<String, Object> validCode(String phone, String code) {
		logger.info("验证手机验证码是否正确,phone:{},code:{}", phone, code);
		Map<String, Object> mapResult = new HashMap<String, Object>();
		if (code == null || "".equals(code.trim())) {
			mapResult.put("flag", false);
			mapResult.put("info", "请输入正确的验证码");
			return mapResult;
		}
		VerificationCodeVo verificationCodeVo = map.get(phone) == null ? null : map.get(phone);
		// 默认不通过
		boolean flag = false;
		// 先code是否匹配再验证是否超时
		if (StringUtil.isNotEmpty(verificationCodeVo) && code.equals(verificationCodeVo.getCode() + "")) {
			if (new Date().before(verificationCodeVo.getInvalidTime())
					|| new Date().equals(verificationCodeVo.getInvalidTime())) {
				flag = true;
				mapResult.put("info", "验证通过");
			}else{
				mapResult.put("info", "输入的验证码已过期");
			}
		} else {
			mapResult.put("info", "请输入正确的验证码");
		}
		logger.info("验证结果{}=={}?{}", code,
				StringUtil.isNotEmpty(verificationCodeVo) ? verificationCodeVo.getCode() : null, flag);
		mapResult.put("flag", flag);
		return mapResult;
	}

	/**
	 * 生成6位随机数
	 * 
	 * @return
	 */
	private static int generateCode() {
		while (true) {
			Random random = new Random();
			int nextInt = random.nextInt(999999);
			if (nextInt > 100000) {
				return nextInt;
			}
		}
	}
}
