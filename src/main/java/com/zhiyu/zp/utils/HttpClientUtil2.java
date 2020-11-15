package com.zhiyu.zp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author wdj
 *
 */

public class HttpClientUtil2
{

    public HttpClientUtil2()
    {
    }

    private static CloseableHttpClient getHttpClient()
    {
        if(httpClient == null)
            synchronized(syncLock)
            {
                if(httpClient == null)
                    httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).setDefaultRequestConfig(config).build();
            }
        return httpClient;
    }

    public static String doCloseableHttpClient(ArrayList<NameValuePair> params,String url){
    	 String content = null;
	        CloseableHttpClient httpclient = null;
	        try {
	            httpclient = HttpClients.createDefault();
	            HttpPost httppost = new HttpPost(url);
	            HttpEntity formEntity = null;
	            try {
	                formEntity = new UrlEncodedFormEntity(params, "utf-8");
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }

	            httppost.setEntity(formEntity);
	            HttpResponse response = httpclient.execute(httppost);
	            content = EntityUtils.toString(response.getEntity(), "utf-8");
	            if (response.getStatusLine().getStatusCode() != 200) {
	                System.out.print("响应异常:\n" + content);
	                content = "";
	            }

	        } catch (Exception ignore) {
	            ignore.printStackTrace();
	        } finally {
	            if (httpclient != null) {
	                try {
	                    httpclient.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        System.out.println(content);
		return content;
    	
    }
     
    public static String doJsonPost(String url, Map params)
    {
        try
        {
            CloseableHttpClient httpClient = getHttpClient();
            String result = "";
            try
            {
                HttpPost httpRequst = new HttpPost(url);
                httpRequst.setHeader("Accept", "application/json");
                httpRequst.setHeader("Accept-Charset", "UFT-8");
                httpRequst.setHeader("Content-Type", "application/json; charset=UTF-8");
                if(params.containsKey("token"))
                {
                    httpRequst.setHeader("token", (String)params.get("token"));
                    params.remove("token");
                }
                String paramsJson = (new ObjectMapper()).writeValueAsString(params);
                httpRequst.setEntity(new StringEntity(paramsJson, Consts.UTF_8));
                CloseableHttpResponse httpResponse = httpClient.execute(httpRequst);
                if(httpResponse.getStatusLine().getStatusCode() == 200)
                {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    result = EntityUtils.toString(httpEntity, Consts.UTF_8);
                    System.out.println(result);
                }
            }
            catch(UnsupportedEncodingException e)
            {
                logger.error(e.getMessage());
                result = e.getMessage().toString();
            }
            catch(ClientProtocolException e)
            {
                logger.error(e.getMessage());
                result = e.getMessage().toString();
            }
            catch(IOException e)
            {
                logger.error(e.getMessage());
                result = e.getMessage().toString();
            }
            System.out.println(result);
            return result;
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
            return "";
        }
    }

    public static String doHttpPost(String url, Map params)
    {
    	 try
         {
             CloseableHttpClient httpClient = getHttpClient();
             String result = "";
             try
             {
		        HttpPost post;
		        List nvps;
		        httpClient = getHttpClient();
		        post = new HttpPost(url);
		        post.setHeader("Accept-Charset", "utf-8");
		        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		        if(params.containsKey("token"))
		        {
		            post.setHeader("token", (String)params.get("token"));
		            params.remove("token");
		        }
		        nvps = new ArrayList();
		        java.util.Map.Entry entry;
		        for(Iterator iterator = params.entrySet().iterator(); iterator.hasNext(); nvps.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue())))
		            entry = (java.util.Map.Entry)iterator.next();
		
		        String s;
		        post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		        CloseableHttpResponse httpResponse = httpClient.execute(post);
		        if(httpResponse.getStatusLine().getStatusCode() == 200)
		        {
		            HttpEntity httpEntity = httpResponse.getEntity();
		            result = EntityUtils.toString(httpEntity, Consts.UTF_8);
		        }
		    }
		    catch(UnsupportedEncodingException e)
		    {
		        logger.error(e.getMessage());
		        result = e.getMessage().toString();
		    }
		    catch(ClientProtocolException e)
		    {
		        logger.error(e.getMessage());
		        result = e.getMessage().toString();
		    }
		    catch(IOException e)
		    {
		        logger.error(e.getMessage());
		        result = e.getMessage().toString();
		    }
		    return result;
		}
		catch(Exception e)
		{
		    logger.error(e.getMessage());
		    e.printStackTrace(System.out);
		    return "";
		}
    }

    public static String get(String url, Map params)
    {
        CloseableHttpClient httpClient;
        HttpGet httpGet;
        List nvps;
        CloseableHttpResponse httpResponse;
        httpClient = getHttpClient();
        httpGet = new HttpGet(url);
        httpGet.setHeader("Accept-Charset", "utf-8");
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        nvps = new ArrayList();
        java.util.Map.Entry entry;
        for(Iterator iterator = params.entrySet().iterator(); iterator.hasNext(); nvps.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue())))
            entry = (java.util.Map.Entry)iterator.next();

        httpResponse = null;
        String s;
        httpGet.setURI(new URI((new StringBuilder(String.valueOf(httpGet.getURI().toString()))).append("?").append(EntityUtils.toString(new UrlEncodedFormEntity(nvps))).toString()));
        httpResponse = httpClient.execute(httpGet);
        if(httpResponse.getStatusLine().getStatusCode() != 200)
            break MISSING_BLOCK_LABEL_357;
        HttpEntity httpEntity = httpResponse.getEntity();
        s = EntityUtils.toString(httpEntity);
        try
        {
            if(httpResponse != null)
                httpResponse.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
        }
        return s;
        try
        {
            if(httpResponse != null)
                httpResponse.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
        }
        break MISSING_BLOCK_LABEL_411;
        Exception exception;
        try
        {
            if(httpResponse != null)
                httpResponse.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
        }
        throw exception;
        try
        {
            if(httpResponse != null)
                httpResponse.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
        }
        break MISSING_BLOCK_LABEL_411;
        return "";
    }

    public static boolean downloadFile(String url, Map params, String filePath)
    {
        boolean isSuccess;
        File file;
        CloseableHttpClient httpClient;
        HttpGet httpGet;
        List nvps;
        CloseableHttpResponse httpResponse;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        isSuccess = true;
        file = new File(filePath);
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/octet-stream");
        nvps = new ArrayList();
        java.util.Map.Entry entry;
        for(Iterator iterator = params.entrySet().iterator(); iterator.hasNext(); nvps.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue())))
            entry = (java.util.Map.Entry)iterator.next();

        httpResponse = null;
        bis = null;
        bos = null;
        try
        {
            httpGet.setURI(new URI((new StringBuilder(String.valueOf(httpGet.getURI().toString()))).append("?").append(EntityUtils.toString(new UrlEncodedFormEntity(nvps))).toString()));
            httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity httpEntity = httpResponse.getEntity();
                bis = new BufferedInputStream(httpEntity.getContent());
                bos = new BufferedOutputStream(new FileOutputStream(file));
                int inByte;
                while((inByte = bis.read()) != -1) 
                    bos.write(inByte);
                bos.flush();
            }
            break MISSING_BLOCK_LABEL_431;
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
            isSuccess = false;
        }
        try
        {
            if(httpResponse != null)
                httpResponse.close();
            if(bis != null)
                bis.close();
            if(bos != null)
                bos.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
        }
        break MISSING_BLOCK_LABEL_489;
        try
        {
            if(httpResponse != null)
                httpResponse.close();
            if(bis != null)
                bis.close();
            if(bos != null)
                bos.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
        }
        try
        {
            if(httpResponse != null)
                httpResponse.close();
            if(bis != null)
                bis.close();
            if(bos != null)
                bos.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace(System.out);
        }
        return isSuccess;
    }

    private static  Logger logger = LoggerFactory.getLogger(HttpClientUtil2.class);
    private static PoolingHttpClientConnectionManager clientConnectionManager;
    private static CloseableHttpClient httpClient = null;
    private static final Object syncLock = new Object();
    private static RequestConfig config = RequestConfig.custom().setCookieSpec("standard-strict").build();

    static 
    {
        clientConnectionManager = null;
        org.apache.http.config.Registry socketFactoryRegistry = RegistryBuilder.create().register("https", SSLConnectionSocketFactory.getSocketFactory()).register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
        clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        clientConnectionManager.setMaxTotal(50);
        clientConnectionManager.setDefaultMaxPerRoute(25);
    }
}
