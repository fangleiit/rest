package com.biz.bizunited.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.service.WeiXinAccessTokenService;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.wechat.bean.AccessToken;


public class WeixinUtil {
	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);
	/**获取access_token的接口地址（GET） 限200（次/天）**/
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	/**微信网页授权获取CODE**/
    public static String AUTHORIZE_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE&connect_redirect=1#wechat_redirect";
    /**微信网页授权获取网页accesstoken和OPENID**/
    public static String WEB_OAUTH_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**获取用户基本信息接口地址  snsapi_userinfo**/
    public static String USER_INFO_URL_BASE = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    /**获取用户基本信息接口地址  snsapi_base**/
    public static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
    /**jsapi调用接口临时凭证的接口地址（GET） 限200（次/天）**/
  	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = { new MyX509TrustManager() };
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                URL url = new URL(requestUrl);
                HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
                httpUrlConn.setSSLSocketFactory(ssf);

                httpUrlConn.setDoOutput(true);
                httpUrlConn.setDoInput(true);
                httpUrlConn.setUseCaches(false);
                // 设置请求方式（GET/POST）
                httpUrlConn.setRequestMethod(requestMethod.toUpperCase());

                if ("GET".equalsIgnoreCase(requestMethod))
                        httpUrlConn.connect();

                // 当有数据需要提交时
                if (null != outputStr) {
                        OutputStream outputStream = httpUrlConn.getOutputStream();
                        // 注意编码格式，防止中文乱码
                        outputStream.write(outputStr.getBytes("UTF-8"));
                        outputStream.close();
                }

                // 将返回的输入流转换成字符串
                InputStream inputStream = httpUrlConn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                        buffer.append(str);
                }
                bufferedReader.close();
                inputStreamReader.close();
                // 释放资源
                inputStream.close();
                inputStream = null;
                httpUrlConn.disconnect();
                jsonObject = JSONObject.parseObject(buffer.toString());
                //jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
        	com.biz.bizunited.util.LogUtil.info("Weixin server connection timed out.");
        } catch (Exception e) {
        	com.biz.bizunited.util.LogUtil.info("https request error:{}"+e.getMessage());
        }
        return jsonObject;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static JSONObject sendPost(String url, String param,String contentType) {
    	JSONObject jsonObject = null;
    	 PrintWriter out = null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection httpUrlConnection = realUrl.openConnection();
            //HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 设置通用的请求属性
            httpUrlConnection.setRequestProperty("accept", "*/*");
            httpUrlConnection.setRequestProperty("connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(!StringUtil.isEmpty(contentType)){
            	httpUrlConnection.setRequestProperty("Content-Type", contentType);
            }
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            out = new PrintWriter(httpUrlConnection.getOutputStream());
            out.print(param);
            out.flush();
            InputStream inputStream = httpUrlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	buffer.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            //httpUrlConnection.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        return jsonObject;
    } 
    public static String sendGet(String url, String param) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            String urlNameString = url + "?" + param;  
            URL realUrl = new URL(urlNameString);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 建立实际的连接  
            connection.connect();  
            // 获取所有响应头字段  
            Map<String, List<String>> map = connection.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : map.keySet()) {  
            }  
            // 定义 BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送GET请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }
    
    /**
     * 获取Request请求的路径信息 带参数
     * @param request
     * @return
     */
    public static String getRequestUrlWithParams(HttpServletRequest request){
  	  String backurl = request.getScheme()+"://"+request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
  	  return backurl;
    }
    /**
	 * 获取浏览用户的openId
	 * @return
	 */
	public static final String getUserOpenId() {
		HttpSession session = ContextHolderUtils.getSession();
		Object userOpenId = session.getAttribute(Constants.USER_OPENID);
		if(userOpenId!=null){
			return userOpenId.toString();
		}else{
			return null;
		}
	} 
	
	/**
	 * 调用微信author2.0 通用方法
	 * @param request    前台请求
	 * @return
	 */
	public static String callWeixinAuthor2ReturnUrl(HttpServletRequest request,String tagetUrl,String appId,String appSecret,WeiXinAccessTokenService accessTokenService){
		/**通过Oauth2.0获取openid_end**/
		String openId = request.getParameter("openid");
		if(StringUtils.isEmpty(openId)){
			openId = getUserOpenId();
		}
		if(StringUtil.isEmpty(openId)){
			//-------------------------------------------------------------------------------------------------------------------------------------
			String code = request.getParameter("code");
			logger.info("code的值="+code);
			//1.判断是否有code值,没有则跳转到授权地址
			if(StringUtil.isEmpty(code)){
				logger.info("targetURL的值="+tagetUrl);
				String redirectURL = OAuth2Util.obtainWeixinOAuth2Url(tagetUrl,appId,OAuth2Util.SNSAPI_BASE);
				return redirectURL;
			}
			// 2.不用用户同意即可获取了code的值
			if (!"authdeny".equals(code)) {
				JSONObject josn= getOauth2AccessToken(appId,appSecret, code);
				openId = (String)josn.get("openid");
				if(!StringUtil.isEmpty(openId)){
					AccessToken accessToken = accessTokenService.getAccessToken(appId, appSecret);
					if(null != accessToken){
						JSONObject userInfoJson = getUserInfo(accessToken.getToken(), (String)josn.get("openid"));
						openId =  (String)userInfoJson.get("openid");
					}
				}
			}
			request.getSession().setAttribute(Constants.USER_OPENID, openId);
		}
		return null;
	}
	/**
	 * 方法描述:  获取网页授权凭证
	 * @return 
	 */
	public static JSONObject getOauth2AccessToken(String appId,String appSecret,String code) {
		String requestUrl = WEB_OAUTH_ACCESSTOKEN_URL;
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE",code);
		return sendPost(requestUrl,null,null);
	}
	
	/**
	 * 方法描述:  获取用户信息
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static JSONObject getUserInfoBase(String accessToken,String openId){
		String url = USER_INFO_URL_BASE.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		return httpRequest(url, "GET", null);
	}
	/**
	 * 方法描述:  获取用户信息
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static JSONObject getUserInfo(String accessToken,String openId){
		String url = USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		return httpRequest(url, "GET", null);
	}
	/**
	 * 获取access_token
	 */
	public static JSONObject getAccessToken(String appid,String appsecret ){
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        return httpRequest(requestUrl, "GET", null);
	}
	
	/**
	 * 获取jspAPI调用凭证
	 */
	public static JSONObject getJsapiTicket(String accessToken){
		 String jsapi_ticket_url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
		 return httpRequest(jsapi_ticket_url, "GET", null);
	}
	/**
	 * 获取JspAPI签名
	 * @return
	 */
	public static String getJspAPISignature(String need_make_string){
		String signature = null;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(need_make_string.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signature;
	}
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    public static void main(String[] args) {
    	String accessToken = "XIT3rzf_h_4wLo_pLoMujWQtT4YdN9Vze1fxmMAGEVvXNXTLqxERrX_vh5EzxzcCiEynPw5OlfKafxn_tp-khJPjt9kzTydQx7WGu6sX01EF_1w8Xe5qyPMW35raRqV4FHKfAAAKMP";
    	String openId = "oI3WwwSfJK--uzmpFisN_wfN8rDs";
    	JSONObject json = getUserInfoBase(accessToken, openId);
    	System.out.println(json);
    	
    	/*String shopBn = "ecstoreb2b2c";
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    	String timestamp = format.format(new Date());
    	String sign=Md5Util.GetMD5Code("shopBn"+shopBn+"timestamp"+timestamp);
    	*//**
    	 * 会员查询
    	 *//*
    	String findParam = "shopBn="+shopBn+"&timestamp="+timestamp+"&sign="+sign+"&name=15928138195&channelCode=1919kuaihe";
    	JSONObject json = sendPost(MEMBER_INFO_URL, findParam);
    	System.out.println(json);
    	
    	*//**
    	 * 会员绑定
    	 *//*
    	String loginParam = "shopBn="+shopBn+"&timestamp="+timestamp+"&sign="+sign+
    			"&name=15928138195&channelCode=1919kuaihe&password="+Md5Util.GetMD5Code("123456")+"&originalpwd=123456";
    	json = sendPost(MEMBER_BIND_URL, loginParam);
    	System.out.println(json);*/
    	/**
    	 * 获取图文列表
    	 */
    	/*String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=1v1cAB6ULbH3oiyjdsFEKhhzPeRreG0QItAkWF9J3RRrY9q3zGQOywUkPjarYfhc_RW6GDeWYe9Y7pwPJgzQS0A_PVL1Up615P7Ap-f2D58W9rz5I6nWigB3NHitkEMiNAHeCGAQDK";
    	JSONObject paramJson = new JSONObject();
    	paramJson.put("type", "news");
    	paramJson.put("offset", 0);
    	paramJson.put("count", 1);
    	
    	JSONObject json = sendPost(url, paramJson.toString());
    	System.out.println(json);*/
    	/**
    	 * 
    	 */
    	/*String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=1v1cAB6ULbH3oiyjdsFEKhhzPeRreG0QItAkWF9J3RRrY9q3zGQOywUkPjarYfhc_RW6GDeWYe9Y7pwPJgzQS0A_PVL1Up615P7Ap-f2D58W9rz5I6nWigB3NHitkEMiNAHeCGAQDK";
    	JSONObject paramJson = new JSONObject();
    	paramJson.put("media_id", "i3FC8SSqihnlRkNLsDLBEblk34nRDRzS_GO_nYfwlgo");
    	
    	JSONObject json = sendPost(url, paramJson.toString());
    	System.out.println(json);*/
    	/**
    	 * 测试短信发送
    	 */
    	/*String url = "http://182.139.182.80:9239/cmp/send.do";
    	JSONObject paramJson = new JSONObject();
    	paramJson.put("channelCode", "weixin");//渠道
    	paramJson.put("token", "3ed13d60af854bd2be2e08027410d738");//token
    	paramJson.put("requestId", UUID.randomUUID().toString());//发送请求的唯一编号
    	paramJson.put("signature", "壹玖壹玖");//短信签名
    	paramJson.put("content", "短信测试");//短信内容
    	//paramJson.put("signaturePosition", "weixin");//签名位置
    	paramJson.put("mobile", "18608116390");//接收者手机号
    	JSONObject json = sendPost(url, paramJson.toString(),"application/json");
    	System.out.println(json);*/
    	
    	//String json = "{'msgtype':'text','text':{'content':'test'},'touser':'ocNJvv6TNBe7lvkUhFfzj1f-eTEU'}";
    	/*TextMessageKf customMessage = new TextMessageKf();
		customMessage.setMsgtype("text");
		Text item = new Text();
		item.setContent("test");
		customMessage.setText(item);
		customMessage.setTouser("ocNJvv6TNBe7lvkUhFfzj1f-eTEU");
		//装换为json
		JSONObject jsonObj = JSONObject.fromObject(customMessage);
    	String accessTocken = "WE-UfzbnVGTyC5ga7HkNAesvb07AOWbljjJrAV0_gYWkOlaD_QrgZyH9zMzAuxLfagMyZiaj3XlSXj0RdLBIMC4WSjeOHZQT-dtpG25wsB4KRFgAJAFMA";
    	String url = send_message_url.replace("ACCESS_TOKEN",accessTocken);
    	JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonObj.toString());
    	System.out.println(jsonObject);*/
    	/**
    	 * 模板消息
    	 */
    	/*JSONObject dataJSon = new  JSONObject();
    	dataJSon.put("content",toJson("测试消息"));
    	JSONObject parentJSON = new JSONObject();
    	parentJSON.put("touser", "ocNJvvxuO1QQCvHqBzia5bWeAafg");
    	parentJSON.put("template_id", "XN5m7tt343cKKrWt1UPWr3evXRjsevOpiVANqg9o0_g");
    	parentJSON.put("data", dataJSon);
    	String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=M5xtJSfDAMAu5MwKjJwgWxtmyMJgLFX2VO_ZD4WwDlaLBK8E2jyTxJ6tVAbv8yJZ3ErRfXjOBy2yaFsxsA1wzCXssggBrvf03IXn9p65TyoDrYeiLehRa6ykJWLXuAdLPLDcAGASOG";
    	JSONObject jsonObject = sendPost(url,URLEncoder.encode(parentJSON.toString()),"application/json");
    	System.out.println(jsonObject);*/
    	/*for (OpenMessageType e : OpenMessageType.values()) {
            System.out.println(e.toString());
        }*/
		/*OpenMessageType openMessageType = OpenMessageType.getOpenMessageTypeByCode("OPENTM203231299");
		System.out.println(openMessageType);*/
    	

	}
    public static JSONObject toJson(String value){
        JSONObject json = new JSONObject();
        json.put("value", value);
        //json.put("color", "#173177");//消息字体颜色
        return json;
    }

}
