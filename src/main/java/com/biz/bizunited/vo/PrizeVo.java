package com.biz.bizunited.vo;



/**
 * 奖项配置
 * @ClassName: PrizeVo 
 * @Description: vo
 * @author ian.zeng
 * @date 2017年6月20日 下午3:06:32
 */
public class PrizeVo {
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
	private java.lang.String first;
	/**是否黄名单中奖*/
	private java.lang.String yellowlist;
	/**是否固定奖*/
	private java.lang.String fixed;
	/**是否大奖*/
	private java.lang.String big;
	/**是否人工投放*/
	private java.lang.String putin;
	/**创建人*/
	private java.lang.String createBy;
	/**状态*/
	private java.lang.String status;
	/**创建时间*/
	private java.util.Date createTime;
	/**中奖金额系列*/
	private java.lang.String moneys;
	/**中奖金额*/
	private java.math.BigDecimal bonus;
	/**红包ID*/
	private java.lang.String redId;
	/**领取时间*/
	private java.lang.String takeTime;
	/**领取人*/
	private java.lang.String takeName;
	
	private java.lang.String putinId;
	
	private java.math.BigDecimal price;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getActId() {
		return actId;
	}
	public void setActId(java.lang.String actId) {
		this.actId = actId;
	}
	
	public java.lang.String getPname() {
		return pname;
	}
	public void setPname(java.lang.String pname) {
		this.pname = pname;
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
	public java.math.BigDecimal getMoney1() {
		return money1;
	}
	public void setMoney1(java.math.BigDecimal money1) {
		this.money1 = money1;
	}
	public java.math.BigDecimal getMoney2() {
		return money2;
	}
	public void setMoney2(java.math.BigDecimal money2) {
		this.money2 = money2;
	}
	public java.math.BigDecimal getMoney3() {
		return money3;
	}
	public void setMoney3(java.math.BigDecimal money3) {
		this.money3 = money3;
	}
	public java.math.BigDecimal getMoney4() {
		return money4;
	}
	public void setMoney4(java.math.BigDecimal money4) {
		this.money4 = money4;
	}
	public java.math.BigDecimal getMoney5() {
		return money5;
	}
	public void setMoney5(java.math.BigDecimal money5) {
		this.money5 = money5;
	}
	public java.lang.Integer getCount() {
		return count;
	}
	public void setCount(java.lang.Integer count) {
		this.count = count;
	}
	public java.lang.Integer getOutCount() {
		return outCount;
	}
	public void setOutCount(java.lang.Integer outCount) {
		this.outCount = outCount;
	}
	public java.lang.String getFirst() {
		return first;
	}
	public void setFirst(java.lang.String first) {
		this.first = first;
	}
	public java.lang.String getYellowlist() {
		return yellowlist;
	}
	public void setYellowlist(java.lang.String yellowlist) {
		this.yellowlist = yellowlist;
	}
	public java.lang.String getFixed() {
		return fixed;
	}
	public void setFixed(java.lang.String fixed) {
		this.fixed = fixed;
	}
	public java.lang.String getBig() {
		return big;
	}
	public void setBig(java.lang.String big) {
		this.big = big;
	}
	public java.lang.String getPutin() {
		return putin;
	}
	public void setPutin(java.lang.String putin) {
		this.putin = putin;
	}
	public java.lang.String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getMoneys() {
		return moneys;
	}
	public void setMoneys(java.lang.String moneys) {
		this.moneys = moneys;
	}
	public java.math.BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(java.math.BigDecimal bonus) {
		this.bonus = bonus;
	}
	public java.lang.String getRedId() {
		return redId;
	}
	public void setRedId(java.lang.String redId) {
		this.redId = redId;
	}
	public java.lang.String getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(java.lang.String takeTime) {
		this.takeTime = takeTime;
	}
	public java.lang.String getTakeName() {
		return takeName;
	}
	public void setTakeName(java.lang.String takeName) {
		this.takeName = takeName;
	}
	public java.lang.String getPutinId() {
		return putinId;
	}
	public void setPutinId(java.lang.String putinId) {
		this.putinId = putinId;
	}
	public java.math.BigDecimal getPrice() {
		return price;
	}
	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}
	
}
