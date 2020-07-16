package com.biz.bizunited.wechat.payment;

import static com.biz.bizunited.wechat.payment.lang.Keys.APPID;
import static com.biz.bizunited.wechat.payment.lang.Keys.DEFAULT_APP_ID;
import static com.biz.bizunited.wechat.payment.lang.Keys.KEY;
import static com.biz.bizunited.wechat.payment.lang.Keys.LOAD_IDENTIFICATION;
import static com.biz.bizunited.wechat.payment.lang.Keys.MCHID;
import static com.biz.bizunited.wechat.payment.lang.Keys.MCH_APPID;
import static com.biz.bizunited.wechat.payment.lang.Keys.MCH_ID;
import static com.biz.bizunited.wechat.payment.lang.Keys.NONCE_STR;
import static com.biz.bizunited.wechat.payment.lang.Keys.SPBILL_CREATE_IP;
import static java.lang.String.format;
import static org.codelogger.utils.StringUtils.isBlank;

import java.io.InputStreamReader;
import java.util.Properties;

import org.codelogger.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.bizunited.wechat.payment.exceptions.MissingResourceException;
import com.biz.bizunited.wechat.payment.lang.CertificateLoader;
import com.biz.bizunited.wechat.payment.lang.CheckName;
import com.biz.bizunited.wechat.payment.lang.Signer;
import com.biz.bizunited.wechat.payment.req.Transfer;
import com.biz.bizunited.wechat.payment.req.TransferQuery;
import com.biz.bizunited.wechat.payment.req.WechatPayRequestBase;

public class WeChatPayFactory {

    private static final Logger logger = LoggerFactory.getLogger(WeChatPayFactory.class);

    private static final int NONCE_STRING_LENGTH = 16;
    public static final String CONFIG_PATH = "/weixinConfig.properties";
    public static final String UTF_8 = "utf-8";

    private static Properties conf;


    private static class WeChatPayFactoryHolder {
        static WeChatPayFactory instance = new WeChatPayFactory();
    }

    private WeChatPayFactory() {
        conf = new Properties();
        try {
            logger.info("load wechat payment configurations.");
            conf.load(new InputStreamReader(WeChatPayFactory.class.getResourceAsStream(CONFIG_PATH),
                UTF_8));
        } catch (Exception ex) {
            throw new MissingResourceException(
                format("Failed to load conf resource [%s]", CONFIG_PATH), ex);
        }
    }


    public static WeChatPayFactory newInstance() {

        return WeChatPayFactoryHolder.instance;
    }

    public Signer newSigner(String appId) {
        return new Signer(conf, getPropertyByAppId(KEY, appId));
    }
    /**
     * 微信转账
     *
     * @param partnerTradeNo 商户的转账订单号。必填
     * @param openid         微信用户openid。必填
     * @param amount         付款金额，单位为分。必填
     * @param desc           付款操作说明信息。必填
     * @param checkName      校验用户姓名选项。必填
     * @param receiverName   收款用户姓名。如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名。
     * @return {@link Transfer}
     */
    public Transfer newTransfer(String appId, String partnerTradeNo, String openid, Integer amount,
        String desc, CheckName checkName, String receiverName) {
        Transfer transfer =
            new Transfer(partnerTradeNo, openid, amount, desc, checkName, receiverName);
        transfer.setSslContext(new CertificateLoader(getPropertyByAppId(LOAD_IDENTIFICATION, appId),
            getPropertyByAppId(MCH_ID, appId)).getSSLContext());
        setConfigurations(appId, transfer);
        transfer.setProperty(MCH_APPID, getFixedAppId(appId));
        transfer.setProperty(MCHID, getPropertyByAppId(MCH_ID, appId));
        transfer.setProperty(SPBILL_CREATE_IP, conf.getProperty(SPBILL_CREATE_IP));
        return transfer;
    }

    /**
     * 微信转账查询
     *
     * @param partnerTradeNo 商户转账单号
     * @return {@link TransferQuery}
     */
    public TransferQuery newTransferQuery(String appId, String partnerTradeNo) {
        TransferQuery refundQuery = new TransferQuery(partnerTradeNo);
        setConfigurations(appId, refundQuery);
        refundQuery.setSslContext(
            new CertificateLoader(getPropertyByAppId(LOAD_IDENTIFICATION, appId),
                getPropertyByAppId(MCH_ID, appId)).getSSLContext());
        return refundQuery;
    }
    
    private void setConfigurations(String appId, WechatPayRequestBase wechatPayRequestBase) {
        wechatPayRequestBase.setProperties(conf);
        wechatPayRequestBase.setProperty(APPID, getFixedAppId(appId));
        wechatPayRequestBase.setProperty(MCH_ID, getPropertyByAppId(MCH_ID, appId));
        wechatPayRequestBase.setProperty(KEY, getPropertyByAppId(KEY, appId));
        wechatPayRequestBase
            .setProperty(NONCE_STR, StringUtils.getRandomPasswordString(NONCE_STRING_LENGTH));
    }

    private String getFixedAppId(String appId) {
        return isBlank(appId) ? conf.getProperty(DEFAULT_APP_ID) : appId;
    }

    private String getPropertyByAppId(String propertyKey, String appId) {
        String fixedAppId = getFixedAppId(appId);
        return conf.getProperty(propertyKey + "." + fixedAppId);
    }

}
