/**
 * @Description: 
 * @ClassName: com.biz.orderDocker.util.StringPlaceUtils
 * @author: Omar(OmarZhang)
 * @date: 2016年4月13日 下午8:36:32 
 */
package com.biz.bizunited.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串替换工具类
 * @Description: 
 * @ClassName: com.biz.orderDocker.util.StringPlaceUtils
 * @author: Omar(OmarZhang)
 * @date: 2016年4月13日 下午8:36:32 
 *
 */
public final class StringPlaceUtils {
	
	
	/**
	 * 字符串(非标准JSON字符串处理成标准JSON)
	 * @Title: formartToJSON 
	 * @param jsonStr
	 * @return
	 * @author: Omar(OmarZhang)
	 * @date: 2016年4月13日 下午8:37:08
	 */
	public static String formartToJSON (String jsonStr) {
		String parseJSON = jsonStr.replaceAll("\\\\", "");
		return parseJSON.replaceAll("\"\\{", "\\{").replaceAll("\\}\"", "\\}");
	}
	
	/**
	 * 字符编码转换
	 * @Title: unicode 
	 * @param str 字符串
	 * @param charset 编码格式
	 * @return
	 * @author: Omar(OmarZhang)
	 * @date: 2016年4月15日 下午9:53:12
	 */
	public static String unicode(String str,String charset) {
		try {
			str = StringUtils.trim(str);
			return new String(str.getBytes(), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	

}
