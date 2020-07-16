package com.biz.bizunited.entity;

import java.sql.Timestamp;

import org.jeecgframework.minidao.hibernate.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *存放微信Accesstoken
 */
@Entity
@Table(name="weixin_accesstoken")
public class WeiXinAccessTokenEntity extends IdEntity {
	
	private String jsapiticket;//调用JS凭证
	private String access_token;//凭证
	private int expires_in;//凭证有效时间
	private Timestamp addTime;//添加时间
	
	@Column(name="access_token")
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}
	@Column(name="expires_ib")
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expiresIn) {
		expires_in = expiresIn;
	}
	@Column(name="addtime")
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	@Column(name="jsapi_ticket")
	public String getJsapiticket() {
		return jsapiticket;
	}
	public void setJsapiticket(String jsapiticket) {
		this.jsapiticket = jsapiticket;
	}
	
}
