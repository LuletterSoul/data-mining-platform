package com.dm.org.service;


import com.dm.org.security.realm.StatelessInfo;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.crypto.hash.Hash;

import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:27 2017/9/14.
 * @since data-minning-platform
 */

public interface StatelessCredentialsService
{

    Hash computeHashWithParams(Object credentials, Map<String,?> params, int iterations);

    String generateRandomSalt(int numBytes);

    /**
     *
     * 客户端与服务器已经协商好使用公盐进行简单的SHA-256
     * 加密，用此方法加密后的非明文密码不可逆；
     * 客户端需要知晓对应利用公盐的加密方式
     * @param plaintext 用户传入的明文密码
     * @param customSalt 系统生成的公盐
     */
    String encryptPassword(String plaintext, String customSalt);

    /**
     * {@link com.dm.org.security.credentials.StatelessCredentialsMatcher#doCredentialsMatch(AuthenticationToken, AuthenticationInfo)}
     * 进行鉴权时，会传入
     * @param info 系统拥有的用户信息
     * @param iterations SHA-256的迭代次数
     * 进行
     */
    Hash  computeHashWithParams(StatelessInfo info, int iterations);

    Hash computeHash(String source);

    Hash digest(String message, String salt);

    Hash digestMultipleParams(String key, Map<String, ?> map);
}
