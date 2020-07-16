package com.biz.bizunited.wechat.payment.req;

import static com.biz.bizunited.wechat.payment.lang.Keys.KEY;
import static com.biz.bizunited.wechat.payment.lang.Keys.SIGN;
import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.bizunited.wechat.payment.exceptions.HttpException;
import com.biz.bizunited.wechat.payment.lang.Signer;
import com.biz.bizunited.wechat.payment.lang.SimpleHttpClient;
import com.biz.bizunited.wechat.payment.lang.XmlBuilder;
import com.biz.bizunited.wechat.payment.res.WechatPayResponseBase;

public abstract class WechatPayRequestBase<T extends WechatPayResponseBase> {

    private static final Logger logger = LoggerFactory.getLogger(WechatPayRequestBase.class);
    public static final String UTF_8 = "utf-8";

    protected Properties conf = new Properties();

    public void setProperties(Properties properties) {
        for (Object key : properties.keySet()) {
            String keyString = key.toString();
            conf.setProperty(keyString, properties.getProperty(keyString));
        }
    }

    public String getProperty(String key) {
        return conf.getProperty(key);
    }

    public void setProperty(String key, String value) {
        if (value != null) {
            conf.setProperty(key, value);
        }
    }

    public T execute() {
        try {
            sign();
            String xmlBody = buildXmlBody();
            if (logger.isDebugEnabled()) {
                logger.debug(xmlBody);
            }
            ContentType textXml = ContentType.create(ContentType.TEXT_XML.getMimeType(), UTF_8);
            String responseBody = getSimpleHttpClient().doPost(getApiUrl(), textXml, xmlBody);
            return parseResponse(responseBody);
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    protected abstract String getApiUrl();

    protected abstract T parseResponse(String responseBody);

    protected abstract TreeSet<String> getSignParamNames();

    protected SimpleHttpClient getSimpleHttpClient() {
        return new SimpleHttpClient();
    }

    private void sign() {

        String sign = Signer.sign(getSignParamNames(), conf, getProperty(KEY));
        setProperty(SIGN, sign);
    }

    private String buildXmlBody() {

        TreeSet<String> signParamNames = getSignParamNames();
        List<KeyValue> data = newArrayList();
        for (String key : signParamNames) {
            if (Objects.equals(key, SIGN)) {
                continue;
            }
            String v = conf.getProperty(key);
            if (v != null)
                data.add(new DefaultKeyValue(key, v));
        }
        data.add(new DefaultKeyValue(SIGN, conf.getProperty(SIGN)));
        return new XmlBuilder(data).buildXmlBody();
    }

}

