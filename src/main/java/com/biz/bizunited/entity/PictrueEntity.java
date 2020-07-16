package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.IdEntity;
import org.jeecgframework.minidao.hibernate.StatusEntity;

/**
 * @ClassName: PictrueEntity 
 * @Description: 图片
 * @author ian.zeng
 * @date 2017年7月24日 上午11:19:14
 */
@Entity
@Table(name = "qrcode_pictrue",schema = "")
@SuppressWarnings("serial")
public class PictrueEntity extends StatusEntity implements Serializable{
	
	private String refId;
	
	private String picName;
	
	private Date createTime;
	
	@Column(name = "ref_id",length = 36)
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	@Column(name = "pic_name",length = 100)
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
