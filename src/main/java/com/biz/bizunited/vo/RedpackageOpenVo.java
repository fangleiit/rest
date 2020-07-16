package com.biz.bizunited.vo;


/**   
 * @Title: vo
 * @Description: 红包开奖领取记录
 * @author Biz_Generator
 * @date 2017-07-12 15:43:55
 * @version V1.0   
 *
 */
public class RedpackageOpenVo {
	/**主键*/
	private java.lang.String id;
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
	private java.lang.String takeTime;
	/**是否已发*/
	private java.lang.Integer isSendout;
	/**创建时间*/
	private java.lang.String createTime;
	/**手机号码*/
	private java.lang.String mobilePhone;
	/**微信昵称*/
	private java.lang.String wechatNikename;
	/**扫码箱数*/
	private java.lang.String total;
	/**行号   名次*/
	private java.lang.Integer rowno;
	/**奖项ID*/
	private java.lang.String prizeId;
	
	private java.lang.Integer ptype;
	
	private java.lang.String remark;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getQrcode() {
		return qrcode;
	}
	public void setQrcode(java.lang.String qrcode) {
		this.qrcode = qrcode;
	}
	public java.lang.String getLastName() {
		return lastName;
	}
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}
	public java.lang.String getTakeName() {
		return takeName;
	}
	public void setTakeName(java.lang.String takeName) {
		this.takeName = takeName;
	}
	public java.lang.String getActId() {
		return actId;
	}
	public void setActId(java.lang.String actId) {
		this.actId = actId;
	}
	public java.lang.String getPrize() {
		return prize;
	}
	public void setPrize(java.lang.String prize) {
		this.prize = prize;
	}
	public java.math.BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(java.math.BigDecimal bonus) {
		this.bonus = bonus;
	}
	public java.lang.String getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(java.lang.String takeTime) {
		this.takeTime = takeTime;
	}
	public java.lang.Integer getIsSendout() {
		return isSendout;
	}
	public void setIsSendout(java.lang.Integer isSendout) {
		this.isSendout = isSendout;
	}
	public java.lang.String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}
	/**
	 * get
	 * 手机号码
	 * @return
	 */
	public java.lang.String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * set
	 * 手机号码
	 * @param mobilePhone
	 */
	public void setMobilePhone(java.lang.String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/** 
	 * get
	 * 微信昵称
	 * */
	public java.lang.String getWechatNikename() {
		return wechatNikename;
	}
	/**
	 * set
	 * 微信昵称
	 * @param wechatNikename
	 */
	public void setWechatNikename(java.lang.String wechatNikename) {
		this.wechatNikename = wechatNikename;
	}
	/**
	 * 扫码箱数
	 */
	public java.lang.String getTotal() {
		return total;
	}
	/**
	 * 扫码箱数
	 * @param total
	 */
	public void setTotal(java.lang.String total) {
		this.total = total;
	}
	/**
	 * 行号  名次
	 * @return
	 */
	public java.lang.Integer getRowno() {
		return rowno;
	}
	/**
	 * 行号  名次
	 * @param rowno
	 */
	public void setRowno(java.lang.Integer rowno) {
		this.rowno = rowno;
	}
	public java.lang.String getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(java.lang.String prizeId) {
		this.prizeId = prizeId;
	}
	public java.lang.Integer getPtype() {
		return ptype;
	}
	public void setPtype(java.lang.Integer ptype) {
		this.ptype = ptype;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
}
