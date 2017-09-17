package com.dm.org.dao;


import com.dm.org.security.HmacSHA256Utils;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


/**
 * UserDao Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * ���� 29, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class UserDaoTest
{

    @Before
    public void before()
        throws Exception
    {}

    @After
    public void after()
        throws Exception
    {}

    /**
     * Method: setSessionFactory(SessionFactory sessionFactory)
     */
    @Test
    public void testSetSessionFactory()
        throws Exception
    {
        // TODO: Test goes here...
    }

    /**
     * Method: getSessionFactory()
     */
    @Test
    public void testGetSessionFactory()
        throws Exception
    {
        String key = "123";
        String content = "1";
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName("SHA-256");
        hashService.setGeneratePublicSalt(false);
        hashService.setHashIterations(5);
//        Hash hash =hashService.computeHashWithParams(request);
//        System.out.println(hash.toBase64());
//        HmacSHA256Utils.digest(salt, key);
//        content = HmacSHA256Utils.digest(key, content);
        HashRequest request = new HashRequest.Builder().setSource(ByteSource.Util.bytes(content+key)).build();
                Hash hash =hashService.computeHash(request);
        System.out.println(hash.toBase64());
    }

    /**
     * Method: saveUser(User user)
     */
    @Test
    public void testSaveUser()
        throws Exception
    {
        // TODO: Test goes here...
    }

    /**
     * Method: findByUserId(String id)
     */
    @Test
    public void testFindByUserId()
        throws Exception
    {
        // TODO: Test goes here...
    }

    /**
     * Method: getUserList()
     */
    @Test
    public void testGetUserList()
        throws Exception
    {
        // TODO: Test goes here...
    }

}
