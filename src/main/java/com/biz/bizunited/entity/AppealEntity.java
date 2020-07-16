package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.StatusEntity;

/**
 * @ClassName: AppealEntity 
 * @Description: 申诉信息
 * @author ian.zeng
 * @date 2017年7月18日 下午5:07:38
 */
@Entity
@Table(name = "qrcode_appeal", schema = "")
@SuppressWarnings("serial")
public class AppealEntity extends StatusEntity implements Serializable{
	
	/**微信openid*/
	private String openid;
	/**红包id*/
	private String redid;
	/**手机号码*/
	private String mobilePhone;
	/**省*/
	private String province;
	/**市*/
	private String city;
	/**区*/
	private String area;
	/**详细地址*/
	private String address;
	/**问题说明*/
	private String note;
	
	private Date createTime;
	
	@Column(name = "openid",length = 50)
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name = "mobile_phone",length = 20)
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	@Column(name = "province",length = 50)
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	@Column(name = "city",length = 50)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column(name = "area",length = 50)
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "address",length = 200)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "note",length = 2000)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Column(name = "redid",length = 36)
	public String getRedid() {
		return redid;
	}
	public void setRedid(String redid) {
		this.redid = redid;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
