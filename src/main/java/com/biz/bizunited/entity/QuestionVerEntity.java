package com.biz.bizunited.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.minidao.hibernate.StatusEntity;

/**   
 * @Title: Entity
 * @Description: 收集用户问卷版本表
 * @author fanglei
 * @version V1.0   
 *
 */
@Entity
@Table(name = "qrcode_question_ver", schema = "")
@SuppressWarnings("serial")
public class QuestionVerEntity extends StatusEntity implements Serializable{
	
	/**
	 * 用户iopenid
	 */
	private String openid;
	/**
	 * 用户问卷版本
	 */
	private String questionver;
	
	@Column(name ="openid",length=50)
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Column(name ="questionver",length=36)
	public String getQuestionver() {
		return questionver;
	}
	public void setQuestionver(String questionver) {
		this.questionver = questionver;
	}
	
	
	
}
