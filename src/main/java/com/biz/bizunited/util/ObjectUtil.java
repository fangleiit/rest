package com.biz.bizunited.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ObjectUtil {
	
	private final static String DATETYPE = "yyyy-MM-dd";
	
	/**
	 * 转换为DTO
	 * @param src
	 * @param tClass Dto类型
	 * @return
	 */
	public static <T> T toDto(Object src, Class<T> tClass){
		T t = null;
		try {
			t = tClass.newInstance();
			copy(t, src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}
	
	public static <T> List<T> toDtos(Collection<?> src ,Class<T> tClass){
		List<T> list = new ArrayList<T>();
		for(Object o :src){
			list.add(toDto(o,tClass));
		}
		return list;
	}
	
	
	public static void copy(Object dest, Object src) {
		try {
			Class<?> destClass = dest.getClass();
			Class<?> srcClass = src.getClass();
			// 目标Dto 不存在继承关系
			for (Field f : getAllFields(destClass)) {
				f.setAccessible(true);
				if (!(Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f
						.getModifiers()))) {
					Method getter = getGetterMethod(f.getName(), srcClass);
					Method setter = getSetterMethod(f.getName(), destClass,f.getType());
					if(setter==null){
						Field orgField = srcClass.getDeclaredField(f.getName());
						setter = getSetterMethod(f.getName(), destClass,orgField.getType());
					}
					if (getter != null && setter != null) {
						setter.invoke(dest, getter.invoke(src));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static List<Field> getAllFields(Class<?> clazz){
		List<Field> fields = new ArrayList<Field>();
		for (Field f : clazz.getDeclaredFields()) {
			f.setAccessible(true);
			fields.add(f);
		}
		if(clazz.getSuperclass()!=null){
			fields.addAll(getAllFields(clazz.getSuperclass()));
		}
		return fields;
	}

	/**
	 * 获得对应的Getter方法
	 * 
	 * @param field
	 * @param clazz
	 * @return
	 */
	private static Method getGetterMethod0(String field, Class<?> clazz) {
		String methodName = "get" + StringUtils.capitalize(field);
		Method m = null;
		try {
			m = clazz.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			try {
				m = clazz.getDeclaredMethod("is"
						+ StringUtils.capitalize(field));
			} catch (Exception e1) {
				m = null;
			}
		} catch (SecurityException e) {
			//e.printStackTrace();
			m = null;
		}
		if (m != null)
			m.setAccessible(true);
		return m;
	}

	private static Method getGetterMethod(String field, Class<?> clazz) {
		Method m = getGetterMethod0(field, clazz);
		if (m == null) {
			Class<?> superClas = clazz.getSuperclass();
			if (superClas != null) {
				m = getGetterMethod(field, superClas);
			}
		}
		return m;
	}

	/**
	 * 获得对应的Setter方法
	 * 
	 * @param field
	 * @param clazz
	 * @return
	 */
	private static Method getSetterMethod0(String field, Class<?> clazz,Class<?> fieldClass) {
		String methodName = "set" + StringUtils.capitalize(field);
		Method m = null;
		try {
			m = clazz.getDeclaredMethod(methodName,fieldClass);
		} catch (Exception e) {
			//e.printStackTrace();
			m = null;
		}
		if (m != null)
			m.setAccessible(true);
		return m;
	}

	/**
	 * 递归向上寻找父类的method
	 * 
	 * @param field
	 * @param clazz
	 * @return
	 */
	private static Method getSetterMethod(String field, Class<?> clazz,Class<?> fieldClass) {
		Method m = getSetterMethod0(field, clazz,fieldClass);
		if (m == null) {
			Class<?> superClas = clazz.getSuperclass();
			if (superClas != null) {
				m = getSetterMethod(field, superClas,fieldClass);
			}
		}
		return m;
	}

	/**
	 * 日期转换
	 * @Title: formatDateToString 
	 * @param dateType 日期转换成某种类型 yyyy-mm-dd 
	 * @param dateTime 日期
	 * @return 字符串
	 */
	public static String formatDateToString(String dateType,Date dateTime){
		if(StringUtils.isNotBlank(dateType)) {
			SimpleDateFormat df = new SimpleDateFormat(dateType,Locale.getDefault());
			return df.format(dateTime);
		}
		return null;
	}
	
	/**
	 * 日期转换
	 * @Title: formatDateToString 
	 * @param dateType 日期转换成某种类型 yyyy-mm-dd 
	 * @param dateTime 日期
	 * @return 字符串
	 */
	public static String formatDateToString(Date dateTime){
		if(dateTime != null) {
			return formatDateToString(DATETYPE,dateTime);
		}
		return null;
	}
	
	/**
	 * 自定义加密
	 * @Title: encript 
	 * @param contextVal 传入内容
	 * @param digestVal 传入摘要
	 * @return 返回字符串
	 */
	public static String encript(String contextVal,String digestVal) {
	    String str = null;
	    byte[] bytes = contextVal.getBytes();
	    byte[] digests = digestVal.getBytes();
	    try {
	        for (int i = 0; i < bytes.length; i++) {
	            bytes[i] = ((byte) (bytes[i] ^ digests[(i % digests.length)]));
	        }
	        str = new String(bytes, "utf8");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return str;
	}

	public static Map<String, Object> toMap(Object object, String... properties) {
		if(properties!=null&&object!=null){
			Map<String,Object> map = new HashMap<String,Object>();
			Class<?> clazz = object.getClass();
			for(String property:properties){
				try {
					Method m = getGetterMethod(property, clazz);
					m.setAccessible(true);
					map.put(property, m.invoke(object));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return map;
		}
		return null;
	}
	
	/**
	 * 日志公共方法
	 * @Title: getLogger 
	 * @param clazz
	 * @return
	 */
	public static void getLoggerErr(Class<?> clazz,Object message,Throwable e) {
		Logger.getLogger(clazz).error(message, e);
	}
	/**
	 * 日志公共方法
	 * @Title: getLogger 
	 * @param clazz
	 * @return
	 */
	public static void getLoggerErr(Class<?> clazz,Object message) {
		Logger.getLogger(clazz).error(message);
	}
	/**
	 * 日志公共方法
	 * @Title: getLogger 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger (Class<?> clazz) {
		return Logger.getLogger(clazz);
	}

	/**
	 * 将字符串转换为集合
	 * @Title: spliteStringToList 
	 * @param ids
	 * @return
	 */
	public static List<Long> spliteStringToLongList(String ids) {
		if(StringUtils.isBlank(ids)) {
			return null;
		}
		List<Long> tempList = new ArrayList<Long>();
		String [] strs = StringUtils.split(ids,",");
		for(String str : strs ) {
			if(StringUtils.isNotBlank(str)) {
				tempList.add(Long.parseLong(str));
			}
		}
		return tempList;
	}
	
	
	/**
	 * 截取指定字符串到最后 默认逗号分隔
	 * @Title: subStrLastToEnd 
	 * @param str
	 * @param chars
	 * @return
	 */
	public static String subStrLastToEnd(String str,String chars){
		if(StringUtils.isBlank(str)) {
			return null;
		}
		if(StringUtils.isBlank(chars)) {
			chars = ",";
		}
		return str.substring(str.lastIndexOf(chars)+1, str.length());
	}
	
	/**
	 * 截取指定字符串到开始 默认逗号分隔
	 * @Title: subStrLastToEnd 
	 * @param str
	 * @param chars
	 * @return
	 */
	public static String subStrLastToStart(String str,String chars){
		if(StringUtils.isBlank(str)) {
			return null;
		}
		if(StringUtils.isBlank(chars)) {
			chars = ",";
		}
		return str.substring(0, str.lastIndexOf(chars));
	}
	
	/**
	 * 是否包含
	 * @Title: isContrainStr 
	 * @param str
	 * @param chars
	 * @return
	 */
	public static Boolean isContrainStr(String str,String chars) {
		if(StringUtils.isBlank(str) || StringUtils.isBlank(chars)){
			return false;
		}
		return StringUtils.contains(str, chars);
	}
	
	
	/**
	 * 字符串转List<Long>集合 默认以逗号(",")隔开
	 * character 以什么分割
	 * @Title: strToList 
	 * @param str
	 * @return
	 */
	public static List<Long> strToList(String str,String character) {
		if(StringUtils.isBlank(str)){
			return new ArrayList<Long>();
		}
		if(StringUtils.isBlank(character)) {
			character = ",";
		}
		return isList(Arrays.asList(StringUtils.split(str,character)));
	}
	
	/**
	 * 字符串转List<Long>集合 默认以逗号(",")隔开
	 * @Title: strToList 
	 * @param str
	 * @return
	 */
	public static List<Long> strToList(String str) {
		String	character = ",";
		return isList(strToList(str, character));
	}
	
	/**
	 * 深度克隆
	 * @Title: copy 
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deepCopy(Object obj) throws IOException, ClassNotFoundException{
		   ByteArrayOutputStream bos = new ByteArrayOutputStream();
		   ObjectOutputStream oos = new ObjectOutputStream(bos);
		   oos.writeObject(obj);
		   ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
		   return ois.readObject();
	 }

	/**
	 * 集合元素中是否有包含
	 * @Title: isContrainElmentVal 
	 * @return
	 */
	public static Boolean isContrainElmentVal(Collection<String> lists,String name){
		if(returnContrainElmentVal(lists,name) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 集合元素中是否有包含 如果包含返回包含的值
	 * @Title: isContrainElmentVal 
	 * @return
	 */
	public static String returnContrainElmentVal(Collection<String> lists,String name){
		if(lists.isEmpty()){
			return null;
		}
		String flag = null;
		for(String str: lists) {
			if(StringUtils.containsIgnoreCase(str, name)){
				flag = str;
			}
		}
		return flag;
	}
	
	/**
	 * 截取指定字符串到开始 默认逗号分隔
	 * 注意: 是从第一个 指定符号字符开始 到开始
	 * @Title: subStrLastToEnd 
	 * @param str
	 * @param chars
	 * @return
	 */
	public static String subStrFirstToStart(String str,String chars){
		if(StringUtils.isBlank(str)) {
			return null;
		}
		if(StringUtils.isBlank(chars)) {
			chars = ",";
		}
		return str.substring(0, str.indexOf(chars));
	}
	
	/**
	 * 截取指定字符串到最后 默认逗号分隔
	 * 注意: 是从第一个 指定符号字符开始 到最后
	 * @Title: subStrLastToEnd 
	 * @param str
	 * @param chars
	 * @return
	 */
	public static String subStrFirstToEnd(String str,String chars){
		if(StringUtils.isBlank(str)) {
			return null;
		}
		if(StringUtils.isBlank(chars)) {
			chars = ",";
		}
		return str.substring(str.indexOf(chars)+1, str.length());
	}
	
	
	
	/**
	 * 是否是List 如果是List 则强转List Long集合
	 * 使用时 小心 只能是long 元素
	 * @Title: isList 
	 * @param obj
	 * @return
	 */
	public static List<Long> isList(Object obj) {
		List<Long> longList = new ArrayList<Long>();
		if(obj == null) {
			return null;
		}else if(obj instanceof Collection){
			List<?> tempLongList = (List<?>) obj;
			for(Object e : tempLongList) {
				if(!(e instanceof Long)){
					longList.add(Long.parseLong(ObjectUtils.toString(e)));
				}else if(e instanceof Long) {
					longList.add((Long) e);
				}
			}
			if(!longList.isEmpty()) {
				return longList;
			}
		}
		return null;
	}
}
