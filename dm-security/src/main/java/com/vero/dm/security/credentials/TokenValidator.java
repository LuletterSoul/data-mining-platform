package com.vero.dm.security.credentials;

import java.nio.file.attribute.UserPrincipalNotFoundException;

/**
 * 验证
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  23:23 2018/2/9.
 * @since data-mining-platform
 */

public interface TokenValidator {

    /**
     *
     * 前端加密的密文就行匹配
     * @param sourceUsername 来源
     * @param providedCredential 客户端提供认证证书{=密码与公盐生成的密文}
     * @return 是否合法
     */
    boolean validate(String sourceUsername,String providedCredential);
}
