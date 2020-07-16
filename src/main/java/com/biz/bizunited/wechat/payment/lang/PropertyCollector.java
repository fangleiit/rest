package com.biz.bizunited.wechat.payment.lang;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.Properties;

import static com.google.common.collect.Lists.newLinkedList;

public class PropertyCollector extends DefaultHandler {

    private Properties prop = new Properties();
    private LinkedList<String> keyStack = newLinkedList();
    private LinkedList<StringBuilder> valueStack = newLinkedList();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        this.keyStack.add(qName);
        this.valueStack.add(new StringBuilder());
    }

    @Override public void endElement(String uri, String localName, String qName) {
        String key = this.keyStack.removeLast();
        StringBuilder value = this.valueStack.removeLast();

        this.prop.setProperty(key, value.toString());

        if (valueStack.size() > 0)
            this.valueStack.getLast().append('<').append(key).append('>').append(value).append("</")
                .append(key).append('>');
    }

    @Override public void characters(char[] ch, int start, int length) {
        this.valueStack.getLast().append(ch, start, length);
    }

    public Properties returnProperties() {
        return (this.prop);
    }
}
