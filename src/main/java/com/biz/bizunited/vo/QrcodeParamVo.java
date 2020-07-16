package com.biz.bizunited.vo;

/**
 * @ClassName: QrcodeParamVo 
 * @Description: 接口参数vo
 * @author ian.zeng
 * @date 2017年7月17日 上午10:14:32
 */
public class QrcodeParamVo {
	/**活动*/
	private String actId = "";
	/**微信用户openid*/
	private String openId = "";
	/**二维码编号*/
	private String qrcode = "";
	/**经销商编号*/
	private String custCode = "";
	/**红包领取记录*/
	private String redId = "";
	
	private Integer isSendout;
	
	private String flag = "false";
	
	private String bool = "true";
	
	private String uuid = "";
	
	private String status = "insert";
	
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getRedId() {
		return redId;
	}
	public void setRedId(String redId) {
		this.redId = redId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getIsSendout() {
		return isSendout;
	}
	public void setIsSendout(Integer isSendout) {
		this.isSendout = isSendout;
	}
	
	public String getBool() {
		return bool;
	}
	public void setBool(String bool) {
		this.bool = bool;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "QrcodeParamVo [actId=" + actId + ", openId=" + openId + ", qrcode=" + qrcode + ", custCode=" + custCode
				+ ", redId=" + redId + ", isSendout=" + isSendout + ", flag=" + flag + ", bool=" + bool + ", uuid="+ uuid +", status=" + " status]";
	}
}
