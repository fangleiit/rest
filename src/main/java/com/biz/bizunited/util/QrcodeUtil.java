package com.biz.bizunited.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * @ClassName: QrcodeUtil 
 * @Description: 二维码
 * @author ian.zeng
 * @date 2017年7月24日 下午3:50:15
 */
public class QrcodeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(QrcodeUtil.class);
	
	/**
	 * 获取产品经销商信息
	 * @param qrcode
	 * @return
	 */
	public static JSONObject getCustAndProduct(String qrcode){
		String result = "";
		JSONObject json = new JSONObject();
		try {
			String urlstr = String.format("", URLEncoder.encode(qrcode, "UTF-8"));
			URL url = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);    
            conn.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;    
	        while ((line = in.readLine()) != null) {    
	        	result += line+"\n";    
	        }    
            in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
