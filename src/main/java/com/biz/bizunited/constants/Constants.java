package com.biz.bizunited.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public interface Constants {

    String ATTR_AGENT_SPIDER = "agent_spider";
    String ATTR_AGENT_ANDROID = "agent_android";
    String ATTR_AGENT_IOS = "agent_ios";
    String ATTR_IS_MOBILE = "agent_mobile";
    //浏览器是微信
    String ATTR_IS_WEIXIN = "agent_weixin";
    String ATTR_IS_QQ = "agent_qq";
    String ATTR_IS_MOBILE_QQ_BROWSER = "agent_mobile_qq_browser";
    String ATTR_IS_MOBILE_UC_BROWSER = "agent_mobile_uc_browser";
    String ATTR_IS_NUOMI = "channel_nuomi";


    String ATTR_HOME_CITY = "current_city";
    String ATTR_HOME_CITY_CODE = "current_city_code";
    String ATTR_IP_CITY = "ip_city";
    String ATTR_CITYID_KEY = "current_city_id";
    String ATTR_LAT = "lat";
    String ATTR_LON = "lon";
    String ATTR_ADDRESS = "address";
    String ATTR_CATEGORY_CODE = "current_category_code";

    String ATTR_WEIXIN_OPENID = "weixin_openid";
    String ATTR_WEIXIN_CODE = "weixin_code";

    String ATTR_SESSION_ID = "session_id";

    String ATTR_USERID = "userId";   //用户Id Integer

    String ATTR_USER = "user";   //用户

    String ATTR_COOKIE_CITY_KEY = "cookie_city";


    String COOKIE_WEIXIN_CODE = "weixincode";
    String COOKIE_WEIXIN_OPENID = "weixinopenid";
    String COOKIE_USER_ID = "userid";
    String COOKIE_SESSION_ID = "sessionid";

    String COOKIE_DOMAIN = ".depotnextdoor.com";

    int COOKIE_AGE = 3600 * 24 * 30; 

    Map<String, String> firstLetterScope = new HashMap<String, String>();

    public static final String USER_OPENID = "USER_OPENID";
    
    String imgBaseProjectSuffPath = "../../../appealPic";
    
    //短信验证码超时时间(毫秒)
    Integer TIME_OUT = 120000;
    //每表同步数据条数
    Integer SYNC_COUNT = 100;
    
    /**可扫码时间段*/
    public static final String S_DATE = "06:00:00";
    public static final String E_DATE = "23:00:00";
    
    /**防窜平台页面*/
    public static final String SYS_URL = ResourceBundle.getBundle("transfer").getString("sys.url");
}
