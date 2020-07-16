/**
 * @Description: 
 * @ClassName: com.biz.bizunited.util.AdminLTEParseUtils
 * @author: Omar(OmarZhang)
 * @date: 2016年5月6日 下午12:32:56 
 */
package com.biz.bizunited.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.biz.bizunited.pagination.CommonSearch;
import com.biz.bizunited.pagination.CommonSort;

/**
 * @Description: 
 * @ClassName: com.biz.bizunited.util.AdminLTEParseUtils
 * @author: Omar(OmarZhang)
 * @date: 2016年5月6日 下午12:32:56 
 *
 */
public class AdminLTEParseUtils {
	
	/**
	 * 通过http获取排序信息
	 * @Title: getByRequest 
	 * @param request
	 * @param definedColumnMap 自定义的排序 传入的列名称与数据库的名称对应 
	 * 注: Map ==> key: 页面的列名称  value: 数据库中的列名称
	 * 如果页面的列名称与数据库的列名称相同 可以对该字段不用映射
	 * @return
	 * @author: Omar(OmarZhang)
	 * @date: 2016年5月6日 下午12:34:47
	 */
	public static CommonSort getSortByRequest(HttpServletRequest request,Map<String,String> definedColumnMap) {
		Integer columnIndex = Integer.parseInt(request.getParameter("order[0][column]"));
		String columnName = request.getParameter("columns["+columnIndex+"][data]");
		String sort = request.getParameter("order[0][dir]");
		String sortName = definedColumnMap.get(columnName);
		if(StringUtils.isBlank(sortName)) {
			sortName = columnName;
		}
		return  new CommonSort(sortName,sort);
	}
	
	/**
	 * 通过http 获取搜索信息
	 * @Title: getSearchByRequest 
	 * @param request
	 * @return
	 * @author: Omar(OmarZhang)
	 * @date: 2016年5月6日 下午12:39:15
	 */
	public static CommonSearch getSearchByRequest(HttpServletRequest request){
		String searchVal = request.getParameter("search[value]");
		Boolean regex = BooleanUtils.toBoolean(request.getParameter("search[regex]"));
		return new CommonSearch(searchVal,regex);
	}

}
