package com.biz.bizunited.exception;

/**
 * 异常错误码定义表
 *
 * @author defei
 */
public interface ExceptionCode {

    /**
     * global 公共模块 1000开始增加
     */
    interface Global {

        /**
         * 无效的session或已经过期
         */
        int INVALID_SESSION_OR_ENCRYPT_TOKEN_EXPIRED = 407;

        /**
         * 提示给用户信息,
         */
        int INFO_TO_USER = 904;


        /**
         * 数据未改变
         */
        int DATA_NOT_CHANGE = 700;

        /**
         * 节点异常
         */
        int SERVER_EXCEPTION = 800; // 异常

        /**
         * 节点失效
         */
        int SERVER_FAIL = 900;

        /**
         * 签名失败
         */
        int SIN_ERROR = 901;

        /**
         * 设备不允许访问
         */
        int DEVICE_LOCKED = 902;

        /**
         * 参数错误
         */
        int PARAMETER_ERROR = 903;

        /**
         * 消息队列出错
         */
        int MQ_FAIL = SERVER_FAIL;

        /**
         * 60秒不能重复发送
         */
        int SMS_SENDTIME_INVALID = 1004;

        /**
         * 图片验证码错误
         */
        int VERIFY_IMAGE_CODE = 1005;

        /**
         * 短信验证码错误
         */
        int ILLEGAL_SMS_CODE = 1006;

        /**
         * 验证码超时
         */
        int VERIFY_TIMEOUT = 1007;

        /**
         * 用户ID参数不正确
         */
        int PARAM_USERID_ERROR = 1008;

        /**
         * ID不存在
         */
        int ID_NOT_EXISTS = 1009;

        /**
         * 无权操作
         */
        int NO_AUTHORITY = 1010;

        int VERIFY_SMS_NOT_UP = 1011;

        /**
         * 服务器数据错误
         */
        int SERVER_DATA_ERROR = 1012;

    }


    /**
     * 用户 1100开始
     */
    interface User {
        /**
         * 手机号已经存在
         */
        int MOBILE_EXISTED = 1100;

        /**
         * 用户号不存在
         */
        int USER_NOT_EXIST = 1101;

        /**
         * 密码不匹配
         */
        int PWD_NOT_MATCH = 1102;

        /**
         * 密码格式不正确
         */
        int ILLEGAL_PASSWORD = 1103;

        /**
         * 用户已被禁用
         */
        int USER_DISABLED = 1104;
        
        /**
         * 支付密码错误
         */
        int PAYMENT_PASSWORD_ERROR = 1105;
    }


    /**
     * 商户 1200开始
     */
    interface Shop {

        int SHOP_STATUS_UNSUITABILITY = 1200;

        int SHOP_DISABLED = 1201;

        int SHOP_NOT_EXIST = 1202;
    }

    /**
     * 商品 1400开始
     */
    public static interface Service {
        /**
         * 用户不具有该商品
         */
        int USER_NOT_EXITS_SERVICE = 1400;

        /**
         * 不具有该商品详情
         */
        int USER_NOT_EXITS_SERVICE_DETAIL = 1401;

        /**
         * 商品删除
         */
        int SERVICE_DELETE = 1402;

        /**
         * 商品下架
         */
        int SERVICE_DOWN = 1403;

        /**
         * 商品已经是当前状态
         */
        int SERVICE_STATUS = 1404;

        /**
         * 商品未上架
         */
        int SERVICE_NOT_ON_SALE = 1405;

        /**
         * 商品未审核通过
         */
        int SERVICE_NOT_ON_NORML = 1406;

        /**
         * 商品上下架状态错误
         */
        int SERVICE_ONSALE_ERROR = 1407;

        /**
         * 商品的H5页面不存在
         */
        int HTML_NOT_EXITS_SERVICE = 1408;
    }


    /**
     * TIMELINE 1500开始
     */
    public static interface Timeline {
        int TS_NOT_NULL = 1500;
        /**
         * timeline id不存在
         */
        int ID_NOT_EXITS = 1501;

        int COMMENT_NOT_EXIST = 1502;

        int COMMENT_CONTENT_IS_BLANK = 1503;

        int COMMENT_CONTENT_IS_INVALIDATE = 1504;
    }


    /**
     * 交易 1600 开始
     */
    public static interface Deal {
        //用户不具有该订单
        int USER_NOT_HAS_ORDER = 1600;
        //订单已经确定
        int ORDER_IS_CONFIRMED = 1601;
        //订单不存在
        int ORDER_IS_NOT_EXITS = 1602;
        //不能取消订单
        int ORDER_IS_NOT_CANCEL = 1603;

        //买家还未评价
        int BUYER_NOT_SCORE = 1604;
        //卖家还未评价
        int SEllER_NOT_SCORE = 1605;
        //评价不存在
        int SCORE_NOT_EXITS = 1606;
        //订单当前状态不能评价
        int SELL_NOT_SCORE = 1607;
        //交易超时
        int SELL_DUE = 1608;
        //买家好评不能修改
        int BUYER_GOOD_SCORE = 1609;
        //买家好评不能修改
        int SELLER_GOOD_SCORE = 1610;
        //买家已评价
        int BUYER_YES_SCORE = 1611;
    }


    /**
     * 收货地址
     * Address
     * maliang
     * 下午3:45:15
     * 1700开始
     */
    public static interface Address {
        //地址为空
        int ADDRESS_NULL = 1700;
        //用户不具有该地址
        int USER_NOT_ADDRESS = 1701;
        //手机 座机 二选一
        int USER_TEL_MOBILE = 1702;
    }


    /**
     * 活动 1800开始
     */
    public static interface Activity {
        /**
         * 活动不存在
         */
        int NOT_EXISTS = 1800;

        /**
         * 不是创建者
         */
        int IS_NOT_OWNER = 1801;

        /**
         * 不是成员
         */
        int IS_NOT_MEMBER = 1802;

        /**
         * 用户不能被踢出
         */
        int USER_CAN_NOT_KICKOUT = 1803;

        /**
         * 只能邀请加入
         */
        int INVITATION_ONLY = 1804;

        /**
         * 已经是正式成员
         */
        int IS_NORMAL_MEMBER = 1805;

        /**
         * 不是管理员
         */
        int IS_NOT_ADMIN = 1806;

        /**
         * 用户不能退出
         */
        int USER_CAN_NOT_QUIT = 1807;

        /**
         * 用户不能被设置为管理员, 如创建者
         */
        int USER_CAN_NOT_BE_SET_ADMIN = 1808;

        /**
         * 活动不是正常状态
         */
        int NOT_NORMARL = 1809;

        /**
         * 不存在加入申请
         */
        int NO_JOIN_APPLY = 1810;

        /**
         * Admin数量达到最大值
         */
        int ADMIN_COUNT_MAX = 1811;

        /**
         * 未过期活动数量达到最大值
         */
        int ACTIVITY_COUNT_MAX = 1812;


        /**
         * 不是活动报名类型（隔壁仓库报名和手动添加）
         */
        int NOT_JOIN_ACTIVITY_TYPE = 1813;

        /**
         * 没有活动成员
         */
        int NO_ACTIVITY_MEMBER_ID = 1814;
    }


    /**
     * 好友 1900开始
     */
    public static interface Friend {
        /**
         * 不是好友
         */
        int NOT_FRIEND = 1900;

        /**
         * 在黑名单中
         */
        int IN_BLACKLIST = 1901;

        /**
         * 好友申请不存在
         */
        int APPLY_NOT_EXIST = 1902;

        /**
         * 在对方用户黑名单中
         */
        int IN_TARGET_BLACKLIST = 1903;

        /**
         * 在我的黑名单中
         */
        int IN_MY_BLACKLIST = 1904;

        /**
         * 未绑定微信用户不能添加好友
         */
        int IS_WEIXIN = 1905;
    }


    /**
     * 快捷回复
     *
     * @author defei
     *         2000
     */
    public static interface Reply {
        /**
         * 用户不具有该快捷回复
         */
        int NOT_REPLY = 2000;
    }


    /**
     * 消息
     * <p/>
     * <p>从2100开始</p>
     *
     * @author defei
     * @date 2014年12月1日
     */
    public static interface Msg {

        /**
         * 生成消息文件出错
         */
        int GENERATE_FILE_ERROR = 2100;

        /**
         * 消息参数错误
         */
        int MSG_PARAM_ERROR = 2101;

    }


    /**
     * 客户端日志
     * <p/>
     * <p>从2200开始</p>
     */
    public static interface ClientLog {
        /**
         * 处理日志文件出错
         */
        int PROCESS_FILE_ERROR = 2200;
    }


    public static interface Web {
        /**
         * 文件未找到
         */
        int PAGE_NOT_FOUND = 404;

    }


    /**
     * 视频链接
     * <p>从2300开始</p>
     */
    public static interface VideoLink {

        /**
         * 视频不为用户所有
         */
        int NOT_USER_OWN = 2300;

        /**
         * 不存在
         */
        int NOT_EXIST = 2301;

        /**
         * 名称不合法 （如:包含敏感词）
         */
        int NAME_INVALID = 2302;

        /**
         * URL不合法
         */
        int URL_INVALID = 2303;

    }


    public static interface Token {
        /**
         * 获取Token失败，请重试
         */
        int TOKEN_REFRESH = 2400;
    }


    public static interface Voucher {
        /**
         * 无权查看优惠券
         */
        int OWNER_ERROR = 2500;

        /**
         * 优惠券不存在
         */
        int VOUCHER_NOT_EXISTS = 2501;


        /**
         * 店铺券不属于当前店铺
         */
        int VOUCHER_NOT_CURRENT_SHOP = 2502;


        /**
         * 店铺满减券金额计算错误
         */
        int VOUCHER_SHOP_FULL_CALCULATE_ERROR = 2503;
        //		/**
        //		 * 列出现金券时：提示信息
        //		 */
        //		int INFO_TO_VOUCHER=2502;

        /**
         * 优惠券不支持支付方式错误
         */
        int VOUCHER_PAYMENT_NOT_SUPPORT = 2504;

        /**
         * 优惠券已经过期错误
         */
        int VOUCHER_HAS_EXPIRED = 2505;

        /**
         * 优惠券减免金额校验错误
         */
        int VOUCHER_OFFSET_COUNT_ERROR = 2506;

        /**
         * 优惠券校验出错
         */
        int VOUCHER_VALIDATE_ERROR = 2507;

        /**
         * 优惠券不足
         */
        int VOUCHER_SHORTAGE_ERROR = 2508;
    }


    interface Order {

        /**
         * 不允许支付
         */
        int PAY_NOT_ALLOW = 2600;


        //用户不具有该订单
        int USER_NOT_HAS_ORDER = 2601;

        //订单不存在
        int NOT_EXISTS = 2602;

        //订单不能取消
        int CANNOT_CANCEL = 2603;

        //订单不能接单
        int CANNOT_ACCEPT = 2604;

        //订单不能拒绝
        int CANNOT_REJECT = 2605;

        //订单不能修改订单金额
        int CANNOT_EDIT = 2606;

        //订单不能确认交易
        int CANNOT_CONFIRM = 2607;

        //订单不能退款
        int CANNOT_REFUND = 2608;

        //订单不能回复退款
        int CANNOT_ANSWER_REFUND = 2609;

        //查询订单状态失败
        int QUERY_STATUS_FAIL = 2610;

        //确认码不存在
        int CONFIRM_CODE_NOT_EXISTS = 2620;

        //卖家已接单(活动)
        int DEAL_FINISH = 2621;

        //店铺类型<=100 不能创建乐慧订单
        int SHOPTYPE_NOT_CREATEORDER = 2622;

        //物流参数不合法
        int ORDER_LOGISTICS_PARAM_ILLEGAL = 2623;

        //物流参数不合法
        int ORDER_STATUS_ILLEGAL = 2624;

    }


    public static interface Payment {

        /**
         * 创建订单失败
         */
        int CREATE_PAYMENT_FAILED = 2700;
    }


    /**
     * 申诉
     *
     * @author defei
     *         2800 开头
     */
    public static interface Appeal {
        /**
         * (未处理)重复申诉
         */
        int REPEAT_APPEAL_STATUS = 2800;
    }


    /**
     * 乐惠
     *
     * @author defei
     *         2900 开头
     */
    public static interface LEHUI {
        /**
         * 商铺没有乐惠
         */
        int SHOP_NOT_HAVE = 2900;
    }


    public static interface Employee {
        /**
         * 已是其他店铺的雇员
         */
        int IS_SHOP_EMPLOYEE = 3000;

        /**
         * 用户已是店主不能添加为雇员
         */
        int EMPLOYEE_OPEN_SHOP = 3001;

        /**
         * 不是当前店铺雇员
         */
        int IS_NOT_SHOP_EMPLOYEE = 3002;

        /**
         * 不是雇员
         */
        int IS_NOT_EMPLOYEE = 3003;

        /**
         * 该用户已设置为接单员
         */
        int IS_SHOP_SALESMAN_EMPLOYEE = 3004;

        /**
         * 该用户已设置为送货人
         */
        int IS_SHOP_DELIVERYMAN_EMPLOYEE = 3005;

    }


    public static interface BalanceAcount {
        /**
         * 已设置过支付密码
         */
        int EXIST_PWD = 3100;

        /**
         * 钱包余额不足
         */
        int BALANCE_NOT_ENOUGH = 3101;

        /**
         * 密码被锁定
         */
        int PWD_LOCK = 3102;

        /**
         * 支付密码错_1
         */
        int PWD_NOT_MATCH_ONE = 3103;

        /**
         * 支付密码错_2
         */
        int PWD_NOT_MATCH_TWO = 3104;

        /**
         * 还未设置支付密码
         */
        int NOT_EXIST_PWD = 3105;

        /**
         * 充值金额小于1分钱
         */
        int RECHARGE_BALANCE_NOT_ENOUGH = 3106;

        /**
         * 超出当日支付金额
         */
        int OUT_CURRENT_DAY_MONEY = 3107;

        /**
         * 超出单次支付金额
         */
        int OUT_ONCE_MONEY = 3108;
        
        /**
         * 预存款金额不足
         */
        int LACK_OF_MONEY = 3109 ;
        
        /**
         * 申请金额大于可提现金额
         */
        int WITHDRAWALS_IS_INSUFFICIENT = 3110;
    }


    /**
     * 秒杀异常码
     * <br>
     * 3200开始
     *
     * @author defei
     * @date 2015年11月25日
     */
    public static interface Seckill {
        /**
         * 店铺不支持发布秒杀
         */
        int NOT_SUPPORT_ISSUE_SECKILL = 3200;
        /**
         * 不允许修改
         */
        int NOT_ALLOWED_TO_UPDATE = 3201;
        /**
         * 不允许删除
         */
        int NOT_ALLOWED_TO_DELETE = 3202;
    }


    /**
     * 优惠券相关异常
     */
    interface LuckMoney {

        /**
         * 创建红包失败红包
         */
        int CREATE_LUCK_MONEY_FAILED = 3300;

        /**
         * 红包不存在
         */
        int LUCK_MONEY_NOT_EXIST = 3301;
    }
    
    interface Product{
      
      /**
       * 商品不存在
       */
      int NOT_EXISTS = 3400;
      
      
      /**
       * 商品下架
       */
      int NOT_ONSALE = 3401;
      
      /**
       * 无权查看
       */
      int PERMISSION_DENIED = 3402;
      
    }

    interface OCS {

        /**
         * 无效token
         */
        int TOKEN_INVALID = 3500;

        /**
         * 参数异常
         */
        int PARAM_INVALID = 3501;

        /**
         * 逻辑错误
         */
        int LOGIC_ERROR = 3502;
    }


    interface Nuomi {
        /**
         * 时间戳为空
         */
        int TIMESTAMP_EMPTY = 9001;

        /**
         * 时间戳有效范围 (默认30s)
         */
        int TIMESTAMP_EXPIRED = 9002;

        /**
         * 参数不合法(创建订单)
         */
        int PARAM_INVALID = 9020;

        /**
         * 商品数量小于最小起售量
         */
        int QUANTITY_LESS_THAN_MIN = 9021;

        /**
         *  销售区域已经更改不能下单
         */
        int AREA_INVALID = 9022;

        /**
         * 商品下架
         */
        int PRODUCT_OFFLINE = 9023;

        /**
         * 库存不足
         */
        int UNDERSTOCK = 9024;

        /**
         * 参数不合法(查询订单)
         */
        int QUERY_PARAM_INVALID = 9030;

        /**
         * 订单id不存在
         */
        int ORDER_ID_NOT_EXIST = 9031;
        

        /**
         * 超出限购数量
         */
        int OUT_OF_LIMIT = 9032;
    }

    interface Refund {
        /**
         * 售后申请不存在
         */
        int REFUND_NOT_EXIST = 9070;
        /**
         * 售后申请状态异常
         */
        int REFUND_STATUS_EXCEPTION = 9071;
    }

    interface Account {
        /**
         * 帐号修改支付密码权限不足
         */
        int CHANGE_PAYMENT_PASSWORD_PERMISSION_DENIED = 7004;

        /**
         * 更改支付密码两次密码输入不一致
         */
        int CONFIRM_PAYMENT_PASSWORD_FAILD = 7002;

        /**
         * 原支付密码输入错误
         */
        int PAYMENT_PASSWORD_NOT_CORRECT = 7003;

        /**
         * 更新商户资质图片出错
         */
        int UPDATE_SHOP_CERTIFICATE_IMGS_FAILED = 7005;

        /**
         * 商户未设置支付密码
         */
        int SHOP_PAYMENT_PASSWORD_NOT_EXIST = 7006;

        /**
         * 拉取商户资质图片类型参数错误
         */
        int NOT_SUPPORT_IMG_TYPE = 7007;
        
        /**
         * 用户没有资格申请徙木账户
         */
        int NOT_QUALIFICATION_APPLY_XIMU = 7008;

        /**
         * 图片信息不完全
         */
        int IMG_NOT_COMPLETE = 7009;

        /**
         * 上传图片参数错误
         */
        int IMG_UPLOAD_PARAM_ERROR = 7010;

        /**
         * 缺少参数
         */
        int PARAM_NOT_COMPLETE = 7011;

        /**
         * 未知的贷款服务类型
         */
        int UNKNOWN_LOAN_TYPE = 7012;
    }

    interface Promotion{

        /**
         * 促销不存在
         */
        int NOT_EXISTS = 7501;




    }
}
