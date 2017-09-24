package com.dm.org.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;

import com.dm.org.security.realm.StatelessInfo;
import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in  12:12 2017/9/14.
 * @since data-mining-platform
 */

public class StatelessCredentialsServiceImpl extends DefaultPasswordService implements StatelessCredentialsService {

    private Mac mac;
    private UserService userService;
    public final static String MAC_DEFAULT_ALGORITHM = "HmacSHA256";
    public final static String SERCRET_KEY_DEFAULT_ALGORITHM = "HMACSHA256";
    private SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public StatelessCredentialsServiceImpl() {
        try {
            mac = Mac.getInstance(MAC_DEFAULT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Hash computeHashWithParams(Object credentials, Map<String,?> params, int iterations) {
        String credential = (String) credentials;
        String paramsString = buildParamsString(params);
        HashRequest request = new HashRequest.Builder()
                .setSource(createByteSource(credential + paramsString))
                .setAlgorithmName(DEFAULT_HASH_ALGORITHM)
                .setIterations(1000).build();
        return  getHashService().computeHash(request);
    }

    @Override
    public String generateRandomSalt(int numBytes) {
        return randomNumberGenerator.nextBytes(numBytes).toBase64();
    }


    @Override
    public String encryptPassword(String plaintext, String customSalt) {
        HashRequest request = new HashRequest.Builder().setSource(plaintext + customSalt)
                .setAlgorithmName(DEFAULT_HASH_ALGORITHM)
                .setIterations(1000).build();
        return getHashService().computeHash(request).toBase64();
    }
    @Override
    public Hash computeHashWithParams(StatelessInfo info, int iterations) {
        String credentials = (String) info.getCredentials();
        String paramsString = buildParamsString(info.getClientParams());
        String serverDigest = digest(credentials, paramsString).toBase64();
        HashRequest request = new HashRequest.Builder()
                .setSource(createByteSource(serverDigest))
                .setAlgorithmName(DEFAULT_HASH_ALGORITHM)
                .setIterations(1000).build();
        return  getHashService().computeHash(request);
    }

    /**
     *
     * 进行简单的SHA-256 的哈希迭代算法
     *
     */
    @Override
    public Hash computeHash(String source) {
        HashRequest request = new HashRequest.Builder()
                .setSource(createByteSource(source))
                .setAlgorithmName(DEFAULT_HASH_ALGORITHM)
                .setIterations(1000).build();
        return getHashService().computeHash(request);
    }

    /**
     *
     * 使用HMAC -256 算法生成一次消息摘要
     *
     */
    public  Hash digest(String message, String salt) {
        try
        {
            byte[] secretByte = salt.getBytes();
            byte[] dataBytes = message.getBytes();
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            SimpleHash digestHash = new SimpleHash(DefaultPasswordService.DEFAULT_HASH_ALGORITHM);
            digestHash.setBytes(mac.doFinal(dataBytes));
            return digestHash;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  Hash digestMultipleParams(String key, Map<String, ?> map) {
       ;
        return digest(key,buildParamsString(map));
    }

    private String buildParamsString(Map<String, ?> map) {
        StringBuilder s = new StringBuilder();
        for(Object values : map.values()) {
            if(values instanceof String[]) {
                for(String value : (String[])values) {
                    s.append(value);
                }
            } else if(values instanceof List) {
                for(String value : (List<String>)values) {
                    s.append(value);
                }
            } else {
                s.append(values);
            }
        }
        return s.toString();
    }

}
