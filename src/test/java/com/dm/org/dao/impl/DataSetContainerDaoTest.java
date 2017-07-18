package com.dm.org.dao.impl;


import com.dm.org.base.BaseDaoInitializer;
import com.dm.org.model.DataSetContainer;
import org.junit.Before;
import org.junit.After;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * DataSetContainerDao Tester.
 *
 * @author XiangDe Liu qq313700046@icloud.com
 * @version 1.0
 * @since <pre>
 * July 5, 2017
 *        </pre>
 */
public class DataSetContainerDaoTest extends BaseDaoInitializer
{
    private DataSetContainerDao dataSetContainerDao;

    @Mock
    private DataSetContainer dataSetContainer;

    @Autowired
    public void setDataSetContainerDao(DataSetContainerDao dataSetContainerDao)
    {
        this.dataSetContainerDao = dataSetContainerDao;
    }

    @Before

    public void before()
            throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after()
            throws Exception
    {

    }

}
