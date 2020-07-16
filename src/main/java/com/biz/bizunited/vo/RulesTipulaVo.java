package com.biz.bizunited.vo;


public class RulesTipulaVo {
	
	private String id;
	//每天限制次数
	private String numPlace;
	//每次时间间隔
	private String timeSpace;
	//防篡平台找不到次数
	private String disErrorNum;
	//每天扫码次数
	private String numScancode;
	//扫码范围
	private String scancodeScope;
	//扫码人数
	private String scancodePeoNum;
	//连续天数
	private String SeriesNumDay;
	//连续天数次数
	private String seriesDayNum;
	//获奖经销商发送短信间隔天数
	private String smsSendDay;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumPlace() {
		return numPlace;
	}
	public void setNumPlace(String numPlace) {
		this.numPlace = numPlace;
	}
	public String getTimeSpace() {
		return timeSpace;
	}
	public void setTimeSpace(String timeSpace) {
		this.timeSpace = timeSpace;
	}
	public String getDisErrorNum() {
		return disErrorNum;
	}
	public void setDisErrorNum(String disErrorNum) {
		this.disErrorNum = disErrorNum;
	}
	public String getNumScancode() {
		return numScancode;
	}
	public void setNumScancode(String numScancode) {
		this.numScancode = numScancode;
	}
	public String getScancodeScope() {
		return scancodeScope;
	}
	public void setScancodeScope(String scancodeScope) {
		this.scancodeScope = scancodeScope;
	}
	public String getScancodePeoNum() {
		return scancodePeoNum;
	}
	public void setScancodePeoNum(String scancodePeoNum) {
		this.scancodePeoNum = scancodePeoNum;
	}
	public String getSeriesNumDay() {
		return SeriesNumDay;
	}
	public void setSeriesNumDay(String seriesNumDay) {
		SeriesNumDay = seriesNumDay;
	}
	public String getSeriesDayNum() {
		return seriesDayNum;
	}
	public void setSeriesDayNum(String seriesDayNum) {
		this.seriesDayNum = seriesDayNum;
	}
	public String getSmsSendDay() {
		return smsSendDay;
	}
	public void setSmsSendDay(String smsSendDay) {
		this.smsSendDay = smsSendDay;
	}
	
}
