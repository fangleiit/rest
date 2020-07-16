package com.biz.bizunited.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	private HttpClientUtil(){}
	
	public static String doPost(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
        	httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();
        }  
        return result;  
    }
	
	public static String doGet(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpGet httpGet = null;  
        String result = null;  
        try{  
        	httpClient = HttpClientBuilder.create().build();
        	httpGet = new HttpGet(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                String string = EntityUtils.toString(new UrlEncodedFormEntity(list, "UTF-8"));
                httpGet = new HttpGet(url+"?"+string);
            }  
            HttpResponse response = httpClient.execute(httpGet);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();
        }  
        return result;  
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
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static JSONObject sendGet(String url, String param, String contentType) {
    	JSONObject jsonObject = null;
    	PrintWriter out = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = null;
        try {
        	URL realUrl = new URL(url);
        	// 打开和URL之间的连接
        	URLConnection connection = realUrl.openConnection();
        	// 设置通用的请求属性
        	connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            in = new BufferedReader(inputStreamReader);
            String line;
            while ((line = in.readLine()) != null) {
            	buffer.append(line);
            }
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (Exception e) {
            System.out.println("发送 GET 请求出现异常！"+e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return jsonObject;
    }
	/*public static void main(String[] args) {
		String openId = "oI3WwwSfJK--uzmpFisN_wfN8rDI";
		String changeStatus = "IS_BLACKLIST";
		String paramJson = "openId="+openId+"&changeStatus="+changeStatus+"&deleteQrcodeError=false";
		//http://10.2.201.118/sanquan/ifs/proinfo.action?openid=OPENID&nikeName=NIKENAME&longitude=LONGITUDE&latitude=LATITUDE&code=CODE&userName=USERNAME&passwd=PASSWD
		
		ResourceBundle bundle = ResourceBundle.getBundle("transfer");
		String code="0321Z23000000MGT6A641";
		String url = bundle.getString("URL");
		String userName = bundle.getString("userName");
		String passwd = bundle.getString("passwd");
		String realUrl = String.format(url,code,userName,passwd);
		//String url = ResourceBundle.getBundle("transfer.properties").getString("eisp.changeUserStatusLog.url");
		JSONObject relust = HttpClientUtil.sendGet(realUrl, null, null);
		System.out.println(relust.get("INNERCODE"));
    	System.out.println(relust.getBoolean("RESULT"));
	}*/
}
