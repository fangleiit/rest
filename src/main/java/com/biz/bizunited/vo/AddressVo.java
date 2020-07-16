package com.biz.bizunited.vo;

/**
 * @ClassName: AddressVo 
 * @Description: 地址信息
 * @author ian.zeng
 * @date 2017年7月19日 下午6:28:36
 */
public class AddressVo {
	/**国家*/
	private String country;
	/**省*/
	private String province;
	/**市*/
	private String city;
	/**区县名*/
	private String district;
	/**街道名*/
	private String street;
	/**街道门牌号*/
	private String street_number;
	/**行政区划代码 */
	private String adcode;
	/**国家代码*/
	private String country_code;
	/**和当前坐标点的方向，当有门牌号的时候返回数据*/
	private String direction;
	/**和当前坐标点的距离，当有门牌号的时候返回数据*/
	private String distance;
	/**经度*/
	private String longitude;
	/**纬度*/
	private String latitude;
	
	/**国家*/
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	/**省*/
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	/**市*/
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	/**区县名*/
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	/**街道名*/
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	/**街道门牌号*/
	public String getStreet_number() {
		return street_number;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	/**行政区划代码 */
	public String getAdcode() {
		return adcode;
	}
	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}
	/**国家代码*/
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	/**和当前坐标点的方向，当有门牌号的时候返回数据*/
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	/**和当前坐标点的距离，当有门牌号的时候返回数据*/
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
