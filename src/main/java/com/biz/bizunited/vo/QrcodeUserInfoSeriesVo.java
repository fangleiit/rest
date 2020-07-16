package com.biz.bizunited.vo;

/**
 * 
 * @ClassName: QrcodeUserInfoSeriesVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author ian.zeng
 * @date 2017年7月12日 下午7:19:48
 */
public class QrcodeUserInfoSeriesVo {
	
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 次数
	 */
	private Integer count;
	/**
	 * 模板URL
	 */
	private String url;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 活动ID
	 */
	private String actId;
	
	private String actName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
}
