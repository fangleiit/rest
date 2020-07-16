package com.biz.bizunited.wechat.payment.lang;

import org.apache.commons.collections.KeyValue;
import org.codelogger.utils.CollectionUtils;

import java.util.List;

public class XmlBuilder {

    private List<KeyValue> data;

    public XmlBuilder(List<KeyValue> data) {
        if (CollectionUtils.isEmpty(data)) {
            throw new IllegalArgumentException("Argument data can not be empty.");
        }
        this.data = data;
    }

    public String buildXmlBody() {

        StringBuilder xml = new StringBuilder("<xml>");
        for (KeyValue keyValue : data) {
            Object key = keyValue.getKey();
            Object value = keyValue.getValue();
            if (value != null)
                xml.append('<').append(key).append('>').append(value).append("</").append(key)
                    .append('>');
        }
        xml.append("</xml>");
        return xml.toString();
    }
}
