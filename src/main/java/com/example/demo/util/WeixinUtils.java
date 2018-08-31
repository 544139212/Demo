package com.example.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeixinUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WeixinUtils.class);
	
	private static String jscode2sessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	private static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

	private static String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
	
	private static String appid = "wx32258027dcaf0663";
	
	private static String appsecret = "2f53f4e6ac1d2549fde65b085ce9cada";
	
	
	public static String getOpenId(String code) throws Exception {
    	String url = String.format(jscode2sessionUrl, appid, appsecret, code);
        return get(url, null);//{"session_key":"O7B0nGx438SYNTBTSaPhJg==","openid":"otHji5CMQMVQd40n7OTGY0PVzM74"}
    }
	
	public static String get(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String var7;
        try {
            List<NameValuePair> qparams = new ArrayList<>();
            if (params != null && qparams.size() > 0) {
                Iterator<Map.Entry<String, String>> var4 = params.entrySet().iterator();

                while (var4.hasNext()) {
                    Map.Entry<String, String> entry = var4.next();
                    qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                String query = URLEncodedUtils.format(qparams, "UTF-8");
                url = url + "?" + query;
            }
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return null;
                }
                var7 = EntityUtils.toString(entity, "UTF-8");
            } finally {
                response.close();
            }
        } catch (Exception var25) {
            throw var25;
        } finally {
            try {
                httpclient.close();
            } catch (IOException var23) {
                throw var23;
            }

        }
        return var7;
    }
}
