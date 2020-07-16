package com.biz.bizunited.vo;

import java.io.Serializable;

/**   
 * @Title: vo
 * @Description: 查询活动vo
 * @author ian.zeng
 * @date 2017-06-21 11:48:25
 * @version V1.0   
 *
 */
public class QrcodeActInfoVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**主键*/
	private java.lang.String id;
	/**活动名称*/
	private java.lang.String actName;
	/**活动描述*/
	private java.lang.String actNote;
	/**活动开始时间*/
	private java.lang.String startTime;
	/**活动结束时间*/
	private java.lang.String endTime;
	/**出库开始时间*/
	private java.lang.String outSTime;
	/**出库结束时间*/
	private java.lang.String outETime;
	/**创建人*/
	private java.lang.String createBy;
	/**创建时间*/
	private java.lang.String createTime;
	/**修改时间*/
	private java.lang.String updateTime;
	/**生效状态*/
	private java.lang.String status;
	/**审批状态*/
	private java.lang.String approvalStatus;
	/**推送状态*/
	private java.lang.String push;
	
	private java.lang.String staffPhone;
	/**是否关联经销商*/
	private java.lang.String relationCust;
	
	private java.lang.String custCode;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getActName() {
		return actName;
	}
	public void setActName(java.lang.String actName) {
		this.actName = actName;
	}
	public java.lang.String getActNote() {
		return actNote;
	}
	public void setActNote(java.lang.String actNote) {
		this.actNote = actNote;
	}
	public java.lang.String getStartTime() {
		return startTime;
	}
	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime;
	}
	public java.lang.String getEndTime() {
		return endTime;
	}
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}
	public java.lang.String getOutSTime() {
		return outSTime;
	}
	public void setOutSTime(java.lang.String outSTime) {
		this.outSTime = outSTime;
	}
	public java.lang.String getOutETime() {
		return outETime;
	}
	public void setOutETime(java.lang.String outETime) {
		this.outETime = outETime;
	}
	public java.lang.String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	public java.lang.String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.lang.String updateTime) {
		this.updateTime = updateTime;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(java.lang.String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public java.lang.String getPush() {
		return push;
	}
	public void setPush(java.lang.String push) {
		this.push = push;
	}
	public java.lang.String getStaffPhone() {
		return staffPhone;
	}
	public void setStaffPhone(java.lang.String staffPhone) {
		this.staffPhone = staffPhone;
	}
	public java.lang.String getRelationCust() {
		return relationCust;
	}
	public void setRelationCust(java.lang.String relationCust) {
		this.relationCust = relationCust;
	}
	public java.lang.String getCustCode() {
		return custCode;
	}
	public void setCustCode(java.lang.String custCode) {
		this.custCode = custCode;
	}
	
}
