package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.minidao.hibernate.StatusEntity;

@Entity
@Table(name="weixin_user")
public class WeiXinUserEntity extends StatusEntity implements Serializable{
	private static final long serialVersionUID = -2927804572668420337L;
	public static final String SUBSCRIBE_NO = "0";
	public static final String SUBSCRIBE_YES = "1";
	
	/**
	 * 是否订阅 
	 * 0 未订阅
	 * 1 已订阅
	 */
	private String subscribe;
	/**
	 * openId
	 */
	private String openid;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 头像
	 */
	private String headimgurl;
	/**
	 * 关注时间
	 */
	private String subscribe_time;
	
	/**
	 * 最新检查时间
	 */
	private Date last_check_time;
	
	@Column(name="openid")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name="nickname")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Column(name="sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name="city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(name="province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	@Column(name="country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	@Column(name="headimgurl")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	@Column(name="subscribe_time")
	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribeTime) {
		subscribe_time = subscribeTime;
	}
	@Column(name="subscribe")
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	@Column(name="last_check_time")
	public Date getLast_check_time() {
		return last_check_time;
	}

	public void setLast_check_time(Date last_check_time) {
		this.last_check_time = last_check_time;
	}

}
