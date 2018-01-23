package com.vero.dm.security.credentials;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  16:01 2018/1/23.
 * @since data-minning-platform
 */

public interface TokenManager {
    String generateTimeOutToken(String username);

    ClientToken getTimeOutToken(String username, String dateString);

    String getHashToken(String key);

    boolean cleanTokenCache(String username);
}
