package com.dm.org.security;


import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.util.ByteSource;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:22 2017/7/24.
 * @since data-mining-platform
 */

public interface UserPasswordService extends HashingPasswordService
{
    String encryptPasswordWithSalt(Object plaintext, Object customSalt) throws IllegalArgumentException;

    boolean passwordMatch(Object submittedPlaintext, String saved, ByteSource salt);

    ByteSource generateRandomSalt(int numBytes);

    Hash hashPassword(Object plaintext, Object customSalt);
}
