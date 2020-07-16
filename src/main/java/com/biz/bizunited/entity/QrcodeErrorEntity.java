package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.IdEntity;

/**
 * 
 * @ClassName: QrcodeError 
 * @Description: 异常二维码 
 * @author ian.zeng
 * @date 2017年7月28日 下午3:00:04
 */
@Entity
@Table(name = "qrcode_error",schema = "")
@SuppressWarnings("serial")
public class QrcodeErrorEntity extends IdEntity implements Serializable{
	
	private String qrcode;
	
	private String openid;
	
	private Date createTime;
	
	@Column(name = "qrcode",length = 50)
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	@Column(name = "openid",length = 100)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
