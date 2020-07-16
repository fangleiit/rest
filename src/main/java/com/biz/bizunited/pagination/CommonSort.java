/**
 * @Description: 
 * @ClassName: com.biz.bizunited.pagination.CommonSort
 * @author: Omar(OmarZhang)
 * @date: 2016年5月5日 下午3:17:20 
 */
package com.biz.bizunited.pagination;

import java.io.Serializable;

/**
 * 公共 排序
 * @Description: 
 * @ClassName: com.biz.bizunited.pagination.CommonSort
 * @author: Omar(OmarZhang)
 * @date: 2016年5月5日 下午3:17:20 
 *
 */
public class CommonSort implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	/** 哪一列进行排序*/
	private String columnName;
	/** 升序/降序*/
	private String dir;
	
	/**
	 * @Description: 
	 */
	public CommonSort() {
	}
	
	/**
	 * 
	 * @Description: 
	 * @param columnName 列名称
	 * @param dir 排序
	 */
	public CommonSort(String columnName, String dir) {
		super();
		this.columnName = columnName;
		this.dir = dir;
	}
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}
	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	
}
