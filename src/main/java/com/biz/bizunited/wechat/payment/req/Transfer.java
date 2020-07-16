package com.biz.bizunited.wechat.payment.req;

import static com.biz.bizunited.wechat.payment.lang.Keys.AMOUNT;
import static com.biz.bizunited.wechat.payment.lang.Keys.CHECK_NAME;
import static com.biz.bizunited.wechat.payment.lang.Keys.DESC;
import static com.biz.bizunited.wechat.payment.lang.Keys.MCHID;
import static com.biz.bizunited.wechat.payment.lang.Keys.MCH_APPID;
import static com.biz.bizunited.wechat.payment.lang.Keys.NONCE_STR;
import static com.biz.bizunited.wechat.payment.lang.Keys.OPENID;
import static com.biz.bizunited.wechat.payment.lang.Keys.PARTNER_TRADE_NO;
import static com.biz.bizunited.wechat.payment.lang.Keys.RE_USER_NAME;
import static com.biz.bizunited.wechat.payment.lang.Keys.SIGN;
import static com.biz.bizunited.wechat.payment.lang.Keys.SPBILL_CREATE_IP;
import static com.google.common.collect.Sets.newTreeSet;
import static org.codelogger.utils.StringUtils.isBlank;
import static org.codelogger.utils.StringUtils.isNotBlank;
import static org.codelogger.utils.ValueUtils.getValue;

import java.util.Arrays;
import java.util.TreeSet;

import javax.net.ssl.SSLContext;

import com.biz.bizunited.wechat.payment.lang.CheckName;
import com.biz.bizunited.wechat.payment.lang.SimpleHttpClient;
import com.biz.bizunited.wechat.payment.res.TransferResponse;

public class Transfer extends WechatPayRequestBase<TransferResponse> {

    private static final String API_URL =
        "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public static final TreeSet<String> KEYS_PARAM_NAME = newTreeSet(Arrays
        .asList(MCH_APPID, MCHID, NONCE_STR, SIGN, PARTNER_TRADE_NO, OPENID, CHECK_NAME,
            RE_USER_NAME, AMOUNT, DESC, SPBILL_CREATE_IP));

    private SSLContext sslContext;

    public Transfer(String partnerTradeNo, String openid, Integer amount, String desc,
        CheckName checkName, String receiverName) {

        if (isBlank(partnerTradeNo)) {
            throw new IllegalArgumentException("Argument partnerTradeNo can not be blank.");
        }
        if (isBlank(openid)) {
            throw new IllegalArgumentException("Argument openid can not be blank.");
        }
        if (getValue(amount) < 1) {
            throw new IllegalArgumentException("Argument totalFee can not less than 1.");
        }
        if (isBlank(desc)) {
            throw new IllegalArgumentException("Argument desc can not be blank.");
        }
        if (checkName == null) {
            throw new IllegalArgumentException("Argument checkName can not be null.");
        }
        if (checkName != CheckName.NO_CHECK && isBlank(receiverName)) {
            throw new IllegalArgumentException(
                "Argument receiverName can not be blank when checkName != NO_CHECK .");
        }

        setProperty(PARTNER_TRADE_NO, partnerTradeNo);
        setProperty(OPENID, openid);
        setProperty(AMOUNT, amount.toString());
        setProperty(DESC, desc);
        setProperty(CHECK_NAME, checkName.name());
        if (isNotBlank(receiverName)) {
            setProperty(RE_USER_NAME, receiverName);
        }
    }

    @Override protected SimpleHttpClient getSimpleHttpClient() {
        if (sslContext == null) {
            throw new IllegalArgumentException("sslContext can not be null");
        } else {
            return new SimpleHttpClient(sslContext);
        }
    }

    @Override protected String getApiUrl() {
        return API_URL;
    }

    @Override protected TransferResponse parseResponse(String responseBody) {
        return new TransferResponse(responseBody);
    }

    @Override protected TreeSet<String> getSignParamNames() {
        return KEYS_PARAM_NAME;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }
}
