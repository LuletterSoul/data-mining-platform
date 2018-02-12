package com.vero.dm.security.credentials;

import java.util.List;

/**
 * 一次性令牌的维护者 客户端与服务器已经协商好“Http传递消息摘要”的交互规则 但交互规则可能存在并发访问问题: 客户端在对应response未返回时候,再次发起了一个请求, 一个一次性令牌
 *
 * @see DisposableTokenWriter 当{@link com.vero.dm.security.realm.StatelessRealm}生成
 *      {@link com.vero.dm.security.realm.StatelessInfo}时, 需要查找 {@link }这条规则后该接口定义了在
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:46 2018/2/12.
 * @since data-mining-platform
 */

public interface DisposableTokenMaintainer
{



    /**
     * 签发一次性令牌
     * 
     * @param key
     *            访问令牌
     */
    void signToken(String key,String privateSalt);

    /**
     * 根据客户端的认证信息获取最后一次请求派发的
     *
     * 一次性令牌
     *
     * 一次性令牌作为API访问的传递参数 用于客户端消息摘要的生成 以及服务器的认证授权
     *
     * @param key
     *            access token
     * @return
     */
    String queryLatestToken(String key);




    /**
     * 获取分派出的所有Token
     * @param key
     * @return
     */
    List<String> retrieveTokenList(String key);
}
