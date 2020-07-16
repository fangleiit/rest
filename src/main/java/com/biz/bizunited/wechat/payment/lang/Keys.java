package com.biz.bizunited.wechat.payment.lang;

public interface Keys {

    String DEFAULT_APP_ID = "appid.default";

    /**
     * 应用id
     */
    String APPID = "appid";

    /**
     * 商户id
     */
    String MCH_ID = "mch_id";

    /**
     * 干扰字符串
     */
    String NONCE_STR = "nonce_str";

    /**
     * 微信私钥，只能存服务器，并切切记保证安全
     */
    String KEY = "key";

    /**
     * 签名
     */
    String SIGN = "sign";

    /**
     * 订单简要描述
     */
    String BODY = "body";

    /**
     * 总金额
     */
    String TOTAL_FEE = "total_fee";

    /**
     * 现金支付金额
     */
    String CASH_FEE = "cash_fee";

    /**
     * 货币种类
     */
    String FEE_TYPE = "fee_type";

    /**
     * 现金支付货币类型
     */
    String CASH_FEE_TYPE = "cash_fee_type";

    /**
     * 代金券或立减优惠金额
     */
    String COUPON_FEE_0 = "coupon_fee_0";

    /**
     * 代金券或立减优惠使用数量
     */
    String COUPON_COUNT = "coupon_count";

    /**
     * 代金券或立减优惠ID
     */
    String COUPON_ID_0 = "coupon_id_0";

    /**
     * 客户端支付时的ip地址，转账时为调用接口的机器Ip地址
     */
    String SPBILL_CREATE_IP = "spbill_create_ip";

    /**
     * 支付类型
     */
    String TRADE_TYPE = "trade_type";

    /**
     * 微信用户在当前app中的id
     */
    String OPENID = "openid";

    String PRODUCT_ID = "product_id";

    /**
     * 微信支付订单号
     */
    String TRANSACTION_ID = "transaction_id";

    /**
     * 隔壁仓库订单号
     */
    String OUT_TRADE_NO = "out_trade_no";

    /**
     * 隔壁仓库退款单号
     */
    String OUT_REFUND_NO = "out_refund_no";

    /**
     * 返回结果代码
     */
    String RETURN_CODE = "return_code";

    /**
     * 返回消息
     */
    String RETURN_MSG = "return_msg";

    /**
     * 处理结果代码
     */
    String RESULT_CODE = "result_code";

    /**
     * 预付单号
     */
    String PREPAY_ID = "prepay_id";

    /**
     * 支付结果通知接口
     */
    String NOTIFY_URL = "notify_url";

    /**
     * 开始时间
     */
    String TIME_START = "time_start";

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     */
    String TIME_EXPIRE = "time_expire";

    /**
     * time end字段日期格式化模版
     */
    String TIME_EXPIRE_DATE_FORMATER = "yyyyMMddHHmmss";

    /**
     * 商品标签
     */
    String GOODS_TAG = "goods_tag";

    /**
     * 设备信息
     */
    String DEVICE_INFO = "device_info";

    /**
     * 商家附加数据
     */
    String ATTACH = "attach";

    /**
     * 交易单号
     */
    String TRADE_STATE = "trade_state";

    /**
     * 错误码
     */
    String ERR_CODE = "err_code";

    /**
     * 错误描述
     */
    String ERR_CODE_DES = "err_code_des";

    /**
     * 退款金额
     */
    String REFUND_FEE = "refund_fee";

    /**
     * 操作员
     */
    String OP_USER_ID = "op_user_id";

    /**
     * 退款渠道
     */
    String REFUND_CHANNEL = "refund_channel";

    /**
     * 退款单号
     */
    String REFUND_ID = "refund_id";

    /**
     * 退款状态
     */
    String REFUND_STATUS_0 = "refund_status_0";

    /**
     * 银行类型，参看微信对照表
     */
    String BANK_TYPE = "bank_type";

    /**
     * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     */
    String TIME_END = "time_end";

    /**
     * time end字段日期格式化模版
     */
    String TIME_END_DATE_FORMATER = "yyyyMMddHHmmss";

    String LOAD_IDENTIFICATION = "LOAD_IDENTIFICATION";


	/*企业付款api begin*/

    /**
     * 商户appid
     */
    String MCH_APPID = "mch_appid";

    /**
     * 微信支付分配的商户号
     */
    String MCHID = "mchid";

    /**
     * 商户订单号，需保持唯一性
     */
    String PARTNER_TRADE_NO = "partner_trade_no";

    /**
     * 付款成功，返回的微信订单号
     */
    String PAYMENT_NO = "payment_no";

    /**
     * 微信系统内部产生的单号
     */
    String DETAIL_ID = "detail_id";

    /**
     * 转帐状态
     */
    String STATUS = "status";

    /**
     * 如果失败则有失败原因
     */
    String REASON = "reason";

    /**
     * 付款成功时间
     */
    String PAYMENT_TIME = "payment_time";

    /**
     * payment_time字段日期格式化模版
     */
    String PAYMENT_TIME_DATE_FORMATER = "yyyy-MM-dd HH:mm:ss";

    /**
     * 收款用户姓名
     */
    String TRANSFER_NAME = "transfer_name";

    /**
     * 付款金额单位(分）
     */
    String PAYMENT_AMOUNT = "payment_amount";

    /**
     * 发起转账的时间
     */
    String TRANSFER_TIME = "transfer_time";

    /**
     * transfer_time字段日期格式化模版
     */
    String TRANSFER_TIME_DATE_FORMATER = "yyyy-MM-dd HH:mm:ss";

    /**
     * 校验用户姓名选项
     */
    String CHECK_NAME = "check_name";

    /**
     * 收款用户姓名
     */
    String RE_USER_NAME = "re_user_name";

    /**
     * 金额
     */
    String AMOUNT = "amount";

    /**
     * 企业付款描述信息
     */
    String DESC = "desc";

    /**
     * 实名验证结果
     */
    String CHECK_NAME_RESULT = "check_name_result";

	/*企业付款api end*/
}
