package com.biz.bizunited.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.minidao.hibernate.StatusEntity;

/**
 * 
 * @ClassName: QrcodeInfo 
 * @Description: 二维码信息表
 * @author ian.zeng
 * @date 2017年7月20日 上午10:47:42
 */
@Entity
@Table(name = "qrcode_info", schema = "")
@SuppressWarnings("serial")
public class QrcodeInfoEntity extends StatusEntity implements Serializable{
	/**二维码编号*/
	private String qrcode;
	/**经销商编码*/
	private String customerCode;
	/**产品编码*/
	private String productCode;
	/**出库时间*/
	private Date outTime;
	/**创建时间*/
	private Date createTime;
	/**生产时间*/
	private Date produceTime;
	/**外码*/
	private String codeOutside;
	
	@Column(name = "qrcode",length = 50)
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	@Column(name = "customer_code",length = 50)
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
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "produce_time")
	public Date getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}
	@Column(name = "out_time")
	public Date getOutTime() {
		return outTime;
	}
	
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	@Column(name = "code_outside")
	public String getCodeOutside() {
		return codeOutside;
	}

	public void setCodeOutside(String codeOutside) {
		this.codeOutside = codeOutside;
	}
	
}
