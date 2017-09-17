package com.dm.org.security.credentials;

import com.dm.org.security.realm.SimpleHash;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.shiro.crypto.hash.Hash;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in  10:43 2017/9/16.
 * @since data-mining-platform
 */


public class TimeOutToken{
    private String publicSalt;
    @JsonDeserialize(as = SimpleHash.class)
    private Hash timeOutTokenHash;
    private String timestamp;


    public String getPublicSalt() {
        return publicSalt;
    }

    public void setPublicSalt(String publicSalt) {
        this.publicSalt = publicSalt;
    }

    public Hash getTimeOutTokenHash() {
        return timeOutTokenHash;
    }
    /**
     *
     * 混入了私盐、用户名、时间戳的一次性服务器要返回的证书；
     * @param timeOutTokenHash {@link TokenManager} 根据用户名以及当前时间生成的封装验证信息;
     *客户端必须提前申请到一次性证书，进行无状态授权验证
     */
    public void setTimeOutTokenHash(Hash timeOutTokenHash) {
        this.timeOutTokenHash = timeOutTokenHash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
