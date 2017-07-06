package com.dm.org.service.impl;


import com.dm.org.base.BaseServiceInitializer;
import com.dm.org.model.DataSetCollection;
import com.dm.org.utils.TestDataGenerator;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * DataSetCollectionServiceImpl Tester.
 *
 * @author XiangDe Liu qq313700046@icloud.com
 * @version 1.0
 * @since
 * 
 *        <pre>
 * July 6, 2017
 *        </pre>
 */
public class DataSetCollectionServiceImplTest extends BaseServiceInitializer
{

    private DataSetCollectionServiceImplAbstract dataSetCollectionService;
    private DataSetCollection dataSetCollection;

    @Autowired
    public void setDataSetCollectionService(DataSetCollectionServiceImplAbstract dataSetCollectionService)
    {
        this.dataSetCollectionService = dataSetCollectionService;
    }

    @Before

    public void before()
        throws Exception
    {
        dataSetCollection = TestDataGenerator.buildDataSetCollection();
    }

    @After
    public void after()
        throws Exception
    {

    }
    
    /**
     * Method: save()
     */
    @Test
    public void testSave()
                throws Exception
    {
        dataSetCollectionService.save(dataSetCollection);
    }

}
