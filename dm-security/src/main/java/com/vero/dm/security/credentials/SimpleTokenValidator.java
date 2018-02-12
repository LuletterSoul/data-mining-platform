package com.vero.dm.security.credentials;


import com.vero.dm.exception.auth.UnknownAccountException;
import com.vero.dm.exception.error.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vero.dm.model.User;
import com.vero.dm.service.UserService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:24 2018/2/9.
 * @since data-mining-platform
 */
@Component
public class SimpleTokenValidator implements TokenValidator
{

    @Autowired
    private StatelessCredentialsComputer credentialsService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Override
    public boolean validate(String sourceUsername, String providedCredential)
    {
        User user = validateUsername(sourceUsername);
        String reliableToke = credentialsService.computeNegotiatedApplyToken(user.getPassword(),
            user.getPublicSalt());
        return reliableToke.equals(providedCredential);
    }

    private User validateUsername(String sourceUsername)
    {
        User user = userService.fetchByUserName(sourceUsername);
        if (user == null)
        {
            String message = "Unknown username: " + sourceUsername;
            throw new UnknownAccountException(message, ExceptionCode.UnknownAccount);
        }
        return user;
    }
}
