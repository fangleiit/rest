package com.biz.bizunited.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.biz.bizunited.vo.AddressVo;


/**
 * 
 * @ClassName: MapsUtil 
 * @Description: 地图工具类
 * @author ian.zeng
 * @date 2017年7月19日 下午12:21:45
 */
public class MapsUtil {
	/**
	 * 百度坐标
	 */
	static class BaiduLocation {
	    public float longitude, latitude;
	    public String baidux, baiduy;
	    public boolean ok = false;
	}
	
	static class Location{
		public String x;
		public String y;
		
		public String getX() {
			return x;
		}
		public void setX(String x) {
			this.x = x;
		}
		public String getY() {
			return y;
		}
		public void setY(String y) {
			this.y = y;
		}
		
	}
	private static final Logger logger = LoggerFactory.getLogger(MapsUtil.class);
	/**
	 * 百度开发者key
	 */
	private static final String key = "SVh7hzBinAgs0PKMqXvlPKbNbFWwtjgx";
	
	//private static double EARTH_RADIUS = 6378.137;
	/**
	 * 地球半径
	 */
	private static double EARTH_RADIUS = 6370996.81; 
	
	/**
	 * 通过百度地图根据经纬度获取信息
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws IOException
	 */
	public static JSONObject getMapsInfo(String longitude,String latitude) throws IOException{
		String result = ""; 
		JSONObject json = new JSONObject();
		try {
			String urlString = String.format("http://api.map.baidu.com/geocoder/v2/?"
					+ "callback=renderReverse&location=%s,%s&output=json&pois=1&ak=%s", 
					latitude,longitude,key);
			URL url = new URL(urlString);    
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();    
            conn.setDoOutput(true);    
            conn.setRequestMethod("POST");    
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));    
            String line;    
	        while ((line = in.readLine()) != null) {    
	        	result += line+"\n";    
	        }    
            in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		result = result.trim();
		result = result.substring(result.indexOf("(")+1);
		result = result.substring(0,result.length()-1);
		JSONObject jsStr = JSONObject.parseObject(result);
		String status = jsStr.getString("status");
		if("2".equals(status)){
			logger.debug("获取地图信息：非法请求参数");
		}
		if("0".equals(status)){
			json = JSONObject.parseObject(jsStr.getString("result"));
		}
		return json;
	}
	/**
	 * 获取省市区地理位置
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static AddressVo getAddress(String longitude,String latitude){
		AddressVo address = new AddressVo();
		try {
			String baidux = "" ;
			String baiduy = "";
			//将经纬度转换为百度专用坐标
			BaiduLocation baidu = new BaiduLocation();
			
			if(StringUtil.isEmpty(longitude)){
				baidu.longitude = (float) 113.6063;//经度
				baidu.latitude = (float) 34.86607;//纬度
			}else{
				baidu.longitude = (float) Float.valueOf(longitude);//经度
				baidu.latitude = (float) Float.valueOf(latitude);//纬度
			}
			
			GetBaiduLocation(baidu);
			if(baidu.ok) {
				//转换成功
                baidux = baidu.baidux;
                baiduy = baidu.baiduy;
            }
			JSONObject json = getMapsInfo(baidux,baiduy);
			address = JSONObject.parseObject(json.getString("addressComponent"), AddressVo.class);
			address.setLongitude(baidux);
			address.setLatitude(baiduy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return address;
	}
	/**
	 * 经纬度转百度坐标调用百度api
	 * @param x
	 * @param y
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String GetBaiduLocation(float x, float y) throws MalformedURLException, IOException {
		String url = String.format("http://api.map.baidu.com/geoconv/v1/?coords=%s,%s&from=1&to=5&ak=%s",x,y,key);
	    HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url).openConnection());
	    urlConnection.connect();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	    String lines = reader.readLine();
	    reader.close(); 
	    urlConnection.disconnect();
	    return lines;
	}
	/**
	 * 经纬度转百度坐标获取转换结果
	 * @param bl
	 * @return
	 */
	public static boolean GetBaiduLocation(BaiduLocation bl) {
	    try {
	        bl.ok = false;
	        String res = GetBaiduLocation(bl.longitude, bl.latitude);
	        JSONObject jsStr = JSONObject.parseObject(res);
	        List<Location> list = new ArrayList<MapsUtil.Location>();
	        list = JSONObject.parseArray(jsStr.getString("result"), Location.class);
	        for (Location location : list) {
				bl.ok = true;
				bl.baidux = location.x;
				bl.baiduy = location.y;
				break;
			}
	        /*if(res.startsWith("{") && res.endsWith("}")) {
	            res = res.substring(1, res.length() - 2).replace("\"", "");
	            String[] lines = res.split(",");
	            for(String line : lines) {
	                String[] items = line.split(":");
	                if(items.length == 2) {
	                    if("error".equals(items[0])) {
	                        bl.ok = "0".equals(items[1]);
	                    }
	                    if("x".equals(items[0])) {
	                        bl.baidux = ConvertBase64(items[1]);
	                    }
	                    if("y".equals(items[0])) {
	                        bl.baiduy = ConvertBase64(items[1]);
	                    }
	                }
	            }
	        }*/
	    } catch (Exception e) {
	    	e.printStackTrace();
	        bl.ok = false;
	    } 
	    return bl.ok;   
	}
	private static float ConvertBase64(String str) {
	    byte[] bs = Base64.decode(str);       
	    return Float.valueOf(new String(bs));
	}
	/**
	 * 计算两点之间的距离
	 * @param lonstr1
	 * @param latstr1
	 * @param lonstr2
	 * @param latstr2
	 * @return
	 */
	public static Integer getDistance(String lonstr1,String latstr1,String lonstr2,String latstr2){
		
		Double lon1 = Double.parseDouble(lonstr1);
		Double lat1 = Double.parseDouble(latstr1);
		Double lon2 = Double.parseDouble(lonstr2);
		Double lat2 = Double.parseDouble(latstr2);
		
		double radlon1 = rad(lon1);
		double radlat1 = rad(lat1);
		double radlon2 = rad(lon2);
		double radlat2 = rad(lat2);
		double distance = Math.acos((Math.sin(radlat1) * Math.sin(radlat2) + 
				Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radlon2 - radlon1)));
		
		distance = distance * EARTH_RADIUS;
		distance = Math.floor(distance);
		Integer dis = new Double(distance).intValue();
		return dis;
	}
	
	private static double rad(Double d) { 
        return d * Math.PI / 180.0; 
    }
	/**
	 * 将wgs84坐标转换为百度坐标
	 * @param lon 经度
	 * @param lat 纬度
	 * @return
	 */
	public static Map<String, String> getBaiduLocation(String lon,String lat){
		Map<String, String> baiduMaps = new HashMap<String, String>();
		String longitude = "";
		String latitude = "";
		BaiduLocation baidu = new BaiduLocation();
		baidu.longitude = (float) Float.valueOf(lon);
		baidu.latitude = (float) Float.valueOf(lat);
		GetBaiduLocation(baidu);
		if(baidu.ok){
			longitude = baidu.baidux;
			latitude = baidu.baiduy;
		}
		baiduMaps.put("longitude", longitude);
		baiduMaps.put("latitude", latitude);
		return baiduMaps;
	}
	/*public static void main(String[] args) {
		String baidux = "" ;
		String baiduy = "";
		String lon = "113.605934";
		String lan = "34.865982";
		BaiduLocation baidu = new BaiduLocation();
		baidu.longitude = (float) Float.valueOf(lon);//经度
		baidu.latitude = (float) Float.valueOf(lan);
		GetBaiduLocation(baidu);
		if(baidu.ok) {
			//转换成功
            baidux = baidu.baidux;
            baiduy = baidu.baiduy;
        }
		System.out.println(baidux+","+baiduy);
		String lon = "113.605934";
		String lat = "34.865982";
		AddressVo addressVo = getAddress(lon, lat);
		Map<String, String> baiduMaps = new HashMap<String, String>();
		//{longitude=113.61847588854, latitude=34.870951519297}
		//longitude=113.61847670211,34.870947194753
		baiduMaps = getBaiduLocation(lon, lat);
		System.out.println(1);
	}*/
}
