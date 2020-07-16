package com.biz.bizunited.wechat.payment.lang;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newTreeSet;
import static org.codelogger.utils.StringUtils.isBlank;
import static org.codelogger.utils.StringUtils.isNotBlank;
import static com.biz.bizunited.wechat.payment.lang.Keys.*;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.codelogger.utils.ArrayUtils;
import org.codelogger.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.bizunited.wechat.payment.exceptions.SignException;

public class Signer {

    private static final Logger logger = LoggerFactory.getLogger(Signer.class);

    private final String key;

    public Signer(Properties properties, String key) throws IllegalArgumentException {
        validateProperties(properties);
        this.key = key;
    }

    public String sign(Object object) {

        Class<?> aClass = object.getClass();
        Properties properties = new Properties();
        Field[] declaredFields = aClass.getDeclaredFields();
        if (ArrayUtils.isNotEmpty(declaredFields)) {
            try {
                for (Field declaredField : declaredFields) {
                    if (declaredField.isAnnotationPresent(SignIgnore.class)) {
                        continue;
                    }
                    String name = declaredField.getName();
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(object);
                    SignProperty signProperty = declaredField.getAnnotation(SignProperty.class);
                    if (signProperty != null && isNotBlank(signProperty.value())) {
                        properties.put(signProperty.value(), value);
                    } else {
                        properties.put(name, value);
                    }
                }
                return sign(properties);
            } catch (Exception e) {
                throw new SignException(e);
            }
        } else {
            throw new IllegalArgumentException("Argument object not have any fields.");
        }

    }

    public String sign(Properties properties) throws IllegalArgumentException {
        return sign(properties, key);
    }

    public String sign(TreeSet<String> signParamNames, Properties properties)
        throws IllegalArgumentException {
        return sign(signParamNames, properties, key);
    }

    public static String sign(Properties properties, String key) throws IllegalArgumentException {

        validateProperties(properties);
        validateKey(key);

        Set<Object> propertiesKeySets = properties.keySet();
        TreeSet<String> signParamNames = newTreeSet();
        for (Object propertiesKeySet : propertiesKeySets) {
            String propertyKey = propertiesKeySet.toString();
            if (Objects.equals(KEY, propertyKey) || Objects.equals(SIGN, propertyKey)) {
                continue;
            }
            signParamNames.add(propertyKey);
        }
        return sign(signParamNames, properties, key);
    }

    public static String sign(TreeSet<String> signParamNames, Properties properties, String key)
        throws IllegalArgumentException {

        validateSignParamNames(signParamNames);
        validateProperties(properties);
        validateKey(key);

        List<String> keyValuePair = newArrayList();
        for (String signParamName : signParamNames) {
            String signParamValue = properties.getProperty(signParamName);
            if (signParamValue != null) {
                keyValuePair.add(signParamName + "=" + signParamValue);
            }
        }
        keyValuePair.add(KEY + "=" + key);
        String finalParams = StringUtils.join(keyValuePair, "&");
        if (logger.isDebugEnabled()) {
            logger.debug("sign keyValuePairs:[{}].", finalParams);
        }
        return encodedByMD5(finalParams).toUpperCase();
    }

    private static void validateSignParamNames(TreeSet<String> signParamNames) {
        if (CollectionUtils.isEmpty(signParamNames)) {
            throw new IllegalArgumentException("Argument signParamNames can not be empty.");
        }
    }

    private static void validateProperties(Properties properties) {
        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("Argument properties can not be empty.");
        }
    }

    private static void validateKey(String key) {
        if (isBlank(key))
            throw (new IllegalArgumentException(
                "Argument key can not be blank, key required to sign."));
    }

    private static String encodedByMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            return byte2string(md.digest());
        } catch (Exception e) {
            return password;
        }
    }


    private static String byte2string(byte[] b) {
        StringBuffer hs = new StringBuffer(100);
        for (int n = 0; n < b.length; n++) {
            hs.append(byte2fex(b[n]));
        }
        return hs.toString();
    }

    private static String byte2fex(byte ib) {
        char[] Digit =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

}
