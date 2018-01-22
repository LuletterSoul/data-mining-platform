package com.vero.dm.security.credentials;


import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 1:31 2017/7/25.
 * @since data-minning-platform
 */

public class UserPasswordSaltedToken extends UsernamePasswordToken
{
    private static final long serialVersionUID = -8915474893639656992L;

    private ByteSource randomSalt;

    public UserPasswordSaltedToken(String username, String password, ByteSource randomSalt)
    {
        super(username, password);
        this.randomSalt = randomSalt;
    }

    public void setRandomSalt(ByteSource randomSalt)
    {
        this.randomSalt = randomSalt;
    }

    public ByteSource getRandomSalt()
    {
        return randomSalt;
    }
}
