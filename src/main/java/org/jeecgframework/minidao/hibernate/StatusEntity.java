package org.jeecgframework.minidao.hibernate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 数据传输标记 
 * @author xiaogang
 *
 */
@MappedSuperclass
public class StatusEntity extends IdEntity{

	/**
	 * 数据传输标记  0：未同步，1：已同步
	 */
	private String dataStatus = "0";

	@Column(name="data_status")
	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	
}
