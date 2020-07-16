package com.biz.bizunited.wechat.payment.res;

import static org.codelogger.utils.DateUtils.getDateFromString;
import static org.codelogger.utils.StringUtils.isBlank;
import static com.biz.bizunited.wechat.payment.lang.Keys.*;
import java.util.Date;

import com.biz.bizunited.wechat.payment.lang.CheckName;
import com.biz.bizunited.wechat.payment.lang.TransferStatus;

public class TransferQueryResponse extends WechatPayResponseBase {

    public TransferQueryResponse(String responseXml) {
        super(responseXml);
    }

    /**
     * 商户订单号，需保持唯一性
     */
    public String getPartnerTradeNo() {
        return getProperty(PARTNER_TRADE_NO);
    }

    /**
     * 微信系统内部产生的单号
     */
    public String getDetailId() {
        return getProperty(DETAIL_ID);
    }

    /**
     * 转帐状态
     */
    public TransferStatus getTransferStatus() {
        return getEnumInstance(TransferStatus.class, getProperty(STATUS));
    }

    /**
     * 如果失败则有失败原因
     */
    public String getReason() {
        return getProperty(REASON);
    }

    /**
     * 收款用户姓名
     */
    public String getTransferName() {
        return getProperty(TRANSFER_NAME);
    }

    /**
     * 校验用户姓名选项
     */
    public CheckName getCheckName() {
        return getEnumInstance(CheckName.class, getProperty(CHECK_NAME));
    }

    /**
     * 实名验证结果
     */
    public String getCheckNameResult() {
        return getProperty(CHECK_NAME_RESULT);
    }

    /**
     * 付款金额单位(分）
     */
    public Integer getPaymentAmount() throws NumberFormatException {
        return Integer.valueOf(getProperty(PAYMENT_AMOUNT));
    }

    /**
     * 发起转账的时间
     */
    public Date getPaymentTime() {
        String paymentTime = getProperty(TRANSFER_TIME);
        return isBlank(paymentTime) ?
            null :
            getDateFromString(paymentTime, TRANSFER_TIME_DATE_FORMATER);
    }

    /**
     * 是否转账成功
     *
     * @return true 如果转账成功
     */
    public Boolean isTransferSuccess() {
        return isProcessSuccess() && getTransferStatus() == TransferStatus.SUCCESS;
    }

}
