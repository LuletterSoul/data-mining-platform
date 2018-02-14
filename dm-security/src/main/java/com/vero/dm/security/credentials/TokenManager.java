package com.vero.dm.security.credentials;


import com.vero.dm.model.User;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:01 2018/1/23.
 * @since data-minning-platform
 */

public interface TokenManager
{
    /**
     * 申请一个有期限的令牌
     * 
     * @param username
     *            用户名
     * @param providedCredential
     *            客户端提供的访问证书
     * @param dateString
     *            请求的时间戳
     * @return 有期限的令牌
     */
    String applyExpiredToken(String username, String providedCredential, String dateString);



    /**
     * 根据用户清除对应用户的令牌
     * 
     * @param username
     *            用户名
     * @return 清除成功
     */
    boolean cleanTokenCache(String username);



    /**
     * 签发访问证书:用户申请证书后，服务器备忘用户信息 可用于查询用户角色、权限
     * 
     * @param accessToken
     *            有效证书
     * @param user
     *            用户信息
     */
    void signAccessToken(String accessToken, User user);

    /**
     * 单纯判断令牌是否注册于服务器缓存中 不会执行令牌过期的判断逻辑
     * 
     * @param accessToken
     *            访问令牌
     * @return 是否注册
     */
    boolean isAccessTokenRegistered(String accessToken);
}
