package com.vero.dm.security.credentials;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:43 2017/9/16.
 * @since data-mining-platform
 */

public class TimeOutToken
{
    private String publicSalt;

    private String timeOutToken;

    private String timestamp;

    public String getPublicSalt()
    {
        return publicSalt;
    }

    public void setPublicSalt(String publicSalt)
    {
        this.publicSalt = publicSalt;
    }

    public String getTimeOutToken()
    {
        return timeOutToken;
    }

    /**
     * 混入了私盐、用户名、时间戳的一次性证书；
     * 
     * @param timeOutToken
     *            {@link DefaultTokenManager} 根据用户名以及当前时间生成的封装验证信息; 客户端必须提前申请到一次性证书，进行无状态授权验证
     */
    public void setTimeOutToken(String timeOutToken)
    {
        this.timeOutToken = timeOutToken;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
}
