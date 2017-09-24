package com.dm.org.client;


import java.io.IOException;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.MockRestServiceServer;

import com.dm.org.model.User;
import com.dm.org.security.constants.Constants;
import com.dm.org.security.credentials.TimeOutToken;
import com.dm.org.utils.DateStyle;
import com.dm.org.utils.DateUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:24 2017/9/14.
 * @since data-mining-platform
 */

public class MockServerClientTest extends AbstractClientTest
{

    private MockRestServiceServer mockRestServiceServer;

    private User user = new User();

    // 用于服务器模拟客户端的消息摘要生成；
    private Map<String, String> paramsDigest;

    private List<NameValuePair> httpClientParamsList = new LinkedList<NameValuePair>();

    private String currentDate;

    private int loginStatus = 0;

    private int tokenStatus = 0;

    @Before

    public void setUp()
        throws Exception
    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DefaultPasswordService defaultPasswordService = (DefaultPasswordService) service;
        HashService hashService = defaultPasswordService.getHashService();
        ((DefaultHashService) hashService).setGeneratePublicSalt(false);
    }

    @Before
    public void before()
        throws Exception
    {
        user.setUsername("zhang");
        user.setPassword("123");
        currentDate =  DateUtil.DateToString(new Date(),
                DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
    }

    @Test
    public void testGetTokenSuccess()
        throws Exception
    {
        token();
        Assert.assertEquals(tokenStatus, HttpStatus.SC_OK);
    }

    /**
     * Method: testGetTokenFailed()
     */
    @Test
    public void testGetTokenFailed()
                throws Exception
    {
        user.setUsername("zhang11");
        token();
        Assert.assertEquals(tokenStatus, HttpStatus.SC_NOT_FOUND);
    }

    /**
     * Method: testTokenTimeOut()
     */
    @Test
    public void testTestTokenTimeOut()
            throws Exception
    {
        currentDate = "2017-09-16 12:06:01";
        login();
        Assert.assertEquals(loginStatus, HttpStatus.SC_FORBIDDEN);
    }

    /**
     * Method: testLoginSuccess()
     */
    @Test
    public void testLoginSuccess()
            throws Exception
    {
        login();
        Assert.assertEquals(loginStatus, HttpStatus.SC_OK);
    }

    private TimeOutToken token() throws IOException {
        HttpPost httpPost = new HttpPost(baseURL + "/user/token");
        // 用于Http Client 请求参数的构造器;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // 用于证书生成的服务类，生成消息摘要;
        Map<String, String> paramsDigest = new LinkedHashMap<String, String>();
        // 模拟前端消息摘要的生成;
        paramsDigest.put(Constants.PARAM_USERNAME, user.getUsername());
        String clientDigest = service.digestMultipleParams(user.getPassword(),
            paramsDigest).toBase64();
        params.add(new BasicNameValuePair(Constants.PARAM_DIGEST, clientDigest));
        params.add(new BasicNameValuePair(Constants.PARAM_USERNAME, user.getUsername()));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
        // 写入前后端协商好的请求头
        httpPost.setHeader(Constants.HEADER_TIMESTAMP, currentDate);
        // 写入请求参数;
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost);
        tokenStatus = response.getStatusLine().getStatusCode();
        HttpEntity httpEntity = response.getEntity();
        String jsonString = EntityUtils.toString(httpEntity);
        if (jsonString == null) {
            return null;
        }
        System.out.println(jsonString);
        return objectMapper.readValue(jsonString, TimeOutToken.class);
    }

    private void login() throws IOException {
        TimeOutToken timeOutToken = token();
        HttpPost httpLoginPost = new HttpPost(baseURL + "/user/login");
        // 从服务器传回的公盐 ,模拟在客户端处的简单加密
        String enryptedPassWord = service.encryptPassword(user.getPassword(),
                timeOutToken.getPublicSalt());
        //哈希迭代次数;
        paramsDigest = new LinkedHashMap<String, String>();
        paramsDigest.put("username", user.getUsername());
        //抽取服务器返回的一次性令牌;
        String tokenTimeOut = timeOutToken.getTimeOutToken();
        String message =tokenTimeOut +enryptedPassWord;
        //生成客户端的消息摘要
        String digestSources = service.digestMultipleParams(message, paramsDigest).toBase64();
        String clientDigest = service.computeHash(digestSources).toBase64();
        httpClientParamsList.add(new BasicNameValuePair(Constants.PARAM_USERNAME, user.getUsername()));
        httpClientParamsList.add(new BasicNameValuePair(Constants.PARAM_DIGEST, clientDigest));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(httpClientParamsList, "utf-8");
        httpLoginPost.setHeader(Constants.HEADER_TIMESTAMP, currentDate);
        System.out.println(currentDate);
        httpLoginPost.setEntity(entity);
        HttpResponse response = client.execute(httpLoginPost);
        HttpEntity httpEntity = response.getEntity();
        String jsonString = EntityUtils.toString(httpEntity);
        System.out.println(jsonString);
        loginStatus = response.getStatusLine().getStatusCode();
    }
}
