package com.dm.org.dao.impl;


import com.dm.org.base.BaseDaoInitializer;
import org.junit.Before;
import org.mockito.MockitoAnnotations;


/**
 * DataSetAttributeDao Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * July 5, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class DataSetAttributeDaoTest extends BaseDaoInitializer
{

    private DataSetAttributeDao dataSetAttributeDao;
    @Before
    public void before()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

}
