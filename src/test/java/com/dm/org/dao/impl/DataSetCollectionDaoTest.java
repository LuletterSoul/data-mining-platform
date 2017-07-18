package com.dm.org.dao.impl;


import com.dm.org.base.BaseDaoInitializer;
import com.dm.org.model.DataSetCollection;
import org.junit.Test;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * DataSetCollectionDao Tester.
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
public class DataSetCollectionDaoTest extends BaseDaoInitializer
{

    private DataSetCollectionDao dataSetCollectionDao;

    private DataSetCollection dataSetCollection;


    @Autowired
    public void setDataSetCollectionDao(DataSetCollectionDao dataSetCollectionDao)
    {
        this.dataSetCollectionDao = dataSetCollectionDao;
    }

    @Before
    public void before()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);
        dataSetCollection = new DataSetCollection();
    }
    /**
     * Method: save()
     */
    @Test
    public void testSave()
                throws Exception
    {
    }

}
