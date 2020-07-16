package com.biz.bizunited.wechat.payment.lang;

public enum CheckName {

    /**
     * 不校验真实姓名
     */
    NO_CHECK,

    /**
     * 强校验真实姓名（未实名认证的用户会校验失败，无法转账）
     */
    FORCE_CHECK,

    /**
     * 针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
     */
    OPTION_CHECK

}
