package com.biz.bizunited.vo;

import java.math.BigDecimal;
import java.util.List;

/**   
 * @Description: 红包记录
 *
 */
public class RedpackageVo {
	
	private String totalAmt;
	
	private String balanceAmt;
	
	private List<RedpackageOpenVo> redpackageOpenVos;
	
	/**
	 * 红包总金额
	 * @return
	 */
	public String getTotalAmt() {
		return totalAmt;
	}
	/**
	 * 红包总金额
	 * @return
	 */
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	/**
	 * 红包余额
	 * @return
	 */
	public String getBalanceAmt() {
		return balanceAmt;
	}
	/**
	 * 红包余额
	 * @return
	 */
	public void setBalanceAmt(String balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	/**
	 * 红包领取记录
	 * @return
	 */
	public List<RedpackageOpenVo> getRedpackageOpenVos() {
		return redpackageOpenVos;
	}
	/**
	 * 红包领取记录
	 * @return
	 */
	public void setRedpackageOpenVos(List<RedpackageOpenVo> redpackageOpenVos) {
		this.redpackageOpenVos = redpackageOpenVos;
	}
}
