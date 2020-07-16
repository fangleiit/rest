/**
 * @Description: 
 * @ClassName: com.biz.bizunited.dto.AdminDto
 * @author: Omar(OmarZhang)
 * @date: 2016年4月28日 下午4:05:06 
 */
package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.minidao.hibernate.IdEntity;

/**
 * 用户DTO
 * @Description: 用于测试
 *
 */
@Entity
@Table(name = "BANK_CARD", schema = "")
@SuppressWarnings("serial")
public class BankCardEntity extends IdEntity implements Serializable{
	
	private String cardNumber;
	private String cardType;
	private String mobile;
	private String name;
	private String shopId;
	private Integer status;
	
	@Column(name ="CARDNUMBER",nullable=true,length=19)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column(name ="CARDTYPE",nullable=true,length=50)
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	@Column(name ="MOBILE",nullable=true,length=11)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name ="NAME",nullable=true,length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name ="SHOPID",nullable=true)
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	@Column(name ="STATUS",nullable=true)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "BankCardEntity ["
				+ "id:"+super.getId()
				+ ",cardNumber:"+cardNumber
				+ ",cardType:"+cardType
				+ ",mobile:"+mobile
				+ ",name:"+name
				+ ",shopId:"+shopId
				+ ",status:"+status
				
				+"]";
	}
}
