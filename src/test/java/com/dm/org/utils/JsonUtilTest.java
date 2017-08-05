package com.dm.org.utils;


import com.dm.org.base.SecurityTestingInitializer;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


/**
 * JsonUtil Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * July 20, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class JsonUtilTest extends SecurityTestingInitializer
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
     * Method: toJSon(Object object)
     */
    @Test
    public void testToJSon()
        throws Exception
    {
        System.out.println(JsonUtil.toJSon(u1));
    }

    /**
     * Method: getObjectFromStr(Class<T> v, String json)
     */
    @Test
    public void testGetObjectFromStr()
        throws Exception
    {
        // TODO: Test goes here...
    }

    /**
     * Method: getObjectFromMap(Class<T> v, HashMap<String, Object> map)
     */
    @Test
    public void testGetObjectFromMap()
        throws Exception
    {
        // TODO: Test goes here...
    }

    /**
     * Method: getArrayListObjectFromStr(Class<T> v, String json)
     */
    @Test
    public void testGetArrayListObjectFromStr()
        throws Exception
    {
        // TODO: Test goes here...
    }

    /**
     * Method: getArrayListMapFromStr(String json)
     */
    @Test
    public void testGetArrayListMapFromStr()
        throws Exception
    {
        // TODO: Test goes here...
    }

}
