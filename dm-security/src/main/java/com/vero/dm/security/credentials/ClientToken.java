package com.vero.dm.security.credentials;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

/**
 * 客户端用于生成消息摘要的封装体
 */
public class ClientToken
{
    private String token;

    private String apiKey;

    public ClientToken(String token, String apiKey)
    {
        this.token = token;
        this.apiKey = apiKey;
    }

    public ClientToken()
    {}

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    /**
     * @return 确保并发访问下，每个api调用时的授权认证是相互独立，不会出现脏读或重复读现象 此为解决并发问题的权宜之计;
     */
    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }
}
