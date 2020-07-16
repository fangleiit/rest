package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.StatusEntity;

/**
 * 
 * @ClassName: DrawShiWuEntity 
 * @Description: 实物奖领取记录
 * @author ian.zeng
 * @date 2017年7月24日 下午3:17:18
 */
@Entity
@Table(name = "draw_shiwu",schema = "")
@SuppressWarnings("serial")
public class DrawShiWuEntity extends StatusEntity implements Serializable{
	
	private String openid;
	
	private String redid;
	
	private Date createTime;
	
	private Integer send = 0;
	
	@Column(name = "openid",length = 100)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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
	@Column(name = "send")
	public Integer getSend() {
		return send;
	}

	public void setSend(Integer send) {
		this.send = send;
	}
	
}
