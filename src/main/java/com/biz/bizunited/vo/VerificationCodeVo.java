package com.biz.bizunited.vo;

import java.util.Date;

/** 
 * 短信验证码vo.
 * @author grover
 * @version v1.0
 */
public class VerificationCodeVo {
	//手机号
	private String phone;
	//随机码
	private Integer code;
	//产生随机码的时间
	private Date produceTime;
	//随机码失效时间
	private Date invalidTime;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Date getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}
	public Date getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
}
