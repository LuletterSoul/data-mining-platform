package com.vero.dm.security.credentials;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:02 2018/2/10.
 * @since data-mining-platform
 */

public interface TokenGenerator
{

    /**
     * 生成一个有期限的可传递令牌 用于客户端生成消息摘要 以及服务端计算并验证客户端消息摘要的合法性
     *
     * @param accessToken
     *            用户名
     * @return 有期限的令牌
     */
    String generateExpiredToken(String accessToken,String privateSalt);

    /**
     * 生成访问令牌
     * 访问令牌的生成应该是重量级的
     *
     * @param username
     *            用户名
     * @return
     */
    String generateHighSecurityTAccessToken(String username,String privateSalt);

    /**
     * 打乱生成的令牌 打乱令牌生成顺序
     *
     * @param sourceToken
     * @return
     */
    String shuffleToken(String sourceToken);
}
