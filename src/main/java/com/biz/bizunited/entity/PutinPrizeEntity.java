package com.biz.bizunited.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 人工投放奖记录
 * @author Biz_Generator
 * @date 2017-06-29 14:55:25
 * @version V1.0   
 *
 */
@Entity 
@Table(name = "qrcode_putin_prize", schema = "")
@SuppressWarnings("serial")
public class PutinPrizeEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**奖项id*/
	private java.lang.String pId;
	/**投放个数*/
	private java.lang.Integer putCount;
	/**剩余个数*/
	private java.lang.Integer sNumber;
	/**指向人*/
	private java.lang.String putinPeople;
	/**开始时间*/
	private java.util.Date sDate;
	/**结束时间*/
	private java.util.Date eDate;

	private java.lang.Integer how;
	
	private java.lang.String putinCust;
	
	private java.lang.String status;
	
	private java.lang.String dataStatus = "1";
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  奖项id
	 */
	@Column(name ="p_id",nullable=true,length=36)
	public java.lang.String getPId(){
		return this.pId;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  奖项id
	 */
	public void setPId(java.lang.String pId){
		this.pId = pId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  投放个数
	 */
	@Column(name ="put_count",nullable=true,length=10)
	public java.lang.Integer getPutCount(){
		return this.putCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  投放个数
	 */
	public void setPutCount(java.lang.Integer putCount){
		this.putCount = putCount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  剩余个数
	 */
	@Column(name ="s_number",nullable=true,length=10)
	public java.lang.Integer getSNumber(){
		return this.sNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  剩余个数
	 */
	public void setSNumber(java.lang.Integer sNumber){
		this.sNumber = sNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  指向人
	 */
	@Column(name ="putin_people",nullable=true,length=50)
	public java.lang.String getPutinPeople(){
		return this.putinPeople;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  指向人
	 */
	public void setPutinPeople(java.lang.String putinPeople){
		this.putinPeople = putinPeople;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="s_date",nullable=true,length=32)
	public java.util.Date getSDate(){
		return this.sDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *描述: 属性set方法
	 *@param: java.util.Date  开始时间
	 */
	public void setSDate(java.util.Date sDate){
		this.sDate = sDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="e_date",nullable=true,length=32)
	public java.util.Date getEDate(){
		return this.eDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *描述: 属性set方法
	 *@param: java.util.Date  结束时间
	 */
	public void setEDate(java.util.Date eDate){
		this.eDate = eDate;
	}
	@Column(name = "how")
	public java.lang.Integer getHow() {
		return how;
	}

	public void setHow(java.lang.Integer how) {
		this.how = how;
	}
	@Column(name = "putin_cust" ,length = 50)
	public java.lang.String getPutinCust() {
		return putinCust;
	}

	public void setPutinCust(java.lang.String putinCust) {
		this.putinCust = putinCust;
	}
	@Column(name = "status",length = 10)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	@Column(name = "data_status",length = 10)
	public java.lang.String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(java.lang.String dataStatus) {
		this.dataStatus = dataStatus;
	}
	
}
