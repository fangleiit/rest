package com.biz.bizunited.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 奖项配置
 * @author Biz_Generator
 * @date 2017-06-22 18:42:56
 * @version V1.0   
 *
 */
@Entity 
@Table(name = "qrcode_prize_config", schema = "")
@SuppressWarnings("serial")
public class PrizeEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**活动ID*/
	private java.lang.String actId;
	/**奖项名称*/
	private java.lang.String pname;
	/**奖项类型*/
	private java.lang.Integer ptype;
	/**描述*/
	private java.lang.String remark;
	/**中奖金额*/
	private java.math.BigDecimal money1;
	/**中奖金额*/
	private java.math.BigDecimal money2;
	/**中奖金额*/
	private java.math.BigDecimal money3;
	/**中奖金额*/
	private java.math.BigDecimal money4;
	/**中奖金额*/
	private java.math.BigDecimal money5;
	/**中奖份数*/
	private java.lang.Integer count;
	/**已领份数*/
	private java.lang.Integer outCount;
	/**是否首次*/
	private java.lang.Integer first;
	/**是否黄名单中奖*/
	private java.lang.Integer yellowlist;
	/**是否固定奖*/
	private java.lang.Integer fixed;
	/**是否大奖*/
	private java.lang.Integer big;
	/**是否人工投放*/
	private java.lang.Integer putin;
	/**状态*/
	private java.lang.String status;
	/**实物价值*/
	private java.math.BigDecimal price;
	
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
	 *@return: java.lang.String  活动ID
	 */
	@Column(name ="act_id",nullable=true,length=36)
	public java.lang.String getActId(){
		return this.actId;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  活动ID
	 */
	public void setActId(java.lang.String actId){
		this.actId = actId;
	}
	@Column(name = "p_name", length=20)
	public java.lang.String getPname() {
		return pname;
	}

	public void setPname(java.lang.String pname) {
		this.pname = pname;
	}
	@Column(name = "p_type",length = 5)
	public java.lang.Integer getPtype() {
		return ptype;
	}

	public void setPtype(java.lang.Integer ptype) {
		this.ptype = ptype;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="remark",nullable=true,length=50)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  描述
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  中奖金额
	 */
	@Column(name ="money_1",nullable=true,length=10)
	public java.math.BigDecimal getMoney1(){
		return this.money1;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *描述: 属性set方法
	 *@param: java.math.BigDecimal  中奖金额
	 */
	public void setMoney1(java.math.BigDecimal money1){
		this.money1 = money1;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  中奖金额
	 */
	@Column(name ="money_2",nullable=true,length=10)
	public java.math.BigDecimal getMoney2(){
		return this.money2;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *描述: 属性set方法
	 *@param: java.math.BigDecimal  中奖金额
	 */
	public void setMoney2(java.math.BigDecimal money2){
		this.money2 = money2;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  中奖金额
	 */
	@Column(name ="money_3",nullable=true,length=10)
	public java.math.BigDecimal getMoney3(){
		return this.money3;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *描述: 属性set方法
	 *@param: java.math.BigDecimal  中奖金额
	 */
	public void setMoney3(java.math.BigDecimal money3){
		this.money3 = money3;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  中奖金额
	 */
	@Column(name ="money_4",nullable=true,length=10)
	public java.math.BigDecimal getMoney4(){
		return this.money4;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *描述: 属性set方法
	 *@param: java.math.BigDecimal  中奖金额
	 */
	public void setMoney4(java.math.BigDecimal money4){
		this.money4 = money4;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  中奖金额
	 */
	@Column(name ="money_5",nullable=true,length=10)
	public java.math.BigDecimal getMoney5(){
		return this.money5;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *描述: 属性set方法
	 *@param: java.math.BigDecimal  中奖金额
	 */
	public void setMoney5(java.math.BigDecimal money5){
		this.money5 = money5;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  中奖份数
	 */
	@Column(name ="count",nullable=true,length=20)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  中奖份数
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  已领份数
	 */
	@Column(name ="out_count",nullable=true,length=20)
	public java.lang.Integer getOutCount(){
		return this.outCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  已领份数
	 */
	public void setOutCount(java.lang.Integer outCount){
		this.outCount = outCount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否首次
	 */
	@Column(name ="first",nullable=true,length=5)
	public java.lang.Integer getFirst(){
		return this.first;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否首次
	 */
	public void setFirst(java.lang.Integer first){
		this.first = first;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否黄名单中奖
	 */
	@Column(name ="yellowlist",nullable=true,length=5)
	public java.lang.Integer getYellowlist(){
		return this.yellowlist;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否黄名单中奖
	 */
	public void setYellowlist(java.lang.Integer yellowlist){
		this.yellowlist = yellowlist;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否固定奖
	 */
	@Column(name ="fixed",nullable=true,length=5)
	public java.lang.Integer getFixed(){
		return this.fixed;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否固定奖
	 */
	public void setFixed(java.lang.Integer fixed){
		this.fixed = fixed;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否大奖
	 */
	@Column(name ="big",nullable=true,length=5)
	public java.lang.Integer getBig(){
		return this.big;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否大奖
	 */
	public void setBig(java.lang.Integer big){
		this.big = big;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否人工投放
	 */
	@Column(name ="putin",nullable=true,length=5)
	public java.lang.Integer getPutin(){
		return this.putin;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否人工投放
	 */
	public void setPutin(java.lang.Integer putin){
		this.putin = putin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="status",nullable=true,length=10)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	@Column(name = "price",length = 10)
	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}
	@Column(name = "data_status",length = 10)
	public java.lang.String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(java.lang.String dataStatus) {
		this.dataStatus = dataStatus;
	}
	
}
