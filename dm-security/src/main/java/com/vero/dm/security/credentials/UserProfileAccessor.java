package com.vero.dm.security.credentials;


import com.vero.dm.repository.dto.UserDto;


/**
 * 从缓存中拉取对应的用户信息
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:01 2018/2/10.
 * @since data-mining-platform
 */

public interface UserProfileAccessor
{

    /**
     * 拉取用户信息
     * 
     * @param accessToken
     *            授权证书
     * @return 用户信息
     */
    UserDto fetchProfile(String accessToken);

}
