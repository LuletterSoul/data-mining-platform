package com.dm.org.service.impl;


import com.dm.org.base.BaseServiceInitializer;
import com.dm.org.model.DataSetContainer;
import com.dm.org.utils.TestDataGenerator;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * DataSetContainerServiceImpl Tester.
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
public class DataSetContainerServiceImplTest extends BaseServiceInitializer
{
    private DataSetContainerServiceImplAbstract dataSetContainerService;

    private DataSetContainer dataSetContainer;

    @Autowired
    public void setDataSetContainerService(DataSetContainerServiceImplAbstract dataSetContainerService)
    {
        this.dataSetContainerService = dataSetContainerService;
    }

    @Before
    public void before()
        throws Exception
    {
        dataSetContainer = TestDataGenerator.buildDataSetContainer();
    }

    @After
    public void after()
        throws Exception
    {

    }

}
