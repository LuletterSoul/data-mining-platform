package com.dm.org.security;

import com.dm.org.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  16:53 2017/7/14.
 * @description
 * @modified by:
 */
public class PasswordHelper
 {
  private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
  private static final String ALGORITHM_STRATIGY = "md5";
  private static final int HASH_ITERATIONS = 2;

  public static void encryptPassword(User user)
  {
      user.setSalt(randomNumberGenerator.nextBytes().toHex());

      String encryptedPassword=new SimpleHash(
              ALGORITHM_STRATIGY,user.getPassword(),
              ByteSource.Util.bytes(user.getCredentialsSalt()),
              HASH_ITERATIONS).toHex();
      user.setPassword(encryptedPassword);
  }
}

