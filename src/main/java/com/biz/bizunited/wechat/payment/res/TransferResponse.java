package com.biz.bizunited.wechat.payment.res;

import java.util.Date;

import static com.biz.bizunited.wechat.payment.lang.Keys.*;
import static org.codelogger.utils.DateUtils.getDateFromString;
import static org.codelogger.utils.StringUtils.isBlank;

public class TransferResponse extends WechatPayResponseBase {

    public TransferResponse(String responseXml) {
        super(responseXml);
    }

    @Override public String getAppId() {
        return getProperty(MCH_APPID);
    }

    @Override public String getMchId() {
        return getProperty(MCHID);
    }

    public String getPartnerTradeNo() {
        return getProperty(PARTNER_TRADE_NO);
    }

    public String getPaymentNo() {
        return getProperty(PAYMENT_NO);
    }

    public Date getPaymentTime() {
        String paymentTime = getProperty(PAYMENT_TIME);
        return isBlank(paymentTime) ?
            null :
            getDateFromString(paymentTime, PAYMENT_TIME_DATE_FORMATER);
    }

    public Boolean isTransferSuccess() {
        return isProcessSuccess();
    }

}
