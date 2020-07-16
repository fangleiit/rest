package com.biz.bizunited.vo;

/**
 * @ClassName: PublicVo 
 * @Description: 公用接收数据vo
 * @author ian.zeng
 * @date 2017年7月27日 下午4:37:57
 */
public class PublicVo {
	
	private String openid;
	
	private int count;
	
	private String scanTime;
	
	private String longitude;
	
	private String latitude;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getScanTime() {
		return scanTime;
	}

	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
