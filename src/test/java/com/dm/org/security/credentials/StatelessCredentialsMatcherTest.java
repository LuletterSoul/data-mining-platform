package com.dm.org.security.credentials;


import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dm.org.model.User;
import com.dm.org.security.realm.StatelessInfo;
import com.dm.org.security.realm.StatelessToken;
import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.UserService;
import com.dm.org.service.impl.StatelessCredentialsServiceImpl;


/**
 * StatelessCredentialsMatcher Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * September 14, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class StatelessCredentialsMatcherTest
{

    private StatelessCredentialsService service = new StatelessCredentialsServiceImpl();

    private StatelessCredentialsMatcher matcher = new StatelessCredentialsMatcher();

    private MockRestServiceServer serviceServer;

    private TokenManager tokenManager = new TokenManager();

    private UserService userService;

    @Before
    public void setUp() throws Exception {
//        serviceServer = MockRestServiceServer.createServer(r)
        DefaultPasswordService defaultPasswordService = (DefaultPasswordService) service;
        HashService hashService = defaultPasswordService.getHashService();
        ((DefaultHashService) hashService).setGeneratePublicSalt(false);
        matcher.setStatelessCredentialsService(service);
        matcher.setHashIterations(DefaultPasswordService.DEFAULT_HASH_ITERATIONS);
        matcher.setHashAlgorithmName(DefaultPasswordService.DEFAULT_HASH_ALGORITHM);
        tokenManager.setCredentialsService(service);
        tokenManager.setUserService(userService);
    }

    @Before
    public void before()
        throws Exception
    {}

    @After
    public void after()
        throws Exception
    {

    }


    /**
     * Method: doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
     */
    @Test
    public void testDoCredentialsMatch()
        throws Exception
    {
        //build a mock user.
        String username = "admin";
        String password = "123";
        User user = new User();
        user.setUserName(username);

        //generate a public salt.
        String publicSalt = service.generateRandomSalt(32);
        String enryptedPassword = service.encryptPassword(password, publicSalt);
        user.setPassword(enryptedPassword);

        //mock client request params.
        String param1 = "param1";
        String param2 = "param2";
        String param3 = "param3";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("param1", param1);
        params.add("param2", param2);
        params.add("param3", param3);

        //mock client's message digest generation.
        Hash hmacDigest = service.digestMultipleParams(user.getPassword(), params);
        Hash clientDigest = service.computeHash(hmacDigest.toBase64());

        StatelessToken token = new StatelessToken("admin",params, clientDigest.toBase64());
        StatelessInfo info = new StatelessInfo(user.getUserName(), user.getPassword(), params, "TEST_REALM");

        Assert.assertTrue(matcher.doCredentialsMatch(token, info));
    }
    /**
     * Method: testBase64Converter()
     */
    @Test
    public void testLOGGER()
                throws Exception
    {
        String de = "123";

    }

}
