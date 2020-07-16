package com.biz.bizunited.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class CollectionUtil.
 * 
 * @author 作者 yangyz
 * @version 创建时间：2011-1-14 上午09:15:04 类说明
 */
/**
 * 项目名称：JNC-COMMONS 类名称：CollectionUtil 类描述： 创建人：David 创建时间：2011-1-14 上午09:15:04 修改人：David 修改时间：2011-1-14 上午09:15:04
 * 修改备注：
 * 
 * @version 1.0
 */
public class CollectionUtil {

    public static boolean collectionNotEmpty(Collection collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    /**
     * List empty.
     * 
     * @param list
     *            the list
     * @return true, if successful
     */
    public static boolean listEmpty(List list) {
        return CollectionUtils.isEmpty(list);
    }

    /**
     * List not empty.
     * 
     * @param list
     *            the list
     * @return true, if successful
     */
    public static boolean listNotEmpty(List list) {
        return CollectionUtils.isNotEmpty(list);
    }

    /**
     * List not empty not size zero.
     * 
     * @param list
     *            the list
     * @return true, if successful
     */
    public static boolean listNotEmptyNotSizeZero(List list) {
        return listNotEmpty(list) && list.size() > 0;
    }

    /**
     * Map not empty.
     * 
     * @param map
     *            the map
     * @return true, if successful
     */
    public static boolean mapNotEmpty(Map map) {
        return MapUtils.isNotEmpty(map);
    }
}
