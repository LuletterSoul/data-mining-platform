package com.dm.org.service.impl;


import com.dm.org.base.BaseServiceInitializer;
import com.dm.org.model.DataSetAttribute;
import com.dm.org.utils.TestDataGenerator;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * DataSetAttributeServiceImpl Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * July 6, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class DataSetAttributeServiceImplTest extends BaseServiceInitializer
{
    private DataSetAttributeServiceImplAbstract dataSetAttributeService;

    private DataSetAttribute dataSetAttribute;

    @Autowired
    public void setDataSetAttributeService(DataSetAttributeServiceImplAbstract dataSetAttributeService)
    {
        this.dataSetAttributeService = dataSetAttributeService;
    }

    @Before
    public void before()
        throws Exception
    {
        dataSetAttribute = TestDataGenerator.buildDataSetAttribute();
    }

    @After
    public void after()
        throws Exception
    {}

}
