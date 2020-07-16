package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.IdEntity;

/**
 * @ClassName: WeChatPaymentRecord 
 * @Description: 微信支付交易记录 
 * @author ian.zeng
 * @date 2017年7月20日 下午8:20:15
 */
@Entity
@Table(name = "wechat_payment_record",schema = "")
@SuppressWarnings("serial")
public class WeChatPaymentRecordEntity extends IdEntity implements Serializable{
	
	private String openid;
	
	private String payCode;
	
	private Integer amount;
	
	private Date payTime;
	
	@Column(name = "openid",length = 50)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name = "amount")
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	@Column(name = "pay_code")
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	@Column(name = "pay_time")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

}
