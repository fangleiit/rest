package com.biz.bizunited.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.StatusEntity;

/**   
 * @Title: Entity
 * @Description: 红包开奖领取记录
 * @author Biz_Generator
 * @date 2017-07-12 15:43:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "qrcode_redpackage_open", schema = "")
@SuppressWarnings("serial")
public class RedpackageOpenEntity extends StatusEntity implements java.io.Serializable {
	/**二维码*/
	private java.lang.String qrcode;
	/**最后扫码人*/
	private java.lang.String lastName;
	/**领取人*/
	private java.lang.String takeName;
	/**修改日期*/
	private java.lang.String actId;
	/**中奖奖项*/
	private java.lang.String prize;
	/**中奖金额*/
	private java.math.BigDecimal bonus;
	/**领取时间*/
	private java.util.Date takeTime;
	/**是否已发 0未提现 1已提现*/
	private java.lang.Integer isSendout;
	/**创建时间*/
	private java.util.Date createTime;
	/**扫码次数*/
	private java.lang.Integer scanCode;
	/**当前活动所属名单状态 10:正常 20:白名单 30:黄名单 40:黑名单*/
	private java.lang.Integer abnormalStatus;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二维码
	 */
	@Column(name ="qrcode",nullable=true,length=36)
	public java.lang.String getQrcode(){
		return this.qrcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  二维码
	 */
	public void setQrcode(java.lang.String qrcode){
		this.qrcode = qrcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最后扫码人
	 */
	@Column(name ="last_name",nullable=true,length=100)
	public java.lang.String getLastName(){
		return this.lastName;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  最后扫码人
	 */
	public void setLastName(java.lang.String lastName){
		this.lastName = lastName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  领取人
	 */
	@Column(name ="take_name",nullable=true,length=100)
	public java.lang.String getTakeName(){
		return this.takeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  领取人
	 */
	public void setTakeName(java.lang.String takeName){
		this.takeName = takeName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改日期
	 */
	@Column(name ="act_id",nullable=true,length=36)
	public java.lang.String getActId(){
		return this.actId;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  修改日期
	 */
	public void setActId(java.lang.String actId){
		this.actId = actId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中奖奖项
	 */
	@Column(name ="prize",nullable=true,length=36)
	public java.lang.String getPrize(){
		return this.prize;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  中奖奖项
	 */
	public void setPrize(java.lang.String prize){
		this.prize = prize;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  中奖金额
	 */
	@Column(name ="bonus",nullable=true,length=10)
	public java.math.BigDecimal getBonus(){
		return this.bonus;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *描述: 属性set方法
	 *@param: java.math.BigDecimal  中奖金额
	 */
	public void setBonus(java.math.BigDecimal bonus){
		this.bonus = bonus;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  领取时间
	 */
	@Column(name ="take_time",nullable=true,length=32)
	public java.util.Date getTakeTime(){
		return this.takeTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *描述: 属性set方法
	 *@param: java.util.Date  领取时间
	 */
	public void setTakeTime(java.util.Date takeTime){
		this.takeTime = takeTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否已发
	 */
	@Column(name ="is_sendout",nullable=true,length=5)
	public java.lang.Integer getIsSendout(){
		return this.isSendout;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否已发
	 */
	public void setIsSendout(java.lang.Integer isSendout){
		this.isSendout = isSendout;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="create_time",nullable=true,length=32)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *描述: 属性set方法
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	@Column(name = "scan_code",length=10)
	public java.lang.Integer getScanCode() {
		return scanCode;
	}

	public void setScanCode(java.lang.Integer scanCode) {
		this.scanCode = scanCode;
	}
	
	/**
	 *方法: 取得javalang.Integer
	 *@return: javalang.Integer  当前活动所属名单状态 10:正常 20:白名单 30:黄名单 40:黑名单
	 */
	@Column(name = "abnormal_status",length=10)
	public java.lang.Integer getAbnormalStatus() {
		return abnormalStatus;
	}
	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: javalang.Integer  当前活动所属名单状态 10:正常 20:白名单 30:黄名单 40:黑名单
	 */
	public void setAbnormalStatus(java.lang.Integer abnormalStatus) {
		this.abnormalStatus = abnormalStatus;
	}
	
}
