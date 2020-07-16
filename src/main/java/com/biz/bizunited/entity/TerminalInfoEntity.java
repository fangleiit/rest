package com.biz.bizunited.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.jeecgframework.minidao.hibernate.StatusEntity;

/**   
 * @Title: Entity
 * @Description: 扫码收集用户信息表
 * @author Biz_Generator
 * @date 2017-07-13 10:27:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "qrcode_terminal_info", schema = "")
@SuppressWarnings("serial")
public class TerminalInfoEntity extends StatusEntity implements java.io.Serializable {
	/**用户微信ID*/
	private java.lang.String openid;
	/**手机号码*/
	private java.lang.String mobilePhone;
	/**渠道类型*/
	private java.lang.String channel;
	/**是否黑名单*/
	private java.lang.Integer isBlacklist = 0;
	/**是否黄名单*/
	private java.lang.Integer isYellowlist = 0;
	/**门店名称*/
	private java.lang.String storeName;
	/**经度*/
	private java.lang.String longitude = "";
	/**纬度*/
	private java.lang.String latitude = "";
	/**省份*/
	private java.lang.String province;
	/**市x*/
	private java.lang.String city;
	/**区域*/
	private java.lang.String area;
	/**详细地址*/
	private java.lang.String address;
	/**微信昵称*/
	private java.lang.String wechatNikename;
	/**创建时间*/
	private java.util.Date createTime;
	/**白名单*/
	private java.lang.Integer whitelist = 0;
	
	private java.lang.String note;
	
	/**
	 * 门店所在城市
	 */
	private java.lang.String storeCity;
	/**
	 * 门店所在区域
	 */
	private java.lang.String storeDistrict;
	/**联系人*/
	private java.lang.String contacts;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户微信ID
	 */
	@Column(name ="openid",nullable=true,length=100)
	public java.lang.String getOpenid(){
		return this.openid;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  用户微信ID
	 */
	public void setOpenid(java.lang.String openid){
		this.openid = openid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	@Column(name ="mobile_phone",nullable=true,length=20)
	public java.lang.String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilePhone(java.lang.String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  渠道类型
	 */
	@Column(name ="channel",nullable=true,length=20)
	public java.lang.String getChannel(){
		return this.channel;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  渠道类型
	 */
	public void setChannel(java.lang.String channel){
		this.channel = channel;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否黑名单
	 */
	@Column(name ="is_blacklist",nullable=true,length=10)
	public java.lang.Integer getIsBlacklist(){
		return this.isBlacklist;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否黑名单
	 */
	public void setIsBlacklist(java.lang.Integer isBlacklist){
		this.isBlacklist = isBlacklist;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否黄名单
	 */
	@Column(name ="is_yellowlist",nullable=true,length=10)
	public java.lang.Integer getIsYellowlist(){
		return this.isYellowlist;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *描述: 属性set方法
	 *@param: java.lang.Integer  是否黄名单
	 */
	public void setIsYellowlist(java.lang.Integer isYellowlist){
		this.isYellowlist = isYellowlist;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  门店名称
	 */
	@Column(name ="store_name",nullable=true,length=200)
	public java.lang.String getStoreName(){
		return this.storeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  门店名称
	 */
	public void setStoreName(java.lang.String storeName){
		this.storeName = storeName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经度
	 */
	@Column(name ="longitude",nullable=true,length=20)
	public java.lang.String getLongitude(){
		return this.longitude;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  经度
	 */
	public void setLongitude(java.lang.String longitude){
		this.longitude = longitude;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  纬度
	 */
	@Column(name ="latitude",nullable=true,length=20)
	public java.lang.String getLatitude(){
		return this.latitude;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  纬度
	 */
	public void setLatitude(java.lang.String latitude){
		this.latitude = latitude;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省份
	 */
	@Column(name ="province",nullable=true,length=32)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  省份
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  市x
	 */
	@Column(name ="city",nullable=true,length=32)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  市x
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域
	 */
	@Column(name ="area",nullable=true,length=32)
	public java.lang.String getArea(){
		return this.area;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  区域
	 */
	public void setArea(java.lang.String area){
		this.area = area;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  详细地址
	 */
	@Column(name ="address",nullable=true,length=200)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *描述: 属性set方法
	 *@param: java.lang.String  详细地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	
	@Column(name ="wechat_nikename",nullable=true,length=50)
	public java.lang.String getWechatNikename() {
		return wechatNikename;
	}

	public void setWechatNikename(java.lang.String wechatNikename) {
		this.wechatNikename = wechatNikename;
	}
	@Column(name = "create_time")
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "whitelist",length = 10)
	public java.lang.Integer getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(java.lang.Integer whitelist) {
		this.whitelist = whitelist;
	}
	@Column(name = "note",length = 2000)
	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}
	@Column(name = "store_city",length = 50)
	public java.lang.String getStoreCity() {
		return storeCity;
	}
	
	public void setStoreCity(java.lang.String storeCity) {
		this.storeCity = storeCity;
	}
	@Column(name = "store_district",length = 50)
	public java.lang.String getStoreDistrict() {
		return storeDistrict;
	}
	
	public void setStoreDistrict(java.lang.String storeDistrict) {
		this.storeDistrict = storeDistrict;
	}
	@Column(name = "contacts",length = 20)
	public java.lang.String getContacts() {
		return contacts;
	}

	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}

}
