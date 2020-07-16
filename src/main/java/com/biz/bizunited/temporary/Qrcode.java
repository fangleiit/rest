package com.biz.bizunited.temporary;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.IdEntity;

/**
 * 
 * @ClassName: Qrcode 
 * @Description: 二维码信息表
 * @author ian.zeng
 * @date 2017年7月20日 上午10:47:42
 */
@Entity
@Table(name = "qrcode", schema = "")
@SuppressWarnings("serial")
public class Qrcode extends IdEntity implements Serializable{
	/**二维码编号*/
	private String qrcode;
	/**经销商编码*/
	private String customerCode;
	/**产品编码*/
	private String productCode;
	/**出库时间*/
	private Date deliveryTime;
	/**生产时间*/
	private Date produceTime;
	
	@Column(name = "qrcode",length = 50)
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	@Column(name = "cust_code",length = 50)
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	@Column(name = "product_code",length = 50)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	@Column(name = "delivery_time")
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	@Column(name = "produce_time")
	public Date getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}
}
